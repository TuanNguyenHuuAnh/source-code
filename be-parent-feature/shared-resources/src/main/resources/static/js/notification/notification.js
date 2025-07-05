$(document).ready(function() {
	var markUnReadIcon = 'fa-check-circle';
	var markReadIcon = 	'fa-circle-o';
	
	//Update when first load page
	updateBadge();
//	loadNotifyMessage(pageNotify);
	loadMessWhenReceivingNofity(true);
	
	//Handle dynamic load DOM	updateReadNotification
	 $(document).on('click', '.notification-view-detail', function () {
		 	//console.log("Click view detail message");
			event.preventDefault();
			var appInboxId = $(this).data('message-id');
			var accountId = $("#accountid-login").val();
			$.ajax({
				type: 'POST',
				url: BASE_URL + "notification/updateReadNotification",
				data: {
					"accountId": accountId,
					"appInboxId": appInboxId
				},
				global: false,
			    beforeSend: function(xhrObj) {
			    	var token = $("meta[name='_csrf']").attr("content");
				  	var header = $("meta[name='_csrf_header']").attr("content");
				  	xhrObj.setRequestHeader(header, token);
			    },
				success: function(data){
					updateBadge();
					if(data!= null && data !=""){
						var url = BASE_URL + "document/detail?id=" + data.documentId + "&formId=" + data.serviceId + "&docType=2" + "&mode=2";
						redirect(url);
					}
				},
				error: function(error){
					console.log(error);
				}
			});
	 });
	 
	//document.addEventListener('scroll', function (event) {
	 $('#notify-ul').on('scroll', function(event) {
		    //if (event.target.id === 'notify-ul') { // or any other filtering condition     
		    	var element = $('#notify-ul').get(0);
		    	var imageUrl = $("#notify-imagepath").val();
		    	console.log('element.scrollTop : ' + element.scrollTop + 'element.clientHeight : ' + element.clientHeight + 'element.scrollHeight : ' + element.scrollHeight);
		    	if(element.scrollTop + element.clientHeight >= element.scrollHeight) {
		    		if(IS_NEED_LOAD_MESSAGE == true){
			    		console.log("scrolling");		    		 
			 	        $(".notify-loader").css("background", "url(" + imageUrl + ") 50% 50% no-repeat");
			 	        $(".notify-loader").css("background-size", "contain");
			 	        $(".notify-loader").css("display", "block");
			 	        var items = $(this).find('.outer-li');
			 	       var count = Math.round(items.length/20);
			 	        if(count > 0) {
			 	        	pageNotify = count + 1;	
			 	        } else {
			 	        	pageNotify = 2;
			 	        }
			 	        setTimeout(function(){ 
			 	        	loadNotifyMessage(pageNotify);
			 	        }, 500);
		        	}      
		    	} else {
		    		
		    	}		        
		   // }
});
		//}, true /*Capture event*/);
	 
	 
	//Handle dynamic load DOM	markAsReadAll
	 $(document).on('click', '#mark-as-read-all', function () {
			event.preventDefault();
			var accountId = $("#accountid-login").val();
			$.ajax({
				type: 'POST',
				url: BASE_URL + "notification/markAsReadAll",
				data: {
					"accountId": accountId,
				},
				global: false,
			    beforeSend: function(xhrObj) {
			    	var token = $("meta[name='_csrf']").attr("content");
				  	var header = $("meta[name='_csrf_header']").attr("content");
				  	xhrObj.setRequestHeader(header, token);
			    },
				success: function(result){
					updateBadge();
					//body = '';
					$("#notify-ul").html('');
					pageNotify = 1;
					loadNotifyMessage(pageNotify);
					/*if(numOfNewLoadedMess < 20){
						IS_NEED_LOAD_MESSAGE = false;
					}
					IS_NEED_LOAD_MESSAGE = true;*/
				},
				error: function(error){
					console.log(error);
				}
			});
	 });

	//Handle dynamic load DOM	deleteAll
	 $(document).on('click', '#delete-all', function () {
			event.preventDefault();
			var accountId = $("#accountid-login").val();
			$.ajax({
				type: 'POST',
				url: BASE_URL + "notification/deleteNotification/all",
				data: {
					"accountId": accountId,
				},
				global: false,
			    beforeSend: function(xhrObj) {
			    	var token = $("meta[name='_csrf']").attr("content");
				  	var header = $("meta[name='_csrf_header']").attr("content");
				  	xhrObj.setRequestHeader(header, token);
			    },
				success: function(result){
					updateBadge();
					//body = '';
					$("#notify-ul").html('');
					pageNotify = 1;
					loadNotifyMessage(pageNotify);
					/*if(numOfNewLoadedMess < 20){
						IS_NEED_LOAD_MESSAGE = false;
					}
					IS_NEED_LOAD_MESSAGE = true;*/
				},
				error: function(error){
					console.log(error);
				}
			});
	 });
	 
	 //Handle dynamic load DOM	deleteNotification
	 $(document).on('click', '.notification-delete-detail', function () {
			event.preventDefault();
			var row = $(this).closest('.notification-view-detail');
			var appInboxId = row.data('message-id');
			var accountId = $("#accountid-login").val();
			var idValue = '#' + appInboxId;
			var val = $('#notify-count').text();
			$.ajax({
				type: 'POST',
				url: BASE_URL + "notification/deleteNotification",
				data: {
					"accountId": accountId,
					"appInboxId": appInboxId
				},
				global: false,
			    beforeSend: function(xhrObj) {
			    	var token = $("meta[name='_csrf']").attr("content");
				  	var header = $("meta[name='_csrf_header']").attr("content");
				  	xhrObj.setRequestHeader(header, token);
			    },
				success: function(url){
					var child = $(idValue).find('.fa-circle-o');
					if(child !== undefined && child !== null && child.length > 0) {
						var count = parseInt(val) - 1;
						$('#notify-count').text(count);
					}
					$(idValue).hide();
				},
				error: function(error){
					console.log(error);
				}
			});
			return false;
	 });

	//Handle dynamic load DOM markToDelete
	 $(document).on('click', '.notification-mark-delete', function () {
			event.preventDefault();
			console.log($(this).find('input'))
			$(this).find('input.input-mark-to-delete').prop("checked", true);
			/*var row = $(this).closest('.notification-view-detail');
			var appInboxId = row.data('message-id');
			var accountId = $("#accountid-login").val();
			$.ajax({
				type: 'POST',
				url: BASE_URL + "notification/markAsRead",
				data: {
					"accountId": accountId,
					"appInboxId": appInboxId
				},
				global: false,
			    beforeSend: function(xhrObj) {
			    	var token = $("meta[name='_csrf']").attr("content");
				  	var header = $("meta[name='_csrf_header']").attr("content");
				  	xhrObj.setRequestHeader(header, token);
			    },
				success: function(url){
					updateBadge();
					row.css("font-weight","normal");
					row.find('.fa.' + markReadIcon).attr("title", NOTIFICATION_MARK_UNREAD);
					row.find('.notification-mark-read').attr('class', 'notification-mark-unread');
					row.find('.fa.' + markReadIcon).attr('class', 'fa ' + markUnReadIcon);
				},
				error: function(error){
					console.log(error);
				}
			});*/
			return false;
	 });
	 
	//Handle dynamic load DOM markAsReadDetail
	 $(document).on('click', '.notification-mark-read', function () {
			event.preventDefault();
			var row = $(this).closest('.notification-view-detail');
			var appInboxId = row.data('message-id');
			var accountId = $("#accountid-login").val();
			$.ajax({
				type: 'POST',
				url: BASE_URL + "notification/markAsRead",
				data: {
					"accountId": accountId,
					"appInboxId": appInboxId
				},
				global: false,
			    beforeSend: function(xhrObj) {
			    	var token = $("meta[name='_csrf']").attr("content");
				  	var header = $("meta[name='_csrf_header']").attr("content");
				  	xhrObj.setRequestHeader(header, token);
			    },
				success: function(url){
					updateBadge();
					row.css("font-weight","normal");
					row.find('.fa.' + markReadIcon).attr("title", NOTIFICATION_MARK_UNREAD);
					row.find('.notification-mark-read').attr('class', 'notification-mark-unread');
					row.find('.fa.' + markReadIcon).attr('class', 'fa ' + markUnReadIcon);
				},
				error: function(error){
					console.log(error);
				}
			});
			return false;
	 });
	 
	//Handle dynamic load DOM markAsUnReadDetail
	 $(document).on('click', '.notification-mark-unread', function () {
			event.preventDefault();
			var row = $(this).closest('.notification-view-detail');
			var appInboxId = row.data('message-id');
			var accountId = $("#accountid-login").val();
			$.ajax({
				type: 'POST',
				url: BASE_URL + "notification/markAsUnRead",
				data: {
					"accountId": accountId,
					"appInboxId": appInboxId
				},
				global: false,
			    beforeSend: function(xhrObj) {
			    	var token = $("meta[name='_csrf']").attr("content");
				  	var header = $("meta[name='_csrf_header']").attr("content");
				  	xhrObj.setRequestHeader(header, token);
			    },
				success: function(url){
					updateBadge();
					row.css("font-weight","bold");
					row.find('.fa.' + markUnReadIcon).attr("title", NOTIFICATION_MARK_READ);
					row.find('.notification-mark-unread').attr('class', 'notification-mark-read');
					row.find('.fa.' + markUnReadIcon).attr('class', 'fa ' + markReadIcon);
				},
				error: function(error){
					console.log(error);
				}
			});
			return false;
	 });
	 
	 /* $("#notify-messages").on("click", function(event){
		console.log("Click bell");
		event.preventDefault();
		loadNotifyMessage();
	}); */
});

function loadMessWhenReceivingNofity(haveNotify){
	$("#notify-messages").click(function(){
		$("#notify-ul").html('');
		if(haveNotify){
			//body = ``;
			pageNotify = 1;
			loadNotifyMessage(pageNotify);	
			//haveNotify = false;
		}
	})
}

function updateBadge() {
	var accountId = $("#accountid-login").val();
	$.ajax({
		type : "GET",
		url : BASE_URL + "notification/countbadge",
		data : {
			"accountId" : accountId
		},
		global: false,
	    beforeSend: function(xhrObj) {
	    	var token = $("meta[name='_csrf']").attr("content");
		  	var header = $("meta[name='_csrf_header']").attr("content");
		  	xhrObj.setRequestHeader(header, token);
	    },
		success : function(result) {
			$("#notify-count").html(result);
		},
		error : function(error) {
			console.log(error);
		}
	})
}