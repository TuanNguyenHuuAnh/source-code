/*******************************************************************************
 * Class        ：ProcessDeployRest
 * Created date ：2020/12/14
 * Lasted date  ：2020/12/14
 * Author       ：KhuongTH
 * Change log   ：2020/12/14：01-00 KhuongTH create a new
******************************************************************************/
package vn.com.unit.ep2p.rest.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
import vn.com.unit.common.utils.CommonBase64Util;
import vn.com.unit.common.utils.CommonJsonUtil;
import vn.com.unit.core.res.ObjectDataRes;
import vn.com.unit.dts.web.rest.common.DtsApiResponse;
import vn.com.unit.ep2p.core.constant.AppApiConstant;
import vn.com.unit.ep2p.rest.AbstractRest;
import vn.com.unit.ep2p.service.ProcessDeployService;
import vn.com.unit.workflow.dto.JpmProcessDeployDto;
import vn.com.unit.workflow.dto.JpmProcessImportExportDto;

/**
 * <p>
 * ProcessDeployRest
 * </p>
 * .
 *
 * @version 01-00
 * @since 01-00
 * @author KhuongTH
 */
@RestController
@RequestMapping(AppApiConstant.API_V1 + AppApiConstant.API_ADMIN)
@Api(tags = {AppApiConstant.API_ADMIN_PROCESS_DEPLOY_DESCR})
public class ProcessDeployRest extends AbstractRest{
	
	/** The processsDeploy service. */
	@Autowired
	private ProcessDeployService processsDeployService;
	
	@GetMapping(AppApiConstant.API_ADMIN_PROCESS_DEPLOY)
	@ApiOperation("Api provides a list processs deploy on systems")
	@ApiResponses(value = {
	            @ApiResponse(code = 200 , message = "success"),
	            @ApiResponse(code = 500 , message = "Internal Server Error"),
	            @ApiResponse(code = 401 , message = "Unauthorized"),
	            @ApiResponse(code = 402 , message = "Forbidden"),
	            @ApiResponse(code = 4021901 , message = "Error process list processs deploy")})
    @ApiImplicitParams({
        @ApiImplicitParam(name = "keySearch", value = "keySearch", required = false, dataTypeClass = String.class, paramType = "query"),
        @ApiImplicitParam(name = "companyId", value = "1", dataTypeClass = String.class, paramType = "query"),
        @ApiImplicitParam(name = "businessId", value = "1", dataTypeClass = String.class, paramType = "query"),
        @ApiImplicitParam(name = "processId", value = "1", dataTypeClass = String.class, paramType = "query"),

        @ApiImplicitParam(name = "page", dataType = "string", paramType = "query", value = "Results page you want to retrieve (0..N)"),
        @ApiImplicitParam(name = "size", dataType = "string", paramType = "query", value = "Number of records per page."),
        @ApiImplicitParam(name = "sort", allowMultiple = true, dataType = "string", paramType = "query", value = "Sorting criteria in the format: property(,asc|desc). "
                + "Default sort order is ascending. " + "Multiple sort criteria are supported.") })
    public DtsApiResponse listProcessDeploy(
            @ApiIgnore @RequestParam MultiValueMap<String, String> requestParams,@ApiIgnore Pageable pageable) {
        long start = System.currentTimeMillis();
        try {
            ObjectDataRes<JpmProcessDeployDto> resObj = processsDeployService.search(requestParams, pageable);
            return this.successHandler.handlerSuccess(resObj, start);
        } catch (Exception ex) {
            return this.errorHandler.handlerException(ex, start);
        }
    }
	
	@DeleteMapping(AppApiConstant.API_ADMIN_PROCESS_DEPLOY)
	@ApiOperation("Api provides delete processsDeploy on systems")
	@ApiResponses(value = {
	            @ApiResponse(code = 200 , message = "success"),
	            @ApiResponse(code = 500 , message = "Internal Server Error"),
	            @ApiResponse(code = 401 , message = "Unauthorized"),
	            @ApiResponse(code = 402 , message = "Forbidden"),
	            @ApiResponse(code = 4021904 , message = "Error process delete processsDeploy"),
	            @ApiResponse(code = 4021906 , message = "Error processsDeploy not found")})
    public DtsApiResponse deleteProcessDeploy(
            @ApiParam(name = "processsDeployId", value = "Delete processsDeploy on system by id", example = "1") @RequestParam("processsDeployId") Long processsDeployId) {
        long start = System.currentTimeMillis();
        try {
            processsDeployService.delete(processsDeployId);
            return this.successHandler.handlerSuccess(null, start);
        } catch (Exception ex) {
            return this.errorHandler.handlerException(ex, start);
        }
    }
	
	@GetMapping(AppApiConstant.API_ADMIN_PROCESS_DEPLOY + "/{processsDeployId}") 
	@ApiOperation("Api provides processsDeploy information detail on systems")
	@ApiResponses(value = {
	            @ApiResponse(code = 200 , message = "success"),
	            @ApiResponse(code = 500 , message = "Internal Server Error"),
	            @ApiResponse(code = 401 , message = "Unauthorized"),
	            @ApiResponse(code = 402 , message = "Forbidden"),
	            @ApiResponse(code = 4021906 , message = "Error processsDeploy not found")})
    public DtsApiResponse detailProcessDeploy(
            @ApiParam(name = "processsDeployId", value = "Get processsDeploy information detail on system by id",example = "1") @PathVariable("processsDeployId") Long processsDeployId) {
        long start = System.currentTimeMillis();
        try {
            JpmProcessDeployDto resObj = processsDeployService.detail(processsDeployId);
            return this.successHandler.handlerSuccess(resObj, start);
        } catch (Exception ex) {
            return this.errorHandler.handlerException(ex, start);
        }
    }
	
	@GetMapping(AppApiConstant.API_ADMIN_PROCESS_DEPLOY + "/export/{processsDeployId}") 
	@ApiOperation("Api provides processsDeploy information detail on systems")
	@ApiResponses(value = {
	        @ApiResponse(code = 200 , message = "success"),
	        @ApiResponse(code = 500 , message = "Internal Server Error"),
	        @ApiResponse(code = 401 , message = "Unauthorized"),
	        @ApiResponse(code = 402 , message = "Forbidden"),
	        @ApiResponse(code = 4021906 , message = "Error processsDeploy not found")})
	public DtsApiResponse exportProcessDeploy(
	        @ApiParam(name = "processsDeployId", value = "Get processsDeploy information detail on system by id",example = "1") @PathVariable("processsDeployId") Long processsDeployId) {
	    long start = System.currentTimeMillis();
	    try {
	        JpmProcessImportExportDto resObj = processsDeployService.exportProcessDeploy(processsDeployId);
	        String jsonObj = CommonJsonUtil.convertObjectToJSON(resObj);
	        String resString = CommonBase64Util.encode(jsonObj);
	        return this.successHandler.handlerSuccess(resString, start);
	    } catch (Exception ex) {
	        return this.errorHandler.handlerException(ex, start);
	    }
	}
	
}
