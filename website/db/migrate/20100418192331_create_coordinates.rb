class CreateCoordinates < ActiveRecord::Migration
  def self.up
    create_table :coordinates do |t|
      t.integer :track_id
      t.decimal :lat, :precision => 9, :scale => 6
      t.decimal :lng, :precision => 9, :scale => 6
      t.datetime :tracked_at

      t.timestamps
    end
  end

  def self.down
    drop_table :coordinates
  end
end
