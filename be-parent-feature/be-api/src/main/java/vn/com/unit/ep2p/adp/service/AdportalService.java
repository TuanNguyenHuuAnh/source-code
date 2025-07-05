package vn.com.unit.ep2p.adp.service;

import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletResponse;

import org.springframework.http.ResponseEntity;

import vn.com.unit.cms.core.dto.CmsCommonPagination;
import vn.com.unit.cms.core.module.contract.dto.ContractSearchDueDateResultDto;
import vn.com.unit.cms.core.module.customerManagement.dto.TotalPolicyDto;
import vn.com.unit.core.res.ObjectDataRes;
import vn.com.unit.ep2p.adp.dto.AgentInfoSaleSopDto;
import vn.com.unit.ep2p.adp.dto.AgentInfoSearchDto;
import vn.com.unit.ep2p.adp.dto.AgentInfoSearchResultDto;
import vn.com.unit.ep2p.adp.dto.AggregateReportDataRes;
import vn.com.unit.ep2p.adp.dto.AggregateReportSearchDto;
import vn.com.unit.ep2p.adp.dto.BenefiDto;
import vn.com.unit.ep2p.adp.dto.CareContactPolicyDto;
import vn.com.unit.ep2p.adp.dto.CareContactPolicySearchDto;
import vn.com.unit.ep2p.adp.dto.ClaimAssessorCommentResultDto;
import vn.com.unit.ep2p.adp.dto.ContactHistoryDto;
import vn.com.unit.ep2p.adp.dto.DuePolicyCardPersonalDto;
import vn.com.unit.ep2p.adp.dto.GeneralReportDto;
import vn.com.unit.ep2p.adp.dto.GeneralReportSearchDto;
import vn.com.unit.ep2p.adp.dto.GroupDocumentDto;
import vn.com.unit.ep2p.adp.dto.ItemDto;
import vn.com.unit.ep2p.adp.dto.PersonalInsuranceDto;
import vn.com.unit.ep2p.adp.dto.PersonalInsuranceSearchDto;
import vn.com.unit.ep2p.adp.dto.PersonalPolicyResultDto;
import vn.com.unit.ep2p.adp.dto.PersonalPolicySearchDto;
import vn.com.unit.ep2p.adp.dto.PolicyClaimSearchResultDto;
import vn.com.unit.ep2p.adp.dto.PolicyContactHistoryDto;
import vn.com.unit.ep2p.adp.dto.PolicyContactHistorySearchDto;
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
import vn.com.unit.ep2p.adp.dto.ReportK2K2PlusDto;
import vn.com.unit.ep2p.adp.dto.ReportK2K2PlusPagination;
import vn.com.unit.ep2p.adp.dto.ReportK2K2PlusSearchDto;
import vn.com.unit.ep2p.adp.dto.TotalProposalDto;
import vn.com.unit.ep2p.adp.dto.TrackingReportDto;
import vn.com.unit.ep2p.adp.dto.TrackingReportSearchDto;

public interface AdportalService {
	public TotalProposalDto getTotalProposal(ProposalSearchDto searchDto);
	public TotalPolicyDto getTotalPolicy(String agentCode);
	
	public ProposalDetailDto getProposalDetail(ProposalDetailSearchDto searchDto);
	
	public CmsCommonPagination<PersonalInsuranceDto> getListInsuranceByStatus(PersonalInsuranceSearchDto searchDto);
	public ObjectDataRes<PersonalPolicyResultDto> getListPolicyByType(PersonalPolicySearchDto dto);
	public ObjectDataRes<PersonalPolicyResultDto> getListPolicyByKeyword(PolicySearchDto dto);
	
	public CmsCommonPagination<ProductDetailDto> getListProduct(ProductDetailSearch dto);
	
	public CmsCommonPagination<BenefiDto> getListBenefi(String policyNo);

    public ResponseEntity exportListPrersonalInsuranceDocuments(PersonalInsuranceSearchDto searchDto);

    public ResponseEntity exportListPrersonalPolicy(PersonalInsuranceSearchDto searchDto);
	
