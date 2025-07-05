/*******************************************************************************
 * Class        ：ConstantRest
 * Created date ：2020/12/01
 * Lasted date  ：2020/12/01
 * Author       ：tantm
 * Change log   ：2020/12/01：01-00 tantm create a new
 ******************************************************************************/
package vn.com.unit.ep2p.rest.admin;

import java.util.List;
import java.util.Locale;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

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
import vn.com.unit.core.dto.JcaConstantDto;
import vn.com.unit.core.res.ObjectDataRes;
import vn.com.unit.dts.web.rest.common.DtsApiResponse;
import vn.com.unit.ep2p.core.constant.AppApiConstant;
import vn.com.unit.ep2p.core.res.dto.EnumsParamSearchRes;
import vn.com.unit.ep2p.dto.req.ConstantAddReq;
import vn.com.unit.ep2p.dto.res.ConstantInfoRes;
import vn.com.unit.ep2p.rest.AbstractRest;
import vn.com.unit.ep2p.service.ConstantService;

/**
 * 
 * ConstantRest
 * 
 * @version 01-00
 * @since 01-00
 * @author tantm
 */

@RestController
@RequestMapping(AppApiConstant.API_V1 + AppApiConstant.API_ADMIN)
@Api(tags = { AppApiConstant.API_ADMIN_CONSTANT_DESCR })
public class ConstantRest extends AbstractRest {

    @Autowired
    private ConstantService constantService;

    @GetMapping(AppApiConstant.API_ADMIN_CONSTANT)
    @ApiOperation("List of constants")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "success"), @ApiResponse(code = 500, message = "Internal Server Error"),
            @ApiResponse(code = 401, message = "Unauthorized"), @ApiResponse(code = 402, message = "Forbidden"),
            @ApiResponse(code = 4023201, message = "Error process list constant") })
    @ApiImplicitParams({
            @ApiImplicitParam(name = "keySearch", value = "keySearch", required = false, dataTypeClass = String.class, paramType = "query"),
            @ApiImplicitParam(name = "companyId", value = "1", dataTypeClass = String.class, paramType = "query"),
            @ApiImplicitParam(name = "active", value = "true", dataType = "boolean", dataTypeClass = Boolean.class, paramType = "query"),
            @ApiImplicitParam(name = "multipleSeachEnums", allowMultiple = true, dataType = "string", paramType = "query", value = "Column CODE, TEXT"),
            
            @ApiImplicitParam(name = "page", dataType = "string", paramType = "query", value = "Results page you want to retrieve (0..N)"),
            @ApiImplicitParam(name = "size", dataType = "string", paramType = "query", value = "Number of records per page."),
            @ApiImplicitParam(name = "sort", allowMultiple = true, dataType = "string", paramType = "query", value = "Sorting criteria in the format: property(,asc|desc). "
                    + "Default sort order is ascending. " + "Multiple sort criteria are supported.") })
    public DtsApiResponse listConstant(@ApiIgnore @RequestParam MultiValueMap<String, String> requestParams,
            @ApiIgnore Pageable pageable) {
        long start = System.currentTimeMillis();
        try {
            /** example structure return object data have extends to add properties */
            // ConstantListObjectRes resObj = constantService.search(requestParams,pageable);
            /** END */
            ObjectDataRes<JcaConstantDto> resObj = constantService.search(requestParams, pageable);
            return this.successHandler.handlerSuccess(resObj, start);
        } catch (Exception ex) {
            return this.errorHandler.handlerException(ex, start);
        }
    }

    @PostMapping(AppApiConstant.API_ADMIN_CONSTANT)
    @ApiOperation("Create constant")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "success"), @ApiResponse(code = 500, message = "Internal Server Error"),
            @ApiResponse(code = 401, message = "Unauthorized"), @ApiResponse(code = 402, message = "Forbidden"),
            @ApiResponse(code = 4023202, message = "Error process add constant") })
    public DtsApiResponse addConstant(
            @ApiParam(name = "body", value = "Constant information to add new") @RequestBody ConstantAddReq constantAddDtoReq) {
        long start = System.currentTimeMillis();
        try {
        	List<JcaConstantDto> resObj = constantService.create(constantAddDtoReq);
            return this.successHandler.handlerSuccess(resObj, start);
        } catch (Exception ex) {
            return this.errorHandler.handlerException(ex, start);
        }
    }

    @PutMapping(AppApiConstant.API_ADMIN_CONSTANT)
    @ApiOperation("Update constant")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "success"), @ApiResponse(code = 500, message = "Internal Server Error"),
            @ApiResponse(code = 401, message = "Unauthorized"), @ApiResponse(code = 402, message = "Forbidden"),
            @ApiResponse(code = 4023203, message = "Error process update info constant"),
            @ApiResponse(code = 4023204, message = "Error constant not found") })
    public DtsApiResponse updateConstant(
            @ApiParam(name = "body", value = "Constant information to update") @RequestBody ConstantAddReq constantUpdateDtoReq) {
        long start = System.currentTimeMillis();
        try {
            constantService.update(constantUpdateDtoReq);
            return this.successHandler.handlerSuccess(null, start);
        } catch (Exception ex) {
            return this.errorHandler.handlerException(ex, start);
        }
    }

