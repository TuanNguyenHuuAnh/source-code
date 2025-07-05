package vn.com.unit.process.workflow.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.activiti.engine.RuntimeService;
import org.activiti.engine.runtime.Execution;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true, rollbackFor = Exception.class)
public class AssigneeExpressionServiceImpl {
	
	@Autowired
	RuntimeService runtimeService;
	
	public void initAssignee(Execution execution) {
		// 1. Get list assignees
		
		// 2. Set variable into the process
		System.out.println("[AssigneeExpressionServiceImpl] :: Method Expression Service Task executed successfully.");
		
		List<String> users = new ArrayList<String>();
		users.add("user113");
		users.add("user114");
		
		runtimeService.setVariable(execution.getId(), "assigneeList", users);
	}

}
