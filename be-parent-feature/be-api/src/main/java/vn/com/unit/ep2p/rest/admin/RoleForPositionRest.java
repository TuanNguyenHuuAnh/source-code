/*******************************************************************************
 * Class        ：RoleForTeamRest
 * Created date ：2020/12/22
 * Lasted date  ：2020/12/22
 * Author       ：SonND
 * Change log   ：2020/12/22：01-00 SonND create a new
 ******************************************************************************/
package vn.com.unit.ep2p.rest.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import vn.com.unit.dts.web.rest.common.DtsApiResponse;
import vn.com.unit.ep2p.core.constant.AppApiConstant;
import vn.com.unit.ep2p.dto.req.RoleForPositionAddListReq;
import vn.com.unit.ep2p.dto.res.RoleForPositionInfoRes;
import vn.com.unit.ep2p.rest.AbstractRest;
import vn.com.unit.ep2p.service.RoleForPositionService;

/**
 * RoleForTeamRest
 * 
 * @version 01-00
 * @since 01-00
 * @author SonND
 */

@RestController
@RequestMapping(AppApiConstant.API_V1 + AppApiConstant.API_ADMIN)
@Api(tags = {AppApiConstant.API_ADMIN_ROLE_FOR_POSITION_DESCR})
public class RoleForPositionRest extends AbstractRest{
	
    public static final String ROLE_FOR_ACCOUNT_ID = "roleForAccountId";
    public static final String ACCOUNT_ID = "accountId";
    
    @Autowired
    private RoleForPositionService roleForPositionService;
    
    @PostMapping(AppApiConstant.API_ADMIN_ROLE_FOR_POSITION)
    @ApiOperation("Create role for position")
    @ApiResponses(value = {
                @ApiResponse(code = 200 , message = "Success"),
                @ApiResponse(code = 401 , message = "Unauthorized"),
                @ApiResponse(code = 402 , message = "Forbidden"),
                @ApiResponse(code = 4024604 , message = "Error not found role for position by position"),
                @ApiResponse(code = 4024603 , message = "Error not found role for position"),
                @ApiResponse(code = 4024601 , message = "Error process add role for position"),
                @ApiResponse(code = 4024606 , message = "Error process update activity SP_UPDATE_ROLE_FOR_POSITION"),
                @ApiResponse(code = 500 , message = "Internal server error"),
    })
    public DtsApiResponse addRoleForPosition(@ApiParam(name = "body", value = "Role for position information to add new") @RequestBody RoleForPositionAddListReq roleForPositionAddListReq) {
        long start = System.currentTimeMillis();
        try {
            roleForPositionService.createRoleForPosition(roleForPositionAddListReq);
            return this.successHandler.handlerSuccess(null, start);
        }catch (Exception ex) {
            return this.errorHandler.handlerException(ex, start);
        }
    }
	
    @GetMapping(AppApiConstant.API_ADMIN_ROLE_FOR_POSITION)
    @ApiOperation("get role for position")
    @ApiResponses(value = {
                @ApiResponse(code = 200 , message = "Success"),
                @ApiResponse(code = 401 , message = "Unauthorized"),
                @ApiResponse(code = 402 , message = "Forbidden"),
                @ApiResponse(code = 4024604 , message = "Error not found role for position by position"),
                @ApiResponse(code = 4024603 , message = "Error not found role for position"),
                @ApiResponse(code = 500 , message = "Internal server error"),
    })
    public DtsApiResponse addRoleForPosition(
            @ApiParam(name = "positionId", value = "Get role for position by position id", example = "1")@RequestParam("positionId") Long positionId,
            @ApiParam(name = "companyId", value = "Filter role for position by company id", example = "1", allowEmptyValue = true)@RequestParam("companyId") Long companyId
    ) {
        long start = System.currentTimeMillis();
        try {
            RoleForPositionInfoRes resObj = roleForPositionService.detail(positionId,companyId);
            return this.successHandler.handlerSuccess(resObj, start);
        }catch (Exception ex) {
            return this.errorHandler.handlerException(ex, start);
        }
    }
}
