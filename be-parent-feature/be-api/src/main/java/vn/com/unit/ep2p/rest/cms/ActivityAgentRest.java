package vn.com.unit.ep2p.rest.cms;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import vn.com.unit.cms.core.constant.CmsApiConstant;
import vn.com.unit.cms.core.module.agent.dto.ActivityAgentDto;
import vn.com.unit.cms.core.module.agent.dto.ActivityGroupDto;
import vn.com.unit.core.security.UserProfileUtils;
import vn.com.unit.dts.web.rest.common.DtsApiResponse;
import vn.com.unit.ep2p.core.constant.AppApiConstant;
import vn.com.unit.ep2p.rest.AbstractRest;
import vn.com.unit.ep2p.service.ApiActivityAgentService;

@RestController
@RequestMapping(CmsApiConstant.BASE_API_URL + "/activity-agent")
@Api(tags = { "Activity agent" })
public class ActivityAgentRest extends AbstractRest{
	
	@Autowired
	ApiActivityAgentService apiActivityAgentService;
	
	//hoat dong dai ly
	@GetMapping(AppApiConstant.API_AGENT_ACTIVITY)
    @ApiOperation("Api agent activities")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "success"), @ApiResponse(code = 500, message = "Internal Server Error"),
            @ApiResponse(code = 401, message = "Unauthorized"), @ApiResponse(code = 403, message = "Forbidden"),
            @ApiResponse(code = 402601, message = "Error process type new") })
	
    public DtsApiResponse getAgentActivity(HttpServletRequest request){
        long start = System.currentTimeMillis();
        try {
        	String agentCode  = UserProfileUtils.getFaceMask();
        	ActivityAgentDto resObj = apiActivityAgentService.getActivityAgent(agentCode);
            return this.successHandler.handlerSuccess(resObj, start, null, null);
        } catch (Exception ex) {
            return this.errorHandler.handlerException(ex, start, null, null);
        }
    }
	
	//hoat dong nhom phong
	@GetMapping(AppApiConstant.API_GROUP_ACTIVITY)
    @ApiOperation("Api group activities")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "success"), @ApiResponse(code = 500, message = "Internal Server Error"),
            @ApiResponse(code = 401, message = "Unauthorized"), @ApiResponse(code = 403, message = "Forbidden"),
            @ApiResponse(code = 402601, message = "Error process type new") })
	
    public DtsApiResponse getGroupActivity(HttpServletRequest request, String agentGroup){
        long start = System.currentTimeMillis();
        try {
        	String agentCode  = UserProfileUtils.getFaceMask();
        	ActivityGroupDto resObj = apiActivityAgentService.getActivityGroup(agentCode, agentGroup);
            return this.successHandler.handlerSuccess(resObj, start, null, null);
        } catch (Exception ex) {
            return this.errorHandler.handlerException(ex, start, null, null);
        }
    }
	
}
