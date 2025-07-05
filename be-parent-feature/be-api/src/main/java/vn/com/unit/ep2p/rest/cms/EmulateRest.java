package vn.com.unit.ep2p.rest.cms;

import java.util.List;
import java.util.Locale;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
import vn.com.unit.cms.core.module.emulate.dto.EmulateResultSearchResultDto;
import vn.com.unit.cms.core.module.emulate.dto.EmulateSearchDto;
import vn.com.unit.cms.core.module.emulate.dto.EmulationChallengeSearchDto;
import vn.com.unit.cms.core.module.emulate.dto.resp.EmulateResp;
import vn.com.unit.cms.core.module.emulate.dto.resp.EmulateRespData;
import vn.com.unit.core.res.ObjectDataRes;
import vn.com.unit.core.security.UserProfileUtils;
import vn.com.unit.dts.exception.DetailException;
import vn.com.unit.dts.web.rest.common.DtsApiResponse;
import vn.com.unit.core.config.SystemConfig;
import vn.com.unit.ep2p.admin.constant.ConstantCore;
import vn.com.unit.ep2p.core.constant.AppApiConstant;
import vn.com.unit.ep2p.core.constant.AppApiExceptionCodeConstant;
import vn.com.unit.ep2p.rest.AbstractRest;
import vn.com.unit.ep2p.service.ApiAgentDetailService;
import vn.com.unit.ep2p.service.ApiEmulateService;
import vn.com.unit.ep2p.utils.LangugeUtil;

@RestController
@RequestMapping(CmsApiConstant.BASE_API_URL + CmsApiConstant.API_CMS_EMULATE)
@Api(tags = { "Emulate" })
public class EmulateRest extends AbstractRest{
	
	@Autowired
    private SystemConfig systemConfig;
	
	@Autowired
	private ApiEmulateService emulateService;

	@Autowired
	ApiAgentDetailService apiAgentDetailService;
	
	private Logger logger = LoggerFactory.getLogger(getClass());
	
