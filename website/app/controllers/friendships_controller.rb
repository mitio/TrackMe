class FriendshipsController < ApplicationController
  before_filter :login_required
  
  def index
    
    @friend_requests = current_user.friend_requests
    @friends = current_user.friends
    
    respond_to do |format|
      format.html # index.html.erb
      format.xml  { render :xml => @tracks }
    end
  end
  
  def friendship_request
    if Friendship.request_friendship params[:user_id], current_user.id
      flash[:notice] = 'Friendship request successful.'
    else
      flash[:error] = 'Friendsip request error.'
    end
    redirect_to :action => 'index'
  end
  
  def approve
    if Friendship.approve_friendship current_user.id, params[:id]
      flash[:notice] = 'Friendship approved.'
    else
      flash[:error] = 'There was an error while approving friendship.'
    end
    
    redirect_to :action => 'index'
  end
  
  def reject
    if Friendship.end_friendship current_user.id, params[:id]
      flash[:notice] = 'Friendship rejected/ended.'
    else
      flash[:error] = 'There was an error while rejecting/ending friendship.'
    end
    
    redirect_to :action => 'index'
  end
  
end
