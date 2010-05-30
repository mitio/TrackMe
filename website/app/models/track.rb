class Track < ActiveRecord::Base
  belongs_to :user
  has_many :coordinates
  
  validates_presence_of :user_id, :name
  validates_inclusion_of :is_public, :in => [true, false]
  
  attr_accessible :name, :description, :is_public

  named_scope :public, :conditions => { :is_public => true }

  def points
    self.coordinates.map do |c|
      [c.lat, c.lng]
    end
  end

  def randomize(count = 20)
    gen = CoordinatesGen.new
    i = 0
    gen.get(count).each do |point|
      self.coordinates.create(:lat => point[:x],
                              :lng => point[:y],
                              :tracked_at => Time.now + i.minute
                             )
      i += 1
    end
  end

end
