/*******************************************************************************
 * Class        ActDiagramController
 * Created date 2019/06/10
 * Lasted date  2019/06/10
 * Author       KhoaNA
 * Change log   2019/06/10 01-00 KhoaNA create a new
 ******************************************************************************/
package vn.com.unit.process.workflow.controller;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.activiti.bpmn.model.BpmnModel;
import org.activiti.engine.RepositoryService;
import org.activiti.image.impl.DefaultProcessDiagramGenerator;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import vn.com.unit.process.admin.service.ActWorkflowService;
import vn.com.unit.process.workflow.dto.AppProcessDto;
import vn.com.unit.process.workflow.service.AppProcessService;
//import vn.com.unit.ep2p.admin.service.ActWorkflowService;
//import vn.com.unit.ep2p.workflow.dto.AppProcessDto;
//import vn.com.unit.ep2p.workflow.service.AppProcessService;
import vn.com.unit.storage.dto.FileDownloadParam;
import vn.com.unit.storage.dto.FileDownloadResult;
import vn.com.unit.storage.service.FileStorageService;
import vn.com.unit.workflow.activiti.utils.BpmnUtil;

/**
 * ActDiagramController
 * 
 * @version 01-00
 * @since 01-00
 * @author KhoaNA
 */
@Controller
@RequestMapping("/workflow/diagram")
public class ActDiagramController {
	
	@Autowired 
	private RepositoryService repositoryService;
	
	@Autowired
	private AppProcessService jpmProcessService;

    @Autowired
    private FileStorageService fileStorageService;
	
	@Autowired 
	private ActWorkflowService actWorkflowService;
	
	private static final String CONTENT_TYPE = "Content-Type";
	private static final String IMAGE_PNG = "image/png";
	
	/** logger */
    @SuppressWarnings("unused")
    private static final Logger logger = LoggerFactory.getLogger(ActDiagramController.class);
	
    @RequestMapping(value = "/{processDefinitionId}", method = RequestMethod.GET)
    public ResponseEntity<byte[]> getDiagramByProcessDefinitionId(
            @PathVariable(value = "processDefinitionId") String processDefinitionId) {

        BpmnModel model = this.repositoryService.getBpmnModel(processDefinitionId);
        //new BpmnAutoLayout(model).execute();
        InputStream in = new DefaultProcessDiagramGenerator().generatePngDiagram(model);
        byte[] bytes = null;
        try {
            bytes = IOUtils.toByteArray(in);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            IOUtils.closeQuietly(in);
        }
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set(CONTENT_TYPE, IMAGE_PNG);
        return new ResponseEntity<byte[]>(bytes, responseHeaders, HttpStatus.OK);
    }
    
    @RequestMapping(value = "/process/{processId}", method = RequestMethod.GET)
    public ResponseEntity<byte[]> getDiagramByProcessId(
            @PathVariable(value = "processId") Long processId) throws Exception {
    	
    	AppProcessDto appProcessDto = jpmProcessService.getAppProcessDtoById(processId);
    	
    	Long repositoryId = appProcessDto.getjRepositoryId();
    	String filePath = appProcessDto.getFilePathBpmn();
    	
    	FileDownloadParam fileDownloadParam = new FileDownloadParam();
        fileDownloadParam.setFilePath(filePath);
        fileDownloadParam.setRepositoryId(repositoryId);

        FileDownloadResult fileDownloadResult = fileStorageService.download(fileDownloadParam);
        byte[] bpmnContent = fileDownloadResult.getFileByteArray();
        
    	InputStream inputStreamBpmn = new ByteArrayInputStream(bpmnContent);
    	
    	BpmnModel bpmnModel = BpmnUtil.convertInputStreamToBpmnModel(inputStreamBpmn);
        InputStream in = new DefaultProcessDiagramGenerator().generatePngDiagram(bpmnModel);
        byte[] bytes = null;
        try {
            bytes = IOUtils.toByteArray(in);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            IOUtils.closeQuietly(inputStreamBpmn);
            IOUtils.closeQuietly(in);
        }
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set(CONTENT_TYPE, IMAGE_PNG);
        return new ResponseEntity<byte[]>(bytes, responseHeaders, HttpStatus.OK);
    }
    
    @RequestMapping(value = "/documents/{processDeployId}", method = RequestMethod.GET)
    public ResponseEntity<byte[]> getActiveDocDiagram(
            @PathVariable Long processDeployId,
            @RequestParam(required=false) String docUuid) throws IOException {

        byte[] bytes = actWorkflowService.getActiveDocumentDiagram(processDeployId, docUuid);

        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set(CONTENT_TYPE, IMAGE_PNG);
        return new ResponseEntity<byte[]>(bytes, responseHeaders, HttpStatus.OK);
    }
}
