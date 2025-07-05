$(window).load(function(){
	check_menu_toggle();
	check_mobile_screen();
	$(".menu_full").addClass("menu_full_view");

	$('.master_nav>.menu_full>li').hover(function() {
		$(this).find('a:first').toggleClass('active');  		
	});	
	$('.master_nav>.menu_full>li>li').hover(function() {
		$(this).find('a:first').toggleClass('active');  		
	});	
})

$(".contain_group h1").each(function(){
	var w_h1_title_span = $(this).find(".text_title").width()+55;
	$(this).find(".line_title").width(w_h1_title_span);
});

/////////////get value height for h_contain
function get_h_contain(){
	var w_recent = document.documentElement.clientWidth;
	var h_recent = document.documentElement.clientHeight||$(window).height();
	var maxHeight = Math.max.apply(null, $(".h_contain").map(function ()
	{
	    return $(this).height();
	}).get());
	if(maxHeight<h_recent){
		if(w_recent>991){
			$(".h_contain").height(h_recent-97);
		}
		else{
			$(".h_contain").height(h_recent-63);
		}
	}
	else{
		$(".h_contain").height(maxHeight);
	}
}

///////////////check mobile screen
function check_mobile_screen(){
	var w_recent = document.documentElement.clientWidth;
	
	if(w_recent>991){
		$("body").removeClass("mobile_screen");
	}
	else{
		$("body").addClass("mobile_screen");
	}
}

//////////////get contain left and right is full view on PC
function contain_full(){
	var w_recent = document.documentElement.clientWidth;
	var w_CRfull = w_recent - 250;	
	var w_CRsmall = w_recent - 50;	
	$(".menu_toggle").css("margin-left","0");
	$(".menu_left-list .gw-menu-text").removeClass("hide_text");
	$(".menu_left-list .text_item2").removeClass("hide_text");		
	$(".contain_right").attr("style","margin-left:250px");
	$(".contain_right").width(w_CRfull);
	$(".contain_left").attr("style","width:250px");
	get_h_contain();
}

//////////////get contain left and right is small view on PC
function contain_small(){
	var w_recent = document.documentElement.clientWidth;
	$(".contain_right").attr("style","margin-left:50px");
	$(".contain_right").css("width","100%");
	$(".contain_left").attr("style","width:50px");
	get_h_contain();
}

//////////////get contain left and right is full view on mobile
function contain_full_mobile(){
	var w_recent = document.documentElement.clientWidth;
	var w_CRsmall = w_recent - 50;	
	$(".contain_right").width("100%");
	$(".contain_right").css("margin-left","50px");
	$(".contain_left").css({"width":"50px","z-index":"1"});		
	$(".menu_left-list .gw-menu-text").addClass("hide_text");
	$(".menu_left-list .text_item2").addClass("hide_text");	
	$(".menu_toggle").css("margin-left","0");
	get_h_contain();
}

//////////////get contain left and right is small view on mobile
function contain_small_mobile(){	
	$(".contain_right").attr("style","margin-left:50px");
	$(".contain_right").css("width","100%");
	$(".contain_left").attr("style","width:50px").attr("style","z-index:1");
	get_h_contain();
}


///////////////check menu arrow left or right
function check_menu_toggle(){
	var w_recent = document.documentElement.clientWidth;
	if(w_recent>991){
		$(".menu_toggle").addClass("open");		
		if($(".menu_toggle").hasClass("open")){
			contain_full();	
		}
		else{
			contain_small();
		}
	}
	else{
		$(".menu_toggle").addClass("open");
		if($(".menu_toggle").hasClass("open")){
			contain_full_mobile();
		}
		else{
			contain_small_mobile();
		}
	}
}

