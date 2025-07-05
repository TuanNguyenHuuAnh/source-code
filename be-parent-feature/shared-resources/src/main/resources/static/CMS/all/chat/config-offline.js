$(document).ready(function($) {
	$('#cancel,.backlist,#linkList').on('click', function (event) {
		event.preventDefault();
		var url = BASE_URL + "chat/list-offline";
		
		// Redirect to page list
		ajaxRedirect(url);
	});
	// tabLanguage click
	$('#tabLanguage a').click(function(e) {
		e.preventDefault();
		$(this).tab('show');
	});
	
	$(".j-required-number").keypress(function(e){
		return isNumber(e,this);
	});
	
	$(".j-setting-chat-sync").keyup(function(e){
		synData(this);
	});
	$(".j-setting-chat-checkbox-sync").change(function(e){
		synData(this);
	});
	
	$('.datepicker').datepicker({
		format: DATE_FORMAT,
        changeMonth: true,
        changeYear: true,
        autoclose: true,
        keyboardNavigation : true,        
	});
	
	$.each(LANGUAGE_LIST, function(key, val) {
		var idEffectedDate = $("#fromDate" + key).val();
		var idExpiredDate = $("#toDate" + key).val();
		
		changeDatepicker(idEffectedDate, idExpiredDate);
	});
	
	clickEditButton();
	
	changeChatControl();
	
	clickDeleteButton();
	
	function synData(ele){
		var ischeck = $(ele).prop('checked');
		var name = $(ele).attr('name');
		var findex = name.indexOf('.');
		name = name.substring(findex,name.length);
		var value = $(ele).val();
		var fieldType = $(ele).attr('type');
		//visible button
		var nameSub = $(ele).attr('name');
		for(var i = 0 ;i < LANGUAGE_LIST.length;i++){
			if(fieldType == 'text'){
				var ele = 'listSettingLanguageChat[' + i + ']' + name;
				$("input[name='"+ele+"']").val(value);				
			}
			else if(fieldType == 'checkbox'){
				var ele = 'listSettingLanguageChat[' + i + ']' + name;
				$("input[name='"+ele+"']").prop('checked',ischeck);
				
			}
			else if(fieldType == 'select'){
				var ele = 'listSettingLanguageChat[' + i + ']' + name;
				$("select[name='"+ele+"']").val(value);
			}
		}
	}

	function updateDateControlValue(field, controlCode, name){
		var isSetData = 0;
		for(var i=0 ; i<CONTROL_LIST.length; i++){
			if(CONTROL_LIST[i].code == controlCode && CONTROL_LIST[i].isSetData == '1'){
				isSetData = 1;
				break;
			}
		}
		if(isSetData == 1){
			var listControl = SETTINGDTO.listSettingLanguageChat[0].listControls; // default chi lay 1 ngon ngu.			
			var controls = [];
			// get list control
			for(var i=0; i<listControl.length; i++){
				var item = listControl[i];
				if(item.fieldCode == field){
					controls = item.controls;
					break;
				}
			}			
			//get list control-value
			var controlValues = [];
			for(var i=0; i<controls.length; i++){
				if(controls[i].code == controlCode){
					controlValues = controls[i].controlValues; 				
					break;
				}
			}
			var url = "chat/config-add-value";			
			var pdata = {					
					'field' : field,
					'controlCode' : controlCode,
					'values' : JSON.stringify(controlValues)
			}
			$.ajax({
				type : "POST",
				url : BASE_URL + url,
				data : pdata,
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
			visibleDisible(name, 1);
		}
		else{
			visibleDisible(name, 0);
		}
	}
	function visibleDisible(nameSub,state){						
		var lindex = nameSub.lastIndexOf('.');
		var nameSub1 = nameSub.substring(lindex,nameSub.length);
		nameSub = nameSub.substring(0,nameSub.length - nameSub1.length);
		nameSub += '.updateControl';
		if(state == 0){
			$("a[name='"+nameSub+"']").css('display','none');	
		}
		else{
			$("a[name='"+nameSub+"']").css('display','block');
		}
		
	}
	// on click button save
	$('#btnSave').on('click', function(event) {	
		
		event.preventDefault();	
				
		if ($(".j-form-validate").valid()) {
			var jsonDto = JSON.stringify(SETTINGDTO);
			$("#jsonDto").val(jsonDto);
			var url = "chat/config-offline";
			var condition = $("#settingForm").serialize();
			$.ajax({
				type : "POST",
				url : BASE_URL + url,
				data : condition,
				success : function(data) {	
					var content = $(data).find('.body-content');
					$(".main_content").ready(function (){
						$('.content').scrollTop();
					});
					$(".main_content").html(content);
					
					var urlPage = $(data).find('#url').val();
					if (urlPage != null && urlPage != '') {
						window.history.pushState('', '', BASE_URL + urlPage);
					}
					unblockbg();
				},
				error : function(xhr, textStatus,  error) {
					console.log(xhr);
					console.log(textStatus);
					console.log(error);
				}
			});		
		}
		else {			
			showTabError(LANGUAGE_LIST);
		}
		
	});	
	$("#addRowConfig").on('click', function(event) {	
		// get one 
		event.preventDefault();
		
		var insert = false;
		if ($(".j-form-validate").valid()) {		
			for(var i=0; i<SETTINGDTO.listSettingLanguageChat.length; i++){		
				for(var j=0; j<SETTINGDTO.listSettingLanguageChat[i].listControls.length; j++){		
					if(SETTINGDTO.listSettingLanguageChat[i].listControls[j].isVisible == false){
						var name = 'listSettingLanguageChat['+i+'].listControls['+j+'].fieldCode';
						$("tr[name='"+name+"']").show();
						var fieldCode = 'listSettingLanguageChat['+i+'].listControls['+j+'].fieldName';
						var labelName = 'listSettingLanguageChat['+i+'].listControls['+j+'].labelName';
						var placeholderName = 'listSettingLanguageChat['+i+'].listControls['+j+'].placeholderName';
						var sortName = 'listSettingLanguageChat['+i+'].listControls['+j+'].sort';
						$("input[name='"+fieldCode+"']").val('');
						$("input[name='"+labelName+"']").val('');
						$("input[name='"+placeholderName+"']").val('');
						$("input[name='"+sortName+"']").val(0);
						SETTINGDTO.listSettingLanguageChat[i].listControls[j].isVisible = true;
						insert = true;
						break;
					}
				}
			}
			if (insert == false) {
				var comboboxHtml = "";
				for (var i=0;i<CONTROL_LIST.length;i++){
					comboboxHtml = comboboxHtml + '<option value="' + CONTROL_LIST[i].code + '">' + CONTROL_LIST[i].controlName +'</option>';
				}
				for(var i=0; i<SETTINGDTO.listSettingLanguageChat.length; i++){
					var j = 0;
					var sort = 0;
					
					if (SETTINGDTO.listSettingLanguageChat[i].listControls.length != 0){
						var controls = SETTINGDTO.listSettingLanguageChat[i].listControls[0].controls;
						
						var data = {
							"controls": controls
						};
						
						var length = SETTINGDTO.listSettingLanguageChat[i].listControls.length;
						j = length - 1;
					}else{
						data = {};
					}
					
					SETTINGDTO.listSettingLanguageChat[i].listControls.push(data);
					
					SETTINGDTO.listSettingLanguageChat[i].listControls[j]["fieldCode"] = "OFFCLTMP" + j;
					SETTINGDTO.listSettingLanguageChat[i].listControls[j]["sort"] = sort;
					
					var name = 'listSettingLanguageChat['+ i +'].listControls[' + j + '].fieldCode';
					$("tr[name='"+name+"']").show();
					
					var html =
						 '<tr data-row-id="OFFCLTMP' + j + '" name="listSettingLanguageChat[' + i + '].listControls[' + j + '].fieldCode">'
						+	'<td style="display:none">'
						+ 		'<input type="hidden"'
						+ 		'name="listSettingLanguageChat[' + i + '].listControls[' + j + '].id"/>'
						+		'<input type="hidden"'
						+		'name="listSettingLanguageChat[' + i + '].listControls[' + j + '].fieldCode"/>'
						+	'</td>'
						+	'<td class="text-left" >'
						+		'<input type="text"	class="form-control j-required"'
						+		'name="listSettingLanguageChat[' + i + '].listControls[' + j + '].fieldName"/>'
						+	'</td>'
						+	'<td class="text-center">'
						+		'<input type="text"	class="form-control j-required"'
						+		'name="listSettingLanguageChat[' + i + '].listControls[' + j + '].labelName"/>'
						+	'</td>'
						+	'<td class="text-center">'
						+		'<input type="text"	class="form-control j-required"'
						+		'name="listSettingLanguageChat[' + i + '].listControls[' + j + '].placeholderName" />'
						+	'</td>'
						+	'<td class="text-center">'
						+		'<div style="width:80%;float:left">'
						+			'<select type="select" class="form-control j-setting-chat-control-sync"' 
						+			'name="listSettingLanguageChat[' + i + '].listControls[' + j + '].fieldType">'
						+				comboboxHtml
						+			'</select>'	
						+		'</div>'
						+		'<div style="width:20%;float:left">'
						+			'<a class="glyphicon glyphicon-edit custom-icon j-btn-edit-control-value" '
						+			'name="listSettingLanguageChat[' + i + '].listControls[' + j + '].updateControl"' 
						+			'style="width:20px;height:20px;margin-top:10px;display:none;float:right" ></a>'
						+		'</div>'
						+	'</td>'
						+	'<td class="text-center">'
						+		'<input type="text"	class="form-control j-number j-required j-required-number j-setting-chat-sync" value=' + sort
						+		' name="listSettingLanguageChat[' + i + '].listControls[' + j + '].sort"/>'
						+	'</td>'
						+	'<td class="text-center">'
						+		'<input type="checkbox" class = "j-setting-chat-checkbox-sync" checked '
						+		'name="listSettingLanguageChat[' + i + '].listControls[' + j + '].isDisplay"/>'
						+	'</td>'
						+	'<td class="text-center">'
						+		'<a class="glyphicon glyphicon-remove-sign font-size-20 red j-config-control-delete j-btn-delete ">'
						+		'</a>'
						+	'</td>'	
						+ '</tr>';
					
					$('#language' + i + ' .table-striped').append(html);
					
					clickEditButton();
					changeChatControl();
					clickDeleteButton();
				}
			}
		}
		else {			
			showTabError(LANGUAGE_LIST);
		}
		
	});

	function clickDeleteButton(){
		$(".j-config-control-delete").on("click", function( event ) {
			var row = $(this).parents("tr");
			var field = row.data("row-id");	
			//visible
			for(var i=0; i<SETTINGDTO.listSettingLanguageChat.length; i++){		
				for(var j=0; j<SETTINGDTO.listSettingLanguageChat[i].listControls.length; j++){		
					if(SETTINGDTO.listSettingLanguageChat[i].listControls[j].fieldCode == field){				
						var name = 'listSettingLanguageChat['+i+'].listControls['+j+'].fieldCode';					
						$("tr[name='"+name+"']").hide();
						var fieldCode = 'listSettingLanguageChat['+i+'].listControls['+j+'].fieldName';
						var labelName = 'listSettingLanguageChat['+i+'].listControls['+j+'].labelName';
						var placeholderName = 'listSettingLanguageChat['+i+'].listControls['+j+'].placeholderName';
						var sortName = 'listSettingLanguageChat['+i+'].listControls['+j+'].sort';
						$("input[name='"+fieldCode+"']").val('TMP');
						$("input[name='"+labelName+"']").val('TMP');
						$("input[name='"+placeholderName+"']").val('TMP');
						$("input[name='"+sortName+"']").val('999999');
						SETTINGDTO.listSettingLanguageChat[i].listControls[j].isVisible = false;
						SETTINGDTO.listSettingLanguageChat[i].listControls[j].controls = CONTROL_LIST;
						break;
					}
				}
			}
		});
	}
	
	function clickEditButton(){
		$('.j-btn-edit-control-value').on('click', function(e) {
			e.preventDefault();
			var row = $(this).parents("tr");
			var field = row.data("row-id");		
			var controlCode = '';
			var name = $(this).attr('name');
			var findex = name.lastIndexOf('.');
			var nameSub = name.substring(findex,name.length);
			name = name.substring(0,name.length - nameSub.length);
			name += '.fieldType';		
			controlCode = $("select[name='"+name+"']").val();
			updateDateControlValue(field, controlCode, name);
		});
	}
	
	function changeChatControl(){
		$(".j-setting-chat-control-sync").change(function(e){
			e.preventDefault();
			synData(this);			
			//popup data 
			var row = $(this).parents("tr");
			var field = row.data("row-id");		
			var controlCode = $(this).val();		
			var name = $(this).attr('name');
			updateDateControlValue(field, controlCode, name);
		});
	}
});
function bindingDataControlValue(obj){
	// chi set zo 1 ngon ngu
	for(var i=0; i<SETTINGDTO.listSettingLanguageChat[0].listControls.length; i++){		
		if(SETTINGDTO.listSettingLanguageChat[0].listControls[i].fieldCode == obj.field){
			// set control			
			for(var j=0; j<SETTINGDTO.listSettingLanguageChat[0].listControls[i].controls.length; j++){
				if(SETTINGDTO.listSettingLanguageChat[0].listControls[i].controls[j].code == obj.controlCode){
					SETTINGDTO.listSettingLanguageChat[0].listControls[i].controls[j].controlValues = obj.controlValues;
					
					break;
				}
			}
			break;
		}
	}
	console.log(SETTINGDTO);
	$("#modal-insert-value").modal('hide');	
}

function blockbg(){
	$.isLoading({ text: "Loading" });
}
function unblockbg(){
	$.isLoading( "hide" );
}