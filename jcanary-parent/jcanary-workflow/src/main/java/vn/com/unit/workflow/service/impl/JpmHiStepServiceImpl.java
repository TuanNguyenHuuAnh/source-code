/*******************************************************************************
* Class        JpmHiStepServiceImpl
* Created date 2020/11/25
* Lasted date  2020/11/25
* Author       KhuongTH
* Change log   2020/11/25 01-00 KhuongTH create a new
******************************************************************************/

package vn.com.unit.workflow.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.ObjectMapper;

import vn.com.unit.workflow.entity.JpmHiStep;
import vn.com.unit.workflow.entity.JpmStep;
import vn.com.unit.workflow.repository.JpmHiStepRepository;
import vn.com.unit.workflow.service.JpmHiStepService;

/**
 * JpmHiStepServiceImpl
 * 
 * @version 01-00
 * @since 01-00
 * @author KhuongTH
 */

@Service
@Transactional(readOnly = true, rollbackFor = Exception.class)
public class JpmHiStepServiceImpl implements JpmHiStepService {

    @Autowired
    private JpmHiStepRepository jpmHiStepRepository;

    // Object mapper
    @Autowired
    private ObjectMapper objectMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public JpmHiStep saveJpmHiStep(JpmStep jpmStep) {
        JpmHiStep jpmHiStep = objectMapper.convertValue(jpmStep, JpmHiStep.class);
        jpmHiStep.setId(null);
        jpmHiStep.setStepId(jpmStep.getId());
        return jpmHiStepRepository.create(jpmHiStep);
    }

}