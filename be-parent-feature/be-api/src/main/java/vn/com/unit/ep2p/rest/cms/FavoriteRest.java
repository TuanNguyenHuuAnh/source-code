package vn.com.unit.ep2p.rest.cms;

import java.util.List;
import java.util.Locale;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import vn.com.unit.cms.core.constant.CmsApiConstant;
import vn.com.unit.cms.core.dto.CmsCommonPagination;
import vn.com.unit.cms.core.module.favorite.dto.FavoriteResult;
import vn.com.unit.cms.core.module.favorite.dto.FavoriteSearchDto;
import vn.com.unit.cms.core.module.favorite.dto.FavoriteSearchResultDto;
import vn.com.unit.cms.core.module.favorite.dto.ObjectDataResFavoriteByAgentCode;
import vn.com.unit.cms.core.module.favorite.entity.Favorite;
import vn.com.unit.core.res.ObjectDataRes;
import vn.com.unit.core.security.UserProfileUtils;
import vn.com.unit.dts.web.rest.common.DtsApiResponse;
import vn.com.unit.ep2p.admin.constant.ConstantCore;
import vn.com.unit.ep2p.core.constant.AppApiConstant;
import vn.com.unit.ep2p.core.dto.QaCodeDto;
import vn.com.unit.ep2p.core.dto.QaCodeRes;
import vn.com.unit.ep2p.core.utils.RetrofitUtils;
import vn.com.unit.ep2p.rest.AbstractRest;
import vn.com.unit.ep2p.service.ApiFavoriteService;
import vn.com.unit.ep2p.service.ParseJsonToParamSearchService;
import vn.com.unit.ep2p.utils.LangugeUtil;

@RestController
@RequestMapping(CmsApiConstant.BASE_API_URL + CmsApiConstant.API_CMS_FAVORITE)
@Api(tags = { CmsApiConstant.API_CMS_FAVORITE_DESCR })
public class FavoriteRest  extends AbstractRest{
	
	@Autowired
	ApiFavoriteService apiFavoriteService;
	
	private Logger logger = LoggerFactory.getLogger(getClass());
	@Autowired
	ParseJsonToParamSearchService parseJsonToParamSearchService;
    
