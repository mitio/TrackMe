# This file is auto-generated from the current state of the database. Instead of editing this file, 
# please use the migrations feature of Active Record to incrementally modify your database, and
# then regenerate this schema definition.
#
# Note that this schema.rb definition is the authoritative source for your database schema. If you need
# to create the application database on another system, you should be using db:schema:load, not running
# all the migrations from scratch. The latter is a flawed and unsustainable approach (the more migrations
# you'll amass, the slower it'll run and the greater likelihood for issues).
#
# It's strongly recommended to check this file into your version control system.

ActiveRecord::Schema.define(:version => 20100620164935) do

  create_table "coordinates", :force => true do |t|
    t.integer  "track_id"
    t.decimal  "lat",        :precision => 9, :scale => 6
    t.decimal  "lng",        :precision => 9, :scale => 6
    t.datetime "tracked_at"
    t.datetime "created_at"
    t.datetime "updated_at"
  end

  create_table "friendships", :force => true do |t|
    t.integer  "user_id"
    t.integer  "friend_id"
    t.boolean  "is_approved", :default => false, :null => false
    t.datetime "created_at"
    t.datetime "updated_at"
  end

  create_table "sessions", :force => true do |t|
    t.string   "session_id", :null => false
    t.text     "data"
    t.datetime "created_at"
    t.datetime "updated_at"
  end

  add_index "sessions", ["session_id"], :name => "index_sessions_on_session_id"
  add_index "sessions", ["updated_at"], :name => "index_sessions_on_updated_at"

  create_table "tracks", :force => true do |t|
    t.string   "name"
    t.text     "description"
    t.boolean  "is_public",   :null => false
    t.decimal  "rating"
    t.datetime "created_at"
    t.datetime "updated_at"
    t.integer  "user_id"
  end

  create_table "users", :force => true do |t|
    t.string   "login"
    t.string   "email"
    t.string   "crypted_password",          :limit => 40
    t.string   "salt",                      :limit => 40
    t.datetime "created_at"
    t.datetime "updated_at"
    t.string   "remember_token"
    t.datetime "remember_token_expires_at"
    t.string   "name",                      :limit => 100, :default => "",    :null => false
    t.boolean  "is_public",                                :default => true,  :null => false
    t.boolean  "show_email",                               :default => false, :null => false
    t.text     "about",                                    :default => "",    :null => false
  end

  add_index "users", ["is_public", "created_at"], :name => "index_users_on_is_public_and_created_at"
  add_index "users", ["is_public", "name"], :name => "index_users_on_is_public_and_name"

end
