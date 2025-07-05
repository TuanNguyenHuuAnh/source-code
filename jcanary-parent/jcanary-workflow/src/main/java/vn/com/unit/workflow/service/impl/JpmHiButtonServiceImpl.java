/*******************************************************************************
* Class        JpmHiButtonServiceImpl
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

import vn.com.unit.workflow.entity.JpmButton;
import vn.com.unit.workflow.entity.JpmHiButton;
import vn.com.unit.workflow.repository.JpmHiButtonRepository;
import vn.com.unit.workflow.service.JpmHiButtonService;

/**
 * JpmHiButtonServiceImpl
 * 
 * @version 01-00
 * @since 01-00
 * @author KhuongTH
 */

@Service
@Transactional(readOnly = true, rollbackFor = Exception.class)
public class JpmHiButtonServiceImpl implements JpmHiButtonService{

	@Autowired
	private JpmHiButtonRepository jpmHiButtonRepository;

	// Object mapper
	@Autowired
	private ObjectMapper objectMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public JpmHiButton saveJpmHiButton(JpmButton jpmButton) {
        JpmHiButton jpmHiButton = objectMapper.convertValue(jpmButton, JpmHiButton.class);
        jpmHiButton.setId(null);
        jpmHiButton.setButtonId(jpmButton.getId());
        return jpmHiButtonRepository.create(jpmHiButton);
    }

}