/*******************************************************************************
 * Class        ActWorkflowService
 * Created date 2019/06/10
 * Lasted date  2019/06/10
 * Author       KhoaNA
 * Change log   2019/06/10 01-00 KhoaNA create a new
 ******************************************************************************/
package vn.com.unit.process.admin.service;

import java.io.IOException;

import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;

/**
 * ActWorkflowService
 * 
 * @version 01-00
 * @since 01-00
 * @author KhoaNA
 */
public interface ActWorkflowService {

    /**
     * Get process definition by id.
     *
     * @param id
     *          type String
     * @return ProcessDefinition
     * @author KhoaNA
     */ 
    public ProcessDefinition getProcessDefinitionById(String id);
    
    /**
     * Get process definition by businessKey.
     *
     * @param businessKey
     *          type String
     * @return ProcessDefinition
     * @author KhoaNA
     */
    public ProcessInstance findProcessInstanceByBusinessKey(String businessKey);
    
    /**
     * png image of diagram with current activity highlighted.
     *
     * @param processDeployId
     *          type Long
     * @param docUuid
     *          type String
     * @return ProcessDefinition
     * @author KhoaNA
     */
    public byte[] getActiveDocumentDiagram(Long processDeployId, String docUuid) throws IOException;
}
