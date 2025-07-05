package vn.com.unit.ep2p.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
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

import vn.com.unit.cms.core.dto.CmsCommonPagination;
import vn.com.unit.cms.core.dto.CommonSearchWithPagingDto;
import vn.com.unit.cms.core.module.report.dto.ReportActiveContractMonthDto;
import vn.com.unit.cms.core.module.report.dto.ReportActiveContractSearchDto;
import vn.com.unit.cms.core.module.report.dto.ReportActiveDto;
import vn.com.unit.cms.core.module.report.dto.ReportActiveSearchDto;
import vn.com.unit.cms.core.module.report.dto.ReportActivelContractParam;
import vn.com.unit.cms.core.module.report.dto.ReportActivelParam;
import vn.com.unit.core.config.SystemConfig;
import vn.com.unit.db.service.SqlManagerDb2Service;
import vn.com.unit.ep2p.enumdef.ReportActiveMonthEnum;
import vn.com.unit.ep2p.service.ApiReportActiveService;
import vn.com.unit.ep2p.service.ParseJsonToParamSearchService;
import vn.com.unit.imp.excel.constant.CommonConstant;
import vn.com.unit.imp.excel.dto.ItemColsExcelDto;
import vn.com.unit.imp.excel.utils.DateUtils;
import vn.com.unit.imp.excel.utils.ExportExcelUtil;
import vn.com.unit.imp.excel.utils.ImportExcelUtil;

@Service
@Transactional(transactionManager = "transactionManagerDb2", readOnly = true, rollbackFor = Exception.class)
public class ApiReportActiveServiceImpl implements ApiReportActiveService {
	private Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	@Qualifier("sqlManageDb2Service")
	private SqlManagerDb2Service sqlManagerDb2Service;
	
	@Autowired
	private ServletContext servletContext;
	
	@Autowired
	ParseJsonToParamSearchService parseJsonToParamSearchService;
	
	@Autowired
	private SystemConfig systemConfig;
	
	private static final String SP_REPORT_ACTIVE = "RPT_ODS.DS_SP_GET_INDIVIDUAL_ACTIVE_REPORTING";
	private static final String SP_REPORT_ACTIVE_3MONTHSAGO = "";
	private static final String SP_REPORT_ACTIVE_CONTRACT_MONTH = "RPT_ODS.DS_SP_GET_LIST_POLICY_MTD";
	private static final String SP_REPORT_ACTIVE_CONTRACT_YEAR = "RPT_ODS.DS_SP_GET_LIST_POLICY_YTD";

	private static final Logger LOGGER = LoggerFactory.getLogger(ApiReportActiveServiceImpl.class);

	@Override
	public SystemConfig getSystemConfig() {
		return systemConfig;
	}

