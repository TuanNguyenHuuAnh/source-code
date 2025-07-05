package vn.com.unit.process.workflow.service.impl;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true, rollbackFor = Exception.class)
public class AssigneeDelegateServiceImpl implements JavaDelegate  {
	
//	@Autowired
//	RuntimeService runtimeService;
	
	@Override
	public void execute(DelegateExecution execution) {
		System.out.println("[AssigneeDelegateServiceImpl] :: Java Delegate Service Task executed successfully.");
	}
}
