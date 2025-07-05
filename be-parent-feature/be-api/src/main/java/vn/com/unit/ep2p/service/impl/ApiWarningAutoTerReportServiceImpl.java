package vn.com.unit.ep2p.service.impl;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.ServletContext;

import org.apache.commons.collections.CollectionUtils;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.jsonwebtoken.io.IOException;
import vn.com.unit.cms.core.dto.CmsCommonPagination;
import vn.com.unit.cms.core.dto.CommonSearchWithPagingDto;
import vn.com.unit.cms.core.module.autoter.dto.AutoterReportDetailDto;
import vn.com.unit.cms.core.module.autoter.dto.AutoterReportParam;
import vn.com.unit.cms.core.module.autoter.dto.AutoterSearchDto;
import vn.com.unit.cms.core.module.autoter.dto.AutoterSumaryDto;
import vn.com.unit.cms.core.module.autoter.dto.AutoterSumaryParam;
import vn.com.unit.core.config.SystemConfig;
import vn.com.unit.db.service.SqlManagerDb2Service;
import vn.com.unit.ep2p.admin.dto.AgentInfoDb2;
import vn.com.unit.ep2p.admin.service.Db2ApiService;
import vn.com.unit.ep2p.enumdef.AutoterAHEnum;
import vn.com.unit.ep2p.enumdef.AutoterBMEnum;
import vn.com.unit.ep2p.enumdef.AutoterCAOEnum;
import vn.com.unit.ep2p.enumdef.AutoterOHEnum;
import vn.com.unit.ep2p.enumdef.AutoterTHEnum;
import vn.com.unit.ep2p.enumdef.AutoterUMEnum;
import vn.com.unit.ep2p.service.ParseJsonToParamSearchService;
import vn.com.unit.ep2p.service.WarningAutoTerReportService;
import vn.com.unit.imp.excel.constant.CommonConstant;
import vn.com.unit.imp.excel.dto.ItemColsExcelDto;
import vn.com.unit.imp.excel.utils.DateUtils;
import vn.com.unit.imp.excel.utils.ExportExcelUtil;
import vn.com.unit.imp.excel.utils.ImportExcelUtil;

@Service
@Transactional(transactionManager = "transactionManagerDb2", readOnly = true, rollbackFor = Exception.class)
public class ApiWarningAutoTerReportServiceImpl implements WarningAutoTerReportService{
	
    @Autowired
    private SystemConfig systemConfig;

    @Autowired
    private ServletContext servletContext;
	@Autowired
	ParseJsonToParamSearchService parseJsonToParamSearchService;
	@Autowired
	protected ObjectMapper objectMapper;
	@Autowired
	private Db2ApiService db2ApiService;

    @Autowired
    @Qualifier("sqlManageDb2Service")
    private SqlManagerDb2Service sqlManagerDb2Service;
    
    private static final String SP_GET_LIST_AUTOTER_REPORT_DETAIL = "RPT_ODS.DS_SP_GET_LIST_AUTO_WARNING_TER_FCSA";
    private static final String SP_GET_AUTOTER_SUMARY = "RPT_ODS.DS_SP_GET_LIST_AUTO_WARNING_TER_FCSA_TOTAL";
    
    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(ApiWarningAutoTerReportServiceImpl.class);
    
    @Override
    public SystemConfig getSystemConfig() {
        return systemConfig;
    }

