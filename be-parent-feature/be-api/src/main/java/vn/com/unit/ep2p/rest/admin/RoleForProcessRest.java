/*******************************************************************************
 * Class        ：RoleForProcessRest
 * Created date ：2021/01/19
 * Lasted date  ：2021/01/19
 * Author       ：KhuongTH
 * Change log   ：2021/01/19：01-00 KhuongTH create a new
******************************************************************************/
package vn.com.unit.ep2p.rest.admin;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
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
import vn.com.unit.core.dto.JcaAuthorityDto;
import vn.com.unit.core.res.ObjectDataRes;
import vn.com.unit.dts.web.rest.common.DtsApiResponse;
import vn.com.unit.ep2p.core.constant.AppApiConstant;
import vn.com.unit.ep2p.dto.req.RoleForProcessAddListReq;
import vn.com.unit.ep2p.dto.res.RoleForProcessInfoRes;
import vn.com.unit.ep2p.rest.AbstractRest;
import vn.com.unit.ep2p.service.RoleForProcessService;

/**
 * <p>
 * RoleForProcessRest
 * </p>
 * .
 *
 * @version 01-00
 * @since 01-00
 * @author KhuongTH
 */
@RestController
@RequestMapping(AppApiConstant.API_V1 + AppApiConstant.API_ADMIN)
@Api(tags = { AppApiConstant.API_ADMIN_ROLE_FOR_PROCESS_DESCR })
public class RoleForProcessRest extends AbstractRest {

    @Autowired
    RoleForProcessService roleForProcessService;

    @GetMapping(AppApiConstant.API_ADMIN_ROLE_FOR_PROCESS)
    @ApiOperation("List of role process")
    @ApiResponses(value = { 
            @ApiResponse(code = 200, message = "success"), 
            @ApiResponse(code = 500, message = "Internal Server Error"),
            @ApiResponse(code = 401, message = "Unauthorized"), 
            @ApiResponse(code = 402, message = "Forbidden"),
            @ApiResponse(code = 4024301, message = "Error process list role for process") })
    @ApiImplicitParams({
            @ApiImplicitParam(name = "companyId", required = true, value = "Id of company", dataTypeClass = Long.class, paramType = "query"),
            @ApiImplicitParam(name = "roleId", required = true, value = "Id of role", dataTypeClass = Long.class, paramType = "query"),
            @ApiImplicitParam(name = "processDeployId", required = true, value = "Id of process deploy", dataTypeClass = Long.class, paramType = "query"),
            @ApiImplicitParam(name = "page", dataType = "string", paramType = "query", value = "Results page you want to retrieve (0..N)"),
            @ApiImplicitParam(name = "size", dataType = "string", paramType = "query", value = "Number of records per page."),
            @ApiImplicitParam(name = "sort", allowMultiple = true, dataType = "string", paramType = "query", value = "Sorting criteria in the format: property(,asc|desc). "
                    + "Default sort order is ascending. " + "Multiple sort criteria are supported.") })
    public DtsApiResponse listRoleForProcess(@ApiIgnore @RequestParam MultiValueMap<String, String> requestParams,
            @ApiIgnore Pageable pageable) {
        long start = System.currentTimeMillis();
        try {
            ObjectDataRes<JcaAuthorityDto> resObj = roleForProcessService.search(requestParams, pageable);
            return this.successHandler.handlerSuccess(resObj, start);
        } catch (Exception ex) {
            return this.errorHandler.handlerException(ex, start);
        }
    }

    @PostMapping(AppApiConstant.API_ADMIN_ROLE_FOR_PROCESS)
    @ApiOperation("Create role for process")
    @ApiResponses(value = { 
            @ApiResponse(code = 200, message = "success"), 
            @ApiResponse(code = 500, message = "Internal Server Error"),
            @ApiResponse(code = 401, message = "Unauthorized"), 
            @ApiResponse(code = 402, message = "Forbidden"),
            @ApiResponse(code = 4024302, message = "Error process add role for process") })
    public DtsApiResponse editRoleForProcess(
            @ApiParam(name = "body", value = "Role for process information to edit") @RequestBody RoleForProcessAddListReq roleForProcessAddReq) {
        long start = System.currentTimeMillis();
        try {
            List<RoleForProcessInfoRes> resObj = roleForProcessService.create(roleForProcessAddReq);
            return this.successHandler.handlerSuccess(resObj, start);
        } catch (Exception ex) {
            return this.errorHandler.handlerException(ex, start);
        }
    }

}