	public ObjectDataRes<PolicyRequestSearchResultDto> getListRequestPolicy(PersonalInsuranceSearchDto dto);
	public PolicyRequestSearchResultDto getDetailRequestPolicy(PolicyRequestSearchDto dto);
	public ResponseEntity exportListRequestPolicy(PersonalInsuranceSearchDto searchDto);
	
	public ObjectDataRes<PolicyClaimSearchResultDto> getListClaimPolicy(PersonalInsuranceSearchDto dto);
	public ResponseEntity exportListClaimPolicy(PersonalInsuranceSearchDto searchDto);
	
	public CmsCommonPagination<PolicyFeeCardPersonalDto> getListFeePolicy(PersonalInsuranceSearchDto searchDto);
	public ResponseEntity exportListFeePolicy(PersonalInsuranceSearchDto searchDto, HttpServletResponse response, Locale locale);
	public CmsCommonPagination<DuePolicyCardPersonalDto> getListDuePolicy(PersonalInsuranceSearchDto searchDto);
	public ResponseEntity exportListDuePolicy(PersonalInsuranceSearchDto searchDto, HttpServletResponse response, Locale locale);
	
	public CmsCommonPagination<ItemDto> getListPartner();
	public CmsCommonPagination<ItemDto> getListItemCombobox(String parentCode, String itemName);

	public CmsCommonPagination<PolicyDeliveryDto> getListPolicyDelivery(PolicyDeliverySearchDto searchDto, boolean isExport);
	public ResponseEntity exportListPolicyDelivery(PolicyDeliverySearchDto searchDto, HttpServletResponse response, Locale locale);
	public CmsCommonPagination<TrackingReportDto> getTrackingReport(TrackingReportSearchDto searchDto);
	public ResponseEntity exportTrackingReport(TrackingReportSearchDto searchDto, HttpServletResponse response, Locale locale);
	public List<PolicyDeliveryDetailsDto> getDetailPolicyDelivery(PolicyDeliveryDetailsSearchDto searchDto);
	public ContractSearchDueDateResultDto getDetailDueDateContract(String policyNo);
	
	public CmsCommonPagination<AgentInfoSearchResultDto> getAgentInfo(AgentInfoSearchDto searchDto);
	public ResponseEntity exportAgentInfo(AgentInfoSearchDto searchDto, HttpServletResponse response, Locale locale);
	
	public GeneralReportDto getGeneralReportInfo(GeneralReportSearchDto searchDto);
	public ReportK2K2PlusPagination<ReportK2K2PlusDto> getK2K2PlusReport(ReportK2K2PlusSearchDto searchDto, boolean isExport);
	public ResponseEntity exportK2K2PlusReport(ReportK2K2PlusSearchDto searchDto, HttpServletResponse response, Locale locale);
	
	public AggregateReportDataRes getAggregateReport(AggregateReportSearchDto searchDto);
	public ResponseEntity exportAggregateReport(AggregateReportSearchDto searchDto, HttpServletResponse response, Locale locale);
	
	public AgentInfoSaleSopDto getAgentInfoSaleSop();
	
	public CmsCommonPagination<ContactHistoryDto> getContactHistoryByPolicy(String agentCode, String policyNo);
	public CmsCommonPagination<CareContactPolicyDto> getListCareContactPolicy(CareContactPolicySearchDto searchDto);
	public ResponseEntity exportListCareContactPolicy(CareContactPolicySearchDto searchDto, HttpServletResponse response, Locale locale);
	public CmsCommonPagination<PolicyContactHistoryDto> getContactHistoryByAgent(PolicyContactHistorySearchDto searchDto);
	public ResponseEntity exportListContactHistoryByAgent(PolicyContactHistorySearchDto searchDto, HttpServletResponse response, Locale locale);
	
	public ObjectDataRes<PolicyClaimSearchResultDto> getClaimByPolicyNo(String policyNo);
	public ObjectDataRes<ClaimAssessorCommentResultDto> getAssessorCommentByClaimNo(String claimNo);
	public ObjectDataRes<PolicyRequestSearchResultDto> getRequestByPolicyNo(String policyNo);
	
	public GroupDocumentDto getGroupDocument(String docNo);
}
