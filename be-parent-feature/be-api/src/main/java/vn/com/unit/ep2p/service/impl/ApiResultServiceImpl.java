package vn.com.unit.ep2p.service.impl;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

import javax.servlet.ServletContext;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import vn.com.unit.cms.core.dto.CmsCommonPagination;
import vn.com.unit.cms.core.dto.CommonSearchWithPagingDto;
import vn.com.unit.cms.core.module.result.dto.*;
import vn.com.unit.db.service.SqlManagerDb2Service;
import vn.com.unit.core.config.SystemConfig;
import vn.com.unit.core.security.UserProfileUtils;
import vn.com.unit.ep2p.admin.dto.Db2AgentDto;
import vn.com.unit.ep2p.admin.service.Db2ApiService;
import vn.com.unit.ep2p.enumdef.ResultMaintainBMEnum;
import vn.com.unit.ep2p.enumdef.ResultMaintainDFAEnum;
import vn.com.unit.ep2p.enumdef.ResultMaintainDFAMEnum;
import vn.com.unit.ep2p.enumdef.ResultMaintainPUMEnum;
import vn.com.unit.ep2p.enumdef.ResultMaintainSBMEnum;
import vn.com.unit.ep2p.enumdef.ResultMaintainSUMEnum;
import vn.com.unit.ep2p.enumdef.ResultMaintainUMEnum;
import vn.com.unit.ep2p.enumdef.ResultPromotionBMEnum;
import vn.com.unit.ep2p.enumdef.ResultPromotionDFAMEnum;
import vn.com.unit.ep2p.enumdef.ResultPromotionFCPUMEnum;
import vn.com.unit.ep2p.enumdef.ResultPromotionSBMEnum;
import vn.com.unit.ep2p.enumdef.ResultPromotionSUMEnum;
import vn.com.unit.ep2p.enumdef.ResultPromotionUMEnum;
import vn.com.unit.ep2p.service.ApiResultService;
import vn.com.unit.ep2p.service.ParseJsonToParamSearchService;
import vn.com.unit.imp.excel.constant.CommonConstant;
import vn.com.unit.imp.excel.dto.ItemColsExcelDto;
import vn.com.unit.imp.excel.utils.ExportExcelUtil;
import vn.com.unit.imp.excel.utils.ImportExcelUtil;

@Service
@Transactional(transactionManager = "transactionManagerDb2", readOnly = true, rollbackFor = Exception.class)
public class ApiResultServiceImpl implements ApiResultService {

	private Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private SystemConfig systemConfig;

	@Autowired
	@Qualifier("sqlManageDb2Service")
	private SqlManagerDb2Service sqlManagerDb2Service;

	@Autowired
	private ServletContext servletContext;

	@Autowired
	private Db2ApiService db2ApiService;

	@Autowired
	ParseJsonToParamSearchService parseJsonToParamSearchService;

	private static final String DS_SP_GET_PROMOTE_PUM = "RPT_ODS.DS_SP_GET_PROMOTE_PUM";
	private static final String DS_SP_GET_PROMOTE_BM = "RPT_ODS.DS_SP_GET_PROMOTE_BM";
	private static final String DS_SP_GET_PROMOTE_DFA = "RPT_ODS.DS_SP_GET_PROMOTE_DFA";
	private static final String DS_SP_GET_PROMOTE_SBM = "RPT_ODS.DS_SP_GET_PROMOTE_SBM";
	private static final String DS_SP_GET_PROMOTE_SUM = "RPT_ODS.DS_SP_GET_PROMOTE_SUM";
	private static final String DS_SP_GET_PROMOTE_UM = "RPT_ODS.DS_SP_GET_PROMOTE_UM";
	
	private static final String DS_SP_GET_PROMOTE_UM_GROUP = "RPT_ODS.DS_SP_GET_PROMOTE_UM_GROUP";
	private static final String DS_SP_GET_PROMOTE_SUM_GROUP = "RPT_ODS.DS_SP_GET_PROMOTE_SUM_GROUP";
	private static final String DS_SP_GET_PROMOTE_BM_GROUP = "RPT_ODS.DS_SP_GET_PROMOTE_BM_GROUP";
	private static final String DS_SP_GET_PROMOTE_SBM_GROUP = "RPT_ODS.DS_SP_GET_PROMOTE_SBM_GROUP";
	private static final String DS_SP_GET_PROMOTE_DFA_GROUP = "RPT_ODS.DS_SP_GET_PROMOTE_DFA_GROUP";

