package vn.com.unit.ep2p.rest.cms;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import vn.com.unit.cms.core.constant.CmsApiConstant;
import vn.com.unit.cms.core.module.faqs.dto.FaqsCategoryLangDto;
import vn.com.unit.cms.core.module.faqs.dto.FaqsSearchDto;
import vn.com.unit.cms.core.module.faqs.dto.FaqsSearchResultDto;
import vn.com.unit.core.res.ObjectDataRes;
import vn.com.unit.core.security.UserProfileUtils;
import vn.com.unit.dts.web.rest.common.DtsApiResponse;
import vn.com.unit.core.config.SystemConfig;
import vn.com.unit.ep2p.admin.constant.ConstantCore;
import vn.com.unit.ep2p.core.constant.AppApiConstant;
import vn.com.unit.ep2p.core.utils.FileUtil;
import vn.com.unit.ep2p.rest.AbstractRest;
import vn.com.unit.ep2p.service.ApiFaqsService;
import vn.com.unit.ep2p.utils.LangugeUtil;

@RestController
@RequestMapping(CmsApiConstant.BASE_API_URL + CmsApiConstant.API_CMS_FAQS)
@Api(tags = { CmsApiConstant.API_CMS_FAQS_DESCR })
public class FaqsRest extends AbstractRest {

    @Autowired
    private ApiFaqsService apiFaqsService;
    
    @Autowired
    private SystemConfig systemConfig;

    @GetMapping(AppApiConstant.LIST)
    @ApiOperation("Api provides a list Faqs on systems")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "success"),
            @ApiResponse(code = 500, message = "Internal Server Error"),
            @ApiResponse(code = 401, message = "Unauthorized"), @ApiResponse(code = 403, message = "Forbidden"),
            @ApiResponse(code = 402601, message = "Error process list faqs") })

    public DtsApiResponse doGetList(HttpServletRequest request, @RequestParam(defaultValue = "1") Integer modeView,
            @RequestParam(value = ConstantCore.PAGE_SIZE, defaultValue = "5") Optional<Integer> pageSizeParam,
            @RequestParam(value = ConstantCore.PAGE, defaultValue = "1") Optional<Integer> pageParam) {
        long start = System.currentTimeMillis();
        Locale locale = LangugeUtil.getLanguageFromHeader(request);
        try {
            List<FaqsSearchResultDto> datas = new ArrayList<>();
            FaqsSearchDto searchDto = new FaqsSearchDto();
            searchDto.setLanguageCode(locale.toString());

            int pageSize = pageSizeParam
                    .orElse(systemConfig.getIntConfig(SystemConfig.PAGING_SIZE, UserProfileUtils.getCompanyId()));
            int page = pageParam.orElse(1);

            Pageable pageable = PageRequest.of(page - 1, pageSize);

            int count = apiFaqsService.countByCondition(searchDto, modeView);
            if (count > 0) {
                datas = apiFaqsService.searchByCondition(searchDto, pageable, modeView);
            }

            ObjectDataRes<FaqsSearchResultDto> resObj = new ObjectDataRes<>(count, datas);
            return this.successHandler.handlerSuccess(resObj, start, null, null);
        } catch (Exception ex) {
            return this.errorHandler.handlerException(ex, start, null, null);
        }
    }
    
    
	@GetMapping(AppApiConstant.API_CATEROGY_FAQS_BY_ID)
    @ApiOperation("Api provides a list Faqs on systems")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "success"), @ApiResponse(code = 500, message = "Internal Server Error"),
            @ApiResponse(code = 401, message = "Unauthorized"), @ApiResponse(code = 403, message = "Forbidden"),
            @ApiResponse(code = 402601, message = "Error process faq category by id") })
	
    public DtsApiResponse getListByIdCategory(HttpServletRequest request,
    		@RequestParam(value = "searchKey") String searchKey
    	   ,@RequestParam(value = "idCategory") Long idCategory	  
    	   ,@RequestParam(defaultValue = "1") Integer page, @RequestParam(defaultValue = "5") Integer size
    	   ,@RequestParam(defaultValue = "0") Integer modeView)  {
        long start = System.currentTimeMillis();
        Locale locale = LangugeUtil.getLanguageFromHeader(request);
        try {
        	List<FaqsSearchResultDto> datas = new ArrayList<>();
        	searchKey = FileUtil.deAccent(searchKey).toLowerCase().replace(" ", "-");
        	int count = apiFaqsService.countFaqsByIdCategory(searchKey,idCategory, modeView, locale.getLanguage());
            if (count > 0) {
                datas = apiFaqsService.searchFaqsByIdCategory(searchKey, idCategory, page, size, locale.getLanguage(), modeView);
            }
            
            ObjectDataRes<FaqsSearchResultDto> resObj = new ObjectDataRes<>(count, datas);
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
	
    public DtsApiResponse getListCategoryFaqs(HttpServletRequest request, @RequestParam(defaultValue = "0") Integer modeView)  {
        long start = System.currentTimeMillis();
        try {
            Locale locale = LangugeUtil.getLanguageFromHeader(request);
        	List<FaqsCategoryLangDto> resObj = apiFaqsService.getListCategoryFaqs(locale.getLanguage(), modeView);
            return this.successHandler.handlerSuccess(resObj, start, null, null);
        } catch (Exception ex) {
            return this.errorHandler.handlerException(ex, start, null, null);
        }
    }
}
