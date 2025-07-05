var pageNotify = 1;
var numOfNewLoadedMess;
var body = '';
var IS_NEED_LOAD_MESSAGE = true;
function loadNotifyMessage(pageNotify){
	var accountId = $('#accountid-login').val();
	//console.log(pageNotify);
	$.ajax({
		type: "GET",
		url: BASE_URL + "notification/build-list-notification-messsage",
		data: {
			"accountId": accountId,
			"pageNotify": pageNotify
		},
		dataType: 'json',
		global: false,
	    beforeSend: function(xhrObj) {
	    	var token = $("meta[name='_csrf']").attr("content");
		  	var header = $("meta[name='_csrf_header']").attr("content");
		  	xhrObj.setRequestHeader(header, token);
	    },
		success: function(result){
			//console.log(result);
			$(".notify-loader").css("display", "none");
			var html = '';
			
			numOfNewLoadedMess = result.length;
			if(pageNotify === 1){
				$("#notify-ul").html('');
			}
			if(20 > result.length){
				IS_NEED_LOAD_MESSAGE = false;
			}else{
				IS_NEED_LOAD_MESSAGE = true;
			}
			
			/*var header =  `					
				<li class="header">
					<span class="pull-left"><b>${NOTIFICATION_MESSAGE}</b></span>
					<span class="pull-right pointer" id="mark-as-read-all">${NOTIFICATION_MARK_ALL_READ}</span>
					<span class="clearfix"></span>
				</li>	
			`;
			
			
			html += header;*/

			
			/*var openGate = `
					<ul class="menu-notifi" id="notify-ul">
			`;
			
			html += openGate;*/
			var items = $('#notify-ul').find('.outer-li');
			var count = Math.round(items.length/20);
			var decimalCount = items.length%20;
			if(count < 2 || (decimalCount === 0 && count < pageNotify)) {
				$.each(result, function(index, object){
					if(!object.description || !object.createDateFormat){
						return;
					}
					if(object.isRead == false){
						html += '<li id="' + object.id + '" class="outer-li"> \
											<a> \
												<h4 data-message-id="' + object.id + '" class="notification-view-detail" style="cursor:pointer;font-weight:bold;">' + object.description + '\
													<small class="notification-delete-detail" title="' + NOTIFICATION_MARK_DELETE + '"> \
														<i class="fa fa-trash"></i> \
													</small> \
													<small class="notification-mark-read"> \
														<i class="fa fa-circle-o" title="' + NOTIFICATION_MARK_READ + '" aria-hidden="true"></i> \
														<i class="fa fa-check hide" aria-hidden="true"></i> \
													</small> \
												</h4> \
												<p>' + NOTIFICATION_CREATED_DATE + ': ' + object.createDateFormat + '</p> \
											</a> \
										</li>';
					}else {
						html += '<li id="' + object.id + '" class="outer-li"> \
											<a> \
												<h4 data-message-id="' + object.id + '" class="notification-view-detail" style="cursor:pointer">' + object.description + ' \
													<small class="notification-delete-detail" title="' + NOTIFICATION_MARK_DELETE + '"> \
														<i class="fa fa-trash"></i> \
													</small> \
													<small class="notification-mark-unread"> \
														<i class="fa fa-check-circle" title="' + NOTIFICATION_MARK_UNREAD + '" aria-hidden="true"></i> \
														<i class="fa fa-check hide" aria-hidden="true"></i> \
													</small> \
												</h4> \
												<p>' + NOTIFICATION_CREATED_DATE + ': ' + object.createDateFormat + '</p> \
											</a> \
										</li>';
					}
					
				});
				
				
				//html += body;
				
				/*var loader = `
					<li>
						<ul class="menu-notifi">
							<li class="notify-loader"></li>
						</ul>
					</li>	
				`;
				
				html += loader;
				
				var closeGate = `
					</ul>
					`;
				
				html += closeGate;*/
				
				
				$("#notify-ul").append(html);
			}
			
			/*var div = $('#notify-ul').get(0);
			if(pageNotify != 1){					
				div.scrollTop = (div.scrollHeight - div.clientHeight) / 2;
			}else{
				div.scrollTop = 0;
			}	*/		
		},
		error: function(error){
			console.log(error);
		}
	});
}

