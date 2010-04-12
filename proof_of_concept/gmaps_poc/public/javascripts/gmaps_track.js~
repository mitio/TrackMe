 function load_google_maps(points) {
    var myLatLng = new google.maps.LatLng(42, 25);
    var myOptions = {
      zoom: 7,
      center: myLatLng,
      mapTypeId: google.maps.MapTypeId.TERRAIN
    };

    var map = new google.maps.Map(document.getElementById("map_canvas"), myOptions);

    var trackCoordinates = new Array();
    for (i=0;i<points.length;i++)
    {
        trackCoordinates[i] = new google.maps.LatLng(points[i].x, points[i].y);
    }
     
    var track = new google.maps.Polyline({
      path: trackCoordinates,
      strokeColor: "#FF0000",
      strokeOpacity: 0.5,
      strokeWeight: 2
    });

   track.setMap(map);
  }

//define a point
function p(x, y)
{
    this.x=x;
    this.y=y;
}
