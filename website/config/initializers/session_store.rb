# Be sure to restart your server when you modify this file.

# Your secret key for verifying cookie session data integrity.
# If you change this key, all old sessions will become invalid!
# Make sure the secret is at least 30 characters and all random, 
# no regular words or you'll be exposed to dictionary attacks.
ActionController::Base.session = {
  :key         => '_website_session',
  :secret      => '81c054d5d60d39dcbbeb20a40ee62eb88c5e8df780a758c7ff8c027b5ee58a25119ffa1c687757ff24ff36d12c172a47248a920e64a1e4aa625a71abb1950852'
}
# Use the database for sessions instead of the cookie-based default,
# which shouldn't be used to store highly confidential information
# (create the session table with "rake db:sessions:create")
ActionController::Base.session_store = :active_record_store
