/*******************************************************************************
* Class        JpmButtonForStepServiceImpl
* Created date 2020/11/25
* Lasted date  2020/11/25
* Author       KhuongTH
* Change log   2020/11/25 01-00 KhuongTH create a new
******************************************************************************/

package vn.com.unit.workflow.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.ObjectMapper;

import vn.com.unit.common.utils.CommonDateUtil;
import vn.com.unit.core.security.UserProfileUtils;
import vn.com.unit.workflow.dto.JpmButtonForStepDto;
import vn.com.unit.workflow.entity.JpmButtonForStep;
import vn.com.unit.workflow.repository.JpmButtonForStepRepository;
import vn.com.unit.workflow.service.JpmButtonForStepService;

/**
 * JpmButtonForStepServiceImpl
 * 
 * @version 01-00
 * @since 01-00
 * @author KhuongTH
 */

@Service
@Transactional(readOnly = true, rollbackFor = Exception.class)
public class JpmButtonForStepServiceImpl implements JpmButtonForStepService {

    @Autowired
    private JpmButtonForStepRepository jpmButtonForStepRepository;

    // Object mapper
    @Autowired
    private ObjectMapper objectMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean deleteById(Long id) {
        boolean res = false;
        if (null != id) {
            try {
                jpmButtonForStepRepository.delete(id);
                res = true;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return res;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public JpmButtonForStep saveJpmButtonForStep(JpmButtonForStep jpmButtonForStep) {
        Long userId = UserProfileUtils.getAccountId(); // TODO Add user
        Date sysDate = CommonDateUtil.getSystemDateTime();
        // Long id = jpmButtonForStep.getId();
        // if (null != id) {
        // JpmButtonForStep oldJpmButtonForStep = jpmButtonForStepRepository.findOne(id);
        // if (null != oldJpmButtonForStep) {
        // jpmButtonForStep.setCreatedId(oldJpmButtonForStep.getCreatedId());
        // jpmButtonForStep.setCreatedDate(oldJpmButtonForStep.getCreatedDate());
        // jpmButtonForStep.setUpdatedId(userId);
        // jpmButtonForStep.setUpdatedDate(sysDate);
        // jpmButtonForStepRepository.update(jpmButtonForStep);
        // }
        // } else {
        // jpmButtonForStep.setCreatedId(userId);
        // jpmButtonForStep.setCreatedDate(sysDate);
        // jpmButtonForStepRepository.create(jpmButtonForStep);
        // }
        jpmButtonForStep.setCreatedId(userId);
        jpmButtonForStep.setCreatedDate(sysDate);
        jpmButtonForStep.setUpdatedId(userId);
        jpmButtonForStep.setUpdatedDate(sysDate);
        jpmButtonForStepRepository.create(jpmButtonForStep);
        return jpmButtonForStep;
    }

    @Override
    public JpmButtonForStep saveJpmButtonForStepDto(JpmButtonForStepDto jpmButtonForStepDto) {
        JpmButtonForStep jpmButtonForStep = objectMapper.convertValue(jpmButtonForStepDto, JpmButtonForStep.class);
        return this.saveJpmButtonForStep(jpmButtonForStep);
    }

    @Override
    public List<JpmButtonForStepDto> getButtonForStepDtosByProcessId(Long processId) {
        return jpmButtonForStepRepository.getButtonForStepDtosByProcessId(processId);
    }
    
    @Override
    public List<JpmButtonForStepDto> getButtonForStepDtosByStepId(Long stepId) {
        return jpmButtonForStepRepository.getButtonForStepDtosByStepId(stepId);
    }

    @Override
    public void saveButtonForStepDtosByProcessId(List<JpmButtonForStepDto> buttonForStepDtos, Long stepId) {
        // delete old
        this.deleteButtonForStepByStepId(stepId);
        
        // create new
        for (JpmButtonForStepDto buttonForStepDto : buttonForStepDtos) {
            this.saveJpmButtonForStepDto(buttonForStepDto);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteButtonForStepByStepId(Long stepId) {
        jpmButtonForStepRepository.deleteButtonForStepByStepId(stepId);
    }
}