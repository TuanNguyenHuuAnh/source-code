$(function(){
	if( SOCKET == null){
		return;
	}	
	//remove notify
	
	//$("#imgAccount").attr("src", BASE_URL + "ajax/download?fileName=account_avatar/30638__1494313046790.jpg");
	/*SOCKET in common_unit.js*/
	var clientidRef = CLIENT_ID_REF;
	var currentClientid = '';
	var typeMessageText = 1;
	var typeMessageImage = 2;
	var fa_online = 'fa fa-check-circle-o';
	var fa_offline = 'fa fa-circle-o';
	var prefix_contact = 'contact';
	var prefix_title = 'title';
	var prefix_recent = 'recent';
	var isConnect = false;
	var list_contact = [];
	var list_contact_history = [];
	var list_message = [];	
	var checkCloseX = 0;
	
	// Kiểm tra để khóa lại màn hình chat
	if (SOCKET.disconnected){
		$("#messageChat").prop('disabled', true);
		$("#btnSendMessage").prop('disabled', true);
		$("#assignUser").prop('disabled', true);
	}else{
		$("#messageChat").prop('disabled', false);
		$("#btnSendMessage").prop('disabled', false);
		$("#assignUser").prop('disabled', false);
	}
	
	$("#userInfoForm").on('click','#iconClose', function(event){
		event.preventDefault();
		if (currentClientid != null && currentClientid != ''){
			var message = MSG_NOTIFY.replace("@customer", $("#infoUser").text());
			popupConfirmWithButtons(message, LIST_BUTTON_CONFIRM_DELETE, function(result){
				if (result) {
					var clientid = currentClientid;
					var message = {
							'clientid' : clientid, // id client
							'agent' : AGENT_CHAT,
							'agentName': $("#agentFullName").text()
					}
					list_message = [];
					$("#infoUser").text('');
					$("#contentChat").text('');
					$("#nickname").text('');
					$("#service").text('');
					$("#phone").text('');
					currentClientid = '';
					sendMail(clientid);
					
				    SOCKET.emit('close.connection', message);
				    removeUserOnContactList(clientid);
				}
			});
		}
	});
	
	getListClientWaiting();
	
	/**
	 * Lấy danh sách Client đang ở trạng thái chờ
	 * */
	function getListClientWaiting(){
		var message = {			
			'agent' : AGENT_CHAT		
			
		}	
		SOCKET.emit('list.client.waiting', message);
	}
	
	checkLastFocus();
	
	//checkLastFocus();
	function checkLastFocus(){
		var isChecking = false;
		var timer = setInterval(function(){ 
			if(list_contact.length > 0){
				if(isChecking == false){
					isChecking = true;
					var dateCurrent = new Date();
					//start check
					for(var i = 0; i < list_contact.length; i++){
						var LAST_DATE_FOCUS = list_contact[i].LASTTIME_FORCUS;
						var diff = dateCurrent.getTime() - LAST_DATE_FOCUS.getTime()
						var diffOfDate = new Date(diff);
						var resultInMinutes = diffOfDate.getMinutes();
						if(resultInMinutes > SESSION_TIMEOUT){
							//disconnect to client
							var clientid = list_contact[i].clientid;
							var message = {
									'clientid'	 : clientid, // id client
									'agent' : AGENT_CHAT
							}	
							if(clientid == currentClientid){
								list_message = [];
								$("#infoUser").text('');
								$("#contentChat").text('');
								currentClientid = '';
							}
						    SOCKET.emit('close.connection.timeout', message);
						    removeUserOnContactList(clientid);
						    
//						    $.cookie(clientid,'',{ expires: -1, path: '/' });
						}
					}
					isChecking = false;
				}
			}	 
		}, 1000);
	}
	
	function removeUserOnContactList(id){
		var list = [];
		for(var i = 0; i < list_contact.length; i++){
			if(list_contact[i].clientid != id){
				list.push(list_contact[i]);
			}
		}
		list_contact = list;
		// hidden in html
		$("#user_" + id).css('display','none');
		$("#user_" + id).addClass('hidden');
	}
	
	
	function getBoxContactOffline(prefix,clientid,fullname,time){
		var box = $("#box_contact_offline").html();
		if(box == undefined){
			return ;
		}
		box = box.replace(/\@id/g,prefix+''+clientid);
		box = box.replace(/\@clientid/g,clientid);
		box = box.replace('@fullname',fullname);
		box = box.replace('@timesend',time);
		
		var sortName = getSortName(fullname);
		box = box.replace('@avatarName',sortName);
		return box;
	}
	
	/**
	 * Lấy dữ liệu tin nhắn để trả vào id=box_contact_online -- Danh sách bên trái
	 * */
	function getBoxContactOnline(prefix, clientid, fullname, time){
		var box = $("#box_contact_online").html();
		if(box == undefined){
			return ;
		}
		box = box.replace(/\@id/g,prefix+''+clientid);
		box = box.replace(/\@clientid/g,clientid);
		box = box.replace('@fullname',fullname);
		box = box.replace('@timesend',time);
		
		var sortName = getSortName(fullname);
		box = box.replace('@avatarName',sortName);
		
		return box;
	}
	
	/**
	 * Lấy dữ liệu tin nhắn để trả vào id=box_message_client -- Cho client
	 * */
	function getBoxMessageClient(fullname,time,messageid,message){
		var box = $("#box_message_client").html();
		if(box == undefined){
			return ;
		}
		box = box.replace('@fullname',fullname);
		box = box.replace('@messageid',messageid);
		box = box.replace('@message',message);
		box = box.replace('@timesend',time);
		
		var sortName = getSortName(fullname);
		box = box.replace('@avatarName', sortName);
		
		return box;
	}
	
	function getBoxMessageImageClient(fullname,time,messageid,filename){
		var box = $("#box_message_client_2").html();
		if(box == undefined){
			return ;
		}
		var imgInput = '<img src = "@srcimage" style="width:200px;height:100px;float:left;" />';
		imgInput = imgInput.replace('@srcimage',filename);
		box = box.replace('@fullname',fullname);
		box = box.replace('@messageid',messageid);
		box = box.replace('@image',imgInput);
		box = box.replace('@timesend',time);
		
		var sortName = getSortName(fullname);
		box = box.replace('@avatarName',sortName);
		return box;
	}
	
	/**
	 * Lấy dữ liệu tin nhắn để trả vào id=box_message_user -- Cho agent
	 * */
	function getBoxMessageUser(time, messageid, message, oldAgentName, boxMessTmp, oldAgent){
		var box = $("#box_message_user").html();
		
		if(box == undefined){
			return ;
		}
		box = box.replace('@box-mess-user', boxMessTmp);
		box = box.replace('@oldAgent', oldAgent);
		box = box.replace('@messageid', messageid);
		box = box.replace('@message', message);
		box = box.replace('@timesend', time);
		
		var agentName = (oldAgentName != "undefined" && oldAgentName != null ) ? oldAgentName : $("#agentFullName").text();
		box = box.replace('@agentName', agentName);
		
		var sortName = getSortName(agentName);
		box = box.replace('@avatarName',sortName);
		
		return box;
	}
	
	function getBoxMessageImageUser(time,messageid,filename){
		var box = $("#box_message_user_2").html();
		
		if(box == undefined){
			return ;
		}
		var imgInput = '<img src = "@srcimage" style="width:200px;height:100px;float:right;" />';
		imgInput = imgInput.replace('@srcimage',filename);
		box = box.replace('@messageid', messageid);
		box = box.replace('@image', imgInput);
		box = box.replace('@timesend', time);
		return box;
	}
	
	/**
	 * Lấy dữ liệu tin nhắn để trả vào id=box_message_system -- Cho admin
	 * */
	function getBoxMessageSystem(message){
		var box = $("#box_message_system").html();
		if(box == undefined){
			return ;
		}
		box = box.replace('@message',message);
		return box;
	}	
	
	/**
	 * Sử kiện khi click vào danh sách contact bên trái
	 * */
	$(document).unbind('.userbox').on('click', '.userbox', function(e) {
		doAction(this);
	});
	
	/**
	 * Lấy Lịch sử tin nhắn và join vào phòng chat với client
	 * */
	function chat2Client(clientid){
		getHistoryMessage(clientid)
		// connect room client
		var message = {
			'type': TYPECHAT,
			'clientid' : clientid,
			'agent':AGENT_CHAT,
			'agentName': $("#agentFullName").text()
		}
		var contact = $("#user_" + clientid);
		$("#numberMessage_" + clientid).text('0');
		
		$("#unreadMessage_" + clientid).css('display', 'none');
		
		saveUnreadMessage(clientid, 0);
		
		if (contact.hasClass("offline")){
			// Lấy lịch sử tin nhắn
			$.ajax({  	
		        type  : "GET",
		        data : {'clientid' : clientid},
		        url   : BASE_URL + "chat/getMessageClient",
		    }).done(function(data){
		    	
		    	setMessageForContent(data);
		    	
		    });
			
//			var box  = getBoxMessageSystem(CHAT_ALERT_CLIENT_LOGOUT);
//			$("#contentChat").append(box).html();
			autoScrollDown();			
			//  disable form 
			$("#messageChat").prop('disabled',true);
			$("#btnSendMessage").prop('disabled',true);
			$("#assignUser").prop('disabled',true);
		}else{
			$("#messageChat").prop('disabled',false);
			$("#btnSendMessage").prop('disabled',false);
			$("#assignUser").prop('disabled',false);
			SOCKET.emit('join.room.client.and.get.list.messsage', message);
		}
	}
	
	/**
	 * Lấy lịch sử chat với ClientId
	 * */
	function getHistoryMessage(clientid){
		list_message = [];
		$("#infoUser").text('');
		$("#contentChat").text('');
		// remove hover 
		$("#"+ prefix_contact).find('.userbox').each(function(){
			if($(this).attr('client-id').trim()==clientid){
				$(this).removeClass('hover-contact-chat');
				return;
			}
		});
		getInfoClient(clientid);
	}
	
	//event right click user
	/**
	 * Sự kiện khi right click vào danh sách contact bên trái : Hiện ra Bảng close user hoặc move user
	 * */
	$(document).on('contextmenu', '.user', function(e) {
		var tab = $(".list-chat").find('.active').attr('id');
		var clientid = $(this).attr('client-id');
    	
		if(tab=='contact'){
			$("#menu-control").find('.moveuser').css('display','none');
			$("#menu-control").find('.closeuser').css('display','block');
		}  else if(tab=='recent'){
			$("#menu-control").find('.moveuser').css('display','none');
			$("#menu-control").find('.closeuser').css('display','none');
			// don't display popup control
			return;
		} else if(tab=='waiting'){
			$("#menu-control").find('.moveuser').css('display','block');
			$("#menu-control").find('.closeuser').css('display','block');
		}  
		$("#menu-control").toggleClass("hide-control");
    	$("#menu-control").find('.closeuser').attr('client-id',clientid);
        $("#menu-control").css(
        {	            	
            position: "absolute",
            top: e.pageY,
            left: e.pageX,
            display: 'block'
        });      
        e.preventDefault();
	});
	
	// close user tab
	/**
	 * Sự kiện khi close user
	 * */
	$(".closeuser").unbind('click').bind('click', function(){
		var clientid = $(this).attr('client-id');
		var message = {
				'clientid' : clientid, // id client
				'agent' : AGENT_CHAT,
				'agentName': $("#agentFullName").text()
		}	
		if(clientid == currentClientid){
			list_message = [];
			$("#infoUser").text('');
			$("#contentChat").text('');
			$("#nickname").text('');
			$("#service").text('');
			$("#phone").text('');
			currentClientid = '';
			sendMail(clientid);
		}
	    SOCKET.emit('close.connection', message);
	    removeUserOnContactList(clientid);
	    
//	    $.cookie(clientid,'',{ expires: -1, path: '/' });
	});
	
	//event right click message
	/**
	 * Sự kiện khi right click vào content message sẽ hiện ra câu chào mặc định
	 * */
	$(document).on('contextmenu', '#contentChat', function(e) {
    	if(currentClientid != null && currentClientid != ''){
    		$("#menu-control-message").find('.defaultMessage').css('display','block');
			$("#menu-control-message").toggleClass("hide-control");
	        $("#menu-control-message").css(
	        {
	            position: "absolute",
	            top: e.pageY,
	            left: e.pageX,
	            display: 'block'
	        });
	        e.preventDefault();
    	}	
		
	});
	
	/**
	 * Gửi tin nhắn default
	 * */
//	$(".defaultMessage").click(function(e){		
//		$("#messageChat").val(MSG_DEFAULT);
//		sendMessage();
//	});
	
	$("#messageDefault").on('click', '', function(event){
		$("#menu-control-message").css({
			display : 'none'
		});
		
		var mes = ($(this).val() != null ? $(this).val() : '');
		
		$("#messageChat").val(mes);
		
	});
	
	$(document).on('click', function(event){
		$("#menu-control-message").css({
			display : 'none'
		});
	});
	
	/**
	 * Lấy thông tin của client
	 * */
	function getInfoClient(clientid){
		var message = {
				'clientid' : clientid,
				'agent':AGENT_CHAT
		}	
		SOCKET.emit('information.client', message);
	}	
	
	/**
	 * Upload file
	 * */
	$("#uploadFile").click(function(){
		if(currentClientid == '' || currentClientid == null){
			alert(ALERT_CHOOSE_CUSTOMER);
			return;
		}
		$("#fileAttach").click();
	})
	
	/**
	 * Sự kiện khi enter sẽ xử lý gửi tin nhắn
	 * */
	$("#messageChat").keypress(function(e){		
		if(e.which == 13){
			var message = $(this).val();
			if(message.length > 0){
				sendMessage();
				$(this).val('');
				e.preventDefault();
			}
		}
	});
	
	$("#messageChat").keydown(function(e){
		var message = {
			'type': TYPECHAT,
			'clientid' : currentClientid, // id client
			'message':$(this).val()
		};
		SOCKET.emit('key.press.message', message);
	});
	
	/**
	 * Khi nhấn button gửi tin nhắn
	 * */
	$("#btnSendMessage").click(function(){
		var message = $("#messageChat").val();
		if(message.length > 0){
			sendMessage();
			$("#messageChat").val('');	
		}
	})
	
	/**
	 * Kiểm tra Client Hiện tại có Online hay không
	 * */
	function checkCurrentClientOnline(){		
		if(currentClientid == '' || currentClientid == null){
			return false;
		}	
		
		for(var i = 0; i < list_contact.length; i++){
			if(list_contact[i].clientid  == currentClientid){
				if(list_contact[i].online == true){
					return true;
				}
			}
		}
		return false;
	}
	
	/**
	 * Xử lý sự kiện gửi tin nhắn
	 * */
	function sendMessage(){
		if(currentClientid == '' || currentClientid == null){
			alert(ALERT_CHOOSE_CUSTOMER);
			return;
		}
		
//		if(checkCurrentClientOnline() == false){
//			alert(CHAT_ALERT_CLIENT_LOGOUT);
//			return;
//		}
	
		//
		var mes = $("#messageChat").val();
		var message = {
				'type': TYPECHAT,
				'clientid' : currentClientid, // id client
				'agent' : AGENT_CHAT, // room client
				'message':mes,
				'typeMessage':typeMessageText,
				'agentName': $("#agentFullName").text(),
				'oldAgent': $("#h_resize_right").find('div.box-mess-param').last().data("old-agent")
				
		}
		
		// update time last time focus
		updateLastTimeFocus(currentClientid);
		//send message
	    SOCKET.emit('send', message);
	    
		$("#messageChat").val('');
		
	}
	function updateLastTimeFocus(id){
		for(var i = 0; i < list_contact.length; i++){
			if(list_contact[i].clientid == id){
				list_contact[i].LASTTIME_FORCUS = new Date();
				break;
			}
		}
	}
	
	// auto scroll down
	function autoScrollDown(){
		var flagAlerBox = $("#chatFlag").val();
		if(flagAlerBox != undefined){
			$('#contentChat').animate({
				scrollTop: $('#contentChat')[0].scrollHeight
			}, 2000);
		}	
		
	}
	
	/**
	 * Kiểm tra phòng của client
	 * */
	function checkRoomClientExists(list,id){
		for(var i = 0; i < list.length; i++){
			if(list[i].clientid == id){
				return true;
			}
		}
		return false;
	}
	
//	var urlUpload = 'http://10.0.0.67:3032/upload';
//	$('input[name="fileAttach"]').ajaxfileupload({
//		'action' : urlUpload,
//		'valid_extensions' : [ 'jpg', 'png', 'gif' ],
//		'onComplete' : function(response) {
//			var mes = response;
//			alert(mes);
//			//
//			var message = {
//				'type': type,
//				'clientid' : currentClientid, // id client
//				'agent' : agent, // room client
//				'agentGroup' : agentGroup, // room server
//				'message':mes,
//				'typeMessage':typeMessageText,				
//				'typeMessage':typeMessageImage					
//			}
//			SOCKET.emit('send', message);
//		},
//		'onStart' : function() {				
//		}
//	});
	$('#fileAttach').fileupload({
			url : 	BASE_URL + 'chat/uploadImageChat',	
			beforeSend : function(){
				$("#loadingUploadFile").css('display','block');
			},
			done : function(e, data) {
				$("#loadingUploadFile").css('display','none');
	        	if( data.result ) {
	        		var fileName = data.result;	        		
	        		var message = {
	        				'type': TYPECHAT,
	        				'clientid' : currentClientid, // id client
	        				'agent' : AGENT_CHAT, // room client
	        				'message':fileName,
	        				'typeMessage':typeMessageImage
	        		}	
	        	    SOCKET.emit('send', message);
	        	}
			}
		}
    );
	
	//
	$(document).contextmenu(function() {
	    return false;
	});
	
	$(document).bind("click", function(e) {
		 //tab
		 $("#menu-control").find('.closeuser').attr('client-id','');
		 $("#menu-control").find('.moveuser').attr('client-id','');
		 $("#menu-control").css({			
			display : 'none'
		 });
		 $("#menu-control-message").find('.editmessage').attr('message-id','');
		 $("#menu-control-message").find('.closemessage').attr('message-id','');
//		 $("#menu-control-message").css({			
//			display : 'none'
//		 });
		 //e.preventDefault();
	});
	
	/**
	 * Kiểm tra tin nhắn tồn tại
	 * */
	function checkMessageExists(id){
		for(var i = 0;i<list_message.lenght; i++){
			if(list_message[i] == id){
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Load page chat. ISLOAD_PAGE_CHAT = false : Default. Set giá trị ở file base-layout2.html
	 * */
	if(ISLOAD_PAGE_CHAT==false){
		ISLOAD_PAGE_CHAT = true;
		SOCKET.on('reply.server',function(data){
			// list user online	
			list_contact = [];
			$("#contact").text('');
			if (data.list != undefined){
				if(data.list.length > 0){
					for(var i = 0; i < data.list.length; i++){
						var item = data.list[i];
						item['LASTTIME_FORCUS'] = new Date();
						item['online'] = item.is_online == 1 ? true : false;
						var id = item.clientid;
						var fullname = item.fullname;
						var timesend = item.time_send;
						if(checkRoomClientExists(list_contact,id)==false){
							var box = getBoxContactOnline(prefix_contact,id,fullname,timesend);
							$("#contact").append(box).html();	
							list_contact.push(item);
						}
					}
					$("#messageChat").prop('disabled', false);
					$("#btnSendMessage").prop('disabled', false);
					$("#assignUser").prop('disabled', false);
				}else{
					$("#messageChat").prop('disabled', true);
					$("#btnSendMessage").prop('disabled', true);
					$("#assignUser").prop('disabled', true);
				}
			}else{
				$("#messageChat").prop('disabled', true);
				$("#btnSendMessage").prop('disabled', true);
				$("#assignUser").prop('disabled', true);
			}
			
			$("#"+ prefix_contact).find('.userbox').each(function(){
				if($(this).attr('client-id').trim() != clientidRef){
					var clientid = $(this).attr('client-id').trim();
					$("#unreadMessage_" + clientid).css('display', '');
					
					// Lấy tin nhắn chờ từ db
					getUnreadMessage(clientid);
				
//					getHistoryMessage(clientid);
					// connect room client
					var message = {
						'type': TYPECHAT,
						'clientid' : clientid,
						'agent':AGENT_CHAT,
						'agentName': $("#agentFullName").text()
					}
					
					var contact = $("#user_" + clientid);
					
					$("#numberMessage_" + clientid).text('0');
					
					$("#unreadMessage_" + clientid).css('display', 'none');
					
//					if (contact.hasClass("offline")){
//						// Lấy lịch sử tin nhắn
//						$.ajax({  	
//					        type  : "GET",
//					        data : {'clientid' : clientid},
//					        global: false,
//					        url   : BASE_URL + "chat/getMessageClient",
//					    }).done(function(data){
//					    	
//					    	setMessageForContent(data);
//					    	
//					    });
//					}else{
//						SOCKET.emit('join.room.client.and.get.list.messsage', message);
//					}
				}
			});
			
			// Chọn vào phần từ clientidRef trong danh sách liên hệ
			if (clientidRef != 'user_@clientid'){
//				getUnreadMessage(clientidRef);
				
				$('#user_' + clientidRef).trigger('click');
				
//				var data = $.cookie(clientidRef);
//				if (clientidRef != "null" && (data == undefined && data == null) && data != ""){
//					$("#messageChat").val(MSG_DEFAULT);
//					sendMessage();
//					
//					var date = new Date();
//					var minutes = 60;
//					date.setTime(date.getTime() + (minutes * 60 * 1000));
//					
//					$.cookie(clientidRef, clientidRef, { expires: date, path: '/'});
//				}
			}
		});
		
		/**
		 * Bắt sự kiện kết nối lại server chat thì đã đóng kết nối với user
		 * */
		SOCKET.on('reply.server.after.close.connection.user',function(data){
			if(data.list.length > 0){
				for(var i = 0; i < data.list.length; i++){
					var item = data.list[i];
					item['LASTTIME_FORCUS'] = new Date();
					item['online'] = item.is_online == 1 ? true : false;
					var id = item.clientid;
					var fullname = item.fullname;
					var timesend = item.time_send;
					if(checkRoomClientExists(list_contact, id)==false){
						var box = getBoxContactOnline(prefix_contact,id,fullname,timesend);
						$("#contact").append(box).html();	
						list_contact.push(item);
					}
				}
			}
		});
		
		/**
		 * Bắt sự kiện lấy tin nhắn từ server SOCKET gửi về
		 * */
		SOCKET.on('list.message',function(data){
			if(data.clientid == currentClientid){
				if(data.list.length > 0){
					list_message = [];
					$("#contentChat").text('');

					var boxMess = 'box-mess-user';
					var boxMess1 = 'box-mess-user-1';
					
					var boxMessTmp = 'box-mess-user';
					var agentTmp= "";

					for(var i = 0; i < data.list.length; i++){
						var box = '';
						var item = data.list[i];
						var fullname = item.fullname;
						var messageid = item.messageid;
						var message = item.message;
						var timesend = item.time_send;
						var oldAgentName = item.old_agent_name;
						var oldAgent = item.old_agent;

						if(item.type == '1'){ // client
							
							if(item.type_message == '1'){ // text
								box = getBoxMessageClient(fullname,timesend,messageid,message);
							}else{ // image
								var filename = BASE_URL + "chat/download?fileName=" + item.message;
								box = getBoxMessageImageClient(fullname,timesend,messageid,filename);
							}
						} else if(item.type == '2'){ // server
							if(item.type_message == '1'){ // text

								if (agentTmp != oldAgent) {
									agentTmp = oldAgent;
									
									boxMessTmp = boxMessTmp == boxMess1 ? boxMess : boxMess1;
								}
								
								box = getBoxMessageUser(timesend,messageid,message, oldAgentName, boxMessTmp, oldAgent);
							} else{ // image
								var filename = BASE_URL + "chat/download?fileName=" + item.message;
								var box = getBoxMessageImageUser(timesend,messageid,filename);
							}	
						}
						if(!checkMessageExists(messageid)){
							list_message.push(messageid);
							$("#contentChat").append(box).html();
						}
						
					}
				}
			}
			autoScrollDown();
		});
		
		// chat room
		SOCKET.on('chat.room',function(data){
			var binddingMessage = '';
			if(data.type == '1'){ // client
				$("#lastMessage_" + data.clientid).text(data.message);
				
				var strDateSend = data.dateSend;
				var dateSend = new Date(strDateSend);
				var hour = dateSend.getHours();
				var minute = dateSend.getMinutes();
				var timeSend = hour + ':' + minute;
				
				$("#timesend_" + data.clientid).text(timeSend);
				if(data.clientid != currentClientid){
					$("#"+ prefix_contact).find('.userbox').each(function(){
						if($(this).attr('client-id').trim() == data.clientid){
							
							//$(this).addClass('hover-contact-chat');
							var number = $("#numberMessage_" + data.clientid).text();
							if (number == ""){
								number = 0;
							}
							
							number = parseInt(number) + 1;
							$("#unreadMessage_" + data.clientid).css('display', '');
							
							$("#numberMessage_" + data.clientid).text(number);

							// lưu xuống db tin nhắn chờ
							saveUnreadMessage(data.clientid, number);
						}
					});
				}else{
					$("#lbwaiting").text('');
					if(data.typeMessage == '1') // text
					{	
						var strDateSend = data.datesend;
						var dateSend = new Date(strDateSend);
						var hour = dateSend.getHours();
						var minute = dateSend.getMinutes();
						var timeSend = hour + ':' + minute;
						var fullname = data.fullname;
						var message = data.message;
						var messageid = data.messageid;
						var box = getBoxMessageClient(fullname,timeSend,messageid,message);
						if(!checkMessageExists(messageid)){
							list_message.push(messageid);
							$("#contentChat").append(box).html();
						}
						
						// Chỉ hiện notify có tin nhắn mới và admin k còn nhìn thấy page nữa
						if (document.visibilityState === 'hidden') {
							showNotify(data.fullname, data.message.substr(0, 30));
						}
					}
					else{ // image
						var strDateSend = data.datesend;
						var dateSend = new Date(strDateSend);
						var hour = dateSend.getHours();
						var minute = dateSend.getMinutes();
						var timeSend = hour + ':' + minute;
						var fullname = data.fullname;
						var filename = BASE_URL + "chat/download?fileName=" + data.message;
						var messageid = data.messageid;
						var box = getBoxMessageImageClient(fullname,timeSend,messageid,filename);
						if(!checkMessageExists(messageid)){
							list_message.push(messageid);
							$("#contentChat").append(box).html();
						}
					}
				}
			}
			else if(data.type == '2') // server
			{
				
				if(data.typeMessage == typeMessageText) // text
				{	
					var strDateSend = data.datesend;
					var dateSend = new Date(strDateSend);
					var hour = dateSend.getHours();
					var minute = dateSend.getMinutes();
					var timeSend = hour + ':' + minute;
					var message = data.message;
					var messageid = data.messageid;
					var agentName = data.agentName;
					var oldAgent = data.oldAgent;
					
					var boxMess = 'box-mess-user';
					var boxMess1 = 'box-mess-user-1';
					
					var boxMessTmp = $("#h_resize_right").find('div.box-mess-param').last().attr("class");
					boxMessTmp = (boxMessTmp != undefined && boxMessTmp != null) ? boxMessTmp.replace(' box-mess-param', '') : 'box-mess-user' ;
					
					var agentTmp= AGENT_CHAT;
					
					if (agentTmp != oldAgent && oldAgent != null) {
						
						agentTmp = oldAgent;
						
						boxMessTmp = boxMessTmp == boxMess1 ? boxMess : boxMess1;
					}					
					
					var box = getBoxMessageUser(timeSend,messageid,message, agentName, boxMessTmp, null);
					if(!checkMessageExists(messageid)){
						if (currentClientid == data.clientid){
							list_message.push(messageid);
							$("#contentChat").append(box).html();
						}
					}
				}
				else{ // image
					
					var strDateSend = data.datesend;
					var dateSend = new Date(strDateSend);
					var hour = dateSend.getHours();
					var minute = dateSend.getMinutes();
					var timeSend = hour + ':' + minute;
					var filename = BASE_URL + "chat/download?fileName=" + data.message;
					var messageid = data.messageid;					
					var box = getBoxMessageImageUser(timeSend,messageid,filename);
					if(!checkMessageExists(messageid)){
						list_message.push(messageid);
						$("#contentChat").append(box).html();
					}
				}
			}
			autoScrollDown();
		});
		
		/**
		 * Bắt sự kiện tạo mới client từ server SOCKET: SOCKET.emit('new.client',message);
		 * */
		SOCKET.on('new.client',function(data){
			var strDateSend = data.datesend;
			var dateSend = new Date(strDateSend);
			var hour = dateSend.getHours();
			var minute = dateSend.getMinutes();
			var timeSend = hour + ':' + minute;
			var id = data.clientid;
			var fullname = data.fullname;
			if(checkRoomClientExists(list_contact,id)==false){
				var box = getBoxContactOnline(prefix_contact,id,fullname,timeSend);
				$("#contact").append(box);	
				var item ={
						'fullname' : fullname,
						'clientid' : id,
						'time_send' : timeSend,
						'LASTTIME_FORCUS' : new Date(),
						'online' : true
				}
				list_contact.push(item);
			}
			else{
				$(".iconstatus_"+id).find('i').attr('class',fa_online);
				$(".status_"+id).text('Online');
				//reset status 
				for(var i = 0; i < list_contact.length; i++){
					if(list_contact[i].clientid == id){
						list_contact[i].LASTTIME_FORCUS = new Date();
						list_contact[i].online = true;
						break;
					}
				}
			}
			$("#"+ prefix_contact).find('.userbox').each(function(){
				if($(this).attr('client-id').trim()==id){
					$(this).removeClass('hover-contact-chat');
					return;
				}
			});	
		});
		
		/**
		 * Bắt sự kiện ngắt kết nối của Room từ server SOCKET: SOCKET.emit('disconnect.room',message);
		 * */
		SOCKET.on('disconnect.room',function(data){
			var clientid_o = data.clientid;
			if(clientid_o != null && clientid_o != '' && clientid_o != undefined){
				$(".iconstatus_"+ clientid_o).find('i').attr('class',fa_offline);
				$(".status_"+clientid_o).text('Offline');
				if(clientid_o == currentClientid){
					var box  = getBoxMessageSystem(CHAT_ALERT_CLIENT_LOGOUT);
					$("#contentChat").append(box).html();
					autoScrollDown();			
					//  disable form 
					$("#messageChat").prop('disabled',true);
					$("#btnSendMessage").prop('disabled',true);
					$("#assignUser").prop('disabled',true);
				}
				
				// đánh dấu, chuyển trạng thái cho user contact
				$("#user_" + clientid_o).addClass('offline');
				
//				$.cookie(clientid_o,'',{ expires: -1, path: '/' });
			}
		});
		
		/**
		 * Bắt sự kiện lấy lịch sử gần đầy từ server SOCKET: SOCKET.emit('list.recent.history.client',message);
		 * */
		SOCKET.on('list.recent.history.client',function(data){
			// list user	
			$("#recent").text('');
			if(data.list.length > 0){
				for(var i = 0; i < data.list.length; i++){
					var item = data.list[i];
					item['LASTTIME_FORCUS'] = new Date();
					item['online'] = item.is_online == 1 ? true : false;
					var id = item.clientid;
					var fullname = item.fullname;
					var timesend = item.time_send;
					var box = '';
					if(item.status == '0' || item.status == '-1'){
						box = getBoxContactOffline(prefix_recent,id,fullname,timesend);	
					}
					else{
						box = getBoxContactOnline(prefix_recent,id,fullname,timesend);
					}
					list_contact_history.push(item);
					$("#recent").append(box);
				}
			}
		});
		
		// Lấy thông tin client	
		SOCKET.on('information.client',function(data){
			// list user
			var item = data.item;
			var id = item.clientid;
			var fullname = item.fullname;
			var timesend = item.time_send;
			var nickname = item.gender != null ? item.gender : '';
			var phone = item.phone != null ? item.phone : '';
			var service = item.service != null ? item.service : '';
			
//			$("#infoUser").text(fullname);
//			$("#nickname").text(nickname);
//			$("#service").text(service);
//			$("#phone").text(phone);
		});
		
		SOCKET.on('list.usert.online.assign',function(data){
			if(data!=null && data.list.length > 0){		
				$("#sleUserOnline").text('');
				for(var i = 0; i<data.list.length; i++){
					var html = '<option value="' +data.list[i].username +'">'+ data.list[i].fullname + '</option>';
					$("#sleUserOnline").append(html)
				}
				$("#modalAssignUser").modal('show');
			}
			else{
				$("#modalNotFoundUserOnline").modal('show');
			}
		});
		
		SOCKET.on('assign.user',function(data){
			if(data != null && parseInt(data.rs) == 0){
				var box  = getBoxMessageSystem(CHAT_ALERT_CLIENT_ASSIGN_SUCESS);
				$("#contentChat").append(box).html();
				
				$("#messageChat").prop('disabled',true);
				$("#btnSendMessage").prop('disabled', true);
				$("#assignUser").prop('disabled', true);
				
				// xoa 				
				list_message = [];
//				$("#infoUser").text('');
//				$("#nickname").text('');
//				$("#service").text('');
//				$("#phone").text('');
//				
				removeUserOnContactList(currentClientid);
				
				currentClientid = '';
			}
			else{
				var box  = getBoxMessageSystem(CHAT_ALERT_CLIENT_ASSIGN_FAIL);
				$("#contentChat").append(box).html();
				autoScrollDown();
			}
		});
		
		SOCKET.on('key.press.message',function(data){
			if(data.type == '1'){
				if(data.clientid == currentClientid){
					$("#lbwaiting").removeClass("hidden");
					var fullname = data.fullname;
					var message = data.message;
					if(message.trim().length > 0){
						$("#lbwaiting").text(fullname + ' is typing ... : ' + message);
					}
					else{
						$("#lbwaiting").text('');
					}
				}else{
					$("#lbwaiting").addClass("hidden");
				}
			}
		});
		
		SOCKET.on('message.is.timeout.for.server',function(data){
			if(data != null){
				// client timeout
				if(data.type == '1'){
					var clientid_o = data.clientid;
					if(clientid_o != null && clientid_o != '' && clientid_o != undefined){
						$(".iconstatus_"+clientid_o).find('i').attr('class',fa_offline);
						$(".status_"+clientid_o).text('Offline');
						if(clientid_o == currentClientid){
//							var box  = getBoxMessageSystem(CHAT_ALERT_CLIENT_LOGOUT);
//							$("#contentChat").append(box).html();
//							autoScrollDown();
							//  disable form 
							$("#messageChat").prop('disabled',true);
							$("#btnSendMessage").prop('disabled', true);
							$("#assignUser").prop('disabled', true);
						}
						
						// đánh dấu, chuyển trạng thái cho user contact
						$("#user_" + clientid_o).addClass('offline');
					}
				}
			}
		});
		
		SOCKET.on('message.status.of.client',function(data){
			var clientid_o = data.clientid;
			if(clientid_o != null && clientid_o != '' && clientid_o != undefined){
				if(clientid_o == currentClientid){	
					if(data.status != null && data.status == '0'){
						$("#messageChat").prop('disabled',true);
						$(".iconstatus_"+clientid_o).find('i').attr('class',fa_offline);
						$(".status_"+clientid_o).text('Offline');
					}
					else{
						$("#messageChat").prop('disabled',false);
						$(".iconstatus_"+clientid_o).find('i').attr('class',fa_online);
						$(".status_"+clientid_o).text('Online');
					}
				}
			}
		});
		
	}
	
	/**
	 * Chỉ định agent chat
	 * */
	$("#assignUser").click(function(){
		if(currentClientid != null && currentClientid != ''){
//			if(checkCurrentClientOnline()){
				var message = {
					'clientid' 	: currentClientid,
					'agent'		: AGENT_CHAT
				}
				SOCKET.emit('get.list.usert.online.assign',message);
//			}else{
//				alert(CHAT_ALERT_CLIENT_LOGOUT);
//				return;
//			}
		}
		
	});
	
	/**
	 * Chấp nhận chỉ định cho agent để chat
	 * */
	$("#acceptAssign").click(function(){
		var agent = $("#sleUserOnline").val();
		var assign = $("#userLogin").val();
		var message = {
				'clientid' : currentClientid,
				'assign' : assign,
				'agent' : agent
		}
		SOCKET.emit('assign.user.user',message);
		$("#modalAssignUser").modal('hide');
	});
	
	/**
	 * Sự kiện quay lại
	 * */
	$('#cancel,.backlist').on('click', function (event) {
		event.preventDefault();
		var url = BASE_URL + "chat/list";
//		//disconnect to user 
//		var message = {
//				'clientid' : currentClientid, // id client
//				'agent' : AGENT_CHAT
//		}	
//		list_message = [];
//		$("#infoUser").text('');
//		$("#contentChat").text('');
//		sendMail(currentClientid);
//		currentClientid = '';		
//	    SOCKET.emit('close.connection', message);	
		// Redirect to page list
//		ajaxRedirect(url);
	});
	
	function sendMail(clientid){
		$.ajax({  	
	        type  : "POST",
	        data : {'clientid' : clientid},
	        url   : BASE_URL + "chat/send-mail",
	        success: function (data) {	
	        		        	
	        }
	    });	
	}
	
	function reconnect2Server(){
		var message = {
				'type': TYPECHAT,
				'agent' : AGENT_CHAT, // room server,
				'roleAdmin' : isRoleAdmin, 
				'message':'This is server',
				'agentName': $("#agentFullName").text()
		}
		
		SOCKET.emit('join', message);	
		getListClientWaiting();
		// 
		if(currentClientid != undefined && currentClientid != null && currentClientid != ''){
			chat2Client(currentClientid);
		}
	}
	
	SOCKET.on('connect_error', function(err) {
		isConnect = false;
		checkConnect(false);
	});
	
	SOCKET.on('connect', function(err) {
		isConnect = true;
		checkConnect(true);
	});
	
	SOCKET.on('disconnect', function(err) {
		isConnect = false;
		checkConnect(false);
	});
	
	// Kiểmt tra kết nối	
	function checkConnect(status){
		if(status == true){
			$("#contentChat").css('display','block');
			$("#contentDisconnted").css('display','none');
			reconnect2Server();
		}
		else{
			$("#contentChat").css('display','none');
			$("#contentDisconnted").css('display','block');
		}
	}
	
	
	function doAction(element){
		var clientid = $(element).attr('client-id');
		currentClientid = clientid;
		$("#lbwaiting").text('');
		// Thêm khung màu
		$("#"+ prefix_contact).find('.userbox').each(function(){
			if($(this).attr('client-id').trim() == currentClientid){
				$(this).attr('style', "background: #ff13229c;");
				
				$("#lbwaiting").addClass("hidden");
			}else{
				$(this).removeAttr('style');
				
				$("#lbwaiting").removeClass("hidden");
			}
		});

		$.ajax({
			type	:	'POST',
			url		:	BASE_URL + "chat/ajaxSupportUserInfo",
			data	:{
				clientid	:	clientid
			},
			global : false
		}).done(function(data){
			$("#userInfoForm").html(data);
		});
		
		chat2Client(clientid);
	}
	
	function showNotify(title, message) {
		function doNotification() {
			var notification = new Notify(title, {
				body : message,
				tag : title,
				timeout : 2,
				notifyClick : whenClickOnNotification,
				timeout : 5,
				icon : 'static/images/icon-default.png'
			});
			if (!("Notification" in window)) {
				alert("This browser does not support desktop notification");
			} else if (Notification.permission === "granted") {
				notification.show();
			} else if (Notification.permission !== 'denied') {
				Notification.requestPermission(function(permission) {
					if (!('permission' in Notification)) {
						Notification.permission = permission;
					}
					if (permission === "denied") {
						Notification.requestPermission();
					}
					if (permission === "granted") {
						notification.show();
					}
				});
			}
		}
		doNotification();
	}

	function whenClickOnNotification() {
		window.focus();
		// Nếu chưa hiện ra khung để chat thì hiện lên rồi focus vào chỗ chat 
		if (IS_DISPLAY_BOX_CHAT == false) {
			$("#content_chat").slideToggle("slow", function() {
				$("#modal-open-box-chat").css('display', 'none');
				$('#messageChat').focus();
			});
			IS_DISPLAY_BOX_CHAT = true;
		} else {
			$('#messageChat').focus();
		}
	}
	
	function setMessageForContent(data){
		if(data.length > 0){
			list_message = [];
			$("#contentChat").text('');
			
			var boxMess = 'box-mess-user';
			var boxMess1 = 'box-mess-user-1';
			
			var boxMessTmp = 'box-mess-user';
			var agentTmp= "";
			
			for(var i = 0; i < data.length; i++){
				var box = '';
				var item = data[i];
				var fullname = item.fullname;
				var messageid = item.messageid;
				var message = item.message;
				var timesend = item.timeSend;
				var oldAgent = item.oldAgent;
				var oldAgentName = item.oldAgentName;
				
				if(item.type == '1'){ // client
					if(item.typeMessage == '1'){// text
						box = getBoxMessageClient(fullname, timesend, messageid, message);
					}else{ // image
						var filename = BASE_URL + "chat/download?fileName=" + item.message;
						box = getBoxMessageImageClient(fullname, timesend, messageid, filename);
					}
				} else if(item.type == '2'){ // server
					if(item.typeMessage == '1'){ // text
						
						if (agentTmp != oldAgent) {
							agentTmp = oldAgent;
							
							boxMessTmp = boxMessTmp == boxMess1 ? boxMess : boxMess1;
						}
						
						box = getBoxMessageUser(timesend, messageid, message, oldAgentName, boxMessTmp, oldAgent);
					} else{ // image
						var filename = BASE_URL + "chat/download?fileName=" + item.message;
						var box = getBoxMessageImageUser(timesend, messageid, filename);
					}	
				}
				if(!checkMessageExists(messageid)){
					list_message.push(messageid);
					$("#contentChat").append(box).html();
				}
			}
		}
		
		var box  = getBoxMessageSystem(CHAT_ALERT_CLIENT_LOGOUT);
		$("#contentChat").append(box).html();
		
		autoScrollDown();
	}
	
	function saveUnreadMessage(clientid, number){
		$.ajax({
			type: "POST",
			url : BASE_URL + "chat/saveUnreadMessage",
			global: false,
			data : {
				clientid: clientid,
				number: number
			}
		}).done(function(){
			
		});
	}
	
	function getUnreadMessage(clientid){
		$.ajax({  	
	        type  : "GET",
	        data : {'clientid' : clientid},
	        global : false,
	        url   : BASE_URL + "chat/getUnreadMessage",
	    }).done(function(result){
	    	var data = JSON.parse(result);
	    	if (data != null && data != undefined){
	    		if (data.numberUnread != null && data.numberUnread != 0){
		    		$("#numberMessage_" + clientid).text(data.numberUnread);
		    		
		    		$("#unreadMessage_" + clientid).css('display', '');
	    		}
	    		
	    		if (data.message != null){
	    			$("#lastMessage_" + clientid).text(data.message);
	    		}
	    		
	    		if (data.timeSend != null && data.timeSend != ""){
	    			$("#timesend_" + clientid).text(data.timeSend);
	    		}
	    	}
	    });	
	}
})