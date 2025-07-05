/*******************************************************************************
 * Class        ：FcmServiceImpl
 * Created date ：2020/12/11
 * Lasted date  ：2020/12/11
 * Author       ：TrieuVD
 * Change log   ：2020/12/11：01-00 TrieuVD create a new
 ******************************************************************************/
package vn.com.unit.fcm.service.impl;

import java.time.Duration;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.google.firebase.messaging.AndroidConfig;
import com.google.firebase.messaging.AndroidNotification;
import com.google.firebase.messaging.ApnsConfig;
import com.google.firebase.messaging.Aps;
import com.google.firebase.messaging.BatchResponse;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.MulticastMessage;
import com.google.firebase.messaging.Notification;

import vn.com.unit.fcm.dto.FcmPushNotificationRequest;
import vn.com.unit.fcm.dto.FcmPushNotificationResponse;
import vn.com.unit.fcm.enumdef.FcmNotificationParameter;
import vn.com.unit.fcm.service.FcmService;
import vn.com.unit.fcm.utils.FcmJsonUtils;

/**
 * FcmServiceImpl.
 *
 * @version 01-00
 * @since 01-00
 * @author TrieuVD
 */
@Service
public class FcmServiceImpl implements FcmService {

    /** The Constant logger. */
    private static final Logger logger = LoggerFactory.getLogger(FcmServiceImpl.class);

    /*
     * (non-Javadoc)
     * 
     * @see vn.com.unit.fcm.service.FcmService#sendMessage(java.util.Map, vn.com.unit.fcm.dto.PushNotificationRequest)
     */
    @Override
    public FcmPushNotificationResponse sendMessage(Map<String, String> data, FcmPushNotificationRequest request) {
        FcmPushNotificationResponse result = new FcmPushNotificationResponse();
        try {
            MulticastMessage message = getPreconfiguredMessage(data, request);
            BatchResponse response = sendAndGetResponse(message);
            result.setStatus(true);
            result.setSuccessCount(response.getSuccessCount());
            result.setResponseJson(FcmJsonUtils.fcmConvertObjectToJson(response.getSuccessCount()));
        } catch (Exception e) {
            result.setStatus(false);
            result.setErrorMessage(e.getMessage());
        }
        return result;
    }

    /*
     * (non-Javadoc)
     * 
     * @see vn.com.unit.fcm.service.FcmService#sendMessageTopic(java.util.Map, vn.com.unit.fcm.dto.PushNotificationRequest)
     */
    @Override
    public void sendMessageTopic(Map<String, String> data, FcmPushNotificationRequest request)
            throws InterruptedException, ExecutionException {
        Message message = getPreconfiguredMessageTopic(data, request);
        String response = sendAndGetResponseTopic(message);
        logger.info("Sent message: " + response);
    }

