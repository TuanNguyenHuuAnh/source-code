(function($) {
	
	var datatables_modal;
	
    datatables_modal = function( options ) {
        // Establish our default settings
        var settings = $.extend({
            url          : null,
            type         : "GET",
            dataDefault  : null,
            setData      : null, // Function
            complete     : null
        }, options);
        console.log(settings)
        var defaults = datatables_modal.defaults;
        var boxTableId = settings.boxTableId;
        var boxMain = settings.boxMain;
        var boxTables = this;
        
        // order by rownum datatable author:phatvt 05/03/2019
        var sortData = boxTables.find(".sort-rownum");
        $(sortData).on('click', function( event ) {
        	event.preventDefault();
        	
        	if ( $.isFunction( settings.setData ) ) {
        		settings.dataDefault = settings.setData.call( this );
        		
            }
        	
        	var elementThis = $(this);

        	var isPopup = elementThis.parents(".modal-dialog").hasClass("modal-dialog");
        	var params = trimData(settings.dataDefault);
        	var page = $('body').find('.pagination > li.active').text();
       
         	if(SORT_ROWNUM === 'ASC'){
         		SORT_ROWNUM = 'DESC';
        	}else{
        		SORT_ROWNUM = 'ASC';
        	}
         	
         	OPTION_SORT = 'sortRowNum';
        	params["page"] = page;
        	params['sortRowNum'] = SORT_ROWNUM;
        	params['sortMessage'] = SORT_MESSAGE;
        	params['optionSort'] = OPTION_SORT;
        	
        	$.ajax({
                url : settings.url,
                type: settings.type,
                data : params,
                success:function(data, textStatus, jqXHR) {
                	if ( $.isFunction( settings.complete ) ) {
                		settings.complete( data );
                    } else {
                    	drawTable( boxTables, data );
                    }
                	
                	anchorTop(isPopup);
                }
            });
        });
        // order by message_error datatable author:phatvt 05/03/2019
        var sortData = boxTables.find(".sort-message");
        $(sortData).on('click', function( event ) {
        	event.preventDefault();
        	
        	if ( $.isFunction( settings.setData ) ) {
        		settings.dataDefault = settings.setData.call( this );
        		
            }
        	
        	var elementThis = $(this);
        	
        	var params = trimData(settings.dataDefault);
        	var page = $('body').find('.pagination > li.active').text();
        	
         	if(SORT_MESSAGE === 'ASC'){
         		SORT_MESSAGE = 'DESC';
        	}else{
        		SORT_MESSAGE = 'ASC';
        	}
        	
         	OPTION_SORT = 'sortMessage';
        	params["page"] = page;
        	params['sortRowNum'] = SORT_ROWNUM;
        	params['sortMessage'] = SORT_MESSAGE;
        	params['optionSort'] = OPTION_SORT;
        	
        	$.ajax({
                url : settings.url,
                type: settings.type,
                data : params,
                success:function(data, textStatus, jqXHR) {
                	if ( $.isFunction( settings.complete ) ) {
                		settings.complete( data );
                    } else {
                    	drawTable( boxTables, data );
                    }
                	
                	//anchorTop( elementThis );
                }
            });
        });
        
        
        // Registry event for list page
        var pageLst = boxTables.find(".pagination a");
        pageLst.each( function() {
            $(this).on('click', function( event ) {
            	event.preventDefault();
            	
            	if ( $.isFunction( settings.setData ) ) {
            		settings.dataDefault = settings.setData.call( this );
            		
                }
            	
            	var elementThis = $(this);
            	var isPopup = elementThis.parents(".modal-dialog").hasClass("modal-dialog");
            	var params = trimData(settings.dataDefault);
            	var page = elementThis.data("page");
            	params["page"] = page;
            	
            	//check exist sort
            	var existSort = $('body').find('.sort').length;
            	if(existSort > 0){
            		params['sortRowNum'] = SORT_ROWNUM;
                	params['sortMessage'] = SORT_MESSAGE;
                	params['optionSort'] = OPTION_SORT;
            	}
            	
            	/* page of Size */
    			var pageSizeSelected = boxTables.find(".page-size-select select.select-page");
    			if (pageSizeSelected != null) {
    				var pageSize = $(pageSizeSelected).val();
    				params["pageSize"] = parseInt(pageSize);
    			}
    			if (boxTableId != null) {
    				params["boxTableId"] = boxTableId;
    			}
    			if (boxMain != null) {
    				params["boxMain"] = boxMain;
    			}
            	
            	$.ajax({
                    url : settings.url,
                    type: settings.type,
                    data : params,
                    success:function(data, textStatus, jqXHR) {
                    	if ( $.isFunction( settings.complete ) ) {
                    		settings.complete( data );
                        } else {
                        	drawTable( boxTables, data );
                        }
                    	
                    	anchorTop(isPopup);
                    }
                });
            });
        });
        
        // Registry event for list page
        var pageSelected = boxTables.find(".pagination-select select.select-page");
        $(pageSelected).on('change', function( event ) {
        	event.preventDefault();
        	
        	if ( $.isFunction( settings.setData ) ) {
        		settings.dataDefault = settings.setData.call( this );
        		
            }
        	
        	var elementThis = $(this);
        	var isPopup = elementThis.parents(".modal-dialog").hasClass("modal-dialog");
        	var params = trimData(settings.dataDefault);
        	var page = elementThis.val();
        	params["page"] = page;
        	
        	/* page of Size */
			var pageSizeSelected = boxTables.find(".page-size-select select.select-page");
			if (pageSizeSelected != null) {
				var pageSize = $(pageSizeSelected).val();
				params["pageSize"] = parseInt(pageSize);
			}
			if (boxTableId != null) {
				params["boxTableId"] = boxTableId;
			}
			if (boxMain != null) {
				params["boxMain"] = boxMain;
			}
        	
        	
        	$.ajax({
                url : settings.url,
                type: settings.type,
                data : params,
                success:function(data, textStatus, jqXHR) {
                	if ( $.isFunction( settings.complete ) ) {
                		settings.complete( data );
                    } else {
                    	drawTable( boxTables, data );
                    }
                	
                	anchorTop(isPopup);
                }
            });
        });
        
     // @author: phunghn
		var pageSizeSelected = boxTables.find(".page-size-select select.select-page");
		$(pageSizeSelected).unbind('change').bind('change', function(event) {
			event.preventDefault();
			blockbg();
			if ($.isFunction(settings.setData)) {
				settings.dataDefault = settings.setData.call(this);
				
			}

			var elementThis = $(this);

        	var isPopup = elementThis.parents(".modal-dialog").hasClass("modal-dialog");
			var params = trimData(settings.dataDefault);
			var pageSize = elementThis.val();
			params["pageSize"] = pageSize;

			if (boxTableId != null) {
				params["boxTableId"] = boxTableId;
			}
			if (boxMain != null) {
				params["boxMain"] = boxMain;
			}
			console.log(JSON.stringify(params));
			$.ajax({
				url : settings.url,
				type : settings.type,
				data : params,
				success : function(data, textStatus, jqXHR) {
					if ($.isFunction(settings.complete)) {
						settings.complete(data);
					} else {
						drawTable(boxTables, data);
					}

					anchorTop(isPopup);

					unblockbg();
				},
				error : function(xhr, textStatus, error) {
					unblockbg();
					console.log(xhr);
					console.log(textStatus);
					console.log(error);
				}
			});
		});
        
        // Show message no data
        var tbody = boxTables.find("table.table tbody");
        var tbodyData = tbody.html();
        tbodyData = $.trim(tbodyData)
        if( tbodyData == "" ) {
        	 var countCol = boxTables.find("table.table thead th").length;
    		 var tr = $("<tr>", {	
    	         "class"     : "odd"
    	     });
    		 
			 var td = $("<td>", {	
		         "class"     : "dataTables_empty",
		         "valign"    : "top",
		         "colspan"   : countCol
		     });
			 td.text(defaults.language.emptyTable);
			 
			 tr.append(td);
			 tbody.append(tr);
        }
        
        function drawTable( element, data ) {
        	element.html(data);
        }
        
        function anchorTop(element) {
        	var eBody = "body";
        	if(element) {
        		eBody = "";
        		console.log("sub")
        	}
        	
        	if( eBody != null && eBody != '' ) {
        		$('html, body').animate({
        	        scrollTop: $(eBody).offset().top
        	    }, 0);
        	}
        }
    }
    
    datatables_modal.defaults = {
		"language": {
	    	"emptyTable"    :     "No data"
	    }
    }
    
    $.fn.datatables_modal = datatables_modal;
    
    $.fn.datatables_modal.defaults = datatables_modal.defaults;
    
    return $.fn.datatables_modal;
}(jQuery));

function trimData(params) {
	try {
		if (params != undefined && params != null && params != '') {
			$.each(params, function( key, value ) {
			  if (value != undefined && value != null && value != '') {
				  value = value.trim();
			  }
			  params[key] = value;
			});
		}
	} catch(err) {
	  console.log(err);
	}

	return params;
}