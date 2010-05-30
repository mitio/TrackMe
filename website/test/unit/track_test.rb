require 'test_helper'

class TrackTest < ActiveSupport::TestCase
  # Replace this with your real tests.
  test "randomize" do

    t = Track.last  
    assert_equal 0, t.coordinates.length, "a new track should have no coordinates yet"

    t.randomize(10)
    assert_equal 10, t.coordinates.length, "this randomized track should have 10 coordinates"
  end
end