	@Override
	public CmsCommonPagination<AutoterReportDetailDto> getListAutoTerReportDetail(AutoterSearchDto searchDto) {
		AutoterReportParam param = new AutoterReportParam();	 
		
		searchDto.setFunctionCode("AUTOTER_REPORT");
		ObjectMapper mapper = new ObjectMapper();
		String stringJsonParam = "";
		try {
			stringJsonParam = mapper.writeValueAsString(searchDto);
		} catch (JsonProcessingException e) {
			logger.error("Exception ", e);
		}
		CommonSearchWithPagingDto common = parseJsonToParamSearchService.getSearchCondition(stringJsonParam,"AUTOTER_REPORT");
		
		param.agentCode = searchDto.getAgentCode();
		param.orgCode = searchDto.getOrgCode();
		param.agentGroup= searchDto.getAgentGroup();
		param.dateKey= DateUtils.formatDateToString(getLastDayOfThePreviousMonth(), "yyyyMMdd");
//		param.dateKey= "20210131";
		param.page = searchDto.getPage();
		param.pageSize  = searchDto.getPageSize();
		param.sort = common.getSort();
		param.search = common.getSearch();
		sqlManagerDb2Service.call(SP_GET_LIST_AUTOTER_REPORT_DETAIL , param);  	 
		List<AutoterReportDetailDto> datas = param.data;
		
		for(AutoterReportDetailDto item : datas) {
			if (searchDto.getAgentGroup() != null) {
				item.setBdthFullName(getStringName(item.getBdthAgenttype(),item.getBdthCode(),item.getBdthName()));
				item.setBdahFullName(getStringName(item.getBdahAgenttype(),item.getBdahCode(),item.getBdahName()));
				item.setBdrhFullName(getStringName(item.getBdrhAgenttype(),item.getBdrhCode(),item.getBdrhName()));
				item.setBdohFullName(getStringName(item.getBdohAgenttype(),item.getBdohCode(),item.getBdohName()));
				item.setOfficeCode(item.getOfficeCode() + " - " + item.getOfficeName());
				item.setManager(item.getLv1Agenttype() + ": " + item.getLv1Agentcode().replace(item.getOrgCode(), "") + " - " + item.getLv1Agentname());
				item.setManage(item.getLv2Agenttype()+ ": " + item.getLv2Agentcode().replace("A", "").replace("B", "").replace("C", "")+ " - " + item.getLv2Agentname());
				item.setTvtcName(item.getLv3Agenttype() + ": " + item.getLv3Agentcode() + " - " + item.getLv3Agentname());

			}
		}
		
		CmsCommonPagination<AutoterReportDetailDto> resultData = new CmsCommonPagination<>();
		resultData.setData(datas);
		resultData.setTotalData(param.totalData);

		return resultData; 
	}
	private Date getLastDayOfThePreviousMonth(){
    	Date currentDate = new Date();

		Calendar calendar = Calendar.getInstance();
		calendar.setTime(currentDate);
		calendar.set(Calendar.DAY_OF_MONTH, 1);
		calendar.add(Calendar.DATE, - 1);
    	return calendar.getTime();
	}

