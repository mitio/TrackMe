# Be sure to restart your server when you modify this file.

# Your secret key for verifying cookie session data integrity.
# If you change this key, all old sessions will become invalid!
# Make sure the secret is at least 30 characters and all random, 
# no regular words or you'll be exposed to dictionary attacks.
ActionController::Base.session = {
  :key         => '_gmaps_poc_session',
  :secret      => '7910faa5ccf2095a303554ef70f205817b2b793c907008aa8fc68cd984f380fcd8cf98f4c507be08ee334572e04260d9dede05ecf42c26858ce533d5382263c4'
}

# Use the database for sessions instead of the cookie-based default,
# which shouldn't be used to store highly confidential information
# (create the session table with "rake db:sessions:create")
# ActionController::Base.session_store = :active_record_store
