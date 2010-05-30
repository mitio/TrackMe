 function load_google_map(points, track_id) {
  
    if (!points.length)
    {
      return;
    }  

    //var myLatLng = new google.maps.LatLng(42, 25);
    var myLatLng = new google.maps.LatLng(points[0][0], points[0][1]);
    var myOptions = {
      zoom: 7,
      center: myLatLng,
      mapTypeId: google.maps.MapTypeId.TERRAIN
    };

    var map = new google.maps.Map(document.getElementById("map_" + track_id), myOptions);

    var trackCoordinates = new Array();
    for (i=0;i<points.length;i++)
    {
        trackCoordinates[i] = new google.maps.LatLng(points[i][0], points[i][1]);
    }
     
    var track = new google.maps.Polyline({
      path: trackCoordinates,
      strokeColor: "#FF0000",
      strokeOpacity: 0.5,
      strokeWeight: 2
    });

   a = track.setMap(map);

   return a;
  }

  function load_track(track_id)
  {
    $.getJSON("/tracks/" + track_id + ".json", 
        function(points)
        {
          load_google_map(points, track_id);
        });
  }

//define a point
function p(x, y)
{
    this.x=x;
    this.y=y;
}
