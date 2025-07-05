/*******************************************************************************
 * Class        ：WorkflowRuntimeServiceImpl
 * Created date ：2020/11/30
 * Lasted date  ：2020/11/30
 * Author       ：tantm
 * Change log   ：2020/11/30：01-00 tantm create a new
 ******************************************************************************/
package vn.com.unit.workflow.activiti.core.impl;

import java.util.Map;

import org.activiti.engine.RuntimeService;
import org.activiti.engine.runtime.ProcessInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import vn.com.unit.workflow.core.WorkflowRuntimeService;

/**
 * WorkflowRuntimeServiceImpl
 * 
 * @version 01-00
 * @since 01-00
 * @author tantm
 */
@Service
@Transactional(readOnly = true, rollbackFor = Exception.class)
public class ActivitiRuntimeServiceImpl implements WorkflowRuntimeService {

    @Autowired
    private RuntimeService runtimeService;

    @Override
    public String getProcessInstanceIdByBusinessKey(String businessKey) {
        ProcessInstance processInstance = runtimeService.createProcessInstanceQuery().processInstanceBusinessKey(businessKey)
                .singleResult();
        if (processInstance == null) {
            return null;
        }
        return processInstance.getProcessInstanceId();
    }

    public String startProcessInstanceByProcessDefKeyAndBusinessKey(String processDefinitionKey, String businessKey) {
        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey(processDefinitionKey, businessKey);
        return processInstance.getId();
    }

    @Override
    public String startProcessInstanceByProcessDefinitionIdAndBusinessKey(String processDefinitionId, String businessKey) {
        ProcessInstance processInstance = runtimeService.startProcessInstanceById(processDefinitionId, businessKey);
        return processInstance.getId();
    }

    @Override
    public void setVariable(String executionId, String variableName, Object value) {
        runtimeService.setVariable(executionId, variableName, value);

    }

    @Override
    public void setVariables(String executionId, Map<String, ? extends Object> variables) {
        runtimeService.setVariables(executionId, variables);
    }

    @Override
    public void deleteProcessInstance(String processInstanceId, String deleteReason) {
        runtimeService.deleteProcessInstance(processInstanceId, deleteReason);
    }

}
