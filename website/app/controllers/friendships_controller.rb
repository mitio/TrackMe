class FriendshipsController < ApplicationController
  before_filter :login_required

  # shows a listing of friends and pending friend requests
  def index
    @friend_requests = current_user.friend_requests
    @friends = current_user.friends
    respond_to do |format|
      format.html # index.html.erb
      format.xml  { render :xml => @tracks }
    end
  end

  # creates a new friend request
  def friendship_request
    if Friendship.request_friendship params[:user_id], current_user.id
      flash[:notice] = 'Friendship request successful.'
    else
      flash[:error] = 'Friendsip request error.'
    end
    redirect_to :action => 'index'
  end

  # approve the given friend request
  def approve
    if Friendship.approve_friendship current_user.id, params[:id]
      flash[:notice] = 'Friendship approved.'
    else
      flash[:error] = 'There was an error while approving friendship.'
    end
    redirect_to :action => 'index'
  end

  # ends a friendship or ignores a friend request (by deleting it)
  def reject
    if Friendship.end_friendship current_user.id, params[:id]
      flash[:notice] = 'Friendship rejected/ended.'
    else
      flash[:error] = 'There was an error while rejecting/ending friendship.'
    end
    redirect_to :action => 'index'
  end
end
