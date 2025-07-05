/*******************************************************************************
 * Class        ：WorkflowDiagramRest
 * Created date ：2021/01/21
 * Lasted date  ：2021/01/21
 * Author       ：KhuongTH
 * Change log   ：2021/01/21：01-00 KhuongTH create a new
 ******************************************************************************/
package vn.com.unit.ep2p.rest.app;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import vn.com.unit.dts.web.rest.common.DtsApiResponse;
import vn.com.unit.ep2p.core.constant.AppApiConstant;
import vn.com.unit.ep2p.rest.AbstractRest;
import vn.com.unit.ep2p.service.AppWorkflowService;

/**
 * <p>
 * WorkflowDiagramRest
 * </p>
 * .
 *
 * @version 01-00
 * @since 01-00
 * @author KhuongTH
 */
@RestController
@RequestMapping(AppApiConstant.API_V1 + AppApiConstant.API_APP + AppApiConstant.API_APP_WORKFLOW_DIAGRAM)
@Api(tags = { AppApiConstant.API_APP_WORKFLOW_DIAGRAM_DESCR })
public class WorkflowDiagramRest extends AbstractRest {

    @Autowired
    private AppWorkflowService appWorkflowService;

    @GetMapping(AppApiConstant.API_APP_DOCUMENT)
    @ApiOperation("Get workflow diagram by document")
    @ApiResponses(value = { 
            @ApiResponse(code = 200, message = "Success"), 
            @ApiResponse(code = 401, message = "Unauthorized"),
            @ApiResponse(code = 402, message = "Forbidden"), 
            @ApiResponse(code = 500, message = "Internal server error")})
    public DtsApiResponse getDocumentWorkflowDiagram(
            @ApiParam(name = "documentId", value = "id of document") @RequestParam Long documentId) {
        long start = System.currentTimeMillis();
        try {
            String resObj = appWorkflowService.getWorkflowDiagramByDocumentId(documentId);
            return this.successHandler.handlerSuccess(resObj, start);
        } catch (Exception ex) {
            return this.errorHandler.handlerException(ex, start);
        }
    }
    
    @GetMapping(AppApiConstant.API_ADMIN_PROCESS)
    @ApiOperation("Get workflow diagram by process")
    @ApiResponses(value = { 
            @ApiResponse(code = 200, message = "Success"), 
            @ApiResponse(code = 401, message = "Unauthorized"),
            @ApiResponse(code = 402, message = "Forbidden"), 
            @ApiResponse(code = 500, message = "Internal server error"), })
    public DtsApiResponse getProcessWorkflowDiagram(
            @ApiParam(name = "processId", value = "Id of process") @RequestParam Long processId) {
        long start = System.currentTimeMillis();
        try {
            String resObj = appWorkflowService.getWorkflowDiagramByProcessId(processId);
            return this.successHandler.handlerSuccess(resObj, start);
        } catch (Exception ex) {
            return this.errorHandler.handlerException(ex, start);
        }
    }

    @GetMapping(AppApiConstant.API_ADMIN_PROCESS_DEPLOY)
    @ApiOperation("Get workflow diagram by process deploy")
    @ApiResponses(value = { 
            @ApiResponse(code = 200, message = "Success"), 
            @ApiResponse(code = 401, message = "Unauthorized"),
            @ApiResponse(code = 402, message = "Forbidden"), 
            @ApiResponse(code = 500, message = "Internal server error"), })
    public DtsApiResponse getProcessDeployWorkflowDiagram(
            @ApiParam(name = "processDeployId", value = "Id of process deploy") @RequestParam Long processDeployId) {
        long start = System.currentTimeMillis();
        try {
            String resObj = appWorkflowService.getWorkflowDiagramByProcessDeployId(processDeployId);
            return this.successHandler.handlerSuccess(resObj, start);
        } catch (Exception ex) {
            return this.errorHandler.handlerException(ex, start);
        }
    }
}
