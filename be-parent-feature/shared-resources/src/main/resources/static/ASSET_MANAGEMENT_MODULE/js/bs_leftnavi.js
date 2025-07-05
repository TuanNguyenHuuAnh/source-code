$(document).ready(function () {
    var nav = function () {
        $('.menu_left > li > a').click(function () {
            var gw_nav = $('.menu_left');
            $('.menu_left > li > ul > li').removeClass('active');

            var checkElement = $(this).parent();
            var ulDom = checkElement.find('.gw-submenu1')[0];

            if (ulDom == undefined) {
                checkElement.addClass('active');
                $('.menu_left').find('li').find('ul:visible').slideUp();
                return;
            }
            if (ulDom.style.display != 'block') {
                gw_nav.find('li').find('ul:visible').slideUp();
                gw_nav.find('li.init-arrow-up').removeClass('init-arrow-up').addClass('arrow-down');
                gw_nav.find('li.arrow-up').removeClass('arrow-up').addClass('arrow-down');
                checkElement.removeClass('init-arrow-down');
                checkElement.removeClass('arrow-down');
                checkElement.addClass('arrow-up');
                checkElement.find('.gw-submenu1').slideDown(300); 
            } else {
                checkElement.removeClass('init-arrow-up');
                checkElement.removeClass('arrow-up');
                checkElement.addClass('arrow-down');
                checkElement.find('.gw-submenu1').slideUp(300);
            }
        });
        
        $('.menu_left li a').click(function() {

            var chsk =$(this).hasClass('active'); 
            var chp =$(this).parent().hasClass("active");   
            $(this).parents('ul:first').children('li').removeClass('active');
            $(this).parent().toggleClass("open");   
            if (!chsk) {
                 
                $(this).parent().addClass('active'); 
                
                $(".gw-submenu1 li.active>.gw-submenu2").slideDown();
                $(".gw-submenu1 li:not('.active')>.gw-submenu2").slideUp();
                if(chp){
                    $(this).parent().removeClass("active");
                    $(".gw-submenu1 li:not('.active')>.gw-submenu2").slideUp();
                }
            }  
        });
        
        $('.menu_left li a').click(function(event) {
        	event.preventDefault();
        	var url = $(this).attr('href');
        	if(null != url && url != "javascript:void(0)"){
        		$.ajax({
                    type  : "GET",
                    url   : url,
                    success: function(data){
                    	var content =  $(data).find('.body-content');
                    	$(".body-content-wrapper").html(content);
                    	window.history.pushState('', '', url);
                    	
                        /*
                        $.ajax({
                           type  : "GET",
                           url   : BASE_URL + "menu/breadcrumb",
                           data  : {'pathname' : url},
                           complete: function (data) {
                           	$(".breadcrumb").html(data.responseText);
                           	check_menu_toggle();
                        	check_mobile_screen();
                        	get_h_contain();
                           }
                       });
                       */
                    }
                }); 
        	}
        });
    };
    nav();
});

