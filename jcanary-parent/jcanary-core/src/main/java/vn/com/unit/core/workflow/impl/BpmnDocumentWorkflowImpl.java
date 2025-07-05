/*******************************************************************************
 * Class        ：BpmnDocumentWorkflowImpl
 * Created date ：2020/11/11
 * Lasted date  ：2020/11/11
 * Author       ：tantm
 * Change log   ：2020/11/11：01-00 tantm create a new
 ******************************************************************************/
package vn.com.unit.core.workflow.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import vn.com.unit.common.dto.ActionDto;
import vn.com.unit.common.service.JCommonService;
import vn.com.unit.core.constant.CoreConstant;
import vn.com.unit.core.constant.CoreExceptionCodeConstant;
import vn.com.unit.core.dto.AbstractDocumentDto;
import vn.com.unit.core.service.DocumentActionEventService;
import vn.com.unit.core.service.DocumentService;
import vn.com.unit.core.workflow.WorkflowAction;
import vn.com.unit.core.workflow.WorkflowInstance;
import vn.com.unit.core.workflow.dto.DocumentAction;
import vn.com.unit.dts.exception.DetailException;

/**
 * BpmnDocumentWorkflowImpl.
 *
 * @version 01-00
 * @since 01-00
 * @author tantm
 */
@Service(value = CoreConstant.BEAN_BPMN_DOCUMENT_WORKFLOW)
@Transactional(readOnly = true, rollbackFor = Exception.class)
public class BpmnDocumentWorkflowImpl implements WorkflowInstance {

    /** The workflow action. */
    @Autowired
    private WorkflowAction workflowAction;

    /** The common service. */
    @Autowired
    private JCommonService commonService;

    /*
     * (non-Javadoc)
     * 
     * @see vn.com.unit.core.workflow.WorkflowInstance#submitDocumentToWorkflow(vn.com.unit.core.dto.AbstractDocumentDto,
     * vn.com.unit.core.workflow.dto.DocumentAction)
     */
    @Override
    public <T extends AbstractDocumentDto> void submitDocumentToWorkflow(T documentDto, DocumentAction documentAction) throws Exception {
        // 1. Generate id
        DocumentService documentService = documentAction.getDocumentService();
        if (documentService == null) {
            throw new DetailException(CoreExceptionCodeConstant.E301750_CORE_DOCUMENT_SERVICE_NOT_IMPLEMENT, true);
        }
        Long docId = (Long) documentService.generateId();
        documentDto.setDocId(docId);
        documentDto.setDocCode(commonService.generateCodeFromId(docId));
        // 2. Submit workflow
        workflowAction.startWorkflowForDocument(documentDto);

        DocumentActionEventService documentActionEventService = documentAction.getDocumentActionEventService();
        // 3. Process data before save
        if (documentActionEventService != null) {
            documentActionEventService.processDataBeforeSave(documentDto);
        }

        // 4. Save document
        documentService.save(documentDto);

        // 5. Process data after save
        if (documentActionEventService != null) {
            documentActionEventService.processDataAfterSave(documentDto, documentAction);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public <T extends AbstractDocumentDto> void actionDocument(T documentDto, ActionDto actionDto, DocumentAction documentAction)
            throws Exception {

        // 2. action workflow
        if (actionDto.isSubmit()) {

            workflowAction.startWorkflowForDocument(documentDto);
        } else {

            workflowAction.completeTask(documentDto, actionDto);
        }

        // 3. Process data before save
        // documentActionEventService.processDataBeforeSave(documentDto);

        // 4. Save document
        DocumentService documentService = documentAction.getDocumentService();
        if (documentService == null) {
            throw new DetailException(CoreExceptionCodeConstant.E301750_CORE_DOCUMENT_SERVICE_NOT_IMPLEMENT, true);
        }
        if (actionDto.isSave()) {
            documentService.save(documentDto);
        } else {
            documentService.updateVersion(documentDto, false);
        }

        // 5. Process data after save
        // documentActionEventService.processDataAfterSave(documentDto);
    }

    @Override
    public <T extends AbstractDocumentDto> T saveDocument(T documentDto, DocumentAction documentAction) throws Exception {
        // 1. Generate id
        DocumentService documentService = documentAction.getDocumentService();
        if (documentService == null) {
            throw new DetailException(CoreExceptionCodeConstant.E301750_CORE_DOCUMENT_SERVICE_NOT_IMPLEMENT, true);
        }
        // if (null == documentDto.getId()) {
        // Long docId = (Long) documentService.generateId();
        // documentDto.setDocId(docId);
        // documentDto.setDocCode(commonService.generateCodeFromId(docId));
        // }

        DocumentActionEventService documentActionEventService = documentAction.getDocumentActionEventService();
        // 3. Process data before save
        if (documentActionEventService != null) {
            // Save and upload mainFile
            documentActionEventService.processDataBeforeSave(documentDto);
        }

        // 4. Save document
        documentService.save(documentDto);

        // 5. Process data after save
        if (documentActionEventService != null) {
            // TODO Save history track full ?
            documentActionEventService.processDataAfterSave(documentDto, documentAction);
        }
        return documentDto;
    }

}
