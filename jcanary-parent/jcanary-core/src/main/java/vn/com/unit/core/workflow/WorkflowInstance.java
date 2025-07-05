/*******************************************************************************
 * Class        ：WorkflowInstance
 * Created date ：2020/11/11
 * Lasted date  ：2020/11/11
 * Author       ：tantm
 * Change log   ：2020/11/11：01-00 tantm create a new
 ******************************************************************************/
package vn.com.unit.core.workflow;

import vn.com.unit.common.dto.ActionDto;
import vn.com.unit.core.dto.AbstractDocumentDto;
import vn.com.unit.core.workflow.dto.DocumentAction;

/**
 * 
 * WorkflowInstance
 * 
 * @version 01-00
 * @since 01-00
 * @author tantm
 */
public interface WorkflowInstance {

    /**
     * Save document and start workflow
     * 
     * @param documentDto
     *            type <T> extends {@link AbstractDocumentDto}
     * @param documentAction
     *            type {@link DocumentAction}
     * @throws Exception
     *             type Exception
     * @author tantm
     */
    <T extends AbstractDocumentDto> void submitDocumentToWorkflow(T documentDto, DocumentAction documentAction) throws Exception;

    <T extends AbstractDocumentDto> T saveDocument(T documentDto, DocumentAction documentAction) throws Exception;

    /**
     * Complete document
     * 
     * @param documentDto
     *            type <T> extends {@link AbstractDocumentDto}
     * @param actionDto
     *            type {@link ActionDto}
     * @param documentAction
     *            type {@link DocumentAction}
     * @throws Exception
     *             type Exception
     * @author KhuongTH
     */
    <T extends AbstractDocumentDto> void actionDocument(T documentDto, ActionDto actionDto, DocumentAction documentAction) throws Exception;

}
