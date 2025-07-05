package vn.com.unit.ep2p.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.ServletContext;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import jp.sf.amateras.mirage.provider.ConnectionProvider;
import vn.com.unit.cms.core.dto.CmsCommonPagination;
import vn.com.unit.cms.core.dto.CommonSearchWithPagingDto;
import vn.com.unit.cms.core.module.customerManagement.dto.ClaimGroupDto;
import vn.com.unit.cms.core.module.customerManagement.dto.ClaimGroupParam;
import vn.com.unit.cms.core.module.customerManagement.dto.ContractBusinessGroupSearchDto;
import vn.com.unit.cms.core.module.customerManagement.dto.ContractBusinessHandleDto;
import vn.com.unit.cms.core.module.customerManagement.dto.ContractBusinessHandleParam;
import vn.com.unit.cms.core.module.customerManagement.dto.ContractClaimGroupSearchDto;
import vn.com.unit.cms.core.module.customerManagement.dto.ContractEffectGroupDto;
import vn.com.unit.cms.core.module.customerManagement.dto.ContractEffectGroupParam;
import vn.com.unit.cms.core.module.customerManagement.dto.ContractEffectGroupSearchDto;
import vn.com.unit.cms.core.module.customerManagement.dto.ContractFeeGroupDetailParam;
import vn.com.unit.cms.core.module.customerManagement.dto.ContractFeeGroupDto;
import vn.com.unit.cms.core.module.customerManagement.dto.ContractFeeGroupParam;
import vn.com.unit.cms.core.module.customerManagement.dto.ContractFeeGroupSearchDto;
import vn.com.unit.cms.core.module.customerManagement.dto.ContractOverdueFeeRypDto;
import vn.com.unit.cms.core.module.customerManagement.dto.ContractOverdueFeeRypParam;
import vn.com.unit.cms.core.module.customerManagement.dto.FinanceSupportStandardDto;
import vn.com.unit.cms.core.module.customerManagement.dto.FinanceSupportStandardParam;
import vn.com.unit.cms.core.module.customerManagement.dto.OfficePolicyClaimDetailDto;
import vn.com.unit.cms.core.module.customerManagement.dto.OfficePolicyClaimParamDto;
import vn.com.unit.cms.core.module.customerManagement.dto.OfficePolicyExpiredDetailDto;
import vn.com.unit.cms.core.module.customerManagement.dto.OfficePolicyExpiredParamDto;
import vn.com.unit.cms.core.module.customerManagement.dto.OfficePolicyOrphanDetailDto;
import vn.com.unit.cms.core.module.customerManagement.dto.OfficePolicyOrphanParamDto;
import vn.com.unit.cms.core.module.customerManagement.dto.PolicyExpiredSearchDto;
import vn.com.unit.cms.core.module.customerManagement.dto.PolicyMaturedDetailDto;
import vn.com.unit.cms.core.module.customerManagement.dto.PolicyMaturedParamDto;
import vn.com.unit.cms.core.module.customerManagement.dto.PolicyMaturedSearchDto;
import vn.com.unit.cms.core.module.customerManagement.dto.PolicyOrphanSearchDto;
import vn.com.unit.cms.core.module.customerManagement.dto.TotalClaimDto;
import vn.com.unit.cms.core.module.customerManagement.dto.TotalClaimGroupDto;
import vn.com.unit.cms.core.module.customerManagement.dto.TotalClaimGroupParamDto;
import vn.com.unit.cms.core.module.customerManagement.dto.TotalClaimParam;
import vn.com.unit.cms.core.module.customerManagement.dto.TotalClaimPersonalParam;
import vn.com.unit.cms.core.module.customerManagement.dto.TotalInsuranceContractGroupDto;
import vn.com.unit.cms.core.module.customerManagement.dto.TotalInsuranceContractGroupParamDto;
import vn.com.unit.cms.core.module.customerManagement.dto.TotalInsuranceContractGroupSearchDto;
import vn.com.unit.cms.core.module.customerManagement.dto.TotalPolicyDto;
import vn.com.unit.cms.core.module.customerManagement.dto.TotalPolicyParam;
import vn.com.unit.cms.core.module.customerManagement.dto.TotalPolicyPersonalParam;
import vn.com.unit.common.utils.CommonCollectionUtil;
import vn.com.unit.core.config.SystemConfig;
import vn.com.unit.db.service.SqlManagerDb2Service;
import vn.com.unit.ep2p.admin.dto.AgentInfoDb2;
import vn.com.unit.ep2p.admin.service.Db2ApiService;
import vn.com.unit.ep2p.core.service.AbstractCommonService;
import vn.com.unit.ep2p.enumdef.ListPolicyClaimAhEnum;
import vn.com.unit.ep2p.enumdef.ListPolicyClaimCaoEnum;
import vn.com.unit.ep2p.enumdef.ListPolicyClaimOhEnum;
import vn.com.unit.ep2p.enumdef.ListPolicyClaimRhEnum;
import vn.com.unit.ep2p.enumdef.ListPolicyClaimThEnum;
import vn.com.unit.ep2p.enumdef.ListPolicyOfficeAhEnum;
import vn.com.unit.ep2p.enumdef.ListPolicyOfficeCaoEnum;
import vn.com.unit.ep2p.enumdef.ListPolicyOfficeOhEnum;
import vn.com.unit.ep2p.enumdef.ListPolicyOfficeRhEnum;
import vn.com.unit.ep2p.enumdef.ListPolicyOfficeThEnum;
import vn.com.unit.ep2p.service.ApiAgentDetailService;
import vn.com.unit.ep2p.service.OfficePolicyService;
import vn.com.unit.ep2p.service.ParseJsonToParamSearchService;
import vn.com.unit.ep2p.service.ReportBusinessResultGaService;
import vn.com.unit.imp.excel.constant.CommonConstant;
import vn.com.unit.imp.excel.dto.ItemColsExcelDto;
import vn.com.unit.imp.excel.utils.DateUtils;
import vn.com.unit.imp.excel.utils.ExportExcelUtil;
import vn.com.unit.imp.excel.utils.ImportExcelUtil;

@Service
@Transactional(transactionManager = "transactionManagerDb2", readOnly = true, rollbackFor = Exception.class)
public class OfficePolicyServiceImpl extends AbstractCommonService implements OfficePolicyService {

	@Autowired
	Db2ApiService db2ApiService;

	
	@Autowired
	@Qualifier("connectionProvider")
	private ConnectionProvider connectionProvider;

	@Autowired
	@Qualifier("sqlManageDb2Service")
	private SqlManagerDb2Service sqlManagerDb2Service;
	
	@Autowired
	private SystemConfig systemConfig;
	
	@Autowired
    private ServletContext servletContext;
	
    @Autowired
    ParseJsonToParamSearchService parseJsonToParamSearchService;
    
    @Autowired
    ApiAgentDetailService apiAgentDetailService;

	@Autowired
	ReportBusinessResultGaService reportBusinessResultGaService;

	private static final String STORE_DETAIL_POLICY_CLAIMS = "RPT_ODS.DS_SP_GET_LIST_CLAIM_CONTRACTS";
	private static final String STORE_DETAIL_POLICY_STANDARD = "RPT_ODS.DS_SP_GET_LIST_LIST_QUALIFIED_FC";
	private static final String STORE_BUSINESS_HANDLE_CONTRACT = "RPT_ODS.DS_SP_GET_LIST_HANDLING_CONTRACTS";
	private static final String STORE_DETAIL_POLICY_EXPIRED = "RPT_ODS.DS_SP_GET_LIST_OF_GROUP_MATURED";
	private static final String STORE_DETAIL_POLICY_ORPHAN = "RPT_ODS.DS_SP_GET_LIST_POLICY_ORPHAN_BY_LEADER";
	private static final String STORE_TOTAL_CONTRACT = "RPT_ODS.DS_SP_GET_LIST_OF_GROUP_INSURANCE_CONTRACT";
	private static final String STORE_OVER_DUE_FEE_RYP = "RPT_ODS.DS_SP_GET_LIST_PAID_TO_DATE_BY_AGENT ";
	private static final String STORE_LIST_POLICY_MATURED_BY_GROUP = "RPT_ODS.DS_SP_GET_LIST_POLICY_MATURED_BY_GROUP";
	private static final String DS_SP_GET_LIST_LEADER_PAID_TO_DATE = "RPT_ODS.DS_SP_GET_LIST_LEADER_PAID_TO_DATE";
	private static final String STORE_FEE_CONTRACT_DETAIL = "RPT_ODS.DS_SP_GET_LIST_PAID_TO_DATE_BY_AGENT ";
	private static final String STORE_GET_TOTAL_POLICY = "RPT_ODS.DS_SP_GET_LIST_OF_GROUP_INSURANCE_CONTRACT";
	private static final String STORE_GET_TOTAL_CLAIM = "RPT_ODS.DS_SP_GET_TOTAL_CLAIM";
	private static final String DS_SP_GET_TOTAL_ACTIVE_INACTIVE_INSURANCE_CONTRACT_PERSONAL = "RPT_ODS.DS_SP_GET_TOTAL_ACTIVE_INACTIVE_INSURANCE_CONTRACT_PERSONAL";
	private static final String DS_SP_GET_TOTAL_HANDING_INSURANCE_CONTRACT_PERSONAL = "RPT_ODS.DS_SP_GET_TOTAL_HANDING_INSURANCE_CONTRACT_PERSONAL";
	private static final String DS_SP_GET_TOTAL_CLAIM_INSURANCE_CONTRACT_PERSONAL = "RPT_ODS.DS_SP_GET_TOTAL_CLAIM_INSURANCE_CONTRACT_PERSONAL";
	private static final String DS_SP_GET_TOTAL_PAID_TODATE_INSURANCE_CONTRACT_PERSONAL = "RPT_ODS.DS_SP_GET_TOTAL_PAID_TODATE_INSURANCE_CONTRACT_PERSONAL";
	private static final String DS_SP_GET_TOTAL_POLICY_MATURED_PERSONAL = "RPT_ODS.DS_SP_GET_TOTAL_POLICY_MATURED_PERSONAL";
	
	private static final String DS_SP_GET_LIST_POLICY_BY_LEADER_SUM_LIST = "RPT_ODS.DS_SP_GET_LIST_POLICY_BY_LEADER_SUM_LIST";
	private static final String DS_SP_GET_LIST_CLAIM_BY_GROUP = "RPT_ODS.DS_SP_GET_LIST_CLAIM_BY_GROUP";
	private static final String DS_SP_GET_LIST_CLAIM_BY_GROUP_EXPORT = "RPT_ODS.DS_SP_GET_LIST_CLAIM_BY_GROUP_EXPORT";
	
	private static final Logger logger = LoggerFactory.getLogger(OfficePolicyServiceImpl.class);
	//expired
	@Override
	public CmsCommonPagination<OfficePolicyExpiredDetailDto> getDetailPolicyExpired(PolicyExpiredSearchDto searchDto) {
	    OfficePolicyExpiredParamDto officePolicyExpiredParamDto = new OfficePolicyExpiredParamDto();
	    CmsCommonPagination<OfficePolicyExpiredDetailDto> rs = new CmsCommonPagination<>();
	    List<OfficePolicyExpiredDetailDto> datas = new ArrayList<>();
	    try {
	    searchDto.setFunctionCode("OFFICE_POLICY_EXPIRED");
	    CommonSearchWithPagingDto common = geranateCommonSearch(searchDto, searchDto.getFunctionCode());
		officePolicyExpiredParamDto.agentCode=searchDto.getAgentCodeSearch();
		officePolicyExpiredParamDto.orgId=searchDto.getOrgId();
		officePolicyExpiredParamDto.agentGroup=searchDto.getAgentGroup();
		officePolicyExpiredParamDto.maturedType=searchDto.getMaturedType();
		officePolicyExpiredParamDto.page=searchDto.getPage();
		officePolicyExpiredParamDto.pageSize=searchDto.getPageSize();
		officePolicyExpiredParamDto.sort=common.getSort();
		officePolicyExpiredParamDto.search=common.getSort();
		sqlManagerDb2Service.call(STORE_DETAIL_POLICY_EXPIRED, officePolicyExpiredParamDto);
		datas = officePolicyExpiredParamDto.lstData;
		for (OfficePolicyExpiredDetailDto dto : datas) {
			if(StringUtils.equalsIgnoreCase(dto.getMaturedType(), "NEXT30")){
				dto.setTotalContractsWillExpired(dto.getTotalContract());
				dto.setTotalAmountPaidEstimate(dto.getTotalAmountPaid());

				dto.setTotalContract(null);
				dto.setTotalAmountPaid(null);
			}

            dto.setAgentAll(agentPosition(dto.getAgentType(), dto.getAgentCode(), dto.getAgentName()));
            dto.setParentAll(agentPosition(dto.getManagerAgentType(), dto.getManagerAgentCode().replace("A", "").replace("B", "").replace("C", ""), dto.getManagerAgentName()));
        }
		rs.setTotalData(officePolicyExpiredParamDto.totalRows);
		rs.setData(datas);
		}
		catch (Exception e) {
		     logger.error("Exception", e);
	    }
		return rs;
	}

