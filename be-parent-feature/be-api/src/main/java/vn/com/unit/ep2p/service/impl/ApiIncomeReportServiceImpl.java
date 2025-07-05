package vn.com.unit.ep2p.service.impl;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.ServletContext;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.DataFormat;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.util.CellAddress;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.CellReference;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import vn.com.unit.cms.core.module.income.dto.IncomePersonaYearlyParam;
import vn.com.unit.cms.core.module.income.dto.IncomePersonalMonthSearchDto;
import vn.com.unit.cms.core.module.income.dto.IncomePersonalMonthlyDto;
import vn.com.unit.cms.core.module.income.dto.IncomePersonalMonthlyParam;
import vn.com.unit.cms.core.module.income.dto.IncomePersonalWeeklyDto;
import vn.com.unit.cms.core.module.income.dto.IncomePersonalWeeklyParam;
import vn.com.unit.cms.core.module.income.dto.IncomePersonalYearlyDto;
import vn.com.unit.cms.core.module.income.dto.IncomePersonalYearlySearchDto;
import vn.com.unit.cms.core.module.income.dto.MonthPaymentDto;
import vn.com.unit.cms.core.module.income.dto.TotalPaymentDto;
import vn.com.unit.cms.core.module.income.dto.YearlyPaymentAGParam;
import vn.com.unit.cms.core.module.income.dto.YearlyPaymentDto;
import vn.com.unit.cms.core.module.income.dto.YearlyPaymentGAParam;
import vn.com.unit.cms.core.module.income.dto.YearlySumPaymentAGParam;
import vn.com.unit.cms.core.module.income.dto.YearlySumPaymentGAParam;
import vn.com.unit.cms.core.module.report.dto.IncomeBonusReportExport;
import vn.com.unit.cms.core.module.report.dto.IncomeBonusReportMO;
import vn.com.unit.cms.core.module.report.dto.IncomeBonusReportParam;
import vn.com.unit.cms.core.module.report.dto.IncomeBonusReportSearchDto;
import vn.com.unit.cms.core.module.report.dto.IncomeBonusReportTQP;
import vn.com.unit.cms.core.module.report.dto.IncomeBonusReportTSM;
import vn.com.unit.common.utils.CommonCollectionUtil;
import vn.com.unit.core.config.SystemConfig;
import vn.com.unit.core.res.ObjectDataRes;
import vn.com.unit.db.service.SqlManagerDb2Service;
import vn.com.unit.ep2p.enumdef.IncomeBonusReportEnumBM;
import vn.com.unit.ep2p.enumdef.IncomeBonusReportEnumRHOH;
import vn.com.unit.ep2p.enumdef.IncomeBonusReportEnumUM;
import vn.com.unit.ep2p.enumdef.IncomePersonalMonthYearlyEnum;
import vn.com.unit.ep2p.enumdef.IncomePersonalMonthlyEnum2;
import vn.com.unit.ep2p.enumdef.IncomePersonalTotalYearlyEnum;
import vn.com.unit.ep2p.enumdef.IncomePersonalWeeklyEnum;
import vn.com.unit.ep2p.enumdef.IncomePersonalYearlyEnum;
import vn.com.unit.ep2p.service.ApiIncomeReportService;
import vn.com.unit.imp.excel.constant.CommonConstant;
import vn.com.unit.imp.excel.dto.CellStyleDto;
import vn.com.unit.imp.excel.dto.ItemColsExcelDto;
import vn.com.unit.imp.excel.utils.ExportExcelUtil;
import vn.com.unit.imp.excel.utils.ImportExcelUtil;

@Service
@Transactional(transactionManager = "transactionManagerDb2", readOnly = true, rollbackFor = Exception.class)
public class ApiIncomeReportServiceImpl implements ApiIncomeReportService {

	@Autowired
	private ServletContext servletContext;

	@Autowired
	private SystemConfig systemConfig;

	@Autowired
	@Qualifier("sqlManageDb2Service")
	private SqlManagerDb2Service sqlManagerDb2Service;

	private Logger logger = LoggerFactory.getLogger(getClass());

	private static final String STORE_REPORT_BONUS_INCOME_MO = "RPT_ODS.";
	private static final String STORE_REPORT_BONUS_INCOME_TQP = "RPT_ODS.";
	private static final String STORE_REPORT_BONUS_INCOME_TSM = "RPT_ODS.";

	private static final String STORE_EXPORT_REPORT_BONUS_INCOME_MO = "RPT_ODS.";
	private static final String STORE_EXPORT_REPORT_BONUS_INCOME_TQP = "RPT_ODS.";
	private static final String STORE_EXPORT_REPORT_BONUS_INCOME_TSM = "RPT_ODS.";

	private static final String STORE_INCOME_PERSONAL_MONTHLY_SUMMARY = "RPT_ODS.DS_SP_GET_MONTHLY_PAYMENT_BY_AGENT";
	private static final String STORE_INCOME_PERSONAL_MONTHLY_DETAIL = "RPT_ODS.DS_SP_GET_MONTHLY_DETAIL_PAYMENT_BY_AGENT";

	private static final String STORE_INCOME_PERSONAL_WEEKLY = "RPT_ODS.DS_SP_GET_WEEKLY_PAYMENT_BY_AGENT";

	private static final String STORE_INCOME_PERSONAL_YEARLY = "RPT_ODS.";
	
	// Bang luong nam AG
	private static final String STORE_GET_YEARLY_PAYMENT_BY_TVTC = "RPT_ODS.DS_SP_GET_YEARLY_PAYMENT_BY_TVTC";
	private static final String STORE_GET_SUM_PAYMENT_YEAR_TVTC = "STG_DMS.DS_SP_GET_SUM_PAYMENT_YEAR_TVTC";
	// Bang luong nam GA	
	private static final String STORE_GET_YEARLY_PAYMENT_BY_GA = "RPT_ODS.DS_SP_GET_YEARLY_PAYMENT_BY_GA";
	private static final String STORE_GET_SUM_PAYMENT_YEAR_GA = "STG_DMS.DS_SP_GET_SUM_PAYMENT_YEAR_GA";
	

	@Override
	public List<IncomeBonusReportMO> getListReportIncomeBonusMO(IncomeBonusReportSearchDto searchDto) {
		IncomeBonusReportParam<IncomeBonusReportMO> param = new IncomeBonusReportParam<IncomeBonusReportMO>();
		param.agentCode = searchDto.getAgentCode();
		param.agentGroup = searchDto.getAgentGroup(); // RH/OH - BM - UM
		param.agentOrg = searchDto.getAgentOrg();
		param.time = searchDto.getTime();
		sqlManagerDb2Service.call(STORE_REPORT_BONUS_INCOME_MO, param);
		List<IncomeBonusReportMO> list = new ArrayList<>();
		if (CommonCollectionUtil.isNotEmpty(param.data)) {
			list = param.data;
		}
		return list;
	}

	@Override
	public List<IncomeBonusReportTQP> getListReportIncomeBonusTQP(IncomeBonusReportSearchDto searchDto) {
		IncomeBonusReportParam<IncomeBonusReportTQP> param = new IncomeBonusReportParam<IncomeBonusReportTQP>();
		param.agentCode = searchDto.getAgentCode();
		param.agentGroup = searchDto.getAgentGroup();
		param.agentOrg = searchDto.getAgentOrg();
		param.time = searchDto.getTime();
		sqlManagerDb2Service.call(STORE_REPORT_BONUS_INCOME_TQP, param);
		List<IncomeBonusReportTQP> list = new ArrayList<>();
		if (CommonCollectionUtil.isNotEmpty(param.data)) {
			list = param.data;
		}
		return list;
	}

