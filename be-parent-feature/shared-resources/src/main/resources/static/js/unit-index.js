// custom scroll bar
$('.table-fixed-head table tbody').mCustomScrollbar();
$('.tbl-responsive').mCustomScrollbar({
  axis:"x"
});
$('.selectpicker').selectpicker();
$(".sidebar").mCustomScrollbar();
$(".menu-notifi").mCustomScrollbar();
// check cookie skin


$(function(){
    // scroll body
	ScrollFixed();
    $(window).scroll(function(){
    	ScrollFixed();
	});
    $(".sidebar-toggle").click(function(){
        $(".menu-open>ul").slideDown();
    });
    
    $(document).on("click", ".resize-popup", function(){
    //$(".resize-popup").click(function(){
		if($("body").hasClass("popup-resize")){
			$("body").removeClass("popup-resize");
		}
		else{
			$("body").addClass("popup-resize");
		}
	});
    //change password
    $("#linkChangePassword").click(function(e){
		  event.preventDefault();
		  var url = BASE_URL + "account/change-password";
		  // Redirect to page list
		  ajaxRedirect(url);
    })
    
    // Change style
    $('.btn-change-color').on('click', function(event) {
    	event.preventDefault();
    	changeStyle(window.location.href, $(this).attr('name'));
    });
    
    // Change style
    $('.change-lang').on('click', function(event) {
    	event.preventDefault();
    	changeLang(window.location.href, $(this).data('lang-code'));
    });
    
    //show info
    $("#infoUser").click(function(e){
		  e.preventDefault();
		  var url = BASE_URL + "account/info";
		  window.location.href = url;
    })
    //feedback user
    $("#linkFeedback").click(function(e){
		  event.preventDefault();
		  var url = BASE_URL + "feedback/list";
		  // Redirect to page list
		  ajaxRedirect(url);
    })
});
function ScrollFixed(){
	var scrolls = $(window).scrollTop();
   	if (scrolls >= 10){
       $("body").addClass("fixed-head");
  	}
   	else{
  		$("body").removeClass("fixed-head");
  	}
}
