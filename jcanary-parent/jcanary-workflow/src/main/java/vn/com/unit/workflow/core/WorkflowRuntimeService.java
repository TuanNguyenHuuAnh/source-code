/*******************************************************************************
 * Class        ：WorkflowRuntimeService
 * Created date ：2020/11/30
 * Lasted date  ：2020/11/30
 * Author       ：tantm
 * Change log   ：2020/11/30：01-00 tantm create a new
 ******************************************************************************/
package vn.com.unit.workflow.core;

import java.util.Map;

/**
 * WorkflowRuntimeService
 * 
 * @version 01-00
 * @since 01-00
 * @author tantm
 */
public interface WorkflowRuntimeService {

    /**
     * Get ProcessInstanceId by businessKey
     * 
     * @param businessKey
     *            type {@link String}
     * @return ProcessInstanceId
     * @author tantm
     */
    String getProcessInstanceIdByBusinessKey(String businessKey);

    /**
     * Starts a new process instance in the latest version of the process definition with the given key.
     * 
     * @param processDefinitionKey
     *            key of process definition, cannot be null.
     * @param businessKey
     *            a key that uniquely identifies the process instance in the context or the given process definition.
     * 
     * @return ProcessInstanceId
     * @author tantm
     */
    String startProcessInstanceByProcessDefKeyAndBusinessKey(String processDefinitionKey, String businessKey);

    /**
     * Starts a new process instance in the exactly specified version of the process definition with the given id.
     * 
     * A business key can be provided to associate the process instance with a certain identifier that has a clear business meaning. For
     * example in an order process, the business key could be an order id. This business key can then be used to easily look up that process
     * instance , see {@link ProcessInstanceQuery#processInstanceBusinessKey(String)}. Providing such a business key is definitely a best
     * practice.
     * 
     * @param processDefinitionId
     *            the id of the process definition, cannot be null.
     * @param businessKey
     *            a key that uniquely identifies the process instance in the context or the given process definition.
     * 
     * @return ProcessInstanceId
     * @author tantm
     * 
     * @throws ActivitiObjectNotFoundException
     *             when no process definition is deployed with the given key.
     */
    String startProcessInstanceByProcessDefinitionIdAndBusinessKey(String processDefinitionId, String businessKey);
    
    /**
     * Update or create a variable for an execution.
     * 
     * <p>
     * The variable is set according to the algorithm as documented for {@link VariableScope#setVariable(String, Object)}.
     * 
     * @see VariableScope#setVariable(String, Object) {@link VariableScope#setVariable(String, Object)}
     * 
     * @param executionId
     *            id of execution to set variable in, cannot be null.
     * @param variableName
     *            name of variable to set, cannot be null.
     * @param value
     *            value to set. When null is passed, the variable is not removed, only it's value will be set to null.
     * @throws ActivitiObjectNotFoundException
     *             when no execution is found for the given executionId.
     */
    void setVariable(String executionId, String variableName, Object value);

    /**
     * Update or create given variables for an execution (including parent scopes).
     * <p>
     * Variables are set according to the algorithm as documented for {@link VariableScope#setVariables(Map)}, applied separately to each variable.
     * 
     * @see VariableScope#setVariables(Map) {@link VariableScope#setVariables(Map)}
     * 
     * @param executionId
     *          id of the execution, cannot be null.
     * @param variables
     *          map containing name (key) and value of variables, can be null.
     * @throws ActivitiObjectNotFoundException
     *           when no execution is found for the given executionId.
     */
    void setVariables(String executionId, Map<String, ? extends Object> variables);
    
    /**
     * Delete an existing runtime process instance.
     * 
     * @param processInstanceId
     *          id of process instance to delete, cannot be null.
     * @param deleteReason
     *          reason for deleting, can be null.
     * @throws ActivitiObjectNotFoundException
     *           when no process instance is found with the given id.
     */
    void deleteProcessInstance(String processInstanceId, String deleteReason);
}
