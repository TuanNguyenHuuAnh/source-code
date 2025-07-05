package vn.com.unit.ep2p.admin.service;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

import vn.com.unit.common.dto.AaGaOfficeDto;
import vn.com.unit.common.dto.PartnerDto;
import vn.com.unit.common.dto.Select2Dto;
import vn.com.unit.common.dto.SelectItem;
import vn.com.unit.ep2p.admin.dto.ADPDeviceTokenDto;
import vn.com.unit.ep2p.admin.dto.AcceptanceCertificationGaInformationDto;
import vn.com.unit.ep2p.admin.dto.AgentContactInfoDto;
import vn.com.unit.ep2p.admin.dto.AgentInfoDb2;
import vn.com.unit.ep2p.admin.dto.AgentInfoDto;
import vn.com.unit.ep2p.admin.dto.AgentInfoSaleSopDto;
import vn.com.unit.ep2p.admin.dto.AgentTaxBankInfoDto;
import vn.com.unit.ep2p.admin.dto.AgentBankDto;
import vn.com.unit.ep2p.admin.dto.ConfirmDecreeDto;
import vn.com.unit.ep2p.admin.dto.ContractBusinessHistoryDto;
import vn.com.unit.ep2p.admin.dto.CustomerBirthdayInMonthParam;
import vn.com.unit.ep2p.admin.dto.CustomerBirthdayParam;
import vn.com.unit.ep2p.admin.dto.CustomerChargeInMonthParam;
import vn.com.unit.ep2p.admin.dto.CustomerChargeParam;
import vn.com.unit.ep2p.admin.dto.CustomerInformationDetailDto;
import vn.com.unit.ep2p.admin.dto.Db2AdpUserInfoParamDto;
import vn.com.unit.ep2p.admin.dto.Db2AgentDto;
import vn.com.unit.ep2p.admin.dto.Db2AgentInformationParamDto;
import vn.com.unit.ep2p.admin.dto.AgentAllowExportTaxCommitmentDto;
import vn.com.unit.ep2p.admin.dto.Db2AgentNotifyParamDto;
import vn.com.unit.ep2p.admin.dto.Db2ContestSummaryParamDto;
import vn.com.unit.ep2p.admin.dto.Db2SummaryDto;
import vn.com.unit.ep2p.admin.dto.GADOfficeDto;
import vn.com.unit.ep2p.admin.dto.IncomeDto;
import vn.com.unit.ep2p.admin.dto.NotifeeDto;
import vn.com.unit.ep2p.admin.dto.NotifyBaseDto;
import vn.com.unit.ep2p.admin.dto.OfficeDto;
import vn.com.unit.ep2p.admin.dto.OfficeParamDto;
import vn.com.unit.ep2p.admin.dto.OrgLocationDto;
import vn.com.unit.ep2p.admin.dto.PolicyChangedDto;
import vn.com.unit.ep2p.admin.dto.PolicyInfoDto;
import vn.com.unit.ep2p.admin.dto.PolicyMaturedInfoDto;
import vn.com.unit.ep2p.admin.dto.TotalBdByAgentCode;
import vn.com.unit.ep2p.admin.dto.TotalInsuranceDocDto;
import vn.com.unit.ep2p.admin.dto.TrainingTraineeDB2Param;
import vn.com.unit.ep2p.admin.dto.RoleDocumentDto;

public interface Db2ApiService {
    Db2AgentDto getAgentInfoByCondition(String agentCode);

    int getAgentDiscipline(String username);

    List<Select2Dto> getListOfficeDb2(String province);

    boolean checkAgentIsGad(String username);

    int countCustomerBirthdayEvent(Date date, String user);

    List<Select2Dto> getListTerritory(String agentCode);

    List<Select2Dto> getListRegion(String territory, String agentCode);

    List<Select2Dto> getListArea(String region, String agentCode, String territory);

    List<Select2Dto> getListEventOffice(String area, String agentCode, String region, String territory);

    List<Select2Dto> getListEventPosition();

    List<Select2Dto> getCity();

    List<Select2Dto> getDistrictByCity(String zipCode,String district);

    void callStoreDb2(String s, Db2AgentNotifyParamDto db2AgentNotifyParamDto);

    void callStoredb2Birthday(String storeCustomerBirthday, CustomerBirthdayParam customerBirthdayParam);

    void callStoreCustomerCharge(String storeCustomerCharge, CustomerChargeParam customerChargeParam);
    
    void callStoreinfomationAgent(String storeInformation, Db2AgentInformationParamDto db2AgentInformationParamDto);

    List<Db2AgentDto> getBdohInfoByOrgId(String office);	

    int countCustomerChargeEvent(Date time, String user);

    boolean checkValidEmailPersonal(String emailPersonal);

    boolean checkValidEmailDlvn(String emailDlvn);

    Db2AgentDto getAgentClientDetail(String agentCode);

    void callStoredBirthdayInMonth(String storeCustomerBirthdayD2d, CustomerBirthdayInMonthParam customerBirthdayParam);

    void callStoreCustomerChargeInMonth(String storeCustomerChargeD2d, CustomerChargeInMonthParam customerChargeInMonthParam);

    List<Db2AgentDto> getDobAgent();
    
    List<Db2SummaryDto> getAllAgent();
    
    Db2SummaryDto getAgentByAgentCode(String agentCode);

    
    Db2SummaryDto findAllAgentType(String memoNo);

    void callStoreAgentDetailDb2(String storeName, Db2ContestSummaryParamDto param);

    Db2AgentDto getAgentInfoByConditionPlus(String agent);