//    @DeleteMapping(AppApiConstant.API_ADMIN_CONSTANT)
//    @ApiOperation("Delete constant")
//    @ApiResponses(value = { @ApiResponse(code = 200, message = "success"), @ApiResponse(code = 500, message = "Internal Server Error"),
//            @ApiResponse(code = 401, message = "Unauthorized"), @ApiResponse(code = 402, message = "Forbidden"),
//            @ApiResponse(code = 4023205, message = "Error process delete constant"),
//            @ApiResponse(code = 4023204, message = "Error constant not found") })
//    public DtsApiResponse deleteConstant(
//            @ApiParam(name = "id", value = "Delete constant on system by id", example = "123") @RequestParam("id") Long id) {
//        long start = System.currentTimeMillis();
//        try {
//            constantService.delete(id);
//            return this.successHandler.handlerSuccess(null, start);
//        } catch (Exception ex) {
//            return this.errorHandler.handlerException(ex, start);
//        }
//    }
//
//    @GetMapping(AppApiConstant.API_ADMIN_CONSTANT + "/{id}")
//    @ApiOperation("Detail of constant")
//    @ApiResponses(value = { @ApiResponse(code = 200, message = "success"), @ApiResponse(code = 500, message = "Internal Server Error"),
//            @ApiResponse(code = 401, message = "Unauthorized"), @ApiResponse(code = 402, message = "Forbidden"),
//            @ApiResponse(code = 4023206, message = "Error process get constant information detail"),
//            @ApiResponse(code = 4023204, message = "Error constant not found") })
//    public DtsApiResponse detailConstant(
//            @ApiParam(name = "id", value = "Get constant information detail on system by id", example = "123") @PathVariable("id") Long id) {
//        long start = System.currentTimeMillis();
//        try {
//            ConstantInfoRes resObj = constantService.getConstantInfoResById(id);
//            return this.successHandler.handlerSuccess(resObj, start);
//        } catch (Exception ex) {
//            return this.errorHandler.handlerException(ex, start);
//        }
//    }
    
    @GetMapping(AppApiConstant.API_ADMIN_CONSTANT + "/{groupCode}/{kind}/{code}")
    @ApiOperation("Detail of constant")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "success"),
            @ApiResponse(code = 500, message = "Internal Server Error"),
            @ApiResponse(code = 401, message = "Unauthorized"), @ApiResponse(code = 402, message = "Forbidden"),
            @ApiResponse(code = 4023206, message = "Error process get constant information detail"),
            @ApiResponse(code = 4023204, message = "Error constant not found") })
    public DtsApiResponse detailConstant(
            @ApiParam(name = "groupCode", value = "Get constant information detail on system by group code", example = "123") @PathVariable("groupCode") String groupCode,
            @ApiParam(name = "kind", value = "Get constant information detail on system by kind", example = "123") @PathVariable("kind") String kind,
            @ApiParam(name = "code", value = "Get constant information detail on system by code", example = "123") @PathVariable("code") String code,
            HttpServletRequest request) {
        long start = System.currentTimeMillis();
        try {

            Locale locale = new Locale(Optional.ofNullable(request.getHeader("Accept-Language")).orElse("en"));

            ConstantInfoRes resObj = constantService.getConstantInfoResByCodeAndGroupCodeAndKind(groupCode, kind, code,
                    locale.toString());
            return this.successHandler.handlerSuccess(resObj, start);
        } catch (Exception ex) {
            return this.errorHandler.handlerException(ex, start);
        }
    }
    
    @GetMapping(AppApiConstant.API_ADMIN_CONSTANT + "/enums")
    @ApiOperation("List enum search")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "Success"), 
            @ApiResponse(code = 401, message = "Unauthorized"), 
            @ApiResponse(code = 402, message = "Forbidden"),
            @ApiResponse(code = 500, message = "Internal server error"),})
    public DtsApiResponse getEnumsSearch() {
        long start = System.currentTimeMillis();
        try {
            List<EnumsParamSearchRes> results = constantService.getListEnumSearch();
            return this.successHandler.handlerSuccess(results, start);
        } catch (Exception ex) {
            return this.errorHandler.handlerException(ex, start);
        }
    }

}
