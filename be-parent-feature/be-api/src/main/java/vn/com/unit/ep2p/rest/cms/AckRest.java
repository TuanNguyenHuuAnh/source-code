package vn.com.unit.ep2p.rest.cms;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import vn.com.unit.cms.core.constant.CmsApiConstant;
import vn.com.unit.core.security.UserProfileUtils;
import vn.com.unit.dts.exception.ErrorHandler;
import vn.com.unit.dts.exception.SuccessHandler;
import vn.com.unit.dts.web.rest.common.DtsApiResponse;
import vn.com.unit.ep2p.core.dto.AckResultDto;
import vn.com.unit.ep2p.core.dto.PostGetLetterRes;
import vn.com.unit.ep2p.core.dto.PostNotificationletterRes;
import vn.com.unit.ep2p.core.res.dto.AckSubmitSearchDto;
import vn.com.unit.ep2p.core.res.dto.DocumentAPISearchDto;
import vn.com.unit.ep2p.core.res.dto.PostGetLetterSearchDto;
import vn.com.unit.ep2p.core.res.dto.PostNotificationletterSearchDto;
import vn.com.unit.ep2p.core.utils.RetrofitUtils;
import vn.com.unit.ep2p.service.PersonalInsuranceDocService;

@RestController
@RequestMapping(CmsApiConstant.BASE_API_URL + "/ack-submit-form")
@Api(tags = { "ACK" })
public class AckRest {
    @Autowired
    protected ErrorHandler errorHandler;

    @Autowired
    protected SuccessHandler successHandler;
    @Autowired
    PersonalInsuranceDocService personalInsuranceDocService;
    
    @PostMapping("eapp_SubmitForm")
    @ApiOperation("eapp_SubmitForm")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "success"),
            @ApiResponse(code = 500, message = "Internal Server Error"),
            @ApiResponse(code = 401, message = "Unauthorized"), @ApiResponse(code = 403, message = "Forbidden") })

    public DtsApiResponse ackSubmit(HttpServletRequest request
            , @RequestBody AckSubmitSearchDto searchDto) {
        long start = System.currentTimeMillis();
        try {
        	if(StringUtils.isEmpty(searchDto.getAPIToken())) {
        		searchDto.setAPIToken(UserProfileUtils.getApiToken());
        	}
        	if(StringUtils.isEmpty(searchDto.getDeviceID())) {
        		searchDto.setDeviceID(UserProfileUtils.getDeviceId());
        	}
            AckResultDto result = RetrofitUtils.ackSubmit(searchDto);
            return this.successHandler.handlerSuccess(result, start, null, null);
        } catch (Exception ex) {
            return this.errorHandler.handlerException(ex, start, null, null);
        }
    }
    
    @PostMapping("DocumentAPI")
    @ApiOperation("DocumentAPI")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "success"),
            @ApiResponse(code = 500, message = "Internal Server Error"),
            @ApiResponse(code = 401, message = "Unauthorized"), @ApiResponse(code = 403, message = "Forbidden") })

    public DtsApiResponse DocumentAPI(HttpServletRequest request
            , @RequestBody DocumentAPISearchDto searchDto) {
        long start = System.currentTimeMillis();
        try {
        	if(StringUtils.isEmpty(searchDto.getAPIToken())) {
        		searchDto.setAPIToken(UserProfileUtils.getApiToken());
        	}
        	if(StringUtils.isEmpty(searchDto.getDeviceID())) {        		
        		searchDto.setDeviceID(UserProfileUtils.getDeviceId());
        	}
            AckResultDto result = RetrofitUtils.DocumentAPI(searchDto);
            return this.successHandler.handlerSuccess(result, start, null, null);
        } catch (Exception ex) {
            return this.errorHandler.handlerException(ex, start, null, null);
        }
    }
    
    @PostMapping("PostNotificationletter")
    @ApiOperation("PostNotificationletter")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "success"),
            @ApiResponse(code = 500, message = "Internal Server Error"),
            @ApiResponse(code = 401, message = "Unauthorized"), @ApiResponse(code = 403, message = "Forbidden") })

    public DtsApiResponse PostNotificationletter(HttpServletRequest request
            , @RequestBody PostNotificationletterSearchDto searchDto) {
        long start = System.currentTimeMillis();
        try {
            PostNotificationletterRes result = RetrofitUtils.PostNotificationletter(searchDto);
            return this.successHandler.handlerSuccess(result, start, null, null);
        } catch (Exception ex) {
            return this.errorHandler.handlerException(ex, start, null, null);
        }
    }
    
    @PostMapping("PostGetLetter")
    @ApiOperation("PostGetLetter")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "success"),
            @ApiResponse(code = 500, message = "Internal Server Error"),
            @ApiResponse(code = 401, message = "Unauthorized"), @ApiResponse(code = 403, message = "Forbidden") })

    public DtsApiResponse PostGetLetter(HttpServletRequest request
            , @RequestBody PostGetLetterSearchDto searchDto) {
        long start = System.currentTimeMillis();
        try {
            PostGetLetterRes result = RetrofitUtils.PostGetLetter(searchDto);
            return this.successHandler.handlerSuccess(result, start, null, null);
        } catch (Exception ex) {
            return this.errorHandler.handlerException(ex, start, null, null);
        }
    }
    
    
}
