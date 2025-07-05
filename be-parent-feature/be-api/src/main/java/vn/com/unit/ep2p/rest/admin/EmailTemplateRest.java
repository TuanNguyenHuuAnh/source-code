/*******************************************************************************
 * Class        ：EmailTemplateRest
 * Created date ：2020/12/23
 * Lasted date  ：2020/12/23
 * Author       ：SonND
 * Change log   ：2020/12/23：01-00 SonND create a new
 ******************************************************************************/
package vn.com.unit.ep2p.rest.admin;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import springfox.documentation.annotations.ApiIgnore;
import vn.com.unit.core.dto.JcaEmailTemplateDto;
import vn.com.unit.core.res.ObjectDataRes;
import vn.com.unit.dts.web.rest.common.DtsApiResponse;
import vn.com.unit.ep2p.core.constant.AppApiConstant;
import vn.com.unit.ep2p.core.req.dto.EmailTemplateAddInfoReq;
import vn.com.unit.ep2p.core.req.dto.EmailTemplateUpdateInfoReq;
import vn.com.unit.ep2p.core.res.dto.EnumsParamSearchRes;
import vn.com.unit.ep2p.rest.AbstractRest;
import vn.com.unit.ep2p.service.EmailTemplateService;
import vn.com.unit.ep2p.service.PagingService;

/**
 * EmailTemplateRest
 * 
 * @version 01-00
 * @since 01-00
 * @author SonND
 */

@RestController
@RequestMapping(AppApiConstant.API_V1 + AppApiConstant.API_ADMIN)
@Api(tags = {AppApiConstant.API_ADMIN_EMAIL_TEMPLATE_DESCR})
public class EmailTemplateRest extends AbstractRest{
	
	@Autowired
	PagingService pagingService;	
	
	@Autowired
	private EmailTemplateService emailTemplateService;
	
	@GetMapping(AppApiConstant.API_ADMIN_EMAIL_TEMPLATE)
	@ApiOperation("List of email template")
	@ApiResponses(value = {
	            @ApiResponse(code = 200 , message = "Success"),
	            @ApiResponse(code = 401 , message = "Unauthorized"),
	            @ApiResponse(code = 402 , message = "Forbidden"),
	            @ApiResponse(code = 4022801 , message = "Error process list email template"),
	            @ApiResponse(code = 500 , message = "Internal server error"),})
	  @ApiImplicitParams({
	      @ApiImplicitParam(name = "keySearch", value = "Send RM" , required = false, dataTypeClass = String.class, paramType = "query"),
          @ApiImplicitParam(name = "companyId", value = "1", dataTypeClass = String.class, paramType = "query"),
          @ApiImplicitParam(name = "actived", value = "true", dataType = "boolean", dataTypeClass = Boolean.class, paramType = "query"),
          @ApiImplicitParam(name = "multipleSeachEnums", allowMultiple = true, dataType = "string", paramType = "query", value = "Column CODE, TEMPLATE_NAME, CREATED_BY"),
          
          @ApiImplicitParam(name = "page", dataType = "string", paramType = "query", value = "Results page you want to retrieve (0..N)"),
          @ApiImplicitParam(name = "size", dataType = "string", paramType = "query", value = "Number of records per page."),
          @ApiImplicitParam(name = "sort", allowMultiple = true, dataType = "string", paramType = "query", value = "Sorting criteria in the format: property(,asc|desc). "
                  + "Default sort order is ascending. " + "Multiple sort criteria are supported. Sort about:ID, CREATED_ID, CREATED_DATE, UPDATED_ID,UPDATED_DATE. Example: ID,asc") 
   })
	
	public DtsApiResponse listEmailTemplate(@ApiIgnore @RequestParam MultiValueMap<String, String> requestParams, @ApiIgnore Pageable pageable) {
        long start = System.currentTimeMillis();
        try {
            /** example structure return object data have extends to add properties */
            /**END*/
            ObjectDataRes<JcaEmailTemplateDto> resObj = emailTemplateService.search(requestParams,pageable);
            return this.successHandler.handlerSuccess(resObj, start);
        } catch (Exception ex) {
            return this.errorHandler.handlerException(ex, start);
        }
    }
	
