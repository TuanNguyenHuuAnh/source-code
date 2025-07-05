package vn.com.unit.ep2p.rest.cms;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
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
import vn.com.unit.cms.core.module.banner.dto.BannerSearchDto;
import vn.com.unit.cms.core.module.banner.dto.resp.BannerResp;
import vn.com.unit.cms.core.module.banner.dto.resp.BannerSearchResultDto;
import vn.com.unit.core.security.UserProfileUtils;
import vn.com.unit.dts.web.rest.common.DtsApiResponse;
import vn.com.unit.core.config.SystemConfig;
import vn.com.unit.ep2p.admin.constant.ConstantCore;
import vn.com.unit.ep2p.rest.AbstractRest;
import vn.com.unit.ep2p.service.CmsBannerService;
import vn.com.unit.ep2p.utils.LangugeUtil;

@RestController
@RequestMapping(CmsApiConstant.BASE_API_URL + CmsApiConstant.API_CMS_BANNER)
@Api(tags = { CmsApiConstant.API_CMS_BANNER_DESCR })
public class BannerRest extends AbstractRest {

    @Autowired
    private CmsBannerService cmsBannerService;
    
    @Autowired
    private SystemConfig systemConfig;
    
    @SuppressWarnings("deprecation")
    @GetMapping("/list")
    @ApiOperation("Banner list")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "success"),
            @ApiResponse(code = 401700, message = "Username is required."),
            @ApiResponse(code = 401701, message = "Password is required."),
            @ApiResponse(code = 401702, message = "Username does not exist."),
            @ApiResponse(code = 401703, message = "The system is required."),
            @ApiResponse(code = 401704, message = "The system does not exist."),
            @ApiResponse(code = 401705, message = "The system expires. Please contact the administrator to support."),
            @ApiResponse(code = 401706, message = "Account locked."),
            @ApiResponse(code = 401707, message = "Account deactivated."),
            @ApiResponse(code = 401708, message = "Login failed."),
            @ApiResponse(code = 500, message = "Internal Server Error"),
            @ApiResponse(code = 401, message = "Unauthorized"), @ApiResponse(code = 402, message = "Forbidden") })
//    @Cacheable(value = "bannerListCache", key = "{ #searchDto.code, #searchDto.name, #searchDto.mobileEnable, "
//                                                + "#searchDto.pcEnable, #searchDto.bannerTop, #searchDto.bannerMiddle, #searchDto.status, "
//                                                + "#searchDto.statusName, #searchDto.languageCode, #searchDto.page, #searchDto.size, #searchDto.sort}")
    public DtsApiResponse list(HttpServletRequest request, @RequestParam(defaultValue = "0") Integer modeView,
    		@RequestParam(value = ConstantCore.PAGE_SIZE, defaultValue = "10") Optional<Integer> pageSizeParam,
            @RequestParam(value = ConstantCore.PAGE, defaultValue = "1") Optional<Integer> pageParam,
            @RequestParam(value = "environment", defaultValue = "mobile") String environmentParam,
            @RequestParam(value = "screen", defaultValue = "PUBLIC") String screenParam,
            @RequestParam(value = "channel", defaultValue = "AG") String channel) {

        long start = System.currentTimeMillis();
        
        try {
        	// environmentParam --> mobile/pc
        	// screenParam --> homepage/activity/news
        	
            Locale locale = LangugeUtil.getLanguageFromHeader(request);
            
            int pageSize = pageSizeParam
                    .orElse(systemConfig.getIntConfig(SystemConfig.PAGING_SIZE, UserProfileUtils.getCompanyId()));
            int page = pageParam.orElse(1);
            
            BannerSearchDto searchDto = new BannerSearchDto();
            searchDto.setLanguageCode(locale.toString());
            
			if ("mobile".equalsIgnoreCase(environmentParam)) {
				searchDto.setBannerDevice("2");
			} else {
				searchDto.setBannerDevice("1");
			}
			searchDto.setBannerPage(screenParam);
			
            Pageable pageable = PageRequest.of(page - 1, pageSize);
            
            String animationSlider = cmsBannerService.getAnimationSlider(searchDto, locale.toString());
            String slideTime = cmsBannerService.getSlideTime(searchDto, locale.toString());
            List<BannerResp> data = cmsBannerService.searchByCondition(searchDto, pageable, modeView, channel);
            List<BannerResp> dataReturn = new ArrayList<>();
            if(CollectionUtils.isNotEmpty(data)) {
				String bannerTopId = ("mobile".equalsIgnoreCase(environmentParam))
						? data.get(0).getStringBannerTopMobileId()
						: data.get(0).getStringBannerTopId();
            	List<String> lstBanner = Arrays.asList(bannerTopId.split(ConstantCore.COMMA));
            	for(String banner: lstBanner) {
                	for(BannerResp bannerTop: data) {
                		if(banner.equalsIgnoreCase(bannerTop.getBannerId())) {
                			dataReturn.add(bannerTop);
                		}
                	}
            	}
            	
            }
            if ("LeftToRight".equalsIgnoreCase(animationSlider) || "TopToBottom".equalsIgnoreCase(animationSlider)) {
            	Collections.reverse(dataReturn); // qua nguoc list
            }
            BannerSearchResultDto<BannerResp> resObj = new BannerSearchResultDto<BannerResp>(animationSlider, slideTime, dataReturn);
            
            return this.successHandler.handlerSuccess(resObj, start);
        } catch (Exception ex) {
            return this.errorHandler.handlerException(ex, start);
        }
    }
    
    
    @GetMapping("/video-home-public")
    @ApiOperation("Video home public")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "success"), @ApiResponse(code = 500, message = "Internal Server Error"),
            @ApiResponse(code = 401, message = "Unauthorized"), @ApiResponse(code = 403, message = "Forbidden"),
            @ApiResponse(code = 402601, message = "Error process type new") })
	
    public DtsApiResponse getVideoHomePublic(HttpServletRequest request, @RequestParam(defaultValue = "0") Integer modeView,
            @RequestParam(value = "environment", defaultValue = "mobile") String environmentParam,
            @RequestParam(value = "screen", defaultValue = "PUBLIC") String screenParam)  {
    	long start = System.currentTimeMillis();
        try {
            Locale locale = LangugeUtil.getLanguageFromHeader(request);
            
            BannerSearchDto searchDto = new BannerSearchDto();
            searchDto.setLanguageCode(locale.toString());
            
			if ("mobile".equalsIgnoreCase(environmentParam)) {
				searchDto.setBannerDevice("2");
			} else {
				searchDto.setBannerDevice("1");
			}
			searchDto.setBannerPage(screenParam);

            BannerResp data = cmsBannerService.getBannerVideo(searchDto, locale.toString());
            
            return this.successHandler.handlerSuccess(data, start);
        }catch (Exception ex) {
            return this.errorHandler.handlerException(ex, start, null, null);
        }
    }
}
