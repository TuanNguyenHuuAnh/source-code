/*******************************************************************************
 * Class        ：Product
 * Created date ：2023/08/04
 * Lasted date  ：2024/03/22
 * Author       ：thu.thai
 * Change log   ：2023/08/04 hand create a new
 * Change log	:2024/03/22	nt.tinh SR16136 - Fix lỗi phát hiện trong quá trình Pentest - 2023
 ******************************************************************************/
package vn.com.unit.ep2p.rest.cms;

import java.util.List;

import com.google.gson.Gson;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import vn.com.unit.cms.core.constant.CmsApiConstant;
import vn.com.unit.cms.core.module.order.dto.OrderDto;
import vn.com.unit.cms.core.module.order.dto.OrderInformationDto;
import vn.com.unit.cms.core.module.order.dto.OrderReviewDto;
import vn.com.unit.core.security.UserProfileUtils;
import vn.com.unit.dts.web.rest.common.DtsApiResponse;
import vn.com.unit.ep2p.rest.AbstractRest;
import vn.com.unit.ep2p.service.ApiOrderService;

@RestController
@RequestMapping(CmsApiConstant.BASE_API_URL + "/order")
@Api(tags = {"get order information"})
public class OrderRest extends AbstractRest{
	@Autowired ApiOrderService apiOrderService;
	
	@GetMapping("/getListOrder")
    @ApiOperation("Get list Order by agent code")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "success"), @ApiResponse(code = 500, message = "Internal Server Error"),
            @ApiResponse(code = 401, message = "Unauthorized"), @ApiResponse(code = 403, message = "Forbidden")})
	public DtsApiResponse getListOrderByAgentCode(@RequestParam(value = "agentCode") String agentCode){
		long start = System.currentTimeMillis();
		try {
			String faceMask = UserProfileUtils.getFaceMask();
			List<OrderInformationDto> resObj = apiOrderService.getListOrderByAgent(faceMask);
			return this.successHandler.handlerSuccess(resObj, start, null, null);
		} catch (Exception ex) {
			ex.printStackTrace();
			return this.errorHandler.handlerException(ex, start, null, null);
		}
	}
	
	@PostMapping("/saveOrder")
	@ApiOperation("save order")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "success"), @ApiResponse(code = 500, message = "Internal Server Error"),
            @ApiResponse(code = 401, message = "Unauthorized"), @ApiResponse(code = 403, message = "Forbidden")})
	public DtsApiResponse saveOrder(@RequestBody OrderDto orderDto){
		long start = System.currentTimeMillis();
		try {
			return this.successHandler.handlerSuccess(apiOrderService.saveOrder(orderDto), start, null, null);
		} catch (Exception ex) {
			return this.errorHandler.handlerException(ex, start, null, null);
		}
	}
	
	@GetMapping("/orderReview")
    @ApiOperation("Get order information")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "success"), @ApiResponse(code = 500, message = "Internal Server Error"),
            @ApiResponse(code = 401, message = "Unauthorized"), @ApiResponse(code = 403, message = "Forbidden")})
	public DtsApiResponse getOrderReviewInfor(@RequestParam(value = "orderId") Long orderId){
		long start = System.currentTimeMillis();
		try {
			OrderReviewDto resObj = apiOrderService.getOrderReview(orderId, UserProfileUtils.getUserPrincipal().getUsername());
			return this.successHandler.handlerSuccess(resObj, start, null, null);
		} catch (Exception ex) {
			ex.printStackTrace();
			return this.errorHandler.handlerException(ex, start, null, null);
		}
	}
	
	@PostMapping("/updateOrder")
    @ApiOperation("update information for temp order")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "success"), @ApiResponse(code = 500, message = "Internal Server Error"),
            @ApiResponse(code = 401, message = "Unauthorized"), @ApiResponse(code = 403, message = "Forbidden")})
	public DtsApiResponse updateOrderInformation(@RequestBody OrderDto orderDto){
		long start = System.currentTimeMillis();
		try {
			OrderDto resObj = apiOrderService.updateOrderInformation(orderDto);
			return this.successHandler.handlerSuccess(resObj, start, null, null);
		} catch (Exception ex) {
			ex.printStackTrace();
			return this.errorHandler.handlerException(ex, start, null, null);
		}
	}
	
	@PostMapping("/submitOrderPublication")
	@ApiOperation("submit order")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "success"), @ApiResponse(code = 500, message = "Internal Server Error"),
            @ApiResponse(code = 401, message = "Unauthorized"), @ApiResponse(code = 403, message = "Forbidden")})
	public DtsApiResponse submitOrder(@RequestParam(required = false, name = "documentList")List<MultipartFile> document,
			@RequestParam(required = false, name = "orderDto") String dto){
		long start = System.currentTimeMillis();
		Gson gson = new Gson();
		OrderDto orderDto = gson.fromJson(dto, OrderDto.class);
		try {
			return this.successHandler.handlerSuccess(apiOrderService.submit(document,orderDto), start, null, null);
		} catch (Exception ex) {
			return this.errorHandler.handlerException(ex, start, null, null);
		}
	}
}
