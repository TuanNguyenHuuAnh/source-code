package vn.com.unit.ep2p.admin.service.impl;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import vn.com.unit.common.dto.AaGaOfficeDto;
import vn.com.unit.common.dto.PartnerDto;
import vn.com.unit.common.dto.Select2Dto;
import vn.com.unit.common.dto.SelectItem;
import vn.com.unit.db.service.SqlManagerDb2Service;
import vn.com.unit.ep2p.admin.db2.Db2ApiRepository;
import vn.com.unit.ep2p.admin.dto.ADPDeviceTokenDto;
import vn.com.unit.ep2p.admin.dto.AcceptanceCertificationGaInformationDto;
import vn.com.unit.ep2p.admin.dto.AgentContactInfoDto;
import vn.com.unit.ep2p.admin.dto.AgentGroupParamDto;
import vn.com.unit.ep2p.admin.dto.AgentInfoDb2;
import vn.com.unit.ep2p.admin.dto.AgentInfoDto;
import vn.com.unit.ep2p.admin.dto.AgentInfoSaleSopDto;
import vn.com.unit.ep2p.admin.dto.AgentTaxBankInfoDto;
import vn.com.unit.ep2p.admin.dto.AgentBankDto;
import vn.com.unit.ep2p.admin.dto.AgentBankInfoParamDto;
import vn.com.unit.ep2p.admin.dto.ConfirmDecreeDto;
import vn.com.unit.ep2p.admin.dto.AgentAllowExportTaxCommitmentDto;
import vn.com.unit.ep2p.admin.dto.ContractBusinessHistoryDto;
import vn.com.unit.ep2p.admin.dto.CustomerBirthdayInMonthParam;
import vn.com.unit.ep2p.admin.dto.CustomerBirthdayParam;
import vn.com.unit.ep2p.admin.dto.CustomerChargeInMonthParam;
import vn.com.unit.ep2p.admin.dto.CustomerChargeParam;
import vn.com.unit.ep2p.admin.dto.CustomerDetailParam;
import vn.com.unit.ep2p.admin.dto.CustomerInformationDetailDto;
import vn.com.unit.ep2p.admin.dto.Db2AdpUserInfoParamDto;
import vn.com.unit.ep2p.admin.dto.Db2AgentDto;
import vn.com.unit.ep2p.admin.dto.Db2AgentInformationParamDto;
import vn.com.unit.ep2p.admin.dto.Db2AgentNotifyParamDto;
import vn.com.unit.ep2p.admin.dto.Db2ContestSummaryParamDto;
import vn.com.unit.ep2p.admin.dto.Db2OrgLocationInfoParamDto;
import vn.com.unit.ep2p.admin.dto.Db2SummaryDto;
import vn.com.unit.ep2p.admin.dto.GADOfficeDto;
import vn.com.unit.ep2p.admin.dto.IncomeDto;
import vn.com.unit.ep2p.admin.dto.IncomeParamDto;
import vn.com.unit.ep2p.admin.dto.NotifeeDto;
import vn.com.unit.ep2p.admin.dto.NotifeeParamDto;
import vn.com.unit.ep2p.admin.dto.NotifyBaseDto;
import vn.com.unit.ep2p.admin.dto.NotifyBaseParamDto;
import vn.com.unit.ep2p.admin.dto.OfficeDto;
import vn.com.unit.ep2p.admin.dto.OfficeParamDto;
import vn.com.unit.ep2p.admin.dto.OrgLocationDto;
import vn.com.unit.ep2p.admin.dto.PolicyChangedDto;
import vn.com.unit.ep2p.admin.dto.PolicyChangedParamDto;
import vn.com.unit.ep2p.admin.dto.PolicyInfoDto;
import vn.com.unit.ep2p.admin.dto.PolicyMaturedInfoDto;
import vn.com.unit.ep2p.admin.dto.AgentInfoSaleSopParamDto;
import vn.com.unit.ep2p.admin.dto.TotalBdByAgentCode;
import vn.com.unit.ep2p.admin.dto.TotalInsuranceDocDto;
import vn.com.unit.ep2p.admin.dto.TrainingTraineeDB2Param;
import vn.com.unit.ep2p.admin.dto.RoleDocumentDto;
import vn.com.unit.ep2p.admin.dto.RoleDocumentParamDto;
import vn.com.unit.ep2p.admin.service.Db2ApiService;
import vn.com.unit.ep2p.utils.StreamUtils;

