package vn.com.unit.ep2p.rest.cms;

import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.ObjectUtils;
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

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import vn.com.unit.cms.core.constant.CmsApiConstant;
import vn.com.unit.cms.core.dto.CmsCommonPagination;
import vn.com.unit.cms.core.module.customer.dto.ProductInformationDto;
import vn.com.unit.cms.core.module.customerManagement.dto.AdditionalDetailDto;
import vn.com.unit.cms.core.module.customerManagement.dto.FileSubmittedDto;
import vn.com.unit.cms.core.module.customerManagement.dto.OfficeInsuranceContractDetailDto;
import vn.com.unit.cms.core.module.customerManagement.dto.OfficeInsuranceDetailDto;
import vn.com.unit.cms.core.module.customerManagement.dto.OfficeInsuranceDto;
import vn.com.unit.cms.core.module.customerManagement.dto.OfficeInsuranceSearchDto;
import vn.com.unit.cms.core.module.customerManagement.dto.OfficeInsuranceTypeSumSearchDto;
import vn.com.unit.cms.core.module.customerManagement.dto.PersonalInsuranceDocumentDto;
import vn.com.unit.cms.core.module.customerManagement.dto.PersonalInsuranceDocumentSearchDto;
import vn.com.unit.cms.core.module.customerManagement.dto.ProductDto;
import vn.com.unit.cms.core.module.customerManagement.dto.RequestAdditionalDto;
import vn.com.unit.cms.core.module.customerManagement.dto.RequestAppraisalDto;
import vn.com.unit.core.res.ObjectDataRes;
import vn.com.unit.core.security.UserProfileUtils;
import vn.com.unit.dts.exception.DetailException;
import vn.com.unit.dts.exception.ErrorHandler;
import vn.com.unit.dts.exception.SuccessHandler;
import vn.com.unit.dts.web.rest.common.DtsApiResponse;
import vn.com.unit.ep2p.admin.constant.ConstantCore;
import vn.com.unit.ep2p.admin.dto.AgentInfoDb2;
import vn.com.unit.ep2p.admin.dto.TotalInsuranceDocDto;
import vn.com.unit.ep2p.admin.service.Db2ApiService;
import vn.com.unit.ep2p.core.constant.AppApiConstant;
import vn.com.unit.ep2p.core.constant.AppApiExceptionCodeConstant;
import vn.com.unit.ep2p.enumdef.GroupInsuranceDocumentsExportActiveBMEnum;
import vn.com.unit.ep2p.enumdef.GroupInsuranceDocumentsExportActiveEnum;
import vn.com.unit.ep2p.enumdef.GroupInsuranceDocumentsExportActiveUMEnum;
import vn.com.unit.ep2p.enumdef.GroupInsuranceDocumentsExportActivesUMEnum;
import vn.com.unit.ep2p.enumdef.GroupInsuranceDocumentsExportCanelEnum;
import vn.com.unit.ep2p.enumdef.GroupInsuranceDocumentsExportCanelUmEnum;
import vn.com.unit.ep2p.service.ApiAgentDetailService;
import vn.com.unit.ep2p.service.PersonalInsuranceDocService;
import vn.com.unit.ep2p.service.impl.PersonalInsuranceDocServiceImpl;
import vn.com.unit.ep2p.utils.LangugeUtil;

@RestController
@RequestMapping(CmsApiConstant.BASE_API_URL + CmsApiConstant.API_CMS_PERSONAL_INSURANCE_DOC)
@Api(tags = { CmsApiConstant.API_CMS_PERSONAL_INSURANCE_DOC_DESC })
public class PersonalInsuranceDocRest {

	@Autowired
	protected ErrorHandler errorHandler;

	@Autowired
	protected SuccessHandler successHandler;
	@Autowired
	PersonalInsuranceDocService personalInsuranceDocService;
	@Autowired
	Db2ApiService db2ApiService;
	@Autowired
	ApiAgentDetailService apiAgentDetailService;


	private Logger loggerClass = LoggerFactory.getLogger(getClass());

	private static final Logger logger = LoggerFactory.getLogger(PersonalInsuranceDocServiceImpl.class);

