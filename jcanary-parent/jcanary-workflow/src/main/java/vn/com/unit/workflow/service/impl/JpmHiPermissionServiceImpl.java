/*******************************************************************************
* Class        JpmHiPermissionServiceImpl
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

import vn.com.unit.workflow.entity.JpmHiPermission;
import vn.com.unit.workflow.entity.JpmPermission;
import vn.com.unit.workflow.repository.JpmHiPermissionRepository;
import vn.com.unit.workflow.service.JpmHiPermissionService;

/**
 * JpmHiPermissionServiceImpl
 * 
 * @version 01-00
 * @since 01-00
 * @author KhuongTH
 */

@Service
@Transactional(readOnly = true, rollbackFor = Exception.class)
public class JpmHiPermissionServiceImpl implements JpmHiPermissionService {

    @Autowired
    private JpmHiPermissionRepository jpmHiPermissionRepository;

    // Object mapper
    @Autowired
    private ObjectMapper objectMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public JpmHiPermission saveJpmHiPermission(JpmPermission jpmPermission) {
        JpmHiPermission jpmHiPermission = objectMapper.convertValue(jpmPermission, JpmHiPermission.class);
        jpmHiPermission.setId(null);
        jpmHiPermission.setPermissionId(jpmPermission.getId());
        return jpmHiPermissionRepository.create(jpmHiPermission);
    }

}