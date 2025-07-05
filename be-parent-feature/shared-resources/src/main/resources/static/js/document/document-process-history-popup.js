$(document).ready(function(){
	//VyNT 20200819 add note 4k
	$(".see-more").on('click', function() {
		
		var parent = $(this).closest('.task-comment-dd');
		parent.find('.task-comment-fully-popup').slideToggle("slow");

		parent.find('.task-comment-not-full-popup').slideToggle("slow");
	});
	
	$('.task-comment-fully-popup').slideToggle("slow");
})