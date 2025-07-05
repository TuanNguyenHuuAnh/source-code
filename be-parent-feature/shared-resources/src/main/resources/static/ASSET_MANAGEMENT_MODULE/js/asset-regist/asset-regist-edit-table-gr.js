$(function(){
	conditionShowAddGR();
	$("#tableListGR").datatables({
		url : BASE_URL + 'asset-regist/ajaxDetailGR',
		type : 'POST',
		setData : setConditionSearch
	});
	
	$("#check_all_gr").on('click', function(){ // day la khi check all
		var ischecked = $(this).is(':checked');
		$(".checkbox_gr").each(function(){
			$(this).prop('checked', ischecked);
			map_select_gr.set($(this).val(),ischecked);
		});
		conditionShowAddGR();
	});
	
	$('.checkbox_gr').on('click', function(){  // day la khi check tung checkbox
		var ischecked = $(this).is(':checked');
		var grId = $(this).val();
		map_select_gr.set($(this).val(),ischecked);
		if(!ischecked)
			$("#check_all_gr").prop("checked", ischecked); // uncheck checkall neu 1 item duoc bo ra
		$(this).prop('checked', ischecked);
		$('.checkbox_gr').each(function(){
			var grIdItem = $(this).val();
			if(grId == grIdItem)
				$(this).prop("checked", ischecked);
		});
		conditionShowAddGR();
	});
	
	
//	for(var [key,value] of map_select_gr){ // khi phan trang
//		$('.gr-id-'+key).prop('checked',value);
//	}
	$(".checkbox_gr").each(function(){
		var value = $(this).val();
		var isCheck = map_select_gr.get(value)
		if(isCheck !=null ){
			$(this).prop("checked", isCheck);
		}
	});
	
	$(".input-search-gr").unbind('keypress').bind('keypress', function(){
		if(event.keyCode == 13){
			onClickSearchGR(this, event);
	    }
	});
	
	$("#btnSearchGR").unbind().bind('click', function(event){
		onClickSearchGR(this, event)
	});
	
	
	$("#btnOKGR").unbind().bind('click', function(){
		var url = "asset-regist/add-from-gr?propOf="+ $("#propertyOf").val();
		addFromGR(url)
	});
	

});

function conditionShowAddGR(){
	var isCheck = false;
	for(var [id,isChecked] of map_select_gr){
		if(isCheck == false)
			isCheck =  isChecked;
	}
	
	showHideButtonAdd(!isCheck);
	return isCheck;
}
function showHideButtonAdd(disabled){
	if(disabled){
		$("#btnOKGR").hide();
		$("#btnOKGR_hide").show();
	}
	else{
		$("#btnOKGR").show();
		$("#btnOKGR_hide").hide();
	}
}

function addFromGR(url){
	if(conditionShowAddGR() == false){
		return;
	}
	$.ajax({
		type : "POST",
		url : BASE_URL + url,
		data : conditionSubmit(),
		success : function(data, textStatus, request) {
//			if($("#tempTable >tbody >tr").length > 0 ){
//				var htmlappend = $(data).find("table > tbody").html();
//				$("#tempTable > tbody").append(htmlappend);
//				getScript();
//				
//			}
//			else{
			
			console.log('return table');
				$("#tableDetail").html(data);
			//}

			blockbg();
		},
		error : function(xhr, textStatus, error) {
			console.log(xhr);
			console.log(textStatus);
			console.log(error);
			unblockbg();
		}
	});
	map_select_gr.clear();
	$(".checkbox_gr").prop("checked",false);
	$(".check_all_gr").prop("checked",false);
	
}

function conditionSubmit(){
	var listId = [];
	var object = {};
	for(var [id,isChecked] of map_select_gr){
		if(isChecked){
			listId.push(id);
		}
	}
	
	object = setConditionSearch();
	object.listId =  listId.join();
	return object;
}


function onClickSearchGR(element, event) {
	setConditionHidden();
	ajaxSearch("asset-regist/ajaxDetailGR", setConditionSearch(), "tableListGR", element, event);
}


function setConditionSearch(){
	var condition = {};
	condition["goodDescription"] = $("#goodDescriptionHidden").val();
	condition["poNO"] = $("#poNoHidden").val();
	condition["prNo"] = $("#prNoHidden").val();
	condition["receiptNo"] = $("#receiptNoHidden").val();
	condition["receiptDateFrom"] = $("#receiptDateFromHidden").val();
	condition["receiptDateTo"] = $("#receiptDateToHidden").val();
	return condition;
}

function setConditionHidden(){
	$("#goodDescriptionHidden").val($("#goodDescription").val());
	$("#poNoHidden").val($("#poNO").val());
	$("#prNoHidden").val($("#prNo").val());
	$("#receiptNoHidden").val($("#receiptNo").val());
	$("#receiptDateFromHidden").val($("#receiptDateFrom").val());
	$("#receiptDateToHidden").val($("#receiptDateTo").val());
}