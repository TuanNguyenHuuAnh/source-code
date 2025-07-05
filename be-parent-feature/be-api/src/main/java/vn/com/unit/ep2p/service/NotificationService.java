/*******************************************************************************
 * Class        ：NotificationService
 * Created date ：2021/02/01
 * Lasted date  ：2021/02/01
 * Author       ：SonND
 * Change log   ：2021/02/01：01-00 SonND create a new
 ******************************************************************************/
package vn.com.unit.ep2p.service;

import org.springframework.data.domain.Pageable;
import org.springframework.util.MultiValueMap;

import vn.com.unit.core.dto.JcaAppInboxDto;
import vn.com.unit.core.res.ObjectDataRes;
import vn.com.unit.dts.exception.DetailException;
import vn.com.unit.ep2p.dto.res.NotificationInfoRes;

/**
 * NotificationService.
 *
 * @version 01-00
 * @since 01-00
 * @author SonND
 */
/**
 * @author sonnd
 *
 */
public interface NotificationService {

    /**
     * <p>
     * Get notication by id.
     * </p>
     *
     * @param notificationId
     *            type {@link Long}
     * @return {@link NotificationInfoRes}
     * @throws DetailException
     *             the detail exception
     * @author SonND
     */
    NotificationInfoRes getNoticationById(Long notificationId) throws DetailException;
    
    /**
     * <p>
     * Get all notication.
     * </p>
     *
     * @param pageable
     *            type {@link Pageable}
     * @return {@link ObjectDataRes<JcaAppInboxDto>}
     * @throws DetailException
     *             the detail exception
     * @author SonND
     */
    ObjectDataRes<JcaAppInboxDto> getAllNotication(MultiValueMap<String, String> requestParams, Pageable pageable) throws DetailException;
    
    /**
     * <p>
     * Delete notification by id.
     * </p>
     *
     * @param notificationId
     *            type {@link Long}
     * @throws DetailException
     *             the detail exception
     * @author SonND
     */
    void deleteNotificationById(Long notificationId) throws DetailException;
    
    /**
     * <p>
     * Delete all notification.
     * </p>
     *
     * @throws DetailException
     *             the detail exception
     * @author SonND
     */
    void deleteAllNotification() throws DetailException;
    
    /**
     * <p>
     * Read notification.
     * </p>
     *
     * @param notificationId
     *            type {@link Long}
     * @param readFlag
     *            type {@link boolean}
     * @throws DetailException
     *             the detail exception
     * @author SonND
     */
    void readNotification(Long notificationId, boolean readFlag) throws DetailException;
    
    /**
     * <p>
     * Read all notification.
     * </p>
     *
     * @param readFlag
     *            type {@link boolean}
     * @throws DetailException
     *             the detail exception
     * @author SonND
     */
    void readAllNotification(boolean readFlag) throws DetailException;
}   
