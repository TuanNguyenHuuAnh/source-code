$(document).ready(function () {
	
	$(".j-comment-list").each(function(index, item) {
		var commentDiv = $(item).parent(".j-comment");
    	
    	var commentReference = $(commentDiv).find("input[name='commentReference']").val();
    	var commentReferenceId = $(commentDiv).find("input[name='commentReferenceId']").val();
    	
    	var commentDto = createCommentDto(null, commentReference, commentReferenceId, null, true);
		loadDataComments(item, commentDto);
	});
	
	$(document).on('click', '.j-btn-comment-delete', function(event) {
	    event.preventDefault();
	    
	    var commentDiv = $(".j-comment-content").closest(".j-comment");
	    // Prepare data
	    var row = $(this).closest('div.media-comment');
	    var id = row.data("id");
	    var commentReference = $(commentDiv).find("input[name='commentReference']").val();
    	var commentReferenceId = $(commentDiv).find("input[name='commentReferenceId']").val();
	    var commentDto = createCommentDto(id, commentReference, commentReferenceId, null, true);
	    var renderDiv = $(commentDiv).find('.j-comment-list');
		deleteCommentById(renderDiv, commentDto);
	});
		
	
	$(document).on('click', '.j-btn-comment-edit', function(event) {
	    event.preventDefault();
	    
	    var commentDiv = $(".j-comment-content").closest(".j-comment");
	    // Prepare data
	    var row = $(this).closest('div.media-comment');
	    var id = row.data("id");
	    var commentReference = $(commentDiv).find("input[name='commentReference']").val();
    	var commentReferenceId = $(commentDiv).find("input[name='commentReferenceId']").val();
	    var commentDto = createCommentDto(id, commentReference, commentReferenceId, null, true);
	    var rowContent = $(row).find('.col-sm-11'); 
	    $(rowContent).html(
	    '<textarea name="commentsEdit" class="width_area2 form-control j-comment-content-edit" onchange="abc(id)" style="min-height: 70px"></textarea> <a class="btn btn-sm close-edit btn-delete" style="padding: 0px 4px;"> <i class="fa fa-close fa-lg"></i>&nbsp;</a> ');
	    
	});
	
	$(".j-comment-content-edit").keyup(function (event) {
		var content = $(this).val();
		
        if(event.which == 13 && content.trim()) {
        	var commentDiv = $(".j-comment-content").closest(".j-comment");
        	var commentReference = $(commentDiv).find("input[name='commentReference']").val();
        	var commentReferenceId = $(commentDiv).find("input[name='commentReferenceId']").val();
        	
        	var commentDto = createCommentDto(null, commentReference, commentReferenceId, content, true);
        	editCommentById(commentDto);
        }
    });
	
	$(document).on('click', '.close-edit', function(event)  {
		var commentDiv = $(".j-comment-content").closest(".j-comment");
    	var renderDiv = $(commentDiv).find(".j-comment-list");
    	var commentReference = $(commentDiv).find("input[name='commentReference']").val();
    	var commentReferenceId = $(commentDiv).find("input[name='commentReferenceId']").val();
		var commentDto = createCommentDto(null, commentReference, commentReferenceId, null, true);
		loadDataComments(renderDiv, commentDto);
    });
	
	$(".j-comment-content").keyup(function (event) {
		var content = $(this).val();
		// replace " " and enter char
		content = content.replace(/(\r\n|\n|\r)/gm, "");
		
        if(event.which == 13 && content.trim()) {
        	var commentDiv = $(".j-comment-content").closest(".j-comment");
        	var commentReference = $(commentDiv).find("input[name='commentReference']").val();
        	var commentReferenceId = $(commentDiv).find("input[name='commentReferenceId']").val();
        	
        	var commentDto = createCommentDto(null, commentReference, commentReferenceId, content, true);
        	var renderDiv = $(commentDiv).find(".j-comment-list");
        	ajaxAddComment(renderDiv, commentDto);
            $(this).val("");
        }
    });
	$(document).on('click', ".widgetCommentToggleLog",function(){
			var isFirst = $(this).attr("isFirstClickComment");
			if(isFirst != null){
				ajaxLoadAllComment(event);
				$(this).removeAttr("isFirstClickComment");
				$('.widgetCommentToggleLog').toggle();
			}else{
				toggleComment();
			}
	});	
});