@Service
@Transactional(transactionManager = "transactionManagerDb2", readOnly = true, rollbackFor = Exception.class)
public class Db2ApiServiceImpl implements Db2ApiService {
    @Autowired
    private Db2ApiRepository db2ApiRepository;

    @Autowired
    @Qualifier("sqlManageDb2Service")
    private SqlManagerDb2Service sqlManagerDb2Service;

    @Override
    public Db2AgentDto getAgentInfoByCondition(String agentCode) {
        return db2ApiRepository.getAgentInfoByCondition(agentCode);
    }

    @Override
    public int getAgentDiscipline(String username) {
        return db2ApiRepository.getDisciplineLevel(username);
    }

    @Override
    public List<Select2Dto> getListOfficeDb2(String province) {
        return db2ApiRepository.getListOfficeDb2(province);
    }

    @Override
    public boolean checkAgentIsGad(String agentCode) {
        return db2ApiRepository.countGadByAgentCode(agentCode) > 0;
    }

    @Override
    public int countCustomerBirthdayEvent(Date date, String user) {
        return db2ApiRepository.countCustomerBirthdayEvent(date, user);
    }

    @Override
    public List<Select2Dto> getListTerritory(String agentCode) {
        return db2ApiRepository.getListTerritory(agentCode);
    }

    @Override
    public List<Select2Dto> getListRegion(String territory, String agentCode) {
        if (!StringUtils.isBlank(territory))
            territory = ",".concat(territory).concat(",");
        return db2ApiRepository.getListRegionByCondition(territory, agentCode);
    }

    @Override
    public List<Select2Dto> getListArea(String region, String agentCode, String territory) {
        if (!StringUtils.isBlank(region))
            region = ",".concat(region).concat(",");
        if (!StringUtils.isBlank(territory))
            territory = ",".concat(territory).concat(",");
        return db2ApiRepository.getListAreaByCondition(region, agentCode, territory);
    }

    @Override
    public List<Select2Dto> getListEventOffice(String area, String agentCode, String region, String territory) {
        if (!StringUtils.isBlank(area))
            area = ",".concat(area).concat(",");
        if (!StringUtils.isBlank(region))
            region = ",".concat(region).concat(",");
        if (!StringUtils.isBlank(territory))
            territory = ",".concat(territory).concat(",");
        return db2ApiRepository.getListEventOfficeByCondition(area, agentCode, region, territory);
    }

    @Override
    public List<Select2Dto> getListEventPosition() {
        return db2ApiRepository.getListEventPosition();
    }

    @Override
    public List<Select2Dto> getCity() {
        return db2ApiRepository.findCity();
    }

    @Override
    public List<Select2Dto> getDistrictByCity(String zipCode, String district) {
        return db2ApiRepository.findDistrictByCity(zipCode, district);
    }

    @Override
    public void callStoreDb2(String store, Db2AgentNotifyParamDto db2AgentNotifyParamDto) {
        sqlManagerDb2Service.call(store, db2AgentNotifyParamDto);
    }

    @Override
    public void callStoredb2Birthday(String storeCustomerBirthday, CustomerBirthdayParam customerBirthdayParam) {
        sqlManagerDb2Service.call(storeCustomerBirthday, customerBirthdayParam);
    }

    @Override
    public void callStoreCustomerCharge(String storeCustomerCharge, CustomerChargeParam customerChargeParam) {
        sqlManagerDb2Service.call(storeCustomerCharge, customerChargeParam);
    }