	@Override
	public ResponseEntity getListAutoTerReportExport(AutoterSearchDto searchDto) {
		ResponseEntity res = null;
		try {
			searchDto.setPage(0);
			searchDto.setSize(0);
			int totalFc = 0;
			int totalSa = 0;
			CmsCommonPagination<AutoterReportDetailDto> resObj = getListAutoTerReportDetail(searchDto);

			List<AutoterReportDetailDto> lstdata = resObj.getData();
			if (CollectionUtils.isNotEmpty(lstdata)) {
				List<AutoterReportDetailDto> lstFc = lstdata.stream().filter(x -> "FC".equalsIgnoreCase(x.getLv3Agenttype())).collect(Collectors.toList());
				totalFc = lstFc.size();
				List<AutoterReportDetailDto> lstSa = lstdata.stream().filter(x -> "SA".equalsIgnoreCase(x.getLv3Agenttype())).collect(Collectors.toList());
				totalSa = lstSa.size();
			}
			for(AutoterReportDetailDto item : lstdata) {
				if (searchDto.getAgentGroup() != null) {
					item.setBdthFullName(getStringName(item.getBdthAgenttype(),item.getBdthCode(),item.getBdthName()));
					item.setBdahFullName(getStringName(item.getBdahAgenttype(),item.getBdahCode(),item.getBdahName()));
					item.setBdrhFullName(getStringName(item.getBdrhAgenttype(),item.getBdrhCode(),item.getBdrhName()));
					item.setBdohFullName(getStringName(item.getBdohAgenttype(),item.getBdohCode(),item.getBdohName()));
					item.setManager(getStringName(item.getLv1Agenttype(),item.getLv1Agentcode().replace(item.getOrgCode(), ""),item.getLv1Agentname()));
					item.setLv1Agentcode(item.getLv1Agentcode().replace(item.getOrgCode(), ""));
					item.setLv2Agentcode(item.getLv2Agentcode().replace("A", "").replace("B", "").replace("C", ""));
					item.setManage(getStringName(item.getLv2Agenttype(),item.getLv2Agentcode().replace("A", "").replace("B", "").replace("C", ""),item.getLv2Agentname()));
					item.setTvtcName(getStringName(item.getLv3Agenttype(),item.getLv3Agentcode(),item.getLv3Agentname()));
					item.setTdCode(getStringNameExport(null,item.getTdCode(), item.getTdName()));
					item.setNCode(getStringNameExport(null,item.getNCode(), item.getNName()));
					item.setRCode(getStringNameExport(null,item.getRCode(), item.getRName()));
				}
			}
			
			String datePattern = getSystemConfig().getConfig(SystemConfig.DATE_PATTERN);
			datePattern = "dd/MM/yyyy";
			String templateName ="";
			List<ItemColsExcelDto> cols = new ArrayList<>();
			if("CAO".equalsIgnoreCase(searchDto.getAgentGroup())) {
				templateName = "Danh_sach_tu_van_se_bi_cham_dut_tu_dong_viewCAO.xlsx";
				ImportExcelUtil.setListColumnExcel(AutoterCAOEnum.class, cols);
			}else if("TH".equalsIgnoreCase(searchDto.getAgentGroup())){
				templateName = "Danh_sach_tu_van_se_bi_cham_dut_tu_dong_viewTH.xlsx";
				ImportExcelUtil.setListColumnExcel(AutoterTHEnum.class, cols);
			}else if("AH".equalsIgnoreCase(searchDto.getAgentGroup())){
				templateName = "Danh_sach_tu_van_se_bi_cham_dut_tu_dong_viewAH.xlsx";
				ImportExcelUtil.setListColumnExcel(AutoterAHEnum.class, cols);
			}
			else if("RH".equalsIgnoreCase(searchDto.getAgentGroup())){
				templateName = "Danh_sach_tu_van_se_bi_cham_dut_tu_dong_viewRH.xlsx";
				ImportExcelUtil.setListColumnExcel(AutoterOHEnum.class, cols);
			}
			else if("OH".equalsIgnoreCase(searchDto.getAgentGroup())){
				templateName = "Danh_sach_tu_van_se_bi_cham_dut_tu_dong_viewOH.xlsx";
				ImportExcelUtil.setListColumnExcel(AutoterOHEnum.class, cols);
			}
			else if("GA".equalsIgnoreCase(searchDto.getAgentGroup())){
				templateName = "Danh_sach_tu_van_se_bi_cham_dut_tu_dong_viewGA.xlsx";
				ImportExcelUtil.setListColumnExcel(AutoterOHEnum.class, cols);
			}
			else if("SO".equalsIgnoreCase(searchDto.getAgentGroup())){
				templateName = "Danh_sach_tu_van_se_bi_cham_dut_tu_dong_viewSO.xlsx";
				ImportExcelUtil.setListColumnExcel(AutoterOHEnum.class, cols);
			}
			else if("BM".equalsIgnoreCase(searchDto.getAgentGroup())){
				templateName = "Danh_sach_tu_van_se_bi_cham_dut_tu_dong_viewBM.xlsx";
				ImportExcelUtil.setListColumnExcel(AutoterBMEnum.class, cols);
			}
			else if("UM".equalsIgnoreCase(searchDto.getAgentGroup())){
				templateName = "Danh_sach_tu_van_se_bi_cham_dut_tu_dong_viewUM.xlsx";
				ImportExcelUtil.setListColumnExcel(AutoterUMEnum.class, cols);
			}
			String templatePath = servletContext
					.getRealPath(CommonConstant.REAL_PATH_TEMPLATE_EXCEL + "/" + templateName);
			String startRow = "A9";

			// start fill data to workbook

			ExportExcelUtil exportExcel = new ExportExcelUtil<>();
			Map<String, String> mapColFormat = null;
			Map<String, Object> setMapColDefaultValue = null;

			// do export
			try (XSSFWorkbook xssfWorkbook = exportExcel.getXSSFWorkbook(templatePath);) {
				Map<String, CellStyle> mapColStyle = new HashMap<>();

				XSSFSheet xssfSheet = xssfWorkbook.getSheetAt(0);
				XSSFRow rowTitle = null;
				rowTitle = xssfSheet.getRow(1);
				XSSFCell cellIndex3 = rowTitle.getCell(0);

				CellStyle headerTitle = xssfSheet.getWorkbook().createCellStyle();
				Font fontTitle  = xssfWorkbook.createFont();
				fontTitle.setBold(true);
				fontTitle.setColor(IndexedColors.BLUE.index);
				fontTitle.setFontName("Times New Roman");
				fontTitle.setFontHeightInPoints((short) 20);

				headerTitle.setFont(fontTitle);
				if (xssfSheet.getRow(1).getCell(0) == null) {
					xssfSheet.getRow(1).createCell(0);
				}
				xssfSheet.getRow(1).getCell(0).setCellStyle(headerTitle);
				xssfSheet.getRow(0).getCell(0).setCellValue("DANH SÁCH ĐẠI LÝ SẼ BỊ CHẤM DỨT HỢP ĐỒNG ĐẠI LÝ TỰ ĐỘNG.");
				xssfSheet.getRow(1).getCell(0).setCellValue("KỲ XÉT " + DateUtils.formatDateToString(new Date(), "MM/yyyy"));

				AgentInfoDb2 dataz = db2ApiService.getParentByAgentCode(searchDto.getAgentCode(),searchDto.getAgentGroup(),searchDto.getOrgCode());
				String repo = systemConfig.getConfig(systemConfig.REPO_UPLOADED_MAIN);
				String path = systemConfig.getPhysicalPathById(repo, null); //path up service
				writeDateNow(searchDto.getAgentGroup(), xssfWorkbook, 0, new Date(),totalFc, totalSa ,dataz.getOrg(), dataz.getAgentType() +": "+ dataz.getAgentCode().replace(dataz.getOrgId(), "").replace("A", "").replace("B", "").replace("C", "") + " - " + dataz.getAgentName(),mapColStyle);
				res = exportExcel.doExportExcelHeaderWithColFormatRestUpService(xssfWorkbook, 0, null, lstdata,
						AutoterReportDetailDto.class, cols, datePattern, startRow, mapColFormat, mapColStyle,
						setMapColDefaultValue, null, true, templateName, true,path);
			} catch (Exception e) {
				logger.error("##ExportList##", e);
			}
		} catch (Exception e) {
			logger.error("exportListData: ", e);
		}
		return res;
	}

