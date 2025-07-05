/*******************************************************************************
 * Class        ：AccountOrgRest
 * Created date ：2020/12/16
 * Lasted date  ：2020/12/16
 * Author       ：SonND
 * Change log   ：2020/12/16：01-00 SonND create a new
 ******************************************************************************/
package vn.com.unit.ep2p.rest.app;

import com.google.api.core.ApiFuture;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.*;
import vn.com.unit.ep2p.admin.constant.FirebaseConstant;
import vn.com.unit.ep2p.core.constant.AppApiConstant;
import vn.com.unit.ep2p.core.dto.AccountApiDto;
import vn.com.unit.ep2p.admin.dto.LoginApiReq;
import vn.com.unit.ep2p.core.dto.EmailResetPassResultDto;
import vn.com.unit.ep2p.core.res.dto.EmailResetPassDto;
import vn.com.unit.ep2p.core.utils.RetrofitUtils;
import vn.com.unit.ep2p.rest.AbstractRest;

import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * AccountOrgRest
 * 
 * @version 01-00
 * @since 01-00
 * @author SonND
 */

@RestController
@RequestMapping(AppApiConstant.API_V1 + AppApiConstant.API_APP)
public class TestApi extends AbstractRest{

	@PostMapping("/test")
	@ResponseBody
	public AccountApiDto test(@RequestBody LoginApiReq loginApiReq){
		AccountApiDto acc = new AccountApiDto();
		acc.setUsername("196638");
		acc.setAccountType("2");
		acc.setFullName("agent ter");
		acc.setDepartment("Department");
		acc.setContactEmail("test@gmail.com");
		acc.setBirthDay("15/01/2021");
		acc.setResponseCode("000");
		return acc;
	}
	@PostMapping("/test2")
	@ResponseBody
	public EmailResetPassResultDto test2(@RequestBody EmailResetPassDto emailResetPassDto){
		EmailResetPassResultDto acc = new EmailResetPassResultDto();
		acc.setErrCode("A000");
		acc.setErrMsg("Exit Status : 2");
		acc.setStatus("Exception Error");
		return acc;
	}
	@PostMapping("/test3")
	@ResponseBody
	public EmailResetPassResultDto test3(@RequestBody EmailResetPassDto emailResetPassDto) throws Exception {
		EmailResetPassResultDto acc = RetrofitUtils.sendEmailResetPass(emailResetPassDto);
		return acc;
	}

	@Autowired
	private Environment env;

	@PostMapping("/send-noti")
	@ResponseBody
	public String sendNoti(String token) throws Exception {
		//send noti to device
		String fileName = env.getProperty(FirebaseConstant.FIREBASE_CONFIGURATOION_FILE);
		GoogleCredentials googleCredentials = GoogleCredentials.fromStream(getClass().getClassLoader().getResourceAsStream(fileName));

		@SuppressWarnings("deprecation")
		FirebaseOptions options = new FirebaseOptions.Builder().setCredentials(googleCredentials).build();

		if (FirebaseApp.getApps().isEmpty()) {
			FirebaseApp.initializeApp(options);
		}
		final  String TOKEN_ANDROID = "fs8LeCbKTbidvC9smILKC0:APA91bFigkviQ3KyGurjsGb2kmfXCEU1MQbRtk8yl--a_fDsr3Tr5iIJeH4CEtNXksvEFFcQwyM2Q6vfm1RgASWptJ31TBEaf26BHBMfxagAY4zSO0Fwy5JWtdhvFJDq6ZWYjQfwQ0fY";
		final String TOKEN_IOS = "dUsrbbAfC0rotSb_S80o7p:APA91bFpSMkVhrEq-4fKRJYQ68jdCH9viL7Zpl8wtufE9fNOZhk8zD1d7lRUcubfqtWZJMR2P6orqX_TXWxqenSydvRVzbawI3-K5hglbJabENPTvWIjoxh73fZtknvtuvs3zdjudYON";
		// Send a message to the device corresponding to the provided
		// registration token.
		Message message = allPlatformsMessage(token);
		try{
//			String response = FirebaseMessaging.getInstance().send(message);
			ApiFuture<String> apiFuture = FirebaseMessaging.getInstance().sendAsync(message);

			// waiting for the response max. 30 minutes
			String messageId = apiFuture.get(30, TimeUnit.MINUTES);
			// Response is a message ID string.
			System.out.println("Successfully sent message: " + messageId);
			return messageId;
		} catch (Exception ex) {
			throw new Exception(ex);
		}
		// [END send_to_token]
	}

	public Message allPlatformsMessage(String deviceToken) {
		Message message = Message.builder()
				.setNotification(Notification.builder()
						.setTitle("Daiichi DSuccess Thông báo!")
						.setBody("This is a test message.")
						.build())
				.putData("click_action", "FLUTTER_NOTIFICATION_CLICK")
				.putData("route", "notificationPage")
				.setToken(deviceToken)
				.build();
		return message;
	}
}
