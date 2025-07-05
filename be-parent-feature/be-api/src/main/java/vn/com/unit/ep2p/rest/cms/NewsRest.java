package vn.com.unit.ep2p.rest.cms;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;
//import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
//import io.swagger.annotations.ApiImplicitParam;
//import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
//import springfox.documentation.annotations.ApiIgnore;
import vn.com.unit.cms.core.constant.CmsApiConstant;
import vn.com.unit.cms.core.module.news.dto.FeNewsSearchDto;
import vn.com.unit.cms.core.module.news.dto.NewsByTypeSearchDto;
import vn.com.unit.cms.core.module.news.dto.NewsEventResp;
import vn.com.unit.cms.core.module.news.dto.NewsHomeResp;
import vn.com.unit.cms.core.module.news.dto.NewsSearchResultDto;
import vn.com.unit.cms.core.module.news.dto.NewsTypeSearchAllPage;
import vn.com.unit.cms.core.module.news.entity.News;
import vn.com.unit.common.utils.CommonCollectionUtil;
import vn.com.unit.core.dto.JcaSystemConfigDto;
import vn.com.unit.core.res.ObjectDataRes;
import vn.com.unit.dts.web.rest.common.DtsApiResponse;
import vn.com.unit.ep2p.core.constant.AppApiConstant;
//import vn.com.unit.ep2p.dto.res.AccountInfoRes;
import vn.com.unit.ep2p.rest.AbstractRest;
import vn.com.unit.ep2p.service.ApiNewsService;
import vn.com.unit.ep2p.service.SystemConfigService;
import vn.com.unit.ep2p.utils.LangugeUtil;

@RestController
@RequestMapping(CmsApiConstant.BASE_API_URL + CmsApiConstant.API_CMS_NEWS)
@Api(tags = { CmsApiConstant.API_CMS_NEWS_DESCR })
public class NewsRest extends AbstractRest{
	
	@Autowired
	ApiNewsService newService;
    
    @Autowired
    private SystemConfigService systemConfigService;
	 
	@GetMapping(AppApiConstant.API_NEWS_LIST)
    @ApiOperation("Api provides a list News on systems")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "success"), @ApiResponse(code = 500, message = "Internal Server Error"),
            @ApiResponse(code = 401, message = "Unauthorized"), @ApiResponse(code = 403, message = "Forbidden"),
            @ApiResponse(code = 402601, message = "Error process list new") })

    public DtsApiResponse listNews(HttpServletRequest request, FeNewsSearchDto searchDto, @RequestParam(defaultValue = "1") Integer page,
			@RequestParam(defaultValue = "5") Integer size, @RequestParam(defaultValue = "0") Integer modeView, @RequestParam(defaultValue = "AG") String channel)  {
        long start = System.currentTimeMillis();
        Locale locale = LangugeUtil.getLanguageFromHeader(request);
		try {
		    JcaSystemConfigDto userGuide = systemConfigService.getConfigByKey("USERGUIDE", 2L);
			List<NewsSearchResultDto> entityHotNews = newService.getNewsHot(locale.getLanguage(),
					searchDto.getMNewsCategoryId());
			if(StringUtils.isNotEmpty(searchDto.getCodeNewsType()) && searchDto.getCodeNewsType().equalsIgnoreCase(userGuide.getSettingKey())) {
                searchDto.setMNewsCategoryId(Long.valueOf(userGuide.getSettingValue()));
            }
			if (CommonCollectionUtil.isNotEmpty(entityHotNews)) {
				if (searchDto.getHomepage() != null && searchDto.getHomepage() == 1) {
					if ("NEWS".equalsIgnoreCase(searchDto.getCodeNewsType())) {
						searchDto.setIdHotNews(entityHotNews.get(0).getId());
						size = 10;
					}
				}
			}
			List<NewsSearchResultDto> datas = new ArrayList<>();
			int count = newService.countByCondition(searchDto, modeView, locale.getLanguage(), channel);
			if (count > 0) {
				datas = newService.searchByCondition(searchDto, page, size, locale.getLanguage(), modeView, channel);
			}
			NewsHomeResp<NewsSearchResultDto> resObj = new NewsHomeResp<NewsSearchResultDto>(entityHotNews, count,
					datas);
			return this.successHandler.handlerSuccess(resObj, start, null, null);
		} catch (Exception ex) {
			ex.printStackTrace();
			return this.errorHandler.handlerException(ex, start, null, null);
		}
    }
	@GetMapping(AppApiConstant.LIST_ACTIVITY_ENABLED)
    @ApiOperation("Api provides a list News on systems")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "success"), @ApiResponse(code = 500, message = "Internal Server Error"),
            @ApiResponse(code = 401, message = "Unauthorized"), @ApiResponse(code = 403, message = "Forbidden"),
            @ApiResponse(code = 402601, message = "Error process list new") })

    public DtsApiResponse getAllEnableByTypeCode(HttpServletRequest request, FeNewsSearchDto searchDto, @RequestParam(defaultValue = "0") Integer modeView)  {
        long start = System.currentTimeMillis();
        Locale locale = LangugeUtil.getLanguageFromHeader(request);
		try {
			List<NewsSearchResultDto> resObj = newService.getAllEnableByTypeCode(searchDto.getCodeNewsType(), locale.getLanguage());
			
			return this.successHandler.handlerSuccess(resObj, start, null, null);
		} catch (Exception ex) {
			ex.printStackTrace();
			return this.errorHandler.handlerException(ex, start, null, null);
		}
    }
	
	@GetMapping(AppApiConstant.API_NEWS_DETAIL+ "/{id}")
    @ApiOperation("Api provides a detail News on systems")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "success"), @ApiResponse(code = 500, message = "Internal Server Error"),
            @ApiResponse(code = 401, message = "Unauthorized"), @ApiResponse(code = 403, message = "Forbidden"),
            @ApiResponse(code = 402601, message = "Error process detail new") })
	
    public DtsApiResponse listDetailNews(HttpServletRequest request,
    		@ApiParam(name = "id", value = "Get detail new by id", example = "1") @PathVariable("id") Long id)  {
        long start = System.currentTimeMillis();
        try {
            Locale locale = LangugeUtil.getLanguageFromHeader(request);
        	NewsSearchResultDto resObj = newService.getDetailListNew(id, locale.getLanguage());
            return this.successHandler.handlerSuccess(resObj, start, null, null);
        } catch (Exception ex) {
            return this.errorHandler.handlerException(ex, start, null, null);
        }
    }
	
