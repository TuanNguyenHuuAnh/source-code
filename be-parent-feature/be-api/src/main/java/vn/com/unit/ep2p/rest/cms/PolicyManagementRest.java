package vn.com.unit.ep2p.rest.cms;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import javax.servlet.http.HttpServletRequest;

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
import vn.com.unit.cms.core.module.contract.dto.ClaimAdditionalInformationDto;
import vn.com.unit.cms.core.module.contract.dto.ClaimOdsDetailDto;
import vn.com.unit.cms.core.module.contract.dto.ContactHistoryDetailDto;
import vn.com.unit.cms.core.module.contract.dto.ContractBusinessSearchDto;
import vn.com.unit.cms.core.module.contract.dto.ContractBusinessSearchResultDto;
import vn.com.unit.cms.core.module.contract.dto.ContractClaimResultDto;
import vn.com.unit.cms.core.module.contract.dto.ContractClaimSearchDto;
import vn.com.unit.cms.core.module.contract.dto.ContractClaimSearchResultDto;
import vn.com.unit.cms.core.module.contract.dto.ContractDueDateSearchDto;
import vn.com.unit.cms.core.module.contract.dto.ContractExpiresSearchDto;
import vn.com.unit.cms.core.module.contract.dto.ContractExpiresSearchResultDto;
import vn.com.unit.cms.core.module.contract.dto.ContractSearchAllResultDto;
import vn.com.unit.cms.core.module.contract.dto.ContractSearchDto;
import vn.com.unit.cms.core.module.contract.dto.ContractSearchDueDateResultDto;
import vn.com.unit.cms.core.module.contract.dto.CostOfRefusalToPayDto;
import vn.com.unit.cms.core.module.contract.dto.PolicyMaturedResultDto;
import vn.com.unit.cms.core.module.contract.dto.PolicyMaturedSearchDto;
import vn.com.unit.cms.core.module.contract.dto.ContractBusinessHistoryResponseDto;
import vn.com.unit.core.res.ObjectDataRes;
import vn.com.unit.core.security.UserProfileUtils;
import vn.com.unit.dts.web.rest.common.DtsApiResponse;
import vn.com.unit.ep2p.admin.constant.ConstantCore;
import vn.com.unit.ep2p.admin.dto.AgentInfoDb2;
import vn.com.unit.ep2p.admin.service.Db2ApiService;
import vn.com.unit.ep2p.adp.service.AdportalService;
import vn.com.unit.ep2p.core.constant.AppApiConstant;
import vn.com.unit.ep2p.enumdef.PersonalActivePolicyEnum;
import vn.com.unit.ep2p.enumdef.PersonalBusinessPolicyEnum;
import vn.com.unit.ep2p.enumdef.PersonalClaimPolicyEnum;
import vn.com.unit.ep2p.enumdef.PersonalDueDatePolicyEnum;
import vn.com.unit.ep2p.enumdef.PersonalExpiredPolicyEnum;
import vn.com.unit.ep2p.enumdef.PersonalInactivePolicyEnum;
import vn.com.unit.ep2p.rest.AbstractRest;
import vn.com.unit.ep2p.service.ApiContractService;
import vn.com.unit.ep2p.utils.LangugeUtil;
import vn.com.unit.ep2p.utils.StreamUtils;

/**
 * @Last updated: 22/03/2024    nt.tinh SR16136 - Fix lỗi phát hiện trong quá trình Pentest - 2023
 */
@RestController
@RequestMapping(CmsApiConstant.BASE_API_URL + CmsApiConstant.API_ODS_MANAGEMENT)
@Api(tags = { "Contract" })
public class PolicyManagementRest extends AbstractRest {
	
	@Autowired
	ApiContractService apiContractService;

    @Autowired
    Db2ApiService db2ApiService;

    @Autowired
    AdportalService adpService;
    
	private Logger logger = LoggerFactory.getLogger(getClass());
	 
