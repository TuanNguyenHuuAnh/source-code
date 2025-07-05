/*******************************************************************************
 * Class        ：SlaActionServiceImpl
 * Created date ：2020/11/11
 * Lasted date  ：2020/11/11
 * Author       ：NganNH
 * Change log   ：2020/11/11：01-00 NganNH create a new
 ******************************************************************************/
package vn.com.unit.sla.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.commons.collections4.map.HashedMap;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import vn.com.unit.common.constant.CommonConstant;
import vn.com.unit.common.sla.dto.CalculateDueDateParam;
import vn.com.unit.common.sla.dto.CalculateElapsedMinutesParam;
import vn.com.unit.common.sla.dto.CreateSlaAlertParam;
import vn.com.unit.common.utils.CommonCollectionUtil;
import vn.com.unit.dts.exception.DetailException;
import vn.com.unit.sla.dto.SlaAlertCreateResult;
import vn.com.unit.sla.dto.SlaConfigAlertToDto;
import vn.com.unit.sla.dto.SlaConfigDetailDto;
import vn.com.unit.sla.dto.SlaConfigDto;
import vn.com.unit.sla.dto.SlaDateResultDto;
import vn.com.unit.sla.dto.SlaEmailAlertDto;
import vn.com.unit.sla.dto.SlaNotiAlertDto;
import vn.com.unit.sla.dto.SlaReceiverDto;
import vn.com.unit.sla.email.dto.SlaEmailTemplateDto;
import vn.com.unit.sla.email.service.SlaEmailTemplateService;
import vn.com.unit.sla.enumdef.AlertStatusEnum;
import vn.com.unit.sla.enumdef.AlertTypeEnum;
import vn.com.unit.sla.service.SlaActionService;
import vn.com.unit.sla.service.SlaAsyncActionService;
import vn.com.unit.sla.service.SlaCalculateService;
import vn.com.unit.sla.service.SlaConfigDetailService;
import vn.com.unit.sla.service.SlaConfigService;
import vn.com.unit.sla.service.SlaEmailAlertService;
import vn.com.unit.sla.service.SlaNotiAlertService;
import vn.com.unit.sla.service.SlaNotiAlertToService;
import vn.com.unit.sla.utils.SlaDateUtils;
import vn.com.unit.sla.utils.SlaJsonUtils;

/**
 * <p>
 * SlaActionServiceImpl
 * </p>
 * 
 *
 * @version 01-00
 * @since 01-00
 * @author TrieuVD
 */
@Service
@Transactional(readOnly = true, rollbackFor = Exception.class)
public class SlaActionServiceImpl extends AbstractSlaService implements SlaActionService {

    /** The sla config service. */
    @Autowired
    private SlaConfigService slaConfigService;

    /** The sla config detail service. */
    @Autowired
    private SlaConfigDetailService slaConfigDetailService;

    /** The sla alert service. */
    @Autowired
    private SlaNotiAlertService slaNotiAlertService;

    @Autowired
    private SlaEmailAlertService slaEmailAlertService;

    /** The sla template service. */
    @Autowired
    private SlaEmailTemplateService slaEmailTemplateService;

    /** The sla account service. */
//    @Autowired
//    private SlaReceiverService slaReceiverService;

    /** The sla calculate service. */
    @Autowired
    private SlaCalculateService slaCalculateService;

    /** The sla async action service. */
    @Autowired
    private SlaAsyncActionService slaAsyncActionService;

    /** The sla alert to service. */
    @Autowired
    private SlaNotiAlertToService slaAlertToService;