	public CmsCommonPagination<ReportActiveDto> getListReportActiveByAgentCode(ReportActiveSearchDto dto) {
		ReportActivelParam param = new ReportActivelParam();
		param.agentCode = dto.getAgentCode();
		param.yyyyMM = dto.getYyyyMM();
		param.page = dto.getPage();
		param.pageSize = dto.getPageSize();
		param.sort = dto.getSort();
		param.search = dto.getSearch();
		sqlManagerDb2Service.call( SP_REPORT_ACTIVE , param);
		List<ReportActiveDto> datas = param.data;
		
		for(ReportActiveDto item : datas ) {
			if(item.getK2() != null){
				item.setK2(item.getK2().multiply(new BigDecimal(100)));
			}
			if(item.getK2plus() != null){
				item.setK2plus(item.getK2plus().multiply(new BigDecimal(100)));
			}
			if(item.getDataType().equals("IN_YEAR")) {				// YTD
				
				item.setYtdSumbitAmount(item.getMtdSumbitAmount());
				item.setYtdSumbitFyp(item.getMtdSumbitFyp());
				item.setYtdReleaseAmount(item.getMtdReleaseAmount());
				item.setYtdReleaseFyp(item.getMtdReleaseFyp());
				item.setYtdActionAmount(item.getMtdActionAmount());
				item.setYtdActionFyp(item.getMtdActionFyp());
				item.setYtdCancelAmount(item.getMtdCancelAmount());
				item.setYtdCancelFyp(item.getMtdCancelFyp());
				item.setYtdRenewalsFyp(item.getMtdRenewalsFyp());
				item.setYtdPee2Yearyp(item.getMtdPee2Yearyp());
				item.setYtdK2(item.getK2());
				item.setYtdK2Plus(item.getK2plus());
				
			}else if(item.getDataType().equals("LAST_YEAR1")) {		// YTD CUNG KY NAM NGOAI YTD
				
				item.setYtdAgoSumbitAmount(item.getMtdSumbitAmount());
				item.setYtdAgoSumbitFyp(item.getMtdSumbitFyp());
				item.setYtdAgoReleaseAmount(item.getMtdReleaseAmount());
				item.setYtdAgoReleaseFyp(item.getMtdReleaseFyp());
				item.setYtdAgoActionAmount(item.getMtdActionAmount());
				item.setYtdAgoActionFyp(item.getMtdActionFyp());
				item.setYtdAgoCancelAmount(item.getMtdCancelAmount());
				item.setYtdAgoCancelFyp(item.getMtdCancelFyp());
				item.setYtdAgoRenewalsFyp(item.getMtdRenewalsFyp());
				item.setYtdAgoPee2Yearyp(item.getMtdPee2Yearyp());
				item.setYtdAgoK2(item.getK2());
				item.setYtdAgoK2Plus(item.getK2plus());
				
			}else if(item.getDataType().equals("LAST_MONTH1")) {	// PHAT HANH THUAN 3 THANG TRUOC (THANG DAU TIEN)
				if(dto.getMonth().equals("01")) {
					item.setMonth1(12);
				}else {
					item.setMonth1(Integer.parseInt(dto.getMonth()) - 1);
				}
				item.setPolicyAmount1(item.getMtdActionAmount());
				item.setFyp1(item.getMtdActionFyp());
				if(item.getK2() != null){
					item.setK2Next1(item.getK2());
				}
				if(item.getK2plus() != null){
					item.setK2PlusNext1(item.getK2plus());
				}

			}else if(item.getDataType().equals("LAST_MONTH2")) {	// PHAT HANH THUAN 3 THANG TRUOC (THANG THU 2)
				if(dto.getMonth().equals("01")) {
					item.setMonth2(11);
				}else if(dto.getMonth().equals("02")) {
					item.setMonth2(12);
				}else {
					item.setMonth2(Integer.parseInt(dto.getMonth()) - 2);
				}
				item.setPolicyAmount2(item.getMtdActionAmount());
				item.setFyp2(item.getMtdActionFyp());
				if(item.getK2() != null){
					item.setK2Next2(item.getK2());
				}
				if(item.getK2plus() != null){
					item.setK2PlusNext2(item.getK2plus());
				}

			}else if(item.getDataType().equals("LAST_MONTH3")) {	// PHAT HANH THUAN 3 THANG TRUOC (THANG THU 3)
				if(dto.getMonth().equals("01")) {
					item.setMonth3(10);
				}else if(dto.getMonth().equals("02")) {
					item.setMonth3(11);
				}else if(dto.getMonth().equals("03")) {
					item.setMonth3(12);
				}else {
					item.setMonth3(Integer.parseInt(dto.getMonth()) - 3);
				}
				item.setPolicyAmount3(item.getMtdActionAmount());
				item.setFyp3(item.getMtdActionFyp());
				if(item.getK2() != null){
					item.setK2Next3(item.getK2());
				}
				if(item.getK2plus() != null){
					item.setK2PlusNext3(item.getK2plus());
				}

			}
		
		}
		CmsCommonPagination<ReportActiveDto> resultData = new CmsCommonPagination<>();
		resultData.setData(datas);
		resultData.setTotalData(param.totalData);
		return resultData;
	}

