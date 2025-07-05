/*******************************************************************************
 * Class        ：ProcessStatusRest
 * Created date ：2021/01/13
 * Lasted date  ：2021/01/13
 * Author       ：SonND
 * Change log   ：2021/01/13：01-00 SonND create a new
 ******************************************************************************/
package vn.com.unit.ep2p.rest.admin;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
import vn.com.unit.ep2p.core.req.dto.ProcessStatusAddInfoReq;
import vn.com.unit.ep2p.core.req.dto.ProcessStatusUpdateInfoReq;
import vn.com.unit.ep2p.rest.AbstractRest;
import vn.com.unit.ep2p.service.ProcessStatusService;
import vn.com.unit.workflow.dto.JpmStatusDto;

/**
 * <p> ProcessStatusRest </p>
 * 
 * @version 01-00
 * @since 01-00
 * @author SonND
 */
@RestController
@RequestMapping(AppApiConstant.API_V1 + AppApiConstant.API_ADMIN)
@Api(tags = {AppApiConstant.API_ADMIN_PROCESS_STATUS_DESCR})
public class ProcessStatusRest extends AbstractRest{
	
	@Autowired
	private ProcessStatusService processStatusService;
	
	@PostMapping(AppApiConstant.API_ADMIN_PROCESS_STATUS)
	@ApiOperation("Api provides add new process status on systems")
	@ApiResponses(value = {
	            @ApiResponse(code = 200 , message = "Success"),
	            @ApiResponse(code = 401 , message = "Unauthorized"),
	            @ApiResponse(code = 402 , message = "Forbidden"),
	            @ApiResponse(code = 4023701 , message = "Error process add process status"),
	            @ApiResponse(code = 4021405 , message = "Error not found information process"),
	            @ApiResponse(code = 500 , message = "Internal server error"),
	            })
    public DtsApiResponse addProcessStatus(
            @ApiParam(name = "body", value = "Process status information to add new") @RequestBody ProcessStatusAddInfoReq processStatusAddInfoReq ) {
        long start = System.currentTimeMillis();
        try {
            Long resObj = processStatusService.create(processStatusAddInfoReq);
            return this.successHandler.handlerSuccess(resObj, start);
        } catch (Exception ex) {
            return this.errorHandler.handlerException(ex, start);
        }
    }
	
	@PutMapping(AppApiConstant.API_ADMIN_PROCESS_STATUS)
	@ApiOperation("Api provides update information process status on systems")
	@ApiResponses(value = {
	            @ApiResponse(code = 200 , message = "Success"),
	            @ApiResponse(code = 401 , message = "Unauthorized"),
	            @ApiResponse(code = 402 , message = "Forbidden"),
	            @ApiResponse(code = 4023702 , message = "Error process update information process status"),
	            @ApiResponse(code = 4021405 , message = "Error not found information process"),
	            @ApiResponse(code = 4023704 , message = "Error not found process status"),
	            @ApiResponse(code = 500 , message = "Internal server error"),
	            })
    public DtsApiResponse updateProcessStatus(
            @ApiParam(name = "body", value = "Process information to update") @RequestBody ProcessStatusUpdateInfoReq processStatusUpdateInfoReq) {
        long start = System.currentTimeMillis();
        try {
            processStatusService.update(processStatusUpdateInfoReq);
            return this.successHandler.handlerSuccess(null, start);
        } catch (Exception ex) {
            return this.errorHandler.handlerException(ex, start);
        }
    }
	
	@DeleteMapping(AppApiConstant.API_ADMIN_PROCESS_STATUS)
	@ApiOperation("Api provides delete process status on systems")
	@ApiResponses(value = {
	            @ApiResponse(code = 200 , message = "Success"),
	            @ApiResponse(code = 401 , message = "Unauthorized"),
	            @ApiResponse(code = 402 , message = "Forbidden"),
	            @ApiResponse(code = 4023703 , message = "Error process delete process status"),
	            @ApiResponse(code = 4023704 , message = "Error not found process status"),
	            @ApiResponse(code = 500 , message = "Internal server error"),
	            })
    public DtsApiResponse deleteProcessStatus(
            @ApiParam(name = "statusId", value = "Delete process status on system by id", example = "1") @RequestParam("statusId") Long statusId) {
        long start = System.currentTimeMillis();
        try {
            processStatusService.delete(statusId);
            return this.successHandler.handlerSuccess(null, start);
        } catch (Exception ex) {
            return this.errorHandler.handlerException(ex, start);
        }
    }
	
	@GetMapping(AppApiConstant.API_ADMIN_PROCESS_STATUS) 
    @ApiOperation("Api provides list process status on system by processId")
    @ApiResponses(value = {
                @ApiResponse(code = 200 , message = "success"),
                @ApiResponse(code = 500 , message = "Internal Server Error"),
                @ApiResponse(code = 401 , message = "Unauthorized"),
                @ApiResponse(code = 402 , message = "Forbidden")})
    public DtsApiResponse getStatusDtosByProcessId(
            @ApiParam(name = "processId", value = "Get list process status on system by processId",example = "1") @RequestParam("processId") Long processId) {
        long start = System.currentTimeMillis();
        try {
            List<JpmStatusDto> result = processStatusService.getStatusDtosByProcessId(processId);
            return this.successHandler.handlerSuccess(result, start);
        } catch (Exception ex) {
            return this.errorHandler.handlerException(ex, start);
        }
    }
	
    @GetMapping(AppApiConstant.API_ADMIN_PROCESS_STATUS + "/business")
    @ApiOperation("Api provides list process status on system by business code")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "success"),
            @ApiResponse(code = 500, message = "Internal Server Error"),
            @ApiResponse(code = 401, message = "Unauthorized"), @ApiResponse(code = 402, message = "Forbidden") })
    public DtsApiResponse getStatusDtosByBusinessCode(
            @ApiParam(name = "businessCode", value = "Get list process status on system by business Code", example = "1") @RequestParam("businessCode") String businessCode) {
        long start = System.currentTimeMillis();
        try {
            List<JpmStatusDto> result = processStatusService.getStatusDtosByBusinessCode(businessCode);
            return this.successHandler.handlerSuccess(result, start);
        } catch (Exception ex) {
            return this.errorHandler.handlerException(ex, start);
        }
    }
	
	@GetMapping(AppApiConstant.API_ADMIN_PROCESS_STATUS +"/{statusId}") 
    @ApiOperation("Api provides detail process status on systems")
    @ApiResponses(value = {
                @ApiResponse(code = 200 , message = "success"),
                @ApiResponse(code = 500 , message = "Internal Server Error"),
                @ApiResponse(code = 401 , message = "Unauthorized"),
                @ApiResponse(code = 402 , message = "Forbidden"),
                @ApiResponse(code = 4023704 , message = "Error not found process status")})
    public DtsApiResponse getJpmPermissionById(
            @ApiParam(name = "statusId", value = "Detail process status on system by id",example = "1") @PathVariable("statusId") Long statusId) {
        long start = System.currentTimeMillis();
        try {
            JpmStatusDto result = processStatusService.getStatusDtoByStatusId(statusId);
            return this.successHandler.handlerSuccess(result, start);
        } catch (Exception ex) {
            return this.errorHandler.handlerException(ex, start);
        }
    }
	
}
