package vn.com.unit.ep2p.rest.adp;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.MessageSource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import vn.com.unit.cms.core.constant.CmsApiConstant;
import vn.com.unit.cms.core.dto.CmsCommonPagination;
import vn.com.unit.cms.core.module.customerManagement.dto.AdditionalDetailDto;
import vn.com.unit.cms.core.module.customerManagement.dto.TotalPolicyDto;
import vn.com.unit.core.res.ObjectDataRes;
import vn.com.unit.core.security.UserProfileUtils;
import vn.com.unit.core.service.JcaSystemConfigService;
import vn.com.unit.dts.exception.ErrorHandler;
import vn.com.unit.dts.exception.SuccessHandler;
import vn.com.unit.dts.web.rest.common.DtsApiResponse;
import vn.com.unit.ep2p.admin.constant.ConstantCore;
import vn.com.unit.ep2p.admin.dto.AgentGroupResponse;
import vn.com.unit.ep2p.admin.dto.AgentInfoDb2;
import vn.com.unit.ep2p.admin.service.AccountService;
import vn.com.unit.ep2p.adp.dto.AgentInfoSaleSopDto;
import vn.com.unit.ep2p.adp.dto.AgentInfoSearchDto;
import vn.com.unit.ep2p.adp.dto.AgentInfoSearchResultDto;
import vn.com.unit.ep2p.adp.dto.AggregateReportDataRes;
import vn.com.unit.ep2p.adp.dto.AggregateReportSearchDto;
import vn.com.unit.ep2p.adp.dto.BenefiDto;
import vn.com.unit.ep2p.adp.dto.ClaimAssessorCommentResultDto;
import vn.com.unit.ep2p.adp.dto.ClaimAssessorCommentSearchDto;
import vn.com.unit.ep2p.adp.dto.ConfirmPolicyDeliveryDto;
import vn.com.unit.ep2p.adp.dto.ConfirmPolicyDeliveryResultDto;
import vn.com.unit.ep2p.adp.dto.DuePolicyCardPersonalDto;
import vn.com.unit.ep2p.adp.dto.GeneralReportDto;
import vn.com.unit.ep2p.adp.dto.GeneralReportSearchDto;
import vn.com.unit.ep2p.adp.dto.GroupDocumentDto;
import vn.com.unit.ep2p.adp.dto.GroupDocumentResDto;
import vn.com.unit.ep2p.adp.dto.ItemDto;
import vn.com.unit.ep2p.adp.dto.ItemSearchDto;
import vn.com.unit.ep2p.adp.dto.PersonalInsuranceDto;
import vn.com.unit.ep2p.adp.dto.PersonalInsuranceSearchDto;
import vn.com.unit.ep2p.adp.dto.PersonalPolicyResultDto;
import vn.com.unit.ep2p.adp.dto.PersonalPolicySearchDto;
import vn.com.unit.ep2p.adp.dto.PolicyClaimSearchResultDto;
import vn.com.unit.ep2p.adp.dto.PolicyDeliveryDetailsDto;
import vn.com.unit.ep2p.adp.dto.PolicyDeliveryDetailsSearchDto;
import vn.com.unit.ep2p.adp.dto.PolicyDeliveryDto;
import vn.com.unit.ep2p.adp.dto.PolicyDeliverySearchDto;
import vn.com.unit.ep2p.adp.dto.PolicyFeeCardPersonalDto;
import vn.com.unit.ep2p.adp.dto.PolicyRequestSearchDto;
import vn.com.unit.ep2p.adp.dto.PolicyRequestSearchResultDto;
import vn.com.unit.ep2p.adp.dto.PolicySearchDto;
import vn.com.unit.ep2p.adp.dto.ProductDetailDto;
import vn.com.unit.ep2p.adp.dto.ProductDetailSearch;
import vn.com.unit.ep2p.adp.dto.ProposalDetailDto;
import vn.com.unit.ep2p.adp.dto.ProposalDetailSearchDto;
import vn.com.unit.ep2p.adp.dto.ProposalSearchDto;
import vn.com.unit.ep2p.adp.dto.ReportK2K2PlusDataRes;
import vn.com.unit.ep2p.adp.dto.ReportK2K2PlusDto;
import vn.com.unit.ep2p.adp.dto.ReportK2K2PlusPagination;
import vn.com.unit.ep2p.adp.dto.ReportK2K2PlusSearchDto;
import vn.com.unit.ep2p.adp.dto.TotalProposalDto;
import vn.com.unit.ep2p.adp.dto.TrackingReportDto;
import vn.com.unit.ep2p.adp.dto.TrackingReportSearchDto;
import vn.com.unit.ep2p.adp.service.AdportalService;
import vn.com.unit.ep2p.core.constant.AppApiConstant;
import vn.com.unit.ep2p.core.dto.PostNotificationletterRes;
import vn.com.unit.ep2p.core.res.dto.PostNotificationletterSearchDto;
import vn.com.unit.ep2p.core.utils.RetrofitUtils;
import vn.com.unit.ep2p.service.PersonalInsuranceDocService;
import vn.com.unit.ep2p.utils.Aes256;
import vn.com.unit.ep2p.utils.LangugeUtil;

@RestController
@RequestMapping(CmsApiConstant.BASE_API_URL + "/adportal")
@Api(tags = {"API For ADPortal Desc"})
public class ADPortalRest {

    @Autowired
    protected ErrorHandler errorHandler;

    @Autowired
    protected SuccessHandler successHandler;

    @Autowired
    private AdportalService service;

    @Autowired
    private PersonalInsuranceDocService personalInsuranceService;

    @Autowired 
	private AccountService accountService; 
    
    @Autowired
    MessageSource messageSource;

    @Autowired
	@Qualifier("appSystemConfigServiceImpl")
	private JcaSystemConfigService jcaSystemConfigService;
    
    private Logger logger = LoggerFactory.getLogger(getClass());

