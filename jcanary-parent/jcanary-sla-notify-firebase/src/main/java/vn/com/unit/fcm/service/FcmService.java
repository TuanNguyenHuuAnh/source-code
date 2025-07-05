/*******************************************************************************
 * Class        ：FcmService
 * Created date ：2020/12/11
 * Lasted date  ：2020/12/11
 * Author       ：TrieuVD
 * Change log   ：2020/12/11：01-00 TrieuVD create a new
 ******************************************************************************/
package vn.com.unit.fcm.service;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import vn.com.unit.fcm.dto.FcmPushNotificationRequest;
import vn.com.unit.fcm.dto.FcmPushNotificationResponse;

/**
 * FcmService.
 *
 * @version 01-00
 * @since 01-00
 * @author TrieuVD
 */
public interface FcmService {

    /**
     * <p>
     * Send message.
     * </p>
     *
     * @param data
     *            type {@link Map<String,String>}
     * @param request
     *            type {@link FcmPushNotificationRequest}
     * @return {@link FcmPushNotificationResponse}
     * @author TrieuVD
     */
    public FcmPushNotificationResponse sendMessage(Map<String, String> data, FcmPushNotificationRequest request);

    /**
     * Send message topic.
     *
     * @param data
     *            type Map<String,String>
     * @param request
     *            type PushNotificationRequest
     * @author TrieuVD
     * @throws ExecutionException 
     * @throws InterruptedException 
     */
    public void sendMessageTopic(Map<String, String> data, FcmPushNotificationRequest request) throws InterruptedException, ExecutionException;

    /**
     * Subscribe topic multiple devices.
     *
     * @param tokens
     *            type List<String>
     * @param topic
     *            type String
     * @author TrieuVD
     */
    public void subscribeTopicMultipleDevices(List<String> tokens, String topic);

    /**
     * Un subscribe topic multiple devices.
     *
     * @param tokens
     *            type List<String>
     * @param topic
     *            type String
     * @author TrieuVD
     */
    public void unSubscribeTopicMultipleDevices(List<String> tokens, String topic);
}
