$(document).ready(function() {
    var tableLeft = $('#tableLeft').dataTable({
    	scrollY:        '50vh',
        "bScrollCollapse": true,
        scrollCollapse: true,
        "paging"    : false,
        "ordering"  : false,
        "info"      : false,
        'sDom': '"top"i'
    }); // first table 
    var tableLeftData = $('#tableLeft').DataTable();
    
	$('#roleNameLeft').on( 'keyup click', function () { alert(this.value);
		tableLeftData.search( this.value ).draw();
    });
    
	var tableRight = $('#tableRight').dataTable({
		scrollY:        '50vh',
        "bScrollCollapse": true,
        scrollCollapse: true,
        "paging"    : false,
        "ordering"  : false,
        "info"      : false,
        'sDom': '"top"i'
    }); // Second table 
	var tableRightData = $('#tableRight').DataTable();
	
	$('#roleNameRight').on( 'keyup click', function () {
		tableRightData.search( this.value ).draw();
    });
   
	tableLeft.on('click', 'tbody tr' ,function() {
		 $(this).toggleClass('selected');
    });
	tableRight.on('click', 'tbody tr' ,function() {
		 $(this).toggleClass('selected');
    });
	
	$('#btnMoveToRight').on('click',function () {
		moveRows(tableLeft, tableRight);			
	});
	
	$('#btnMoveAllToRight').on('click',function () {
        moveAllRows(tableLeft, tableRight);
	});
	
	$('#btnMoveToLeft').on('click',function () {
		moveRows(tableRight, tableLeft);		
	});
	
	$('#btnMoveAllToLeft').on('click',function () {
		moveAllRows(tableRight, tableLeft);
	});
	
// ----------------- Organization --------------------------------------------------------
	
	// Edit organization
	$('#id-btn-edit-org').on('click', function() {
		edit();
	});
	
	// New organization
	$('#id-btn-new-org').on('click', function() {
		newOrg();
	});
	
	// Cancel edit organization
	$('#id-btn-cancel-org').on('click', function() {
		cancel();
	});
	
	// Update organization
	$('#id-btn-update-org').on('click', function(event) {
		update();
	});
	
	// Delete Organization	
	$('#id-btn-delete-org').on('click', function() {
		deleteOrg();
	});
	
	$("#id-org-code").change(function() {
		var orgCode = $("#id-org-code").val().toUpperCase();
		$("#id-org-code").val(orgCode);
	});
});
	
function moveRows(fromTable, toTable){	
	var $row = fromTable.find(".selected");	
	$.each($row, function(k, v){
		if(this !== null){
			addRow = fromTable.fnGetData(this);
			//$(this).attr("name", "chkTeamLeft");
			toTable.fnAddData(addRow);
			fromTable.fnDeleteRow(this);
		}
	}); 	
}

function moveAllRows(fromTable, toTable) {
	var recordNumber = fromTable.fnSettings().aoData.length;
	if( recordNumber > 0 ) {
		var $row = fromTable.find("tbody tr");
		$.each($row, function(k, v) {
			if(this !== null){
				addRow = fromTable.fnGetData(this);
				toTable.fnAddData(addRow);
				fromTable.fnDeleteRow(this);
			}			
		});
	}
}