function toggleComment() {
	$(".widgetCommentHiddenComment").toggle("fast", "swing");
	$('.widgetCommentToggleLog').toggle();
}
function ajaxLoadAllComment(event){
	event.preventDefault();
	
	var commentDiv = $(".j-comment-content").closest(".j-comment");
	var commentReference = $(commentDiv).find("input[name='commentReference']").val();
	var commentReferenceId = $(commentDiv).find("input[name='commentReferenceId']").val();
	
	var commentDto = createCommentDto(null, commentReference, commentReferenceId, null, false);
	commentDto["isPaging"] = 'false';
	var renderDiv = $(commentDiv).find(".j-comment-list");

	loadDataComments(renderDiv, commentDto);
}

function deleteCommentById(renderDiv, commentDto) {
	$.ajax({
        type: "POST",
        url: BASE_URL + "comment/ajax/delete",
        data: commentDto,
        success : function(data) {
        	//console.log(data);

        	loadDataComments(renderDiv, commentDto);
		},
		error : function(xhr, textStatus, error) {
			console.log(xhr);
			console.log(textStatus);
			console.log(error);
		}
    });
}

function createCommentDto(id, reference, referenceId, content, isPaging) {
	var commentDto = {};
	commentDto["id"] = id;
	commentDto["reference"] = reference;
	commentDto["referenceId"] = referenceId;
	commentDto["content"] = content;
	commentDto["isPaging"] = isPaging;
    return commentDto;
}

function ajaxAddComment(renderDiv, commentDto) {
	$.ajax({
        type: "POST",
        url: BASE_URL + "comment/ajax/add",
        data: commentDto,
        success : function(data) {
        	//console.log(data);

        	loadDataComments(renderDiv, commentDto);
		},
		error : function(xhr, textStatus, error) {
			console.log(xhr);
			console.log(textStatus);
			console.log(error);
		}
    });
}

function loadDataComments(renderDiv, commentDto) {
	
	$.ajax({
        type: "POST",
        url: BASE_URL + "comment/ajax/list",
        global: false,
	    beforeSend: function(xhrObj) {
	    	var token = $("meta[name='_csrf']").attr("content");
		  	var header = $("meta[name='_csrf_header']").attr("content");
		  	xhrObj.setRequestHeader(header, token);
	    },
        data: commentDto,
        success : function(data) {       
        	$(renderDiv).html(data);
        	var commentSize = $(document).find("#commentSize").val( );
        	if (commentSize >= 5){
        		$('#widgetCommentViewAllComment').removeClass('hidden');
        	}else{
        		$('#widgetCommentViewAllComment').addClass('hidden');
        	}
			
		},
		error : function(xhr, textStatus, error) {
			console.log(xhr);
			console.log(textStatus);
			console.log(error);
		}
    });
}

function editCommentById(renderDiv,commentDto){
	$.ajax({
        type: "POST",
        url: BASE_URL + "comment/ajax/edit",
        data: commentDto,
        success : function(data) {

        	loadDataComments(renderDiv, commentDto);
		},
		error : function(xhr, textStatus, error) {
			console.log(xhr);
			console.log(textStatus);
			console.log(error);
		}
    });
}

function abc(){
	var content = $('.j-comment-content-edit').val();
	 var id = $('.j-comment-content-edit').closest('div.media-comment').data("id");
    if(content.length > 0) {
    	var commentDiv = $(".j-comment-content").closest(".j-comment");
    	var commentReference = $(commentDiv).find("input[name='commentReference']").val();
    	var commentReferenceId = $(commentDiv).find("input[name='commentReferenceId']").val();

    	var commentDto = createCommentDto(id, commentReference, commentReferenceId, content, true);
    	var renderDiv = $(commentDiv).find(".j-comment-list");
    	editCommentById(renderDiv,commentDto);
    }
}