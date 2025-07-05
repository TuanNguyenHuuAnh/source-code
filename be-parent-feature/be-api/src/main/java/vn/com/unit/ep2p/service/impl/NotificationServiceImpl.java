/*******************************************************************************
 * Class        ：RoleForTeamServiceImpl
 * Created date ：2020/12/22
 * Lasted date  ：2020/12/22
 * Author       ：SonND
 * Change log   ：2020/12/22：01-00 SonND create a new
 ******************************************************************************/
package vn.com.unit.ep2p.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.MultiValueMap;

import vn.com.unit.core.dto.JcaAppInboxDto;
import vn.com.unit.core.entity.JcaAppInbox;
import vn.com.unit.core.res.ObjectDataRes;
import vn.com.unit.core.service.JcaAppInboxService;
import vn.com.unit.dts.exception.DetailException;
import vn.com.unit.ep2p.core.constant.AppApiExceptionCodeConstant;
import vn.com.unit.ep2p.core.service.AbstractCommonService;
import vn.com.unit.ep2p.dto.res.NotificationInfoRes;
import vn.com.unit.ep2p.service.NotificationService;

/**
 * RoleForTeamServiceImpl
 * 
 * @version 01-00
 * @since 01-00
 * @author SonND
 */
@Service
@Transactional(readOnly = true, rollbackFor = Exception.class)
public class NotificationServiceImpl extends AbstractCommonService implements NotificationService {

    @Autowired
    private JcaAppInboxService jcaAppInboxService;

    @Override
    public NotificationInfoRes getNoticationById(Long id) throws DetailException {
        NotificationInfoRes notificationInfoRes = null;
        JcaAppInboxDto jcaAppInboxDto = jcaAppInboxService.getJcaAppInboxDtoByAppInboxId(id);
        if (null != jcaAppInboxDto) {
            notificationInfoRes = this.convertJcaAppInboxDtoToNotificationInfoRes(jcaAppInboxDto);
        } else {
            throw new DetailException(AppApiExceptionCodeConstant.E4025001_APPAPI_NOTIFICATION_NOT_FOUND_BY_ID_ERROR, true);
        }

        return notificationInfoRes;
    }

    private NotificationInfoRes convertJcaAppInboxDtoToNotificationInfoRes(JcaAppInboxDto jcaAppInboxDto) {
        return objectMapper.convertValue(jcaAppInboxDto, NotificationInfoRes.class);
    }

    @Override
    public ObjectDataRes<JcaAppInboxDto> getAllNotication(MultiValueMap<String, String> requestParams, Pageable pageable) throws DetailException {
        ObjectDataRes<JcaAppInboxDto> resObj = null;
        try {
            /** init pageable */
            Pageable pageableAfterBuild = this.buildPageable(pageable, JcaAppInbox.class,JcaAppInboxService.TABLE_ALIAS_JCA_APP_INBOX);
            /** init param search repository */
            
            int totalData = jcaAppInboxService.countJcaAppInbox();
            List<JcaAppInboxDto> datas = new ArrayList<>();
            if (totalData > 0) {
                datas = jcaAppInboxService.getJcaAppInboxDto(pageableAfterBuild);
            }
            resObj = new ObjectDataRes<>(totalData, datas);
        } catch (Exception e) {
            handlerCastException.castException(e, AppApiExceptionCodeConstant.E4025006_APPAPI_NOTIFICATION_GET_INFO_BY_USER_ID_ERROR);
        }
        return resObj;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteNotificationById(Long id) throws DetailException {
        JcaAppInboxDto jcaAppInboxDto = jcaAppInboxService.getJcaAppInboxDtoByAppInboxId(id);
        if (null != jcaAppInboxDto) {
            try {
                jcaAppInboxService.deleteJcaAppInboxDtoByAppInboxId(id);
            } catch (Exception e) {
                handlerCastException.castException(e, AppApiExceptionCodeConstant.E4025003_APPAPI_NOTIFICATION_DELETE_BY_ID_ERROR);
            }
        } else {
            throw new DetailException(AppApiExceptionCodeConstant.E4025001_APPAPI_NOTIFICATION_NOT_FOUND_BY_ID_ERROR, true);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteAllNotification() throws DetailException {
        try {
            jcaAppInboxService.deleteAllJcaAppInboxDto();
        } catch (Exception e) {
            handlerCastException.castException(e, AppApiExceptionCodeConstant.E4025004_APPAPI_NOTIFICATION_DELETE_BY_USER_ID_ERROR);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void readNotification(Long notificationId, boolean readFlag) throws DetailException {
        try {
            if(readFlag) {
                jcaAppInboxService.readJcaAppInboxById(notificationId, readFlag);
            }else {
                jcaAppInboxService.readJcaAppInboxById(notificationId, false);
            }
            
        } catch (Exception e) {
            handlerCastException.castException(e, AppApiExceptionCodeConstant.E4025007_APPAPI_NOTIFICATION_READ_ITEM_ERROR);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void readAllNotification(boolean readFlag) throws DetailException {
        try {
            if(readFlag) {
                jcaAppInboxService.readAllJcaAppInbox(readFlag);
            }else {
                jcaAppInboxService.readAllJcaAppInbox(false);
            }
            
        } catch (Exception e) {
            handlerCastException.castException(e, AppApiExceptionCodeConstant.E4025008_APPAPI_NOTIFICATION_READ_ALL_ERROR);
        }
    }

}
