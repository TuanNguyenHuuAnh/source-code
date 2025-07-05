package vn.com.unit.ep2p.rest.web;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import vn.com.unit.common.api.service.ApiExternalService;
import vn.com.unit.common.api.service.RESTApiService;
import vn.com.unit.dts.web.rest.common.DtsApiResponse;
import vn.com.unit.ep2p.core.constant.AppApiConstant;
import vn.com.unit.ep2p.core.dto.CompanyListDto;
import vn.com.unit.ep2p.core.dto.CompanyListRes;
import vn.com.unit.ep2p.core.ers.service.UserService;
import vn.com.unit.ep2p.rest.AbstractRest;
import vn.com.unit.storage.service.FileStorageService;

@RestController
@RequestMapping("/api")
@Api(tags = {"Greeting Resource"},description = "Greeting Controller API test")
public class GreetingResource extends AbstractRest{

	@Autowired
	private UserService userService;
	
	@Autowired
	private FileStorageService fileStorageService;
	
	@Autowired
	private RESTApiService restApiService;
	
	@Autowired
	private ApiExternalService apiExternalService;
	
    @GetMapping("/greeting")
    @ApiOperation("Reponse testing default of API")
    @ApiResponses(value = {
            @ApiResponse(code = 0, message = "Successfully"),
            @ApiResponse(code = -99, message = "Error exception") })
    public DtsApiResponse greeting(@ApiParam("Param request testing")@RequestParam(value="name", defaultValue="World") String name, HttpServletRequest httpServletRequest) {
        long start = System.currentTimeMillis();
//        String deviceID = httpServletRequest.getHeader(AppApiConstant.JWT_DEVICE_ID);
        try {
            userService.getUserByUsername(name);
            
//            fileStorageService.upload(null , null);
                      
//            actionWorkflowService.submitDocument(1L, "BPMN", null);
            
            return this.getSuccessHandler().handlerSuccess(null, start);
        } catch (Exception ex) {
            return this.getErrorHandler().handlerException(ex, start);
        }
    }
    
    @GetMapping("/greeting2")
    public DtsApiResponse greeting2(@RequestParam(value="name", defaultValue="World") String name, HttpServletRequest httpServletRequest) {
        long start = System.currentTimeMillis();
        try {

            CompanyListDto companyListDto = new CompanyListDto();
            companyListDto.setAbstractApiExternalService(restApiService);
            companyListDto.setTimeoutSeconds(AppApiConstant.API_EXTERNAL_TIMEOUT);
            companyListDto.setHeaders(null);
            companyListDto.setHttpMethod(HttpMethod.POST);
            
            CompanyListRes resDto = apiExternalService.callAPI(companyListDto, "https://ppl-api.unit.vn/mbalApi/api/v1/company/get-all", null, CompanyListRes.class,AppApiConstant.API_PROTOCOL_REST_FULL);
            return this.successHandler.handlerSuccess(resDto, start);
        } catch (Exception ex) {
            return this.getErrorHandler().handlerException(ex, start);
        }
    }
}