var id;
var LIST_HIDE = ["sName", "sWorkingHours", "sDescription"];
$(document).ready(function($) {
	init_data(LIST_HIDE);
	var id =  $("#id").val() == null ? -1 : parseInt($("#id").val());
	
		
	//thay đổi hàng bắt sự kiện lưu thứ tự hàng	
	$('#tb-sort-list tbody').on('mousedown', function() {
		isDragging = false;
	}).mousemove(function() {
		isDragging = true;
	}).mouseup(function() {
		var wasDragging = isDragging;
		isDragging = false;
		if (wasDragging) {
			var url = "calendar-type/list/sort";
			$.ajax({
				type: "POST",
				url: BASE_URL + url,
				contentType: "application/json",
				data: getDataJson(),
				success: function(data) {
					var content = $(data).find('.body-content');
					$(".main_content").ready(function() {
						$('.content').scrollTop();
					});

					$(".main_content").html(content);
				},
				error: function(xhr, textStatus, error) {
					console.log(xhr);
					console.log(textStatus);
					console.log(error);
				}
			});
		}
	});

	
	$('.sort').sortable({
			cursor : 'move',
			axis : 'y',
			update : function(e, ui) {
				$(this).sortable('refresh');
				//debugger
				var lst = [];
			      $('.sort').find('li > a > input').each(function(index, element) {
			   		console.log($(element).data('name'));
			   		lst.push($(element).data('name'));
				      });
			    LIST_COLUMN = lst;
				newSort();
			}
			
	});
	
});

/**
 * getDataJson
 * @returns
 */
function getDataJson() {
	
	var result = {};
	
	var sortOderList = [];

	$('#tb-sort-list tbody tr').each(function(key, val) {

		sortOderList.push({
			"objectId" : $(this).data("dto-id"),
			"sortValue" : key + 1,
		});
	});
	
	result["sortOderList"] = sortOderList;

	return JSON.stringify(result);
}

function newSort(event) {
   order($(this), event);
}

function order(element, event) {
    var data = setOrder();
    ajaxSearch("calendar-type/ajaxList", data, 'tableList', element, event);
}

function setOrder() {
    var condition = {};
    condition["fieldSearch"] = $("#fieldSearch").val();
	condition["fieldValues"] = $("#fieldValuesHidden").val();
	condition["companyId"] = $("#companyId").val();
	if ( $('#cbDes').is(':checked') ) {
		condition["description"] = $("#description").val();
	}
	if ( $('#cbCode').is(':checked') ) {
		condition["code"] = $("#code").val();
	}
	if ( $('#cbName').is(':checked') ) {
		condition["name"] = $("#name").val();
	}
	if ( $('#sName').is(':checked') ) {
		condition["sName"] = 1;
	}else{
		condition["sName"] = 0;
	}
	if ( $('#sWorkingHours').is(':checked') ) {
		condition["sWorkingHours"] = 1;
	}else{
		condition["sWorkingHours"] = 0;
	}
	if ( $('#sDescription').is(':checked') ) {
		condition["sDescription"] = 1;
	}else{
		condition["sDescription"] = 0;
	}
    condition["orderColumn"] = LIST_COLUMN;
    return condition;
}

function setDataSearchHidden() {
	$("#fieldSearchHidden").val($("#fieldSearch").val());
	$("#fieldValuesHidden").val($("select[name=fieldValues]").val());
}

function init_data(list){
		for(const a of list){
			hide_show_table(a);
		}
} 
// show/hide column in table
function hide_show_table(col_name){
		var checkbox_val=document.getElementById(col_name).checked;
			if(!checkbox_val)
			{
			var all_col=document.getElementsByClassName(col_name);
			for(var i=0;i<all_col.length;i++)
			{
			   all_col[i].style.display="none";
		  	}
			  document.getElementById(col_name+"_head").style.display="none";
			  document.getElementById(col_name).value="hide";
		 	}
		 	else
		 	{
			var all_col=document.getElementsByClassName(col_name);
			for(var i=0;i<all_col.length;i++)
		{
		   	all_col[i].style.display="table-cell";
		}
		document.getElementById(col_name+"_head").style.display="table-cell";
		document.getElementById(col_name).value="show";
	}
} 


