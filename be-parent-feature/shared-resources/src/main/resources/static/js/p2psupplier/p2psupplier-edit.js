
$(function(){
    
    // check for payment checkbox event
    $('#lockForPayment').change(function(){
        if($('#lockForPayment').is(':checked')){
              $('#lockForPayment').val(1);
         }else{
              $('#lockForPayment').val(0);
         }
    });
    
	//on click cancel
	$('#cancel').on('click', function (event) {
		event.preventDefault();
		var url = BASE_URL + "p2psupplier/list";
		
		// Redirect to page list
		ajaxRedirect(url);
	});
	
	//on click list
	$('#linkList').on('click', function (event) {
		event.preventDefault();
		var url = BASE_URL + "p2psupplier/list";

		// Redirect to page list
		ajaxRedirect(url);
	});
	
	// on click add
	$("#add").on("click", function(event) {
		var url = BASE_URL + "p2psupplier/edit";
		// Redirect to page add
		ajaxRedirect(url);
	});	

	// Post edit save
	$('.btn-save').on('click', function(event) {
	    var total = $('#totalPercent').val();
	    if(total < 0 || total >100){
	        return;
	    }
		if ($(".j-form-validate").valid()) {
			var url = "p2psupplier/edit";
			var condition = $("#form-edit").serialize();
			ajaxSubmit(url, condition, event);
		}			
		goTopPage();
	});
	
	$('.j-btn-delete').on('click', deleteRow );
	
	$('.datepicker').datepickerUnit({
		format: DATE_FORMAT,
        changeMonth: true,
        changeYear: true,
        autoclose: true,
        keyboardNavigation : true
	});
	
	$("#bankSelect").select2();

	function goTopPage(){
		$("html, body").animate({ scrollTop: 0 }, "1");
	}
	
});

function deleteRow(num) {
    $("#"+num).remove();
}

function calcTotal(){
    var num = $('.table-payment tbody tr').length;
    var total = 0;
    for(var i=0; i<num;i++){
        var currentRow = parseInt( $("input[name='listPaymentTerm["+i+"].paymentPercent']").val() );
        total += currentRow;
    }
    $('#totalPercent').val(total);
    if( total > 100 || total < 0){
        $('#totalPercent').after("<br/><span class='error'>Total must equal 100%</span>");
    } else {
        $('#totalPercent').parent().find("br").remove();
        $('#totalPercent').parent().find("span").remove();
    }
}

function addNewRow(){
//    event.preventDefault();
    var num = $('.table-payment tbody tr').length;
    $(".table-payment tbody").append("<tr id="+num+">" +
            "<td class='text-center'>"+ 
                "<a class='glyphicon glyphicon-remove-sign font-size-20 red j-btn-delete' onclick='deleteRow("+num+")'></a>"
            +"</td>" +
            "<td> <select class='form-control' id='listPaymentTerm"+num+".paymentType' name='listPaymentTerm["+num+"].paymentType'>" +
                "<option value='Deposit' selected='selected'>Deposit</option>"+
                "<option value='Payment'>Payment</option>"
            +"</select>"
            +"</td>" +
            "<td class='text-center'>"+
            "<input onchange='calcTotal()' class='j-number required' id='listPaymentTerm"+ num +".paymentPercent' name='listPaymentTerm["+ num +"].paymentPercent' value=''>"
            +"</td>" +
            "<td class='text-center'>"+
            "<input id='listPaymentDescription"+ num +".paymentDescription' name='listPaymentTerm["+ num +"].paymentDescription' value=''>"
            +"</td>" +
    "</tr>");
}