	private static final String DS_SP_GET_MAINTENANCE_FC = "RPT_ODS.DS_SP_GET_MAINTENANCE_FC";
	private static final String DS_SP_GET_MAINTENANCE_SBM = "RPT_ODS.DS_SP_GET_MAINTENANCE_SBM";
	private static final String DS_SP_GET_MAINTENANCE_BM = "RPT_ODS.DS_SP_GET_MAINTENANCE_BM";
	private static final String DS_SP_GET_MAINTENANCE_SUM = "RPT_ODS.DS_SP_GET_MAINTENANCE_SUM";
	private static final String DS_SP_GET_MAINTENANCE_PUM = "RPT_ODS.DS_SP_GET_MAINTENANCE_PUM";
	private static final String DS_SP_GET_MAINTENANCE_UM = "RPT_ODS.DS_SP_GET_MAINTENANCE_UM";
	private static final String DS_SP_GET_MAINTENANCE_DFA = "RPT_ODS.DS_SP_GET_MAINTENANCE_DFA";
	private static final String DS_SP_GET_MAINTENANCE_DFAM = "RPT_ODS.DS_SP_GET_MAINTENANCE_DFAM";
	
	private static final String DS_SP_GET_MAINTENANCE_SBM_GROUP = "RPT_ODS.DS_SP_GET_MAINTENANCE_SBM_GROUP";
	private static final String DS_SP_GET_MAINTENANCE_BM_GROUP = "RPT_ODS.DS_SP_GET_MAINTENANCE_BM_GROUP";
	private static final String DS_SP_GET_MAINTENANCE_SUM_GROUP = "RPT_ODS.DS_SP_GET_MAINTENANCE_SUM_GROUP";
	private static final String DS_SP_GET_MAINTENANCE_PUM_GROUP = "RPT_ODS.DS_SP_GET_MAINTENANCE_PUM_GROUP";
	private static final String DS_SP_GET_MAINTENANCE_UM_GROUP = "RPT_ODS.DS_SP_GET_MAINTENANCE_UM_GROUP";
	private static final String DS_SP_GET_MAINTENANCE_DFA_GROUP = "RPT_ODS.DS_SP_GET_MAINTENANCE_DFA_GROUP";
	private static final String DS_SP_GET_MAINTENANCE_DFAM_GROUP = "RPT_ODS.DS_SP_GET_MAINTENANCE_DFAM_GROUP";


	@Override
	public SystemConfig getSystemConfig() {
		return systemConfig;
	}

	@Transactional(transactionManager = "transactionManagerDb2", readOnly = true, rollbackFor = Exception.class)
	public ResultParamGroup callDb2ResultGroup(String store, ResultSearchDto searchDto, String functionCode) {

		searchDto.setFunctionCode(functionCode);
		ObjectMapper mapper = new ObjectMapper();
		String stringJsonParam = "";
		try {
			stringJsonParam = mapper.writeValueAsString(searchDto);
		} catch (JsonProcessingException e) {
			logger.error("Exception ", e);
		}
		CommonSearchWithPagingDto common = parseJsonToParamSearchService.getSearchCondition(stringJsonParam, functionCode);

		ResultParamGroup param = new ResultParamGroup();
		param.agentCode = searchDto.getAgentCode();
		param.agentGroup = searchDto.getAgentGroup();
		param.orgCode = searchDto.getOrgCode();
		param.yyyyMM = searchDto.getYyyyMM();
		param.page = common.getPage();
		param.pageSize = common.getPageSize();
		param.sort = common.getSort();
		param.search = common.getSearch();
		sqlManagerDb2Service.call(store +"_Group", param);

		return param;
	}
	@Transactional(transactionManager = "transactionManagerDb2", readOnly = true, rollbackFor = Exception.class)
	public ResultParamGroupCustom callDb2ResultGroupCustom(String store, ResultSearchDto searchDto, String functionCode) {

		searchDto.setFunctionCode(functionCode);
		ObjectMapper mapper = new ObjectMapper();
		String stringJsonParam = "";
		try {
			stringJsonParam = mapper.writeValueAsString(searchDto);
		} catch (JsonProcessingException e) {
			logger.error("Exception ", e);
		}
		CommonSearchWithPagingDto common = parseJsonToParamSearchService.getSearchCondition(stringJsonParam, functionCode);

		ResultParamGroupCustom param = new ResultParamGroupCustom();
		param.agentCode = searchDto.getAgentCode();
		param.agentGroup = searchDto.getAgentGroup();
		param.orgCode = searchDto.getOrgCode();
		param.yyyyMM = searchDto.getYyyyMM();
		param.page = common.getPage();
		param.pageSize = common.getPageSize();
		param.sort = common.getSort();
		param.search = common.getSearch();
		sqlManagerDb2Service.call(store +"_Group", param);

		return param;
	}
	
