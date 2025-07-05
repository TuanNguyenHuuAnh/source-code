/*******************************************************************************
 * Class        ：ProcessRest
 * Created date ：2020/12/07
 * Lasted date  ：2020/12/07
 * Author       ：KhuongTH
 * Change log   ：2020/12/07：01-00 KhuongTH create a new
 ******************************************************************************/
package vn.com.unit.ep2p.rest.admin;

import java.io.File;
import java.nio.file.Files;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.util.MultiValueMap;
import org.springframework.util.ResourceUtils;
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
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import springfox.documentation.annotations.ApiIgnore;
import vn.com.unit.core.res.ObjectDataRes;
import vn.com.unit.dts.web.rest.common.DtsApiResponse;
import vn.com.unit.ep2p.core.constant.AppApiConstant;
import vn.com.unit.ep2p.dto.req.ProcessAddReq;
import vn.com.unit.ep2p.dto.req.ProcessImportReq;
import vn.com.unit.ep2p.dto.req.ProcessUpdateReq;
import vn.com.unit.ep2p.dto.res.ProcessInfoRes;
import vn.com.unit.ep2p.rest.AbstractRest;
import vn.com.unit.ep2p.service.ProcessService;
import vn.com.unit.workflow.dto.JpmProcessDto;

/**
 * <p> ProcessRest </p>
 * 
 * @version 01-00
 * @since 01-00
 * @author KhuongTH
 */
@RestController
@RequestMapping(AppApiConstant.API_V1 + AppApiConstant.API_ADMIN)
@Api(tags = {AppApiConstant.API_ADMIN_PROCESS_DESCR})
public class ProcessRest extends AbstractRest{
	
	@Autowired
	private ProcessService processService;
	
	@GetMapping(AppApiConstant.API_ADMIN_PROCESS)
	@ApiOperation("Api provides a list process on systems")
	@ApiResponses(value = {
	            @ApiResponse(code = 200 , message = "success"),
	            @ApiResponse(code = 500 , message = "Internal Server Error"),
	            @ApiResponse(code = 401 , message = "Unauthorized"),
	            @ApiResponse(code = 402 , message = "Forbidden"),
	            @ApiResponse(code = 4021400 , message = "Error process list process")})
    @ApiImplicitParams({
        @ApiImplicitParam(name = "keySearch", value = "keySearch", required = false, dataTypeClass = String.class, paramType = "query"),
        @ApiImplicitParam(name = "companyId", value = "1", dataTypeClass = String.class, paramType = "query"),
        @ApiImplicitParam(name = "businessId", value = "1", dataTypeClass = String.class, paramType = "query"),

        @ApiImplicitParam(name = "page", dataType = "string", paramType = "query", value = "Results page you want to retrieve (0..N)"),
        @ApiImplicitParam(name = "size", dataType = "string", paramType = "query", value = "Number of records per page."),
        @ApiImplicitParam(name = "sort", allowMultiple = true, dataType = "string", paramType = "query", value = "Sorting criteria in the format: property(,asc|desc). "
                + "Default sort order is ascending. " + "Multiple sort criteria are supported.") })
    public DtsApiResponse listProcess(
            @ApiIgnore @RequestParam MultiValueMap<String, String> requestParams,@ApiIgnore Pageable pageable) {
        long start = System.currentTimeMillis();
        try {
            ObjectDataRes<JpmProcessDto> obj = processService.search(requestParams, pageable);
            ObjectDataRes<ProcessInfoRes> resObj = objectMapper.convertValue(obj, ObjectDataRes.class);
            return this.successHandler.handlerSuccess(resObj, start);
        } catch (Exception ex) {
            return this.errorHandler.handlerException(ex, start);
        }
    }
	
