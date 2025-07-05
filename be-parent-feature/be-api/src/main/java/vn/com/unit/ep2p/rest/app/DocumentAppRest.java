/*******************************************************************************
 * Class        ：DocumentAppRest
 * Created date ：2021/01/19
 * Lasted date  ：2021/01/19
 * Author       ：tantm
 * Change log   ：2021/01/19：01-00 tantm create a new
 ******************************************************************************/
package vn.com.unit.ep2p.rest.app;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import vn.com.unit.dts.web.rest.common.DtsApiResponse;
import vn.com.unit.ep2p.core.constant.AppApiConstant;
import vn.com.unit.ep2p.core.efo.dto.EfoDocDto;
import vn.com.unit.ep2p.core.res.dto.DocumentActionReq;
import vn.com.unit.ep2p.core.res.dto.DocumentAppRes;
import vn.com.unit.ep2p.core.res.dto.DocumentSaveReq;
import vn.com.unit.ep2p.core.res.dto.TaskSlaRes;
import vn.com.unit.ep2p.core.service.DocumentAppService;
import vn.com.unit.ep2p.core.validator.DocumentAppValidator;
import vn.com.unit.ep2p.rest.AbstractRest;
import vn.com.unit.workflow.activiti.core.impl.ActivitiWorkflowParams;
import vn.com.unit.workflow.constant.WorkflowConstant;
import vn.com.unit.workflow.dto.JpmHiTaskDto;

/**
 * 
 * DocumentAppRest
 * 
 * @version 01-00
 * @since 01-00
 * @author tantm
 */
@SuppressWarnings("rawtypes")
@RestController
@RequestMapping(AppApiConstant.API_V1 + AppApiConstant.API_APP)
@Api(tags = { AppApiConstant.API_APP_DOCUMENT_DESCR })
public class DocumentAppRest extends AbstractRest {


    @Autowired
    private DocumentAppService documentAppService;

    @Autowired
    private DocumentAppValidator documentAppValidator;

    @Autowired
    private ActivitiWorkflowParams activitiWorkflowParams;
    
