# This file should contain all the record creation needed to seed the database with its default values.
# The data can then be loaded with the rake db:seed (or created alongside the db with db:setup).
#
# Examples:
#   
#   cities = City.create([{ :name => 'Chicago' }, { :name => 'Copenhagen' }])
#   Major.create(:name => 'Daley', :city => cities.first)

# Seed if needed
def add_coordinates_first_track
  track = Track.first
  time = Time.now
  unless track.nil?
    Coordinate.create :track_id=>track.id, :lat => 42.036, :lng => 23, :tracked_at => time - 6.hours - 45.minutes
    Coordinate.create :track_id=>track.id, :lat => 42.039, :lng => 23.09, :tracked_at => time - 5.hours
    Coordinate.create :track_id=>track.id, :lat => 42, :lng => 23.264, :tracked_at => time - 4.hours
    Coordinate.create :track_id=>track.id, :lat => 42.432, :lng => 23.672, :tracked_at => time - 2.hours - 45.minutes
    Coordinate.create :track_id=>track.id, :lat => 42.33, :lng => 23.57, :tracked_at => time -  2.hours
    Coordinate.create :track_id=>track.id, :lat => 42.477, :lng => 23.459, :tracked_at => time - 10.minutes
    Coordinate.create :track_id=>track.id, :lat => 42.423, :lng => 23.645, :tracked_at => time
  end

end

add_coordinates_first_track