	@PostMapping(AppApiConstant.API_ADMIN_PROCESS)
	@ApiOperation("Api provides add new process on systems")
	@ApiResponses(value = {
	            @ApiResponse(code = 200 , message = "success"),
	            @ApiResponse(code = 500 , message = "Internal Server Error"),
	            @ApiResponse(code = 401 , message = "Unauthorized"),
	            @ApiResponse(code = 402 , message = "Forbidden"),
	            @ApiResponse(code = 4021401 , message = "Error process add process")})
    public DtsApiResponse addProcess(
            @ApiParam(name = "body", value = "Process information to add new") @RequestBody ProcessAddReq reqProcessAddDto) {
        long start = System.currentTimeMillis();
        try {
            Long resObj = processService.create(reqProcessAddDto);
            return this.successHandler.handlerSuccess(resObj, start);
        } catch (Exception ex) {
            return this.errorHandler.handlerException(ex, start);
        }
    }
	
	@PutMapping(AppApiConstant.API_ADMIN_PROCESS)
	@ApiOperation("Api provides update information process on systems")
	@ApiResponses(value = {
	            @ApiResponse(code = 200 , message = "success"),
	            @ApiResponse(code = 500 , message = "Internal Server Error"),
	            @ApiResponse(code = 401 , message = "Unauthorized"),
	            @ApiResponse(code = 402 , message = "Forbidden"),
	            @ApiResponse(code = 4021402 , message = "Error process update info process"),
	            @ApiResponse(code = 4021405 , message = "Error process not found")})
    public DtsApiResponse updateProcess(
            @ApiParam(name = "body", value = "Process information to update") @RequestBody ProcessUpdateReq reqProcessUpdateDto) {
        long start = System.currentTimeMillis();
        try {
            processService.update(reqProcessUpdateDto);
            return this.successHandler.handlerSuccess(null, start);
        } catch (Exception ex) {
            return this.errorHandler.handlerException(ex, start);
        }
    }
	
	@DeleteMapping(AppApiConstant.API_ADMIN_PROCESS)
	@ApiOperation("Api provides delete process on systems")
	@ApiResponses(value = {
	            @ApiResponse(code = 200 , message = "success"),
	            @ApiResponse(code = 500 , message = "Internal Server Error"),
	            @ApiResponse(code = 401 , message = "Unauthorized"),
	            @ApiResponse(code = 402 , message = "Forbidden"),
	            @ApiResponse(code = 4021403 , message = "Error process delete process"),
	            @ApiResponse(code = 4021405 , message = "Error process not found")})
    public DtsApiResponse deleteProcess(
            @ApiParam(name = "processId", value = "Delete process on system by id", example = "123") @RequestParam("processId") Long processId) {
        long start = System.currentTimeMillis();
        try {
            processService.delete(processId);
            return this.successHandler.handlerSuccess(null, start);
        } catch (Exception ex) {
            return this.errorHandler.handlerException(ex, start);
        }
    }
	
	@GetMapping(AppApiConstant.API_ADMIN_PROCESS + "/{processId}") 
	@ApiOperation("Api provides process information detail on systems")
	@ApiResponses(value = {
	            @ApiResponse(code = 200 , message = "success"),
	            @ApiResponse(code = 500 , message = "Internal Server Error"),
	            @ApiResponse(code = 401 , message = "Unauthorized"),
	            @ApiResponse(code = 402 , message = "Forbidden"),
	            @ApiResponse(code = 4021405 , message = "Error process not found")})
    public DtsApiResponse detailProcess(
            @ApiParam(name = "processId", value = "Get process information detail on system by id", example = "123") @PathVariable("processId") Long processId) {
        long start = System.currentTimeMillis();
        try {
            JpmProcessDto obj = processService.detail(processId);
            ProcessInfoRes resObj = objectMapper.convertValue(obj, ProcessInfoRes.class);
            return this.successHandler.handlerSuccess(resObj, start);
        } catch (Exception ex) {
            return this.errorHandler.handlerException(ex, start);
        }
    }
	
