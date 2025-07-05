$(function(){
	if( SOCKET == null){
		return;
	}	
	//remove notify
	
	//$("#imgAccount").attr("src", BASE_URL + "ajax/download?fileName=account_avatar/30638__1494313046790.jpg");
	/*SOCKET in common_unit.js*/
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
	getListClientWaiting();		
	function getListClientWaiting(){
		var message = {			
			'agent' : AGENT_CHAT		
			
		}	
		SOCKET.emit('list.client.waiting', message);
	}	
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
						    //removeUserOnContactList(clientid);						    
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
		return box;
	}
	function getBoxContactOnline(prefix,clientid,fullname,time){
		var box = $("#box_contact_online").html();
		if(box == undefined){
			return ;
		}
		box = box.replace(/\@id/g,prefix+''+clientid);
		box = box.replace(/\@clientid/g,clientid);
		box = box.replace('@fullname',fullname);
		box = box.replace('@timesend',time);
		return box;
	}
	function getBoxMessageClient(fullname,time,messageid,message){
		var box = $("#box_message_client").html();
		if(box == undefined){
			return ;
		}
		box = box.replace('@fullname',fullname);
		box = box.replace('@messageid',messageid);
		box = box.replace('@message',message);
		box = box.replace('@timesend',time);
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
		return box;
	}
	function getBoxMessageUser(time,messageid,message){		
		var box = $("#box_message_user").html();
		if(box == undefined){
			return ;
		}
		box = box.replace('@messageid',messageid);
		box = box.replace('@message',message);
		box = box.replace('@timesend',time);
		return box;
	}
	function getBoxMessageImageUser(time,messageid,filename){
		var box = $("#box_message_user_2").html();
		if(box == undefined){
			return ;
		}
		var imgInput = '<img src = "@srcimage" style="width:200px;height:100px;float:right;" />';
		imgInput = imgInput.replace('@srcimage',filename);
		box = box.replace('@messageid',messageid);
		box = box.replace('@image',imgInput);
		box = box.replace('@timesend',time);
		return box;
	}
	function getBoxMessageSystem(message){
		var box = $("#box_message_system").html();
		if(box == undefined){
			return ;
		}
		box = box.replace('@message',message);
		return box;
	}	
		
	$(document).unbind('.userbox').on('click', '.userbox', function(e) {
		var clientid = $(this).attr('client-id');
		currentClientid = clientid;
		
		chat2Client(clientid);
		
				
	});
	function chat2Client(clientid){
		getHistoryMessage(clientid)
		// connect room client								
		var message = {
			'type': TYPECHAT,
			'clientid' : clientid,
			'agent':AGENT_CHAT
		}	
	    SOCKET.emit('join.room.client.and.get.list.messsage', message);
	}
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
	$(document).on('contextmenu', '.user', function(e) {
		var tab = $(".tab-content ").find('.active').attr('id');
		var clientid = $(this).attr('client-id');
    	
		if(tab=='contact'){
			$("#menu-control").find('.moveuser').css('display','none');
			$("#menu-control").find('.closeuser').css('display','block');
		}  
		else if(tab=='recent'){
			$("#menu-control").find('.moveuser').css('display','none');
			$("#menu-control").find('.closeuser').css('display','none');
			// don't display popup control
			return;
		} 
		else if(tab=='waiting'){
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
	$(".moveuser").click(function(){
		var clientid = $(this).attr('client-id');
		var tab = $(".tab-content ").find('.active').attr('id');
		if(tab=='contact'){
			$('#tabchat a[href="#recent"]').tab('show');
		}  
		else if(tab=='recent'){
		} 
		else if(tab=='waiting'){
			$('#tabchat a[href="#contact"]').tab('show');
		} 
	})
	// close user tab
	$(".closeuser").click(function(){
		var clientid = $(this).attr('client-id');
		var message = {
				'clientid' : clientid, // id client
				'agent' : AGENT_CHAT
		}	
		if(clientid == currentClientid){
			list_message = [];
			$("#infoUser").text('');
			$("#contentChat").text('');
			currentClientid = '';
			sendMail(clientid);
		}			
	    SOCKET.emit('close.connection', message);
	    removeUserOnContactList(clientid);
	});
	//event right click message
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
	//
	$(".defaultMessage").click(function(e){		
		$("#messageChat").val(MSG_DEFAULT);
		sendMessage();
	});
	//
	function getInfoClient(clientid){
		var message = {
				'clientid' : clientid,
				'agent':AGENT_CHAT
		}	
		SOCKET.emit('information.client', message);
	}	
	$("#uploadFile").click(function(){
		if(currentClientid == '' || currentClientid == null){
			alert(ALERT_CHOOSE_CUSTOMER);
			return;
		}
		$("#fileAttach").click();
	})
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
	$("#btnSendMessage").click(function(){
		var message = $("#messageChat").val();
		if(message.length > 0){
			sendMessage();
			$("#messageChat").val('');	
		}
	})
	function checkCurrentClientOnline(){		
		if(currentClientid == '' || currentClientid == null){			
			return false;
		}	
		
		for(var i = 0; i < list_contact.length; i++){
			if(list_contact[i].clientid  == currentClientid){
				if(list_contact[i].ONLINE == true){
					return true;
				}
			}
		}
		return false;
	}
	function sendMessage(){
//		if(currentClientid == '' || currentClientid == null){
//			alert(ALERT_CHOOSE_CUSTOMER);
//			return;
//		}
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
				'typeMessage':typeMessageText
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
	$('#fileAttach').fileupload(
	{
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
		 $("#menu-control-message").css({			
			display : 'none'
		 });
		 //e.preventDefault();
	});
	$("#tabchat li").bind('click',function(e){
		 var tabactive = $(".tab-content ").find('.active').attr('id');
		 var link = $(this).find('a').attr('href');
		 link = link.replace('#','');
		 if(link == 'recent' &&  tabactive != 'recent'){			 
			 //get list history
			 var message = {
				'agent' : AGENT_CHAT
			 }			
			 SOCKET.emit('list.recent.history.client', message);
			 return;
		 }
	});
	
	function checkMessageExists(id){
		for(var i = 0;i<list_message.lenght; i++){
			if(list_message[i] == id){
				return true;
			}
		}
		return false;
	}
	if(ISLOAD_PAGE_CHAT==false){
		ISLOAD_PAGE_CHAT = true;
		SOCKET.on('reply.server',function(data){		
			// list user online		
			list_contact = [];
			$("#contact").text('');
			if(data.list.length > 0){
				for(var i = 0; i < data.list.length; i++){
					var item = data.list[i];
					item['LASTTIME_FORCUS'] = new Date();
					item['ONLINE'] = item.is_online == 1 ? true : false;
					var id = item.clientid;
					var fullname = item.fullname;
					var timesend = item.time_send;
					if(checkRoomClientExists(list_contact,id)==false){
						var box = getBoxContactOnline(prefix_contact,id,fullname,timesend);
						$("#contact").append(box).html();	
						list_contact.push(item);						
					}				
				}			
			}		
		});
		SOCKET.on('reply.server.after.close.connection.user',function(data){		
						
			if(data.list.length > 0){
				for(var i = 0; i < data.list.length; i++){
					var item = data.list[i];
					item['LASTTIME_FORCUS'] = new Date();
					item['ONLINE'] = item.is_online == 1 ? true : false;
					var id = item.clientid;
					var fullname = item.fullname;
					var timesend = item.time_send;
					if(checkRoomClientExists(list_contact,id)==false){
						var box = getBoxContactOnline(prefix_contact,id,fullname,timesend);
						$("#contact").append(box).html();	
						list_contact.push(item);						
					}				
				}			
			}		
		});
		// get list message 
		SOCKET.on('list.message',function(data){
			if(data.clientid == currentClientid){
				if(data.list.length > 0){
					list_message = [];
					$("#contentChat").text('');
					for(var i = 0; i < data.list.length; i++){
						var box = '';
						var item = data.list[i];
						var fullname = item.fullname;
						var messageid = item.messageid;
						var message = item.message;
						var timesend = item.time_send;
						if(item.type == '1'){ // client
							
							if(item.type_message == '1') // text
							{
								box = getBoxMessageClient(fullname,timesend,messageid,message);
							}
							else{ // image
								var filename = BASE_URL + "chat/download?fileName=" + item.message;
								box = getBoxMessageImageClient(fullname,timesend,messageid,filename);
							}						
						}
						else if(item.type == '2'){ // server
							if(item.type_message == '1') // text
							{
								box = getBoxMessageUser(timesend,messageid,message);	
							}
							else{ // image
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
				if(data.clientid != currentClientid){		
					$("#"+ prefix_contact).find('.userbox').each(function(){
						if($(this).attr('client-id').trim()==data.clientid){
							$(this).addClass('hover-contact-chat');
							return;
						}
					});					
				}
				else{
					$("#lbwaiting").text('');
					if(data.typeMessage == '1') // text
					{
						var strDateSend = data.dateSend;
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
					}
					else{ // image
						var strDateSend = data.dateSend;
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
					var strDateSend = data.dateSend;
					var dateSend = new Date(strDateSend);
					var hour = dateSend.getHours();
					var minute = dateSend.getMinutes();
					var timeSend = hour + ':' + minute;
					var message = data.message;
					var messageid = data.messageid;
					var box = getBoxMessageUser(timeSend,messageid,message);
					if(!checkMessageExists(messageid)){
						list_message.push(messageid);
						$("#contentChat").append(box).html();	
					}
				}
				else{ // image
					
					var strDateSend = data.dateSend;
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
		SOCKET.on('new.client',function(data){
			var strDateSend = data.dateSend;
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
						'ONLINE' : true
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
						list_contact[i].ONLINE = true;
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
		SOCKET.on('disconnect.room',function(data){
			var clientid_o = data.clientid;
			if(clientid_o != null && clientid_o != '' && clientid_o != undefined){
				$(".iconstatus_"+clientid_o).find('i').attr('class',fa_offline);
				$(".status_"+clientid_o).text('Offline');
				if(clientid_o == currentClientid){
					var box  = getBoxMessageSystem(CHAT_ALERT_CLIENT_LOGOUT);
					$("#contentChat").append(box).html();
					autoScrollDown();			
					//  disable form 
					$("#messageChat").prop('disabled',true);				
				}				
			}
		});
		SOCKET.on('list.recent.history.client',function(data){		
			// list user	
			$("#recent").text('');
			if(data.list.length > 0){
				for(var i = 0; i < data.list.length; i++){
					var item = data.list[i];
					item['LASTTIME_FORCUS'] = new Date();
					item['ONLINE'] = item.is_online == 1 ? true : false;					
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
		SOCKET.on('information.client',function(data){		
			// list user
			var item = data.item;
			var id = item.clientid;
			var fullname = item.fullname;
			var timesend = item.time_send;
			var box  = '';
			if(item.status == '1' || item.status == '2') // online
			{
				box = getBoxContactOnline(prefix_title,id,fullname,timesend);	
			}
			else{ //offline
				box = getBoxContactOffline(prefix_title,id,fullname,timesend);
			}		
			
			$("#infoUser").html(box);
		});
		
		SOCKET.on('list.usert.online.assign',function(data){
			if(data!=null && data.list.length > 0){		
				$("#sleUserOnline").text('');
				for(var i = 0; i<data.list.length; i++){
					var html = '<option value="'+data.list[i].username+'">'+data.list[i].fullname+'</option>';
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
				$("#contentChat").html(box);
				// xoa 				
				list_message = [];
				$("#infoUser").text('');				
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
					var fullname = data.fullname;
					var message = data.message;
					if(message.trim().length > 0){
						$("#lbwaiting").text(fullname + ' is typing ...');
					}
					else{
						$("#lbwaiting").text('');
					}
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
							var box  = getBoxMessageSystem(CHAT_ALERT_CLIENT_LOGOUT);
							$("#contentChat").append(box).html();
							autoScrollDown();				
							//  disable form 
							$("#messageChat").prop('disabled',true);					
						}
						
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
	$("#assignUser").click(function(){
		if(currentClientid != null && currentClientid != ''){
			if(checkCurrentClientOnline()){
				var message = {
						'clientid' : currentClientid
				}
				SOCKET.emit('get.list.usert.online.assign',message);	
			}
			else{
				alert(CHAT_ALERT_CLIENT_LOGOUT);
				return;
			}
			
				
		}
		
	});
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
		ajaxRedirect(url);
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
				'message':'This is server'
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
})
