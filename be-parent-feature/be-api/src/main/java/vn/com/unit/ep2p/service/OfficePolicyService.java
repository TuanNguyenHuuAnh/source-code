package vn.com.unit.ep2p.service;

import java.util.List;

import org.springframework.http.ResponseEntity;

import vn.com.unit.cms.core.dto.CmsCommonPagination;
import vn.com.unit.cms.core.dto.CommonSearchWithPagingDto;
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

public interface OfficePolicyService {
    CmsCommonPagination<OfficePolicyClaimDetailDto> getDetailPolicyClaim(ContractClaimGroupSearchDto contractClaimGroupSearchDto);

    //CmsCommonPagination<OfficePolicyRenewDetailDto> getDetailPolicyRenew(ContractRenewGroupSearchDto contractRenewGroupSearchDto);

    CmsCommonPagination<OfficePolicyExpiredDetailDto> getDetailPolicyExpired(PolicyExpiredSearchDto searchDto);

    CmsCommonPagination<OfficePolicyOrphanDetailDto> getDetailPolicyOrphan(PolicyOrphanSearchDto searchDto);

	CmsCommonPagination<TotalInsuranceContractGroupDto> getListTotalContractByGroup(TotalInsuranceContractGroupSearchDto searchDto);
	
	CmsCommonPagination<ContractOverdueFeeRypDto> getListContractOverdueFeeRYP(ContractFeeGroupSearchDto searchDto);

	CmsCommonPagination<ContractFeeGroupDto> getListFeeByGroup(ContractFeeGroupSearchDto searchDto);
	
	CmsCommonPagination<ContractFeeGroupDto> getListFeeByGroupDetail(ContractFeeGroupSearchDto searchDto);

	CmsCommonPagination<ContractEffectGroupDto> getListContractEffectByGroup(ContractEffectGroupSearchDto searchDto, boolean isExport);
	
	List<FinanceSupportStandardDto> getListAgentStandard(String agentCode, String agentGroup, String orgCode);

	CmsCommonPagination<ContractBusinessHandleDto> getListBusinessHandle(ContractBusinessGroupSearchDto contractBusinessGroupSearchDto);
	
	@SuppressWarnings("rawtypes")
    public<T, E extends Enum<E>, M> ResponseEntity exportListDataWithHeader(List<T> resultDto, String view, String policyType, String[] titleHeader, String row, Class<E> enumDto, Class<M> className);

	@SuppressWarnings("rawtypes")    public<T, E extends Enum<E>, M> ResponseEntity exportListDataWithHeaderDto(List<T> resultDto, String view, String policyType, String[] titleHeader, String row, Class<E> enumDto, Class<M> className, String agentCode, String agentType, int total);

	@SuppressWarnings("rawtypes")
    public<T, E extends Enum<E>, M> ResponseEntity exportListData(List<T> resultDto, String fileName, String row, Class<E> enumDto, Class<M> className);

	TotalPolicyDto getTotalPolicy(String agentCode, String agentGroup, String orgId);
	
	@SuppressWarnings("rawtypes")
    ResponseEntity exportListViewBD(TotalInsuranceContractGroupSearchDto searchDto);
	
	CommonSearchWithPagingDto geranateCommonSearch(Object searchDto, String functionCode );

	String searchBmUm(String keyWord, String searchManager, String searchAgent, String search);

	String fieldSearchBmUm(Object keySearch, String functionCode);

	CmsCommonPagination<PolicyMaturedDetailDto> getDetailPolicyMaturedByGroup(PolicyMaturedSearchDto searchDto, boolean isExport);
	
	TotalClaimDto getTotalClaim(String agentCode, String agentGroup, String orgId);
	
	CmsCommonPagination<ClaimGroupDto> getListClaimByGroup(ContractClaimGroupSearchDto searchDto);
	
	CmsCommonPagination<TotalClaimGroupDto> getListClaimByGroupBD(TotalInsuranceContractGroupSearchDto searchDto);
	
	@SuppressWarnings("rawtypes")
	ResponseEntity exportListClaimViewBD(TotalInsuranceContractGroupSearchDto dto);
}