	@PostMapping(AppApiConstant.API_ADMIN_PROCESS + "/deploy") 
	@ApiOperation("Api provides deploy a process on systems")
	@ApiResponses(value = {
	        @ApiResponse(code = 200 , message = "success"),
	        @ApiResponse(code = 500 , message = "Internal Server Error"),
	        @ApiResponse(code = 401 , message = "Unauthorized"),
	        @ApiResponse(code = 402 , message = "Forbidden"),
            @ApiResponse(code = 4021405 , message = "Error process not found"),
            @ApiResponse(code = 4021407 , message = "Error deploy process"),
            @ApiResponse(code = 4021408 , message = "Error process deploy clone SLA"),
            @ApiResponse(code = 4021409 , message = "Error process deploy clone role")})
    public DtsApiResponse deployProcess(
            @ApiParam(name = "processId", value = "Deploy a process on system by id", example = "1") @RequestParam("processId") Long processId,
            @ApiParam(name = "oldProcessDeployId", value = "Id of process deploy", example = "1", required = false) @RequestParam(name = "oldProcessDeployId", required = false) Long oldProcessDeployId,
            @ApiParam(name = "cloneSlaFlag", value = "Create new SLA from old process deploy id", example = "false") @RequestParam("cloneSlaFlag") boolean cloneSlaFlag,
            @ApiParam(name = "cloneRoleFlag", value = "Create new Role from old process deploy id", example = "false") @RequestParam("cloneRoleFlag") boolean cloneRoleFlag
            ) {
        long start = System.currentTimeMillis();
        try {
            Long resObj = processService.deploy(processId, oldProcessDeployId, cloneSlaFlag, cloneRoleFlag);
            return this.successHandler.handlerSuccess(resObj, start);
        } catch (Exception ex) {
            return this.errorHandler.handlerException(ex, start);
        }
    }
	
    @PostMapping(AppApiConstant.API_ADMIN_PROCESS + "/import")
    @ApiOperation("Api provides import process on systems")
    @ApiResponses(value = {
                @ApiResponse(code = 200 , message = "success"),
                @ApiResponse(code = 500 , message = "Internal Server Error"),
                @ApiResponse(code = 401 , message = "Unauthorized"),
                @ApiResponse(code = 402 , message = "Forbidden"),
                @ApiResponse(code = 4021405 , message = "Error process not found"),
                @ApiResponse(code = 4021406 , message = "Error process already exists")})
    public DtsApiResponse importProcess(
            @ApiParam(name = "body", value = "Process information to import") @RequestBody ProcessImportReq reqProcessImportDto) {
        long start = System.currentTimeMillis();
        try {
            Long resObj = processService.importProcess(reqProcessImportDto);
            return this.successHandler.handlerSuccess(resObj, start);
        } catch (Exception ex) {
            return this.errorHandler.handlerException(ex, start);
        }
    }
    
    @PostMapping(AppApiConstant.API_ADMIN_PROCESS + "/init-defaut")
    @ApiOperation("Api provides init process 3-step on systems")
    @ApiResponses(value = {
                @ApiResponse(code = 200 , message = "success"),
                @ApiResponse(code = 500 , message = "Internal Server Error"),
                @ApiResponse(code = 401 , message = "Unauthorized"),
                @ApiResponse(code = 402 , message = "Forbidden"),
                @ApiResponse(code = 4021405 , message = "Error process not found"),
                @ApiResponse(code = 4021406 , message = "Error process already exists")})
    public DtsApiResponse initDefaultProcess(
            @ApiParam(name = "companyId", value = "Id of company", example = "1") @RequestParam("companyId") Long companyId) {
        long start = System.currentTimeMillis();
        try {
            ProcessImportReq processImportReq = new ProcessImportReq();
            processImportReq.setCompanyId(companyId);
            processImportReq.setOverride(false);
            File file = ResourceUtils.getFile("classpath:process-export/ProcessSimpleTest.txt");
            String content = new String(Files.readAllBytes(file.toPath()));
            processImportReq.setImportFile(content);
            Long resObj = processService.importProcess(processImportReq);
            return this.successHandler.handlerSuccess(resObj, start);
        } catch (Exception ex) {
            return this.errorHandler.handlerException(ex, start);
        }
    }
}
