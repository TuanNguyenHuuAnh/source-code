/*******************************************************************************
 * Class        ：GroupConstantRest
 * Created date ：2020/12/01
 * Lasted date  ：2020/12/01
 * Author       ：tantm
 * Change log   ：2020/12/01：01-00 tantm create a new
 ******************************************************************************/
package vn.com.unit.ep2p.rest.admin;

//import java.util.List;

//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.domain.Pageable;
//import org.springframework.util.MultiValueMap;
//import org.springframework.web.bind.annotation.DeleteMapping;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.PutMapping;
//import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
//import vn.com.unit.ep2p.api.req.dto.GroupConstantAddReq;
//import vn.com.unit.ep2p.api.req.dto.GroupConstantUpdateReq;
import vn.com.unit.ep2p.core.constant.AppApiConstant;
//import vn.com.unit.ep2p.core.res.dto.EnumsParamSearchRes;
//import vn.com.unit.ep2p.res.dto.GroupConstantInfoRes;
//import vn.com.unit.ep2p.service.GroupConstantService;
import vn.com.unit.ep2p.rest.AbstractRest;

/**
 * 
 * GroupConstantRest
 * 
 * @version 01-00
 * @since 01-00
 * @author tantm
 */

@RestController
@RequestMapping(AppApiConstant.API_V1 + AppApiConstant.API_ADMIN)
@Api(tags = { AppApiConstant.API_ADMIN_GROUP_CONSTANT_DESCR })
public class GroupConstantRest extends AbstractRest {

//    @Autowired
//    private GroupConstantService groupConstantService;
//
//    @GetMapping(AppApiConstant.API_ADMIN_GROUP_CONSTANT)
//    @ApiOperation("List of groupConstants")
//    @ApiResponses(value = { @ApiResponse(code = 200, message = "success"), @ApiResponse(code = 500, message = "Internal Server Error"),
//            @ApiResponse(code = 401, message = "Unauthorized"), @ApiResponse(code = 402, message = "Forbidden"),
//            @ApiResponse(code = 4023101, message = "Error process list groupConstant") })
//    @ApiImplicitParams({
//            @ApiImplicitParam(name = "keySearch", value = "keySearch", required = false, dataTypeClass = String.class, paramType = "query"),
//            @ApiImplicitParam(name = "companyId", value = "1", dataTypeClass = String.class, paramType = "query"),
//            @ApiImplicitParam(name = "multipleSeachEnums", allowMultiple = true, dataType = "string", paramType = "query", value = "Column CODE, TEXT"),
//
//            @ApiImplicitParam(name = "page", dataType = "string", paramType = "query", value = "Results page you want to retrieve (0..N)"),
//            @ApiImplicitParam(name = "size", dataType = "string", paramType = "query", value = "Number of records per page."),
//            @ApiImplicitParam(name = "sort", allowMultiple = true, dataType = "string", paramType = "query", value = "Sorting criteria in the format: property(,asc|desc). "
//                    + "Default sort order is ascending. " + "Multiple sort criteria are supported.") })
//    public DtsApiResponse listGroupConstant(@ApiIgnore @RequestParam MultiValueMap<String, String> requestParams,
//            @ApiIgnore Pageable pageable) {
//        long start = System.currentTimeMillis();
//        try {
//            /** example structure return object data have extends to add properties */
//            // GroupConstantListObjectRes resObj = groupConstantService.search(requestParams,pageable);
//            /** END */
//            ObjectDataRes<JcaGroupConstantDto> resObj = groupConstantService.search(requestParams, pageable);
//            return this.successHandler.handlerSuccess(resObj, start);
//        } catch (Exception ex) {
//            return this.errorHandler.handlerException(ex, start);
//        }
//    }
//
//    @PostMapping(AppApiConstant.API_ADMIN_GROUP_CONSTANT)
//    @ApiOperation("Create groupConstant")
//    @ApiResponses(value = { @ApiResponse(code = 200, message = "success"), @ApiResponse(code = 500, message = "Internal Server Error"),
//            @ApiResponse(code = 401, message = "Unauthorized"), @ApiResponse(code = 402, message = "Forbidden"),
//            @ApiResponse(code = 4023102, message = "Error process add groupConstant") })
//    public DtsApiResponse addGroupConstant(
//            @ApiParam(name = "body", value = "GroupConstant information to add new") @RequestBody GroupConstantAddReq groupConstantAddDtoReq) {
//        long start = System.currentTimeMillis();
//        try {
//            JcaGroupConstantDto resObj = groupConstantService.create(groupConstantAddDtoReq);
//            return this.successHandler.handlerSuccess(resObj, start);
//        } catch (Exception ex) {
//            return this.errorHandler.handlerException(ex, start);
//        }
//    }
//
//    @PutMapping(AppApiConstant.API_ADMIN_GROUP_CONSTANT)
//    @ApiOperation("Update groupConstant")
//    @ApiResponses(value = { @ApiResponse(code = 200, message = "success"), @ApiResponse(code = 500, message = "Internal Server Error"),
//            @ApiResponse(code = 401, message = "Unauthorized"), @ApiResponse(code = 402, message = "Forbidden"),
//            @ApiResponse(code = 4023103, message = "Error process update info groupConstant"),
//            @ApiResponse(code = 4023104, message = "Error groupConstant not found") })
//    public DtsApiResponse updateGroupConstant(
//            @ApiParam(name = "body", value = "GroupConstant information to update") @RequestBody GroupConstantUpdateReq groupConstantUpdateDtoReq) {
//        long start = System.currentTimeMillis();
//        try {
//            groupConstantService.update(groupConstantUpdateDtoReq);
//            return this.successHandler.handlerSuccess(null, start);
//        } catch (Exception ex) {
//            return this.errorHandler.handlerException(ex, start);
//        }
//    }
//
//    @DeleteMapping(AppApiConstant.API_ADMIN_GROUP_CONSTANT)
//    @ApiOperation("Delete groupConstant")
//    @ApiResponses(value = { @ApiResponse(code = 200, message = "success"), @ApiResponse(code = 500, message = "Internal Server Error"),
//            @ApiResponse(code = 401, message = "Unauthorized"), @ApiResponse(code = 402, message = "Forbidden"),
//            @ApiResponse(code = 4023105, message = "Error process delete groupConstant"),
//            @ApiResponse(code = 4023104, message = "Error groupConstant not found") })
//    public DtsApiResponse deleteGroupConstant(
//            @ApiParam(name = "groupId", value = "Delete groupConstant on system by id", example = "123") @RequestParam("groupId") Long groupId) {
//        long start = System.currentTimeMillis();
//        try {
//            groupConstantService.delete(groupId);
//            return this.successHandler.handlerSuccess(null, start);
//        } catch (Exception ex) {
//            return this.errorHandler.handlerException(ex, start);
//        }
//    }
//
//    @GetMapping(AppApiConstant.API_ADMIN_GROUP_CONSTANT + "/{groupId}")
//    @ApiOperation("Detail of groupConstant")
//    @ApiResponses(value = { @ApiResponse(code = 200, message = "success"), @ApiResponse(code = 500, message = "Internal Server Error"),
//            @ApiResponse(code = 401, message = "Unauthorized"), @ApiResponse(code = 402, message = "Forbidden"),
//            @ApiResponse(code = 4023106, message = "Error process get groupConstant information detail"),
//            @ApiResponse(code = 4023104, message = "Error groupConstant not found") })
//    public DtsApiResponse detailGroupConstant(
//            @ApiParam(name = "groupId", value = "Get groupConstant information detail on system by id", example = "123") @PathVariable("groupId") Long groupId) {
//        long start = System.currentTimeMillis();
//        try {
//            GroupConstantInfoRes resObj = groupConstantService.getGroupConstantInfoResById(groupId);
//            return this.successHandler.handlerSuccess(resObj, start);
//        } catch (Exception ex) {
//            return this.errorHandler.handlerException(ex, start);
//        }
//    }
//    
//    @GetMapping(AppApiConstant.API_ADMIN_GROUP_CONSTANT + "/enums")
//    @ApiOperation("List enum search")
//    @ApiResponses(value = { @ApiResponse(code = 200, message = "Success"), 
//            @ApiResponse(code = 401, message = "Unauthorized"), 
//            @ApiResponse(code = 402, message = "Forbidden"),
//            @ApiResponse(code = 500, message = "Internal server error"),})
//    public DtsApiResponse getEnumsSearch() {
//        long start = System.currentTimeMillis();
//        try {
//            List<EnumsParamSearchRes> results = groupConstantService.getListEnumSearch();
//            return this.successHandler.handlerSuccess(results, start);
//        } catch (Exception ex) {
//            return this.errorHandler.handlerException(ex, start);
//        }
//    }

}
