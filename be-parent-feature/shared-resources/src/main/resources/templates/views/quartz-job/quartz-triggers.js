
  $(document).ready(function() {
	  	// multiple select
		$('select[multiple]').multiselect({
			columns : 1,
			placeholder : SEARCH_LABEL,
			search : true
		});
		
		// on click search
		$("#btnSearch").on('click', function(event) {
			onClickSearch(this, event);
		});

		//onRenew();

  });
  
  function redirectEdit(triggerName,schedName){
	  var url = 'system/quartz-job/edit';
	  $.ajax({
			type : "GET",
			url : BASE_URL + url,
			data : {schedName : schedName, triggerName : triggerName},
			beforeSend: blockbg(),
			success : function(res) {
				var content = $(res).find('.body-content');
				$(".main_content").html(content);
				window.history.pushState('', '', url);
			},
			error : function(xhr, textStatus, error) {
				console.log(xhr);
				console.log(textStatus);
				console.log(error);
			}
			
		});
  }

  function playTriggerButton(triggerName) {
    window.location.href = BASE_URL + 'system/quartz-job/play?triggerName=' + triggerName;
  }

  function pauseTriggerButton(triggerName) {
    window.location.href = BASE_URL + 'system/quartz-job/pause?triggerName=' + triggerName;
  }

  function onRenew() {
	setTimeout(function(){ window.location.href = BASE_URL + "system/quartz-job"; }, 60000);
    
  }
  
  /**
   * on click button search
   */
  function onClickSearch(element, event) {
  	
  	setDataSearchHidd();
  	
  	ajaxSearch("system/quartz-job/ajaxList", setConditionSearch(), "tableList", element, event);
  }
  
  
  /**
   * setConditionSearch
   */
  function setConditionSearch() {
  	var condition = {};
  	condition["searchValue"] = $("#fieldSearchHidd").val();
  	condition["searchKeyIds"] = $("#fieldValuesHidd").val();
  	return condition;
  }

  /**
   * setDataSearchHidd
   */
  function setDataSearchHidd() {
  	$("#fieldSearchHidd").val($("#searchValue").val());
  	$("#fieldValuesHidd").val($("select[name=searchKeyIds]").val());
  }
 
  
