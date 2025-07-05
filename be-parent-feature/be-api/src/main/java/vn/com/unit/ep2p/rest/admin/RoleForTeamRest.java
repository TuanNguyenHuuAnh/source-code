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
import vn.com.unit.ep2p.core.req.dto.RoleForTeamInfoListReq;
import vn.com.unit.ep2p.dto.res.RoleForTeamInfoRes;
import vn.com.unit.ep2p.rest.AbstractRest;
import vn.com.unit.ep2p.service.RoleForTeamService;

/**
 * RoleForTeamRest
 * 
 * @version 01-00
 * @since 01-00
 * @author SonND
 */

@RestController
@RequestMapping(AppApiConstant.API_V1 + AppApiConstant.API_ADMIN)
@Api(tags = {AppApiConstant.API_ADMIN_ROLE_FOR_TEAM_DESCR})
public class RoleForTeamRest extends AbstractRest{
	
	@Autowired
	private RoleForTeamService roleForTeamService;
	
	@PostMapping(AppApiConstant.API_ADMIN_ROLE_FOR_TEAM)
	@ApiOperation("Create role for team")
	@ApiResponses(value = {
	            @ApiResponse(code = 200 , message = "Success"),
	            @ApiResponse(code = 401 , message = "Unauthorized"),
	            @ApiResponse(code = 402 , message = "Forbidden"),
	            @ApiResponse(code = 4022502 , message = "Error process add role for team"),
	            @ApiResponse(code = 4022507 , message = "Error not found role id"),
	            @ApiResponse(code = 4021506 , message = "Error not found information team"),
	            @ApiResponse(code = 4021203 , message = "Error company not found"),
	            @ApiResponse(code = 500 , message = "Internal server error"),
	})
	public DtsApiResponse addRoleForTeam(@ApiParam(name = "body", value = "Role for team information to add new") @RequestBody RoleForTeamInfoListReq roleForTeamInfoListReq) {
        long start = System.currentTimeMillis();
        try {
        	roleForTeamService.saveListRoleForTeam(roleForTeamInfoListReq);
            return this.successHandler.handlerSuccess(null, start);
        }catch (Exception ex) {
            return this.errorHandler.handlerException(ex, start);
        }
    }

	@GetMapping(AppApiConstant.API_ADMIN_ROLE_FOR_TEAM ) 
	@ApiOperation("Detail of role for team")
	@ApiResponses(value = {
	            @ApiResponse(code = 200 , message = "Success"),
	            @ApiResponse(code = 401 , message = "Unauthorized"),
	            @ApiResponse(code = 402 , message = "Forbidden"),
	            @ApiResponse(code = 4022506 , message = "Error process get information role for team"),
	            @ApiResponse(code = 4021506 , message = "Error not found information team"),
	            @ApiResponse(code = 500 , message = "Internal server error"),})
	public DtsApiResponse detailTeam(
	        @ApiParam(name = "teamId", value = "Get role for team information detail on system by team id", example = "1")@RequestParam("teamId") Long teamId
	        ,@ApiParam(name = "companyId", value = "Filter role for team by company id", example = "1", allowEmptyValue = true)@RequestParam("companyId") Long companyId) {
        long start = System.currentTimeMillis();
        try {
            RoleForTeamInfoRes resObj = roleForTeamService.getRoleForTeamInfoResByTeamId(teamId,companyId);
            return this.successHandler.handlerSuccess(resObj, start);
        }catch (Exception ex) {
            return this.errorHandler.handlerException(ex, start);
        }
    }
	
	
}
