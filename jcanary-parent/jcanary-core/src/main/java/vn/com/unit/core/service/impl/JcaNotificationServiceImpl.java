/*******************************************************************************
 * Class        ：JcaNotificationServiceImpl
 * Created date ：2021/02/01
 * Lasted date  ：2021/02/01
 * Author       ：TrieuVD
 * Change log   ：2021/02/01：01-00 TrieuVD create a new
 ******************************************************************************/
package vn.com.unit.core.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import vn.com.unit.common.dto.PushNotificationResponse;
import vn.com.unit.common.service.NotificationService;
import vn.com.unit.common.utils.CommonCollectionUtil;
import vn.com.unit.common.utils.CommonJsonUtil;
import vn.com.unit.common.utils.CommonStringUtil;
import vn.com.unit.core.dto.JcaAppInboxDto;
import vn.com.unit.core.dto.JcaPushNotificationRequest;
import vn.com.unit.core.service.JcaAppInboxService;
import vn.com.unit.core.service.JcaNotificationService;

/**
 * JcaNotificationServiceImpl
 * 
 * @version 01-00
 * @since 01-00
 * @author TrieuVD
 */
@Service
@Transactional(readOnly = true, rollbackFor = Exception.class)
public class JcaNotificationServiceImpl implements JcaNotificationService {

    @Autowired
    private JcaAppInboxService jcaAppInboxService;

    @Autowired
    private NotificationService notificationService;

    /*
     * (non-Javadoc)
     * 
     * @see vn.com.unit.core.service.JcaNotificationService#pushNotification(vn.com.unit.common.dto.PushNotificationRequest)
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public PushNotificationResponse pushNotification(JcaPushNotificationRequest notificationRequest) {
        // TODO TrieuVD
        Map<String, String> configMap = new HashMap<String, String>();
        PushNotificationResponse pushNotiResponse = notificationService.pushNotification(notificationRequest, configMap);
        List<Long> accountIdList = notificationRequest.getAccountIdList();
        if (CommonCollectionUtil.isNotEmpty(accountIdList)) {
            String title = notificationRequest.getTitle();
            String description = notificationRequest.getMessage();
            String data = CommonStringUtil.EMPTY;
            try {
                data = CommonJsonUtil.convertObjectToJSON(notificationRequest.getData());
            } catch (Exception e) {

            }
            String responseJson = pushNotiResponse.getResponseJson();
            for (Long accountId : accountIdList) {
                JcaAppInboxDto appInboxDto = new JcaAppInboxDto();
                appInboxDto.setUserId(accountId);
                appInboxDto.setTitle(title);
                appInboxDto.setDescription(description);
                appInboxDto.setResponseJson(responseJson);
                appInboxDto.setData(data);
                jcaAppInboxService.saveJcaAppInboxDto(appInboxDto);
            }
        }
        return pushNotiResponse;
    }

}
