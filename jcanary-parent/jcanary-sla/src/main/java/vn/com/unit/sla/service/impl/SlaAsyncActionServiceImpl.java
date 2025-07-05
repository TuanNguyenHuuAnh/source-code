/*******************************************************************************
 * Class        ：SlaAsyncActionServiceImpl
 * Created date ：2021/01/15
 * Lasted date  ：2021/01/15
 * Author       ：TrieuVD
 * Change log   ：2021/01/15：01-00 TrieuVD create a new
 ******************************************************************************/
package vn.com.unit.sla.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import vn.com.unit.common.sla.dto.CreateSlaAlertParam;
import vn.com.unit.common.utils.CommonCollectionUtil;
import vn.com.unit.dts.exception.DetailException;
import vn.com.unit.sla.dto.SlaDataResultDto;
import vn.com.unit.sla.dto.SlaEmailAlertDto;
import vn.com.unit.sla.dto.SlaNotiAlertDto;
import vn.com.unit.sla.dto.SlaReceiverDto;
import vn.com.unit.sla.email.dto.SlaEmailDto;
import vn.com.unit.sla.email.dto.SlaEmailResultDto;
import vn.com.unit.sla.email.service.SlaEmailService;
import vn.com.unit.sla.enumdef.AlertStatusEnum;
import vn.com.unit.sla.enumdef.ReceiverTypeEnum;
import vn.com.unit.sla.noti.dto.SlaNotiTemplateDto;
import vn.com.unit.sla.noti.dto.SlaNotificationDto;
import vn.com.unit.sla.noti.dto.SlaNotificationResultDto;
import vn.com.unit.sla.noti.service.SlaDeviceTokenService;
import vn.com.unit.sla.noti.service.SlaNotiTemplateService;
import vn.com.unit.sla.noti.service.SlaNotificationService;
import vn.com.unit.sla.service.SlaActionService;
import vn.com.unit.sla.service.SlaAsyncActionService;
import vn.com.unit.sla.service.SlaEmailAlertService;
import vn.com.unit.sla.service.SlaNotiAlertService;
import vn.com.unit.sla.service.SlaReceiverService;
import vn.com.unit.sla.utils.SlaCollectionUtils;
import vn.com.unit.sla.utils.SlaJsonUtils;

/**
 * SlaAsyncActionServiceImpl
 * 
 * @version 01-00
 * @since 01-00
 * @author TrieuVD
 */
@Async
@Service
@Transactional(readOnly = true, rollbackFor = Exception.class)
public class SlaAsyncActionServiceImpl extends AbstractSlaService implements SlaAsyncActionService {

    @Autowired
    private SlaEmailService slaEmailService;

    @Autowired
    private SlaNotificationService slaNotificationService;

    @Autowired
    private SlaNotiAlertService slaNotiAlertService;

    @Autowired
    private SlaEmailAlertService slaEmailAlertService;

    @Autowired
    private SlaReceiverService slaReceiverService;

    @Autowired
    private SlaDeviceTokenService slaDeviceTokenService;

    @Autowired
    private SlaActionService slaActionService;

    @Autowired
    private SlaNotiTemplateService slaNotiTemplateService;

    // @Autowired
    // private SlaAlertHistoryService slaAlertHistoryService;

