// initializes a google map component in the container 
// with ID of "map_<track_id>", where <track_id> is the ID of the tracks

var main_map = null;

var last_track = null;

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

	main_map = main_map || new google.maps.Map(document.getElementById("map_" + track_id), myOptions);

	var trackCoordinates = new Array();
	for (i = 0; i < points.length; i++) {
		trackCoordinates[i] = new google.maps.LatLng(points[i][0], points[i][1]);
		(mapBounds && mapBounds.extend && mapBounds.extend(trackCoordinates[i]));
	}

	if (mapBounds) {
		main_map.fitBounds(mapBounds);
	}
	
        if (last_track)
        {
          //make sure to clean the last track
          last_track.setMap(null);
        } 

	var track = new google.maps.Polyline({
		path: trackCoordinates,
		strokeColor: "#FF0000",
		strokeOpacity: 0.5,
		strokeWeight: 2
	});

        //draw the track
	track.setMap(main_map);

        last_track = track;
}

var lastPoints = null;

function load_track(track_id)
{
    refresh_track(track_id);

    //register the timer
    $(".map_div").everyTime(10000,function(i) {
                    refresh_track(track_id); 
              });

}

//send an ajax request evety 10 seconds
//and refresh if something has changed
function refresh_track(track_id) {
	$.getJSON("/tracks/" + track_id + ".json", function(points) {
            
            if (!lastPoints || (lastPoints.toString() != points.toString()))
            {
              //refresh the google map
              load_google_map(points, track_id);

              lastPoints = points;
            }
	});
}

