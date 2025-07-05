$(document).ready(function() {
	$("#btnSearch").on("click", () => {
		console.log('hide alert 3');
		$('.alert').hide();
	});
});

function searchCombobox(element, placeholder, url, data, dataResult, allowClear) {
	if (allowClear === undefined) {
		allowClear = true;
	}
	var token = $("meta[name='_csrf']").attr("content");
  	var header = $("meta[name='_csrf_header']").attr("content");
	$(element).select2({
		placeholder : placeholder,
		minimumInputLength : 0,
		allowClear : allowClear,
		ajax : {
			url : BASE_URL + url,
			dataType : 'json',
			type : "POST",
			delay: 250,
			quietMillis : 50,
			data : data,
			processResults : dataResult,
			beforeSend: function(jqXHR, settings ) {
				$(document).unbind("ajaxSend").bind("ajaxSend", function(e, xhr, options) {
					xhr.setRequestHeader(header, token);
				});
			},
			complete : function(result) {
				$(document).unbind("ajaxSend").bind("ajaxSend", function(e, xhr, options) {
					blockbg();
					xhr.setRequestHeader(header, token);
				});
			},
			error : function(xhr, textStatus, error) {
				$(document).unbind("ajaxSend").bind("ajaxSend", function(e, xhr, options) {
					blockbg();
					xhr.setRequestHeader(header, token);
				});
			}
		},
		language: {
	      noResults: function() {
	        return SELECT2_NO_RESULTS;
	      },
	    },
	});
}

function searchCombobox(element, placeholder, url, data, dataResult, allowClear, dropdownParent) {
	if (allowClear === undefined) {
		allowClear = true;
	}
	var token = $("meta[name='_csrf']").attr("content");
	var header = $("meta[name='_csrf_header']").attr("content");
	$(element).select2({
		placeholder : placeholder,
		minimumInputLength : 0,
		allowClear : allowClear,
		ajax : {
			url : BASE_URL + url,
			dataType : 'json',
			type : "POST",
			delay: 250,
			quietMillis : 50,
			data : data,
			processResults : dataResult,
			beforeSend: function(jqXHR, settings ) {
				$(document).unbind("ajaxSend").bind("ajaxSend", function(e, xhr, options) {
					xhr.setRequestHeader(header, token);
				});
			},
			complete : function(result) {
				$(document).unbind("ajaxSend").bind("ajaxSend", function(e, xhr, options) {
					blockbg();
					xhr.setRequestHeader(header, token);
				});
			},
			error : function(xhr, textStatus, error) {
				$(document).unbind("ajaxSend").bind("ajaxSend", function(e, xhr, options) {
					blockbg();
					xhr.setRequestHeader(header, token);
				});
			}
		},
		language: {
			noResults: function() {
				return SELECT2_NO_RESULTS;
			},
		},
		dropdownParent: dropdownParent
	});
}

function formatRepoSelection (repo) {
	  return repo.text || repo.name;
	}

function searchCombobox(element, placeholder, url, data, dataResult, allowClear, dropdownParent, templateResult, templateSelection) {
	if (allowClear === undefined) {
		allowClear = true;
	}
	
	if (templateSelection === undefined || templateSelection == null) {
		templateSelection = formatRepoSelection;
	}
	
	var token = $("meta[name='_csrf']").attr("content");
	var header = $("meta[name='_csrf_header']").attr("content");
	$(element).select2({
		placeholder : placeholder,
		minimumInputLength : 0,
		allowClear : allowClear,
		templateResult: templateResult,
		ajax : {
			url : BASE_URL + url,
			dataType : 'json',
			type : "POST",
			delay: 250,
			quietMillis : 50,
			data : data,
			processResults : dataResult,
			beforeSend: function(jqXHR, settings ) {
				$(document).unbind("ajaxSend").bind("ajaxSend", function(e, xhr, options) {
					xhr.setRequestHeader(header, token);
				});
			},
			complete : function(result) {
				$(document).unbind("ajaxSend").bind("ajaxSend", function(e, xhr, options) {
					blockbg();
					xhr.setRequestHeader(header, token);
				});
			},
			error : function(xhr, textStatus, error) {
				$(document).unbind("ajaxSend").bind("ajaxSend", function(e, xhr, options) {
					blockbg();
					xhr.setRequestHeader(header, token);
				});
			}
		},
		language: {
			noResults: function() {
				return SELECT2_NO_RESULTS;
			},
		},
		dropdownParent: dropdownParent,
		templateSelection: templateSelection
	});
}