    /*
     * (non-Javadoc)
     * 
     * @see vn.com.unit.sla.service.SlaAsyncActionService#sendNotification(vn.com.unit.sla.dto.SlaAlertDto, boolean)
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
    public SlaDataResultDto sendNotification(SlaNotiAlertDto alertDto, boolean createAlertHisFlag) {
        SlaDataResultDto dataResultDto = new SlaDataResultDto();
        SlaNotificationDto notiDto = new SlaNotificationDto();
        Long notiTempalteId = alertDto.getNotiTemplateId();
        String notiJsonData = alertDto.getNotiJsonData();
        Map<String, String> data = SlaJsonUtils.slaConvertJSONToMap(notiJsonData);
        SlaNotiTemplateDto notiTemplateDto = this.getSlaNotiTemplateDto(notiTempalteId, data);
        if(null != notiTemplateDto) {
            notiDto.setTitle(alertDto.getNotiTitle());
            notiDto.setMessage(alertDto.getNotiContent());
            data.put("messageFullLang", SlaJsonUtils.slaConvertObjectToJson(notiTemplateDto));
            notiDto.setData(data);
            List<SlaReceiverDto> slaReceiverDtoList = alertDto.getSlaReceiverDtoList();
            if (SlaCollectionUtils.isNotEmpty(slaReceiverDtoList)) {
                slaReceiverDtoList = slaReceiverDtoList.stream().filter(SlaCollectionUtils.distinctByKey(SlaReceiverDto::getReceiverId))
                        .collect(Collectors.toList());
                List<String> notificationTokenList = getNotificationTokenList(slaReceiverDtoList);
                // List<String> notificationTokenList = new ArrayList<String>();
                // notificationTokenList.add("ersAHO-S2jbZ-tGS-aft68:APA91bG-6KbCG4pSrwc-U4M9TusvL_UetBbjMoYGXGcN52miorEi-ThkHBUwt-Y6bWiVzLFNmMSB84wOWg4YONO8q_sB_GdRoNYN8pMuvwdW_KFAa-zp9pY6e1GQf5GQLeV9La7sBuCN");
                notiDto.setTokenList(notificationTokenList);
                notiDto.setAccountIdList(slaReceiverDtoList.stream().map(SlaReceiverDto::getReceiverId).collect(Collectors.toList()));
            }
            SlaNotificationResultDto notiResult = slaNotificationService.pushNotification(notiDto);
            if (createAlertHisFlag) {
                Integer statust = notiResult.isStatus() ? AlertStatusEnum.SUCCESS.getValue() : AlertStatusEnum.FAIL.getValue();
                slaNotiAlertService.moveSlaNotiAlertToSlaNotiAlertHistory(alertDto, statust, SlaJsonUtils.slaConvertObjectToJson(notiResult));
            }
            dataResultDto.setStatus(notiResult.isStatus());
            dataResultDto.setMessage(notiResult.getErrorMessage());
        }
        return dataResultDto;
    }

    /*
     * (non-Javadoc)
     * 
     * @see vn.com.unit.sla.service.SlaAsyncActionService#sendEmail(vn.com.unit.sla.dto.SlaAlertDto, boolean)
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
    public SlaDataResultDto sendEmail(SlaEmailAlertDto alertDto, boolean createAlertHisFlag) {
        SlaDataResultDto dataResultDto = new SlaDataResultDto();
        SlaEmailDto slaEmailDto = new SlaEmailDto();
        slaEmailDto.setSubject(alertDto.getEmailSubject());
        slaEmailDto.setEmailContent(alertDto.getEmailContent());
        slaEmailDto.setContentType("text/html; charset=utf-8");

        List<String> emailToList = new ArrayList<>();
        List<String> emailCcList = new ArrayList<>();
        List<String> emailBccList = new ArrayList<>();
        List<SlaReceiverDto> slaReceiverDtoList = alertDto.getSlaReceiverDtoList();

        List<Long> listToUserId = new ArrayList<>();
        List<Long> listCCUserId = new ArrayList<>();
        List<Long> listBCCUserId = new ArrayList<>();

        List<String> listToUserBusiness = new ArrayList<>();
        List<String> listCCUserBusiness = new ArrayList<>();
        List<String> listBCCUserBusiness = new ArrayList<>();
        
        for (SlaReceiverDto slaReceiverDto : slaReceiverDtoList) {
            Integer receiverType = slaReceiverDto.getReceiverType();
            ReceiverTypeEnum receiverTypeEnum = ReceiverTypeEnum.resolveByValue(receiverType);
            switch (receiverTypeEnum) {
            case ACCOUNT_TO:
                listToUserId.add(slaReceiverDto.getReceiverId());
                if (StringUtils.isNotBlank(slaReceiverDto.getBusinessEmail())) {
                    listToUserBusiness.add(slaReceiverDto.getBusinessEmail());
                }
                break;
            case ACCOUNT_CC:
                listCCUserId.add(slaReceiverDto.getReceiverId());
                if (StringUtils.isNotBlank(slaReceiverDto.getBusinessEmail())) {
                    listCCUserBusiness.add(slaReceiverDto.getBusinessEmail());
                }
                break;
            case ACCOUNT_BCC:
                listBCCUserId.add(slaReceiverDto.getReceiverId());
                if (StringUtils.isNotBlank(slaReceiverDto.getBusinessEmail())) {
                    listBCCUserBusiness.add(slaReceiverDto.getBusinessEmail());
                }
                break;
            default:
                break;
            }
        }

        if (!listToUserId.isEmpty()) {
            emailToList = slaReceiverService.getListEmailByListReceiverId(listToUserId);
        }
        if (!listCCUserId.isEmpty()) {
            emailCcList = slaReceiverService.getListEmailByListReceiverId(listCCUserId);
        }
        if (!listBCCUserId.isEmpty()) {
            emailBccList = slaReceiverService.getListEmailByListReceiverId(listBCCUserId);
        }
        
        /**
         * TaiTM: Thêm email business
         * */
        addEmail(emailToList, listToUserBusiness);
        addEmail(listCCUserBusiness, emailCcList);
        addEmail(listBCCUserBusiness, emailBccList);
        
