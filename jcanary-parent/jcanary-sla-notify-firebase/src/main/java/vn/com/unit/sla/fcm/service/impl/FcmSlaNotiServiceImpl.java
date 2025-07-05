/*******************************************************************************
 * Class        ：FcmSlaNotiServiceImpl
 * Created date ：2020/12/11
 * Lasted date  ：2020/12/11
 * Author       ：TrieuVD
 * Change log   ：2020/12/11：01-00 TrieuVD create a new
 ******************************************************************************/
package vn.com.unit.sla.fcm.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;

import vn.com.unit.common.dto.PushNotificationRequest;
import vn.com.unit.common.dto.PushNotificationResponse;
import vn.com.unit.common.service.NotificationService;
import vn.com.unit.fcm.dto.FcmPushNotificationRequest;
import vn.com.unit.fcm.dto.FcmPushNotificationResponse;
import vn.com.unit.fcm.service.FcmService;

/**
 * FcmSlaNotiServiceImpl
 * 
 * @version 01-00
 * @since 01-00
 * @author TrieuVD
 */
@Service
public class FcmSlaNotiServiceImpl implements NotificationService {

    @Autowired
    private ObjectMapper mapper;

    @Autowired
    private FcmService fcmService;

    @Override
    public PushNotificationResponse pushNotification(PushNotificationRequest notificationRequest, Map<String, String> configMap) {
        FcmPushNotificationRequest request = new FcmPushNotificationRequest();
        Map<String, String> data = new HashMap<>();
        request.setTitle(notificationRequest.getTitle());
        request.setMessage(notificationRequest.getMessage());
        // request.setToken(notificationRequest.getToken());
        request.setMultipleTokens(notificationRequest.getTokenList());
        FcmPushNotificationResponse responeResult = fcmService.sendMessage(data, request);
        return mapper.convertValue(responeResult, PushNotificationResponse.class);
    }

    // @Override
    // public SlaNotificationResultDto pushNotification(SlaNotificationDto notiDto, Map<String, String> configNoti) {
    //
    // Map<String, String> data = new HashMap<>();
    // PushNotificationRequest request = new PushNotificationRequest();
    // request.setTitle(notiDto.getTitle());
    // request.setMessage(notiDto.getMessage());
    // request.setToken(notiDto.getNotificationToken());
    // request.setMultipleTokens(notiDto.getNotificationTokenList());
    //
    // PushNotificationResponse responeResult = fcmService.sendMessage(data, request);
    //
    // return mapper.convertValue(responeResult, SlaNotificationResultDto.class);
    // }

}
