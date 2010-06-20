class CreateTracks < ActiveRecord::Migration
  def self.up
    create_table :tracks do |t|
      t.string :name
      t.text :description
      t.boolean :is_public, :null => false
      t.decimal :rating, :precision => 2, :scale => 2
      t.timestamps
    end
  end

  def self.down
    drop_table :tracks
  end
end
