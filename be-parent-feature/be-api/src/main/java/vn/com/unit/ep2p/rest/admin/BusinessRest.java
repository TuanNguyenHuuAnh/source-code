/*******************************************************************************
 * Class        ：BusinessRest
 * Created date ：2020/12/07
 * Lasted date  ：2020/12/07
 * Author       ：KhuongTH
 * Change log   ：2020/12/07：01-00 KhuongTH create a new
 ******************************************************************************/
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
import vn.com.unit.ep2p.dto.req.BusinessAddReq;
import vn.com.unit.ep2p.dto.req.BusinessUpdateReq;
import vn.com.unit.ep2p.rest.AbstractRest;
import vn.com.unit.ep2p.service.BusinessService;
import vn.com.unit.workflow.dto.JpmBusinessDto;

/**
 * <p> BusinessRest </p>
 * 
 * @version 01-00
 * @since 01-00
 * @author KhuongTH
 */
@RestController
@RequestMapping(AppApiConstant.API_V1 + AppApiConstant.API_ADMIN)
@Api(tags = {AppApiConstant.API_ADMIN_BUSINESS_DESCR})
public class BusinessRest extends AbstractRest{
	
	@Autowired
	private BusinessService businessService;
	
	@GetMapping(AppApiConstant.API_ADMIN_BUSINESS)
	@ApiOperation("Api provides a list business on systems")
	@ApiResponses(value = {
	            @ApiResponse(code = 200 , message = "success"),
	            @ApiResponse(code = 500 , message = "Internal Server Error"),
	            @ApiResponse(code = 401 , message = "Unauthorized"),
	            @ApiResponse(code = 402 , message = "Forbidden"),
	            @ApiResponse(code = 4021300 , message = "Error process list business")})
    @ApiImplicitParams({
            @ApiImplicitParam(name = "keySearch", value = "search by ProcessName or ProcessCode ", required = false, dataTypeClass = String.class, paramType = "query"),
            @ApiImplicitParam(name = "companyId", value = "id of company", dataTypeClass = Long.class, paramType = "query"),
            @ApiImplicitParam(name = "processType", value = "type in (BPMN, FREE, INTEGRATE)", dataTypeClass = String.class, paramType = "query"),

            @ApiImplicitParam(name = "page", dataType = "string", paramType = "query", value = "Results page you want to retrieve (0..N)"),
            @ApiImplicitParam(name = "size", dataType = "string", paramType = "query", value = "Number of records per page."),
            @ApiImplicitParam(name = "sort", allowMultiple = true, dataType = "string", paramType = "query", value = "Sorting criteria in the format: property(,asc|desc). "
                    + "Default sort order is ascending. " + "Multiple sort criteria are supported.") })
    public DtsApiResponse listBusiness(@ApiIgnore @RequestParam MultiValueMap<String, String> requestParams,@ApiIgnore Pageable pageable) {
        long start = System.currentTimeMillis();
        try {
            ObjectDataRes<JpmBusinessDto> resObj =  businessService.search(requestParams, pageable);
            return this.successHandler.handlerSuccess(resObj, start);
        } catch (Exception ex) {
            return this.errorHandler.handlerException(ex, start);
        }
    }
	
	@PostMapping(AppApiConstant.API_ADMIN_BUSINESS)
	@ApiOperation("Api provides add new business on systems")
	@ApiResponses(value = {
	            @ApiResponse(code = 200 , message = "success"),
	            @ApiResponse(code = 500 , message = "Internal Server Error"),
	            @ApiResponse(code = 401 , message = "Unauthorized"),
	            @ApiResponse(code = 402 , message = "Forbidden"),
	            @ApiResponse(code = 4021301 , message = "Error process add business"),
	            @ApiResponse(code = 4021306 , message = "Error business already exist")
	            })
    public DtsApiResponse addBusiness(
            @ApiParam(name = "body", value = "Business information to add new") 
            @RequestBody BusinessAddReq reqBusinessAddDto) {
        long start = System.currentTimeMillis();
        try {
            JpmBusinessDto resObj = businessService.create(reqBusinessAddDto);
            return this.successHandler.handlerSuccess(resObj, start);
        } catch (Exception ex) {
            return this.errorHandler.handlerException(ex, start);
        }
    }
	
	@PutMapping(AppApiConstant.API_ADMIN_BUSINESS)
	@ApiOperation("Api provides update information business on systems")
	@ApiResponses(value = {
	            @ApiResponse(code = 200 , message = "success"),
	            @ApiResponse(code = 500 , message = "Internal Server Error"),
	            @ApiResponse(code = 401 , message = "Unauthorized"),
	            @ApiResponse(code = 402 , message = "Forbidden"),
	            @ApiResponse(code = 4021302 , message = "Error process update info business"),
	            @ApiResponse(code = 4021305 , message = "Error business not found")})
    public DtsApiResponse updateBusiness(
            @ApiParam(name = "body", value = "Business information to update") 
            @RequestBody BusinessUpdateReq reqBusinessUpdateDto) {
        long start = System.currentTimeMillis();
        try {
            businessService.update(reqBusinessUpdateDto);
            return this.successHandler.handlerSuccess(null, start);
        } catch (Exception ex) {
            return this.errorHandler.handlerException(ex, start);
        }
    }
	
	@DeleteMapping(AppApiConstant.API_ADMIN_BUSINESS)
	@ApiOperation("Api provides delete business on systems")
	@ApiResponses(value = {
	            @ApiResponse(code = 200 , message = "success"),
	            @ApiResponse(code = 500 , message = "Internal Server Error"),
	            @ApiResponse(code = 401 , message = "Unauthorized"),
	            @ApiResponse(code = 402 , message = "Forbidden"),
	            @ApiResponse(code = 4021303 , message = "Error process delete business"),
	            @ApiResponse(code = 4021305 , message = "Error business not found")})
    public DtsApiResponse deleteBusiness(
            @ApiParam(name = "businessId", value = "Delete business on system by id", example = "123") 
            @RequestParam("businessId") Long businessId) {
        long start = System.currentTimeMillis();
        try {
            businessService.delete(businessId);
            return this.successHandler.handlerSuccess(null, start);
        } catch (Exception ex) {
            return this.errorHandler.handlerException(ex, start);
        }
    }
	
	@GetMapping(AppApiConstant.API_ADMIN_BUSINESS + "/{businessId}") 
	@ApiOperation("Api provides business information detail on systems")
	@ApiResponses(value = {
	            @ApiResponse(code = 200 , message = "success"),
	            @ApiResponse(code = 500 , message = "Internal Server Error"),
	            @ApiResponse(code = 401 , message = "Unauthorized"),
	            @ApiResponse(code = 402 , message = "Forbidden"),
	            @ApiResponse(code = 4021304 , message = "Error process get business information detail"),
	            @ApiResponse(code = 4021305 , message = "Error business not found")})
    public DtsApiResponse detailBusiness(
            @ApiParam(name = "businessId", value = "Get business information detail on system by id", example = "123") 
            @PathVariable("businessId") Long businessId) {
        long start = System.currentTimeMillis();
        try {
            JpmBusinessDto resObj = businessService.detail(businessId);
            return this.successHandler.handlerSuccess(resObj, start);
        } catch (Exception ex) {
            return this.errorHandler.handlerException(ex, start);
        }
    }
	
}
