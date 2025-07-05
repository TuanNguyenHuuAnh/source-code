/*******************************************************************************
 * Class        ：ActivitiIntegrateServiceImpl
 * Created date ：2021/03/15
 * Lasted date  ：2021/03/15
 * Author       ：KhuongTH
 * Change log   ：2021/03/15：01-00 KhuongTH create a new
******************************************************************************/
package vn.com.unit.workflow.activiti.core.impl;

import java.util.Map;

import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.repository.ProcessDefinition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import vn.com.unit.workflow.core.WorkflowIntegrateService;

/**
 * <p>
 * ActivitiIntegrateServiceImpl
 * </p>
 *
 * @version 01-00
 * @since 01-00
 * @author KhuongTH
 */
@Service
public class ActivitiIntegrateServiceImpl implements WorkflowIntegrateService {

    @Autowired
    private RuntimeService runtimeService;
    
    @Autowired
    private RepositoryService repositoryService;

    /**
     * <p>
     * Notify continue workflow.
     * </p>
     *
     * @param executionId
     *            type {@link String}
     * @param messageName
     *            type {@link String}
     * @param variables
     *            type {@link Map<String,Object>}
     * @author KhuongTH
     */
    @Override
    public void notifyContinueWorkflow(String executionId, String messageName, Map<String, Object> variables) {
        runtimeService.messageEventReceived(messageName, executionId, variables);
        ProcessDefinition processDefinition = repositoryService.getProcessDefinition(messageName);
        String deploymentId = processDefinition.getDeploymentId();
    }

}