    List<Db2AgentDto> getAllAgentCode();

    String getAgentTypeByAgentDate(String agentCode, String yyyyMM) throws ParseException;

    List<Db2AgentDto> getDescription();

	List<TotalInsuranceDocDto> getTotalInsuranceByAgent(String agentCode, String agentGroup, String orgCode);

    boolean checkAgentTypeIsDfa(String agentType);

    PolicyInfoDto getDetailContract(Integer policyNo);

	AgentInfoDb2 getParentByAgentCode(String agentCode, String agentType, String orgCode);

	List<AaGaOfficeDto> getListOfficeByGad(String agentCode, String orgCode, String inActive);

	List<AaGaOfficeDto> getListOfficeByGadForPayment(String agentCode, String orgCode, String cutoffDate, Boolean payroll);
	
	List<AaGaOfficeDto> getListOfficeByGadForYearlyPayment(String agentCode, String year);

	Db2AgentDto getAgentInfoHist(String agentCode, String dateKey);

	TotalBdByAgentCode getTotalBd(String agentCode, String agentGroup, String orgCode);

	AgentInfoDb2 getAgentGroupHist(String agentCode, String yyyyMm, String orgCode);

	Integer getAgentStatusByCondition(String username);
	
	CustomerInformationDetailDto getCustomerInformationDetail(String agentCode, String customerNo);
	
	String getITrustFlag(String agentCode);
	
	List<Db2AgentDto> getAgentInfoByCode(List<String> agentCodes);
	
	boolean checkDiscipline(String agentCode);
	
	String getPosition(String agentCode);
	
	List<Db2AgentDto> getAllAgentOfSupport(String agentCode);
	
	List<Db2AgentDto> getAllAgentOfManager(String agentCode);
	
	List<Db2AgentDto> getAllAgentOfBD(String agentCode);
	
	List<Db2AgentDto> getAllAgentOfCAO(String agentCode);
	
	List<Db2AgentDto> getAllAgentOfLDAP(String agentCode);
	
	List<OfficeDto> getListOfficeActive();
	
	List<Select2Dto> getWardByDistrict(String district);
    
	AgentContactInfoDto getContactAndCommonInfo(Long agentCode);
    
	List<GADOfficeDto> getGADOfficeInformation(String officeCode, String gadCode);
	
	void callStoreAdpUserDetailDb2(String storeName, Db2AdpUserInfoParamDto param);
	
	List<String> getListCodeForOrderNotify(String channel);
	
	List<ADPDeviceTokenDto> getDeviceTokenInfo(List<String> agentCodes);
	
	List<OrgLocationDto> getOrgLocationByAgent(String agentCode);
	
	List<OrgLocationDto> getOrgLocationByZd(String agentCode);
    
    List<PolicyChangedDto> getPolicyChanged();
    
    List<AgentInfoDto> getAgentInfo(String agentCode);
    
    List<OrgLocationDto> getOrgLocationInfo(String agentCode, String agentType, String partnerCode);
    
    List<ContractBusinessHistoryDto> getBusinessHistory(String policyNo);
    
	List<PolicyMaturedInfoDto> getListCodeNotifyPolicyMatured();
	
	boolean checkChildrenInParent(String parentCode, String childrenCode);
	
	List<String> getZdCodeByZoneCode(String zoneCode);
	
	List<OrgLocationDto> getOrgLocationByBu(String buCode);
	
	List<Db2AgentDto> getListAgent(String agentCode, String orgCode);
	
	List<IncomeDto> getIncomeNotiInfo();
	
	AcceptanceCertificationGaInformationDto getAcceptanceCertification(String agentCode, String officeCode, String period);

	AgentTaxBankInfoDto getAgentTaxBankInfo(String agentCode, String cutOffDateYYYYMM);

	List<NotifeeDto> getNotifeeInfo();
	
	List<NotifyBaseDto> getNotificationsByProcedureName(String procedureName);

	AgentTaxBankInfoDto getTaxBusinessHouseHoldByAgentCode(String agentCode);
	
	GADOfficeDto getOfficeInActive();

	List<PartnerDto> getListPartnerByChannel(String channel);
	
	List<String> getGroupNameForAD(String agentCode);
	
	List<AgentInfoDb2> getGroupAgentDocument(String agentCode, String proposalNo, String policyKey);
	String getPartnerByAgentCode(String agentCode);
	AgentInfoSaleSopDto getAgentInfoSaleSop(String agentCode);
	
	List<SelectItem> getListBanks();
	
	List<SelectItem> getListBankBranchs(String bankGroup);
	
	AgentBankDto getAgentBankInfo(String agentCode);
	
	String getBankCode(String bankName, String bankBranchName);

	List<ConfirmDecreeDto> getDecreeOfAgentTerminate();

	AgentAllowExportTaxCommitmentDto getAgentInfoTaxCommitment(String agentCode);
	
	boolean checkDocumentStatus(String agentCode);
	
	boolean checkDupBankAccountNumber(String agentCode, String bankAccountNumber);
	
	String getOfficeName(String agentCode);
	
	void callStoredb2GetListAgentByLeader(String storeGetListAgentByLeader, TrainingTraineeDB2Param trainingTraineeDB2Param);
    void callStoreDb2GetListOffice(String storeName, OfficeParamDto paramDto);
    public AgentInfoDb2 getBdByOffice(String officeCode);
    public List<String> getListOfficeByBd(String agentCode);

    boolean checkBDTHRole(String agentCode);
	RoleDocumentDto getRoleDocument(String agentCode);
}
