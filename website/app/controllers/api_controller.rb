class ApiException < StandardError ;    HTTP_STATUS = 500 ; end
class NotLoggedIn < ApiException ;      HTTP_STATUS = 401 ; end


class ApiController < ApplicationController
  before_filter :set_up_request
  before_filter :login_required, :except => [:login]
  rescue_from ApiException, ActiveRecord::RecordNotFound, ActiveRecord::RecordInvalid, :with => :handle_api_exception
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
    @track = Track.new
    @track.name = "Track from #{Time.now}"
    @track.user = current_user
    @track.is_public = false
    @track.save!
  end

  def push_coordinates
    @track = Track.find params[:track_id], :conditions => { :user_id => current_user.id }
    unless params[:coord].blank?
      [params[:coord]].flatten.each do |coord_params|
        lat, lng, tracked_at = coord_params.split '|'
        # TODO: convert tracked_at from string to datetime
        coordinate = Coordinate.new :lat => lat, :lng => lng, :tracked_at => tracked_at
        coordinate.tracked_at ||= Time.now
        @track.coordinates << coordinate
      end
      @track.save!
    end
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
        @http_status = defined?(e.class::HTTP_STATUS) ? e.class::HTTP_STATUS : nil
        @http_status ||= 400 if [ActiveRecord::RecordNotFound, ActiveRecord::RecordInvalid].include?(e.class)
        render :template => 'api/error', :status => @http_status
      end
    end
  end
end
