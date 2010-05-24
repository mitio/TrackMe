class Track < ActiveRecord::Base
  belongs_to :user
  has_many :coordinates
  
  validates_presence_of :user_id, :name
  validates_inclusion_of :is_public, :in => [true, false]
  
  attr_accessible :name, :description, :is_public

  named_scope :public, :conditions => { :is_public => true }
  
end
