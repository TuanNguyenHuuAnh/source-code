package vn.com.unit.ep2p.rest.cms;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
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
import vn.com.unit.cms.core.module.branch.dto.CmsBranchDto;
import vn.com.unit.cms.core.module.customer.dto.CustomerInformationDto;
import vn.com.unit.cms.core.module.ecard.dto.ECardReqDto;
import vn.com.unit.cms.core.module.ecard.dto.ECardResDto;
import vn.com.unit.cms.core.module.favorite.dto.FavoriteSearchDto;
import vn.com.unit.cms.core.module.notify.dto.NotifyDto;
import vn.com.unit.cms.core.module.notify.dto.NotifyInputDto;
import vn.com.unit.cms.core.module.notify.dto.NotifyReadDto;
import vn.com.unit.cms.core.module.notify.dto.NotifySearchLike;
import vn.com.unit.cms.core.module.notify.dto.ObjectDataResNotify;
import vn.com.unit.core.res.ObjectDataRes;
import vn.com.unit.core.security.UserProfileUtils;
import vn.com.unit.dts.web.rest.common.DtsApiResponse;
import vn.com.unit.ep2p.core.constant.AppApiConstant;
import vn.com.unit.ep2p.rest.AbstractRest;
import vn.com.unit.ep2p.service.ApiNotifyService;
import vn.com.unit.ep2p.utils.LangugeUtil;

@RestController
@RequestMapping(CmsApiConstant.BASE_API_URL + CmsApiConstant.API_CMS_NOTIFYS)
@Api(tags = { CmsApiConstant.API_CMS_NOTIFYS_DESCR })
public class NotifysRest  extends AbstractRest{

    
    @Autowired
    ApiNotifyService apiNotifyService;
    
    @GetMapping(AppApiConstant.API_NOTIFYS_LIST_BY_TYPE)
    @ApiOperation("Api provides notifys on systems")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "success"), @ApiResponse(code = 500, message = "Internal Server Error"),
            @ApiResponse(code = 401, message = "Unauthorized"), @ApiResponse(code = 403, message = "Forbidden"),
            @ApiResponse(code = 402601, message = "Error process type new") })
    
    public DtsApiResponse getListCategoryByPageType(@RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "5") Integer size
            , @RequestParam(value = "notifyType",required = false) Integer notifyType
            , @RequestParam(value = "isReadAlready",required = false) Integer isReadAlready
            , @RequestParam(value = "searchValues",required = false) String searchValues
            , @RequestParam(value = "isLike",required = false) Integer isLike
            , @RequestParam(defaultValue = "0") Integer modeView)  {
        long start = System.currentTimeMillis();
        try {
            String username = UserProfileUtils.getFaceMask();
            List<NotifyDto> datas = new ArrayList<>();
            Long agent ;
            try {
            	agent = new Long(username);
            }
            catch(Exception ex) {
            	agent = null;
            }
            int totalData = apiNotifyService.countNotifyByType(agent, notifyType, modeView,isReadAlready, searchValues,isLike);
            NotifyReadDto dto = apiNotifyService.getTotalUnread(agent);
            if (totalData > 0) {
                datas = apiNotifyService.getListNotifyByType(page, size, agent, notifyType, modeView,isReadAlready, searchValues,isLike);
            }
            ObjectDataResNotify<NotifyDto> resObj = new ObjectDataResNotify<>(dto.getTotalMessageUnread(),totalData, datas);
            
            return this.successHandler.handlerSuccess(resObj, start, null, null);
        } catch (Exception ex) {
            return this.errorHandler.handlerException(ex, start, null, null);//SelectDto
        }
    }
    
    
    @PostMapping(AppApiConstant.API_NOTIFYS_CHECK_READ)
    @ApiOperation("Api notify check read")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "success"), @ApiResponse(code = 500, message = "Internal Server Error"),
            @ApiResponse(code = 401, message = "Unauthorized"), @ApiResponse(code = 403, message = "Forbidden")})

    public DtsApiResponse notifyCheckRead(HttpServletRequest request
            , @RequestParam(value = "notifyId") Long notifyId
            , @RequestParam(value = "agentCode") String agentCode
            , @RequestParam(value = "isReadAlready") Integer isReadAlready)  {
        long start = System.currentTimeMillis();
        try {
            String username = UserProfileUtils.getFaceMask();
        	  Long agent;
        	  try {
              	agent = new Long(username);
              }
              catch(Exception ex) {
              	agent = null;
              }
        	    	  
            NotifyReadDto resObj = apiNotifyService.saveNotifyCheckRead(notifyId,agent,isReadAlready);
            return this.successHandler.handlerSuccess(resObj, start, null, null);
        } catch (Exception ex) {
            return this.errorHandler.handlerException(ex, start, null, null);
        }
    }
    
    @PostMapping(AppApiConstant.API_ADD_NOTIFY_AUTO)
    @ApiOperation("Api add notify auto")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "success"),
    		@ApiResponse(code = 500, message = "Internal Server Error"),
            @ApiResponse(code = 401, message = "Unauthorized"), 
            @ApiResponse(code = 403, message = "Forbidden")})
    public DtsApiResponse addNotifyByType(HttpServletRequest request,@RequestBody List<NotifyInputDto> dto) {
        long start = System.currentTimeMillis();
        Locale locale = LangugeUtil.getLanguageFromHeader(request);
        try {
            String message = apiNotifyService.addNotifyByType(dto);
            return this.successHandler.handlerSuccess(message, start, null, null);
        } catch (Exception ex) {
            return this.errorHandler.handlerException(ex, start, null, null);
        }
    }
    
	@PostMapping(AppApiConstant.API_LIKE_MESSAGE)
    @ApiOperation("Api add like")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "success"), @ApiResponse(code = 500, message = "Internal Server Error"),
            @ApiResponse(code = 401, message = "Unauthorized"),
            @ApiResponse(code = 4027100, message = "Error link exists favorite"),
            @ApiResponse(code = 403, message = "Forbidden")})

    public DtsApiResponse addLike(HttpServletRequest request, @RequestBody NotifySearchLike  dto)  {
        long start = System.currentTimeMillis();
        try {
        	boolean dtoResult = apiNotifyService.likeMessage(dto);
            return this.successHandler.handlerSuccess(dtoResult, start, null, null);
        } catch (Exception ex) {
            return this.errorHandler.handlerException(ex, start, null, null);
        }
    }
}
