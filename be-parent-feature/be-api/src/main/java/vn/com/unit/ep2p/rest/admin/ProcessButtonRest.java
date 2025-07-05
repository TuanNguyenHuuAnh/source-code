/*******************************************************************************
 * Class        ：ProcessButtonRest
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
import vn.com.unit.ep2p.core.req.dto.ProcessButtonAddInfoReq;
import vn.com.unit.ep2p.core.req.dto.ProcessButtonUpdateInfoReq;
import vn.com.unit.ep2p.rest.AbstractRest;
import vn.com.unit.ep2p.service.ProcessButtonService;
import vn.com.unit.workflow.dto.JpmButtonDto;

/**
 * <p> ProcessButtonRest </p>
 * 
 * @version 01-00
 * @since 01-00
 * @author SonND
 */
@RestController
@RequestMapping(AppApiConstant.API_V1 + AppApiConstant.API_ADMIN)
@Api(tags = {AppApiConstant.API_ADMIN_PROCESS_BUTTON_DESCR})
public class ProcessButtonRest extends AbstractRest{
	
	@Autowired
	private ProcessButtonService processButtonService;
	
	@PostMapping(AppApiConstant.API_ADMIN_PROCESS_BUTTON)
	@ApiOperation("Api provides add new process button on systems")
	@ApiResponses(value = {
	            @ApiResponse(code = 200 , message = "Success"),
	            @ApiResponse(code = 401 , message = "Unauthorized"),
	            @ApiResponse(code = 402 , message = "Forbidden"),
	            @ApiResponse(code = 4023601 , message = "Error process add process button"),
	            @ApiResponse(code = 4021405 , message = "Error not found information process"),
	            @ApiResponse(code = 500 , message = "Internal server error"),
	            })
    public DtsApiResponse addProcessButton(
            @ApiParam(name = "body", value = "Process button information to add new") @RequestBody ProcessButtonAddInfoReq processButtonAddInfoReq) {
        long start = System.currentTimeMillis();
        try {
            Long resObj = processButtonService.create(processButtonAddInfoReq);
            return this.successHandler.handlerSuccess(resObj, start);
        } catch (Exception ex) {
            return this.errorHandler.handlerException(ex, start);
        }
    }
	
	@PutMapping(AppApiConstant.API_ADMIN_PROCESS_BUTTON)
	@ApiOperation("Api provides update information process button on systems")
	@ApiResponses(value = {
	            @ApiResponse(code = 200 , message = "Success"),
	            @ApiResponse(code = 401 , message = "Unauthorized"),
	            @ApiResponse(code = 402 , message = "Forbidden"),
	            @ApiResponse(code = 4023602 , message = "Error process update information process button"),
	            @ApiResponse(code = 4021405 , message = "Error not found information process"),
	            @ApiResponse(code = 4023604 , message = "Error not found process button"),
	            @ApiResponse(code = 500 , message = "Internal server error"),
	            })
    public DtsApiResponse updateProcessButton(
            @ApiParam(name = "body", value = "Process information to update") @RequestBody ProcessButtonUpdateInfoReq processButtonUpdateInfoReq) {
        long start = System.currentTimeMillis();
        try {
            processButtonService.update(processButtonUpdateInfoReq);
            return this.successHandler.handlerSuccess(null, start);
        } catch (Exception ex) {
            return this.errorHandler.handlerException(ex, start);
        }
    }
	
	@DeleteMapping(AppApiConstant.API_ADMIN_PROCESS_BUTTON)
	@ApiOperation("Api provides delete process button on systems")
	@ApiResponses(value = {
	            @ApiResponse(code = 200 , message = "Success"),
	            @ApiResponse(code = 401 , message = "Unauthorized"),
	            @ApiResponse(code = 402 , message = "Forbidden"),
	            @ApiResponse(code = 4023603 , message = "Error process delete process button"),
	            @ApiResponse(code = 4023604 , message = "Error not found process button"),
	            @ApiResponse(code = 500 , message = "Internal server error"),
	            })
    public DtsApiResponse deleteProcessButton(
            @ApiParam(name = "buttonId", value = "Delete process button on system by id", example = "1") @RequestParam("buttonId") Long buttonId) {
        long start = System.currentTimeMillis();
        try {
            processButtonService.delete(buttonId);
            return this.successHandler.handlerSuccess(null, start);
        } catch (Exception ex) {
            return this.errorHandler.handlerException(ex, start);
        }
    }
	
	@GetMapping(AppApiConstant.API_ADMIN_PROCESS_BUTTON) 
    @ApiOperation("Api provides list process button on system by processId")
    @ApiResponses(value = {
                @ApiResponse(code = 200 , message = "success"),
                @ApiResponse(code = 500 , message = "Internal Server Error"),
                @ApiResponse(code = 401 , message = "Unauthorized"),
                @ApiResponse(code = 402 , message = "Forbidden")})
    public DtsApiResponse getButtonByProcessId(
            @ApiParam(name = "processId", value = "Get list process button on system by processId", example = "1") @RequestParam("processId") Long processId) {
        long start = System.currentTimeMillis();
        try {
            List<JpmButtonDto> result = processButtonService.getButtonDtosByProcessId(processId);
            return this.successHandler.handlerSuccess(result, start);
        } catch (Exception ex) {
            return this.errorHandler.handlerException(ex, start);
        }
    }
	
	@GetMapping(AppApiConstant.API_ADMIN_PROCESS_BUTTON +"/{buttonId}") 
    @ApiOperation("Api provides detail process button on systems")
    @ApiResponses(value = {
                @ApiResponse(code = 200 , message = "success"),
                @ApiResponse(code = 500 , message = "Internal Server Error"),
                @ApiResponse(code = 401 , message = "Unauthorized"),
                @ApiResponse(code = 402 , message = "Forbidden"),
                @ApiResponse(code = 4023604 , message = "Error not found information process button")})
    public DtsApiResponse detailButtonById(
            @ApiParam(name = "buttonId", value = "Detail process button on system by id",example = "1") @PathVariable("buttonId") Long buttonId) {
        long start = System.currentTimeMillis();
        try {
            JpmButtonDto result = processButtonService.getJpmButtonDtoByButtonId(buttonId);
            return this.successHandler.handlerSuccess(result, start);
        } catch (Exception ex) {
            return this.errorHandler.handlerException(ex, start);
        }
    }
	
}