	@Override
	public CmsCommonPagination<ResultDto> getLisResultPromotion(ResultSearchDto searchDto) {
		String store = fillParamPromoteGroup(searchDto);
		ResultParamGroupCustom callDb2Result = callDb2ResultGroupCustom(store, searchDto, "OFFICE_PROMOTION_" + searchDto.getAgentTitle());
		List<ResultDto> datas = callDb2Result.data;
		List<Db2AgentDto> description = db2ApiService.getDescription();

		CmsCommonPagination<ResultDto> resultData = new CmsCommonPagination<>();
		for (ResultDto ls : datas) {
			if(ls.getK2PlusAdd() != null) {
				ls.setK2PlusAddStr(String.valueOf(ls.getK2PlusAdd().multiply(new BigDecimal(100)).setScale(2, RoundingMode.FLOOR)).concat("%"));
				ls.setK2PlusAdd(ls.getK2PlusAdd().multiply(new BigDecimal(100)));
			}
			if(ls.getK2Plus() != null){
				ls.setK2PlusStr(String.valueOf(ls.getK2Plus().multiply(new BigDecimal(100)).setScale(2, RoundingMode.FLOOR)).concat("%"));
				ls.setK2Plus(ls.getK2Plus().multiply(new BigDecimal(100)));
			}
			ls.setBdth(ls.getTerritoryCode() + " - " + ls.getTerritoryName());
			ls.setBdah(ls.getAreaCode() + " - " + ls.getAreaName());
//			ls.setBdrh(ls.getRegionCode() + " - " + ls.getRegionName());
			ls.setBdoh(ls.getOfficeCode() + " - " + ls.getOfficeName());
			ls.setAgentType(searchDto.getAgentTitle());

			Db2AgentDto data = description.stream().filter(x-> StringUtils.equalsIgnoreCase(x.getAgentType(),ls.getAgentType())).findAny().orElse(new Db2AgentDto());
			ls.setAgentTypeName(data.getAgentTypeName());

			Db2AgentDto datast = description.stream().filter(x-> StringUtils.equalsIgnoreCase(x.getAgentType(),ls.getEvaluatePromotion())).findAny().orElse(new Db2AgentDto());
			ls.setEvaluatePromotionName(datast.getAgentTypeName());

		}
		resultData.setData(datas);

		resultData.setTotalData(callDb2Result.totalData);
		resultData.setTotalPromoteDemote(callDb2Result.totalDemotePromote);
		return resultData;

	}

