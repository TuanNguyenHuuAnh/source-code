package vn.com.unit.ep2p.service;

public interface FirebaseCloudMessagingService {

	public String sendMessageToToken(String registrationToken, String title, String body);
	
}
