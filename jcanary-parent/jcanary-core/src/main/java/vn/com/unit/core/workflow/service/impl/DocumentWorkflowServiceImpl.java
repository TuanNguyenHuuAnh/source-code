/*******************************************************************************
 * Class        ：DocumentWorkflowServiceImpl
 * Created date ：2020/11/13
 * Lasted date  ：2020/11/13
 * Author       ：tantm
 * Change log   ：2020/11/13：01-00 tantm create a new
 ******************************************************************************/
package vn.com.unit.core.workflow.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import vn.com.unit.common.dto.ActionDto;
import vn.com.unit.core.dto.AbstractDocumentDto;
import vn.com.unit.core.workflow.WorkflowInstance;
import vn.com.unit.core.workflow.WorkflowTypeBuilder;
import vn.com.unit.core.workflow.dto.DocumentAction;
import vn.com.unit.core.workflow.service.DocumentWorkflowService;

/**
 * DocumentWorkflowServiceImpl
 * 
 * @version 01-00
 * @since 01-00
 * @author tantm
 */
@Service
@Transactional(readOnly = true, rollbackFor = Exception.class)
public class DocumentWorkflowServiceImpl implements DocumentWorkflowService {

    @Autowired
    private WorkflowTypeBuilder workflowTypeBuilder;

    @Override
    public <T extends AbstractDocumentDto> void submitDocument(T documentDto, DocumentAction documentAction) throws Exception {
        Integer processType = documentDto.getProcessType();
        WorkflowInstance workflowInstance = workflowTypeBuilder.getWorkflowInstance(processType);
        workflowInstance.submitDocumentToWorkflow(documentDto, documentAction);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public <T extends AbstractDocumentDto> void actionDocument(T documentDto, ActionDto actionDto, DocumentAction documentAction)
            throws Exception {
        Integer processType = documentDto.getProcessType();
        WorkflowInstance workflowInstance = workflowTypeBuilder.getWorkflowInstance(processType);
        workflowInstance.actionDocument(documentDto, actionDto, documentAction);
    }

    @Override
    public <T extends AbstractDocumentDto> T saveDocument(T documentDto, DocumentAction documentAction) throws Exception {
        Integer processType = documentDto.getProcessType();
        WorkflowInstance workflowInstance = workflowTypeBuilder.getWorkflowInstance(processType);
        return workflowInstance.saveDocument(documentDto, documentAction);
    }

}
