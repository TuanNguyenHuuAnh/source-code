/*******************************************************************************
 * Class        ：WorkflowTaskServiceImpl
 * Created date ：2020/11/30
 * Lasted date  ：2020/11/30
 * Author       ：tantm
 * Change log   ：2020/11/30：01-00 tantm create a new
 ******************************************************************************/
package vn.com.unit.workflow.activiti.core.impl;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.activiti.engine.TaskService;
import org.activiti.engine.task.IdentityLink;
import org.activiti.engine.task.IdentityLinkType;
import org.activiti.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import vn.com.unit.dts.utils.DtsCollectionUtil;
import vn.com.unit.workflow.core.WorkflowTaskService;
import vn.com.unit.workflow.dto.TaskInfoDto;

/**
 * WorkflowTaskServiceImpl
 * 
 * @version 01-00
 * @since 01-00
 * @author tantm
 */
@Service
@Transactional(readOnly = true, rollbackFor = Exception.class)
public class ActivitiTaskServiceImpl implements WorkflowTaskService {

    @Autowired
    private TaskService taskService;

    @Override
    public TaskInfoDto getTaskInfoDtoByProcessInstanceId(String processInstanceId) {
        Task task = taskService.createTaskQuery().processInstanceId(processInstanceId).singleResult();

        return copyTaskToDto(task);
    }

    @Override
    public void setAssignee(String taskId, String userId) {
        taskService.setAssignee(taskId, userId);
    }

    @Override
    public void setVariableLocal(String taskId, String variableName, Object value) {
        taskService.setVariableLocal(taskId, variableName, value);
    }

    @Override
    public void addComment(String taskId, String processInstanceId, String message) {
        taskService.addComment(taskId, processInstanceId, message);
    }

    @Override
    public void setVariables(String taskId, Map<String, ? extends Object> variables) {
        taskService.setVariables(taskId, variables);
    }

    @Override
    public void complete(String taskId) {
        taskService.complete(taskId);

    }

    @Override
    public TaskInfoDto getTaskInfoDtoByCoreTaskId(String coreTaskId) {
        Task task = taskService.createTaskQuery().taskId(coreTaskId).singleResult();

        return copyTaskToDto(task);
    }

    private TaskInfoDto copyTaskToDto(Task task) {

        if (task == null) {
            return new TaskInfoDto();
        }
        TaskInfoDto result = new TaskInfoDto();
        result.setCoreTaskId(task.getId());
        result.setExecutionId(task.getExecutionId());
        result.setProcessInstanceId(task.getProcessInstanceId());
        result.setTaskDefinitionKey(task.getTaskDefinitionKey());
        
        List<IdentityLink> identities = taskService.getIdentityLinksForTask(task.getId());
        List<String> groupIds = identities.stream().filter(p -> p.getType().equals(IdentityLinkType.CANDIDATE)).map(i -> i.getGroupId()).collect(Collectors.toList());
        if(DtsCollectionUtil.isNotEmpty(groupIds)) {
            result.setGroupIds(groupIds);
        }
        return result;
    }

    @Override
    public int getPermissionTypeByCoreTaskId(String coreTaskId) {
        List<IdentityLink> identities = taskService.getIdentityLinksForTask(coreTaskId);
        List<String> groupIds = identities.stream().filter(p -> p.getType().equals(IdentityLinkType.CANDIDATE)).map(i -> i.getGroupId())
                .collect(Collectors.toList());
        if (DtsCollectionUtil.isNotEmpty(groupIds)) {
            return 1;
        }
        return 0;
    }
}