    /*
     * (non-Javadoc)
     * 
     * @see vn.com.unit.sla.service.SlaActionService#createSlaAlertByConfig(vn.com.unit.sla.dto.SlaReqParamDto)
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public SlaAlertCreateResult createSlaAlertByConfig(CreateSlaAlertParam slaCreateAlertParam) throws DetailException {
        SlaAlertCreateResult result = new SlaAlertCreateResult();
        List<Long> slaNotiAlert = new ArrayList<>();
        List<Long> slaEmailAlert = new ArrayList<>();
        Date submitDate = slaCreateAlertParam.getSubmitDate();
        Date dueDate = slaCreateAlertParam.getSubmitDate();
        Long slaConfigId = slaCreateAlertParam.getSlaConfigId();
        Map<String, Object> mapData = slaCreateAlertParam.getMapData();
        if (CommonCollectionUtil.isEmpty(mapData)) {
            mapData = new HashedMap<>();
        }
        // SlaConfigDto configDto = slaConfigService.getSlaConfigDtoById(slaConfigId);
        List<SlaConfigDetailDto> configDetailDtoList = slaConfigDetailService.findAllByConfigId(slaConfigId);
        for (SlaConfigDetailDto slaConfigDetailDto : configDetailDtoList) {
            if (slaConfigDetailDto.isNotiSendFlag()) {
                slaNotiAlert.addAll(this.createSlaNotiAlert(submitDate, dueDate, slaConfigDetailDto, mapData));
            }
            if (slaConfigDetailDto.isEmailSendFlag()) {
                slaEmailAlert.addAll(this.createSlaEmailAlert(submitDate, dueDate, slaConfigDetailDto, mapData));
            }
        }
        result.setSlaEmailAlertIdList(slaEmailAlert);
        result.setSlaNotiAlertIdList(slaNotiAlert);
        return result;
    }

    /*
     * (non-Javadoc)
     * 
     * @see vn.com.unit.sla.service.SlaActionService#scanSlaAlerJob(java.util.Date, java.util.Date, int)
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void scanSlaNotiAlertJob(Date fromDate, Date toDate) {
        List<Long> idAlertFailList = new ArrayList<>();
        // get list
        List<SlaNotiAlertDto> slaAlertDtos = slaNotiAlertService.getSlaNotiAlertDtoListByCondition(fromDate, toDate,
                AlertStatusEnum.WAITING.getValue());
        // update status
        List<Long> idList = slaAlertDtos.stream().map(SlaNotiAlertDto::getId).collect(Collectors.toList());
        slaAsyncActionService.updateSlaNotiAlertByIdList(AlertStatusEnum.IN_PROGRESS.getValue(), idList);
        for (SlaNotiAlertDto slaAlertDto : slaAlertDtos) {
            try {
                slaAlertDto.setSlaReceiverDtoList(slaAlertToService.getSlaAccountDtoListByAlertId(slaAlertDto.getId(), null));
                slaAsyncActionService.sendNotification(slaAlertDto, true);
            } catch (Exception e) {
                idAlertFailList.add(slaAlertDto.getId());
            }
        }
        if (CommonCollectionUtil.isNotEmpty(idAlertFailList)) {
            for (Long idAlertFail : idAlertFailList) {
                slaNotiAlertService.moveSlaNotiAlertToSlaNotiAlertHistoryById(idAlertFail, AlertStatusEnum.ERROR.getValue(),
                        "[scanSlaNotiAlertJob]");
            }
        }
    }

    @Override
    public void scanSlaEmailAlertJob(Date fromDate, Date toDate) {
        List<Long> idAlertFailList = new ArrayList<>();
        // get list
        List<SlaEmailAlertDto> slaAlertDtos = slaEmailAlertService.getSlaEmailAlertDtoListByCondition(fromDate, toDate,
                AlertStatusEnum.WAITING.getValue());
        // update status
        List<Long> idList = slaAlertDtos.stream().map(SlaEmailAlertDto::getId).collect(Collectors.toList());
        slaAsyncActionService.updateSlaNotiAlertByIdList(AlertStatusEnum.IN_PROGRESS.getValue(), idList);
        for (SlaEmailAlertDto slaAlertDto : slaAlertDtos) {
            try {
                slaAlertDto.setSlaReceiverDtoList(slaAlertToService.getSlaAccountDtoListByAlertId(slaAlertDto.getId(), null));
                slaAsyncActionService.sendEmail(slaAlertDto, true);
            } catch (Exception e) {
                idAlertFailList.add(slaAlertDto.getId());
            }
        }
        if (CommonCollectionUtil.isNotEmpty(idAlertFailList)) {
            for (Long idAlertFail : idAlertFailList) {
                slaEmailAlertService.moveSlaEmailAlertToSlaEmailAlertHistoryById(idAlertFail, AlertStatusEnum.ERROR.getValue(),
                        "[scanSlaEmailAlertJob]");
            }
        }
    }

    @Override
    @Transactional
    public List<Long> createSlaNotiAlert(Date submitDate, Date dueDate, SlaConfigDetailDto slaConfigDetailDto, Map<String, Object> mapData)
            throws DetailException {
        List<Long> slaAlertIdList = new ArrayList<>();
        Long calendarTypeId = slaConfigDetailDto.getCalendarTypeId();
        if (null != calendarTypeId) {
            Integer alertType = slaConfigDetailDto.getAlertType();
            Long notiTemplateId = slaConfigDetailDto.getNotiTemplateId();

            SlaNotiAlertDto alertDto = new SlaNotiAlertDto();
            alertDto.setAlertType(alertType);
            alertDto.setNotiTemplateId(notiTemplateId);
            String notiJsonData = SlaJsonUtils.slaConvertObjectToJson(mapData);
            alertDto.setNotiJsonData(notiJsonData);

            alertDto.setSlaReceiverDtoList(this.getSlaReceiverDtoListByConfig(slaConfigDetailDto, mapData));
            if (null == dueDate) {
                dueDate = this.getDueDateByConfigAndCalendarTypeId(slaConfigDetailDto, submitDate, calendarTypeId);
            }
            alertDto.setAlertDate(dueDate);
            // check type
            AlertTypeEnum alertTypeEnum = AlertTypeEnum.resolveByValue(alertType);
            switch (alertTypeEnum) {
            case NOTIFICATION:
                slaAsyncActionService.sendNotification(alertDto, true);
                break;
            case REMINDER:
            case ESCALATE:
                slaAlertIdList.add(slaNotiAlertService.saveSlaNotiAlertDto(alertDto).getId());
                break;
            default:
                break;
            }
        }
        return slaAlertIdList;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public List<Long> createSlaEmailAlert(Date submitDate, Date dueDate, SlaConfigDetailDto slaConfigDetailDto, Map<String, Object> mapData)
            throws DetailException {
        List<Long> slaAlertIdList = new ArrayList<>();
        Long calendarTypeId = slaConfigDetailDto.getCalendarTypeId();
        if (null != calendarTypeId) {
            Integer alertType = slaConfigDetailDto.getAlertType();
            Long emailTemplateId = slaConfigDetailDto.getEmailTemplateId();

            SlaEmailAlertDto alertDto = new SlaEmailAlertDto();
            alertDto.setAlertType(alertType);
            // set content, subject email
            SlaEmailTemplateDto emailTemplateDto = slaEmailTemplateService.getSlaEmailTemplateDtoByTemplateId(emailTemplateId, mapData);
            if (null != emailTemplateDto) {
                // Lấy ra đanh sách để gửi email
                alertDto.setSlaReceiverDtoList(this.getSlaReceiverDtoListByConfig(slaConfigDetailDto, mapData));
                
                alertDto.setEmailSubject(emailTemplateDto.getSubject());
                alertDto.setEmailContent(emailTemplateDto.getEmailContent());
                if (null == dueDate) {
                    dueDate = this.getDueDateByConfigAndCalendarTypeId(slaConfigDetailDto, submitDate, calendarTypeId);
                }
                alertDto.setAlertDate(dueDate);
                // check type
                AlertTypeEnum alertTypeEnum = AlertTypeEnum.resolveByValue(alertType);
                switch (alertTypeEnum) {
                case NOTIFICATION:
                    slaAsyncActionService.sendEmail(alertDto, true);
                    break;
                case REMINDER:
                case ESCALATE:
                    slaAlertIdList.add(slaEmailAlertService.saveSlaEmailAlertDto(alertDto).getId());
                    break;
                default:
                    break;
                }
            }
        }
        return slaAlertIdList;
    }

    /**
     * <p>
     * Get account list by config.
     * </p>
     *
     * @param slaConfigDetailDto
     *            type {@link SlaConfigDetailDto}
     * @param alertDto
     *            type {@link SlaNotiAlertDto}
     * @param mapData
     *            type {@link Map<String,String>}
     * @return {@link void}
     * @author TrieuVD
     */
    @SuppressWarnings("unchecked")
    private List<SlaReceiverDto> getSlaReceiverDtoListByConfig(SlaConfigDetailDto slaConfigDetailDto, Map<String, Object> mapData) {
        // get slaReceiverDtoList
        List<SlaReceiverDto> slaReceiverDtoList = new ArrayList<>();
        List<SlaConfigAlertToDto> alertToDtoList = slaConfigDetailDto.getAlertToList();

        List<Long> assgineeIdList = (List<Long>) mapData.getOrDefault(CreateSlaAlertParam.ASSGINEE_ID_LIST, null);
        List<Long> submittedIdList = (List<Long>) mapData.getOrDefault(CreateSlaAlertParam.SUBMITTED_ID_LIST, null);
        List<Long> ownerIdList = (List<Long>) mapData.getOrDefault(CreateSlaAlertParam.OWNER_ID_LIST, null);

        for (SlaConfigAlertToDto alertToDto : alertToDtoList) {
            Long involedType = alertToDto.getInvoledType();
            Integer receiverType = alertToDto.getReceiverType();
            List<SlaReceiverDto> slaReceiverDtoTempList = new ArrayList<>();
            if (null == involedType) {
                SlaReceiverDto slaReceiverDto = new SlaReceiverDto();
                slaReceiverDto.setReceiverId(alertToDto.getReceiverId());
                slaReceiverDtoTempList.add(slaReceiverDto);
            } else {
                // Long businessKey = (Long) mapData.getOrDefault(CreateSlaAlertParam.BUSINESS_KEY, null);
                // slaReceiverDtoTempList = slaReceiverService.getSlaReceiverDtoListByInvoledType(involedType, businessKey);
                if (involedType.equals(-1L)) { // người chỉ định ở bước tiếp theo
                    if (CommonCollectionUtil.isNotEmpty(assgineeIdList)) {
                        slaReceiverDtoTempList.addAll(assgineeIdList.stream().map(f -> new SlaReceiverDto(f)).collect(Collectors.toList()));
                    }
                } else if (involedType.equals(-2L)) { // người tạo dữ liệu
                    if (CommonCollectionUtil.isNotEmpty(submittedIdList)) {
                        slaReceiverDtoTempList
                                .addAll(submittedIdList.stream().map(f -> new SlaReceiverDto(f)).collect(Collectors.toList()));
                    }
                } else if (involedType.equals(-3L)) { // những người có liên quan đến dữ liệu
                    if (CommonCollectionUtil.isNotEmpty(submittedIdList)) {
                        slaReceiverDtoTempList
                                .addAll(submittedIdList.stream().map(f -> new SlaReceiverDto(f)).collect(Collectors.toList()));
                    }
                    if (CommonCollectionUtil.isNotEmpty(assgineeIdList)) {
                        slaReceiverDtoTempList.addAll(assgineeIdList.stream().map(f -> new SlaReceiverDto(f)).collect(Collectors.toList()));
                    }
                    if (CommonCollectionUtil.isNotEmpty(ownerIdList)) {
                        slaReceiverDtoTempList.addAll(ownerIdList.stream().map(f -> new SlaReceiverDto(f)).collect(Collectors.toList()));
                    }
                } else { // liên quan đến nghiệp vụ
                    if (mapData.containsKey(CommonConstant.BUSINESS_EMAIL)) {
                        try {
                            String email = (String) mapData.get(CommonConstant.BUSINESS_EMAIL);
                            if (StringUtils.isNotBlank(email)) {
                                List<String> emails = new ArrayList<String>(Arrays.asList(email.split(";")));
                                slaReceiverDtoTempList.addAll(emails.stream().map(f -> new SlaReceiverDto(-4L, f))
                                        .collect(Collectors.toList()));
                            }
                        } catch (Exception e) {
                            // TODO: handle exception
                        }
                    }
                }
            }
            if (CommonCollectionUtil.isNotEmpty(slaReceiverDtoTempList)) {
                slaReceiverDtoTempList = slaReceiverDtoTempList.stream().map(f -> {
                    f.setReceiverType(receiverType);
                    return f;
                }).collect(Collectors.toList());
                slaReceiverDtoList.addAll(slaReceiverDtoTempList);
            }
        }
        return slaReceiverDtoList;
    }