	@Override
	public CmsCommonPagination<OfficePolicyOrphanDetailDto> getDetailPolicyOrphan(PolicyOrphanSearchDto searchDto) {
	    OfficePolicyOrphanParamDto officePolicyOrphanParamDto = new OfficePolicyOrphanParamDto();
	    CmsCommonPagination<OfficePolicyOrphanDetailDto> rs = new CmsCommonPagination<>();
	    try {
    	    searchDto.setFunctionCode("OFFICE_POLICY_ORPHAN");
    	    CommonSearchWithPagingDto common = geranateCommonSearch(searchDto, searchDto.getFunctionCode());
    		officePolicyOrphanParamDto.agentCode=searchDto.getAgentCodeSearch();
    		officePolicyOrphanParamDto.agentGroup=searchDto.getAgentGroup();
    		officePolicyOrphanParamDto.page=searchDto.getPage();
    		officePolicyOrphanParamDto.pageSize=searchDto.getPageSize();
    		officePolicyOrphanParamDto.sort=searchDto.getSort();
    		officePolicyOrphanParamDto.search=common.getSearch();
    		sqlManagerDb2Service.call(STORE_DETAIL_POLICY_ORPHAN, officePolicyOrphanParamDto);
    		rs.setTotalData(officePolicyOrphanParamDto.totalRows);
    		rs.setData(officePolicyOrphanParamDto.lstData);
	    } catch (Exception e) {
            logger.error("Exception", e);
        }
		return rs;
	}
	
	//list group contract
	@Override
	public CmsCommonPagination<TotalInsuranceContractGroupDto> getListTotalContractByGroup(TotalInsuranceContractGroupSearchDto searchDto) {
	    TotalInsuranceContractGroupParamDto param = new TotalInsuranceContractGroupParamDto();
	    CmsCommonPagination<TotalInsuranceContractGroupDto> rs = new CmsCommonPagination<>();
	    List<TotalInsuranceContractGroupDto> datas = new ArrayList<>();
	    try {

	        searchDto.setFunctionCode("GROUP_"+searchDto.getAgentGroup());
	        TotalInsuranceContractGroupSearchDto searchBd1 = objectMapper.convertValue(searchDto, TotalInsuranceContractGroupSearchDto.class);
	        TotalInsuranceContractGroupSearchDto searchBd2 = objectMapper.convertValue(searchDto, TotalInsuranceContractGroupSearchDto.class);
	        if (searchDto.getAgentGroup().equals("OH")) {
	        	searchDto.setOrgId("");
	        }
    		param.agentCode=searchDto.getAgentCode();
    		param.orgId= searchDto.getOrgId();
    		if (searchDto.getAgentGroup().equals("SO") || searchDto.getAgentGroup().equals("GA")) {
        		param.agentGroup="OH";
	        } else {
	    		param.agentGroup=searchDto.getAgentGroup();
	        }
    		param.page = searchDto.getPage();
    		param.pageSize=searchDto.getPageSize();
    		param.sort = searchDto.getSort();
			param.search = reportBusinessResultGaService.searchAdvance(searchDto,searchDto.getAgentGroup(),searchDto.getKeyword(),searchBd1,searchBd2);

			sqlManagerDb2Service.call(STORE_TOTAL_CONTRACT, param);
    		datas = param.data;

    		List<TotalInsuranceContractGroupDto> lstBdTh = datas.stream()
					.filter(e->StringUtils.equalsIgnoreCase(e.getParentGroup(), "CAO"))
					.filter(e->!e.getTreeLevel().equals(0))
					.collect(Collectors.toList());

    		if(lstBdTh != null && !lstBdTh.isEmpty()){
				TotalInsuranceContractGroupDto north = lstBdTh.stream()
						.filter(e-> StringUtils.equalsIgnoreCase("North", e.getOrgName()))
						.findFirst().orElse(null);
				TotalInsuranceContractGroupDto central = lstBdTh.stream()
						.filter(e-> StringUtils.equalsIgnoreCase("Central", e.getOrgName()))
						.findFirst().orElse(null);
				TotalInsuranceContractGroupDto south = lstBdTh.stream()
						.filter(e-> StringUtils.equalsIgnoreCase("South", e.getOrgName()))
						.findFirst().orElse(null);
				TotalInsuranceContractGroupDto other = lstBdTh.stream()
						.filter(e-> StringUtils.equalsIgnoreCase("Other Territory", e.getOrgName()))
						.findFirst().orElse(null);
				//remove nhung dong co parent_group = CAO
				datas.removeIf(e->StringUtils.equalsIgnoreCase(e.getParentGroup(), "CAO") && (StringUtils.equalsIgnoreCase("North", e.getOrgName())
						|| StringUtils.equalsIgnoreCase("Central", e.getOrgName())
						|| StringUtils.equalsIgnoreCase("South", e.getOrgName())
						|| StringUtils.equalsIgnoreCase("Other Territory", e.getOrgName())));
				//add lại nhung dong co  parent_group = CAO theo thu tu: north-central-south-other

				int i =0;
				if(ObjectUtils.isNotEmpty(north)){ datas.add( 1+i,north); i+=1;}
				if(ObjectUtils.isNotEmpty(central)){ datas.add(1+i,central);i+=1;}
				if(ObjectUtils.isNotEmpty(south)){ datas.add(1+i,south);i+=1;}
				if(ObjectUtils.isNotEmpty(other)){ datas.add(1+i,other);}
			}
    		// datas.stream().forEach(x-> x.setTotalContractClaim(x.getSumOfPolicyClaim())); // #64044);
    		
    		sumPolicy(datas, 0);
			groupAgent(searchDto, datas);
    		for (TotalInsuranceContractGroupDto dto : datas) {
    		    dto.setAgentAll(agentPosition(dto.getAgentType(), dto.getAgentCode(), dto.getAgentName()));
				mapAgent(dto, searchDto.getAgentGroup(), dto.getTreeLevel() == 0);
            }
			
    		rs.setTotalData(param.totalRows != null ? param.totalRows : 0);
    	    rs.setData(datas);
	    } catch (Exception e) {
            logger.error("Exception", e);
        }
		return rs;
	}
	private void sumPolicy(List<TotalInsuranceContractGroupDto> datas, int treeLevel){
		if(!datas.isEmpty()){
			//List<TotalInsuranceContractGroupDto> lstRoot = datas.stream().filter(e -> e.getTreeLevel().equals(new Integer(0))).collect(Collectors.toList());
			//datas.removeIf(e -> e.getTreeLevel().equals(new Integer(0)));
			List<TotalInsuranceContractGroupDto> lstRoot = datas.stream().filter(e -> e.getTreeLevel().equals(new Integer(treeLevel))).collect(Collectors.toList());
			List<String> adresses = lstRoot.stream()
					.map(TotalInsuranceContractGroupDto::getOrgName)
					.collect(Collectors.toList());
			String orgNameNew = String.join(", ", adresses);
			datas.removeIf(e -> e.getTreeLevel().equals(new Integer(treeLevel)));

			if(!lstRoot.isEmpty()){
				TotalInsuranceContractGroupDto rootTmp = lstRoot.get(0);
				if(treeLevel == 1 && "OH".equalsIgnoreCase(rootTmp.getChildGroup())) {
					rootTmp.setOrgCode(null);
					rootTmp.setOrgId(rootTmp.getAgentCode());
				}
				rootTmp.setOrgNameNew(orgNameNew);
				rootTmp.setTotalContractEffect(lstRoot.stream().map(TotalInsuranceContractGroupDto :: getTotalContractEffect).reduce(new BigDecimal(0), (a, b) -> a.add(b)));
				rootTmp.setTotalContractInvalid(lstRoot.stream().map(TotalInsuranceContractGroupDto :: getTotalContractInvalid).reduce(new BigDecimal(0), (a, b) -> a.add(b)));
				rootTmp.setTotalContractDueFee(lstRoot.stream().map(TotalInsuranceContractGroupDto :: getTotalContractDueFee).reduce(new BigDecimal(0), (a, b) -> a.add(b)));
				rootTmp.setTotalContractOverdueFeeRyp(lstRoot.stream().map(TotalInsuranceContractGroupDto :: getTotalContractOverdueFeeRyp).reduce(new BigDecimal(0), (a, b) -> a.add(b)));
				// rootTmp.setTotalContractExpired(lstRoot.stream().map(TotalInsuranceContractGroupDto :: getTotalContractExpired).reduce(new BigDecimal(0), (a, b) -> a.add(b)));
				rootTmp.setTotalContractExpired30Days(lstRoot.stream().map(TotalInsuranceContractGroupDto :: getTotalContractExpired30Days).reduce(new BigDecimal(0), (a, b) -> a.add(b)));
				rootTmp.setTotalContractOrphan(lstRoot.stream().map(TotalInsuranceContractGroupDto :: getTotalContractOrphan).reduce(new BigDecimal(0), (a, b) -> a.add(b)));
				// rootTmp.setTotalContractClaim(lstRoot.stream().map(TotalInsuranceContractGroupDto :: getSumOfPolicyClaim).reduce(new BigDecimal(0), (a, b) -> a.add(b)));
				rootTmp.setTotalPolicyMatured(lstRoot.stream().map(TotalInsuranceContractGroupDto :: getTotalPolicyMatured).reduce(new BigDecimal(0), (a, b) -> a.add(b)));
				datas.add( rootTmp);
			}
		}
	}

	private void groupAgent(TotalInsuranceContractGroupSearchDto searchDto, List<TotalInsuranceContractGroupDto> datas) {
		if ("AH".equals(searchDto.getAgentGroup())) {
			List<TotalInsuranceContractGroupDto> listTree2 = new ArrayList<TotalInsuranceContractGroupDto>();
			List<TotalInsuranceContractGroupDto> lstTree1 = datas.stream().filter(e -> e.getTreeLevel().equals(new Integer(1))).collect(Collectors.toList());
			if(CollectionUtils.isNotEmpty(lstTree1)) {
				lstTree1.forEach(item -> {
					List<TotalInsuranceContractGroupDto> lstTree2 = datas.stream()
							.filter(e -> e.getTreeLevel().equals(new Integer(2)) && e.getOrgParentId().equals(item.getOrgId()) && e.getParentAgentCode().equals(item.getAgentCode()))
							.collect(Collectors.toList());
					if (CollectionUtils.isNotEmpty(lstTree2)) {

						if ("TH".equals(searchDto.getAgentGroup())) {
							Map<String, List<TotalInsuranceContractGroupDto>> maplv2Group = lstTree2.stream()
									.filter(e -> e.getTreeLevel() == 2)
									.collect(Collectors.groupingBy(bd ->bd.getAgentCode()));
							for (Map.Entry<String, List<TotalInsuranceContractGroupDto>> entry : maplv2Group.entrySet()) {
								String key = entry.getKey();
								if(StringUtils.isNotEmpty(key) && CollectionUtils.isNotEmpty( entry.getValue())) {
									sumPolicy(entry.getValue(), 2);
									listTree2.addAll(entry.getValue());
								}
							}
						} else {
							lstTree2.forEach(item2 -> {
								item2.setOrgParentId(item.getAgentCode());
							});
							listTree2.addAll(lstTree2);
						}
					}

				});
				datas.removeIf(e -> e.getTreeLevel().equals(new Integer(2)));
				Map<String, List<TotalInsuranceContractGroupDto>> maplv1 = lstTree1.stream()
						.filter(e -> e.getTreeLevel() == 1)
						.collect(Collectors.groupingBy(bd ->bd.getAgentCode()));
				if (CollectionUtils.isNotEmpty(maplv1.entrySet())) {
					datas.removeIf(e -> e.getTreeLevel().equals(new Integer(1)));
					for (Map.Entry<String, List<TotalInsuranceContractGroupDto>> entry : maplv1.entrySet()) {
						String key = entry.getKey();
						if(StringUtils.isNotEmpty(key) && CollectionUtils.isNotEmpty( entry.getValue())) {
							sumPolicy(entry.getValue(), 1);
							datas.addAll(entry.getValue());
						}
					}
					datas.addAll(listTree2);
				}

			}
		}
	}
	
