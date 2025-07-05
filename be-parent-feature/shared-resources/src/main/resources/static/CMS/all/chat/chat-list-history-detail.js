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
	var prefix_history = 'contactHistory';
	var isConnect = false;
	var list_contact = [];
	var list_contact_history = [];
	var list_message = [];	
	var checkCloseX = 0;	
	
	var AGENT_SUPPORT = $("#agent").val();
	
	var AGENT_FULLNAME = $("#agentFullName").val();
	
	var LIST_CONTACT = getListContactByJSON(LIST_CONTACT_JSON);
	
	var LIST_CONTACT_HISTORY = getListContactByJSON(LIST_CONTACT_HISTORY_JSON);
	
	$("#tabchat li").bind('click',function(e){
		
		 var tabactive = $(".tab-content ").find('.active').attr('id');
		 
		 var link = $(this).find('a').attr('href');
		 
		 link = link.replace('#','');
		 
		 if(link == 'menuHistory' &&  tabactive != 'menuHistory'){
			if ($('#menuHistory .userbox').first().attr("id") != 'user_@clientid'){
				$('#menuHistory .userbox').first().trigger('click');
			}
			
			return;
		 }else{
			if ($('#menuToday .userbox').first().attr("id") != 'user_@clientid'){
				$('#menuToday .userbox').first().trigger('click');
			}
			return;
		 }
	});
	
	function getBoxContactOffline(prefix,clientid,fullname,time){
		var box = $("#box_contact_offline").html();
		if(box == undefined){
			return ;
		}
		box = box.replace(/\@id/g,prefix+''+clientid);
		box = box.replace(/\@clientid/g,clientid);
		box = box.replace('@fullname',fullname);
		box = box.replace('@timesend',time);
		
		var sortName = fullname.charAt(0).toUpperCase();
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
		
		var dateStr = getFormatDate(time);
		
		box = box.replace(/\@id/g,prefix+''+ clientid);
		box = box.replace(/\@clientid/g, clientid);
		box = box.replace('@fullname', fullname);
		box = box.replace('@timesend', dateStr);
		
		var sortName = fullname.charAt(0).toUpperCase();
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
	function getBoxMessageUser(time, messageid, message, boxMessTmp, oldAgentName){
		var box = $("#box_message_user").html();
		
		if(box == undefined){
			return ;
		}
		box = box.replace('@box-mess-user', boxMessTmp);
		box = box.replace('@agentName', oldAgentName);
		box = box.replace('@messageid', messageid);
		box = box.replace('@message', message);
		box = box.replace('@timesend', time);
		
		var sortName = getSortName(oldAgentName);
		
		box = box.replace('@avatarName', sortName);
		
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
		
		// Lấy lịch sử tin nhắn
		$.ajax({  	
	        type  : "GET",
	        data : {'clientid' : clientid},
	        url   : BASE_URL + "chat/getMessageClient",
	    }).done(function(data){
	    	
	    	setMessageForContent(data);
	    	
	    });
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
	
	/**
	 * Lấy thông tin của client
	 * */
	function getInfoClient(clientid){
		$.ajax({  	
	        type  : "GET",
	        data : {'clientid' : clientid},
	        url   : BASE_URL + "chat/getInfoUser",
	    }).done(function(item){
	    	
			var id = item.clientid;
			var fullname = item.fullname;
			
			var timesend = getFormatDate(item.createdDate);
			
			$("#infoUser").text(fullname);
			$("#timeChated").text("Chat started, " + timesend);
	    });	
	}
	
	// auto scroll down
	function autoScrollDown(){
		var flagAlerBox = $("#chatFlag").val();
		if(flagAlerBox != undefined){
			$('#contentChat').animate({
				scrollTop: $('#contentChat')[0].scrollHeight
			}, 3000);
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
	
	//
	$(document).contextmenu(function() {
	    return false;
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
		var checkToday = false;
		if (LIST_CONTACT.length > 0){
			for(var i = 0; i < LIST_CONTACT.length; i++){
				var item = LIST_CONTACT[i];
				item['LASTTIME_FORCUS'] = new Date();
				item['online'] = item.is_online == 1 ? true : false;
				var id = item.clientid;
				if (id == clientidRef){
					checkToday = true;
				}
				var fullname = item.fullname;
				var timesend = item.disconnectedDate;
				if(checkRoomClientExists(list_contact, id)==false){
					var box = getBoxContactOnline(prefix_contact, id, fullname, timesend);
					$("#contact").append(box).html();	
					list_contact.push(item);
				}
			}
			
			// Chọn vào phần tử clientidRef trong danh sách liên hệ
			if (clientidRef != 'user_@clientid'){
				$('#user_' + clientidRef).trigger('click');
			}
		}
		
		if (LIST_CONTACT_HISTORY.length > 0){
			for(var i = 0; i < LIST_CONTACT_HISTORY.length; i++){
				var item = LIST_CONTACT_HISTORY[i];
				item['LASTTIME_FORCUS'] = new Date();
				item['online'] = item.is_online == 1 ? true : false;
				var id = item.clientid;
				var fullname = item.fullname;
				var timesend = item.disconnectedDate;
				if(checkRoomClientExists(list_contact_history, id)==false){
					var box = getBoxContactOnline(prefix_contact, id, fullname, timesend);
					$("#contactHistory").append(box).html();	
					list_contact_history.push(item);
				}
			}
		}
		
		if (LIST_CONTACT.length <= 0 || !checkToday){
			$("#today").removeClass("active");
			$("#menuToday").removeClass("active");
			
			$("#history").addClass("active");
			$("#menuHistory").addClass("active");
			$("#menuHistory").addClass("in");
			
			if (LIST_CONTACT_HISTORY.length > 0){
				for(var i = 0; i < LIST_CONTACT_HISTORY.length; i++){
					var item = LIST_CONTACT_HISTORY[i];
					item['LASTTIME_FORCUS'] = new Date();
					item['online'] = item.is_online == 1 ? true : false;
					var id = item.clientid;
					var fullname = item.fullname;
					var timesend = item.disconnectedDate;
					if(checkRoomClientExists(list_contact_history, id)==false){
						var box = getBoxContactOnline(prefix_contact, id, fullname, timesend);
						$("#contactHistory").append(box).html();	
						list_contact_history.push(item);
					}
				}
				
				// Chọn vào phần tử clientidRef trong danh sách liên hệ
				if (clientidRef != 'user_@clientid'){
					$('#user_' + clientidRef).trigger('click');
				}
			}
			
			return;
		}
		
	}
	
	/**
	 * Sự kiện quay lại
	 * */
	$('#cancel,.backlist').on('click', function (event) {
		event.preventDefault();
		var url = BASE_URL + "chat/list";
	});
	
	// Kiểm tra kết nối	
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
		
		// Thêm khung màu
		$('.userbox').each(function(){
			if($(this).attr('client-id').trim() == currentClientid){
				$(this).attr('style', "background: #e0e0e0");
				
				$("#lbwaiting").addClass("hidden");
			}else{
				$(this).removeAttr('style');
				
				$("#lbwaiting").removeClass("hidden");
			}
		});

		chat2Client(clientid);
	}
	
	function getListContactByJSON(LIST_CONTACT_JSON){
		return JSON.parse(LIST_CONTACT_JSON);
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
						
						box = getBoxMessageUser(timesend, messageid, message, boxMessTmp, oldAgentName);
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
		autoScrollDown();
	}
})