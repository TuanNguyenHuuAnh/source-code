/*******************************************************************************
 * Class        ：ProcessPermissionRest
 * Created date ：2021/01/12
 * Lasted date  ：2021/01/12
 * Author       ：SonND
 * Change log   ：2021/01/12：01-00 SonND create a new
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
import vn.com.unit.ep2p.core.req.dto.ProcessPermissionAddInfoReq;
import vn.com.unit.ep2p.core.req.dto.ProcessPermissionUpdateInfoReq;
import vn.com.unit.ep2p.rest.AbstractRest;
import vn.com.unit.ep2p.service.ProcessPermissionService;
import vn.com.unit.workflow.dto.JpmPermissionDto;

/**
 * <p> ProcessPermissionRest </p>
 * 
 * @version 01-00
 * @since 01-00
 * @author SonND
 */
@RestController
@RequestMapping(AppApiConstant.API_V1 + AppApiConstant.API_ADMIN)
@Api(tags = {AppApiConstant.API_ADMIN_PROCESS_PERMISSION_DESCR})
public class ProcessPermissionRest extends AbstractRest{
	
	@Autowired
	private ProcessPermissionService processPermissionService;
	
	@PostMapping(AppApiConstant.API_ADMIN_PROCESS_PERMISSION)
	@ApiOperation("Api provides add new process permission on systems")
	@ApiResponses(value = {
	            @ApiResponse(code = 200 , message = "Success"),
	            @ApiResponse(code = 401 , message = "Unauthorized"),
	            @ApiResponse(code = 402 , message = "Forbidden"),
	            @ApiResponse(code = 4023801 , message = "Error process add process permission"),
	            @ApiResponse(code = 4021405 , message = "Error not found information process"),
	            @ApiResponse(code = 500 , message = "Internal server error"),
	            })
    public DtsApiResponse addProcessPermission(
            @ApiParam(name = "body", value = "Process permission information to add new") @RequestBody ProcessPermissionAddInfoReq processPermissionAddInfoReq) {
        long start = System.currentTimeMillis();
        try {
            Long resObj = processPermissionService.create(processPermissionAddInfoReq);
            return this.successHandler.handlerSuccess(resObj, start);
        } catch (Exception ex) {
            return this.errorHandler.handlerException(ex, start);
        }
    }
	
	@PutMapping(AppApiConstant.API_ADMIN_PROCESS_PERMISSION)
	@ApiOperation("Api provides update information process parameter on systems")
	@ApiResponses(value = {
	            @ApiResponse(code = 200 , message = "Success"),
	            @ApiResponse(code = 401 , message = "Unauthorized"),
	            @ApiResponse(code = 402 , message = "Forbidden"),
	            @ApiResponse(code = 4023802 , message = "Error process update information process permission"),
	            @ApiResponse(code = 4021405 , message = "Error not found information process"),
	            @ApiResponse(code = 4023804 , message = "Error not found process permission"),
	            @ApiResponse(code = 500 , message = "Internal server error"),
	            })
    public DtsApiResponse updateProcessPermission(
            @ApiParam(name = "body", value = "Process information to update") @RequestBody ProcessPermissionUpdateInfoReq processPermissionUpdateInfoReq) {
        long start = System.currentTimeMillis();
        try {
            processPermissionService.update(processPermissionUpdateInfoReq);
            return this.successHandler.handlerSuccess(null, start);
        } catch (Exception ex) {
            return this.errorHandler.handlerException(ex, start);
        }
    }
	
	@DeleteMapping(AppApiConstant.API_ADMIN_PROCESS_PERMISSION)
	@ApiOperation("Api provides delete process permission on systems")
	@ApiResponses(value = {
	            @ApiResponse(code = 200 , message = "Success"),
	            @ApiResponse(code = 401 , message = "Unauthorized"),
	            @ApiResponse(code = 402 , message = "Forbidden"),
	            @ApiResponse(code = 4023803 , message = "Error process delete process permission"),
	            @ApiResponse(code = 4023804 , message = "Error not found process permission"),
	            @ApiResponse(code = 500 , message = "Internal server error"),
	            })
    public DtsApiResponse deleteProcess(
            @ApiParam(name = "permissionId", value = "Delete process permission on system by id", example = "1") @RequestParam("permissionId") Long permissionId) {
        long start = System.currentTimeMillis();
        try {
            processPermissionService.delete(permissionId);
            return this.successHandler.handlerSuccess(null, start);
        } catch (Exception ex) {
            return this.errorHandler.handlerException(ex, start);
        }
    }
	
	@GetMapping(AppApiConstant.API_ADMIN_PROCESS_PERMISSION) 
    @ApiOperation("Api provides list process permission on system by processId")
    @ApiResponses(value = {
                @ApiResponse(code = 200 , message = "success"),
                @ApiResponse(code = 500 , message = "Internal Server Error"),
                @ApiResponse(code = 401 , message = "Unauthorized"),
                @ApiResponse(code = 402 , message = "Forbidden")})
    public DtsApiResponse getPermissionDtosByProcessId(
            @ApiParam(name = "processId", value = "Get list process permission on system by processId",example = "1") @RequestParam("processId") Long processId) {
        long start = System.currentTimeMillis();
        try {
            List<JpmPermissionDto> result = processPermissionService.getPermissionDtosByProcessId(processId);
            return this.successHandler.handlerSuccess(result, start);
        } catch (Exception ex) {
            return this.errorHandler.handlerException(ex, start);
        }
    }
	
	@GetMapping(AppApiConstant.API_ADMIN_PROCESS_PERMISSION +"/{permissionId}") 
    @ApiOperation("Api provides detail process permission on systems")
    @ApiResponses(value = {
                @ApiResponse(code = 200 , message = "success"),
                @ApiResponse(code = 500 , message = "Internal Server Error"),
                @ApiResponse(code = 401 , message = "Unauthorized"),
                @ApiResponse(code = 402 , message = "Forbidden"),
                @ApiResponse(code = 4023804 , message = "Error not found process permission")})
    public DtsApiResponse getJpmPermissionById(
            @ApiParam(name = "permissionId", value = "Detail process permission on system by id",example = "1") @PathVariable("permissionId") Long permissionId) {
        long start = System.currentTimeMillis();
        try {
            JpmPermissionDto result = processPermissionService.getJpmPermissionDtoByPermissionId(permissionId);
            return this.successHandler.handlerSuccess(result, start);
        } catch (Exception ex) {
            return this.errorHandler.handlerException(ex, start);
        }
    }
	
}
