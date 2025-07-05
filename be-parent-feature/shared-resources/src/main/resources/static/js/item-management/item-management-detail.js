$(document).ready(function () {

    // on click list, cancel
    $('#linkList, #cancel').on('click',function(){
    	back();
    });
});

function back() {
    var url = BASE_URL + "item/list";
    ajaxRedirect(url);
}