	public void writeDateNow(String agentType, XSSFWorkbook xssfWorkbook, int sheetNumber, Date runDate,int totalFc, int totalSa,String org, String leader,Map<String, CellStyle> mapColStyle)
			throws IOException {
		XSSFSheet xssfSheet = xssfWorkbook.getSheetAt(sheetNumber);
		CellStyle titleStyle = xssfSheet.getWorkbook().createCellStyle();
		Font fontTitle = xssfWorkbook.createFont();
		fontTitle.setColor(IndexedColors.BLUE.index);
		fontTitle.setFontName("Times New Roman");
		fontTitle.setBold(true);
		fontTitle.setFontHeightInPoints((short)15);
		titleStyle.setAlignment(HorizontalAlignment.CENTER);
		titleStyle.setVerticalAlignment(VerticalAlignment.CENTER);
		titleStyle.setFont(fontTitle);

		CellStyle titleCell = xssfSheet.getWorkbook().createCellStyle();
		Font fontCell = xssfWorkbook.createFont();
		fontCell.setFontName("Times New Roman");
		fontCell.setBold(true);
		titleCell.setFont(fontCell);
		titleCell.setBorderTop(BorderStyle.THIN);
		titleCell.setBorderLeft(BorderStyle.THIN);
		titleCell.setBorderRight(BorderStyle.THIN);
		titleCell.setBorderBottom(BorderStyle.THIN);
		mapColStyle.put("PAREN",titleCell);
		
		CellStyle titleStyleDate = xssfSheet.getWorkbook().createCellStyle();

		Font fontTitleDate = xssfWorkbook.createFont();
		fontTitleDate.setColor(IndexedColors.BLUE.index);
		fontTitleDate.setFontName("Times New Roman");
		titleStyleDate.setFont(fontTitleDate);
		if (xssfSheet.getRow(2) != null && xssfSheet.getRow(2).getCell(0) != null)
			xssfSheet.getRow(2).getCell(0).setCellValue(org);
		else xssfSheet.createRow(2).createCell(0).setCellValue(org);
		
		DecimalFormatSymbols symbols = new DecimalFormatSymbols();
		symbols.setGroupingSeparator(',');
		symbols.setDecimalSeparator('.');
		String pattern = "#,##0";
		DecimalFormat decimalFormat = new DecimalFormat(pattern, symbols);
		decimalFormat.setParseBigDecimal(true);
		String totalFcF = "";
		totalFcF = decimalFormat.format(totalFc);
		String totalSaF = "";
		totalSaF = decimalFormat.format(totalSa);
		if (xssfSheet.getRow(3) != null && xssfSheet.getRow(3).getCell(0) != null)
			xssfSheet.getRow(3).getCell(0).setCellValue("Số lượng FC cảnh báo chấm dức tự động: "+ totalFcF);
		else
			xssfSheet.createRow(3).createCell(0).setCellValue("Số lượng FC cảnh báo chấm dức tự động: " + totalFcF);
		
		if (xssfSheet.getRow(4) != null && xssfSheet.getRow(4).getCell(0) != null)
			xssfSheet.getRow(4).getCell(0).setCellValue("Số lượng SA cảnh báo chấm dức tự động: "+ totalSaF);
		else
			xssfSheet.createRow(4).createCell(0).setCellValue("Số lượng SA cảnh báo chấm dức tự động: "+ totalSaF);

		if (xssfSheet.getRow(5) != null && xssfSheet.getRow(5).getCell(0) != null)
			xssfSheet.getRow(5).getCell(0).setCellValue("Cập nhật đến ngày: " + DateUtils.formatDateToString(new Date(), "dd/MM/yyyy"));
		else
			xssfSheet.createRow(5).createCell(0).setCellValue("Cập nhật đến ngày: " + DateUtils.formatDateToString(new Date(), "dd/MM/yyyy"));

	}
	
	private String getStringName(String agentType, String agentCode, String agentName) {
		return agentType+ ":" + agentCode + " - " + agentName;
	}
	private String getStringNameExport(String agentType, String agentCode, String agentName) {
		return agentCode + " - " + agentName;
	}
	@Override
	public CmsCommonPagination<AutoterSumaryDto> getAutoterSumary(AutoterSearchDto searchDto) {
		AutoterSumaryParam param = new AutoterSumaryParam();	 
		
		
		param.agentCode = searchDto.getAgentCode();
		param.orgCode = searchDto.getOrgCode();
		param.agentGroup= searchDto.getAgentGroup();
		param.dateKey= DateUtils.formatDateToString(getLastDayOfThePreviousMonth(), "yyyyMMdd");
//		param.dateKey= "20220131";
		param.search = searchDto.getSearch();
		sqlManagerDb2Service.call(SP_GET_AUTOTER_SUMARY , param);  	 
		List<AutoterSumaryDto> datas = param.data;
	
		CmsCommonPagination<AutoterSumaryDto> resultData = new CmsCommonPagination<>();
		resultData.setData(datas);

		return resultData; 
	}

}
