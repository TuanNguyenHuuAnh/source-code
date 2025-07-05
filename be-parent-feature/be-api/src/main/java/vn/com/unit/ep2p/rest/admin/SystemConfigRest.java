/*******************************************************************************
 * Class        ：SystemConfigRest
 * Created date ：2020/12/11
 * Lasted date  ：2020/12/11
 * Author       ：ngannh
 * Change log   ：2020/12/11：01-00 ngannh create a new
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

import com.google.api.client.util.Strings;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import springfox.documentation.annotations.ApiIgnore;
import vn.com.unit.common.constant.CommonConstant;
//import vn.com.unit.core.dto.JcaMenuDto;
import vn.com.unit.core.dto.JcaSystemConfigDto;
import vn.com.unit.core.res.ObjectDataRes;
import vn.com.unit.dts.web.rest.common.DtsApiResponse;
import vn.com.unit.ep2p.core.constant.AppApiConstant;
import vn.com.unit.ep2p.core.res.dto.EnumsParamSearchRes;
import vn.com.unit.ep2p.dto.req.SystemConfigAddReq;
import vn.com.unit.ep2p.dto.req.SystemConfigUpdateReq;
import vn.com.unit.ep2p.dto.res.SystemConfigInfoRes;
import vn.com.unit.ep2p.rest.AbstractRest;
import vn.com.unit.ep2p.service.PagingService;
import vn.com.unit.ep2p.service.SystemConfigService;

/**
 * SystemConfigRest
 * 
 * @version 01-00
 * @since 01-00
 * @author ngannh
 */
@RestController
@RequestMapping(AppApiConstant.API_V1 + AppApiConstant.API_ADMIN)
@Api(tags = {AppApiConstant.API_ADMIN_SYSTEM_CONFIG_DESCR})
public class SystemConfigRest extends AbstractRest{

    @Autowired
    PagingService pagingService;    
    
    @Autowired
    private SystemConfigService systemConfigService;
    
    @GetMapping(AppApiConstant.API_ADMIN_SYSTEM_COFIG)
    @ApiOperation("Api provides a list systemConfig on systems")
    @ApiResponses(value = {
                @ApiResponse(code = 200 , message = "success"),
                @ApiResponse(code = 500 , message = "Internal Server Error"),
                @ApiResponse(code = 401 , message = "Unauthorized"),
                @ApiResponse(code = 402 , message = "Forbidden"),
                @ApiResponse(code = 402801 , message = "Error process list systemConfig")})
    @ApiImplicitParams({
        @ApiImplicitParam(name = "keySearch", value = "keySearch", required = false, dataTypeClass = String.class, paramType = "query"),
        @ApiImplicitParam(name = "companyId", value = "1", dataTypeClass = String.class, paramType = "query"),
        @ApiImplicitParam(name = "groupId", value = "1", dataTypeClass = String.class, paramType = "query"),
        @ApiImplicitParam(name = "multipleSeachEnums", allowMultiple = true, dataType = "string", paramType = "query", value = "Column KEY, VALUE"),
        
        @ApiImplicitParam(name = "page", dataType = "string", paramType = "query", value = "Results page you want to retrieve (0..N)"),
        @ApiImplicitParam(name = "size", dataType = "string", paramType = "query", value = "Number of records per page."),
        @ApiImplicitParam(name = "sort", allowMultiple = true, dataType = "string", paramType = "query", value = "Sorting criteria in the format: property(,asc|desc). "
                + "Default sort order is ascending. " + "Multiple sort criteria are supported.") 
 })
    public DtsApiResponse listSystemConfig(@ApiIgnore @RequestParam MultiValueMap<String, String> requestParams,@ApiIgnore Pageable pageable) {
        long start = System.currentTimeMillis();
        try {
            ObjectDataRes<JcaSystemConfigDto> resObj = systemConfigService.search(requestParams,pageable);
            return this.successHandler.handlerSuccess(resObj, start);
        }catch (Exception ex) {
            return this.errorHandler.handlerException(ex, start);
        }
    }
    
