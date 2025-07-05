/*******************************************************************************
 * Class        ：GroupSystemConfigRest
 * Created date ：2020/12/15
 * Lasted date  ：2020/12/15
 * Author       ：ngannh
 * Change log   ：2020/12/15：01-00 ngannh create a new
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
import vn.com.unit.core.dto.JcaGroupSystemConfigDto;
import vn.com.unit.core.res.ObjectDataRes;
import vn.com.unit.dts.web.rest.common.DtsApiResponse;
import vn.com.unit.ep2p.core.constant.AppApiConstant;
import vn.com.unit.ep2p.core.res.dto.EnumsParamSearchRes;
import vn.com.unit.ep2p.dto.req.GroupSystemConfigAddReq;
import vn.com.unit.ep2p.dto.req.GroupSystemConfigUpdateReq;
import vn.com.unit.ep2p.dto.res.GroupSystemConfigInfoRes;
import vn.com.unit.ep2p.rest.AbstractRest;
import vn.com.unit.ep2p.service.GroupSystemConfigService;
import vn.com.unit.ep2p.service.PagingService;

/**
 * GroupSystemConfigRest
 * 
 * @version 01-00
 * @since 01-00
 * @author ngannh
 */
@RestController
@RequestMapping(AppApiConstant.API_V1 + AppApiConstant.API_ADMIN)
@Api(tags = {AppApiConstant.API_ADMIN_GROUP_SYSTEM_CONFIG_DESCR})
public class GroupSystemConfigRest extends AbstractRest{
    
    @Autowired
    PagingService pagingService;    
    
    @Autowired
    private GroupSystemConfigService groupSystemConfigService;
    
    @GetMapping(AppApiConstant.API_ADMIN_GROUP_SYSTEM_COFIG)
    @ApiOperation("List of groupSystemConfigs")
    @ApiResponses(value = {
                @ApiResponse(code = 200 , message = "success"),
                @ApiResponse(code = 500 , message = "Internal Server Error"),
                @ApiResponse(code = 401 , message = "Unauthorized"),
                @ApiResponse(code = 402 , message = "Forbidden"),
                @ApiResponse(code = 402801 , message = "Error process list groupSystemConfig")})
    @ApiImplicitParams({
        @ApiImplicitParam(name = "keySearch", value = "keySearch", required = false, dataTypeClass = String.class, paramType = "query"),
        @ApiImplicitParam(name = "companyId", value = "1", dataTypeClass = String.class, paramType = "query"),
        @ApiImplicitParam(name = "multipleSeachEnums", allowMultiple = true, dataType = "string", paramType = "query", value = "Column CODE, NAME"),
        
        @ApiImplicitParam(name = "page", dataType = "string", paramType = "query", value = "Results page you want to retrieve (0..N)"),
        @ApiImplicitParam(name = "size", dataType = "string", paramType = "query", value = "Number of records per page."),
        @ApiImplicitParam(name = "sort", allowMultiple = true, dataType = "string", paramType = "query", value = "Sorting criteria in the format: property(,asc|desc). "
                + "Default sort order is ascending. " + "Multiple sort criteria are supported.") 
 })
    public DtsApiResponse listGroupSystemConfig(@ApiIgnore @RequestParam MultiValueMap<String, String> requestParams,@ApiIgnore Pageable pageable) {
        long start = System.currentTimeMillis();
        try {
            ObjectDataRes<JcaGroupSystemConfigDto>  resObj = groupSystemConfigService.search(requestParams,pageable);
            return this.successHandler.handlerSuccess(resObj, start);
        }catch (Exception ex) {
            return this.errorHandler.handlerException(ex, start);
        }
    }
    
    @PostMapping(AppApiConstant.API_ADMIN_GROUP_SYSTEM_COFIG)
    @ApiOperation("Create groupSystemConfig")
    @ApiResponses(value = {
                @ApiResponse(code = 200 , message = "success"),
                @ApiResponse(code = 500 , message = "Internal Server Error"),
                @ApiResponse(code = 401 , message = "Unauthorized"),
                @ApiResponse(code = 402 , message = "Forbidden"),
                @ApiResponse(code = 402802 , message = "Error process add groupSystemConfig")})
    public DtsApiResponse addGroupSystemConfig(@ApiParam(name = "body", value = "GroupSystemConfig information to add new") @RequestBody GroupSystemConfigAddReq groupSystemConfigAddDtoReq) {
        long start = System.currentTimeMillis();
        try {
            JcaGroupSystemConfigDto resObj = groupSystemConfigService.create(groupSystemConfigAddDtoReq);
            return this.successHandler.handlerSuccess(resObj, start);
        }catch (Exception ex) {
            return this.errorHandler.handlerException(ex, start);
        }
    }
    
