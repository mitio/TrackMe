class Coordinate < ActiveRecord::Base
  belongs_to :track
  
  EARTH_RADIUS = 6378.137 #km
  
  
#Conversion of the degrees, minutes and seconds in a decimal value:
#Latitude Frankfurt.: 50 + (06 / 60) + (44 / 3600) = 50.11222°
#Longitude Frankfurt: 08 + (40 / 60) + (55 / 3600) = 8.68194°
#Latitude Berlin....: 52 + (31 / 60) + (20 / 3600) = 52.52222°
#Longitude Berlin...: 13 + (17 / 60) + (51 / 3600) = 13.29750°
#
#Version A of the calculation (for programs/software that can only calculate angles with radian functions)
#
#      Conversion of the coordinates in radian values:
#      Latitude Frankfurt.: (LAT1) 50.11222° / 180 * PI = 0.87462
#      Longitude Frankfurt: (LONG1) 8.68194° / 180 * PI = 0.15153
#      Latitude Berlin....: (LAT2) 52.52222° / 180 * PI = 0.91669
#      Longitude Berlin...: (LONG2) 13.29750° / 180 * PI = 0.23209
#
#      The formula uses the distance calculation on a sphere:
#      e = ARCCOS[ SIN(LAT1)*SIN(LAT2) + COS(LAT1)*COS(LAT2)*COS(LONG2-LONG1) ]
#      e = ARCCOS[ SIN(0.87462)*SIN(0.91669) + COS(0.87462)*COS(0.91669)*COS(0.23209-0.15153) ]
#      e = ARCCOS[ 0.60892 + 0.38893 ]
#      e = 0.06559
#Now, the obtained result must be multiplied with the radius of the equator:
#Distance = e*r = 0.06559*6378.137 km = 418.34 km  or
  
  def distance coordinate
    lat1 = self.lat / 180 * Math::PI
    lng1 = self.lng / 180 * Math::PI
    lat2 = coordinate.lat / 180 * Math::PI
    lng2 = coordinate.lng / 180 * Math::PI
     
    e = Math.acos( Math.sin(lat1) * Math.sin(lat2) +
                  Math.cos(lat1) * Math.cos(lat2) * Math.cos(lng2 - lng1))
    return e * EARTH_RADIUS
  end
  
  def speed coordinate, dist = nil
    dist = dist.nil? ? self.distance(coordinate) : dist
    
    if(self.tracked_at - coordinate.tracked_at != 0)
      return dist / ((self.tracked_at - coordinate.tracked_at).abs / 3600 )
    end
    return nil
  end
end


