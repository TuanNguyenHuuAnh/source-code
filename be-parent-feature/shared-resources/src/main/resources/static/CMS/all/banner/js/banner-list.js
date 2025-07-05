$(document).ready(function($) {

	var mouseX;
	var mouseY;
	$(document).mousemove( function(e) {
	   mouseX = e.pageX; 
	   mouseY = e.pageY;
	});  
	$(".tbl-img").mouseover(function(){
		if(mouseY > '400'){
			$('.img_banner_big').css({'bottom':'mouseY - 420','left':mouseX - 350});
		}
		else{
			$('.img_banner_big').css({'top':mouseY,'left':mouseX - 350});
		}
	  
	});
	var img_banner = $('#idDivTable').find('table tbody').find('video.img_banner');
	Array.from(img_banner).forEach((el) => {
		console.log($(el).data('banner-video'));
		var fileName = $(el).data('banner-video');
		if (flvjs.isSupported() && fileName.split('.').pop() == 'flv') {
			var flvPlayer = flvjs.createPlayer({
				type: 'flv',
				url: fileName
			});
			flvPlayer.attachMediaElement(el);
			flvPlayer.load();
			flvPlayer.pause();
		}
	});
	var img_banner_big = $('#idDivTable').find('table tbody').find('video.img_banner_big');
	Array.from(img_banner_big).forEach((el) => {
		console.log($(el).data('banner-video'));
		var fileName = $(el).data('banner-video');
		if (flvjs.isSupported() && fileName.split('.').pop() == 'flv') {
			var flvPlayer = flvjs.createPlayer({
				type: 'flv',
				url: fileName
			});
			flvPlayer.attachMediaElement(el);
			flvPlayer.load();
			flvPlayer.pause();
		}
	});
	
	$( window ).resize(function() {
  		$(document).mousemove( function(e) {
		   mouseX = e.pageX; 
		   mouseY = e.pageY;
		});  
		$(".tbl-img").mouseover(function(){
			if(mouseY > '400'){
				$('.img_banner_big').css({'bottom':'mouseY - 420','left':mouseX - 350});
			}
			else{
				$('.img_banner_big').css({'top':mouseY,'left':mouseX - 350});
			}
		  
		});
	});

/*	$('#fieldSearch').keypress(function(event){
	    var keycode = (event.keyCode ? event.keyCode : event.which);
	    if(keycode == '13'){
	    	onClickSearch(this, event);
	    	$('.close').trigger('click');
	    }
	});
*/

	// block press enter search
	$('#fieldSearch, #search-box').bind("keypress", function (event) {
    if (event.keyCode == '13') {
        return false;
    	}
	});
	
	var table = document.getElementById('tbl-manager-banner');

	var rowLength = table.rows.length;

	for(var i=0; i < rowLength; i += 1){
		  var id = "#title"+ i;
		  var txt = $(id).text();
		    if(txt.length > 80)
		      $(id).text(txt.substring(0,77) + '...')
	}
	
	// datatable load
    if (SEARCH_DTO.searchType == 1){ // search field
        $("#tableList").datatables({
            url : BASE_URL + 'banner/ajaxList',
            type: 'POST',
            setData: setConditionSearch
        });
    }else{ // search all
        $("#tableList").datatables({
            url: BASE_URL + 'banner/ajaxList',
            type: 'POST',
            setData: setConditionSearchAll
        });
    }

	// on click add
	$("#addNew").on("click", function(event) {
		var url = BASE_URL + "banner/edit";
		// Redirect to page add
		ajaxRedirectWithCondition(url, setConditionSearch());
	});

	// on click edit
	$(".j-btn-edit").on("click", function(event) {
		editBanner(this, event);
	});

	// on click delete
	$(".j-btn-delete").on("click", function(event) {
		deleteBanner(this, event);
	});

	// on click detail
	$(".j-btn-detail").on("click", function(event) {
		detail(this, event);
	});

	$('select[multiple]').multiselect({
		columns : 1,
		placeholder : SEARCH_LABEL,
		search : true
	});
	setDataSearchHidd();
	// on click search
	$("#btnSearch").on('click', function(event) {
		onClickSearch(this, event);
		$('.close').trigger('click');
	});
	$("#btnSearchAll").on('click', function(event) {
		onClickSearchAll(this, event);
	});
	
	$("#status, #bannerPC, #bannerMobile, #code, #name").on('keypress', function(e) {		
		if((e.which && e.which == 13) || (e.keyCode && e.keyCode == 13)){
			onClickSearch(this, event);
		}	
	});

/*	$("#btnRefresh").on('click', function(event) {
		refresh();
	});*/
	$("#btnRefresh").unbind('click').bind('click', function(event) {
		refresh();
	});

	
	//double click
	/*
	$(".trEdit").on("dblclick", function(event) {
		editBanner(this, event, $(this));
	});	
	*/
	signatureImage();
	
	showIconTableSort(C_NAME, 'idDivTable');
});

