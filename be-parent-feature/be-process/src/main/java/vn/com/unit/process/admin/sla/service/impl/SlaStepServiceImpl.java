package vn.com.unit.process.admin.sla.service.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.ObjectMapper;

import vn.com.unit.common.service.JCommonService;
import vn.com.unit.common.utils.CommonStringUtil;
import vn.com.unit.core.security.UserProfileUtils;
import vn.com.unit.ep2p.admin.sla.dto.SlaSettingDto;
import vn.com.unit.ep2p.admin.sla.dto.SlaStepDto;
import vn.com.unit.ep2p.admin.sla.dto.SlaConfiguration.SlaInfomation.StepInfomation;
import vn.com.unit.ep2p.admin.sla.repository.SlaStepRepository;
import vn.com.unit.process.admin.sla.service.SlaSettingService;
import vn.com.unit.process.admin.sla.service.SlaStepService;
import vn.com.unit.sla.entity.SlaConfig;
import vn.com.unit.sla.enumdef.AlertTypeEnum;
import vn.com.unit.sla.service.SlaConfigService;
import vn.com.unit.workflow.dto.JpmSlaConfigDto;
import vn.com.unit.workflow.dto.JpmSlaConfigSearchDto;
import vn.com.unit.workflow.entity.JpmSlaConfig;
import vn.com.unit.workflow.service.impl.JpmSlaConfigServiceImpl;

@Service
@Primary
@Transactional(readOnly = true, rollbackFor = Exception.class)
public class SlaStepServiceImpl extends JpmSlaConfigServiceImpl implements SlaStepService {

    @Autowired
    private SlaStepRepository stepRepository;

    @Autowired
    private SlaSettingService slaSettingService;

    @Autowired
    private JCommonService comService;
    
    @Autowired
    private SlaConfigService slaConfigService;

    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public List<SlaStepDto> getListStepByInfoId(Long slaInfoId, Locale locale) {
        String lang = "EN";
        if(null != locale) {
            lang = locale.getLanguage().toUpperCase();
        }
        return stepRepository.findListBySlaInfoId(slaInfoId, lang);
    }

    @Override
    public SlaConfig findOneById(Long id) throws SQLException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public SlaConfig saveStep(JpmSlaConfig step) throws SQLException {
        // TODO Auto-generated method stub
        return null;
    }

    // @Override
    // public void save(Iterable<StepInfomation> steps) throws SQLException {
    // // TODO Auto-generated method stub
    //
    // }

    @Override
    public void saveListSlaStep(List<SlaConfig> slaSteps) throws Exception {
        // TODO Auto-generated method stub

    }

    @Override
    public SlaStepDto getSlaStepDtoById(Long slaStepId, String lang) throws Exception {
        SlaStepDto resultDto = new SlaStepDto();
        JpmSlaConfigDto jpmSlaConfigDto = this.getJpmSlaConfigDtoById(slaStepId, lang);
        if (null != jpmSlaConfigDto) {
            resultDto = objectMapper.convertValue(jpmSlaConfigDto, SlaStepDto.class);
        }
        // get setting list
        if (resultDto != null && null != resultDto.getId()) {
            List<SlaSettingDto> lstAlertDto = slaSettingService.getListSettingByStepId(resultDto.getId());
            if (lstAlertDto != null && !lstAlertDto.isEmpty()) {
                List<SlaSettingDto> notificationList = new ArrayList<>();
                List<SlaSettingDto> reminderList = new ArrayList<>();
                List<SlaSettingDto> escalateList = new ArrayList<>();
                for (SlaSettingDto dto : lstAlertDto) {
                    Integer alertType = dto.getAlertType();
                    AlertTypeEnum alertTypeEnum = AlertTypeEnum.resolveByValue(alertType);
                    switch (alertTypeEnum) {
                    case NOTIFICATION:
                        notificationList.add(dto);
                        break;
                    case REMINDER:
                        reminderList.add(dto);
                        break;
                    case ESCALATE:
                        escalateList.add(dto);
                        break;
                    default:
                        break;
                    }
                }
                resultDto.setNotificationList(notificationList);
                resultDto.setEscalateList(escalateList);
                resultDto.setReminderList(reminderList);
            }
        }
        return resultDto;
    }

    @Override
    public void deleteStepSetting(Long id) throws Exception {
        JpmSlaConfig jpmSlaConfig = this.findOne(id);
        if(null != jpmSlaConfig) {
            Long slaConfigId = jpmSlaConfig.getSlaConfigId();
            jpmSlaConfig.setSlaConfigId(null);
            jpmSlaConfig.setDeletedId(UserProfileUtils.getAccountId());
            jpmSlaConfig.setDeletedDate(comService.getSystemDate());
            this.update(jpmSlaConfig);
            if(null != slaConfigId) {
                slaConfigService.deleteById(slaConfigId);
            }
        }
    }

