$(document).keypress(function(event){

    var keycode = (event.keyCode ? event.keyCode : event.which);
    if(keycode == '13'){
    	onClickSearch(this, event);
    }
});

$(document).ready(function($) {
	
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

function onClickSearch(element, event) {
	
	setDataSearchHidd();
	
	ajaxSearch("chat/ajaxList", setConditionSearch(), "tableUserOnline", element, event);
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