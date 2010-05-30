require 'test_helper'

class TracksControllerTest < ActionController::TestCase
  test "should redirect to login" do
    get :index
    assert_redirected_to new_session_path
    get :show, :id => tracks(:one).to_param
    assert_redirected_to new_session_path
    get :new
    assert_redirected_to new_session_path
    get :edit, :id => tracks(:one).to_param
    assert_redirected_to new_session_path
  end

  test "should get index" do
    login_as :quentin
    get :index
    assert_response :success
    assert_not_nil assigns(:tracks)
  end

  test "should get new" do
    login_as :quentin
    get :new
    assert_response :success
  end

  test "should not create track for guests" do
    assert_no_difference('Track.count') do
      post :create, :track => { :user_id => 1, :name => 'Test Track', :is_public => false }
    end
  end

  test "should create track" do
    login_as :quentin
    assert_difference('Track.count') do
      post :create, :track => { :name => 'Test Track', :is_public => false }
    end
    assert_redirected_to tracks_path
  end

  test "should show track" do
    login_as :quentin
    get :show, :id => tracks(:one).to_param
    assert_response :success
  end

  test "should get edit" do
    login_as :quentin
    get :edit, :id => tracks(:one).to_param
    assert_response :success
  end

  test "should update track" do
    login_as :quentin
    put :update, :id => tracks(:one).to_param, :track => { }
    assert_redirected_to tracks_path
  end

  test "should not destroy track for guest" do
    assert_no_difference('Track.count') do
      delete :destroy, :id => tracks(:one).to_param
    end
  end

  test "should not destroy track for other users" do
    login_as :aaron
    assert_raises ActiveRecord::RecordNotFound do
      delete :destroy, :id => tracks(:one).to_param
    end
  end

  test "should destroy track" do
    login_as :quentin
    assert_difference('Track.count', -1) do
      delete :destroy, :id => tracks(:one).to_param
    end
    assert_redirected_to tracks_path
  end

  test "should get the coordiantes of the track in json format" do 
    login_as :quentin

    get :show, :format => "json", :id => tracks(:one).to_param

    assert_response :success

    assert_equal "[[9.99,9.99],[9.99,9.99]]", @response.body
  end
  
  test "should not be able get the coordiantes if not logged in" do
    get :show, :format => "json", :id => tracks(:one).to_param

    assert_response 401
  end

  test "should show a map in the track" do
    login_as :quentin
     
    get :show, :id => tracks(:one).to_param
    
    assert_response :success
  
    assert_not_equal nil, @response.body.index("load_track(1)")
    assert_not_equal nil, @response.body.index('div id="map_1"')
  end
end