	@PostMapping(AppApiConstant.API_ADMIN_EMAIL_TEMPLATE)
	@ApiOperation("Create email template")
	@ApiResponses(value = {
	            @ApiResponse(code = 200 , message = "Success"),
	            @ApiResponse(code = 401 , message = "Unauthorized"),
	            @ApiResponse(code = 402 , message = "Forbidden"),
	            @ApiResponse(code = 4021203 , message = "Error not found role for company"),
	            @ApiResponse(code = 4022802 , message = "Error process add email template"),
	            @ApiResponse(code = 500 , message = "Internal server error"),})
	public DtsApiResponse addEmailTemplate(@ApiParam(name = "body", value = "Email template information to add new") @RequestBody EmailTemplateAddInfoReq emailTemplateAddInfoReq) {
        long start = System.currentTimeMillis();
        try {
            JcaEmailTemplateDto resObj = emailTemplateService.create(emailTemplateAddInfoReq);
            return this.successHandler.handlerSuccess(resObj, start);
        }catch (Exception ex) {
            return this.errorHandler.handlerException(ex, start);
        }
    }
	
	@PutMapping(AppApiConstant.API_ADMIN_EMAIL_TEMPLATE)
	@ApiOperation("Update email template")
	@ApiResponses(value = {
	            @ApiResponse(code = 200 , message = "Success"),
	            @ApiResponse(code = 401 , message = "Unauthorized"),
	            @ApiResponse(code = 402 , message = "Forbidden"),
                @ApiResponse(code = 4022804 , message = "Error not found email template"),
	            @ApiResponse(code = 4021203 , message = "Error not found company"),
	            @ApiResponse(code = 4022803 , message = "Error process update email template"),
	            @ApiResponse(code = 500 , message = "Internal server error"),
	            })
	public DtsApiResponse updateEmailTemplate(@ApiParam(name = "body", value = "Email template information to update")@RequestBody EmailTemplateUpdateInfoReq emailTemplateUpdateInfoReq) {
        long start = System.currentTimeMillis();
        try {
            emailTemplateService.update(emailTemplateUpdateInfoReq);
            return this.successHandler.handlerSuccess(null, start);
        }catch (Exception ex) {
            return this.errorHandler.handlerException(ex, start);
        }
    }
	
	@DeleteMapping(AppApiConstant.API_ADMIN_EMAIL_TEMPLATE)
	@ApiOperation("Delete email template")
	@ApiResponses(value = {
	            @ApiResponse(code = 200 , message = "Success"),
	            @ApiResponse(code = 401 , message = "Unauthorized"),
	            @ApiResponse(code = 402 , message = "Forbidden"),
	            @ApiResponse(code = 4022804 , message = "Error not found email template"),
	            @ApiResponse(code = 4022805 , message = "Error process delete email template"),
	            @ApiResponse(code = 500 , message = "Internal server error"),})
	public DtsApiResponse deleteEmailTemplate(@ApiParam(name = "emailTemplateId", value = "Delete email template on system by id", example = "1")@RequestParam("emailTemplateId") Long emailTemplateId) {
        long start = System.currentTimeMillis();
        try {
            emailTemplateService.delete(emailTemplateId);
            return this.successHandler.handlerSuccess(null, start);
        }catch (Exception ex) {
            return this.errorHandler.handlerException(ex, start);
        }
    }
	
	@GetMapping(AppApiConstant.API_ADMIN_EMAIL_TEMPLATE + "/{emailTemplateId}") 
	@ApiOperation("Detail of email template")
	@ApiResponses(value = {
	            @ApiResponse(code = 200 , message = "Success"),
	            @ApiResponse(code = 401 , message = "Unauthorized"),
	            @ApiResponse(code = 402 , message = "Forbidden"),
	            @ApiResponse(code = 4022804 , message = "Error not found email template"),
	            @ApiResponse(code = 500 , message = "Internal server error"),})
	public DtsApiResponse detailAccount(@ApiParam(name = "emailTemplateId", value = "Get email template information detail on system by id" , example = "1")@PathVariable("emailTemplateId") Long emailTemplateId) {
        long start = System.currentTimeMillis();
        try {
            JcaEmailTemplateDto resObj = emailTemplateService.detail(emailTemplateId);
            return this.successHandler.handlerSuccess(resObj, start);
        }catch (Exception ex) {
            return this.errorHandler.handlerException(ex, start);
        }
    }
	
	@GetMapping(AppApiConstant.API_ADMIN_EMAIL_TEMPLATE + "/enums")
    @ApiOperation("List enum search")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "Success"), 
            @ApiResponse(code = 401, message = "Unauthorized"), 
            @ApiResponse(code = 402, message = "Forbidden"),
            @ApiResponse(code = 500, message = "Internal server error"),})
    public DtsApiResponse getEnumsSearch() {
        long start = System.currentTimeMillis();
        try {
            List<EnumsParamSearchRes> results = emailTemplateService.getListEnumSearch();
            return this.successHandler.handlerSuccess(results, start);
        } catch (Exception ex) {
            return this.errorHandler.handlerException(ex, start);
        }
    }
	
}
