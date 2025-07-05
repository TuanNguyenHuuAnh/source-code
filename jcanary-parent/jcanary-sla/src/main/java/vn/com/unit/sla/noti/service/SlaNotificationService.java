/*******************************************************************************
 * Class        ：SlaNotiService
 * Created date ：2020/11/11
 * Lasted date  ：2020/11/11
 * Author       ：TrieuVD
 * Change log   ：2020/11/11：01-00 TrieuVD create a new
 ******************************************************************************/
package vn.com.unit.sla.noti.service;

import vn.com.unit.sla.noti.dto.SlaNotificationDto;
import vn.com.unit.sla.noti.dto.SlaNotificationResultDto;

/**
 * SlaNotiService
 * 
 * @version 01-00
 * @since 01-00
 * @author TrieuVD
 */
public interface SlaNotificationService {

    /**
     * <p>
     * Push notification.
     * </p>
     *
     * @author TrieuVD
     * @param notiDto
     *            type {@link SlaNotificationDto}
     * @return {@link SlaNotificationResultDto}
     */
    public SlaNotificationResultDto pushNotification(SlaNotificationDto notiDto);
}
