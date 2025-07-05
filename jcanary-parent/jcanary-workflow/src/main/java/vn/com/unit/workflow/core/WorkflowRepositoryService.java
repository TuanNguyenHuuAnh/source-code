/*******************************************************************************
 * Class        ：WorkflowRepositoryService
 * Created date ：2020/11/30
 * Lasted date  ：2020/11/30
 * Author       ：tantm
 * Change log   ：2020/11/30：01-00 tantm create a new
 ******************************************************************************/
package vn.com.unit.workflow.core;

import java.io.IOException;

/**
 * WorkflowRepositoryService
 * 
 * @version 01-00
 * @since 01-00
 * @author tantm
 */
public interface WorkflowRepositoryService {

    /**
     * Get ProcessDefinitionKey by id
     * 
     * @param processDefinitionId
     *            type {@link String}
     * @return ProcessDefinitionKey
     * @author tantm
     */
    String getProcessDefinitionKeyById(String processDefinitionId);

    /**
     * <p>
     * Deploy bpmn.
     * </p>
     *
     * @param contentFile
     *            type {@link byte[]}
     * @param deploymentName
     *            type {@link String}
     * @param categoryName
     *            type {@link String}
     * @param tenantId
     *            type {@link String}
     * @param filePathBpmn
     *            type {@link String}
     * @return {@link String}: deploymentId
     * @author KhuongTH
     */
    String deployBpmn(byte[] contentFile, String deploymentName, String categoryName, String tenantId, String filePathBpmn);

    /**
     * <p>
     * Gets the workflow diagram by process definition id.
     * </p>
     *
     * @param processDefinitionId
     *            type {@link String}
     * @return the workflow diagram by process definition id; encode by base64
     * @author KhuongTH
     * @throws IOException
     */
    String getWorkflowDiagramByProcessDefinitionId(String processDefinitionId) throws IOException;

    /**
     * <p>
     * Gets the workflow diagram by business key.
     * </p>
     *
     * @param businessKey
     *            type {@link String}
     * @return the workflow diagram by business key; encode by base64
     * @throws IOException
     *             Signals that an I/O exception has occurred.
     * @author KhuongTH
     */
    String getWorkflowDiagramByBusinessKey(String businessKey) throws IOException;

    /**
     * <p>
     * Deploy dmn.
     * </p>
     *
     * @param contentFile
     *            type {@link byte[]}
     * @param categoryName
     *            type {@link String}
     * @param tenantId
     *            type {@link String}
     * @param filePathBpmn
     *            type {@link String}
     * @param parentDeploymentId
     *            type {@link String}
     * @return {@link String}: deploymentId
     * @author KhuongTH
     */
    String deployDmn(byte[] contentFile, String categoryName, String tenantId, String filePathBpmn,
            String parentDeploymentId);

    /**
     * <p>
     * Gets the process definition id by deployment id.
     * </p>
     *
     * @param deploymentId
     *            type {@link String}
     * @return the process definition id by deployment id
     * @author KhuongTH
     */
    String getProcessDefinitionIdByDeploymentId(String deploymentId);
}
