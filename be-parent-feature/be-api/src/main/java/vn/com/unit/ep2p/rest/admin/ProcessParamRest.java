/*******************************************************************************
 * Class        ：ProcessParamRest
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
import vn.com.unit.ep2p.core.req.dto.ProcessParamInfoReq;
import vn.com.unit.ep2p.core.req.dto.ProcessParamUpdateInfoReq;
import vn.com.unit.ep2p.rest.AbstractRest;
import vn.com.unit.ep2p.service.ProcessParamService;
import vn.com.unit.workflow.dto.JpmParamDto;

/**
 * <p> ProcessParamRest </p>
 * 
 * @version 01-00
 * @since 01-00
 * @author SonND
 */
@RestController
@RequestMapping(AppApiConstant.API_V1 + AppApiConstant.API_ADMIN)
@Api(tags = {AppApiConstant.API_ADMIN_PROCESS_PARAM_DESCR})
public class ProcessParamRest extends AbstractRest{
	
	@Autowired
	private ProcessParamService processParamService;
	
	@PostMapping(AppApiConstant.API_ADMIN_PROCESS_PARAM)
	@ApiOperation("Api provides add new process parameter on systems")
	@ApiResponses(value = {
	            @ApiResponse(code = 200 , message = "Success"),
	            @ApiResponse(code = 401 , message = "Unauthorized"),
	            @ApiResponse(code = 402 , message = "Forbidden"),
	            @ApiResponse(code = 4023501 , message = "Error process add process parameter"),
	            @ApiResponse(code = 4021405 , message = "Error not found information process"),
	            @ApiResponse(code = 500 , message = "Internal server error"),
	            })
    public DtsApiResponse addProcessParam(
            @ApiParam(name = "body", value = "Process parameter information to add new") @RequestBody ProcessParamInfoReq processParamInfoReq) {
        long start = System.currentTimeMillis();
        try {
            Long resObj = processParamService.create(processParamInfoReq);
            return this.successHandler.handlerSuccess(resObj, start);
        } catch (Exception ex) {
            return this.errorHandler.handlerException(ex, start);
        }
    }
	
	@PutMapping(AppApiConstant.API_ADMIN_PROCESS_PARAM)
	@ApiOperation("Api provides update information process parameter on systems")
	@ApiResponses(value = {
	            @ApiResponse(code = 200 , message = "Success"),
	            @ApiResponse(code = 401 , message = "Unauthorized"),
	            @ApiResponse(code = 402 , message = "Forbidden"),
	            @ApiResponse(code = 4023502 , message = "Error process update information process parameter"),
	            @ApiResponse(code = 4021405 , message = "Error not found information process"),
	            @ApiResponse(code = 4023504 , message = "Error not found process parameter"),
	            @ApiResponse(code = 500 , message = "Internal server error"),
	            })
    public DtsApiResponse updateProcessParam(
            @ApiParam(name = "body", value = "Process information to update") @RequestBody ProcessParamUpdateInfoReq processParamUpdateInfoReq) {
        long start = System.currentTimeMillis();
        try {
            processParamService.update(processParamUpdateInfoReq);
            return this.successHandler.handlerSuccess(null, start);
        } catch (Exception ex) {
            return this.errorHandler.handlerException(ex, start);
        }
    }
	
	@DeleteMapping(AppApiConstant.API_ADMIN_PROCESS_PARAM)
	@ApiOperation("Api provides delete process parameter on systems")
	@ApiResponses(value = {
	            @ApiResponse(code = 200 , message = "Success"),
	            @ApiResponse(code = 401 , message = "Unauthorized"),
	            @ApiResponse(code = 402 , message = "Forbidden"),
	            @ApiResponse(code = 4023503 , message = "Error process delete process parameter"),
	            @ApiResponse(code = 4023504 , message = "Error not found process parameter"),
	            @ApiResponse(code = 500 , message = "Internal server error"),
	            })
    public DtsApiResponse deleteProcess(
            @ApiParam(name = "paramId", value = "Delete process parameter on system by id", example = "1") @RequestParam("paramId") Long paramId) {
        long start = System.currentTimeMillis();
        try {
            processParamService.delete(paramId);
            return this.successHandler.handlerSuccess(null, start);
        } catch (Exception ex) {
            return this.errorHandler.handlerException(ex, start);
        }
    }
	
	@GetMapping(AppApiConstant.API_ADMIN_PROCESS_PARAM) 
    @ApiOperation("Api provides list process parameter on system by processId")
    @ApiResponses(value = {
                @ApiResponse(code = 200 , message = "success"),
                @ApiResponse(code = 500 , message = "Internal Server Error"),
                @ApiResponse(code = 401 , message = "Unauthorized"),
                @ApiResponse(code = 402 , message = "Forbidden")})
    public DtsApiResponse getParamDtosByProcessId(
            @ApiParam(name = "processId", value = "Get list process parameter on system by processId",example = "1") @RequestParam("processId") Long processId) {
        long start = System.currentTimeMillis();
        try {
            List<JpmParamDto> result = processParamService.getParamDtosByProcessId(processId);
            return this.successHandler.handlerSuccess(result, start);
        } catch (Exception ex) {
            return this.errorHandler.handlerException(ex, start);
        }
    }
	
	@GetMapping(AppApiConstant.API_ADMIN_PROCESS_PARAM +"/{paramId}") 
    @ApiOperation("Api provides detail process parameter on systems")
    @ApiResponses(value = {
                @ApiResponse(code = 200 , message = "success"),
                @ApiResponse(code = 500 , message = "Internal Server Error"),
                @ApiResponse(code = 401 , message = "Unauthorized"),
                @ApiResponse(code = 402 , message = "Forbidden"),
                @ApiResponse(code = 4023504 , message = "Error not found information process parameter")})
    public DtsApiResponse getJpmParamById(
            @ApiParam(name = "paramId", value = "Detail process parameter on system by id",example = "1") @PathVariable("paramId") Long paramId) {
        long start = System.currentTimeMillis();
        try {
            JpmParamDto result = processParamService.getJpmParamDtoByParamId(paramId);
            return this.successHandler.handlerSuccess(result, start);
        } catch (Exception ex) {
            return this.errorHandler.handlerException(ex, start);
        }
    }
	
}
