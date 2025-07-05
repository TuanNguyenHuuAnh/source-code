$(document).ready(function() {	
	//on click cancel
	$('#cancel').on('click', function (event) {
		event.preventDefault();
		var url = BASE_URL + "stock/list";
		
		// Redirect to page list
		ajaxRedirect(url);
	});
	
	//on click list
	$('#linkList').on('click', function (event) {
		event.preventDefault();
		var url = BASE_URL + "stock/list";

		// Redirect to page list
		ajaxRedirect(url);
	});
	
	// on click add
	$("#add").on("click", function(event) {
		var url = BASE_URL + "stock/edit";
		// Redirect to page add
		ajaxRedirect(url);
	});	
	// Post edit save job
	$('.btn-save').on('click', function(event) {
		if ($(".j-form-validate").valid()) {
			var url = "stock/edit";
			var condition = $("#form-stock-edit").serialize();
			ajaxSubmit(url, condition, event);		
		}
	});
    $('#keeper').select2({
        ajax: {
            url: BASE_URL + "stock/account",
            data: function (params) {
              var query = {
                dataType: 'json',
                search: params.term,
                page: params.page || 1
              }
              // Query parameters will be ?search=[term]&page=[page]
              return query;
            },
            results: function (data) {
                console.log(data);
            }
          }
    });
});