	@GetMapping(AppApiConstant.API_EMULATE_PROGRAM)//db99
    @ApiOperation("List emulate")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "success"), @ApiResponse(code = 500, message = "Internal Server Error"),
            @ApiResponse(code = 401, message = "Unauthorized"), @ApiResponse(code = 403, message = "Forbidden"),
            @ApiResponse(code = 402601, message = "Error process type new")})
	
    public DtsApiResponse getVideoHomePublic(HttpServletRequest request,
    		@RequestParam(defaultValue = "0") Integer modeView
    		, @RequestParam(value = ConstantCore.PAGE_SIZE, defaultValue = "5") Optional<Integer> pageSizeParam
    		, @RequestParam(value = ConstantCore.PAGE, defaultValue = "1") Optional<Integer> pageParam){
    	long start = System.currentTimeMillis();
        try {
            Locale locale = LangugeUtil.getLanguageFromHeader(request);
            EmulateSearchDto searchDto = new EmulateSearchDto();
            String agentCode = UserProfileUtils.getFaceMask();
        	if(StringUtils.isNotEmpty(UserProfileUtils.getFaceMask())) {
        		agentCode = UserProfileUtils.getFaceMask();
        	}
        	searchDto.setAgentCode(agentCode);
            searchDto.setLanguageCode(locale.toString());
            int pageSize = pageSizeParam
                    .orElse(systemConfig.getIntConfig(SystemConfig.PAGING_SIZE, UserProfileUtils.getCompanyId()));
            int page = pageParam.orElse(1);
            Pageable pageable = PageRequest.of(page - 1, pageSize);
            int count = emulateService.countListEmulateInMonth(searchDto, modeView);
            int total = 0;
            if(count>0) {
            	total = count;
            }
            List<EmulateResp> data = emulateService.getListEmulateInMonth(searchDto, pageable, modeView);
            EmulateResp focus = emulateService.getHotEmulateInMonth(searchDto);
            EmulateRespData<EmulateResp> resObj = new EmulateRespData<>(total, focus, data);
        	return this.successHandler.handlerSuccess(resObj, start, null, null);
        }catch (Exception ex) {
            return this.errorHandler.handlerException(ex, start, null, null);
        }
    }
	
    @GetMapping(AppApiConstant.API_EMULATE_CONTEST_TYPE)
    @ApiOperation("List emulate group")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "success"), @ApiResponse(code = 500, message = "Internal Server Error"),
            @ApiResponse(code = 401, message = "Unauthorized"), @ApiResponse(code = 403, message = "Forbidden"),
            @ApiResponse(code = 402601, message = "Error process type new")})
    
    public DtsApiResponse getListContestType(HttpServletRequest request){
        long start = System.currentTimeMillis();
        try {
            List<EmulateSearchDto> data = emulateService.findByContestType();
            return this.successHandler.handlerSuccess(data, start, null, null);
        }catch (Exception ex) {
            return this.errorHandler.handlerException(ex, start, null, null);
        }
    }

	//1. ds td/tt
	@GetMapping(AppApiConstant.API_EMULATE_AND_CHELLENGE_PERSONAL)
    @ApiOperation("List emulate personal")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "success"), @ApiResponse(code = 500, message = "Internal Server Error"),
            @ApiResponse(code = 401, message = "Unauthorized"), @ApiResponse(code = 403, message = "Forbidden"),
            @ApiResponse(code = 402601, message = "Error process type new")})
	
    public DtsApiResponse getEmulateAndChallengePersonal(HttpServletRequest request
    		, EmulationChallengeSearchDto searchDto){
    	long start = System.currentTimeMillis();
        try {
	        String agentParent = UserProfileUtils.getFaceMask();
	        if (StringUtils.isNotEmpty(agentParent) && !agentParent.equalsIgnoreCase(searchDto.getAgentCode())) {
	            boolean isChild = apiAgentDetailService.checkAgentChild(agentParent, searchDto.getAgentCode());
	            if (!isChild) {
	                throw new DetailException(AppApiExceptionCodeConstant.E4027100_APPAPI_LINK_EXISTS_ERROR);
	            }
	        }
            ObjectDataRes<EmulateResultSearchResultDto> resObj = emulateService.getEmulateAndChallengePersonal(searchDto);
        	return this.successHandler.handlerSuccess(resObj, start, null, null);
        }catch (Exception ex) {
            return this.errorHandler.handlerException(ex, start, null, null);
        }
    }
	
	//2. ds result td/ tt
    @GetMapping(AppApiConstant.API_EMULATE_AND_CHELLENGE_RESULT_PERSONAL)
    @ApiOperation("List emulate personal")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "success"), @ApiResponse(code = 500, message = "Internal Server Error"),
            @ApiResponse(code = 401, message = "Unauthorized"), @ApiResponse(code = 403, message = "Forbidden"),
            @ApiResponse(code = 402601, message = "Error process type new")})
    
    public DtsApiResponse getResultEmulateAndChallengePersonal(HttpServletRequest request
            , EmulationChallengeSearchDto searchDto){
        long start = System.currentTimeMillis();
        try {

	        String agentParent = UserProfileUtils.getFaceMask();
	        if (StringUtils.isNotEmpty(agentParent) && !agentParent.equalsIgnoreCase(searchDto.getAgentCode())) {
	            boolean isChild = apiAgentDetailService.checkAgentChild(agentParent, searchDto.getAgentCode());
	            if (!isChild) {
	                throw new DetailException(AppApiExceptionCodeConstant.E4027100_APPAPI_LINK_EXISTS_ERROR);
	            }
	        }
            ObjectDataRes<EmulateResultSearchResultDto> resObj = emulateService.getListEmulateAndChallengeResultPersonal(searchDto);
            return this.successHandler.handlerSuccess(resObj, start, null, null);
        }catch (Exception ex) {
            return this.errorHandler.handlerException(ex, start, null, null);
        }
    }
	//3. export list person
    @SuppressWarnings("rawtypes")
    @PostMapping(AppApiConstant.API_EXPORT_EMULATE_AND_CHELLENGE_PERSONAL)
    @ApiOperation("Api provides constant on systems")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "success"), @ApiResponse(code = 500, message = "Internal Server Error"),
            @ApiResponse(code = 401, message = "Unauthorized"), @ApiResponse(code = 403, message = "Forbidden"),
            @ApiResponse(code = 402601, message = "Error process type new") })
    public ResponseEntity exportEmulatePersonal(HttpServletRequest request
            , @RequestBody EmulationChallengeSearchDto searchDto) {
        ResponseEntity resObj = null;
        try {
            searchDto.setPage(0);
            searchDto.setSize(0);
            resObj = emulateService.exportEmulateAndChellengePersonal(searchDto);
        } catch (Exception e) {
            logger.error("##exportEmulatePersonal##", e);
        }
        return resObj;
    }
    
   //4. exprot result person
    @SuppressWarnings("rawtypes")
    @PostMapping(AppApiConstant.API_EXPORT_EMULATE_AND_CHELLENGE_RESULT_PERSONAL)
    @ApiOperation("Api provides constant on systems")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "success"), @ApiResponse(code = 500, message = "Internal Server Error"),
            @ApiResponse(code = 401, message = "Unauthorized"), @ApiResponse(code = 403, message = "Forbidden"),
            @ApiResponse(code = 402601, message = "Error process type new") })
    public ResponseEntity exportEmulateResultPersonal(HttpServletRequest request
            , @RequestBody EmulationChallengeSearchDto searchDto) {
        ResponseEntity resObj = null;
        try {
            searchDto.setPage(0);
            searchDto.setSize(0);
            resObj = emulateService.exportEmulateAndChellengePersonalResult(searchDto);
        } catch (Exception e) {
            logger.error("##exportLis##", e);
        }
        return resObj;
    }


    //1. ds td/tt group
    @GetMapping(AppApiConstant.API_EMULATE_AND_CHELLENGE_GROUP)
    @ApiOperation("List emulate personal")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "success"), @ApiResponse(code = 500, message = "Internal Server Error"),
            @ApiResponse(code = 401, message = "Unauthorized"), @ApiResponse(code = 403, message = "Forbidden"),
            @ApiResponse(code = 402601, message = "Error process type new")})
    
    public DtsApiResponse getEmulateAndChallengeGroup(HttpServletRequest request
            , EmulationChallengeSearchDto searchDto){
        long start = System.currentTimeMillis();
        try {
	        String agentParent = UserProfileUtils.getFaceMask();
	        if (StringUtils.isNotEmpty(agentParent) && !agentParent.equalsIgnoreCase(searchDto.getAgentCode())) {
	            boolean isChild = apiAgentDetailService.checkAgentChild(agentParent, searchDto.getAgentCode());
	            if (!isChild) {
	                throw new DetailException(AppApiExceptionCodeConstant.E4027100_APPAPI_LINK_EXISTS_ERROR);
	            }
	        }
            ObjectDataRes<EmulateResultSearchResultDto> resObj = emulateService.getEmulateAndChallengeGroup(searchDto);
            return this.successHandler.handlerSuccess(resObj, start, null, null);
        }catch (Exception ex) {
            return this.errorHandler.handlerException(ex, start, null, null);
        }
    }
    
    //2. ds result td/tt group
    @GetMapping(AppApiConstant.API_EMULATE_AND_CHELLENGE_RESULT_GROUP)
    @ApiOperation("List emulate personal")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "success"), @ApiResponse(code = 500, message = "Internal Server Error"),
            @ApiResponse(code = 401, message = "Unauthorized"), @ApiResponse(code = 403, message = "Forbidden"),
            @ApiResponse(code = 402601, message = "Error process type new")})
    
    public DtsApiResponse getEmulateAndChallengeResultGroup(HttpServletRequest request
            , EmulationChallengeSearchDto searchDto){
        long start = System.currentTimeMillis();
        try {
            ObjectDataRes<EmulateResultSearchResultDto> resObj = emulateService.getEmulateAndChallengeResultGroupSumary(searchDto);
            return this.successHandler.handlerSuccess(resObj, start, null, null);
        }catch (Exception ex) {
            return this.errorHandler.handlerException(ex, start, null, null);
        }
    }
    
  //3. export td/tt group
    @SuppressWarnings("rawtypes")
    @PostMapping(AppApiConstant.API_EXPORT_EMULATE_AND_CHELLENGE_GROUP)
    @ApiOperation("Api provides constant on systems")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "success"), @ApiResponse(code = 500, message = "Internal Server Error"),
            @ApiResponse(code = 401, message = "Unauthorized"), @ApiResponse(code = 403, message = "Forbidden"),
            @ApiResponse(code = 402601, message = "Error process type new") })
    public ResponseEntity exportEmulateGroup(HttpServletRequest request
            ,@RequestBody EmulationChallengeSearchDto searchDto) {
        ResponseEntity resObj = null;
        try {
            resObj = emulateService.exportEmulateAndChellengeGroup(searchDto);
        } catch (Exception e) {
            logger.error("##exportLis##", e);
        }
        return resObj;
    }
    
    //4. export result td/tt group
    @SuppressWarnings("rawtypes")
    @PostMapping(AppApiConstant.API_EXPORT_EMULATE_AND_CHELLENGE_RESULT_GROUP)
    @ApiOperation("Api provides constant on systems")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "success"), @ApiResponse(code = 500, message = "Internal Server Error"),
            @ApiResponse(code = 401, message = "Unauthorized"), @ApiResponse(code = 403, message = "Forbidden"),
            @ApiResponse(code = 402601, message = "Error process type new") })
    
    public ResponseEntity exportEmulateResultGroup(HttpServletRequest request
            ,@RequestBody EmulationChallengeSearchDto searchDto) {
        ResponseEntity resObj = null;
        try {
            resObj = emulateService.exportEmulateAndChellengeGroupResult(searchDto);
        } catch (Exception e) {
            logger.error("##exportLis##", e);
        }
        return resObj;
    }
    
    //5. result ga
    @GetMapping(AppApiConstant.API_EMULATE_AND_CHELLENGE_RESULT_GA)
    @ApiOperation("List emulate personal")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "success"), @ApiResponse(code = 500, message = "Internal Server Error"),
            @ApiResponse(code = 401, message = "Unauthorized"), @ApiResponse(code = 403, message = "Forbidden"),
            @ApiResponse(code = 402601, message = "Error process type new")})
    
    public DtsApiResponse getEmulateAndChallengeResultGA(HttpServletRequest request
            , EmulationChallengeSearchDto searchDto){
        long start = System.currentTimeMillis();
        try {
            ObjectDataRes<EmulateResultSearchResultDto> resObj = emulateService.getEmulateAndChallengeResultGa(searchDto);
            return this.successHandler.handlerSuccess(resObj, start, null, null);
        }catch (Exception ex) {
            return this.errorHandler.handlerException(ex, start, null, null);
        }
    }

    //4. export result td/tt group
    @SuppressWarnings("rawtypes")
    @PostMapping(AppApiConstant.API_EXPORT_EMULATE_AND_CHELLENGE_RESULT_GA)
    @ApiOperation("Api provides constant on systems")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "success"), @ApiResponse(code = 500, message = "Internal Server Error"),
            @ApiResponse(code = 401, message = "Unauthorized"), @ApiResponse(code = 403, message = "Forbidden"),
            @ApiResponse(code = 402601, message = "Error process type new") })
    
    public ResponseEntity exportEmulateAndChallengeResultGA(HttpServletRequest request
            ,@RequestBody EmulationChallengeSearchDto searchDto) {
        ResponseEntity resObj = null;
        try {
            resObj = emulateService.exportEmulateAndChallengeResultGa(searchDto);
        } catch (Exception e) {
            logger.error("##exportLis##", e);
        }
        return resObj;
    }
    
    @GetMapping(AppApiConstant.API_CHECK_AGENT_CHILD_IN_GROUP)
    @ApiOperation("List emulate personal")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "success"), @ApiResponse(code = 500, message = "Internal Server Error"),
            @ApiResponse(code = 401, message = "Unauthorized"), @ApiResponse(code = 403, message = "Forbidden"),
            @ApiResponse(code = 402601, message = "Error process type new")})
    public DtsApiResponse checkAgentChildInGroup(
            String territory, String region, String area, String office, String agentParent, String agentChild){
        long start = System.currentTimeMillis();
        try {
            boolean resObj = emulateService.checkAgentChild(territory, region, area, office, agentParent, agentChild);
            return this.successHandler.handlerSuccess(resObj, start, null, null);
        }catch (Exception ex) {
            return this.errorHandler.handlerException(ex, start, null, null);
        }
    }
    
}
