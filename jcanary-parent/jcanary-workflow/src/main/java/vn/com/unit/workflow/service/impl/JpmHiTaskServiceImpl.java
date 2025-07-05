/*******************************************************************************
* Class        JpmHiTaskServiceImpl
* Created date 2020/11/25
* Lasted date  2020/11/25
* Author       KhuongTH
* Change log   2020/11/25 01-00 KhuongTH create a new
******************************************************************************/

package vn.com.unit.workflow.service.impl;

import java.util.Date;
import java.util.List;

import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.ObjectMapper;

import vn.com.unit.common.service.JCommonService;
import vn.com.unit.core.security.UserProfileUtils;
import vn.com.unit.workflow.dto.JpmHiTaskDto;
import vn.com.unit.workflow.entity.JpmHiTask;
import vn.com.unit.workflow.repository.JpmHiTaskRepository;
import vn.com.unit.workflow.service.JpmHiTaskService;

/**
 * JpmHiTaskServiceImpl
 * 
 * @version 01-00
 * @since 01-00
 * @author KhuongTH
 */

@Service
@Transactional(readOnly = true, rollbackFor = Exception.class)
public class JpmHiTaskServiceImpl implements JpmHiTaskService {

    @Autowired
    private JpmHiTaskRepository jpmHiTaskRepository;

    // Object mapper
    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private JCommonService commonService;

    @Override
    public JpmHiTaskDto getJpmHiTaskDtoById(Long id) {
        JpmHiTaskDto jpmHiTaskDto = new JpmHiTaskDto();
        if (null != id) {
            JpmHiTask jpmHiTask = jpmHiTaskRepository.findOne(id);
            if (null != jpmHiTask) {
                jpmHiTaskDto = objectMapper.convertValue(jpmHiTask, JpmHiTaskDto.class);
            }
        }
        return jpmHiTaskDto;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public JpmHiTask saveJpmHiTask(JpmHiTask jpmHiTask) {
        Long userId = UserProfileUtils.getUserPrincipal().getAccountId();
        Date sysDate = commonService.getSystemDate();
        Long id = jpmHiTask.getId();
        if (null != id) {
            JpmHiTask oldJpmHiTask = jpmHiTaskRepository.findOne(id);
            if (null != oldJpmHiTask) {
                jpmHiTask.setCreatedId(oldJpmHiTask.getCreatedId());
                jpmHiTask.setCreatedDate(oldJpmHiTask.getCreatedDate());
                jpmHiTask.setUpdatedId(userId);
                jpmHiTask.setUpdatedDate(sysDate);
                jpmHiTaskRepository.update(jpmHiTask);
            } else {
                jpmHiTask.setCreatedId(userId);
                jpmHiTask.setCreatedDate(sysDate);
                jpmHiTask.setUpdatedId(userId);
                jpmHiTask.setUpdatedDate(sysDate);
                jpmHiTaskRepository.create(jpmHiTask);
            }
        } else {
            jpmHiTask.setCreatedId(userId);
            jpmHiTask.setCreatedDate(sysDate);
            jpmHiTask.setUpdatedId(userId);
            jpmHiTask.setUpdatedDate(sysDate);
            jpmHiTaskRepository.create(jpmHiTask);
        }
        return jpmHiTask;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public JpmHiTask saveJpmHiTaskDto(JpmHiTaskDto jpmHiTaskDto) {
        JpmHiTask jpmHiTask = objectMapper.convertValue(jpmHiTaskDto, JpmHiTask.class);
        return this.saveJpmHiTask(jpmHiTask);
    }

    @Override
    public JpmHiTask getJpmHiTaskById(@NotNull Long id) {
        return jpmHiTaskRepository.findOne(id);
    }

    @Override
    public void updateSlaDueDateHiTask(Long jpmTaskId, Date startDate, Date dueDate, int estimateTime, int callandarType, String estimateUnitTime, Long totalTime, Long slaConfigId) {
        jpmHiTaskRepository.updateSlaDueDateHiTask(jpmTaskId, startDate, dueDate, estimateTime, callandarType, estimateUnitTime, totalTime, slaConfigId);
    }

    @Override
    public List<JpmHiTaskDto> getListJpmHiTaskDtoByDocId(Long docId) {
        return jpmHiTaskRepository.getListJpmHiTaskDtoByDocId(docId, UserProfileUtils.getLanguage());
    }

}