	@GetMapping(AppApiConstant.API_LIST_PERSONAL_INSURANCE_DOC)
	@ApiOperation("Get all personal insurance document")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "success"),
			@ApiResponse(code = 500, message = "Internal Server Error"),
			@ApiResponse(code = 401, message = "Unauthorized"), @ApiResponse(code = 403, message = "Forbidden") })

	public DtsApiResponse getListDocument(HttpServletRequest request,
			@RequestParam(value = ConstantCore.PAGE_SIZE, defaultValue = "10") Optional<Integer> pageSizeParam,
			@RequestParam(value = ConstantCore.PAGE, defaultValue = "0") Optional<Integer> pageParam,
			PersonalInsuranceDocumentSearchDto searchDto) {
		long start = System.currentTimeMillis();
		Locale locale = LangugeUtil.getLanguageFromHeader(request);
		try {
	        String agentParent = UserProfileUtils.getFaceMask();
	        if (StringUtils.isNotEmpty(agentParent) && !agentParent.equalsIgnoreCase(searchDto.getAgentCode())) {
	            boolean isChild = apiAgentDetailService.checkAgentChild(agentParent, searchDto.getAgentCode());
	            if (!isChild) {
	                throw new DetailException(AppApiExceptionCodeConstant.E4027100_APPAPI_LINK_EXISTS_ERROR);
	            }
	        }
			searchDto.setPage(pageParam.get());
			searchDto.setPageSize(pageSizeParam.get());
			CmsCommonPagination<PersonalInsuranceDocumentDto> common = personalInsuranceDocService
					.getListDocByStatus(searchDto);
			ObjectDataRes<PersonalInsuranceDocumentDto> resObj = new ObjectDataRes<>(common.getTotalData(),
					common.getData());
			return this.successHandler.handlerSuccess(resObj, start, null, null);
		} catch (Exception ex) {
			return this.errorHandler.handlerException(ex, start, null, null);
		}
	}

	@GetMapping(AppApiConstant.API_LIST_PERSONAL_INSURANCE_DOC_DETAIL)
	@ApiOperation("Get personal insurance document detail")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "success"),
			@ApiResponse(code = 500, message = "Internal Server Error"),
			@ApiResponse(code = 401, message = "Unauthorized"), @ApiResponse(code = 403, message = "Forbidden") })

	public DtsApiResponse getListDocumentDetail(HttpServletRequest request,
			@RequestParam(value = "docNo", required = false) String docNo) {
		long start = System.currentTimeMillis();
		Locale locale = LangugeUtil.getLanguageFromHeader(request);
		try {
			PersonalInsuranceDocumentDto personalInsuranceDocumentDto = personalInsuranceDocService
					.getDetailDocumentByDocNo(docNo);
			return this.successHandler.handlerSuccess(personalInsuranceDocumentDto, start, null, null);
		} catch (Exception ex) {
			return this.errorHandler.handlerException(ex, start, null, null);
		}
	}

	@GetMapping(AppApiConstant.API_LIST_PERSONAL_INSURANCE_DOC_PRODUCT)
	@ApiOperation("Get list product")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "success"),
			@ApiResponse(code = 500, message = "Internal Server Error"),
			@ApiResponse(code = 401, message = "Unauthorized"), @ApiResponse(code = 403, message = "Forbidden") })
	public DtsApiResponse getDetailDocument(HttpServletRequest request,
			@RequestParam(value = "docNo", required = false) String docNo,
			@RequestParam(value = "page", defaultValue = "0") Integer page,
			@RequestParam(value = "size", defaultValue = "5") Integer size,
			@RequestParam(value = "agentCode", required = false) String agentCode) {
		long start = System.currentTimeMillis();
		Locale locale = LangugeUtil.getLanguageFromHeader(request);
		try {
			CmsCommonPagination<ProductDto> common = personalInsuranceDocService.getListProductByDocNo(docNo, page,
					size, agentCode);
			ObjectDataRes<ProductDto> resObj = new ObjectDataRes<>(common.getTotalData(), common.getData());
			return this.successHandler.handlerSuccess(resObj, start, null, null);
		} catch (Exception ex) {
			return this.errorHandler.handlerException(ex, start, null, null);
		}
	}

	@GetMapping(AppApiConstant.API_LIST_PERSONAL_INSURANCE_REQUEST_APPRAISAL)
	@ApiOperation("Get list request appraisal")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "success"),
			@ApiResponse(code = 500, message = "Internal Server Error"),
			@ApiResponse(code = 401, message = "Unauthorized"), @ApiResponse(code = 403, message = "Forbidden") })
	public DtsApiResponse getListRequestAppraisal(HttpServletRequest request,
			@RequestParam(value = "docNo", required = false) String docNo,
			@RequestParam(value = "page", defaultValue = "0") Integer page,
			@RequestParam(value = "size", defaultValue = "5") Integer size,
			@RequestParam(value = "agentCode", required = false) String agentCode) {
		long start = System.currentTimeMillis();
		Locale locale = LangugeUtil.getLanguageFromHeader(request);
		try {
			CmsCommonPagination<RequestAppraisalDto> common = personalInsuranceDocService.getListRequestAppraisal(docNo,
					page, size, agentCode);
			ObjectDataRes<RequestAppraisalDto> resObj = new ObjectDataRes<>(common.getTotalData(), common.getData());
			return this.successHandler.handlerSuccess(resObj, start, null, null);
		} catch (Exception ex) {
			return this.errorHandler.handlerException(ex, start, null, null);
		}
	}

	@GetMapping(AppApiConstant.API_LIST_PERSONAL_INSURANCE_REQUEST_ADDITIONAL)
	@ApiOperation("Get list request appraisal")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "success"),
			@ApiResponse(code = 500, message = "Internal Server Error"),
			@ApiResponse(code = 401, message = "Unauthorized"), @ApiResponse(code = 403, message = "Forbidden") })
	public DtsApiResponse getListAdditionalRequest(HttpServletRequest request,
			@RequestParam(value = "docNo", required = true) String docNo,
			@RequestParam(value = "page", defaultValue = "0") Integer page,
			@RequestParam(value = "size", defaultValue = "5") Integer size,
			@RequestParam(value = "agentCode", required = true) String agentCode) {
		long start = System.currentTimeMillis();
		Locale locale = LangugeUtil.getLanguageFromHeader(request);
		try {
			CmsCommonPagination<RequestAdditionalDto> common = personalInsuranceDocService
					.getListRequestAdditional(docNo, page, size, agentCode);
			ObjectDataRes<RequestAdditionalDto> resObj = new ObjectDataRes<>(common.getTotalData(), common.getData());
			return this.successHandler.handlerSuccess(resObj, start, null, null);
		} catch (Exception ex) {
			return this.errorHandler.handlerException(ex, start, null, null);
		}
	}

	@GetMapping(AppApiConstant.API_LIST_PERSONAL_INSURANCE_FILE)
	@ApiOperation("Get list File")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "success"),
			@ApiResponse(code = 500, message = "Internal Server Error"),
			@ApiResponse(code = 401, message = "Unauthorized"), @ApiResponse(code = 403, message = "Forbidden") })
	public DtsApiResponse getListFileSubmitted(HttpServletRequest request,
			@RequestParam(value = "docNo", required = false) String docNo,
			@RequestParam(value = "type", required = false) String type,
			@RequestParam(value = "page", defaultValue = "0") Integer page,
			@RequestParam(value = "size", defaultValue = "5") Integer size,
			@RequestParam(value = "agentCode", required = false) String agentCode) {
		long start = System.currentTimeMillis();
		Locale locale = LangugeUtil.getLanguageFromHeader(request);
		try {
			CmsCommonPagination<FileSubmittedDto> common = personalInsuranceDocService.getListFileSubmitted(docNo, page,
					size, agentCode);
			ObjectDataRes<FileSubmittedDto> resObj = new ObjectDataRes<>(common.getTotalData(), common.getData());
			return this.successHandler.handlerSuccess(resObj, start, null, null);
		} catch (Exception ex) {
			return this.errorHandler.handlerException(ex, start, null, null);
		}
	}

	// LIT HSYCBH THEO BRANCH
	@GetMapping(AppApiConstant.API_LIST_OFFICE_INSURANCE)
	@ApiOperation("Get list office insurance document")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "success"),
			@ApiResponse(code = 500, message = "Internal Server Error"),
			@ApiResponse(code = 401, message = "Unauthorized"), @ApiResponse(code = 403, message = "Forbidden") })
	public DtsApiResponse getListOfficeInsurance(HttpServletRequest request,
			@RequestParam(value = ConstantCore.PAGE_SIZE, defaultValue = "10") Optional<Integer> pageSizeParam,
			@RequestParam(value = ConstantCore.PAGE, defaultValue = "0") Optional<Integer> pageParam,
			OfficeInsuranceSearchDto searchDto) {
		long start = System.currentTimeMillis();
		Locale locale = LangugeUtil.getLanguageFromHeader(request);
		try {
			searchDto.setPage(pageParam.get());
			searchDto.setPageSize(pageSizeParam.get());
			CmsCommonPagination<OfficeInsuranceDto> common = personalInsuranceDocService
					.getListOfficeDocument(searchDto);
			ObjectDataRes<OfficeInsuranceDto> resObj = new ObjectDataRes<>(common.getTotalData(), common.getData());
			return this.successHandler.handlerSuccess(resObj, start, null, null);
		} catch (Exception ex) {
			return this.errorHandler.handlerException(ex, start, null, null);
		}
	}

	// LIT HSYCBH by status
	@GetMapping(AppApiConstant.API_LIST_OFFICE_INSURANCE_TYPE)
	@ApiOperation("Get list office insurance document by type")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "success"),
			@ApiResponse(code = 500, message = "Internal Server Error"),
			@ApiResponse(code = 401, message = "Unauthorized"), @ApiResponse(code = 403, message = "Forbidden") })
	public DtsApiResponse getListOfficeInsuranceByType(HttpServletRequest request,
			@RequestParam(value = "page", defaultValue = "0") Integer page,
			@RequestParam(value = "size", defaultValue = "5") Integer size, OfficeInsuranceTypeSumSearchDto searchDto) {
		long start = System.currentTimeMillis();
		try {
			CmsCommonPagination<OfficeInsuranceDetailDto> common = personalInsuranceDocService
					.getListOfficeDocumentByType(searchDto);
			ObjectDataRes<OfficeInsuranceDetailDto> resObj = new ObjectDataRes<>(common.getTotalData(),
					common.getData());
			return this.successHandler.handlerSuccess(resObj, start, null, null);
		} catch (Exception ex) {
			return this.errorHandler.handlerException(ex, start, null, null);
		}
	}

	@GetMapping(AppApiConstant.API_LIST_OFFICE_INSURANCE_CONTRACT_TYPE)
	@ApiOperation("Get list office insurance contract by type")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "success"),
			@ApiResponse(code = 500, message = "Internal Server Error"),
			@ApiResponse(code = 401, message = "Unauthorized"), @ApiResponse(code = 403, message = "Forbidden") })
	public DtsApiResponse getListOfficeInsuranceContractByType(HttpServletRequest request,
			@RequestParam(value = "page", defaultValue = "0") Integer page,
			@RequestParam(value = "size", defaultValue = "5") Integer size, OfficeInsuranceTypeSumSearchDto searchDto) {
		long start = System.currentTimeMillis();
		try {
			searchDto.setPage(page);
			searchDto.setPageSize(size);
			CmsCommonPagination<OfficeInsuranceContractDetailDto> common = personalInsuranceDocService
					.getListOfficeDocumentContractByType(searchDto);
			ObjectDataRes<OfficeInsuranceContractDetailDto> resObj = new ObjectDataRes<>(common.getTotalData(),
					common.getData());
			return this.successHandler.handlerSuccess(resObj, start, null, null);
		} catch (Exception ex) {
			return this.errorHandler.handlerException(ex, start, null, null);
		}
	}

	@GetMapping(AppApiConstant.API_LIST_OFFICE_INSURANCE_CONTRACT_TYPE_TYPE)
	@ApiOperation("Get list office insurance contract detail by type")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "success"),
			@ApiResponse(code = 500, message = "Internal Server Error"),
			@ApiResponse(code = 401, message = "Unauthorized"), @ApiResponse(code = 403, message = "Forbidden") })
	public DtsApiResponse getListOfficeInsuranceContractDetailByType(HttpServletRequest request,
OfficeInsuranceTypeSumSearchDto searchDto) {
		long start = System.currentTimeMillis();
		try {
			CmsCommonPagination<OfficeInsuranceContractDetailDto> common = personalInsuranceDocService
					.getListOfficeDocumentContractDetailByType(searchDto);
			ObjectDataRes<OfficeInsuranceContractDetailDto> resObj = new ObjectDataRes<>(common.getTotalData(),
					common.getData());
			return this.successHandler.handlerSuccess(resObj, start, null, null);
		} catch (Exception ex) {
			return this.errorHandler.handlerException(ex, start, null, null);
		}
	}

	@SuppressWarnings("rawtypes")
	@PostMapping(AppApiConstant.API_EXPORT_OFFICE_INSURANCE)
	@ApiOperation("Api export office insurance ")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "success"),
			@ApiResponse(code = 500, message = "Internal Server Error"),
			@ApiResponse(code = 401, message = "Unauthorized"), @ApiResponse(code = 403, message = "Forbidden"),
			@ApiResponse(code = 402601, message = "Error process type new") })

	public ResponseEntity exportListPolicyByAgentByCondition(HttpServletRequest request,
			@RequestBody OfficeInsuranceSearchDto searchDto, HttpServletResponse response) {
		ResponseEntity resObj = null;
		try {

        	searchDto.setAgentGroup(searchDto.getAgentType());
			AgentInfoDb2 agentDetail = db2ApiService.getParentByAgentCode(searchDto.getAgentCode(), searchDto.getAgentType(), searchDto.getOrgId());
			if (ObjectUtils.isNotEmpty(agentDetail)) {
				searchDto.setAgentName(agentDetail.getAgentName());
				searchDto.setOrgName(agentDetail.getOrgName());
				searchDto.setAgentType(agentDetail.getAgentType());
			}
			resObj = personalInsuranceDocService.exportListOfficeDocument(searchDto);
		} catch (Exception e) {
			logger.error("##exportLis##", e.getMessage());
		}
		return resObj;
	}
	private String formatPolicyNumber(int digits, String policyNumber){
		if(StringUtils.isEmpty(policyNumber)) {
			return "";
		}
		return IntStream.range(0, digits - policyNumber.length()).mapToObj(i -> "0").collect(Collectors.joining("")).concat(policyNumber);
	}
	@SuppressWarnings("rawtypes")
	@PostMapping(AppApiConstant.API_EXPORT_OFFICE_INSURANCE_BM_UM)
	@ApiOperation("Api export office insurance BM UM ")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "success"),
			@ApiResponse(code = 500, message = "Internal Server Error"),
			@ApiResponse(code = 401, message = "Unauthorized"), @ApiResponse(code = 403, message = "Forbidden"),
			@ApiResponse(code = 402601, message = "Error process type new") })

	public ResponseEntity exportListPolicyByAgentByConditionBMUM(HttpServletRequest request,
			@RequestBody OfficeInsuranceTypeSumSearchDto searchDto, HttpServletResponse response) {
		ResponseEntity resObj = null;
		try {

        	searchDto.setAgentGroup(searchDto.getAgentType());
			searchDto.setPage(0);
			searchDto.setPageSize(0);
			searchDto.setSize(0);
			CmsCommonPagination<OfficeInsuranceContractDetailDto> common = personalInsuranceDocService
					.exportListPolicyByAgentByConditionBMUM(searchDto);
			int total = common.getTotalData();

			AgentInfoDb2 agentDetail = db2ApiService.getParentByAgentCode(searchDto.getAgentCode(), searchDto.getAgentType(), searchDto.getOrgId());
			if (ObjectUtils.isNotEmpty(agentDetail)) {
				searchDto.setAgentName(agentDetail.getAgentName());
				searchDto.setOrgName(agentDetail.getOrgName());
				searchDto.setAgentType(agentDetail.getAgentType());
				searchDto.setOrgId(agentDetail.getOrgId());
			}
			
			for (OfficeInsuranceContractDetailDto ls : common.getData()) {
				ls.setAgent(ls.getAgentType() + ":" + ls.getAgentCode() + "-" + ls.getAgentName());

				ls.setManager(ls.getManagerAgentType() + ":"
						+ ls.getManagerAgentCode().replace("A", "").replace("B", "").replace("C", "") + "-"
						+ ls.getManagerAgentName());

				if (!StringUtils.isBlank(ls.getPolicyNo()))
					ls.setPolicyNo(formatPolicyNumber(9, ls.getPolicyNo()));

				if (StringUtils.isNotEmpty(ls.getInsuranceBuyer()))
					ls.setInsuranceBuyer(ls.getInsuranceBuyer().toUpperCase());

				if (StringUtils.isNotEmpty(ls.getInsuredPerson()))
					ls.setInsuredPerson(ls.getInsuredPerson().toUpperCase());
			}
			
			String[] titleHeader = null;
			if (searchDto.getAgentGroup().equalsIgnoreCase("BM")) {

				if (searchDto.getType().equalsIgnoreCase("ACTIVE")) {
					titleHeader = new String[] { "STT", "Quản lý", "Tư vấn tài chính", "Số HĐBH", "Bên mua BH", "NĐBH chính", "Ngày hiệu lực","Ngày phát hành","Định kỳ đóng phí","Phí dự tính định kỳ","Phí định kỳ/cơ bản định kỳ","Phí đầu tiên","Số tiền bảo hiểm"};
					resObj = personalInsuranceDocService.exportListDataWithHeader(common.getData(), "phat_hanh_cap_phong_ban",
							"DANH SÁCH HỢP ĐỒNG PHÁT HÀNH", titleHeader, "A9", GroupInsuranceDocumentsExportActiveEnum.class,
							OfficeInsuranceContractDetailDto.class,searchDto.getAgentCode().replace(searchDto.getOrgId(), ""),searchDto.getAgentType(),searchDto.getAgentName(),searchDto.getOrgName(),total,searchDto.getAgentGroup());
				}
				if (searchDto.getType().equalsIgnoreCase("CANCEL")) {
					titleHeader = new String[] { "STT", "Quản lý", "Tư vấn tài chính", "Số HĐBH", "Bên mua BH","NĐBH chính", "Ngày hiệu lực","Ngày hủy","Định kỳ đóng phí","Phí dự tính định kỳ","Phí định kỳ/cơ bản định kỳ","Phí đầu tiên","Số tiền bảo hiểm","Chi phí kiếm tra y tế"};
					resObj = personalInsuranceDocService.exportListDataWithHeader(common.getData(), "huy_cap_phong_ban",
							"DANH SÁCH HỢP ĐỒNG HỦY", titleHeader, "A9", GroupInsuranceDocumentsExportCanelEnum.class,
							OfficeInsuranceContractDetailDto.class,searchDto.getAgentCode().replace(searchDto.getOrgId(), ""),searchDto.getAgentGroup(),searchDto.getAgentName(),searchDto.getOrgName(),total,searchDto.getAgentGroup());
				}

			}

			else if (searchDto.getAgentGroup().equalsIgnoreCase("UM")) {

				if (searchDto.getType().equalsIgnoreCase("ACTIVE")) {
					titleHeader = new String[] { "STT", "Tư vấn tài chính", "Số HĐBH", "Bên mua BH", "NĐBH chính", "Ngày hiệu lực","Ngày phát hành","Định kỳ đóng phí","Phí dự tính định kỳ","Phí định kỳ/cơ bản định kỳ","Phí đầu tiên","Số tiền bảo hiểm"};
					resObj = personalInsuranceDocService.exportListDataWithHeader(common.getData(), "phat_hanh_nhom_phong",
							"DANH SÁCH HỢP ĐỒNG PHÁT HÀNH ", titleHeader, "A9", GroupInsuranceDocumentsExportActiveUMEnum.class,
							OfficeInsuranceContractDetailDto.class,searchDto.getAgentCode().replace("A", "").replace("B", "").replace("C", ""),searchDto.getAgentType(),searchDto.getAgentName(),searchDto.getOrgName(),total,searchDto.getAgentGroup());
				}
				if (searchDto.getType().equalsIgnoreCase("CANCEL")) {
					titleHeader = new String[] { "STT", "Tư vấn tài chính", "Số HĐBH", "Bên mua BH", "NĐBH chính", "Ngày hiệu lực","Ngày hủy","Định kỳ đóng phí","Phí dự tính định kỳ","Phí định kỳ/cơ bản định kỳ","Phí đầu tiên","Số tiền bảo hiểm","Chi phí kiếm tra y tế"};
					resObj = personalInsuranceDocService.exportListDataWithHeader(common.getData(), "huy_nhom_phong",
							"DANH SÁCH HỢP ĐỒNG HỦY ", titleHeader, "A9", GroupInsuranceDocumentsExportCanelUmEnum.class,
							OfficeInsuranceContractDetailDto.class,searchDto.getAgentCode().replace("A", "").replace("B", "").replace("C", ""),searchDto.getAgentType(),searchDto.getAgentName(),searchDto.getOrgName(),total,searchDto.getAgentGroup());
				}
			}

		} catch (Exception e) {
			logger.error("##exportPolicyExpired##", e);
		}
		return resObj;
	}
	
	@SuppressWarnings("rawtypes")
	@PostMapping(AppApiConstant.API_EXPORT_OFFICE_INSURANCE_BM_UM_DETAIL)
	@ApiOperation("Api export office insurance BM UM DETAIL")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "success"),
			@ApiResponse(code = 500, message = "Internal Server Error"),
			@ApiResponse(code = 401, message = "Unauthorized"), @ApiResponse(code = 403, message = "Forbidden"),
			@ApiResponse(code = 402601, message = "Error process type new") })

	public ResponseEntity exportListPolicyByAgentByConditionBMUMDetail(HttpServletRequest request,
			@RequestBody OfficeInsuranceTypeSumSearchDto searchDto, HttpServletResponse response) {
		ResponseEntity resObj = null;
		try {
        	searchDto.setAgentGroup(searchDto.getAgentType());
			resObj = personalInsuranceDocService.exportListOfficeDocumentBMUMDetail(searchDto);
		} catch (Exception e) {
			logger.error("##exportLis##", e.getMessage());
		}
		return resObj;
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
			@RequestBody PersonalInsuranceDocumentSearchDto searchDto) {
		Locale locale = LangugeUtil.getLanguageFromHeader(request);
		searchDto.setLanguage(locale.getLanguage());
		ResponseEntity resObj = null;
		try {
        	searchDto.setAgentGroup(searchDto.getAgentType());
			resObj = personalInsuranceDocService.exportListPrersonalInsuranceDocByCondition(searchDto, response,
					locale);
		} catch (Exception e) {
			loggerClass.error("##exportLis##", e.getMessage());
		}
		return resObj;
	}
    @PostMapping(AppApiConstant.API_EXPORT_OFFICE_INSURANCE_TYPE_BM_UM)
    @ApiOperation("Api export office insurance type BM UM ")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "success"),
            @ApiResponse(code = 500, message = "Internal Server Error"),
            @ApiResponse(code = 401, message = "Unauthorized"), @ApiResponse(code = 403, message = "Forbidden"),
            @ApiResponse(code = 402601, message = "Error process type new") })

    public ResponseEntity exportListPolicyByAgentByTypeBMUM(HttpServletRequest request,
         @RequestBody OfficeInsuranceTypeSumSearchDto searchDto, HttpServletResponse response) {
        ResponseEntity resObj = null;
        try {
            searchDto.setPage(0);
            searchDto.setPageSize(0);
            searchDto.setSize(0);
            CmsCommonPagination<OfficeInsuranceDetailDto> common = personalInsuranceDocService.getListOfficeDocumentByType(searchDto);
			int total =0;
			if (common.getData().size() > 0)
				total = common.getData().stream().mapToInt(OfficeInsuranceDetailDto::getTotalProposal).sum();
			AgentInfoDb2 agentDetail = db2ApiService.getParentByAgentCode(searchDto.getAgentCode(), searchDto.getAgentType(), searchDto.getOrgId());
			if (ObjectUtils.isNotEmpty(agentDetail)) {
				searchDto.setAgentName(agentDetail.getAgentName());
				searchDto.setOrgName(agentDetail.getOrgName());
				searchDto.setAgentType(agentDetail.getAgentType());
				searchDto.setOrgId(agentDetail.getOrgId());
			}
            String[] titleHeader = null;

            if (searchDto.getAgentGroup().equalsIgnoreCase("BM")) {
                if (searchDto.getType().equalsIgnoreCase("1")) {
                    titleHeader = new String[] { "STT", "Quản lý", "Tư vấn tài chính", "Tổng số HSYCBH" };
                    resObj = personalInsuranceDocService.exportListDataWithHeader(common.getData(), "cap_phong_ban",
                            "DANH SÁCH TẤT CẢ HỒ SƠ YÊU CẦU BẢO HIỂM CẤP PHÒNG BAN", titleHeader, "A9", GroupInsuranceDocumentsExportActiveBMEnum.class,
                            OfficeInsuranceDetailDto.class,searchDto.getAgentCode().replace(searchDto.getOrgId(), ""),searchDto.getAgentType(),searchDto.getAgentName(),searchDto.getOrgName(),total,searchDto.getAgentGroup());
                }else if (searchDto.getType().equalsIgnoreCase("2")) {
                    titleHeader = new String[] { "STT", "Quản lý", "Tư vấn tài chính", "Tổng số HSYCBH" };
                    resObj = personalInsuranceDocService.exportListDataWithHeader(common.getData(), "cho_bo_sung_cap_phong_ban",
                            "DANH SÁCH HỒ SƠ YÊU CẦU BẢO HIỂM CHỜ BỔ SUNG CẤP PHÒNG BAN", titleHeader, "A9", GroupInsuranceDocumentsExportActiveBMEnum.class,
                            OfficeInsuranceDetailDto.class,searchDto.getAgentCode().replace(searchDto.getOrgId(), ""),searchDto.getAgentType(),searchDto.getAgentName(),searchDto.getOrgName(),total,searchDto.getAgentGroup());
                }else if (searchDto.getType().equalsIgnoreCase("3")) {
                    titleHeader = new String[] { "STT", "Quản lý", "Tư vấn tài chính", "Tổng số HSYCBH" };
                    resObj = personalInsuranceDocService.exportListDataWithHeader(common.getData(), "da_phat_hanh_cap_phong_ban",
                            "DANH SÁCH HỒ SƠ YÊU CẦU BẢO HIỂM ĐÃ PHÁT HÀNH CẤP PHÒNG BAN", titleHeader, "A9", GroupInsuranceDocumentsExportActiveBMEnum.class,
                            OfficeInsuranceDetailDto.class,searchDto.getAgentCode().replace(searchDto.getOrgId(), ""),searchDto.getAgentType(),searchDto.getAgentName(),searchDto.getOrgName(),total,searchDto.getAgentGroup());
                }else if (searchDto.getType().equalsIgnoreCase("4")) {
                    titleHeader = new String[] { "STT", "Quản lý", "Tư vấn tài chính", "Tổng số HSYCBH" };
                    resObj = personalInsuranceDocService.exportListDataWithHeader(common.getData(), "tu_choi_cap_phong_ban",
                            "DANH SÁCH HỒ SƠ YÊU CẦU BẢO HIỂM TỪ CHỐI CẤP PHÒNG BAN", titleHeader, "A9", GroupInsuranceDocumentsExportActiveBMEnum.class,
                            OfficeInsuranceDetailDto.class,searchDto.getAgentCode().replace(searchDto.getOrgId(), ""),searchDto.getAgentType(),searchDto.getAgentName(),searchDto.getOrgName(),total,searchDto.getAgentGroup());
                }

            }

            else if (searchDto.getAgentGroup().equalsIgnoreCase("UM")) {

                if (searchDto.getType().equalsIgnoreCase("1")) {
                    titleHeader = new String[] { "STT", "Tư vấn tài chính", "Tổng số HSYCBH" };
                    resObj = personalInsuranceDocService.exportListDataWithHeader(common.getData(), "cap_phong_ban",
                            "DANH SÁCH TẤT CẢ HỒ SƠ YÊU CẦU BẢO HIỂM CẤP PHÒNG BAN", titleHeader, "A9", GroupInsuranceDocumentsExportActivesUMEnum.class,
                            OfficeInsuranceDetailDto.class,searchDto.getAgentCode().replace("A", "").replace("B", "").replace("C", ""),searchDto.getAgentType(),searchDto.getAgentName(),searchDto.getOrgName(),total,searchDto.getAgentGroup());
                } else if (searchDto.getType().equalsIgnoreCase("2")) {
                    titleHeader = new String[] { "STT", "Tư vấn tài chính", "Tổng số HSYCBH" };
                    resObj = personalInsuranceDocService.exportListDataWithHeader(common.getData(), "cho_bo_sung_cap_phong_ban",
                            "DANH SÁCH HỒ SƠ YÊU CẦU BẢO HIỂM CHỜ BỔ SUNG CẤP PHÒNG BAN", titleHeader, "A9", GroupInsuranceDocumentsExportActivesUMEnum.class,
                            OfficeInsuranceDetailDto.class,searchDto.getAgentCode().replace("A", "").replace("B", "").replace("C", ""),searchDto.getAgentType(),searchDto.getAgentName(),searchDto.getOrgName(),total,searchDto.getAgentGroup());
                } else if (searchDto.getType().equalsIgnoreCase("3")) {
                    titleHeader = new String[] { "STT", "Tư vấn tài chính", "Tổng số HSYCBH" };
                    resObj = personalInsuranceDocService.exportListDataWithHeader(common.getData(), "da_phat_hanh_cap_phong_ban",
                            "DANH SÁCH HỒ SƠ YÊU CẦU BẢO HIỂM ĐÃ PHÁT HÀNH CẤP PHÒNG BAN", titleHeader, "A9", GroupInsuranceDocumentsExportActivesUMEnum.class,
                            OfficeInsuranceDetailDto.class,searchDto.getAgentCode().replace("A", "").replace("B", "").replace("C", ""),searchDto.getAgentType(),searchDto.getAgentName(),searchDto.getOrgName(),total,searchDto.getAgentGroup());
                } else if (searchDto.getType().equalsIgnoreCase("4")) {
                    titleHeader = new String[] { "STT", "Tư vấn tài chính", "Tổng số HSYCBH" };
                    resObj = personalInsuranceDocService.exportListDataWithHeader(common.getData(), "tu_choi_cap_phong_ban",
                            "DANH SÁCH HỒ SƠ YÊU CẦU BẢO HIỂM TỪ CHỐI CẤP PHÒNG BAN", titleHeader, "A9", GroupInsuranceDocumentsExportActivesUMEnum.class,
                            OfficeInsuranceDetailDto.class,searchDto.getAgentCode().replace("A", "").replace("B", "").replace("C", ""),searchDto.getAgentType(),searchDto.getAgentName(),searchDto.getOrgName(),total,searchDto.getAgentGroup());
                }
            }

        } catch (Exception e) {
            logger.error("##exportPolicyExpired##", e);
        }
        return resObj;
    }
    
    //detail insurance
    //chi tiet sp
    @GetMapping("get-detail-product-personal-insurance")
    @ApiOperation("Get detail personal insurance contract by type")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "success"),
            @ApiResponse(code = 500, message = "Internal Server Error"),
            @ApiResponse(code = 401, message = "Unauthorized"), @ApiResponse(code = 403, message = "Forbidden") })
    public DtsApiResponse getDetailProduct(HttpServletRequest request, String policyNo) {
        long start = System.currentTimeMillis();
        try {
            List<ProductInformationDto> data = personalInsuranceDocService.getDetailProduct(policyNo);//so hd
            return this.successHandler.handlerSuccess(data, start, null, null);
        } catch (Exception ex) {
            return this.errorHandler.handlerException(ex, start, null, null);
        }
    }
    
    //chi tiet hs
    @GetMapping("get-detail-profile-personal-insurance")
    @ApiOperation("Get detail personal insurance contract by type")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "success"),
            @ApiResponse(code = 500, message = "Internal Server Error"),
            @ApiResponse(code = 401, message = "Unauthorized"), @ApiResponse(code = 403, message = "Forbidden") })
    public DtsApiResponse getDetailProfile(HttpServletRequest request, String docType, String docNo, String agentCode, String policyNo) {
        long start = System.currentTimeMillis();
        try {
        	PersonalInsuranceDocumentDto data  = new PersonalInsuranceDocumentDto();
        	data = personalInsuranceDocService.getDetailProfile(docType, docNo);
        	if(ObjectUtils.isNotEmpty(data) && data.getPolicyNo() != null && (!data.getAgentCode().equals(agentCode) || !Integer.valueOf(data.getPolicyNo()).equals(Integer.valueOf(policyNo)))) {
        		data  = new PersonalInsuranceDocumentDto();
        		data.setCheckData(false);
        	}
        	
            return this.successHandler.handlerSuccess(data, start, null, null);
        } catch (Exception ex) {
            return this.errorHandler.handlerException(ex, start, null, null);
        }
    }
    
    //chi tiet yêu cầu bổ sung
    @GetMapping("get-detail-profile-additional-personal-insurance")
    @ApiOperation("Get detail personal insurance contract by type")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "success"),
            @ApiResponse(code = 500, message = "Internal Server Error"),
            @ApiResponse(code = 401, message = "Unauthorized"), @ApiResponse(code = 403, message = "Forbidden") })
    public DtsApiResponse getDetailProfileAdditional(HttpServletRequest request, String policyNo) {//
        long start = System.currentTimeMillis();
        try {
            List<AdditionalDetailDto> data = personalInsuranceDocService.getDetailAdditional(policyNo);//so hd
            return this.successHandler.handlerSuccess(data, start, null, null);
        } catch (Exception ex) {
            return this.errorHandler.handlerException(ex, start, null, null);
        }
    }

    @GetMapping("get-total-insurance-by-agent")
    @ApiOperation("Get detail personal insurance contract by type")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "success"),
            @ApiResponse(code = 500, message = "Internal Server Error"),
            @ApiResponse(code = 401, message = "Unauthorized"), @ApiResponse(code = 403, message = "Forbidden") })
    public DtsApiResponse getTotalInsuranceByAgent(HttpServletRequest request, String agentCode, String agentGroup, String orgCode) {//
        long start = System.currentTimeMillis();
        try {
        	// TotalInsuranceDocDto
            List<TotalInsuranceDocDto> data = db2ApiService.getTotalInsuranceByAgent(agentCode, agentGroup, orgCode);
            ObjectDataRes<TotalInsuranceDocDto> resObj = new ObjectDataRes<>(0, data);
            return this.successHandler.handlerSuccess(resObj, start, null, agentCode);
        } catch (Exception ex) {
            return this.errorHandler.handlerException(ex, start, null, null);
        }
    }
    
}
