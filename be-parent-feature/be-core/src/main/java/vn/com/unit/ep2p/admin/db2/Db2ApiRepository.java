package vn.com.unit.ep2p.admin.db2;

import java.util.Date;
import java.util.List;

import org.springframework.data.repository.query.Param;

import vn.com.unit.common.dto.AaGaOfficeDto;
import vn.com.unit.common.dto.PartnerDto;
import vn.com.unit.common.dto.Select2Dto;
import vn.com.unit.common.dto.SelectItem;
import vn.com.unit.db.repository.DbRepository;
import vn.com.unit.ep2p.admin.dto.ADPDeviceTokenDto;
import vn.com.unit.ep2p.admin.dto.AcceptanceCertificationGaInformationDto;
import vn.com.unit.ep2p.admin.dto.AgentContactInfoDto;
import vn.com.unit.ep2p.admin.dto.AgentInfoDb2;
import vn.com.unit.ep2p.admin.dto.AgentInfoDto;
import vn.com.unit.ep2p.admin.dto.AgentTaxBankInfoDto;
import vn.com.unit.ep2p.admin.dto.ConfirmDecreeDto;
import vn.com.unit.ep2p.admin.dto.CustomerInformationDetailDto;
import vn.com.unit.ep2p.admin.dto.Db2AgentDto;
import vn.com.unit.ep2p.admin.dto.AgentAllowExportTaxCommitmentDto;
import vn.com.unit.ep2p.admin.dto.Db2SummaryDto;
import vn.com.unit.ep2p.admin.dto.GADOfficeDto;
import vn.com.unit.ep2p.admin.dto.OfficeDto;
import vn.com.unit.ep2p.admin.dto.OrgLocationDto;
import vn.com.unit.ep2p.admin.dto.PolicyInfoDto;
import vn.com.unit.ep2p.admin.dto.PolicyMaturedInfoDto;
import vn.com.unit.ep2p.admin.dto.TotalBdByAgentCode;
import vn.com.unit.ep2p.admin.dto.TotalInsuranceDocDto;
import vn.com.unit.ep2p.admin.dto.ContractBusinessHistoryDto;

public interface Db2ApiRepository extends DbRepository<Db2AgentDto, Long> {
	public Db2AgentDto getAgentInfoByCondition(@Param("agentCode") String agentCode);

	int getDisciplineLevel(@Param("agentCode") String username);

	List<Select2Dto> getListOfficeDb2(@Param("province") String province);

	int countGadByAgentCode(@Param("agentCode") String agentCode);

	int countCustomerBirthdayEvent(@Param("date") Date date, @Param("agentCode") String user);

	List<Select2Dto> getListTerritory(@Param("agentCode") String agentCode);

	List<Select2Dto> getListRegionByCondition(@Param("territory") String territory,
			@Param("agentCode") String agentCode);

	List<Select2Dto> getListAreaByCondition(@Param("region") String region, @Param("agentCode") String agentCode,
			@Param("territory") String territory);

	List<Select2Dto> getListEventOfficeByCondition(@Param("area") String area, @Param("agentCode") String agentCode,
			@Param("region") String region, @Param("territory") String territory);

	List<Select2Dto> findCity();

	List<Select2Dto> findDistrictByCity(@Param("zipCode") String zipCode, @Param("district") String district);

	List<Select2Dto> getListEventPosition();

	List<Db2AgentDto> getBdohInfoByOrgId(@Param("orgId") String office);

	List<Select2Dto> findAllProvinceOds();

	int countCustomerChargeEvent(@Param("date") Date date, @Param("agentCode") String user);

	public List<AgentInfoDb2> getParentByAgentCode(@Param("agentCode") String agentCode,
			@Param("agentGroup") String agentGroup, @Param("orgId") String orgId);

	public AgentInfoDb2 getParentByAgentCodeDetail(@Param("agentCode") String agentCode,
			@Param("agentType") String agentType, @Param("orgId") String orgId);

	int countAgentInfoByEmailPersonal(@Param("email") String emailPersonal);

	int countAgentInfoByEmailDlvn(@Param("email") String emailDlvn);

	Db2AgentDto getAgentClientDetail(@Param("agentCode") String agentCode);

	List<Db2AgentDto> getBirthDayAgent();

	public List<Db2SummaryDto> getAllAgent();

	public Db2SummaryDto findAgentTypeByMemo(String memoNo);

	Db2AgentDto getAgentInfoByConditionPlus(String agentCode);

	List<Db2AgentDto> getAllAgentCodeActive();

	String getAgentTypeByAgentDate(@Param("agentCode") String agentCode, @Param("yyyyMM") String yyyyMM);

	List<Db2AgentDto> getDescription();

	public List<String> getOrdIdByOrdCode(@Param("agentCode") String agentCode);

	List<AaGaOfficeDto> getListOfficeByGadCode(@Param("agentCode") String agentCode, @Param("orgCode") String orgCode, @Param("inActive") String inActive);

	public List<TotalInsuranceDocDto> getTotalInsuranceByAgent(@Param("agentGroupCode") String agentGroupCode);

	int checkAgentTypeIsDfa(@Param("agentType") String agentType);

	public Db2SummaryDto getAgentByAgentCode(@Param("agentCode") String agentCode);

	PolicyInfoDto getDetailContractByPolicyNo(@Param("policyNo") Integer policyNo);

	List<AaGaOfficeDto> getListOfficeByGadCodeForPayment(@Param("agentCode") String agentCode,
			@Param("orgCode") String orgCode, @Param("cutoffDate") String cutoffDate, @Param("payroll") Boolean payroll);