	@Override
	public CmsCommonPagination<ReportActiveDto> getListReportActive3MonthsAgoByAgent(ReportActiveSearchDto dto) {
		ReportActivelParam param = new ReportActivelParam();
		param.agentCode = dto.getAgentCode();
		param.yyyyMM = dto.getYyyyMM();
		param.page = dto.getPage();
		param.pageSize = dto.getPageSize();
		sqlManagerDb2Service.call( SP_REPORT_ACTIVE_3MONTHSAGO , param);
		List<ReportActiveDto> datas = param.data;
		CmsCommonPagination<ReportActiveDto> resultData = new CmsCommonPagination<>();
		resultData.setData(datas);
		resultData.setTotalData(param.totalData);
		return resultData;
	}

	@Override
	public CmsCommonPagination<ReportActiveContractMonthDto> getListContractMonth(ReportActiveContractSearchDto dto) {
		ReportActivelContractParam param = new ReportActivelContractParam();	
		dto.setFunctionCode("ACTIVE_REPORT_MONTH");
    	ObjectMapper mapper = new ObjectMapper();
    	String stringJsonParam ="";
    	try {
			stringJsonParam = mapper.writeValueAsString(dto);
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			logger.error("Exception ", e);
		}
    	
		CommonSearchWithPagingDto common = parseJsonToParamSearchService.getSearchCondition(stringJsonParam, "ACTIVE_REPORT_MONTH");
	
		param.type = dto.getKeyType() != null ? new Integer(dto.getKeyType()) : null;
		param.agentCode = dto.getAgentCode();
		param.yyyyMM = new Integer(dto.getYyyyMM());
		param.page = dto.getPage();
		param.pageSize = dto.getPageSize();
		param.sort = common.getSort();
		param.search = common.getSearch();
		sqlManagerDb2Service.call( SP_REPORT_ACTIVE_CONTRACT_MONTH , param);
		List<ReportActiveContractMonthDto> datas = param.data;

//		List<ReportActiveContractMonthDto> datas = new ArrayList<ReportActiveContractMonthDto>();
//		datas.add(new ReportActiveContractMonthDto(1, "KEY1", "KEY1", null, null, 1, 1, 1, 1, 1, 1, 1));
//		datas.add(new ReportActiveContractMonthDto(2, "KEY2", "KEY1", null, null, 1, 1, 1, 1, 1, 1, 1));
//		datas.add(new ReportActiveContractMonthDto(3, "KEY3", "KEY1", null, null, 1, 1, 1, 1, 1, 1, 1));
//		datas.add(new ReportActiveContractMonthDto(4, "KEY4", "KEY1", null, null, 1, 1, 1, 1, 1, 1, 1));
//		datas.add(new ReportActiveContractMonthDto(5, "KEY5", "KEY1", null, null, 1, 1, 1, 1, 1, 1, 1));
//		datas.add(new ReportActiveContractMonthDto(6, "KEY6", "KEY1", null, null, 1, 1, 1, 1, 1, 1, 1));

		
		if(!datas.isEmpty()) {		 
			for(ReportActiveContractMonthDto item : datas) {
				item.setSumFyp(item.getFypIssue() - item.getFypCancel());
//				item.setSumFypall(item.getFypIssue() + item.getFypCancel() + item.getSumFyp() + item.getFypReject() + item.getFypPending());
			}
			
			    ReportActiveContractMonthDto dataDto = new ReportActiveContractMonthDto();
			    dataDto.setPolicyKey("GrandTotal");
				dataDto.setFypIssue(datas.stream().map(ReportActiveContractMonthDto::getFypIssue).reduce(0, (a, b) -> a + b));
				dataDto.setFypCancel(datas.stream().map(ReportActiveContractMonthDto::getFypCancel).reduce(0, (a, b) -> a + b));
				dataDto.setSumFyp(datas.stream().map(ReportActiveContractMonthDto::getSumFyp).reduce(0, (a, b) -> a + b));
				datas.add(dataDto);
		}
		CmsCommonPagination<ReportActiveContractMonthDto> resultData = new CmsCommonPagination<>();
		resultData.setData(datas);

		resultData.setTotalData(param.totalData);
//		resultData.setTotalData(10);
		return resultData;
	}

