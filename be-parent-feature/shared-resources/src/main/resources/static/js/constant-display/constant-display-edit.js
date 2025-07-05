var IS_VALID = true;
$(document).ready(function () {
    $('#btnAddCom').on('click', function() {
        addComponent();
    });
    $('#addProductTable' + ' tbody tr').each(function(){
    	let td = $(this).find('td');
    	let group = $(td).find('input.class-kind');
    	let value = $(td).find('input.class-cat');
    	$(group).on('change',function(){
    		$(this).parent().find('span.error').remove();
        	checkDuplicated($(this));
        })
        $(value).on('change',function(){
        	$(this).parent().find('span.error').remove();
        	checkDuplicated($(this));
        })
    });
    // on click list
    $('#linkList').on('click',function(event){
        event.preventDefault();
        var url = BASE_URL + "constant-display/list";

        // Redirect to page list
        ajaxRedirect(url);
    });

    // on click cancel
    $('#cancel').on('click',function(event){
        event.preventDefault();
        var url = BASE_URL + "constant-display/list";

        // Redirect to page list
        ajaxRedirect(url);
    });

    $("tbody").on('click', '.j-btn-delete', function(event) {
        event.preventDefault();
//        var tbody = $(this).parent().parent();
//        var id = $($(tbody).find('td:eq(0)').children()).val();
        var row = $(this).parents("tr");
        var id = row.data("item-id");

        var len = $('#addProductTable' + ' tbody tr').length;
        
        var groupCode = row.data("item-id");
	    var kind = row.data("item-kind");
	    var code = row.data("item-code");

        if(len === 1 && (id === '' || id === null)){
            return;
        }
        if(id === '' || id === null || id == undefined){
            $(this).closest('tr').remove();
            //getDisplayOrder();
        }else {
            popupConfirm(MSG_DEL_CONFIRM, function(result) {
                if (result) {
                    var delComponent = len !== 1;
                    blockbg();
                    $.ajax({
                        url: BASE_URL + "constant-display/delete-item",
                        type: "POST",
                        data: {
                            "id" : id,
                            "delComponent" : delComponent,
                            "groupCode" : groupCode,
                            "kind" : kind,
                            "code" : code
                        },
                        success: function (data) {
                            var content = $(data).find('.body-content');
                            $(".main_content").html(content);
                            unblockbg();
                        }
                    });
                }
                $(this).closest('tr').remove();
                //getDisplayOrder();
            });
        }
    });

    $(".btn-save").on('click', function(e) {
        if ($(".j-form-validate").valid()) {
			let condition = getDataJson();
        	if (IS_VALID) {
        		blockbg();
                addRowIndex.reIndex('#form-edit-constant tbody');
                $.ajax({
          	      type  : "POST",
          	      contentType : "application/json",
          	      url   : BASE_URL + "constant-display/doSave",
          	      data  : condition,
          	      success: function (data, textStatus, request) {
          				var msgFlag = request.getResponseHeader('msgFlag');
          				if ("1" == msgFlag) {
          					$(".message-info").html(data);
          				} else {
          					var breadcrumb = $('#breadcrumb').html();
          					var content = $(data).find('.body-content');
          					$(".main_content").html(content);
          					$('#breadcrumb').html(breadcrumb);
          				}

          				var urlPage = $(data).find('#url').val();
          				if (urlPage != null && urlPage != '') {
          					window.history.pushState('', '', BASE_URL + urlPage);
          				}
          			},
          			error: function (xhr, textStatus, error) {
          				console.log(xhr);
          				console.log(textStatus);
          				console.log(error);
          			}
                });
                unblockbg();
        	}
        }
    });

//    $('#type').blur(function () {
//    	var action = $(this).data("action"); 
//    	if(action == null || action == 1){
//    		for(var i=0;i<TYPE_LIST.length;i++){
//                if ($('#type').val() === TYPE_LIST[i].id) {
//                    reLoadConstantTable($('#type').val());
//                }
//            }
//    	}
//    	
//        
//    });
    
    $('#type').on('change keyup', function(){
    	checkType();
    });

});

function checkType(){
	$(".lbl-error-type").remove();
	var type = $("#type").val();
	for(var i = 0; i < TYPE_LIST.length; i ++){
		if(type.toUpperCase() == TYPE_LIST[i].id.toUpperCase()){
			$("#type").after(`<span style='color:red;' class='lbl-error-type'>This type is exist.</span>`);
		}
	}
}