	List<AaGaOfficeDto> getListOfficeByGadCodeForYearlyPayment(@Param("agentCode") String agentCode, @Param("cutoffDate") String year);

	public Db2AgentDto getAgentInfoHist(@Param("agentCode") String agentCode, @Param("dateKey") String dateKey);

	public List<TotalInsuranceDocDto> getTotalPolicyGroup(@Param("orgCode") String orgCode,
			@Param("agentGroup") String agentGroup, @Param("agentCode") String agentCode);

	public List<TotalInsuranceDocDto> getTotalPlicyByAgent(@Param("agentCode") String agentCode,
			@Param("agentGroup") String agentGroup, @Param("orgCode") String orgCode);

	public TotalBdByAgentCode getTotalBd(@Param("agentGroupCode") String agentGroupCode);

	public AgentInfoDb2 getAgentGroupHist(@Param("agentCode")String agentCode, @Param("yyyyMm")String yyyyMm, @Param("orgCode")String orgCode);

	public Integer getAgentStatusByCondition(@Param("agentCode")String username);
	
	public CustomerInformationDetailDto getCustomerInformationDetail(@Param("agentCode") String username, @Param("customerNo") String customerNo);
	
	public String getITrustFlag(@Param("agentCode")String agentCode);
	
	public List<Db2AgentDto> getAgentInfoByCode(@Param("agentCodes") List<String> agentCodes);

	public int checkDiscipline(@Param("agentCode") String agentCode);
	
	public String getPosition(@Param("agentCode") String agentCode);
	
	public List<Db2AgentDto> getAllAgentOfSupport(@Param("agentCode") String agentCode);
	
	public List<Db2AgentDto> getAllAgentOfManager(@Param("agentCode") String agentCode);
	
	public List<Db2AgentDto> getAllAgentOfBD(@Param("agentCode") String agentCode);
	
	public List<Db2AgentDto> getAllAgentOfCAO(@Param("agentCode") String agentCode);
	
	public List<Db2AgentDto> getAllAgentOfLDAP(@Param("agentCode") String agentCode);
	
	public List<OfficeDto> getListOfficeActive();
	
	List<Select2Dto> findWardByDistrict(@Param("district") String district);
	
	public AgentContactInfoDto getContactAndCommonInfo(Long agentCode);
	
	public List<GADOfficeDto> getGADOfficeInfor(@Param("officeCode") String officeCode, @Param("gadCode") String gadCode);
	
	public List<String> getListCodeForOrderNotify(@Param("channel") String channel);
	
	public List<ADPDeviceTokenDto> getDeviceTokenInfo(@Param("agentCodes") List<String> agentCodes);
	public List<ContractBusinessHistoryDto> getBusinessHistory(@Param("policyNo") String policyNo);
	
	public List<OrgLocationDto> getOrgLocationByAgent(@Param("agentCode") String agentCode);
	
	public List<OrgLocationDto> getOrgLocationByZd(@Param("agentCode") String agentCode);
	
	public List<AgentInfoDto> getAgentInfo(@Param("agentCode") String agentCode);
	
	public List<PolicyMaturedInfoDto> getListCodeNotifyPolicyMatured();
	
	public int countChildrenCodeInParent(@Param("parentCode") String parentCode, @Param("childrenCode") String childrenCode);
	
	public List<String> getZdCodeByZoneCode(@Param("zoneCode") String zoneCode);
	
	public List<OrgLocationDto> getOrgLocationByBu(@Param("buCode") String buCode);
	
	public List<Db2AgentDto> getListAgent(@Param("agentCode") String agentCode, @Param("orgCode") String orgCode);
	
	public AcceptanceCertificationGaInformationDto getAcceptanceCertification(@Param("agentCode") String agentCode, @Param("officeCode") String officeCode, @Param("period") String period);

	public AgentTaxBankInfoDto getAgentTaxBankInfo(@Param("agentCode") String agentCode, @Param("cutOffDateYYYYMM") String cutOffDateYYYYMM);
	
	public AgentTaxBankInfoDto getTaxBusinessHouseHoldByAgentCode(@Param("agentCode") String agentCode);
	
	public List<ConfirmDecreeDto> getDecreeOfAgentTerminate();
	
	public GADOfficeDto getOfficeInActive();
	
	public List<PartnerDto> getListPartnerByChannel(@Param("channel") String channel);
	
	public List<String> getGroupNameForAD(@Param("agentCode") String agentCode);
	
	public String getPartnerByAgentCode(@Param("agentCode") String agentCode);

	public AgentAllowExportTaxCommitmentDto getAgentInfoTaxCommitment(@Param("agentCode") String agentCode);
	
	public int checkDocumentStatus(@Param("agentCode") String agentCode);
	
	public List<SelectItem> getListBanks();
	public List<SelectItem> getListBankBranchs(@Param("bankGroup") String bankGroup);
	public String getBankCode(@Param("bankName") String bankName, @Param("bankBranchName") String bankBranchName);
	public int checkDupBankAccountNumber(@Param("agentCode") String agentCode, @Param("bankAccountNumber") String bankAccountNumber);
	
	public String getOfficeName(@Param("agentCode") String agentCode);
	
	public AgentInfoDb2 getBdByOffice(@Param("officeCode") String officeCode);
    public List<String> getListOfficeByBd(@Param("agentCode") String agentCode);

    public int checkBDTHRole(@Param("agentCode") String agentCode);
}
