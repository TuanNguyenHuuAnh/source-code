/* ajax load table */
function loadTable() {
	var data = $('[role="form"]').serialize();
    $.ajax({
        url: BASE_URL + 'holidays/ajax',
        data: $('[role="form"]').serialize(),
        type: 'post',
        success: function (repon) {
            $('#tableList').html(repon);
        }
    });
    
}

function clearCombobox(element) {
    $(element).find('option').remove().end();
}
/*
$(window).on('load', function () {
	loadTypeCalendar();
});
*/
function loadTypeCalendar() {
	clearCombobox('#calendarType');
	var year = $('#year-calendar').val();
	var companyId = $('#companyId').val();
	var data = {
			"year" : year,
			"companyId" : companyId
		}
	blockbg();
	$.ajax({
	    url: BASE_URL + 'holidays/getListTypeCalendar',
	    data: data,
	    type: 'post',
	    success: function (data) {
	      	unblockbg();
	       	$('#calendarType').select2({data: data});
	       	
	       	let html = '';
	       	if (data != null && data.length > 0){
                for (let i=0;i<data.length;i++){
                    html = html + '<option value=' + data[i].id + '>' + data[i].text + '</option>';
                }
            }
	       	$('#calendarTypeId').html(html);
	       	
	       	loadTable();
	    }
	});
}

function clearSelectedBox() {
	$('#companyId').val("");
	var isAdmin = $('#isAdmin').val();
	if(isAdmin == "false") {
		loadTypeCalendar();
	}
	else {
		clearCombobox('#calendarType');
	}
}


$(document).ready(function () {

	$('body').find('.modal-backdrop').remove();
	var year = $('#year-calendar').val();
	var companyId = $('#companyId').val();
	var calendarType = $('#calendarType').val();
	// set title holiday
	  $('#tableHoliday tbody tr td.holiday').each(function() {
		  $(this).prop('title', $(this).attr('data'))
		})
    $('#tableHoliday tbody tr')
            .each(
                    function () {
                        var td = $(this).find('td').length;
                        for (var i = 0; i < 32 - td; i++) {
                            $(this).append($('<td style="color:red;font-weight:bold;background:#ccc" class="text-center">X</td>'))
                        }
                    });
	  
	  $('.calendar-date').click(function() {
	      var chk3 = $(this).text() == 'H' ? 'checked' : '';
	      var chk1 = $(this).text() == 'AM' ? 'checked' : '';
	      var chk2 = $(this).text() == 'PM' ? 'checked' : '';
	      var chk0 = $(this).text() == '' ? 'checked' : '';
	      var date = $(this).data("calendar-date");
	      var description = '';
	      var id = '';
	      $.ajax({
	          url: BASE_URL + 'holidays/getVietnameseHoliday',
	          data: {
	        	  "vietnameseHolidayDate" : date,
	        	  "companyId" : companyId,
	        	  "calendarType" : calendarType
	        	  },
	          type: 'post',
	          success: function (data) {
	        	  var calendarTypeId = $('#calendarType').val();
	        	  var companyId = $('#companyId').val();
	        	  var startTimeMorning = $('#startTimeMorning').val()
	        	  var endTimeMorning = $('#endTimeMorning').val()
	        	  var startTimeEvening = $('#startTimeEvening').val()
	        	  var endTimeEvening = $('#endTimeEvening').val()
	        	 	var listDate = $('#listDate').val()
	        	  
	              if(data != ""){
	                  description = data.description;
	                  id = data.id;
	                  startTimeMorning= data.startTimeMorning
	                  endTimeMorning= data.endTimeMorning
	                  startTimeEvening= data.startTimeEvening
	                  endTimeEvening= data.endTimeEvening
	                  listDate = data.listDate
	                  
	              }
	              $('#titleDate').html(date);
	              $('#vietnameseHolidayDate').val(date);
	              $('#fromDateEdit').val(date);
	              $('#toDateEdit').val(date);
	              $('#isHoliday3').prop( "checked", chk3);
	              $('#isHoliday1').prop( "checked", chk1);
	              $('#isHoliday2').prop( "checked", chk2);
	              $('#isHoliday0').prop( "checked", chk0);
	              $('#description').val(description);
	              
	              $('#endTimeMorning').val(endTimeMorning);
	              $('#startTimeMorning').val(startTimeMorning);
	              $('#startTimeEvening').val(startTimeEvening);
	              $('#endTimeEvening').val(endTimeEvening);
	              $('#listDate').val(listDate);
	              
	              $('#calendarTypeDetail').val(calendarTypeId);
	              $('#companyIdDetail').val(companyId);
	              $('#popupHoliday').modal('show');
	              /*bootbox.dialog({
	                  title: 'Setting ' + date,
	                  message: "<form id='frmHoliday'>"
	                          + "<div class='row'>"
	                          + "<div class='col-md-2'><input class='hide' value='"
	                          + date
	                          + "' name='vietnameseHolidayDate'></div>"
	                          + "<div class='col-md-4'><label> <input type='radio' name='isHoliday' value='1' "
	                          + chk1
	                          + "/> Holiday</label></div>"
	                          + "<div class='col-md-4'><label><input type='radio' name='isHoliday' value='0' "
	                          + chk2 + "/> Working day</label></div></div>" 
	                          + "<div class='row'><div class='col-md-2'>Description</div><div class='col-md-10'>" +
	                                  "<textarea class='form-control' value='"+ description+ "' name='description'>"+ description+ "</textarea>" +
	                                  "</div></div>"
	                          + "<div class='col-md-2'><input class='hide' value='"
	                          + companyId
	                          + "' name='companyId'></div>"
	                          + "<div class='col-md-2'><input class='hide' value='"
	                          + calendarType
	                          + "' name='calendarType'></div>"
	                          + "</form>" +
	                                  "<script>" +
	                                   "$('[data-bb-handler=\"save\"]').on('click', function (event) {"+
	                                   "      var url = 'holidays/save';"+
	                                   "      var condition = $('#frmHoliday').serialize();"+
	                                   "      ajaxSubmit(url, condition, event); "+
	                                  "    });"+

	                                  "</script>",
	                  className: "popupHoliday",
	                  buttons: {
	                      save: {
	                          label: "Submit",
	                          className: 'btn-common',
	                          callback: function () {
	                              // Save();
	                          }
	                      },
	                      cancel: {
	                          label: "Cancel",
	                          className: 'btn-cancel',
	                          callback: function () {

	                          }
	                      },
	                  }
	              });*/
	          }
	      });
	  });
	$("#calendarType").on('change',()=>{
		$("#calendarTypeId").val($("#calendarType").val());
	})
	
	$("#companyId").on('change',()=>{
		$("#companyAddId").val($("#companyId").val());
	})
	
	/*$(window).on("popstate", function () {
		// if the state is the page you expect, pull the name and load it.
	  	ajaxRedirect(window.location.pathname.split('?')[0]);
	});*/

});

function loadWorkingTime(){
	 var calendarTypeId = $('#calendarType').val();
	 $.ajax({
		 url: BASE_URL + 'holidays/getCalendarType',
		 data: {calendarTypeId: calendarTypeId},
		 type: 'post',
		 success: function (data) {
		    if(data!=null){
		    	$('#startMorningTime').val(data.startMorningTime);
		    	$('#endMorningTime').val(data.endMorningTime);
		    	$('#startAfternoonTime').val(data.startAfternoonTime);
		    	$('#endAfternoonTime').val(data.endAfternoonTime);
		    }
		 }
	});
}