    /**
     * <p>
     * Get due date by config and calendar type id.
     * </p>
     *
     * @param slaConfigDetailDto
     *            type {@link SlaConfigDetailDto}
     * @param submitDate
     *            type {@link Date}
     * @param calendarTypeId
     *            type {@link Long}
     * @return {@link Date}
     * @throws DetailException
     *             the detail exception
     * @author TrieuVD
     */
    private Date getDueDateByConfigAndCalendarTypeId(SlaConfigDetailDto slaConfigDetailDto, Date submitDate, Long calendarTypeId)
            throws DetailException {
        Date dueDate = null;

        Long workTime = slaConfigDetailDto.getSlaDueTime();
        Integer timeType = slaConfigDetailDto.getSlaTimeType();
        Long alertTime = slaConfigDetailDto.getAlertTime();
        Integer alertTimeType = slaConfigDetailDto.getAlertUnitTime();

        long totalTime = slaCalculateService.getSlaMinutesByTimeType(submitDate, workTime, timeType, calendarTypeId);
        int slaMinutes;
        Integer alertType = slaConfigDetailDto.getAlertType();
        AlertTypeEnum alertTypeEnum = AlertTypeEnum.resolveByValue(alertType);

        switch (alertTypeEnum) {
        case REMINDER:
            slaMinutes = (int) (totalTime - SlaDateUtils.convertToMinutesByUnitTimeType(alertTime, alertTimeType));
            break;
        case ESCALATE:
            slaMinutes = (int) (totalTime + SlaDateUtils.convertToMinutesByUnitTimeType(alertTime, alertTimeType));
            break;
        default:
            slaMinutes = (int) totalTime;
            break;
        }
        SlaDateResultDto slaDateResultDto = slaCalculateService.calcDueDate(submitDate, slaMinutes, calendarTypeId);
        dueDate = slaDateResultDto.getDueDate();

        return dueDate;
    }