    @GetMapping("/get-agent-info")
    @ApiOperation("Get Agent Info")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "success"), @ApiResponse(code = 500, message = "Internal Server Error"), @ApiResponse(code = 401, message = "Unauthorized"), @ApiResponse(code = 403, message = "Forbidden")})
    public DtsApiResponse getAgentInfo(@RequestParam(value = "type", defaultValue = "1") String type, AgentInfoSearchDto searchDto) {
        long start = System.currentTimeMillis();
        try {
        	searchDto.setType(type);
        	CmsCommonPagination<AgentInfoSearchResultDto> common = service.getAgentInfo(searchDto);
            ObjectDataRes<AgentInfoSearchResultDto> resObj = new ObjectDataRes<>(common.getTotalData(), common.getData());
            return this.successHandler.handlerSuccess(resObj, start, null, null);
        } catch (Exception ex) {
            logger.error("##getAgentInfo##", ex.getMessage());
            return this.errorHandler.handlerException(ex, start, null, null);
        }
    }
    
    @SuppressWarnings("rawtypes")
    @PostMapping("/export-agent-info")
    @ApiOperation("Api export excel agent info")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "success"), @ApiResponse(code = 500, message = "Internal Server Error"), @ApiResponse(code = 401, message = "Unauthorized"), @ApiResponse(code = 403, message = "Forbidden"), @ApiResponse(code = 402601, message = "Error process type new")})
    public ResponseEntity exportAgentInfo(HttpServletRequest request, HttpServletResponse response, @RequestBody AgentInfoSearchDto searchDto) {
        Locale locale = LangugeUtil.getLanguageFromHeader(request);
        searchDto.setLanguage(locale.getLanguage());
        ResponseEntity resObj = null;
        try {
            resObj = service.exportAgentInfo(searchDto, response, locale);
        } catch (Exception e) {
            logger.error("##exportAgentInfo##", e.getMessage());
        }
        return resObj;
    }
    
    @GetMapping(AppApiConstant.API_LIST_PERSONAL_INSURANCE_DOC)
    @ApiOperation("Get all personal insurance document")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "success"), @ApiResponse(code = 500, message = "Internal Server Error"), @ApiResponse(code = 401, message = "Unauthorized"), @ApiResponse(code = 403, message = "Forbidden")})

    public DtsApiResponse getListDocument(HttpServletRequest request, @RequestParam(value = ConstantCore.PAGE_SIZE, defaultValue = "10") Optional<Integer> pageSizeParam, @RequestParam(value = ConstantCore.PAGE, defaultValue = "0") Optional<Integer> pageParam, PersonalInsuranceSearchDto searchDto) {
        long start = System.currentTimeMillis();
        try {
            searchDto.setAgentCode(UserProfileUtils.getFaceMask());
            searchDto.setPage(pageParam.get());
            searchDto.setPageSize(pageSizeParam.get());
            CmsCommonPagination<PersonalInsuranceDto> common = service.getListInsuranceByStatus(searchDto);
            ObjectDataRes<PersonalInsuranceDto> resObj = new ObjectDataRes<>(common.getTotalData(), common.getData());
            return this.successHandler.handlerSuccess(resObj, start, null, null);
        } catch (Exception ex) {
            return this.errorHandler.handlerException(ex, start, null, null);
        }
    }
    
    @SuppressWarnings("rawtypes")
	@PostMapping(AppApiConstant.API_EXPORT_PERSONAL_INSURANCE_DOC)
	@ApiOperation("Api export excel insurance document policy")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "success"),
			@ApiResponse(code = 500, message = "Internal Server Error"),
			@ApiResponse(code = 401, message = "Unauthorized"), @ApiResponse(code = 403, message = "Forbidden"),
			@ApiResponse(code = 402601, message = "Error process type new") })

	public ResponseEntity exportListPersonalInsuranceDocByCondition(HttpServletRequest request,
			@RequestParam(defaultValue = "0") Integer modeView, HttpServletResponse response,
			@RequestBody PersonalInsuranceSearchDto searchDto) {
		Locale locale = LangugeUtil.getLanguageFromHeader(request);
		searchDto.setLanguage(locale.getLanguage());
		ResponseEntity resObj = null;
		try {
        	resObj = service.exportListPrersonalInsuranceDocuments(searchDto);
		} catch (Exception e) {
			logger.error("##exportListPersonalInsuranceDocByCondition##", e.getMessage());
		}
		return resObj;
	}

    @GetMapping(AppApiConstant.API_LIST_ALL_CONTRACT)// get tat ca hop dong
    @ApiOperation("Api provides constant on systems")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "success"), @ApiResponse(code = 500, message = "Internal Server Error"),
            @ApiResponse(code = 401, message = "Unauthorized"), @ApiResponse(code = 403, message = "Forbidden")})
	
    public DtsApiResponse getListAllContractByCondition(HttpServletRequest request, @RequestParam(defaultValue = "0") Integer modeView,
    		@RequestParam(value = ConstantCore.PAGE_SIZE, defaultValue = "16") Optional<Integer> pageSizeParam,
            @RequestParam(value = ConstantCore.PAGE, defaultValue = "1") Optional<Integer> pageParam
            , PersonalPolicySearchDto searchDto)  {
        long start = System.currentTimeMillis();
        try {      	
            searchDto.setPage(pageParam.get());
        	searchDto.setPageSize(pageSizeParam.get());
        	searchDto.setAgentCode(UserProfileUtils.getFaceMask());
        	ObjectDataRes<PersonalPolicyResultDto> resObj = service.getListPolicyByType(searchDto);
            
        	return this.successHandler.handlerSuccess(resObj, start, null, null);
        } catch (Exception ex) {
            return this.errorHandler.handlerException(ex, start, null, null);
        }
    }
    
    @SuppressWarnings("rawtypes")
	@PostMapping(AppApiConstant.API_LIST_ALL_CONTRACT_EXPORT)
	@ApiOperation("Api export excel insurance document policy")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "success"),
			@ApiResponse(code = 500, message = "Internal Server Error"),
			@ApiResponse(code = 401, message = "Unauthorized"), @ApiResponse(code = 403, message = "Forbidden"),
			@ApiResponse(code = 402601, message = "Error process type new") })

	public ResponseEntity exportListPolicy(HttpServletRequest request,
			@RequestParam(defaultValue = "0") Integer modeView, HttpServletResponse response,
			@RequestBody PersonalInsuranceSearchDto searchDto) {
		Locale locale = LangugeUtil.getLanguageFromHeader(request);
		searchDto.setLanguage(locale.getLanguage());
		ResponseEntity resObj = null;
		try {
        	resObj = service.exportListPrersonalPolicy(searchDto);
		} catch (Exception e) {
			logger.error("##exportListPolicy##", e.getMessage());
		}
		return resObj;
	}
    
    @GetMapping("/get-total-proposal")
    @ApiOperation("Get total proposal by login user")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "success"), @ApiResponse(code = 500, message = "Internal Server Error"), @ApiResponse(code = 401, message = "Unauthorized"), @ApiResponse(code = 403, message = "Forbidden")})
    public DtsApiResponse getTotalProposal(ProposalSearchDto searchDto) {
        long start = System.currentTimeMillis();
        try {
            TotalProposalDto data = service.getTotalProposal(searchDto);
            return this.successHandler.handlerSuccess(data, start, null, null);
        } catch (Exception ex) {
            logger.error("##getTotalProposal##", ex.getMessage());
            return this.errorHandler.handlerException(ex, start, null, null);
        }
    }
    
    @GetMapping("/get-total-policy")
    @ApiOperation("Get total policy by login user")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "success"), @ApiResponse(code = 500, message = "Internal Server Error"), @ApiResponse(code = 401, message = "Unauthorized"), @ApiResponse(code = 403, message = "Forbidden")})
    public DtsApiResponse getTotalPolicy(String agentCode) {
        long start = System.currentTimeMillis();
        try {
        	TotalPolicyDto data = service.getTotalPolicy(agentCode);
            return this.successHandler.handlerSuccess(data, start, null, null);
        } catch (Exception ex) {
            logger.error("##getTotalPolicy##", ex.getMessage());
            return this.errorHandler.handlerException(ex, start, null, null);
        }
    }

    @PostMapping("/get-detail-proposal")
    @ApiOperation("Get detail proposal by policyno")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "success"), @ApiResponse(code = 500, message = "Internal Server Error"), @ApiResponse(code = 401, message = "Unauthorized"), @ApiResponse(code = 403, message = "Forbidden")})
    public DtsApiResponse getDetailProposal(HttpServletRequest request, @RequestBody ProposalDetailSearchDto searchDto) {
        long start = System.currentTimeMillis();
        try {
            ProposalDetailDto detail = service.getProposalDetail(searchDto);
            return this.successHandler.handlerSuccess(detail, start, null, null);
        } catch (Exception ex) {
            logger.error("##getDetailProposal##", ex.getMessage());
            return this.errorHandler.handlerException(ex, start, null, null);
        }
    }

    @GetMapping("/get-list-policy-by-keyword")
    @ApiOperation("Api get constant on systems by keyword")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "success"), @ApiResponse(code = 500, message = "Internal Server Error"),
            @ApiResponse(code = 401, message = "Unauthorized"), @ApiResponse(code = 403, message = "Forbidden")})
	
    public DtsApiResponse getListPolicyByKeyword(HttpServletRequest request,
    		@RequestParam(value = ConstantCore.PAGE_SIZE, defaultValue = "16") Optional<Integer> pageSizeParam,
            @RequestParam(value = ConstantCore.PAGE, defaultValue = "1") Optional<Integer> pageParam
            , PolicySearchDto searchDto)  {
        long start = System.currentTimeMillis();
        try {
            searchDto.setPage(pageParam.get());
        	searchDto.setPageSize(pageSizeParam.get());
        	ObjectDataRes<PersonalPolicyResultDto> resObj = service.getListPolicyByKeyword(searchDto);
            
        	return this.successHandler.handlerSuccess(resObj, start, null, null);
        } catch (Exception ex) {
            return this.errorHandler.handlerException(ex, start, null, null);
        }
    }
    
    @PostMapping("/get-list-product")
    @ApiOperation("Get list product by policyno")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "success"), @ApiResponse(code = 500, message = "Internal Server Error"), @ApiResponse(code = 401, message = "Unauthorized"), @ApiResponse(code = 403, message = "Forbidden")})
    public DtsApiResponse getListProduct(HttpServletRequest request, @RequestBody ProposalDetailSearchDto searchDto) {
        long start = System.currentTimeMillis();
        try {
            ProductDetailSearch param = new ProductDetailSearch();
            param.setPolicyNo(Integer.parseInt(searchDto.getPolicyNo()));
            CmsCommonPagination<ProductDetailDto> common = service.getListProduct(param);
            ObjectDataRes<ProductDetailDto> resObj = new ObjectDataRes<>(common.getTotalData(), common.getData());
            return this.successHandler.handlerSuccess(resObj, start, null, null);
        } catch (Exception ex) {
            logger.error("##getListProduct##", ex.getMessage());
            return this.errorHandler.handlerException(ex, start, null, null);
        }
    }

    @PostMapping("/get-list-benefi")
    @ApiOperation("Get list benefi by policyno")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "success"), @ApiResponse(code = 500, message = "Internal Server Error"), @ApiResponse(code = 401, message = "Unauthorized"), @ApiResponse(code = 403, message = "Forbidden")})
    public DtsApiResponse getListBenefi(HttpServletRequest request, @RequestBody ProposalDetailSearchDto searchDto) {
        long start = System.currentTimeMillis();
        try {
            CmsCommonPagination<BenefiDto> common = service.getListBenefi(searchDto.getPolicyNo());
            ObjectDataRes<BenefiDto> resObj = new ObjectDataRes<>(common.getTotalData(), common.getData());
            return this.successHandler.handlerSuccess(resObj, start, null, null);
        } catch (Exception ex) {
            logger.error("##getListProduct##", ex.getMessage());
            return this.errorHandler.handlerException(ex, start, null, null);
        }
    }

    @PostMapping("/get-detail-profile-additional")
    @ApiOperation("Get detail personal insurance contract by type")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "success"), @ApiResponse(code = 500, message = "Internal Server Error"), @ApiResponse(code = 401, message = "Unauthorized"), @ApiResponse(code = 403, message = "Forbidden")})
    public DtsApiResponse getDetailProfileAdditional(HttpServletRequest request, @RequestBody ProposalDetailSearchDto searchDto) {
        long start = System.currentTimeMillis();
        try {
            List<AdditionalDetailDto> data = personalInsuranceService.getDetailAdditional(searchDto.getPolicyNo());
            return this.successHandler.handlerSuccess(data, start, null, null);
        } catch (Exception ex) {
            return this.errorHandler.handlerException(ex, start, null, null);
        }
    }

    @PostMapping("/get-uw-letter")
    @ApiOperation("Get UW Letter")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "success"), @ApiResponse(code = 500, message = "Internal Server Error"), @ApiResponse(code = 401, message = "Unauthorized"), @ApiResponse(code = 403, message = "Forbidden")})
    public DtsApiResponse GetUWLetter(HttpServletRequest request, @RequestBody PostNotificationletterSearchDto searchDto) {
        long start = System.currentTimeMillis();
        try {
            PostNotificationletterRes result = RetrofitUtils.PostNotificationletter(searchDto);
            return this.successHandler.handlerSuccess(result, start, null, null);
        } catch (Exception ex) {
            return this.errorHandler.handlerException(ex, start, null, null);
        }
    }

    @GetMapping("/get-list-partner")
    @ApiOperation("Get list partner by login user")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "success"), @ApiResponse(code = 500, message = "Internal Server Error"), @ApiResponse(code = 401, message = "Unauthorized"), @ApiResponse(code = 403, message = "Forbidden")})
    public DtsApiResponse getListPartner() {
        long start = System.currentTimeMillis();
        try {
            // Lấy dữ liệu từ service
            CmsCommonPagination<ItemDto> common = service.getListPartner();

            // Tạo đối tượng phản hồi
            ObjectDataRes<ItemDto> resObj = new ObjectDataRes<>(common.getTotalData(), common.getData());

            // Trả về phản hồi thành công
            return this.successHandler.handlerSuccess(resObj, start, null, null);

        } catch (Exception ex) {
            // Ghi lại log lỗi
            logger.error("##getListPartner##", ex.getMessage());

            // Xử lý lỗi và trả về phản hồi lỗi
            return this.errorHandler.handlerException(ex, start, null, null);
        }
    }

    @PostMapping("/get-list-item-combobox")
    @ApiOperation("Get list item combobox by condition")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "success"), @ApiResponse(code = 500, message = "Internal Server Error"), @ApiResponse(code = 401, message = "Unauthorized"), @ApiResponse(code = 403, message = "Forbidden")})
    public DtsApiResponse getListItemCombobox(HttpServletRequest request, @RequestBody ItemSearchDto searchDto) {
        long start = System.currentTimeMillis();
        try {
            // Lấy dữ liệu từ service
            CmsCommonPagination<ItemDto> common = null;
            if ("PARTNER".equals(searchDto.getItemName())) {
            	common = service.getListPartner();
            } else if ("AREA".equals(searchDto.getItemName())) {
            	common = service.getListItemCombobox(searchDto.getPartnerCode(), searchDto.getItemName());
            } else if ("ZONE".equals(searchDto.getItemName())) {
            	common = service.getListItemCombobox(searchDto.getAreaCode(), searchDto.getItemName());
            } else if ("UO".equals(searchDto.getItemName())) {
            	common = service.getListItemCombobox(searchDto.getZoneCode(), searchDto.getItemName());
            } else if ("AREA_DLVN".equals(searchDto.getItemName())) {
            	common = service.getListItemCombobox(searchDto.getPartnerCode(), searchDto.getItemName());
            } else if ("REGION_DLVN".equals(searchDto.getItemName())) {
            	common = service.getListItemCombobox(searchDto.getAreaCodeDLVN(), searchDto.getItemName());
            } else if ("ZONE_DLVN".equals(searchDto.getItemName())) {
            	common = service.getListItemCombobox(searchDto.getRegionCodeDLVN(), searchDto.getItemName());
            } else if ("IL".equals(searchDto.getItemName())) {
            	common = service.getListItemCombobox(searchDto.getZoneCodeDLVN(), searchDto.getItemName());
            } else if ("IS".equals(searchDto.getItemName())) {
            	common = service.getListItemCombobox(searchDto.getZoneCodeDLVN(), searchDto.getItemName());
            } else if ("SM".equals(searchDto.getItemName())) {
            	common = service.getListItemCombobox(searchDto.getZoneCodeDLVN(), searchDto.getItemName());
            } else if ("UO_DELIVERY".equals(searchDto.getItemName())) {
                common = service.getListItemCombobox(searchDto.getPartnerCode(), searchDto.getItemName());
            }

            // Tạo đối tượng phản hồi
            ObjectDataRes<ItemDto> resObj = new ObjectDataRes<>(common.getTotalData(), common.getData());

            // Trả về phản hồi thành công
            return this.successHandler.handlerSuccess(resObj, start, null, null);

        } catch (Exception ex) {
            // Ghi lại log lỗi
            logger.error("##getListItemCombobox##", ex.getMessage());

            // Xử lý lỗi và trả về phản hồi lỗi
            return this.errorHandler.handlerException(ex, start, null, null);
        }
    }
    
    @GetMapping("/get-list-request-policy")	//hop dong dang điều chỉnh thông tin
    @ApiOperation("Get all policy request change info")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "success"), @ApiResponse(code = 500, message = "Internal Server Error"),
            @ApiResponse(code = 401, message = "Unauthorized"), @ApiResponse(code = 403, message = "Forbidden")})
	
    public DtsApiResponse getListRequestPolicy(HttpServletRequest request, @RequestParam(defaultValue = "0") Integer modeView,
    		@RequestParam(value = ConstantCore.PAGE_SIZE, defaultValue = "16") Optional<Integer> pageSizeParam,
            @RequestParam(value = ConstantCore.PAGE, defaultValue = "1") Optional<Integer> pageParam
            , PersonalInsuranceSearchDto searchDto)  {
        long start = System.currentTimeMillis();
        try {
            searchDto.setPage(pageParam.get());
        	searchDto.setPageSize(pageSizeParam.get());
        	ObjectDataRes<PolicyRequestSearchResultDto> resObj = service.getListRequestPolicy(searchDto);
        	return this.successHandler.handlerSuccess(resObj, start, null, null);
        } catch (Exception ex) {
            return this.errorHandler.handlerException(ex, start, null, null);
        }
    }
    
    @PostMapping("/get-detail-request-policy")
    @ApiOperation("Get all policy request change info")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "success"), @ApiResponse(code = 500, message = "Internal Server Error"),
            @ApiResponse(code = 401, message = "Unauthorized"), @ApiResponse(code = 403, message = "Forbidden")})
	
    public DtsApiResponse getDetailRequestPolicy(HttpServletRequest request, @RequestBody PolicyRequestSearchDto searchDto)  {
        long start = System.currentTimeMillis();
        try {
        	PolicyRequestSearchResultDto resObj = service.getDetailRequestPolicy(searchDto);
        	return this.successHandler.handlerSuccess(resObj, start, null, null);
        } catch (Exception ex) {
            return this.errorHandler.handlerException(ex, start, null, null);
        }
    }
    
    @SuppressWarnings("rawtypes")
	@PostMapping("/export-list-request-policy")
	@ApiOperation("Api export excel request policy")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "success"),
			@ApiResponse(code = 500, message = "Internal Server Error"),
			@ApiResponse(code = 401, message = "Unauthorized"), @ApiResponse(code = 403, message = "Forbidden"),
			@ApiResponse(code = 402601, message = "Error process type new") })

	public ResponseEntity exportListRequestPolicy(HttpServletRequest request,
			@RequestParam(defaultValue = "0") Integer modeView, HttpServletResponse response,
			@RequestBody PersonalInsuranceSearchDto searchDto) {
		Locale locale = LangugeUtil.getLanguageFromHeader(request);
		searchDto.setLanguage(locale.getLanguage());
		ResponseEntity resObj = null;
		try {
        	resObj = service.exportListRequestPolicy(searchDto);
		} catch (Exception e) {
			logger.error("##exportListRequestPolicy##", e.getMessage());
		}
		return resObj;
	}
    
    @GetMapping("/get-list-claim-policy")
    @ApiOperation("Get all claim policy")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "success"), @ApiResponse(code = 500, message = "Internal Server Error"),
            @ApiResponse(code = 401, message = "Unauthorized"), @ApiResponse(code = 403, message = "Forbidden")})
	
    public DtsApiResponse getListClaimPolicy(HttpServletRequest request, @RequestParam(defaultValue = "0") Integer modeView,
    		@RequestParam(value = ConstantCore.PAGE_SIZE, defaultValue = "16") Optional<Integer> pageSizeParam,
            @RequestParam(value = ConstantCore.PAGE, defaultValue = "1") Optional<Integer> pageParam
            , PersonalInsuranceSearchDto searchDto)  {
        long start = System.currentTimeMillis();
        try {
            searchDto.setPage(pageParam.get());
        	searchDto.setPageSize(pageSizeParam.get());
        	ObjectDataRes<PolicyClaimSearchResultDto> resObj = service.getListClaimPolicy(searchDto);
        	return this.successHandler.handlerSuccess(resObj, start, null, null);
        } catch (Exception ex) {
            return this.errorHandler.handlerException(ex, start, null, null);
        }
    }
    
    @SuppressWarnings("rawtypes")
	@PostMapping("/export-list-claim-policy")
	@ApiOperation("Api export excel request policy")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "success"),
			@ApiResponse(code = 500, message = "Internal Server Error"),
			@ApiResponse(code = 401, message = "Unauthorized"), @ApiResponse(code = 403, message = "Forbidden"),
			@ApiResponse(code = 402601, message = "Error process type new") })

	public ResponseEntity exportListClaimPolicy(HttpServletRequest request,
			@RequestParam(defaultValue = "0") Integer modeView, HttpServletResponse response,
			@RequestBody PersonalInsuranceSearchDto searchDto) {
		Locale locale = LangugeUtil.getLanguageFromHeader(request);
		searchDto.setLanguage(locale.getLanguage());
		ResponseEntity resObj = null;
		try {
        	resObj = service.exportListClaimPolicy(searchDto);
		} catch (Exception e) {
			logger.error("##exportListClaimPolicy##", e.getMessage());
		}
		return resObj;
	}
    
    @GetMapping("/get-list-fee-policy")
    @ApiOperation("Get all fee policy by condition")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "success"), @ApiResponse(code = 500, message = "Internal Server Error"), @ApiResponse(code = 401, message = "Unauthorized"), @ApiResponse(code = 403, message = "Forbidden")})
    public DtsApiResponse getListFeePolicy(HttpServletRequest request, @RequestParam(value = "page", defaultValue = "0") Integer page, 
    		@RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize, PersonalInsuranceSearchDto searchDto) {
        long start = System.currentTimeMillis();
        try {
            searchDto.setPage(page);
            searchDto.setPageSize(pageSize);

            // Lấy dữ liệu từ service
            CmsCommonPagination<PolicyFeeCardPersonalDto> common = service.getListFeePolicy(searchDto);

            // Lấy danh sách dữ liệu
            List<PolicyFeeCardPersonalDto> dataList = common.getData();

            // Tạo đối tượng phản hồi
            ObjectDataRes<PolicyFeeCardPersonalDto> resObj = new ObjectDataRes<>(common.getTotalData(), dataList);

            // Trả về phản hồi thành công
            return this.successHandler.handlerSuccess(resObj, start, null, null);

        } catch (Exception ex) {
            // Ghi lại log lỗi
            logger.error("##getListFeePolicy##", ex.getMessage());

            // Xử lý lỗi và trả về phản hồi lỗi
            return this.errorHandler.handlerException(ex, start, null, null);
        }
    }
    
    @SuppressWarnings("rawtypes")
    @PostMapping("/export-list-fee-policy")
    @ApiOperation("Api export excel list group policy")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "success"), @ApiResponse(code = 500, message = "Internal Server Error"), @ApiResponse(code = 401, message = "Unauthorized"), @ApiResponse(code = 403, message = "Forbidden"), @ApiResponse(code = 402601, message = "Error process type new")})
    public ResponseEntity exportListFeePolicy(HttpServletRequest request, HttpServletResponse response, @RequestBody PersonalInsuranceSearchDto searchDto) {
        Locale locale = LangugeUtil.getLanguageFromHeader(request);
        searchDto.setLanguage(locale.getLanguage());
        ResponseEntity resObj = null;
        try {
             resObj = service.exportListFeePolicy(searchDto, response, locale);
        } catch (Exception e) {
            logger.error("##exportListFeePolicy##", e.getMessage());
        }
        return resObj;
    }
    
    @GetMapping("/get-list-due-policy")
    @ApiOperation("Get all due policy by condition")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "success"), @ApiResponse(code = 500, message = "Internal Server Error"), @ApiResponse(code = 401, message = "Unauthorized"), @ApiResponse(code = 403, message = "Forbidden")})
    public DtsApiResponse getListDuePolicy(HttpServletRequest request, @RequestParam(value = "page", defaultValue = "0") Integer page, 
    		@RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize, PersonalInsuranceSearchDto searchDto) {
        long start = System.currentTimeMillis();
        try {
            searchDto.setPage(page);
            searchDto.setPageSize(pageSize);

            // Lấy dữ liệu từ service
            CmsCommonPagination<DuePolicyCardPersonalDto> common = service.getListDuePolicy(searchDto);

            // Lấy danh sách dữ liệu
            List<DuePolicyCardPersonalDto> dataList = common.getData();

            // Tạo đối tượng phản hồi
            ObjectDataRes<DuePolicyCardPersonalDto> resObj = new ObjectDataRes<>(common.getTotalData(), dataList);

            // Trả về phản hồi thành công
            return this.successHandler.handlerSuccess(resObj, start, null, null);

        } catch (Exception ex) {
            // Ghi lại log lỗi
            logger.error("##getListFeePolicy##", ex.getMessage());

            // Xử lý lỗi và trả về phản hồi lỗi
            return this.errorHandler.handlerException(ex, start, null, null);
        }
    }
    
    @SuppressWarnings("rawtypes")
    @PostMapping("/export-list-due-policy")
    @ApiOperation("Api export excel list group policy")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "success"), @ApiResponse(code = 500, message = "Internal Server Error"), @ApiResponse(code = 401, message = "Unauthorized"), @ApiResponse(code = 403, message = "Forbidden"), @ApiResponse(code = 402601, message = "Error process type new")})
    public ResponseEntity exportListDuePolicy(HttpServletRequest request, HttpServletResponse response, @RequestBody PersonalInsuranceSearchDto searchDto) {
        Locale locale = LangugeUtil.getLanguageFromHeader(request);
        searchDto.setLanguage(locale.getLanguage());
        ResponseEntity resObj = null;
        try {
             resObj = service.exportListDuePolicy(searchDto, response, locale);
        } catch (Exception e) {
            logger.error("##exportListFeePolicy##", e.getMessage());
        }
        return resObj;
    }
    
    @GetMapping("/get-list-policy-delivery")
    @ApiOperation("Get all policy delivery by condition")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "success"), @ApiResponse(code = 500, message = "Internal Server Error"), @ApiResponse(code = 401, message = "Unauthorized"), @ApiResponse(code = 403, message = "Forbidden")})
    public DtsApiResponse getListPolicyDelivery(HttpServletRequest request, @RequestParam(value = "page", defaultValue = "0") Integer page, @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize, PolicyDeliverySearchDto searchDto) {
        long start = System.currentTimeMillis();
        try {
            searchDto.setPage(page);
            searchDto.setPageSize(pageSize);

            // Lấy dữ liệu từ service
            CmsCommonPagination<PolicyDeliveryDto> common = service.getListPolicyDelivery(searchDto, false);

            // Tạo đối tượng phản hồi
            ObjectDataRes<PolicyDeliveryDto> resObj = new ObjectDataRes<>(common.getTotalData(), common.getData());

            // Trả về phản hồi thành công
            return this.successHandler.handlerSuccess(resObj, start, null, null);

        } catch (Exception ex) {
            // Ghi lại log lỗi
            logger.error("##getListPolicyDelivery##", ex.getMessage());

            // Xử lý lỗi và trả về phản hồi lỗi
            return this.errorHandler.handlerException(ex, start, null, null);
        }
    }

    @PostMapping("/get-detail-policy-delivery")
    @ApiOperation("Get detail policy-delivery by type")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "success"), @ApiResponse(code = 500, message = "Internal Server Error"), @ApiResponse(code = 401, message = "Unauthorized"), @ApiResponse(code = 403, message = "Forbidden")})
    public DtsApiResponse getDetailProfileAdditional(HttpServletRequest request, @RequestBody PolicyDeliveryDetailsSearchDto searchDto) {
        long start = System.currentTimeMillis();
        try {
            List<PolicyDeliveryDetailsDto> data = service.getDetailPolicyDelivery(searchDto);
            return this.successHandler.handlerSuccess(data, start, null, null);
        } catch (Exception ex) {
            return this.errorHandler.handlerException(ex, start, null, null);
        }
    }

    @SuppressWarnings("rawtypes")
    @PostMapping("/export-list-policy-delivery")
    @ApiOperation("Api export excel list policy delivery")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "success"), @ApiResponse(code = 500, message = "Internal Server Error"), @ApiResponse(code = 401, message = "Unauthorized"), @ApiResponse(code = 403, message = "Forbidden"), @ApiResponse(code = 402601, message = "Error process type new")})
    public ResponseEntity exportListPolicyDelivery(HttpServletRequest request, HttpServletResponse response, @RequestBody PolicyDeliverySearchDto searchDto) {
        Locale locale = LangugeUtil.getLanguageFromHeader(request);
        searchDto.setLanguage(locale.getLanguage());
        ResponseEntity resObj = null;
        try {
            resObj = service.exportListPolicyDelivery(searchDto, response, locale);
        } catch (Exception e) {
            logger.error("##exportListPolicyDelivery##", e.getMessage());
        }
        return resObj;
    }

    @GetMapping("/get-tracking-report")
    @ApiOperation("Get area tracking report by condition")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "success"), @ApiResponse(code = 500, message = "Internal Server Error"), @ApiResponse(code = 401, message = "Unauthorized"), @ApiResponse(code = 403, message = "Forbidden")})
    public DtsApiResponse getAreaTrackingReport(HttpServletRequest request, @RequestParam(value = "page", defaultValue = "0") Integer page, @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize, TrackingReportSearchDto searchDto) {
        long start = System.currentTimeMillis();
        try {
            // Lấy dữ liệu từ service
            CmsCommonPagination<TrackingReportDto> common = service.getTrackingReport(searchDto);

            // Tạo đối tượng phản hồi
            ObjectDataRes<TrackingReportDto> resObj = new ObjectDataRes<>(common.getTotalData(), common.getData());

            // Trả về phản hồi thành công
            return this.successHandler.handlerSuccess(resObj, start, null, null);

        } catch (Exception ex) {
            // Ghi lại log lỗi
            logger.error("##getAreaTrackingReport##", ex.getMessage());

            // Xử lý lỗi và trả về phản hồi lỗi
            return this.errorHandler.handlerException(ex, start, null, null);
        }
    }

    @SuppressWarnings("rawtypes")
    @PostMapping("/export-tracking-report")
    @ApiOperation("Api export excel tracking-report")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "success"), @ApiResponse(code = 500, message = "Internal Server Error"), @ApiResponse(code = 401, message = "Unauthorized"), @ApiResponse(code = 403, message = "Forbidden"), @ApiResponse(code = 402601, message = "Error process type new")})
    public ResponseEntity exportTrackingReport(HttpServletRequest request, HttpServletResponse response, @RequestBody TrackingReportSearchDto searchDto) {
        Locale locale = LangugeUtil.getLanguageFromHeader(request);
        ResponseEntity resObj = null;
        try {
            resObj = service.exportTrackingReport(searchDto, response, locale);
        } catch (Exception e) {
            logger.error("##exportTrackingReport##", e.getMessage());
        }
        return resObj;
    }

    @PostMapping("/confirm-ems")
    @ApiOperation("Confirm EMS")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "success"), @ApiResponse(code = 500, message = "Internal Server Error"), @ApiResponse(code = 401, message = "Unauthorized"), @ApiResponse(code = 403, message = "Forbidden")})
    public DtsApiResponse confirmEms(HttpServletRequest request, @RequestBody ConfirmPolicyDeliveryDto dto) {
        long start = System.currentTimeMillis();
        try {
            List<String> ids = Arrays.asList(dto.getId());
            List<String> unitIds = Arrays.asList(dto.getUnitId());
            boolean confirmRet = RetrofitUtils.confirmPolicyDelivery("Confirm_EMS", UserProfileUtils.getFaceMask(), String.join(",", ids), String.join(",", unitIds));
            ConfirmPolicyDeliveryResultDto resObj = new ConfirmPolicyDeliveryResultDto();
            resObj.setResult(confirmRet);
            resObj.setConfirmDate(new Date());
            return this.successHandler.handlerSuccess(resObj, start, null, null);
        } catch (Exception ex) {
            logger.error("##confirmEms##", ex.getMessage());
            return this.errorHandler.handlerException(ex, start, null, null);
        }
    }

    @PostMapping("/confirm-by-agent")
    @ApiOperation("Confirm By Agent")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "success"), @ApiResponse(code = 500, message = "Internal Server Error"), @ApiResponse(code = 401, message = "Unauthorized"), @ApiResponse(code = 403, message = "Forbidden")})
    public DtsApiResponse confirmByAgent(HttpServletRequest request, @RequestBody ConfirmPolicyDeliveryDto dto) {
        long start = System.currentTimeMillis();
        try {
        	List<String> ids = Arrays.asList(dto.getId());
            boolean confirmRet = RetrofitUtils.confirmPolicyDelivery("Confirm_By_Agent", UserProfileUtils.getFaceMask(), String.join(",", ids), "");
            ConfirmPolicyDeliveryResultDto resObj = new ConfirmPolicyDeliveryResultDto();
            resObj.setResult(confirmRet);
            resObj.setConfirmDate(new Date());
            return this.successHandler.handlerSuccess(resObj, start, null, null);
        } catch (Exception ex) {
            logger.error("##confirmByAgent##", ex.getMessage());
            return this.errorHandler.handlerException(ex, start, null, null);
        }
    }
    
    @GetMapping("/get-general-report")
    @ApiOperation("Get general report by condition")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "success"), @ApiResponse(code = 500, message = "Internal Server Error"), @ApiResponse(code = 401, message = "Unauthorized"), @ApiResponse(code = 403, message = "Forbidden")})
    public DtsApiResponse getGeneralReport(HttpServletRequest request, GeneralReportSearchDto searchDto) {
        long start = System.currentTimeMillis();
        try {
            // Lấy dữ liệu từ service
            GeneralReportDto reportData = service.getGeneralReportInfo(searchDto);

            // Trả về phản hồi thành công
            return this.successHandler.handlerSuccess(reportData, start, null, null);

        } catch (Exception ex) {
            // Ghi lại log lỗi
            logger.error("##getGeneralReport##", ex.getMessage());

            // Xử lý lỗi và trả về phản hồi lỗi
            return this.errorHandler.handlerException(ex, start, null, null);
        }
    }
    
    @GetMapping("/get-k2-k2p-report")
    @ApiOperation("Get data K2/K2+ by condition")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "success"), @ApiResponse(code = 500, message = "Internal Server Error"), @ApiResponse(code = 401, message = "Unauthorized"), @ApiResponse(code = 403, message = "Forbidden")})
    public DtsApiResponse getK2K2PlusReport(HttpServletRequest request, @RequestParam(value = "page", defaultValue = "0") Integer page, @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize, ReportK2K2PlusSearchDto searchDto) {
        long start = System.currentTimeMillis();
        try {
            // Lấy dữ liệu từ service
        	ReportK2K2PlusPagination<ReportK2K2PlusDto> common = service.getK2K2PlusReport(searchDto, false);

            // Tạo đối tượng phản hồi
        	ReportK2K2PlusDataRes<ReportK2K2PlusDto> resObj = new ReportK2K2PlusDataRes<>(common.getTotalData(), common.getTotalEpp(), common.getTotalApp(), common.getTotalTp(), common.getTotalEp(), common.getData());

            // Trả về phản hồi thành công
            return this.successHandler.handlerSuccess(resObj, start, null, null);

        } catch (Exception ex) {
            // Ghi lại log lỗi
            logger.error("##getAreaTrackingReport##", ex.getMessage());

            // Xử lý lỗi và trả về phản hồi lỗi
            return this.errorHandler.handlerException(ex, start, null, null);
        }
    }
    
    @SuppressWarnings("rawtypes")
    @PostMapping("/export-k2-k2p-report")
    @ApiOperation("Api export excel K2/K2+ report")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "success"), @ApiResponse(code = 500, message = "Internal Server Error"), @ApiResponse(code = 401, message = "Unauthorized"), @ApiResponse(code = 403, message = "Forbidden"), @ApiResponse(code = 402601, message = "Error process type new")})
    public ResponseEntity exportK2K2PlusReport(HttpServletRequest request, HttpServletResponse response, @RequestBody ReportK2K2PlusSearchDto searchDto) {
        Locale locale = LangugeUtil.getLanguageFromHeader(request);
        ResponseEntity resObj = null;
        try {
            resObj = service.exportK2K2PlusReport(searchDto, response, locale);
        } catch (Exception e) {
            logger.error("##exportK2K2PlusReport##", e.getMessage());
        }
        return resObj;
    }
    
    @GetMapping("/get-aggregate-report")
    @ApiOperation("Get data aggregate-report by condition")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "success"), @ApiResponse(code = 500, message = "Internal Server Error"), @ApiResponse(code = 401, message = "Unauthorized"), @ApiResponse(code = 403, message = "Forbidden")})
    public DtsApiResponse getAggregateReport(HttpServletRequest request, @RequestParam(value = "page", defaultValue = "0") Integer page, @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize, AggregateReportSearchDto searchDto) {
        long start = System.currentTimeMillis();
        try {
            // Lấy dữ liệu từ service
        	AggregateReportDataRes resObj = service.getAggregateReport(searchDto);
        	
            // Trả về phản hồi thành công
            return this.successHandler.handlerSuccess(resObj, start, null, null);

        } catch (Exception ex) {
            // Ghi lại log lỗi
            logger.error("##getAggregateReport##", ex.getMessage());

            // Xử lý lỗi và trả về phản hồi lỗi
            return this.errorHandler.handlerException(ex, start, null, null);
        }
    }
    
    @SuppressWarnings("rawtypes")
    @PostMapping("/export-aggregate-report")
    @ApiOperation("Api export excel aggregate-report")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "success"), @ApiResponse(code = 500, message = "Internal Server Error"), @ApiResponse(code = 401, message = "Unauthorized"), @ApiResponse(code = 403, message = "Forbidden"), @ApiResponse(code = 402601, message = "Error process type new")})
    public ResponseEntity exportAggregateReport(HttpServletRequest request, HttpServletResponse response, @RequestBody AggregateReportSearchDto searchDto) {
        Locale locale = LangugeUtil.getLanguageFromHeader(request);
        ResponseEntity resObj = null;
        try {
            resObj = service.exportAggregateReport(searchDto, response, locale);
        } catch (Exception e) {
            logger.error("##exportTrackingReport##", e.getMessage());
        }
        return resObj;
    }
    
    @PostMapping("/get-link-sop")
	@ApiOperation("update user had confirm SOP")
	public DtsApiResponse getLinkSop(){
		long start = System.currentTimeMillis();
		try {
			accountService.saveConfirmSop();
			AgentInfoSaleSopDto agentInfo = service.getAgentInfoSaleSop();
            String sopUrl = RetrofitUtils.getPurchaseUrl(agentInfo.getProductCode(), 
            		agentInfo.getAgentCode(), agentInfo.getAgentName(), 
            		agentInfo.getBranchCode(), agentInfo.getEmailAddress(), 
            		agentInfo.getPhoneNumber(), agentInfo.getPartnerCode());
            
			return this.successHandler.handlerSuccess(sopUrl, start, null, null);
		} catch (Exception ex) {
			return this.errorHandler.handlerException(ex, start, null, null);
		}
	}
    
    @PostMapping("/get-claim-by-policy-no")
    @ApiOperation("Get list claim by policy no")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "success"), @ApiResponse(code = 500, message = "Internal Server Error"), @ApiResponse(code = 401, message = "Unauthorized"), @ApiResponse(code = 403, message = "Forbidden")})
    public DtsApiResponse getClaimByPolicyNo(HttpServletRequest request, @RequestBody ProposalDetailSearchDto searchDto) {
        long start = System.currentTimeMillis();
        try {
        	ObjectDataRes<PolicyClaimSearchResultDto> resObj = service.getClaimByPolicyNo(searchDto.getPolicyNo());
            return this.successHandler.handlerSuccess(resObj, start, null, null);
        } catch (Exception ex) {
            return this.errorHandler.handlerException(ex, start, null, null);
        }
    }
    
    @PostMapping("/get-assessor-comment-by-claim-no")
    @ApiOperation("Get list Assessor Comment by claim no")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "success"), @ApiResponse(code = 500, message = "Internal Server Error"), @ApiResponse(code = 401, message = "Unauthorized"), @ApiResponse(code = 403, message = "Forbidden")})
    public DtsApiResponse getAssessorCommentByClaimNo(HttpServletRequest request, @RequestBody ClaimAssessorCommentSearchDto searchDto) {
        long start = System.currentTimeMillis();
        try {
        	ObjectDataRes<ClaimAssessorCommentResultDto> resObj = service.getAssessorCommentByClaimNo(searchDto.getClaimNo());
            return this.successHandler.handlerSuccess(resObj, start, null, null);
        } catch (Exception ex) {
            return this.errorHandler.handlerException(ex, start, null, null);
        }
    }
    
    @PostMapping("/get-request-by-policy-no")
    @ApiOperation("Get list request by policy no")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "success"), @ApiResponse(code = 500, message = "Internal Server Error"), @ApiResponse(code = 401, message = "Unauthorized"), @ApiResponse(code = 403, message = "Forbidden")})
    public DtsApiResponse getRequestByPolicyNo(HttpServletRequest request, @RequestBody ProposalDetailSearchDto searchDto) {
        long start = System.currentTimeMillis();
        try {
        	ObjectDataRes<PolicyRequestSearchResultDto> resObj = service.getRequestByPolicyNo(searchDto.getPolicyNo());
            return this.successHandler.handlerSuccess(resObj, start, null, null);
        } catch (Exception ex) {
            return this.errorHandler.handlerException(ex, start, null, null);
        }
    }
    
    @GetMapping("/get-group-document")
	@ApiOperation("Get group document")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "success"),
			@ApiResponse(code = 500, message = "Internal Server Error"),
			@ApiResponse(code = 401, message = "Unauthorized"), @ApiResponse(code = 403, message = "Forbidden"),
			@ApiResponse(code = 402601, message = "Error process type new") })
    public DtsApiResponse getGroupDocument(HttpServletRequest request, 
    		@RequestParam(required = true, name = "docNo")String docNo,
    		@RequestParam(required = false, name = "deviceId")String deviceId,
       		@RequestParam(required = false, name = "clientId")String clientId) {
        long start = System.currentTimeMillis();
        try {
        	GroupDocumentResDto resObj = new GroupDocumentResDto();
        	String agentCode = UserProfileUtils.getFaceMask();
        	GroupDocumentDto groupDocument = service.getGroupDocument(docNo);
        	if (groupDocument != null) {
        		if ("NO_PROCESSING_PERSONAL_DATA".equals(groupDocument.getGroup())) {
        			resObj.setData(groupDocument.getGroup());
                } else {
                	AgentGroupResponse agentGroupRes = new AgentGroupResponse();
                	agentGroupRes.sourceSystem = "D-Success";
                    if ("AD".equals(UserProfileUtils.getChannel())) {
                    	agentGroupRes.sourceSystem = "AD Portal";
                    }
                    
                    agentGroupRes.deviceId = deviceId;
                    if(StringUtils.isEmpty(deviceId)) {
                    	agentGroupRes.deviceId = "";
                    }
                    agentGroupRes.clientId = clientId;
                    if(StringUtils.isEmpty(clientId)) {
                    	agentGroupRes.clientId = "";
                    }
                    agentGroupRes.policyNo = groupDocument.getPolicyNo();
                    agentGroupRes.proposalNo = groupDocument.getProposalNo();
                    agentGroupRes.group = groupDocument.getGroup();
                
                	Gson gson = new Gson();
                	String jsonString = gson.toJson(agentGroupRes);
                	String sdkPolicySecrectKey = jcaSystemConfigService.getValueByKey("SDK_POLICY_SECRECT_KEY", ConstantCore.COMP_CUSTOMER_ID);
                	resObj.setProposalNo(groupDocument.getProposalNo());
                	resObj.setData(Aes256.encrypt(jsonString, sdkPolicySecrectKey));
                }
        	}
        	return this.successHandler.handlerSuccess(resObj, start, null, agentCode);
        } catch (Exception ex) {
            return this.errorHandler.handlerException(ex, start, null, null);
        }
    }
}
