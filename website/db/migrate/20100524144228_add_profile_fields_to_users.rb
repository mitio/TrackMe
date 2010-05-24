class AddProfileFieldsToUsers < ActiveRecord::Migration
  def self.up
    add_column :users, :name, :string, :null => false, :default => '', :limit => 100
    add_column :users, :is_public, :boolean, :null => false, :default => true
    add_column :users, :show_email, :boolean, :null => false, :default => false
    add_column :users, :about, :text, :null => false, :default => ''
    add_index :users, [:is_public, :name]
    add_index :users, [:is_public, :created_at]
  end

  def self.down
    remove_index :users, [:is_public, :created_at]
    remove_index :users, [:is_public, :name]
    remove_column :users, :about
    remove_column :users, :show_email
    remove_column :users, :is_public
    remove_column :users, :name
  end
end
