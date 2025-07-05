/*******************************************************************************
 * Class        ActWorkflowServiceImpl
 * Created date 2019/06/21
 * Lasted date  2019/06/21
 * Author       KhoaNA
 * Change log   2016/07/1901-00 KhoaNA create a new
 ******************************************************************************/
package vn.com.unit.process.admin.service.impl;

import java.io.IOException;
import java.io.InputStream;

import org.activiti.bpmn.model.BpmnModel;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.impl.RepositoryServiceImpl;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntity;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.image.impl.DefaultProcessDiagramGenerator;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import vn.com.unit.ep2p.admin.constant.Utils;
import vn.com.unit.ep2p.admin.service.BeAdminFileService;
import vn.com.unit.process.admin.service.ActWorkflowService;
import vn.com.unit.process.workflow.dto.AppProcessDeployDto;
import vn.com.unit.process.workflow.service.AppProcessDeployService;

/**
 * ActWorkflowServiceImpl
 * 
 * @version 01-00
 * @since 01-00
 * @author KhoaNA
 */
@Service
@Transactional(readOnly = true, rollbackFor = Exception.class)
public class ActWorkflowServiceImpl implements ActWorkflowService {
	
	@Autowired
	private RepositoryService repositoryService;
	
	@Autowired 
	private RuntimeService runtimeService;
	
	@Autowired
	private AppProcessDeployService appProcessDeployService;
	
	@Autowired
	private BeAdminFileService fileService;
	
	private static final String PNG = "png";
	
//	private static final Logger logger = LoggerFactory.getLogger(ActWorkflowServiceImpl.class);

	@Override
	public ProcessDefinition getProcessDefinitionById(String id) {
		ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery().
    			processDefinitionId(id).singleResult();
        return processDefinition;
	}

	@Override
	public ProcessInstance findProcessInstanceByBusinessKey(String businessKey) {
		return runtimeService.createProcessInstanceQuery().processInstanceBusinessKey(businessKey).singleResult();
	}
	
    @Override
    public byte[] getActiveDocumentDiagram(Long processDeployId, String docUuid) throws IOException {
    	InputStream in = null;
    	
        //http://forums.activiti.org/content/process-diagram-highlighting-current-process
    	ProcessInstance pi = runtimeService.createProcessInstanceQuery()
	   			.processInstanceBusinessKey(docUuid).singleResult();
    	
    	if( pi != null ) {
    		RepositoryServiceImpl impl = (RepositoryServiceImpl) repositoryService;
    		ProcessDefinitionEntity pde = (ProcessDefinitionEntity) impl.getDeployedProcessDefinition(pi.getProcessDefinitionId());
    		BpmnModel bpmnModel = repositoryService.getBpmnModel(pde.getId());
    		
    		//new BpmnAutoLayout(bpmnModel).execute();
    		in = new DefaultProcessDiagramGenerator().generateDiagram(bpmnModel, PNG, runtimeService.getActiveActivityIds(pi.getProcessInstanceId()));
    	} else {
    		AppProcessDeployDto jpmProcessDto = appProcessDeployService.getJpmProcessDeployDtoById(processDeployId);
    		
        	Long repositoryId = jpmProcessDto.getjRepositoryId();
        	String filePath = jpmProcessDto.getFilePathBpmn();
        	InputStream inputStreamBpmn = fileService.getInputStreamByRepositoryIdAndFilePath(repositoryId, filePath);
        	
        	BpmnModel bpmnModel = Utils.convertInputStreamToBpmnModel(inputStreamBpmn);
            in = new DefaultProcessDiagramGenerator().generatePngDiagram(bpmnModel);
    	}

    	byte[] bytes = null;
    	try {
            bytes = IOUtils.toByteArray(in);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            IOUtils.closeQuietly(in);
        }
    	
    	return bytes;
    }
}