        slaEmailDto.setToAddress(emailToList);
        slaEmailDto.setCcAddress(emailCcList);
        slaEmailDto.setBccAddress(emailBccList);

        SlaEmailResultDto emailResultDto = slaEmailService.sendEmail(slaEmailDto);
        dataResultDto.setStatus(emailResultDto.isStatus());
        dataResultDto.setMessage(emailResultDto.getErrorMessage());

        if (createAlertHisFlag) {
            Integer statust = emailResultDto.isStatus() ? AlertStatusEnum.SUCCESS.getValue() : AlertStatusEnum.FAIL.getValue();
            slaEmailAlertService.moveSlaAlertToSlaAlertHistory(alertDto, statust, SlaJsonUtils.slaConvertObjectToJson(emailResultDto));
        }

        return dataResultDto;
    }

    /**
     * getNotificationTokenList
     * 
     * @param slaAccountList
     * @return
     * @author TrieuVD
     */
    private List<String> getNotificationTokenList(List<SlaReceiverDto> slaReceiverDtoList) {
        List<String> resuList = new ArrayList<>();
        if (CommonCollectionUtil.isNotEmpty(slaReceiverDtoList)) {
            List<Long> receiverIdList = slaReceiverDtoList.stream().map(receiver -> {
                return receiver.getReceiverId();
            }).collect(Collectors.toList());
            resuList = slaDeviceTokenService.getListTokenByListReceiverIdList(receiverIdList);
        }
        return resuList;
    }

    /*
     * (non-Javadoc)
     * 
     * @see vn.com.unit.sla.service.SlaAsyncActionService#updateSlaAlertByIdList(java.lang.String, int, java.util.List)
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
    public void updateSlaNotiAlertByIdList(Integer status, List<Long> idList) {
        if (CommonCollectionUtil.isNotEmpty(idList)) {
            slaNotiAlertService.updateStatusByIdList(status, idList);
        }
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
    public void updateSlaEmailAlertByIdList(Integer status, List<Long> idList) {
        if (CommonCollectionUtil.isNotEmpty(idList)) {
            slaEmailAlertService.updateStatusByIdList(status, idList);
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see vn.com.unit.sla.service.SlaAsyncActionService#createSlaAlertByConfig(vn.com.unit.sla.dto.SlaCreateAlertParam)
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void createSlaAlertByConfig(CreateSlaAlertParam slaCreateAlertParam) {
        try {
            slaActionService.createSlaAlertByConfig(slaCreateAlertParam);
        } catch (DetailException e) {
            e.printStackTrace();
        }
    }

    private SlaNotiTemplateDto getSlaNotiTemplateDto(Long slaNotiTempalteId, Map<String, String> mapData) {
        return slaNotiTemplateService.getSlaNotiTemplateDtoBytemplateId(slaNotiTempalteId, mapData);
    }

    private void addEmail(List<String> results, List<String> listEmails) {
        if (!listEmails.isEmpty()) {
            if (results == null) {
                results = new ArrayList<String>();
            }
            results.addAll(listEmails);
        }
    }
}
