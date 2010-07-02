// initializes a google map component in the container
// with ID of "map_<track_id>", where <track_id> is the ID of the tracks

var Maps = {
	refreshInterval: 2000,
	mainMap:         null,
	lastTrack:       null,
	lastPoints:      null,
	currentPosition: null
};

function load_google_map(points, trackId) {
	if (!points || !points.length || !trackId) {
		return;
	}

	//var myLatLng = new google.maps.LatLng(42, 25);
	var myLatLng = new google.maps.LatLng(points[0][0], points[0][1]),
		mapBounds = new google.maps.LatLngBounds(myLatLng, myLatLng);
	var myOptions = {
		zoom: 1,
		center: myLatLng,
		scrollwheel: false,
		mapTypeId: google.maps.MapTypeId.TERRAIN
	};

	Maps.mainMap = Maps.mainMap || new google.maps.Map(document.getElementById("map_" + trackId), myOptions);

	var trackCoordinates = new Array();
	for (i = 0; i < points.length; i++) {
		trackCoordinates[i] = new google.maps.LatLng(points[i][0], points[i][1]);
		(mapBounds && mapBounds.extend && mapBounds.extend(trackCoordinates[i]));
	}

	if (mapBounds) {
		Maps.mainMap.fitBounds(mapBounds);
	}

	if (Maps.lastTrack) {
		//make sure to clean the last track
		Maps.lastTrack.setMap(null);
	}

	var track = new google.maps.Polyline({
		path: trackCoordinates,
		strokeColor: "#FF0000",
		strokeOpacity: 0.5,
		strokeWeight: 3
	});

	// draw the track
	track.setMap(Maps.mainMap);
	Maps.lastTrack = track;

	// show a blinking dot where the last point is
	if (points.length > 0) {
		var lastPoint = points[points.length - 1];
		if (!Maps.currentPosition) {
			Maps.currentPosition = new google.maps.Marker();
			Maps.currentPosition.setIcon(new google.maps.MarkerImage(
				'/images/current-location.gif',  // image
				new google.maps.Size(24, 24),    // size
				new google.maps.Point(0, 0),     // origin (offset from a sprite's top-left corner)
				new google.maps.Point(12, 12)    // anchor point
			))
		}
		Maps.currentPosition.setPosition(new google.maps.LatLng(lastPoint[0], lastPoint[1]));
		Maps.currentPosition.setMap(Maps.mainMap);
	}
}


function load_track(trackId) {
	refresh_track(trackId);
	//register the timer
	$(".map_div").everyTime(Maps.refreshInterval, function(i) {
		refresh_track(trackId);
	});
}


// send an ajax request evety 10 seconds
// and refresh if something has changed
function refresh_track(trackId) {
	$.getJSON("/tracks/" + trackId + ".json", function(points) {
		if (!Maps.lastPoints || (Maps.lastPoints.toString() != points.toString())) {
			// refresh the google map
			load_google_map(points, trackId);
			Maps.lastPoints = points;
		}
	});
}

