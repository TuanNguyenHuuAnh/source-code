package vn.com.unit.ep2p.service.impl;

import jp.sf.amateras.mirage.annotation.In;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.CellStyle;
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
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.ctc.wstx.util.StringUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import vn.com.unit.cms.core.dto.CmsCommonPagination;
import vn.com.unit.cms.core.dto.CommonSearchWithPagingDto;
import vn.com.unit.cms.core.module.agent.dto.InfoAgentDto;
import vn.com.unit.cms.core.module.agent.dto.InfoAgentPagingParam;
import vn.com.unit.cms.core.module.candidate.dto.TemporaryCandidateDto;
import vn.com.unit.cms.core.module.customerManagement.dto.ContractEffectGroupSearchDto;
import vn.com.unit.cms.core.module.customerManagement.dto.OfficeInsuranceDto;
import vn.com.unit.cms.core.module.customerManagement.dto.OfficeInsuranceSearchDto;
import vn.com.unit.cms.core.module.report.dto.ReportActiveContractMonthDto;
import vn.com.unit.cms.core.module.report.dto.ReportGroupDetailDto;
import vn.com.unit.cms.core.module.report.dto.ReportGroupDetailSearchDto;
import vn.com.unit.cms.core.module.report.dto.ReportResultViewGADTabPremiumDto;
import vn.com.unit.cms.core.module.reportGroup.*;
import vn.com.unit.cms.core.module.reportGroup.dto.pram.*;
import vn.com.unit.cms.core.module.reportGroup.dto.search.ReportGroupSearchDetailDto;
import vn.com.unit.cms.core.module.reportGroup.dto.search.ReportGroupSearchDto;
import vn.com.unit.cms.core.module.result.dto.ResultDto;
import vn.com.unit.db.service.SqlManagerDb2Service;
import vn.com.unit.core.config.SystemConfig;
import vn.com.unit.ep2p.enumdef.*;
import vn.com.unit.ep2p.service.ApiReportGroupService;
import vn.com.unit.ep2p.service.OfficePolicyService;
import vn.com.unit.ep2p.service.ParseJsonToParamSearchService;
import vn.com.unit.imp.excel.constant.CommonConstant;
import vn.com.unit.imp.excel.dto.ItemColsExcelDto;
import vn.com.unit.imp.excel.utils.DateUtils;
import vn.com.unit.imp.excel.utils.ExportExcelUtil;
import vn.com.unit.imp.excel.utils.ImportExcelUtil;

import javax.servlet.ServletContext;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional(transactionManager = "transactionManagerDb2", readOnly = true, rollbackFor = Exception.class)
public class ApiReportGroupServiceImpl implements ApiReportGroupService {

	@Autowired
	private SystemConfig systemConfig;

	@Autowired
	private ServletContext servletContext;
	@Autowired
	ParseJsonToParamSearchService parseJsonToParamSearchService;
	@Autowired
	protected ObjectMapper objectMapper;

	@Autowired
	@Qualifier("sqlManageDb2Service")
	private SqlManagerDb2Service sqlManagerDb2Service;

	private static final String SP_GET_LIST_REPORT_GROUP_TOTAL = "RPT_ODS.DS_SP_GET_REPORTING_ACTIVITY_MANPOWER_PRIMEUM";
	private static final String DS_SP_GET_REPORTING_ACTIVITY_SUMMARY = "RPT_ODS.DS_SP_GET_REPORTING_ACTIVITY_SUMMARY";
	private static final String SP_GET_LIST_REPORT_GROUP_TOTAL_BM_UM = "RPT_ODS.DS_SP_GET_REPORTING_ACTIVITY_LEADER_SUMMARY";
	private static final String SP_GET_LIST_REPORT_GROUP_MANPOWER = "RPT_ODS.DS_SP_GET_REPORTING_ACTIVITY_GROUP_MANPOWER";
	private static final String SP_GET_LIST_REPORT_GROUP_MANPOWER_DETAIL = "RPT_ODS.DS_SP_GET_REPORTING_ACTIVITY_GROUP_MANPOWER";
	private static final String SP_GET_LIST_REPORT_GROUP_MANPOWER_DETAIL_BM_UM = "RPT_ODS.DS_SP_GET_REPORTING_ACTIVITY_LEADER_MANPOWER";
	private static final String SP_GET_LIST_REPORT_GROUP_PREMIUM = "RPT_ODS.DS_SP_GET_REPORTING_ACTIVITY_GROUP_PREMIUM";
	private static final String SP_GET_LIST_REPORT_GROUP_PREMIUM_DETAIL = "RPT_ODS.DS_SP_GET_REPORTING_ACTIVITY_GROUP_PREMIUM";
	private static final String SP_GET_LIST_REPORT_GROUP_PREMIUM_DETAIL_BM_UM = "RPT_ODS.DS_SP_GET_REPORTING_ACTIVITY_LEADER_PREMIUM";
	private static final String SP_GET_LIST_REPORT_TARGET_ACHIEVEMENT_SALE = "";
	private static final String SP_GET_LIST_REPORT_TARGET_ACHIEVEMENT_COMPARE = "";
	private static final String SP_GET_LIST_REPORT_TARGET_ACHIEVEMENT_MISSING = "";
	private static final String SP_GET_LIST_REPORT_TARGET_ACHIEVEMENT_PAY_FEES = "";

	private static final String SP_GET_LIST_REPORT_TARGET = "";

	private static final org.slf4j.Logger logger = LoggerFactory.getLogger(ApiReportGroupServiceImpl.class);

	@Autowired
	OfficePolicyService officePolicyService;

	@Override
	public SystemConfig getSystemConfig() {
		return systemConfig;
	}

	@Override
	public ReportGroupTotalDto getListReportGroupTotal(ReportGroupSearchDto searchDto) {
		
		if (searchDto.getDataType().equalsIgnoreCase("QTD") && StringUtils.isNotEmpty(String.valueOf(searchDto.getQtd()))) {
			Integer qtd =  searchDto.getQtd();
			searchDto.setMonth(qtd.toString());
		}
		searchDto.setYyyyMM(formatYyyyMM(searchDto.getYear(), searchDto.getMonth()));
		
		ReportGroupResultTotalPram param = new ReportGroupResultTotalPram();
		param.agentCode = searchDto.getAgentCode();
		param.orgCode = searchDto.getOrgCode();
		param.agentGroup = searchDto.getAgentGroup();
		param.yyyyMM = searchDto.getYyyyMM();
		param.dataType = searchDto.getDataType();
		param.page = searchDto.getPage();
		param.pageSize = searchDto.getPageSize();
		param.sort = null;
		param.search = "";
		sqlManagerDb2Service.call(DS_SP_GET_REPORTING_ACTIVITY_SUMMARY, param);
		ReportGroupTotalDto data = new ReportGroupTotalDto();
		if (CollectionUtils.isNotEmpty(param.data)) {
			data.setCountBdthCode (param.data.stream().filter(x -> x.getCountBdthCode() != null).map(ReportGroupTotalDto:: getCountBdthCode).reduce(0,(a, b) -> a + b));
			data.setCountBdahCode (param.data.stream().filter(x -> x.getCountBdahCode() != null).map(ReportGroupTotalDto:: getCountBdahCode).reduce(0,(a, b) -> a + b));
			data.setCountBdrhCode (param.data.stream().filter(x -> x.getCountBdrhCode() != null).map(ReportGroupTotalDto:: getCountBdrhCode).reduce(0,(a, b) -> a + b));
			data.setCountBdohCode (param.data.stream().filter(x -> x.getCountBdohCode() != null).map(ReportGroupTotalDto:: getCountBdohCode).reduce(0,(a, b) -> a + b));
			data.setCountGadCode (param.data.stream().filter(x -> x.getCountGadCode() != null).map(ReportGroupTotalDto:: getCountGadCode).reduce(0,(a, b) -> a + b));
			data.setCountOfficeCode (param.data.stream().filter(x -> x.getCountOfficeCode() != null).map(ReportGroupTotalDto:: getCountOfficeCode).reduce(0,(a, b) -> a + b));
			data.setCountFcAgentcode (param.data.stream().filter(x -> !isNullOrZero(x.getCountFcAgentcode())).map(ReportGroupTotalDto:: getCountFcAgentcode).reduce(new BigDecimal(0),(a, b) -> a.add(b)));
			data.setCountUmAgentcode (param.data.stream().filter(x -> !isNullOrZero(x.getCountUmAgentcode())).map(ReportGroupTotalDto:: getCountUmAgentcode).reduce(new BigDecimal(0),(a, b) -> a.add(b)));
			data.setCountUmTypeAgentcode (param.data.stream().filter(x -> !isNullOrZero(x.getCountUmTypeAgentcode())).map(ReportGroupTotalDto:: getCountUmTypeAgentcode).reduce(new BigDecimal(0),(a, b) -> a.add(b)));
			data.setCountNewRecruitment(param.data.stream().filter(x -> !isNullOrZero(x.getCountNewRecruitment())).map(ReportGroupTotalDto:: getCountNewRecruitment).reduce(new BigDecimal(0),(a, b) -> a.add(b)));
			data.setCountActive(param.data.stream().filter(x -> !isNullOrZero(x.getCountActive())).map(ReportGroupTotalDto:: getCountActive).reduce(new BigDecimal(0),(a, b) -> a.add(b)));
			data.setCountReinstate(param.data.stream().filter(x -> !isNullOrZero(x.getCountReinstate())).map(ReportGroupTotalDto:: getCountReinstate).reduce(new BigDecimal(0),(a, b) -> a.add(b)));
			data.setSumFyp(param.data.stream().filter(x -> !isNullOrZero(x.getSumFyp())).map(ReportGroupTotalDto:: getSumFyp).reduce(new BigDecimal(0),(a, b) -> a.add(b)));
			data.setSumRyp(param.data.stream().filter(x -> !isNullOrZero(x.getSumRyp())).map(ReportGroupTotalDto:: getSumRyp).reduce(new BigDecimal(0),(a, b) -> a.add(b)));
		}
		return data;
	}

	@Override
	public CmsCommonPagination<ReportGroupResultPremiumDto> getListReportGroupPremium(
			ReportGroupSearchDetailDto searchDto) {
		if (searchDto.getDataType().equalsIgnoreCase("QTD") && StringUtils.isNotEmpty(String.valueOf(searchDto.getQtd()))) {
			Integer qtd =  searchDto.getQtd();
			searchDto.setMonth(qtd.toString());
		}
		searchDto.setYyyyMM(formatYyyyMM(searchDto.getYear(), searchDto.getMonth()));
		ReportGroupResultPremiumPram param = new ReportGroupResultPremiumPram();
		param.agentCode = searchDto.getAgentCode();
		if(searchDto.getAgentGroup().equals("SO") || searchDto.getAgentGroup().equals("GA") )
			param.orgCode = searchDto.getOrgCode();
		else param.orgCode =null;
		param.agentGroup = searchDto.getAgentGroup();
		param.yyyyMM = searchDto.getYyyyMM();
		param.dataType = searchDto.getDataType();
		param.dataLevel = "ALL";
		param.page = searchDto.getPage();
		param.pageSize = searchDto.getPageSize();
		param.sort = searchDto.getSort();
		param.search = "AND TREE_LEVEL IN (0,1)";
		sqlManagerDb2Service.call(SP_GET_LIST_REPORT_GROUP_PREMIUM, param);
		List<ReportGroupResultPremiumDto> datas = param.data;

		List<ReportGroupResultPremiumDto> lv0= datas.stream().filter(x->x.getTreeLevel()== 0).collect(Collectors.toList());
		datas.removeAll(lv0);
		
		 if(!datas.isEmpty()){
//		 	if(StringUtils.equalsIgnoreCase(searchDto.getAgentGroup(),"CAO")){
//				List<ReportGroupResultPremiumDto> dataCao = new ArrayList<>();
//
//				datas.stream()
//						.filter(x ->x.getOrgName().equalsIgnoreCase("North"))
//						.findAny().ifPresent(dataCao::add);
//				datas.stream()
//						.filter(x ->x.getOrgName().equalsIgnoreCase("Central"))
//						.findAny().ifPresent(dataCao::add);
//				datas.stream()
//						.filter(x ->x.getOrgName().equalsIgnoreCase("South"))
//						.findAny().ifPresent(dataCao::add);
//				datas.stream()
//						.filter(x ->!x.getOrgName().equalsIgnoreCase("South"))
//						.filter(x ->!x.getOrgName().equalsIgnoreCase("Central"))
//						.filter(x ->!x.getOrgName().equalsIgnoreCase("South"))
//						.findAny().ifPresent(dataCao::add);
//
//				datas.clear();
//				datas.addAll(dataCao);
//			}
				ReportGroupResultPremiumDto grandTotal = new ReportGroupResultPremiumDto();
				grandTotal.setOrgName("Tổng cộng");
				grandTotal.setFypTarget(datas.stream().filter(x-> ObjectUtils.isNotEmpty(x.getFypTarget())).map(ReportGroupResultPremiumDto :: getFypTarget).reduce(new BigDecimal(0), (a, b) -> a.add(b)));
				grandTotal.setFyp(datas.stream().filter(x-> ObjectUtils.isNotEmpty(x.getFyp())).map(ReportGroupResultPremiumDto :: getFyp).reduce(new BigDecimal(0), (a, b) -> a.add(b)));

				grandTotal.setRypTarget(datas.stream().filter(x-> ObjectUtils.isNotEmpty(x.getRypTarget())).map(ReportGroupResultPremiumDto :: getRypTarget).reduce(new BigDecimal(0), (a, b) -> a.add(b)));
				grandTotal.setRyp(datas.stream().filter(x-> ObjectUtils.isNotEmpty(x.getRyp())).map(ReportGroupResultPremiumDto :: getRyp).reduce(new BigDecimal(0), (a, b) -> a.add(b)));

				grandTotal.setPolicyCountTarget(datas.stream().filter(x-> ObjectUtils.isNotEmpty(x.getPolicyCountTarget())).map(ReportGroupResultPremiumDto :: getPolicyCountTarget).reduce(new BigDecimal(0), (a, b) -> a.add(b)));
				grandTotal.setPolicyCount(datas.stream().filter(x-> ObjectUtils.isNotEmpty(x.getPolicyCount())).map(ReportGroupResultPremiumDto :: getPolicyCount).reduce(new BigDecimal(0), (a, b) -> a.add(b)));

				datas.add(grandTotal);

			 for (ReportGroupResultPremiumDto item : datas) {
				 if(StringUtils.equalsIgnoreCase(searchDto.getAgentGroup(),"OH")){
					 item.setAgentName(item.getGadName());
				 }

				 if (item.getFyp() != null && item.getFypTarget() != null && item.getFyp().intValue() != 0  && item.getFypTarget().intValue() != 0 ) {
					 item.setRateOfReachingFyp(item.getFyp().divide(item.getFypTarget()).setScale(0,RoundingMode.HALF_UP));
				 } else {
					 item.setRateOfReachingFyp(BigDecimal.valueOf(0));
				 }
				 if (item.getRyp() != null && item.getRypTarget() != null && item.getRyp().intValue() != 0
						 && item.getRypTarget().intValue() != 0 ) {
					 item.setRateOfReachingRyp(item.getRyp().divide(item.getRypTarget()).setScale(0,RoundingMode.HALF_UP));
				 } else {
					 item.setRateOfReachingRyp(BigDecimal.valueOf(0));
				 }
				 if (item.getPolicyCount() != null && item.getPolicyCountTarget() != null && item.getPolicyCount().intValue() != 0
						 && item.getPolicyCountTarget().intValue() != 0 ) {
					 item.setInsuranceContractRatio(item.getPolicyCount().divide(item.getPolicyCountTarget()).setScale(0,RoundingMode.HALF_UP));
				 } else {
					 item.setInsuranceContractRatio(BigDecimal.valueOf(0));
				 }

				 if(!isNullOrZero(item.getFyp()))
					 item.setFyp(item.getFyp().setScale(0,RoundingMode.HALF_UP));
				 if(!isNullOrZero(item.getRyp()))
					 item.setRyp(item.getRyp().setScale(0,RoundingMode.HALF_UP));
				 if(!isNullOrZero(item.getFyp()))
					 item.setFyp(item.getFyp().setScale(0,RoundingMode.HALF_UP));
				 if(!isNullOrZero(item.getPolicyCount()))
					 item.setPolicyCount(item.getPolicyCount().setScale(0,RoundingMode.HALF_UP));

			 }

		 }

		CmsCommonPagination<ReportGroupResultPremiumDto> resultData = new CmsCommonPagination<>();
		resultData.setData(datas);
		resultData.setTotalData(param.totalData);
		return resultData;
	}