	private void mapAgent(TotalInsuranceContractGroupDto data, String agentGroup, boolean first) {
		if (StringUtils.isNotEmpty(data.getChildGroup())) {
			if (first) {
				switch (data.getChildGroup()) {
					case "CAO":
						data.setBdthCode(data.getAgentCode());
						data.setBdthName("Tổng cộng");
						data.setBdthType(data.getAgentType());
						break;
					case "TH":
						data.setBdahCode(data.getAgentCode());
						data.setBdahName("Tổng cộng");
						data.setBdahType(data.getAgentType());
						break;
					case "AH":
						data.setBdohCode(data.getAgentCode());
						data.setBdohName("Tổng cộng");
						data.setBdohType(data.getAgentType());
						break;
					case "RH":
						data.setBdohCode(data.getAgentCode());
						data.setBdohName("Tổng cộng");
						data.setBdohType(data.getAgentType());
						break;
					case "OH":
						data.setGaCode(data.getAgentCode());
						data.setGaName("Tổng cộng");
						data.setGaType(data.getAgentType());
						break;
					case "GA":
						data.setBranchCode(data.getAgentCode());
						data.setBranchName("Tổng cộng");
						data.setBranchType(data.getAgentType());
						break;
					case "SO":
						data.setBranchCode(data.getAgentCode());
						data.setBranchName("Tổng cộng");
						data.setBranchType(data.getAgentType());
					case "BM":
						data.setBranchCode(data.getAgentCode());
						data.setBranchName("Tổng cộng");
						data.setBranchType(data.getAgentType());
						break;
					case "UM":
						data.setUnitCode(data.getAgentCode());
						data.setUnitName("Tổng cộng");
						data.setUnitType(data.getAgentType());
					default:
						data.setAgentCode(data.getAgentCode());
						break;
				}
			} else {
				switch (data.getChildGroup()) {
					case "CAO":
						data.setCaoCode(data.getAgentCode());
						data.setCaoName(data.getOrgId() + ": " + data.getAgentCode() + "-" + data.getAgentName());
						data.setCaoType(data.getAgentType());
						break;
					case "TH":
						data.setBdthCode(data.getAgentCode());
						data.setBdthName(data.getOrgId() + ": " + data.getAgentCode() + "-" + data.getAgentName());
						data.setBdthType(data.getAgentType());
						break;
					case "AH":
						data.setBdahCode(data.getAgentCode());
						data.setBdahName(data.getOrgId() + ": " + data.getAgentCode() + "-" + data.getAgentName());
						data.setBdahType(data.getAgentType());
						break;
					case "RH":
						data.setBdrhCode(data.getAgentCode());
						data.setBdrhName(data.getOrgId() + ": " + data.getAgentCode() + "-" + data.getAgentName());
						data.setBdrhType(data.getAgentType());
						break;
					case "OH":
						String orgId = data.getAgentType();
						if (data.getTreeLevel() == 2) {
							orgId = data.getOrgId();
							data.setOrgCode(data.getOrgId());
						}
						data.setBdohName(orgId + "-" + data.getAgentCode() + "-" + data.getAgentName());
						data.setBdohCode(data.getAgentCode());
						if (StringUtils.contains(data.getAgentName(), "Dummy Sales")) {
							data.setBdohName(data.getAgentName());

						}
						data.setBdohType(data.getAgentType());
						break;
					case "GA":
						data.setGaCode(data.getAgentCode());
						data.setGaName(data.getOrgId() + "-" + data.getAgentCode() + "-" + data.getAgentName());
						if (StringUtils.contains(data.getAgentName(), "Dummy Sales")) {
							data.setGaName(data.getAgentName());

						}
						data.setGaType(data.getAgentType());
						break;
					case "SO":
						data.setGaCode(data.getAgentCode());
						data.setGaName(data.getOrgId() + "-" + data.getAgentCode() + "-" + data.getAgentName());
						if (StringUtils.contains(data.getAgentName(), "Dummy Sales")) {
							data.setGaName(data.getAgentName());

						}
						data.setGaType(data.getAgentType());
						break;
					case "BM":
						data.setBranchCode(data.getAgentCode());
						data.setBranchName(data.getAgentType() + ": " + data.getAgentCode().replace(data.getOrgId(), "") + "-" + data.getAgentName());
						if (StringUtils.contains(data.getAgentName(), "Dummy Sales")) {
							data.setBranchName(data.getAgentName());

						}
						data.setBranchType(data.getAgentType());
						break;
					case "UM":
						data.setUnitCode(data.getAgentCode());
						data.setUnitName(data.getAgentType() + ": " + data.getAgentCode().replace("A", "").replace("B", "").replace("C", "") + "-" + data.getAgentName());
						if (StringUtils.contains(data.getAgentName(), "Dummy Sales")) {
							data.setUnitName(data.getAgentName());

						}
					default:
						data.setAgentCode(data.getAgentCode());
						break;
				}
			}
		}
	}
	@Override
	public CmsCommonPagination<ContractOverdueFeeRypDto> getListContractOverdueFeeRYP(ContractFeeGroupSearchDto searchDto) {
		ContractOverdueFeeRypParam param = new ContractOverdueFeeRypParam();
		CmsCommonPagination<ContractOverdueFeeRypDto> rs = new CmsCommonPagination<>();
		List<ContractOverdueFeeRypDto> datas = new ArrayList<>();
		try {
			param.agentCode=searchDto.getAgentCodeSearch();
    		param.agentGroup=searchDto.getAgentGroup();
    		param.paidType=searchDto.getPaidType();
    		param.page=searchDto.getPage();
    		param.pageSize=searchDto.getPageSize();
    		if (ObjectUtils.isEmpty(searchDto.getKeyword()) && ObjectUtils.isEmpty(searchDto.getManagerAgentCode()) && ObjectUtils.isEmpty(searchDto.getAgentCode())) {
    			searchDto.setFunctionCode("OFFICE_POLICY_OVERDUE_30DAYS");
        	    CommonSearchWithPagingDto common = geranateCommonSearch(searchDto, searchDto.getFunctionCode());
        	    param.search=common.getSearch();
        	} else {
        		param.search=searchGroup("OFFICE_POLICY_OVERDUE_30DAYS", searchDto.getKeyword(), searchDto.getManagerAgentCode(), searchDto.getAgentCode());
        	}
    		param.sort=searchDto.getSort();
			sqlManagerDb2Service.call(STORE_OVER_DUE_FEE_RYP, param);
			datas=param.data;
			for (ContractOverdueFeeRypDto dto : datas) {
				dto.setAgentAll(agentPosition(dto.getAgentType(), dto.getAgentCode(), dto.getAgentName()));
				dto.setManagerAll(agentPosition(dto.getManagerAgentType(), dto.getManagerAgentCode().replace("A", "").replace("B", "").replace("C", ""), dto.getManagerAgentName()));
				dto.setParentAll(agentPosition(dto.getManagerAgentType(), dto.getManagerAgentCode().replace("A", "").replace("B", "").replace("C", ""), dto.getManagerAgentName()));
			}
    		rs.setTotalData(param.totalRows);
    		rs.setData(datas);
		} catch (Exception e) {
            logger.error("Exception", e);
        }
		return rs;	
	}
	
	@Override
	public CmsCommonPagination<ContractFeeGroupDto> getListFeeByGroup(ContractFeeGroupSearchDto searchDto) {
	    CmsCommonPagination<ContractFeeGroupDto> rs = new CmsCommonPagination<>();
	    ContractFeeGroupParam param = new ContractFeeGroupParam();
	    List<ContractFeeGroupDto> datas = new ArrayList<>();
	    try {
    		param.agentCode=searchDto.getAgentCodeSearch();
    		param.orgId=searchDto.getOrgId();
    		param.agentGroup=searchDto.getAgentGroup();
    		param.paidType=searchDto.getPaidType();
    		param.page=searchDto.getPage();
    		param.pageSize=searchDto.getPageSize();
    		param.sort=searchDto.getSort();
    		param.search=searchGroup("OFFICE_POLICY_FEE", searchDto.getKeyword(), searchDto.getManagerAgentCode(), searchDto.getAgentCode());
    		sqlManagerDb2Service.call(DS_SP_GET_LIST_LEADER_PAID_TO_DATE, param);
    		datas = param.data;
    		for (ContractFeeGroupDto dto : datas) {
    			dto.setManagerAgentCode(dto.getLv2AgentCode());
    			dto.setManagerAgentType(dto.getLv2AgentType());
    			dto.setManagerAgentName(dto.getLv2AgentName());

    			dto.setAgentCode(dto.getLv3AgentCode());
    			dto.setAgentType(dto.getLv3AgentType());
    			dto.setAgentName(dto.getLv3AgentName());
                dto.setAgentAll(agentPosition(dto.getLv3AgentType(), dto.getLv3AgentCode(), dto.getLv3AgentName()));
                dto.setManagerAll(agentPosition(dto.getLv2AgentType(), dto.getLv2AgentCode().replace("A", "").replace("B", "").replace("C", ""), dto.getLv2AgentName()));
                dto.setParentAll(agentPosition(dto.getLv2AgentType(), dto.getLv2AgentCode().replace("A", "").replace("B", "").replace("C", ""), dto.getLv2AgentName()));
            }
    		rs.setTotalData(param.totalRows);
    		rs.setData(datas);
	    } catch (Exception e) {
            logger.error("Exception", e);
        }
		return rs;
	}
	
	@Override
    public CmsCommonPagination<ContractFeeGroupDto> getListFeeByGroupDetail(ContractFeeGroupSearchDto searchDto) {
	    CmsCommonPagination<ContractFeeGroupDto> rs = new CmsCommonPagination<>();
        ContractFeeGroupDetailParam param = new ContractFeeGroupDetailParam();
        try {
            searchDto.setFunctionCode("OFFICE_POLICY_FEE_DETAIL");
            CommonSearchWithPagingDto common = geranateCommonSearch(searchDto, searchDto.getFunctionCode());
            param.agentCode=searchDto.getAgentCodeSearch();
            param.agentGroup=searchDto.getAgentGroup();
            param.paidType=searchDto.getPaidType();
            param.search=searchDto.getSearch();
			param.page=searchDto.getPage();
			param.pageSize=searchDto.getPageSize();
            param.sort=common.getSort();
            //param.search=common.getSearch();
            sqlManagerDb2Service.call(STORE_FEE_CONTRACT_DETAIL, param);
            rs.setTotalData(param.totalRows);
            rs.setData(param.data);
        } catch (Exception e) {
            logger.error("Exception", e);
        }
        return rs;
    }
	
	//active-inactive
	@Override
	public CmsCommonPagination<ContractEffectGroupDto> getListContractEffectByGroup(ContractEffectGroupSearchDto searchDto, boolean isExport) {
		ContractEffectGroupParam param = new ContractEffectGroupParam();
		CmsCommonPagination<ContractEffectGroupDto> rs = new CmsCommonPagination<>();
		List<ContractEffectGroupDto> datas = new ArrayList<>();
		try {
			param.agentCode=searchDto.getAgentCodeSearch();
	        param.agentGroup=searchDto.getAgentGroup();
	        param.orgCode = searchDto.getOrgId();
	        param.policyType=searchDto.getPolicyType();
	        param.page=searchDto.getPage();
	        param.pageSize=searchDto.getPageSize();
	        param.search=searchGroup("OFFICE_POLICY_ACTIVE", searchDto.getKeyword(), searchDto.getManagerAgentCode(), searchDto.getAgentCode());
			
			if (isExport) {
				// Hiện tại dùng chung được export và list
				sqlManagerDb2Service.call(DS_SP_GET_LIST_POLICY_BY_LEADER_SUM_LIST, param);
			} else {
				sqlManagerDb2Service.call(DS_SP_GET_LIST_POLICY_BY_LEADER_SUM_LIST, param);
			}
			
	        datas = param.data;
	        for (ContractEffectGroupDto dto : datas) {
	            dto.setParentAll(agentPosition(dto.getManagerAgentType(), dto.getManagerAgentCode().replace("A", "").replace("B", "").replace("C", ""), dto.getManagerAgentName()));
	            dto.setAgentAll(agentPosition(dto.getAgentType(), dto.getAgentCode(), dto.getAgentName()));
	        }
	        rs.setTotalData(param.totalRows);
	        rs.setData(datas);
        } catch (Exception e) {
            logger.error("getListContractEffectByGroup", e);
        }
		return rs;
	}

	@Override
	public String fieldSearchBmUm(Object keySearch, String functionCode){
		CommonSearchWithPagingDto search = new CommonSearchWithPagingDto();
		if(ObjectUtils.isNotEmpty(keySearch)) {
			ContractEffectGroupSearchDto manager = new ContractEffectGroupSearchDto();
			manager.setManagerAgentCode(keySearch);
			manager.setFunctionCode(functionCode);
			search = geranateCommonSearch(manager, functionCode);
		}
		return search.getSearch();
	}

