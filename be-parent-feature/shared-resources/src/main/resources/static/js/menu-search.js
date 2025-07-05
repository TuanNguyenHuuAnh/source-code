var elementClick = null;
$(document).ready(function(){
	
	$('#leftMenuSearch').click(function(event) {
		elementClick = $(event.target).text();
	});
	$(document).mouseup(function(e) {
		var value = '#leftMenuSearch';
		 if (!$(value).is(e.target) // if the target of the click isn't the container...
		            && $(value).has(e.target).length === 0){
			 elementClick = null;
		 }
		
	});
	
	$(document).on('keyup', '.select2-search__field', function (e) { 
		if(elementClick == null){
			return false;
		}
		//elementClick = null;
	//$('#menuSearch').keyup(function(){
//		var temp = $('#menuSearch').val().toLowerCase();
		
		delay(function(){
			var temp = $('.select2-search__field').val().toLowerCase();
			console.log(temp);
			if(temp === ''){
				 $SIDEBAR_MENU.find('li ul').slideUp();
				 return;
			}	
		    //remove all class active
		    $('li').removeClass('active');
	
		    //loop level 1
		    $.each(menuDto.listSubMenu, function(index, item) {
			    if(item.listSubMenu != null && item.listSubMenu.length > 0){
				    var listChild = item.listSubMenu;
				    //loop level 2
				    $.each(listChild, function(index, child) {
				    	var aHref = $('a[href="/jCanary'+child.url+'"]');
				    	if( child.menuName.toLowerCase().indexOf(temp) != -1 ){
				    		aHref.closest('li').addClass('active');
				    		aHref.parents("ul").slideDown(function() {
								setContentHeight();
							});
				   		 }
					    if(child.listSubMenu != null && child.listSubMenu.length > 0){
						    var listNode = child.listSubMenu ;
						    //loop level 3
					    	$.each(listNode, function(index, node) {
					    		var aHref = $('a[href="/jCanary'+node.url+'"]');
					    		if( node.menuName.toLowerCase().indexOf(temp) != -1 ){
						    		aHref.closest('li').addClass('active');
						    		aHref.parents('ul').slideDown(function() {
										setContentHeight();
									});
						   		 }
					    	});//end loop lv3
					    }
				    });//end loop lv2
				}
			}); //end loop lv1
			
	 		var $ul3 = [];
	 		var $ul2 = [];
			//close lv3
			$('#sidebar-menu').find('ul .child_menu.child_menu2').each(function(index, ul){
				var x = $(ul).children('li.active').length;
				if(x === 0){
					$ul3.push($(ul));
				}
			})
			//close lv2
			$('#sidebar-menu').find('ul .child_menu').each(function(index, ul){
				var ele = $(ul).find('.child_menu2');
				var check = $(ele).children('li.active').length;
				if(check > 0)
					return;
				var x = $(ul).children('li.active').length;
				if(x === 0){
					$ul2.push($(ul));
				}
			})
			
			$.each($ul3, function( index, value ) {
				value.slideUp(function() {
					setContentHeight();
				});
			});
			$.each($ul2, function( index, value ) {
				value.slideUp(function() {
					setContentHeight();
				});
			});
		}, 100 );//delay when typping
	});
	
	
	$("#menuSearch").select2();
	
	$("#menuSearch").change(function(){
		var link = $(this).val();
		link = link.split('_/')[1];
		var url = BASE_URL + link;
		ajaxRedirect(url);
	})	
});

function setContentHeight() {
	// reset height
	$MAIN_CONTENT.css('min-height', $(window).height());

	var bodyHeight = $BODY.outerHeight(),
		footerHeight = $BODY.hasClass('footer_fixed') ? -10 : $FOOTER.height(),
		leftColHeight = $LEFT_COL.eq(1).height() + $SIDEBAR_FOOTER.height(),
		contentHeight = bodyHeight < leftColHeight ? leftColHeight : bodyHeight;

	// normalize content
	contentHeight -= $NAV_MENU.height() + footerHeight;
	$MAIN_CONTENT.css('min-height', contentHeight);
};

var delay = (function(){
	  var timer = 0;
	  return function(callback, ms){
	    clearTimeout (timer);
	    timer = setTimeout(callback, ms);
	  };
})();