$(document).ready(function() {
	
	$(".j-form-validate").validate({});
	//isFirst = true;
	currentOrgNode = null;
//	loadOrgTree(0, null);
	messageList = null;
	
	// --------------- Other ---------------------------------------------------
	/*$(document).on('click', '#approvalLimitFlg', function() {
		if($(this).is(':checked')) {
			$("#approvalLimit").prop( "disabled", true );
		} else {
			$("#approvalLimit").prop( "disabled", false );
		}
    });
	
	$(document).on('click', '#dailyLimitFlg', function() {
		if($(this).is(':checked')) {
			$("#dailyLimit").prop( "disabled", true );
		} else {
			$("#dailyLimit").prop( "disabled", false );
		}
    });
	
	$(document).on('click', '#id-table-list-acc .check-account', function() {
		$(this).val($(this).is(':checked'));
	});*/
	
	$('#id-organization-tree').tree({
	    url: BASE_URL + 'organization/get-node'
	    
	    ,onClick: function(node){
			$('#id-current-org-id').val(node.id);
			currentOrgNode = node;
			// Build organization detail
			getOrgDetail(node);
		},
		
		onLoadSuccess:function(node){
			var orgId = $('#id-current-org-id').val();
			if(orgId != ''){
				var nodeTmp = $('#id-organization-tree').tree('find', orgId);
				if(nodeTmp!=null){
					$('#id-organization-tree').tree('expandTo', nodeTmp.target).tree('select', nodeTmp.target);
				}else{
					parentOrgId = $('#parent-org-id').val();
					var node = $('#id-organization-tree').tree('find', parentOrgId);
					if(node!=null){
						 $('#id-organization-tree').tree('reload', node.target);
//						loadOrgTree(0, null);
					}
				}
			}else{
				var element = $('#id-organization-tree').find('div:first-child');
				element.first('.tree-node').click();
			}
			
		}
	});
	
});

// ----------------- Function --------------------------------------------------
// Get detail of organization
function getOrgDetail(node) {
	
	$.ajax({
        type  : "GET",
        url   : BASE_URL + "organization/detail",
        data  : {
        	"orgId" : node.id
        },
        success: function (data , textStatus, resp ) {
        	$("#id-box-org-detail").html(data);    
        },
        complete:function(){
        	if(node.state == 'closed' || (node.children != null && node.children.length != 0))
        	{
        		$('#id-btn-delete-org').addClass('hide')
        	}
        	loadUserList(node.id);
        	if(messageList!=null){
        		$('#msg').html('<div class="alert alert-'+messageList.messages[0].status+' alert-dismissible">'
    					+'<button aria-hidden="true" data-dismiss="alert" class="close" type="button">×</button>'
    					+'<h4><i class="icon fa fa-'+messageList.messages[0].status+'"></i>Alert!</h4>'
    					+'<div/>'+messageList.messages[0].content+'</div>');
        		messageList = null;
        	}
        }
    });
	
}

// Build organization tree
function loadOrgTree(nodeId, selectId) {
	
	$.ajax({                
    	dataType : "json",
        type  : "POST",
        url   : BASE_URL + "organization/build-organization",
        success: function (data) {
        	// Load and build organization
    		$('body #id-organization-tree').tree({
        		data : data
        	});
    		
    		if (nodeId > 0) {
    			// Find node and selected this node
    			var nodeTmp = $('#id-organization-tree').tree('find', nodeId)
	        	$('#id-organization-tree').tree('select', nodeTmp.target);
    			$('#id-current-org-id').val(nodeId);
    			currentOrgNode = nodeTmp;
    		}
        }, 
        complete:function()
        {
        	$('#id-organization-tree').tree('collapseAll');
        	
        	if(isFirst){
	        	//default show detail first node
	        	var element = $('#id-organization-tree').find('div:first-child');
	        	element.first('.tree-node').click();
	        	isFirst = false;
        	}
        	if(selectId!=null){
        		var nodeTmp = $('#id-organization-tree').tree('find', selectId)
        		$('#id-organization-tree').tree('expandTo', nodeTmp.target).tree('select', nodeTmp.target);
    			$('#id-current-org-id').val(selectId);
    			currentOrgNode = nodeTmp;
    			//console.log(currentOrgNode);
        	}
        		
        }
    });
}

// Load list account department
function loadListAccDep(orgId) {
	$.ajax({
        type  : "GET",
        url   : BASE_URL + "organization/view-account-list",
        data  : {
        	"orgId" : orgId
        },
        success: function (data , textStatus, resp ) {
        	$("#id-box-org-list-account").html(data);
        }
    });
}

