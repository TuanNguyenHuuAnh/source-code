/* @author : dinhpv
 * @description : Add script handler for ajax session expiration
 * @date : 2017-04-07
 */
function sessionTimeOutModal()
{
	$('#sessionTimeOutModal').modal('show');
	$('#sessionTimeOutModal').css('z-index',5000);
}

!function($){
	$.ajaxSetup({
		statusCode: 
		{
			500: sessionTimeOutModal // Khi có mã lỗi 901 gọi hàm show dialog
		}
	});
}(window.jQuery);