	@Override
	public CmsCommonPagination<ResultDto> getLisResultMaintain(ResultSearchDto searchDto) {
		String store = fillParamMaintenance(searchDto);
		ResultParamGroupCustom callDb2Result = callDb2ResultGroupCustom(store, searchDto, "OFFICE_MAINTENANCE_" + searchDto.getAgentTitle());
		List<ResultDto> datas = callDb2Result.data;
		List<Db2AgentDto> description = db2ApiService.getDescription();

		CmsCommonPagination<ResultDto> resultData = new CmsCommonPagination<>();

		for (ResultDto ls : datas) {
			if(ls.getK2PlusAdd() != null) {
				ls.setK2PlusAddStr(String.valueOf(ls.getK2PlusAdd().multiply(new BigDecimal(100)).setScale(2, RoundingMode.FLOOR)).concat("%"));
				ls.setK2PlusAdd(ls.getK2PlusAdd().multiply(new BigDecimal(100)));
			}
			if(ls.getK2Plus() != null){
				ls.setK2PlusStr(String.valueOf(ls.getK2Plus().multiply(new BigDecimal(100)).setScale(2, RoundingMode.FLOOR)).concat("%"));
				ls.setK2Plus(ls.getK2Plus().multiply(new BigDecimal(100)));
			}
			ls.setBdth(ls.getTerritoryCode() + " - " + ls.getTerritoryName());
			ls.setBdah(ls.getAreaCode() + " - " + ls.getAreaName());
//			ls.setBdrh(ls.getRegionCode() + " - " + ls.getRegionName());
			ls.setBdoh(ls.getOfficeCode() + " - " + ls.getOfficeName());
			ls.setAgentType(searchDto.getAgentTitle());

			Db2AgentDto data = description.stream().filter(x-> StringUtils.equalsIgnoreCase(x.getAgentType(),ls.getAgentType())).findAny().orElse(new Db2AgentDto());
			ls.setAgentTypeName(data.getAgentTypeName());

			Db2AgentDto datast = description.stream().filter(x-> StringUtils.equalsIgnoreCase(x.getAgentType(),ls.getEvaluatePromotion())).findAny().orElse(new Db2AgentDto());
			ls.setEvaluatePromotionName(datast.getAgentTypeName());


		}

		resultData.setData(datas);
		resultData.setTotalData(callDb2Result.totalData);
		resultData.setTotalPromoteDemote(callDb2Result.totalDemotePromote);
		return resultData;
	}

	
	
	@Override
	public ResultDto getLisResultPromotionPersonal(ResultSearchDto searchDto) throws ParseException {
		searchDto.setPage(0);
		searchDto.setPageSize(1);
		searchDto.setAgentCode(UserProfileUtils.getFaceMask());
		
		String agentType = db2ApiService.getAgentTypeByAgentDate(searchDto.getAgentCode(),searchDto.getYyyyMM());
		if(StringUtils.isNotBlank(agentType)){
			searchDto.setAgentTitle(agentType);
		}
		
		String store = fillParamPromote(searchDto);
		ResultParam callDb2Result = callDb2Result(store, searchDto, null);
		ResultDto lstData = new ResultDto();	
		List<Db2AgentDto> description = db2ApiService.getDescription();
		
		if (callDb2Result.data.size() > 0) {
			lstData = callDb2Result.data.get(0);
			ResultDto finalLstData = lstData;
			if(lstData.getK2PlusAdd() != null) {
				lstData.setK2PlusAdd(lstData.getK2PlusAdd().multiply(new BigDecimal(100)));
			}
			if(lstData.getK2Plus() != null){
				lstData.setK2Plus(lstData.getK2Plus().multiply(new BigDecimal(100)));
			}
			Db2AgentDto datas = description.stream().filter(x-> StringUtils.equalsIgnoreCase(x.getAgentType(),finalLstData.getEvaluatePromotion())).findAny().orElse(new Db2AgentDto());
			lstData.setEvaluatePromotionName(datas.getAgentTypeName());
		}
		
		
		switch (searchDto.getAgentTitle()){
		case "SA":
		case "FC":
			lstData.setAgentType("PUM");
			break;
		case "PUM":
			lstData.setAgentType("UM");
			break;
		case "UM":
			lstData.setAgentType("SUM");
			break;
		case "SUM":
			lstData.setAgentType("BM");
			break;
		case "BM":
			lstData.setAgentType("SBM");
			break;
		case "DFA":
			lstData.setAgentType("DFAM");
			break;
		default:
			break;
	}	
		final String searchType = lstData.getAgentType();
		
		Db2AgentDto data = description.stream().filter(x-> StringUtils.equalsIgnoreCase(x.getAgentType(),searchType)).findAny().orElse(new Db2AgentDto());
		lstData.setAgentTypeName(data.getAgentTypeName());
		lstData.setAgentTitle(searchDto.getAgentTitle());
		return lstData;
	}


