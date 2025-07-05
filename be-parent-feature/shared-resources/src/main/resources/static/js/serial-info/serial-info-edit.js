$(document).ready(function() {
    $('.datepicker').datepickerUnit({
        format: DATE_FORMAT,
        changeMonth: true,
        changeYear: true,
        autoclose: true,
        keyboardNavigation : true
    });
    
    $('#review').click(function(e) {
    	if ($(".j-form-validate").valid()) {
            var url = "serial-info/review-serial";
            var data = $("#form-serial-info-edit").serialize();

            ajaxSubmit(url, data, event);
        }
    });
    
    $('.e-only-number').keyup(function(e) {
    	if (/\D/g.test(this.value)) {
			// Filter non-digits from input value.
			this.value = this.value.replace(/\D/g, '');
		}
	});
    
    var numberFormat = $('#numberFormat').val();
    if( numberFormat ) {
    	$(".c-group-numberFormat").removeAttr("disabled");
    }
    $('#numberFormat').change(function(e) {
    	if(this.value) {
    		$(".c-group-numberFormat").removeAttr("disabled");
    	} else {
    		$(".c-group-numberFormat").attr("disabled", "disabled");
    	}
    });

    //on click cancel
    $('#cancel').on('click', function (event) {
        event.preventDefault();
        var url = BASE_URL + "serial-info/list";

        // Redirect to page list
        ajaxRedirect(url);
    });
    
    //on click list
    $('#linkList').on('click', function (event) {
        event.preventDefault();
        var url = BASE_URL + "serial-info/list";

        // Redirect to page list
        ajaxRedirect(url);
    });
    
    // on click add
    $("#add").on("click", function(event) {
        var url = BASE_URL + "serial-info/edit";
        // Redirect to page add
        ajaxRedirect(url);
    });
    // Post edit save job
    $('.btn-save').on('click', function(event) {
        if ($(".j-form-validate").valid()) {
            var url = "serial-info/edit";
            var data = $("#form-serial-info-edit").serialize();

            ajaxSubmit(url, data, event);
        }
    });
});