    @Override
    public List<StepInfomation> getStepListBySlaId(Long slaId) throws Exception {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<SlaStepDto> getListBySlaInfoId(Long slaInfoId, String lang) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void deleteByInfoId(Long id, Long deletedBy, Date deletedDate) {
        // TODO Auto-generated method stub

    }

    @Override
    public List<SlaConfig> findListByOldProcessId(Long oldProcessDeployId) throws SQLException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void deleteList(List<SlaConfig> steps) throws SQLException {
        // TODO Auto-generated method stub

    }

    @Override
    public List<JpmSlaConfigDto> getJpmSlaConfigDtoByInfoIdAndLang(Long slaInfoId, String langCode) {
        if(CommonStringUtil.isBlank(langCode)) {
            langCode = UserProfileUtils.getLanguage();
        }
        JpmSlaConfigSearchDto searchDto = new JpmSlaConfigSearchDto();
        searchDto.setJpmSlaInfoId(slaInfoId);
        searchDto.setLangCode(langCode);
        return this.getJpmSlaConfigDtoListBySearchDto(searchDto);
    }

    // @Override
    // public void saveListSlaStep(List<JpmSlaConfig> slaSteps) throws Exception {
    // //stepRepository.save(slaSteps);
    // }
    //
    // @Override
    // public SlaStepDto getSlaStepDtoById(Long slaStepId) throws Exception {
    // SlaStepDto stepDto = stepRepository.getSlaStepDtoById(slaStepId);
    // if (stepDto != null) {
    // List<SlaSettingDto> lstAlertDto = slaSettingService.getListSettingByStepId(slaStepId);
    // if (lstAlertDto != null && !lstAlertDto.isEmpty()) {
    // List<SlaSettingDto> notificationList = new ArrayList<>();
    // List<SlaSettingDto> reminderList = new ArrayList<>();
    // List<SlaSettingDto> escalateList = new ArrayList<>();
    // for (SlaSettingDto dto : lstAlertDto) {
    // String alertType = StringUtils.isNotBlank(dto.getAlertType()) ? dto.getAlertType() : StringUtils.EMPTY;
    // String reminder = AlertTypeEnum.REMINDER.toString().trim();
    // String escalate = AlertTypeEnum.ESCALATE.toString().trim();
    //
    // if (alertType.equals(reminder)) {
    // reminderList.add(dto);
    // } else if(alertType.equals(escalate)) {
    // escalateList.add(dto);
    // } else {
    // //stepDto.setNotification(dto);
    // notificationList.add(dto);
    // }
    // }
    // stepDto.setNotificationList(notificationList);
    // stepDto.setEscalateList(escalateList);
    // stepDto.setReminderList(reminderList);
    // }
    // }
    // return stepDto;
    // }
    //
    // @Override
    // @Transactional
    // public void deleteStepSetting(Long id) throws Exception {
    // String deletedBy = UserProfileUtils.getUserNameLogin();
    // Date deletedDate = comService.getSystemDateTime();
    //
    // // Delete all setting alert to by settingId
    // slaSettingService.removeSetting(id);
    //
    // // Reset workTime of step
    // SlaStep step = stepRepository.findOne(id);
    // step.setActived(false);
    // step.setWorkTime(null);
    // step.setTimeType(null);
    // step.setUpdatedBy(deletedBy);
    // step.setUpdatedDate(deletedDate);
    // stepRepository.save(step);
    // }
    //
    // @Override
    // public List<StepInfomation> getStepListBySlaId(Long slaId) throws Exception {
    // return stepRepository.findStepListBySlaId(slaId);
    // }
    //
    // @Override
    // public SlaStep findOneById(Long id) throws SQLException {
    // return stepRepository.findOne(id);
    // }
    //
    // @Override
    // public SlaStep saveStep(SlaStep step) throws SQLException {
    // return stepRepository.save(step);
    // }
    //
    // @Override
    // public List<SlaStepDto> getListBySlaInfoId(Long slaInfoId, String lang) {
    // return stepRepository.findListBySlaInfoId(slaInfoId, lang);
    // }
    //
    // @Override
    // public void deleteByInfoId(Long id, String deletedBy, Date deletedDate) {
    // stepRepository.deleteByInfoId(id, deletedBy, deletedDate);
    // }
    //
    // @Override
    // public void save(Iterable<SlaConfiguration.SlaInfomation.StepInfomation> steps) throws SQLException {
    // stepRepository.save(steps);
    // }
    //
    // @Override
    // public List<SlaStep> findListByOldProcessId(Long oldProcessDeployId) throws SQLException {
    // return stepRepository.findListByOldProcessId(oldProcessDeployId);
    // }
    //
    // @Override
    // public void deleteList(List<SlaStep> steps) throws SQLException {
    // stepRepository.delete(steps);
    // }
}
