$(function(){
	conditionShowAddGE();
	$("#tableListGE").datatables({
		url : BASE_URL + 'asset-regist/ajaxDetailGE',
		type : 'POST',
		setData : setConditionSearchGE
	});
	
	$("#check_all_payment").on('click', function(){ // day la khi check all
		var ischecked = $(this).is(':checked');
		$(".checkbox_payment").each(function(){
			$(this).prop('checked', ischecked);
			map_select_ge.set($(this).val(),ischecked);
		});
		conditionShowAddGE();
	});
	
	$('.checkbox_payment').on('click', function(){  // day la khi check tung checkbox
		var ischecked = $(this).is(':checked');
		var grId = $(this).val();
		map_select_ge.set($(this).val(),ischecked);
		if(!ischecked)
			$("#checkbox_payment").prop("checked", ischecked); // uncheck checkall neu 1 item duoc bo ra
		$(this).prop('checked', ischecked);
		$('.checkbox_payment').each(function(){
			var grIdItem = $(this).val();
			if(grId == grIdItem)
				$(this).prop("checked", ischecked);
		});
		conditionShowAddGE();
	});
	
	
//	for(var [key,value] of map_select_ge){ // khi phan trang
//		$('.ge-id-'+key).prop('checked',value);
//	}
	
	$(".checkbox_payment").each(function(){
		var value = $(this).val();
		var isCheck = map_select_ge.get(value)
		if(isCheck !=null ){
			$(this).prop("checked", isCheck);
		}
	});
	
	$(".input-search-ge").unbind('keypress').bind('keypress', function(){
		if(event.keyCode == 13){
			onClickSearchGE(this, event);
	    }
	});
	
	$("#btnSearchGE").on('click', function(event){
		onClickSearchGE(this, event)
	});
	
	
	$("#btnOKGE").on('click', function(){
		var url = "asset-regist/add-from-ge"
			addFromGE(url)
	});
	

});

function conditionShowAddGE(){
	var isCheck = false;
	for(var [id,isChecked] of map_select_ge){
		if(isCheck == false)
			isCheck =  isChecked;
	}
	showHideButtonAddGE(!isCheck);
	return isCheck;
}
function showHideButtonAddGE(disabled){
	if(disabled){
		$("#btnOKGE").hide();
		$("#btnOKGE_hide").show();
	}
	else{
		$("#btnOKGE").show();
		$("#btnOKGE_hide").hide();
	}
}


function addFromGE(url){
	
	if(conditionShowAddGE() == false){
		return;
	}
	
	$.ajax({
		type : "POST",
		url : BASE_URL + url,
		data : conditionSubmitGE(),
		success : function(data, textStatus, request) {
			$("#tableDetail").html(data);
			unblockbg();
		},
		error : function(xhr, textStatus, error) {
			console.log(xhr);
			console.log(textStatus);
			console.log(error);
			unblockbg();
		}
	});
	map_select_ge.clear();
	$(".checkbox_payment").prop("checked",false);
	$(".check_all_payment").prop("checked",false);
	
}

function conditionSubmitGE(){
	var listId = [];
	var object = {};
	for(var [id,isChecked] of map_select_ge){
		if(isChecked){
			listId.push(id);
		}
	}
	
	object = setConditionSearchGE();
	object.listId =  listId.join();
	return object;
}


function onClickSearchGE(element, event) {
	setConditionHiddenGE();
	ajaxSearch("asset-regist/ajaxDetailGE", setConditionSearchGE(), "tableListGE", element, event);
}


function setConditionSearchGE(){
	var condition = {};
	condition["paymentNo"] = $("#paymentNoHidden").val();
	condition["requester"] = $("#requesterHidden").val();
	condition["paymentDateFrom"] = $("#paymentDateFromHidden").val();
	condition["paymentDateTo"] = $("#paymentDateToHidden").val();
	return condition;
}

function setConditionHiddenGE(){
	$("#paymentNoHidden").val($("#paymentNo").val());
	$("#requesterHidden").val($("#requester").val());
	$("#paymentDateFromHidden").val($("#paymentDateFrom").val());
	$("#paymentDateToHidden").val($("#paymentDateTo").val());
	
}