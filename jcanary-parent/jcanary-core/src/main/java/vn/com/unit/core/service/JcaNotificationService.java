/*******************************************************************************
 * Class        ：JcaNotificationService
 * Created date ：2021/02/01
 * Lasted date  ：2021/02/01
 * Author       ：TrieuVD
 * Change log   ：2021/02/01：01-00 TrieuVD create a new
 ******************************************************************************/
package vn.com.unit.core.service;

import vn.com.unit.common.dto.PushNotificationRequest;
import vn.com.unit.common.dto.PushNotificationResponse;
import vn.com.unit.core.dto.JcaPushNotificationRequest;

/**
 * JcaNotificationService
 * 
 * @version 01-00
 * @since 01-00
 * @author TrieuVD
 */
public interface JcaNotificationService {
    
    /**
     * <p>
     * Push notification.
     * </p>
     *
     * @param notificationRequest
     *            type {@link PushNotificationRequest}
     * @return {@link PushNotificationResponse}
     * @author TrieuVD
     */
    public PushNotificationResponse pushNotification(JcaPushNotificationRequest notificationRequest);
}