	@Override
	public CmsCommonPagination<ReportActiveContractMonthDto> getListContractYear(ReportActiveContractSearchDto dto) {
		ReportActivelContractParam param = new ReportActivelContractParam();
		dto.setFunctionCode("ACTIVE_REPORT_MONTH");
		ObjectMapper mapper = new ObjectMapper();
		String stringJsonParam ="";
		try {
			stringJsonParam = mapper.writeValueAsString(dto);
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			logger.error("Exception ", e);
		}

		CommonSearchWithPagingDto common = parseJsonToParamSearchService.getSearchCondition(stringJsonParam, "ACTIVE_REPORT_MONTH");

		param.type = dto.getKeyType() != null ? new Integer(dto.getKeyType()) : null;
		param.agentCode = dto.getAgentCode();
		param.yyyyMM = new Integer(dto.getYyyyMM());
		param.page = dto.getPage();
		param.pageSize = dto.getPageSize();
		param.sort = common.getSort();
		param.search = common.getSearch();
		sqlManagerDb2Service.call( SP_REPORT_ACTIVE_CONTRACT_YEAR , param);
		List<ReportActiveContractMonthDto> datas = param.data;


		if(!datas.isEmpty()) {
			for(ReportActiveContractMonthDto item : datas) {
				item.setSumFyp(item.getFypIssue() - item.getFypCancel());
				item.setSumFypall(item.getFypIssue() + item.getFypCancel() + item.getSumFyp() + item.getFypReject() + item.getFypPending());
			}

			ReportActiveContractMonthDto dataDto = new ReportActiveContractMonthDto();
			dataDto.setPolicyKey("GrandTotal");
			dataDto.setFypIssue(datas.stream().map(ReportActiveContractMonthDto::getFypIssue).reduce(0, (a, b) -> a + b));
			dataDto.setFypCancel(datas.stream().map(ReportActiveContractMonthDto::getFypCancel).reduce(0, (a, b) -> a + b));
			dataDto.setSumFyp(datas.stream().map(ReportActiveContractMonthDto::getSumFyp).reduce(0, (a, b) -> a + b));
			datas.add(dataDto);
		}
		CmsCommonPagination<ReportActiveContractMonthDto> resultData = new CmsCommonPagination<>();
		resultData.setData(datas);

		resultData.setTotalData(param.totalData);
		return resultData;
	}

	@Override
	public ResponseEntity exportResultListContractMonth(ReportActiveContractSearchDto searchDto,
			HttpServletResponse response, Locale locale) {
		ResponseEntity res = null;
		try {
			
			searchDto.setPage(0);
			searchDto.setPageSize(0);
			
			CmsCommonPagination<ReportActiveContractMonthDto> resObj = getListContractMonth(searchDto);
			List<ReportActiveContractMonthDto> lstdata = resObj.getData();
			// start fill data to workbook
			List<ItemColsExcelDto> cols = new ArrayList<>();
			String template = "ReportActiveMonth.xlsx";
			String templatePath = servletContext.getRealPath(CommonConstant.REAL_PATH_TEMPLATE_EXCEL + "/" + template);
			String templateName = "Bao_cao_hoat_dong_ca_nhan_hop_dong_trong_thang.xlsx";

			String startRow = "A6";

			String datePattern = getSystemConfig().getConfig(SystemConfig.DATE_PATTERN);
			datePattern = "dd/MM/yyyy";			
			
			ImportExcelUtil.setListColumnExcel(ReportActiveMonthEnum.class, cols);
			ExportExcelUtil exportExcel = new ExportExcelUtil<>();
			
			Map<String, String> mapColFormat = null;// setMapColFormat();
			Map<String, Object> setMapColDefaultValue = null;// setMapColDefaultValue();
			
			// do export
			try (XSSFWorkbook xssfWorkbook = exportExcel.getXSSFWorkbook(templatePath);) {
				Map<String, CellStyle> mapColStyle = null;// setMapColStyle(xssfWorkbook);
				
				XSSFSheet xssfSheet = xssfWorkbook.getSheetAt(0);
				CellStyle headerCellStyle = xssfSheet.getWorkbook().createCellStyle();
		        Font font = xssfWorkbook.createFont();
		        font.setColor(IndexedColors.BLUE.index);
		        font.setFontName("Times new roman");
		        headerCellStyle.setFont(font);
		        
				XSSFRow row = xssfSheet.getRow(2);

				int cellIndex = 0;
				XSSFCell cellDate = row.getCell(cellIndex);
				if (cellDate == null)
					cellDate = row.createCell(cellIndex);
				cellDate.setCellStyle(headerCellStyle);
				
				cellDate.setCellValue("Ngày cập nhật: " + DateUtils.formatDateToString(new Date(new Date().getTime() -  86400000), "dd/MM/yyyy"));
				String repo = systemConfig.getConfig(systemConfig.REPO_UPLOADED_MAIN);
				String path = systemConfig.getPhysicalPathById(repo, null); //path up service
				res = exportExcel.doExportExcelHeaderWithColFormatRestUpService(xssfWorkbook, 0, locale, lstdata,
						ReportActiveContractMonthDto.class, cols, datePattern, startRow, mapColFormat, mapColStyle,
					setMapColDefaultValue, null, true, templateName, true,path);
			} catch (Exception e) {
				throw new Exception(e.getMessage());
			}
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
		}
		return res;
	}