    @Override
    public List<Db2AgentDto> getBdohInfoByOrgId(String office) {
        return db2ApiRepository.getBdohInfoByOrgId(office);
    }

    @Override
    public int countCustomerChargeEvent(Date date, String user) {
        return db2ApiRepository.countCustomerChargeEvent(date, user);
    }

    @Override
    public AgentInfoDb2 getParentByAgentCode(String agentCode, String agentType, String orgCode) {
        AgentInfoDb2 response = null;
        if ("FC".equalsIgnoreCase(agentType) || "UM".equalsIgnoreCase(agentType)) {
            String parentGroup = agentType;
            parentGroup = "UM".equalsIgnoreCase(agentType) ? "BM" : "UM";
            response = db2ApiRepository.getParentByAgentCodeDetail(agentCode, parentGroup, orgCode);
        }
        if (ObjectUtils.isEmpty(response)) {
            if (!StringUtils.equalsIgnoreCase("GA", agentType) && !StringUtils.equalsIgnoreCase("SO", agentType)) {
                orgCode = "";
            }
            List<AgentInfoDb2> data = db2ApiRepository.getParentByAgentCode(agentCode, agentType, orgCode);
            if (data != null && !data.isEmpty()) {
                List<AgentInfoDb2> lstResult = data.stream().filter(StreamUtils.distinctByKey(AgentInfoDb2::getOrgId)).collect(Collectors.toList());
                List<String> lstOrg = new ArrayList<>();
                String orgName = lstResult.stream().distinct().map(AgentInfoDb2::getOrgName).collect(Collectors.joining(","));
                response = lstResult.get(0);
                response.setOrgName(orgName);
                String orgParentName = lstResult.stream().distinct().map(AgentInfoDb2::getOrgParentName).collect(Collectors.joining(","));
                response.setOrgParentName(orgParentName);
                List<AgentInfoDb2> lstOrgDistinct = data.stream().filter(StreamUtils.distinctByKey(AgentInfoDb2::getOrgId)).collect(Collectors.toList());
                lstOrgDistinct.forEach(x -> {
                    String org = x.getOrgId() + " - " + x.getOrgNameFirst();
                    lstOrg.add(org);
                });

                response.setOrg(String.join(",", lstOrg));
            }
        }
        return response;
    }

    @Override
    public void callStoreinfomationAgent(String storeInformation,
                                         Db2AgentInformationParamDto db2AgentInformationParamDto) {
        sqlManagerDb2Service.call(storeInformation, db2AgentInformationParamDto);

    }

    @Override
    public boolean checkValidEmailPersonal(String emailPersonal) {
        return db2ApiRepository.countAgentInfoByEmailPersonal(emailPersonal) > 0;
    }

    @Override
    public boolean checkValidEmailDlvn(String emailDlvn) {
        return db2ApiRepository.countAgentInfoByEmailDlvn(emailDlvn) > 0;
    }

    @Override
    public Db2AgentDto getAgentClientDetail(String agentCode) {
        return db2ApiRepository.getAgentClientDetail(agentCode);
    }

    @Override
    public void callStoredBirthdayInMonth(String storeCustomerBirthdayD2d, CustomerBirthdayInMonthParam customerBirthdayParam) {
        sqlManagerDb2Service.call(storeCustomerBirthdayD2d, customerBirthdayParam);
    }

    @Override
    public void callStoreCustomerChargeInMonth(String storeCustomerChargeD2d, CustomerChargeInMonthParam customerChargeInMonthParam) {
        sqlManagerDb2Service.call(storeCustomerChargeD2d, customerChargeInMonthParam);
    }

    @Override
    public List<Db2AgentDto> getDobAgent() {
        return db2ApiRepository.getBirthDayAgent();
    }

    @Override
    public List<Db2SummaryDto> getAllAgent() {
        return db2ApiRepository.getAllAgent();
    }

    @Override
    public Db2SummaryDto findAllAgentType(String memoNo) {
        return db2ApiRepository.findAgentTypeByMemo(memoNo);

    }

