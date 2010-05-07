class ApiController < ApplicationController
	
	def login
		self.current_user = User.authenticate(params[:login], params[:password])
		if logged_in?
			respond_to do |format|
				format.xml 
			end 
		else
			@error_msg = "Login failed"
			respond_to do |format|
				format.xml{ render :status => 401, :layout => true, :template => 'api/error'}
			end 
		end
	end
	
	def logout
		params[:session_id]
		#how the hell do I reset the session by session_id?!
		if false #there is such a session_id?
			respond_to do |format|
				format.text {render :status => 200, :text => 'OK'}
			end
		else
			respond_to do |format|
				format.text {render :status => 401, :text => 'NO'}
			end
		end
	end
	
	def start_track
		
	end
	
	def push_coordinates
	end
end
