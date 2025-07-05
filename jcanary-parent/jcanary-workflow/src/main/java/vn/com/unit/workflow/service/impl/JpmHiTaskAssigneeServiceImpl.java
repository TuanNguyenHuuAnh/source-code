/*******************************************************************************
* Class        JpmHiTaskServiceImpl
* Created date 2020/11/25
* Lasted date  2020/11/25
* Author       KhuongTH
* Change log   2020/11/25 01-00 KhuongTH create a new
******************************************************************************/

package vn.com.unit.workflow.service.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import vn.com.unit.common.service.JCommonService;
import vn.com.unit.core.security.UserProfileUtils;
import vn.com.unit.workflow.entity.JpmHiTaskAssignee;
import vn.com.unit.workflow.repository.JpmHiTaskAssigneeRepository;
import vn.com.unit.workflow.service.JpmHiTaskAssigneeService;

/**
 * JpmHiTaskServiceImpl
 * 
 * @version 01-00
 * @since 01-00
 * @author KhuongTH
 */

@Service
@Transactional(readOnly = true, rollbackFor = Exception.class)
public class JpmHiTaskAssigneeServiceImpl implements JpmHiTaskAssigneeService {

    @Autowired
    private JpmHiTaskAssigneeRepository jpmHiTaskAssigneeRepository;

    @Autowired
    private JCommonService commonService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public JpmHiTaskAssignee saveJpmHiTaskAssignee(JpmHiTaskAssignee jpmHiTaskAssignee) {
        Long userId = UserProfileUtils.getUserPrincipal().getAccountId();
        Date sysDate = commonService.getSystemDate();
    	jpmHiTaskAssignee.setCreatedId(userId);
        jpmHiTaskAssignee.setCreatedDate(sysDate);
        jpmHiTaskAssignee.setUpdatedId(userId);
        jpmHiTaskAssignee.setUpdatedDate(sysDate);
        jpmHiTaskAssigneeRepository.create(jpmHiTaskAssignee);
        return jpmHiTaskAssignee;
    }
    
    
    @Override
    @Transactional(rollbackFor = Exception.class)
    public JpmHiTaskAssignee updateJpmHiTaskAssignee(JpmHiTaskAssignee jpmHiTaskAssignee) {
        Long userId = UserProfileUtils.getUserPrincipal().getAccountId();
        Date sysDate = commonService.getSystemDate();
        jpmHiTaskAssignee.setUpdatedId(userId);
        jpmHiTaskAssignee.setUpdatedDate(sysDate);
        jpmHiTaskAssigneeRepository.update(jpmHiTaskAssignee);
        return jpmHiTaskAssignee;
    }
    
	@Transactional
	@Override
	public void deleteByTaskId(Long taskId) {
		jpmHiTaskAssigneeRepository.deleteByTaskId(taskId);
	}


}