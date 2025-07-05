package vn.com.unit.process.admin.sla.service.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.fasterxml.jackson.databind.ObjectMapper;

import vn.com.unit.common.utils.CommonCollectionUtil;
import vn.com.unit.core.security.UserProfileUtils;
import vn.com.unit.core.service.CommonService;
import vn.com.unit.ep2p.admin.sla.dto.SlaConfiguration.SlaInfomation.StepInfomation.SettingInfomation;
import vn.com.unit.ep2p.admin.sla.dto.SlaConfiguration.SlaInfomation.StepInfomation.SettingInfomation.DetailSettingInfomation;
import vn.com.unit.ep2p.admin.sla.dto.SlaSettingDto;
import vn.com.unit.ep2p.admin.sla.dto.SlaStepDto;
import vn.com.unit.ep2p.admin.sla.repository.SlaSettingAlertToRepository;
import vn.com.unit.ep2p.admin.sla.repository.SlaSettingRepository;
import vn.com.unit.process.admin.sla.service.SlaInfoService;
import vn.com.unit.process.admin.sla.service.SlaSettingService;
import vn.com.unit.sla.dto.SlaConfigAlertToDto;
import vn.com.unit.sla.dto.SlaConfigDetailDto;
import vn.com.unit.sla.dto.SlaConfigDto;
import vn.com.unit.sla.entity.SlaConfigDetail;
import vn.com.unit.sla.entity.SlaNotiAlertTo;
import vn.com.unit.sla.enumdef.AlertTypeEnum;
import vn.com.unit.sla.enumdef.ReceiverTypeEnum;
import vn.com.unit.sla.service.SlaConfigAlertToService;
import vn.com.unit.sla.service.SlaConfigService;
import vn.com.unit.sla.service.impl.SlaConfigDetailServiceImpl;
import vn.com.unit.workflow.dto.JpmSlaConfigDto;
import vn.com.unit.workflow.dto.JpmSlaInfoDto;
import vn.com.unit.workflow.service.JpmSlaConfigService;

@Service
@Primary
@Transactional(readOnly = true, rollbackFor = Exception.class)
public class SlaSettingServiceImpl extends SlaConfigDetailServiceImpl implements SlaSettingService {

//    private static final Logger logger = LoggerFactory.getLogger(SlaSettingServiceImpl.class);

    @Autowired
    private SlaSettingRepository settingRepository;

    @Autowired
    private SlaSettingAlertToRepository settingAlertToRepository;

//    @Autowired
//    private SlaStepRepository slaStepRepository;

    @Autowired
    private CommonService comService;

//    @Autowired
//    private AccountService accountService;
//
    @Autowired
    private SlaInfoService slaInfoService;
//
//    @Autowired
//    private TemplateService templateService;

    @Autowired
    private JpmSlaConfigService jpmSlaConfigService;

    @Autowired
    private SlaConfigService slaConfigService;

