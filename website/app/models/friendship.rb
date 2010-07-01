class Friendship < ActiveRecord::Base
  belongs_to :user

  belongs_to :friend,
            :class_name => 'User',
            :foreign_key => 'friend_id'
            
  validates_uniqueness_of :friend_id, :scope => [:user_id]

  attr_accessible :user_id, :friend_id

  #after_save :add_reversed_friendship
  after_destroy :remove_reverse_friendship


  def self.request_friendship user_id, friend_id
    request = Friendship.new :user_id => user_id, :friend_id => friend_id
    request.save
  end

  def self.check_status user_id, friend_id
    return :identical if user_id == friend_id
    friendship = Friendship.first :conditions => { :user_id => friend_id, :friend_id => user_id }
    case friendship.andand.is_approved
      when true:  :friends
      when false: :requested
      else        :none
    end
  end

  # approves friendship and adds a reverse friendship relation
  # so when A approves friendship with B,
  # B is friend with A and A is friend with B
  def self.approve_friendship approved_id, approver_id 
    request = Friendship.first :conditions => { :user_id => approved_id, :friend_id => approver_id }
    return false if !request or request.is_approved

    request.is_approved = true
    reverse = Friendship.new :user_id => approver_id, :friend_id => approved_id
    reverse.is_approved = true

    transaction do
      request.save
      reverse.save
      return true
    end
    return false
  end

  # end e current friendship
  def self.end_friendship user_id, friend_id
    friendship = Friendship.first :conditions => { :user_id => user_id, :friend_id => friend_id }
    if friendship
      friendship.destroy
    end
  end

  #rejects a friendship
  def self.reject_friendship requester_id, rejector_id
    self.end_friendship requester_id, rejector_id
  end

protected  

  # removes the reversed friendship
  # if one of the users ends his friendship wish someone
  def remove_reverse_friendship
    Friendship.delete_all :user_id => self.friend_id, :friend_id => self.user_id
  end
end
