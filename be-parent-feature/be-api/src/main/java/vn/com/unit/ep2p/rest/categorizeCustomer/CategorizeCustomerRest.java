package vn.com.unit.ep2p.rest.categorizeCustomer;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
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
import vn.com.unit.cms.core.dto.CmsCommonPagination;
import vn.com.unit.core.res.ObjectDataRes;
import vn.com.unit.core.security.UserProfileUtils;
import vn.com.unit.dts.exception.ErrorHandler;
import vn.com.unit.dts.exception.SuccessHandler;
import vn.com.unit.dts.web.rest.common.DtsApiResponse;
import vn.com.unit.cms.core.module.categorizeCustomer.dto.*;
import vn.com.unit.ep2p.admin.dto.Db2AgentDto;
import vn.com.unit.ep2p.core.constant.AppApiConstant;
import vn.com.unit.ep2p.service.CategorizeCustomerService;
import vn.com.unit.ep2p.utils.LangugeUtil;

/**
 * @author lmi.quan
 * SR16451 - Phrase 3 Tính năng phân loại khách hàng 
 * @createdDate 10/6/2024 
 * Routing 4 APIs
 */
@RestController
@RequestMapping(CmsApiConstant.BASE_API_URL + CmsApiConstant.API_CMS_CATEGORIZE_CUSTOMER)
@Api(tags = { CmsApiConstant.API_CMS_CATEGORIZE_CUSTOMER_DESCR })
public class CategorizeCustomerRest {
	@Autowired
	protected ErrorHandler errorHandler;
	
	@Autowired
	protected SuccessHandler successHandler;
	
	@Autowired
	private CategorizeCustomerService service;
	
	@Autowired
	MessageSource messageSource;
	
	private Logger log = LoggerFactory.getLogger(getClass());

	private static final String FUNCTION_ID = "SCREEN#FONTEND#CATEGORIZE_CUSTOMER";
	
	@GetMapping(AppApiConstant.API_LIST_CATEGORIZE_CUSTOMER)
	@ApiOperation("Get all categorize customer")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "success"),
			@ApiResponse(code = 500, message = "Internal Server Error"),
			@ApiResponse(code = 401, message = "Unauthorized"), @ApiResponse(code = 403, message = "Forbidden") })
	public DtsApiResponse getListCategorizeCustomer(HttpServletRequest request, 
			
			@ModelAttribute(value = "searchDto") CategorizeCustomerSearchDto searchDto) {
		
		log.info("Begin getActivityInfo");
		
		long start = System.currentTimeMillis();
		try {
			searchDto.setAgentCode(UserProfileUtils.getFaceMask());			
			if (!UserProfileUtils.getAuthentication().getAuthorities().contains(new SimpleGrantedAuthority(FUNCTION_ID))) {
				return this.errorHandler.handlerException(new Exception("Bạn không có quyền!"), start, null, null);
			}
			CmsCommonPagination<CategorizeCustomerDto> common = service.getAllCategorizeCustomer(searchDto);
			ObjectDataRes<CategorizeCustomerDto> resObj = new ObjectDataRes<CategorizeCustomerDto>(common.getTotalData(), common.getData());
			return this.successHandler.handlerSuccess(resObj, start, null, null);
		} catch (Exception ex) {
			return this.errorHandler.handlerException(ex, start, null, null);
		}
	}
	
	@GetMapping(AppApiConstant.API_DETAIL_CATEGORIZE_CUSTOMER)
	@ApiOperation("Get categorize customer detail by proposalNo")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "success"),
			@ApiResponse(code = 500, message = "Internal Server Error"),
			@ApiResponse(code = 401, message = "Unauthorized"), @ApiResponse(code = 403, message = "Forbidden") })
	public DtsApiResponse getActivitiDetail(HttpServletRequest request, @ModelAttribute(value = "searchDto") CategorizeCustomerSearchDto searchDto ) {
		log.info("Begin getActivitiDetail");
		long start = System.currentTimeMillis();
		try {
			if (!UserProfileUtils.getAuthentication().getAuthorities().contains(new SimpleGrantedAuthority(FUNCTION_ID))) {
				return this.errorHandler.handlerException(new Exception("Bạn không có quyền!"), start, null, null);
			}
			searchDto.setAgentCode(UserProfileUtils.getFaceMask());
			CategorizeCustomerDto res = service.getCategorizeCustomerDetail(searchDto);
			return this.successHandler.handlerSuccess(res, start, null, null);
		} catch (Exception ex) {
			return this.errorHandler.handlerException(ex, start, null, null);
		}
	}
	@PostMapping(AppApiConstant.API_EDIT_DETAIL_CATEGORIZE_CUSTOMER)
    @ApiOperation("Update")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "success"),
    		@ApiResponse(code = 500, message = "Internal Server Error"),
            @ApiResponse(code = 401, message = "Unauthorized"), 
            @ApiResponse(code = 403, message = "Forbidden") })
    public DtsApiResponse submitCategorizeCustomerDetail(HttpServletRequest request, @RequestBody submitDto sa)  {
        long start = System.currentTimeMillis();
        try {
        	if (!UserProfileUtils.getAuthentication().getAuthorities().contains(new SimpleGrantedAuthority(FUNCTION_ID))) {
				return this.errorHandler.handlerException(new Exception("Bạn không có quyền!"), start, null, null);
			}
        	int resultData = service.submitCategorizeCustomer(sa);
            return this.successHandler.handlerSuccess(resultData, start, null, null);
        } catch (Exception ex) {
            return this.errorHandler.handlerException(ex, start, null, null);
        }
    }
	
	@GetMapping(AppApiConstant.API_GET_LIST_AGENT)
	@ApiOperation("Get categorize customer detail by proposalNo")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "success"),
			@ApiResponse(code = 500, message = "Internal Server Error"),
			@ApiResponse(code = 401, message = "Unauthorized"), @ApiResponse(code = 403, message = "Forbidden") })
	public DtsApiResponse getListAgent(HttpServletRequest request, @RequestParam(required = true, name = "orgCode")String orgCode ) {
		log.info("Begin getActivitiDetail");
		long start = System.currentTimeMillis();
		try {
			if (!UserProfileUtils.getAuthentication().getAuthorities().contains(new SimpleGrantedAuthority(FUNCTION_ID))) {
				return this.errorHandler.handlerException(new Exception("Bạn không có quyền!"), start, null, null);
			}
			List<Db2AgentDto> res = service.getListAgent(orgCode);
			return this.successHandler.handlerSuccess(res, start, null, null);
		} catch (Exception ex) {
			return this.errorHandler.handlerException(ex, start, null, null);
		}
	}
}
