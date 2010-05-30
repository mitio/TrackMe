#################################################  
#
#  Test the random generator of coordinates 
#
#################################################

require 'test_helper'

class GeneratorTest < ActiveSupport::TestCase

  def setup
    @generator = CoordinatesGen.new
  end 

  def test_get_random_coordinates
    coordinates = @generator.get(10)
    assert_equal 10, coordinates.length, "get(x) should return array of x elements"

#    p coordinates

    coordinates.each do |pair|
      assert(pair[:x] >= CoordinatesGen::MIN_X && pair[:x] <= CoordinatesGen::MAX_X, "x coordinate should be between MIN_X and MAX_X")
      assert(pair[:y] >= CoordinatesGen::MIN_Y && pair[:y] <= CoordinatesGen::MAX_Y, "y coordinate should be between MIN_Y and MAX_Y")
    end
  end
end
