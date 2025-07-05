/*******************************************************************************
 * Class        ：CaManagementRest
 * Created date ：2020/12/16
 * Lasted date  ：2020/12/16
 * Author       ：minhnv
 * Change log   ：2020/12/16：01-00 minhnv create a new
 ******************************************************************************/
package vn.com.unit.ep2p.rest.admin;

import java.util.List;

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
import vn.com.unit.core.dto.JcaCaManagementDto;
import vn.com.unit.core.res.ObjectDataRes;
import vn.com.unit.dts.web.rest.common.DtsApiResponse;
import vn.com.unit.ep2p.core.constant.AppApiConstant;
import vn.com.unit.ep2p.core.res.dto.EnumsParamSearchRes;
import vn.com.unit.ep2p.dto.req.CaManagementAddReq;
import vn.com.unit.ep2p.dto.req.CaManagementUpdateReq;
import vn.com.unit.ep2p.dto.res.CaManagementInfoRes;
import vn.com.unit.ep2p.rest.AbstractRest;
import vn.com.unit.ep2p.service.CaManagementService;

/**
 * CaManagementRest
 * 
 * @version 01-00
 * @since 01-00
 * @author minhnv
 */
@RestController
@RequestMapping(AppApiConstant.API_V1 + AppApiConstant.API_ADMIN)
@Api(tags = { AppApiConstant.API_ADMIN_ACCOUNT_CA_DESCR })
public class CaManagementRest extends AbstractRest {

    @Autowired
    private CaManagementService caManagementService;

    @GetMapping(AppApiConstant.API_ADMIN_ACCOUNT_CA)
    @ApiOperation("List of certificate authorities")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "keySearch", value = "keySearch", required = false, dataTypeClass = String.class, paramType = "query"),
        @ApiImplicitParam(name = "companyId", value = "1", dataTypeClass = String.class, paramType = "query"),
        @ApiImplicitParam(name = "multipleSeachEnums", allowMultiple = true, dataType = "string", paramType = "query", value = "Column CA_NAME, ACCOUNT_NAME, CA_SLOT"),

        @ApiImplicitParam(name = "page", dataType = "string", paramType = "query", value = "Results page you want to retrieve (0..N)"),
        @ApiImplicitParam(name = "size", dataType = "string", paramType = "query", value = "Number of records per page."),
        @ApiImplicitParam(name = "sort", allowMultiple = true, dataType = "string", paramType = "query", value = "Sorting criteria in the format: property(,asc|desc). "
                + "Default sort order is ascending. " + "Multiple sort criteria are supported.") })
    public DtsApiResponse listCaManagement(
            @ApiIgnore @RequestParam MultiValueMap<String, String> requestParams,@ApiIgnore Pageable pageable) {
        long start = System.currentTimeMillis();
        try {
            ObjectDataRes<JcaCaManagementDto> resObj = caManagementService.search(requestParams, pageable);
            return this.successHandler.handlerSuccess(resObj, start);
        } catch (Exception ex) {
            return this.errorHandler.handlerException(ex, start);
        }
    }

    @PostMapping(AppApiConstant.API_ADMIN_ACCOUNT_CA)
    @ApiOperation("Create CA")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "success"),
            @ApiResponse(code = 500, message = "Internal Server Error"),
            @ApiResponse(code = 401, message = "Unauthorized"),
            @ApiResponse(code = 402, message = "Forbidden"),
            @ApiResponse(code = 402802, message = "Error process add CA") })
    public DtsApiResponse addCaManagement(
            @ApiParam(name = "body", value = "Account information to add new") @RequestBody CaManagementAddReq caManagementAddReq) {
        long start = System.currentTimeMillis();
        try {
            CaManagementInfoRes resObj = caManagementService.create(caManagementAddReq);
            return this.successHandler.handlerSuccess(resObj, start);
        } catch (Exception ex) {
            return this.errorHandler.handlerException(ex, start);
        }
    }

    @PutMapping(AppApiConstant.API_ADMIN_ACCOUNT_CA)
    @ApiOperation("Update CA")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "success"),
            @ApiResponse(code = 500, message = "Internal Server Error"),
            @ApiResponse(code = 401, message = "Unauthorized"),
            @ApiResponse(code = 402, message = "Forbidden"),
            @ApiResponse(code = 402803, message = "Error process update info CA"),
            @ApiResponse(code = 402806, message = "Error CA not found") })
    public DtsApiResponse updateCaManagement(
            @ApiParam(name = "body", value = "Account information to update") @RequestBody CaManagementUpdateReq caManagementUpdateReq) {
        long start = System.currentTimeMillis();
        try {
            caManagementService.update(caManagementUpdateReq);
            return this.successHandler.handlerSuccess(null, start);
        } catch (Exception ex) {
            return this.errorHandler.handlerException(ex, start);
        }
    }

    @DeleteMapping(AppApiConstant.API_ADMIN_ACCOUNT_CA)
    @ApiOperation("Delete CA")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "success"),
            @ApiResponse(code = 500, message = "Internal Server Error"),
            @ApiResponse(code = 401, message = "Unauthorized"),
            @ApiResponse(code = 402, message = "Forbidden"),
            @ApiResponse(code = 402804, message = "Error process delete CA"),
            @ApiResponse(code = 402806, message = "Error CA not found") })
    public DtsApiResponse deleteCaManagement(
            @ApiParam(name = "caManagementId", value = "Delete CA on system by id", example = "1") @RequestParam("caManagementId") Long caManagementId) {
        long start = System.currentTimeMillis();
        try {
            caManagementService.delete(caManagementId);
            return this.successHandler.handlerSuccess(null, start);
        } catch (Exception ex) {
            return this.errorHandler.handlerException(ex, start);
        }
    }

    @GetMapping(AppApiConstant.API_ADMIN_ACCOUNT_CA + "/{caManagementId}")
    @ApiOperation("Detail of CA")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "success"),
            @ApiResponse(code = 500, message = "Internal Server Error"),
            @ApiResponse(code = 401, message = "Unauthorized"),
            @ApiResponse(code = 402, message = "Forbidden"),
            @ApiResponse(code = 402805, message = "Error process get CA information detail"),
            @ApiResponse(code = 402806, message = "Error CA not found") })
    public DtsApiResponse detailCaManagement(
            @ApiParam(name = "caManagementId", value = "Get CA information detail on system by id", example = "1") @PathVariable("caManagementId") Long caManagementId) {
        long start = System.currentTimeMillis();
        try {
            CaManagementInfoRes resObj = caManagementService.getCaManagementInfoResById(caManagementId);
            return this.successHandler.handlerSuccess(resObj, start);
        } catch (Exception ex) {
            return this.errorHandler.handlerException(ex, start);
        }
    }
    
    @GetMapping(AppApiConstant.API_ADMIN_ACCOUNT_CA + "/enums")
    @ApiOperation("List enum search")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "Success"), 
            @ApiResponse(code = 401, message = "Unauthorized"), 
            @ApiResponse(code = 402, message = "Forbidden"),
            @ApiResponse(code = 500, message = "Internal server error"),})
    public DtsApiResponse getEnumsSearch() {
        long start = System.currentTimeMillis();
        try {
            List<EnumsParamSearchRes> results = caManagementService.getListEnumSearch();
            return this.successHandler.handlerSuccess(results, start);
        } catch (Exception ex) {
            return this.errorHandler.handlerException(ex, start);
        }
    }
}
