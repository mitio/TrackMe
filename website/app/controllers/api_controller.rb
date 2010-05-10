class ApiException < StandardError ; HTTP_STATUS = 500 ; end
class NotLoggedIn < ApiException ;   HTTP_STATUS = 401 ; end


class ApiController < ApplicationController
  before_filter :set_up_request
  before_filter :login_required, :except => [:login]
  rescue_from ApiException, :with => :handle_api_exception
  layout 'application.builder'

  protect_from_forgery :only => [] # TODO

  def login
    self.current_user = User.authenticate(params[:login], params[:password])
    login_required
  end
  
  def logout
    session[:user_id] = nil
  end

  def status
    @session_status = 'active'
    @user = current_user
  end
  
  def start_track
  end
  
  def push_coordinates
  end

private

  def set_up_request
    # all requests succeed by default, unless they fail later on
    @status = 'success'
    @message = 'Request successful.'
  end

  def login_required
    raise NotLoggedIn unless logged_in?
    @session_id = request.session_options[:id]
  end

  def handle_api_exception(e)
    respond_to do |format|
      format.xml do
        @status = 'error'
        @message = e.message.underscore.humanize
        render :template => 'api/error', :status => defined?(e.class::HTTP_STATUS) ? e.class::HTTP_STATUS : 500
      end
    end
  end
end