//	@GetMapping(AppApiConstant.API_NEWS_TYPE)
//    @ApiOperation("Api provides a type News on systems")
//    @ApiResponses(value = { @ApiResponse(code = 200, message = "success"), @ApiResponse(code = 500, message = "Internal Server Error"),
//            @ApiResponse(code = 401, message = "Unauthorized"), @ApiResponse(code = 403, message = "Forbidden"),
//            @ApiResponse(code = 402601, message = "Error process type new") })
//	
//    public DtsApiResponse listNewsType()  {
//        long start = System.currentTimeMillis();
//        try {
//        	Long[] typeIds = {100000L, 100049L};
//        	List<NewsLanguageSearchDto> resObj = newService.getListLabelNewsType(typeIds);
//            return this.successHandler.handlerSuccess(resObj, start, null, null);
//        } catch (Exception ex) {
//            return this.errorHandler.handlerException(ex, start, null, null);
//        }
//    }
	
	@GetMapping(AppApiConstant.API_NEWS_BY_TYPE)
    @ApiOperation("Api provides a type News on systems")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "success"), @ApiResponse(code = 500, message = "Internal Server Error"),
            @ApiResponse(code = 401, message = "Unauthorized"), @ApiResponse(code = 403, message = "Forbidden"),
            @ApiResponse(code = 402601, message = "Error process type new") })
	
    public DtsApiResponse listNewsByTypeIds(Long[] typeIds)  {
        long start = System.currentTimeMillis();
        try {
        	List<News> resObj = newService.getListNewsByTypeIds(typeIds);
            return this.successHandler.handlerSuccess(resObj, start, null, null);
        } catch (Exception ex) {
            return this.errorHandler.handlerException(ex, start, null, null);
        }
    }
	
	@GetMapping(AppApiConstant.API_NEWS_TYPE_BY_LANG)
    @ApiOperation("Api provides a category on systems")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "success"), @ApiResponse(code = 500, message = "Internal Server Error"),
            @ApiResponse(code = 401, message = "Unauthorized"), @ApiResponse(code = 403, message = "Forbidden"),
            @ApiResponse(code = 402601, message = "Error process type new") })
	
    public DtsApiResponse getListPageType(HttpServletRequest request, @RequestParam(defaultValue = "0") Integer modeView, @RequestParam(defaultValue = "AG") String channel)  {
        long start = System.currentTimeMillis();
        try {
        	 Locale locale = LangugeUtil.getLanguageFromHeader(request);
        	List<NewsSearchResultDto> resObj = newService.getListPageType(locale.getLanguage(), modeView, channel);
            return this.successHandler.handlerSuccess(resObj, start, null, null);
        } catch (Exception ex) {
            return this.errorHandler.handlerException(ex, start, null, null);
        }
    }
	
	@GetMapping(AppApiConstant.API_NEWS_DETAIL_BY_LINK+ "/{link}")
    @ApiOperation("Api provides a detail News on systems")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "success"), @ApiResponse(code = 500, message = "Internal Server Error"),
            @ApiResponse(code = 401, message = "Unauthorized"), @ApiResponse(code = 403, message = "Forbidden"),
            @ApiResponse(code = 402601, message = "Error process detail new") })
	
    public DtsApiResponse listDetailNewsByLink(HttpServletRequest request,
    		@RequestParam(defaultValue = "") String type,
    		@RequestParam(defaultValue = "") String categoryId,
    		@ApiParam(name = "link", value = "Get detail new by link", example = "1") @PathVariable("link") String link)  {
        long start = System.currentTimeMillis();
        try {
            Locale locale = LangugeUtil.getLanguageFromHeader(request);
        	NewsSearchResultDto resObj = newService.detailNewsByLink(link, type, categoryId, locale.getLanguage());
            return this.successHandler.handlerSuccess(resObj, start, null, null);
        } catch (Exception ex) {
            return this.errorHandler.handlerException(ex, start, null, null);
        }
    }
	@GetMapping(AppApiConstant.API_NEWS_TYPE)
    @ApiOperation("Api provides a category on systems")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "success"), @ApiResponse(code = 500, message = "Internal Server Error"),
            @ApiResponse(code = 401, message = "Unauthorized"), @ApiResponse(code = 403, message = "Forbidden"),
            @ApiResponse(code = 402601, message = "Error process type new") })
	
    public DtsApiResponse getTypeByPageType(HttpServletRequest request,
    		@RequestParam(defaultValue = "NEWS") String pageType
    		, @RequestParam(defaultValue = "0") Integer modeView)  {
        long start = System.currentTimeMillis();
        try {
            Locale locale = LangugeUtil.getLanguageFromHeader(request);
        	NewsSearchResultDto resObj = newService.getTypeByPageType(pageType, modeView, locale.getLanguage());
            return this.successHandler.handlerSuccess(resObj, start, null, null);
        } catch (Exception ex) {
            return this.errorHandler.handlerException(ex, start, null, null);
        }
    }
	@GetMapping(AppApiConstant.API_NEWS_TYPE_BY_NEWS)
    @ApiOperation("Api provides all page by key on systems")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "success"), @ApiResponse(code = 500, message = "Internal Server Error"),
            @ApiResponse(code = 401, message = "Unauthorized"), @ApiResponse(code = 403, message = "Forbidden"),
            @ApiResponse(code = 402601, message = "Error process type new") })
	
    public DtsApiResponse getTypeByNewsKey(HttpServletRequest request,
    		@RequestParam(defaultValue = "NEWS") String key
    		, @RequestParam(defaultValue = "0") Integer modeView)  {
        long start = System.currentTimeMillis();
        try {
            Locale locale = LangugeUtil.getLanguageFromHeader(request);
            List<NewsTypeSearchAllPage> resObj = newService.getTypeByNewsKey(key, modeView, locale.getLanguage());
            return this.successHandler.handlerSuccess(resObj, start, null, null);
        } catch (Exception ex) {
            return this.errorHandler.handlerException(ex, start, null, null);
        }
    }
	@GetMapping(AppApiConstant.API_NEWS_LIST_BY_TYPE)
    @ApiOperation("Api provides a list News on systems")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "success"), @ApiResponse(code = 500, message = "Internal Server Error"),
            @ApiResponse(code = 401, message = "Unauthorized"), @ApiResponse(code = 403, message = "Forbidden"),
            @ApiResponse(code = 402601, message = "Error process list new") })

    public DtsApiResponse listNewsByType(HttpServletRequest request
    		, NewsByTypeSearchDto searchDto
    		, @RequestParam(defaultValue = "1") Integer page, @RequestParam(defaultValue = "5") Integer size
    		, @RequestParam(defaultValue = "0") Integer modeView)  {
        long start = System.currentTimeMillis();
        Locale locale = LangugeUtil.getLanguageFromHeader(request);
        try {
        	List<NewsSearchResultDto> datas = new ArrayList<>();
        	int count = newService.countNewsByType(searchDto, modeView, locale.getLanguage());
            if (count > 0) {
                datas = newService.searchNewsByType(searchDto, page, size, locale.getLanguage(), modeView);
            }
            
            ObjectDataRes<NewsSearchResultDto> resObj = new ObjectDataRes<>(count, datas);
            return this.successHandler.handlerSuccess(resObj, start, null, null);
        } catch (Exception ex) {
            return this.errorHandler.handlerException(ex, start, null, null);
        }
    }
	
	@GetMapping(AppApiConstant.API_NEWS_CATEGORY)
    @ApiOperation("Api provides a category on systems")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "success"), @ApiResponse(code = 500, message = "Internal Server Error"),
            @ApiResponse(code = 401, message = "Unauthorized"), @ApiResponse(code = 403, message = "Forbidden"),
            @ApiResponse(code = 402601, message = "Error process type new") })
	
    public DtsApiResponse getListCategoryByPageType(@RequestParam(defaultValue = "NEWS") String pageType
    		, @RequestParam(defaultValue = "0") Integer modeView
    		, @RequestParam(defaultValue = "AG") String channel)  {
        long start = System.currentTimeMillis();
        try {
        	List<NewsSearchResultDto> resObj = newService.getListCategoryByPageType(pageType, modeView, channel);
            return this.successHandler.handlerSuccess(resObj, start, null, null);
        } catch (Exception ex) {
            return this.errorHandler.handlerException(ex, start, null, null);
        }
    }
	@GetMapping(AppApiConstant.API_NEWS_LIST_CAREER)
    @ApiOperation("Api provides a list News on systems")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "success"), @ApiResponse(code = 500, message = "Internal Server Error"),
            @ApiResponse(code = 401, message = "Unauthorized"), @ApiResponse(code = 403, message = "Forbidden"),
            @ApiResponse(code = 402601, message = "Error process list new") })

    public DtsApiResponse listNewsCareer(HttpServletRequest request, @RequestParam(defaultValue = "") String typeCode)  {
        long start = System.currentTimeMillis();
        Locale locale = LangugeUtil.getLanguageFromHeader(request);
        try {
        	List<NewsSearchResultDto> datas = newService.listNewsCareer(typeCode, locale.getLanguage());
            return this.successHandler.handlerSuccess(datas, start, null, null);
        } catch (Exception ex) {
            return this.errorHandler.handlerException(ex, start, null, null);
        }
    }

	
	@GetMapping(AppApiConstant.API_NEWS_LIST_NEWS)
    @ApiOperation("Api provides a list News on systems")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "success"), @ApiResponse(code = 500, message = "Internal Server Error"),
            @ApiResponse(code = 401, message = "Unauthorized"), @ApiResponse(code = 403, message = "Forbidden"),
            @ApiResponse(code = 402601, message = "Error process list new") })

    public DtsApiResponse listNewsUpcomingEvent(HttpServletRequest request
    		,  @RequestParam(defaultValue = "NEWS") String typeCode){
        long start = System.currentTimeMillis();
        Locale locale = LangugeUtil.getLanguageFromHeader(request);
       
        try {
        	NewsSearchResultDto entity = newService.newsHot(typeCode, locale.getLanguage());
        	Long newsId=null;
        	NewsSearchResultDto hotNews = new NewsSearchResultDto();
        	if(!ObjectUtils.isEmpty(entity)) {
        		hotNews=entity;
        		newsId=entity.getId();
        	}
        	List<NewsSearchResultDto> eventNews = newService.listNewsEvent(typeCode, locale.getLanguage(), null);
        	NewsEventResp<NewsSearchResultDto> resObj = new NewsEventResp<>(hotNews, eventNews);
            return this.successHandler.handlerSuccess(resObj, start, null, null);
        }
        
        catch (Exception ex) {
            return this.errorHandler.handlerException(ex, start, null, null);
        }
    }
	
}