	@Override
	public ResponseEntity exportResultListContractYear(ReportActiveContractSearchDto searchDto, HttpServletResponse response, Locale locale) {
		ResponseEntity res = null;
		try {

			searchDto.setPage(0);
			searchDto.setPageSize(0);

			CmsCommonPagination<ReportActiveContractMonthDto> resObj = getListContractYear(searchDto);
			List<ReportActiveContractMonthDto> lstdata = resObj.getData();
			// start fill data to workbook
			List<ItemColsExcelDto> cols = new ArrayList<>();
			String template = "ReportActiveMonth.xlsx";
			String templatePath = servletContext.getRealPath(CommonConstant.REAL_PATH_TEMPLATE_EXCEL + "/" + template);
			String templateName = "Bao_cao_hoat_dong_ca_nhan_hop_dong_trong_thang.xlsx";

			String startRow = "A6";

			String datePattern = getSystemConfig().getConfig(SystemConfig.DATE_PATTERN);
			datePattern = "dd/MM/yyyy";

			ImportExcelUtil.setListColumnExcel(ReportActiveMonthEnum.class, cols);
			ExportExcelUtil exportExcel = new ExportExcelUtil<>();

			Map<String, String> mapColFormat = null;// setMapColFormat();
			Map<String, Object> setMapColDefaultValue = null;// setMapColDefaultValue();

			// do export
			try (XSSFWorkbook xssfWorkbook = exportExcel.getXSSFWorkbook(templatePath);) {
				Map<String, CellStyle> mapColStyle = null;// setMapColStyle(xssfWorkbook);

				XSSFSheet xssfSheet = xssfWorkbook.getSheetAt(0);
				CellStyle headerCellStyle = xssfSheet.getWorkbook().createCellStyle();
				Font font = xssfWorkbook.createFont();
				font.setColor(IndexedColors.BLUE.index);
				font.setFontName("Times new roman");
				headerCellStyle.setFont(font);

				XSSFRow row = xssfSheet.getRow(2);

				int cellIndex = 0;
				XSSFCell cellDate = row.getCell(cellIndex);
				if (cellDate == null)
					cellDate = row.createCell(cellIndex);
				cellDate.setCellStyle(headerCellStyle);

				cellDate.setCellValue("Ngày cập nhật: " + DateUtils.formatDateToString(new Date(new Date().getTime() -  86400000), "dd/MM/yyyy"));
				String repo = systemConfig.getConfig(systemConfig.REPO_UPLOADED_MAIN);
				String path = systemConfig.getPhysicalPathById(repo, null); //path up service
				res = exportExcel.doExportExcelHeaderWithColFormatRestUpService(xssfWorkbook, 0, locale, lstdata,
						ReportActiveContractMonthDto.class, cols, datePattern, startRow, mapColFormat, mapColStyle,
						setMapColDefaultValue, null, true, templateName, true,path);
			} catch (Exception e) {
				throw new Exception(e.getMessage());
			}
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
		}
		return res;
	}
}
