// initializes a google map component in the container 
// with ID of "map_<track_id>", where <track_id> is the ID of the tracks

function load_google_map(points, track_id) {
	if (!points || !points.length || !track_id) {
		return;
	}	

	//var myLatLng = new google.maps.LatLng(42, 25);
	var myLatLng = new google.maps.LatLng(points[0][0], points[0][1]),
		mapBounds = new google.maps.LatLngBounds(myLatLng, myLatLng);
	var myOptions = {
		zoom: 1,
		center: myLatLng,
		mapTypeId: google.maps.MapTypeId.TERRAIN
	};

	var map = new google.maps.Map(document.getElementById("map_" + track_id), myOptions);

	var trackCoordinates = new Array();
	for (i = 0; i < points.length; i++) {
		trackCoordinates[i] = new google.maps.LatLng(points[i][0], points[i][1]);
		(mapBounds && mapBounds.extend && mapBounds.extend(trackCoordinates[i]));
	}

	if (mapBounds) {
		map.fitBounds(mapBounds);
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

var lastPoints;

// loads the coordinates for the given track and calls load_google_map() to display the map for them
function load_track(track_id, refresh) {
	$.getJSON("/tracks/" + track_id + ".json", function(points) {

            refresh = refresh || false;
            
            if (!refresh || (lastPoints.toString() != points.toString()))
            {
              load_google_map(points, track_id);

              lastPoints = points;
            }


	});

        if (!refresh)
        {
          $(".map_div").everyTime(10000,function(i) {
                    load_track(track_id, true); 
              });
        }
}