/////////////menu toggle for PC
$(".menu_toggle").click(function(){
	$(this).toggleClass("open");
	var w_recent = document.documentElement.clientWidth;	
	var w_CRfull = w_recent - 250;	
	var w_CRsmall = w_recent - 50;
	if($(".menu_toggle_top").hasClass("open")){
		$(".menu_toggle_top").removeClass("open");
		$(".menu_top_small").slideUp();
	}

	if(w_recent>991){
		if($(".menu_toggle").hasClass("open")){
			$(".contain_left").animate({"width":"50px"},150,"linear").animate({"width":"250px"},150,"linear");
			setTimeout(function(){ 
				$(".menu_arrow").removeClass("arrow_left");
				$(".menu_arrow").addClass("arrow_right");
				$(".menu_left-list .gw-menu-text").removeClass("hide_text");
				$(".menu_left-list .text_item2").removeClass("hide_text");
				$(".contain_right").css({"width":w_CRfull,"margin-left":"250px"});	
			}, 150);
		}
		else{
			$(".contain_left").animate({"width":"250px"},150,"linear").animate({"width":"50px"},150,"linear");
			setTimeout(function(){ 
				$(".menu_arrow").removeClass("arrow_right");
				$(".menu_arrow").addClass("arrow_left");	
				$(".menu_left-list .gw-menu-text").addClass("hide_text");	
				$(".menu_left-list .text_item2").addClass("hide_text");	
				$(".contain_right").css({"width":"95%","margin-left":"50px"});
			}, 150);
		}
	}
	else{
		if($(".menu_toggle").hasClass("open")){
			$(".contain_left").animate({"width":"60%"},150,"linear").animate({"width":"50px"},150,"linear");
			$(".contain_right").css("width","95%");
			$(".contain_right").css("margin-left","50px");
			$(".contain_left").css("z-index","1");
			$(".menu_toggle").animate({"margin-left":"60%"},150,"linear").animate({"margin-left":"0"},150,"linear");
			setTimeout(function(){ 
				$(".menu_arrow").removeClass("arrow_left");
				$(".menu_arrow").addClass("arrow_right");
				$(".menu_left-list .gw-menu-text").addClass("hide_text");
				$(".menu_left-list .text_item2").addClass("hide_text");	
			}, 100);	
		}
		else{
			$(".contain_left").css("z-index","2");
			$(".contain_left").animate({"width":"50px"},150,"linear").animate({"width":"60%"},150,"linear");
			$(".contain_right").css("width","100%");
			$(".contain_right").css("margin-left","0");
			$(".menu_toggle").animate({"margin-left":"50px"},150,"linear").animate({"margin-left":"60%"},150,"linear");
			setTimeout(function(){ 
				$(".menu_arrow").removeClass("arrow_right");
				$(".menu_arrow").addClass("arrow_left");	
				$(".menu_left-list .gw-menu-text").removeClass("hide_text");	
				$(".menu_left-list .text_item2").removeClass("hide_text");	
			}, 100);
		}
	}
})

///////////////menu top small screen
$(".menu_toggle_top").click(function(){
	$(this).toggleClass("open");
	if($(".menu_toggle_top").hasClass("open")){
		$(".menu_top_small").slideDown();
	}
	else{
		$(".menu_top_small").slideUp();
	}
	if(!$(".menu_toggle").hasClass("open")){
		$(".menu_toggle").addClass("open");
		$(".contain_left").animate({"width":"60%"},150,"linear").animate({"width":"50px"},150,"linear");
		$(".contain_right").css("width","95%");
		$(".contain_right").css("margin-left","50px");
		$(".contain_left").css("z-index","1");
		$(".menu_toggle").animate({"margin-left":"60%"},150,"linear").animate({"margin-left":"0"},150,"linear");
		setTimeout(function(){ 
			$(".menu_arrow").removeClass("arrow_left");
			$(".menu_arrow").addClass("arrow_right");
			$(".menu_left-list .gw-menu-text").addClass("hide_text");
			$(".menu_left-list .text_item2").addClass("hide_text");	
		}, 100);	
	}
});

/////////////////active menu top hover
$('.menu_full>li').hover(function() {
	$(this).toggleClass("active");  
	$(this).find("a:first").toggleClass("active");		
});	

$(document).ready(function(){
	check_menu_toggle();
	check_mobile_screen();
	get_h_contain();
})
$(window).resize(function(){
	check_menu_toggle();
	check_mobile_screen();
	get_h_contain();
})