#############################################
#
#   Generate random coordinates   
#
#############################################


class CoordinatesGen 
  def get(count)
    results = []
    x = MIN_X
    y = MIN_Y
    count.times do |i|
      dx, dy = get_vector()
      x = [[x+dx, MIN_X].max, MAX_X].min      
      y = [[y+dy, MIN_Y].max, MAX_Y].min

      results << {:x => x, :y => y}
    end

    return results
  end

  #consts
  MIN_X = 42
  MAX_X = 43.5
  MIN_Y = 23
  MAX_Y = 25

  private
    C_STEP = 0.003
    C_MAX_STEPS = 100

    def random_sign()
      rand(2) == 1 ? 1 : -1 
    end

    def get_vector()      
      [random_sign * rand(C_MAX_STEPS) * C_STEP, 
       random_sign * rand(C_MAX_STEPS) * C_STEP] 
    end
end
