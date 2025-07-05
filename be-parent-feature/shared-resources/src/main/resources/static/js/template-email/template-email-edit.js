$(document).ready(function(){
	$('#content').summernote({
		/*hint: {
    		params: LIST_PARAM.replace(/\s+/g, '').split(",").sort(),
    		match: /\B\${(\w*)$/,
    		search: function (keyword, callback) {
    			callback($.grep(this.params, function (item) {
    		    return item.indexOf(keyword) === 0;
    		}));
    		},
    		content: function (item) {
    		    return '${'+item+'}';
    		}  
    	}*/
		callbacks: {
		    onChange: function(contents, $editable) {
		    	$('#templateContent').val(contents);
		    },
		    onBlurCodeview: function(contents, $editable) {
		    	$('#templateContent').val(contents);
	        }
		  },
	});
	
	var content = $('#templateContent').val();
	$('#content').summernote('code', content);
    
    //on click list
	$('#linkList, #btnCancel').on('click', function (event) {
		event.preventDefault();
		var url = BASE_URL + "email/template/list";
		// Redirect to page list
		ajaxRedirect(url);
	});
	
	// on click add
	$("#add").on("click", function(event) {
		var url = BASE_URL + "email/template/edit";
		// Redirect to page add
		ajaxRedirect(url);
	});
	
	// Post edit save job
	$('.btn-save').on('click', function(event) {
		if ($(".j-form-validate").valid()) {
			saveTemplate(event);
		}
	});
	
    jQuery.validator.addMethod("input-text", function (value, element) {
        var re = /^[a-zA-Z0-9]*$/;
        // allow any non-whitespace characters as the host part
        return this.optional(element) || re.test(value) || (value.indexOf(' ') == -1);
    }, MSG_ERROR);
});

function getContent(){
	var templateId = $('#id').val();
	if(null!=templateId && templateId != ""){
		$.ajax({
	        url: BASE_URL + "email/template/template-detail",
	        type: "POST",
	        data : {templateId : templateId},
	        success:function(data) {
		        $('#content').summernote('codeview.deactivate');
		        $('#content').summernote('code', htmlDecode(data.templateContent));
	        },
	        error:function(e) {
	        	console.log(e);
	        }
	    });
	}
}

function saveTemplate(event) {
	event.preventDefault();
	var url = "email/template/edit";
	if ($("#updateTemplate").valid()){
        var dataForm = $("#updateTemplate").serializeArray();
        ajaxSubmit(url, dataForm, event);
    }
}