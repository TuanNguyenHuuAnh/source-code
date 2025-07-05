package vn.com.unit.ep2p.service;

import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletResponse;

import org.springframework.http.ResponseEntity;

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

public interface ApiContractService {
	
	ObjectDataRes<ContractSearchAllResultDto> getListAllContractByCondition(ContractSearchDto dto, boolean isExport);
	ObjectDataRes<ContractSearchDueDateResultDto> getListDueDateContractByCondition(ContractDueDateSearchDto dto);
	ObjectDataRes<ContractSearchDueDateResultDto> getListDueDateFc(ContractDueDateSearchDto dto);
	ObjectDataRes<ContractBusinessSearchResultDto> getListBusinessContractByCondition(ContractBusinessSearchDto searchDto);
	ObjectDataRes<ContractExpiresSearchResultDto> getListExpiresContractByCondition(ContractExpiresSearchDto dto);
	ObjectDataRes<ContractClaimSearchResultDto> getListClaimContractByCondition(ContractClaimSearchDto dto);

	ContractSearchAllResultDto getDetailContractByCondition(ContractSearchDto searchDto);
	ContractBusinessHistoryResponseDto getBusinessHistory(String policyNo);
	

    ContractSearchDueDateResultDto getDetailDueDateContractByCondition(ContractDueDateSearchDto searchDto);

	@SuppressWarnings("rawtypes")
	public<T, E extends Enum<E>, M> ResponseEntity exportListData(List<T> resultDto, String fileName, String row, Class<E> enumDto, Class<M> className, String[] titleHeader, String titleName,String agentCode, String agentGroup,String agentName, int total);

	ContractClaimSearchResultDto getDetailClaimContractByCondition(ContractClaimSearchDto searchDto);

    ContractBusinessSearchResultDto getDetailBusinessContractByCondition(ContractBusinessSearchDto searchDto);

    ClaimOdsDetailDto getDetailClaimByClaimNo(String claimNo);

	List<ClaimAdditionalInformationDto> getListAdditionalInformation(String claimNo);
	
	List<ContractClaimResultDto> getListClaimForExport(ContractClaimSearchDto searchDto);

    ObjectDataRes<ContractSearchDueDateResultDto> getListExportDueDateContractByCondition(ContractDueDateSearchDto searchDto);

    ObjectDataRes<ContractBusinessSearchResultDto> exportListBusinessContract(ContractBusinessSearchDto dto);
    
    ObjectDataRes<PolicyMaturedResultDto> getListPolicyMatured(PolicyMaturedSearchDto searchDto, boolean isExport);
    
    ResponseEntity exportListPolicyMatured(PolicyMaturedSearchDto searchDto);
    
    PolicyMaturedResultDto getDetailPolicyMatured(String polNo);
    
    List<ContactHistoryDetailDto> getContactHistoryByClaimNo(String claimNo);
    
    List<CostOfRefusalToPayDto> getCostOfRefusalToPay(String claimNo);
}