    @GetMapping(AppApiConstant.LIST)
    @ApiOperation("Api provides a list favorite on systems")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "success"),
            @ApiResponse(code = 500, message = "Internal Server Error"),
            @ApiResponse(code = 401, message = "Unauthorized"), @ApiResponse(code = 403, message = "Forbidden"),
            @ApiResponse(code = 402601, message = "Error process list favorite") })

    public DtsApiResponse doGetListByAgentCode(HttpServletRequest request,
    		@RequestParam(value = "newVersion", defaultValue = "0") String newVersion) {
        long start = System.currentTimeMillis();
        try {
        	String agentCode  = UserProfileUtils.getFaceMask();
        	List<FavoriteSearchResultDto> datas = null;
        	if ("1".equals(newVersion)) {
        		datas = apiFavoriteService.getListFavoriteByAgentCode(agentCode);
        	} else {
        		datas = apiFavoriteService.getListByAgentCode(agentCode);
        	}
 			for(FavoriteSearchResultDto ls: datas) {
 		 		if(StringUtils.isBlank(ls.getType())) {
 		 			ls.setType("");
        		}
        		if(StringUtils.isBlank(ls.getTitle())){
        			ls.setTitle("");
        		}
        		if(StringUtils.isBlank(ls.getLink()))  {
        			ls.setLink("");
        		}
        		if(StringUtils.isBlank(ls.getIcon())) {
        			ls.setIcon("");
        		}
 			}
        	ObjectDataResFavoriteByAgentCode<FavoriteSearchResultDto> resObj = new ObjectDataResFavoriteByAgentCode<>(datas);
            return this.successHandler.handlerSuccess(resObj, start, null, null);
        } catch (Exception ex) {
            return this.errorHandler.handlerException(ex, start, null, null);
        }
    }
    
	@PostMapping(AppApiConstant.API_FAVORITE_ADD)
    @ApiOperation("Api add favorite")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "success"), @ApiResponse(code = 500, message = "Internal Server Error"),
            @ApiResponse(code = 401, message = "Unauthorized"),
            @ApiResponse(code = 4027100, message = "Error link exists favorite"),
            @ApiResponse(code = 403, message = "Forbidden")})

    public DtsApiResponse addFavorites(HttpServletRequest request, @RequestBody List<FavoriteSearchDto>  dto)  {
        long start = System.currentTimeMillis();
        try {
        	String agentCode  = UserProfileUtils.getFaceMask();
        	boolean dtoResult = apiFavoriteService.addFavoritesByAgentCode(dto,agentCode);
            return this.successHandler.handlerSuccess(dtoResult, start, null, null);
        } catch (Exception ex) {
            return this.errorHandler.handlerException(ex, start, null, null);
        }
    }
	@PostMapping(AppApiConstant.API_FAVORITE_INSERT)
    @ApiOperation("Api add favorite")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "success"), @ApiResponse(code = 500, message = "Internal Server Error"),
            @ApiResponse(code = 401, message = "Unauthorized"),
            @ApiResponse(code = 4027100, message = "Error link exists favorite"),
            @ApiResponse(code = 403, message = "Forbidden")})

    public DtsApiResponse addFavorite(HttpServletRequest request, @RequestBody FavoriteSearchDto  dto)  {
        long start = System.currentTimeMillis();
        try {
        	String agentCode  = UserProfileUtils.getFaceMask();
        	FavoriteResult data = apiFavoriteService.addFavoriteByAgentCode(dto,agentCode);
            return this.successHandler.handlerSuccess(data, start, null, null);
        } catch (Exception ex) {
            return this.errorHandler.handlerException(ex, start, null, null);
        }
    }
	
	@DeleteMapping(AppApiConstant.API_FAVORITE_DELETE)
    @ApiOperation("Api delete favorite")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "success"), @ApiResponse(code = 500, message = "Internal Server Error"),
            @ApiResponse(code = 401, message = "Unauthorized"), @ApiResponse(code = 403, message = "Forbidden")})

    public DtsApiResponse deleteFavoriteById(HttpServletRequest request, @RequestParam(value = "id") Long id)  {
        long start = System.currentTimeMillis();
        try {
        	apiFavoriteService.deleteFavoriteById(id);
            ObjectDataRes<FavoriteSearchDto> resObj = new ObjectDataRes<>(0, null);
            return this.successHandler.handlerSuccess(resObj, start, null, null);
        } catch (Exception ex) {
            return this.errorHandler.handlerException(ex, start, null, null);
        }
    }
	@DeleteMapping(AppApiConstant.API_FAVORITE_DELETE_BY_IDS)
    @ApiOperation("Api delete favorite")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "success"), @ApiResponse(code = 500, message = "Internal Server Error"),
            @ApiResponse(code = 401, message = "Unauthorized"), @ApiResponse(code = 403, message = "Forbidden")})

    public DtsApiResponse deleteFavoriteByIds(HttpServletRequest request, @RequestParam(value = "id") List<Long> id)  {
        long start = System.currentTimeMillis();
        try {
        	apiFavoriteService.deleteFavoriteByIds(id);
            ObjectDataRes<FavoriteSearchDto> resObj = new ObjectDataRes<>(0, null);
            return this.successHandler.handlerSuccess(resObj, start, null, null);
        } catch (Exception ex) {
            return this.errorHandler.handlerException(ex, start, null, null);
        }
    }
	@GetMapping(AppApiConstant.API_FAVORITE_DETAIL)
    @ApiOperation("Api provides a detail News on systems")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "success"), @ApiResponse(code = 500, message = "Internal Server Error"),
            @ApiResponse(code = 401, message = "Unauthorized"), @ApiResponse(code = 403, message = "Forbidden"),
            @ApiResponse(code = 402601, message = "Error process detail new") })
	
    public DtsApiResponse detailFavoriteByLink(HttpServletRequest request,
    		@ApiParam(name = "itemId", value = "Get detail new by function code", example = "1") @RequestParam("itemId") String itemId)  {
        long start = System.currentTimeMillis();
        try {
            Locale locale = LangugeUtil.getLanguageFromHeader(request);
        	String agentCode  = UserProfileUtils.getFaceMask();
            Favorite resObj = apiFavoriteService.detailFavorite(itemId, agentCode);
            return this.successHandler.handlerSuccess(resObj, start, null, null);
        } catch (Exception ex) {
            return this.errorHandler.handlerException(ex, start, null, null);
        }
    }
	@GetMapping(AppApiConstant.LIST_BY_CONDITION)
    @ApiOperation("Api provides a list favorite on systems")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "success"),
            @ApiResponse(code = 500, message = "Internal Server Error"),
            @ApiResponse(code = 401, message = "Unauthorized"), @ApiResponse(code = 403, message = "Forbidden"),
            @ApiResponse(code = 402601, message = "Error process list favorite") })

    public DtsApiResponse doGetListByCondition(HttpServletRequest request, @RequestParam(defaultValue = "0") Integer modeView,
    		@RequestParam(value = ConstantCore.PAGE_SIZE, defaultValue = "10") Optional<Integer> pageSizeParam,
            @RequestParam(value = ConstantCore.PAGE, defaultValue = "1") Optional<Integer> pageParam
            , FavoriteSearchDto searchDto)  {
        long start = System.currentTimeMillis();
        try {
        	Locale locale = LangugeUtil.getLanguageFromHeader(request);
        	String agentCode  = UserProfileUtils.getFaceMask();
        	ObjectMapper mapper = new ObjectMapper();
        	searchDto.setPage(pageParam.get());
        	searchDto.setFunctionCode("FAVORITE");
        	searchDto.setAgentCode(StringUtils.isNotEmpty(searchDto.getAgentCode()) ? searchDto.getAgentCode() : agentCode);
        	searchDto.setPageSize(pageSizeParam.get());
        	searchDto.setLanguage(locale.getLanguage());
        	String stringJsonParam = mapper.writeValueAsString(searchDto);
        	CmsCommonPagination<FavoriteSearchResultDto> common = apiFavoriteService.doGetListFavoriteByCondition(stringJsonParam);
        	ObjectDataRes<FavoriteSearchResultDto> resObj = new ObjectDataRes<>(common.getTotalData(), common.getData());
            return this.successHandler.handlerSuccess(resObj, start, null, null);
        } catch (Exception ex) {
            return this.errorHandler.handlerException(ex, start, null, null);
        }
    }
	
	@SuppressWarnings("rawtypes")
	@PostMapping(AppApiConstant.API_EXPORT_FAVORITE)
    @ApiOperation("Api provides constant on systems")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "success"), @ApiResponse(code = 500, message = "Internal Server Error"),
            @ApiResponse(code = 401, message = "Unauthorized"), @ApiResponse(code = 403, message = "Forbidden"),
            @ApiResponse(code = 402601, message = "Error process type new") })
	
	public ResponseEntity exportFavorite(HttpServletRequest request) {
		ResponseEntity resObj = null;
		try {
			Locale locale = LangugeUtil.getLanguageFromHeader(request);
			resObj = apiFavoriteService.exportFavorite(locale);
		} catch (Exception e) {
			logger.error("##exportLis##", e.getMessage());
		}
		return resObj;
	}

	@PostMapping(AppApiConstant.GEN_QA_CODE)
    @ApiOperation("Api provides a list library masterial on systems")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "success"), @ApiResponse(code = 500, message = "Internal Server Error"),
    @ApiResponse(code = 401, message = "Unauthorized"), @ApiResponse(code = 403, message = "Forbidden"), })
    public DtsApiResponse genQaCode(@RequestBody QaCodeDto dto)  {
        long start = System.currentTimeMillis();
        try {
        	QaCodeRes qaCode = RetrofitUtils.getQaCode(dto);
            
            return this.successHandler.handlerSuccess(qaCode, start, null, null);
        } catch (Exception ex) {
            return this.errorHandler.handlerException(ex, start, null, null);
        }
    }
}
