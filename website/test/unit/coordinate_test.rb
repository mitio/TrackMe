require 'test_helper'

class CoordinateTest < ActiveSupport::TestCase
  # Replace this with your real tests.
  test "the truth" do
    assert true
  end
  
  test "coodrinates from example" do
    a = Coordinate.new :lat => 50.11222, :lng => 8.68194
    b = Coordinate.new :lat => 52.52222, :lng => 13.29750
    assert ((a.distance(b) - 418.34 ).abs < 0.05) 
  end
end