	@Override
	public CmsCommonPagination<ReportGroupResultManPower2Dto> getListReportGroupManPower(
			ReportGroupSearchDetailDto searchDto) {
		if (searchDto.getDataType().equalsIgnoreCase("QTD") && StringUtils.isNotEmpty(String.valueOf(searchDto.getQtd()))) {
			Integer qtd = searchDto.getQtd();
			searchDto.setMonth(qtd.toString());
		}
		searchDto.setYyyyMM(formatYyyyMM(searchDto.getYear(), searchDto.getMonth()));
		ReportGroupResultManpowerPram param = new ReportGroupResultManpowerPram();
		param.agentCode = searchDto.getAgentCode();
		if(searchDto.getAgentGroup().equals("SO") || searchDto.getAgentGroup().equals("GA") )
			param.orgCode = searchDto.getOrgCode();
		else param.orgCode =null;
		param.agentGroup = searchDto.getAgentGroup();
		param.yyyyMM = searchDto.getYyyyMM();
		param.dataType = searchDto.getDataType();
		param.dataLevel = "ALL";
		param.page = searchDto.getPage();
		param.pageSize = searchDto.getPageSize();
		param.sort = searchDto.getSort();
		param.search = "AND TREE_LEVEL IN (0,1)";
		sqlManagerDb2Service.call(SP_GET_LIST_REPORT_GROUP_MANPOWER, param);
		List<ReportGroupResultManPower2Dto> datas = param.data;

		List<ReportGroupResultManPower2Dto> lv0= datas.stream().filter(x->x.getTreeLevel()== 0).collect(Collectors.toList());
		datas.removeAll(lv0);
		if(ObjectUtils.isNotEmpty(datas)){
			ReportGroupResultManPower2Dto grandTotal = new ReportGroupResultManPower2Dto();
			grandTotal.setOrgName("Tổng cộng");
			grandTotal.setCountActive(datas.stream().filter(x -> !isNullOrZero(x.getCountActive())).map(ReportGroupResultManPower2Dto :: getCountActive).reduce(new BigDecimal(0),(a, b) -> a.add(b)));
			grandTotal.setCountFcTypeAgentcode(datas.stream().filter(x -> !isNullOrZero(x.getCountFcTypeAgentcode())).map(ReportGroupResultManPower2Dto :: getCountFcTypeAgentcode).reduce(new BigDecimal(0),(a, b) -> a.add(b)));
			grandTotal.setNewRecruitFc(datas.stream().filter(x -> !isNullOrZero(x.getNewRecruitFc())).map(ReportGroupResultManPower2Dto :: getNewRecruitFc).reduce(new BigDecimal(0),(a, b) -> a.add(b)));
			grandTotal.setCountNewRecruitment(datas.stream().filter(x -> !isNullOrZero(x.getCountNewRecruitment())).map(ReportGroupResultManPower2Dto :: getCountNewRecruitment).reduce(new BigDecimal(0),(a, b) -> a.add(b)));
			grandTotal.setActiveFc(datas.stream().filter(x ->!isNullOrZero( x.getActiveFc())).map(ReportGroupResultManPower2Dto :: getActiveFc).reduce(new BigDecimal(0),(a, b) -> a.add(b)));
			grandTotal.setCountNewRecruitmentActive(datas.stream().filter(x -> !isNullOrZero(x.getCountNewRecruitmentActive())).map(ReportGroupResultManPower2Dto :: getCountNewRecruitmentActive).reduce(new BigDecimal(0),(a, b) -> a.add(b)));
			grandTotal.setCountSaTypeAgentcode(datas.stream().filter(x -> !isNullOrZero(x.getCountFcTypeAgentcode())).map(ReportGroupResultManPower2Dto :: getCountFcTypeAgentcode).reduce(new BigDecimal(0),(a, b) -> a.add(b)));
			grandTotal.setCountSaTypeAgentcode(datas.stream().filter(x -> !isNullOrZero(x.getCountSaTypeAgentcode() )).map(ReportGroupResultManPower2Dto :: getCountSaTypeAgentcode).reduce(new BigDecimal(0),(a, b) -> a.add(b)));

			datas.add(grandTotal);
		}

		for (ReportGroupResultManPower2Dto item : datas) {
			if (!isNullOrZero(item.getNewRecruitFc()) && !isNullOrZero(item.getNewRecruitFc())){
				item.setRcRatio(String.valueOf(item.getCountNewRecruitment().divide(item.getNewRecruitFc(),4, RoundingMode.HALF_UP).multiply(new BigDecimal(100)).setScale(2,RoundingMode.HALF_UP)).concat("%"));
			} else {
				item.setRcRatio("0%");
			}
			if (!isNullOrZero(item.getCountActive()) && !isNullOrZero(item.getActiveFc())) {
				item.setActionRatio(String.valueOf(item.getCountActive().divide(item.getActiveFc(),4, RoundingMode.HALF_UP).multiply(new BigDecimal(100)).setScale(2,RoundingMode.HALF_UP)).concat("%"));
			} else {
				item.setActionRatio("0%");
			}
			if (!isNullOrZero(item.getCountActive()) && !isNullOrZero(item.getCountFcTypeAgentcode()) && !isNullOrZero(item.getCountSaTypeAgentcode())) {
				item.setActionRatioAction(String.valueOf(item.getCountActive().divide(item.getCountFcTypeAgentcode(),4, RoundingMode.HALF_UP).multiply(new BigDecimal(100)).setScale(2,RoundingMode.HALF_UP)).concat("%"));
			} else {
				item.setActionRatioAction("0%");
			}
			if (!isNullOrZero(item.getCountNewRecruitmentActive()) && !isNullOrZero(item.getCountNewRecruitment())) {
				item.setActionNewRaio(String.valueOf(item.getCountNewRecruitmentActive().divide(item.getCountNewRecruitment(),4, RoundingMode.HALF_UP).multiply(new BigDecimal(100)).setScale(2,RoundingMode.HALF_UP)).concat("%"));
			} else {
				item.setActionNewRaio("0%");
			}
		}
			
			
		CmsCommonPagination<ReportGroupResultManPower2Dto> resultData = new CmsCommonPagination<>();
		resultData.setData(datas);
		resultData.setTotalData(param.totalData);
		return resultData;
	}

	@Override
	public CmsCommonPagination<ReportGroupResultPremiumDetailDto> getListReportGroupPremiumDetail(
			ReportGroupSearchDetailDto searchDto) {

		if (searchDto.getDataType().equalsIgnoreCase("QTD") && StringUtils.isNotEmpty(String.valueOf(searchDto.getQtd()))) {
			Integer qtd =  searchDto.getQtd();
			searchDto.setMonth(qtd.toString());
		}
		searchDto.setYyyyMM(formatYyyyMM(searchDto.getYear(), searchDto.getMonth()));
		CmsCommonPagination<ReportGroupResultPremiumDetailDto> rs = new CmsCommonPagination<>();
		List<ReportGroupResultPremiumDetailDto> datas = new ArrayList<>();
		try {
			ObjectMapper mapper = new ObjectMapper();

			String stringJsonParam = "";
			searchDto.setFunctionCode("GROUP_" + searchDto.getAgentGroup());
			ReportGroupSearchDetailDto searchBd1 = objectMapper.convertValue(searchDto,
					ReportGroupSearchDetailDto.class);
			ReportGroupSearchDetailDto searchBd2 = objectMapper.convertValue(searchDto,
					ReportGroupSearchDetailDto.class);
			String bd1 = "1 = 1";
			String bd2 = "1 = 1";
			try {
				setConditionSearch(searchBd1, 1);
				stringJsonParam = mapper.writeValueAsString(searchBd1);
			} catch (JsonProcessingException e) {
				logger.error("getListOfficeDocument", e);
			}
			CommonSearchWithPagingDto commonLv1 = parseJsonToParamSearchService.getSearchConditionBd(stringJsonParam,
					searchDto.getAgentGroup());
			if (StringUtils.isNotEmpty(commonLv1.getSearch())) {
				bd1 = commonLv1.getSearch();
			}
			try {
				setConditionSearch(searchBd2, 2);
				stringJsonParam = mapper.writeValueAsString(searchBd2);
			} catch (JsonProcessingException e) {
				logger.error("getListOfficeDocument", e);
			}
			CommonSearchWithPagingDto commonLv2 = parseJsonToParamSearchService.getSearchConditionBd(stringJsonParam,
					searchDto.getAgentGroup());
			if (StringUtils.isNotEmpty(commonLv2.getSearch())) {
				bd2 = commonLv2.getSearch();
				if (StringUtils.isNotEmpty(commonLv1.getSearch())) {
					// lay data cap con thuoc cap cha
					bd2 = bd2 + " AND " + commonLv1.getSearch().replace("UPPER(nvl(L.Agent_Name,''))",
							"UPPER(nvl(L.Parent_Agent_Name,''))");
				}
			} else {
				if (StringUtils.isNotEmpty(commonLv1.getSearch())) {
					// lay data cap con thuoc cap cha
					bd2 = commonLv1.getSearch().replace("UPPER(nvl(L.Agent_Name,''))",
							"UPPER(nvl(L.Parent_Agent_Name,''))");
				}
			}

			ReportGroupResultPremiumDetailPram param = new ReportGroupResultPremiumDetailPram();
			param.agentCode = searchDto.getAgentCode();
			if(searchDto.getAgentGroup().equals("SO") || searchDto.getAgentGroup().equals("GA") )
				param.orgCode = searchDto.getOrgCode();
			else param.orgCode =null;
			param.agentGroup = searchDto.getAgentGroup();
			param.yyyyMM = searchDto.getYyyyMM();
			param.dataType = searchDto.getDataType();
			param.dataLevel = "ALL";
			param.page = searchDto.getPage();
			param.pageSize = searchDto.getPageSize();
			param.sort = searchDto.getSort();
			String searchAll = "";
			if (StringUtils.isNotEmpty(searchDto.getKeyword())) {
				searchAll = " AND (L.TREE_LEVEL = 0  or ((UPPER(nvl(L.Agent_Name,''))  LIKE UPPER(N'%"
						+ searchDto.getKeyword()
						+ "%')  and L.TREE_LEVEL = 1) or (UPPER(nvl(L.Agent_Name,''))  LIKE UPPER(N'%"
						+ searchDto.getKeyword() + "%')  AND UPPER(nvl(L.Parent_Agent_Name,''))  LIKE UPPER(N'%"
						+ searchDto.getKeyword() + "%')  and L.TREE_LEVEL = 2)) )";
			}
			param.search = searchAll + "AND (L.TREE_LEVEL = 0  or ((" + bd1 + " and L.TREE_LEVEL = 1) or (" + bd2
					+ " and L.TREE_LEVEL = 2)) )";
			sqlManagerDb2Service.call(SP_GET_LIST_REPORT_GROUP_PREMIUM_DETAIL, param);

			datas = param.data;
			boolean first = true;
			for (ReportGroupResultPremiumDetailDto item : datas) {
				item.setAgentAll(agentPosition(item.getAgentType(), item.getAgentCode(), item.getAgentName()));
				mapAgent(item, searchDto.getAgentGroup(), first);
				if (searchDto.getAgentGroup() != null) {
					item.setAgentGroup(item.getOrgId() + "-" + item.getAgentCode() + "-" + item.getAgentName());
					if (searchDto.getAgentGroup() == "BM" || searchDto.getAgentGroup() == "SBM") {
						item.setAgentGroup(
								item.getAgentGroup() + "-" + item.getAgentCode() + "-" + item.getAgentName());
					}
					if (searchDto.getAgentGroup() == "Dummy sales") {
						item.setAgentGroup(item.getAgentCode() + "-" + "Dummy Sales Team" + "-" + item.getAgentName());
					}

				}

				first = false;
			}
			sumPolicy(datas, 0);
			groupAgent(searchDto, datas);
			for (ReportGroupResultPremiumDetailDto item : datas) {
				if(!isNullOrZero(item.getK2App()) && !isNullOrZero(item.getK2Epp())) {
					item.setK2(item.getK2App().divide(item.getK2Epp(),4, RoundingMode.CEILING).multiply(new BigDecimal(100)).setScale(2).toString());
				} else {
					item.setK2("0");
				}
				if(!isNullOrZero(item.getK2plusApp()) && !isNullOrZero(item.getK2plusEpp())) {
					item.setK2Plus(item.getK2plusApp().divide(item.getK2plusEpp(),4, RoundingMode.CEILING).multiply(new BigDecimal(100)).setScale(2).toString());
				}else {
					item.setK2Plus("0");
				}
				if(!isNullOrZero(item.getFypReceived()))
					item.setFypReceived(item.getFypReceived().setScale(0,RoundingMode.HALF_UP));
				if(!isNullOrZero(item.getFypIssued()))
					item.setFypIssued(item.getFypIssued().setScale(0,RoundingMode.HALF_UP));
				if(!isNullOrZero(item.getFyp()))
					item.setFyp(item.getFyp().setScale(0,RoundingMode.HALF_UP));
				if(!isNullOrZero(item.getFypCancel()))
					item.setFypCancel(item.getFypCancel().setScale(0,RoundingMode.HALF_UP));
				if(!isNullOrZero(item.getRfyp()))
					item.setRfyp(item.getRfyp().setScale(0,RoundingMode.HALF_UP));
				if(!isNullOrZero(item.getRyp()))
					item.setRyp(item.getRyp().setScale(0,RoundingMode.HALF_UP));
				if(!isNullOrZero(item.getPolicyCountReceived()))
					item.setPolicyCountReceived(item.getPolicyCountReceived().setScale(2,RoundingMode.HALF_UP));
				if(!isNullOrZero(item.getPolicyCountIssued()))
					item.setPolicyCountIssued(item.getPolicyCountIssued().setScale(2,RoundingMode.HALF_UP));
				if(!isNullOrZero(item.getLastRyp()))
					item.setLastRyp(item.getLastRyp().setScale(0,RoundingMode.HALF_UP));
				if(!isNullOrZero(item.getLastFyp()))
					item.setLastFyp(item.getLastFyp().setScale(2,RoundingMode.HALF_UP));
				if(!isNullOrZero(item.getLastPolicyCountIssued()))
					item.setLastPolicyCountIssued(item.getLastPolicyCountIssued().setScale(2,RoundingMode.HALF_UP));
			}
			rs.setTotalData(param.totalData);
			rs.setData(datas);

		} catch (Exception e) {
			logger.error("Exception ", e);
		}

		return rs;
	}

