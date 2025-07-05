
$(function() {
	//$("#button-approval-module-save-draft-id, #button-approval-module-submit-id, #btnClearData").show();
	onNoPO();
	initCategory();
	
	$("#propertyOf").on("change",function(){
		var prop = $(this).val();
		$(".assetCategory").each(function(){
			loadCategory(prop, this, loadByCategory);
		});
	});
	
	$(".assetCategory").on("change",function(){
		//var cateCode = $(this).val();
		//var element = $(this).closest("tr").find(".assetSubCategoryCode");
		loadByCategory(this);
	});
	
	
	
	
	$('.quantity, .unitPrice').on('keyup', function(){
		var $tr = $(this).closest('tr');
		reCalculateRow($tr);
	});
	

	$('.big-number').number(true,undefined,undefined,undefined, false);
    $("#tempTable").on('click','.j-btn-delete', function(event){
    	//deleteRow(this);
    	$(this).parents("tr").remove();
    	var rowTableDetail = $("#tempTable > tbody > tr").length;
    	if(rowTableDetail > 0){
    		resetRowIndex();
    	}
    	else{
    		$("#isNoPO").prop("disabled", false);
    	}
    });
    $("#tempTable").on('click','.j-btn-copy', function(event){
    	$(".select2-hidden-accessible").select2("destroy");
        var htmlInvoceRow = "<tr>" + $(this).parents("tr").html() + "</tr>";
        var htmlAppend = $(htmlInvoceRow);
        htmlAppend.find('.id-detail').val(null);
		$('#tempTable>tbody').append("<tr>"+htmlAppend.html()+"</tr>");
		//last tr
		let lastTr = $('#tempTable>tbody>tr').length - 1;
		let elementTr = $('#tempTable>tbody>tr')[lastTr];
		let $elementTr = $(elementTr);

		$elementTr.find('.number').text(lastTr + 1);
		$elementTr.find("td input:text,input:hidden,textarea,select").each(function() {
			 var name = $(this).attr('name');
			 if(typeof name !== 'undefined'){
				 var indexNeedToReplace = name.substr(0, name.lastIndexOf("."));
				 var indexReplace = name.substr(0, name.lastIndexOf("[")) + "["+lastTr+"]";
				 name = name.replace(indexNeedToReplace, indexReplace);
				 $(this).attr('name',name);
			 }
			 
		 });
		
		getScript();
		$(".select-table-detail").each(function(){
			setWidthAllRow(this);
		});
		setWidthHeader();
    });

    // date picker
	$(".date").datepicker({autoclose : true,format: 'dd/mm/yyyy'});
	$("#tempTable").on('change','.select-table-detail',function(){
		setWidthAllRow(this);
		setWidthHeader();
	});
	
	$('.select2').select2({		
		tokenSeparators: [',', ' '],
		width: '100%'
		//allowClear: true
	});
	
	$('#btnExport').click(function() {
		var linkExport = BASE_URL + "asset-regist/export-detail";
		doExportExcelWithToken(linkExport, "token", setConditionExportDetail());
	});
	
	onLoadSetMaxWidthSelect();
	setWidthHeader();
	
});




function setConditionExportDetail(){
	var condition = {};
	condition["idHeader"] = $("#id").val();
	return condition;
}





























