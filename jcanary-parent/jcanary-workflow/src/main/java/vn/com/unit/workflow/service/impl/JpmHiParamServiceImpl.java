/*******************************************************************************
* Class        JpmHiParamServiceImpl
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

import vn.com.unit.workflow.entity.JpmHiParam;
import vn.com.unit.workflow.entity.JpmParam;
import vn.com.unit.workflow.repository.JpmHiParamRepository;
import vn.com.unit.workflow.service.JpmHiParamService;

/**
 * JpmHiParamServiceImpl
 * 
 * @version 01-00
 * @since 01-00
 * @author KhuongTH
 */

@Service
@Transactional(readOnly = true, rollbackFor = Exception.class)
public class JpmHiParamServiceImpl implements JpmHiParamService{

	@Autowired
	private JpmHiParamRepository jpmHiParamRepository;

	// Object mapper
	@Autowired
	private ObjectMapper objectMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public JpmHiParam saveJpmHiParam(JpmParam jpmParam) {
        JpmHiParam jpmHiParam = objectMapper.convertValue(jpmParam, JpmHiParam.class);
        jpmHiParam.setId(null);
        jpmHiParam.setParamId(jpmParam.getId());
        return jpmHiParamRepository.create(jpmHiParam);
    }
}