// jQuery DatePicker: Validate End date should be greater than Start date		
//function changeDatepicker(){
//	
//	var startDate = new Date('01/01/2010');
//	
//	var idEffectedDate = $("#id-effected-date").val().split('-').join('/');
//	if (idEffectedDate != null && idEffectedDate != "undefined"){
//		startDate = idEffectedDate;
//	}
//	
//	var FromEndDate = new Date();	
//	FromEndDate.setDate(FromEndDate.getDate() + 365);
//	
//	var idExpiredDate = $("#id-expired-date").val().split('-').join('/');
//	if (idExpiredDate != null && idExpiredDate != "undefined"){
//		FromEndDate = idExpiredDate;
//	}
//	
//	var ToEndDate = '31/12/9999';	
//	
//	$('.datepicker-from').datepickerUnit({
//		format: DATE_FORMAT,
//        changeMonth: true,
//        changeYear: true,
//        autoclose: true,
//        keyboardNavigation : true,
//		weekStart: 1,
//		startDate: '01/01/2010',
//		endDate: FromEndDate,
//		autoclose: true
//		})
//		.on('changeDate', function (selected) {
//		        startDate = new Date(selected.date.valueOf());
//		        startDate.setDate(startDate.getDate(new Date(selected.date.valueOf())));
//		        $('.datepicker-to').datepickerUnit('setStartDate', startDate);  
//		    });
//		$('.datepicker-to')
//		    .datepickerUnit({
//		    	format: DATE_FORMAT,
//                changeMonth: true,
//                changeYear: true,
//                autoclose: true,
//                keyboardNavigation : true,
//		    	weekStart: 1,
//		        startDate: startDate,
//		        endDate: ToEndDate,
//		        autoclose: true
//		    })
//		    .on('changeDate', function (selected) {
//		        FromEndDate = new Date(selected.date.valueOf());
//		        FromEndDate.setDate(FromEndDate.getDate(new Date(selected.date.valueOf())));
//		        $('.datepicker-from').datepickerUnit('setEndDate', FromEndDate);
//		    });
//}  

/*function initMap() {	
	var Lat = "10.762622";
	var Long = "106.660172";
	var isDraggable = false;
	
	if ($('#id-latitude-view').text() != null && $('#id-latitude-view').text() != "undefined" &&  $('#id-latitude-view').text() != ""){
		Lat = $('#id-latitude-view').text();
	} else if ($('#id-latitude-edit').val() != null && $('#id-latitude-edit').val() != "undefined" &&  $('#id-latitude-edit').val() != "") {
		Lat = $('#id-latitude-edit').val();
		isDraggable = true;
	}
	
	if ($('#id-longtitude-view').text() != null && $('#id-longtitude-view').text() != "undefined" &&  $('#id-longtitude-view').text() != ""){
		Long = $('#id-longtitude-view').text();
	} else if ($('#id-longtitude-edit').val() != null && $('#id-longtitude-edit').val() != "undefined" &&  $('#id-longtitude-edit').val() != "") {
		Long = $('#id-longtitude-edit').val();
		isDraggable = true;
	}
	
	var latlng = {lat: parseFloat(Lat), lng: parseFloat(Long)};
	var map = new google.maps.Map(document.getElementById('map'), {
		zoom: 20,
		center: latlng,		
		mapTypeId : google.maps.MapTypeId.ROADMAP
	 });
	var marker = new google.maps.Marker({
        position: latlng,
        map: map,
        draggable: isDraggable
     });
	
	google.maps.event.addListener(marker,'drag',function(event) {
	    document.getElementById('id-latitude-edit').value = event.latLng.lat();
	    document.getElementById('id-longtitude-edit').value = event.latLng.lng();
	});
	
	google.maps.event.addListener(marker,'dragend',function(event) 
	        {
	    document.getElementById('id-latitude-edit').value =event.latLng.lat();
	    document.getElementById('id-longtitude-edit').value =event.latLng.lng();
	});

	// Create the search box and link it to the UI element.
	var input = document.getElementById('pac-input');
	
	var autocomplete = new google.maps.places.Autocomplete(input, {types: ['geocode']});
	autocomplete.bindTo('bounds', map);			
	
	// [START region_getplaces]
	// Listen for the event fired when the user selects a prediction and retrieve
	// more details for that place.
	
	google.maps.event.addListener(autocomplete, 'place_changed', function () {	
		var place = autocomplete.getPlace();
		
		if (place.length == 0) {
			return;
		}	
		
		$('#id-latitude-edit').val(place.geometry.location.lat());
		$('#id-longtitude-edit').val(place.geometry.location.lng());
		
		// For each place, get the icon, name and location.
		var bounds = new google.maps.LatLngBounds();
		var icon = {
			url : place.icon,
			size : new google.maps.Size(80, 80),
			origin : new google.maps.Point(0, 0),
			anchor : new google.maps.Point(17, 34),
			scaledSize : new google.maps.Size(25, 25)
		};

		//marker.setMap(null);
		
		// Create a marker for each place.
		var marker = new google.maps.Marker({
			map : map,
			//icon : icon,
			title : place.name,
			position : place.geometry.location,
			draggable:true,
		});
		
		google.maps.event.addListener(marker,'drag',function(event) {
		    document.getElementById('id-latitude-edit').value = event.latLng.lat();
		    document.getElementById('id-longtitude-edit').value = event.latLng.lng();
		});
		
		google.maps.event.addListener(marker,'dragend',function(event) {
		    document.getElementById('id-latitude-edit').value =event.latLng.lat();
		    document.getElementById('id-longtitude-edit').value =event.latLng.lng();
		});

		if (place.geometry.viewport) {
			// Only geocodes have viewport.
			bounds.union(place.geometry.viewport);
		} else {
			bounds.extend(place.geometry.location);
		}

		map.fitBounds(bounds);
	});
}*/

