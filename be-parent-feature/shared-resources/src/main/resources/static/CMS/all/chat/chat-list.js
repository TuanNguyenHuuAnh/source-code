var typeSearch = 1;

var clickChat = 1;

$(document).keypress(function(event){

    var keycode = (event.keyCode ? event.keyCode : event.which);
    if(keycode == '13'){
    	onClickSearch(this, event, typeSearch);
    }
});

$(document).ready(function($) {
	//SOCKET.removeListener('admin.join.room.client');
	// datatable load
	$(".unit-bg-table").datatables({
		url : BASE_URL + 'chat/ajaxList',
		type : 'POST',
		setData : setConditionSearch
	});
	
	$('select[multiple]').multiselect({
		columns : 1,
		placeholder : SEARCH_LABEL,
		search : true
	});
	
	$(".j-btn-chat-edit").on("click", function(event) {
		event.preventDefault();		
		var url = BASE_URL + "chat/edit";		
		window.location.href = url;
	});
	
	//on click detail
	$("#tableListForChat").on('click', '.j-btn-detail', function(event) {
		event.preventDefault();
		var row = $(this).parents("tr");
		
		var agent = row.find(".agent-hidden").text();
		
		if (agent != undefined){
			var url = BASE_URL + "chat/history/detail?agent=" + agent;
			redirect(url);
		}
	});
	
	// on click search
	$("#btnSearch").on('click', function(event) {
		onClickSearch(this, event, typeSearch);
	});
	
	// refesh click
	$("#btnRefresh").unbind('click').bind('click', function(event) {
		refresh();
	});

	$(".j_branchname").each(function(){
		var ele = $(this).text();
		$(this).html(ele);
	});
	var clientidCurrent  = '';
	
	//chat
	var eventCurrent;
	$("#tableListForChat").on('click', '.j-btn-chat', function(event) {
		event.preventDefault();
		clickChat = 1;
		var row = $(this).parents("tr");
		var id = row.data("clientid");	
		var user = $("#userLogin").val();
		var fullname = $("#fullnameSupport").val();
		
		var message = {
				'clientid' : id,
				'agent' : user,
				'agentName': fullname
		}
		//SOCKET.once('admin.join.room.client', function(){});
		SOCKET.emit('admin.join.room.client', message);
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
	
	SOCKET.on('admin.join.room.client', function(data) {
		if(data != null && parseInt(data.rs) == 0 && clickChat == 1){
			
			$("#btnSearch").trigger('click');
			
			var url = BASE_URL + "chat/support";
			
			var clientId = data.clientid;
			
			if (clientId != undefined && clientId != null && clientId != '') {
				url = url + "?clientid=" + clientId;
			}
//			var win = window.open(url);
//			win.opener.reload();
			
			var myWindow = window.open(url, "chatWindow");
		}		

		clickChat = 2;
	});
	
	function setConditionSearchReload() {
		var condition = {};
		condition["fieldSearch"] = $("#fieldSearchHidd").val();
		condition["fieldValues"] = $("#fieldValuesHidd").val();
		condition["page"] = $("#chatCurrentPage").val();
		return condition;
	}
	
	function reloadCurrentPage(){
		eventCurrent.preventDefault();		
		setDataSearchHidd();
		ajaxSearch("chat/ajaxList", setConditionSearchReload(), "tableListForChat", this, event);
	}
	
	function getNewClient(){
		var isRunning = false;
		setInterval(function(){ 
			if(isRunning == false){
				isRunning = true;
				var url = "chat/ajaxList";
				var condition = setConditionSearchReload();
				$.ajax({
					type : "POST",
					url : BASE_URL + url,
					data : condition,
					success : function(data) {
						$("#tableListForChat").html(data);
						isRunning = false;
					},
					complete : function(result) {
					},
					error : function(xhr, textStatus, error) {
						console.log(xhr);
						console.log(textStatus);
						console.log(error);
					}
				});
			} 
		}, 10 * 60 * 1000);
	}
	
	SOCKET.on('reload.client.list.for.admin', function(data){
		typeSearch = 0;
		
	    $("#btnSearch").trigger('click');
	    
		typeSearch = 1;
		
	});
});

function setConditionSearch() {
	var condition = {};
	condition["fieldSearch"] = $("#fieldSearchHidd").val();
	condition["fieldValues"] = $("#fieldValuesHidd").val();
	
	condition["fullname"] = $("#fullname").val();
	condition["email"] = $("#email").val();
	condition["phone"] = $("#phone").val();
	condition["service"] = $("#service").val();
	condition["agent"] = $("#agent").val();
	condition["status"] = $("#status").val();
	return condition;
}

function setDataSearchHidd() {
	$("#fieldSearchHidd").val($("#fieldSearch").val());
	$("#fieldValuesHidd").val($("select[name=fieldValues]").val());
}

function onClickSearch(element, event, type) {
	
	setDataSearchHidd();
	
	if (type == 1){
		ajaxSearch("chat/ajaxList", setConditionSearch(), "tableListForChat", element, event);
	}else{
		ajaxGobalSearch("chat/ajaxList", setConditionSearch(), "tableListForChat", element, event);
	}
}

function refresh() {
	$("#fullname").val('');
	$("#email").val('');
	$("#phone").val('');
	$("#service").val('');
	$("#agent").val('');
	$("#status").val('');
	
	$("#btnSearch").trigger('click');
}