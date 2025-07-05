/*******************************************************************************
 * Class        ：TaskSlaAsyncServiceImpl
 * Created date ：2021/03/01
 * Lasted date  ：2021/03/01
 * Author       ：TrieuVD
 * Change log   ：2021/03/01：01-00 TrieuVD create a new
 ******************************************************************************/
package vn.com.unit.ep2p.core.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import vn.com.unit.common.sla.dto.CalculateDueDateParam;
import vn.com.unit.common.sla.dto.CalculateElapsedMinutesParam;
import vn.com.unit.common.sla.dto.CreateSlaAlertParam;
import vn.com.unit.common.utils.CommonCollectionUtil;
import vn.com.unit.dts.exception.DetailException;
import vn.com.unit.sla.dto.SlaAlertCreateResult;
import vn.com.unit.sla.dto.SlaDateResultDto;
import vn.com.unit.sla.enumdef.AlertStatusEnum;
import vn.com.unit.sla.service.SlaActionService;
import vn.com.unit.sla.service.SlaEmailAlertService;
import vn.com.unit.sla.service.SlaNotiAlertService;
import vn.com.unit.workflow.dto.JpmTaskSlaDto;
import vn.com.unit.workflow.entity.JpmTaskEmailAlert;
import vn.com.unit.workflow.entity.JpmTaskNotiAlert;
import vn.com.unit.workflow.service.JpmTaskEmailAlertService;
import vn.com.unit.workflow.service.JpmTaskNotiAlertService;
import vn.com.unit.workflow.service.JpmTaskSlaAsyncService;
import vn.com.unit.workflow.service.JpmTaskSlaService;

/**
 * <p>
 * TaskSlaAsyncServiceImpl
 * </p>
 * .
 *
 * @author TrieuVD
 * @version 01-00
 * @since 01-00
 */
@Async
@Service
@Transactional(readOnly = true, rollbackFor = Exception.class)
public class TaskSlaAsyncServiceImpl implements JpmTaskSlaAsyncService {

    @Autowired
    private SlaActionService slaActionService;

    @Autowired
    private JpmTaskSlaService jpmTaskSlaService;

    @Autowired
    private JpmTaskEmailAlertService jpmTaskEmailAlertService;

    @Autowired
    private JpmTaskNotiAlertService jpmTaskNotiAlertService;

    @Autowired
    private SlaEmailAlertService slaEmailAlertService;

    @Autowired
    private SlaNotiAlertService slaNotiAlertService;

    @Override
    @Transactional
    public void createTaskSla(Long jpmTaskId, CalculateDueDateParam param) {
        SlaDateResultDto slaDateResult = slaActionService.calculateDueDate(param);
        if (null != slaDateResult && null != slaDateResult.getPlanDueDate()) {
            jpmTaskSlaService.createJpmTaskSlaBySlaDateResult(jpmTaskId, slaDateResult);
        }
    }

    @Override
    @Transactional
    public void completeTaskSla(Long jpmTaskId, CalculateElapsedMinutesParam param) {
        JpmTaskSlaDto jpmTaskSlaDto = jpmTaskSlaService.getJpmTaskSlaDtoByJpmTaskId(jpmTaskId);
        if (null != jpmTaskSlaDto) {
            // cal ElapseTime
            Date completeDate = param.getCompleteDate();
            param.setSubmitDate(jpmTaskSlaDto.getPlanStartDate());
            param.setTimeTotal(jpmTaskSlaDto.getPlanTotalTime().intValue());
            param.setSlaConfigId(jpmTaskSlaDto.getSlaConfigId());
            int elapsedMinutes = slaActionService.calculateElapsedMinutes(param);
            jpmTaskSlaService.moveJpmTaskSlaToJpmHiTaskSla(jpmTaskId, completeDate, Long.valueOf(elapsedMinutes));
            // remove slaAlert
            List<JpmTaskEmailAlert> taskEmailAlertList = jpmTaskEmailAlertService.getJpmTaskEmailAlertListByJpmTaskId(jpmTaskId);
            if (CommonCollectionUtil.isNotEmpty(taskEmailAlertList)) {
                for (JpmTaskEmailAlert jpmTaskEmailAlert : taskEmailAlertList) {
                    slaEmailAlertService.moveSlaEmailAlertToSlaEmailAlertHistoryById(jpmTaskEmailAlert.getSlaEmailAlertId(),
                            AlertStatusEnum.SUCCESS.getValue(), "COMPLETE_TASK_SLA");
                    jpmTaskEmailAlertService.delete(jpmTaskEmailAlert);
                }
            }
            List<JpmTaskNotiAlert> taskNotiAlertList = jpmTaskNotiAlertService.getJpmTaskNotiAlertListByJpmTaskId(jpmTaskId);
            if (CommonCollectionUtil.isNotEmpty(taskNotiAlertList)) {
                for (JpmTaskNotiAlert jpmTaskNotiAlert : taskNotiAlertList) {
                    slaNotiAlertService.moveSlaNotiAlertToSlaNotiAlertHistoryById(jpmTaskNotiAlert.getSlaNotiAlertId(),
                            AlertStatusEnum.SUCCESS.getValue(), "COMPLETE_TASK_SLA");
                    jpmTaskNotiAlertService.delete(jpmTaskNotiAlert);
                }
            }
        }
    }

    @Override
    @Transactional
    public void excuteSlaForTask(Long jpmTaskId, CreateSlaAlertParam param) {
        try {
            SlaAlertCreateResult slaAlertCreateResult = slaActionService.createSlaAlertByConfig(param);
            List<Long> slaNotiAlertIdList = slaAlertCreateResult.getSlaNotiAlertIdList();
            jpmTaskNotiAlertService.createJpmTaskNotiAlertByListAlertId(jpmTaskId, slaNotiAlertIdList);
            List<Long> slaEmailAlertIdList = slaAlertCreateResult.getSlaEmailAlertIdList();
            jpmTaskEmailAlertService.createJpmTaskEmailAlertByListAlertId(jpmTaskId, slaEmailAlertIdList);
        } catch (DetailException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void excuteSlaForFinished(Long processDeployId, Long stepDeployId, Long docId, Date sysDate) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void excuteSlaForReject(Long processDeployId, Long stepDeployId, Long docId, Date sysDate) {
        // TODO Auto-generated method stub
        
    }

}