	@Override
	public String searchBmUm(String keyWord, String searchManager, String searchAgent, String search){
		String db1,db2,db3,db4,db = "";
		String db12 = "";
		String db34 = "";
		if(ObjectUtils.isNotEmpty(keyWord)){
			db = "AND (UPPER(nvl(LV2_AGENTCODE,'')) LIKE UPPER(N'%"+ keyWord +"%') "
					+ 	"OR (UPPER(nvl(LV2_AGENTNAME,'')) LIKE UPPER(N'%"+ keyWord +"%')) "
					+   "OR (UPPER(nvl(LV2_AGENTTYPE,'')) LIKE UPPER(N'%"+ keyWord +"%')) "
					+   "OR (UPPER(nvl(LV3_AGENTCODE,'')) LIKE UPPER(N'%"+ keyWord +"%')) "
					+   "OR (UPPER(nvl(LV3_AGENTNAME,'')) LIKE UPPER(N'%"+ keyWord +"%')) "
					+	"OR (UPPER(nvl(LV3_AGENTTYPE,'')) LIKE UPPER(N'%"+ keyWord +"%'))) ";
		}

		if(ObjectUtils.isNotEmpty(searchManager)){
			db1 = searchManager.replace("and UPPER(nvl(LV2_AGENTNAME,''))", "OR UPPER(nvl(LV2_AGENTTYPE,''))");
			db2 = searchManager.replace("and UPPER(nvl(LV2_AGENTNAME,''))", "OR UPPER(nvl(LV2_AGENTCODE,''))");
			db12 = searchManager + db1 + db2;
		}
		if(ObjectUtils.isNotEmpty(searchAgent)) {
			db3 = searchAgent.replace("and UPPER(nvl(LV3_AGENTNAME,''))", "OR UPPER(nvl(LV3_AGENTTYPE,''))");
			db4 = searchAgent.replace("and UPPER(nvl(LV3_AGENTNAME,''))", "OR UPPER(nvl(LV3_AGENTCODE,''))");
			db34 = searchAgent + db3 + db4;
		}
		return " AND ( 1 = 1 " + db + ")  AND  ( 1 = 1 " + db12 + ") AND  ( 1 = 1 " + db34 + ")";
	}
	@Override
	public CmsCommonPagination<ContractBusinessHandleDto> getListBusinessHandle(ContractBusinessGroupSearchDto searchDto) {
	    ContractBusinessHandleParam param = new ContractBusinessHandleParam();
	    CmsCommonPagination<ContractBusinessHandleDto> rs = new CmsCommonPagination<>();
	    try {
			param.agentCode=searchDto.getAgentCodeSearch();
			param.orgId=searchDto.getOrgId();
	        param.agentGroup=searchDto.getAgentGroup();
			param.page=searchDto.getPage();
			param.pageSize=searchDto.getPageSize();
			param.sort=searchDto.getSort();
			param.search=searchGroup("OFFICE_POLICY_BUSINESS", searchDto.getKeyword(), searchDto.getManagerAgentCode(), searchDto.getAgentCode());
			sqlManagerDb2Service.call(STORE_BUSINESS_HANDLE_CONTRACT, param);
			if (CommonCollectionUtil.isNotEmpty(param.data)) {
				int no = 1;
					for (ContractBusinessHandleDto x: param.data){
					x.setNo(no++);
					x.setParentAll(agentPosition(x.getManagerAgentType(), x.getManagerAgentCode().replace("A", "").replace("B", "").replace("C", ""), x.getManagerAgentName()));
					x.setAgentAll(agentPosition(x.getAgentType(), x.getAgentCode(), x.getAgentName()));
				}
			}
			int total = 0;
			if(ObjectUtils.isNotEmpty(param.data)) total = param.data.size();
			rs.setTotalData(total);
			rs.setData(param.data);
		} catch (Exception e) {
            logger.error("Exception", e);
        }
		return rs;
	}

    @Override
    public CmsCommonPagination<OfficePolicyClaimDetailDto> getDetailPolicyClaim(ContractClaimGroupSearchDto searchDto) {
        List<OfficePolicyClaimDetailDto> datas = new ArrayList<>();
        OfficePolicyClaimParamDto param = new OfficePolicyClaimParamDto();
        CmsCommonPagination<OfficePolicyClaimDetailDto> rs = new CmsCommonPagination<>();
        try {
			param.agentCode=searchDto.getAgentCodeSearch();
            param.orgId=searchDto.getOrgId();
            param.agentGroup=searchDto.getAgentGroup();
            param.page=searchDto.getPage();
            param.pageSize=searchDto.getPageSize();
            param.sort=searchDto.getSearch();
            param.search=searchGroup("OFFICE_POLICY_CLAIM", searchDto.getKeyword(), searchDto.getManagerAgentCode(), searchDto.getAgentCode());
            sqlManagerDb2Service.call(STORE_DETAIL_POLICY_CLAIMS, param);
            rs.setTotalData(param.totalRows);
            datas=param.lstData;
            for (OfficePolicyClaimDetailDto dto : datas) {
                dto.setParentAll(agentPosition(dto.getManagerAgentType(), dto.getManagerAgentCode().replace("A", "").replace("B", "").replace("C", ""), dto.getManagerAgentName()));
                dto.setAgentAll(agentPosition(dto.getAgentType(), dto.getAgentCode(), dto.getAgentName()));
            }
            rs.setData(datas);
        } catch (Exception e) {
            logger.error("Exception", e);
        }
        return rs;
    }

    @Override
    public List<FinanceSupportStandardDto> getListAgentStandard(String agentCode, String agentGroup, String orgCode) {
        List<FinanceSupportStandardDto> datas = new ArrayList<>();
        try {FinanceSupportStandardParam param = new FinanceSupportStandardParam(agentCode, orgCode, agentGroup, 0, 0, null, null, null, null);
            sqlManagerDb2Service.call(STORE_DETAIL_POLICY_STANDARD, param);
            datas=param.data;
        } catch (Exception e) {
            logger.error("getListAgentStandard: ", e);
        }
        return datas;
    }

	@Override
	public TotalPolicyDto getTotalPolicy(String agentCode, String agentGroup, String orgId) {
		List<TotalPolicyDto> datas = new ArrayList<>();
		TotalPolicyDto data = new TotalPolicyDto();
		try {
			if (StringUtils.equalsIgnoreCase(agentGroup, "FC")) {
				TotalPolicyPersonalParam paramPersonal = new TotalPolicyPersonalParam();
				paramPersonal.agentCode=agentCode;
				sqlManagerDb2Service.call(DS_SP_GET_TOTAL_ACTIVE_INACTIVE_INSURANCE_CONTRACT_PERSONAL, paramPersonal);
				datas=paramPersonal.data;

				TotalPolicyPersonalParam handing = new TotalPolicyPersonalParam();
				handing.agentCode=agentCode;
				sqlManagerDb2Service.call(DS_SP_GET_TOTAL_HANDING_INSURANCE_CONTRACT_PERSONAL, handing);
				datas.addAll(handing.data);

				/*TotalPolicyPersonalParam claim = new TotalPolicyPersonalParam();
				claim.agentCode=agentCode;
				sqlManagerDb2Service.call(DS_SP_GET_TOTAL_CLAIM_INSURANCE_CONTRACT_PERSONAL, claim);
				datas.addAll(claim.data);*/

				TotalPolicyPersonalParam paid = new TotalPolicyPersonalParam();
				paid.agentCode=agentCode;
				sqlManagerDb2Service.call(DS_SP_GET_TOTAL_PAID_TODATE_INSURANCE_CONTRACT_PERSONAL, paid);
				datas.addAll(paid.data);
				
				TotalPolicyPersonalParam matured = new TotalPolicyPersonalParam();
				matured.agentCode=agentCode;
				sqlManagerDb2Service.call(DS_SP_GET_TOTAL_POLICY_MATURED_PERSONAL, matured);
				datas.addAll(matured.data);
			} else {
				TotalPolicyParam param = new TotalPolicyParam();
				param.agentCode=agentCode;
				param.agentGroup=agentGroup;
				param.orgId= orgId;
				param.page=0;
				param.pageSize=1;
				param.search = " AND L.TREE_LEVEL = 0";
				sqlManagerDb2Service.call(STORE_GET_TOTAL_POLICY, param);
				datas=param.data;
			}

		} catch (Exception e) {
			logger.error("exportListData: ", e);
		}
		if(datas.isEmpty()){
			return null;
		}
		data.setTotalContractEffect(datas.stream().map(TotalPolicyDto::getTotalContractEffect).reduce(0, (a, b) -> a + b));
		data.setTotalContractInvalid(datas.stream().map(TotalPolicyDto::getTotalContractInvalid).reduce(0, (a, b) -> a + b));
		data.setTotalContractExpired30Days(datas.stream().map(TotalPolicyDto::getTotalContractExpired30Days).reduce(0, (a, b) -> a + b));
		data.setTotalContractDueFee(datas.stream().map(TotalPolicyDto::getTotalContractDueFee).reduce(0, (a, b) -> a + b));
		data.setTotalBusiness(datas.stream().map(TotalPolicyDto::getTotalBusiness).reduce(0, (a, b) -> a + b));
		data.setSumOfPolicyHanding(datas.stream().map(TotalPolicyDto::getSumOfPolicyHanding).reduce(0, (a, b) -> a + b));
		// data.setSumOfPolicyClaim(datas.stream().map(TotalPolicyDto::getSumOfPolicyClaim).reduce(0, (a, b) -> a + b));
		data.setTotalContractOverdueFeeRyp(datas.stream().map(TotalPolicyDto::getTotalContractOverdueFeeRyp).reduce(0, (a, b) -> a + b));
		data.setTotalRenew(datas.stream().map(TotalPolicyDto::getTotalRenew).reduce(0, (a, b) -> a + b));
		data.setTotalContractOrphan(datas.stream().map(TotalPolicyDto::getTotalContractOrphan).reduce(0, (a, b) -> a + b));
		data.setSumOfPolicyPaidTodateAll(datas.stream().map(TotalPolicyDto::getSumOfPolicyPaidTodateAll).reduce(0, (a, b) -> a + b));
		data.setTotalPolicyMatured(datas.stream().map(TotalPolicyDto::getTotalPolicyMatured).reduce(0, (a, b) -> a + b));
		
		return data;
	}
	
    @Override
    @SuppressWarnings({"rawtypes", "unchecked" })
    public <T, E extends Enum<E>, M> ResponseEntity exportListDataWithHeader(List<T> resultDto, String view, String titleName, String[] titleHeader, String row, Class<E> enumDto, Class<M> className) {
        ResponseEntity res = null;
        try {
            String datePattern = systemConfig.getConfig(SystemConfig.DATE_PATTERN);
            datePattern = "dd/MM/yyyy";
            String template= "HDBH.xlsx";
            String templatePath = servletContext.getRealPath(CommonConstant.REAL_PATH_TEMPLATE_EXCEL + "/" + template);
            String templateName = "HDBH.xlsx";

            String startRow = row;
            List<ItemColsExcelDto> cols = new ArrayList<>();
            
            ExportExcelUtil exportExcel = new ExportExcelUtil<>();
            Map<String, String> mapColFormat = null;
            Map<String, Object> setMapColDefaultValue = null;
            try (XSSFWorkbook xssfWorkbook = exportExcel.getXSSFWorkbook(templatePath);) {
				Map<String, CellStyle> mapColStyle = new HashMap<>();
                templateName+="_"+view;
                ImportExcelUtil.setListColumnExcel(enumDto, cols);
                apiAgentDetailService.setDataHeaderToXSSFWorkbookSheet(xssfWorkbook, 0, titleHeader, titleName, mapColStyle);
				String repo = systemConfig.getConfig(systemConfig.REPO_UPLOADED_MAIN);
				String path = systemConfig.getPhysicalPathById(repo, null); //path up service
                res = exportExcel.doExportExcelHeaderWithColFormatRestUpService(xssfWorkbook, 0, null, resultDto,
                        className, cols, datePattern, startRow, mapColFormat, mapColStyle,
                        setMapColDefaultValue, null, true, templateName, true,path);
            } catch (Exception e) {
                logger.error("exportListData: ", e);
            }
        } catch (Exception e) {
            logger.error("exportListData: ", e);
        }
        return res;
    }
    
    @Override
    @SuppressWarnings({"rawtypes", "unchecked" })
	public <T, E extends Enum<E>, M> ResponseEntity exportListDataWithHeaderDto(List<T> resultDto, String view, String titleName, String[] titleHeader, String row, Class<E> enumDto, Class<M> className, String agentCode, String agentType, int total) {
        ResponseEntity res = null;
        try {
            String datePattern = systemConfig.getConfig(SystemConfig.DATE_PATTERN);
            datePattern = "dd/MM/yyyy";
            String template= "HDBH.xlsx";
            String templatePath = servletContext.getRealPath(CommonConstant.REAL_PATH_TEMPLATE_EXCEL + "/" + template);
            String templateName = "HDBH.xlsx";

            String startRow = "A8";
            List<ItemColsExcelDto> cols = new ArrayList<>();
            
            ExportExcelUtil exportExcel = new ExportExcelUtil<>();
            Map<String, String> mapColFormat = null;
            Map<String, Object> setMapColDefaultValue = null;
            try (XSSFWorkbook xssfWorkbook = exportExcel.getXSSFWorkbook(templatePath);) {
                Map<String, CellStyle> mapColStyle = new HashMap<>();
                if ("HỒ SƠ YÊU CẦU BỒI THƯỜNG CẤP PHÒNG BAN".equals(titleName)) {
                	templateName = view;
                } else {
                	templateName+="_"+view;
                }
                ImportExcelUtil.setListColumnExcel(enumDto, cols);
				setDataHeaderToXSSFWorkbookSheet(xssfWorkbook, 0, titleHeader, titleName, mapColStyle, agentCode, agentType, total);
				String repo = systemConfig.getConfig(systemConfig.REPO_UPLOADED_MAIN);
				String path = systemConfig.getPhysicalPathById(repo, null); //path up service
                res = exportExcel.doExportExcelHeaderWithColFormatRestUpService(xssfWorkbook, 0, null, resultDto,
                        className, cols, datePattern, startRow, mapColFormat, mapColStyle,
                        setMapColDefaultValue, null, true, templateName, true,path);
            } catch (Exception e) {
                logger.error("exportListData: ", e);
            }
        } catch (Exception e) {
            logger.error("exportListData: ", e);
        }
        return res;
    }

