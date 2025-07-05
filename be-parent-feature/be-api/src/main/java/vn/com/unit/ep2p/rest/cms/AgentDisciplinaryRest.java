package vn.com.unit.ep2p.rest.cms;

import java.util.Locale;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
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
import vn.com.unit.cms.core.module.agent.dto.AgentDisciplinaryDetailDto;
import vn.com.unit.cms.core.module.agent.dto.AgentDisciplinaryDetailSearchDto;
import vn.com.unit.cms.core.module.agent.dto.AgentDisciplinaryDto;
import vn.com.unit.cms.core.module.agent.dto.AgentDisciplinarySearchDto;
import vn.com.unit.core.res.ObjectDataRes;
import vn.com.unit.core.security.UserProfileUtils;
import vn.com.unit.dts.web.rest.common.DtsApiResponse;
import vn.com.unit.ep2p.admin.constant.ConstantCore;
import vn.com.unit.ep2p.core.constant.AppApiConstant;
import vn.com.unit.ep2p.rest.AbstractRest;
import vn.com.unit.ep2p.service.AgentDisciplinaryService;
import vn.com.unit.ep2p.utils.LangugeUtil;

@RestController
@RequestMapping(CmsApiConstant.BASE_API_URL + "/agent-disciplinary")
@Api(tags = { "Agent disciplinary" })
public class AgentDisciplinaryRest extends AbstractRest{
	private Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	AgentDisciplinaryService agentDisciplinaryService;
	
	@GetMapping(AppApiConstant.API_ANGENT_DISCIPLINARY)
	@ApiOperation("Api provides constant on systems")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "success"), @ApiResponse(code = 500, message = "Internal Server Error"),
            @ApiResponse(code = 401, message = "Unauthorized"), @ApiResponse(code = 403, message = "Forbidden"),
            @ApiResponse(code = 402601, message = "Error process type new") })
	
    public DtsApiResponse getListAgentDisciplinaryByCondition(HttpServletRequest request,
    		@RequestParam(value = ConstantCore.PAGE_SIZE, defaultValue = "10") Optional<Integer> pageSizeParam,
            @RequestParam(value = ConstantCore.PAGE, defaultValue = "1") Optional<Integer> pageParam
            , AgentDisciplinarySearchDto searchDto)  {
        long start = System.currentTimeMillis();
        try {
        	String agentCode = UserProfileUtils.getFaceMask();
        	if(StringUtils.isNotEmpty(UserProfileUtils.getFaceMask())) {
        		agentCode = UserProfileUtils.getFaceMask();
        	}
        	searchDto.setAgentCode(agentCode);
            searchDto.setPage(pageParam.get());
        	searchDto.setPageSize(pageSizeParam.get());
        	ObjectDataRes<AgentDisciplinaryDto> resObj = agentDisciplinaryService.getListAgentDisciplinaryByCondition(searchDto);
        	return this.successHandler.handlerSuccess(resObj, start, null, null);
        } catch (Exception ex) {
            return this.errorHandler.handlerException(ex, start, null, null);
        }
    }
	
	@GetMapping(AppApiConstant.API_ANGENT_DISCIPLINARY_DETAIL)
	@ApiOperation("Api provides constant on systems")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "success"), @ApiResponse(code = 500, message = "Internal Server Error"),
            @ApiResponse(code = 401, message = "Unauthorized"), @ApiResponse(code = 403, message = "Forbidden"),
            @ApiResponse(code = 402601, message = "Error process type new") })
	
    public DtsApiResponse getAgentDisciplinaryDetailByCondition(HttpServletRequest request, AgentDisciplinaryDetailSearchDto searchDto)  {
        long start = System.currentTimeMillis();
        try {
        	String agentCode = UserProfileUtils.getFaceMask();
        	if(StringUtils.isNotEmpty(UserProfileUtils.getFaceMask())) {
        		agentCode = UserProfileUtils.getFaceMask();
        	}
        	searchDto.setAgentCode(agentCode);
        	AgentDisciplinaryDetailDto data = agentDisciplinaryService.getAgentDisciplinaryDettailByCondition(searchDto);
        	return this.successHandler.handlerSuccess(data, start, null, null);
        } catch (Exception ex) {
            return this.errorHandler.handlerException(ex, start, null, null);
        }
    }
	
	@SuppressWarnings("rawtypes")
	@PostMapping(AppApiConstant.API_EXPORT_ANGENT_DISCIPLINARY)
    @ApiOperation("Api provides constant on systems")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "success"), @ApiResponse(code = 500, message = "Internal Server Error"),
            @ApiResponse(code = 401, message = "Unauthorized"), @ApiResponse(code = 403, message = "Forbidden"),
            @ApiResponse(code = 402601, message = "Error process type new") })
	
	public ResponseEntity exportExamSchedule(HttpServletRequest request,@RequestBody AgentDisciplinarySearchDto searchDto) {
		ResponseEntity resObj = null;
		try {
			Locale locale = LangugeUtil.getLanguageFromHeader(request);
			String agentCode = UserProfileUtils.getFaceMask();
			searchDto.setAgentCode(agentCode);
			resObj = agentDisciplinaryService.exportAgentDisciplinary(searchDto, locale);
		} catch (Exception e) {
			logger.error("##exportLis##", e.getMessage());
		}
		return resObj;
	}
	
}
