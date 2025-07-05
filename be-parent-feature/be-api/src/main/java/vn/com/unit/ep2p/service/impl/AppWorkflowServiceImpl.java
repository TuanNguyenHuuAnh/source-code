/*******************************************************************************
 * Class        ：AppWorkflowServiceImpl
 * Created date ：2021/01/21
 * Lasted date  ：2021/01/21
 * Author       ：KhuongTH
 * Change log   ：2021/01/21：01-00 KhuongTH create a new
 ******************************************************************************/
package vn.com.unit.ep2p.service.impl;

import java.io.IOException;
import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import vn.com.unit.common.constant.CommonConstant;
import vn.com.unit.common.utils.CommonStringUtil;
import vn.com.unit.dts.exception.DetailException;
import vn.com.unit.ep2p.core.efo.dto.EfoDocDto;
import vn.com.unit.ep2p.core.efo.service.EfoDocService;
import vn.com.unit.ep2p.dto.CompleteStepReq;
import vn.com.unit.ep2p.dto.StartWorkflowReq;
import vn.com.unit.ep2p.dto.res.AppWorkflowRes;
import vn.com.unit.ep2p.service.AppWorkflowService;
import vn.com.unit.storage.dto.FileDownloadParam;
import vn.com.unit.storage.dto.FileDownloadResult;
import vn.com.unit.storage.service.FileStorageService;
import vn.com.unit.workflow.core.WorkflowDiagramService;
import vn.com.unit.workflow.core.WorkflowRepositoryService;
import vn.com.unit.workflow.core.WorkflowRuntimeService;
import vn.com.unit.workflow.dto.JpmProcessDeployDto;
import vn.com.unit.workflow.dto.JpmProcessDto;
import vn.com.unit.workflow.enumdef.DocumentState;
import vn.com.unit.workflow.service.JpmProcessDeployService;
import vn.com.unit.workflow.service.JpmProcessService;

/**
 * <p>
 * AppWorkflowServiceImpl
 * </p>
 * 
 * @version 01-00
 * @since 01-00
 * @author KhuongTH
 */
@Service
@Transactional(readOnly = true, rollbackFor = Exception.class)
public class AppWorkflowServiceImpl implements AppWorkflowService {

	private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private JpmProcessDeployService processDeployService;

    @Autowired
    private JpmProcessService processService;

    @Autowired
    private WorkflowRuntimeService workflowRuntimeService;

    @Autowired
    private WorkflowRepositoryService workflowRepositoryService;
    
    @Autowired
    private WorkflowDiagramService workflowDiagramService;

    @Autowired
    private FileStorageService fileStorageService;

    @Autowired
    private EfoDocService efoDocService;
    
    @Override
    public AppWorkflowRes startWorkflow(StartWorkflowReq startWorkflowReq) throws DetailException {
        AppWorkflowRes res = new AppWorkflowRes();
        try {
            Long companyId = startWorkflowReq.getCompanyId();
            String businessCode = startWorkflowReq.getBusinessCode();
            JpmProcessDeployDto processDeploy = processDeployService.getJpmProcessDeployLastedByBusinessCode(companyId, businessCode);
            if (Objects.isNull(processDeploy)) {
                throw new DetailException("không tìm thấy quy trình được triển khai cho nghiệp vụ", true);
            }
            String processInstanceId = workflowRuntimeService.startProcessInstanceByProcessDefinitionIdAndBusinessKey(businessCode,
                    businessCode);
            res.setProcessInstanceId(processInstanceId);
        } catch (DetailException e) {
            throw e;
        } catch (Exception e) {
            throw new DetailException("Lỗi không xác định khi start process", true);
        }

        return res;
    }

    @Override
    public AppWorkflowRes completeStep(CompleteStepReq completeStepReq) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public boolean deleteProcessInstance(String processInstanceId) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public String getWorkflowDiagramByProcessId(Long processId) throws DetailException {
        JpmProcessDto processDto = processService.getJpmProcessDtoByProcessId(processId);

        Long bpmnRepoId = processDto.getBpmnRepoId();
        String filePathBpmn = processDto.getBpmnFilePath();
        String res = CommonConstant.EMPTY;
        try {
            res = this.getWorkflowDiagramByRepositoryIdAndFilePath(bpmnRepoId, filePathBpmn);
        } catch (Exception e) {
            logger.error("Exception ", e);
        }
        return res;
    }

    @Override
    public String getWorkflowDiagramByDocumentId(Long documentId) throws DetailException {
        String res = null;
        EfoDocDto docDto = efoDocService.getEfoDocDtoById(documentId);
        if (Objects.nonNull(docDto)) {
            String docState = docDto.getCommonStatusCode();
            if (DocumentState.DRAFT.toString().equalsIgnoreCase(docState) 
                    || DocumentState.REJECT.toString().equalsIgnoreCase(docState)
                    || DocumentState.COMPLETE.toString().equalsIgnoreCase(docState)) {
                res = this.getWorkflowDiagramByProcessDeployId(docDto.getProcessDeployId());
            } else {
                try {
                    res = workflowRepositoryService.getWorkflowDiagramByBusinessKey(docDto.getDocCode());
                } catch (IOException e) {
                    logger.error("Exception ", e);
                }
            }
        }

        return res;
    }

    @Override
    public String getWorkflowDiagramByProcessDeployId(Long processDeployId) throws DetailException {
        JpmProcessDeployDto processDeployDto = processDeployService.getJpmProcessDeployDtoByProcessDeployId(processDeployId);

        String processDefinitionId = processDeployDto.getProcessDefinitionId();
        String res = CommonConstant.EMPTY;
        try {
            res = workflowRepositoryService.getWorkflowDiagramByProcessDefinitionId(processDefinitionId);

            if (CommonStringUtil.isBlank(res)) {
                Long bpmnRepoId = processDeployDto.getBpmnRepoId();
                String filePathBpmn = processDeployDto.getBpmnFilePath();
                res = this.getWorkflowDiagramByRepositoryIdAndFilePath(bpmnRepoId, filePathBpmn);
            }

        } catch (Exception e) {
            logger.error("Exception ", e);
        }

        return res;
    }

    private String getWorkflowDiagramByRepositoryIdAndFilePath(Long repositoryId, String filePath) throws Exception {
        FileDownloadParam fileDownloadParam = new FileDownloadParam();
        fileDownloadParam.setFilePath(filePath);
        fileDownloadParam.setRepositoryId(repositoryId);

        FileDownloadResult fileDownloadResult = fileStorageService.download(fileDownloadParam);
        byte[] bpmnContent = fileDownloadResult.getFileByteArray();
        
        return workflowDiagramService.getDiagramFromProcessFile(bpmnContent);
    }
}
