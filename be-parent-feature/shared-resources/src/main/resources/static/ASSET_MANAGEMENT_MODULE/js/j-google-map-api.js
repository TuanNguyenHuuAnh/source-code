function initMap() {	
	var Lat = "10.762622";
	var Long = "106.660172";
	var isDraggable = false;
	
	if ($('#latitude').val() != "" && $('#latitude').val() != "undefined" ){	
		Lat = $('#latitude').val();
		isDraggable = true;
	}
	if ($('#longtitude').val() != "" && $('#longtitude').val() != "undefined" ){			
		Long = $('#longtitude').val();
		isDraggable = true;
	}
	
	var latlng = {lat: parseFloat(Lat), lng: parseFloat(Long)};
	
	var map = new google.maps.Map(document.getElementById("map"), {
		zoom: 17,
		center: latlng,	
		mapTypeId : google.maps.MapTypeId.ROADMAP
	 });
	
	var marker = new google.maps.Marker({
        position: latlng,
        map: map,
        draggable: isDraggable,
     });
	
	google.maps.event.addListener(marker,'drag',function(event) {
	    document.getElementById('latitude').value = event.latLng.lat();
	    document.getElementById('longtitude').value = event.latLng.lng();
	});
	
	google.maps.event.addListener(marker,'dragend',function(event) 
	        {
	    document.getElementById('latitude').value =event.latLng.lat();
	    document.getElementById('longtitude').value =event.latLng.lng();
	});

	// Create the search box and link it to the UI element.
	var input = document.getElementById("pac-input");
	
	var autocomplete = new google.maps.places.Autocomplete(input, {types: []});
	autocomplete.bindTo('bounds', map);			
	
	var componentForm = {
			street_number: 'short_name',
			route: 'long_name',			
			locality: 'long_name',
			country: 'long_name',
			postal_code: 'short_name',
			administrative_area_level_1 : 'long_name',
			administrative_area_level_2 : 'long_name',
		};
	
	// [START region_getplaces]
	// Listen for the event fired when the user selects a prediction and retrieve
	// more details for that place.
	
	autocomplete.addListener('place_changed', function() {
		
		var place = autocomplete.getPlace();
		
		if (place.length == 0) {
			return;
		}	
		
		for (var i = 0; i < place.address_components.length; i++) {
			var addressType = place.address_components[i].types[0];
			if (componentForm[addressType]) {
				var val = place.address_components[i][componentForm[addressType]];
				document.getElementById(addressType).value = val;
			}
		}
		
		$('#latitude').val(place.geometry.location.lat());
		$('#longtitude').val(place.geometry.location.lng());
		
		// For each place, get the icon, name and location.
		var bounds = new google.maps.LatLngBounds();
		var icon = {
			url : place.icon,			
			size : new google.maps.Size(80, 80),
			origin : new google.maps.Point(0, 0),
			anchor : new google.maps.Point(17, 34),
			scaledSize : new google.maps.Size(25, 25)
		};
		
		marker.setMap(null);
		
		// Create a marker for each place.
		marker = new google.maps.Marker({
			map : map,
			title : place.name,
			position : place.geometry.location,
			draggable:true,
		});
		
		google.maps.event.addListener(marker,'drag',function(event) {
		    document.getElementById('latitude').value = event.latLng.lat();
		    document.getElementById('longtitude').value = event.latLng.lng();
		});
		
		google.maps.event.addListener(marker,'dragend',function(event) 
		        {
		    document.getElementById('latitude').value =event.latLng.lat();
		    document.getElementById('longtitude').value =event.latLng.lng();
		});

		if (place.geometry.viewport) {
			// Only geocodes have viewport.
			bounds.union(place.geometry.viewport);
		} else {
			bounds.extend(place.geometry.location);
		}

		map.fitBounds(bounds);
	});
	
}		