class MainController < ApplicationController
  def index
    gen = CoordinatesGen.new
    @coordinates = gen.get(20)
  end
end