    @Autowired
    private SlaConfigAlertToService slaConfigAlertToService;

    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public List<SlaSettingDto> getListSettingByStepId(Long stepId) throws Exception {
        List<SlaSettingDto> listSettingDto = settingRepository.findListSettingByStepId(stepId, null, null, null);
        if (listSettingDto != null && !listSettingDto.isEmpty()) {
            for (SlaSettingDto dto : listSettingDto) {
                Long id = dto.getId();
                if (id != null) {
                    // Long templateId = dto.getEmailTemplateId();
                    // String name = templateService.getNameById(templateId);
                    // dto.setEmailTemplateName(name);
                    List<Long> listEmailToId = new ArrayList<>();
                    List<Long> listEmailCcId = new ArrayList<>();
                    List<SlaConfigAlertToDto> slaConfigAlertToDtoList = slaConfigAlertToService.getListByConfigDetailId(id);
                    if (CommonCollectionUtil.isNotEmpty(slaConfigAlertToDtoList)) {
                        for (SlaConfigAlertToDto configAlertToDto : slaConfigAlertToDtoList) {
                            Long emailId = null != configAlertToDto.getReceiverId() ? configAlertToDto.getReceiverId()
                                    : configAlertToDto.getInvoledType();
                            ReceiverTypeEnum receiverTypeEnum = ReceiverTypeEnum.resolveByValue(configAlertToDto.getReceiverType());
                            switch (receiverTypeEnum) {
                            case ACCOUNT_TO:
                                listEmailToId.add(emailId);
                                break;
                            case ACCOUNT_CC:
                                listEmailCcId.add(emailId);
                                break;
                            default:
                                break;
                            }
                        }
                    }
                    dto.setLstEmailToId(listEmailToId);
                    dto.setLstEmailCcId(listEmailCcId);
                }
            }
        }
        return listSettingDto;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveSLASetting(SlaStepDto slaStepDto, Locale locale) throws Exception {
        JpmSlaInfoDto jpmSlaInfoDto = slaInfoService.getJpmSlaInfoDtoById(slaStepDto.getSlaInfoId());
        if(null != jpmSlaInfoDto) {
            slaStepDto.setCalendarTypeId(jpmSlaInfoDto.getSlaCalendarTypeId());
        }
        // save SlaConfig
        SlaConfigDto slaConfigDto = objectMapper.convertValue(slaStepDto, SlaConfigDto.class);
        slaStepDto.setId(slaConfigService.saveSlaConfigDto(slaConfigDto).getId());
        // save JpmSlaConfig
        JpmSlaConfigDto jpmSlaConfigDto = objectMapper.convertValue(slaStepDto, JpmSlaConfigDto.class);
        jpmSlaConfigService.saveJpmSlaConfigDto(jpmSlaConfigDto);
        Long slaConfigId = jpmSlaConfigDto.getId();
        
        List<Long> deletedList = new ArrayList<Long>();
        List<SlaConfigDetailDto>  slaConfigDetailDtoList = this.getSlaConfigDetailDtoBySlaConfigId(slaConfigId);
        if(CommonCollectionUtil.isNotEmpty(slaConfigDetailDtoList)) {
            deletedList = slaConfigDetailDtoList.stream().map(SlaConfigDetailDto::getId).collect(Collectors.toList());
        }
        
        //save SlaConfigDetail
        List<SlaSettingDto> notificationList = slaStepDto.getNotificationList();
        if (CommonCollectionUtil.isNotEmpty(notificationList)) {
            for (SlaSettingDto slaSettingDto : notificationList) {
                slaSettingDto.setSlaConfigId(slaConfigId);
                slaSettingDto.setEmailSendFlag(null != slaSettingDto.getEmailTemplateId());
                slaSettingDto.setNotiSendFlag(null != slaSettingDto.getNotiTemplateId());
                slaSettingDto.setAlertType(AlertTypeEnum.NOTIFICATION.getValue());
                slaSettingDto.setActived(true);
                this.saveSlaSettingDto(slaSettingDto);
                deletedList.remove(slaSettingDto.getId());
            }
        }

        List<SlaSettingDto> escalateList = slaStepDto.getEscalateList();
        if (CommonCollectionUtil.isNotEmpty(escalateList)) {
            for (SlaSettingDto slaSettingDto : escalateList) {
                slaSettingDto.setSlaConfigId(slaConfigId);
                slaSettingDto.setEmailSendFlag(null != slaSettingDto.getEmailTemplateId());
                slaSettingDto.setNotiSendFlag(null != slaSettingDto.getNotiTemplateId());
                slaSettingDto.setAlertType(AlertTypeEnum.ESCALATE.getValue());
                this.saveSlaSettingDto(slaSettingDto);
                slaSettingDto.setActived(true);
                deletedList.remove(slaSettingDto.getId());
            }
        }

        List<SlaSettingDto> reminderList = slaStepDto.getReminderList();
        if (CommonCollectionUtil.isNotEmpty(reminderList)) {
            for (SlaSettingDto slaSettingDto : reminderList) {
                slaSettingDto.setSlaConfigId(slaConfigId);
                slaSettingDto.setEmailSendFlag(null != slaSettingDto.getEmailTemplateId());
                slaSettingDto.setNotiSendFlag(null != slaSettingDto.getNotiTemplateId());
                slaSettingDto.setAlertType(AlertTypeEnum.REMINDER.getValue());
                slaSettingDto.setActived(true);
                this.saveSlaSettingDto(slaSettingDto);
                deletedList.remove(slaSettingDto.getId());
            }
        }
        
        //Delete SlaConfigDetail
        for (Long  slaConfigDetailId : deletedList) {
            this.deleteById(slaConfigDetailId);
        }
    }

    /**
     * @param alertDto
     * @param lstEmail
     * @param companyId
     */
//    private void saveAlertTo(SlaSettingDto alertDto, Long companyId) throws Exception {
//        logger.debug("#saveAlertTo - SlaSettingDto : ", JsonUtils.convertObjectToJSON(alertDto));
        // List<SlaNotiAlertTo> listAlertTo = new ArrayList<>();
        // List<Long> emailToId = alertDto.getLstEmailToId();
        // Long settingId = alertDto.getId();
        // String emailType = EmailTypeEnum.EMAIL_TO.toString().trim();
        // List<JcaAccountDto> lstAccount = CollectionUtils.isEmpty(emailToId) ? new ArrayList<JcaAccountDto>() :
        // accountService.getAccountListByIds(emailToId);
        //
        // if (!CollectionUtils.isEmpty(emailToId)) {
        // for (Long user : emailToId) {
        // //handle only for accounts with id < 1
        // if(user < 1) {
        // JcaAccountDto account = new JcaAccountDto();
        // account.setUserId(user);
        // if(user < -1) {
        // account.setEmail(CommonConstant.MSG_SLA_MAIL_TRANSFER);
        // } else if(user < 0) {
        // account.setEmail(CommonConstant.MSG_SLA_MAIL_REQUEST);
        // } else {
        // account.setEmail(CommonConstant.MSG_SLA_MAIL_TO);
        // }
        // lstAccount.add(account);
        // }
        // }
        // }
        //
        // if (!CollectionUtils.isEmpty(lstAccount)) {
        // List<SlaNotiAlertTo> list = setSlaSettingAlert(lstAccount, emailType, settingId, companyId);
        // listAlertTo.addAll(list);
        // }
        // lstAccount.clear();
        // List<Long> emailCcId = alertDto.getLstEmailCcId();
        // emailType = EmailTypeEnum.EMAIL_CC.toString().trim();
        // if (!CollectionUtils.isEmpty(emailCcId)) {
        // List<JcaAccountDto> lstAccountCC = accountService.getAccountListByIds(emailCcId);
        // if(lstAccountCC != null && !CollectionUtils.isEmpty(lstAccountCC)) {
        // lstAccount.addAll(lstAccountCC);
        // }
        // for (Long user : emailCcId) {
        // //handle only for accounts with id < 1
        // if(user < 1) {
        // JcaAccountDto account = new JcaAccountDto();
        // account.setUserId(user);
        // if(user < -1) {
        // account.setEmail(CommonConstant.MSG_SLA_MAIL_RELATION);
        // } else if(user < 0) {
        // account.setEmail(CommonConstant.MSG_SLA_MAIL_REQUEST);
        // } else {
        // account.setEmail(CommonConstant.MSG_SLA_MAIL_CC);
        // }
        // lstAccount.add(account);
        // }
        // }
        // }
        //
        // if (!CollectionUtils.isEmpty(lstAccount)) {
        // List<SlaNotiAlertTo> list = setSlaSettingAlert(lstAccount, emailType, settingId, companyId);
        // listAlertTo.addAll(list);
        // }
        //
        // if (!listAlertTo.isEmpty()) {
        // logger.debug("#saveAlertTo - before saving a SlaSettingAlertTo List : ", JsonUtils.convertObjectToJSON(listAlertTo));
        //// settingAlertToRepository.save(listAlertTo);
        // }
//    }

    /**
     * @param slaAlertDto
     * @throws Exception
     */
//    private void saveAlert(SlaSettingDto slaAlertDto) throws Exception {
        // SlaSetting slaAlert = new SlaSetting();
        // slaAlertDto.setId(null);
        // String createdBy = UserProfileUtils.getUserNameLogin();
        // Date createdDate = comService.getSystemDateTime();
        // Long timeId = slaAlertDto.getUnitTimeId();
        // NullAwareBeanUtils.copyPropertiesWONull(slaAlertDto, slaAlert);
        // slaAlert.setCreatedBy(createdBy);
        // slaAlert.setCreatedDate(createdDate);
        // slaAlert.setUnitTimeId(timeId);
        // if(slaAlert.getIsTransfer() == null) {
        // slaAlert.setIsTransfer(Boolean.FALSE);
        // }
        // logger.debug("#saveAlert - before saving a SlaSetting : ", JsonUtils.convertObjectToJSON(slaAlert));
        // slaAlert = settingRepository.save(slaAlert);
        // slaAlertDto.setId(slaAlert.getId());
//    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void removeSetting(Long stepId) throws Exception {
        String deletedBy = UserProfileUtils.getUserNameLogin();
        Date deletedDate = comService.getSystemDateTime();

        List<SlaSettingDto> lstSettingDto = getListSettingByStepId(stepId);

        if (!CollectionUtils.isEmpty(lstSettingDto)) {
            for (SlaSettingDto settingDto : lstSettingDto) {
                if (settingDto.getId() != null) {
                    Long settingId = settingDto.getId();
                    settingAlertToRepository.deleteBySettingId(settingId, deletedBy, deletedDate);
                    settingRepository.deleteByStepId(settingId, deletedBy, deletedDate);
                }
            }
        }

    }

//    private List<SlaNotiAlertTo> setSlaSettingAlert(List<AccountDto> lstAccount, String emailType, Long settingId, Long companyId)
//            throws Exception {
//        List<SlaNotiAlertTo> listAlertTo = new ArrayList<>();
        // for (AccountDto user : lstAccount) {
        // Long id = user.getId();
        // String createdBy = UserProfileUtils.getUserNameLogin();
        // Date createdDate = comService.getSystemDateTime();
        // String email = user.getEmail();
        // SlaSettingAlertTo alertTo = new SlaSettingAlertTo();
        // alertTo.setAccountId(id);
        // alertTo.setCreatedBy(createdBy);
        // alertTo.setCreatedDate(createdDate);
        // alertTo.setUserType(UserTypeEnum.USER.toString());
        // alertTo.setSlaSettingId(settingId);
        // alertTo.setCompanyId(companyId);
        // alertTo.setEmail(email);
        // alertTo.setSendMailType(emailType);
        // listAlertTo.add(alertTo);
        // }
//        return listAlertTo;
//    }