	@GetMapping(AppApiConstant.API_LIST_ALL_CONTRACT)// get tat ca hop dong
    @ApiOperation("Api provides constant on systems")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "success"), @ApiResponse(code = 500, message = "Internal Server Error"),
            @ApiResponse(code = 401, message = "Unauthorized"), @ApiResponse(code = 403, message = "Forbidden")})
	
    public DtsApiResponse getListAllContractByCondition(HttpServletRequest request, @RequestParam(defaultValue = "0") Integer modeView,
    		@RequestParam(value = ConstantCore.PAGE_SIZE, defaultValue = "16") Optional<Integer> pageSizeParam,
            @RequestParam(value = ConstantCore.PAGE, defaultValue = "1") Optional<Integer> pageParam
            , ContractSearchDto searchDto)  {
        long start = System.currentTimeMillis();
        try {      	
            searchDto.setPage(pageParam.get());
        	searchDto.setPageSize(pageSizeParam.get());
        	ObjectDataRes<ContractSearchAllResultDto> resObj = apiContractService.getListAllContractByCondition(searchDto, false);
            resObj.getDatas().forEach(e->{
                if(e.getPolAgtShrPct() != null){
                    e.setPolAgtShrPct(e.getPolAgtShrPct().setScale(0, RoundingMode.HALF_UP));
                } else {
                    e.setPolAgtShrPct(new BigDecimal(0));
                }
            });
        	return this.successHandler.handlerSuccess(resObj, start, null, null);
        } catch (Exception ex) {
            return this.errorHandler.handlerException(ex, start, null, null);
        }
    }
    @GetMapping("/get-detail-contract")
    @ApiOperation("API get detail of policy")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "success"), @ApiResponse(code = 500, message = "Internal Server Error"),
            @ApiResponse(code = 401, message = "Unauthorized"), @ApiResponse(code = 403, message = "Forbidden")})

    public DtsApiResponse getDetailContract(HttpServletRequest request,
            @RequestParam(value = ConstantCore.PAGE_SIZE, defaultValue = "16") Optional<Integer> pageSizeParam,
            @RequestParam(value = ConstantCore.PAGE, defaultValue = "0") Optional<Integer> pageParam
            , ContractSearchDto searchDto)  {
        long start = System.currentTimeMillis();
        try {
            Locale locale = LangugeUtil.getLanguageFromHeader(request);
            searchDto.setPage(pageParam.get());
            searchDto.setPageSize(pageSizeParam.get());
            if (StringUtils.isEmpty(searchDto.getAgentCode())) {
            	searchDto.setAgentCode(UserProfileUtils.getFaceMask());
            }
            ContractSearchAllResultDto resObj = new ContractSearchAllResultDto();
            resObj = apiContractService.getDetailContractByCondition(searchDto);
            
            if(ObjectUtils.isNotEmpty(resObj) && resObj.getInsuranceContract()!=null && !Integer.valueOf(resObj.getInsuranceContract()).equals(Integer.valueOf(searchDto.getPolicyNo()))) {
            	resObj  = new ContractSearchAllResultDto();
            	resObj.setCheck(false);
        	}
            
            return this.successHandler.handlerSuccess(resObj, start, null, null);
        } catch (Exception ex) {
            return this.errorHandler.handlerException(ex, start, null, null);
        }
    }
    
    @GetMapping("/get-business-history")// get chi tiet hop dong claim
    @ApiOperation("Api get detail policy claim contract")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "success"),
            @ApiResponse(code = 500, message = "Internal Server Error"),
            @ApiResponse(code = 401, message = "Unauthorized"),
            @ApiResponse(code = 403, message = "Forbidden")})

    public DtsApiResponse getBusinessHistory(HttpServletRequest request, String policyNo)  {
        long start = System.currentTimeMillis();
        try {
            ContractBusinessHistoryResponseDto resObj = apiContractService.getBusinessHistory(policyNo);

            return this.successHandler.handlerSuccess(resObj, start, null, null);
        } catch (Exception ex) {
            return this.errorHandler.handlerException(ex, start, null, null);
        }
    }
	@GetMapping(AppApiConstant.API_LIST_DUE_DATE_CONTRACT)// get hop dong den han thu phi
    @ApiOperation("Api provides constant on systems")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "success"), @ApiResponse(code = 500, message = "Internal Server Error"),
            @ApiResponse(code = 401, message = "Unauthorized"), @ApiResponse(code = 403, message = "Forbidden")})
	
    public DtsApiResponse getListDueDateContractByCondition(HttpServletRequest request, @RequestParam(defaultValue = "0") Integer modeView,
    		@RequestParam(value = ConstantCore.PAGE_SIZE, defaultValue = "16") Optional<Integer> pageSizeParam,
            @RequestParam(value = ConstantCore.PAGE, defaultValue = "0") Optional<Integer> pageParam
            , ContractDueDateSearchDto searchDto)  {
        long start = System.currentTimeMillis();
        try {
            searchDto.setPage(pageParam.get());
        	searchDto.setPageSize(pageSizeParam.get());
        	ObjectDataRes<ContractSearchDueDateResultDto> resObj = apiContractService.getListDueDateContractByCondition(searchDto);
            return this.successHandler.handlerSuccess(resObj, start, null, null);
        } catch (Exception ex) {
            return this.errorHandler.handlerException(ex, start, null, null);
        }
    }
    @GetMapping("/get-list-due-date-fc")// get hop dong den han thu phi
    @ApiOperation("Api provides constant on systems")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "success"), @ApiResponse(code = 500, message = "Internal Server Error"),
            @ApiResponse(code = 401, message = "Unauthorized"), @ApiResponse(code = 403, message = "Forbidden")})

    public DtsApiResponse getListDueDateFc(HttpServletRequest request, @RequestParam(defaultValue = "0") Integer modeView,
                                                            @RequestParam(value = ConstantCore.PAGE_SIZE, defaultValue = "16") Optional<Integer> pageSizeParam,
                                                            @RequestParam(value = ConstantCore.PAGE, defaultValue = "0") Optional<Integer> pageParam
            , ContractDueDateSearchDto searchDto)  {
        long start = System.currentTimeMillis();
        try {
            searchDto.setPage(pageParam.get());
            searchDto.setPageSize(pageSizeParam.get());
            ObjectDataRes<ContractSearchDueDateResultDto> resObj = apiContractService.getListDueDateFc(searchDto);
            return this.successHandler.handlerSuccess(resObj, start, null, null);
        } catch (Exception ex) {
            return this.errorHandler.handlerException(ex, start, null, null);
        }
    }
    @GetMapping("/get-detail-due-date-contract")// get chi tiet hop dong den han thu phi
    @ApiOperation("Api get detail policy due date")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "success"), @ApiResponse(code = 500, message = "Internal Server Error"),
            @ApiResponse(code = 401, message = "Unauthorized"), @ApiResponse(code = 403, message = "Forbidden")})

    public DtsApiResponse getDetailPolicyDueDate(HttpServletRequest request,
             @RequestParam(value = ConstantCore.PAGE_SIZE, defaultValue = "16") Optional<Integer> pageSizeParam,
             @RequestParam(value = ConstantCore.PAGE, defaultValue = "0") Optional<Integer> pageParam
            , ContractDueDateSearchDto searchDto)  {
        long start = System.currentTimeMillis();
        try {
        	if (StringUtils.isEmpty(searchDto.getAgentCode())) {
        		searchDto.setAgentCode(UserProfileUtils.getFaceMask());
        	}
            searchDto.setPage(pageParam.get());
            searchDto.setPageSize(pageSizeParam.get());
            ContractSearchDueDateResultDto resObj = new ContractSearchDueDateResultDto();
            
            if ("AD".equals(UserProfileUtils.getChannel())) {
            	resObj = adpService.getDetailDueDateContract(searchDto.getPolicyNo());
            } else {
            	resObj = apiContractService.getDetailDueDateContractByCondition(searchDto);
            	if(ObjectUtils.isNotEmpty(resObj) && resObj.getPolicyNo() !=null &&(!resObj.getAgentKey().equals(searchDto.getAgentCode()) || !Integer.valueOf(resObj.getPolicyNo()).equals(Integer.valueOf(searchDto.getPolicyNo())))) {
                	resObj  = new ContractSearchDueDateResultDto();
                	resObj.setCheck(false);
            	}	
            }
            
            return this.successHandler.handlerSuccess(resObj, start, null, null);
        } catch (Exception ex) {
            return this.errorHandler.handlerException(ex, start, null, null);
        }
    }
	@GetMapping(AppApiConstant.API_LIST_BUSINESS_CONTRACT)//hop dong dang xu ly nghiep vu
    @ApiOperation("Api provides constant on systems")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "success"), @ApiResponse(code = 500, message = "Internal Server Error"),
            @ApiResponse(code = 401, message = "Unauthorized"), @ApiResponse(code = 403, message = "Forbidden")})
	
    public DtsApiResponse getListBusinessContractByCondition(HttpServletRequest request, @RequestParam(defaultValue = "0") Integer modeView,
    		@RequestParam(value = ConstantCore.PAGE_SIZE, defaultValue = "16") Optional<Integer> pageSizeParam,
            @RequestParam(value = ConstantCore.PAGE, defaultValue = "1") Optional<Integer> pageParam
            , ContractBusinessSearchDto searchDto)  {
        long start = System.currentTimeMillis();
        try {
            searchDto.setPage(pageParam.get());
        	searchDto.setPageSize(pageSizeParam.get());
        	ObjectDataRes<ContractBusinessSearchResultDto> resObj = apiContractService.getListBusinessContractByCondition(searchDto);
//            ObjectDataRes<ContractBusinessSearchResultDto> resObj = new ObjectDataRes<>();
//            resObj.setDatas(dummyData());
        	return this.successHandler.handlerSuccess(resObj, start, null, null);
        } catch (Exception ex) {
            return this.errorHandler.handlerException(ex, start, null, null);
        }
    }
    @GetMapping(AppApiConstant.API_DETAIL_BUSINESS_CONTRACT)// detail hop dong dang xu ly nghiep vu
    @ApiOperation("API DETAIL BUSINESS CONTRACT")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "success"), @ApiResponse(code = 500, message = "Internal Server Error"),
            @ApiResponse(code = 401, message = "Unauthorized"), @ApiResponse(code = 403, message = "Forbidden")})

    public DtsApiResponse getDetailBusinessContractByCondition(HttpServletRequest request,
           @RequestParam(defaultValue = "0") Integer modeView,
           @RequestParam(value = ConstantCore.PAGE_SIZE, defaultValue = "16") Optional<Integer> pageSizeParam,
           @RequestParam(value = ConstantCore.PAGE, defaultValue = "0") Optional<Integer> pageParam
            , ContractBusinessSearchDto searchDto)  {
        long start = System.currentTimeMillis();
        try {
            searchDto.setPage(pageParam.get());
            searchDto.setPageSize(pageSizeParam.get());
            ContractBusinessSearchResultDto resObj =new ContractBusinessSearchResultDto();
             resObj = apiContractService.getDetailBusinessContractByCondition(searchDto);
            
            if(ObjectUtils.isNotEmpty(resObj) && resObj.getPolicyNo() != null &&(!resObj.getAgentCode().equals(searchDto.getAgentCode()) || !Integer.valueOf(resObj.getPolicyNo()).equals(Integer.valueOf(searchDto.getPolicyNo().toString())))) {
            	resObj  = new ContractBusinessSearchResultDto();
            	resObj.setCheck(false);
        	}
            
            return this.successHandler.handlerSuccess(resObj, start, null, null);
        } catch (Exception ex) {
            return this.errorHandler.handlerException(ex, start, null, null);
        }
    }
	@GetMapping(AppApiConstant.API_LIST_EXPIRES_CONTRACT)//hop dong dao han
    @ApiOperation("Api provides constant on systems")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "success"), @ApiResponse(code = 500, message = "Internal Server Error"),
            @ApiResponse(code = 401, message = "Unauthorized"), @ApiResponse(code = 403, message = "Forbidden")})
	
    public DtsApiResponse getListExpiresContractByCondition(HttpServletRequest request, @RequestParam(defaultValue = "0") Integer modeView,
    		@RequestParam(value = ConstantCore.PAGE_SIZE, defaultValue = "16") Optional<Integer> pageSizeParam,
            @RequestParam(value = ConstantCore.PAGE, defaultValue = "1") Optional<Integer> pageParam
            , ContractExpiresSearchDto searchDto)  {
        long start = System.currentTimeMillis();
        try {
            searchDto.setPage(pageParam.get());
        	searchDto.setPageSize(pageSizeParam.get());
        	ObjectDataRes<ContractExpiresSearchResultDto> resObj = apiContractService.getListExpiresContractByCondition(searchDto);
        	return this.successHandler.handlerSuccess(resObj, start, null, null);
        } catch (Exception ex) {
            return this.errorHandler.handlerException(ex, start, null, null);
        }
    }
    @GetMapping(AppApiConstant.API_DETAIL_EXPIRES_CONTRACT)// get chi tiet hop dong claim
    @ApiOperation("Api get detail policy claim contract")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "success"),
            @ApiResponse(code = 500, message = "Internal Server Error"),
            @ApiResponse(code = 401, message = "Unauthorized"),
            @ApiResponse(code = 403, message = "Forbidden")})

    public DtsApiResponse getDetailPolicyClaim(HttpServletRequest request,
           @RequestParam(value = ConstantCore.PAGE_SIZE, defaultValue = "16") Optional<Integer> pageSizeParam,
           @RequestParam(value = ConstantCore.PAGE, defaultValue = "0") Optional<Integer> pageParam
            , ContractClaimSearchDto searchDto)  {
        long start = System.currentTimeMillis();
        try {
            searchDto.setPage(pageParam.get());
            searchDto.setPageSize(pageSizeParam.get());
            ContractClaimSearchResultDto resObj = new ContractClaimSearchResultDto();
            resObj = apiContractService.getDetailClaimContractByCondition(searchDto);
            
            if("AG".equals(UserProfileUtils.getChannel()) && ObjectUtils.isNotEmpty(resObj) && resObj.getPolicyNo()!= null &&(!resObj.getAgentCode().equals(searchDto.getAgentCode()) || !Integer.valueOf(resObj.getPolicyNo()).equals(Integer.valueOf(searchDto.getPolicyNo().toString())))) {
            	resObj  = new ContractClaimSearchResultDto();
            	resObj.setCheck(false);
        	}
            
            return this.successHandler.handlerSuccess(resObj, start, null, null);
        } catch (Exception ex) {
            return this.errorHandler.handlerException(ex, start, null, null);
        }
    }
	
	@GetMapping(AppApiConstant.API_LIST_CLAIM_CONTRACT)//hop dong dang claim
    @ApiOperation("Api provides constant on systems")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "success"), @ApiResponse(code = 500, message = "Internal Server Error"),
            @ApiResponse(code = 401, message = "Unauthorized"), @ApiResponse(code = 403, message = "Forbidden")})
	
    public DtsApiResponse getListClaimContractByCondition(HttpServletRequest request, @RequestParam(defaultValue = "0") Integer modeView,
    		@RequestParam(value = ConstantCore.PAGE_SIZE, defaultValue = "16") Optional<Integer> pageSizeParam,
            @RequestParam(value = ConstantCore.PAGE, defaultValue = "1") Optional<Integer> pageParam
            , ContractClaimSearchDto searchDto)  {
        long start = System.currentTimeMillis();
        try {
            searchDto.setPage(pageParam.get());
        	searchDto.setPageSize(pageSizeParam.get());
        	ObjectDataRes<ContractClaimSearchResultDto> resObj = apiContractService.getListClaimContractByCondition(searchDto);
        	return this.successHandler.handlerSuccess(resObj, start, null, null);
        } catch (Exception ex) {
            return this.errorHandler.handlerException(ex, start, null, null);
        }
    }
    private String formatPolicyNumber(int digits, String policyNumber){
		if(StringUtils.isEmpty(policyNumber)) {
			return "";
		}
        return IntStream.range(0, digits - policyNumber.length()).mapToObj(i -> "0").collect(Collectors.joining("")).concat(policyNumber);
    }
    //export Active-Inactive
    @SuppressWarnings("rawtypes")
    @PostMapping(AppApiConstant.API_LIST_ALL_CONTRACT_EXPORT)
    @ApiOperation("Export list finace support standard by group")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "success"), @ApiResponse(code = 500, message = "Internal Server Error"),
            @ApiResponse(code = 401, message = "Unauthorized"), @ApiResponse(code = 403, message = "Forbidden"),
            @ApiResponse(code = 402601, message = "Error process type new") })
    public ResponseEntity export(HttpServletRequest request, @RequestBody ContractSearchDto searchDto) {
        ResponseEntity resObj = null;
        String[] headers;
        String title;
        try {

        	searchDto.setPage(0);
        	searchDto.setPageSize(0);
            ObjectDataRes<ContractSearchAllResultDto> common = apiContractService.getListAllContractByCondition(searchDto, true);
            if(common.getDatas().size() > 0) formatData(common);
            for(ContractSearchAllResultDto ls: common.getDatas()){
                ls.setCustomerName(ls.getCustomerName().toUpperCase());
                ls.setInsuranceContract(formatPolicyNumber(9, ls.getInsuranceContract()));
                if(ls.getZprxUnpaidPremAmt() != null && ls.getZprxUnpaidPremAmt().compareTo(BigDecimal.ZERO)  < 0) {
                    ls.setZprxUnpaidPremAmt(BigDecimal.ZERO);
                    ls.setZprxUnpaidPremAmt(BigDecimal.ZERO);
                }
            }
            AgentInfoDb2 agentDetail = db2ApiService.getParentByAgentCode(searchDto.getAgentCode(), searchDto.getAgentGroup(), searchDto.getOrgId());
            if (ObjectUtils.isNotEmpty(agentDetail)) {
                searchDto.setAgentName(agentDetail.getAgentName());
                searchDto.setAgentType(agentDetail.getAgentType());
            }
            common.getDatas().stream().filter(x->isNullOrZero(x.getHangingFee()) || (!isNullOrZero(x.getHangingFee()) && x.getHangingFee().compareTo(new BigDecimal(0)) < 0)).forEach(x->x.setHangingFee(new BigDecimal(0)));
            if(StringUtils.equalsIgnoreCase(searchDto.getTypeEffect(), "Active")){
                headers = new String[] {"STT", "Số HĐBH", "Bên mua BH", "NĐBH chính", "Tình trạng", "Ngày hiệu lực", "Ngày đáo hạn", "Tỉ lệ chia", "Định kỳ đóng phí","Phí dự tính định kỳ","Phí định kỳ/cơ bản định kỳ","Phí cơ bản các kỳ trước chưa đóng","Nợ APL(Vay đóng phí + Lãi)" };
                title = "DANH SÁCH HỢP ĐỒNG CÒN HIỆU LỰC CÁ NHÂN";
                resObj = apiContractService.exportListData(common.getDatas(), "HDBH_con_hieu_luc_ca_nhan.xlsx", "A7", PersonalActivePolicyEnum.class, ContractSearchAllResultDto.class, headers, title,searchDto.getAgentCode(),searchDto.getAgentType(),searchDto.getAgentName(),common.getTotalData());
            } else {
                // headers = new String[] {"STT", "Số HĐBH", "Bên mua BH ", "NĐBH chính ","Địa chỉ","Điện thoại", "Tỉ lệ chia ", "Định kỳ đóng phí   ", "Tình trạng", "Ngày hiệu lực  ", "Ngày mất hiệu lực ","Phí dự tính định kỳ" ,"Phí định kỳ/cơ bản định kỳ", "Số phí còn treo", "Phí bảo hiểm cần đóng tạm tính"};
                headers = new String[] {"STT", "Số HĐBH", "Bên mua BH ", "NĐBH chính ","Địa chỉ","Điện thoại", "Tỉ lệ chia ", "Định kỳ đóng phí   ", "Tình trạng", "Ngày hiệu lực  ", "Ngày mất hiệu lực ","Phí dự tính định kỳ" ,"Phí định kỳ/cơ bản định kỳ", "Số phí còn treo"};
                title = "DANH SÁCH HỢP ĐỒNG MẤT HIỆU LỰC (TRONG 2 NĂM)";
                resObj = apiContractService.exportListData(common.getDatas(), "HDBH_mat_hieu_luc_ca_nhan.xlsx", "A7", PersonalInactivePolicyEnum.class, ContractSearchAllResultDto.class, headers, title,searchDto.getAgentCode(),searchDto.getAgentType(),searchDto.getAgentName(),common.getTotalData());

            }
        } catch (Exception e) {
            logger.error("##exportListAgentStandard##", e);
        }
        return resObj;
    }
    
    private void formatData(ObjectDataRes<ContractSearchAllResultDto> common){
            common.getDatas().forEach(e -> {
                e.setCustomerName(StringUtils.upperCase(e.getCustomerName()));
                if (e.getPolAgtShrPct() != null) {
                    e.setPolAgtShrPct(e.getPolAgtShrPct().setScale(0, RoundingMode.HALF_UP));
                    e.setPolAgtShrPctStr(isNullOrZero(e.getPolAgtShrPct()) || (!isNullOrZero(e.getPolAgtShrPct()) &&e.getPolAgtShrPct().compareTo(new BigDecimal(0)) == 0) ? "0%" : String.valueOf(e.getPolAgtShrPct()).concat("%"));
                } else {
                    e.setPolAgtShrPctStr("0%");
                }
                //set policyKey have 9 number
                common.getDatas().forEach(ls -> {if (StringUtils.isNotBlank(ls.getInsuranceContract())) ls.setInsuranceContract(formatPolicyNumber(9, ls.getInsuranceContract()));
                });
            });
    }

	public static boolean isNullOrZero(BigDecimal number) {
		boolean isBigDecimalValueNullOrZero = false;
		if (number == null)
			isBigDecimalValueNullOrZero = true;
		else if (number != null && number.compareTo(BigDecimal.ZERO) == 0)
			isBigDecimalValueNullOrZero = true;

		return isBigDecimalValueNullOrZero;
	}



    @SuppressWarnings("rawtypes")
    @PostMapping(AppApiConstant.API_LIST_DUE_DATE_CONTRACT_EXPORT)
    @ApiOperation("Export list finace support standard by group")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "success"), @ApiResponse(code = 500, message = "Internal Server Error"),
            @ApiResponse(code = 401, message = "Unauthorized"), @ApiResponse(code = 403, message = "Forbidden"),
            @ApiResponse(code = 402601, message = "Error process type new") })
    public ResponseEntity exportDueDate(HttpServletRequest request, @RequestBody ContractDueDateSearchDto searchDto) {
        ResponseEntity resObj = null;
        String[] headers;
        String title;
        try {
            ObjectDataRes<ContractSearchDueDateResultDto> common = apiContractService.getListExportDueDateContractByCondition(searchDto);
            for(ContractSearchDueDateResultDto ls: common.getDatas()){
                ls.setPolicyNo(formatPolicyNumber(9, ls.getPolicyNo()));
                ls.setInsuranceBuyer(ls.getInsuranceBuyer().toUpperCase());
        		if (isNullOrZero(ls.getFeeMustReceive()) || (!isNullOrZero(ls.getFeeMustReceive()) && ls.getFeeMustReceive().compareTo( new BigDecimal(0)) < 0)) {
        			ls.setFeeMustReceive( new BigDecimal(0));
        		}
            }
            if(StringUtils.equalsIgnoreCase(searchDto.getType(), "NOW")){
                headers = new String[] {"STT ", "Số HĐBH ", "Bên mua BH  ", "NĐBH chính  ", "Địa chỉ thu phí", "Điện thoại di động ", "Ngày hiệu lực","Định kỳ đóng phí","Số tiền","Kỳ đóng phí","Tình trạng thu phí","Phí dự tính định kỳ","Phí định kỳ/cơ bản định kỳ","Phí đóng trước cho kỳ tới","Phí cơ bản các kỳ trước chưa đóng","Nợ APL (Vay tự động + Lãi)","Ngày kết thúc thời gian gia hạn đóng phí"};
                title = "DANH SÁCH HỢP ĐỒNG THU PHÍ CÁ NHÂN";
                resObj = apiContractService.exportListData(common.getDatas(), "HDBH_thu_phi_ca_nhan.xlsx", "A7", PersonalDueDatePolicyEnum.class, ContractSearchDueDateResultDto.class, headers, title,searchDto.getAgentCode(),searchDto.getAgentType(),searchDto.getAgentName(),common.getTotalData());
            } else {
                headers = new String[] {"STT ", "Số HĐBH ", "Bên mua BH  ", "NĐBH chính  ", "Địa chỉ thu phí", "Điện thoại di động ", "Ngày hiệu lực","Định kỳ đóng phí","Số tiền","Kỳ đóng phí","Tình trạng thu phí","Phí dự tính định kỳ","Phí định kỳ/cơ bản định kỳ","Phí đóng trước cho kỳ tới","Phí cơ bản các kỳ trước chưa đóng","Nợ APL (Vay tự động + Lãi)","Ngày kết thúc thời gian gia hạn đóng phí"};
                title = "DANH SÁCH HỢP ĐỒNG THU PHÍ CÁ NHÂN";
                resObj = apiContractService.exportListData(common.getDatas(), "HDBH_thu_phi_ca_nhan.xlsx", "A7", PersonalDueDatePolicyEnum.class, ContractSearchDueDateResultDto.class, headers, title,searchDto.getAgentCode(),searchDto.getAgentType(),searchDto.getAgentName(),common.getTotalData());
            }
        } catch (Exception e) {
            logger.error("##exportListAgentStandard##", e);
        }
        return resObj;
    }
    @PostMapping(AppApiConstant.API_LIST_BUSINESS_CONTRACT_EXPORT)//hop dong dang xu ly nghiep vu
    @ApiOperation("Api provides constant on systems")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "success"), @ApiResponse(code = 500, message = "Internal Server Error"),
            @ApiResponse(code = 401, message = "Unauthorized"), @ApiResponse(code = 403, message = "Forbidden")})

    public ResponseEntity exportBusiness(HttpServletRequest request, @RequestParam(defaultValue = "0") Integer modeView , @RequestBody ContractBusinessSearchDto searchDto)  {
        ResponseEntity resObj = null;
        String[] headers;
        String title;
		AgentInfoDb2 agentDetail = db2ApiService.getParentByAgentCode(searchDto.getAgentCode(), "FC", "");
		if (ObjectUtils.isNotEmpty(agentDetail)) {
			searchDto.setAgentName(agentDetail.getAgentName());
			searchDto.setAgentType(agentDetail.getAgentType());
		}
        try {
            ObjectDataRes<ContractBusinessSearchResultDto> common = apiContractService.exportListBusinessContract(searchDto);
            headers = new String[] {"STT ", "Số HĐBH ", "Bên mua BH  ", "Loại yêu cầu   ", "Tình trạng yêu cầu  ", "Thông tin yêu cầu bổ sung   ", "Ngày tiếp nhận  ", "Ngày thực hiện  ", "Ngày hết hạn bổ sung thông tin", "Văn phòng tiếp nhận"};
            title = "DANH SÁCH HỢP ĐỒNG ĐIỀU CHỈNH NGHIỆP VỤ CÁ NHÂN";
            int i = 1;
            for(ContractBusinessSearchResultDto ls: common.getDatas()){
                ls.setPolicyNo(formatPolicyNumber(9, ls.getPolicyNo()));
                ls.setNo(i++);
                ls.setInsuranceBuyer(ls.getInsuranceBuyer().toUpperCase());
            }
            resObj = apiContractService.exportListData(common.getDatas(), "HDBH_dieu_chinh_nghiep_vu_ca_nhan.xlsx", "A7", PersonalBusinessPolicyEnum.class, ContractBusinessSearchResultDto.class, headers, title,searchDto.getAgentCode(),searchDto.getAgentType(),searchDto.getAgentName(),common.getTotalData());
        } catch (Exception e) {
            logger.error("##exportListAgentStandard##", e);
        }
        return resObj;
    }

    @PostMapping(AppApiConstant.API_LIST_EXPIRES_CONTRACT_EXPORT)//hop dong dao han
    @ApiOperation("Api provides constant on systems")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "success"), @ApiResponse(code = 500, message = "Internal Server Error"),
            @ApiResponse(code = 401, message = "Unauthorized"), @ApiResponse(code = 403, message = "Forbidden")})

    public ResponseEntity exportExpired(HttpServletRequest request,@RequestBody ContractExpiresSearchDto searchDto)  {
        ResponseEntity resObj = null;
        String[] headers;
        String title;
        try {
    		AgentInfoDb2 agentDetail = db2ApiService.getParentByAgentCode(searchDto.getAgentCode(), "FC", "");
    		if (ObjectUtils.isNotEmpty(agentDetail)) {
    			searchDto.setAgentName(agentDetail.getAgentName());
    			searchDto.setAgentType(agentDetail.getAgentType());
    		}
            ObjectDataRes<ContractExpiresSearchResultDto> common = apiContractService.getListExpiresContractByCondition(searchDto);
            headers = new String[] {"STT ", "Số HĐBH ", "BMBH    ", "NĐBH chính  ", "SPBH chính  ", "Ngày hiệu lực   ", "Ngày đáo hạn    ", "Số tiền BH  ", "Số tiền tạm tính của quyền lợi đáo hạn"};
            if(StringUtils.equalsIgnoreCase(searchDto.getType(), "NOW")){
                title = "DANH SÁCH HỢP ĐỒNG ĐÁO HẠN CÁ NHÂN";
            } else {
                title = "DANH SÁCH HỢP ĐỒNG ĐÁO HẠN CÁ NHÂN";
            }
            for(ContractExpiresSearchResultDto ls: common.getDatas()){
                ls.setPolicyKey(formatPolicyNumber(9, ls.getPolicyKey()));
                ls.setPoName(ls.getPoName().toUpperCase());
            }
            resObj = apiContractService.exportListData(common.getDatas(), "HDBH_dao_han_ca_nhan.xlsx", "A7", PersonalExpiredPolicyEnum.class, ContractExpiresSearchResultDto.class, headers, title,searchDto.getAgentCode(),searchDto.getAgentType(),searchDto.getAgentName(),common.getTotalData());
        } catch (Exception e) {
            logger.error("##exportListAgentStandard##", e);
        }
        return resObj;
    }


    @PostMapping(AppApiConstant.API_LIST_CLAIM_CONTRACT_EXPORT)//hop dong dang claim
    @ApiOperation("Api provides constant on systems")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "success"), @ApiResponse(code = 500, message = "Internal Server Error"),
            @ApiResponse(code = 401, message = "Unauthorized"), @ApiResponse(code = 403, message = "Forbidden")})

    public ResponseEntity exportClaim(HttpServletRequest request, @RequestBody ContractClaimSearchDto searchDto)  {
        ResponseEntity resObj = null;
        String[] headers;
        String title;
		AgentInfoDb2 agentDetail = db2ApiService.getParentByAgentCode(searchDto.getAgentCode(), "FC", "");
		if (ObjectUtils.isNotEmpty(agentDetail)) {
			searchDto.setAgentName(agentDetail.getAgentName());
			searchDto.setAgentType(agentDetail.getAgentType());
		}
        try {
            List<ContractClaimResultDto> dataList = apiContractService.getListClaimForExport(searchDto);

            headers = new String[]{"STT ", "Số HĐBH ", "Bên mua BH  ", "Người được bảo hiểm", "Số hồ sơ yêu cầu bồi thường  ", "Ngày mở mở hồ sơ", "Loại yêu cầu bồi thường", "Tình trạng  ", "Thông tin chờ bổ sung   ", "Ngày hết hạn bổ sung    "
                    , "Ngày có kết quả bồi thường  ", "Số tiền bồi thường  ", "Bổ sung quyền lợi nhận thanh toán   ", "Lý do từ chối"};
            title = "HỒ SƠ YÊU CẦU BỒI THƯỜNG CÁ NHÂN";
            int i = 1;
            int total = 0;
            if(ObjectUtils.isNotEmpty(dataList)) {
                List<ContractClaimResultDto> distinctCalim = dataList.stream().filter(StreamUtils.distinctByKey(ContractClaimResultDto::getClaimno)).collect(Collectors.toList());
                total = distinctCalim.size();
            }
            for (ContractClaimResultDto ls : dataList) {
                ls.setNo(i++);
                ls.setLiName(ls.getLiName() !=null ? ls.getLiName().toUpperCase(): null);
                ls.setPoName(ls.getPoName() !=null ? ls.getPoName().toUpperCase(): null);
                ls.setPolicyno(formatPolicyNumber(9, ls.getPolicyno()));
            }

            String fileName;
            if ("1".equals(searchDto.getPolicyType())) {
            	fileName = "Ho_so_YCBT_ca_nhan_dang_xu_ly.xlsx";
            } else if ("2".equals(searchDto.getPolicyType())) {
            	fileName = "Ho_so_YCBT_ca_nhan_cho_bo_sung_thong_tin.xlsx";
            } else if ("3".equals(searchDto.getPolicyType())) {
            	fileName = "Ho_so_YCBT_ca_nhan_da_duoc_dong_y_chi_tra.xlsx";
            } else if ("4".equals(searchDto.getPolicyType())) {
            	fileName = "Ho_so_YCBT_ca_nhan_bi_tu_choi.xlsx";
            } else {
            	fileName = "Ho_so_YCBT_ca_nhan_khong_hop_le.xlsx";
            }
            resObj = apiContractService.exportListData(dataList, fileName, "A7", PersonalClaimPolicyEnum.class, ContractClaimResultDto.class, headers, title,searchDto.getAgentCode(),searchDto.getAgentType(),searchDto.getAgentName(),total);
        } catch (Exception e) {
            logger.error("##exportListAgentStandard##", e);
        }
        return resObj;
    }
    //get more info of Detail CLaim policy
    @GetMapping("/get-detail-claim-by-claimNo")// get chi tiet hop dong claim
    @ApiOperation("Api get detail policy claim contract")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "success"),
            @ApiResponse(code = 500, message = "Internal Server Error"),
            @ApiResponse(code = 401, message = "Unauthorized"),
            @ApiResponse(code = 403, message = "Forbidden")})

    public DtsApiResponse getDetailPolicyClaim(HttpServletRequest request, String claimNoDetail)  {
        long start = System.currentTimeMillis();
        try {
            ClaimOdsDetailDto resObj = apiContractService.getDetailClaimByClaimNo(claimNoDetail);
            
            return this.successHandler.handlerSuccess(resObj, start, null, null);
        } catch (Exception ex) {
            return this.errorHandler.handlerException(ex, start, null, null);
        }
    }
    //get list aditional information
    @GetMapping("/get-list-additional-information-claim-by-claimNo")// get chi tiet hop dong claim
    @ApiOperation("Api get detail policy claim contract")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "success"),
            @ApiResponse(code = 500, message = "Internal Server Error"),
            @ApiResponse(code = 401, message = "Unauthorized"),
            @ApiResponse(code = 403, message = "Forbidden")})

    public DtsApiResponse getListAdditionalInformation(HttpServletRequest request, String claimNoDetail)  {
        long start = System.currentTimeMillis();
        try {
            List<ClaimAdditionalInformationDto> resObj = apiContractService.getListAdditionalInformation(claimNoDetail);

            return this.successHandler.handlerSuccess(resObj, start, null, null);
        } catch (Exception ex) {
            return this.errorHandler.handlerException(ex, start, null, null);
        }
    }
    
    @GetMapping(AppApiConstant.API_LIST_POLICY_MATURED) // get tat ca hop dong dao han
    @ApiOperation("Api get list policy matured")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "success"), @ApiResponse(code = 500, message = "Internal Server Error"),
            @ApiResponse(code = 401, message = "Unauthorized"), @ApiResponse(code = 403, message = "Forbidden")})
	
    public DtsApiResponse getListPolicyMatured(HttpServletRequest request, @RequestParam(defaultValue = "0") Integer modeView,
    		@RequestParam(value = ConstantCore.PAGE_SIZE, defaultValue = "10") Optional<Integer> pageSizeParam,
            @RequestParam(value = ConstantCore.PAGE, defaultValue = "0") Optional<Integer> pageParam
            , PolicyMaturedSearchDto searchDto)  {
        long start = System.currentTimeMillis();
        try {      	
            searchDto.setPage(pageParam.get());
        	searchDto.setPageSize(pageSizeParam.get());
        	ObjectDataRes<PolicyMaturedResultDto> resObj = apiContractService.getListPolicyMatured(searchDto, false);
            
        	return this.successHandler.handlerSuccess(resObj, start, null, null);
        } catch (Exception ex) {
            return this.errorHandler.handlerException(ex, start, null, null);
        }
    }
    
    @SuppressWarnings("rawtypes")
	@PostMapping(AppApiConstant.API_LIST_POLICY_MATURED_EXPORT)
	@ApiOperation("Api export excel list policy matured")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "success"),
			@ApiResponse(code = 500, message = "Internal Server Error"),
			@ApiResponse(code = 401, message = "Unauthorized"), @ApiResponse(code = 403, message = "Forbidden"),
			@ApiResponse(code = 402601, message = "Error process type new") })

	public ResponseEntity exportListPolicyMatured(HttpServletRequest request, @RequestBody PolicyMaturedSearchDto searchDto) {
		Locale locale = LangugeUtil.getLanguageFromHeader(request);
		searchDto.setLanguage(locale.getLanguage());
		ResponseEntity resObj = null;
		try {
			AgentInfoDb2 agentDetail = db2ApiService.getParentByAgentCode(searchDto.getAgentCode(), "FC", "");
			if (ObjectUtils.isNotEmpty(agentDetail)) {
				searchDto.setAgentName(agentDetail.getAgentName());
				searchDto.setAgentType(agentDetail.getAgentType());
			}
			resObj = apiContractService.exportListPolicyMatured(searchDto);
		} catch (Exception e) {
			logger.error("##exportListPolicyMatured##", e.getMessage());
		}
		return resObj;
	}
    
    @GetMapping(AppApiConstant.API_DETAIL_POLICY_MATURED) // get chi tiet hop dong dao han
    @ApiOperation("Api get detail policy matured")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "success"),
            @ApiResponse(code = 500, message = "Internal Server Error"),
            @ApiResponse(code = 401, message = "Unauthorized"),
            @ApiResponse(code = 403, message = "Forbidden")})

    public DtsApiResponse getDetailPolicyMatured(HttpServletRequest request, String policyNo)  {
        long start = System.currentTimeMillis();
        try {
        	PolicyMaturedResultDto resObj = apiContractService.getDetailPolicyMatured(policyNo);

            return this.successHandler.handlerSuccess(resObj, start, null, null);
        } catch (Exception ex) {
            return this.errorHandler.handlerException(ex, start, null, null);
        }
    }
    
    //get contact history
    @GetMapping("/get-contact-history-by-claimNo")// get lich su lien he khach hang
    @ApiOperation("Api get contact history")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "success"),
            @ApiResponse(code = 500, message = "Internal Server Error"),
            @ApiResponse(code = 401, message = "Unauthorized"),
            @ApiResponse(code = 403, message = "Forbidden")})

    public DtsApiResponse getContactHistory(HttpServletRequest request, String claimNo)  {
        long start = System.currentTimeMillis();
        try {
            List<ContactHistoryDetailDto> resObj = apiContractService.getContactHistoryByClaimNo(claimNo);

            return this.successHandler.handlerSuccess(resObj, start, null, null);
        } catch (Exception ex) {
            return this.errorHandler.handlerException(ex, start, null, null);
        }
    }
    
    @GetMapping("/get-cost-of-refusal-to-pay")
    @ApiOperation("Api get cost of refusal to pay")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "success"),
            @ApiResponse(code = 500, message = "Internal Server Error"),
            @ApiResponse(code = 401, message = "Unauthorized"),
            @ApiResponse(code = 403, message = "Forbidden")})

    public DtsApiResponse getCostOfRefusalToPay(HttpServletRequest request, String policyNo, String claimNo)  {
        long start = System.currentTimeMillis();
        try {
            List<CostOfRefusalToPayDto> resObj = apiContractService.getCostOfRefusalToPay(claimNo);

            return this.successHandler.handlerSuccess(resObj, start, null, null);
        } catch (Exception ex) {
            return this.errorHandler.handlerException(ex, start, null, null);
        }
    }
}