	@Override
	public ResultDto getLisResultMaintainPersonal(ResultSearchDto searchDto) throws ParseException {
		searchDto.setPage(0);
		searchDto.setPageSize(1);
		String agentType = db2ApiService.getAgentTypeByAgentDate(searchDto.getAgentCode(),searchDto.getYyyyMM());
		if(StringUtils.isNotBlank(agentType)){
			searchDto.setAgentTitle(agentType);
		}
		String store = fillParamMaintenance(searchDto);
		ResultParam callDb2Result = callDb2Result(store, searchDto, null);
		List<Db2AgentDto> description = db2ApiService.getDescription();

		ResultDto lstData = new ResultDto();
		if (callDb2Result.data.size() > 0) {
			lstData = callDb2Result.data.get(0);
			ResultDto finalLstData = lstData;
			if(lstData.getK2PlusAdd() != null) {
				lstData.setK2PlusAdd(lstData.getK2PlusAdd().multiply(new BigDecimal(100)));
			}
			if(lstData.getK2Plus() != null){
				lstData.setK2Plus(lstData.getK2Plus().multiply(new BigDecimal(100)));
			}
			Db2AgentDto datas = description.stream().filter(x-> StringUtils.equalsIgnoreCase(x.getAgentType(),finalLstData.getEvaluatePromotion())).findAny().orElse(new Db2AgentDto());
			lstData.setEvaluatePromotionName(datas.getAgentTypeName());

		}
		
		switch (searchDto.getAgentTitle()){
		case "SA":
			lstData.setAgentType("SA");
			break;
		case "FC":
			lstData.setAgentType("FC");
			break;
		case "PUM":
			lstData.setAgentType("PUM");
			break;
		case "UM":
			lstData.setAgentType("UM");
			break;
		case "SUM":
			lstData.setAgentType("SUM");
			break;
		case "BM":
			lstData.setAgentType("BM");
			break;
		case "SBM":
			lstData.setAgentType("SBM");
			break;
		case "DFA":
			lstData.setAgentType("DFA");
			break;
		case "DFAM":
		case "DFARM":
		case "DFASM":
			lstData.setAgentType("DFAM");
			break;
		default:
			break;
	}
		final String searchType = lstData.getAgentType();

		Db2AgentDto data = description.stream().filter(x-> StringUtils.equalsIgnoreCase(x.getAgentType(),searchType)).findAny().orElse(new Db2AgentDto());
		lstData.setAgentTypeName(data.getAgentTypeName());
		return lstData;
	}
	
	@Transactional(transactionManager = "transactionManagerDb2", readOnly = true, rollbackFor = Exception.class)
	public ResultParam callDb2Result(String store, ResultSearchDto searchDto, String functionCode) {

		searchDto.setFunctionCode(functionCode);
		ObjectMapper mapper = new ObjectMapper();
		String stringJsonParam = "";
		try {
			stringJsonParam = mapper.writeValueAsString(searchDto);
		} catch (JsonProcessingException e) {
			logger.error("Exception ", e);
		}
		CommonSearchWithPagingDto common = parseJsonToParamSearchService.getSearchCondition(stringJsonParam, functionCode);

		ResultParam param = new ResultParam();
		param.agentCode = searchDto.getAgentCode();
		param.yyyyMM = searchDto.getYyyyMM();
		param.page = common.getPage();
		param.pageSize = common.getPageSize();
		param.sort = common.getSort();
		param.search = common.getSearch();
		sqlManagerDb2Service.call(store, param);

		return param;
	}
	
	