    @PostMapping(AppApiConstant.API_APP_DOCUMENT)
    @ApiOperation("Create document")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "Success"), @ApiResponse(code = 401, message = "Unauthorized"),
            @ApiResponse(code = 402, message = "Forbidden"), @ApiResponse(code = 4024402, message = "Error process add document"),
            @ApiResponse(code = 500, message = "Internal server error"), })
    public DtsApiResponse saveDocument(
            @ApiParam(name = "body", value = "Document information to add new") @RequestBody DocumentSaveReq documentSaveReq,
            BindingResult bindingResult) {
        long start = System.currentTimeMillis();
        try {
            
            EfoDocDto efoOzDocDto = documentAppService.setValueSaveDocument(documentSaveReq);
            // 1.Validate
            documentAppValidator.validate(efoOzDocDto, bindingResult);

            if (bindingResult.hasErrors()) {
                return this.successHandler.handlerSuccess(bindingResult.getAllErrors(), start);
            }

            // 2. Save document
            documentAppService.save(efoOzDocDto);

            return this.successHandler.handlerSuccess(efoOzDocDto, start);
        } catch (Exception ex) {
            return this.errorHandler.handlerException(ex, start);
        }
    }
    
    
    @PutMapping(AppApiConstant.API_APP_DOCUMENT)
    @ApiOperation("Update document")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "Success"), @ApiResponse(code = 401, message = "Unauthorized"),
            @ApiResponse(code = 402, message = "Forbidden"), @ApiResponse(code = 4024403, message = "Error process update info document"),
            @ApiResponse(code = 4024404, message = "Error document not found"),
            @ApiResponse(code = 500, message = "Internal server error"), })
    public DtsApiResponse actionDocument(
            @ApiParam(name = "body", value = "Document information to update") @RequestBody DocumentActionReq documentActionReq
            ,BindingResult bindingResult) {
        long start = System.currentTimeMillis();
        try {
        	EfoDocDto efoOzDocDto = documentAppService.setValueActionDocument(documentActionReq);
        	
            // 1.Validate
            documentAppValidator.validate(efoOzDocDto, bindingResult);

            if (bindingResult.hasErrors()) {
                return this.successHandler.handlerSuccess(bindingResult.getAllErrors(), start);
            }
            
            documentAppService.action(efoOzDocDto);
            String coreTaskId = (String) activitiWorkflowParams.getParam(WorkflowConstant.P_CORE_TASK_ID);
            efoOzDocDto.setCoreTaskId(coreTaskId);
            return this.successHandler.handlerSuccess(efoOzDocDto, start);
        } catch (Exception ex) {
            return this.errorHandler.handlerException(ex, start);
        }
    }

    @GetMapping(AppApiConstant.API_APP_DOCUMENT + "/{documentId}")
    @ApiOperation("detail document")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "Success"), @ApiResponse(code = 401, message = "Unauthorized"),
            @ApiResponse(code = 402, message = "Forbidden"), @ApiResponse(code = 4024403, message = "Error process update info document"),
            @ApiResponse(code = 4024404, message = "Error document not found"),
            @ApiResponse(code = 500, message = "Internal server error"), })
    public DtsApiResponse detailDocument(
            @ApiParam(name = "documentId", value = "Get document information detail on system by id", example = "1") @PathVariable("documentId") Long documentId) {
        long start = System.currentTimeMillis();
        try {
            DocumentAppRes res = documentAppService.detail(documentId);
            return this.successHandler.handlerSuccess(res, start);
        } catch (Exception ex) {
            return this.errorHandler.handlerException(ex, start);
        }
    }

    @SuppressWarnings("unchecked")
    @GetMapping(AppApiConstant.API_APP_DOCUMENT + "/process/{documentId}")
    @ApiOperation("list process history document")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "Success"), @ApiResponse(code = 401, message = "Unauthorized"),
            @ApiResponse(code = 402, message = "Forbidden"), @ApiResponse(code = 4024403, message = "Error process update info document"),
            @ApiResponse(code = 4024404, message = "Error document not found"),
            @ApiResponse(code = 500, message = "Internal server error"), })
    public DtsApiResponse listProcessHistoryDocument(
            @ApiParam(name = "documentId", value = "Get list process history document information on system by id", example = "1") @PathVariable("documentId") Long documentId) {
        long start = System.currentTimeMillis();
        try {
            List<JpmHiTaskDto> res = documentAppService.getListProcessHistoryDocument(documentId);
            return this.successHandler.handlerSuccess(res, start);
        } catch (Exception ex) {
            return this.errorHandler.handlerException(ex, start);
        }
    }
    
    @GetMapping(AppApiConstant.API_APP_DOCUMENT + "/init/{formId}")
    @ApiOperation("Init document by form")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "Success"), 
            @ApiResponse(code = 401, message = "Unauthorized"),
            @ApiResponse(code = 402, message = "Forbidden"), 
            @ApiResponse(code = 4024403, message = "Error process update info document"),
            @ApiResponse(code = 4024404, message = "Error document not found"),
            @ApiResponse(code = 500, message = "Internal server error"), })
    public DtsApiResponse initDocument(
            @ApiParam(name = "formId", value = "Get document information default by form Id", example = "1") @PathVariable("formId") Long formId) {
        long start = System.currentTimeMillis();
        try {
            DocumentAppRes res = documentAppService.initDocument(formId);
            return this.successHandler.handlerSuccess(res, start);
        } catch (Exception ex) {
            return this.errorHandler.handlerException(ex, start);
        }
    }

    @SuppressWarnings("unchecked")
    @GetMapping(AppApiConstant.API_APP_DOCUMENT + "/sla-list/{documentId}")
    @ApiOperation("list sla of document")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "Success"), @ApiResponse(code = 401, message = "Unauthorized"),
            @ApiResponse(code = 402, message = "Forbidden"), @ApiResponse(code = 4024403, message = "Error process update info document"),
            @ApiResponse(code = 4024404, message = "Error document not found"),
            @ApiResponse(code = 500, message = "Internal server error"), })
    public DtsApiResponse listSlaForDocument(
            @ApiParam(name = "documentId", value = "Get list process history document information on system by id", example = "1") @PathVariable("documentId") Long documentId) {
        long start = System.currentTimeMillis();
        try {
            List<TaskSlaRes> res = documentAppService.getListSlaDocument(documentId);
            return this.successHandler.handlerSuccess(res, start);
        } catch (Exception ex) {
            return this.errorHandler.handlerException(ex, start);
        }
    }
}
