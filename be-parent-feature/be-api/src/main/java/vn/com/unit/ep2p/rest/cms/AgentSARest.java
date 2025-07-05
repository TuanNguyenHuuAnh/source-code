package vn.com.unit.ep2p.rest.cms;

import java.util.List;
import java.util.Locale;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import vn.com.unit.cms.core.constant.CmsApiConstant;
import vn.com.unit.cms.core.module.agent.dto.AgentSADto;
import vn.com.unit.cms.core.module.agent.dto.AgentSASearchDto;
import vn.com.unit.cms.core.module.agent.dto.DataRange;
import vn.com.unit.core.res.ObjectDataRes;
import vn.com.unit.core.security.UserProfileUtils;
import vn.com.unit.dts.web.rest.common.DtsApiResponse;
import vn.com.unit.ep2p.admin.constant.ConstantCore;
import vn.com.unit.ep2p.core.constant.AppApiConstant;
import vn.com.unit.ep2p.rest.AbstractRest;
import vn.com.unit.ep2p.service.AgentSAService;
import vn.com.unit.ep2p.utils.LangugeUtil;

@RestController
@RequestMapping(CmsApiConstant.BASE_API_URL + "/agent-sa")
@Api(tags = { "Agent SA" })
public class AgentSARest extends AbstractRest{
	@Autowired
	AgentSAService agentSAService;
	
	private Logger logger = LoggerFactory.getLogger(getClass());
	
	@GetMapping(AppApiConstant.API_ANGENT_SA)
    @ApiOperation("Api provides constant on systems")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "success"), @ApiResponse(code = 500, message = "Internal Server Error"),
            @ApiResponse(code = 401, message = "Unauthorized"), @ApiResponse(code = 403, message = "Forbidden"),
            @ApiResponse(code = 402601, message = "Error process type new") })
	
    public DtsApiResponse getListAgentSAByCondition(HttpServletRequest request,
    		@RequestParam(value = ConstantCore.PAGE_SIZE, defaultValue = "10") Optional<Integer> pageSizeParam,
            @RequestParam(value = ConstantCore.PAGE, defaultValue = "1") Optional<Integer> pageParam
            , AgentSASearchDto searchDto)  {
        long start = System.currentTimeMillis();
        try {
        	searchDto.setAgentCode(UserProfileUtils.getFaceMask());
        	//searchDto.setAgentCode("250261");
        	//searchDto.setAgentCode("149688");
            searchDto.setPage(pageParam.get());
        	searchDto.setPageSize(pageSizeParam.get());
        	ObjectDataRes<AgentSADto> resObj = agentSAService.getListAgentSAByCondition(searchDto);
            return this.successHandler.handlerSuccess(resObj, start, null, null);
        	
        } catch (Exception ex) {
            return this.errorHandler.handlerException(ex, start, null, null);
        }
    }
	
	@GetMapping(AppApiConstant.API_GET_DATA_RANGE)
    @ApiOperation("Api provides constant on systems")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "success"), @ApiResponse(code = 500, message = "Internal Server Error"),
            @ApiResponse(code = 401, message = "Unauthorized"), @ApiResponse(code = 403, message = "Forbidden"),
            @ApiResponse(code = 402601, message = "Error process type new") })
	
    public DtsApiResponse getDataRange(HttpServletRequest request,
    		String userName)  {
        long start = System.currentTimeMillis();
        try {
        	List<DataRange> data = agentSAService.getDataRange(userName);
            return this.successHandler.handlerSuccess(data, start, null, null);
        } catch (Exception ex) {
            return this.errorHandler.handlerException(ex, start, null, null);
        }
    }
	
	//export
	@SuppressWarnings("rawtypes")
	@PostMapping(AppApiConstant.API_EXPORT_ANGENT_SA)
    @ApiOperation("Api provides constant on systems")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "success"), @ApiResponse(code = 500, message = "Internal Server Error"),
            @ApiResponse(code = 401, message = "Unauthorized"), @ApiResponse(code = 403, message = "Forbidden"),
            @ApiResponse(code = 402601, message = "Error process type new") })
	
	public ResponseEntity exportAgentSA(HttpServletRequest request, @RequestBody AgentSASearchDto searchDto) {
		ResponseEntity resObj = null;
		try {
			Locale locale = LangugeUtil.getLanguageFromHeader(request);
			String agentCode = UserProfileUtils.getFaceMask();
			searchDto.setAgentCode(agentCode);
			resObj = agentSAService.exportAgentSA(searchDto, locale);
		} catch (Exception e) {
			logger.error("exportLis", e);
		}
		return resObj;
	}
}
