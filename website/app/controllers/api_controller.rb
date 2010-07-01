class ApiException < StandardError ;    HTTP_STATUS = 500 ; end
class NotLoggedIn < ApiException ;      HTTP_STATUS = 401 ; end

# implement the public HTTP API's actions and business logic
class ApiController < ApplicationController
  before_filter :set_up_request
  before_filter :login_required, :except => [:login]
  rescue_from ApiException, ActiveRecord::RecordNotFound, ActiveRecord::RecordInvalid, :with => :handle_api_exception
  layout 'application.builder'

  protect_from_forgery :only => [] # TODO

  # authenticates a user with the given login & password
  # requires: login, password
  # returns a session_id if successful
  def login
    self.current_user = User.authenticate(params[:login], params[:password])
    login_required
  end

  # terminates the current session
  # requires: session_id
  def logout
    session[:user_id] = nil
  end

  # requires: session_id
  # returns information about the current status of the session
  def status
    @session_status = 'active'
    @user = current_user
  end

  # starts a new track by creating a record in the tracks table
  # requires: session_id
  # optional input: name
  # returns a track_id if successful
  def start_track
    @track = Track.new
    @track.name = params[:name] || "Track from #{Time.now}"
    @track.user = current_user
    @track.is_public = false
    @track.save!
  end

  # adds the given coordinate(s) to the track, identified by track_id
  # requires: session_id, track_id, coord
  # optional input: more coord instances
  # returns the total number of coordinates of the track after the insertion
  def push_coordinates
    @track = Track.find params[:track_id], :conditions => { :user_id => current_user.id }
    unless params[:coord].blank?
      [params[:coord]].flatten.each do |coord_params|
        lat, lng, tracked_at = coord_params.split '|'

        #make sure we do not receive (0,0)s
        #the device sometimes sends buggy coordinates
        next if (lat.to_i == 0) and (lng.to_i == 0)

        # TODO: convert tracked_at from string to datetime
        coordinate = Coordinate.new :lat => lat, :lng => lng, :tracked_at => tracked_at
        coordinate.tracked_at ||= Time.now
        @track.coordinates << coordinate
      end
      @track.save!
    end
  end

private

  # set up some parameters, common for all API actions
  def set_up_request
    # all requests succeed by default, unless they fail later on
    @status = 'success'
    @message = 'Request successful.'
  end

  # the API needs different login_required logic: throws an exception if the user is not logged in
  def login_required
    raise NotLoggedIn unless logged_in?
    @session_id = request.session_options[:id]
  end

  # all exceptions, thrown during processing of any of the actions are handled here and rendered properly to the client
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
