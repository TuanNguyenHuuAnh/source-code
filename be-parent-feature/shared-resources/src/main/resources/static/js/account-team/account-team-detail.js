$(document).ready(function($) {
	ajaxGetUserList();
	$('#form-team-edit').on('keyup keypress', function(e) {
		  var keyCode = e.keyCode || e.which;
		  if (keyCode === 13) { 
		    e.preventDefault();
		    return false;
		  }
		});
	//on click list
	$('#linkList').on('click', function (event) {
		event.preventDefault();
		var url = BASE_URL + "account-team/list";

		// Redirect to page list
		ajaxRedirect(url);
	});
	
	//on click list
	$('#btnCancel').on('click', function (event) {
		event.preventDefault();
		var url = BASE_URL + "account-team/list";

		// Redirect to page list
		ajaxRedirect(url);
	});
	
	//search user
	$('.btnSearch').on('click', function(event) {
		ajaxGetUserList();
	});
	
	
});


function ajaxGetUserList() {
	$.ajax({
		  async : false,
	      type  : "GET",
	      url   : BASE_URL + "account-team/get-user-for-team-detail",
	      data  : setConditionSearchUser(),
	      success: function( data ) {
	          $("#userList").html(data);
	      }
	});
}


function setConditionSearchUser(){
	var condition = {};
	condition["keySearch"] = $("#keySearch").val();
	condition["companyId"] = $("#companyId").val();
	condition["teamId"] = $("#teamId").val();
	console.log(condition);
	return condition;
}