class ApiController < ApplicationController
	protect_from_forgery :only => []

	def login
		self.current_user = User.authenticate(params[:login], params[:password])
		if logged_in?
			#I'm not sure if it is not better to create the xml here than in the views
			#when it's not somthing common like views/api/error
			xml = Builder::XmlMarkup.new
			xml.sessionId request.session_options[:id]
			respond_to do |format|
				format.xml {render :layout => true, :content => xml}
			end 
		else
			@error_msg = "Login failed"
			respond_to do |format|
				format.xml{ render :status => 401, :layout => true, :template => 'api/error'}
			end 
		end
	end
	
	def logout
		#p request.session_options[:id] = params[:session_id]
		request.session_options[:cookie_only] = false
		#request.session_options[:domain] = "localhost:3000"
		p params[:session_id]
		
		#how the hell do I reset the session by session_id?!
		
		#p session
		#p request.session_options

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
