class UsersController < ApplicationController
  def index
    # public users' listing
    @users = User.public.paginate :page => params[:page], :per_page => 10, :order => (params[:sort] == 'name' ? 'name ASC' : 'created_at DESC')
  end

  def show
    @user = User.find params[:id]
    unless @user.public? || @user == current_user
      raise ActiveRecord::RecordNotFound 
    end
  end

  def edit
    @user = current_user
  end

  def update
    @user = current_user
    if @user.update_attributes(params[:user])
      flash[:notice] = 'Your profile has been successfully updated.'
      redirect_to user_path(@user)
    else
      render :action => 'edit'
    end
  end

  def new
    # the signup page
  end

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