    @Override
    public void callStoreAgentDetailDb2(String storeName, Db2ContestSummaryParamDto param) {
        sqlManagerDb2Service.call(storeName, param);

    }

    @Override
    public Db2AgentDto getAgentInfoByConditionPlus(String agent) {
        return db2ApiRepository.getAgentInfoByConditionPlus(agent);

    }

    @Override
    public List<Db2AgentDto> getAllAgentCode() {
        return db2ApiRepository.getAllAgentCodeActive();
    }

    @Override
    public String getAgentTypeByAgentDate(String agentCode, String yyyyMM) throws ParseException {
        return db2ApiRepository.getAgentTypeByAgentDate(agentCode, yyyyMM);
    }

    @Override
    public List<Db2AgentDto> getDescription() {
        return db2ApiRepository.getDescription();
    }

    @Override
    public List<AaGaOfficeDto> getListOfficeByGad(String agentCode, String orgCode, String inActive) {
        List<AaGaOfficeDto> list = db2ApiRepository.getListOfficeByGadCode(agentCode, orgCode, inActive);
        return list;
    }

    @Override
    public List<TotalInsuranceDocDto> getTotalInsuranceByAgent(String agentCode, String agentGroup, String orgCode) {
        List<TotalInsuranceDocDto> policy;
        String agentGroupCode = ";" + orgCode + ";" + agentGroup + ";" + agentCode;
        List<TotalInsuranceDocDto> list = db2ApiRepository.getTotalInsuranceByAgent(agentGroupCode);
        if (StringUtils.equalsIgnoreCase("FC", agentGroup)) {
            policy = db2ApiRepository.getTotalPlicyByAgent(agentCode, agentGroup, orgCode);
        } else {
            policy = db2ApiRepository.getTotalPolicyGroup("", agentGroup, agentCode);
        }
        list.addAll(policy);
        return list;
    }

    @Override
    public boolean checkAgentTypeIsDfa(String agentType) {
        return db2ApiRepository.checkAgentTypeIsDfa(agentType) > 0;
    }

    @Override
    public PolicyInfoDto getDetailContract(Integer policyNo) {
        return db2ApiRepository.getDetailContractByPolicyNo(policyNo);
    }

    @Override
    public Db2SummaryDto getAgentByAgentCode(String agentCode) {
        return db2ApiRepository.getAgentByAgentCode(agentCode);
    }

    @Override
    public List<AaGaOfficeDto> getListOfficeByGadForPayment(String agentCode, String orgCode, String cutoffDate, Boolean payroll) {
        List<AaGaOfficeDto> list = db2ApiRepository.getListOfficeByGadCodeForPayment(agentCode, orgCode, cutoffDate, payroll);
        return list;
    }

    @Override
    public List<AaGaOfficeDto> getListOfficeByGadForYearlyPayment(String agentCode, String year) {
        List<AaGaOfficeDto> list = db2ApiRepository.getListOfficeByGadCodeForYearlyPayment(agentCode, year);
        return list;
    }

    @Override
    public Db2AgentDto getAgentInfoHist(String agentCode, String dateKey) {
        return db2ApiRepository.getAgentInfoHist(agentCode, dateKey);
    }


    @Override
    public TotalBdByAgentCode getTotalBd(String agentCode, String agentGroup, String orgCode) {
        if (!StringUtils.equalsIgnoreCase("SO", orgCode) && !StringUtils.equalsIgnoreCase("GA", orgCode)) {
            orgCode = "";
        }
        String agentGroupCode = orgCode.concat(";" + agentGroup).concat(";" + agentCode);
        return db2ApiRepository.getTotalBd(agentGroupCode);
    }

    @Override
    public AgentInfoDb2 getAgentGroupHist(String agentCode, String yyyyMm, String orgCode) {
        return db2ApiRepository.getAgentGroupHist(agentCode, yyyyMm, orgCode);
    }

    @Override
    public Integer getAgentStatusByCondition(String username) {
        return db2ApiRepository.getAgentStatusByCondition(username);
    }

