package vn.com.unit.ep2p.service;

import org.springframework.http.ResponseEntity;
import vn.com.unit.cms.core.dto.CmsCommonPagination;
import vn.com.unit.cms.core.module.customer.dto.PolicyInformationSearchDto;
import vn.com.unit.cms.core.module.customer.dto.ProductInformationDto;
import vn.com.unit.cms.core.module.customerManagement.dto.*;
import vn.com.unit.core.config.SystemConfig;

import javax.servlet.http.HttpServletResponse;
import java.util.Locale;

import vn.com.unit.cms.core.module.events.dto.EventsDto;
import vn.com.unit.core.config.SystemConfig;
import vn.com.unit.ep2p.admin.dto.CustomerBirthdayDto;
import vn.com.unit.ep2p.admin.dto.CustomerChargeDto;
import vn.com.unit.ep2p.admin.dto.EventDetailDto;

import java.io.FileNotFoundException;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletResponse;

import org.springframework.http.ResponseEntity;


public interface PersonalInsuranceDocService {

    CmsCommonPagination<PersonalInsuranceDocumentDto> getListDocByStatus(PersonalInsuranceDocumentSearchDto searchDto);

    PersonalInsuranceDocumentDto getDetailDocumentByDocNo(String docNo);

    CmsCommonPagination<ProductDto> getListProductByDocNo(String docNo, Integer page, Integer size, String agentCode);

    CmsCommonPagination<RequestAppraisalDto> getListRequestAppraisal(String docNo, Integer page, Integer size, String agentCode);

    CmsCommonPagination<RequestAdditionalDto> getListRequestAdditional(String docNo, Integer page, Integer size, String agentCode);

    CmsCommonPagination<FileSubmittedDto> getListFileSubmitted(String docNo, Integer page, Integer size, String agentCode);

    CmsCommonPagination<OfficeInsuranceDto> getListOfficeDocument(OfficeInsuranceSearchDto searchDto);

    CmsCommonPagination<OfficeInsuranceDetailDto> getListOfficeDocumentByType(OfficeInsuranceTypeSumSearchDto searchDto);

    CmsCommonPagination<OfficeInsuranceContractDetailDto> getListOfficeDocumentContractByType(OfficeInsuranceTypeSumSearchDto searchDto);

    CmsCommonPagination<OfficeInsuranceContractDetailDto> getListOfficeDocumentContractDetailByType(OfficeInsuranceTypeSumSearchDto searchDto);
    
    CmsCommonPagination<OfficeInsuranceContractDetailDto> exportListPolicyByAgentByConditionBMUM(OfficeInsuranceTypeSumSearchDto searchDto);
    
    
    public ResponseEntity exportListOfficeDocumentBMUMDetail (OfficeInsuranceTypeSumSearchDto searchDto);

    public ResponseEntity exportListOfficeDocument(OfficeInsuranceSearchDto searchDto);

    public SystemConfig getSystemConfig();

    @SuppressWarnings("rawtypes")
    public<T, E extends Enum<E>, M> ResponseEntity exportListDataWithHeader(List<T> resultDto, String view, String policyType, String[] titleHeader, String row, Class<E> enumDto, Class<M> className,String agentCode,String agentType,String agentName, String orgName, int total, String type);


    CmsCommonPagination<OfficeInsuranceDetailDto> getDetailOfficeDocumentPolicyByAgentCode(String agentCode, String agentType, String status, Integer page, Integer size);
    
	@SuppressWarnings("rawtypes")
	public ResponseEntity exportListPrersonalInsuranceDocByCondition(PersonalInsuranceDocumentSearchDto searchDto, HttpServletResponse response,
			Locale locale);
	
	List<ProductInformationDto> getDetailProduct(String policyNo);//so hd
	
	List<AdditionalDetailDto> getDetailAdditional(String policyNo);//so hd
	
	PersonalInsuranceDocumentDto getDetailProfile(String docType, String docNo);//so hs
}