    @PutMapping(AppApiConstant.API_ADMIN_GROUP_SYSTEM_COFIG)
    @ApiOperation("Update groupSystemConfig")
    @ApiResponses(value = {
                @ApiResponse(code = 200 , message = "success"),
                @ApiResponse(code = 500 , message = "Internal Server Error"),
                @ApiResponse(code = 401 , message = "Unauthorized"),
                @ApiResponse(code = 402 , message = "Forbidden"),
                @ApiResponse(code = 402803 , message = "Error process update info groupSystemConfig"),
                @ApiResponse(code = 402806 , message = "Error groupSystemConfig not found")})
    public DtsApiResponse updateGroupSystemConfig(@ApiParam(name = "body", value = "GroupSystemConfig information to update")@RequestBody GroupSystemConfigUpdateReq groupSystemConfigUpdateDtoReq) {
        long start = System.currentTimeMillis();
        try {
            groupSystemConfigService.update(groupSystemConfigUpdateDtoReq);
            return this.successHandler.handlerSuccess(null, start);
        }catch (Exception ex) {
            return this.errorHandler.handlerException(ex, start);
        }
    }
    
    @DeleteMapping(AppApiConstant.API_ADMIN_GROUP_SYSTEM_COFIG)
    @ApiOperation("Delete groupSystemConfig")
    @ApiResponses(value = {
                @ApiResponse(code = 200 , message = "success"),
                @ApiResponse(code = 500 , message = "Internal Server Error"),
                @ApiResponse(code = 401 , message = "Unauthorized"),
                @ApiResponse(code = 402 , message = "Forbidden"),
                @ApiResponse(code = 402804 , message = "Error process delete groupSystemConfig"),
                @ApiResponse(code = 402806 , message = "Error groupSystemConfig not found")})
    public DtsApiResponse deleteGroupSystemConfig(@ApiParam(name = "body", value = "Delete groupSystemConfig on system by id",example = "1")@RequestParam("groupId") Long groupId) {
        long start = System.currentTimeMillis();
        try {
            groupSystemConfigService.delete(groupId);
            return this.successHandler.handlerSuccess(null, start);
        }catch (Exception ex) {
            return this.errorHandler.handlerException(ex, start);
        }
    }
    
    @GetMapping(AppApiConstant.API_ADMIN_GROUP_SYSTEM_COFIG + "/{groupId}") 
    @ApiOperation("Detail of groupSystemConfig")
    @ApiResponses(value = {
                @ApiResponse(code = 200 , message = "success"),
                @ApiResponse(code = 500 , message = "Internal Server Error"),
                @ApiResponse(code = 401 , message = "Unauthorized"),
                @ApiResponse(code = 402 , message = "Forbidden"),
                @ApiResponse(code = 402805 , message = "Error process get groupSystemConfig information detail"),
                @ApiResponse(code = 402806 , message = "Error groupSystemConfig not found")})
    public DtsApiResponse detailGroupSystemConfig(@ApiParam(name = "groupId", value = "Get groupSystemConfig information detail on system by id",example = "1")@PathVariable("groupId") Long groupId) {
        long start = System.currentTimeMillis();
        try {
            GroupSystemConfigInfoRes resObj = groupSystemConfigService.getGroupSystemConfigInfoResById(groupId);
            return this.successHandler.handlerSuccess(resObj, start);
        }catch (Exception ex) {
            return this.errorHandler.handlerException(ex, start);
        }
    }
    
    @GetMapping(AppApiConstant.API_ADMIN_GROUP_SYSTEM_COFIG + "/enums")
    @ApiOperation("List enum search")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "Success"), 
            @ApiResponse(code = 401, message = "Unauthorized"), 
            @ApiResponse(code = 402, message = "Forbidden"),
            @ApiResponse(code = 500, message = "Internal server error"),})
    public DtsApiResponse getEnumsSearch() {
        long start = System.currentTimeMillis();
        try {
            List<EnumsParamSearchRes> results = groupSystemConfigService.getListEnumSearch();
            return this.successHandler.handlerSuccess(results, start);
        } catch (Exception ex) {
            return this.errorHandler.handlerException(ex, start);
        }
    }
    
}