    @Override
    public CustomerInformationDetailDto getCustomerInformationDetail(String agentCode, String customerNo) {
    	CustomerDetailParam param = new CustomerDetailParam();
    	param.agentCode = agentCode;
    	param.customerNo = customerNo;
    	sqlManagerDb2Service.call("RPT_ODS.DS_SP_GET_CUSTOMERS_DETAIL", param);
    	if (param.lstData != null && param.lstData.size() > 0) {
    		return param.lstData.get(0);	
    	}
    	return null;
    }

    @Override
    public String getITrustFlag(String agentCode) {
        return db2ApiRepository.getITrustFlag(agentCode);
    }

    @Override
    public List<Db2AgentDto> getAgentInfoByCode(List<String> agentCodes) {
        return db2ApiRepository.getAgentInfoByCode(agentCodes);
    }

    @Override
    public boolean checkDiscipline(String agentCode) {
        return db2ApiRepository.checkDiscipline(agentCode) > 0;
    }

    @Override
    public String getPosition(String agentCode) {
        return db2ApiRepository.getPosition(agentCode);
    }

    @Override
    public List<Db2AgentDto> getAllAgentOfSupport(String agentCode) {
        return db2ApiRepository.getAllAgentOfSupport(agentCode);
    }

    @Override
    public List<Db2AgentDto> getAllAgentOfManager(String agentCode) {
        return db2ApiRepository.getAllAgentOfManager(agentCode);
    }

    @Override
    public List<Db2AgentDto> getAllAgentOfBD(String agentCode) {
        return db2ApiRepository.getAllAgentOfBD(agentCode);
    }

    @Override
    public List<Db2AgentDto> getAllAgentOfCAO(String agentCode) {
        return db2ApiRepository.getAllAgentOfCAO(agentCode);
    }

    @Override
    public List<Db2AgentDto> getAllAgentOfLDAP(String agentCode) {
        return db2ApiRepository.getAllAgentOfLDAP(agentCode);
    }

    @Override
    public List<OfficeDto> getListOfficeActive() {
        return db2ApiRepository.getListOfficeActive();
    }

    public List<Select2Dto> getWardByDistrict(String district) {
        return db2ApiRepository.findWardByDistrict(district);
    }

    @Override
    public AgentContactInfoDto getContactAndCommonInfo(Long agentCode) {
        return db2ApiRepository.getContactAndCommonInfo(agentCode);
    }


    @Override
    public void callStoreAdpUserDetailDb2(String storeName, Db2AdpUserInfoParamDto param) {
        sqlManagerDb2Service.call(storeName, param);
    }

    @Override
    public List<ADPDeviceTokenDto> getDeviceTokenInfo(List<String> agentCodes) {
        return db2ApiRepository.getDeviceTokenInfo(agentCodes);
    }

    @Override
    public List<OrgLocationDto> getOrgLocationByAgent(String agentCode) {
        return db2ApiRepository.getOrgLocationByAgent(agentCode);
    }

    @Override
    public List<OrgLocationDto> getOrgLocationByZd(String agentCode) {
        return db2ApiRepository.getOrgLocationByZd(agentCode);
    }

    @Override
    public List<GADOfficeDto> getGADOfficeInformation(String officeCode, String gadCode) {
        return db2ApiRepository.getGADOfficeInfor(officeCode, gadCode);
    }

    @Override
    public List<String> getListCodeForOrderNotify(String channel) {
        return db2ApiRepository.getListCodeForOrderNotify(channel);
    }

    @Override
    public List<PolicyChangedDto> getPolicyChanged() {
        PolicyChangedParamDto param = new PolicyChangedParamDto();
        sqlManagerDb2Service.call("RPT_ODS.ADP_SP_GET_INFO_NOTIFICATION_POLICY", param);
        return param.lstData;
    }

    @Override
    public List<AgentInfoDto> getAgentInfo(String agentCode) {
        return db2ApiRepository.getAgentInfo(agentCode);
    }

