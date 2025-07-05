/*******************************************************************************
 * Class        ：AppWorkflowService
 * Created date ：2021/01/21
 * Lasted date  ：2021/01/21
 * Author       ：KhuongTH
 * Change log   ：2021/01/21：01-00 KhuongTH create a new
 ******************************************************************************/
package vn.com.unit.ep2p.service;

import vn.com.unit.dts.exception.DetailException;
import vn.com.unit.ep2p.dto.CompleteStepReq;
import vn.com.unit.ep2p.dto.StartWorkflowReq;
import vn.com.unit.ep2p.dto.res.AppWorkflowRes;

/**
 * <p>
 * AppWorkflowService
 * </p>
 * 
 * @version 01-00
 * @since 01-00
 * @author KhuongTH
 */
public interface AppWorkflowService {

    /**
     * <p>
     * Start workflow.
     * </p>
     *
     * @param startWorkflowReq
     *            type {@link StartWorkflowReq}
     * @return {@link AppWorkflowRes}
     * @throws DetailException
     *             the detail exception
     * @author KhuongTH
     */
    AppWorkflowRes startWorkflow(StartWorkflowReq startWorkflowReq) throws DetailException;

    /**
     * <p>
     * Complete step.
     * </p>
     *
     * @param completeStepReq
     *            type {@link CompleteStepReq}
     * @return {@link AppWorkflowRes}
     * @throws DetailException
     *             the detail exception
     * @author KhuongTH
     */
    AppWorkflowRes completeStep(CompleteStepReq completeStepReq) throws DetailException;

    /**
     * <p>
     * Delete process instance.
     * </p>
     *
     * @param processInstanceId
     *            type {@link String}
     * @return true, if successful
     * @throws DetailException
     *             the detail exception
     * @author KhuongTH
     */
    boolean deleteProcessInstance(String processInstanceId) throws DetailException;

    /**
     * <p>
     * Gets the workflow diagram by process id.
     * </p>
     *
     * @param processId
     *            type {@link Long}
     * @return the workflow diagram by process id
     * @throws DetailException
     *             the detail exception
     * @author KhuongTH
     */
    String getWorkflowDiagramByProcessId(Long processId) throws DetailException;

    /**
     * <p>
     * Gets the workflow diagram by document id.
     * </p>
     *
     * @param documentId
     *            type {@link Long}
     * @return the workflow diagram by document id
     * @throws DetailException
     *             the detail exception
     * @author KhuongTH
     */
    String getWorkflowDiagramByDocumentId(Long documentId) throws DetailException;
    
    /**
     * <p>
     * Gets the workflow diagram by process deploy id.
     * </p>
     *
     * @param processDeployId
     *            type {@link Long}
     * @return the workflow diagram by process deploy id
     * @throws DetailException
     *             the detail exception
     * @author KhuongTH
     */
    String getWorkflowDiagramByProcessDeployId(Long processDeployId) throws DetailException;
}
