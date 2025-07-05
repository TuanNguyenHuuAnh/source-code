/*******************************************************************************
* Class        JpmHiProcessServiceImpl
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

import vn.com.unit.workflow.entity.JpmHiProcess;
import vn.com.unit.workflow.entity.JpmProcess;
import vn.com.unit.workflow.repository.JpmHiProcessRepository;
import vn.com.unit.workflow.service.JpmHiProcessService;

/**
 * JpmHiProcessServiceImpl
 * 
 * @version 01-00
 * @since 01-00
 * @author KhuongTH
 */

@Service
@Transactional(readOnly = true, rollbackFor = Exception.class)
public class JpmHiProcessServiceImpl implements JpmHiProcessService{

	@Autowired
	private JpmHiProcessRepository jpmHiProcessRepository;

	// Object mapper
	@Autowired
	private ObjectMapper objectMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveJpmHiProcess(JpmProcess jpmProcess) {
        JpmHiProcess jpmHiProcess = objectMapper.convertValue(jpmProcess, JpmHiProcess.class);
        jpmHiProcess.setId(null);
        jpmHiProcess.setProcessId(jpmProcess.getId());
        jpmHiProcessRepository.create(jpmHiProcess);
    }

}