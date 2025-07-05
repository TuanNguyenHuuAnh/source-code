package vn.com.unit.ep2p.rest.cms;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import vn.com.unit.cms.core.constant.CmsApiConstant;
import vn.com.unit.cms.core.module.ecard.dto.ECardReqDto;
import vn.com.unit.cms.core.module.ecard.dto.ECardResDto;
import vn.com.unit.core.res.ObjectDataRes;
import vn.com.unit.dts.web.rest.common.DtsApiResponse;
import vn.com.unit.ep2p.core.constant.AppApiConstant;
import vn.com.unit.ep2p.rest.AbstractRest;
import vn.com.unit.ep2p.service.ApiECardService;
import vn.com.unit.ep2p.utils.LangugeUtil;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Locale;

@RestController
@RequestMapping(CmsApiConstant.BASE_API_URL + CmsApiConstant.API_CMS_E_CARD)
@Api(tags = { CmsApiConstant.API_CMS_E_CARD_DESCR })
public class ECardRest extends AbstractRest{
	
	@Autowired
    ApiECardService apiECardService;

	@PostMapping(AppApiConstant.API_E_CARD_MANAGEMENT)
    @ApiOperation("Api generate e card")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "success"), @ApiResponse(code = 500, message = "Internal Server Error"),
            @ApiResponse(code = 401, message = "Unauthorized"), @ApiResponse(code = 403, message = "Forbidden")})

    public DtsApiResponse eCardManagement(HttpServletRequest request, @RequestBody ECardReqDto eCardReqDto)  {
        long start = System.currentTimeMillis();
        Locale locale = LangugeUtil.getLanguageFromHeader(request);
        try {
        	List<ECardResDto> datas = apiECardService.geranateECard(eCardReqDto);
            ObjectDataRes<ECardResDto> resObj = new ObjectDataRes<>(datas.size(), datas);
            return this.successHandler.handlerSuccess(resObj, start, null, null);
        } catch (Exception ex) {
            return this.errorHandler.handlerException(ex, start, null, null);
        }
    }

}