	public void setDataHeaderToXSSFWorkbookSheet (XSSFWorkbook xssfWorkbook,int sheetNumber, String[] titleHeader, String titleName, Map < String, CellStyle > mapColStyle, String agentCode, String agentType, int total){
			XSSFSheet xssfSheet = xssfWorkbook.getSheetAt(sheetNumber);

			CellStyle titleStyle = xssfSheet.getWorkbook().createCellStyle();
			CellStyle titleStyleDate = xssfSheet.getWorkbook().createCellStyle();
			CellStyle no = xssfSheet.getWorkbook().createCellStyle();

			// set col NO
			Font fontNo = xssfWorkbook.createFont();
			fontNo.setFontName("Times New Roman");
			fontNo.setFontHeightInPoints((short) 11);
			no.setFont(fontNo);
			no.setAlignment(HorizontalAlignment.CENTER);
			no.setVerticalAlignment(VerticalAlignment.CENTER);
			no.setBorderBottom(BorderStyle.THIN);
			no.setBorderTop(BorderStyle.THIN);
			no.setBorderRight(BorderStyle.THIN);
			no.setBorderLeft(BorderStyle.THIN);

			mapColStyle.put("NO", no);

			// set col title
			Font fontTitle = xssfWorkbook.createFont();
			fontTitle.setColor(IndexedColors.BLUE.index);
			fontTitle.setFontName("Times New Roman");
			fontTitle.setBold(true);
			fontTitle.setFontHeightInPoints((short) 20);
			titleStyle.setFont(fontTitle);
			titleStyle.setAlignment(HorizontalAlignment.CENTER);
			titleStyle.setVerticalAlignment(VerticalAlignment.CENTER);

			if (xssfSheet.getRow(0) != null) xssfSheet.getRow(0).getCell(0).setCellValue(titleName);
			else xssfSheet.createRow(0).createCell(0).setCellValue(titleName);
			xssfSheet.getRow(0).getCell(0).setCellStyle(titleStyle);

			if (titleName.equals("DANH SÁCH HỢP ĐỒNG ĐIỀU CHỈNH NGHIỆP VỤ CẤP PHÒNG BAN") && agentType.equals("BM"))
				xssfSheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 10));
			else if (titleName.equals("DANH SÁCH HỢP ĐỒNG ĐIỀU CHỈNH NGHIỆP VỤ CẤP PHÒNG BAN")&& agentType.equals("UM"))
				xssfSheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 9));
			else if (titleName.equals("DANH SÁCH HỢP ĐỒNG THU PHÍ CẤP PHÒNG BAN")&& agentType.equals("BM"))
				 xssfSheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 5));
			else if (titleName.equals("DANH SÁCH HỢP ĐỒNG THU PHÍ CẤP PHÒNG BAN")&& agentType.equals("UM"))
				 xssfSheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 4));
			else if (titleName.equals("DANH SÁCH HỢP ĐỒNG QUÁ HẠN THU PHÍ RYP 30 NGÀY")&& agentType.equals("BM"))
				 xssfSheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 16));
			else if (titleName.equals("DANH SÁCH HỢP ĐỒNG QUÁ HẠN THU PHÍ RYP 30 NGÀY")&& agentType.equals("UM"))
				 xssfSheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 15));
			else if (titleName.equals("HỒ SƠ YÊU CẦU BỒI THƯỜNG CẤP PHÒNG BAN")&& agentType.equals("BM"))
				 xssfSheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 15));
			else if (titleName.equals("HỒ SƠ YÊU CẦU BỒI THƯỜNG CẤP PHÒNG BAN")&& agentType.equals("UM"))
				 xssfSheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 14));
			else if (agentType.equals("UM"))
				 xssfSheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 2));
			else
				 xssfSheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 3));


			// set infor
			Font fontTitleDate = xssfWorkbook.createFont();
			fontTitleDate.setColor(IndexedColors.BLUE.index);
			fontTitleDate.setFontName("Times New Roman");
			titleStyleDate.setFont(fontTitleDate);

			AgentInfoDb2 resObj = db2ApiService.getParentByAgentCode(agentCode, agentType, "");

			if (xssfSheet.getRow(1) != null)
				xssfSheet.getRow(1).getCell(0).setCellValue(resObj.getOrgId() + " - " + resObj.getOrgName());
			else xssfSheet.createRow(1).createCell(0).setCellValue(resObj.getOrgId()  + " - " + resObj.getOrgName());

			if (xssfSheet.getRow(2) != null)
				xssfSheet.getRow(2).getCell(0).setCellValue(resObj.getAgentType() + ": " + resObj.getAgentCode().replace(resObj.getOrgId(), "").replace("A", "").replace("B", "").replace("C", "")  + " - " + resObj.getAgentName());
			else
				xssfSheet.createRow(2).createCell(0).setCellValue(resObj.getAgentType() + ": " + resObj.getAgentCode().replace(resObj.getOrgId(), "").replace("A", "").replace("B", "").replace("C", "")  + " - " + resObj.getAgentName());

			if (xssfSheet.getRow(3) != null)
				xssfSheet.getRow(3).getCell(0).setCellValue("Ngày báo cáo: " + DateUtils.formatDateToString(new Date(), "dd/MM/yyyy"));
			else
				xssfSheet.createRow(3).createCell(0).setCellValue("Ngày báo cáo: " + DateUtils.formatDateToString(new Date(), "dd/MM/yyyy"));

			if (xssfSheet.getRow(4) != null) xssfSheet.getRow(4).getCell(0).setCellValue("Tổng số HĐ: " + total);
			else xssfSheet.createRow(4).createCell(0).setCellValue("Tổng số HĐ: " + total);

			xssfSheet.getRow(4).getCell(0).setCellStyle(titleStyleDate);
			xssfSheet.getRow(3).getCell(0).setCellStyle(titleStyleDate);
			xssfSheet.getRow(2).getCell(0).setCellStyle(titleStyleDate);
			xssfSheet.getRow(1).getCell(0).setCellStyle(titleStyleDate);

			//set title table
			Font font = xssfWorkbook.createFont();
			font.setFontName("Times New Roman");
			font.setBold(true);
			CellStyle headerCellStyle = xssfSheet.getWorkbook().createCellStyle();
			headerCellStyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.index);
			headerCellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
			headerCellStyle.setAlignment(HorizontalAlignment.CENTER);
			headerCellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
			headerCellStyle.setWrapText(true);
			headerCellStyle.setFont(font);
			headerCellStyle.setBorderBottom(BorderStyle.THIN);
			headerCellStyle.setBorderTop(BorderStyle.THIN);
			headerCellStyle.setBorderRight(BorderStyle.THIN);
			headerCellStyle.setBorderLeft(BorderStyle.THIN);

			XSSFRow row4 = xssfSheet.getRow(6);
			for (int i = 0; i < titleHeader.length; i++) {
				XSSFCell cell4 = row4.getCell(i);
				cell4.setCellValue(titleHeader[i]);
				cell4.setCellStyle(headerCellStyle);
			}
		}


    @Override
    @SuppressWarnings({"rawtypes", "unchecked" })
    public <T, E extends Enum<E>, M> ResponseEntity exportListData(List<T> resultDto, String fileName, String row, Class<E> enumDto, Class<M> className) {
        ResponseEntity res = null;
        try {
            String datePattern = systemConfig.getConfig(SystemConfig.DATE_PATTERN);
            datePattern = "dd/MM/yyyy";
            String templateName = fileName;
            String templatePath = servletContext.getRealPath(CommonConstant.REAL_PATH_TEMPLATE_EXCEL + "/" + templateName);
            String startRow = row;
            List<ItemColsExcelDto> cols = new ArrayList<>();
            ImportExcelUtil.setListColumnExcel(enumDto, cols);
            ExportExcelUtil exportExcel = new ExportExcelUtil<>();
            Map<String, String> mapColFormat = null;
            Map<String, Object> setMapColDefaultValue = null;
            try (XSSFWorkbook xssfWorkbook = exportExcel.getXSSFWorkbook(templatePath);) {
                Map<String, CellStyle> mapColStyle = null;
				String repo = systemConfig.getConfig(systemConfig.REPO_UPLOADED_MAIN);
				String path = systemConfig.getPhysicalPathById(repo, null); //path up service
                res = exportExcel.doExportExcelHeaderWithColFormatRestUpService(xssfWorkbook, 0, null, resultDto,
                        className, cols, datePattern, startRow, mapColFormat, mapColStyle,
                        setMapColDefaultValue, null, true, templateName, true,path);
            } catch (Exception e) {
                logger.error("exportListData: ", e);
            }
        } catch (Exception e) {
            logger.error("exportListData: ", e);
        }
        return res;
    }
    
    @SuppressWarnings({ "unchecked", "rawtypes" })
    @Override
    public ResponseEntity exportListViewBD(TotalInsuranceContractGroupSearchDto dto) {
    	ResponseEntity res = null;
        try {
            dto.setPage(0);
            dto.setPageSize(0);
            CmsCommonPagination<TotalInsuranceContractGroupDto> common = getListTotalContractByGroup(dto);
            String datePattern = systemConfig.getConfig(SystemConfig.DATE_PATTERN);
            datePattern = "dd/MM/yyyy";
            String templateName = "HDBH_cap_phong_ban.xlsx";
            String templatePath = servletContext.getRealPath(CommonConstant.REAL_PATH_TEMPLATE_EXCEL + "/" + templateName);
            String startRow = "A8";
            List<TotalInsuranceContractGroupDto> lstdata = common.getData();
            TotalInsuranceContractGroupDto root = new TotalInsuranceContractGroupDto();
            if(ObjectUtils.isNotEmpty(lstdata)) {
            	 root = lstdata.stream().filter(e->e.getTreeLevel().equals(0)).findFirst().get();
            }
			List<TotalInsuranceContractGroupDto> allData = new ArrayList<>();
			// get lv1
            List<TotalInsuranceContractGroupDto> lv1 = lstdata.stream().filter(e->e.getTreeLevel() == 1).collect(Collectors.toList());
			for (TotalInsuranceContractGroupDto groupLv1 : lv1) {
				allData.add(groupLv1);
				// get lv2
				List<TotalInsuranceContractGroupDto> lv2 = lstdata.stream().filter(e->e.getTreeLevel() == 2 
						&& e.getParentAgentCode().equals(groupLv1.getAgentCode()) && e.getOrgParentId().equals(groupLv1.getOrgId())).collect(Collectors.toList());
				allData.addAll(lv2);
			}
			allData.add(root);
            List<ItemColsExcelDto> cols = new ArrayList<>();
            ExportExcelUtil exportExcel = new ExportExcelUtil<>();
            Map<String, String> mapColFormat = null;
            Map<String, Object> setMapColDefaultValue = null;
            String orgId = null;
            if("SO".equalsIgnoreCase(dto.getAgentGroup()) || "GA".equalsIgnoreCase(dto.getAgentGroup())) {
            	orgId = dto.getOrgId();
            }
			AgentInfoDb2 dataz = db2ApiService.getParentByAgentCode(dto.getAgentCode(),dto.getAgentGroup(),orgId);
            // do export
            try (XSSFWorkbook xssfWorkbook = exportExcel.getXSSFWorkbook(templatePath);) {
                Map<String, CellStyle> mapColStyle = new HashMap<>();
                
                String titleName="DANH SÁCH HỢP ĐỒNG CẤP PHÒNG BAN";
                String[] titleHeader = null;
                titleHeader = new String[] {"", ""
                        , "Tổng HĐ còn hiệu lực"
                        , "Tổng HĐ đến hạn thu phí"
                        , "Tổng HĐ quá hạn thu phí RYP 30 ngày"
                        , "Tổng HĐ đáo hạn"
                        , "Tổng HĐ mất hiệu lực"};
                List<String> listHeader = Arrays.asList(titleHeader);
                templateName+="_";
                if(dto.getAgentGroup().equalsIgnoreCase("CAO")
                   || dto.getAgentGroup().equalsIgnoreCase("AH")
                   || dto.getAgentGroup().equalsIgnoreCase("TH")
                   || dto.getAgentGroup().equalsIgnoreCase("RH")
                   || dto.getAgentGroup().equalsIgnoreCase("OH")
                   || dto.getAgentGroup().equalsIgnoreCase("SO")
                   || dto.getAgentGroup().equalsIgnoreCase("GA")){
                    if(dto.getAgentGroup().equalsIgnoreCase("CAO")) {
                        listHeader.set(0, "BDTH");
                        listHeader.set(1, "BDAH");
                        titleName += "CAO";
                        templateName +="CAO";
                        ImportExcelUtil.setListColumnExcel(ListPolicyOfficeCaoEnum.class, cols);
                    }
                    else if(dto.getAgentGroup().equalsIgnoreCase("TH")) {
                        listHeader.set(0, "BDAH");
                        listHeader.set(1, "BDOH");
                        titleName += "TH";
                        templateName +="TH";
                        ImportExcelUtil.setListColumnExcel(ListPolicyOfficeThEnum.class, cols);
                    }
                    else if(dto.getAgentGroup().equalsIgnoreCase("AH")) {
                	   listHeader.set(0, "BDOH");
                       listHeader.set(1, "Văn phòng/ Tổng đại lý");
                       titleName += "AH";
                       templateName +="AH";
                       ImportExcelUtil.setListColumnExcel(ListPolicyOfficeAhEnum.class, cols);
                    }
                    else if(dto.getAgentGroup().equalsIgnoreCase("OH")) {
                        listHeader.set(0, "Văn phòng/ Tổng đại lý");
                        listHeader.set(1, "Trưởng Phòng");
                        titleName += "OH";
                        templateName +="OH";
                        ImportExcelUtil.setListColumnExcel(ListPolicyOfficeOhEnum.class, cols);
                    }
                    else if(dto.getAgentGroup().equalsIgnoreCase("GA")) {
                        listHeader.set(0, "Văn phòng/ Tổng đại lý");
                        listHeader.set(1, "Trưởng Phòng");
                        titleName += "GA";
                        templateName +="GA";
                        ImportExcelUtil.setListColumnExcel(ListPolicyOfficeOhEnum.class, cols);
                    }
                    else if(dto.getAgentGroup().equalsIgnoreCase("SO")) {
                        listHeader.set(0, "Văn phòng/ Tổng đại lý");
                        listHeader.set(1, "Trưởng Phòng");
                        titleName += "SO";
                        templateName +="SO";
                        ImportExcelUtil.setListColumnExcel(ListPolicyOfficeOhEnum.class, cols);
                    }
                }
                titleName = "DANH SÁCH HỢP ĐỒNG CẤP PHÒNG BAN";
                titleHeader = listHeader.toArray(new String[0]);
                setDataHeaderToXSSFWorkbookSheet(xssfWorkbook, 0, titleHeader, titleName ,dataz.getOrgId(), dataz.getOrgName(),dataz.getAgentType() +": "+ dataz.getAgentCode().replace(dataz.getOrgId(), "").replace("A", "").replace("B", "").replace("C", "") + " - " + dataz.getAgentName(), mapColStyle);
				String repo = systemConfig.getConfig(systemConfig.REPO_UPLOADED_MAIN);
				String path = systemConfig.getPhysicalPathById(repo, null); //path up service
                res =  exportExcel.doExportExcelHeaderWithColFormatRestUpService(xssfWorkbook, 0, null, allData, TotalInsuranceContractGroupDto.class
                        , cols, datePattern, startRow, mapColFormat, mapColStyle,
                        setMapColDefaultValue, null, true, templateName, true,path);
            } catch (Exception e) {
                logger.error("##doExport##", e);
            }
          } catch (Exception e) {
              logger.error("##exportListViewBD##", e);
        }
        return res;
    }
    private void setDataHeaderToXSSFWorkbookSheet(XSSFWorkbook xssfWorkbook, int sheetNumber, String[] titleHeader, String titleName ,String orgId, String office, String leader, Map<String, CellStyle> mapColStyle) {
		XSSFSheet xssfSheet = xssfWorkbook.getSheetAt(sheetNumber);
		xssfSheet.addMergedRegion(new CellRangeAddress(0, 0, 0, titleHeader.length - 1));
		CellStyle titleStyle = xssfSheet.getWorkbook().createCellStyle();
		CellStyle titleStyleDate = xssfSheet.getWorkbook().createCellStyle();
		CellStyle no = xssfSheet.getWorkbook().createCellStyle();

		Font fontNo = xssfWorkbook.createFont();
		fontNo.setFontName("Times New Roman");
		fontNo.setFontHeightInPoints((short)11);
		no.setFont(fontNo);
		no.setAlignment(HorizontalAlignment.CENTER);
		no.setVerticalAlignment(VerticalAlignment.CENTER);
		no.setBorderBottom(BorderStyle.THIN);
		no.setBorderTop(BorderStyle.THIN);
		no.setBorderRight(BorderStyle.THIN);
		no.setBorderLeft(BorderStyle.THIN);

		Font fontTitleDate = xssfWorkbook.createFont();
		fontTitleDate.setColor(IndexedColors.BLUE.index);
		fontTitleDate.setFontName("Times New Roman");
		titleStyleDate.setFont(fontTitleDate);

		titleStyle.setAlignment(HorizontalAlignment.CENTER);
		titleStyle.setVerticalAlignment(VerticalAlignment.CENTER);

		Font fontTitle = xssfWorkbook.createFont();
		fontTitle.setColor(IndexedColors.BLUE.index);
		fontTitle.setFontName("Times New Roman");
		fontTitle.setBold(true);
		fontTitle.setFontHeightInPoints((short)20);

		mapColStyle.put("NO",no);


		titleStyle.setFont(fontTitle);
		XSSFRow row1 = xssfSheet.getRow(0);
		XSSFCell cell1 = row1.getCell(0);
		cell1.setCellValue(titleName);
		cell1.setCellStyle(titleStyle);
		
		if (xssfSheet.getRow(1) != null)
			xssfSheet.getRow(1).getCell(0).setCellValue(orgId + " - " + office);
		else xssfSheet.createRow(1).createCell(0).setCellValue(orgId  + " - " + office);

		xssfSheet.getRow(1).getCell(0).setCellStyle(titleStyleDate);
		if (xssfSheet.getRow(2) != null)
			xssfSheet.getRow(2).getCell(0).setCellValue(leader);
		else
			xssfSheet.createRow(2).createCell(0).setCellValue(leader);


		xssfSheet.getRow(2).getCell(0).setCellStyle(titleStyleDate);
		if (xssfSheet.getRow(3) != null)
			xssfSheet.getRow(3).getCell(0).setCellValue("Ngày báo cáo: " + DateUtils.formatDateToString(new Date(), "dd/MM/yyyy"));
		else
			xssfSheet.createRow(3).createCell(0).setCellValue("Ngày báo cáo: " + DateUtils.formatDateToString(new Date(), "dd/MM/yyyy"));
		

		xssfSheet.getRow(3).getCell(0).setCellStyle(titleStyleDate);
		XSSFRow row7;
		if (xssfSheet.getRow(6) != null)
			row7 = xssfSheet.getRow(6);
		else
			row7 = xssfSheet.createRow(6);
		
		CellStyle headerCellStyle = xssfSheet.getWorkbook().createCellStyle();
		headerCellStyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.index);
		headerCellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

		Font font = xssfWorkbook.createFont();
		font.setFontName("Times New Roman");
		font.setBold(true);
		headerCellStyle.setAlignment(HorizontalAlignment.CENTER);
		headerCellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
		headerCellStyle.setWrapText(true);
		headerCellStyle.setFont(font);

		headerCellStyle.setBorderBottom(BorderStyle.THIN);
		headerCellStyle.setBorderTop(BorderStyle.THIN);
		headerCellStyle.setBorderRight(BorderStyle.THIN);
		headerCellStyle.setBorderLeft(BorderStyle.THIN);
		for (int i = 0; i < titleHeader.length; i++) {
			XSSFCell cell4 = row7.getCell(i);
			if (row7.getCell(i) != null)
				cell4 = row7.getCell(i);
			else
				cell4 = row7.createCell(i);
			
			cell4.setCellValue(titleHeader[i]);
			cell4.setCellStyle(headerCellStyle);
		}
	}
    
    private String agentPosition(String agentType, String agentCode, String agentName) {
        return agentType + ": " + agentCode.replace("A", "").replace("B", "").replace("C", "") + " - " + agentName;
    }
    
    @Override
    public CommonSearchWithPagingDto geranateCommonSearch(Object searchDto, String functionCode ) {
        ObjectMapper mapper = new ObjectMapper();
        String stringJsonParam ="";
        try {
            stringJsonParam = mapper.writeValueAsString(searchDto);
        } catch (JsonProcessingException e) {
            logger.error("Exception", e);
        }
        CommonSearchWithPagingDto common = parseJsonToParamSearchService.getSearchCondition(stringJsonParam, functionCode);
        return common;
    }
    
    @Override
    public CmsCommonPagination<PolicyMaturedDetailDto> getDetailPolicyMaturedByGroup(PolicyMaturedSearchDto searchDto, boolean isExport) {
        List<PolicyMaturedDetailDto> datas = new ArrayList<>();
        PolicyMaturedParamDto param = new PolicyMaturedParamDto();
        CmsCommonPagination<PolicyMaturedDetailDto> rs = new CmsCommonPagination<>();
        try {
			param.agentCode=searchDto.getAgentCodeSearch();
            param.orgId=searchDto.getOrgId();
            param.agentGroup=searchDto.getAgentGroup();
            param.page=searchDto.getPage();
            param.pageSize=searchDto.getPageSize();
            param.sort=searchDto.getSearch();
            param.search=searchGroup("POLICY_MATURED", searchDto.getKeyword(), searchDto.getManagerAgentCode(), searchDto.getAgentCode());
            
            try {
            	sqlManagerDb2Service.call(STORE_LIST_POLICY_MATURED_BY_GROUP, param);
    		} catch (Exception e) {
    			logger.error("Exception SQL: ", e);
    		}
            
            rs.setTotalData(param.totalRows);
            datas=param.lstData;
            for (PolicyMaturedDetailDto dto : datas) {
                dto.setParentAll(agentPosition(dto.getManagerAgentType(), dto.getManagerAgentCode().replace("A", "").replace("B", "").replace("C", ""), dto.getManagerAgentName()));
                dto.setAgentAll(agentPosition(dto.getAgentType(), dto.getAgentCode(), dto.getAgentName()));
            }
            rs.setData(datas);
        } catch (Exception e) {
            logger.error("Exception", e);
        }
        return rs;
    }
    
    private String searchGroup(String functionCode, String keyword, Object managerAgentCode, Object agentCode) {
    	if (ObjectUtils.isEmpty(keyword) && ObjectUtils.isEmpty(managerAgentCode) && ObjectUtils.isEmpty(agentCode)) {
    		return null;
    	}
    	
    	String db = "";
		String db12 = "";
		String db34 = "";
		CommonSearchWithPagingDto commonSearch = null;
    	if (ObjectUtils.isNotEmpty(keyword)) {
    		ContractEffectGroupSearchDto searchDto = new ContractEffectGroupSearchDto();
    		searchDto.setKeyword(keyword);
    		searchDto.setFunctionCode(functionCode);
			commonSearch = geranateCommonSearch(searchDto, functionCode);
			String search = commonSearch.getSearch().substring(6, commonSearch.getSearch().length() - 2);
			String searchCodeLv2 = search.replace("LV2_AGENTNAME", "LV2_AGENTCODE");
			String searchTypeLv2 = search.replace("LV2_AGENTNAME", "LV2_AGENTTYPE");
			String searchCodeLv3 = search.replace("LV3_AGENTNAME", "LV3_AGENTCODE");
			String searchTypeLv3 = search.replace("LV3_AGENTNAME", "LV3_AGENTTYPE");
			db = "and (" + search + " or " + searchCodeLv2 + " or " + searchTypeLv2 + " or " + searchCodeLv3 + " or " + searchTypeLv3 + ")";
    	}
    	if (ObjectUtils.isNotEmpty(managerAgentCode)) {
    		ContractEffectGroupSearchDto searchDto = new ContractEffectGroupSearchDto();
    		searchDto.setManagerAgentCode(managerAgentCode);
    		searchDto.setFunctionCode(functionCode);
			commonSearch = geranateCommonSearch(searchDto, functionCode);
			String search = commonSearch.getSearch().substring(6);
			String searchCodeLv2 = search.replace("LV2_AGENTNAME", "LV2_AGENTCODE");
			String searchTypeLv2 = search.replace("LV2_AGENTNAME", "LV2_AGENTTYPE");
			db12 = "and " + search + " or " + searchCodeLv2 + " or " + searchTypeLv2;
    	}
    	if (ObjectUtils.isNotEmpty(agentCode)) {
    		ContractEffectGroupSearchDto searchDto = new ContractEffectGroupSearchDto();
    		searchDto.setManagerAgentCode(agentCode);
    		searchDto.setFunctionCode(functionCode);
			commonSearch = geranateCommonSearch(searchDto, functionCode);
			String search = commonSearch.getSearch().substring(6);
			String searchCodeLv3 = search.replace("LV3_AGENTNAME", "LV3_AGENTCODE");
			String searchTypeLv3 = search.replace("LV3_AGENTNAME", "LV3_AGENTTYPE");
			db34 = "and " + search + " or " + searchCodeLv3 + " or " + searchTypeLv3;
    	}
		return " AND ( 1 = 1 " + db + ")  AND  ( 1 = 1 " + db12 + ") AND  ( 1 = 1 " + db34 + ")";
	}
    
    @Override
    public TotalClaimDto getTotalClaim(String agentCode, String agentGroup, String orgId) {
		List<TotalClaimDto> datas = new ArrayList<>();
    	TotalClaimDto data = new TotalClaimDto();
		
    	try {
			if (StringUtils.equalsIgnoreCase(agentGroup, "FC")) {
				TotalClaimPersonalParam claim = new TotalClaimPersonalParam();
				claim.agentCode=agentCode;
				sqlManagerDb2Service.call(DS_SP_GET_TOTAL_CLAIM_INSURANCE_CONTRACT_PERSONAL, claim);
				if (claim.data.size() > 0) {
					data = claim.data.get(0);	
				}
			} else {
				TotalClaimParam param = new TotalClaimParam();
				param.agentCode=agentCode;
				param.agentGroup=agentGroup;
				param.orgId= orgId;
				param.page=0;
				param.pageSize=1;
				param.search = " AND L.TREE_LEVEL = 0";
				sqlManagerDb2Service.call(STORE_GET_TOTAL_CLAIM, param);
				datas=param.data;
				if(datas.isEmpty()){
					return null;
				}
				
				data.setTotalHandling(datas.stream().map(TotalClaimDto::getTotalHandling).reduce(0, (a, b) -> a + b));
				data.setTotalWaiting(datas.stream().map(TotalClaimDto::getTotalWaiting).reduce(0, (a, b) -> a + b));
				data.setTotalAgreeing(datas.stream().map(TotalClaimDto::getTotalAgreeing).reduce(0, (a, b) -> a + b));
				data.setTotalRejecting(datas.stream().map(TotalClaimDto::getTotalRejecting).reduce(0, (a, b) -> a + b));
				data.setTotalError(datas.stream().map(TotalClaimDto::getTotalError).reduce(0, (a, b) -> a + b));
			}

		} catch (Exception e) {
			logger.error("getTotalClaim: ", e);
		}
		
		return data;
	}
    
    @Override
	public CmsCommonPagination<ClaimGroupDto> getListClaimByGroup(ContractClaimGroupSearchDto searchDto) {
	    CmsCommonPagination<ClaimGroupDto> rs = new CmsCommonPagination<>();
	    ClaimGroupParam param = new ClaimGroupParam();
	    List<ClaimGroupDto> datas = new ArrayList<>();
	    try {
	    	String functionCode = "OFFICE_POLICY_CLAIM";
	    	if ("UM".equals(searchDto.getAgentGroup())) {
				functionCode = "OFFICE_POLICY_CLAIM_UM";
			}
	    	searchDto.setFunctionCode(functionCode);
	    	searchDto.setSearchType("ALL");
	    	ObjectMapper mapper = new ObjectMapper();
			String stringJsonParam ="";
			try {
				stringJsonParam = mapper.writeValueAsString(searchDto);
			} catch (JsonProcessingException e) {
				logger.error("getListClaimByGroup", e);
			}
			CommonSearchWithPagingDto common = parseJsonToParamSearchService.getSearchCondition(stringJsonParam, functionCode);
    		param.agentCode=searchDto.getAgentCodeSearch();
    		param.orgId=searchDto.getOrgId();
    		param.agentGroup=searchDto.getAgentGroup();
    		param.policyType=searchDto.getPolicyType();
    		param.page=searchDto.getPage();
    		param.pageSize=searchDto.getPageSize();
    		param.sort=common.getSort();
    		param.search=common.getSearch();
    		if (param.pageSize == 0) {
    			sqlManagerDb2Service.call(DS_SP_GET_LIST_CLAIM_BY_GROUP_EXPORT, param);
    		} else {
    			sqlManagerDb2Service.call(DS_SP_GET_LIST_CLAIM_BY_GROUP, param);
    		}
    		datas=param.data;
            for (ClaimGroupDto dto : datas) {
                dto.setParentAll(agentPosition(dto.getManagerAgentType(), dto.getManagerAgentCode().replace("A", "").replace("B", "").replace("C", ""), dto.getManagerAgentName()));
                dto.setAgentAll(agentPosition(dto.getAgentType(), dto.getAgentCode(), dto.getAgentName()));
            }
            
    		rs.setTotalData(param.totalRows);
    		rs.setData(datas);
	    } catch (Exception e) {
            logger.error("Exception", e);
        }
		return rs;
	}
    
    @SuppressWarnings({ "unchecked", "rawtypes" })
    @Override
    public ResponseEntity exportListClaimViewBD(TotalInsuranceContractGroupSearchDto dto) {
        ResponseEntity res = null;
        try {
            dto.setPage(0);
            dto.setPageSize(0);
            CmsCommonPagination<TotalClaimGroupDto> common = getListClaimByGroupBD(dto);
            String datePattern = systemConfig.getConfig(SystemConfig.DATE_PATTERN);
            datePattern = "dd/MM/yyyy";
            String templateName = "HSYCQL_cap_phong_ban.xlsx";
            String templatePath = servletContext.getRealPath(CommonConstant.REAL_PATH_TEMPLATE_EXCEL + "/" + templateName);
            String startRow = "A8";
            List<TotalClaimGroupDto> lstdata = common.getData();
            TotalClaimGroupDto root = new TotalClaimGroupDto();
            if(ObjectUtils.isNotEmpty(lstdata)) {
            	 root = lstdata.stream().filter(e->e.getTreeLevel().equals(0)).findFirst().get();
            	 root.setTotal(root.getTotalHandling().add(root.getTotalWaiting().add(root.getTotalAgreeing().add(root.getTotalRejecting().add(root.getTotalError())))));
            }
			List<TotalClaimGroupDto> allData = new ArrayList<>();
			// get lv1
            List<TotalClaimGroupDto> lv1 = lstdata.stream().filter(e->e.getTreeLevel() == 1).collect(Collectors.toList());
			for (TotalClaimGroupDto groupLv1 : lv1) {
				groupLv1.setTotal(groupLv1.getTotalHandling().add(groupLv1.getTotalWaiting().add(groupLv1.getTotalAgreeing().add(groupLv1.getTotalRejecting().add(groupLv1.getTotalError())))));
				allData.add(groupLv1);
				// get lv2
				List<TotalClaimGroupDto> lv2 = lstdata.stream().filter(e->e.getTreeLevel() == 2 
						&& e.getParentAgentCode().equals(groupLv1.getAgentCode()) && e.getOrgParentId().equals(groupLv1.getOrgId())).collect(Collectors.toList());
				allData.addAll(lv2);
			}
			allData.add(root);
            List<ItemColsExcelDto> cols = new ArrayList<>();
            ExportExcelUtil exportExcel = new ExportExcelUtil<>();
            Map<String, String> mapColFormat = null;
            Map<String, Object> setMapColDefaultValue = null;
            String orgId = null;
            if("SO".equalsIgnoreCase(dto.getAgentGroup()) || "GA".equalsIgnoreCase(dto.getAgentGroup())) {
            	orgId = dto.getOrgId();
            }
			AgentInfoDb2 dataz = db2ApiService.getParentByAgentCode(dto.getAgentCode(),dto.getAgentGroup(),orgId);
            // do export
            try (XSSFWorkbook xssfWorkbook = exportExcel.getXSSFWorkbook(templatePath);) {
                Map<String, CellStyle> mapColStyle = new HashMap<>();
                
                String titleName="HỒ SƠ YÊU CẦU QUYỀN LỢI CẤP PHÒNG/BAN";
                String[] titleHeader = null;

                titleHeader = new String[] {"", ""
                        , "Hồ sơ đang xử lý"
                        , "Hồ sơ chờ bổ sung thông tin"
                        , "Hồ sơ đã được đồng ý chi trả"
                        , "Hồ sơ bị từ chối"
                        , "Hồ sơ không hợp lệ"
                        , "Tổng cộng"};
                List<String> listHeader = Arrays.asList(titleHeader);
                templateName+="_";
                if(dto.getAgentGroup().equalsIgnoreCase("CAO")
                   || dto.getAgentGroup().equalsIgnoreCase("AH")
                   || dto.getAgentGroup().equalsIgnoreCase("TH")
                   || dto.getAgentGroup().equalsIgnoreCase("RH")
                   || dto.getAgentGroup().equalsIgnoreCase("OH")
                   || dto.getAgentGroup().equalsIgnoreCase("SO")
                   || dto.getAgentGroup().equalsIgnoreCase("GA")){
                    if(dto.getAgentGroup().equalsIgnoreCase("CAO")) {
                        listHeader.set(0, "BDTH");
                        listHeader.set(1, "BDAH");
                        templateName +="CAO";
                        ImportExcelUtil.setListColumnExcel(ListPolicyClaimCaoEnum.class, cols);
                    }
                    else if(dto.getAgentGroup().equalsIgnoreCase("TH")) {
                        listHeader.set(0, "BDAH");
                        listHeader.set(1, "BDOH");
                        templateName +="TH";
                        ImportExcelUtil.setListColumnExcel(ListPolicyClaimThEnum.class, cols);
                    }
                    else if(dto.getAgentGroup().equalsIgnoreCase("AH")) {
                        listHeader.set(0, "BDOH");
                        listHeader.set(1, "Văn phòng/ Tổng đại lý");
                        templateName +="AH";
                        ImportExcelUtil.setListColumnExcel(ListPolicyClaimAhEnum.class, cols);
                    }
                    else if(dto.getAgentGroup().equalsIgnoreCase("OH")) {
                        listHeader.set(0, "Văn phòng/ Tổng đại lý");
                        listHeader.set(1, "Trưởng Phòng");
                        templateName +="OH";
                        ImportExcelUtil.setListColumnExcel(ListPolicyClaimOhEnum.class, cols);
                    }
                    else if(dto.getAgentGroup().equalsIgnoreCase("GA")) {
                        listHeader.set(0, "Văn phòng/ Tổng đại lý");
                        listHeader.set(1, "Trưởng Phòng");
                        templateName +="GA";
                        ImportExcelUtil.setListColumnExcel(ListPolicyClaimOhEnum.class, cols);
                    }
                    else if(dto.getAgentGroup().equalsIgnoreCase("SO")) {
                        listHeader.set(0, "Văn phòng/ Tổng đại lý");
                        listHeader.set(1, "Trưởng Phòng");
                        templateName +="SO";
                        ImportExcelUtil.setListColumnExcel(ListPolicyClaimOhEnum.class, cols);
                    }
                }
                // titleName = "DANH SÁCH HỢP ĐỒNG CẤP PHÒNG BAN";
                titleHeader = listHeader.toArray(new String[0]);
                setDataHeaderToXSSFWorkbookSheet(xssfWorkbook, 0, titleHeader, titleName ,dataz.getOrgId(), dataz.getOrgName(),dataz.getAgentType() +": "+ dataz.getAgentCode().replace(dataz.getOrgId(), "").replace("A", "").replace("B", "").replace("C", "") + " - " + dataz.getAgentName(), mapColStyle);
				String repo = systemConfig.getConfig(systemConfig.REPO_UPLOADED_MAIN);
				String path = systemConfig.getPhysicalPathById(repo, null); //path up service
                res =  exportExcel.doExportExcelHeaderWithColFormatRestUpService(xssfWorkbook, 0, null, allData, TotalClaimGroupDto.class
                        , cols, datePattern, startRow, mapColFormat, mapColStyle,
                        setMapColDefaultValue, null, true, templateName, true,path);
            } catch (Exception e) {
                logger.error("##doExport##", e);
            }
          } catch (Exception e) {
              logger.error("##exportListViewBD##", e);
        }
        return res;
    }
    
    @Override
	public CmsCommonPagination<TotalClaimGroupDto> getListClaimByGroupBD(TotalInsuranceContractGroupSearchDto searchDto) {
	    TotalClaimGroupParamDto param = new TotalClaimGroupParamDto();
	    CmsCommonPagination<TotalClaimGroupDto> rs = new CmsCommonPagination<>();
	    List<TotalClaimGroupDto> datas = new ArrayList<>();
	    try {

	        searchDto.setFunctionCode("GROUP_"+searchDto.getAgentGroup());
	        TotalInsuranceContractGroupSearchDto searchBd1 = objectMapper.convertValue(searchDto, TotalInsuranceContractGroupSearchDto.class);
	        TotalInsuranceContractGroupSearchDto searchBd2 = objectMapper.convertValue(searchDto, TotalInsuranceContractGroupSearchDto.class);
	        if (searchDto.getAgentGroup().equals("OH")) {
	        	searchDto.setOrgId("");
	        }
    		param.agentCode=searchDto.getAgentCode();
    		param.orgId= searchDto.getOrgId();
    		if (searchDto.getAgentGroup().equals("SO") || searchDto.getAgentGroup().equals("GA")) {
        		param.agentGroup="OH";
	        } else {
	    		param.agentGroup=searchDto.getAgentGroup();
	        }
    		param.page = searchDto.getPage();
    		param.pageSize=searchDto.getPageSize();
    		param.sort = searchDto.getSort();
			param.search = reportBusinessResultGaService.searchAdvance(searchDto,searchDto.getAgentGroup(),searchDto.getKeyword(),searchBd1,searchBd2);

			sqlManagerDb2Service.call(STORE_GET_TOTAL_CLAIM, param);
    		datas = param.data;

    		List<TotalClaimGroupDto> lstBdTh = datas.stream()
					.filter(e->StringUtils.equalsIgnoreCase(e.getParentGroup(), "CAO"))
					.filter(e->!e.getTreeLevel().equals(0))
					.collect(Collectors.toList());

    		if(lstBdTh != null && !lstBdTh.isEmpty()){
    			TotalClaimGroupDto north = lstBdTh.stream()
						.filter(e-> StringUtils.equalsIgnoreCase("North", e.getOrgName()))
						.findFirst().orElse(null);
    			TotalClaimGroupDto central = lstBdTh.stream()
						.filter(e-> StringUtils.equalsIgnoreCase("Central", e.getOrgName()))
						.findFirst().orElse(null);
    			TotalClaimGroupDto south = lstBdTh.stream()
						.filter(e-> StringUtils.equalsIgnoreCase("South", e.getOrgName()))
						.findFirst().orElse(null);
    			TotalClaimGroupDto other = lstBdTh.stream()
						.filter(e-> StringUtils.equalsIgnoreCase("Other Territory", e.getOrgName()))
						.findFirst().orElse(null);
				//remove nhung dong co parent_group = CAO
				datas.removeIf(e->StringUtils.equalsIgnoreCase(e.getParentGroup(), "CAO") && (StringUtils.equalsIgnoreCase("North", e.getOrgName())
						|| StringUtils.equalsIgnoreCase("Central", e.getOrgName())
						|| StringUtils.equalsIgnoreCase("South", e.getOrgName())
						|| StringUtils.equalsIgnoreCase("Other Territory", e.getOrgName())));
				//add lại nhung dong co  parent_group = CAO theo thu tu: north-central-south-other

				int i =0;
				if(ObjectUtils.isNotEmpty(north)){ datas.add( 1+i,north); i+=1;}
				if(ObjectUtils.isNotEmpty(central)){ datas.add(1+i,central);i+=1;}
				if(ObjectUtils.isNotEmpty(south)){ datas.add(1+i,south);i+=1;}
				if(ObjectUtils.isNotEmpty(other)){ datas.add(1+i,other);}
			}
    		
    		sumPolicyClaim(datas, 0);
			groupAgentForClaim(searchDto, datas);
    		for (TotalClaimGroupDto dto : datas) {
    		    dto.setAgentAll(agentPosition(dto.getAgentType(), dto.getAgentCode(), dto.getAgentName()));
				mapAgentForClaim(dto, searchDto.getAgentGroup(), dto.getTreeLevel() == 0);
            }
			
    		rs.setTotalData(param.totalRows != null ? param.totalRows : 0);
    	    rs.setData(datas);
	    } catch (Exception e) {
            logger.error("Exception", e);
        }
		return rs;
	}
    
    private void sumPolicyClaim(List<TotalClaimGroupDto> datas, int treeLevel){
		if(!datas.isEmpty()){
			List<TotalClaimGroupDto> lstRoot = datas.stream().filter(e -> e.getTreeLevel().equals(new Integer(treeLevel))).collect(Collectors.toList());
			List<String> adresses = lstRoot.stream()
					.map(TotalClaimGroupDto::getOrgName)
					.collect(Collectors.toList());
			String orgNameNew = String.join(", ", adresses);
			datas.removeIf(e -> e.getTreeLevel().equals(new Integer(treeLevel)));

			if(!lstRoot.isEmpty()){
				TotalClaimGroupDto rootTmp = lstRoot.get(0);
				if(treeLevel == 1 && "OH".equalsIgnoreCase(rootTmp.getChildGroup())) {
					rootTmp.setOrgCode(null);
					rootTmp.setOrgId(rootTmp.getAgentCode());
				}
				rootTmp.setOrgNameNew(orgNameNew);
				rootTmp.setTotalHandling(lstRoot.stream().map(TotalClaimGroupDto :: getTotalHandling).reduce(new BigDecimal(0), (a, b) -> a.add(b)));
				rootTmp.setTotalWaiting(lstRoot.stream().map(TotalClaimGroupDto :: getTotalWaiting).reduce(new BigDecimal(0), (a, b) -> a.add(b)));
				rootTmp.setTotalAgreeing(lstRoot.stream().map(TotalClaimGroupDto :: getTotalAgreeing).reduce(new BigDecimal(0), (a, b) -> a.add(b)));
				rootTmp.setTotalRejecting(lstRoot.stream().map(TotalClaimGroupDto :: getTotalRejecting).reduce(new BigDecimal(0), (a, b) -> a.add(b)));
				rootTmp.setTotalError(lstRoot.stream().map(TotalClaimGroupDto :: getTotalError).reduce(new BigDecimal(0), (a, b) -> a.add(b)));
				
				datas.add( rootTmp);
			}
		}
	}

	private void groupAgentForClaim(TotalInsuranceContractGroupSearchDto searchDto, List<TotalClaimGroupDto> datas) {
		if ("AH".equals(searchDto.getAgentGroup())) {
			List<TotalClaimGroupDto> listTree2 = new ArrayList<TotalClaimGroupDto>();
			List<TotalClaimGroupDto> lstTree1 = datas.stream().filter(e -> e.getTreeLevel().equals(new Integer(1))).collect(Collectors.toList());
			if(CollectionUtils.isNotEmpty(lstTree1)) {
				lstTree1.forEach(item -> {
					List<TotalClaimGroupDto> lstTree2 = datas.stream()
							.filter(e -> e.getTreeLevel().equals(new Integer(2)) && e.getOrgParentId().equals(item.getOrgId()) && e.getParentAgentCode().equals(item.getAgentCode()))
							.collect(Collectors.toList());
					if (CollectionUtils.isNotEmpty(lstTree2)) {

						if ("TH".equals(searchDto.getAgentGroup())) {
							Map<String, List<TotalClaimGroupDto>> maplv2Group = lstTree2.stream()
									.filter(e -> e.getTreeLevel() == 2)
									.collect(Collectors.groupingBy(bd ->bd.getAgentCode()));
							for (Map.Entry<String, List<TotalClaimGroupDto>> entry : maplv2Group.entrySet()) {
								String key = entry.getKey();
								if(StringUtils.isNotEmpty(key) && CollectionUtils.isNotEmpty( entry.getValue())) {
									sumPolicyClaim(entry.getValue(), 2);
									listTree2.addAll(entry.getValue());
								}
							}
						} else {
							lstTree2.forEach(item2 -> {
								item2.setOrgParentId(item.getAgentCode());
							});
							listTree2.addAll(lstTree2);
						}
					}

				});
				datas.removeIf(e -> e.getTreeLevel().equals(new Integer(2)));
				Map<String, List<TotalClaimGroupDto>> maplv1 = lstTree1.stream()
						.filter(e -> e.getTreeLevel() == 1)
						.collect(Collectors.groupingBy(bd ->bd.getAgentCode()));
				if (CollectionUtils.isNotEmpty(maplv1.entrySet())) {
					datas.removeIf(e -> e.getTreeLevel().equals(new Integer(1)));
					for (Map.Entry<String, List<TotalClaimGroupDto>> entry : maplv1.entrySet()) {
						String key = entry.getKey();
						if(StringUtils.isNotEmpty(key) && CollectionUtils.isNotEmpty( entry.getValue())) {
							sumPolicyClaim(entry.getValue(), 1);
							datas.addAll(entry.getValue());
						}
					}
					datas.addAll(listTree2);
				}

			}
		}
	}
	
	private void mapAgentForClaim(TotalClaimGroupDto data, String agentGroup, boolean first) {
		if (StringUtils.isNotEmpty(data.getChildGroup())) {
			if (first) {
				switch (data.getChildGroup()) {
					case "CAO":
						data.setBdthCode(data.getAgentCode());
						data.setBdthName("Tổng cộng");
						data.setBdthType(data.getAgentType());
						break;
					case "TH":
						data.setBdahCode(data.getAgentCode());
						data.setBdahName("Tổng cộng");
						data.setBdahType(data.getAgentType());
						break;
					case "AH":
						data.setBdohCode(data.getAgentCode());
						data.setBdohName("Tổng cộng");
						data.setBdohType(data.getAgentType());
						break;
					case "RH":
						data.setBdohCode(data.getAgentCode());
						data.setBdohName("Tổng cộng");
						data.setBdohType(data.getAgentType());
						break;
					case "OH":
						data.setGaCode(data.getAgentCode());
						data.setGaName("Tổng cộng");
						data.setGaType(data.getAgentType());
						break;
					case "GA":
						data.setBranchCode(data.getAgentCode());
						data.setBranchName("Tổng cộng");
						data.setBranchType(data.getAgentType());
						break;
					case "SO":
						data.setBranchCode(data.getAgentCode());  
						data.setBranchName("Tổng cộng");
						data.setBranchType(data.getAgentType());
					case "BM":
						data.setBranchCode(data.getAgentCode());
						data.setBranchName("Tổng cộng");
						data.setBranchType(data.getAgentType());
						break;
					case "UM":
						data.setUnitCode(data.getAgentCode());
						data.setUnitName("Tổng cộng");
						data.setUnitType(data.getAgentType());
					default:
						data.setAgentCode(data.getAgentCode());
						break;
				}
			} else {
				switch (data.getChildGroup()) {
					case "CAO":
						data.setCaoCode(data.getAgentCode());
						data.setCaoName(data.getOrgId() + ": " + data.getAgentCode() + "-" + data.getAgentName());
						data.setCaoType(data.getAgentType());
						break;
					case "TH":
						data.setBdthCode(data.getAgentCode());
						data.setBdthName(data.getOrgId() + ": " + data.getAgentCode() + "-" + data.getAgentName());
						data.setBdthType(data.getAgentType());
						break;
					case "AH":
						data.setBdahCode(data.getAgentCode());
						data.setBdahName(data.getOrgId() + ": " + data.getAgentCode() + "-" + data.getAgentName());
						data.setBdahType(data.getAgentType());
						break;
					case "RH":
						data.setBdrhCode(data.getAgentCode());
						data.setBdrhName(data.getOrgId() + ": " + data.getAgentCode() + "-" + data.getAgentName());
						data.setBdrhType(data.getAgentType());
						break;
					case "OH":
						String orgId = data.getAgentType();
						if (data.getTreeLevel() == 2) {
							orgId = data.getOrgId();
							data.setOrgCode(data.getOrgId());
						}
						data.setBdohName(orgId + "-" + data.getAgentCode() + "-" + data.getAgentName());
						data.setBdohCode(data.getAgentCode());
						if (StringUtils.contains(data.getAgentName(), "Dummy Sales")) {
							data.setBdohName(data.getAgentName());

						}
						data.setBdohType(data.getAgentType());
						break;
					case "GA":
						data.setGaCode(data.getAgentCode());
						data.setGaName(data.getOrgId() + "-" + data.getAgentCode() + "-" + data.getAgentName());
						if (StringUtils.contains(data.getAgentName(), "Dummy Sales")) {
							data.setGaName(data.getAgentName());

						}
						data.setGaType(data.getAgentType());
						break;
					case "SO":
						data.setGaCode(data.getAgentCode());
						data.setGaName(data.getOrgId() + "-" + data.getAgentCode() + "-" + data.getAgentName());
						if (StringUtils.contains(data.getAgentName(), "Dummy Sales")) {
							data.setGaName(data.getAgentName());

						}
						data.setGaType(data.getAgentType());
						break;
					case "BM":
						data.setBranchCode(data.getAgentCode());
						data.setBranchName(data.getAgentType() + ": " + data.getAgentCode().replace(data.getOrgId(), "") + "-" + data.getAgentName());
						if (StringUtils.contains(data.getAgentName(), "Dummy Sales")) {
							data.setBranchName(data.getAgentName());

						}
						data.setBranchType(data.getAgentType());
						break;
					case "UM":
						data.setUnitCode(data.getAgentCode());
						data.setUnitName(data.getAgentType() + ": " + data.getAgentCode().replace("A", "").replace("B", "").replace("C", "") + "-" + data.getAgentName());
						if (StringUtils.contains(data.getAgentName(), "Dummy Sales")) {
							data.setUnitName(data.getAgentName());

						}
					default:
						data.setAgentCode(data.getAgentCode());
						break;
				}
			}
		}
	}
}
