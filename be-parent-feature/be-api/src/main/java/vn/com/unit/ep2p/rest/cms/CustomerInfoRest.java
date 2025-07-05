package vn.com.unit.ep2p.rest.cms;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import vn.com.unit.cms.core.constant.CmsApiConstant;
import vn.com.unit.cms.core.dto.CmsCommonPagination;
import vn.com.unit.cms.core.dto.CommonSearchWithPagingDto;
import vn.com.unit.cms.core.module.appointment.dto.AppointmentDto;
import vn.com.unit.cms.core.module.customer.dto.ClientNickNameDto;
import vn.com.unit.cms.core.module.customer.dto.CustomerInformationDto;
import vn.com.unit.cms.core.module.customer.dto.CustomerInformationNoSearchDto;
import vn.com.unit.cms.core.module.customer.dto.CustomerInformationSearchDto;
import vn.com.unit.cms.core.module.customer.dto.CustomerInteractionHistoryDto;
import vn.com.unit.cms.core.module.customer.dto.CustomerInteractionHistorySearchDto;
import vn.com.unit.cms.core.module.customer.dto.CustomerPotentialDto;
import vn.com.unit.cms.core.module.customer.dto.InsuranceFeesInformationDto;
import vn.com.unit.cms.core.module.customer.dto.PolicyInformationDto;
import vn.com.unit.cms.core.module.customer.dto.PolicyInformationNoSearchDto;
import vn.com.unit.cms.core.module.customer.dto.PolicyInformationSearchDto;
import vn.com.unit.cms.core.module.customer.dto.ProductInformationDto;
import vn.com.unit.cms.core.module.customer.dto.ProductInformationSearch;
import vn.com.unit.core.res.ObjectDataRes;
import vn.com.unit.core.security.UserProfileUtils;
import vn.com.unit.dts.web.rest.common.DtsApiResponse;
import vn.com.unit.ep2p.admin.constant.ConstantCore;
import vn.com.unit.ep2p.admin.dto.CustomerAppointmentDto;
import vn.com.unit.ep2p.admin.dto.CustomerInformationDetailDto;
import vn.com.unit.ep2p.admin.dto.PolicyInfoDto;
import vn.com.unit.ep2p.core.constant.AppApiConstant;
import vn.com.unit.ep2p.rest.AbstractRest;
import vn.com.unit.ep2p.service.ApiCustomerInformationService;
import vn.com.unit.ep2p.service.impl.AppointmentServiceImpl;
import vn.com.unit.ep2p.utils.LangugeUtil;

@RestController
@RequestMapping(CmsApiConstant.BASE_API_ODS_URL + CmsApiConstant.API_ODS_CUSTOMER)
@Api(tags = { CmsApiConstant.API_ODS_CUSTOMER_DESCR })
public class CustomerInfoRest extends AbstractRest {

    @Autowired
    private ApiCustomerInformationService apiCustomerInformationService;
    
    @Autowired
    private AppointmentServiceImpl appointmentService;
    
	private Logger logger = LoggerFactory.getLogger(getClass());
    
