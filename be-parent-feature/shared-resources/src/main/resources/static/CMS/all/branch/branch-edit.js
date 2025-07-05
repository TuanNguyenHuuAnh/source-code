$(document).ready(function() {
	init();
	$('.select2').select2({ allowClear: true });
	
	$('#type').select2({ allowClear: false });
	
	// set readonly code
	if ($("#id").val() != "") {
		$("#code").attr('readonly', 'readonly');
	}
	
	//on click cancel
	$('#cancel, #linkList').on('click', function (event) {
		event.preventDefault();
		
		popupConfirmWithButtons(MSG_BACK_CONFIRM, LIST_BUTTON_CONFIRM_DELETE, function(result) {
			if (result) {
				var url = BASE_URL + "branch/list";
				// Redirect to page list
				ajaxRedirect(url);
			}
		});
	});
	
	// on click add
	$("#addNew").on("click", function(event) {

		popupConfirmWithButtons(MSG_ADD_NEW_CONFIRM, LIST_BUTTON_CONFIRM_DELETE, function(result) {
			if (result) {
				var url = BASE_URL + "branch/edit";
				// Redirect to page add
				ajaxRedirect(url);
			}
		})
	});	
	// Post edit save job
	$('#btn-save').on('click', function(event) {
		
		
		if($("#activeFlagF").is(":checked")) {
			$('#activeFlag').val(1);
		} else {
			$('#activeFlag').val(0);
		}
		
		if ($(".j-form-validate").valid()) {						
			var url = "branch/edit";
			var condition = $("#form-branch-edit").serialize();

			ajaxSubmit(url, condition, event);		
		}else{
			rollToError('j-form-validate');
		}			
	});
	// IMAGE signature
	signatureImage();
	// init image uploader
	initImageUploader();
	
	$("#city").change(function() {
		newRegionChange(this);
		$('#region-error').empty();
	});
	
	$("#city").change(function() {
		$('#city-error').empty();
	});
	
	$("#type").change(function() {
		$('#type-error').empty();
	});
	
	$("#pac-input").change(function() {
		var geocoder = new google.maps.Geocoder();
		geocoder.geocode({
			"address" : $("#pac-input").val()
		}, function(results, status) {
			if(status == google.maps.GeocoderStatus.OK) {
				$("#latitude").val(results[0].geometry.location.lat().toFixed(6));
				$("#longtitude").val(results[0].geometry.location.lng().toFixed(6));
			} else {
				/*alert("Please enter correct place name");*/
			}
		});
		return false;
	});
	
});	

function init(){
	var isDisabled = $('#hasEdit').val() == 'false';
	disabledAllField('form-branch-edit', isDisabled);
}

/**
 * IMAGE signature
 */
function signatureImage() {

	var image = $("#physicalImg").val();
	if (image != "") {
		$("#img_icon").attr("src", BASE_URL + "ajax/download?fileName=" + image);
		$("#img_icon").removeClass('hide');
	}
}

/**
 * init image uploader
 */
function initImageUploader() {
	var uploadUrl = BASE_URL + "ajax/uploadTemp";
	var imagePickfiles = 'imgPickfiles' ;
	var imageContainer = 'imageContainer' ;
	var imageMaxFileSize = '7mb';
	var imageMimeTypes = [ {
		title : "Image files",
		extensions : "jpg,bmp,png,jpeg"
	} ];
	var resize = {
		width : 1366,
		height : 320
	};
	var imageFileList = 'imageFilelist' ;
	var imageConsole = 'imageConsole' ;
	var imageFileUploaded = function(up, file, info) {		
		$("#physicalImg").val(cutString(info.response));
	};

	var imageUploadComplete = function(up, files) {
		var lstImg = $("#physicalImg").val();
		$("#img_icon").attr("src", BASE_URL + "ajax/download?fileName=" + lstImg);
		$("#img_icon").removeClass('hide');
		$("#" + imageConsole).hide();
		$("#" + imageFileList).hide();
	};
	InitPlupload(imagePickfiles, imageContainer, uploadUrl, false, imageMaxFileSize, imageMimeTypes, imageFileList,
			imageConsole, imageFileUploaded, imageUploadComplete, BASE_URL);
			
}


/**
 * region select box on change
 */
function newRegionChange(element) {
	var city = $(element).val();
	$.ajax({
		type : "GET",
		url : BASE_URL + "branch/region/change",
		data : {
			"city" : city,
		},
		success : function(data) {
	
			$('#district').find('option:not(:first)').remove();
			
			$.each(jQuery.parseJSON(data), function(key, val) {
				$('#district').append('<option value="' + val.id + '">' + val.name + '</option>');
			});
		}
	});
}
	
var map;
var market;
function initMap() {
  map = new google.maps.Map(document.getElementById('map'), {
    zoom: 4,
    center: new google.maps.LatLng(10.773599,106.694420),
    mapTypeId: google.maps.MapTypeId.ROADMAP,
    zoomControl:false,
    mapTypeControl:false,
    panControl:false,
    scaleControl:false,
    disableDefaultUI: true,   
    overviewMapControl:false
  });

  // Create a <script> tag and set the USGS URL as the source.
  var script = document.createElement('script');
  // This example uses a local copy of the GeoJSON stored at
  // http://earthquake.usgs.gov/earthquakes/feed/v1.0/summary/2.5_week.geojsonp
  script.src = 'https://developers.google.com/maps/documentation/javascript/examples/json/earthquake_GeoJSONP.js';
  document.getElementsByTagName('head')[0].appendChild(script);
}

// Loop through the results array and place a marker for each
// set of coordinates.

window.eqfeed_callback = function(results) {
	//$.each(JSON.parse(RESULT_LIST), function(key, obj) {
		var latLng = new google.maps.LatLng(10.773599,106.694420);	
		var images = BASE_URL + "static/images/icon-default.png";
		//if (null != obj.icon){
			//images = BASE_URL + "ajax/download?fileName=" + obj.icon;
		//}
		
		var myIcon = new google.maps.MarkerImage(
				images, new google.maps.Size(80, 80), 
				new google.maps.Point(0, 0), 
				new google.maps.Point(96, 96), 
				new google.maps.Size(20,30)
			);
		var contentString = '<div id="content">'+
        '<div id="siteNotice">'+
        '</div>'+
        '<h1 id="firstHeading" class="firstHeading">VietCapital Bank</h1>'+ 
        '<div id="bodyContent">'+
        '</div>'+
        '</div>';
		var infowindow = new google.maps.InfoWindow({
	          content: contentString
	        });
		marker = new google.maps.Marker({
		    position: latLng,			    	   
		    map: map,
		    flat: true,
		    title: "text",		    
		    icon: myIcon
		});
		marker.addListener('mouseover', function() {
	          infowindow.open(map, marker);
	        });
		marker.addListener('mouseout', function() {
			infowindow.close();
	  	});
	//});		

}