	public String fillParamPromote(ResultSearchDto searchDto) {
		String store = null;
		if (StringUtils.equalsIgnoreCase(searchDto.getAgentTitle(), "PUM")) {
			store = DS_SP_GET_PROMOTE_UM;
		} else if (StringUtils.equalsIgnoreCase(searchDto.getAgentTitle(), "UM")) {
			store = DS_SP_GET_PROMOTE_SUM;
		} else if (StringUtils.equalsIgnoreCase(searchDto.getAgentTitle(), "SUM")) {
			store = DS_SP_GET_PROMOTE_BM;
		} else if (StringUtils.equalsIgnoreCase(searchDto.getAgentTitle(), "BM")) {
			store = DS_SP_GET_PROMOTE_SBM;
		} else if (StringUtils.equalsIgnoreCase(searchDto.getAgentTitle(), "DFA")) {
			store = DS_SP_GET_PROMOTE_DFA;
		}else{
			store = DS_SP_GET_PROMOTE_PUM;

		}
		return store;
	}
	public String fillParamPromoteGroup(ResultSearchDto searchDto) {
		String store = null;
		if (StringUtils.equalsIgnoreCase(searchDto.getAgentTitle(), "UM")) {
			store = DS_SP_GET_PROMOTE_UM;
		} else if (StringUtils.equalsIgnoreCase(searchDto.getAgentTitle(), "SUM")) {
			store = DS_SP_GET_PROMOTE_SUM;
		} else if (StringUtils.equalsIgnoreCase(searchDto.getAgentTitle(), "BM")) {
			store = DS_SP_GET_PROMOTE_BM;
		} else if (StringUtils.equalsIgnoreCase(searchDto.getAgentTitle(), "SBM")) {
			store = DS_SP_GET_PROMOTE_SBM;
		} else if (StringUtils.equalsIgnoreCase(searchDto.getAgentTitle(), "DFA")) {
			store = DS_SP_GET_PROMOTE_DFA;
		}else if (StringUtils.equalsIgnoreCase(searchDto.getAgentTitle(), "PUM")) {
			store = DS_SP_GET_PROMOTE_PUM;
		}
		return store;
	}
	public String fillParamMaintenance(ResultSearchDto searchDto) {
		String store = null;
		if (StringUtils.equalsIgnoreCase(searchDto.getAgentTitle(), "PUM")) {
			store = DS_SP_GET_MAINTENANCE_PUM;
		} else if (StringUtils.equalsIgnoreCase(searchDto.getAgentTitle(), "UM")) {
			store = DS_SP_GET_MAINTENANCE_UM;
		} else if (StringUtils.equalsIgnoreCase(searchDto.getAgentTitle(), "SUM")) {
			store = DS_SP_GET_MAINTENANCE_SUM;
		} else if (StringUtils.equalsIgnoreCase(searchDto.getAgentTitle(), "BM")) {
			store = DS_SP_GET_MAINTENANCE_BM;
		} else if (StringUtils.equalsIgnoreCase(searchDto.getAgentTitle(), "SBM")) {
			store = DS_SP_GET_MAINTENANCE_SBM;
		} else if (StringUtils.equalsIgnoreCase(searchDto.getAgentTitle(), "FC") || StringUtils.equalsIgnoreCase(searchDto.getAgentTitle(), "SA")) {
			store = DS_SP_GET_MAINTENANCE_FC;
		} else if (StringUtils.equalsIgnoreCase(searchDto.getAgentTitle(), "DFA")) {
			store = DS_SP_GET_MAINTENANCE_DFA;
		} else if (StringUtils.equalsIgnoreCase(searchDto.getAgentTitle(), "DFAM")
				|| StringUtils.equalsIgnoreCase(searchDto.getAgentTitle(), "DFARM")
				|| StringUtils.equalsIgnoreCase(searchDto.getAgentTitle(), "DFASM")) {
			store = DS_SP_GET_MAINTENANCE_DFAM;
		}
		return store;
	}

