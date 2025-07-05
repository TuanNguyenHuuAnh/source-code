package vn.com.unit.ep2p.rest.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.util.MultiValueMap;
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
import vn.com.unit.ep2p.dto.req.SlaConfigAddReq;
import vn.com.unit.ep2p.dto.req.SlaConfigUpdateReq;
import vn.com.unit.ep2p.rest.AbstractRest;
import vn.com.unit.ep2p.service.SlaConfigEnterpriseService;
import vn.com.unit.workflow.dto.JpmSlaConfigDto;

@RestController
@RequestMapping(AppApiConstant.API_V1 + AppApiConstant.API_ADMIN)
@Api(tags = {AppApiConstant.API_SLA_CONFIG_DESCR})
public class SlaConfigRest extends AbstractRest{
	
	@Autowired
	private SlaConfigEnterpriseService slaConfigService;
	
	
	@PostMapping(AppApiConstant.API_SLA_CONFIG)
	@ApiOperation("Create sla config")
	@ApiResponses(value = {
	            @ApiResponse(code = 200 , message = "Success"),
	            @ApiResponse(code = 500 , message = "Internal server error"),
	            @ApiResponse(code = 401 , message = "Unauthorized"),
	            @ApiResponse(code = 402 , message = "Forbidden")})
	public DtsApiResponse addSlaConfig(@ApiParam(name = "body", value = "Sla config information to add new") @RequestBody SlaConfigAddReq slaConfigReq) {
        long start = System.currentTimeMillis();
        try {
        	JpmSlaConfigDto result = slaConfigService.createJpmSlaConfigDto(slaConfigReq);
            return this.successHandler.handlerSuccess(result, start);
        }catch (Exception ex) {
            return this.errorHandler.handlerException(ex, start);
        }
    }
	
	@PutMapping(AppApiConstant.API_SLA_CONFIG)
	@ApiOperation("Update sla config")
	@ApiResponses(value = {
	            @ApiResponse(code = 200 , message = "Success"),
	            @ApiResponse(code = 500 , message = "Internal server error"),
	            @ApiResponse(code = 401 , message = "Unauthorized"),
	            @ApiResponse(code = 402 , message = "Forbidden")})
	public DtsApiResponse updateSlaConfig(@ApiParam(name = "body", value = "Sla config information to update") @RequestBody SlaConfigUpdateReq slaConfigReq) {
        long start = System.currentTimeMillis();
        try {
        	JpmSlaConfigDto result = slaConfigService.updateJpmSlaConfigDto(slaConfigReq);
            return this.successHandler.handlerSuccess(result, start);
        }catch (Exception ex) {
            return this.errorHandler.handlerException(ex, start);
        }
    }
	
	@DeleteMapping(AppApiConstant.API_SLA_CONFIG + "/{slaConfigId}")
	@ApiOperation("Delete sla config")
	@ApiResponses(value = {
	            @ApiResponse(code = 200 , message = "Success"),
	            @ApiResponse(code = 500 , message = "Internal server error"),
	            @ApiResponse(code = 401 , message = "Unauthorized"),
	            @ApiResponse(code = 402 , message = "Forbidden"),
                @ApiResponse(code = 4024002 , message = "Error delete sla config")})
	public DtsApiResponse deleteSlaConfig(@ApiParam(name = "slaConfigId", value = "Delete sla config on system by id", example = "123", required = true) @PathVariable("slaConfigId") Long slaConfigId) {
        long start = System.currentTimeMillis();
        try {
        	slaConfigService.delete(slaConfigId);
            return this.successHandler.handlerSuccess(null, start);
        }catch (Exception ex) {
            return this.errorHandler.handlerException(ex, start);
        }
    }
	
	
	@GetMapping(AppApiConstant.API_SLA_CONFIG)
    @ApiOperation("List of sla config")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "Success"), 
            @ApiResponse(code = 401, message = "Unauthorized"), @ApiResponse(code = 402, message = "Forbidden"),
            @ApiResponse(code = 402801, message = "Error process list account"),
            @ApiResponse(code = 500, message = "Internal server error"),
            @ApiResponse(code = 4024001 , message = "Error get list sla config")})
    @ApiImplicitParams({
            @ApiImplicitParam(name = "slaType", value = "0", required = false, dataTypeClass = String.class, paramType = "query"),
            @ApiImplicitParam(name = "businessId", value = "1", required = false, dataTypeClass = String.class, paramType = "query"),
            @ApiImplicitParam(name = "processDeployId", value = "1", required = false, dataTypeClass = String.class, paramType = "query"),
            @ApiImplicitParam(name = "stepDeployId", value = "1", required = false, dataTypeClass = String.class, paramType = "query"),
            @ApiImplicitParam(name = "page", dataType = "string", paramType = "query", value = "Results page you want to retrieve (0..N)"),
            @ApiImplicitParam(name = "size", dataType = "string", paramType = "query", value = "Number of records per page."),
            @ApiImplicitParam(name = "sort", allowMultiple = true, dataType = "string", paramType = "query", value = "Sorting criteria in the format: property(,asc|desc). "
                    + "Default sort order is ascending. " + "Multiple sort criteria are supported.") 
     })
    public DtsApiResponse listSlaConfig(@ApiIgnore @RequestParam MultiValueMap<String, String> requestParams,@ApiIgnore Pageable pageable) {
        long start = System.currentTimeMillis();
        try {
            ObjectDataRes<JpmSlaConfigDto> result = slaConfigService.search(requestParams, pageable);
            return this.successHandler.handlerSuccess(result, start);
        } catch (Exception ex) {
            return this.errorHandler.handlerException(ex, start);
        }
    }
	
	@GetMapping(AppApiConstant.API_SLA_CONFIG + "/{slaConfigId}")
    @ApiOperation("Api provides slaConfig information detail on systems")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "success"), @ApiResponse(code = 500, message = "Internal Server Error"),
            @ApiResponse(code = 401, message = "Unauthorized"), @ApiResponse(code = 403, message = "Forbidden"),
            //@ApiResponse(code = 201601, message = "Error id is required"),
            @ApiResponse(code = 402604, message = "Error process get slaConfig information detail") })
    public DtsApiResponse getDetail(
            @ApiParam(name = "slaConfigId", value = "Get sla config information detail on system by id", example = "1", required = true) @PathVariable("slaConfigId") Long slaConfigId) {
        long start = System.currentTimeMillis();
        try {
            JpmSlaConfigDto resObj = slaConfigService.detail(slaConfigId);
            return this.successHandler.handlerSuccess(resObj, start);
        } catch (Exception ex) {
            return this.errorHandler.handlerException(ex, start);
        }
    }
	
}
