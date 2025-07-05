var selectedObj = null;

$(document).ready(function() {
	// check all access
	$('.check-all-access').on('click', function() {
		var checked = $(this).is(':checked');
        $(this).closest('table').find('.check-access').prop('checked', checked);
    });
	/*
	$("#formFieldName").select2({
		placeholder : " ",
		allowClear: true,
		minimumInputLength : 0,
		dropdownParent: $("#param-modal .modal-content")
	});*/
	
	$('.check-all-access').prop('checked', isCheckAll());
	
	$('.check-access').on('click', function() {
		$('.check-all-access').prop('checked', isCheckAll());
    });
	
	$(".sign-type-input").on("click", function(){
		$(this).prop('checked', false);
		var value = $(this).data("value");
		$(selectedObj).val(value);
		$("#sign-type-chooser").modal("hide");
	});
	
	$(".btn-show-sign-type-chooser").on("click", function(){
		selectedObj = $(this).closest(".input-group").find("input:first-child");
	});
	
	$("#sign-type-chooser").on("hidden.bs.modal", function(){
		
	});
});

function isCheckAll(){
	var checkAll = true;
	$('#table-param-config-step > tbody').find('tr').each(function(e){
		var checked = $(this).find('.check-access').is(':checked');
		if(!checked){
			checkAll = false;
			return false; 
		}
	});
	return checkAll;
}