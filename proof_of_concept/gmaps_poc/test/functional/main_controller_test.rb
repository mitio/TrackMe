require 'test_helper'

class MainControllerTest < ActionController::TestCase
  # Replace this with your real tests.
  test "there are coordinates on the main page" do
    get :index  
    assert_response :success, "There should be a valid response"
    assert_not_nil assigns(:coordinates), "Variable coordinates should be valid"
  end
end