	private void groupAgent(ReportGroupSearchDetailDto searchDto, List<ReportGroupResultPremiumDetailDto> datas) {
		if ("AH".equals(searchDto.getAgentGroup())) {
			List<ReportGroupResultPremiumDetailDto> listTree2 = new ArrayList<ReportGroupResultPremiumDetailDto>();
			List<ReportGroupResultPremiumDetailDto> lstTree1 = datas.stream().filter(e -> e.getTreeLevel().equals(new Integer(1))).collect(Collectors.toList());
			if(CollectionUtils.isNotEmpty(lstTree1)) {
				lstTree1.forEach(item -> {
					List<ReportGroupResultPremiumDetailDto> lstTree2 = datas.stream()
							.filter(e -> e.getTreeLevel().equals(new Integer(2)) && e.getOrgParentId().equals(item.getOrgId()) && e.getParentAgentCode().equals(item.getAgentCode()))
							.collect(Collectors.toList());
					if (CollectionUtils.isNotEmpty(lstTree2)) {

						if ("TH".equals(searchDto.getAgentGroup())) {
							Map<String, List<ReportGroupResultPremiumDetailDto>> maplv2Group = lstTree2.stream()
									.filter(e -> e.getTreeLevel() == 2)
									.collect(Collectors.groupingBy(bd ->bd.getAgentCode()));
							for (Map.Entry<String, List<ReportGroupResultPremiumDetailDto>> entry : maplv2Group.entrySet()) {
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
				Map<String, List<ReportGroupResultPremiumDetailDto>> maplv1 = lstTree1.stream()
						.filter(e -> e.getTreeLevel() == 1)
						.collect(Collectors.groupingBy(bd ->bd.getAgentCode()));
				if (CollectionUtils.isNotEmpty(maplv1.entrySet())) {
					datas.removeIf(e -> e.getTreeLevel().equals(new Integer(1)));
					for (Map.Entry<String, List<ReportGroupResultPremiumDetailDto>> entry : maplv1.entrySet()) {
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
	private void sumPolicy(List<ReportGroupResultPremiumDetailDto> datas, int treeLevel){
		if(!datas.isEmpty()){
			List<ReportGroupResultPremiumDetailDto> lstRoot = datas.stream().filter(e -> e.getTreeLevel().equals(new Integer(treeLevel))).collect(Collectors.toList());
			List<String> adresses = lstRoot.stream()
					.map(ReportGroupResultPremiumDetailDto::getOrgName)
					.collect(Collectors.toList());
			String orgNameNew = String.join(", ", adresses);
			datas.removeIf(e -> e.getTreeLevel().equals(new Integer(treeLevel)));
			if(!lstRoot.isEmpty()){
				ReportGroupResultPremiumDetailDto rootTmp = lstRoot.get(0);
				if(treeLevel == 1 && "OH".equalsIgnoreCase(rootTmp.getChildGroup())) {
					rootTmp.setOrgCode(null); //
					rootTmp.setOrgId(rootTmp.getAgentCode());
				}
				rootTmp.setOrgNameNew(orgNameNew);
				rootTmp.setPolicyCountReceived(lstRoot.stream().filter(x -> !isNullOrZero(x.getPolicyCountReceived())).map(ReportGroupResultPremiumDetailDto :: getPolicyCountReceived).reduce(new BigDecimal(0),(a, b) -> a.add(b)));
				rootTmp.setFypReceived(lstRoot.stream().filter(x -> !isNullOrZero(x.getFypReceived())).map(ReportGroupResultPremiumDetailDto :: getFypReceived).reduce(new BigDecimal(0),(a, b) -> a.add(b)));
				rootTmp.setPolicyCountIssued(lstRoot.stream().filter(x -> !isNullOrZero(x.getPolicyCountIssued())).map(ReportGroupResultPremiumDetailDto :: getPolicyCountIssued).reduce(new BigDecimal(0),(a, b) -> a.add(b)));
				rootTmp.setFypIssued(lstRoot.stream().filter(x -> !isNullOrZero(x.getFypIssued())).map(ReportGroupResultPremiumDetailDto :: getFypIssued).reduce(new BigDecimal(0),(a, b) -> a.add(b)));
				rootTmp.setPolicyCount(lstRoot.stream().filter(x -> !isNullOrZero(x.getPolicyCount())).map(ReportGroupResultPremiumDetailDto :: getPolicyCount).reduce(new BigDecimal(0),(a, b) -> a.add(b)));
				rootTmp.setFyp(lstRoot.stream().filter(x -> !isNullOrZero(x.getFyp())).map(ReportGroupResultPremiumDetailDto :: getFyp).reduce(new BigDecimal(0),(a, b) -> a.add(b)));
				rootTmp.setPolicyCountCancel(lstRoot.stream().filter(x -> !isNullOrZero(x.getPolicyCountCancel())).map(ReportGroupResultPremiumDetailDto :: getPolicyCountCancel).reduce(new BigDecimal(0),(a, b) -> a.add(b)));
				rootTmp.setFypCancel(lstRoot.stream().filter(x -> !isNullOrZero(x.getFypCancel())).map(ReportGroupResultPremiumDetailDto :: getFypCancel).reduce(new BigDecimal(0),(a, b) -> a.add(b)));
				rootTmp.setRfyp(lstRoot.stream().filter(x -> !isNullOrZero(x.getRfyp())).map(ReportGroupResultPremiumDetailDto :: getRfyp).reduce(new BigDecimal(0),(a, b) -> a.add(b)));
				rootTmp.setLastPolicyCountReceived(lstRoot.stream().filter(x -> !isNullOrZero(x.getLastPolicyCountReceived())).map(ReportGroupResultPremiumDetailDto :: getLastPolicyCountReceived).reduce(new BigDecimal(0),(a, b) -> a.add(b)));
				rootTmp.setLastFypReceived(lstRoot.stream().filter(x -> !isNullOrZero(x.getLastFypReceived())).map(ReportGroupResultPremiumDetailDto :: getLastFypReceived).reduce(new BigDecimal(0),(a, b) -> a.add(b)));
				rootTmp.setLastPolicyCountIssued(lstRoot.stream().filter(x -> !isNullOrZero(x.getLastPolicyCountIssued())).map(ReportGroupResultPremiumDetailDto :: getLastPolicyCountIssued).reduce(new BigDecimal(0),(a, b) -> a.add(b)));
				rootTmp.setLastFypIssued(lstRoot.stream().filter(x -> !isNullOrZero(x.getLastFypIssued())).map(ReportGroupResultPremiumDetailDto :: getLastFypIssued).reduce(new BigDecimal(0),(a, b) -> a.add(b)));
				rootTmp.setLastPolicyCount(lstRoot.stream().filter(x -> !isNullOrZero(x.getLastPolicyCount())).map(ReportGroupResultPremiumDetailDto :: getLastPolicyCount).reduce(new BigDecimal(0),(a, b) -> a.add(b)));
				rootTmp.setLastFyp(lstRoot.stream().filter(x -> !isNullOrZero(x.getLastFyp())).map(ReportGroupResultPremiumDetailDto :: getLastFyp).reduce(new BigDecimal(0),(a, b) -> a.add(b)));
				rootTmp.setLastRyp(lstRoot.stream().filter(x -> !isNullOrZero(x.getLastRyp())).map(ReportGroupResultPremiumDetailDto :: getLastRyp).reduce(new BigDecimal(0),(a, b) -> a.add(b)));

				rootTmp.setK2Epp(lstRoot.stream().filter(x -> !isNullOrZero(x.getK2Epp())).map(ReportGroupResultPremiumDetailDto :: getK2Epp).reduce(new BigDecimal(0),(a, b) -> a.add(b)));
				rootTmp.setK2plusEpp(lstRoot.stream().filter(x -> !isNullOrZero(x.getK2plusEpp())).map(ReportGroupResultPremiumDetailDto :: getK2plusEpp).reduce(new BigDecimal(0),(a, b) -> a.add(b)));
				rootTmp.setK2App(lstRoot.stream().filter(x -> !isNullOrZero(x.getK2App())).map(ReportGroupResultPremiumDetailDto :: getK2App).reduce(new BigDecimal(0),(a, b) -> a.add(b)));
				rootTmp.setK2plusApp(lstRoot.stream().filter(x -> !isNullOrZero(x.getK2plusApp())).map(ReportGroupResultPremiumDetailDto :: getK2plusApp).reduce(new BigDecimal(0),(a, b) -> a.add(b)));
				rootTmp.setRyp(lstRoot.stream().filter(x -> !isNullOrZero(x.getRyp())).map(ReportGroupResultPremiumDetailDto::getRyp).reduce(BigDecimal.ZERO, (a, b) -> a.add(b)));

				datas.add(rootTmp);
			}
		}
	}

	private void sumPolicyMan(List<ReportGroupResultManPowerDetailDto> datas, int treeLevel){
		if(!datas.isEmpty()){
			List<ReportGroupResultManPowerDetailDto> lstRoot = datas.stream().filter(e -> e.getTreeLevel().equals(new Integer(treeLevel))).collect(Collectors.toList());
			List<String> adresses = lstRoot.stream()
					.map(ReportGroupResultManPowerDetailDto::getOrgName)
					.collect(Collectors.toList());
			String orgNameNew = String.join(", ", adresses);
			datas.removeIf(e -> e.getTreeLevel().equals(new Integer(treeLevel)));
			if(!lstRoot.isEmpty()){
				ReportGroupResultManPowerDetailDto rootTmp = lstRoot.get(0);
				if(treeLevel == 1 && "OH".equalsIgnoreCase(rootTmp.getChildGroup())) {
					rootTmp.setOrgCode(null); //
					rootTmp.setOrgId(rootTmp.getAgentCode());
				}
				rootTmp.setOrgNameNew(orgNameNew);
				rootTmp.setCountBmTypeAgentcode(lstRoot.stream().filter(x -> !isNullOrZero(x.getCountBmTypeAgentcode())).map(ReportGroupResultManPowerDetailDto :: getCountBmTypeAgentcode).reduce(new BigDecimal(0),(a, b) -> a.add(b)));
				rootTmp.setCountUmTypeAgentcode(lstRoot.stream().filter(x -> !isNullOrZero(x.getCountUmTypeAgentcode())).map(ReportGroupResultManPowerDetailDto :: getCountUmTypeAgentcode).reduce(new BigDecimal(0),(a, b) -> a.add(b)));
				rootTmp.setCountPumTypeAgentcode(lstRoot.stream().filter(x -> !isNullOrZero(x.getCountPumTypeAgentcode())).map(ReportGroupResultManPowerDetailDto :: getCountPumTypeAgentcode).reduce(new BigDecimal(0),(a, b) -> a.add(b)));
				rootTmp.setCountFcTypeAgentcode(lstRoot.stream().filter(x -> !isNullOrZero(x.getCountFcTypeAgentcode())).map(ReportGroupResultManPowerDetailDto :: getCountFcTypeAgentcode).reduce(new BigDecimal(0),(a, b) -> a.add(b)));
				rootTmp.setCountSaTypeAgentcode(lstRoot.stream().filter(x -> !isNullOrZero(x.getCountSaTypeAgentcode())).map(ReportGroupResultManPowerDetailDto :: getCountSaTypeAgentcode).reduce(new BigDecimal(0),(a, b) -> a.add(b)));
				rootTmp.setCountNewRecruitment(lstRoot.stream().filter(x -> !isNullOrZero(x.getCountNewRecruitment())).map(ReportGroupResultManPowerDetailDto :: getCountNewRecruitment).reduce(new BigDecimal(0),(a, b) -> a.add(b)));
				rootTmp.setCountReinstate(lstRoot.stream().filter(x -> !isNullOrZero(x.getCountReinstate())).map(ReportGroupResultManPowerDetailDto :: getCountReinstate).reduce(new BigDecimal(0),(a, b) -> a.add(b)));
				rootTmp.setCountActive(lstRoot.stream().filter(x -> !isNullOrZero(x.getCountActive())).map(ReportGroupResultManPowerDetailDto :: getCountActive).reduce(new BigDecimal(0),(a, b) -> a.add(b)));
				rootTmp.setCountNewRecruitmentActive(lstRoot.stream().filter(x -> !isNullOrZero(x.getCountNewRecruitmentActive())).map(ReportGroupResultManPowerDetailDto :: getCountNewRecruitmentActive).reduce(new BigDecimal(0),(a, b) -> a.add(b)));
				rootTmp.setCountSchemeFc(lstRoot.stream().filter(x -> !isNullOrZero(x.getCountSchemeFc())).map(ReportGroupResultManPowerDetailDto :: getCountSchemeFc).reduce(new BigDecimal(0),(a, b) -> a.add(b)));
				rootTmp.setCountPfcFc(lstRoot.stream().filter(x -> !isNullOrZero(x.getCountPfcFc())).map(ReportGroupResultManPowerDetailDto :: getCountPfcFc).reduce(new BigDecimal(0),(a, b) -> a.add(b)));
				rootTmp.setLastCountNewRecruitment(lstRoot.stream().filter(x -> !isNullOrZero(x.getLastCountNewRecruitment())).map(ReportGroupResultManPowerDetailDto :: getLastCountNewRecruitment).reduce(new BigDecimal(0),(a, b) -> a.add(b)));
				rootTmp.setLastCountReinstate(lstRoot.stream().filter(x -> !isNullOrZero(x.getLastCountReinstate())).map(ReportGroupResultManPowerDetailDto :: getLastCountReinstate).reduce(new BigDecimal(0),(a, b) -> a.add(b)));
				rootTmp.setLastCountNewRecruitmentActive(lstRoot.stream().filter(x -> !isNullOrZero(x.getLastCountNewRecruitmentActive())).map(ReportGroupResultManPowerDetailDto :: getLastCountNewRecruitmentActive).reduce(new BigDecimal(0),(a, b) -> a.add(b)));
				rootTmp.setLastCountActive(lstRoot.stream().filter(x -> !isNullOrZero(x.getLastCountActive())).map(ReportGroupResultManPowerDetailDto :: getLastCountActive).reduce(new BigDecimal(0),(a, b) -> a.add(b)));

				datas.add(rootTmp);
			}
		}
	}
	
	private void groupAgentManPower(ReportGroupSearchDetailDto searchDto, List<ReportGroupResultManPowerDetailDto> datas) {
		if ("AH".equals(searchDto.getAgentGroup())) {
			List<ReportGroupResultManPowerDetailDto> listTree2 = new ArrayList<ReportGroupResultManPowerDetailDto>();
			List<ReportGroupResultManPowerDetailDto> lstTree1 = datas.stream().filter(e -> e.getTreeLevel().equals(new Integer(1))).collect(Collectors.toList());
			if(CollectionUtils.isNotEmpty(lstTree1)) {
				lstTree1.forEach(item -> {
					List<ReportGroupResultManPowerDetailDto> lstTree2 = datas.stream()
							.filter(e -> e.getTreeLevel().equals(new Integer(2)) && e.getOrgParentId().equals(item.getOrgId()) && e.getParentAgentCode().equals(item.getAgentCode()))
							.collect(Collectors.toList());
					if (CollectionUtils.isNotEmpty(lstTree2)) {

						if ("TH".equals(searchDto.getAgentGroup())) {
							Map<String, List<ReportGroupResultManPowerDetailDto>> maplv2Group = lstTree2.stream()
									.filter(e -> e.getTreeLevel() == 2)
									.collect(Collectors.groupingBy(bd ->bd.getAgentCode()));
							for (Map.Entry<String, List<ReportGroupResultManPowerDetailDto>> entry : maplv2Group.entrySet()) {
								String key = entry.getKey();
								if(StringUtils.isNotEmpty(key) && CollectionUtils.isNotEmpty( entry.getValue())) {
									sumPolicyMan(entry.getValue(), 2);
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
				Map<String, List<ReportGroupResultManPowerDetailDto>> maplv1 = lstTree1.stream()
						.filter(e -> e.getTreeLevel() == 1)
						.collect(Collectors.groupingBy(bd ->bd.getAgentCode()));
				if (CollectionUtils.isNotEmpty(maplv1.entrySet())) {
					datas.removeIf(e -> e.getTreeLevel().equals(new Integer(1)));
					for (Map.Entry<String, List<ReportGroupResultManPowerDetailDto>> entry : maplv1.entrySet()) {
						String key = entry.getKey();
						if(StringUtils.isNotEmpty(key) && CollectionUtils.isNotEmpty( entry.getValue())) {
							sumPolicyMan(entry.getValue(), 1);
							datas.addAll(entry.getValue());
						}
					}
					datas.addAll(listTree2);
				}

			}
		}
	}
	
	 public static boolean isNullOrZero(BigDecimal number) {
	        boolean isBigDecimalValueNullOrZero = false;
	        if (number == null)
	            isBigDecimalValueNullOrZero = true;
	        else if (number != null && number.compareTo(BigDecimal.ZERO) == 0)
	            isBigDecimalValueNullOrZero = true;

	        return isBigDecimalValueNullOrZero;
	    }

	@Override
	public CmsCommonPagination<ReportGroupResultManPowerDetailDto> getListReportGroupManpowerDetail(
			ReportGroupSearchDetailDto searchDto) {

		if (searchDto.getDataType().equalsIgnoreCase("QTD") && StringUtils.isNotEmpty(String.valueOf(searchDto.getQtd()))) {
			Integer qtd =  searchDto.getQtd();
			searchDto.setMonth(qtd.toString());
		}
		searchDto.setYyyyMM(formatYyyyMM(searchDto.getYear(), searchDto.getMonth()));
		CmsCommonPagination<ReportGroupResultManPowerDetailDto> rs = new CmsCommonPagination<>();
		List<ReportGroupResultManPowerDetailDto> datas = new ArrayList<>();
		try {
			ObjectMapper mapper = new ObjectMapper();

			String stringJsonParam = "";
			searchDto.setFunctionCode("GROUP_" + searchDto.getAgentGroup());
			ReportGroupSearchDetailDto searchBd1 = objectMapper.convertValue(searchDto,
					ReportGroupSearchDetailDto.class);
			ReportGroupSearchDetailDto searchBd2 = objectMapper.convertValue(searchDto,
					ReportGroupSearchDetailDto.class);
			String bd1 = "1 = 1";
			String bd2 = "1 = 1";
			try {
				setConditionSearch(searchBd1, 1);
				stringJsonParam = mapper.writeValueAsString(searchBd1);
			} catch (JsonProcessingException e) {
				logger.error("getListOfficeDocument", e);
			}
			CommonSearchWithPagingDto commonLv1 = parseJsonToParamSearchService.getSearchConditionBd(stringJsonParam,
					searchDto.getAgentGroup());
			if (StringUtils.isNotEmpty(commonLv1.getSearch())) {
				bd1 = commonLv1.getSearch();
			}
			try {
				setConditionSearch(searchBd2, 2);
				stringJsonParam = mapper.writeValueAsString(searchBd2);
			} catch (JsonProcessingException e) {
				logger.error("getListOfficeDocument", e);
			}
			CommonSearchWithPagingDto commonLv2 = parseJsonToParamSearchService.getSearchConditionBd(stringJsonParam,
					searchDto.getAgentGroup());
			if (StringUtils.isNotEmpty(commonLv2.getSearch())) {
				bd2 = commonLv2.getSearch();
				if (StringUtils.isNotEmpty(commonLv1.getSearch())) {
					// lay data cap con thuoc cap cha
					bd2 = bd2 + " AND " + commonLv1.getSearch().replace("UPPER(nvl(L.Agent_Name,''))",
							"UPPER(nvl(L.Parent_Agent_Name,''))");
				}
			} else {
				if (StringUtils.isNotEmpty(commonLv1.getSearch())) {
					// lay data cap con thuoc cap cha
					bd2 = commonLv1.getSearch().replace("UPPER(nvl(L.Agent_Name,''))",
							"UPPER(nvl(L.Parent_Agent_Name,''))");
				}
			}

			ReportGroupResultManpowerDetailPram param = new ReportGroupResultManpowerDetailPram();
			param.agentCode = searchDto.getAgentCode();

			if(searchDto.getAgentGroup().equals("SO") || searchDto.getAgentGroup().equals("GA") )
			param.orgCode = searchDto.getOrgCode();
			else param.orgCode =null;

			param.agentGroup = searchDto.getAgentGroup();
			param.yyyyMM = searchDto.getYyyyMM();
			param.dataType = searchDto.getDataType();
			param.page = searchDto.getPage();
			param.pageSize = searchDto.getPageSize();
			param.sort = searchDto.getSort();
			String searchAll = "";
			if (StringUtils.isNotEmpty(searchDto.getKeyword())) {
				searchAll = " AND (L.TREE_LEVEL = 0  or ((UPPER(nvl(L.Agent_Name,''))  LIKE UPPER(N'%"
						+ searchDto.getKeyword()
						+ "%')  and L.TREE_LEVEL = 1) or (UPPER(nvl(L.Agent_Name,''))  LIKE UPPER(N'%"
						+ searchDto.getKeyword() + "%')  AND UPPER(nvl(L.Parent_Agent_Name,''))  LIKE UPPER(N'%"
						+ searchDto.getKeyword() + "%')  and L.TREE_LEVEL = 2)) )";
			}
			param.search = searchAll + "AND (L.TREE_LEVEL = 0  or ((" + bd1 + " and L.TREE_LEVEL = 1) or (" + bd2
					+ " and L.TREE_LEVEL = 2)) )";
			sqlManagerDb2Service.call(SP_GET_LIST_REPORT_GROUP_TOTAL, param);
			datas = param.data;
			boolean first = true;
			for (ReportGroupResultManPowerDetailDto item : datas) {
				item.setAgentAll(agentPosition(item.getAgentType(), item.getAgentCode(), item.getAgentName()));
				mapAgentManPower(item, searchDto.getAgentGroup(), first);
				if (searchDto.getAgentGroup() != null) {
					item.setAgentGroup(item.getOrgId() + "-" + item.getAgentCode() + "-" + item.getAgentName());
					if (searchDto.getAgentGroup() == "BM" || searchDto.getAgentGroup() == "SBM") {
						item.setAgentGroup(
								item.getAgentGroup() + "-" + item.getAgentCode() + "-" + item.getAgentName());
					}
					if (searchDto.getAgentGroup() == "Dummy sales") {
						item.setAgentGroup(item.getAgentCode() + "-" + "Dummy Sales Team" + "-" + item.getAgentName());
					}

				}
				first = false;

			}
			sumPolicyMan(datas, 0);
			groupAgentManPower(searchDto, datas);
			rs.setTotalData(param.totalData);
			rs.setData(datas);

		} catch (Exception e) {
			logger.error("Exception ", e);
		}

		return rs;
	}


	@Override
	public CmsCommonPagination<ReportGroupResultPremiumDetailDto> getListReportGroupPremiumUmBmDetail(
			ReportGroupSearchDetailDto searchDto) {

		if (searchDto.getDataType().equalsIgnoreCase("QTD") && StringUtils.isNotEmpty(String.valueOf(searchDto.getQtd()))) {
			Integer qtd =  searchDto.getQtd();
			searchDto.setMonth(qtd.toString());
		}
		searchDto.setYyyyMM(formatYyyyMM(searchDto.getYear(), searchDto.getMonth()));
		ReportGroupResultPremiumBmUmPram paramBMUM = new ReportGroupResultPremiumBmUmPram();
		searchDto.setFunctionCode("GROUP_REPORTING_PEMIUM");

		String searchManager = officePolicyService.fieldSearchBmUm(searchDto.getUnitName(), searchDto.getFunctionCode());
		String searchAgent = officePolicyService.fieldSearchBmUm(searchDto.getAgentAll(), searchDto.getFunctionCode());

		paramBMUM.agentCode = searchDto.getAgentCode();
		if(searchDto.getAgentGroup().equals("SO") || searchDto.getAgentGroup().equals("GA") )
			paramBMUM.orgCode = searchDto.getOrgCode();
		else paramBMUM.orgCode =null;		paramBMUM.agentGroup = searchDto.getAgentGroup();
		paramBMUM.yyyyMM = searchDto.getYyyyMM();
		paramBMUM.dataType = searchDto.getDataType();
		paramBMUM.page = searchDto.getPage();
		paramBMUM.pageSize = searchDto.getPageSize();
		paramBMUM.sort = searchDto.getSort();
		paramBMUM.search = officePolicyService.searchBmUm(searchDto.getKeyword(), searchManager, searchAgent, null );
		sqlManagerDb2Service.call(SP_GET_LIST_REPORT_GROUP_PREMIUM_DETAIL_BM_UM, paramBMUM);
		List<ReportGroupResultPremiumDetailDto> datas = paramBMUM.data;

		datas.forEach(ls-> ls.setManager(ls.getLv2Agenttype() + ":" + ls.getLv2Agentcode().replace("A", "").replace("B", "").replace("C", "") + "-" + ls.getLv2Agentname()));
		datas.forEach(ls->ls.setAgentAll(ls.getLv3Agenttype() + ":" + ls.getLv3Agentcode() + "-" + ls.getLv3Agentname()));

		CmsCommonPagination<ReportGroupResultPremiumDetailDto> resultData = new CmsCommonPagination<>();
		if(datas != null && datas.size() > 0) {
				ReportGroupResultPremiumDetailDto grandTotal = new ReportGroupResultPremiumDetailDto();
			if(StringUtils.isEmpty(searchDto.getKeyword())
					&& ObjectUtils.isEmpty(searchDto.getUnitName())
					&& ObjectUtils.isEmpty(searchDto.getAgentAll())) {
                     grandTotal = callGrandTotalPremium(searchDto);
                }else{
					grandTotal.setPolicyCountReceived(datas.stream().filter(x -> !isNullOrZero(x.getPolicyCountReceived())).map(ReportGroupResultPremiumDetailDto::getPolicyCountReceived).reduce(BigDecimal.ZERO, (a, b) -> a.add(b)));
					grandTotal.setFypReceived(datas.stream().filter(x -> !isNullOrZero(x.getFypReceived())).map(ReportGroupResultPremiumDetailDto::getFypReceived).reduce(BigDecimal.ZERO, (a, b) -> a.add(b)));
					grandTotal.setPolicyCountIssued(datas.stream().filter(x -> !isNullOrZero(x.getPolicyCountIssued())).map(ReportGroupResultPremiumDetailDto::getPolicyCountIssued).reduce(BigDecimal.ZERO, (a, b) -> a.add(b)));
					grandTotal.setFypIssued(datas.stream().filter(x -> !isNullOrZero(x.getFypIssued())).map(ReportGroupResultPremiumDetailDto::getFypIssued).reduce(BigDecimal.ZERO, (a, b) -> a.add(b)));
					grandTotal.setPolicyCount(datas.stream().filter(x -> !isNullOrZero(x.getPolicyCount())).map(ReportGroupResultPremiumDetailDto::getPolicyCount).reduce(BigDecimal.ZERO, (a, b) -> a.add(b)));
					grandTotal.setFyp(datas.stream().filter(x -> !isNullOrZero(x.getFyp())).map(ReportGroupResultPremiumDetailDto::getFyp).reduce(BigDecimal.ZERO, (a, b) -> a.add(b)));
					grandTotal.setPolicyCountCancel(datas.stream().filter(x -> !isNullOrZero(x.getPolicyCountCancel())).map(ReportGroupResultPremiumDetailDto::getPolicyCountCancel).reduce(BigDecimal.ZERO, (a, b) -> a.add(b)));
					grandTotal.setFypCancel(datas.stream().filter(x -> !isNullOrZero(x.getFypCancel())).map(ReportGroupResultPremiumDetailDto::getFypCancel).reduce(BigDecimal.ZERO, (a, b) -> a.add(b)));
					grandTotal.setRfyp(datas.stream().filter(x -> !isNullOrZero(x.getRfyp())).map(ReportGroupResultPremiumDetailDto::getRfyp).reduce(BigDecimal.ZERO, (a, b) -> a.add(b)));
					grandTotal.setRyp(datas.stream().filter(x -> !isNullOrZero(x.getRyp())).map(ReportGroupResultPremiumDetailDto::getRyp).reduce(BigDecimal.ZERO, (a, b) -> a.add(b)));
					grandTotal.setK2Epp(datas.stream().filter(x -> !isNullOrZero(x.getK2Epp())).map(ReportGroupResultPremiumDetailDto::getK2Epp).reduce(BigDecimal.ZERO, (a, b) -> a.add(b)));
					grandTotal.setK2plusEpp(datas.stream().filter(x -> !isNullOrZero(x.getK2plusEpp())).map(ReportGroupResultPremiumDetailDto::getK2plusEpp).reduce(BigDecimal.ZERO, (a, b) -> a.add(b)));
					grandTotal.setK2App(datas.stream().filter(x -> !isNullOrZero(x.getK2App())).map(ReportGroupResultPremiumDetailDto::getK2App).reduce(BigDecimal.ZERO, (a, b) -> a.add(b)));
					grandTotal.setK2plusApp(datas.stream().filter(x -> !isNullOrZero(x.getK2plusApp())).map(ReportGroupResultPremiumDetailDto::getK2plusApp).reduce(BigDecimal.ZERO, (a, b) -> a.add(b)));
				}

			if (StringUtils.equalsIgnoreCase(searchDto.getAgentGroup(), "UM")) grandTotal.setAgentAll("Tổng cộng");
			grandTotal.setManager("Tổng cộng");
            datas.add(grandTotal);
		}


		for (ReportGroupResultPremiumDetailDto ls : datas) {
			if(!isNullOrZero(ls.getK2App()) && !isNullOrZero(ls.getK2Epp())) {
				ls.setK2((ls.getK2App().multiply(new BigDecimal(100))).divide(ls.getK2Epp(), 2, RoundingMode.FLOOR).toString());
			} else if (isNullOrZero(ls.getK2Epp())) {
				ls.setK2("");
			} else {
				ls.setK2("0");
			}
			if(!isNullOrZero(ls.getK2plusApp()) && !isNullOrZero(ls.getK2plusEpp())) {
				ls.setK2Plus((ls.getK2plusApp().multiply(new BigDecimal(100))).divide(ls.getK2plusEpp(), 2, RoundingMode.FLOOR).toString());
			} else if (isNullOrZero(ls.getK2Epp())) {
				ls.setK2Plus("");
			} else {
				ls.setK2Plus("0");
			}

		}

		resultData.setData(datas);
		resultData.setTotalData(paramBMUM.totalData != null ? paramBMUM.totalData : 0);
		return resultData;
	}
	private ReportGroupResultPremiumDetailDto callGrandTotalPremium(ReportGroupSearchDetailDto searchDto){
		ReportGroupResultPremiumDetailPram param = new ReportGroupResultPremiumDetailPram();
		param.agentCode = searchDto.getAgentCode();
		param.orgCode = searchDto.getOrgCode();
		param.agentGroup = searchDto.getAgentGroup();
		param.yyyyMM = searchDto.getYyyyMM();
		param.dataType = searchDto.getDataType();
		param.dataLevel = "ALL";
		param.page = 0;
		param.pageSize = 0;
		param.sort = null;
		param.search = "AND TREE_LEVEL=0";
		sqlManagerDb2Service.call(SP_GET_LIST_REPORT_GROUP_PREMIUM, param);
		return param.data.size() > 0 ? param.data.get(0) : new ReportGroupResultPremiumDetailDto();
	}
	private ReportGroupResultManPowerDetailDto callGrandTotalManPower(ReportGroupSearchDetailDto searchDto) {
		ReportGroupResultManpowerDetailPram param = new ReportGroupResultManpowerDetailPram();
		param.agentCode = searchDto.getAgentCode();
		param.orgCode = searchDto.getOrgCode();
		param.agentGroup = searchDto.getAgentGroup();
		param.yyyyMM = searchDto.getYyyyMM();
		param.dataType = searchDto.getDataType();
		param.page = 0;
		param.pageSize = 0;
		param.sort = null;
		param.search ="AND TREE_LEVEL=0";
		sqlManagerDb2Service.call(SP_GET_LIST_REPORT_GROUP_TOTAL, param);
		return param.data.size() > 0 ? param.data.get(0) : new ReportGroupResultManPowerDetailDto();

	}

		@Override
	public CmsCommonPagination<ReportGroupResultManPowerDetailDto> getListReportGroupManPowerUmBmDetail(
			ReportGroupSearchDetailDto searchDto) {

		if (searchDto.getDataType().equalsIgnoreCase("QTD") && StringUtils.isNotEmpty(String.valueOf(searchDto.getQtd()))) {
			Integer qtd =  searchDto.getQtd();
			searchDto.setMonth(qtd.toString());
		}
		searchDto.setYyyyMM(formatYyyyMM(searchDto.getYear(), searchDto.getMonth()));

		searchDto.setFunctionCode("REPORT_GROUP_MAN_BM");
		String searchManager = officePolicyService.fieldSearchBmUm(searchDto.getManager(), searchDto.getFunctionCode());
		String searchAgent = officePolicyService.fieldSearchBmUm(searchDto.getAgentAll(), searchDto.getFunctionCode());

		ReportGroupResultManpowerBmUmPram paramBMUM = new ReportGroupResultManpowerBmUmPram();
		paramBMUM.agentCode = searchDto.getAgentCode();
		paramBMUM.orgCode = searchDto.getOrgCode();
		paramBMUM.agentGroup = searchDto.getAgentGroup();
		paramBMUM.yyyyMM = searchDto.getYyyyMM();
		paramBMUM.dataType = searchDto.getDataType();
		paramBMUM.page = searchDto.getPage();
		paramBMUM.pageSize = searchDto.getPageSize();
		paramBMUM.sort = searchDto.getSort();
		paramBMUM.search = officePolicyService.searchBmUm(searchDto.getKeyword(), searchManager, searchAgent, null );
		sqlManagerDb2Service.call(SP_GET_LIST_REPORT_GROUP_MANPOWER_DETAIL_BM_UM, paramBMUM);
		List<ReportGroupResultManPowerDetailDto> datas = paramBMUM.data;
		for (ReportGroupResultManPowerDetailDto ls : datas) {
			ls.setManager(ls.getLv2Agenttype() + ":" + ls.getLv2Agentcode().replace("A", "").replace("B", "").replace("C", "") + "-" + ls.getLv2Agentname());
			ls.setAgentAll(ls.getLv3Agenttype() + ":" + ls.getLv3Agentcode() + "-" + ls.getLv3Agentname());
		}
		CmsCommonPagination<ReportGroupResultManPowerDetailDto> resultData = new CmsCommonPagination<>();

			if(datas != null && datas.size() > 0) {
				ReportGroupResultManPowerDetailDto grandTotal = new ReportGroupResultManPowerDetailDto();
				if (StringUtils.equalsIgnoreCase(searchDto.getAgentGroup(), "UM")) grandTotal.setAgentAll("Tổng cộng");
				grandTotal.setManager("Tổng cộng");

				if(StringUtils.isEmpty(searchDto.getKeyword())
						&& ObjectUtils.isEmpty(searchDto.getUnitName())
						&& ObjectUtils.isEmpty(searchDto.getAgentAll())) {
					grandTotal = callGrandTotalManPower(searchDto);

				} else {
					grandTotal.setCountBmTypeAgentcode(datas.stream().filter(x -> !isNullOrZero(x.getCountBmTypeAgentcode())).map(ReportGroupResultManPowerDetailDto::getCountBmTypeAgentcode).reduce(BigDecimal.ZERO, (a, b) -> a.add(b)));
					grandTotal.setCountUmTypeAgentcode(datas.stream().filter(x -> !isNullOrZero(x.getCountUmTypeAgentcode())).map(ReportGroupResultManPowerDetailDto::getCountUmTypeAgentcode).reduce(BigDecimal.ZERO, (a, b) -> a.add(b)));
					grandTotal.setCountPumTypeAgentcode(datas.stream().filter(x -> !isNullOrZero(x.getCountPumTypeAgentcode())).map(ReportGroupResultManPowerDetailDto::getCountPumTypeAgentcode).reduce(BigDecimal.ZERO, (a, b) -> a.add(b)));
					grandTotal.setCountFcTypeAgentcode(datas.stream().filter(x -> !isNullOrZero(x.getCountFcTypeAgentcode())).map(ReportGroupResultManPowerDetailDto::getCountFcTypeAgentcode).reduce(BigDecimal.ZERO, (a, b) -> a.add(b)));
					grandTotal.setCountSaTypeAgentcode(datas.stream().filter(x -> !isNullOrZero(x.getCountSaTypeAgentcode())).map(ReportGroupResultManPowerDetailDto::getCountSaTypeAgentcode).reduce(BigDecimal.ZERO, (a, b) -> a.add(b)));
					grandTotal.setCountNewRecruitment(datas.stream().filter(x -> !isNullOrZero(x.getCountNewRecruitment())).map(ReportGroupResultManPowerDetailDto::getCountNewRecruitment).reduce(BigDecimal.ZERO, (a, b) -> a.add(b)));
					grandTotal.setCountReinstate(datas.stream().filter(x -> !isNullOrZero(x.getCountReinstate())).map(ReportGroupResultManPowerDetailDto::getCountReinstate).reduce(BigDecimal.ZERO, (a, b) -> a.add(b)));
					grandTotal.setCountActive(datas.stream().filter(x -> !isNullOrZero(x.getCountActive())).map(ReportGroupResultManPowerDetailDto::getCountActive).reduce(BigDecimal.ZERO, (a, b) -> a.add(b)));
					grandTotal.setCountNewRecruitmentActive(datas.stream().filter(x -> !isNullOrZero(x.getCountNewRecruitmentActive())).map(ReportGroupResultManPowerDetailDto::getCountNewRecruitmentActive).reduce(BigDecimal.ZERO, (a, b) -> a.add(b)));
					grandTotal.setCountSchemeFc(datas.stream().filter(x -> !isNullOrZero(x.getCountSchemeFc())).map(ReportGroupResultManPowerDetailDto::getCountSchemeFc).reduce(BigDecimal.ZERO, (a, b) -> a.add(b)));
					grandTotal.setCountPfcFc(datas.stream().filter(x -> !isNullOrZero(x.getCountPfcFc())).map(ReportGroupResultManPowerDetailDto::getCountPfcFc).reduce(BigDecimal.ZERO, (a, b) -> a.add(b)));
				}
				datas.add(grandTotal);

			}


			resultData.setData(datas);
		resultData.setTotalData(paramBMUM.totalData != null ? paramBMUM.totalData : 0);
		return resultData;
	}

	// DOANH SO
	@Override
	public ResponseEntity 	getListReportGroupExportPremium(ReportGroupSearchDetailDto searchDto, Locale locale) {
		ResponseEntity res = null;
		try {
			if (searchDto.getDataType().equalsIgnoreCase("QTD") && StringUtils.isNotEmpty(String.valueOf(searchDto.getQtd()))) {
				Integer qtd =  searchDto.getQtd();
				searchDto.setMonth(qtd.toString());
			}
			searchDto.setYyyyMM(formatYyyyMM(searchDto.getYear(), searchDto.getMonth()));
			searchDto.setPage(0);
			searchDto.setSize(0);
			String datePattern = getSystemConfig().getConfig(SystemConfig.DATE_PATTERN);
			datePattern = "dd/MM/yyyy";

			if (searchDto.isDetail()) {
				CmsCommonPagination<ReportGroupResultPremiumDetailDto> resObj = getListReportGroupPremiumDetail(searchDto);
				String template ;
				if(searchDto.getDataType().equals("MTD")) template = "Bao_cao_hoat_dong_cap_phong_ban_chi_tiet_MTD.xlsx";
				else template = "Bao_cao_hoat_dong_cap_phong_ban_chi_tiet.xlsx";

				String templatePath = servletContext
						.getRealPath(CommonConstant.REAL_PATH_TEMPLATE_EXCEL + "/" + template);
				String templateName = "Bao_cao_hoat_dong_cap_phong_ban_view"+searchDto.getAgentGroup()+".xlsx";
				String startRow = "A6";
				List<ReportGroupResultPremiumDetailDto> lstdata = resObj.getData();
				ReportGroupResultPremiumDetailDto root = new ReportGroupResultPremiumDetailDto();
				if(CollectionUtils.isNotEmpty(lstdata)) {
					lstdata.stream().forEach(x->{
						if(StringUtils.isNotEmpty(x.getK2())) x.setK2(x.getK2().concat("%"));
						if(StringUtils.isNotEmpty(x.getK2Plus())) x.setK2Plus(x.getK2Plus().concat("%"));
					});
					root = lstdata.stream().filter(e -> e.getTreeLevel().equals(0)).findFirst().get();
				}
				List<ReportGroupResultPremiumDetailDto> allData = new ArrayList<>();
				// get lv1
				List<ReportGroupResultPremiumDetailDto> lv1 = lstdata.stream().filter(e -> e.getTreeLevel() == 1)
						.collect(Collectors.toList());
				for (ReportGroupResultPremiumDetailDto groupLv1 : lv1) {
					allData.add(groupLv1);
					// get lv2
					List<ReportGroupResultPremiumDetailDto> lv2 = lstdata.stream()
							.filter(e -> e.getTreeLevel() == 2 && e.getParentAgentCode().equals(groupLv1.getAgentCode())
									&& e.getOrgParentId().equals(groupLv1.getOrgId()))
							.collect(Collectors.toList());
					allData.addAll(lv2);
				}
				allData.add(root);
				addDateInColumn(allData, searchDto, searchDto.getAgentGroup());
				List<ItemColsExcelDto> cols = new ArrayList<>();
				// start fill data to workbook
				ExportExcelUtil exportExcel = new ExportExcelUtil<>();
				Map<String, String> mapColFormat = null;
				Map<String, Object> setMapColDefaultValue = null;

				// do export
				try (XSSFWorkbook xssfWorkbook = exportExcel.getXSSFWorkbook(templatePath);) {
					Map<String, CellStyle> mapColStyle = null;

					if(searchDto.getDataType().equals("MTD")) {
						ImportExcelUtil.setListColumnExcel(ReportGroupPremiumDetailCAOMTDEnum.class, cols);
						if(xssfWorkbook.getSheetAt(0).getRow(2) != null) {
							xssfWorkbook.getSheetAt(0).getRow(2).getCell(2).setCellValue("MTD");
							xssfWorkbook.getSheetAt(0).getRow(2).getCell(14).setCellValue("Tháng cùng kỳ năm trước");
						}
						else{
							xssfWorkbook.getSheetAt(0).createRow(2).createCell(2).setCellValue("MTD");
							xssfWorkbook.getSheetAt(0).createRow(2).createCell(14).setCellValue("Tháng cùng kỳ năm trước");
						}
					}else if(searchDto.getDataType().equals("QTD")){
						ImportExcelUtil.setListColumnExcel(ReportGroupPremiumDetailCAOEnum.class, cols);

						if(xssfWorkbook.getSheetAt(0).getRow(2) != null) {
							xssfWorkbook.getSheetAt(0).getRow(2).getCell(2).setCellValue("QTD");
							xssfWorkbook.getSheetAt(0).getRow(2).getCell(12).setCellValue("Quý cùng kỳ năm trước");
						}
						else {
							xssfWorkbook.getSheetAt(0).createRow(2).createCell(2).setCellValue("QTD");
							xssfWorkbook.getSheetAt(0).createRow(2).createCell(12).setCellValue("Quý cùng kỳ năm trước");
						}
					}else if(searchDto.getDataType().equals("YTD")){
						ImportExcelUtil.setListColumnExcel(ReportGroupPremiumDetailCAOEnum.class, cols);

						if(xssfWorkbook.getSheetAt(0).getRow(2) != null) {
							xssfWorkbook.getSheetAt(0).getRow(2).getCell(2).setCellValue("YTD");
							xssfWorkbook.getSheetAt(0).getRow(2).getCell(12).setCellValue("Cùng kỳ năm trước");
						}
						else  {
							xssfWorkbook.getSheetAt(0).createRow(2).createCell(2).setCellValue("YTD");
							xssfWorkbook.getSheetAt(0).createRow(2).createCell(12).setCellValue("Cùng kỳ năm trước");
						}
					}

					writeDateNow(searchDto.getAgentGroup(), searchDto, xssfWorkbook, 0, new Date(),searchDto.getDataType(), true);
					String repo = systemConfig.getConfig(systemConfig.REPO_UPLOADED_MAIN);
					String path = systemConfig.getPhysicalPathById(repo, null); //path up service
					res = exportExcel.doExportExcelHeaderWithColFormatRestUpService(xssfWorkbook, 0, null, allData,
							ReportGroupResultPremiumDetailDto.class, cols, datePattern, startRow, mapColFormat,
							mapColStyle, setMapColDefaultValue, null, true, templateName, true,path);
				} catch (Exception e) {
					logger.error("##ExportList##", e);
				}
				return res;
			} else {
				CmsCommonPagination<ReportGroupResultPremiumDto> resObj = getListReportGroupPremium(searchDto);
				String template = "Bao_cao_hoat_dong_cap_phong_ban.xlsx";
				String templatePath = servletContext
						.getRealPath(CommonConstant.REAL_PATH_TEMPLATE_EXCEL + "/" + template);
				String templateName = "Bao_cao_hoat_dong_cap_phong_ban_view"+searchDto.getAgentGroup()+".xlsx";

				String startRow = "A6";
				List<ReportGroupResultPremiumDto> lstdata = resObj.getData();
				List<ItemColsExcelDto> cols = new ArrayList<>();
				// start fill data to workbook
				ImportExcelUtil.setListColumnExcel(ReportGroupPremiumEnum.class, cols);
				ExportExcelUtil exportExcel = new ExportExcelUtil<>();
				Map<String, String> mapColFormat = null;
				Map<String, Object> setMapColDefaultValue = null;
				// do export
				try (XSSFWorkbook xssfWorkbook = exportExcel.getXSSFWorkbook(templatePath);) {
					Map<String, CellStyle> mapColStyle = null;
					writeHeader(searchDto.getAgentGroup(), searchDto, xssfWorkbook, 0, new Date(), searchDto.getDataType());
					String repo = systemConfig.getConfig(systemConfig.REPO_UPLOADED_MAIN);
					String path = systemConfig.getPhysicalPathById(repo, null); //path up service
					res = exportExcel.doExportExcelHeaderWithColFormatRestUpService(xssfWorkbook, 0, null, lstdata,
							ReportGroupResultPremiumDto.class, cols, datePattern, startRow, mapColFormat, mapColStyle,
							setMapColDefaultValue, null, true, templateName, true,path);
				} catch (Exception e) {
					logger.error("##ExportList##", e.getMessage());
				}
				return res;

			}

		} catch (Exception e) {
			logger.error("exportListData: ", e);
		}
		return res;
	}

	@Override
	public ResponseEntity getListReportGroupExportManpower(ReportGroupSearchDetailDto searchDto, Locale locale) {
		ResponseEntity res = null;
		try {

			if (searchDto.getDataType().equalsIgnoreCase("QTD") && StringUtils.isNotEmpty(String.valueOf(searchDto.getQtd()))) {
				Integer qtd = searchDto.getQtd();
				searchDto.setMonth(qtd.toString());
			}
			searchDto.setYyyyMM(formatYyyyMM(searchDto.getYear(), searchDto.getMonth()));
			searchDto.setPage(0);
			searchDto.setSize(0);
			String datePattern = getSystemConfig().getConfig(SystemConfig.DATE_PATTERN);
			datePattern = "dd/MM/yyyy";
			if(searchDto.isDetail()){
				CmsCommonPagination<ReportGroupResultManPowerDetailDto> resObj = getListReportGroupManpowerDetail(
						searchDto);
				String template = "Bao_cao_hoat_dong_cap_phong_ban_luc_luong_tu_van_chi_tiet.xlsx";
				String templatePath = servletContext
						.getRealPath(CommonConstant.REAL_PATH_TEMPLATE_EXCEL + "/" + template);
				String templateName = "Bao_cao_hoat_dong_cap_phong_ban_view"+searchDto.getAgentGroup()+".xlsx";

				String startRow = "A6";
				List<ReportGroupResultManPowerDetailDto> lstdata = resObj.getData();
				ReportGroupResultManPowerDetailDto root = lstdata.stream().filter(e -> e.getTreeLevel().equals(0))
						.findFirst().get();
				List<ReportGroupResultManPowerDetailDto> allData = new ArrayList<>();
				// get lv1
				List<ReportGroupResultManPowerDetailDto> lv1 = lstdata.stream().filter(e -> e.getTreeLevel() == 1)
						.collect(Collectors.toList());
				for (ReportGroupResultManPowerDetailDto groupLv1 : lv1) {
					allData.add(groupLv1);
					// get lv2
					List<ReportGroupResultManPowerDetailDto> lv2 = lstdata.stream()
							.filter(e -> e.getTreeLevel() == 2 && e.getParentAgentCode().equals(groupLv1.getAgentCode())
									&& e.getOrgParentId().equals(groupLv1.getOrgId()))
							.collect(Collectors.toList());
					allData.addAll(lv2);
				}
				allData.add(root);
	
				addDateInColumnManPower(allData, searchDto, searchDto.getAgentGroup());
	
				List<ItemColsExcelDto> cols = new ArrayList<>();
				// start fill data to workbook
				ImportExcelUtil.setListColumnExcel(ReportGroupManpowerDetailCAOEnum.class, cols);
				ExportExcelUtil exportExcel = new ExportExcelUtil<>();
				Map<String, String> mapColFormat = null;
				Map<String, Object> setMapColDefaultValue = null;
	
				// do export
				try (XSSFWorkbook xssfWorkbook = exportExcel.getXSSFWorkbook(templatePath);) {
					Map<String, CellStyle> mapColStyle = null;
					if(searchDto.getDataType().equals("MTD")) {
						if(xssfWorkbook.getSheetAt(0).getRow(2) != null) xssfWorkbook.getSheetAt(0).getRow(2).getCell(13).setCellValue("Tháng cùng kỳ năm trước");
						else xssfWorkbook.getSheetAt(0).createRow(2).createCell(14).setCellValue("Tháng cùng kỳ năm trước");
					}else if(searchDto.getDataType().equals("QTD")){
						if(xssfWorkbook.getSheetAt(0).getRow(2) != null) xssfWorkbook.getSheetAt(0).getRow(2).getCell(13).setCellValue("Quý cùng kỳ năm trước");
						else xssfWorkbook.getSheetAt(0).createRow(2).createCell(14).setCellValue("Quý cùng kỳ năm trước");
					}else if(searchDto.getDataType().equals("YTD")){
						if(xssfWorkbook.getSheetAt(0).getRow(2) != null) xssfWorkbook.getSheetAt(0).getRow(2).getCell(13).setCellValue("Cùng kỳ năm trước");
						else xssfWorkbook.getSheetAt(0).createRow(2).createCell(14).setCellValue("Cùng kỳ năm trước");

					}
					writeDateNow(searchDto.getAgentGroup(), searchDto, xssfWorkbook, 0, new Date(),searchDto.getDataType(), true);
					String repo = systemConfig.getConfig(systemConfig.REPO_UPLOADED_MAIN);
					String path = systemConfig.getPhysicalPathById(repo, null); //path up service
					res = exportExcel.doExportExcelHeaderWithColFormatRestUpService(xssfWorkbook, 0, null, allData,
							ReportGroupResultManPowerDetailDto.class, cols, datePattern, startRow, mapColFormat,
							mapColStyle, setMapColDefaultValue, null, true, templateName, true,path);
				} catch (Exception e) {
					logger.error("##ExportList##", e.getMessage());
				}
			}else {
				CmsCommonPagination<ReportGroupResultManPower2Dto> resObj = getListReportGroupManPower(searchDto);
				String template = "Bao_cao_hoat_dong_cap_phong_ban_luc_luong_tu_van.xlsx";
				String templatePath = servletContext.getRealPath(CommonConstant.REAL_PATH_TEMPLATE_EXCEL + "/" + template);
				String templateName = "Bao_cao_hoat_dong_cap_phong_ban_view"+searchDto.getAgentGroup()+".xlsx";

				String startRow = "A6";
				List<ReportGroupResultManPower2Dto> lstdata = resObj.getData();
				List<ItemColsExcelDto> cols = new ArrayList<>();
				// start fill data to workbook
				ImportExcelUtil.setListColumnExcel(ReportGroupMANEnum.class, cols);
				ExportExcelUtil exportExcel = new ExportExcelUtil<>();
				Map<String, String> mapColFormat = null;
				Map<String, Object> setMapColDefaultValue = null;
				// do export
				try (XSSFWorkbook xssfWorkbook = exportExcel.getXSSFWorkbook(templatePath);) {
					Map<String, CellStyle> mapColStyle = null;


					writeHeader(searchDto.getAgentGroup(), searchDto, xssfWorkbook, 0, new Date(), searchDto.getDataType());
					String repo = systemConfig.getConfig(systemConfig.REPO_UPLOADED_MAIN);
					String path = systemConfig.getPhysicalPathById(repo, null); //path up service
					res = exportExcel.doExportExcelHeaderWithColFormatRestUpService(xssfWorkbook, 0, null, lstdata,
							ReportGroupResultManPower2Dto.class, cols, datePattern, startRow, mapColFormat, mapColStyle,
							setMapColDefaultValue, null, true, templateName, true,path);
				} catch (Exception e) {
					logger.error("##ExportList##", e.getMessage());
				}
				return res;
			}
			
		} catch (Exception e) {
			logger.error("exportListData: ", e);
		}
		return res;
	}

	@Override
	public ResponseEntity getListReportGroupExportPremiumUmBm(ReportGroupSearchDetailDto searchDto, Locale locale) {
		ResponseEntity res = null;
		try {
			if (searchDto.getDataType().equalsIgnoreCase("QTD") && StringUtils.isNotEmpty(String.valueOf(searchDto.getQtd()))) {
				Integer qtd =  searchDto.getQtd();
				searchDto.setMonth(qtd.toString());
			}
			searchDto.setYyyyMM(formatYyyyMM(searchDto.getYear(), searchDto.getMonth()));
			searchDto.setPage(0);
			searchDto.setSize(0);
			CmsCommonPagination<ReportGroupResultPremiumDetailDto> resObj = getListReportGroupPremiumUmBmDetail(
					searchDto);

			List<ReportGroupResultPremiumDetailDto> lstdata = resObj.getData();
			List<ReportGroupResultPremiumDetailDto> allData = new ArrayList<>();

			Map<String, List<ReportGroupResultPremiumDetailDto>> pmMap = new HashMap<>();
			ReportGroupResultPremiumDetailDto premiumDto = null;
			String pmKey = null;
			for (ReportGroupResultPremiumDetailDto data : lstdata) {
				premiumDto = data;

				pmKey = data.getManager();

				List<ReportGroupResultPremiumDetailDto> premiumList = pmMap.get(pmKey);

				if (premiumList == null) {
					premiumList = new ArrayList<>();

					if (!StringUtils.equals(pmKey, data.getAgentAll())) {
						if(!StringUtils.equalsIgnoreCase(data.getManager(),"Tổng cộng")) {
							//premiumDto.setManager("");
						}
					}
					premiumList.add(premiumDto);

					pmMap.put(pmKey, premiumList);

				} else {
					if (StringUtils.equals(pmKey, data.getAgentAll())) {
						premiumList.add(0, premiumDto);
					} else {
						//premiumDto.setManager("");
						premiumList.add(premiumDto);
					}
				}

				if (data.getK2Epp() == null || data.getK2Epp().intValue() == 0)
					data.setK2("");
				if (data.getK2plusEpp() == null || data.getK2plusEpp().intValue() == 0)
					data.setK2Plus("");
				if(StringUtils.isNotEmpty(data.getK2()))
					data.setK2(data.getK2().concat("%"));
				if(StringUtils.isNotEmpty(data.getK2Plus()))
					data.setK2Plus(data.getK2Plus().concat("%"));
			}

			for (Map.Entry<String, List<ReportGroupResultPremiumDetailDto>> entry : pmMap.entrySet()) {
				allData.addAll(entry.getValue());
			}

			String datePattern = getSystemConfig().getConfig(SystemConfig.DATE_PATTERN);
			datePattern = "dd/MM/yyyy";
			String template= "";
			List<ItemColsExcelDto> cols = new ArrayList<>();
			if("BM".equalsIgnoreCase(searchDto.getAgentGroup()) && searchDto.getDataType().equals("MTD")) {
				template = "ReportGroupBM.xlsx";
				ImportExcelUtil.setListColumnExcel(ReportGroupPremiumDetailBMEnum.class, cols);
			} else if("UM".equalsIgnoreCase(searchDto.getAgentGroup()) && searchDto.getDataType().equals("MTD")){
				template = "ReportGroupUM.xlsx";
				ImportExcelUtil.setListColumnExcel(ReportGroupPremiumDetailUMEnum.class, cols);
			} else if("BM".equalsIgnoreCase(searchDto.getAgentGroup()) && searchDto.getDataType().equals("QTD")) {
				template = "ReportGroupQTDBM.xlsx";
				ImportExcelUtil.setListColumnExcel(ReportGroupPremiumDetailQTDBMEnum.class, cols);
			} else if("UM".equalsIgnoreCase(searchDto.getAgentGroup()) && searchDto.getDataType().equals("QTD")) {
				template = "ReportGroupQTDUM.xlsx";
				ImportExcelUtil.setListColumnExcel(ReportGroupPremiumDetailQTDUMEnum.class, cols);
			} else if ("BM".equalsIgnoreCase(searchDto.getAgentGroup()) && searchDto.getDataType().equals("YTD")) {
				template = "ReportGroupYtdBM.xlsx";
				ImportExcelUtil.setListColumnExcel(ReportGroupPremiumDetailYTDBMEnum.class, cols);
			} else if ("UM".equalsIgnoreCase(searchDto.getAgentGroup()) && searchDto.getDataType().equals("YTD")) {
				template = "ReportGroupYtdUM.xlsx";
				ImportExcelUtil.setListColumnExcel(ReportGroupPremiumDetailQTDUMEnum.class, cols);
			}

			String templatePath = servletContext.getRealPath(CommonConstant.REAL_PATH_TEMPLATE_EXCEL + "/" + template);
			String templateName = "Bao_cao_hoat_dong_cap_phong_ban_Doanh_so_"+searchDto.getAgentGroup()+".xlsx";
			String startRow = "A6";

			addDateInColumn(lstdata, searchDto, searchDto.getAgentGroup());

			// start fill data to workbook

			ExportExcelUtil exportExcel = new ExportExcelUtil<>();
			Map<String, String> mapColFormat = null;
			Map<String, Object> setMapColDefaultValue = null;

			// do export
			try (XSSFWorkbook xssfWorkbook = exportExcel.getXSSFWorkbook(templatePath);) {
				Map<String, CellStyle> mapColStyle = null;

				writeDateNowPro(searchDto.getAgentGroup(), searchDto, xssfWorkbook, 0, new Date(),searchDto.getDataType());


				String repo = systemConfig.getConfig(systemConfig.REPO_UPLOADED_MAIN);
				String path = systemConfig.getPhysicalPathById(repo, null); //path up service
				res = exportExcel.doExportExcelHeaderWithColFormatRestUpService(xssfWorkbook, 0, null, lstdata,
						ReportGroupResultPremiumDetailDto.class, cols, datePattern, startRow, mapColFormat, mapColStyle,
						setMapColDefaultValue, null, true, templateName, true,path);
			} catch (Exception e) {
				logger.error("##ExportList##", e);
			}
		} catch (Exception e) {
			logger.error("exportListData: ", e);
		}
		return res;
	}

	@Override
	public ResponseEntity getListReportGroupExportManpowerUmBm(ReportGroupSearchDetailDto searchDto, Locale locale) {
		ResponseEntity res = null;
		try {
			if (searchDto.getDataType().equalsIgnoreCase("QTD") && StringUtils.isNotEmpty(String.valueOf(searchDto.getQtd()))) {
				Integer qtd =  searchDto.getQtd();
				searchDto.setMonth(qtd.toString());
			}
			searchDto.setYyyyMM(formatYyyyMM(searchDto.getYear(), searchDto.getMonth()));
			searchDto.setPage(0);
			searchDto.setSize(0);
			CmsCommonPagination<ReportGroupResultManPowerDetailDto> resObj = getListReportGroupManPowerUmBmDetail(
					searchDto);

			List<ReportGroupResultManPowerDetailDto> lstdata = resObj.getData();
			List<ReportGroupResultManPowerDetailDto> allData = new ArrayList<>();

			Map<String, List<ReportGroupResultManPowerDetailDto>> pmMap = new HashMap<>();
			ReportGroupResultManPowerDetailDto premiumDto = null;
			String pmKey = null;
			for (ReportGroupResultManPowerDetailDto data : lstdata) {
				premiumDto = data;

				pmKey = data.getManager();

				List<ReportGroupResultManPowerDetailDto> premiumList = pmMap.get(pmKey);

				if (premiumList == null) {
					premiumList = new ArrayList<>();

					if (!StringUtils.equals(pmKey, data.getAgentAll())) {
						if(!StringUtils.equalsIgnoreCase(data.getManager(),"Tổng cộng")) {
							//premiumDto.setManager("");
						}					}
					premiumList.add(premiumDto);

					pmMap.put(pmKey, premiumList);

				} else {
					if (StringUtils.equals(pmKey, data.getAgentAll())) {
						premiumList.add(0, premiumDto);
					} else {
						//premiumDto.setManager("");
						premiumList.add(premiumDto);
					}
				}
			}

			for (Map.Entry<String, List<ReportGroupResultManPowerDetailDto>> entry : pmMap.entrySet()) {
				allData.addAll(entry.getValue());
			}

			String datePattern = getSystemConfig().getConfig(SystemConfig.DATE_PATTERN);
			datePattern = "dd/MM/yyyy";
			String template = "";
			List<ItemColsExcelDto> cols = new ArrayList<>();
			if ("BM".equalsIgnoreCase(searchDto.getAgentGroup())) {
				template = "ReportManPowerBM.xlsx";
				ImportExcelUtil.setListColumnExcel(ReportGroupManpowerDetailBMEnum.class, cols);
			} else {
				template = "ReportManPowerUM.xlsx";
				ImportExcelUtil.setListColumnExcel(ReportGroupManpowerDetailUMEnum.class, cols);
			}
			String templatePath = servletContext
					.getRealPath(CommonConstant.REAL_PATH_TEMPLATE_EXCEL + "/" + template);
			String startRow = "A6";

			addDateInColumnManPower(allData, searchDto, searchDto.getAgentGroup());

			// start fill data to workbook
			String templateName = "Bao_cao_hoat_dong_cap_phong_ban_view" + searchDto.getDataType() + ".xlsx";
			ExportExcelUtil exportExcel = new ExportExcelUtil<>();
			Map<String, String> mapColFormat = null;
			Map<String, Object> setMapColDefaultValue = null;

			// do export
			try (XSSFWorkbook xssfWorkbook = exportExcel.getXSSFWorkbook(templatePath);) {
				Map<String, CellStyle> mapColStyle = null;

				writeDateNow(searchDto.getAgentGroup(), searchDto, xssfWorkbook, 0, new Date(),searchDto.getDataType(), false);
				String repo = systemConfig.getConfig(systemConfig.REPO_UPLOADED_MAIN);
				String path = systemConfig.getPhysicalPathById(repo, null); //path up service
				res = exportExcel.doExportExcelHeaderWithColFormatRestUpService(xssfWorkbook, 0, null, lstdata,
						ReportGroupResultManPowerDetailDto.class, cols, datePattern, startRow, mapColFormat,
						mapColStyle, setMapColDefaultValue, null, true, templateName, true,path);
			} catch (Exception e) {
				logger.error("##ExportList##", e);
			}
		} catch (Exception e) {
			logger.error("exportListData: ", e);
		}
		return res;
	}

	public void addDateInColumn(List<ReportGroupResultPremiumDetailDto> lstdata, ReportGroupSearchDetailDto searchDto,
			String agentType) {

		for (ReportGroupResultPremiumDetailDto ls : lstdata) {

			if (StringUtils.endsWithIgnoreCase(agentType, "CAO")) {
				ls.setParen(ls.getBdthName());
				ls.setChild(ls.getBdahName());
			}
			if (StringUtils.endsWithIgnoreCase(agentType, "TH")) {
				ls.setParen(ls.getBdahName());
				ls.setChild(ls.getBdohName());
			}
			if (StringUtils.endsWithIgnoreCase(agentType, "AH")) {
				ls.setParen(ls.getBdohName());
				ls.setChild(ls.getGaName());
			}
			if (StringUtils.endsWithIgnoreCase(agentType, "OH")) {
				ls.setParen(ls.getGaName());
				ls.setChild(ls.getBranchName());
			}
			if (StringUtils.endsWithIgnoreCase(agentType, "GA")) {
				ls.setParen(ls.getBranchName());
				ls.setChild(ls.getUnitName());
			}
			if (StringUtils.endsWithIgnoreCase(agentType, "BM")) {
				ls.setParen(ls.getManager());
				ls.setChild(ls.getAgentAll());
			}
			if (StringUtils.endsWithIgnoreCase(agentType, "UM")) {
				ls.setChild(ls.getAgentAll());
			}

		}
	}

	public void addDateInColumnManPower(List<ReportGroupResultManPowerDetailDto> lstdata,
			ReportGroupSearchDetailDto searchDto, String agentType) {

		for (ReportGroupResultManPowerDetailDto ls : lstdata) {

			if (StringUtils.endsWithIgnoreCase(agentType, "CAO")) {
				ls.setParen(ls.getBdthName());
				ls.setChild(ls.getBdahName());
			}
			if (StringUtils.endsWithIgnoreCase(agentType, "TH")) {
				ls.setParen(ls.getBdahName());
				ls.setChild(ls.getBdrhName());
			}
			if (StringUtils.endsWithIgnoreCase(agentType, "AH")) {
				ls.setParen(ls.getBdohName());
				ls.setChild(ls.getGaName());
			}
			if (StringUtils.endsWithIgnoreCase(agentType, "RH")) {
				ls.setParen(ls.getBdohName());
				ls.setChild(ls.getGaName());
			}
			if (StringUtils.endsWithIgnoreCase(agentType, "OH")) {
				ls.setParen(ls.getGaName());
				ls.setChild(ls.getBranchName());
			}
			if (StringUtils.endsWithIgnoreCase(agentType, "GA") || StringUtils.endsWithIgnoreCase(agentType, "SO")) {
				ls.setParen(ls.getGaName());
				ls.setChild(ls.getUnitName());
			}
			if (StringUtils.endsWithIgnoreCase(agentType, "BM")) {
				ls.setParen(ls.getManager());
				ls.setChild(ls.getAgentAll());
			}
			if (StringUtils.endsWithIgnoreCase(agentType, "UM")) {
				ls.setChild(ls.getAgentAll());
			}

		}
	}

	public void writeHeader(String agentType, ReportGroupSearchDetailDto searchDto, XSSFWorkbook xssfWorkbook,
			int sheetNumber, Date runDate, String dataType) throws IOException {
		XSSFSheet xssfSheet = xssfWorkbook.getSheetAt(sheetNumber);
		XSSFRow row = xssfSheet.getRow(3);
		XSSFRow rowTitle = xssfSheet.getRow(2);
		XSSFRow rowTitleName = xssfSheet.getRow(0);

		int Index1 = 0;
		int Index2 = 1;

		XSSFCell cellIndex1 = row.getCell(Index1);
		XSSFCell cellIndex2 = row.getCell(Index2);
		XSSFCell cellIndex3 = rowTitle.getCell(Index1);
		XSSFCell cellIndex4 = rowTitleName.getCell(Index1);

		cellIndex3.setCellValue("Cập nhật đến ngày " + DateUtils.formatDateToString(new Date(runDate.getTime() -  86400000), "dd/MM/yyyy"));
		if(dataType.equals("YTD")) {
			cellIndex4.setCellValue("BÁO CÁO HOẠT ĐỘNG NĂM " + searchDto.getYear());
		}else if(dataType.equals("QTD")){
			cellIndex4.setCellValue("BÁO CÁO HOẠT ĐỘNG QUÝ " + formatMmYyyy(searchDto.getYear(), searchDto.getMonth()));
		}
		else{
			cellIndex4.setCellValue("BÁO CÁO HOẠT ĐỘNG THÁNG " + formatMmYyyy(searchDto.getYear(), searchDto.getMonth()));
		}

		if (StringUtils.endsWithIgnoreCase(agentType, "CAO")) {
			cellIndex1.setCellValue("Miền");
			cellIndex2.setCellValue("Tên BDTH");
		} else if (StringUtils.endsWithIgnoreCase(agentType, "TH")) {
			cellIndex1.setCellValue("Khu");
			cellIndex2.setCellValue("Tên BD");
		} else if (StringUtils.endsWithIgnoreCase(agentType, "AH")) {
			cellIndex1.setCellValue("Văn phòng");
			cellIndex2.setCellValue("Tên BD");
		} else if (StringUtils.endsWithIgnoreCase(agentType, "RH")) {
			cellIndex1.setCellValue("Văn phòng");
			cellIndex2.setCellValue("Tên BD");
		} else if (StringUtils.endsWithIgnoreCase(agentType, "OH")) {
			cellIndex1.setCellValue("Văn phòng");
			cellIndex2.setCellValue("Tên GAD");
		} else if (StringUtils.endsWithIgnoreCase(agentType, "BM")) {
			cellIndex1.setCellValue("Quản lý");
			cellIndex2.setCellValue("Tư vấn tài chính");
		} else if (StringUtils.endsWithIgnoreCase(agentType, "UM")) {
			cellIndex1.setCellValue("Tư vấn tài chính");
			cellIndex2.setCellValue("FYP ngày");
		} 

	}

	public void writeDateNow(String agentType, ReportGroupSearchDetailDto searchDto, XSSFWorkbook xssfWorkbook,
			int sheetNumber, Date runDate, String dataType, boolean check) throws IOException {
		XSSFSheet xssfSheet = xssfWorkbook.getSheetAt(sheetNumber);
		XSSFRow row = null;
		XSSFRow rowTitle = null;
		XSSFRow rowTitleName = null;

		int Index1 = 0;
		int Index2 = 1;

		rowTitle = xssfSheet.getRow(1);
		rowTitleName = xssfSheet.getRow(0);
		XSSFCell cellIndex3 = rowTitle.getCell(Index1);
		XSSFCell cellIndex4 = rowTitleName.getCell(Index1);
		if (cellIndex3 == null)
			cellIndex3 = rowTitle.createCell(Index1);

		row = xssfSheet.getRow(3);

		XSSFCell cellIndex1 = row.getCell(Index1);
		if (cellIndex1 == null)
			cellIndex1 = row.createCell(Index1);
		XSSFCell cellIndex2 = row.getCell(Index2);
		if (cellIndex2 == null)
			cellIndex2 = row.createCell(Index2);
        	cellIndex3.setCellValue("Cập nhật đến ngày " + DateUtils.formatDateToString(new Date(runDate.getTime() -  86400000), "dd/MM/yyyy"));
		if(dataType.equals("YTD")) {
			cellIndex4.setCellValue("BÁO CÁO HOẠT ĐỘNG NĂM " + searchDto.getYear());
		}else if(dataType.equals("QTD")){
			cellIndex4.setCellValue("BÁO CÁO HOẠT ĐỘNG QUÝ " + formatMmYyyy(searchDto.getYear(), searchDto.getMonth()));
		}
		else{
			cellIndex4.setCellValue("BÁO CÁO HOẠT ĐỘNG THÁNG " + formatMmYyyy(searchDto.getYear(), searchDto.getMonth()));

		}
		if (StringUtils.equals(agentType, "CAO")) {
			cellIndex1.setCellValue("BDTH");
			cellIndex2.setCellValue("BDAH");
		}
		if (StringUtils.equals(agentType, "TH")) {
			cellIndex1.setCellValue("BDAH");
			cellIndex2.setCellValue("BDOH");
		}
		if (StringUtils.equals(agentType, "AH")) {
			cellIndex1.setCellValue("BDOH");
			cellIndex2.setCellValue("Văn phòng/Tổng đại lý");
		}
		if (StringUtils.equals(agentType, "OH")
				|| StringUtils.equals(agentType, "GA")
				|| StringUtils.equals(agentType, "Office")
				|| StringUtils.equals(agentType, "SO")) {
			cellIndex1.setCellValue("Văn phòng/Tổng đại lý");
			cellIndex2.setCellValue("Trưởng phòng");
		}
		if (StringUtils.equals(agentType, "BM")) {
			cellIndex1.setCellValue("Quản lý");
			cellIndex2.setCellValue("Tư vấn tài chính");
		}
		if (StringUtils.equals(agentType, "UM")) {
			cellIndex1.setCellValue("Tư vấn tài chính");
		}
		
		if ("BM".equalsIgnoreCase(searchDto.getAgentGroup()) && searchDto.getDataType().equals("MTD") && check) {

			String aMonthAgo, twoMonthAgo, threeMonthAgo ;
			int months = Integer.valueOf(searchDto.getMonth());
			int year = Integer.valueOf(searchDto.getYear());

			aMonthAgo = getMmonthYear(months,year, -1);
			twoMonthAgo = getMmonthYear(months,year, -2);
			threeMonthAgo = getMmonthYear(months,year, -3);

			XSSFRow rowBm=null;
			rowBm = xssfSheet.getRow(3);

			XSSFCell cellIndex5 = rowBm.getCell(16);
			if (cellIndex5 == null) cellIndex5 = rowBm.createCell(16);
			cellIndex5.setCellValue("Tháng "+ threeMonthAgo );

			XSSFCell cellIndex6 = rowBm.getCell(18);
			if (cellIndex6 == null) cellIndex6 = rowBm.createCell(18);
			cellIndex6.setCellValue("Tháng "+ twoMonthAgo );

			XSSFCell cellIndex7 = rowBm.getCell(20);
			if (cellIndex7 == null) cellIndex7 = rowBm.createCell(20);
			cellIndex7.setCellValue("Tháng "+  aMonthAgo );

		}

	}
	
	public void writeDateNowPro(String agentType, ReportGroupSearchDetailDto searchDto, XSSFWorkbook xssfWorkbook,
			int sheetNumber, Date runDate, String dataType) throws IOException {
		XSSFSheet xssfSheet = xssfWorkbook.getSheetAt(sheetNumber);
		XSSFRow row = null;
		XSSFRow rowTitle = null;
		XSSFRow rowTitleName = null;

		int Index1 = 0;
		int Index2 = 1;

		rowTitle = xssfSheet.getRow(1);
		rowTitleName = xssfSheet.getRow(0);
		XSSFCell cellIndex3 = rowTitle.getCell(Index1);
		XSSFCell cellIndex4 = rowTitleName.getCell(Index1);
		if (cellIndex3 == null)
			cellIndex3 = rowTitle.createCell(Index1);

		row = xssfSheet.getRow(3);

		XSSFCell cellIndex1 = row.getCell(Index1);
		if (cellIndex1 == null)
			cellIndex1 = row.createCell(Index1);
		XSSFCell cellIndex2 = row.getCell(Index2);
		if (cellIndex2 == null)
			cellIndex2 = row.createCell(Index2);

		cellIndex3.setCellValue("Cập nhật đến ngày " + DateUtils.formatDateToString(new Date(runDate.getTime() -  86400000), "dd/MM/yyyy"));
		if(dataType.equals("YTD")) {
			cellIndex4.setCellValue("BÁO CÁO HOẠT ĐỘNG NĂM " + searchDto.getYear());
		}else if(dataType.equals("QTD")) {
			cellIndex4.setCellValue("BÁO CÁO HOẠT ĐỘNG QUÝ " + formatMmYyyy(searchDto.getYear(), searchDto.getMonth()));
		}else{
		cellIndex4.setCellValue("BÁO CÁO HOẠT ĐỘNG THÁNG " + formatMmYyyy(searchDto.getYear(), searchDto.getMonth()));
		}
		if (StringUtils.endsWithIgnoreCase(agentType, "CAO")) {
			cellIndex1.setCellValue("BDTH");
			cellIndex2.setCellValue("BDAH");
		}
		if (StringUtils.endsWithIgnoreCase(agentType, "TH")) {
			cellIndex1.setCellValue("BDAH");
			cellIndex2.setCellValue("BDRH");
		}
		if (StringUtils.endsWithIgnoreCase(agentType, "AH")) {
			cellIndex1.setCellValue("BDOH");
			cellIndex2.setCellValue("Văn phòng/Tổng đại lý");
		}
		if (StringUtils.endsWithIgnoreCase(agentType, "RH")) {
			cellIndex1.setCellValue("BDOH");
			cellIndex2.setCellValue("Văn phòng/Tổng đại lý");
		}
		if (StringUtils.equals(agentType, "OH")
				|| StringUtils.equals(agentType, "GA")
				|| StringUtils.equals(agentType, "Office")
				|| StringUtils.equals(agentType, "SO")) {
			cellIndex1.setCellValue("Văn phòng/Tổng đại lý");
			cellIndex2.setCellValue("Trưởng phòng");
		}
		if (StringUtils.endsWithIgnoreCase(agentType, "BM")) {
			cellIndex1.setCellValue("Quản lý");
			cellIndex2.setCellValue("Tư vấn tài chính");
		}
		if (StringUtils.endsWithIgnoreCase(agentType, "UM")) {
			cellIndex1.setCellValue("Tư vấn tài chính");
		}

		if(searchDto.getDataType().equals("MTD")) {
			String aMonthAgo, twoMonthAgo, threeMonthAgo ;
			int months = Integer.valueOf(searchDto.getMonth());
			int year = Integer.valueOf(searchDto.getYear());

			aMonthAgo = getMmonthYear(months,year, -1);
			twoMonthAgo = getMmonthYear(months,year, -2);
			threeMonthAgo = getMmonthYear(months,year, -3);

			XSSFRow rowBm=null;
			rowBm = xssfSheet.getRow(3);
			if ("BM".equalsIgnoreCase(searchDto.getAgentGroup())) {

				XSSFCell cellIndex5 = rowBm.getCell(15);
				if (cellIndex5 == null) cellIndex5 = rowBm.createCell(15);
				cellIndex5.setCellValue("Tháng " + threeMonthAgo);

				XSSFCell cellIndex6 = rowBm.getCell(17);
				if (cellIndex6 == null) cellIndex6 = rowBm.createCell(17);
				cellIndex6.setCellValue("Tháng " + twoMonthAgo);

				XSSFCell cellIndex7 = rowBm.getCell(19);
				if (cellIndex7 == null) cellIndex7 = rowBm.createCell(19);
				cellIndex7.setCellValue("Tháng " + aMonthAgo);

			} else {

				XSSFCell cellIndex5 = rowBm.getCell(14);
				if (cellIndex5 == null) cellIndex5 = rowBm.createCell(14);
				cellIndex5.setCellValue("Tháng " + threeMonthAgo);

				XSSFCell cellIndex6 = rowBm.getCell(16);
				if (cellIndex6 == null) cellIndex6 = rowBm.createCell(16);
				cellIndex6.setCellValue("Tháng " + twoMonthAgo);

				XSSFCell cellIndex7 = rowBm.getCell(18);
				if (cellIndex7 == null) cellIndex7 = rowBm.createCell(18);
				cellIndex7.setCellValue("Tháng " + aMonthAgo);

			}
		}

	}

	public String getMmonthYear(int month, int year, int subMonth){
	if(month + subMonth <=0){
		month = 12 + month + subMonth;
		year = year - 1;
	} else {
		month = month + subMonth;
	}
	long monthResult = new Date(month + "/01/" + year).getMonth()+  1;
	return monthResult < 10 ? "0" + monthResult + "/" +year : monthResult + "/" +year;
	}

	private void mapAgent(ReportGroupResultPremiumDetailDto data, String agentGroup, boolean first) {
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
				case "BM":
					data.setUnitCode(data.getAgentCode());
					data.setUnitName("Tổng cộng");
					data.setUnitType(data.getAgentType());
					break;
				case "UM":
					data.setUnitCode(data.getAgentCode());
					data.setUnitName("Tổng cộng");
					data.setUnitType(data.getAgentType());
					break;
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
					data.setBdohCode(data.getAgentCode());

					String orgId = data.getAgentType();
					if (data.getTreeLevel() == 2) {
						orgId = data.getOrgId();
						data.setOrgCode(data.getOrgId());
					}
					
					data.setBdohName(orgId + "-" + data.getAgentCode() + "-" + data.getAgentName());
					if (StringUtils.contains(data.getAgentName(), "Dummy Sales")) {
						data.setBdohName(data.getAgentName());

					}
					data.setBdohType(data.getAgentType());
					break;
				case "GA":
					data.setGaCode(data.getAgentCode());
					data.setGaName(data.getOrgId() + "-" + data.getParentAgentCode() + "-" + data.getAgentName());
					if (StringUtils.contains(data.getAgentName(), "Dummy Sales")) {
						data.setGaName(data.getAgentName());

					}
					data.setGaType(data.getAgentType());
					break;
				case "SO":
					data.setGaCode(data.getAgentCode());
					data.setGaName(data.getOrgId() + "-" + data.getParentAgentCode() + "-" + data.getAgentName());
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

	public void writeUpdateDateNow(XSSFWorkbook xssfWorkbook, int sheetNumber, Date runDate) throws IOException {
		XSSFSheet xssfSheet = xssfWorkbook.getSheetAt(sheetNumber);
		XSSFRow row = null;
		int cellIndex = 0;
		row = xssfSheet.getRow(1);
		XSSFCell cellDate = row.getCell(cellIndex);
		if (cellDate == null)
			cellDate = row.createCell(cellIndex);
		cellDate.setCellValue("Cập nhật đén ngày  " + DateUtils.formatDateToString(new Date(runDate.getTime() -  86400000), "dd/MM/yyyy"));

	}

	@Override
	public CmsCommonPagination<ReportGroupResultTargetAchievementDto> getListTargetAchievementSale(
			ReportGroupSearchDetailDto searchDto) {
		ReportGroupResultTargetAchievementSale param = new ReportGroupResultTargetAchievementSale();
		param.agentCode = searchDto.getAgentCode();
		param.dataType = searchDto.getDataType();
		param.agentGroup = searchDto.getAgentGroup();
		sqlManagerDb2Service.call(SP_GET_LIST_REPORT_TARGET_ACHIEVEMENT_SALE, param);
		List<ReportGroupResultTargetAchievementDto> datas = param.data;
		CmsCommonPagination<ReportGroupResultTargetAchievementDto> resultData = new CmsCommonPagination<>();
		resultData.setData(datas);
		resultData.setTotalData(param.totalData);
		return resultData;
	}

	@Override
	public CmsCommonPagination<ReportGroupResultTargetAchievementDto> getListTargetAchievementCompare(
			ReportGroupSearchDetailDto searchDto) {
		ReportGroupResultTargetAchievementCompare param = new ReportGroupResultTargetAchievementCompare();
		param.agentCode = searchDto.getAgentCode();
		param.dataType = searchDto.getDataType();
		param.agentGroup = searchDto.getAgentGroup();
		sqlManagerDb2Service.call(SP_GET_LIST_REPORT_TARGET_ACHIEVEMENT_COMPARE, param);
		List<ReportGroupResultTargetAchievementDto> datas = param.data;
		CmsCommonPagination<ReportGroupResultTargetAchievementDto> resultData = new CmsCommonPagination<>();
		resultData.setData(datas);
		resultData.setTotalData(param.totalData);
		return resultData;
	}

	@Override
	public CmsCommonPagination<ReportGroupResultTargetAchievementDto> getListTargetAchievementMissing(
			ReportGroupSearchDetailDto searchDto) {
		ReportGroupResultTargetAchievementMissing param = new ReportGroupResultTargetAchievementMissing();
		param.agentCode = searchDto.getAgentCode();
		param.dataType = searchDto.getDataType();
		param.agentGroup = searchDto.getAgentGroup();
		sqlManagerDb2Service.call(SP_GET_LIST_REPORT_TARGET_ACHIEVEMENT_MISSING, param);
		List<ReportGroupResultTargetAchievementDto> datas = param.data;
		CmsCommonPagination<ReportGroupResultTargetAchievementDto> resultData = new CmsCommonPagination<>();
		resultData.setData(datas);
		resultData.setTotalData(param.totalData);
		return resultData;
	}

	@Override
	public CmsCommonPagination<ReportGroupResultTargetAchievementDto> getListTargetAchievementPayFees(
			ReportGroupSearchDetailDto searchDto) {
		ReportGroupResultTargetAchievementPayFees param = new ReportGroupResultTargetAchievementPayFees();
		param.agentCode = searchDto.getAgentCode();
		param.dataType = searchDto.getDataType();
		param.agentGroup = searchDto.getAgentGroup();
		sqlManagerDb2Service.call(SP_GET_LIST_REPORT_TARGET_ACHIEVEMENT_PAY_FEES, param);
		List<ReportGroupResultTargetAchievementDto> datas = param.data;
		CmsCommonPagination<ReportGroupResultTargetAchievementDto> resultData = new CmsCommonPagination<>();
		resultData.setData(datas);
		resultData.setTotalData(param.totalData);
		return resultData;
	};

	private void setConditionSearch(ReportGroupSearchDetailDto data, int level) {
		if (StringUtils.isNotEmpty(data.getAgentGroup())) {
			switch (data.getAgentGroup()) {
			case "CAO":
				if (level == 1)
					data.setBdahName(null);
				if (level == 2)
					data.setBdthName(null);
				break;
			case "TH":
				if (level == 1)
					data.setBdrhName(null);
				if (level == 2)
					data.setBdahName(null);
				break;
			case "AH":
				if (level == 1)
					data.setBdohName(null);
				if (level == 2)
					data.setBdrhName(null);
				break;
			case "RH":
				if (level == 1)
					data.setGaName(null);
				if (level == 2)
					data.setBdohName(null);
				break;
			case "OH":
				if (level == 1)
					data.setBranchName(null);
				if (level == 2)
					data.setGaName(null);
				break;
			case "GA":
				if (level == 1)
					data.setUnitName(null);
				if (level == 2)
					data.setBranchName(null);
				break;
			default:
				break;
			}
		}

	}

	private void mapAgentManPower(ReportGroupResultManPowerDetailDto data, String agentGroup, boolean first) {
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
				case "BM":
					data.setUnitCode(data.getAgentCode());
					data.setUnitName("Tổng cộng");
					data.setUnitType(data.getAgentType());
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
					data.setCaoType(data.getAgentGroup());
					break;
				case "TH":
					data.setBdthCode(data.getAgentCode());
					data.setBdthName(data.getOrgId() + ": " + data.getAgentCode() + "-" + data.getAgentName());
					data.setBdthType(data.getAgentGroup());
					break;
				case "AH":
					data.setBdahCode(data.getAgentCode());
					data.setBdahName(data.getOrgId() + ": " + data.getAgentCode() + "-" + data.getAgentName());
					data.setBdahType(data.getAgentGroup());
					break;
				case "RH":
					data.setBdrhCode(data.getAgentCode());
					data.setBdrhName(data.getOrgId() + ": " + data.getAgentCode() + "-" + data.getAgentName());
					data.setBdrhType(data.getAgentGroup());
					break;
				case "OH":
					data.setBdohCode(data.getAgentCode());
					String orgId = data.getAgentType();
					if (data.getTreeLevel() == 2) {
						orgId = data.getOrgId();
						data.setOrgCode(data.getOrgId());
					}
					
					data.setBdohName(orgId + "-" + data.getAgentCode() + "-" + data.getAgentName());
					data.setBdohType(data.getAgentType());				
					if (StringUtils.contains(data.getAgentName(), "Dummy Sales")) {
						data.setBdohName(data.getOrgId() + "-" + data.getAgentName());
					}				
					break;
				case "SO":
					data.setGaCode(data.getAgentCode());
					data.setGaName(data.getOrgId() + "-" + data.getParentAgentCode() + "-" + data.getAgentName());
					if (StringUtils.contains(data.getAgentName(), "Dummy Sales")) {
						data.setGaName(data.getOrgId() + "-" + data.getAgentName());

					}
					data.setGaType(data.getAgentType());
					break;
				case "GA":
					data.setGaCode(data.getAgentCode());
					data.setGaName(data.getOrgId() + "-" + data.getParentAgentCode() + "-" + data.getAgentName());
					if (StringUtils.contains(data.getAgentName(), "Dummy Sales")) {
						data.setGaName(data.getOrgId() + "-" + data.getAgentName());

					}
					data.setGaType(data.getAgentType());
					break;
				case "BM":
					data.setBranchCode(data.getAgentCode());
					data.setBranchName(data.getAgentType() + ": " + data.getAgentCode().replace(data.getOrgId(), "") + "-" + data.getAgentName());
					data.setBranchType(data.getAgentType());
					if (StringUtils.contains(data.getAgentName(), "Dummy Sales")) {
						data.setBranchName(data.getOrgId() + "-" + data.getAgentName());
					}
					break;
				case "UM":
					data.setUnitCode(data.getAgentCode());
					data.setUnitName(data.getAgentType() + ": " + data.getAgentCode().replace("A", "").replace("B", "").replace("C", "") + "-" + data.getAgentName());
					data.setUnitType(data.getAgentType());
					if (StringUtils.contains(data.getAgentName(), "Dummy Sales")) {
						data.setUnitName(data.getOrgId() + "-" + data.getAgentName());
					}
					break;
				default:
					data.setAgentCode(data.getAgentCode());
					break;
				}
			}
		}
	}

	private String agentPosition(String agentType, String agentCode, String agentName) {
		return agentType + ": " + agentCode + " - " + agentName;
	}

	private String formatYyyyMM(String year, String month) {
		Date currentDate = new Date();
		if (month != null && Integer.parseInt(month) < 10) {
			month = ("0" + Integer.parseInt(month));
		}
		// default current date
		if (year == null) {
			year = (new SimpleDateFormat("yyyy").format(currentDate));
		}
		if (month == null) {
			month = (new SimpleDateFormat("MM").format(currentDate));
		}
		String yyyyMM = year + month;
		return yyyyMM;
	}
	
	private String formatMmYyyy(String year, String month) {
		Date currentDate = new Date();
		if (month != null && Integer.parseInt(month) < 10) {
			month = ("0" + Integer.parseInt(month));
		}
		// default current date
		if (year == null) {
			year = (new SimpleDateFormat("yyyy").format(currentDate));
		}
		if (month == null) {
			month = (new SimpleDateFormat("MM").format(currentDate));
		}
		String MMyyyy = month + "/"+ year;
		return MMyyyy;
	}

}
