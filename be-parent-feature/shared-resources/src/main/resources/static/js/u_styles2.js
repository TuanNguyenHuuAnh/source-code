/**
 * ===== HEADER & MENU LEFT =====
 */

var menu_left_bar = $('.sidebar-menu');
var CURRENT_URL = window.location.href.split('#')[0].split('?')[0],
$BODY = $('body'),
$MENU_TOGGLE = $('#menu_toggle'),
$SIDEBAR_MENU = $('#sidebar-menu'),
$SIDEBAR_FOOTER = $('.sidebar-footer'),
$LEFT_COL = $('.left_col'),
$MAIN_CONTENT = $('.main_content'),
$NAV_MENU = $('.nav_menu'),
$FOOTER = $('footer'),
$LOGOLEFT = $('.site_title img');



function setActiveMenu() {
	let CURRENT_URL = window.location.pathname;
	let findex = CURRENT_URL.indexOf(BASE_URL);
	let lindex = CURRENT_URL.length;
	let url = CURRENT_URL.substring(findex, lindex);
	// Phuc Nguyen
	// Auto detect active menu
	// 10/01/2018
	if (url != null && url != '') {

		// get all link in menu
		let mostSimilar;
		let tmpSimilar = 0;
		let count;
		let countSimalar;

		// check to get the most similar url to check active
		menu_left_bar.find('a').each(function() {
			// reset counter
			count = 0;
			countSimalar = 0;
			// find the shorter length to check
			let subUrl = $(this).attr('href');
			if(subUrl === undefined){
				subUrl = '';
			}
			let maxSize = url.length > subUrl.length ? subUrl.length : url.length;
			// counter number of similar char
			while (count < maxSize && subUrl.charAt(count) == url.charAt(count)) {
				countSimalar++;
				count++;
			}

			// check if it is the most similar url
			if (countSimalar > tmpSimilar) {
				mostSimilar = subUrl;
				tmpSimilar = countSimalar;
			}

		});

		// continue to get and set active
		var ele = menu_left_bar.find('a[href="' + mostSimilar + '"]').first().parents('li');
		var eleLength = $(ele).length;
		// li element will run from n->0
		var n = i = eleLength;

		$(ele).each(function() {
			$(this).addClass('menu-open');
			if (i == n) {
				$(this).find('a').addClass('active');
			} else {
				$(this).find('.menu-open').parent('ul').first().css(
						'display', 'block');
			}
			i--;

		});
	}
}

$(document).ready(function() {
	
	var x = $("#searchLeftBox").select2();
	// console.log(x);
	setActiveMenu();
	$('.sidebar-menu li a').unbind('click').bind('click',function(event) {
		$(document).unbind('keypress');
		event.preventDefault();
		var url = $(this).attr('href');
		var mennuType = $(this).data("menu-type");
		/* download file from menu don't use ajax */
		if (mennuType == '3'){
			window.open(url,'_blank');
		} else if (url == BASE_URL + "user-guide/download-user"){
			$.ajax({
				type: "POST",
				url: BASE_URL + "user-guide/validate-download-userguide",
				success : function(data, textStatus, request) {
					var msgFlag = request.getResponseHeader("msgFlag");
					if( "1" == msgFlag ) {
						doExport("user-guide/download-user",null);
					}else if( "0" == msgFlag ){
						popupAlert(data);
					}
				},
				error : function(xhr, textStatus, error) {
					console.log(xhr);
					console.log(textStatus);
					console.log(error);
				}
			});
			
		}else{
			if (null != url
					&& url != "javascript:void(0)") {
				// remove active class
				menu_left_bar.find('.active')
						.parent('li').find('a')
						.removeClass('active');
				$(this).addClass('active');
				
				// remove active class
				
				// menu_left_bar.find('.menu-open').removeClass('menu-open');
				// $(this).closest('li').addClass('menu-open');
				
				$.ajax({
					type : "GET",
					url : url,
					success : function(data) {
						var content = $(data).find('.body-content');
						$(".main_content").html(content);
						window.history.pushState('', '', url);
						$(window).scrollTop(0);
						/*$.ajax({
							type : "GET",
							url : BASE_URL + "menu/breadcrumb",
							data : {
								'pathname' : url
							},
							complete : function(data) {
								$("#breadcrumb").html(data.responseText);
							}
						});*/
					},
					error : function(xhr,
							textStatus, error) {
						unblockbg();
						console.log(xhr);
						console.log(textStatus);
						console.log(error);
					}
				});
			}
		}
		
	});

		$("#searchLeftBox").change(function() {
			var link = $(this).val();
			link = link.split('_')[1];
			window.location.href = BASE_URL + link;
		})

	});
/**
 * ===== END HEADER & MENU LEFT =====
 */