function addComponent() {
	var i = 0;
	var row = $("#tableHidden tbody").html();
    $('#addProductTable' + ' tbody').append(row);
    var lastRow = $('#addProductTable' + ' tbody tr:last');
    
    var len = $('#addProductTable' + ' tbody tr').length;
    var inputs = $(lastRow).find('td > input');
    
    /*let id = $(inputs[i++]);
    //var id = $($(lastRow).find('td').children().get(i++));
    $(id).attr("name", 'constants['+len+'].groupCode');*/
    
    let kind = $(inputs[i++]);
    //var kind = $($(lastRow).find('td > input').get(i++));
    $(kind).attr("name",'constants['+len+'].kind' );
    $(kind).on('change',function(){
    	$(this).parent().find('span.error').remove();
    	checkDuplicated($(this));
    })
    
    //var cat = $($(lastRow).find('td').children().get(i++));
    let code = $(inputs[i++]);
    $(code).attr("name", 'constants['+len+'].code');
    $(code).on('change',function(){
    	$(this).parent().find('span.error').remove();
    	checkDuplicated($(this));
    })
    
    //var catOfficialName = $($(lastRow).find('td').children().get(i++));
    let active = $(inputs[i++]);
    $(active).attr("name", 'constants['+len+'].actived');
    
    //var catOfficialNameVi = $($(lastRow).find('td').children().get(i++));
    let displayOrder = $(inputs[i++]);
    $(displayOrder).attr("name", 'constants['+len+'].displayOrder');
    
    //var catAbbrName = $($(lastRow).find('td').children().get(i++));
    let nameEn = $(inputs[i++]);
    $(nameEn).attr("name", 'constants['+len+'].nameEn');
    
    //var catAbbrNameVi = $($(lastRow).find('td').children().get(i++));
    let nameVi = $(inputs[i++]);
    $(nameVi).attr("name", 'constants['+len+'].nameVi');
    
    $(displayOrder).val(getDisplayOrderNext());

    
}

function getDisplayOrderNext(){
	var valDisplayOrderMax = 0;
	$('#addProductTable tbody tr').each(function(){
        var valDisplayOrder = Number($(this).find('.displayOrder').val());
        if(valDisplayOrderMax < valDisplayOrder){
        	valDisplayOrderMax = valDisplayOrder;
        }
    })
    return valDisplayOrderMax + 1;
}

function getDisplayOrder() {
    var num = 1;
    $('#addProductTable tbody tr').each(function(){
        $(this).find('.displayOrder').val(num);
        num++;
    })
}

var addRowIndex = {
	    reIndex : function(table)  {
        var index = 0;
        $(table).find('tr').each(function() {
        	var i = 1;
        	var id = $($(this).find('td').children().get(i++));
        	$(id).attr("name", "constants" + '[' + index + '].' + "groupCode");
            var kind = $($(this).find('td').children().get(i++));
            $(kind).attr("name", "constants" + '[' + index + '].' + "kind");
            var code = $($(this).find('td').children().get(i++));
            $(code).attr("name", "constants" + '[' + index + '].' + "code");
            var active = $($(this).find('td').children().get(i++));
            $(active).attr("name", "constants" + '[' + index + '].' + "actived");
            var displayOrder = $($(this).find('td').children().get(i++));
            $(displayOrder).attr("name", "constants" + '[' + index + '].' + "displayOrder");
            var nameEn = $($(this).find('td').children().get(i++));
            $(nameEn).attr("name", "constants" + '[' + index + '].' + "nameEn");
            var nameVi = $($(this).find('td').children().get(i++));
            $(nameVi).attr("name", "constants" + '[' + index + '].' + "nameVi");
            index++;
        })
    }
};

function reLoadConstantTable(inputQuery) {
    $.ajax({
        url: BASE_URL + "constant-display/reLoad",
        type: "POST",
        data: {
            "inputQuery" : inputQuery
        },
        success: function (data) {
            var content = $(data).find('.body-content');
            $(".main_content").html(content);
            unblockbg();
        }
    });
}

function getDataJson() {
	 var resultList = {};
	 var constants = [];

	 $('#addProductTable tbody tr').each(function(key, val) {
		 constants.push({
			 "groupCode" 			:   $(this).find(".class-groupCode").val(),
			 "kind" 				:   $(this).find(".class-kind").val(),
			 "code" 				:   $(this).find(".class-code").val(),
			 "nameEn" 				:   $(this).find(".class-nameEn").val(),
			 "nameVi" 				:   $(this).find(".class-nameVi").val(),
			 "actived" 				:   $(this).find(".class-actived").val(),
			 "displayOrder" 		:   $(this).find(".class-displayOrder").val(),
			 "oldKind" 				:   $(this).find(".class-oldKind").val(),
			 "oldCode" 				:   $(this).find(".class-oldCode").val(),
			 "oldGroupCode" 		:   $(this).find(".class-oldGroupCode").val(),
		  });
	 });
	debugger
	 resultList["code"] = $("#code").val();
	 resultList["nameEn"] = $("#nameEn").val();
	 resultList["nameVi"] = $("#nameVi").val();
	 resultList["constants"] = constants;

	return JSON.stringify(resultList);
}

function checkDuplicated(element){
//	value.parent().find('span.error').remove();
//	group.parent().find('span.error').remove();
	var rows = $('#addProductTable' + ' tbody tr');
	let td = element.parent().parent().find('td');
	let group = $(td).find('input.class-kind');
	let value = $(td).find('input.class-cat');
	var count = 0;
	rows.each(function(){
		var groupTmp = $(this).find('td').find('input.class-kind');
		var valueTmp = $(this).find('td').find('input.class-cat');
		if(equal(groupTmp.val(),group.val()) 
					&& equal(valueTmp.val(), value.val())
					){
				count++;
			}
	})
	if(count > 1){
		element.parent().append('<span class="error">Duplicated value</span>');
		IS_VALID = false;
	}else {
		value.parent().find('span.error').remove();
		group.parent().find('span.error').remove();
		IS_VALID = true;
	}
		
}
function equal(str1, str2){
	if(str1 != null && str2 != null){
		return str1.trim().toLowerCase() === str2.trim().toLowerCase();
	}else{
		return false;
	}
}