	@Override
	public ResponseEntity exportPromotion(ResultSearchDto searchDto, Locale locale) {
		ResponseEntity res = null;
		try {
			CmsCommonPagination<ResultDto> resObj = getLisResultPromotion(searchDto);

			// start fill data to workbook
			List<ItemColsExcelDto> cols = new ArrayList<>();
			String templateName = null;
			if (StringUtils.equalsIgnoreCase(searchDto.getAgentTitle(), "PUM")) {
				templateName = "Ket_qua_thang_chuc_PUM.xlsx";
				ImportExcelUtil.setListColumnExcel(ResultPromotionFCPUMEnum.class, cols);
			} else if (StringUtils.equalsIgnoreCase(searchDto.getAgentTitle(), "UM")) {
				templateName = "Ket_qua_thang_chuc_UM.xlsx";
				ImportExcelUtil.setListColumnExcel(ResultPromotionFCPUMEnum.class, cols);
			} else if (StringUtils.equalsIgnoreCase(searchDto.getAgentTitle(), "SUM")) {
				templateName = "Ket_qua_thang_chuc_SUM.xlsx";
				ImportExcelUtil.setListColumnExcel(ResultPromotionUMEnum.class, cols);
			} else if (StringUtils.equalsIgnoreCase(searchDto.getAgentTitle(), "BM")) {
				templateName = "Ket_qua_thang_chuc_BM.xlsx";
				ImportExcelUtil.setListColumnExcel(ResultPromotionSUMEnum.class, cols);
			} else if (StringUtils.equalsIgnoreCase(searchDto.getAgentTitle(), "SBM")) {
				templateName = "Ket_qua_thang_chuc_SBM.xlsx";
				ImportExcelUtil.setListColumnExcel(ResultPromotionBMEnum.class, cols);
			} else if (StringUtils.equalsIgnoreCase(searchDto.getAgentTitle(), "DFA")) {
				templateName = "Ket_qua_thang_chuc_DFAM.xlsx";
				ImportExcelUtil.setListColumnExcel(ResultPromotionDFAMEnum.class, cols);
			}

			String templatePath = servletContext.getRealPath(CommonConstant.REAL_PATH_TEMPLATE_EXCEL + "/" + templateName);
			String startRow = "A6";

			String datePattern = getSystemConfig().getConfig(SystemConfig.DATE_PATTERN);
			datePattern = "dd/MM/yyyy";
			List<ResultDto> lstdata = resObj.getData();

			ExportExcelUtil exportExcel = new ExportExcelUtil<>();
			Map<String, String> mapColFormat = null;// setMapColFormat();
			Map<String, Object> setMapColDefaultValue = null;// setMapColDefaultValue();

			// do export
			try (XSSFWorkbook xssfWorkbook = exportExcel.getXSSFWorkbook(templatePath);) {
				Map<String, CellStyle> mapColStyle = null;// setMapColStyle(xssfWorkbook);
				SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
				String yyyy = searchDto.getYyyyMM().substring(0, 4);
				String MM = searchDto.getYyyyMM().substring(4, 6);
				XSSFSheet xssfSheet = xssfWorkbook.getSheetAt(0);
				xssfSheet.getRow(1).getCell(0).setCellValue("Kỳ xem xét Tháng " + MM + "/" + yyyy);

				Calendar calendar = Calendar.getInstance();
				calendar.setTime(new Date());
				calendar.add(Calendar.DATE, -1);

				xssfSheet.getRow(3).getCell(0).setCellValue("Cập nhật đến ngày " + sdf.format(calendar.getTime()));

				String repo = systemConfig.getConfig(systemConfig.REPO_UPLOADED_MAIN);
				String path = systemConfig.getPhysicalPathById(repo, null); //path up service
				res = exportExcel.doExportExcelHeaderWithColFormatRestUpService(xssfWorkbook, 0, null, lstdata,
						ResultDto.class, cols, datePattern, startRow, mapColFormat, mapColStyle,
						setMapColDefaultValue, null, true, templateName, true,path);
			} catch (Exception e) {
				throw new Exception(e.getMessage());
			}
		} catch (Exception e) {
		}
		return res;
	}

