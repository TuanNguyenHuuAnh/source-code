/*******************************************************************************
 * Class        ：ComboboxRest
 * Created date ：2021/01/28
 * Lasted date  ：2021/01/28
 * Author       ：tantm
 * Change log   ：2021/01/28：01-00 tantm create a new
 ******************************************************************************/
package vn.com.unit.ep2p.rest.app;

import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import springfox.documentation.annotations.ApiIgnore;
import vn.com.unit.common.dto.MultiSelectDto;
import vn.com.unit.common.dto.Select2Dto;
import vn.com.unit.core.dto.JcaDatatableDefaultConfigDto;
import vn.com.unit.core.res.ObjectDataRes;
import vn.com.unit.core.service.JcaDatatableConfigService;
import vn.com.unit.dts.web.rest.common.DtsApiResponse;
import vn.com.unit.ep2p.core.constant.AppApiConstant;
import vn.com.unit.ep2p.rest.AbstractRest;
import vn.com.unit.ep2p.service.ComboboxService;
import vn.com.unit.ep2p.utils.LangugeUtil;

/**
 * ComboboxRest
 * 
 * @version 01-00
 * @since 01-00
 * @author tantm
 */
@RestController
@RequestMapping(AppApiConstant.API_V1 + AppApiConstant.API_APP)
@Api(tags = { AppApiConstant.API_APP_COMBOBOX_DESCR })
public class ComboboxRest extends AbstractRest {

    @Autowired
    private ComboboxService comboboxService;
    @Autowired
    private JcaDatatableConfigService jcaDatatableConfigService;

