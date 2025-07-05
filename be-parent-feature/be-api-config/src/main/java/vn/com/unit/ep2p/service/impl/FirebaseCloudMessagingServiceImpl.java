package vn.com.unit.ep2p.service.impl;

import java.io.FileInputStream;
import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;

import vn.com.unit.ep2p.admin.constant.FirebaseConstant;
import vn.com.unit.ep2p.service.FirebaseCloudMessagingService;

@Service
@Transactional(readOnly = true, rollbackFor = Exception.class)
public class FirebaseCloudMessagingServiceImpl implements FirebaseCloudMessagingService {

	@Autowired
	private Environment env;

	private static final Logger logger = LoggerFactory.getLogger(FirebaseCloudMessagingServiceImpl.class);

	@Override
	public String sendMessageToToken(String registrationToken, String title, String body) {

		String fileName = env.getProperty(FirebaseConstant.FIREBASE_CONFIGURATOION_FILE);

//		logger.error("NEED CHANGE FILE NAME FROM ENV");
//		String fileName = "d-success-daiichi-firebase-adminsdk-jfj3e-0626cfc385.json";
//		logger.error("NEED CHANGE FILE NAME FROM ENV");

		String serviceAccountKey = Objects.requireNonNull(getClass().getClassLoader().getResource(fileName)).getPath();

		FileInputStream serviceAccount;
		try {
			serviceAccount = new FileInputStream(serviceAccountKey);

			GoogleCredentials googleCredentials = GoogleCredentials.fromStream(serviceAccount);

			@SuppressWarnings("deprecation")
			FirebaseOptions options = new FirebaseOptions.Builder().setCredentials(googleCredentials).build();

//			FirebaseOptions options = FirebaseOptions.builder()
//	  		  .setCredentials(GoogleCredentials.fromStream(serviceAccount))
//	  		  .build();

			if (FirebaseApp.getApps().isEmpty()) {
				FirebaseApp.initializeApp(options);
			}

			String firebaseProjectId = options.getProjectId();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// [START send_to_token]

		// This registration token comes from the client FCM SDKs.
//	    String registrationToken = "YOUR_REGISTRATION_TOKEN";

		// See documentation on defining a message payload.
		// Message message = Message.builder().putData(title, body).setToken(registrationToken.trim()).build();


		Message message = Message.builder()
				.setNotification(
						Notification.builder().setTitle(title).setBody(body).build())
				.setToken(registrationToken.trim()).build();

		// (new Date()).toString() + " - " +

		// Send a message to the device corresponding to the provided registration
		// token.
		String response = null;
		try {
//			response = FirebaseMessaging.getInstance().send(message);
			response = FirebaseMessaging.getInstance(FirebaseApp.getInstance()).send(message);
		} catch (FirebaseMessagingException e) {
			response = "Sent message error: " + e.getMessage();
		}

		// Response is a message ID string.
//	    System.out.println("Successfully sent message: " + response);

		// [END send_to_token]

		return response;
	}

}