	@Override
	public ResponseEntity exportMaintain(ResultSearchDto searchDto, Locale locale) {
		ResponseEntity res = null;
		try {
			CmsCommonPagination<ResultDto> resObj = getLisResultMaintain(searchDto);
			List<ResultDto> lstdata = resObj.getData();

			// start fill data to workbook
			List<ItemColsExcelDto> cols = new ArrayList<>();

			String templateName = null;

			if (StringUtils.equalsIgnoreCase(searchDto.getAgentTitle(), "PUM")) {
				templateName = "Ket_qua_duy_tri_PUM.xlsx";
				ImportExcelUtil.setListColumnExcel(ResultMaintainPUMEnum.class, cols);

			}
			if (StringUtils.equalsIgnoreCase(searchDto.getAgentTitle(), "UM")) {
				templateName = "Ket_qua_duy_tri_UM.xlsx";
				ImportExcelUtil.setListColumnExcel(ResultMaintainUMEnum.class, cols);

			}
			if (StringUtils.equalsIgnoreCase(searchDto.getAgentTitle(), "SUM")) {
				templateName = "Ket_qua_duy_tri_SUM.xlsx";
				ImportExcelUtil.setListColumnExcel(ResultMaintainSUMEnum.class, cols);
			}
			if (StringUtils.equalsIgnoreCase(searchDto.getAgentTitle(), "BM")) {
				templateName = "Ket_qua_duy_tri_BM.xlsx";
				ImportExcelUtil.setListColumnExcel(ResultMaintainBMEnum.class, cols);
			}
			if (StringUtils.equalsIgnoreCase(searchDto.getAgentTitle(), "SBM")) {
				templateName = "Ket_qua_duy_tri_SBM.xlsx";
				ImportExcelUtil.setListColumnExcel(ResultMaintainSBMEnum.class, cols);
			}
			if (StringUtils.equalsIgnoreCase(searchDto.getAgentTitle(), "DFA")) {
				templateName = "Ket_qua_duy_tri_DFA.xlsx";
				ImportExcelUtil.setListColumnExcel(ResultMaintainDFAEnum.class, cols);
			}
			if (StringUtils.equalsIgnoreCase(searchDto.getAgentTitle(), "DFAM")) {
				templateName = "Ket_qua_duy_tri_DFAM.xlsx";
				ImportExcelUtil.setListColumnExcel(ResultMaintainDFAMEnum.class, cols);

			}
			String templatePath = servletContext.getRealPath(CommonConstant.REAL_PATH_TEMPLATE_EXCEL + "/" + templateName);
			String startRow = "A7";

			String datePattern = getSystemConfig().getConfig(SystemConfig.DATE_PATTERN);
			datePattern = "dd/MM/yyyy";

			ExportExcelUtil exportExcel = new ExportExcelUtil<>();
			Map<String, String> mapColFormat = null;// setMapColFormat();
			Map<String, Object> setMapColDefaultValue = null;// setMapColDefaultValue();

			// do export
			try (XSSFWorkbook xssfWorkbook = exportExcel.getXSSFWorkbook(templatePath);) {
				Map<String, CellStyle> mapColStyle = null;// setMapColStyle(xssfWorkbook);

				SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
				String yyyy = searchDto.getYyyyMM().substring(0, 4);
				String MM = searchDto.getYyyyMM().substring(4, 6);
				XSSFSheet xssfSheet = xssfWorkbook.getSheetAt(0);
				xssfSheet.getRow(1).getCell(0).setCellValue("Kỳ xem xét Tháng " + MM + "/" + yyyy);

				Calendar calendar = Calendar.getInstance();
				calendar.setTime(new Date());
				calendar.add(Calendar.DATE, -1);

				xssfSheet.getRow(3).getCell(0).setCellValue("Cập nhật đến ngày " + sdf.format(calendar.getTime()));
				String repo = systemConfig.getConfig(systemConfig.REPO_UPLOADED_MAIN);
				String path = systemConfig.getPhysicalPathById(repo, null); //path up service
				res = exportExcel.doExportExcelHeaderWithColFormatRestUpService(xssfWorkbook, 0, locale, lstdata,
						ResultDto.class, cols, datePattern, startRow, mapColFormat, mapColStyle,
						setMapColDefaultValue, null, true, templateName, true,path);
			} catch (Exception e) {
				throw new Exception(e.getMessage());
			}
		} catch (Exception e) {
		}
		return res;
	}

	@Override
	public boolean checkAgentType(String agentType) {
		return db2ApiService.checkAgentTypeIsDfa(agentType);
	}
}