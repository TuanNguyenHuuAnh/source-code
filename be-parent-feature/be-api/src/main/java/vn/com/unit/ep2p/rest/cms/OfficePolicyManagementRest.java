package vn.com.unit.ep2p.rest.cms;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.CollectionUtils;
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
import vn.com.unit.cms.core.module.contract.dto.ContractDueDateSearchDto;
import vn.com.unit.cms.core.module.contract.dto.ContractSearchDueDateResultDto;
import vn.com.unit.cms.core.module.customerManagement.dto.ClaimGroupDto;
import vn.com.unit.cms.core.module.customerManagement.dto.ContractBusinessGroupSearchDto;
import vn.com.unit.cms.core.module.customerManagement.dto.ContractBusinessHandleDto;
import vn.com.unit.cms.core.module.customerManagement.dto.ContractClaimGroupSearchDto;
import vn.com.unit.cms.core.module.customerManagement.dto.ContractEffectGroupDto;
import vn.com.unit.cms.core.module.customerManagement.dto.ContractEffectGroupSearchDto;
import vn.com.unit.cms.core.module.customerManagement.dto.ContractFeeGroupDto;
import vn.com.unit.cms.core.module.customerManagement.dto.ContractFeeGroupSearchDto;
import vn.com.unit.cms.core.module.customerManagement.dto.ContractOverdueFeeRypDto;
import vn.com.unit.cms.core.module.customerManagement.dto.FinanceSupportStandardDto;
import vn.com.unit.cms.core.module.customerManagement.dto.OfficePolicyClaimDetailDto;
import vn.com.unit.cms.core.module.customerManagement.dto.OfficePolicyExpiredDetailDto;
import vn.com.unit.cms.core.module.customerManagement.dto.OfficePolicyOrphanDetailDto;
import vn.com.unit.cms.core.module.customerManagement.dto.PolicyExpiredSearchDto;
import vn.com.unit.cms.core.module.customerManagement.dto.PolicyMaturedDetailDto;
import vn.com.unit.cms.core.module.customerManagement.dto.PolicyMaturedSearchDto;
import vn.com.unit.cms.core.module.customerManagement.dto.PolicyOrphanSearchDto;
import vn.com.unit.cms.core.module.customerManagement.dto.TotalClaimDto;
import vn.com.unit.cms.core.module.customerManagement.dto.TotalClaimGroupDto;
import vn.com.unit.cms.core.module.customerManagement.dto.TotalInsuranceContractGroupDto;
import vn.com.unit.cms.core.module.customerManagement.dto.TotalInsuranceContractGroupSearchDto;
import vn.com.unit.cms.core.module.customerManagement.dto.TotalPolicyDto;
import vn.com.unit.core.res.ObjectDataRes;
import vn.com.unit.core.security.UserProfileUtils;
import vn.com.unit.dts.exception.DetailException;
import vn.com.unit.dts.exception.ErrorHandler;
import vn.com.unit.dts.exception.SuccessHandler;
import vn.com.unit.dts.web.rest.common.DtsApiResponse;
import vn.com.unit.ep2p.admin.service.Db2ApiService;
import vn.com.unit.ep2p.core.constant.AppApiConstant;
import vn.com.unit.ep2p.core.constant.AppApiExceptionCodeConstant;
import vn.com.unit.ep2p.enumdef.ContractBusinessGroupBmEnum;
import vn.com.unit.ep2p.enumdef.ContractBusinessGroupUmEnum;
import vn.com.unit.ep2p.enumdef.ContractClaimGroupBmEnum;
import vn.com.unit.ep2p.enumdef.ContractClaimGroupUmEnum;
import vn.com.unit.ep2p.enumdef.ContractEffectGroupBmEnum;
import vn.com.unit.ep2p.enumdef.ContractEffectGroupUmEnum;
import vn.com.unit.ep2p.enumdef.ContractFeeGroupBmEnum;
import vn.com.unit.ep2p.enumdef.ContractFeeGroupFcEnum;
import vn.com.unit.ep2p.enumdef.ContractFeeGroupUmEnum;
import vn.com.unit.ep2p.enumdef.ContractOverdueFeeRypBmEnum;
import vn.com.unit.ep2p.enumdef.ContractOverdueFeeRypUmEnum;
import vn.com.unit.ep2p.enumdef.FinanceSupportStandardEnum;
import vn.com.unit.ep2p.enumdef.OfficePolicyExpiredBmEnum;
import vn.com.unit.ep2p.enumdef.OfficePolicyExpiredUmEnum;
import vn.com.unit.ep2p.enumdef.OfficePolicyOrphanEnum;
import vn.com.unit.ep2p.enumdef.PolicyMaturedGroupBmEnum;
import vn.com.unit.ep2p.enumdef.PolicyMaturedGroupUmEnum;
import vn.com.unit.ep2p.service.ApiAgentDetailService;
import vn.com.unit.ep2p.service.ApiContractService;
import vn.com.unit.ep2p.service.OfficePolicyService;
import vn.com.unit.ep2p.utils.StreamUtils;

/**
 * @Last updated: 22/03/2024    nt.tinh SR16136 - Fix lỗi phát hiện trong quá trình Pentest - 2023
 */
@RestController
@RequestMapping(CmsApiConstant.BASE_API_ODS_URL + CmsApiConstant.API_OFFICE_POLICY)
@Api(tags = { CmsApiConstant.API_ODS_OFFICE_POLICY_DESCR })
public class OfficePolicyManagementRest {

	@Autowired
	protected ErrorHandler errorHandler;

	@Autowired
	protected SuccessHandler successHandler;

	@Autowired
	private OfficePolicyService getListOfficeDocument;

    @Autowired
    ApiContractService apiContractService;
	
	@Autowired
	Db2ApiService db2ApiService;

	@Autowired
	ApiAgentDetailService apiAgentDetailService;

	
	private Logger logger = LoggerFactory.getLogger(getClass());
	
