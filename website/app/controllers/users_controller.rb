class UsersController < ApplicationController
  # public users' listing
  def index
    @users = User.public.paginate :page => params[:page], :per_page => 10, :order => (params[:sort] == 'name' ? 'name ASC' : 'created_at DESC')
  end

  # show a user's profile to anyone, if the profile is publicly accessible, or to the user himself, if not
  def show
    @user = User.find params[:id]
    unless @user.public? || @user == current_user
      raise ActiveRecord::RecordNotFound 
    end
  end

  # editing current user's profile
  def edit
    @user = current_user
  end

  # update the current user's profile
  def update
    @user = current_user
    if @user.update_attributes(params[:user])
      flash[:notice] = 'Your profile has been successfully updated.'
      redirect_to user_path(@user)
    else
      render :action => 'edit'
    end
  end

  # the signup page
  def new
  end

  # the actual signup logic resides here
  def create
    cookies.delete :auth_token
    # protects against session fixation attacks, wreaks havoc with 
    # request forgery protection.
    # uncomment at your own risk
    # reset_session
    @user = User.new(params[:user])
    @user.save
    if @user.errors.empty?
      self.current_user = @user
      redirect_back_or_default('/')
      flash[:notice] = "Thanks for signing up!"
    else
      render :action => 'new'
    end
  end
  
end
