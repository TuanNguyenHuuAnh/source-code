/*******************************************************************************
 * Class        ：WorkflowTaskService
 * Created date ：2020/11/30
 * Lasted date  ：2020/11/30
 * Author       ：tantm
 * Change log   ：2020/11/30：01-00 tantm create a new
 ******************************************************************************/
package vn.com.unit.workflow.core;

import java.util.Map;

import vn.com.unit.workflow.dto.TaskInfoDto;

/**
 * WorkflowTaskService
 * 
 * @version 01-00
 * @since 01-00
 * @author tantm
 */
public interface WorkflowTaskService {

    /**
     * Get TaskInfoDto By ProcessInstanceId
     * 
     * @param processInstanceId
     *            type String
     * @return TaskInfoDto
     * @author tantm
     */
    TaskInfoDto getTaskInfoDtoByProcessInstanceId(String processInstanceId);

    /**
     * Changes the assignee of the given task to the given userId. No check is done whether the user is known by the identity component.
     * 
     * @param taskId
     *            id of the task, cannot be null.
     * @param userId
     *            id of the user to use as assignee.
     * @throws ActivitiObjectNotFoundException
     *             when the task or user doesn't exist.
     */
    void setAssignee(String coreTaskId, String coreUserId);

    /**
     * set variable on a task. If the variable is not already existing, it will be created in the task.
     */
    void setVariableLocal(String taskId, String variableName, Object value);

    /** Add a comment to a task and/or process instance. */
    void addComment(String taskId, String processInstanceId, String message);

    /**
     * set variables on a task. If the variable is not already existing, it will be created in the most outer scope. This means the process
     * instance in case this task is related to an execution.
     */
    void setVariables(String taskId, Map<String, ? extends Object> variables);

    /**
     * Called when the task is successfully executed.
     * 
     * @param taskId
     *            the id of the task to complete, cannot be null.
     * @throws ActivitiObjectNotFoundException
     *             when no task exists with the given id.
     * @throws ActivitiException
     *             when this task is {@link DelegationState#PENDING} delegation.
     */
    void complete(String taskId);

    /**
     * getTaskInfoDtoByCoreTaskId
     * @param coreTaskId
     *          type {@link String}: id of core task
     * @return {@link TaskInfoDto}
     * @author KhuongTH
     */
    TaskInfoDto getTaskInfoDtoByCoreTaskId(String coreTaskId);
    
    /**
     * <p>
     * Get permission type by core task id.
     * </p>
     *
     * @author tantm
     * @param coreTaskId
     *            type {@link String}
     * @return {@link int}
     */
    int getPermissionTypeByCoreTaskId(String coreTaskId);
}
