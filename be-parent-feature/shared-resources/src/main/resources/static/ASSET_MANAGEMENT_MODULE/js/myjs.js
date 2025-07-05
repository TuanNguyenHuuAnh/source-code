//<!-- page 4 -->
/*$(document).on('click', '.panel-heading span.clickable', function(e){
    var $this = $(this);
	if(!$this.hasClass('panel-collapsed')) {
		$this.parents('.panel').find('.panel-body').slideUp();
		$this.addClass('panel-collapsed');
		$this.find('i').removeClass('glyphicon glyphicon-menu-up').addClass('glyphicon glyphicon-menu-down');
	} else {
		$this.parents('.panel').find('.panel-body').slideDown();
		$this.removeClass('panel-collapsed');
		$this.find('i').removeClass('glyphicon glyphicon-menu-down').addClass('glyphicon glyphicon-menu-up');
	}
})*/
//<!-- end page 4 -->

 

//<!-- loading --> 
var myVar;

function myFunction() {
    myVar = setTimeout(showPage, 3000);
}

function showPage() {
  document.getElementById("loader").style.display = "none";
  document.getElementById("myDiv").style.display = "block";
}
//<!-- end loading  --> 

/*$(document).ready(function() {
	// Registry event plugins
	initPlugins();
});

function initPlugins() {
	$(".select2").select2();
	
	$('.datepicker').datepicker({
	      autoclose: true,
	      format: DATE_FORMAT.toLowerCase(),
	      keyboardNavigation: true
	});
}*/

//fix menu top
/*$(window).scroll(function () {
    if ($(this).scrollTop() > 250) {
         $('.nav-container').addClass("fix-nav");
	  $('body').addClass("fix-body");
    } else {
	  $('.nav-container').removeClass("fix-nav");
	  $('body').removeClass("fix-body");
    }	
});*/
// end fix menu top


// login page



