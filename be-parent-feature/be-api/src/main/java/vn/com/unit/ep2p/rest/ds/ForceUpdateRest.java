package vn.com.unit.ep2p.rest.ds;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import vn.com.unit.dts.exception.ErrorHandler;
import vn.com.unit.dts.exception.SuccessHandler;
import vn.com.unit.dts.web.rest.common.DtsApiResponse;
import vn.com.unit.ep2p.core.constant.AppApiConstant;
import vn.com.unit.ep2p.core.ds.dto.VersionDto;
import vn.com.unit.ep2p.service.VersionService;

@RestController
@RequestMapping(AppApiConstant.API_V1 + AppApiConstant.DS_APP)
@Api(tags = { AppApiConstant.API_FORCE_UPDATE_QUESTION_DESCR })
public class ForceUpdateRest {
	
	@Autowired
	protected ErrorHandler errorHandler;

	@Autowired
	protected SuccessHandler successHandler;
	
	@Autowired
	private VersionService service;
	
	@Autowired
	MessageSource messageSource;

	private Logger log = LoggerFactory.getLogger(getClass());
	
    @GetMapping(AppApiConstant.API_GET_FORCE_UPDATE)
    @ApiOperation("Get all version")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "success"), @ApiResponse(code = 500, message = "Internal Server Error"),
            @ApiResponse(code = 401, message = "Unauthorized"), @ApiResponse(code = 403, message = "Forbidden")})
    public DtsApiResponse getListVersion(HttpServletRequest request, @RequestParam(value = "platform", required = false) String platform)  {
        long start = System.currentTimeMillis();
        log.info("Begin getListVersion at: ", start);
        try {
            List<VersionDto> result = service.getAllVersion(platform);
            return this.successHandler.handlerSuccess(result, start, null, null);
        } catch (Exception ex) {
            return this.errorHandler.handlerException(ex, start, null, null);
        }
    }
}