/**
 * editBanner
 * 
 * @param element
 * @param event
 */
function editBanner(element, event, row) {
	event.preventDefault();

	// Prepare data
	if (row == null) {
		row = $(element).parents("tr");
	}
	
	var id = row.data("banner-id");
	var url = BASE_URL + "banner/edit?id=" + id;

	// Redirect to page detail
	ajaxRedirectWithCondition(url, setConditionSearch());
}

/**
 * delete banner
 * 
 * @param element
 * @param event
 */
function deleteBanner(element, event) {
	event.preventDefault();

	// Prepare data
	var row = $(element).parents("tr");
	var bannerId = row.data("banner-id");
	popupConfirmWithButtons(MSG_DEL_CONFIRM, LIST_BUTTON_CONFIRM_DELETE, function(result) {
		if (result) {
			var url = "banner/delete";
			var condition = {
				"id" : bannerId
			}
			
			ajaxSubmit(url, condition, event);
			
		}
	});
}

/**
 * detail
 * 
 * @param element
 * @param event
 */
function detail(element, event) {
	event.preventDefault();

	// Prepare data
	var row = $(element).parents("tr");
	var id = row.data("banner-id");
	var url = BASE_URL + "banner/detail?id=" + id;

	// Redirect to page detail
	ajaxRedirectWithCondition(url, setConditionSearch());
}

/**
 * on click button search
 */
function onClickSearch(element, event) {

	setDataSearchHidd();
	
	ajaxSearch("banner/ajaxList", setConditionSearch(), "tableList", element, event);
}
function onClickSearchAll(element, event) {
	let condition = {};

	var listCheckInFilterTable = [];
	$('#tableList .dropdown-filter-sort').find('li').find('input').each(function(key, val) {
		let isChecked = $(val).is(':checked');

		if (isChecked){
			listCheckInFilterTable.push($(val).data("column"));
		}
	});
	condition["listCheckInFilterTable"] = listCheckInFilterTable;
	$("#conditionHidd").val(JSON.stringify(condition));



	condition["fieldSearch"] = $('#fieldSearch').val();
    condition["searchType"] = 0;
	ajaxSearch("banner/ajaxList", condition, "tableList", element, event);
}
function setConditionSearchAll(){
    let condition = {};
    
    condition["fieldSearch"] = $('#fieldSearch').val();
    condition["searchType"] = 0;
    
    return JSON.parse(JSON.stringify(condition));
}
/**
 * setConditionSearch
 */
function setConditionSearch() {
	setDataSearchHidd();
	
	return JSON.parse($("#conditionHidd").val());
}
function setConditionBySort(){
		setDataSearchHidd();
		return JSON.parse($("#conditionHidd").val());
}
/**
 * setDataSearchHidd
 */
