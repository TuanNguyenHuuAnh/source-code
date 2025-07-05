/*******************************************************************************
* Class        JpmHiStatusServiceImpl
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

import vn.com.unit.workflow.entity.JpmHiStatus;
import vn.com.unit.workflow.entity.JpmStatus;
import vn.com.unit.workflow.repository.JpmHiStatusRepository;
import vn.com.unit.workflow.service.JpmHiStatusService;

/**
 * JpmHiStatusServiceImpl
 * 
 * @version 01-00
 * @since 01-00
 * @author KhuongTH
 */

@Service
@Transactional(readOnly = true, rollbackFor = Exception.class)
public class JpmHiStatusServiceImpl implements JpmHiStatusService{

	@Autowired
	private JpmHiStatusRepository jpmHiStatusRepository;

	// Object mapper
	@Autowired
	private ObjectMapper objectMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public JpmHiStatus saveJpmHiStatus(JpmStatus jpmStatus) {
        JpmHiStatus jpmHiStatus = objectMapper.convertValue(jpmStatus, JpmHiStatus.class);
        jpmHiStatus.setId(null);
        jpmHiStatus.setStatusId(jpmStatus.getId());
        return jpmHiStatusRepository.create(jpmHiStatus);
    }

}