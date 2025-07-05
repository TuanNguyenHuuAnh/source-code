$(document).ready(function() {
	ajaxGetUserList();

	$('.datepicker').datepickerUnit({
		  format: DATE_FORMAT,
		        changeMonth: true,
		        changeYear: true,
		        autoclose: true,
				language : APP_LOCALE.toLowerCase(),
		        keyboardNavigation : true
		 });
	

	//on click cancel
	$('#btnCancel').on('click', function (event) {
		event.preventDefault();
		var url = BASE_URL + "account-team/list";
		
		// Redirect to page list
		ajaxRedirect(url);
	});
	
	//on click list
	$('#linkList').on('click', function (event) {
		event.preventDefault();
		var url = BASE_URL + "account-team/list";

		// Redirect to page list
		ajaxRedirect(url);
	});
	
	// on click add
	$("#add").on("click", function(event) {
		var url = BASE_URL + "account-team/edit";
		// Redirect to page add
		ajaxRedirect(url);
	});	
	// Post edit save 
	$('.btnSave').on('click', function(event) {
		if ($(".j-form-validate").valid()) {
			var url = "account-team/edit";
			var condition = $("#form-team-edit").serialize();
			console.log(condition);
			ajaxSubmit(url, condition, event);
			
		}			
	});
	
	//toggle add user div
	$('.btn-toggle-add-user').click(function(){
		  $('.btn-toggle-add-user i').toggleClass('fa-plus fa-compress');
	});
});

function onClickSearch(event) {
	ajaxGetUserList();
}

function ajaxGetUserList() {
	$.ajax({
		  async : false,
	      type  : "GET",
	      url   : BASE_URL + "account-team/get-user-for-team",
	      data  : setConditionSearchUser(),
	      success: function( data ) {
	          $("#userList").html(data);
	      }
	});
}

function getListDeleted(){
	var deleteList = [];
	$('#listAccount tbody tr').each(function(key, val) {
		 var row = $(this);
		 if(row.hasClass("item-tr-data")){
			 if(row.find(".check-delete").is(":checked") == true){
				 deleteList.push(row.find(".check-delete").val());
			 }
		 }
	 });
	return deleteList;
}

function setConditionSearchUser(){
	var condition = {};
	condition["keySearch"] = $("#keySearch").val();
	condition["companyId"] = $("#companyId").val();
	condition["teamId"] = $("#teamId").val();
	console.log(condition);
	return condition;
}