    /*
     * (non-Javadoc)
     * 
     * @see vn.com.unit.fcm.service.FcmService#subscribeTopicMultipleDevices(java.util.List, java.lang.String)
     */
    @Override
    public void subscribeTopicMultipleDevices(List<String> tokens, String topic) {
        try {
            FirebaseMessaging.getInstance().subscribeToTopic(tokens, topic);
        } catch (FirebaseMessagingException e) {
            e.printStackTrace();
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see vn.com.unit.fcm.service.FcmService#unSubscribeTopicMultipleDevices(java.util.List, java.lang.String)
     */
    @Override
    public void unSubscribeTopicMultipleDevices(List<String> tokens, String topic) {
        try {
            FirebaseMessaging.getInstance().unsubscribeFromTopic(tokens, topic);
        } catch (FirebaseMessagingException e) {
            e.printStackTrace();
        }
    }

    /**
     * Send and get response.
     *
     * @param message
     *            type MulticastMessage
     * @return BatchResponse
     * @throws InterruptedException
     *             the interrupted exception
     * @throws ExecutionException
     *             the execution exception
     * @author TrieuVD
     */
    private BatchResponse sendAndGetResponse(MulticastMessage message) throws InterruptedException, ExecutionException {
        return FirebaseMessaging.getInstance().sendEachForMulticastAsync(message).get();
    }

    /**
     * Send and get response topic.
     *
     * @param message
     *            type Message
     * @return String
     * @throws InterruptedException
     *             the interrupted exception
     * @throws ExecutionException
     *             the execution exception
     * @author TrieuVD
     */
    private String sendAndGetResponseTopic(Message message) throws InterruptedException, ExecutionException {
        return FirebaseMessaging.getInstance().sendAsync(message).get();
    }

    /**
     * Gets the preconfigured message.
     *
     * @param data
     *            type Map<String,String>
     * @param request
     *            type PushNotificationRequest
     * @return the preconfigured message
     * @author TrieuVD
     */
    private MulticastMessage getPreconfiguredMessage(Map<String, String> data, FcmPushNotificationRequest request) {

        return getPreconfiguredMessageBuilder(request).addAllTokens(request.getMultipleTokens())
                // .setTopic(request.getTopic())
                .putAllData(data).build();
    }

    /**
     * Gets the preconfigured message topic.
     *
     * @param data
     *            type Map<String,String>
     * @param request
     *            type PushNotificationRequest
     * @return the preconfigured message topic
     * @author TrieuVD
     */
    private Message getPreconfiguredMessageTopic(Map<String, String> data, FcmPushNotificationRequest request) {
        return getPreconfiguredMessageBuilderTopic(request).setToken(request.getToken()).setTopic(request.getTopic()).putAllData(data)
                .build();
    }

    /**
     * Gets the preconfigured message builder topic.
     *
     * @param request
     *            type PushNotificationRequest
     * @return the preconfigured message builder topic
     * @author TrieuVD
     */
    private Message.Builder getPreconfiguredMessageBuilderTopic(FcmPushNotificationRequest request) {
        AndroidConfig androidConfig = getAndroidConfig(request.getTopic());
        ApnsConfig apnsConfig = getApnsConfig(request.getTopic());
        return Message.builder().setApnsConfig(apnsConfig).setAndroidConfig(androidConfig)
                .setNotification(Notification.builder().setTitle(request.getTitle()).setBody(request.getMessage()).build());
    }

    /**
     * Gets the preconfigured message builder.
     *
     * @param request
     *            type PushNotificationRequest
     * @return the preconfigured message builder
     * @author TrieuVD
     */
    private MulticastMessage.Builder getPreconfiguredMessageBuilder(FcmPushNotificationRequest request) {
        AndroidConfig androidConfig = getAndroidConfig(request.getTopic());
        ApnsConfig apnsConfig = getApnsConfig(request.getTopic());
        return MulticastMessage.builder().setApnsConfig(apnsConfig).setAndroidConfig(androidConfig)
                .setNotification(Notification.builder().setTitle(request.getTitle()).setBody(request.getMessage()).build());
    }

    /**
     * Gets the android config.
     *
     * @param topic
     *            type String
     * @return the android config
     * @author TrieuVD
     */
    private AndroidConfig getAndroidConfig(String topic) {
        return AndroidConfig.builder().setTtl(Duration.ofMinutes(2).toMillis()).setCollapseKey(topic)
                .setPriority(AndroidConfig.Priority.HIGH)
                .setNotification(AndroidNotification.builder().setSound(FcmNotificationParameter.SOUND.getValue())
                        // .setColor(NotificationParameter.COLOR.getValue()).setTag(topic).build()).build();
                        .setColor(FcmNotificationParameter.COLOR.getValue()).build())
                .build();
    }

    /**
     * Gets the apns config.
     *
     * @param topic
     *            type String
     * @return the apns config
     * @author TrieuVD
     */
    private ApnsConfig getApnsConfig(String topic) {
        return ApnsConfig.builder().setAps(Aps.builder().setCategory(topic).setThreadId(topic).build()).build();
    }

}
