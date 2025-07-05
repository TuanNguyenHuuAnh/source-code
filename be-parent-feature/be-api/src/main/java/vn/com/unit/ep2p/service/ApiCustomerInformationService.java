package vn.com.unit.ep2p.service;

import java.sql.SQLException;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletResponse;

import org.springframework.http.ResponseEntity;

import vn.com.unit.cms.core.dto.CmsCommonPagination;
import vn.com.unit.cms.core.module.customer.dto.ClientNickNameDto;
import vn.com.unit.cms.core.module.customer.dto.CustomerDetailParam;
import vn.com.unit.cms.core.module.customer.dto.CustomerInformationDto;
import vn.com.unit.cms.core.module.customer.dto.CustomerInformationSearchDto;
import vn.com.unit.cms.core.module.customer.dto.CustomerInteractionHistoryDto;
import vn.com.unit.cms.core.module.customer.dto.CustomerInteractionHistorySearchDto;
import vn.com.unit.cms.core.module.customer.dto.CustomerPotentialDto;
import vn.com.unit.cms.core.module.customer.dto.InsuranceFeesInformationDto;
import vn.com.unit.cms.core.module.customer.dto.PolicyInformationDto;
import vn.com.unit.cms.core.module.customer.dto.PolicyInformationSearchDto;
import vn.com.unit.cms.core.module.customer.dto.ProductInformationDto;
import vn.com.unit.cms.core.module.customer.dto.ProductInformationSearch;
import vn.com.unit.cms.core.module.events.dto.EventsGuestDetailDto;
import vn.com.unit.cms.core.module.events.dto.EventsGuestSearchDto;
import vn.com.unit.core.config.SystemConfig;
import vn.com.unit.ep2p.admin.dto.CustomerInformationDetailDto;
import vn.com.unit.ep2p.admin.dto.PolicyInfoDto;

public interface ApiCustomerInformationService {

	ClientNickNameDto editOrAddEvents(ClientNickNameDto dto) throws SQLException;
	
	CmsCommonPagination<CustomerInformationDto> getListCustomerByCondition(CustomerInformationSearchDto dto);

	CustomerInformationDetailDto getListCustomerByCustomerNo(String customerCode,String agentCode);

	CmsCommonPagination<PolicyInformationDto> getListPolicyByCondition(PolicyInformationSearchDto searchDto);

	PolicyInfoDto getListPolicyByPolicyNo(String policyNo);

	CmsCommonPagination<ProductInformationDto> getListProductByCondition(ProductInformationSearch dto);

	CmsCommonPagination<InsuranceFeesInformationDto> getListInsuranceFeesByCondition(String stringJsonParam);
	
	@SuppressWarnings("rawtypes")
	public ResponseEntity exportListCustomerAgentByCondition(CustomerInformationSearchDto searchDto, HttpServletResponse response,
			Locale locale,String agentCode);
	
	@SuppressWarnings("rawtypes")
	public ResponseEntity exportListPolicyByAgentByCondition(PolicyInformationSearchDto searchDto, HttpServletResponse response,
			Locale locale,String agentCode,String customerNo,String policytype);
	
	public SystemConfig getSystemConfig();

	String getNickName(String agentCode, String customerNo);
	String getNickNameByAgentCode(String agentCode, String customerNo);
	void callStoreCustomerBirthdayDetail(String store, CustomerDetailParam param);

	List<ProductInformationDto> orderbyProduct(List<ProductInformationDto> data);
	
	CmsCommonPagination<EventsGuestDetailDto> getListGuestsOfEvent(EventsGuestSearchDto searchDto);
	
	CmsCommonPagination<CustomerPotentialDto> getListCustomerPotential(CustomerInformationSearchDto dto);
	
	@SuppressWarnings("rawtypes")
	public ResponseEntity exportListCustomerPotential(CustomerInformationSearchDto searchDto, HttpServletResponse response,
			Locale locale,String agentCode);
	
	CustomerPotentialDto getDetailCustomerPotential(String agentCode, String phoneNumber);
	
	CmsCommonPagination<CustomerInteractionHistoryDto> getListInteractionHistory(CustomerInteractionHistorySearchDto dto);
	
	@SuppressWarnings("rawtypes")
	public ResponseEntity exportListInteractionHistory(CustomerInteractionHistorySearchDto searchDto, HttpServletResponse response,
			Locale locale, String agentCode, String phoneNumber);
	
	CustomerInteractionHistoryDto getDetailInteractionHistory(String agentCode, String phoneNumber, String proposalNo);
}
