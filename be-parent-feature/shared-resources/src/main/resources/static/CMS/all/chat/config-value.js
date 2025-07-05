$(document).ready(function($) {
	
	// tabLanguage click
	$('#tabLanguageSub a').click(function(e) {
		e.preventDefault();
		$(this).tab('show');
	});	
	function fnAfterInsertRow(){
		$(".j-setting-control-value-textbox-sync").keyup(function(e){			
			synDataOfValue(this);
		});
	}
	function synDataOfValue(ele){	
		var ischeck = $(ele).prop('checked');
		var name = $(ele).attr('name');
		var findex = name.indexOf('.');
		name = name.substring(findex,name.length);
		var value = $(ele).val();
		var fieldType = $(ele).attr('type');
		for(var i = 0 ;i < LANGUAGE_LIST.length;i++){
			if(fieldType == 'text'){
				var ele = 'controlValues[' + i + ']' + name;
				$("input[name='"+ele+"']").val(value);
			}
			else if(fieldType == 'checkbox'){
				var ele = 'controlValues[' + i + ']' + name;
				$("input[name='"+ele+"']").prop('checked',ischeck);
			}
			else if(fieldType == 'select'){
				var ele = 'controlValues[' + i + ']' + name;
				$("select[name='"+ele+"']").val(value);
			}
		}
	}
	// on click button save
	$('.add-control-value').on('click', function(event) {
		event.preventDefault();		
		if ($("#valueForm").valid()) {
			var url = "chat/config-add-value-field";
			var condition = $("#valueForm").serialize();
			$.ajax({
				type : "POST",
				url : BASE_URL + url,
				data : condition,
				success : function(data) {	
//					var content = $(data).find('.body-content');					
//					$(".main_content").html(content);
					var content = $(data).find('.body-content');				
					$("#modal-body").html(content);
					$("#modal-insert-value").modal('show');		
					//
					fnAfterInsertRow();
				},
				error : function(xhr, textStatus, error) {
					console.log(xhr);
					console.log(textStatus);
					console.log(error);
				}
			});	
		}		
		else {			
			checkLanguage();
		}
		
	});	
	
	
	$('#btnSaveValue').on('click', function(event) {
		event.preventDefault();		
		if ($("#valueForm").valid()) {
			generateFormToJson('valueForm', 'chat/generate-json-value-form', function(dataJsonValueFrm){
				bindingDataControlValue(dataJsonValueFrm);	
			})
		}		
		else {			
			checkLanguage();
		}
	});	
	function generateFormToJson(frm, url, callback){
		var settingForm = $("#" + frm).serialize();		
		$.ajax({
			type : "POST",
			dataType : 'json',
			url : BASE_URL + url,
			data : settingForm,
			success : function(data) {				
				callback(data);
			},
			error : function(xhr, textStatus, error) {
				console.log(xhr);
				console.log(textStatus);
				console.log(error);
			}
		});
	}	
	function checkLanguage(){
		$.each(LANGUAGE_LIST, function(key) {
			var errorServer = $("#languageSub"  + key).find(".has-error").length;
			var errorClient = false;

			$("#languageSub"  + key).find("label.error").each(function() {
				if ($(this).html().length > 0) {
					errorClient = true;
					return false;
				}
			});

			if (errorClient || errorServer > 0) {
				$('#tabLanguageSub a[href="#languageSub' + key +'"]').tab('show')
				return false;
			}
		});
	}
	$(".j-control-delete").on("click", function( event ) {
		deleteControlValue(this, event);
	});
	function deleteControlValue(element, event){
		event.preventDefault();
		
		// Prepare data
		var row = $(element).parents("tr");
		var id = row.data("row-value-id");
		
		popupConfirm( MSG_DEL_CONFIRM, function(result) {
			if (result) {
				generateFormToJson('valueForm', 'chat/generate-json-value-form', function(dataJsonValueFrm){
					var url = 'chat/config-delete-value-field';
					console.log('id: ' + id);
					$.ajax({
						type : "POST",
						url : BASE_URL + url,
						data : {							
							'id' : id,
							'dataJsonValue' : JSON.stringify(dataJsonValueFrm)
						},
						success : function(data) {				
							var content = $(data).find('.body-content');				
							$("#modal-body").html(content);
							$("#modal-insert-value").modal('show');	
						},
						error : function(xhr, textStatus, error) {
							console.log(xhr);
							console.log(textStatus);
							console.log(error);
						}
					});
				})				
			}		
		});
	}
	$('#btncancelValue').on('click', function(event) {
		event.preventDefault();		
		$("#modal-insert-value").modal('hide');
	});
});