//----------------- Organization --------------------------------------------------------

// Edit organization
function edit() {
	$.ajax({
        type  : "GET",
        url   : BASE_URL + "organization/edit",
        data  : {
        	"orgId" : $('#id-current-org-id').val()
        },
        success: function (data , textStatus, resp ) {
        	$("#id-box-org-detail").html(data);	     
//        	changeDatepicker();
        	// set readonly code
        	if ($("#id-org-code").val() != "") {
        		$("#id-org-code").attr('readonly', 'readonly');
        	}
        }
    });
};

// New organization
function newOrg() {
	$.ajax({
        type  : "GET",
        url   : BASE_URL + "organization/add",
        data  : {
        	"orgId" : $('#id-current-org-id').val()
        },
        success: function (data , textStatus, resp ) {
        	$("#id-box-org-detail").html(data);	      
//        	changeDatepicker();
        	$('input#orgId').val('');
        	$('input#orgParentId').val($('#id-current-org-id').val());
        }
    });
};

// Cancel edit organization
function cancel() {
	var node = $('#id-organization-tree').tree('find', $('#id-current-org-id').val())
	getOrgDetail(node);
};

// Update organization
function update() {
	if ($(".j-form-validate").valid()) {
		var values = $("#id-form-org-edit").serialize();
		$.ajax({                	        	
            type  : "POST",
            data  : values,
            url   : BASE_URL + "organization/update",
            success: function (data) {	          
            	$("#id-box-org-detail").html(data);	            	         
            },
            complete:function()
            {
            	if($('.alert-danger').length==0){
            		var detailId = $('#detailId').val();
            		var currentOrgId = $('#id-current-org-id').val();
            		//var parentNodeTmp = null;
            		var parentNode = null;
            		var nodeTmp = $('#id-organization-tree').tree('find', currentOrgId);
            		if(detailId != currentOrgId){
            			parentNode = $('#id-organization-tree').tree('getParent', nodeTmp.target);
            			$('#parent-org-id').val(currentOrgId);
            			$('#id-current-org-id').val(detailId);
            		}else{
            			parentNode = $('#id-organization-tree').tree('getParent', nodeTmp.target);
            		}
            		$('#id-organization-tree').tree('reload', parentNode != null ? parentNode.target : nodeTmp.target);
//            		loadOrgTree(0, null);
            		loadUserList(detailId);
            	}
        	}
        });				
	}
};

// Delete Organization	
function deleteOrg() {
	popupConfirm( MSG_DEL_CONFIRM, function(result) {
		if (result) {
			$.ajax({
		        type  : "POST",
		        url   : BASE_URL + "organization/delete",
		        data  : {
		        	"orgId" : $('#id-current-org-id').val()
		        },
		        success: function (data) {
		        	if(data.messages[0].status=='success'){
		        		messageList = data;
		        		$('#id-current-org-id').val('');
						$('#id-organization-tree').tree('reload', $('#id-organization-tree').tree('getRoot').target);
						getOrgDetail($('#id-organization-tree').tree('getRoot'));
//		        		loadOrgTree(0, null);
		        	}else{
		        		$('#msg').html('<div class="alert alert-'+data.messages[0].status+' alert-dismissible">'
		    					+'<button aria-hidden="true" data-dismiss="alert" class="close" type="button">×</button>'
		    					+'<h4><i class="icon fa fa-'+data.messages[0].status+'"></i>Alert!</h4>'
		    					+'<div/>'+data.messages[0].content+'</div>');
		        	}
		        	
		        }
		    });
		}			
	});
};

function loadUserList(orgId){
	ajaxSearch("organization/ajaxList", {"orgId" : orgId}, "userList", this, event);
}

function setConditionSearch() {
	var condition = {};
	condition["orgId"] = $('#id-current-org-id').val()
	return condition;
}