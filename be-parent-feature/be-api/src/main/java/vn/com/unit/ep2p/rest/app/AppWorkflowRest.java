/*******************************************************************************
 * Class        ：WorkflowAppRest
 * Created date ：2021/01/21
 * Lasted date  ：2021/01/21
 * Author       ：KhuongTH
 * Change log   ：2021/01/21：01-00 KhuongTH create a new
 ******************************************************************************/
package vn.com.unit.ep2p.rest.app;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
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
import vn.com.unit.ep2p.dto.CompleteStepReq;
import vn.com.unit.ep2p.dto.StartWorkflowReq;
import vn.com.unit.ep2p.dto.res.AppWorkflowRes;
import vn.com.unit.ep2p.rest.AbstractRest;
import vn.com.unit.ep2p.service.AppWorkflowService;

/**
 * <p>
 * WorkflowAppRest
 * </p>
 * 
 * @version 01-00
 * @since 01-00
 * @author KhuongTH
 */
@RestController
@RequestMapping(AppApiConstant.API_V1 + AppApiConstant.API_APP + AppApiConstant.API_APP_WORKFLOW)
@Api(tags = { AppApiConstant.API_APP_WORKFLOW_DESCR })
public class AppWorkflowRest extends AbstractRest {

    @Autowired
    private AppWorkflowService appWorkflowService;

    @PostMapping(AppApiConstant.START)
    @ApiOperation("Start workflow by businessCode and companyId")
    @ApiResponses(value = { 
            @ApiResponse(code = 200, message = "Success"), 
            @ApiResponse(code = 401, message = "Unauthorized"),
            @ApiResponse(code = 402, message = "Forbidden"), 
            @ApiResponse(code = 402801, message = "Error process list document"),
            @ApiResponse(code = 500, message = "Internal server error")})
    public DtsApiResponse startWorkflow(
            @ApiParam(name = "body", value = "Information for start process instance") @RequestBody StartWorkflowReq startWorkflowReq) {
        long start = System.currentTimeMillis();
        try {
            AppWorkflowRes resObj = appWorkflowService.startWorkflow(startWorkflowReq);
            return this.successHandler.handlerSuccess(resObj, start);
        } catch (Exception ex) {
            return this.errorHandler.handlerException(ex, start);
        }
    }
    
    @PostMapping(AppApiConstant.COMPLETE)
    @ApiOperation("Complete step by workflow")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "Success"), 
            @ApiResponse(code = 401, message = "Unauthorized"),
            @ApiResponse(code = 402, message = "Forbidden"), 
            @ApiResponse(code = 402801, message = "Error process list document"),
            @ApiResponse(code = 500, message = "Internal server error"), })
    public DtsApiResponse completeStep(
            @ApiParam(name = "body", value = "Information for complete step") @RequestBody CompleteStepReq completeStepReq) {
        long start = System.currentTimeMillis();
        try {
            AppWorkflowRes resObj = appWorkflowService.completeStep(completeStepReq);
            return this.successHandler.handlerSuccess(resObj, start);
        } catch (Exception ex) {
            return this.errorHandler.handlerException(ex, start);
        }
    }

}
