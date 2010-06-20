require 'digest/sha1'
require 'digest/md5'

class User < ActiveRecord::Base
  include ERB::Util # for the h() helper

  has_many :tracks
                          
  has_many :relations_to,
            :foreign_key => 'user_id',
            :class_name => 'Friendship'
  has_many :relations_from,
            :foreign_key => 'friend_id',
            :class_name => 'Friendship'                             

  #has_many :linked_to,
  # :through => :relations_to,   :source => :friend
  
  has_many :friends,
            :through => :relations_to,
            :source => :friend,
            :conditions => { :friendships => { :is_approved => true } }

  has_many :friend_requests,
            :through => :relations_to,
            :source => :friend,
            :conditions => { :friendships => { :is_approved => false } }


  attr_accessor :password # virtual attribute for the unencrypted password

  validates_presence_of     :login, :email
  validates_presence_of     :password,                   :if => :password_required?
  validates_presence_of     :password_confirmation,      :if => :password_required?
  validates_length_of       :password, :within => 4..40, :if => :password_required?
  validates_confirmation_of :password,                   :if => :password_required?
  validates_length_of       :login,    :within => 3..40
  validates_length_of       :email,    :within => 3..100
  validates_uniqueness_of   :login, :email, :case_sensitive => false
  validates_inclusion_of    :is_public, :in => [true, false]
  validates_inclusion_of    :show_email, :in => [true, false]

  before_save :encrypt_password

  # prevents a user from submitting a crafted form that bypasses activation
  # anything else you want your user to change should be added here.
  attr_accessible :login, :email, :password, :password_confirmation, :is_public, :show_email, :name, :about

  named_scope :public, :conditions => { :is_public => true }

  def to_s
    name.blank? ? login : name
  end

  def public?
    self.is_public
  end

  def avatar_url(size = 256)
    'http://www.gravatar.com/avatar/' + Digest::MD5.hexdigest(self.email) + "?s=#{size}&d=identicon"
  end

  def avatar_tag(size = 256)
    url = avatar_url size
    '<img src="' + h(url) + '" alt="' + h("Avatar of #{self}") + '" title="' + h(self.to_s) + '" />'
  end


  # Authenticates a user by their login name and unencrypted password.  Returns the user or nil.
  def self.authenticate(login, password)
    u = find_by_login(login) # need to get the salt
    u && u.authenticated?(password) ? u : nil
  end

  # Encrypts some data with the salt.
  def self.encrypt(password, salt)
    Digest::SHA1.hexdigest("--#{salt}--#{password}--")
  end

  # Encrypts the password with the user salt
  def encrypt(password)
    self.class.encrypt(password, salt)
  end

  def authenticated?(password)
    crypted_password == encrypt(password)
  end

  def remember_token?
    remember_token_expires_at && Time.now.utc < remember_token_expires_at 
  end

  # These create and unset the fields required for remembering users between browser closes
  def remember_me
    remember_me_for 2.weeks
  end

  def remember_me_for(time)
    remember_me_until time.from_now.utc
  end

  def remember_me_until(time)
    self.remember_token_expires_at = time
    self.remember_token            = encrypt("#{email}--#{remember_token_expires_at}")
    save(false)
  end

  def forget_me
    self.remember_token_expires_at = nil
    self.remember_token            = nil
    save(false)
  end

  # Returns true if the user has just been activated.
  def recently_activated?
    @activated
  end

  protected
    # before filter 
    def encrypt_password
      return if password.blank?
      self.salt = Digest::SHA1.hexdigest("--#{Time.now.to_s}--#{login}--") if new_record?
      self.crypted_password = encrypt(password)
    end
      
    def password_required?
      crypted_password.blank? || !password.blank?
    end
    
    
end