    @Override
    public List<SettingInfomation> getSettingListBySlaStepId(Long stepId) throws Exception {
        return settingRepository.findSettingListBySlaStepId(stepId);
    }

    @Override
    public List<DetailSettingInfomation> getDetailListBySettingId(Long settingId) throws Exception {
        return settingAlertToRepository.findDetailListBySettingId(settingId);
    }

    @Override
    public List<SlaConfigDetail> getListByOldProcessId(Long stepId, Long oldProcessDeployId) throws SQLException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<SlaNotiAlertTo> getSettingListByOldProcessId(Long settingId, Long oldProcessDeployId) throws SQLException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void saveAlertList(List<SlaNotiAlertTo> alerts) throws SQLException {
        // TODO Auto-generated method stub

    }

    @Override
    public void deleteList(List<SlaConfigDetail> settings) throws SQLException {
        // TODO Auto-generated method stub

    }

    @Override
    public SlaSettingDto saveSlaSettingDto(SlaSettingDto slaSettingDto) throws Exception {
        SlaConfigDetailDto slaConfigDetailDto = objectMapper.convertValue(slaSettingDto, SlaConfigDetailDto.class);
        slaConfigDetailDto.setSlaConfigId(slaSettingDto.getSlaConfigId());
        // sla Alert to
        List<SlaConfigAlertToDto> alertToList = new ArrayList<>();
        List<Long> emailToIdList = slaSettingDto.getLstEmailToId();
        if (CommonCollectionUtil.isNotEmpty(emailToIdList)) {
            for (Long emailToId : emailToIdList) {
                SlaConfigAlertToDto slaConfigAlertToDto = new SlaConfigAlertToDto();
                if (emailToId > 0) {
                    slaConfigAlertToDto.setReceiverId(emailToId);
                } else {
                    slaConfigAlertToDto.setInvoledType(emailToId);
                }
                slaConfigAlertToDto.setReceiverType(ReceiverTypeEnum.ACCOUNT_TO.getValue());
                alertToList.add(slaConfigAlertToDto);
            }
        }
        List<Long> emailCcIdList = slaSettingDto.getLstEmailCcId();
        if (CommonCollectionUtil.isNotEmpty(emailCcIdList)) {
            for (Long emailCcId : emailCcIdList) {
                SlaConfigAlertToDto slaConfigAlertToDto = new SlaConfigAlertToDto();
                if (emailCcId > 0) {
                    slaConfigAlertToDto.setReceiverId(emailCcId);
                } else {
                    slaConfigAlertToDto.setInvoledType(emailCcId);
                }
                slaConfigAlertToDto.setReceiverType(ReceiverTypeEnum.ACCOUNT_CC.getValue());
                alertToList.add(slaConfigAlertToDto);
            }
        }

        slaConfigDetailDto.setAlertToList(alertToList);
        this.saveSlaConfigDetailDto(slaConfigDetailDto);
        return slaSettingDto;
    }

    // @Override
    // public List<SlaSetting> getListByOldProcessId(Long stepId, Long oldProcessDeployId) {
    // return settingRepository.findListByOldProcessId(stepId, oldProcessDeployId);
    // }
    //
    // @Override
    // public List<SlaSettingAlertTo> getSettingListByOldProcessId(Long settingId, Long oldProcessDeployId)
    // throws SQLException {
    // return settingAlertToRepository.findListByOldProcessId(settingId, oldProcessDeployId);
    // }
    //
    // @Override
    // public void saveAlertList(List<SlaSettingAlertTo> alerts) throws SQLException {
    // settingAlertToRepository.save(alerts);
    // }
    //
    // @Override
    // public SlaSetting saveOne(SlaSetting setting) throws SQLException {
    // return settingRepository.save(setting);
    // }
    //
    // @Override
    // public void deleteList(List<SlaSetting> settings) throws SQLException {
    // settingRepository.delete(settings);
    // }

}
