$(document).ready(function() {
	var initialState = 'collapsed';
	// datatable load
	$("#tableList").datatables({
		url : BASE_URL + 'log-api/ajaxList',
		type : 'POST',
		setData : setConditionSearch
	});
    
});

function downloadJson(data, fileName){
	try {
	const jsonData = JSON.stringify(JSON.parse(data), null, 2);
	const blob = new Blob([jsonData], {type: 'application/json'});
	const url = URL.createObjectURL(blob);
	const a = document.createElement('a');
	a.href = url;
	a.download = fileName;
	a.click();
	URL.revokeObjectURL(url);
	} catch (e) {
		alert("Error");
	}

}
