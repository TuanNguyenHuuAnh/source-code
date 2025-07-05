package vn.com.unit.ep2p.rest.ds;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.ObjectUtils;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import vn.com.unit.core.security.jwt.TokenProvider;
import vn.com.unit.dts.web.rest.common.DtsApiResponse;
import vn.com.unit.ep2p.core.constant.AppApiConstant;
import vn.com.unit.ep2p.dto.req.AccountQuestionReq;
import vn.com.unit.ep2p.dto.req.QuestionForgotPasswordReq;
import vn.com.unit.ep2p.dto.res.AccountLoginRes;
import vn.com.unit.ep2p.rest.AbstractRest;
import vn.com.unit.ep2p.service.ApiDsAccountQuestionService;
import vn.com.unit.ep2p.utils.LangugeUtil;

/**
 * @author TaiTM
 **/
@RestController
@RequestMapping(AppApiConstant.API_V1 + AppApiConstant.DS_APP)
@Api(tags = { AppApiConstant.API_ACCOUNT_QUESTION_DESCR })
public class AccountQuestionRest extends AbstractRest {
    
    @Autowired
    private ApiDsAccountQuestionService apiDsAccountQuestionService;
    
    @Autowired
    private TokenProvider tokenProvider;
    
    @PostMapping("/save-account-question")
    @ApiOperation("Save or Update question security for account")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "Success"), 
            @ApiResponse(code = 401, message = "Unauthorized"), 
            @ApiResponse(code = 402, message = "Forbiddwen"),
            @ApiResponse(code = 500, message = "Internal server error"),})
    public DtsApiResponse saveOrUpdateQuestion(
            @ApiParam(name = "body", value = "Account security question") @RequestBody AccountQuestionReq accountQuestionReq,
            HttpServletRequest request) {
        long start = System.currentTimeMillis();
        try {
        	//get user login by token
        	String authorizationHeaderValue = request.getHeader("Authorization");
            Locale locale = LangugeUtil.getLanguageFromHeader(request);
            Authentication authentication = tokenProvider.getAuthentication(authorizationHeaderValue.replace("Bearer ", ""), locale);
            SecurityContextHolder.getContext().setAuthentication(authentication);

            apiDsAccountQuestionService.saveOrUpdateAccountQuestion(accountQuestionReq);
            return this.successHandler.handlerSuccess(null, start);
        } catch (Exception ex) {
            return this.errorHandler.handlerException(ex, start);
        }
    }
    
    @GetMapping("/get-account-question")
    @ApiOperation("Get Security Question of Account")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "Success"), 
            @ApiResponse(code = 401, message = "Unauthorized"), 
            @ApiResponse(code = 402, message = "Forbidden"),
            @ApiResponse(code = 500, message = "Internal server error"),})
    public DtsApiResponse getAccountQuestion(@RequestParam("userId") Long userId, HttpServletRequest request) {
        long start = System.currentTimeMillis();
        try {
        	//get user login by token
        	String authorizationHeaderValue = request.getHeader("Authorization");
            Locale locale = LangugeUtil.getLanguageFromHeader(request);
            Authentication authentication = tokenProvider.getAuthentication(authorizationHeaderValue.replace("Bearer ", ""), locale);
            SecurityContextHolder.getContext().setAuthentication(authentication);
            return this.successHandler.handlerSuccess(apiDsAccountQuestionService.getQuestionForgotPasswordRes(userId),
                    start);
        } catch (Exception ex) {
            return this.errorHandler.handlerException(ex, start);
        }
    }
    //check question
    @GetMapping("/validate-question")
    @ApiOperation("Get Security Question of Account")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "Success"),
            @ApiResponse(code = 401, message = "Unauthorized"),
            @ApiResponse(code = 402, message = "Forbidden"),
            @ApiResponse(code = 500, message = "Internal server error"),})
    public DtsApiResponse checkQuestionMapping(QuestionForgotPasswordReq condition, HttpServletRequest request) {
        long start = System.currentTimeMillis();
        try {
            return this.successHandler.handlerSuccess(apiDsAccountQuestionService.checkQuestionMapping(condition),
                    start);
        } catch (Exception ex) {
            return this.errorHandler.handlerException(ex, start);
        }
    }
}
