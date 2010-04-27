class AddUserIdToTracks < ActiveRecord::Migration
  def self.up
    add_column :tracks, :user_id, :int
  end

  def self.down
    remove_column :tracks, :user_id
  end
end
