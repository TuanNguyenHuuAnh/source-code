/*******************************************************************************
 * Class        ：SlaNotificationServiceImpl
 * Created date ：2021/03/01
 * Lasted date  ：2021/03/01
 * Author       ：TrieuVD
 * Change log   ：2021/03/01：01-00 TrieuVD create a new
 ******************************************************************************/
package vn.com.unit.ep2p.core.sla.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import vn.com.unit.common.dto.PushNotificationResponse;
import vn.com.unit.core.dto.JcaPushNotificationRequest;
import vn.com.unit.core.service.JcaNotificationService;
import vn.com.unit.ep2p.core.service.AbstractCommonService;
import vn.com.unit.sla.noti.dto.SlaNotificationDto;
import vn.com.unit.sla.noti.dto.SlaNotificationResultDto;
import vn.com.unit.sla.noti.service.SlaNotificationService;

/**
 * <p>
 * SlaNotificationServiceImpl
 * </p>
 * 
 *
 * @author TrieuVD
 * @version 01-00
 * @since 01-00
 */
@Service
public class SlaNotificationServiceImpl extends AbstractCommonService implements SlaNotificationService {
    
    @Autowired
    private JcaNotificationService jcaNotificationService;

    @Override
    public SlaNotificationResultDto pushNotification(SlaNotificationDto notiDto) {
        JcaPushNotificationRequest notificationRequest = objectMapper.convertValue(notiDto, JcaPushNotificationRequest.class);
        PushNotificationResponse  notificationResponse =  jcaNotificationService.pushNotification(notificationRequest);
        return objectMapper.convertValue(notificationResponse , SlaNotificationResultDto.class) ;
    }

}