	//expired
	@GetMapping(AppApiConstant.API_LIST_OFFICE_POLICY_EXPIRED)
	@ApiOperation("Get detail policy expired")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "success"), @ApiResponse(code = 500, message = "Internal Server Error"),
			@ApiResponse(code = 401, message = "Unauthorized"), @ApiResponse(code = 403, message = "Forbidden")})
	public DtsApiResponse getDetailPolicyExpired(HttpServletRequest request, PolicyExpiredSearchDto searchDto)  {
		long start = System.currentTimeMillis();
		try {
			CmsCommonPagination<OfficePolicyExpiredDetailDto> common = getListOfficeDocument.getDetailPolicyExpired(searchDto);
			ObjectDataRes<OfficePolicyExpiredDetailDto> resObj = new ObjectDataRes<>(common.getTotalData(), common.getData());
			return this.successHandler.handlerSuccess(resObj, start, null, null);
		} catch (Exception ex) {
			return this.errorHandler.handlerException(ex, start, null, null);
		}
	}
	//export exrpired
	@SuppressWarnings("rawtypes")
    @PostMapping(AppApiConstant.API_EXPORT_OFFICE_POLICY_EXPIRED)
    @ApiOperation("Export list finace support standard by group")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "success"), @ApiResponse(code = 500, message = "Internal Server Error"),
            @ApiResponse(code = 401, message = "Unauthorized"), @ApiResponse(code = 403, message = "Forbidden"),
            @ApiResponse(code = 402601, message = "Error process type new") })
    public ResponseEntity exportPolicyExpired(HttpServletRequest request, @RequestBody PolicyExpiredSearchDto searchDto) {
        ResponseEntity resObj = null;
        try {
            searchDto.setPage(0);
            searchDto.setPageSize(0);
            CmsCommonPagination<OfficePolicyExpiredDetailDto> common = getListOfficeDocument.getDetailPolicyExpired(searchDto);
            int total = 0;
            String[] titleHeader=null;
            if(searchDto.getAgentGroup().equalsIgnoreCase("BM")) {
                titleHeader = new String[] {"STT", "Quản lý", "Tư vấn tài chính", "Số hợp HĐBH đã đáo hạn", "Tổng số tiền chi trả", "Số HĐBH sẽ đáo hạn", "Tổng số tiền ước tính chi trả"};
                resObj = getListOfficeDocument.exportListDataWithHeaderDto(common.getData(), "dao_han_nhom_phong", "DANH SÁCH HỢP ĐỒNG ĐÁO HẠN CẤP PHÒNG BAN", titleHeader, "A5", OfficePolicyExpiredBmEnum.class, OfficePolicyExpiredDetailDto.class, searchDto.getAgentCodeSearch(), searchDto.getAgentType(), total);
            }
            else if(searchDto.getAgentGroup().equalsIgnoreCase("UM")) {
                titleHeader = new String[] {"STT", "Tư vấn tài chính", "Số hợp HĐBH đã đáo hạn", "Tổng số tiền chi trả", "Số HĐBH sẽ đáo hạn", "Tổng số tiền ước tính chi trả"};
                resObj = getListOfficeDocument.exportListDataWithHeaderDto(common.getData(), "dao_han_nhom_phong", "DANH SÁCH HỢP ĐỒNG ĐÁO HẠN CẤP PHÒNG BAN", titleHeader, "A5", OfficePolicyExpiredUmEnum.class, OfficePolicyExpiredDetailDto.class, searchDto.getAgentCodeSearch(), searchDto.getAgentType(), total);
            }
        } catch (Exception e) {
            logger.error("##exportPolicyExpired##", e);
        }
        return resObj;
    }
	
	@GetMapping(AppApiConstant.API_LIST_OFFICE_POLICY_ORPHAN)
	@ApiOperation("Get detail policy orphan")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "success"), @ApiResponse(code = 500, message = "Internal Server Error"),
			@ApiResponse(code = 401, message = "Unauthorized"), @ApiResponse(code = 403, message = "Forbidden")})
	public DtsApiResponse getDetailPolicyOrphan(HttpServletRequest request
	        , PolicyOrphanSearchDto searchDto)  {
		long start = System.currentTimeMillis();
		try {
			CmsCommonPagination<OfficePolicyOrphanDetailDto> common = getListOfficeDocument.getDetailPolicyOrphan(searchDto);
			ObjectDataRes<OfficePolicyOrphanDetailDto> resObj = new ObjectDataRes<>(common.getTotalData(), common.getData());
			return this.successHandler.handlerSuccess(resObj, start, null, null);
		} catch (Exception ex) {
			return this.errorHandler.handlerException(ex, start, null, null);
		}
	}
	//export orphan
	@SuppressWarnings("rawtypes")
    @PostMapping(AppApiConstant.API_EXPORT_OFFICE_POLICY_ORPHAN)
    @ApiOperation("Export list finace support standard by group")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "success"), @ApiResponse(code = 500, message = "Internal Server Error"),
            @ApiResponse(code = 401, message = "Unauthorized"), @ApiResponse(code = 403, message = "Forbidden"),
            @ApiResponse(code = 402601, message = "Error process type new") })
    public ResponseEntity exportPolicyOrphan(HttpServletRequest request, @RequestBody PolicyOrphanSearchDto searchDto) {
        ResponseEntity resObj = null;
        try {
            searchDto.setPage(0);
            searchDto.setPageSize(0);
            CmsCommonPagination<OfficePolicyOrphanDetailDto> common = getListOfficeDocument.getDetailPolicyOrphan(searchDto);
            int total = 0;
            String[] titleHeader=null;
            if(searchDto.getAgentGroup().equalsIgnoreCase("BM")) {
                titleHeader = new String[] {"STT", "Số HĐBH", "Bên mua bảo hiểm", "Địa chỉ", "Số điện thoại", "Định kỳ đóng phí", "Tên TVTC phục vụ", "Ngày chấm dứt mã số", "Mã số TVTC phục vụ mới","Tên TVTC phục vụ mới"};
                resObj = getListOfficeDocument.exportListDataWithHeaderDto(common.getData(), "mo_coi_nhom_phong", "DANH SÁCH HỢP ĐỒNG MỒ CÔI CẤP PHÒNG BAN", titleHeader, "A5", OfficePolicyOrphanEnum.class, OfficePolicyOrphanDetailDto.class, searchDto.getAgentCodeSearch(), searchDto.getAgentType(), total);
            }
        } catch (Exception e) {
            logger.error("##exportPolicyExpired##", e);
        }
        return resObj;
    }
	
	//list group
	@GetMapping(AppApiConstant.API_LIST_CONTRACT_BY_GROUP)
    @ApiOperation("Get list contract by group")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "success"), @ApiResponse(code = 500, message = "Internal Server Error"),
            @ApiResponse(code = 401, message = "Unauthorized"), @ApiResponse(code = 403, message = "Forbidden")})
    public DtsApiResponse getListTotalOfficeInsurance(HttpServletRequest request, @RequestParam(value = "agentCode", required = false) String agentCode
            , TotalInsuranceContractGroupSearchDto searchDto)  {
        long start = System.currentTimeMillis();
        try {
            CmsCommonPagination<TotalInsuranceContractGroupDto> common = getListOfficeDocument.getListTotalContractByGroup(searchDto);
            ObjectDataRes<TotalInsuranceContractGroupDto> resObj = new ObjectDataRes<>(common.getTotalData(), common.getData());
            return this.successHandler.handlerSuccess(resObj, start, null, null);
        } catch (Exception ex) {
            return this.errorHandler.handlerException(ex, start, null, null);
        }
    }
	

    //export list policy
    @SuppressWarnings("rawtypes")
    @PostMapping(AppApiConstant.API_EXPORT_LIST_CONTRACT_BY_GROUP)
    @ApiOperation("Export list finace support standard by group")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "success"), @ApiResponse(code = 500, message = "Internal Server Error"),
            @ApiResponse(code = 401, message = "Unauthorized"), @ApiResponse(code = 403, message = "Forbidden"),
            @ApiResponse(code = 402601, message = "Error process type new") })
    public ResponseEntity exportListTotalOfficeInsurance(HttpServletRequest request, @RequestBody TotalInsuranceContractGroupSearchDto searchDto) {
        ResponseEntity resObj = null;
        try {
            searchDto.setPage(0);
            searchDto.setPageSize(0);
            resObj = getListOfficeDocument.exportListViewBD(searchDto);
            } catch (Exception e) {
            logger.error("##exportListTotalOfficeInsurance##", e);
        }
        return resObj;
    }
	
	//over 30 days
	@GetMapping(AppApiConstant.API_LIST_CONTRACT_OVERDUE_FEE)
    @ApiOperation("Get list contract by group")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "success"), @ApiResponse(code = 500, message = "Internal Server Error"),
            @ApiResponse(code = 401, message = "Unauthorized"), @ApiResponse(code = 403, message = "Forbidden")})
    public DtsApiResponse getListContractOverdueFeeRYP(HttpServletRequest request, ContractFeeGroupSearchDto searchDto)  {
        long start = System.currentTimeMillis();
        try {
        	if (!db2ApiService.checkChildrenInParent(UserProfileUtils.getFaceMask(), searchDto.getAgentCodeSearch())) {
        		return this.errorHandler.handlerException(new Exception("Quyền truy cập không hợp lệ"), start, null, null);
        	}
            CmsCommonPagination<ContractOverdueFeeRypDto> common = getListOfficeDocument.getListContractOverdueFeeRYP(searchDto);
            ObjectDataRes<ContractOverdueFeeRypDto> resObj = new ObjectDataRes<>(common.getTotalData(), common.getData());
            return this.successHandler.handlerSuccess(resObj, start, null, null);
        } catch (Exception ex) {
            return this.errorHandler.handlerException(ex, start, null, null);
        }
    }
	//export over 30 days
	@SuppressWarnings("rawtypes")
    @PostMapping(AppApiConstant.API_EXPORT_LIST_CONTRACT_OVERDUE_FEE)
    @ApiOperation("Export list finace support standard by group")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "success"), @ApiResponse(code = 500, message = "Internal Server Error"),
            @ApiResponse(code = 401, message = "Unauthorized"), @ApiResponse(code = 403, message = "Forbidden"),
            @ApiResponse(code = 402601, message = "Error process type new") })
    public ResponseEntity exportListContractOverdueFeeRYP(HttpServletRequest request, @RequestBody ContractFeeGroupSearchDto searchDto) {
        ResponseEntity resObj = null;
        try {
            searchDto.setPage(0);
            searchDto.setPageSize(0);
            CmsCommonPagination<ContractOverdueFeeRypDto> common = getListOfficeDocument.getListContractOverdueFeeRYP(searchDto);
            int total = common.getTotalData();
            common.getData().forEach(e -> {
                if(StringUtils.isNotBlank(e.getPolicyNo())){
                    e.setPolicyNo(formatPolicyNumber(9, e.getPolicyNo()));
                }
                if(e.getInsuranceBuyer() !=null)
                    e.setInsuranceBuyer(e.getInsuranceBuyer().toUpperCase());
            });
            String[] titleHeader=null;
            if(searchDto.getAgentGroup().equalsIgnoreCase("BM")) {
                titleHeader = new String[] {"STT", "Quản lý", "Tư vấn Tài chính", "Ngày chấm dứt mã số" ,"Số HĐBH", "Bên mua BH" ,"Địa chỉ thu phí", "ĐT nhà ", "ĐT cơ quan", "ĐT Di động", "Định kỳ đóng phí","Kỳ đóng phí", "Phí dự tính định kỳ","Phí cơ bản định kỳ","Phí các kỳ trước chưa đóng","Kỳ đóng đủ phí gần nhất","Ngày kết thúc thời gian gia hạn đóng phí"};
                resObj = getListOfficeDocument.exportListDataWithHeaderDto(common.getData(), "qua_han_thu_phi_RYP_30ngay", "DANH SÁCH HỢP ĐỒNG QUÁ HẠN THU PHÍ RYP 30 NGÀY", titleHeader, "A5", ContractOverdueFeeRypBmEnum.class, ContractOverdueFeeRypDto.class, searchDto.getAgentCodeSearch(), searchDto.getAgentGroup(), total);
            }
            else if(searchDto.getAgentGroup().equalsIgnoreCase("UM")) {
                titleHeader = new String[] {"STT","Tư vấn Tài chính", "Ngày chấm dứt mã số" ,"Số HĐBH", "Bên mua BH" ,"Địa chỉ thu phí", "ĐT nhà ", "ĐT cơ quan", "ĐT Di động", "Định kỳ đóng phí","Kỳ đóng phí", "Phí dự tính định kỳ","Phí cơ bản định kỳ","Phí các kỳ trước chưa đóng","Kỳ đóng đủ phí gần nhất","Ngày kết thúc thời gian gia hạn đóng phí"};
                resObj = getListOfficeDocument.exportListDataWithHeaderDto(common.getData(), "qua_han_thu_phi_RYP_30ngay", "DANH SÁCH HỢP ĐỒNG QUÁ HẠN THU PHÍ RYP 30 NGÀY", titleHeader, "A5", ContractOverdueFeeRypUmEnum.class, ContractOverdueFeeRypDto.class, searchDto.getAgentCodeSearch(), searchDto.getAgentGroup(), total);
            }
        } catch (Exception e) {
            logger.error("##exportListContractOverdueFeeRYP##", e);
        }
        return resObj;
    }
	private String formatPolicyNumber(int digits, String policyNumber){
		if(StringUtils.isEmpty(policyNumber)) {
			return "";
		}
		if(digits < policyNumber.length()) return policyNumber;
	    return IntStream.range(0, digits - policyNumber.length()).mapToObj(i -> "0").collect(Collectors.joining("")).concat(policyNumber);
    }
	//list contract fee sumary
    //danh sach hd den han thu phi
	@GetMapping(AppApiConstant.API_LIST_CONTRACT_FEE_BY_GROUP)
    @ApiOperation("Get list contract by group")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "success"), @ApiResponse(code = 500, message = "Internal Server Error"),
            @ApiResponse(code = 401, message = "Unauthorized"), @ApiResponse(code = 403, message = "Forbidden")})
    public DtsApiResponse getListFeeByGroup(HttpServletRequest request
            , ContractFeeGroupSearchDto searchDto)  {
        long start = System.currentTimeMillis();
        try {
        	if (!db2ApiService.checkChildrenInParent(UserProfileUtils.getFaceMask(), searchDto.getAgentCodeSearch())) {
        		return this.errorHandler.handlerException(new Exception("Quyền truy cập không hợp lệ"), start, null, null);
        	}
            CmsCommonPagination<ContractFeeGroupDto> common = getListOfficeDocument.getListFeeByGroup(searchDto);
            ObjectDataRes<ContractFeeGroupDto> resObj = new ObjectDataRes<>(common.getTotalData(), common.getData());
            return this.successHandler.handlerSuccess(resObj, start, null, null);
        } catch (Exception ex) {
            return this.errorHandler.handlerException(ex, start, null, null);
        }
    }

	public static boolean isNullOrZero(BigDecimal number) {
		boolean isBigDecimalValueNullOrZero = false;
		if (number == null)
			isBigDecimalValueNullOrZero = true;
		else if (number != null && number.compareTo(BigDecimal.ZERO) == 0)
			isBigDecimalValueNullOrZero = true;

		return isBigDecimalValueNullOrZero;
	}
	//export contract fee
	@SuppressWarnings("rawtypes")
    @PostMapping(AppApiConstant.API_EXPORT_LIST_CONTRACT_FEE_BY_GROUP)
    @ApiOperation("Export list finace support standard by group")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "success"), @ApiResponse(code = 500, message = "Internal Server Error"),
            @ApiResponse(code = 401, message = "Unauthorized"), @ApiResponse(code = 403, message = "Forbidden"),
            @ApiResponse(code = 402601, message = "Error process type new") })
    public ResponseEntity exportContractFee(HttpServletRequest request, @RequestBody ContractFeeGroupSearchDto searchDto) {
        ResponseEntity resObj = null;
        try {
            searchDto.setPage(0);
            searchDto.setPageSize(0);
            ContractDueDateSearchDto dto = new ContractDueDateSearchDto();
            dto.setAgentCode(searchDto.getAgentCodeSearch() != null ? searchDto.getAgentCodeSearch(): searchDto.getAgentCode().toString());
            dto.setType(searchDto.getAgentGroup());
            ObjectDataRes<ContractSearchDueDateResultDto> data = new ObjectDataRes<>();
            CmsCommonPagination<ContractFeeGroupDto> common = new CmsCommonPagination<>();
            int total = 0;
            if(searchDto.getAgentGroup().equalsIgnoreCase("FC")) {
                data = apiContractService.getListDueDateContractByCondition(dto);
                if(data != null && !data.getDatas().isEmpty()){
                    data.getDatas().forEach(e->e.setPolicyNo(formatPolicyNumber(9 , e.getPolicyNo())));
                    data.getDatas().forEach(x->{
                        if(x.getInsuranceBuyer() != null)
                            x.setInsuranceBuyer(x.getInsuranceBuyer().toUpperCase());
                    });
                }
                total = data.getTotalData();
            }else {
                common = getListOfficeDocument.getListFeeByGroup(searchDto);
                total = common.getTotalData();
                if(common.getData().size()>0)
                    total = common.getData().stream().filter(x ->x.getTotalContract() != null).map(ContractFeeGroupDto:: getTotalContract).reduce(BigDecimal.ZERO,(a, b) -> a.add(b)).intValue();

            }
            if(CollectionUtils.isNotEmpty(common.getData())) {
            	common.getData().forEach(x -> {
            		if (isNullOrZero(x.getFeeMustReceive()) || (!isNullOrZero(x.getFeeMustReceive()) && x.getFeeMustReceive().compareTo( new BigDecimal(0)) < 0)) {
            			x.setFeeMustReceive( new BigDecimal(0));
            		}
            	});
            }

            String[] titleHeader=null;
            if(searchDto.getAgentGroup().equalsIgnoreCase("BM")) {
                titleHeader = new String[] {"STT", "Quản lý", "Tư vấn tài chính", "Tổng số HĐBH", "Tổng phí cần thu", "Tổng phí mong đợi"};
                resObj = getListOfficeDocument.exportListDataWithHeaderDto(common.getData(), "thu_phi_cap_phong_ban", "DANH SÁCH HỢP ĐỒNG THU PHÍ CẤP PHÒNG BAN", titleHeader, "A5", ContractFeeGroupBmEnum.class, ContractFeeGroupDto.class, searchDto.getAgentCodeSearch(), searchDto.getAgentGroup(), total);
            }
            else if(searchDto.getAgentGroup().equalsIgnoreCase("UM")) {
                titleHeader = new String[] {"STT", "Tư vấn tài chính", "Tổng số HĐBH", "Tổng phí cần thu", "Tổng phí mong đợi"};
                resObj = getListOfficeDocument.exportListDataWithHeaderDto(common.getData(), "thu_phi_cap_phong_ban", "DANH SÁCH HỢP ĐỒNG THU PHÍ CẤP PHÒNG BAN", titleHeader, "A5", ContractFeeGroupUmEnum.class, ContractFeeGroupDto.class, searchDto.getAgentCodeSearch(), searchDto.getAgentGroup(), total);
            }
            else if(searchDto.getAgentGroup().equalsIgnoreCase("FC")) {
                titleHeader = new String[] {"STT", "Số hợp đồng bảo hiểm", "Bên mua bảo hiểm", "Phí phải thu", "Phí mong đợi"};
                resObj = getListOfficeDocument.exportListDataWithHeaderDto(data.getDatas(), "thu_phi_cap_phong_ban", "DANH SÁCH HỢP ĐỒNG THU PHÍ CẤP PHÒNG BAN", titleHeader, "A5", ContractFeeGroupFcEnum.class, ContractSearchDueDateResultDto.class, searchDto.getAgentCode().toString(), searchDto.getAgentGroup(), total);
            }
        } catch (Exception e) {
            logger.error("##exportPolicyExpired##", e);
        }
        return resObj;
    }
	//list contract fee detail
	@GetMapping(AppApiConstant.API_LIST_CONTRACT_FEE_BY_GROUP_DETAIL)
    @ApiOperation("Get list contract by group")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "success"), @ApiResponse(code = 500, message = "Internal Server Error"),
            @ApiResponse(code = 401, message = "Unauthorized"), @ApiResponse(code = 403, message = "Forbidden")})
    public DtsApiResponse getDetailFeeByGroup(HttpServletRequest request
            , ContractFeeGroupSearchDto searchDto)  {
        long start = System.currentTimeMillis();
        try {
            CmsCommonPagination<ContractFeeGroupDto> common = getListOfficeDocument.getListFeeByGroupDetail(searchDto);
            ObjectDataRes<ContractFeeGroupDto> resObj = new ObjectDataRes<>(common.getTotalData(), common.getData());
            return this.successHandler.handlerSuccess(resObj, start, null, null);
        } catch (Exception ex) {
            return this.errorHandler.handlerException(ex, start, null, null);
        }
    }
	
	//active - inactive
	@GetMapping(AppApiConstant.API_LIST_CONTRACT_EFFECT_BY_GROUP)
    @ApiOperation("Get list contract by group")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "success"), @ApiResponse(code = 500, message = "Internal Server Error"),
            @ApiResponse(code = 401, message = "Unauthorized"), @ApiResponse(code = 403, message = "Forbidden")})
    public DtsApiResponse getListContractEffectByGroup(HttpServletRequest request
			, ContractEffectGroupSearchDto searchDto)  {
        long start = System.currentTimeMillis();
        try {
        	if (!db2ApiService.checkChildrenInParent(UserProfileUtils.getFaceMask(), searchDto.getAgentCodeSearch())) {
        		return this.errorHandler.handlerException(new Exception("Quyền truy cập không hợp lệ"), start, null, null);
        	}
            CmsCommonPagination<ContractEffectGroupDto> common = getListOfficeDocument.getListContractEffectByGroup(searchDto, false);
            ObjectDataRes<ContractEffectGroupDto> resObj = new ObjectDataRes<>(common.getTotalData(), common.getData());
            return this.successHandler.handlerSuccess(resObj, start, null, null);
        } catch (Exception ex) {
            return this.errorHandler.handlerException(ex, start, null, null);
        }
    }
	
	//export active - inactive
	@SuppressWarnings("rawtypes")
    @PostMapping(AppApiConstant.API_EXPORT_LIST_CONTRACT_EFFECT_BY_GROUP)
    @ApiOperation("Export list finace support standard by group")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "success"), @ApiResponse(code = 500, message = "Internal Server Error"),
            @ApiResponse(code = 401, message = "Unauthorized"), @ApiResponse(code = 403, message = "Forbidden"),
            @ApiResponse(code = 402601, message = "Error process type new") })
    public ResponseEntity exportListContractEffectByGroup(HttpServletRequest request, @RequestBody ContractEffectGroupSearchDto searchDto) {
        ResponseEntity resObj = null;
        try {
            searchDto.setPage(0);
            searchDto.setPageSize(0);
            CmsCommonPagination<ContractEffectGroupDto> common = getListOfficeDocument.getListContractEffectByGroup(searchDto, true);
            int total = 0;
            total = common.getData().stream().map(ContractEffectGroupDto::getTotalContract).reduce(0, (a, b) -> a + b);
            String[] titleHeader=null;
            if(searchDto.getAgentGroup().equalsIgnoreCase("BM")) {
                if(searchDto.getPolicyType().equalsIgnoreCase("ACTIVE")) {
                    titleHeader = new String[] {"STT", "Quản lý", "Tư vấn tài chính", "Tổng số hợp đồng"};
                    resObj = getListOfficeDocument.exportListDataWithHeaderDto(common.getData(), "con_hieu_luc_cap_phong_ban", "DANH SÁCH HỢP ĐỒNG CÒN HIỆU LỰC CẤP PHÒNG BAN", titleHeader, "A5", ContractEffectGroupBmEnum.class, ContractEffectGroupDto.class, searchDto.getAgentCodeSearch(), searchDto.getAgentGroup(), total);
                }
                else if(searchDto.getPolicyType().equalsIgnoreCase("INACTIVE")){
                    titleHeader = new String[] {"STT", "Quản lý", "Tư vấn tài chính", "Tổng số hợp đồng"};
                    resObj = getListOfficeDocument.exportListDataWithHeaderDto(common.getData(), "mat_hieu_luc_cap_phong_ban", "DANH SÁCH HỢP ĐỒNG MẤT HIỆU LỰC CẤP PHÒNG BAN (TRONG 2 NĂM)", titleHeader, "A5", ContractEffectGroupBmEnum.class, ContractEffectGroupDto.class, searchDto.getAgentCodeSearch(), searchDto.getAgentGroup(), total);
                }
            }
            else if(searchDto.getAgentGroup().equalsIgnoreCase("UM")) {
                if(searchDto.getPolicyType().equalsIgnoreCase("ACTIVE")) {
                    titleHeader = new String[] {"STT","Tư vấn tài chính", "Tổng số hợp đồng"};
                    resObj = getListOfficeDocument.exportListDataWithHeaderDto(common.getData(), "con_hieu_luc_cap_phong_ban", "DANH SÁCH HỢP ĐỒNG CÒN HIỆU LỰC CẤP PHÒNG BAN", titleHeader, "A5", ContractEffectGroupUmEnum.class, ContractEffectGroupDto.class, searchDto.getAgentCodeSearch(), searchDto.getAgentGroup(), total);
                }
                else if(searchDto.getPolicyType().equalsIgnoreCase("INACTIVE")){
                    titleHeader = new String[] {"STT", "Tư vấn tài chính", "Tổng số hợp đồng"};
                    resObj = getListOfficeDocument.exportListDataWithHeaderDto(common.getData(), "mat_hieu_luc_cap_phong_ban", "DANH SÁCH HỢP ĐỒNG MẤT HIỆU LỰC CẤP PHÒNG BAN (TRONG 2 NĂM)", titleHeader, "A5", ContractEffectGroupUmEnum.class, ContractEffectGroupDto.class, searchDto.getAgentCodeSearch(), searchDto.getAgentGroup(), total);
                }
            }
        } catch (Exception e) {
            logger.error("##exportListContractEffectByGroup##", e);
        }
        return resObj;
    }

    //list business
    @GetMapping(AppApiConstant.API_LIST_CONTRACT_BUSINESS_HANDLE)
    @ApiOperation("Get list contract by group")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "success"), @ApiResponse(code = 500, message = "Internal Server Error"),
            @ApiResponse(code = 401, message = "Unauthorized"), @ApiResponse(code = 403, message = "Forbidden")})
    public DtsApiResponse getListBusinessHandle(HttpServletRequest request
    , ContractBusinessGroupSearchDto contractBusinessGroupSearchDto)  {
        long start = System.currentTimeMillis();
        try {
            CmsCommonPagination<ContractBusinessHandleDto> common = getListOfficeDocument.getListBusinessHandle(contractBusinessGroupSearchDto);
            ObjectDataRes<ContractBusinessHandleDto> resObj = new ObjectDataRes<>(common.getTotalData(), common.getData());
            return this.successHandler.handlerSuccess(resObj, start, null, null);
        } catch (Exception ex) {
            return this.errorHandler.handlerException(ex, start, null, null);
        }
    }
    
    //export business
    @SuppressWarnings("rawtypes")
    @PostMapping(AppApiConstant.API_EXPROT_CONTRACT_BUSINESS_HANDLE)
    @ApiOperation("Export list finace support standard by group")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "success"), @ApiResponse(code = 500, message = "Internal Server Error"),
            @ApiResponse(code = 401, message = "Unauthorized"), @ApiResponse(code = 403, message = "Forbidden"),
            @ApiResponse(code = 402601, message = "Error process type new") })
    public ResponseEntity exportListContractBusinessByGroup(HttpServletRequest request, @RequestBody ContractBusinessGroupSearchDto searchDto) {
        ResponseEntity resObj = null;
        try {
            searchDto.setPage(0);
            searchDto.setPageSize(0);
            searchDto.setPolicyType("HANDING");
            CmsCommonPagination<ContractBusinessHandleDto> common = getListOfficeDocument.getListBusinessHandle(searchDto);
            common.getData().forEach(e -> {
                if(StringUtils.isNotBlank(e.getPolicyNo()))
                    e.setPolicyNo(formatPolicyNumber(9, e.getPolicyNo()));

                if(e.getInsuranceBuyer() !=null)
                    e.setInsuranceBuyer(e.getInsuranceBuyer().toUpperCase());
            });

            int total =0;
            //count type
            if(ObjectUtils.isNotEmpty(common.getData())) {
                List<ContractBusinessHandleDto> distinctCalim = common.getData().stream().filter(StreamUtils.distinctByKey(ContractBusinessHandleDto::getPolicyNo)).collect(Collectors.toList());
                total = distinctCalim.size();
            }

            String[] titleHeader=null;
            if(searchDto.getAgentGroup().equalsIgnoreCase("BM")) {
                    titleHeader = new String[] {"STT", "Quản lý", "Tư vấn tài chính", "Số HĐBH", "Bên mua BH", "Loại yêu cầu", "Tình trạng yêu cầu", "Thông tin chờ bổ sung", "Ngày tiếp nhận", "Ngày thực hiện","Ngày hết hạn bổ sung thông tin"};
                    resObj = getListOfficeDocument.exportListDataWithHeaderDto(common.getData(), "dieu_chinh_nghiep_vu_cap_phong_ban", "DANH SÁCH HỢP ĐỒNG ĐIỀU CHỈNH NGHIỆP VỤ CẤP PHÒNG BAN", titleHeader, "A8", ContractBusinessGroupBmEnum.class, ContractBusinessHandleDto.class, searchDto.getAgentCodeSearch(), searchDto.getAgentGroup(), total);
             }
            else if(searchDto.getAgentGroup().equalsIgnoreCase("UM")) {
                    titleHeader = new String[] {"STT","Tư vấn tài chính", "Số HĐBH", "Bên mua BH", "Loại yêu cầu", "Tình trạng yêu cầu", "Thông tin chờ bổ sung", "Ngày tiếp nhận", "Ngày thực hiện","Ngày hết hạn bổ sung thông tin"};
                    resObj = getListOfficeDocument.exportListDataWithHeaderDto(common.getData(), "dieu_chinh_nghiep_vu_cap_phong_ban", "DANH SÁCH HỢP ĐỒNG ĐIỀU CHỈNH NGHIỆP VỤ CẤP PHÒNG BAN", titleHeader, "A8", ContractBusinessGroupUmEnum.class, ContractBusinessHandleDto.class, searchDto.getAgentCodeSearch(), searchDto.getAgentGroup(), total);
                }
        } catch (Exception e) {
            logger.error("##exportListContractBusinessByGroup##", e);
        }
        return resObj;
    }
    
    //list claim
    @GetMapping(AppApiConstant.API_LIST_OFFICE_POLICY_CLAIM)
    @ApiOperation("Get detail policy claims")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "success"), @ApiResponse(code = 500, message = "Internal Server Error"),
            @ApiResponse(code = 401, message = "Unauthorized"), @ApiResponse(code = 403, message = "Forbidden")})
    public DtsApiResponse getDetailPolicyClaim(HttpServletRequest request
            , ContractClaimGroupSearchDto searchDto)  {
        long start = System.currentTimeMillis();
        try {
            CmsCommonPagination<OfficePolicyClaimDetailDto> common = getListOfficeDocument.getDetailPolicyClaim(searchDto);
            ObjectDataRes<OfficePolicyClaimDetailDto> resObj = new ObjectDataRes<>(common.getTotalData(), common.getData());
            return this.successHandler.handlerSuccess(resObj, start, null, null);
        } catch (Exception ex) {
            return this.errorHandler.handlerException(ex, start, null, null);
        }
    }
    
    //export claim
    @SuppressWarnings("rawtypes")
    @PostMapping(AppApiConstant.API_EXPORT_OFFICE_POLICY_CLAIM)
    @ApiOperation("Export list finace support standard by group")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "success"), @ApiResponse(code = 500, message = "Internal Server Error"),
            @ApiResponse(code = 401, message = "Unauthorized"), @ApiResponse(code = 403, message = "Forbidden"),
            @ApiResponse(code = 402601, message = "Error process type new") })
    public ResponseEntity exportListContractClaimByGroup(HttpServletRequest request, @RequestBody ContractEffectGroupSearchDto searchDto) {
        ResponseEntity resObj = null;
        try {
            searchDto.setPage(0);
            searchDto.setPageSize(0);
            CmsCommonPagination<ContractEffectGroupDto> common = getListOfficeDocument.getListContractEffectByGroup(searchDto, true);
            int total = 0;
            total = common.getData().stream().map(ContractEffectGroupDto::getTotalContract).reduce(0, (a, b) -> a + b);
            String[] titleHeader=null;
            if(searchDto.getAgentGroup().equalsIgnoreCase("BM")) {
                    titleHeader = new String[] {"STT", "Quản lý", "Tư vấn tài chính", "Tổng số hợp đồng"};
                    resObj = getListOfficeDocument.exportListDataWithHeaderDto(common.getData(), "yeu_cau_boi_thuong_nhom_phong", "DANH SÁCH HỢP ĐỒNG YÊU CẦU BỒI THƯỜNG CẤP PHÒNG BAN", titleHeader, "A5", ContractClaimGroupBmEnum.class, ContractEffectGroupDto.class, searchDto.getAgentCodeSearch(), searchDto.getAgentGroup(), total);
             }
            else if(searchDto.getAgentGroup().equalsIgnoreCase("UM")) {
                    titleHeader = new String[] {"STT","Tư vấn tài chính", "Tổng số hợp đồng"};
                    resObj = getListOfficeDocument.exportListDataWithHeaderDto(common.getData(), "yeu_cau_boi_thuong_nhom_phong", "DANH SÁCH HỢP ĐỒNG YÊU CẦU BỒI THƯỜNG CẤP PHÒNG BAN", titleHeader, "A5", ContractClaimGroupUmEnum.class, ContractEffectGroupDto.class, searchDto.getAgentCodeSearch(), searchDto.getAgentGroup(), total);
                }
        } catch (Exception e) {
            logger.error("##exportListContractBusinessByGroup##", e);
        }
        return resObj;
    }
    
    //export standard
    @SuppressWarnings("rawtypes")
    @PostMapping(AppApiConstant.API_EXPORT_LIST_AGENT_STANDARD)
    @ApiOperation("Export list finace support standard by group")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "success"), @ApiResponse(code = 500, message = "Internal Server Error"),
            @ApiResponse(code = 401, message = "Unauthorized"), @ApiResponse(code = 403, message = "Forbidden"),
            @ApiResponse(code = 402601, message = "Error process type new") })
    public ResponseEntity exportListAgentStandard(HttpServletRequest request
            , String agentCodeSearch, String agentGroup, String orgCode) {
        ResponseEntity resObj = null;
        try {
            List<FinanceSupportStandardDto> datas = getListOfficeDocument.getListAgentStandard(agentCodeSearch, agentGroup, orgCode);
            resObj = getListOfficeDocument.exportListData(datas, "List_agent_standard.xlsx", "A5", FinanceSupportStandardEnum.class, FinanceSupportStandardDto.class);
        } catch (Exception e) {
            logger.error("##exportListAgentStandard##", e);
        }
        return resObj;
    }
    @SuppressWarnings("rawtypes")
    @GetMapping("/get-total-policy")
    @ApiOperation("Export list finace support standard by group")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "success"), @ApiResponse(code = 500, message = "Internal Server Error"),
            @ApiResponse(code = 401, message = "Unauthorized"), @ApiResponse(code = 403, message = "Forbidden"),
            @ApiResponse(code = 402601, message = "Error process type new") })
    public DtsApiResponse getTotalPolicy(HttpServletRequest request, String agentCode, String agentGroup, String orgId) {
        DtsApiResponse resObj = null;
        long start = System.currentTimeMillis();
        try {

	        String agentParent = UserProfileUtils.getFaceMask();
	        if (StringUtils.isEmpty(agentCode) || agentCode == "undefined") {
	        	agentCode = agentParent;
	        }
	        if (StringUtils.isNotEmpty(agentParent) && !agentParent.equalsIgnoreCase(agentCode)) {
	            boolean isChild = apiAgentDetailService.checkAgentChild(agentParent, agentCode);
	            if (!isChild) {
	                throw new DetailException(AppApiExceptionCodeConstant.E4027100_APPAPI_LINK_EXISTS_ERROR);
	            }
	        }
            TotalPolicyDto datas = getListOfficeDocument.getTotalPolicy(agentCode, agentGroup, orgId);
            return this.successHandler.handlerSuccess(datas, start, null, null);
        } catch (Exception e) {
            logger.error("##exportListAgentStandard##", e);
        }
        return null;
    }
    
    //list matured
    @GetMapping(AppApiConstant.API_LIST_POLICY_MATURED_BY_GROUP)
    @ApiOperation("Get list policy matured by group")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "success"), @ApiResponse(code = 500, message = "Internal Server Error"),
            @ApiResponse(code = 401, message = "Unauthorized"), @ApiResponse(code = 403, message = "Forbidden")})
    public DtsApiResponse getListPolicyMaturedByGroup(HttpServletRequest request
            , PolicyMaturedSearchDto searchDto)  {
        long start = System.currentTimeMillis();
        try {
        	if (!db2ApiService.checkChildrenInParent(UserProfileUtils.getFaceMask(), searchDto.getAgentCodeSearch())) {
        		return this.errorHandler.handlerException(new Exception("Quyền truy cập không hợp lệ"), start, null, null);
        	}
            CmsCommonPagination<PolicyMaturedDetailDto> common = getListOfficeDocument.getDetailPolicyMaturedByGroup(searchDto, false);
            ObjectDataRes<PolicyMaturedDetailDto> resObj = new ObjectDataRes<>(common.getTotalData(), common.getData());
            return this.successHandler.handlerSuccess(resObj, start, null, null);
        } catch (Exception ex) {
            return this.errorHandler.handlerException(ex, start, null, null);
        }
    }
    
    //export matured
    @SuppressWarnings("rawtypes")
    @PostMapping(AppApiConstant.API_EXPORT_LIST_POLICY_MATURED_BY_GROUP)
    @ApiOperation("Export list policy matured by group")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "success"), @ApiResponse(code = 500, message = "Internal Server Error"),
            @ApiResponse(code = 401, message = "Unauthorized"), @ApiResponse(code = 403, message = "Forbidden"),
            @ApiResponse(code = 402601, message = "Error process type new") })
    public ResponseEntity exportListPolicyMaturedByGroup(HttpServletRequest request, @RequestBody PolicyMaturedSearchDto searchDto) {
        ResponseEntity resObj = null;
        try {
            searchDto.setPage(0);
            searchDto.setPageSize(0);
            CmsCommonPagination<PolicyMaturedDetailDto> common = getListOfficeDocument.getDetailPolicyMaturedByGroup(searchDto, true);
            int total = 0;
            total = common.getData().stream().map(PolicyMaturedDetailDto::getTotalPolicyMatured).reduce(0, (a, b) -> a + b);
            String[] titleHeader=null;
            if (searchDto.getAgentGroup().equalsIgnoreCase("BM") || searchDto.getAgentGroup().equalsIgnoreCase("SBM")) {
            	titleHeader = new String[] {"STT", "Quản lý", "Tư vấn tài chính", "Tổng số HĐ đáo hạn", "Tổng số tiền ước tính của quyền lợi đáo hạn", "Khách hàng có nhu cầu mua mới"};
                resObj = getListOfficeDocument.exportListDataWithHeaderDto(common.getData(),
                		"dao_han_cap_phong_ban", "DANH SÁCH HỢP ĐỒNG ĐÁO HẠN CẤP PHÒNG BAN", titleHeader, "A5",
                		PolicyMaturedGroupBmEnum.class, PolicyMaturedDetailDto.class, searchDto.getAgentCodeSearch(), searchDto.getAgentGroup(), total);
            } else if ( searchDto.getAgentGroup().equalsIgnoreCase("UM")
            		|| searchDto.getAgentGroup().equalsIgnoreCase("SUM")
            		|| searchDto.getAgentGroup().equalsIgnoreCase("PUM")
            		|| searchDto.getAgentGroup().equalsIgnoreCase("DFAM")
            		|| searchDto.getAgentGroup().equalsIgnoreCase("DFASM")
            		|| searchDto.getAgentGroup().equalsIgnoreCase("DFARM") ) {
                titleHeader = new String[] {"STT","Tư vấn tài chính", "Tổng số HĐ đáo hạn", "Tổng số tiền ước tính của quyền lợi đáo hạn", "Khách hàng có nhu cầu mua mới"};
                resObj = getListOfficeDocument.exportListDataWithHeaderDto(common.getData(),
                		"dao_han_cap_phong_ban", "DANH SÁCH HỢP ĐỒNG ĐÁO HẠN CẤP PHÒNG BAN", titleHeader, "A5",
                		PolicyMaturedGroupUmEnum.class, PolicyMaturedDetailDto.class, searchDto.getAgentCodeSearch(), searchDto.getAgentGroup(), total);
            }
        } catch (Exception e) {
            logger.error("##exportListPolicyMaturedByGroup##", e);
        }
        return resObj;
    }
    
    @SuppressWarnings("rawtypes")
    @GetMapping("/get-total-claim")
    @ApiOperation("Export list finace support standard by group")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "success"), @ApiResponse(code = 500, message = "Internal Server Error"),
            @ApiResponse(code = 401, message = "Unauthorized"), @ApiResponse(code = 403, message = "Forbidden"),
            @ApiResponse(code = 402601, message = "Error process type new") })
    public DtsApiResponse getTotalClaim(HttpServletRequest request, String agentCode, String agentGroup, String orgId) {
        long start = System.currentTimeMillis();
        try {

	        String agentParent = UserProfileUtils.getFaceMask();
	        if (StringUtils.isEmpty(agentCode) || agentCode == "undefined") {
	        	agentCode = agentParent;
	        }
	        if (StringUtils.isNotEmpty(agentParent) && !agentParent.equalsIgnoreCase(agentCode)) {
	            boolean isChild = apiAgentDetailService.checkAgentChild(agentParent, agentCode);
	            if (!isChild) {
	                throw new DetailException(AppApiExceptionCodeConstant.E4027100_APPAPI_LINK_EXISTS_ERROR);
	            }
	        }
            TotalClaimDto datas = getListOfficeDocument.getTotalClaim(agentCode, agentGroup, orgId);
            return this.successHandler.handlerSuccess(datas, start, null, null);
        } catch (Exception e) {
            logger.error("##exportListAgentStandard##", e);
        }
        return null;
    }
    
    @SuppressWarnings("rawtypes")
    @GetMapping("/get-list-claim-by-group")
    @ApiOperation("Export list finace support standard by group")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "success"), @ApiResponse(code = 500, message = "Internal Server Error"),
            @ApiResponse(code = 401, message = "Unauthorized"), @ApiResponse(code = 403, message = "Forbidden"),
            @ApiResponse(code = 402601, message = "Error process type new") })
    public DtsApiResponse getListClaimByGroup(HttpServletRequest request, ContractClaimGroupSearchDto searchDto) {
        long start = System.currentTimeMillis();
        try {
        	CmsCommonPagination<ClaimGroupDto> common = getListOfficeDocument.getListClaimByGroup(searchDto);
            ObjectDataRes<ClaimGroupDto> resObj = new ObjectDataRes<>(common.getTotalData(), common.getData());
            return this.successHandler.handlerSuccess(resObj, start, null, null);
        } catch (Exception e) {
            logger.error("##getListClaimByGroup##", e);
        }
        return null;
    }
    
    @SuppressWarnings("rawtypes")
    @PostMapping(AppApiConstant.API_EXPORT_LIST_CLAIM_BY_GROUP)
    @ApiOperation("Export list finace support standard by group")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "success"), @ApiResponse(code = 500, message = "Internal Server Error"),
            @ApiResponse(code = 401, message = "Unauthorized"), @ApiResponse(code = 403, message = "Forbidden"),
            @ApiResponse(code = 402601, message = "Error process type new") })
    public ResponseEntity exportListClaimByGroup(HttpServletRequest request, @RequestBody ContractClaimGroupSearchDto searchDto) {
        ResponseEntity resObj = null;
        try {
            searchDto.setPage(0);
            searchDto.setPageSize(0);
            CmsCommonPagination<ClaimGroupDto> common = getListOfficeDocument.getListClaimByGroup(searchDto);
            int total = (int) common.getData().stream().filter(StreamUtils.distinctByKey(ClaimGroupDto::getPolicyNo)).count();
            String[] titleHeader=null;
            String fileName;
            if ("1".equals(searchDto.getPolicyType())) {
            	fileName = "Ho_so_YCBT_%s_dang_xu_ly.xlsx";
            } else if ("2".equals(searchDto.getPolicyType())) {
            	fileName = "Ho_so_YCBT_%s_cho_bo_sung_thong_tin.xlsx";
            } else if ("3".equals(searchDto.getPolicyType())) {
            	fileName = "Ho_so_YCBT_%s_da_duoc_dong_y_chi_tra.xlsx";
            } else if ("4".equals(searchDto.getPolicyType())) {
            	fileName = "Ho_so_YCBT_%s_bi_tu_choi.xlsx";
            } else {
            	fileName = "Ho_so_YCBT_%s_khong_hop_le.xlsx";
            }
            if(searchDto.getAgentGroup().equalsIgnoreCase("BM")) {
            	titleHeader = new String[] {"STT", "Quản lý", "Tư vấn tài chính", "Số HĐBH", "Bên mua bảo hiểm", "Người được bảo hiểm", "Số hồ sơ yêu cầu bồi thường", "Ngày mở mở hồ sơ", "Loại yêu cầu bồi thường", "Tình trạng", "Thông tin chờ bổ sung", "Ngày hết hạn bổ sung", "Ngày có kết quả bồi thường", "Số tiền bồi thường", "Bổ sung quyền lợi nhận thanh toán", "Lý do từ chối"};
            	resObj = getListOfficeDocument.exportListDataWithHeaderDto(common.getData(), String.format(fileName, "Branch"), "HỒ SƠ YÊU CẦU BỒI THƯỜNG CẤP PHÒNG BAN", titleHeader, "A5", ContractClaimGroupBmEnum.class, ClaimGroupDto.class, searchDto.getAgentCodeSearch(), searchDto.getAgentGroup(), total);
            }
            else if(searchDto.getAgentGroup().equalsIgnoreCase("UM")) {
                titleHeader = new String[] {"STT","Tư vấn tài chính", "Số HĐBH", "Bên mua bảo hiểm", "Người được bảo hiểm", "Số hồ sơ yêu cầu bồi thường", "Ngày mở mở hồ sơ", "Loại yêu cầu bồi thường", "Tình trạng", "Thông tin chờ bổ sung", "Ngày hết hạn bổ sung", "Ngày có kết quả bồi thường", "Số tiền bồi thường", "Bổ sung quyền lợi nhận thanh toán", "Lý do từ chối"};
                resObj = getListOfficeDocument.exportListDataWithHeaderDto(common.getData(), String.format(fileName, "Unit"), "HỒ SƠ YÊU CẦU BỒI THƯỜNG CẤP PHÒNG BAN", titleHeader, "A5", ContractClaimGroupUmEnum.class, ClaimGroupDto.class, searchDto.getAgentCodeSearch(), searchDto.getAgentGroup(), total);
            }
        } catch (Exception e) {
            logger.error("##exportListContractBusinessByGroup##", e);
        }
        return resObj;
    }
    
    @GetMapping("/get-list-total-claim-by-group")
  	@ApiOperation("Get list claim by group")
  	@ApiResponses(value = { @ApiResponse(code = 200, message = "success"), @ApiResponse(code = 500, message = "Internal Server Error"),
  	@ApiResponse(code = 401, message = "Unauthorized"), @ApiResponse(code = 403, message = "Forbidden")})
    public DtsApiResponse getListTotalClaimByGroup(HttpServletRequest request, @RequestParam(value = "agentCode", required = false) String agentCode
          , TotalInsuranceContractGroupSearchDto searchDto)  {
  		long start = System.currentTimeMillis();
  		try {
  			CmsCommonPagination<TotalClaimGroupDto> common = getListOfficeDocument.getListClaimByGroupBD(searchDto);
  			ObjectDataRes<TotalClaimGroupDto> resObj = new ObjectDataRes<>(common.getTotalData(), common.getData());
  			return this.successHandler.handlerSuccess(resObj, start, null, null);
  		} catch (Exception ex) {
  			return this.errorHandler.handlerException(ex, start, null, null);
  		}
  	}
    
    @SuppressWarnings("rawtypes")
    @PostMapping(AppApiConstant.API_EXPORT_LIST_CLAIM_BY_GROUP_BD)
    @ApiOperation("Export list finace support standard by group")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "success"), @ApiResponse(code = 500, message = "Internal Server Error"),
            @ApiResponse(code = 401, message = "Unauthorized"), @ApiResponse(code = 403, message = "Forbidden"),
            @ApiResponse(code = 402601, message = "Error process type new") })
    public ResponseEntity exportListClaimByGroupBd(HttpServletRequest request, @RequestBody TotalInsuranceContractGroupSearchDto searchDto) {
        ResponseEntity resObj = null;
        try {
            searchDto.setPage(0);
            searchDto.setPageSize(0);
            resObj = getListOfficeDocument.exportListClaimViewBD(searchDto);
            } catch (Exception e) {
            logger.error("##exportListTotalOfficeInsurance##", e);
        }
        return resObj;
    }
}