    /*
     * (non-Javadoc)
     * 
     * @see vn.com.unit.sla.service.SlaActionService#caLculateDueDate(vn.com.unit.sla.dto.SlaReqParamDto)
     */
    @Override
    public SlaDateResultDto calculateDueDate(CalculateDueDateParam slaCalcDueDateParam) {
        SlaDateResultDto resultDto = new SlaDateResultDto();
        Long slaConfigId = slaCalcDueDateParam.getSlaConfigId();
        if (null != slaConfigId) {
            Date submitDate = slaCalcDueDateParam.getSubmitDate();
            SlaConfigDto configDto = null;
            try {
                configDto = slaConfigService.getSlaConfigDtoById(slaConfigId);
            } catch (DetailException e) {
                e.printStackTrace();
            }
            if (null != configDto && configDto.isActived()) {
                Long workTime = configDto.getSlaDueTime();
                Integer timeType = configDto.getSlaTimeType();
                Long calendarTypeId = configDto.getCalendarTypeId();
                resultDto = slaCalculateService.calcDueDateByWorkingTime(submitDate, workTime, timeType, calendarTypeId);
                resultDto.setPlanEstimateTime(workTime.intValue());
                resultDto.setPlanCalandarType(timeType);
                resultDto.setSlaConfigId(slaConfigId);
            }
        }
        return resultDto;
    }

    /*
     * (non-Javadoc)
     * 
     * @see vn.com.unit.sla.service.SlaActionService#caLculateElapsedMinutes(vn.com.unit.sla.dto.SlaReqParamDto)
     */
    @Override
    public int calculateElapsedMinutes(CalculateElapsedMinutesParam calcElapsedMinutesParam) {
        int result = 0;
        Long slaConfigId = calcElapsedMinutesParam.getSlaConfigId();
        Date submitDate = calcElapsedMinutesParam.getSubmitDate();
        Date completeDate = calcElapsedMinutesParam.getCompleteDate();
        // Long ownerId = calcElapsedMinutesParam.getOwnerId();

        SlaConfigDto configDto = null;
        try {
            configDto = slaConfigService.getSlaConfigDtoById(slaConfigId);
        } catch (DetailException e) {
            e.printStackTrace();
        }
        if (null != configDto) {
            Long calendarTypeId = configDto.getCalendarTypeId();
            int timeTotal = calcElapsedMinutesParam.getTimeTotal();
            result = slaCalculateService.calcElapsedMinutes(submitDate, completeDate, timeTotal, calendarTypeId).getElapsedMinutes();
        }
        return result;
    }

}
