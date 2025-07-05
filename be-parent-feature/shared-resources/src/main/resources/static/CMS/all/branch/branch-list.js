$(document).ready(function($) {
    
    $('#fieldSearch').keypress(function(event){
    
        var keycode = (event.keyCode ? event.keyCode : event.which);
        if(keycode == '13'){
            onClickSearch(this, event);
        }
    
    });
	
	// on click delete
	$(".j-branch-delete").on("click", function( event ) {
		deleteBranch(this, event);
	});
	$('select[multiple]').multiselect({
	    columns: 1,
	    placeholder: SEARCH_LABEL,
	    search: true
	});
	// on click add
	$("#add").on("click", function(event) {
		var url = BASE_URL + "branch/edit";
		// Redirect to page add
		ajaxRedirect(url);
	});

	// on click edit
	$(".j-btn-edit").on("click", function(event) {
		editBranch(this, event);
	});
	
	// on click detail
	$(".j-btn-detail").on("click", function(event) {
		detailBranch(this, event);
	});
	
	// on click search
	$("#btnSearch").on('click', function(event) {
		onClickSearch(this, event);
	});
	
	// datatable load
	$(".unit-bg-table").datatables({
		url : BASE_URL + 'branch/ajaxList',
		type : 'POST',
		setData : setConditionSearch
	});
	
	//double click
	/*
	$(".trEdit").on("dblclick", function(event) {
		editBranch(this, event, $(this));
	});	
	*/
});
/**
 * deleteBranch
 * @param element
 * @param event
 * @returns
 */
function deleteBranch(element, event){
	event.preventDefault();
	
	// Prepare data
	var row = $(element).parents("tr");
	var branchId = row.data("branch-id");
	
	popupConfirm( MSG_DEL_CONFIRM, function(result) {
		if (result) {
			var url = "branch/delete";
			var condition = {
				"branchId" : branchId
			}
			
			ajaxSubmit(url, condition, event);
			
		}		
	});
}
/**
 * editBranch
 * @param element
 * @param event
 * @returns
 */
function editBranch(element, event, row) {
	event.preventDefault();

	// Prepare data
	if (row == null) {
		row = $(element).parents("tr");
	}
	
	var id = row.data("branch-id");
	var url = BASE_URL + "branch/edit?id=" + id;

	// Redirect to page detail
	ajaxRedirect(url);
}
/**
 * detailBranch
 * @param element
 * @param event
 * @returns
 */
function detailBranch(element, event) {
	event.preventDefault();

	// Prepare data
	var row = $(element).parents("tr");
	var id = row.data("branch-id");
	var url = BASE_URL + "branch/detail?id=" + id;

	// Redirect to page detail
	ajaxRedirect(url);
}

/**
 * onClickSearch
 * @param element
 * @param event
 * @returns
 */
function onClickSearch(element, event) {

	setDataSearchHidden();

	ajaxSearch("branch/ajaxList", setConditionSearch(), "tableList", element, event);
}

function setConditionSearch() {
	var condition = {};
	condition["fieldSearch"] = $("#fieldSearchHidden").val();
	condition["fieldValues"] = $("#fieldValuesHidden").val();
	return condition;
}

function setDataSearchHidden() {
	$("#fieldSearchHidden").val($("#fieldSearch").val());
	$("#fieldValuesHidden").val($("select[name=fieldValues]").val());
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