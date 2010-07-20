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

  def length
    return 0 unless coordinates.size > 1
    length, last_coordinate = 0, coordinates.first
    coordinates[1..-1].each do |coordinate|
      length += coordinate.distance(last_coordinate)
      last_coordinate = coordinate
    end
    length
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