    @PostMapping(AppApiConstant.API_ADMIN_SYSTEM_COFIG)
    @ApiOperation("API use to add new a systemConfig on systems")
    @ApiResponses(value = {
                @ApiResponse(code = 200 , message = "success"),
                @ApiResponse(code = 500 , message = "Internal Server Error"),
                @ApiResponse(code = 401 , message = "Unauthorized"),
                @ApiResponse(code = 402 , message = "Forbidden"),
                @ApiResponse(code = 402802 , message = "Error process add role")})
    public DtsApiResponse addSystemConfig(@ApiParam(name = "body", value = "SystemConfig information used to create new ones") @RequestBody SystemConfigAddReq systemConfigAddReq) {
        long start = System.currentTimeMillis();
        try {
            JcaSystemConfigDto resObj = systemConfigService.create(systemConfigAddReq);
            return this.successHandler.handlerSuccess(resObj, start);
        }catch (Exception ex) {
            return this.errorHandler.handlerException(ex, start);
        }
    }
    @DeleteMapping(AppApiConstant.API_ADMIN_SYSTEM_COFIG)
    @ApiOperation("API use to delete a systemConfig on systems")
    @ApiResponses(value = {
                @ApiResponse(code = 200 , message = "success"),
                @ApiResponse(code = 500 , message = "Internal Server Error"),
                @ApiResponse(code = 401 , message = "Unauthorized"),
                @ApiResponse(code = 402 , message = "Forbidden"),
                @ApiResponse(code = 402802 , message = "Error process delete systemConfig")})
    public DtsApiResponse deleteSystemConfig(@ApiParam(name = "body", value = "System config information used to delete new ones",example = "1") @RequestParam("systemConfigId") Long systemConfigId) {
        long start = System.currentTimeMillis();
        try {
            systemConfigService.delete(systemConfigId);
            return this.successHandler.handlerSuccess(null, start);
        }catch (Exception ex) {
            return this.errorHandler.handlerException(ex, start);
        }
    }
    
    @PutMapping(AppApiConstant.API_ADMIN_SYSTEM_COFIG)
    @ApiOperation("API use to update a systemConfig on systems")
    @ApiResponses(value = {
                @ApiResponse(code = 200 , message = "success"),
                @ApiResponse(code = 500 , message = "Internal Server Error"),
                @ApiResponse(code = 401 , message = "Unauthorized"),
                @ApiResponse(code = 402 , message = "Forbidden"),
                @ApiResponse(code = 402805 , message = "Error process update systemConfig")})
    public DtsApiResponse updateSystemConfig(@ApiParam(name = "body", value = "SystemConfig information used to update systemConfig by id") @RequestBody SystemConfigUpdateReq systemConfigUpdateReq) {
        long start = System.currentTimeMillis();
        try {
            systemConfigService.update(systemConfigUpdateReq);
            return this.successHandler.handlerSuccess(null, start);
        }catch (Exception ex) {
            return this.errorHandler.handlerException(ex, start);
        }
    }
    
    @GetMapping(AppApiConstant.API_ADMIN_SYSTEM_COFIG + "/{systemConfigId}") 
    @ApiOperation("Detail of systemConfig")
    @ApiResponses(value = {
                @ApiResponse(code = 200 , message = "success"),
                @ApiResponse(code = 500 , message = "Internal Server Error"),
                @ApiResponse(code = 401 , message = "Unauthorized"),
                @ApiResponse(code = 402 , message = "Forbidden"),
                @ApiResponse(code = 402805 , message = "Error process get systemConfig information detail"),
                @ApiResponse(code = 402806 , message = "Error systemConfig not found")})
    public DtsApiResponse detailSystemConfig(@ApiParam(name = "systemConfigId", value = "Get systemConfig information detail on system by id",example = "1")@PathVariable("systemConfigId") Long systemConfigId) {
        long start = System.currentTimeMillis();
        try {
            SystemConfigInfoRes resObj = systemConfigService.getSystemConfigInfoResById(systemConfigId);
            return this.successHandler.handlerSuccess(resObj, start);
        }catch (Exception ex) {
            return this.errorHandler.handlerException(ex, start);
        }
    }
    
    @GetMapping(AppApiConstant.API_ADMIN_SYSTEM_COFIG + "/enums")
    @ApiOperation("List enum search")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "Success"), 
            @ApiResponse(code = 401, message = "Unauthorized"), 
            @ApiResponse(code = 402, message = "Forbidden"),
            @ApiResponse(code = 500, message = "Internal server error"),})
    public DtsApiResponse getEnumsSearch() {
        long start = System.currentTimeMillis();
        try {
            List<EnumsParamSearchRes> results = systemConfigService.getListEnumSearch();
            return this.successHandler.handlerSuccess(results, start);
        } catch (Exception ex) {
            return this.errorHandler.handlerException(ex, start);
        }
    }

    @GetMapping(AppApiConstant.API_ADMIN_SYSTEM_COFIG_BY_KEY)
    @ApiOperation("get setting value by setting key")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "Success"),
            @ApiResponse(code = 500, message = "Internal server error"),
            @ApiResponse(code = 401, message = "Unauthorized"), @ApiResponse(code = 402, message = "Forbidden"),
            @ApiResponse(code = 4021604, message = "Error get setting value by setting key"), })
    public DtsApiResponse getConfigByKey(@RequestParam("settingKey") String settingKey,
            @RequestParam(value = "companyId", required = true) Long companyId) {
        long start = System.currentTimeMillis();
        try {
            JcaSystemConfigDto resObj = systemConfigService.getConfigByKey(settingKey, companyId);
            return this.successHandler.handlerSuccess(resObj, start);
        } catch (Exception ex) {
            return this.errorHandler.handlerException(ex, start);
        }
    }
}
