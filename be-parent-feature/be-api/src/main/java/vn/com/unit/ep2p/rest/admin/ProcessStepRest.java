/*******************************************************************************
 * Class        ：ProcessStepRest
 * Created date ：2021/01/13
 * Lasted date  ：2021/01/13
 * Author       ：SonND
 * Change log   ：2021/01/13：01-00 SonND create a new
 ******************************************************************************/
package vn.com.unit.ep2p.rest.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
import vn.com.unit.ep2p.core.req.dto.ProcessStepAddInfoReq;
import vn.com.unit.ep2p.core.req.dto.ProcessStepUpdateInfoReq;
import vn.com.unit.ep2p.rest.AbstractRest;
import vn.com.unit.ep2p.service.ProcessStepService;

/**
 * <p> ProcessStepRest </p>
 * 
 * @version 01-00
 * @since 01-00
 * @author SonND
 */
@RestController
@RequestMapping(AppApiConstant.API_V1 + AppApiConstant.API_ADMIN)
@Api(tags = {AppApiConstant.API_ADMIN_PROCESS_STEP_DESCR})
public class ProcessStepRest extends AbstractRest{
	
	@Autowired
	private ProcessStepService processStepService;
	
	@PostMapping(AppApiConstant.API_ADMIN_PROCESS_STEP)
	@ApiOperation("Api provides add new process step on systems")
	@ApiResponses(value = {
	            @ApiResponse(code = 200 , message = "Success"),
	            @ApiResponse(code = 401 , message = "Unauthorized"),
	            @ApiResponse(code = 402 , message = "Forbidden"),
	            @ApiResponse(code = 4023901 , message = "Error process add process step"),
	            @ApiResponse(code = 4021405 , message = "Error not found information process"),
	            @ApiResponse(code = 4023704 , message = "Error not found process status"),
	            @ApiResponse(code = 4023805 , message = "Error not found process permission"),
	            @ApiResponse(code = 4023605 , message = "Error not found information process button"),
	            @ApiResponse(code = 500 , message = "Internal server error"),
	            })
    public DtsApiResponse addProcessStep(
            @ApiParam(name = "body", value = "Process step information to add new") @RequestBody ProcessStepAddInfoReq processStepAddInfoReq ) {
        long start = System.currentTimeMillis();
        try {
            Long resObj = processStepService.create(processStepAddInfoReq);
            return this.successHandler.handlerSuccess(resObj, start);
        } catch (Exception ex) {
            return this.errorHandler.handlerException(ex, start);
        }
    }
	
	@PutMapping(AppApiConstant.API_ADMIN_PROCESS_STEP)
	@ApiOperation("Api provides update information process step on systems")
	@ApiResponses(value = {
	            @ApiResponse(code = 200 , message = "Success"),
	            @ApiResponse(code = 401 , message = "Unauthorized"),
	            @ApiResponse(code = 402 , message = "Forbidden"),
	            @ApiResponse(code = 4023902 , message = "Error process update information process step"),
	            @ApiResponse(code = 4021405 , message = "Error not found information process"),
	            @ApiResponse(code = 4023704 , message = "Error not found process status"),
                @ApiResponse(code = 4023805 , message = "Error not found process permission"),
                @ApiResponse(code = 4023605 , message = "Error not found information process button"),
	            @ApiResponse(code = 4023904 , message = "Error not found process step"),
	            @ApiResponse(code = 500 , message = "Internal server error"),
	            })
    public DtsApiResponse updateProcessStep(
            @ApiParam(name = "body", value = "Process information to update") @RequestBody ProcessStepUpdateInfoReq processStepUpdateInfoReq) {
        long start = System.currentTimeMillis();
        try {
            processStepService.update(processStepUpdateInfoReq);
            return this.successHandler.handlerSuccess(null, start);
        } catch (Exception ex) {
            return this.errorHandler.handlerException(ex, start);
        }
    }
	
	@DeleteMapping(AppApiConstant.API_ADMIN_PROCESS_STEP)
	@ApiOperation("Api provides delete process step on systems")
	@ApiResponses(value = {
	            @ApiResponse(code = 200 , message = "Success"),
	            @ApiResponse(code = 401 , message = "Unauthorized"),
	            @ApiResponse(code = 402 , message = "Forbidden"),
	            @ApiResponse(code = 4023903 , message = "Error process delete process step"),
	            @ApiResponse(code = 4023904 , message = "Error not found process step"),
	            @ApiResponse(code = 500 , message = "Internal server error"),
	            })
    public DtsApiResponse deleteProcessStep(
            @ApiParam(name = "stepId", value = "Delete process step on system by id", example = "1") @RequestParam("stepId") Long stepId) {
        long start = System.currentTimeMillis();
        try {
            processStepService.delete(stepId);
            return this.successHandler.handlerSuccess(null, start);
        } catch (Exception ex) {
            return this.errorHandler.handlerException(ex, start);
        }
    }
	
}
