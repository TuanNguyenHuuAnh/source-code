/*******************************************************************************
 * Class        ：NotificationService
 * Created date ：2021/02/01
 * Lasted date  ：2021/02/01
 * Author       ：TrieuVD
 * Change log   ：2021/02/01：01-00 TrieuVD create a new
 ******************************************************************************/
package vn.com.unit.common.service;

import java.util.Map;

import vn.com.unit.common.dto.PushNotificationRequest;
import vn.com.unit.common.dto.PushNotificationResponse;

/**
 * NotificationService
 * 
 * @version 01-00
 * @since 01-00
 * @author TrieuVD
 */
public interface NotificationService {

    /**
     * <p>
     * Push notification.
     * </p>
     *
     * @param notificationRequest
     *            type {@link PushNotificationRequest}
     * @param configMap
     *            type {@link Map<String,String>}
     * @return {@link PushNotificationResponse}
     * @author TrieuVD
     */
    public PushNotificationResponse pushNotification(PushNotificationRequest notificationRequest, Map<String, String> configMap);
}