    @Override
    public List<OrgLocationDto> getOrgLocationInfo(String agentCode, String agentType, String partnerCode) {
        List<OrgLocationDto> resultLst = new ArrayList<>();
        Db2OrgLocationInfoParamDto param = new Db2OrgLocationInfoParamDto();
        param.agentCode = agentCode;
        param.agentType = agentType;
        param.partnerCode = partnerCode;

        // Call store to DBs
        try {
            sqlManagerDb2Service.call("RPT_ODS.DS_SAM_SP_FILTER_HIERARCHY", param);
        } catch (Exception e) {
            e.printStackTrace();
        }

        resultLst = param.resultLst;
        return resultLst;
    }

    @Override
    public List<ContractBusinessHistoryDto> getBusinessHistory(String policyNo) {
        return db2ApiRepository.getBusinessHistory(policyNo);
    }

    @Override
    public List<PolicyMaturedInfoDto> getListCodeNotifyPolicyMatured() {
        return db2ApiRepository.getListCodeNotifyPolicyMatured();
    }

    @Override
    public boolean checkChildrenInParent(String parentCode, String childrenCode) {
        if (parentCode.equals(childrenCode)) {
            return true;
        }
        return db2ApiRepository.countChildrenCodeInParent(parentCode, childrenCode) > 0;
    }

    @Override
    public List<String> getZdCodeByZoneCode(String zoneCode) {
        return db2ApiRepository.getZdCodeByZoneCode(zoneCode);
    }

    @Override
    public List<OrgLocationDto> getOrgLocationByBu(String buCode) {
        return db2ApiRepository.getOrgLocationByBu(buCode);
    }

    @Override
    public List<Db2AgentDto> getListAgent(String agentCode, String orgCode) {
        return db2ApiRepository.getListAgent(agentCode, orgCode);
    }

    @Override
    public List<IncomeDto> getIncomeNotiInfo() {
        IncomeParamDto param = new IncomeParamDto();
        sqlManagerDb2Service.call("RPT_ODS.DS_SP_GET_INFO_NOTIFICATION_INCOME", param);
        return param.lstData;
    }

    @Override
    public List<NotifeeDto> getNotifeeInfo() {
        NotifeeParamDto param = new NotifeeParamDto();
        sqlManagerDb2Service.call("RPT_ODS.DS_SP_GET_INFO_NOTIFICATION_FEE", param);
        return param.lstData;
    }

    @Override
    public AcceptanceCertificationGaInformationDto getAcceptanceCertification(String agentCode, String officeCode, String period) {
        return db2ApiRepository.getAcceptanceCertification(agentCode, officeCode, period);
    }

    @Override
    public AgentTaxBankInfoDto getAgentTaxBankInfo(String agentCode, String cutOffDateYYYYMM) {
        return db2ApiRepository.getAgentTaxBankInfo(agentCode, cutOffDateYYYYMM);
    }