function setDataSearchHidd() {
	var condition = {};
	// condition["fieldSearch"] = $('#fieldSearch').val();

	$('.field-seach').each(function(key, val) {
		let $el = $(val);

		let id = $el.attr('id');
		let name = $el.attr('name');
		let idConditionSearch = id + 'Condition';
		let value = $('#' + id).val();

		let conditionSearch = $('#' + idConditionSearch).val();

		let disabledAttr = $('#' + id).attr('disabled');

		if ($el.get(0).nodeName == 'SELECT') {
			if ($('#' + id).attr('disabled') != 'disabled') {
				let nameSearch = $('#' + id).find('option:selected').text();
				condition[name] = getValueByConditionSearch(nameSearch, conditionSearch);
			}
		} else if ($el.get(0).nodeName == 'INPUT') {
			let type = $el.data('field-type');
			if (type == 'INPUT') {
				if (disabledAttr != 'disabled') {
					condition[name] = getValueByConditionSearch(value, conditionSearch);
				}
			} else if (type == 'CHECKBOX') {
				if (disabledAttr != 'disabled') {
					if ($('#' + id).is(':checked')) {
						value = 1;
					} else {
						value = 0;
					}
					
					if (!isNull(conditionSearch)){
						condition[name] = getValueByConditionSearch(value, conditionSearch);
					}
				}
			} else if (type == 'DATE') {
				if (value != ''){
				if (conditionSearch != undefined && conditionSearch.indexOf('BETWEEN') > -1) {
					let valueTo = $('#' + id + 'To').val();
					if (DATE_FORMATE_TYPE == 'FULL_DATE') {
						if (disabledAttr != 'disabled') {
							if (isValidDate(value)) {
								value = "CONVERT(DATE, '" + value + ' 00:00:00' + "', 103)";
								valueTo = "CONVERT(DATE, '" + valueTo + ' 00:00:00' + "', 103)";
							}
							let valueSearch = conditionSearch.replace('paramFrom', value).replace('paramTo', valueTo);
							condition[name] = valueSearch;
						}
					} else {
						if (disabledAttr != 'disabled') {
							let valueSearch = conditionSearch.replace('paramFrom', value).replace('paramTo', valueTo);
							condition[name] = valueSearch;
						}
					}
				} else {
					if (DATE_FORMATE_TYPE == 'FULL_DATE') {
						if (disabledAttr != 'disabled') {
							if (isValidDate(value)) {
								value = "CONVERT(DATE, '" + value + ' 00:00:00' + "', 103)";
							}

							condition[name] = conditionSearch + ' ' + value;
						}
					} else {
						if (disabledAttr != 'disabled') {
							condition[name] = conditionSearch + ' ' + value;
						}
					}
				
			
			}
			}
		}
		}
	})

	var listCheckInFilterTable = [];

	$('#tableList .dropdown-filter-sort').find('li').find('input').each(function(key, val) {
		let isChecked = $(val).is(':checked');
		
		if (isChecked){
			listCheckInFilterTable.push($(val).data("column"));
		}
	});

	if(isNull($("#fieldSearch").val())) {
		condition["searchType"] = 1;
	}else{
		condition["fieldSearch"] = $('#fieldSearch').val();
		condition["searchType"] = 0;
	}
	condition["listCheckInFilterTable"] = listCheckInFilterTable;
	condition["pageSize"] = SEARCH_DTO.pageSize;
	$("#conditionHidd").val(JSON.stringify(condition));
}

function getValueByConditionSearch(value, conditionSearch){
	let rs = '';
	if (isNull(conditionSearch)) {
		if (!isNull(value)){
			rs = value;
		}
	} else {
		if (conditionSearch.indexOf('LIKE') > -1) {
			if (!isNull(value)){
				value = conditionSearch.replaceAll('param', value);
				rs = value;
			}
		} else if (conditionSearch.indexOf('=') >- 1 || conditionSearch.indexOf('<>') >- 1){
			rs = conditionSearch + "N'" + value + "' ";
		} else {
			if (!isNull(value)){
				rs = conditionSearch + value;
			}
		}
	}
	
	return rs;
}

/*function refresh() {
	$("#code").val('');
	$("#title").val('');
	$("#bannerType").val("").change();
	$("#bannerDevice").val("").change();
	
	$('#btnSearch').trigger('click');
}
*/

function refresh() {
	$("#fieldSearch").val('');

	$('.field-seach').each(function(key, val) {
		let $el = $(val);

		let id = $el.attr('id');

		if ($el.get(0).nodeName == 'SELECT') {
			if ($('#' + id).attr('disabled') != 'disabled') {
				$('#' + id).val('').change();
			}
		} else if ($el.get(0).nodeName == 'INPUT') {
			let type = $el.data('field-type');
			if (type == 'INPUT') {
				if ($('#' + id).attr('disabled') != 'disabled') {
					$('#' + id).val('');
				}
			} else if (type == 'CHECKBOX') {
				$('#' + id).val('');
			} else if (type == 'DATE') {
				$('#' + id).val('');
			}
		}
	})
	
	
	$('.divSearch').each(function(key, val){
		if ($(val).attr('style') != 'display: none;'){
			let $element =  $(val).find('.field-seach-condition');
			let name = $element.attr('name').replaceAll('Condition', 'Search');
			let isChecked = $('#' + name).is(":checked");
			if (isChecked){
				$('#' + name).trigger('click');
			}
		}
	})
	
	$('#btnSearch').trigger('click');
}

/**
 * IMAGE signature
 */
function signatureImage() {
	$(".divIframeVideo").each(function(key, val){
		let $div = $(val);
		
		var iframe = $div.find('.iframe');
		let link = $(iframe).attr('src');
		
		
		addYoutubeVideoToHtml($div.attr('id'), $(iframe).attr('id'), link, 240, 198);
	});
}
