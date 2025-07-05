package vn.com.unit.ep2p.rest.cms;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;
import vn.com.unit.cms.core.constant.CmsApiConstant;
import vn.com.unit.cms.core.module.notify.dto.*;
import vn.com.unit.common.constant.CommonConstant;
import vn.com.unit.common.utils.CommonStringUtil;
import vn.com.unit.dts.web.rest.common.DtsApiResponse;
import vn.com.unit.ep2p.core.constant.AppApiConstant;
import vn.com.unit.ep2p.rest.AbstractRest;
import vn.com.unit.ep2p.service.ApiNotifyService;
import vn.com.unit.ep2p.utils.LangugeUtil;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@RestController
@RequestMapping(CmsApiConstant.BASE_API_URL + CmsApiConstant.API_CMS_POPUP)
@Api(tags = { "Popup maintain" })
public class PopupRest extends AbstractRest{

    
    @Autowired
    ApiNotifyService apiNotifyService;

	@GetMapping("/get")
    @ApiOperation("get detail Popup maintain")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "success"), @ApiResponse(code = 500, message = "Internal Server Error"),
            @ApiResponse(code = 401, message = "Unauthorized"),
            @ApiResponse(code = 4027100, message = "Error link exists favorite"),
            @ApiResponse(code = 403, message = "Forbidden")})

    public DtsApiResponse addLike(HttpServletRequest request, String code)  {
        long start = System.currentTimeMillis();
        try {
        	String result = apiNotifyService.getTemplatePopupMaintain(code);
            return this.successHandler.handlerSuccess(unescapeTemplateHtml(result), start, null, null);
        } catch (Exception ex) {
            return this.errorHandler.handlerException(ex, start, null, null);
        }
    }
    public String unescapeTemplateHtml(String templateHtmlEscape) {
        if(CommonStringUtil.isNotBlank(templateHtmlEscape)) {
            templateHtmlEscape = templateHtmlEscape.replaceAll(CommonConstant.ESCAPE_QUOT, CommonConstant.CHAR_DOUBLE_QUOTATION_MARK+ CommonConstant.EMPTY);
            templateHtmlEscape = templateHtmlEscape.replaceAll(CommonConstant.ESCAPE_AMP, CommonConstant.CHAR_AMPERSAND+ CommonConstant.EMPTY);
            templateHtmlEscape = templateHtmlEscape.replaceAll(CommonConstant.ESCAPE_LT, CommonConstant.CHAR_IS_LESS_THAN+ CommonConstant.EMPTY);
            templateHtmlEscape = templateHtmlEscape.replaceAll(CommonConstant.ESCAPE_GT, CommonConstant.CHAR_IS_MORE_THAN+ CommonConstant.EMPTY);
            return templateHtmlEscape;
        }else {
            return templateHtmlEscape;
        }
    }
}