    @PostMapping(AppApiConstant.API_EDIT_EVENTS)
    @ApiOperation("Api add/edit nick name")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "success"),
    		@ApiResponse(code = 500, message = "Internal Server Error"),
            @ApiResponse(code = 401, message = "Unauthorized"), 
            @ApiResponse(code = 403, message = "Forbidden")})

    public DtsApiResponse editOrEditEvents(HttpServletRequest request, @RequestBody ClientNickNameDto dto)  {
        long start = System.currentTimeMillis();
        try {
            ClientNickNameDto resObj = apiCustomerInformationService.editOrAddEvents(dto);
            return this.successHandler.handlerSuccess(resObj, start, null, null);
        } catch (Exception ex) {
            return this.errorHandler.handlerException(ex, start, null, null);
        }
    }
    @GetMapping("/get-nickname")
    @ApiOperation("Get nick name ")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "success"),
            @ApiResponse(code = 500, message = "Internal Server Error"),
            @ApiResponse(code = 401, message = "Unauthorized"),
            @ApiResponse(code = 403, message = "Forbidden")})

    public DtsApiResponse getNickName(HttpServletRequest request
            , @RequestParam(value = "customerNo") String customerNo)  {
        long start = System.currentTimeMillis();
        try {
        	String agentCode = UserProfileUtils.getFaceMask();
    		if (StringUtils.isNotEmpty(UserProfileUtils.getFaceMask())) {
    			agentCode = UserProfileUtils.getFaceMask();
    		}
            String nickName = apiCustomerInformationService.getNickName(agentCode, customerNo);
            return this.successHandler.handlerSuccess(nickName, start, null, null);
        } catch (Exception ex) {
            return this.errorHandler.handlerException(ex, start, null, null);
        }
    }
	@GetMapping(AppApiConstant.API_CUSTOMER_INFO_BY_AGENT)
    @ApiOperation("Get all customer by agent")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "success"),
                            @ApiResponse(code = 500, message = "Internal Server Error"),
                            @ApiResponse(code = 401, message = "Unauthorized"),
                            @ApiResponse(code = 403, message = "Forbidden"),
                            @ApiResponse(code = 402601, message = "Error process type new") })
	
    public DtsApiResponse getListCustomerByCondition(HttpServletRequest request,
            // @RequestParam(defaultValue = "0") Integer modeView,
             @RequestParam(value = ConstantCore.PAGE_SIZE, defaultValue = "10") Optional<Integer> pageSizeParam,
             @RequestParam(value = ConstantCore.PAGE, defaultValue = "0") Optional<Integer> pageParam,
             CustomerInformationSearchDto searchDto)  {
        long start = System.currentTimeMillis();
        try {
        	String agentCode = UserProfileUtils.getFaceMask();
    		if (StringUtils.isNotEmpty(UserProfileUtils.getFaceMask())) {
    			agentCode = UserProfileUtils.getFaceMask();
    		}
    		searchDto.setAgentCode(agentCode);
            searchDto.setPage(pageParam.get());
        	searchDto.setPageSize(pageSizeParam.get());
        	CmsCommonPagination<CustomerInformationDto> common = apiCustomerInformationService.getListCustomerByCondition(searchDto);
        	ObjectDataRes<CustomerInformationDto> resObj = new ObjectDataRes<>(common.getTotalData(), common.getData());
            return this.successHandler.handlerSuccess(resObj, start, null, null);
        } catch (Exception ex) {
            return this.errorHandler.handlerException(ex, start, null, null);
        }
    }
	
	@GetMapping(AppApiConstant.API_CUSTOMER_INFO_BY_CUMSTOMERNO)
    @ApiOperation("Get customer detail by customer")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "success"),
                            @ApiResponse(code = 500, message = "Internal Server Error"),
                            @ApiResponse(code = 401, message = "Unauthorized"),
                            @ApiResponse(code = 403, message = "Forbidden"),
                            @ApiResponse(code = 402601, message = "Error process type new") })
	
    public DtsApiResponse getListCustomerByCustomerNo(HttpServletRequest request,
             CustomerInformationNoSearchDto searchDto)  {
		
		long start = System.currentTimeMillis();
        try {
        	String customerCode = searchDto.getCustomerCode();
        	String agentCode = UserProfileUtils.getFaceMask();
    		if (StringUtils.isEmpty(agentCode)) {
    			agentCode = searchDto.getAgentCode();
    		}
    		CustomerInformationDetailDto common = apiCustomerInformationService.getListCustomerByCustomerNo(customerCode,agentCode);
            return this.successHandler.handlerSuccess(common, start, null, null);
        } catch (Exception ex) {
            return this.errorHandler.handlerException(ex, start, null, null);
        }
    }
	
	@GetMapping(AppApiConstant.API_POLICY_BY_AGENT)
    @ApiOperation("Get policy by customer")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "success"),
    		@ApiResponse(code = 500, message = "Internal Server Error"),
            @ApiResponse(code = 401, message = "Unauthorized"), 
            @ApiResponse(code = 403, message = "Forbidden"),
            @ApiResponse(code = 402601, message = "Error process type new") })
	
    public DtsApiResponse getListPolicyByCondition(HttpServletRequest request,
//            @RequestParam(defaultValue = "0") Integer modeView,
            @RequestParam(value = ConstantCore.PAGE_SIZE, defaultValue = "10") Optional<Integer> pageSizeParam,
            @RequestParam(value = ConstantCore.PAGE, defaultValue = "0") Optional<Integer> pageParam
            ,PolicyInformationSearchDto searchDto)  {
        long start = System.currentTimeMillis();
        try {
        	String agentCode = UserProfileUtils.getFaceMask();
    		if (StringUtils.isNotEmpty(UserProfileUtils.getFaceMask())) {
    			agentCode = UserProfileUtils.getFaceMask();
    		}
    		searchDto.setAgentCode(agentCode);
            searchDto.setPage(pageParam.get());
        	searchDto.setPageSize(pageSizeParam.get());
        	CmsCommonPagination<PolicyInformationDto> common = apiCustomerInformationService.getListPolicyByCondition(searchDto);
        	ObjectDataRes<PolicyInformationDto> resObj = new ObjectDataRes<>(common.getTotalData(), common.getData());
            return this.successHandler.handlerSuccess(resObj, start, null, null);
        } catch (Exception ex) {
            return this.errorHandler.handlerException(ex, start, null, null);
        }
    }
	
	@GetMapping(AppApiConstant.API_POLICY_BY_POLICY)
    @ApiOperation("Get policy detail by policy")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "success"),
    		@ApiResponse(code = 500, message = "Internal Server Error"),
            @ApiResponse(code = 401, message = "Unauthorized"), 
            @ApiResponse(code = 403, message = "Forbidden"),
            @ApiResponse(code = 402601, message = "Error process type new") })
	
    public DtsApiResponse getListPolicyByPolicyNo(HttpServletRequest request,
            PolicyInformationNoSearchDto searchDto)  {
        long start = System.currentTimeMillis();
        try {
        	String policyNo = searchDto.getPolicyNo();

        	PolicyInfoDto common = apiCustomerInformationService.getListPolicyByPolicyNo(policyNo);

            return this.successHandler.handlerSuccess(common, start, null, null);
        } catch (Exception ex) {
            return this.errorHandler.handlerException(ex, start, null, null);
        }
    }
	
	@GetMapping(AppApiConstant.API_PRODUCT_BY_POLICY)
    @ApiOperation("get product by policy")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "success"), 
    		@ApiResponse(code = 500, message = "Internal Server Error"),
            @ApiResponse(code = 401, message = "Unauthorized"), 
            @ApiResponse(code = 403, message = "Forbidden"),
            @ApiResponse(code = 402601, message = "Error process type new") })
	
    public DtsApiResponse getListProductByCondition(HttpServletRequest request,
//            @RequestParam(defaultValue = "0") Integer modeView,
//            @RequestParam(value = ConstantCore.PAGE_SIZE, defaultValue = "10") Optional<Integer> pageSizeParam,
//            @RequestParam(value = ConstantCore.PAGE, defaultValue = "1") Optional<Integer> pageParam,
    		ProductInformationSearch searchDto)  {
        long start = System.currentTimeMillis();
        try {
//          searchDto.setPage(pageParam.get());
//        	searchDto.setPageSize(pageSizeParam.get());
        	
        	CmsCommonPagination<ProductInformationDto> common = apiCustomerInformationService.getListProductByCondition(searchDto);
//        	ObjectDataRes<ProductInformationDto> resObj = new ObjectDataRes<>(common.getTotalData(), common.getData());
            return this.successHandler.handlerSuccess(common, start, null, null);
        } catch (Exception ex) {
            return this.errorHandler.handlerException(ex, start, null, null);
        }
    }

	@GetMapping(AppApiConstant.API_INSURANCE_FEES_BY_POLICY)
    @ApiOperation("Api provides constant on systems")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "success"), @ApiResponse(code = 500, message = "Internal Server Error"),
            @ApiResponse(code = 401, message = "Unauthorized"), @ApiResponse(code = 403, message = "Forbidden"),
            @ApiResponse(code = 402601, message = "Error process type new") })
	
    public DtsApiResponse getListInsuranceFeesByCondition(HttpServletRequest request, @RequestParam(defaultValue = "0") Integer modeView,
    		@RequestParam(value = ConstantCore.PAGE_SIZE, defaultValue = "10") Optional<Integer> pageSizeParam,
            @RequestParam(value = ConstantCore.PAGE, defaultValue = "1") Optional<Integer> pageParam
            , PolicyInformationSearchDto searchDto)  {
        long start = System.currentTimeMillis();
        try {
        	ObjectMapper mapper = new ObjectMapper();
            searchDto.setPage(pageParam.get());
        	searchDto.setPageSize(pageSizeParam.get());
        	//searchDto.setLanguage(locale.getLanguage());
        	String stringJsonParam = mapper.writeValueAsString(searchDto);
        	
        	CmsCommonPagination<InsuranceFeesInformationDto> common = apiCustomerInformationService.getListInsuranceFeesByCondition(stringJsonParam);
        	ObjectDataRes<InsuranceFeesInformationDto> resObj = new ObjectDataRes<>(common.getTotalData(), common.getData());
            return this.successHandler.handlerSuccess(resObj, start, null, null);
        } catch (Exception ex) {
            return this.errorHandler.handlerException(ex, start, null, null);
        }
    }
	
	@SuppressWarnings("rawtypes")
	@PostMapping(AppApiConstant.API_EXPORT_CUSTOMER_AGENT)
    @ApiOperation("Api provides constant on systems")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "success"), @ApiResponse(code = 500, message = "Internal Server Error"),
            @ApiResponse(code = 401, message = "Unauthorized"), @ApiResponse(code = 403, message = "Forbidden"),
            @ApiResponse(code = 402601, message = "Error process type new") })
	
	public ResponseEntity exportListPolicyByCondition(HttpServletRequest request,
			@RequestParam(defaultValue = "0") Integer modeView,
			@RequestParam(defaultValue = "0") String agentCode
			, HttpServletResponse response
			,@RequestBody CustomerInformationSearchDto searchDto) {
		Locale locale = LangugeUtil.getLanguageFromHeader(request);
    	searchDto.setLanguage(locale.getLanguage());
		ResponseEntity resObj = null;
		try {
			resObj = apiCustomerInformationService.exportListCustomerAgentByCondition(searchDto, response, locale,agentCode);
		} catch (Exception e) {
			logger.error("##exportLis##", e.getMessage());
		}
		return resObj;
	}
	
	@SuppressWarnings("rawtypes")
	@PostMapping(AppApiConstant.API_EXPORT_POLICY_AGENT)
    @ApiOperation("Api provides constant on systems")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "success"), @ApiResponse(code = 500, message = "Internal Server Error"),
            @ApiResponse(code = 401, message = "Unauthorized"), @ApiResponse(code = 403, message = "Forbidden"),
            @ApiResponse(code = 402601, message = "Error process type new") })
	
	public ResponseEntity exportListPolicyByAgentByCondition(HttpServletRequest request,
			@RequestParam(defaultValue = "0") Integer modeView,
			@RequestParam(defaultValue = "0") String agentCode,
			@RequestParam(defaultValue = "0") String customerNo,
			@RequestParam(defaultValue = "") String policytype,

			 HttpServletResponse response
			,@RequestBody PolicyInformationSearchDto searchDto) {
		Locale locale = LangugeUtil.getLanguageFromHeader(request);
    	searchDto.setLanguage(locale.getLanguage());
		ResponseEntity resObj = null;
		try {
			if(StringUtils.isBlank(searchDto.getAgentCode())){
				agentCode = UserProfileUtils.getFaceMask();
			}
			resObj = apiCustomerInformationService.exportListPolicyByAgentByCondition(searchDto, response, locale,agentCode, customerNo,policytype);
		} catch (Exception e) {
			logger.error("##exportLis##", e.getMessage());
		}
		return resObj;
	}
	
	// SR15598 Start
	@GetMapping(AppApiConstant.API_LIST_CUSTOMER_POTENTIAL)
    @ApiOperation("Get all customer potential by agent")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "success"),
                            @ApiResponse(code = 500, message = "Internal Server Error"),
                            @ApiResponse(code = 401, message = "Unauthorized"),
                            @ApiResponse(code = 403, message = "Forbidden"),
                            @ApiResponse(code = 402601, message = "Error process type new") })
	
    public DtsApiResponse getListCustomerPotential(HttpServletRequest request,
             @RequestParam(value = ConstantCore.PAGE_SIZE, defaultValue = "10") Optional<Integer> pageSizeParam,
             @RequestParam(value = ConstantCore.PAGE, defaultValue = "0") Optional<Integer> pageParam,
             CustomerInformationSearchDto searchDto)  {
        long start = System.currentTimeMillis();
        try {
        	String agentCode = UserProfileUtils.getFaceMask();
    		if (StringUtils.isNotEmpty(UserProfileUtils.getFaceMask())) {
    			agentCode = UserProfileUtils.getFaceMask();
    		}
    		searchDto.setAgentCode(agentCode);
            searchDto.setPage(pageParam.get());
        	searchDto.setPageSize(pageSizeParam.get());
        	CmsCommonPagination<CustomerPotentialDto> common = apiCustomerInformationService.getListCustomerPotential(searchDto);
        	ObjectDataRes<CustomerPotentialDto> resObj = new ObjectDataRes<>(common.getTotalData(), common.getData());
            return this.successHandler.handlerSuccess(resObj, start, null, null);
        } catch (Exception ex) {
            return this.errorHandler.handlerException(ex, start, null, null);
        }
    }
	
	@SuppressWarnings("rawtypes")
	@PostMapping(AppApiConstant.API_EXPORT_CUSTOMER_POTENTIAL)
    @ApiOperation("Export list customer potential")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "success"), @ApiResponse(code = 500, message = "Internal Server Error"),
            @ApiResponse(code = 401, message = "Unauthorized"), @ApiResponse(code = 403, message = "Forbidden"),
            @ApiResponse(code = 402601, message = "Error process type new") })
	
	public ResponseEntity exportListCustomerPotential(HttpServletRequest request,
			@RequestParam(defaultValue = "0") Integer modeView,
			@RequestParam(defaultValue = "0") String agentCode
			, HttpServletResponse response
			,@RequestBody CustomerInformationSearchDto searchDto) {
		Locale locale = LangugeUtil.getLanguageFromHeader(request);
    	searchDto.setLanguage(locale.getLanguage());
		ResponseEntity resObj = null;
		try {
			resObj = apiCustomerInformationService.exportListCustomerPotential(searchDto, response, locale, agentCode);
		} catch (Exception e) {
			logger.error("##exportListCustomerPotential##", e.getMessage());
		}
		return resObj;
	}
	
	@GetMapping(AppApiConstant.API_DETAIL_CUSTOMER_POTENTIAL)
    @ApiOperation("Get customer potential detail by customer no")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "success"),
                            @ApiResponse(code = 500, message = "Internal Server Error"),
                            @ApiResponse(code = 401, message = "Unauthorized"),
                            @ApiResponse(code = 403, message = "Forbidden"),
                            @ApiResponse(code = 402601, message = "Error process type new") })
	
    public DtsApiResponse getCustomerPotentialByCustomerNo(HttpServletRequest request,
             CustomerInformationNoSearchDto searchDto) {
		long start = System.currentTimeMillis();
        try {
        	String agentCode = UserProfileUtils.getFaceMask();
    		if (StringUtils.isNotEmpty(searchDto.getAgentCode())) {
    			agentCode = searchDto.getAgentCode();
    		}
        	CustomerPotentialDto detail = apiCustomerInformationService.getDetailCustomerPotential(agentCode, searchDto.getCustomerCode());
            return this.successHandler.handlerSuccess(detail, start, null, null);
        } catch (Exception ex) {
            return this.errorHandler.handlerException(ex, start, null, null);
        }
    }
	
	@GetMapping(AppApiConstant.API_LIST_INTERACTION_HISTORY)
    @ApiOperation("Get all customer potential by agent")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "success"),
                            @ApiResponse(code = 500, message = "Internal Server Error"),
                            @ApiResponse(code = 401, message = "Unauthorized"),
                            @ApiResponse(code = 403, message = "Forbidden"),
                            @ApiResponse(code = 402601, message = "Error process type new") })
	
    public DtsApiResponse getListInteractionHistory(HttpServletRequest request,
             @RequestParam(value = ConstantCore.PAGE_SIZE, defaultValue = "10") Optional<Integer> pageSizeParam,
             @RequestParam(value = ConstantCore.PAGE, defaultValue = "0") Optional<Integer> pageParam,
             CustomerInteractionHistorySearchDto searchDto)  {
        long start = System.currentTimeMillis();
        try {
            searchDto.setPage(pageParam.get());
        	searchDto.setPageSize(pageSizeParam.get());
        	CmsCommonPagination<CustomerInteractionHistoryDto> common = apiCustomerInformationService.getListInteractionHistory(searchDto);
        	ObjectDataRes<CustomerInteractionHistoryDto> resObj = new ObjectDataRes<>(common.getTotalData(), common.getData());
            return this.successHandler.handlerSuccess(resObj, start, null, null);
        } catch (Exception ex) {
            return this.errorHandler.handlerException(ex, start, null, null);
        }
    }
	
	@GetMapping(AppApiConstant.API_DETAIL_INTERACTION_HISTORY)
    @ApiOperation("Get all customer potential by agent")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "success"),
                            @ApiResponse(code = 500, message = "Internal Server Error"),
                            @ApiResponse(code = 401, message = "Unauthorized"),
                            @ApiResponse(code = 403, message = "Forbidden"),
                            @ApiResponse(code = 402601, message = "Error process type new") })
	
    public DtsApiResponse getDetailInteractionHistory(HttpServletRequest request,
             @RequestParam(value = ConstantCore.PAGE_SIZE, defaultValue = "10") Optional<Integer> pageSizeParam,
             @RequestParam(value = ConstantCore.PAGE, defaultValue = "0") Optional<Integer> pageParam,
             @RequestParam(defaultValue = "0") String phoneNumber,
 				@RequestParam(defaultValue = "0") String proposalNo)  {
        long start = System.currentTimeMillis();
        try {
        	String agentCode = UserProfileUtils.getFaceMask();
    		if (StringUtils.isNotEmpty(UserProfileUtils.getFaceMask())) {
    			agentCode = UserProfileUtils.getFaceMask();
    		}
        	CustomerInteractionHistoryDto detail = apiCustomerInformationService.getDetailInteractionHistory(agentCode, phoneNumber, proposalNo);
        	return this.successHandler.handlerSuccess(detail, start, null, null);
        } catch (Exception ex) {
            return this.errorHandler.handlerException(ex, start, null, null);
        }
    }
	
	@SuppressWarnings("rawtypes")
	@PostMapping(AppApiConstant.API_EXPORT_INTERACTION_HISTORY)
    @ApiOperation("Export list customer potential")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "success"), @ApiResponse(code = 500, message = "Internal Server Error"),
            @ApiResponse(code = 401, message = "Unauthorized"), @ApiResponse(code = 403, message = "Forbidden"),
            @ApiResponse(code = 402601, message = "Error process type new") })
	
	public ResponseEntity exportListInteractionHistory(HttpServletRequest request,
			@RequestParam(defaultValue = "0") Integer modeView,
			@RequestParam(defaultValue = "0") String agentCode,
			@RequestParam(defaultValue = "0") String phoneNumber
			, HttpServletResponse response
			,@RequestBody CustomerInteractionHistorySearchDto searchDto) {
		Locale locale = LangugeUtil.getLanguageFromHeader(request);
    	searchDto.setLanguage(locale.getLanguage());
		ResponseEntity resObj = null;
		try {
			resObj = apiCustomerInformationService.exportListInteractionHistory(searchDto, response, locale, agentCode, phoneNumber);
		} catch (Exception e) {
			logger.error("##exportListInteractionHistory##", e.getMessage());
		}
		return resObj;
	}
	
	@PostMapping("/add-appointment")
    @ApiOperation("Api add Appointment")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "success"), @ApiResponse(code = 500, message = "Internal Server Error"),
            @ApiResponse(code = 401, message = "Unauthorized"), @ApiResponse(code = 403, message = "Forbidden")})

    public DtsApiResponse addAppointment(HttpServletRequest request, @RequestBody AppointmentDto dto)  {
        long start = System.currentTimeMillis();
        try {
        	AppointmentDto resObj = appointmentService.addAppointment(dto);
            return this.successHandler.handlerSuccess(resObj, start, null, null);
        } catch (Exception ex) {
            return this.errorHandler.handlerException(ex, start, null, null);
        }
    }
	
	@GetMapping("/get-list-appointment-by-date")
    @ApiOperation("Get all appointment's by date")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "success"), @ApiResponse(code = 500, message = "Internal Server Error"),
            @ApiResponse(code = 401, message = "Unauthorized"), @ApiResponse(code = 403, message = "Forbidden")})
    public DtsApiResponse getListAppointment(HttpServletRequest request
    		, @RequestParam(value = "appointmentDate", required = false) String appointmentDate
    		, @RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "pageSize", required = false) Integer pageSize
    		, CommonSearchWithPagingDto searchDto)  {
        long start = System.currentTimeMillis();
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            Date searchDate;
            try{
            	searchDate = sdf.parse(appointmentDate);
            } catch (ParseException e){
            	searchDate = null;
            }
            CmsCommonPagination<CustomerAppointmentDto> common = appointmentService.getListAppointment(searchDate, page, pageSize, null);
            ObjectDataRes<CustomerAppointmentDto> resObj = new ObjectDataRes<>(common.getTotalData(), common.getData());
            return this.successHandler.handlerSuccess(resObj, start, null, null);
        } catch (Exception ex) {
            return this.errorHandler.handlerException(ex, start, null, null);
        }
    }
}
