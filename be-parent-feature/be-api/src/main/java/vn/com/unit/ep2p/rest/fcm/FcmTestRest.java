/*******************************************************************************
 * Class        ：FcmTestRest
 * Created date ：2020/12/11
 * Lasted date  ：2020/12/11
 * Author       ：TrieuVD
 * Change log   ：2020/12/11：01-00 TrieuVD create a new
 ******************************************************************************/
package vn.com.unit.ep2p.rest.fcm;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import vn.com.unit.dts.web.rest.common.DtsApiResponse;
import vn.com.unit.ep2p.core.constant.AppApiConstant;
import vn.com.unit.ep2p.dto.req.FcmReqDto;
import vn.com.unit.ep2p.rest.AbstractRest;
import vn.com.unit.ep2p.service.FirebaseCloudMessagingService;
import vn.com.unit.fcm.dto.FcmPushNotificationRequest;
import vn.com.unit.fcm.service.FcmService;

/**
 * FcmTestRest
 * 
 * @version 01-00
 * @since 01-00
 * @author TrieuVD
 */
@RestController
@RequestMapping(AppApiConstant.API_V1 + "/fcm")
@Api(tags = { "firebase" })
public class FcmTestRest extends AbstractRest {

	@Autowired
	private FcmService fcmService;

	@Autowired
	private FirebaseCloudMessagingService firebaseCloudMessagingService;

	@ApiOperation("Api provides push notify using firebase")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "success"),
			@ApiResponse(code = 500, message = "Internal Server Error"),
			@ApiResponse(code = 401, message = "Unauthorized"), @ApiResponse(code = 402, message = "Forbidden") })
	@PostMapping("/send-notify")
	public DtsApiResponse sendNotification(
			@ApiParam(name = "body", value = "req information to push notify") @RequestBody FcmReqDto requestObj) {
		long start = System.currentTimeMillis();

		Map<String, String> params = requestObj.getData();
		FcmPushNotificationRequest request = new FcmPushNotificationRequest();
		request.setTitle(requestObj.getTitle());
		request.setMessage(requestObj.getMessage());
		request.setToken(requestObj.getToken());
		request.setTopic(requestObj.getTopic());
		request.setMultipleTokens(requestObj.getMultipleTokens());
		try {
			fcmService.sendMessage(params, request);
			return this.successHandler.handlerSuccess(null, start);
		} catch (Exception ex) {
			return this.errorHandler.handlerException(ex, start);
		}
	}

	@PostMapping("/fcm-test")
	public DtsApiResponse sendNotification(@ApiParam(name = "token", value = "token") @RequestBody String token) {
		long start = System.currentTimeMillis();

		try {
			firebaseCloudMessagingService.sendMessageToToken(token, "title", "body");
			return this.successHandler.handlerSuccess(null, start);
		} catch (Exception ex) {
			return this.errorHandler.handlerException(ex, start);
		}
	}
}