    @GetMapping(AppApiConstant.API_APP_COMBOBOX + "/constant")
    @ApiOperation("List of constant")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "Success"), @ApiResponse(code = 401, message = "Unauthorized"),
            @ApiResponse(code = 402, message = "Forbidden"), @ApiResponse(code = 500, message = "Internal server error"), })
    @ApiImplicitParams({
            @ApiImplicitParam(name = "keySearch", value = "keySearch", required = false, dataTypeClass = String.class, paramType = "query"),
            @ApiImplicitParam(name = "groupCode", value = "groupCode", required = true, dataTypeClass = String.class, paramType = "query"),
            @ApiImplicitParam(name = "kind", value = "kind", required = true, dataTypeClass = String.class, paramType = "query"),

            @ApiImplicitParam(name = "page", dataType = "string", paramType = "query", value = "Results page you want to retrieve (0..N)"),
            @ApiImplicitParam(name = "size", dataType = "string", paramType = "query", value = "Number of records per page."),
            @ApiImplicitParam(name = "sort", allowMultiple = true, dataType = "string", paramType = "query", value = "Sorting criteria in the format: property(,asc|desc). "
                    + "Default sort order is ascending. " + "Multiple sort criteria are supported.") })
    public DtsApiResponse getCmbConstant(@ApiIgnore @RequestParam MultiValueMap<String, String> requestParams,
            @ApiIgnore Pageable pageable) {
        long start = System.currentTimeMillis();
        try {
            ObjectDataRes<Select2Dto> resObj = comboboxService.getListSelect2DtoConstantDisplay(requestParams, pageable);
            return this.successHandler.handlerSuccess(resObj, start);
        } catch (Exception ex) {
            return this.errorHandler.handlerException(ex, start);
        }
    }

    @GetMapping(AppApiConstant.API_APP_COMBOBOX + "/form")
    @ApiOperation("List of form")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "Success"), @ApiResponse(code = 401, message = "Unauthorized"),
            @ApiResponse(code = 402, message = "Forbidden"), @ApiResponse(code = 500, message = "Internal server error"), })
    @ApiImplicitParams({
            @ApiImplicitParam(name = "keySearch", value = "keySearch", required = false, dataTypeClass = String.class, paramType = "query"),
            @ApiImplicitParam(name = "categoryId", value = "categoryId", required = false, dataTypeClass = String.class, paramType = "query"),
            
            @ApiImplicitParam(name = "page", dataType = "string", paramType = "query", value = "Results page you want to retrieve (0..N)"),
            @ApiImplicitParam(name = "size", dataType = "string", paramType = "query", value = "Number of records per page."),
            @ApiImplicitParam(name = "sort", allowMultiple = true, dataType = "string", paramType = "query", value = "Sorting criteria in the format: property(,asc|desc). "
                    + "Default sort order is ascending. " + "Multiple sort criteria are supported.") })
    public DtsApiResponse getCmbForm(@ApiIgnore @RequestParam MultiValueMap<String, String> requestParams, @ApiIgnore Pageable pageable) {
        long start = System.currentTimeMillis();
        try {
            ObjectDataRes<Select2Dto> resObj = comboboxService.getListSelect2DtoForm(requestParams, pageable);
            return this.successHandler.handlerSuccess(resObj, start);
        } catch (Exception ex) {
            return this.errorHandler.handlerException(ex, start);
        }
    }

    @GetMapping(AppApiConstant.API_APP_COMBOBOX + "/org")
    @ApiOperation("List org of account")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "Success"), @ApiResponse(code = 401, message = "Unauthorized"),
            @ApiResponse(code = 402, message = "Forbidden"), @ApiResponse(code = 500, message = "Internal server error"), })

    @ApiImplicitParams({
            @ApiImplicitParam(name = "keySearch", value = "keySearch", required = false, dataTypeClass = String.class, paramType = "query"),

            @ApiImplicitParam(name = "page", dataType = "string", paramType = "query", value = "Results page you want to retrieve (0..N)"),
            @ApiImplicitParam(name = "size", dataType = "string", paramType = "query", value = "Number of records per page."),
            @ApiImplicitParam(name = "sort", allowMultiple = true, dataType = "string", paramType = "query", value = "Sorting criteria in the format: property(,asc|desc). "
                    + "Default sort order is ascending. " + "Multiple sort criteria are supported.") })
    public DtsApiResponse getCmbOrg(@ApiIgnore @RequestParam MultiValueMap<String, String> requestParams, @ApiIgnore Pageable pageable) {
        long start = System.currentTimeMillis();
        try {
            ObjectDataRes<Select2Dto> resObj = comboboxService.getListSelect2DtoOrg(requestParams, pageable);
            return this.successHandler.handlerSuccess(resObj, start);
        } catch (Exception ex) {
            return this.errorHandler.handlerException(ex, start);
        }
    }

    @GetMapping(AppApiConstant.API_APP_COMBOBOX + "/status")
    @ApiOperation("List of status")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "Success"), @ApiResponse(code = 401, message = "Unauthorized"),
            @ApiResponse(code = 402, message = "Forbidden"), @ApiResponse(code = 500, message = "Internal server error"), })

    @ApiImplicitParams({
            @ApiImplicitParam(name = "keySearch", value = "keySearch", required = false, dataTypeClass = String.class, paramType = "query"),
            @ApiImplicitParam(name = "processDeployId", value = "1",  dataTypeClass = String.class, paramType = "query"),
            @ApiImplicitParam(name = "formId", value = "1",  dataTypeClass = String.class, paramType = "query"),
            
            @ApiImplicitParam(name = "page", dataType = "string", paramType = "query", value = "Results page you want to retrieve (0..N)"),
            @ApiImplicitParam(name = "size", dataType = "string", paramType = "query", value = "Number of records per page."),
            @ApiImplicitParam(name = "sort", allowMultiple = true, dataType = "string", paramType = "query", value = "Sorting criteria in the format: property(,asc|desc). "
                    + "Default sort order is ascending. " + "Multiple sort criteria are supported.") })
    public DtsApiResponse getCmbStatus(@ApiIgnore @RequestParam MultiValueMap<String, String> requestParams, @ApiIgnore Pageable pageable,
            @ApiIgnore Long processDeployId,
            @ApiIgnore Long formId) {
        long start = System.currentTimeMillis();
        try {
            ObjectDataRes<Select2Dto> resObj = comboboxService.getListSelect2DtoStatus(processDeployId, formId, requestParams, pageable);
            return this.successHandler.handlerSuccess(resObj, start);
        } catch (Exception ex) {
            return this.errorHandler.handlerException(ex, start);
        }
    }

    @GetMapping(AppApiConstant.API_APP_COMBOBOX + "/process")
    @ApiOperation("List of process")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "Success"), @ApiResponse(code = 401, message = "Unauthorized"),
            @ApiResponse(code = 402, message = "Forbidden"), @ApiResponse(code = 500, message = "Internal server error"), })

    @ApiImplicitParams({
            @ApiImplicitParam(name = "keySearch", value = "keySearch", required = false, dataTypeClass = String.class, paramType = "query"),

            @ApiImplicitParam(name = "page", dataType = "string", paramType = "query", value = "Results page you want to retrieve (0..N)"),
            @ApiImplicitParam(name = "size", dataType = "string", paramType = "query", value = "Number of records per page."),
            @ApiImplicitParam(name = "sort", allowMultiple = true, dataType = "string", paramType = "query", value = "Sorting criteria in the format: property(,asc|desc). "
                    + "Default sort order is ascending. " + "Multiple sort criteria are supported.") })
    public DtsApiResponse getCmbProcess(@ApiIgnore Pageable pageable,
            @ApiIgnore @RequestParam MultiValueMap<String, String> requestParams) {
        long start = System.currentTimeMillis();
        try {
            ObjectDataRes<Select2Dto> resObj = comboboxService.getListSelect2DtoProcess(requestParams, pageable);
            return this.successHandler.handlerSuccess(resObj, start);
        } catch (Exception ex) {
            return this.errorHandler.handlerException(ex, start);
        }
    }
    
    @GetMapping(AppApiConstant.API_APP_COMBOBOX + "/category")
    @ApiOperation("List of category")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "Success"), @ApiResponse(code = 401, message = "Unauthorized"),
            @ApiResponse(code = 402, message = "Forbidden"), @ApiResponse(code = 500, message = "Internal server error"), })
    @ApiImplicitParams({
            @ApiImplicitParam(name = "keySearch", value = "keySearch", required = false, dataTypeClass = String.class, paramType = "query"),

            @ApiImplicitParam(name = "page", dataType = "string", paramType = "query", value = "Results page you want to retrieve (0..N)"),
            @ApiImplicitParam(name = "size", dataType = "string", paramType = "query", value = "Number of records per page."),
            @ApiImplicitParam(name = "sort", allowMultiple = true, dataType = "string", paramType = "query", value = "Sorting criteria in the format: property(,asc|desc). "
                    + "Default sort order is ascending. " + "Multiple sort criteria are supported.") })
    public DtsApiResponse getCmbCategory(@ApiIgnore @RequestParam MultiValueMap<String, String> requestParams, @ApiIgnore Pageable pageable) {
        long start = System.currentTimeMillis();
        try {
            ObjectDataRes<Select2Dto> resObj = comboboxService.getListSelect2DtoCategory(requestParams, pageable);
            return this.successHandler.handlerSuccess(resObj, start);
        } catch (Exception ex) {
            return this.errorHandler.handlerException(ex, start);
        }
    }
	
	@GetMapping(AppApiConstant.API_APP_COMBOBOX + "/team")
    @ApiOperation("List of team")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "Success"), @ApiResponse(code = 401, message = "Unauthorized"),
            @ApiResponse(code = 402, message = "Forbidden"), @ApiResponse(code = 500, message = "Internal server error"), })
    
    @ApiImplicitParams({
        @ApiImplicitParam(name = "keySearch", value = "keySearch", required = false, dataTypeClass = String.class, paramType = "query"),
        @ApiImplicitParam(name = "companyId", value = "companyId", required = false, dataTypeClass = String.class, paramType = "query"),
        
        @ApiImplicitParam(name = "page", dataType = "string", paramType = "query", value = "Results page you want to retrieve (0..N)"),
        @ApiImplicitParam(name = "size", dataType = "string", paramType = "query", value = "Number of records per page."),
        @ApiImplicitParam(name = "sort", allowMultiple = true, dataType = "string", paramType = "query", value = "Sorting criteria in the format: property(,asc|desc). "
                + "Default sort order is ascending. " + "Multiple sort criteria are supported.") })
    public DtsApiResponse getCmbTeam(@ApiIgnore Pageable pageable,
            @ApiIgnore @RequestParam MultiValueMap<String, String> requestParams) {
        long start = System.currentTimeMillis();
        try {
            ObjectDataRes<MultiSelectDto> resObj = comboboxService.getListMultiSelectDtoTeam(requestParams, pageable);
            return this.successHandler.handlerSuccess(resObj.getDatas(), start);
        } catch (Exception ex) {
            return this.errorHandler.handlerException(ex, start);
        }
    }
	 @GetMapping(AppApiConstant.API_APP_COMBOBOX + "/table-config")
	    public DtsApiResponse getTableConfig(String functionCode, HttpServletRequest request) {
	        long start = System.currentTimeMillis();
	        try {
	            final String FUNCTION_TYPE = "FE";
	            Locale locale = LangugeUtil.getLanguageFromHeader(request);

	            List<JcaDatatableDefaultConfigDto> resObj = jcaDatatableConfigService
	                    .getListJcaDatatableDefaultConfigDto(functionCode, locale.toLanguageTag());
	            return this.successHandler.handlerSuccess(resObj, start);
	        } catch (Exception ex) {
	            return this.errorHandler.handlerException(ex, start);
	        }
	    }

}