    @Override
    public AgentTaxBankInfoDto getTaxBusinessHouseHoldByAgentCode(String agentCode) {
        return db2ApiRepository.getTaxBusinessHouseHoldByAgentCode(agentCode);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<NotifyBaseDto> getNotificationsByProcedureName(String procedureName) {
        NotifyBaseParamDto param = new NotifyBaseParamDto();
        sqlManagerDb2Service.call(procedureName, param);
        return param.lstData;
    }
	
	@Override
	public GADOfficeDto getOfficeInActive() {
		return db2ApiRepository.getOfficeInActive();
	}

    @Override
    public List<ConfirmDecreeDto> getDecreeOfAgentTerminate() {
        return db2ApiRepository.getDecreeOfAgentTerminate();
    }

    @Override
    public List<PartnerDto> getListPartnerByChannel(String channel) {
        return db2ApiRepository.getListPartnerByChannel(channel);
    }

    @Override
    public List<String> getGroupNameForAD(String agentCode) {
        return db2ApiRepository.getGroupNameForAD(agentCode);
    }

    @Override
    public List<AgentInfoDb2> getGroupAgentDocument(String agentCode, String proposalNo, String policyKey) {
        AgentGroupParamDto param = new AgentGroupParamDto();
        param.agentCode = agentCode;
        param.proposalNo = proposalNo;
        param.policyKey = policyKey;
        sqlManagerDb2Service.call("RPT_ODS.DS_SP_GET_GROUP_AGENT_DOCUMENT", param);
        return param.lstData;
    }
    
    @Override
    public String getPartnerByAgentCode(String agentCode) {
        return db2ApiRepository.getPartnerByAgentCode(agentCode);
    }
    
    @Override
    public AgentInfoSaleSopDto getAgentInfoSaleSop(String agentCode) {
        AgentInfoSaleSopParamDto param = new AgentInfoSaleSopParamDto();
        param.agentCode = agentCode;
        sqlManagerDb2Service.call("RPT_ODS.AD_SP_GET_AGENT_INFO_SALE_SOP", param);
        if (param.datas != null && !param.datas.isEmpty()) {
        	return param.datas.get(0);
        }
        return null;
    }

     @Override
    public AgentAllowExportTaxCommitmentDto getAgentInfoTaxCommitment(String agentCode) {
        return db2ApiRepository.getAgentInfoTaxCommitment(agentCode);
    }
    
    @Override
    public boolean checkDocumentStatus(String agentCode) {
        return db2ApiRepository.checkDocumentStatus(agentCode) > 0;
    }

    @Override
	public List<SelectItem> getListBanks(){
		return db2ApiRepository.getListBanks();
	}
	
	@Override
	public List<SelectItem> getListBankBranchs(String bankGroup){
		return db2ApiRepository.getListBankBranchs(bankGroup);
	}
	
	@Override
	public AgentBankDto getAgentBankInfo(String agentCode){
		AgentBankInfoParamDto param = new AgentBankInfoParamDto();
        param.agentCode = agentCode;
        sqlManagerDb2Service.call("RPT_ODS.DS_SP_GET_AGENT_BANK_INFO", param);
        if (param.datas != null && !param.datas.isEmpty()) {
        	return param.datas.get(0);
        }
        return null;
	}

	@Override
	public String getBankCode(String bankName, String bankBranchName) {
		return db2ApiRepository.getBankCode(bankName, bankBranchName);
	}
	
	@Override
    public boolean checkDupBankAccountNumber(String agentCode, String bankAccountNumber) {
        return db2ApiRepository.checkDupBankAccountNumber(agentCode, bankAccountNumber) > 0;
    }
	
	@Override
    public boolean checkBDTHRole(String agentCode) {
        return db2ApiRepository.checkBDTHRole(agentCode) > 0;
    }

    @Override
	public String getOfficeName(String agentCode) {
		return db2ApiRepository.getOfficeName(agentCode);
	}
	
	@Override
    public void callStoredb2GetListAgentByLeader(String storeGetListAgentByLeader, TrainingTraineeDB2Param trainingTraineeDB2Param) {
        sqlManagerDb2Service.call(storeGetListAgentByLeader, trainingTraineeDB2Param);
    }
    
    @Override
    public void callStoreDb2GetListOffice(String storeName, OfficeParamDto paramDto) {
        sqlManagerDb2Service.call(storeName, paramDto);
    }
    
    @Override
    public AgentInfoDb2 getBdByOffice(String agentCode) {
    	return db2ApiRepository.getBdByOffice(agentCode);
    }
    
    @Override
    public List<String> getListOfficeByBd(String agentCode) {
    	return db2ApiRepository.getListOfficeByBd(agentCode);
    }
	
	public RoleDocumentDto getRoleDocument(String agentCode) {
        RoleDocumentParamDto param = new RoleDocumentParamDto();
        param.agentCode = agentCode;
        sqlManagerDb2Service.call("RPT_ODS.AD_SP_GET_ROLE_DOCUMENT", param);
        if (param.datas != null && !param.datas.isEmpty()) {
            return param.datas.get(0);
        }
        return null;
    }
}
