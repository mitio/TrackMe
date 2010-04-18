class FixTracksRating < ActiveRecord::Migration
  def self.up
    change_column :tracks, :rating, :decimal, :precision => 4, :scale => 2
  end

  def self.down
    change_column :tracks, :rating, :decimal, :precision => 2, :scale => 2
  end
end
