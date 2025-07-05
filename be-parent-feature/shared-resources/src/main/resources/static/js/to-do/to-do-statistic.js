$(document).ready(function(){
	countInit(10,10,10,10);
	loadCountToCounter();
});

function getCountForToDoStatistic(){
//	event.preventDefault();
//	var hasArchive = $("#hasArchive")[0].checked;
	$.ajax({
		type: "GET",
		url: BASE_URL + "todo/ajaxList/getCountForToDo",
		global: false,
//		data: {hasArchive : hasArchive},
	    beforeSend: function(xhrObj) {
	    	var token = $("meta[name='_csrf']").attr("content");
		  	var header = $("meta[name='_csrf_header']").attr("content");
		  	xhrObj.setRequestHeader(header, token);
	    },
		success : function(data) {
			var incommingCount = data.incommingCount;
			var outgoingCount = data.outgoingCount;
			var draftCount = data.draftCount;
			var relatedCount = data.relatedCount;
//			$("#noProcessCount").counter({
//				autoStart: true, // true/false, default: true
//				duration: 2500, // milliseconds, default: 1500
//				countFrom: 0,// start counting at this number, default: 0
//				countTo: incommingCount,// count to this number, default: 0
//				runOnce: false,// only run the counter once, default: false
//				placeholder: "0", // replace the number with this before counting,
//				easing: "easeOutCubic", // easing effects
//				onStart: function() {}, // callback on start of the counting
//				onComplete: function() {
//					$(this).text("10000");
//				} // callback on completion of the counting
//				});

			$('.counter').stop( true, false );
			setTimeout(countTodo(incommingCount,outgoingCount,draftCount,relatedCount), 300);
		},
		error : function(xhr, textStatus, error) {
			console.log(xhr);
			console.log(textStatus);
			console.log(error);
		}
	});	
}

function loadCountToCounter() {
	getCountForToDoStatistic();
}

function countTodo(incommingCount,outgoingCount,draftCount,relatedCount){
	$("#noProcessCount").data('count', incommingCount);
	$("#overdueCount").data('count', outgoingCount);
	$("#resolvedCount").data('count', draftCount);
	$("#relatedCount").data('count', relatedCount);
	
	$('.counter').each(function() {
		var $this = $(this), countTo = $this.data('count');

		$({
			countNum : $this.text()
		}).animate({
			countNum : countTo
		},
		{
			duration : 1500,
			easing : 'easeOutCubic',
			step : function() {
				$this.text(Math.floor(this.countNum));
			},
			complete : function() {
				$this.text(this.countNum);
			}
		});
	});
}
function countInit(incommingCount,outgoingCount,draftCount,relatedCount){
	$("#noProcessCount").data('count', incommingCount);
	$("#overdueCount").data('count', outgoingCount);
	$("#resolvedCount").data('count', draftCount);
	$("#relatedCount").data('count', relatedCount);
	
	$('.counter').each(function() {
		var $this = $(this), countTo = $this.data('count');

		$({
			countNum : $this.text()
		}).animate({
			countNum : countTo
		},
		{
			duration : 1500,
			easing : 'easeOutCubic',
			step : function() {
				$this.text(Math.floor(this.countNum));
			}
		});
	});
}

//function countInit(incommingCount,outgoingCount,draftCount,relatedCount){
////	$("#noProcessCount").data('count', incommingCount);
////	$("#overdueCount").data('count', outgoingCount);
////	$("#resolvedCount").data('count', draftCount);
////	$("#relatedCount").data('count', relatedCount);
//	
//	$('.counter').each(function() {
//		var $this = $(this);
//		
//		$({
//			countNum : 50000000
//		}).animate({
//			countNum : 0
//		},
//		{
//			duration : 30000000,
//			easing : 'easeOutCubic',
//			step : function() {
//				$this.text(Math.floor(this.countNum));
//			}
//		});
//	});
//}