	@Override
	public List<IncomeBonusReportTSM> getListReportIncomeBonusTSM(IncomeBonusReportSearchDto searchDto) {
		IncomeBonusReportParam<IncomeBonusReportTSM> param = new IncomeBonusReportParam<IncomeBonusReportTSM>();
		param.agentCode = searchDto.getAgentCode();
		param.agentGroup = searchDto.getAgentGroup();
		param.agentOrg = searchDto.getAgentOrg();
		param.time = searchDto.getTime();
		sqlManagerDb2Service.call(STORE_REPORT_BONUS_INCOME_TSM, param);
		List<IncomeBonusReportTSM> list = new ArrayList<>();
		if (CommonCollectionUtil.isNotEmpty(param.data)) {
			list = param.data;
		}
		return list;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public ResponseEntity exportReportIncome(IncomeBonusReportSearchDto searchDto, Locale locale) {
		ResponseEntity res = null;
		try {
			List<IncomeBonusReportExport> lstdata = new ArrayList<>();
			IncomeBonusReportParam<IncomeBonusReportExport> param = new IncomeBonusReportParam<IncomeBonusReportExport>();
			param.agentCode = searchDto.getAgentCode();
			param.agentGroup = searchDto.getAgentGroup();
			param.agentOrg = searchDto.getAgentOrg();
			sqlManagerDb2Service.call(STORE_EXPORT_REPORT_BONUS_INCOME_MO, param);
			if (CommonCollectionUtil.isNotEmpty(param.data)) {
				lstdata = param.data;
			}
			String datePattern = systemConfig.getConfig(SystemConfig.DATE_PATTERN);
			datePattern = "dd/MM/yyyy";
			String templateName = "";
			List<ItemColsExcelDto> cols = new ArrayList<>();
			if (searchDto.getAgentGroup().equalsIgnoreCase("RH")) {// RH or OH
				templateName = "Report_income_bonus_RHOH.xlsx";
				ImportExcelUtil.setListColumnExcel(IncomeBonusReportEnumRHOH.class, cols);
			} else if (searchDto.getAgentGroup().equalsIgnoreCase("BM")) {
				templateName = "Report_income_bonus_BM.xlsx";
				ImportExcelUtil.setListColumnExcel(IncomeBonusReportEnumBM.class, cols);
			} else if (searchDto.getAgentGroup().equalsIgnoreCase("UM")) {
				templateName = "Report_income_bonus_UM.xlsx";
				ImportExcelUtil.setListColumnExcel(IncomeBonusReportEnumUM.class, cols);
			}

			String templatePath = servletContext
					.getRealPath(CommonConstant.REAL_PATH_TEMPLATE_EXCEL + "/" + templateName);
			String startRow = "A7";
			ExportExcelUtil exportExcel = new ExportExcelUtil<>();
			Map<String, String> mapColFormat = null;
			Map<String, Object> setMapColDefaultValue = null;
			try (XSSFWorkbook xssfWorkbook = exportExcel.getXSSFWorkbook(templatePath);) {
				Map<String, CellStyle> mapColStyle = null;
				String repo = systemConfig.getConfig(systemConfig.REPO_UPLOADED_MAIN);
				String path = systemConfig.getPhysicalPathById(repo, null); // path up service
				res = exportExcel.doExportExcelHeaderWithColFormatRestUpService(xssfWorkbook, 0, locale, lstdata,
						IncomeBonusReportExport.class, cols, datePattern, startRow, mapColFormat, mapColStyle,
						setMapColDefaultValue, null, true, templateName, true, path);
			} catch (Exception e) {
				logger.error("##doExport##", e);
			}
		} catch (Exception e) {
			logger.error("##exportLis##", e);
		}
		return res;
	}

	// monthly detail
	@Override
	public List<IncomePersonalMonthlyDto> getListIncomePersonalMonthlyDetail(IncomePersonalMonthSearchDto searchDto) {
		IncomePersonalMonthlyParam param = new IncomePersonalMonthlyParam();
		List<IncomePersonalMonthlyDto> list = new ArrayList<>();
		try {
			param.agentCode = searchDto.getAgentCode();
			param.paymentPeriod = searchDto.getPaymentPeriod();
			sqlManagerDb2Service.call(STORE_INCOME_PERSONAL_MONTHLY_DETAIL, param);
			if (CommonCollectionUtil.isNotEmpty(param.data)) {
				list = param.data;
			}
		} catch (Exception e) {
			logger.error("##getListIncomePersonalMonthlyDetail##", e);
		}
		return list;
	}

	// monthly sumary
	@Override
	public List<IncomePersonalMonthlyDto> getListIncomePersonalMonthlySumary(IncomePersonalMonthSearchDto searchDto) {
		IncomePersonalMonthlyParam param = new IncomePersonalMonthlyParam();
		List<IncomePersonalMonthlyDto> list = new ArrayList<>();
		try {
			param.agentCode = searchDto.getAgentCode();
			param.paymentPeriod = searchDto.getPaymentPeriod();
			sqlManagerDb2Service.call(STORE_INCOME_PERSONAL_MONTHLY_SUMMARY, param);
			if (CommonCollectionUtil.isNotEmpty(param.data)) {
				list = param.data;
			}
		} catch (Exception e) {
			logger.error("##getListIncomePersonalMonthlySumary##", e);
		}
		return list;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public ResponseEntity exportIncomePersonalMonthly(IncomePersonalMonthSearchDto searchDto) {
		ResponseEntity res = null;
		try {
			List<IncomePersonalMonthlyDto> sumary = getListIncomePersonalMonthlySumary(searchDto);
			IncomePersonalMonthlyDto info = (CommonCollectionUtil.isNotEmpty(sumary)) ? sumary.get(0)
					: new IncomePersonalMonthlyDto();
			List<IncomePersonalMonthlyDto> lstdata = getListIncomePersonalMonthlyDetail(searchDto);
			lstdata = formatData(lstdata);
			String datePattern = systemConfig.getConfig(SystemConfig.DATE_PATTERN);
			datePattern = "dd/MM/yyyy";
			String templateName = "Thu_nhap_thang.xlsx";
			List<ItemColsExcelDto> cols = new ArrayList<>();
			ImportExcelUtil.setListColumnExcel(IncomePersonalMonthlyEnum2.class, cols);
			String templatePath = servletContext
					.getRealPath(CommonConstant.REAL_PATH_TEMPLATE_EXCEL + "/" + templateName);
			String startRow = "A9";
			ExportExcelUtil exportExcel = new ExportExcelUtil<>();
			Map<String, String> mapColFormat = null;
			Map<String, Object> setMapColDefaultValue = null;
			try (XSSFWorkbook xssfWorkbook = exportExcel.getXSSFWorkbook(templatePath);) {
				Map<String, CellStyle> mapColStyle = null;
				List<String> datas = new ArrayList<String>();
				datas.add("Đại lý: " + searchDto.getAgentCode() + " - " + searchDto.getAgentType() + " - "
						+ searchDto.getAgentName());
				datas.add("Mã số thuế: " + searchDto.getIdPersonalIncomeTax());
				datas.add("Số TK: " + searchDto.getBankAccountNumber() + " - " + searchDto.getBankAccountName());
				datas.add("Kỳ thanh toán: " + searchDto.getPaymentPeriod().substring(4, 6) + "/"
						+ searchDto.getPaymentPeriod().substring(0, 4));
				setDataHeaderToXSSFWorkbookSheet(xssfWorkbook, 0, null, templatePath, "A1", datas);

				res = doExportExcelHeaderWithColFormatRestUpServiceNew(xssfWorkbook, 0, null, lstdata,
						IncomePersonalMonthlyDto.class, cols, datePattern, startRow, mapColFormat, mapColStyle,
						setMapColDefaultValue, null, false, templateName, true);
			} catch (Exception e) {
				logger.error("##doExport##", e);
			}
		} catch (Exception e) {
			logger.error("##exportLis##", e);
		}
		return res;
	}

	@SuppressWarnings("rawtypes")
	public ResponseEntity doExportExcelHeaderWithColFormatRestUpServiceNew(XSSFWorkbook xssfWorkbook,
			Integer sheetIndex, Locale locale, List<IncomePersonalMonthlyDto> listData,
			Class<IncomePersonalMonthlyDto> objDto, List<ItemColsExcelDto> cols, String datePattern,
			String cellReference, Map<String, String> mapColFormat, Map<String, CellStyle> mapColStyle,
			Map<String, Object> mapColDefaultValue, XSSFColor colorToTal, boolean isAllBorder, String templateName,
			boolean exportFile) throws Exception {

		ExportExcelUtil exportExcel = new ExportExcelUtil<>();

		// create sheet of file excel
		XSSFSheet sxssfSheet = null;
		if (sheetIndex == null) {
			sxssfSheet = xssfWorkbook.getSheetAt(0);
		} else {
			sxssfSheet = xssfWorkbook.getSheetAt(sheetIndex);
		}

		if (sxssfSheet == null) {
			throw new Exception("Cannot find Sheet!!!");
		}

		CellReference landMark = new CellReference(cellReference);
		int startRow = landMark.getRow();

		// cellStyleDto
		CellStyleDto cellStyleDto = new CellStyleDto(xssfWorkbook, "Arial", isAllBorder, datePattern);

		// cellStyleDtoForTotal
		CellStyleDto cellStyleDtoForTotal = new CellStyleDto(cellStyleDto, colorToTal);

		// field of objDto
		Map<String, Field> mapFields = new HashMap<>();
		Field[] fields = populateFields(objDto);
		for (Field f : fields) {
			mapFields.put(f.getName().toUpperCase(), f);
		}

		if (CollectionUtils.isNotEmpty(listData)) {
//	            if (listData.size() > 0) {
			int dataSize = listData.size();

			for (int i = 0; i < listData.size(); i++) {
				if (colorToTal != null && i == dataSize - 1) {
					// Do fill data
					fillDataForCell(sxssfSheet, listData, objDto, cols, mapColFormat, mapColStyle, mapColDefaultValue,
							mapFields, startRow, i, cellStyleDtoForTotal, dataSize, true);
				} else {
					// Do fill data

					fillDataForCell(sxssfSheet, listData, objDto, cols, mapColFormat, mapColStyle, mapColDefaultValue,
							mapFields, startRow, i, cellStyleDto, dataSize, false);
				}
				startRow += 1;

			}
//	            }
		}

		// set point view and active cell default
		sxssfSheet.setActiveCell(new CellAddress(landMark));
		sxssfSheet.getCTWorksheet().getSheetViews().getSheetViewArray(0).setTopLeftCell("A1");

		if (exportFile) {
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			SimpleDateFormat formatDateExport = new SimpleDateFormat("yyyyMMdd_HHmmss");
			String currentDate = formatDateExport.format(new Date());
			xssfWorkbook.write(out);
			// update to service
			String repo = systemConfig.getConfig(systemConfig.REPO_UPLOADED_MAIN);
			String path = systemConfig.getPhysicalPathById(repo, null); // path up service
			String pathFile = Paths.get(path, CommonConstant.PATH_FILE_EXPORT, CommonConstant.PATH_FILE_DLVN,
					templateName.replace(CommonConstant.TYPE_EXCEL, "") + "_" + currentDate + CommonConstant.TYPE_EXCEL)
					.toString();

			File file = new File(pathFile);
			try (OutputStream os = new FileOutputStream(file)) {
				xssfWorkbook.write(os);
			}

			String pathOut = (File.separator + Paths.get(CommonConstant.PATH_FILE_EXPORT, CommonConstant.PATH_FILE_DLVN,
					templateName.replace(CommonConstant.TYPE_EXCEL, "") + "_" + currentDate + CommonConstant.TYPE_EXCEL)
					.toString()).replace("\\", "/");

			ByteArrayInputStream in = new ByteArrayInputStream(out.toByteArray());
			HttpHeaders headers = new HttpHeaders();
			headers.add(CommonConstant.CONTENT_DISPOSITION,
					CommonConstant.ATTCHMENT_FILENAME + templateName.replace(CommonConstant.TYPE_EXCEL, "") + "_"
							+ currentDate + CommonConstant.TYPE_EXCEL + "\"" + ";path=" + pathOut);

			headers.add("Access-Control-Expose-Headers", CommonConstant.CONTENT_DISPOSITION);

			return ResponseEntity.ok().eTag(pathOut).headers(headers)
					.contentType(MediaType.parseMediaType(CommonConstant.CONTENT_TYPE_EXCEL))
					.body(new InputStreamResource(in));
		}
		return null;
	}

	private Field[] populateFields(Class<IncomePersonalMonthlyDto> cls) {
		Field[] fieldsSuper = cls.getSuperclass().getDeclaredFields();
		Field[] fieldsChild = cls.getDeclaredFields();
		int superLength = fieldsSuper.length;
		int childLength = fieldsChild.length;
		Field[] fields = new Field[superLength + childLength];
		System.arraycopy(fieldsSuper, 0, fields, 0, superLength);
		System.arraycopy(fieldsChild, 0, fields, superLength, childLength);
		return fields;
	}
	
	private Field[] getPopulateFields(Class<?> cls) {
		Field[] fieldsSuper = cls.getSuperclass().getDeclaredFields();
		Field[] fieldsChild = cls.getDeclaredFields();
		int superLength = fieldsSuper.length;
		int childLength = fieldsChild.length;
		Field[] fields = new Field[superLength + childLength];
		System.arraycopy(fieldsSuper, 0, fields, 0, superLength);
		System.arraycopy(fieldsChild, 0, fields, superLength, childLength);
		return fields;
	}

	private enum DataType {
		LONG, DOUBLE, INTEGER, STRING, DATE, TIMESTAMP, INT, BIGDECIMAL, BOOLEAN, BYTE;
	}

	private static String EMP_STRING = "";

	public void fillDataForCell(XSSFSheet sxssfSheet, List<IncomePersonalMonthlyDto> listData,
			Class<IncomePersonalMonthlyDto> objDto, List<ItemColsExcelDto> cols, Map<String, String> mapColFormat,
			Map<String, CellStyle> mapColStyle, Map<String, Object> mapColDefaultValue, Map<String, Field> mapFields,
			int rowIndex, int dataIndex, CellStyleDto cellStyleDto, int dataSize, boolean fillColor)
			throws IllegalArgumentException, IllegalAccessException {

		XSSFRow row = sxssfSheet.createRow(rowIndex);

		IncomePersonalMonthlyDto excelDto = listData.get(dataIndex);

		if (excelDto != null) {

			// set value to map
			Field[] headerFields = populateFields(objDto);
			Map<String, Object> mapValueFields = new HashMap<String, Object>();
			for (Field field : headerFields) {
				if (!field.isAccessible()) {
					field.setAccessible(true);
				}
				mapValueFields.put(field.getName().toUpperCase(), field.get(excelDto));
			}
			// begin fill to cell

			for (ItemColsExcelDto col : cols) {

				// data type of field
				XSSFCell cell = row.createCell(col.getColIndex());

				// col.getColName());
				Field field = mapFields.get(col.getColName().toUpperCase());

				String typeFields = field.getType().getSimpleName().toUpperCase();
				DataType dataType = DataType.valueOf(typeFields);

				String formatType = null;
				if (null != mapColFormat && mapColFormat.size() != 0) {
					formatType = mapColFormat.get(col.getColName());
				}

				// col value
//		                for(IncomePersonalMonthlyDto ls: listData) {
//
//		                Object colValue = mapValueFields.get(col.getColName().toUpperCase());
//
//		                if (mapColDefaultValue != null && mapColDefaultValue.containsKey(col.getColName())) {
//		                    if (null == mapColDefaultValue.get(col.getColName())   || EMP_STRING.equals(mapColDefaultValue.get(col.getColName()))) {
//		                        cell.setCellValue(EMP_STRING);
//		                    } else {
//		                        String val = String.valueOf(mapColDefaultValue.get(col.getColName()));
//		                        cell.setCellValue(val);
//		                    }
//		                }

				Object colValue = mapValueFields.get(col.getColName().toUpperCase());

				if (mapColDefaultValue != null && mapColDefaultValue.containsKey(col.getColName())) {
					if (null == mapColDefaultValue.get(col.getColName())
							|| EMP_STRING.equals(mapColDefaultValue.get(col.getColName()))) {
						cell.setCellValue(EMP_STRING);
					} else {
						String val = String.valueOf(mapColDefaultValue.get(col.getColName()));
						cell.setCellValue(val);
					}
				}

				switch (dataType) {
				case LONG:
					if (colValue != null) {
						Long valueOfLong = Long.parseLong(colValue.toString());
						cell.setCellValue(valueOfLong);
						cell.setCellType(CellType.NUMERIC);
					}

					cell.setCellStyle(cellStyleDto.getCellStyleLeft());
					if (mapColStyle != null && mapColStyle.containsKey(col.getColName())) {
						cell.setCellStyle(mapColStyle.get(col.getColName()));
					}
					break;
				case INTEGER:
					if (colValue != null) {
						Integer valueOfInteger = Integer.parseInt(colValue.toString());
						cell.setCellValue(valueOfInteger);
						cell.setCellType(CellType.NUMERIC);
					}

					cell.setCellStyle(cellStyleDto.getCellStyleRight());
					if (col.getColName().equals("ROWNUM") || col.getColName().equals("NO")) {
						cell.setCellStyle(cellStyleDto.getCellStyleCenter());
					}

					if (mapColStyle != null && mapColStyle.containsKey(col.getColName())) {
						cell.setCellStyle(mapColStyle.get(col.getColName()));
					}
					break;
				case INT:
					if (col.getColName().equals("ROWNUM") || col.getColName().equals("NO")) {
						if (colValue != null && !fillColor) {
							int valueOfInt = Integer.parseInt(colValue.toString());
							cell.setCellValue(valueOfInt);
						}

						cell.setCellStyle(cellStyleDto.getCellStyleCenter());
						if (mapColStyle != null && mapColStyle.containsKey(col.getColName())) {
							cell.setCellStyle(mapColStyle.get(col.getColName()));
						}
					} else {
						if (colValue != null) {
							int valueOfInt = Integer.parseInt(colValue.toString());
							cell.setCellValue(valueOfInt);
						}

						cell.setCellStyle(cellStyleDto.getCellStyleRight());
						if (mapColStyle != null && mapColStyle.containsKey(col.getColName())) {
							cell.setCellStyle(mapColStyle.get(col.getColName()));
						}
					}

					break;
				case DOUBLE:
					if (colValue != null) {
						Double valueOfDouble = Double.parseDouble(colValue.toString());

						if (valueOfDouble % 1 > 0) {
							// format "0.00"
							if (null != formatType && formatType.equalsIgnoreCase(CommonConstant.PERCENT)) {
								cell.setCellValue(valueOfDouble / 100);
								cell.setCellStyle(cellStyleDto.getCellStyleRightDouble1WithPercent());
							} else if (null != formatType
									&& formatType.equalsIgnoreCase(CommonConstant.DOUBLE_SHOW_ALL)) {
								cell.setCellValue(valueOfDouble);
								cell.setCellStyle(cellStyleDto.getCellStyleRightDouble3());
							} else {
								cell.setCellValue(valueOfDouble);
								cell.setCellStyle(cellStyleDto.getCellStyleRightDouble1());
							}

							if (mapColStyle != null && mapColStyle.containsKey(col.getColName())) {
								cell.setCellStyle(mapColStyle.get(col.getColName()));
							}
						} else {
							// format "0"
							if (null != formatType && formatType.equalsIgnoreCase(CommonConstant.PERCENT)) {
								cell.setCellValue(valueOfDouble / 100);
								cell.setCellStyle(cellStyleDto.getCellStyleRightDouble2WithPercent());
							} else {
								cell.setCellValue(valueOfDouble);
								cell.setCellStyle(cellStyleDto.getCellStyleRightDouble2());
							}

							if (mapColStyle != null && mapColStyle.containsKey(col.getColName())) {
								cell.setCellStyle(mapColStyle.get(col.getColName()));
							}
						}
					} else {
						cell.setCellStyle(cellStyleDto.getCellStyleBoder());

						if (mapColStyle != null && mapColStyle.containsKey(col.getColName())) {
							cell.setCellStyle(mapColStyle.get(col.getColName()));
						}
					}

					break;
				case BIGDECIMAL:
					if (colValue != null) {
						BigDecimal valueBigdecimal = (BigDecimal) colValue;
						if (valueBigdecimal != null) {
							cell.setCellValue(valueBigdecimal.doubleValue());
							if (cellStyleDto.getIsFormatFinance() == 0) { // Normal
								// format
								// number
								if (valueBigdecimal.doubleValue() % 1 > 0) {
									// format "#,##0.00"
									cell.setCellStyle(cellStyleDto.getCellStyleRightBigDecimal1());
								} else {
									// format "#,##0"
									cell.setCellStyle(cellStyleDto.getCellStyleRightBigDecimal2());
								}
							} else { // Format number with Finance
										// money
								cell.setCellStyle(cellStyleDto.getCellStyleFinanceFormat());
							}
							if (StringUtils.equalsIgnoreCase(excelDto.getMainDirectory(), "Hoa hồng")
									|| StringUtils.equalsIgnoreCase(excelDto.getMainDirectory(), "Tài trợ")
									|| StringUtils.equalsIgnoreCase(excelDto.getMainDirectory(), "Phụ cấp")
											&& StringUtils.isBlank(excelDto.getSubDirectory())) {
								cell.setCellStyle(cellStyleDto.getCellStyleTitle3());
							} else if (StringUtils.equalsIgnoreCase(excelDto.getMainDirectory(),
									"Số dư tháng trước mang sang")) {
								cell.setCellStyle(cellStyleDto.getCellStyleTitle5());
							} else if (StringUtils.isNotBlank(excelDto.getMainDirectory())
									&& StringUtils.isBlank(excelDto.getSubDirectory())
									&& StringUtils.isBlank(excelDto.getDetailDirectory())) {
								cell.setCellStyle(cellStyleDto.getCellStyleTitle5());
							} else {
								cell.setCellStyle(cellStyleDto.getCellStyleTitle4());
							}

//		                            if(StringUtils.equalsIgnoreCase(excelDto.getMainDirectory(),"Số dư tháng trước mang sang")) {
//				                        cell.setCellStyle(cellStyleDto.getCellStyleTitle5());	
//			                        } else if(StringUtils.isNotBlank(excelDto.getMainDirectory()) && StringUtils.isBlank(excelDto.getSubDirectory()) &&  StringUtils.isBlank(excelDto.getDetailDirectory())) {
//				                        cell.setCellStyle(cellStyleDto.getCellStyleTitle3());	
//			                        } else {
//				                        cell.setCellStyle(cellStyleDto.getCellStyleTitle4());	
//			                        }	

						}
					} else {
						if (StringUtils.isNotBlank(excelDto.getMainDirectory())
								&& StringUtils.isBlank(excelDto.getSubDirectory())
								&& StringUtils.isBlank(excelDto.getDetailDirectory())) {
							cell.setCellStyle(cellStyleDto.getCellStyleTitle3());
						} else {
							cell.setCellStyle(cellStyleDto.getCellStyleTitle4());
						}

					}

					break;
				case DATE:
					if (colValue != null) {
						cell.setCellValue((Date) colValue);
					}
					cell.setCellStyle(cellStyleDto.getCellStyleDateCenter());

					// short oldFormat = cellStyleDto.getCellStyleDateCenter().getDataFormat();

					// 2020 11 25 LocLT format cell date
					try {
						if (formatType != null) {

//		                        	short format = cellStyleDto.getSxssfWorkbook().getCreationHelper().createDataFormat().getFormat(formatType);
//		                        	cellStyleDto.getCellStyleDateCenter().setDataFormat(format);

							SXSSFWorkbook wb = cellStyleDto.getSxssfWorkbook();
							CellStyle cellStyle = wb.createCellStyle();
							cellStyle.cloneStyleFrom(cellStyleDto.getCellStyleDateCenter());
							CreationHelper createHelper = wb.getCreationHelper();

							short fmt = createHelper.createDataFormat().getFormat(formatType);
							cellStyle.setDataFormat(fmt);

							cell.setCellStyle(cellStyle);
						}
					} catch (Exception e) {
						// TODO: handle exception
					}
					// 2020 11 25 LocLT format cell date

					if (mapColStyle != null && mapColStyle.containsKey(col.getColName())) {
						cell.setCellStyle(mapColStyle.get(col.getColName()));
					}

					// cellStyleDto.getCellStyleDateCenter().setDataFormat(oldFormat);

					break;
				case TIMESTAMP:
					if (colValue != null) {
						cell.setCellValue((Date) colValue);
					}
					cell.setCellStyle(cellStyleDto.getCellStyleDateCenter());

					if (mapColStyle != null && mapColStyle.containsKey(col.getColName())) {
						cell.setCellStyle(mapColStyle.get(col.getColName()));
					}
					break;
				case STRING:
					if (colValue != null) {
						cell.setCellValue(colValue.toString());

					}
					if (StringUtils.isNotBlank(excelDto.getMainDirectory())
							&& StringUtils.isBlank(excelDto.getSubDirectory())
							&& StringUtils.isBlank(excelDto.getDetailDirectory())) {
						cell.setCellStyle(cellStyleDto.getCellStyleTitle1());
					} else {
						cell.setCellStyle(cellStyleDto.getCellStyleTitle2());
					}

					break;
				case BOOLEAN:
					if (colValue != null) {
						cell.setCellValue(colValue.toString());
					}
					cell.setCellStyle(cellStyleDto.getCellStyleCenter());

					if (mapColStyle != null && mapColStyle.containsKey(col.getColName())) {
						cell.setCellStyle(mapColStyle.get(col.getColName()));
					}
					break;
				default:
					break;
				}
			} // END FOR 2

		}
//		        }
	}

	public List<IncomePersonalMonthlyDto> formatData(List<IncomePersonalMonthlyDto> data) {
//        List<IncomePersonalMonthlyDto> dataAll = new ArrayList<IncomePersonalMonthlyDto>();
		List<IncomePersonalMonthlyDto> dataAll2 = new ArrayList<IncomePersonalMonthlyDto>();
//        Map<String,List<IncomePersonalMonthlyDto>>  map1 = data.stream().collect(Collectors.groupingBy(IncomePersonalMonthlyDto::getSubCode));
//        Set<String> sub = map1.keySet(); 
//        List<IncomePersonalMonthlyDto> testData = new ArrayList<>();
//        for(String subCode : sub){
//        	testData = map1.get(subCode);
//        	//dòng đầu tiên là dm con của dm 9
//	        testData.add((0), new IncomePersonalMonthlyDto(testData.get(0).getMainCode(), testData.get(0).getMainName(), null, testData.get(0).getSubName(), null));
//	       dataAll.addAll(testData);
//        }
//        Map<String,List<IncomePersonalMonthlyDto>> map2 = dataAll.stream()
//                .collect(Collectors.groupingBy(IncomePersonalMonthlyDto::getMainCode));
//        Set<String> main = map2.keySet();
//        List<IncomePersonalMonthlyDto> testData2 = new ArrayList<>();
//        for(String mainCode : main){
//            testData2 = map2.get(mainCode);
//            String sum = testData2.stream().map(x -> x.getAmount()==null?"0":x.getAmount()).reduce("0", String::concat);
//            //dòng đầu tiên của dm 9
//            for (IncomePersonalMonthlyDto dto : testData2) {
//                dto.setDetailDirectory (dto.getItemName());
//                dto.setAmountDirectory(dto.getAmount());
//            }
////            testData2.add(0, new IncomePersonalMonthlyDto(null, null, testData2.get(1).getMainName(), null, sum));
//            dataAll2.addAll(testData2);
//        }

		// vunt

		List<IncomePersonalMonthlyDto> level0 = new ArrayList<IncomePersonalMonthlyDto>();
		level0 = data.stream().filter(lv0 -> "0".equalsIgnoreCase(lv0.getLevelId())).collect(Collectors.toList());
		List<IncomePersonalMonthlyDto> level1 = new ArrayList<IncomePersonalMonthlyDto>();
		level1 = data.stream().filter(lv1 -> "1".equalsIgnoreCase(lv1.getLevelId())).collect(Collectors.toList());
		List<IncomePersonalMonthlyDto> level2 = new ArrayList<IncomePersonalMonthlyDto>();
		level2 = data.stream().filter(lv2 -> "2".equalsIgnoreCase(lv2.getLevelId())).collect(Collectors.toList());

		List<IncomePersonalMonthlyDto> level1New = new ArrayList<IncomePersonalMonthlyDto>();
		for (IncomePersonalMonthlyDto dto : level1) {
			dto.setSubDirectory(dto.getSubName());
			dto.setAmountDirectory(dto.getAmount());
			level1New.add(dto);
			List<IncomePersonalMonthlyDto> level2OfLevel1 = level2.stream()
					.filter(lv2 -> lv2.getSubCode().equalsIgnoreCase(dto.getSubCode())).collect(Collectors.toList());
			if (CollectionUtils.isNotEmpty(level2OfLevel1)) {
				level2OfLevel1.forEach((entry) -> {
					if (StringUtils.equalsIgnoreCase(entry.getMainCode(), "02")) {
						String[] lstTT;
						lstTT = entry.getItemName().split(";");
						for (String tt : lstTT) {
							IncomePersonalMonthlyDto ttDto = new IncomePersonalMonthlyDto();
							ttDto.setMainCode(entry.getMainCode());
							ttDto.setDetailDirectory(tt);
							level1New.add(ttDto);
						}
					} else {
//						DecimalFormatSymbols symbols = new DecimalFormatSymbols();
//						symbols.setGroupingSeparator(',');
//						symbols.setDecimalSeparator('.');
//						String pattern = "#,##0.0#";
//						if (!isNullOrZero(entry.getAmount()) && entry.getAmount().doubleValue() % 1 > 0) {
//							// format "#,##0.00"
//							pattern = "#,##0.00";
//						} else {
//							// format "#,##0"
//							pattern = "#,##0";
//						}
//						DecimalFormat decimalFormat = new DecimalFormat(pattern, symbols);
//						decimalFormat.setParseBigDecimal(true);
//						String amount = "";
//						if (!isNullOrZero(entry.getAmount())) {
//							amount = decimalFormat.format(entry.getAmount());
//							entry.setDetailDirectory(entry.getItemName().concat(": ").concat(amount.toString()));
//						}
            			entry.setDetailDirectory(entry.getItemName());
        				entry.setAmountDirectory(entry.getAmount());
						level1New.add(entry);
					}
				});
			}
		}
		for (IncomePersonalMonthlyDto dto : level0) {
			dto.setMainDirectory(dto.getMainName());
			dto.setAmountDirectory(dto.getAmount());
			dataAll2.add(dto);
			List<IncomePersonalMonthlyDto> level1OfLevel0 = level1New.stream()
					.filter(lv1 -> lv1.getMainCode().equalsIgnoreCase(dto.getMainCode())).collect(Collectors.toList());
			if (CollectionUtils.isNotEmpty(level1OfLevel0)) {
				dataAll2.addAll(level1OfLevel0);
			}

		}
		return dataAll2;
	}

	public static boolean isNullOrZero(BigDecimal number) {
		boolean isBigDecimalValueNullOrZero = false;
		if (number == null)
			isBigDecimalValueNullOrZero = true;
		else if (number != null && number.compareTo(BigDecimal.ZERO) == 0)
			isBigDecimalValueNullOrZero = true;

		return isBigDecimalValueNullOrZero;
	}

//	public static void main(String[] args) {
//	    //List<IncomePersonalMonthlyDto> dataAll = new ArrayList<IncomePersonalMonthlyDto>();
//	    //List<IncomePersonalMonthlyDto> dataAll2 = new ArrayList<IncomePersonalMonthlyDto>();
//        
//	    List<IncomePersonalMonthlyDto> data = detail();
//	    List<IncomePersonalMonthlyDto> dataAll = new ArrayList<IncomePersonalMonthlyDto>();
//        List<IncomePersonalMonthlyDto> dataAll2 = new ArrayList<IncomePersonalMonthlyDto>();
//        Map<String,List<IncomePersonalMonthlyDto>>  map1 = data.stream()
//                .collect(Collectors.groupingBy(IncomePersonalMonthlyDto::getSubCode));
//        Set<String> sub = map1.keySet(); 
//        List<IncomePersonalMonthlyDto> testData = new ArrayList<>();
//        for(String subCode : sub){
//        testData = map1.get(subCode);
//        //dòng đầu tiên là dm con của dm 9
//        testData.add((0), new IncomePersonalMonthlyDto(
//                                                          testData.get(0).getMainCode()//group
//                                                        , testData.get(0).getMainName()//display
//                                                        , null
//                                                        , null
//                                                        , null
//                                                        , null
//                                                        , null //amount
//                                                        , null //main
//                                                        , testData.get(0).getSubName()//sub
//                                                        , null//item
//                                                        , null));//amount dir
//       dataAll.addAll(testData);
//  }
//        Map<String,List<IncomePersonalMonthlyDto>> map2 = dataAll.stream()
//                .collect(Collectors.groupingBy(IncomePersonalMonthlyDto::getMainCode));
//        Set<String> main = map2.keySet();
//        List<IncomePersonalMonthlyDto> testData2 = new ArrayList<>();
//        for(String mainCode : main){
//            testData2 = map2.get(mainCode);
//
//            Integer sum = testData2.stream()
//                    .map(x -> x.getAmount()==null?0:x.getAmount())
//                    .reduce(0, Integer::sum);
//            
//            //dòng đầu tiên của dm 9
//            for (IncomePersonalMonthlyDto dto : testData2) {
//                dto.setDetailDirectory (dto.getItemName());
//                //dto.setAmountDirectory(sum);
//            }
//            testData2.add(0, new IncomePersonalMonthlyDto(
//                                                            null
//                                                          , null
//                                                          , null
//                                                          , null
//                                                          , null
//                                                          , null
//                                                          , null //amount
//                                                          , testData2.get(1).getMainName() //main
//                                                          , null//sub
//                                                          , null//item
//                                                          , sum));//amount dir
//            
//            dataAll2.addAll(testData2);
//        }
//    }

	// weekly
	@Override
	public ObjectDataRes<IncomePersonalWeeklyDto> getListIncomePersonalWeekly(IncomePersonalMonthSearchDto searchDto) {
		ObjectDataRes<IncomePersonalWeeklyDto> resulLst = new ObjectDataRes<>();
		List<IncomePersonalWeeklyDto> list = new ArrayList<IncomePersonalWeeklyDto>();
		try {
			IncomePersonalWeeklyParam param = new IncomePersonalWeeklyParam(searchDto.getAgentCode(),
					searchDto.getPaymentPeriod(), null, null);
			sqlManagerDb2Service.call(STORE_INCOME_PERSONAL_WEEKLY, param);
			list = (CommonCollectionUtil.isNotEmpty(param.data) ? param.data : list);
			IncomePersonalWeeklyDto dto = new IncomePersonalWeeklyDto();
			dto.setIncomeType("Tổng cộng");
			dto.setPaymentAmount(list.stream().mapToInt(x -> x.getPaymentAmount()).sum());
			list.add(dto);
			resulLst.setDatas(list);
		} catch (Exception e) {
			logger.error("##getListIncomePersonalWeekly##", e);
		}
		return resulLst;
	}

	// export weekly
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public ResponseEntity exportIncomePersonalWeekly(IncomePersonalMonthSearchDto searchDto) {
		ResponseEntity res = null;
		try {
			List<IncomePersonalWeeklyDto> lstdata = new ArrayList<>();
			IncomePersonalWeeklyParam param = new IncomePersonalWeeklyParam();
			param.agentCode = searchDto.getAgentCode();
			param.paymentPeriod = searchDto.getPaymentPeriod();
			sqlManagerDb2Service.call(STORE_INCOME_PERSONAL_WEEKLY, param);
			if (CommonCollectionUtil.isNotEmpty(param.data)) {
				lstdata = param.data;
			}
			String datePattern = systemConfig.getConfig(SystemConfig.DATE_PATTERN);
			datePattern = "dd/MM/yyyy";
			String templateName = "Thu_nhap_tuan.xlsx";
			List<ItemColsExcelDto> cols = new ArrayList<>();
			ImportExcelUtil.setListColumnExcel(IncomePersonalWeeklyEnum.class, cols);

			String templatePath = servletContext
					.getRealPath(CommonConstant.REAL_PATH_TEMPLATE_EXCEL + "/" + templateName);
			String startRow = "A9";
			ExportExcelUtil exportExcel = new ExportExcelUtil<>();
			Map<String, String> mapColFormat = null;
			Map<String, Object> setMapColDefaultValue = null;
			try (XSSFWorkbook xssfWorkbook = exportExcel.getXSSFWorkbook(templatePath);) {
				Map<String, CellStyle> mapColStyle = null;
				String repo = systemConfig.getConfig(systemConfig.REPO_UPLOADED_MAIN);
				String path = systemConfig.getPhysicalPathById(repo, null); // path up service
				res = exportExcel.doExportExcelHeaderWithColFormatRestUpService(xssfWorkbook, 0, null, lstdata,
						IncomePersonalWeeklyDto.class, cols, datePattern, startRow, mapColFormat, mapColStyle,
						setMapColDefaultValue, null, true, templateName, true, path);
			} catch (Exception e) {
				logger.error("##doExport##", e);
			}
		} catch (Exception e) {
			logger.error("##exportLis##", e);
		}
		return res;
	}

	// yearly
	@Override
	public List<IncomePersonalYearlyDto> getListIncomePersonalYearly(IncomePersonalYearlySearchDto searchDto) {
		IncomePersonaYearlyParam param = new IncomePersonaYearlyParam();
		param.agentCode = searchDto.getAgentCode();
		param.year = searchDto.getYear();
		sqlManagerDb2Service.call(STORE_INCOME_PERSONAL_YEARLY, param);
		List<IncomePersonalYearlyDto> list = new ArrayList<>();
		if (CommonCollectionUtil.isNotEmpty(param.data)) {
			list = param.data;
		}
		return list;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public ResponseEntity exportIncomePersonalYearly(IncomePersonalYearlySearchDto searchDto) {
		ResponseEntity res = null;
		try {
			List<IncomePersonalYearlyDto> lstdata = new ArrayList<>();
			IncomePersonaYearlyParam param = new IncomePersonaYearlyParam();
			param.agentCode = searchDto.getAgentCode();
			param.year = searchDto.getYear();
			sqlManagerDb2Service.call(STORE_INCOME_PERSONAL_YEARLY, param);
			if (CommonCollectionUtil.isNotEmpty(param.data)) {
				lstdata = param.data;
			}
			String datePattern = systemConfig.getConfig(SystemConfig.DATE_PATTERN);
			datePattern = "dd/MM/yyyy";
			String templateName = "Income_Personal_Yearly.xlsx";
			List<ItemColsExcelDto> cols = new ArrayList<>();

			ImportExcelUtil.setListColumnExcel(IncomePersonalYearlyEnum.class, cols);

			String templatePath = servletContext
					.getRealPath(CommonConstant.REAL_PATH_TEMPLATE_EXCEL + "/" + templateName);
			String startRow = "A8";
			ExportExcelUtil exportExcel = new ExportExcelUtil<>();
			Map<String, String> mapColFormat = null;
			Map<String, Object> setMapColDefaultValue = null;
			try (XSSFWorkbook xssfWorkbook = exportExcel.getXSSFWorkbook(templatePath);) {
				Map<String, CellStyle> mapColStyle = null;
				String repo = systemConfig.getConfig(systemConfig.REPO_UPLOADED_MAIN);
				String path = systemConfig.getPhysicalPathById(repo, null); // path up service
				res = exportExcel.doExportExcelHeaderWithColFormatRestUpService(xssfWorkbook, 0, null, lstdata,
						IncomePersonalYearlyDto.class, cols, datePattern, startRow, mapColFormat, mapColStyle,
						setMapColDefaultValue, null, true, templateName, true, path);
			} catch (Exception e) {
				logger.error("##doExport##", e);
			}
		} catch (Exception e) {
			logger.error("##exportLis##", e);
		}
		return res;
	}

	public void setDataHeaderToXSSFWorkbookSheet(XSSFWorkbook xssfWorkbook, int sheetNumber, String[] titleHeader,
			String titleName, String startRow, List<String> datas) {
		XSSFSheet xssfSheet = xssfWorkbook.getSheetAt(sheetNumber);
		CellReference landMark = new CellReference(startRow);
		int dataSize = datas.size();
		if (datas != null && dataSize > 0) {
			int mark = landMark.getRow();
			int j = 0;
			for (int i = mark; i < mark + dataSize; i++) {
				XSSFRow row = xssfSheet.getRow(i);
				XSSFCell cell = null;
				if (row == null) {
					row = xssfSheet.createRow(i);
					cell = row.getCell(0);
					if (cell == null) {
						cell = row.createCell(0);
					}
				} else {
					cell = row.getCell(0);
				}
				cell.setCellValue(datas.get(j));
				j++;
			}
		}
		if (titleHeader != null && titleHeader.length > 0) {
			CellStyle headerCellStyle = xssfSheet.getWorkbook().createCellStyle();
			headerCellStyle.setFillForegroundColor(IndexedColors.ROYAL_BLUE.index);
			headerCellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

			Font font = xssfWorkbook.createFont();
			font.setBold(true);
			font.setColor(IndexedColors.WHITE.index);
			headerCellStyle.setAlignment(HorizontalAlignment.CENTER);
			headerCellStyle.setWrapText(true);
			headerCellStyle.setFont(font);

			headerCellStyle.setBorderBottom(BorderStyle.THIN);
			headerCellStyle.setBorderTop(BorderStyle.THIN);
			headerCellStyle.setBorderRight(BorderStyle.THIN);
			headerCellStyle.setBorderLeft(BorderStyle.THIN);

			XSSFRow row4 = xssfSheet.getRow(4);
			for (int i = 0; i < titleHeader.length; i++) {
				XSSFCell cell4 = row4.getCell(i);
				if (cell4 == null) {
					cell4 = row4.createCell(i);
				}
				cell4.setCellValue(titleHeader[i]);
				cell4.setCellStyle(headerCellStyle);
			}
		}
	}

	@Override
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public <T, E extends Enum<E>, M> ResponseEntity exportListData(List<T> resultDto, String type, BigDecimal amount,
			String fileName, String row, Class<E> enumDto, Class<M> className, List<String> lstInfor,
			String startInfo) {
		ResponseEntity res = null;
		try {
			String datePattern = systemConfig.getConfig(SystemConfig.DATE_PATTERN);
			datePattern = "dd/MM/yyyy";
			String templateName = fileName;
			String templatePath = servletContext
					.getRealPath(CommonConstant.REAL_PATH_TEMPLATE_EXCEL + "/" + templateName);
			String startRow = row;
			List<ItemColsExcelDto> cols = new ArrayList<>();
			ImportExcelUtil.setListColumnExcel(enumDto, cols);
			ExportExcelUtil exportExcel = new ExportExcelUtil<>();
			Map<String, String> mapColFormat = null;
			Map<String, Object> setMapColDefaultValue = null;
			try (XSSFWorkbook xssfWorkbook = exportExcel.getXSSFWorkbook(templatePath);) {
				Map<String, CellStyle> mapColStyle = null;

				int maxRow = resultDto.size();

				if (!type.equals("")) {
					writeTotalData(type, amount, xssfWorkbook, 0, maxRow);
				}
				

				setDataHeaderToXSSFWorkbookSheet(xssfWorkbook, 0, null, null, startInfo, lstInfor);
				String repo = systemConfig.getConfig(systemConfig.REPO_UPLOADED_MAIN);
				String path = systemConfig.getPhysicalPathById(repo, null); // path up service
				res = exportExcel.doExportExcelHeaderWithColFormatRestUpService(xssfWorkbook, 0, null, resultDto,
						className, cols, datePattern, startRow, mapColFormat, mapColStyle, setMapColDefaultValue, null,
						true, templateName, true, path);
			} catch (Exception e) {
				logger.error("exportListData: ", e);
			}
		} catch (Exception e) {
			logger.error("exportListData: ", e);
		}
		return res;
	}

	public void writeTotalData(String type, BigDecimal amount, XSSFWorkbook xssfWorkbook, int sheetNumber, int maxRow) {

		XSSFSheet xssfSheet = xssfWorkbook.getSheetAt(0);
		XSSFRow rowl = xssfSheet.createRow(maxRow + 8);

		xssfSheet.addMergedRegion(new CellRangeAddress(maxRow + 8, maxRow + 8, 0, 1));

		CellStyle style = xssfWorkbook.createCellStyle();

		style.setBorderBottom(BorderStyle.THIN);
		style.setBorderRight(BorderStyle.THIN);
		style.setBorderLeft(BorderStyle.THIN);
		style.setBorderTop(BorderStyle.THIN);

		for (int i = 0; i < 4; i++) {
			XSSFCell cell = rowl.createCell(i);
			cell.setCellStyle(style);
		}

		Font font = xssfWorkbook.createFont();
		font.setBold(true);
		style.setFont(font);

		XSSFCell cellTotal = rowl.getCell(0);
		cellTotal.setCellValue(type);

		XSSFRow row = xssfSheet.getRow(maxRow + 8);
		CellStyle stylel = xssfWorkbook.createCellStyle();

		stylel.setBorderBottom(BorderStyle.THIN);
		stylel.setBorderRight(BorderStyle.THIN);
		stylel.setBorderLeft(BorderStyle.THIN);
		stylel.setBorderTop(BorderStyle.THIN);
		stylel.setFont(font);

		// createHelper
		CreationHelper createHelper = xssfWorkbook.getCreationHelper();
		// dataFormat
		DataFormat dataFormat = createHelper.createDataFormat();

		stylel.setAlignment(HorizontalAlignment.RIGHT);
		stylel.setFont(font);
		stylel.setDataFormat(dataFormat.getFormat("#,##0"));

		XSSFCell cellNumber = row.getCell(3);
		cellNumber.setCellStyle(stylel);
		BigDecimal valueBigdecimal = (BigDecimal) amount;
		cellNumber.setCellValue(valueBigdecimal.doubleValue());

	}
	
	public YearlyPaymentDto getYearlyPaymentForAG(IncomePersonalYearlySearchDto searchDto) {
		YearlyPaymentDto result = new YearlyPaymentDto();
		try {
			YearlyPaymentAGParam param = new YearlyPaymentAGParam();
			param.agentCode = searchDto.getAgentCode();
			param.year = searchDto.getYear();
			sqlManagerDb2Service.call(STORE_GET_YEARLY_PAYMENT_BY_TVTC, param);
			if (CommonCollectionUtil.isNotEmpty(param.data)) {
				result.setMonthPayment(param.data);
			}
		} catch (Exception e) {
			logger.error("##getYearlyPaymentForAG Call DS_SP_GET_YEARLY_PAYMENT_BY_TVTC##", e);
		}

		try {
			YearlySumPaymentAGParam param = new YearlySumPaymentAGParam();
			param.agentCode = searchDto.getAgentCode();
			param.year = searchDto.getYear();
			sqlManagerDb2Service.call(STORE_GET_SUM_PAYMENT_YEAR_TVTC, param);
			if (CommonCollectionUtil.isNotEmpty(param.data)) {
				result.setTotalPayment(param.data);
			}
		} catch (Exception e) {
			logger.error("##getYearlyPaymentForAG Call DS_SP_GET_SUM_PAYMENT_YEAR_TVTC##", e);
		}
		return result;
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public ResponseEntity exportYearlyPaymentForAG(IncomePersonalYearlySearchDto searchDto) {
		ResponseEntity result = null;
		try {     
   	 
        	YearlyPaymentDto yearData =  this.getYearlyPaymentForAG(searchDto);
			String templateName = "Thu_nhap_nam_AG.xlsx";
			
			String templatePath = servletContext
					.getRealPath(CommonConstant.REAL_PATH_TEMPLATE_EXCEL + "/" + templateName);
		
			ExportExcelUtil exportExcel = new ExportExcelUtil<>();			

			try (XSSFWorkbook xssfWorkbook = exportExcel.getXSSFWorkbook(templatePath);) {
				
				List<String> datas = new ArrayList<String>();
				datas.add("Đại lý: " + searchDto.getAgentCode() + " - " + searchDto.getAgentType() + " "
						+ searchDto.getAgentName());
				datas.add("Số TK: " + searchDto.getBankAccountNumber() + " - " + searchDto.getBankAccountName());
				datas.add("Mã số thuế: " + (searchDto.getIdPersonalIncomeTax() != null ? searchDto.getIdPersonalIncomeTax() : ""));
				
				setDataHeaderWithoutTitle(xssfWorkbook, 0, "B3", datas);
				result = doExportYearlyPayment(xssfWorkbook, yearData, templateName, true, searchDto.getAgentCode(), null);
					
			} catch (Exception e) {
				logger.error("##doExport##", e);
			}
		} catch (Exception e) {
			logger.error("##exportYearlyPaymentForAG##", e);
		}
		return result;
	}
	
	public YearlyPaymentDto getYearlyPaymentForGA(IncomePersonalYearlySearchDto searchDto) {
		YearlyPaymentDto result = new YearlyPaymentDto();
		try {
			YearlyPaymentGAParam param = new YearlyPaymentGAParam();
			param.agentCode = searchDto.getAgentCode();
			param.gaCode = searchDto.getGaCode();
			param.year = searchDto.getYear();
			sqlManagerDb2Service.call(STORE_GET_YEARLY_PAYMENT_BY_GA, param);
			if (CommonCollectionUtil.isNotEmpty(param.data)) {
				 List<MonthPaymentDto> dataAdjusted = adjustMonthPaymentDtos(param.data);
				result.setMonthPayment(dataAdjusted);
			}
		} catch (Exception e) {
			logger.error("##getYearlyPaymentForAG Call DS_SP_GET_YEARLY_PAYMENT_BY_GA##", e);
		}

		try {
			YearlySumPaymentGAParam param = new YearlySumPaymentGAParam();
			param.agentCode = searchDto.getAgentCode();
			param.gaCode = searchDto.getGaCode();
			param.year = searchDto.getYear();
			sqlManagerDb2Service.call(STORE_GET_SUM_PAYMENT_YEAR_GA, param);
			if (CommonCollectionUtil.isNotEmpty(param.data)) {
				result.setTotalPayment(param.data);
			}
		} catch (Exception e) {
			logger.error("##getYearlyPaymentForAG Call DS_SP_GET_SUM_PAYMENT_YEAR_GA##", e);
		}
		return result;
	}
	
	/**
	 * Adjust datas before use it
	 * @param datas
	 * @return
	 */
	public static List<MonthPaymentDto> adjustMonthPaymentDtos(List<MonthPaymentDto> datas) {
		final String MAIN_CODE_VAT10 = "04";
		final String STYLE_BLUE = "blue";
		final List<String> LIST_LINE_BLUE = Arrays.asList("00", "07");
		final String MAIN_NAME_VAT10 = "TỔNG CHI TRẢ TRONG THÁNG (XUẤT HÓA ĐƠN VAT 10%)";
		
        BigDecimal janTotal = BigDecimal.ZERO;
        BigDecimal febTotal = BigDecimal.ZERO;
        BigDecimal marTotal = BigDecimal.ZERO;
        BigDecimal aprTotal = BigDecimal.ZERO;
        BigDecimal mayTotal = BigDecimal.ZERO;
        BigDecimal junTotal = BigDecimal.ZERO;
        BigDecimal julTotal = BigDecimal.ZERO;
        BigDecimal augTotal = BigDecimal.ZERO;
        BigDecimal sepTotal = BigDecimal.ZERO;
        BigDecimal octTotal = BigDecimal.ZERO;
        BigDecimal novTotal = BigDecimal.ZERO;
        BigDecimal decTotal = BigDecimal.ZERO;

        List<MonthPaymentDto> toRemove = new ArrayList<>();
        int insertIndex = -1; // To store the position of the first '04'

        // Sum amounts and collect elements with mainCode '04'
        for (int i = 0; i < datas.size(); i++) {
            MonthPaymentDto dto = datas.get(i);
            String mainCode = dto.getMainCode();
            if (MAIN_CODE_VAT10.equals(mainCode)) {
				if (insertIndex == -1) {
					insertIndex = i; // Record the first occurrence of '04'
				}
                janTotal = janTotal.add(dto.getJanAmount());
                febTotal = febTotal.add(dto.getFebAmount());
                marTotal = marTotal.add(dto.getMarAmount());
                aprTotal = aprTotal.add(dto.getAprAmount());
                mayTotal = mayTotal.add(dto.getMayAmount());
                junTotal = junTotal.add(dto.getJunAmount());
                julTotal = julTotal.add(dto.getJulAmount());
                augTotal = augTotal.add(dto.getAugAmount());
                sepTotal = sepTotal.add(dto.getSepAmount());
                octTotal = octTotal.add(dto.getOctAmount());
                novTotal = novTotal.add(dto.getNovAmount());
                decTotal = decTotal.add(dto.getDecAmount());

                // Collect the element to remove later
                toRemove.add(dto);
            } else {
            	if(i > 0 && LIST_LINE_BLUE.contains(mainCode)) {
            		dto.setStyle(STYLE_BLUE);
            	}
            }
        }

        // Remove the collected elements from the original list
        datas.removeAll(toRemove);

        // Create a new MonthPaymentDto with the summed amounts
        MonthPaymentDto totalDto = new MonthPaymentDto();
        totalDto.setMainCode(MAIN_CODE_VAT10);
        totalDto.setMainName(MAIN_NAME_VAT10);
        totalDto.setJanAmount(janTotal);
        totalDto.setFebAmount(febTotal);
        totalDto.setMarAmount(marTotal);
        totalDto.setAprAmount(aprTotal);
        totalDto.setMayAmount(mayTotal);
        totalDto.setJunAmount(junTotal);
        totalDto.setJulAmount(julTotal);
        totalDto.setAugAmount(augTotal);
        totalDto.setSepAmount(sepTotal);
        totalDto.setOctAmount(octTotal);
        totalDto.setNovAmount(novTotal);
        totalDto.setDecAmount(decTotal);
        totalDto.setStyle(STYLE_BLUE);

        // Add the new element to the list
        datas.add(insertIndex, totalDto);

        return datas;
    }	

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public ResponseEntity exportYearlyPaymentForGA(IncomePersonalYearlySearchDto searchDto) {
		ResponseEntity result = null;
		try {     
			YearlyPaymentDto yearData =  this.getYearlyPaymentForGA(searchDto);
			String templateName = "Thu_nhap_nam_GA.xlsx";
			
			String templatePath = servletContext
					.getRealPath(CommonConstant.REAL_PATH_TEMPLATE_EXCEL + "/" + templateName);
		
			ExportExcelUtil exportExcel = new ExportExcelUtil<>();			

			try (XSSFWorkbook xssfWorkbook = exportExcel.getXSSFWorkbook(templatePath);) {
				
				List<String> datas = new ArrayList<String>();
				datas.add("Văn phòng: " + searchDto.getGaCode() + " - " + searchDto.getGaName());
				datas.add("Giám đốc TĐL: " + searchDto.getAgentCode() + " - "+ searchDto.getAgentName());
				datas.add("Số TK: " + searchDto.getBankAccountNumber() + " - "+ searchDto.getBankAccountName());
				datas.add("Phân hạng: " + searchDto.getGaSegment());
				datas.add("Năm: " + searchDto.getYear());
				
				setDataHeaderWithoutTitle(xssfWorkbook, 0, "B3", datas);
				result = doExportYearlyPayment(xssfWorkbook, yearData, templateName, false, searchDto.getAgentCode(), searchDto.getGaCode() );
					
			} catch (Exception e) {
				logger.error("##doExport##", e);
			}
		
		} catch (Exception e) {
			logger.error("##exportYearlyPaymentForGA##", e);
		}
		return result;
	}
	
	@SuppressWarnings("rawtypes")
	public ResponseEntity doExportYearlyPayment(XSSFWorkbook xssfWorkbook, YearlyPaymentDto yearData, String templateName, boolean isAGMode, String agentCode, String gadCode) throws Exception {
		
		
		
		String datePattern = systemConfig.getConfig(SystemConfig.DATE_PATTERN);
		datePattern = "dd/MM/yyyy";
		
		// GA
		String tbl1_startRow =  "B13";
		String tbl2_startRow = "B30";
		if (isAGMode) { //AG 
			tbl1_startRow = "B10";
		}
		
		Map<String, String> mapColFormat = null;
		Map<String, CellStyle> mapColStyle = null;
		Map<String, Object> mapColDefaultValue = null;
		
		XSSFColor colorToTal = null;
		boolean isAllBorder = true;
		boolean exportFile = true;
		

		// create sheet of file excel
		XSSFSheet sxssfSheet  = xssfWorkbook.getSheetAt(0);
		

		if (sxssfSheet == null) {
			throw new Exception("Cannot find Sheet!!!");
		}
	
	
		// cellStyleDto
		CellStyleDto cellStyleDto = new CellStyleDto(xssfWorkbook, "Arial", isAllBorder, datePattern);

		// cellStyleDtoForTotal
		CellStyleDto cellStyleDtoForTotal = new CellStyleDto(cellStyleDto, colorToTal);

		// field of objDto
	
		CellReference landMark = new CellReference("A1");;
		if(yearData != null) {			
			Map<String, Field> mapFields = new HashMap<>();
			// monthPayment table 1
			if (CollectionUtils.isNotEmpty(yearData.getMonthPayment())) {
				List<ItemColsExcelDto> cols = new ArrayList<>();
				ImportExcelUtil.setListColumnExcel(IncomePersonalMonthYearlyEnum.class, cols);					
				
				landMark = new CellReference(tbl1_startRow);
				int startRow = landMark.getRow();

				List<MonthPaymentDto> monthPayment = yearData.getMonthPayment();
				Field[] fields = getPopulateFields(MonthPaymentDto.class);
				for (Field f : fields) {
					mapFields.put(f.getName().toUpperCase(), f);
				}
				
				int dataSize = monthPayment.size();
	
				for (int i = 0; i < monthPayment.size(); i++) {
					if (colorToTal != null && i == dataSize - 1) {
						// Do fill data
						fillDataMonthPayment(sxssfSheet, monthPayment, fields, cols, mapColFormat, mapColStyle, mapColDefaultValue,
								mapFields, startRow, i, cellStyleDtoForTotal, dataSize, true);
					} else {
						// Do fill data
						fillDataMonthPayment(sxssfSheet, monthPayment, fields, cols, mapColFormat, mapColStyle, mapColDefaultValue,
								mapFields, startRow, i, cellStyleDto, dataSize, false);
					}
					startRow += 1;
	
				}
			}
			// totalPayment table 2
			if (CollectionUtils.isNotEmpty(yearData.getTotalPayment())) {
				List<ItemColsExcelDto> cols = new ArrayList<>();
				ImportExcelUtil.setListColumnExcel(IncomePersonalTotalYearlyEnum.class, cols);
				
				landMark = new CellReference(tbl2_startRow);
				int startRow = landMark.getRow();

				List<TotalPaymentDto> totalPayment = yearData.getTotalPayment();
				Field[] fields = getPopulateFields(TotalPaymentDto.class);
				for (Field f : fields) {
					mapFields.put(f.getName().toUpperCase(), f);
				}
				
				int dataSize = totalPayment.size();
	
				for (int i = 0; i < totalPayment.size(); i++) {
					if (colorToTal != null && i == dataSize - 1) {
						// Do fill data
						fillDataTotalPayment(sxssfSheet, totalPayment, fields, cols, mapColFormat, mapColStyle, mapColDefaultValue,
								mapFields, startRow, i, cellStyleDtoForTotal, dataSize, true);
					} else {
						// Do fill data
						fillDataTotalPayment(sxssfSheet, totalPayment, fields, cols, mapColFormat, mapColStyle, mapColDefaultValue,
								mapFields, startRow, i, cellStyleDto, dataSize, false);
					}
					startRow += 1;
	
				}
			}
		}

		// set point view and active cell default
		sxssfSheet.setActiveCell(new CellAddress(landMark));
		sxssfSheet.getCTWorksheet().getSheetViews().getSheetViewArray(0).setTopLeftCell("A1");

		if (exportFile) {
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			SimpleDateFormat formatDateExport = new SimpleDateFormat("yyyy");
			String currentYear = formatDateExport.format(new Date());
			xssfWorkbook.write(out);
			// update to service
			String repo = systemConfig.getConfig(systemConfig.REPO_UPLOADED_MAIN);
			String path = systemConfig.getPhysicalPathById(repo, null); // path up service
			String pathFile = Paths.get(path, CommonConstant.PATH_FILE_EXPORT, CommonConstant.PATH_FILE_DLVN,
					templateName.replace(CommonConstant.TYPE_EXCEL, "") +  "_" + agentCode + "_" + currentYear + CommonConstant.TYPE_EXCEL)
					.toString();
			if (!isAGMode) { //GA
				pathFile = Paths.get(path, CommonConstant.PATH_FILE_EXPORT, CommonConstant.PATH_FILE_DLVN,
					templateName.replace(CommonConstant.TYPE_EXCEL, "")  + "_" + gadCode + "_" + agentCode +  "_" + currentYear + CommonConstant.TYPE_EXCEL)
					.toString();
			} 

			File file = new File(pathFile);
			try (OutputStream os = new FileOutputStream(file)) {
				xssfWorkbook.write(os);
			}

			String pathOut = (File.separator + pathFile).replace("\\", "/").replace(path, "").replace("//", "/");

			ByteArrayInputStream in = new ByteArrayInputStream(out.toByteArray());
			HttpHeaders headers = new HttpHeaders();
			headers.add(CommonConstant.CONTENT_DISPOSITION,
					CommonConstant.ATTCHMENT_FILENAME + templateName.replace(CommonConstant.TYPE_EXCEL, "") + "_"
							+ currentYear + CommonConstant.TYPE_EXCEL + "\"" + ";path=" + pathOut);

			headers.add("Access-Control-Expose-Headers", CommonConstant.CONTENT_DISPOSITION);

			return ResponseEntity.ok().eTag(pathOut).headers(headers)
					.contentType(MediaType.parseMediaType(CommonConstant.CONTENT_TYPE_EXCEL))
					.body(new InputStreamResource(in));
		}
		return null;
	}
	
	public void fillDataMonthPayment(XSSFSheet sxssfSheet, List<MonthPaymentDto> listData,
			Field[] headerFields, List<ItemColsExcelDto> cols, Map<String, String> mapColFormat,
			Map<String, CellStyle> mapColStyle, Map<String, Object> mapColDefaultValue, Map<String, Field> mapFields,
			int rowIndex, int dataIndex, CellStyleDto cellStyleDto, int dataSize, boolean fillColor)
			throws IllegalArgumentException, IllegalAccessException {

		XSSFRow row = sxssfSheet.createRow(rowIndex);

		MonthPaymentDto excelDto = listData.get(dataIndex);

		if (excelDto != null) {

			// set value to map
			//Field[] headerFields = populateFields(objDto);
			Map<String, Object> mapValueFields = new HashMap<String, Object>();
			for (Field field : headerFields) {
				if (!field.isAccessible()) {
					field.setAccessible(true);
				}
				mapValueFields.put(field.getName().toUpperCase(), field.get(excelDto));
			}
			// begin fill to cell

			for (ItemColsExcelDto col : cols) {

				// data type of field
				XSSFCell cell = row.createCell(col.getColIndex());

				// col.getColName());
				Field field = mapFields.get(col.getColName().toUpperCase());

				String typeFields = field.getType().getSimpleName().toUpperCase();
				DataType dataType = DataType.valueOf(typeFields);

				String formatType = null;
				if (null != mapColFormat && mapColFormat.size() != 0) {
					formatType = mapColFormat.get(col.getColName());
				}

				Object colValue = mapValueFields.get(col.getColName().toUpperCase());

				if (mapColDefaultValue != null && mapColDefaultValue.containsKey(col.getColName())) {
					if (null == mapColDefaultValue.get(col.getColName())
							|| EMP_STRING.equals(mapColDefaultValue.get(col.getColName()))) {
						cell.setCellValue(EMP_STRING);
					} else {
						String val = String.valueOf(mapColDefaultValue.get(col.getColName()));
						cell.setCellValue(val);
					}
				}

				switch (dataType) {
				case BIGDECIMAL:
					if (colValue != null) {
						BigDecimal valueBigdecimal = (BigDecimal) colValue;
						if (valueBigdecimal != null) {
							cell.setCellValue(valueBigdecimal.doubleValue());
							if (cellStyleDto.getIsFormatFinance() == 0) { // Normal
								// format
								// number
								if (valueBigdecimal.doubleValue() % 1 > 0) {
									// format "#,##0.00"
									cell.setCellStyle(cellStyleDto.getCellStyleRightBigDecimal1());
								} else {
									// format "#,##0"
									cell.setCellStyle(cellStyleDto.getCellStyleRightBigDecimal2());
								}
							} else { // Format number with Finance
										// money
								cell.setCellStyle(cellStyleDto.getCellStyleFinanceFormat());
							}

						}
					} else {
						cell.setCellStyle(cellStyleDto.getCellStyleTitle4());
					}

					break;
				case STRING:
					if (colValue != null) {
						cell.setCellValue(colValue.toString());

					}	
					cell.setCellStyle(cellStyleDto.getCellStyleTitle2());


					break;
				}
			} // END FOR 2

		}

	}
	
	public void fillDataTotalPayment(XSSFSheet sxssfSheet, List<TotalPaymentDto> listData,
			Field[] headerFields, List<ItemColsExcelDto> cols, Map<String, String> mapColFormat,
			Map<String, CellStyle> mapColStyle, Map<String, Object> mapColDefaultValue, Map<String, Field> mapFields,
			int rowIndex, int dataIndex, CellStyleDto cellStyleDto, int dataSize, boolean fillColor)
			throws IllegalArgumentException, IllegalAccessException {

		XSSFRow row = sxssfSheet.createRow(rowIndex);

		TotalPaymentDto excelDto = listData.get(dataIndex);

		if (excelDto != null) {

			// set value to map
			//Field[] headerFields = populateFields(objDto);
			Map<String, Object> mapValueFields = new HashMap<String, Object>();
			for (Field field : headerFields) {
				if (!field.isAccessible()) {
					field.setAccessible(true);
				}
				mapValueFields.put(field.getName().toUpperCase(), field.get(excelDto));
			}
			// begin fill to cell

			for (ItemColsExcelDto col : cols) {

				// data type of field
				XSSFCell cell = row.createCell(col.getColIndex());

				// col.getColName());
				Field field = mapFields.get(col.getColName().toUpperCase());

				String typeFields = field.getType().getSimpleName().toUpperCase();
				DataType dataType = DataType.valueOf(typeFields);

				String formatType = null;
				if (null != mapColFormat && mapColFormat.size() != 0) {
					formatType = mapColFormat.get(col.getColName());
				}

				Object colValue = mapValueFields.get(col.getColName().toUpperCase());

				if (mapColDefaultValue != null && mapColDefaultValue.containsKey(col.getColName())) {
					if (null == mapColDefaultValue.get(col.getColName())
							|| EMP_STRING.equals(mapColDefaultValue.get(col.getColName()))) {
						cell.setCellValue(EMP_STRING);
					} else {
						String val = String.valueOf(mapColDefaultValue.get(col.getColName()));
						cell.setCellValue(val);
					}
				}

				switch (dataType) {
				case BIGDECIMAL:
					if (colValue != null) {
						BigDecimal valueBigdecimal = (BigDecimal) colValue;
						if (valueBigdecimal != null) {
							cell.setCellValue(valueBigdecimal.doubleValue());
							if (cellStyleDto.getIsFormatFinance() == 0) { // Normal
								// format
								// number
								if (valueBigdecimal.doubleValue() % 1 > 0) {
									// format "#,##0.00"
									cell.setCellStyle(cellStyleDto.getCellStyleRightBigDecimal1());
								} else {
									// format "#,##0"
									cell.setCellStyle(cellStyleDto.getCellStyleRightBigDecimal2());
								}
							} else { // Format number with Finance
										// money
								cell.setCellStyle(cellStyleDto.getCellStyleFinanceFormat());
							}

						}
					} else {
						cell.setCellStyle(cellStyleDto.getCellStyleTitle4());
					}

					break;
				case STRING:
					if (colValue != null) {
						cell.setCellValue(colValue.toString());

					}
					cell.setCellStyle(cellStyleDto.getCellStyleTitle2());
					break;			
				}
			} // END FOR 2

		}

	}
	
	public void setDataHeaderWithoutTitle(XSSFWorkbook xssfWorkbook, int sheetNumber, String startRow, List<String> datas) {
	    XSSFSheet xssfSheet = xssfWorkbook.getSheetAt(sheetNumber);
	    CellReference landMark = new CellReference(startRow);
	    int dataSize = datas.size();
	    
	    if (datas != null && dataSize > 0) {
	        int startRowIndex = landMark.getRow();
	        int columnIndex = landMark.getCol();
	        
	        for (int i = 0; i < dataSize; i++) {
	            XSSFRow row = xssfSheet.getRow(startRowIndex + i);
	            if (row == null) {
	                row = xssfSheet.createRow(startRowIndex + i);
	            }
	            XSSFCell cell = row.getCell(columnIndex);
	            if (cell == null) {
	                cell = row.createCell(columnIndex);
	            }
	            cell.setCellValue(datas.get(i));
	        }
	    }
	}
	
	
}
