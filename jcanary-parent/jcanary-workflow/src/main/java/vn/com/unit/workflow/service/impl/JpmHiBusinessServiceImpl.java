/*******************************************************************************
* Class        JpmHiBusinessServiceImpl
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

import vn.com.unit.workflow.entity.JpmBusiness;
import vn.com.unit.workflow.entity.JpmHiBusiness;
import vn.com.unit.workflow.repository.JpmHiBusinessRepository;
import vn.com.unit.workflow.service.JpmHiBusinessService;

/**
 * JpmHiBusinessServiceImpl
 * 
 * @version 01-00
 * @since 01-00
 * @author KhuongTH
 */

@Service
@Transactional(readOnly = true, rollbackFor = Exception.class)
public class JpmHiBusinessServiceImpl implements JpmHiBusinessService {

    @Autowired
    private JpmHiBusinessRepository jpmHiBusinessRepository;

    // Object mapper
    @Autowired
    private ObjectMapper objectMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public JpmHiBusiness saveJpmHiBusiness(JpmBusiness jpmBusiness) {
        JpmHiBusiness jpmHiBusiness = objectMapper.convertValue(jpmBusiness, JpmHiBusiness.class);
        jpmHiBusiness.setBusinessId(jpmBusiness.getId());
        jpmHiBusiness.setCreatedId(jpmBusiness.getUpdatedId());
        jpmHiBusiness.setCreatedDate(jpmBusiness.getUpdatedDate());
        jpmHiBusiness.setId(null);
        return jpmHiBusinessRepository.create(jpmHiBusiness);
    }

}