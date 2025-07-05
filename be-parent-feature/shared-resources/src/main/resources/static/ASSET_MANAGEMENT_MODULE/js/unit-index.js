// custom scroll bar

$(".main-sidebar").scrollBox();


// check cookie skin
var classColor = $.cookie("color");
if(classColor =='bg-skin-purple'){
    $("body").addClass("bg-skin-purple").removeClass("bg-skin-blue bg-skin-light-blue bg-skin-red");
}
else if(classColor == 'bg-skin-blue'){
    $("body").addClass("bg-skin-blue").removeClass("bg-skin-purple bg-skin-light-blue bg-skin-red");
}
else if(classColor == 'bg-skin-light-blue'){
    $("body").addClass("bg-skin-light-blue").removeClass("bg-skin-blue bg-skin-purple bg-skin-red");
}
else if(classColor == 'bg-skin-red'){
    $("body").addClass("bg-skin-red").removeClass("bg-skin-blue bg-skin-purple bg-skin-light-blue");
}
else if(classColor =='bg-skin-gray'){
    $("body").removeClass("bg-skin-blue bg-skin-purple bg-skin-light-blue bg-skin-red");
}

$(function(){
    // scroll body
	ScrollFixed();
    $(window).scroll(function(){
    	ScrollFixed();
	});
	$(window).resize(function(){
        if($("body").hasClass("sidebar-collapse")){
            $("body").removeClass("sidebar-collapse");
        }
        // $(".treeview").removeClass("menu-open");
        // $(".treeview-menu").removeAttr("style");
    });	
    $(".sidebar-toggle").click(function(){
        $(".menu-open>ul").slideDown();
    });
    $(".sidebar-menu>li:not('.treeview')").click(function(){
    	$(".sidebar-menu>li:not('.treeview')").removeClass("menu-open");
    	$(this).addClass("menu-open");
    });
    // change color skin
    $(".btn-color-blue").click(function(){
        $.cookie("color", "bg-skin-blue", { expires: 365, path: '/' });
    	$("body").addClass("bg-skin-blue").removeClass("bg-skin-purple bg-skin-light-blue bg-skin-red");
    });

    $(".btn-color-light-blue").click(function(){
        $.cookie("color", "bg-skin-light-blue", { expires: 365, path: '/' });
        $("body").addClass("bg-skin-light-blue").removeClass("bg-skin-purple bg-skin-blue bg-skin-red");
    });

    $(".btn-color-purple").click(function(){
         $.cookie("color", "bg-skin-purple", { expires: 365, path: '/' });
    	$("body").addClass("bg-skin-purple").removeClass("bg-skin-blue bg-skin-light-blue bg-skin-red");
    });
    
    $(".btn-color-red").click(function(){
        $.cookie("color", "bg-skin-red", { expires: 365, path: '/' });
   	$("body").addClass("bg-skin-red").removeClass("bg-skin-blue bg-skin-light-blue bg-skin-purple");
   });
    
    $(".btn-color-gray").click(function(){
        $.cookie("color", "bg-skin-gray", { expires: 365, path: '/' });
    	$("body").removeClass("bg-skin-blue bg-skin-purple bg-skin-light-blue bg-skin-red");
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
