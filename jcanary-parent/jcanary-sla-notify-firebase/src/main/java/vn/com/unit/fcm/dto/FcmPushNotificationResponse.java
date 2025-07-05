/*******************************************************************************
 * Class        ：PushNotificationResponse
 * Created date ：2021/01/20
 * Lasted date  ：2021/01/20
 * Author       ：TrieuVD
 * Change log   ：2021/01/20：01-00 TrieuVD create a new
 ******************************************************************************/
package vn.com.unit.fcm.dto;

import lombok.Getter;
import lombok.Setter;
import vn.com.unit.common.dto.PushNotificationResponse;

/**
 * <p>
 * PushNotificationResponse
 * </p>
 * .
 *
 * @version 01-00
 * @since 01-00
 * @author TrieuVD
 */
@Getter
@Setter
public class FcmPushNotificationResponse extends PushNotificationResponse {

    /** The success count. */
    private int successCount;
}
