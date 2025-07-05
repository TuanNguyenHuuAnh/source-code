/*******************************************************************************
 * Class        ：FormRest
 * Created date ：2020/12/23
 * Lasted date  ：2020/12/23
 * Author       ：taitt
 * Change log   ：2020/12/23：01-00 taitt create a new
 ******************************************************************************/
package vn.com.unit.ep2p.rest.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.util.MultiValueMap;
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
import vn.com.unit.ep2p.core.dto.EfoFormRegisterRes;
import vn.com.unit.ep2p.core.efo.dto.EfoFormDto;
import vn.com.unit.ep2p.dto.req.FormRegisterReq;
import vn.com.unit.ep2p.dto.req.FormUpdateReq;
import vn.com.unit.ep2p.dto.res.FormInfoRes;
import vn.com.unit.ep2p.dto.res.FormRegisterResultRes;
import vn.com.unit.ep2p.rest.AbstractRest;
import vn.com.unit.ep2p.service.FormService;

/**
 * FormRest
 * 
 * @version 01-00
 * @since 01-00
 * @author taitt
 */
@RestController
@RequestMapping(AppApiConstant.API_V1 + AppApiConstant.API_ADMIN)
@Api(tags = { AppApiConstant.API_ADMIN_FORM_DESCR })
public class FormRest extends AbstractRest {

    @Autowired
    FormService formService;
    
    @GetMapping(AppApiConstant.API_ADMIN_FORM)
    @ApiOperation("List of form")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "success"), @ApiResponse(code = 500, message = "Internal Server Error"),
            @ApiResponse(code = 401, message = "Unauthorized"), @ApiResponse(code = 402, message = "Forbidden"),
            @ApiResponse(code = 402801, message = "Error process list form") })
    @ApiImplicitParams({
            @ApiImplicitParam(name = "keySearch", value = "keySearch", required = false, dataTypeClass = String.class, paramType = "query"),
            @ApiImplicitParam(name = "companyId", value = "1", dataTypeClass = String.class, paramType = "query"),
            @ApiImplicitParam(name = "categoryId", value = "1", dataTypeClass = String.class, paramType = "query"),
            
            @ApiImplicitParam(name = "page", dataType = "string", paramType = "query", value = "Results page you want to retrieve (0..N)"),
            @ApiImplicitParam(name = "size", dataType = "string", paramType = "query", value = "Number of records per page."),
            @ApiImplicitParam(name = "sort", allowMultiple = true, dataType = "string", paramType = "query", value = "Sorting criteria in the format: property(,asc|desc). "
                    + "Default sort order is ascending. " + "Multiple sort criteria are supported.") 
     })
    public DtsApiResponse listForm(@ApiIgnore @RequestParam MultiValueMap<String, String> requestParams,@ApiIgnore Pageable pageable) {
        long start = System.currentTimeMillis();
        try {
            ObjectDataRes<EfoFormDto> resObj = formService.search(requestParams,pageable);
            return this.successHandler.handlerSuccess(resObj, start);
        } catch (Exception ex) {
            return this.errorHandler.handlerException(ex, start);
        }
    }
    
    @GetMapping(AppApiConstant.API_ADMIN_FORM + "/{formId}")
    @ApiOperation("Detail of form")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "success"), @ApiResponse(code = 500, message = "Internal Server Error"),
            @ApiResponse(code = 401, message = "Unauthorized"), @ApiResponse(code = 402, message = "Forbidden"),
            @ApiResponse(code = 402805, message = "Error process get account information detail"),
            @ApiResponse(code = 402806, message = "Error account not found") })
    public DtsApiResponse detailForm(
            @ApiParam(name = "formId", value = "Get form information detail on system by id", example = "123") @PathVariable("formId") Long formId) {
        long start = System.currentTimeMillis();
        try {
            FormInfoRes resObj = formService.getFormInfoResById(formId);
            return this.successHandler.handlerSuccess(resObj, start);
        } catch (Exception ex) {
            return this.errorHandler.handlerException(ex, start);
        }
    }
    
    @PutMapping(AppApiConstant.API_ADMIN_FORM)
    @ApiOperation("Update form")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "success"), @ApiResponse(code = 500, message = "Internal Server Error"),
            @ApiResponse(code = 401, message = "Unauthorized"), @ApiResponse(code = 402, message = "Forbidden"),
            @ApiResponse(code = 402803, message = "Error process update info form"),
            @ApiResponse(code = 402806, message = "Error form not found") })
    public DtsApiResponse updateForm(
            @ApiParam(name = "body", value = "Form information to update") @RequestBody FormUpdateReq formUpdateReq) {
        long start = System.currentTimeMillis();
        try {
            formService.update(formUpdateReq);
            return this.successHandler.handlerSuccess(null, start);
        } catch (Exception ex) {
            return this.errorHandler.handlerException(ex, start);
        }
    }
    
    @GetMapping( AppApiConstant.API_ADMIN_FORM_REGISTER)
    @ApiOperation("List ozr report eform")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "success"), @ApiResponse(code = 500, message = "Internal Server Error"),
            @ApiResponse(code = 401, message = "Unauthorized"), @ApiResponse(code = 402, message = "Forbidden"),
            @ApiResponse(code = 402803, message = "Error process update info form"),
            @ApiResponse(code = 402806, message = "Error form not found") })
    @ApiImplicitParams({
        @ApiImplicitParam(name = "keySearch", value = "keySearch", required = false, dataTypeClass = String.class, paramType = "query"),
        @ApiImplicitParam(name = "companyId", value = "1", dataTypeClass = String.class, paramType = "query"),
        @ApiImplicitParam(name = "categoryId", value = "1", dataTypeClass = String.class, paramType = "query"),
        
        @ApiImplicitParam(name = "page", dataType = "string", paramType = "query", value = "Results page you want to retrieve (0..N)"),
        @ApiImplicitParam(name = "size", dataType = "string", paramType = "query", value = "Number of records per page."),
        @ApiImplicitParam(name = "sort", allowMultiple = true, dataType = "string", paramType = "query", value = "Sorting criteria in the format: property(,asc|desc). "
                + "Default sort order is ascending. " + "Multiple sort criteria are supported.") 
 })
    public DtsApiResponse getRegisterFormList(
            @ApiIgnore @RequestParam MultiValueMap<String, String> requestParams) {
        long start = System.currentTimeMillis();
        try {
            EfoFormRegisterRes resDto = formService.getFormRegisterDtoByCondition(requestParams);
            return this.successHandler.handlerSuccess(resDto, start);
        } catch (Exception ex) {
            return this.errorHandler.handlerException(ex, start);
        }
    }
    
    @PostMapping( AppApiConstant.API_ADMIN_FORM_REGISTER)
    @ApiOperation("Register form")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "success"), @ApiResponse(code = 500, message = "Internal Server Error"),
            @ApiResponse(code = 401, message = "Unauthorized"), @ApiResponse(code = 402, message = "Forbidden"),
            @ApiResponse(code = 402802, message = "Error process add account") })
    public DtsApiResponse createRegisterFormList(
            @ApiParam(name = "body", value = "Register form") @RequestBody FormRegisterReq formRegisterReq) {
        long start = System.currentTimeMillis();
        try {
            FormRegisterResultRes resDto = formService.registerForm(formRegisterReq);
            return this.successHandler.handlerSuccess(resDto, start);
        } catch (Exception ex) {
            return this.errorHandler.handlerException(ex, start);
        }
    }
}
