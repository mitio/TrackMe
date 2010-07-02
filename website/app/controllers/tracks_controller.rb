class TracksController < ApplicationController
  before_filter :login_required, :except => [:user_tracks, :user_track, :show, :public_tracks]

  # GET /tracks
  # GET /tracks.xml
  def index
    @tracks = current_user.tracks
    @user = current_user

    respond_to do |format|
      format.html # index.html.erb
      format.xml  { render :xml => @tracks }
    end
  end

  def public_tracks
    @tracks             = Track.public
    @user               = current_user
    @all_public_tracks  = true
    render :action => 'index'
  end

  # show all public tracks for the given user
  # GET /users/1/tracks
  def user_tracks
    @user               = User.find params[:user_id]
    @friends_with_user  = logged_in? && Friendship.check_status(current_user.id, @user.id) == :friends
    @only_public        = !@friends_with_user
    @tracks             = @friends_with_user ? @user.tracks : @user.tracks.public
    @some_public_tracks = true
    render :action => 'index'
  end

  # show a public track for the given user
  # GET /users/1/tracks/12
  def user_track
    @user               = User.find params[:user_id]
    @friends_with_user  = logged_in? && Friendship.check_status(current_user.id, @user.id) == :friends
    @track              = (@friends_with_user ? Track : Track.public).find params[:track_id], :conditions => { :user_id => @user.id }
    respond_to do |format|
      format.html { render :action => 'show' }
      format.dashboard { render :partial => 'track_dashboard.html', :layout => false }
    end
  end

  # GET /tracks/1
  # GET /tracks/1.xml
  def show
    @track = Track.find params[:id]
    raise ActiveRecord::RecordNotFound unless current_user == @track.user || @track.is_public

    respond_to do |format|
      format.html # show.html.erb
      format.xml  { render :xml => @track }
      format.json { render :json => @track.points }
      format.dashboard { render :partial => 'track_dashboard.html', :layout => false }
    end
  end

  # GET /tracks/new
  # GET /tracks/new.xml
  def new
    @track = Track.new
    
    respond_to do |format|
      format.html # new.html.erb
      format.xml  { render :xml => @track }
    end
  end

  # GET /tracks/1/edit
  def edit
    @track = Track.find(params[:id], :conditions => { :user_id => current_user.id })
  end

  # POST /tracks
  # POST /tracks.xml
  def create
    @track = Track.new(params[:track])
    @track.user_id = current_user.id
    
    respond_to do |format|
      if @track.save
        flash[:notice] = 'Track was successfully created.'
        format.html { redirect_to tracks_url }
        format.xml  { render :xml => @track, :status => :created, :location => @track }
      else
        format.html { render :action => "new" }
        format.xml  { render :xml => @track.errors, :status => :unprocessable_entity }
      end
    end
  end

  # PUT /tracks/1
  # PUT /tracks/1.xml
  def update
    @track = Track.find(params[:id], :conditions => { :user_id => current_user.id })

    respond_to do |format|
      if @track.update_attributes(params[:track])
        flash[:notice] = 'Track was successfully updated.'
        format.html { redirect_to tracks_url }
        format.xml  { head :ok }
      else
        format.html { render :action => "edit" }
        format.xml  { render :xml => @track.errors, :status => :unprocessable_entity }
      end
    end
  end

  # DELETE /tracks/1
  # DELETE /tracks/1.xml
  def destroy
    @track = Track.find(params[:id], :conditions => { :user_id => current_user.id })
    @track.destroy

    respond_to do |format|
      format.html { redirect_to(tracks_url) }
      format.xml  { head :ok }
    end
  end
end
