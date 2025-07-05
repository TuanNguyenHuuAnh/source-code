package vn.com.unit.ep2p.service.impl;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.nio.file.Paths;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.ServletContext;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.ObjectUtils;
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

import vn.com.unit.cms.core.module.ga.dto.IncomeGaDto;
import vn.com.unit.cms.core.module.ga.dto.IncomeMonthsGaDto;
import vn.com.unit.cms.core.module.ga.dto.param.IncomeMonthsParamGa;
import vn.com.unit.cms.core.module.ga.dto.param.IncomeParamGa;
import vn.com.unit.cms.core.module.ga.dto.param.IncomeWeeklyParamGa;
import vn.com.unit.cms.core.module.ga.dto.search.IncomeSearchGa;
import vn.com.unit.common.dto.AaGaOfficeDto;
import vn.com.unit.common.utils.CommonCollectionUtil;
import vn.com.unit.core.config.SystemConfig;
import vn.com.unit.core.res.ObjectDataRes;
import vn.com.unit.db.service.SqlManagerDb2Service;
import vn.com.unit.ep2p.admin.service.Db2ApiService;
import vn.com.unit.ep2p.enumdef.IncomeGaExportEnum;
import vn.com.unit.ep2p.enumdef.IncomeGaYearExportEnum;
import vn.com.unit.ep2p.service.IncomeGaService;
import vn.com.unit.imp.excel.constant.CommonConstant;
import vn.com.unit.imp.excel.dto.CellStyleDto;
import vn.com.unit.imp.excel.dto.ItemColsExcelDto;
import vn.com.unit.imp.excel.utils.ExportExcelUtil;
import vn.com.unit.imp.excel.utils.ImportExcelUtil;

@Service
@Transactional(transactionManager = "transactionManagerDb2", readOnly = true, rollbackFor = Exception.class)
public class IncomeGaServiceImpl implements IncomeGaService {

	@Autowired
	@Qualifier("sqlManageDb2Service")
	private SqlManagerDb2Service sqlManagerDb2Service;

	@Autowired
	private SystemConfig systemConfig;

	@Autowired
	private ServletContext servletContext;

	@Autowired
	private Db2ApiService db2ApiService;

	@Autowired
	private ApiIncomeReportServiceImpl apiIncomeReportServiceImpl;

	private static final Logger logger = LoggerFactory.getLogger(IncomeGaServiceImpl.class);

	private static final String STORE_INCOME_GA_MONTHS = "RPT_ODS.DS_SP_GET_MONTHLY_PAYMENT_BY_GA";
	private static final String STORE_INCOME_GA_YEAR = "RPT_ODS.DS_SP_GET_YEARLY_PAYMENT_BY_GA";
	private static final String STORE_INCOME_GA_YEAR_DETAIL = "RPT_ODS.DS_SP_GET_YEARLY_PAYMENT_DETAIL_BY_GA";
	private static final String STORE_INCOME_GA_DETAIL = "RPT_ODS.DS_SP_GET_MONTHLY_DETAIL_PAYMENT_BY_GA";
	
	private static final String STORE_DROPLIST_INCOME_GA_WEEKLY = "RPT_ODS.DS_SP_GET_DROPLIST_INCOME_WEEKLY_GA";
	private static final String STORE_LIST_INCOME_GA_WEEKLY = "RPT_ODS.DS_SP_GET_LIST_INCOME_WEEKLY_GA";

	@Override
	public SystemConfig getSystemConfig() {
		return systemConfig;
	}

	@Override
	public IncomeMonthsGaDto getListIncomeGaDetail(IncomeSearchGa searchDto) throws Exception {
		IncomeMonthsGaDto datas = new IncomeMonthsGaDto();
		Map<String, Map<String, List<IncomeMonthsGaDto>>> map = null;
		IncomeGaDto total = null;
		List<IncomeGaDto> amount = calStoreIncomeGa(searchDto);
		List<IncomeMonthsGaDto> detail = callStoreIncomeGadDetail(searchDto);

		if (detail != null && detail.size() > 0) {
			map = detail.stream().collect(Collectors.groupingBy(IncomeMonthsGaDto::getMainName,
					Collectors.groupingBy((IncomeMonthsGaDto::getSubName))));
		}
		if (amount != null && amount.size() > 0) {
			total = amount.get(0);
		}
		if (detail.size() > 0 && amount.size() > 0) {
			datas.setData(map);
		}
		return datas;
	}

	@Override
	public List<IncomeMonthsGaDto> callStoreIncomeGadDetail(IncomeSearchGa searchDto) {
		IncomeMonthsParamGa incomeParamGa = new IncomeMonthsParamGa();
		incomeParamGa.agentCode = searchDto.getAgentCode();
		incomeParamGa.orgCode = searchDto.getOrgCode() != "" ? searchDto.getOrgCode() : null;
		incomeParamGa.yyyyMMDD = searchDto.getPaymentPeriod();
		if (StringUtils.isEmpty(searchDto.getPaymentPeriod())) {
			incomeParamGa.yyyyMMDD = searchDto.getYear() + searchDto.getMonth();
		}
		sqlManagerDb2Service.call(STORE_INCOME_GA_DETAIL, incomeParamGa);
		// sqlManagerDb2Service.call("RPT_ODS.DS_SP_GET_MONTHLY_DETAIL_PAYMENT_BY_AGENT",
		// incomeParamGa);
		return incomeParamGa.lstData;
	}

	// call RPT_ODS.DS_SP_GET_MONTHLY_PAYMENT_BY_GA(129178,NULL,'20220331');
	@Override
	public IncomeGaDto getListIncomeGa(IncomeSearchGa searchDto) {
		List<IncomeGaDto> lstData = calStoreIncomeGa(searchDto);
		IncomeGaDto title = new IncomeGaDto();
		if (!lstData.isEmpty()) {
			title = lstData.get(0);
			title.setOfffice(title.getOrgCode() + " - " + title.getOfficeName());
			title.setManager(title.getGadCode() + " - " + title.getGadName());
			title.setNumberTdl(title.getBankAccountNumber() + " - " + title.getBankAccountName());
			title.setSegmentGa(title.getSegmentGa());
			title.setPayDate(title.getPayDate());
		}
		return title;
	}

	public List<IncomeGaDto> calStoreIncomeGa(IncomeSearchGa searchDto) {
		IncomeParamGa incomeParamGa = new IncomeParamGa();
		incomeParamGa.agentCode = searchDto.getAgentCode();
		incomeParamGa.orgCode = searchDto.getOrgCode();
		incomeParamGa.yyyyMMDD = searchDto.getPaymentPeriod();
		sqlManagerDb2Service.call(STORE_INCOME_GA_MONTHS, incomeParamGa);
		return incomeParamGa.lstData;
	}

	// call RPT_ODS.DS_SP_GET_YEARLY_PAYMENT_DETAIL_BY_GA(111541,'GAHUE','202203')
	// -detail
	// call RPT_ODS.DS_SP_GET_YEARLY_PAYMENT_BY_GA(111541,'GAHUE','2022003');
	// -totalsk
	@Override
	public List<IncomeGaDto> getListIncomeGaYear(IncomeSearchGa searchDto) {
		List<IncomeGaDto> lstData = new ArrayList<>();
		IncomeParamGa incomeParamGa = new IncomeParamGa();
		incomeParamGa.agentCode = searchDto.getAgentCode();
		incomeParamGa.orgCode = searchDto.getOrgCode();
		incomeParamGa.yyyyMMDD = searchDto.getYear() + searchDto.getMonth();
		if (searchDto.isDetail() == true) {
			sqlManagerDb2Service.call(STORE_INCOME_GA_YEAR_DETAIL, incomeParamGa);
			lstData = incomeParamGa.lstData;
			IncomeGaDto dto = new IncomeGaDto();
			dto.setManager(lstData.get(0).getGadCode() + " - " + searchDto.getAgentCode());
			dto.setOfffice(lstData.get(0).getOrgCode() + " - " + lstData.get(0).getOfficeName());
			dto.setNumberTdl(lstData.get(0).getBankAccountNumber() + " - " + lstData.get(0).getBankAccountName());
			dto.setSegmentGa(lstData.get(0).getSegmentGa());
			lstData.add(0, dto);
		} else if (searchDto.isTotal() == true) {
			sqlManagerDb2Service.call(STORE_INCOME_GA_YEAR, incomeParamGa);
			lstData = incomeParamGa.lstData;
		}
		return lstData;
	}

	private List<IncomeMonthsGaDto> formatData(List<IncomeMonthsGaDto> data) {
		List<IncomeMonthsGaDto> dataAll = new ArrayList<>();

		List<IncomeMonthsGaDto> main = data.stream().filter(x -> StringUtils.isEmpty(x.getSubCode()))
				.collect(Collectors.toList());

		for (IncomeMonthsGaDto ls1 : main) {
			dataAll.add(ls1);
			if (ObjectUtils.isEmpty(ls1)) {
				break;
			}
			List<IncomeMonthsGaDto> lstSubs = data.stream()
					.filter(x -> StringUtils.isNotEmpty(x.getSubCode()) && StringUtils.isEmpty(x.getItemCode())
							&& StringUtils.endsWithIgnoreCase(x.getMainCode(), ls1.getMainCode()))
					.collect(Collectors.toList());

			for (IncomeMonthsGaDto sub : lstSubs) {
				sub.setMainName(null);
				sub.setItemName(null);
				dataAll.add(sub);
				List<IncomeMonthsGaDto> lstDetails = data.stream()
						.filter(x -> StringUtils.isNotEmpty(x.getItemCode())
								&& StringUtils.endsWithIgnoreCase(x.getMainCode(), ls1.getMainCode())
								&& StringUtils.endsWithIgnoreCase(x.getSubCode(), sub.getSubCode()))
						.collect(Collectors.toList());
				for (IncomeMonthsGaDto detail : lstDetails) {
					detail.setMainName(null);
					detail.setSubName(null);
					DecimalFormatSymbols symbols = new DecimalFormatSymbols();
					symbols.setGroupingSeparator(',');
					symbols.setDecimalSeparator('.');
					String pattern = "#,##0.0#";
					if (!isNullOrZero(detail.getAmount()) && detail.getAmount().doubleValue() % 1 != 0) {
						// format "#,##0.00"
						pattern = "#,##0.0000";
					} else {
						// format "#,##0"
						pattern = "#,##0";
					}
					DecimalFormat decimalFormat = new DecimalFormat(pattern, symbols);
					decimalFormat.setParseBigDecimal(true);
					String amount = "";
					if (!isNullOrZero(detail.getAmount())) {
						amount = decimalFormat.format(detail.getAmount());
						detail.setItemName(detail.getItemName().concat(": ").concat(amount.toString()));
					}
					detail.setAmount(null);
					dataAll.add(detail);
				}

			}
		}

		return dataAll;
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
	public ResponseEntity exportListIncomeGa(IncomeSearchGa searchDto, Locale locale) {
		ResponseEntity res = null;
		try {			
			//List<AaGaOfficeDto> gaInfo = db2ApiService.getListOfficeByGad(searchDto.getAgentCode(),
			//		searchDto.getOrgCode(), null);
			List<AaGaOfficeDto> gaInfo = db2ApiService.getListOfficeByGadForPayment(searchDto.getAgentCode(), searchDto.getOrgCode(), searchDto.getPaymentPeriod(), false);
			List<IncomeMonthsGaDto> data = callStoreIncomeGadDetail(searchDto);
			String datePattern = getSystemConfig().getConfig(SystemConfig.DATE_PATTERN);

			datePattern = "dd/MM/yyyy";
			String templateName = "Thu_nhap_thang_GA.xlsx";
			String templatePath = servletContext
					.getRealPath(CommonConstant.REAL_PATH_TEMPLATE_EXCEL + "/" + templateName);
			String startRow = "A9";

			List<IncomeMonthsGaDto> lstData = formatData(data);

			List<ItemColsExcelDto> cols = new ArrayList<>();
			ImportExcelUtil.setListColumnExcel(IncomeGaExportEnum.class, cols);
			ExportExcelUtil exportExcel = new ExportExcelUtil<>();
			Map<String, String> mapColFormat = null;
			Map<String, Object> setMapColDefaultValue = null;
			try (XSSFWorkbook xssfWorkbook = exportExcel.getXSSFWorkbook(templatePath);) {

				Map<String, CellStyle> mapColStyle = mapStyle(xssfWorkbook, 0,lstData.size()+7);

				if (CollectionUtils.isNotEmpty(gaInfo)) {
					insertTitle(0, xssfWorkbook, gaInfo.get(0), searchDto);
				}



				res = doExportExcelHeaderWithColFormatRestUpServiceNew(xssfWorkbook, 0, null, lstData,
						IncomeMonthsGaDto.class, cols, datePattern, startRow, mapColFormat, mapColStyle,
						setMapColDefaultValue, null, false, templateName, true);

			} catch (Exception e) {
				logger.error("##ExportList##", e);
				throw new Exception(e.getMessage());
			}
		} catch (Exception e) {
			logger.error("exportListData: ", e);
		}
		return res;
	}

	@Override
	public ResponseEntity exportListIncomeGaYear(IncomeSearchGa searchDto, Locale locale) {
		ResponseEntity res = null;
		try {
			List<IncomeGaDto> detail = getListIncomeGaYear(searchDto);
			detail.remove(0);
			for (IncomeGaDto ls : detail) {
				if (StringUtils.endsWithIgnoreCase(ls.getType(), "GROSS")) {
					ls.setType("Thu nhập trước thuế [gross]");
				}
				if (StringUtils.endsWithIgnoreCase(ls.getType(), "DEDUCT")) {
					ls.setType("Thuế");
				}
				if (StringUtils.endsWithIgnoreCase(ls.getType(), "PAYMENT")) {
					ls.setType("Thực lãnh ");
				}
			}
			String datePattern = getSystemConfig().getConfig(SystemConfig.DATE_PATTERN);

			datePattern = "dd/MM/yyyy";
			String templateName = "IncomeGaYear.xlsx";
			String templatePath = servletContext
					.getRealPath(CommonConstant.REAL_PATH_TEMPLATE_EXCEL + "/" + templateName);
			String startRow = "A9";

			List<IncomeGaDto> lstdata = detail;
			IncomeGaDto data = lstdata.get(0);

			List<ItemColsExcelDto> cols = new ArrayList<>();
			ImportExcelUtil.setListColumnExcel(IncomeGaYearExportEnum.class, cols);
			ExportExcelUtil exportExcel = new ExportExcelUtil<>();
			Map<String, String> mapColFormat = null;
			Map<String, Object> setMapColDefaultValue = null;
			try (XSSFWorkbook xssfWorkbook = exportExcel.getXSSFWorkbook(templatePath);) {
				Map<String, CellStyle> mapColStyle = null;

				insertTitleYear(0, xssfWorkbook, data, searchDto);
				String repo = systemConfig.getConfig(systemConfig.REPO_UPLOADED_MAIN);
				String path = systemConfig.getPhysicalPathById(repo, null); // path up service
				res = exportExcel.doExportExcelHeaderWithColFormatRestUpService(xssfWorkbook, 0, locale, lstdata,
						IncomeGaDto.class, cols, datePattern, startRow, mapColFormat, mapColStyle,
						setMapColDefaultValue, null, true, templateName, true, path);

			} catch (Exception e) {
				logger.error("##ExportList##", e);
				throw new Exception(e.getMessage());
			}
		} catch (Exception e) {
			logger.error("exportListData: ", e);
		}
		return res;
	}

	public void insertTitle(int sheet, XSSFWorkbook xssfWorkbook, AaGaOfficeDto data, IncomeSearchGa searchDto) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		SimpleDateFormat sdf2 = new SimpleDateFormat("MM_yyyy");
		String resultDate;
		try {
			Date paymentDate = sdf.parse(searchDto.getPaymentPeriod() + "01");
			resultDate = sdf2.format(paymentDate);
		} catch (Exception e) {
			logger.error(e.getMessage());
			resultDate = searchDto.getPaymentPeriod();
		}
		XSSFSheet xssfSheet = xssfWorkbook.getSheetAt(sheet);
		xssfSheet.getRow(0).getCell(0).setCellValue("Văn phòng : " + data.getTitle());
		xssfSheet.getRow(1).getCell(0)
				.setCellValue("Giám đốc TĐL: " + data.getGadCode().toString().concat(" - ").concat(data.getGadName()));
		xssfSheet.getRow(2).getCell(0).setCellValue(
				"Số tài khoản TĐL: " + data.getBankAccountNumber().concat(" - ").concat(data.getBankAccountName()));
		xssfSheet.getRow(3).getCell(0).setCellValue("Phân hạng: " + data.getSegmentGa());
		xssfSheet.getRow(4).getCell(0).setCellValue("Kỳ thanh toán : " + resultDate);

	}

	public void insertTitleYear(int sheet, XSSFWorkbook xssfWorkbook, IncomeGaDto data, IncomeSearchGa searchDto) {
		XSSFSheet xssfSheet = xssfWorkbook.getSheetAt(sheet);
		xssfSheet.getRow(2).getCell(0).setCellValue("Văn phòng: " + data.getOrgCode() + " - " + data.getOfficeName());
		xssfSheet.getRow(3).getCell(0)
				.setCellValue("Giám đốc TĐL: " + data.getGadCode() + " - " + searchDto.getAgentCode());
		xssfSheet.getRow(4).getCell(0)
				.setCellValue("Số tài khoản TĐL: " + data.getBankAccountNumber() + " - " + data.getBankAccountName());
		xssfSheet.getRow(5).getCell(0).setCellValue("Phân hạng: " + data.getSegmentGa());
		searchDto.setDetail(false);
		searchDto.setTotal(true);
		List<IncomeGaDto> total = getListIncomeGaYear(searchDto);
		IncomeGaDto totalDto = total.get(0);
		xssfSheet.getRow(12).getCell(1).setCellValue(totalDto.getBonus());
		xssfSheet.getRow(13).getCell(1).setCellValue(totalDto.getAllowancePretax());
		xssfSheet.getRow(14).getCell(1).setCellValue(totalDto.getGrossY());
		xssfSheet.getRow(15).getCell(1).setCellValue(totalDto.getTotalRecallInYear());
		xssfSheet.getRow(16).getCell(1).setCellValue(totalDto.getTotalHould());

	}

	public Map<String, CellStyle> mapStyle(XSSFWorkbook xssfWorkbook, int sheet,int total) {
		// dataFormat
		DataFormat dataFormat = xssfWorkbook.createDataFormat();
		Font fontMain = xssfWorkbook.createFont();
		fontMain.setFontName("Arial");
		fontMain.setFontHeight((short) 12);
		fontMain.setFontHeightInPoints((short) 12);
		fontMain.setBold(true);

		Font fontSub = xssfWorkbook.createFont();
		fontSub.setFontName("Arial");
		fontSub.setFontHeightInPoints((short) 9);

		Font fontItem = xssfWorkbook.createFont();
		fontItem.setFontName("Arial");
		fontItem.setFontHeightInPoints((short) 9);

		Font fontAmount = xssfWorkbook.createFont();
		fontAmount.setFontName("Arial");
		fontAmount.setFontHeightInPoints((short) 11);
		fontAmount.setBold(true);

		CellStyle cellStyleMain = xssfWorkbook.createCellStyle();
		cellStyleMain.setFont(fontMain);
		cellStyleMain.setAlignment(HorizontalAlignment.LEFT);
		cellStyleMain.setBorderTop(BorderStyle.THIN);
		cellStyleMain.setBorderLeft(BorderStyle.THIN);

		cellStyleMain.setWrapText(true);

		CellStyle cellStyleSub = xssfWorkbook.createCellStyle();
		cellStyleSub.setFont(fontSub);
		cellStyleSub.setAlignment(HorizontalAlignment.LEFT);
		cellStyleSub.setBorderTop(BorderStyle.THIN);
		cellStyleSub.setWrapText(false);

		CellStyle cellStyleItem = xssfWorkbook.createCellStyle();
		cellStyleItem.setFont(fontItem);
		cellStyleItem.setAlignment(HorizontalAlignment.LEFT);
		cellStyleItem.setBorderTop(BorderStyle.THIN);
		cellStyleItem.setWrapText(false);

		Map<String, CellStyle> mapColStyle = new HashMap<>();

		mapColStyle.put("MAINNAME", cellStyleMain);
		mapColStyle.put("SUBNAME", cellStyleSub);
		mapColStyle.put("ITEMNAME", cellStyleItem);

		CellStyle cellStyleRight = xssfWorkbook.createCellStyle();
		cellStyleRight.setAlignment(HorizontalAlignment.RIGHT);
		cellStyleRight.setFont(fontAmount);
		cellStyleRight.setDataFormat(dataFormat.getFormat("#,##0"));
		cellStyleRight.setBorderTop(BorderStyle.THIN);
		cellStyleRight.setBorderRight(BorderStyle.THIN);

		cellStyleRight.setWrapText(false);
		mapColStyle.put("AMOUNT", cellStyleRight);

		CellStyle cellEnd = xssfWorkbook.createCellStyle();
		cellEnd.setBorderTop(BorderStyle.THIN);

		XSSFRow row4 = xssfWorkbook.getSheetAt(0).getRow(total);
		if(row4 == null) row4 = xssfWorkbook.getSheetAt(0).createRow(total);

		for (int i = 0; i < 4; i++) {
			XSSFCell cell4 = row4.getCell(i);
			if(cell4 == null) cell4 = row4.createCell(i);
			cell4.setCellStyle(cellEnd);
		}
		return mapColStyle;
	}

	@SuppressWarnings("rawtypes")
	public ResponseEntity doExportExcelHeaderWithColFormatRestUpServiceNew(XSSFWorkbook xssfWorkbook,
			Integer sheetIndex, Locale locale, List<IncomeMonthsGaDto> listData, Class<IncomeMonthsGaDto> objDto,
			List<ItemColsExcelDto> cols, String datePattern, String cellReference, Map<String, String> mapColFormat,
			Map<String, CellStyle> mapColStyle, Map<String, Object> mapColDefaultValue, XSSFColor colorToTal,
			boolean isAllBorder, String templateName, boolean exportFile) throws Exception {

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
//            if (listData.size() > 0) {
			int dataSize = listData.size();
			IncomeMonthsGaDto data = null;
			for (int i = 0; i < listData.size(); i++) {
				data = listData.get(i);
				if (data.getMainName() != null) {
					data.setMainName(data.getMainName().replace("<br/>", "                                        "));
				}
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
//            }
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

			String pathOut = (File.separator + Paths.get( CommonConstant.PATH_FILE_EXPORT, CommonConstant.PATH_FILE_DLVN,
					templateName.replace(CommonConstant.TYPE_EXCEL, "") + "_" + currentDate + CommonConstant.TYPE_EXCEL)
					.toString()).replace("\\", "/");

			ByteArrayInputStream in = new ByteArrayInputStream(out.toByteArray());
			HttpHeaders headers = new HttpHeaders();
			headers.add(CommonConstant.CONTENT_DISPOSITION,
					CommonConstant.ATTCHMENT_FILENAME + templateName.replace(CommonConstant.TYPE_EXCEL, "") + "_"
							+ currentDate + CommonConstant.TYPE_EXCEL + "\"" + ";path=" + pathOut);

			headers.add("Access-Control-Expose-Headers", CommonConstant.CONTENT_DISPOSITION);

			return ResponseEntity
					.ok()
					.eTag(pathOut)
					.headers(headers)
					.contentType(MediaType.parseMediaType(CommonConstant.CONTENT_TYPE_EXCEL))
					.body(new InputStreamResource(in));
		}
		return null;
	}

	private Field[] populateFields(Class<IncomeMonthsGaDto> cls) {
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

	public void fillDataForCell(XSSFSheet sxssfSheet, List<IncomeMonthsGaDto> listData, Class<IncomeMonthsGaDto> objDto,
			List<ItemColsExcelDto> cols, Map<String, String> mapColFormat, Map<String, CellStyle> mapColStyle,
			Map<String, Object> mapColDefaultValue, Map<String, Field> mapFields, int rowIndex, int dataIndex,
			CellStyleDto cellStyleDto, int dataSize, boolean fillColor)
			throws IllegalArgumentException, IllegalAccessException {

		XSSFRow row = sxssfSheet.createRow(rowIndex);

		IncomeMonthsGaDto excelDto = listData.get(dataIndex);

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
							if (StringUtils.isNotBlank(excelDto.getMainName())
									&& StringUtils.isBlank(excelDto.getSubName())
									&& StringUtils.isBlank(excelDto.getItemName())) {
//			                        cell.setCellStyle(cellStyleDto.getCellStyleTitle5());
								cell.setCellStyle(mapColStyle.get(col.getColName()));
							} else {
								cell.setCellStyle(cellStyleDto.getCellStyleFinanceFormat());
							}

//	                            if(StringUtils.equalsIgnoreCase(excelDto.getMainDirectory(),"Số dư tháng trước mang sang")) {
//			                        cell.setCellStyle(cellStyleDto.getCellStyleTitle5());	
//		                        } else if(StringUtils.isNotBlank(excelDto.getMainDirectory()) && StringUtils.isBlank(excelDto.getSubDirectory()) &&  StringUtils.isBlank(excelDto.getDetailDirectory())) {
//			                        cell.setCellStyle(cellStyleDto.getCellStyleTitle3());	
//		                        } else {
//			                        cell.setCellStyle(cellStyleDto.getCellStyleTitle4());	
//		                        }	

						}
					} else {
						if (StringUtils.isNotBlank(excelDto.getMainName()) && StringUtils.isBlank(excelDto.getSubName())
								&& StringUtils.isBlank(excelDto.getItemName())) {
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

//	                        	short format = cellStyleDto.getSxssfWorkbook().getCreationHelper().createDataFormat().getFormat(formatType);
//	                        	cellStyleDto.getCellStyleDateCenter().setDataFormat(format);

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
					if (StringUtils.isNotBlank(excelDto.getMainCode()) && StringUtils.isBlank(excelDto.getSubName())
							&& StringUtils.isBlank(excelDto.getItemName())) {
						cell.setCellStyle(mapColStyle.get(col.getColName()));
					} else {
						cell.setCellStyle(cellStyleDto.getCellStyleLeft());
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
//	        }
	}

	@Override
	public List<IncomeGaDto> getDroplistIncomeWeeklyGA(IncomeSearchGa searchDto) {
		IncomeWeeklyParamGa incomeParamGa = new IncomeWeeklyParamGa();
		incomeParamGa.agentCode = searchDto.getAgentCode();
		incomeParamGa.paymentPeriod = searchDto.getPaymentPeriod(); // only YYYYMM
		sqlManagerDb2Service.call(STORE_DROPLIST_INCOME_GA_WEEKLY, incomeParamGa);
		return incomeParamGa.lstData;
	}

	@Override
	public ObjectDataRes<IncomeGaDto> getListIncomeWeeklyGA(IncomeSearchGa searchDto) {
		ObjectDataRes<IncomeGaDto> resulLst = new ObjectDataRes<>();
		List<IncomeGaDto> list = new ArrayList<IncomeGaDto>();
		try {
			IncomeParamGa incomeParamGa = new IncomeParamGa();
			incomeParamGa.agentCode = searchDto.getAgentCode();
			incomeParamGa.orgCode = searchDto.getOrgCode();
			incomeParamGa.yyyyMMDD = searchDto.getPaymentPeriod() + "01"; // format YYYYMMDD
			sqlManagerDb2Service.call(STORE_LIST_INCOME_GA_WEEKLY, incomeParamGa);
			
			list = (CommonCollectionUtil.isNotEmpty(incomeParamGa.lstData) ? incomeParamGa.lstData : list);
			IncomeGaDto dto = new IncomeGaDto();
			dto.setTypePaymentName("Tổng cộng");
			dto.setPayAmount(list.stream().mapToInt(x -> x.getPayAmount()).sum());
			list.add(dto);
			resulLst.setDatas(list);
		} catch (Exception e) {
			logger.error("##getListIncomeWeeklyGA##", e);
		}
		return resulLst;
	}

	@Override
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public ResponseEntity exportIncomeWeeklyGA(List<IncomeGaDto> resultDto, String type, BigDecimal amount,
			String fileName, String row, Class enumDto, Class className, List<String> lstInfor,
			String startInfo, String appendExportFileName) {
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

				int countDetails = resultDto.size();

				if (!type.equals("")) {
					writeTotalData(type, amount, xssfWorkbook, 0, countDetails);
				}
				

				setDataHeaderWithoutTitle(xssfWorkbook, 0, startInfo, lstInfor);
				String repo = systemConfig.getConfig(systemConfig.REPO_UPLOADED_MAIN);
				String path = systemConfig.getPhysicalPathById(repo, null); // path up service
				res = doExportIncomeWeeklyGA(xssfWorkbook, 0, null, resultDto,
						className, cols, datePattern, startRow, mapColFormat, mapColStyle, setMapColDefaultValue, null,
						true, templateName, true, path, appendExportFileName, exportExcel);
			} catch (Exception e) {
				logger.error("exportListData: ", e);
			}
		} catch (Exception e) {
			logger.error("exportListData: ", e);
		}
		return res;
	}
	
	private void writeTotalData(String type, BigDecimal amount, XSSFWorkbook xssfWorkbook, int sheetNumber, int countDetails) {

		XSSFSheet xssfSheet = xssfWorkbook.getSheetAt(0);
		XSSFRow rowl = xssfSheet.createRow(countDetails + 11);

		xssfSheet.addMergedRegion(new CellRangeAddress(countDetails + 11, countDetails + 11, 1, 2));

		CellStyle style = xssfWorkbook.createCellStyle();

		style.setBorderBottom(BorderStyle.THIN);
		style.setBorderRight(BorderStyle.THIN);
		style.setBorderLeft(BorderStyle.THIN);
		style.setBorderTop(BorderStyle.THIN);

		for (int i = 1; i < 4; i++) {
			XSSFCell cell = rowl.createCell(i);
			cell.setCellStyle(style);
		}

		Font font = xssfWorkbook.createFont();
		font.setBold(true);
		style.setFont(font);
		style.setAlignment(HorizontalAlignment.CENTER);

		XSSFCell cellTotal = rowl.getCell(1);
		cellTotal.setCellStyle(style);
		cellTotal.setCellValue(type);

		XSSFRow row = xssfSheet.getRow(countDetails + 11);
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
	
	
	@SuppressWarnings("rawtypes")
    private ResponseEntity doExportIncomeWeeklyGA(XSSFWorkbook xssfWorkbook, Integer sheetIndex, Locale locale,
            List<IncomeGaDto> listData, Class objDto, List<ItemColsExcelDto> cols, String datePattern, String cellReference,
            Map<String, String> mapColFormat, Map<String, CellStyle> mapColStyle,
            Map<String, Object> mapColDefaultValue, XSSFColor colorToTal, boolean isAllBorder,
            String templateName, boolean exportFile, String path, String appendExportFileName,ExportExcelUtil exportExcel) throws Exception {
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
        CellStyleDto cellStyleDto = new CellStyleDto(xssfWorkbook, "Times New Roman", isAllBorder, datePattern);

        // cellStyleDtoForTotal
        CellStyleDto cellStyleDtoForTotal = new CellStyleDto(cellStyleDto, colorToTal);

        // field of objDto
        Map<String, Field> mapFields = new HashMap<>();
        Field[] fields = populateFields(objDto);
        for (Field f : fields) {
            mapFields.put(f.getName().toUpperCase(), f);
        }

        if (listData != null) {
            if (listData.size() > 0) {
                int dataSize = listData.size();
                for (int i = 0; i < listData.size(); i++) {
                    if (colorToTal != null && i == dataSize - 1) {
                        // Do fill data
                    	exportExcel.fillDataForCell(sxssfSheet, listData, objDto, cols, mapColFormat, mapColStyle, mapColDefaultValue, mapFields, startRow, i,
                                cellStyleDtoForTotal, dataSize, true);
                    } else {
                        // Do fill data
                    	exportExcel.fillDataForCell(sxssfSheet, listData, objDto, cols, mapColFormat, mapColStyle, mapColDefaultValue, mapFields, startRow, i,
                                cellStyleDto, dataSize, false);
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
            xssfWorkbook.write(out);
            //update to service
    		
            String pathFile = Paths.get(path,CommonConstant.PATH_FILE_EXPORT, CommonConstant.PATH_FILE_DLVN,
            		templateName.replace(CommonConstant.TYPE_EXCEL, "") + appendExportFileName  + CommonConstant.TYPE_EXCEL).toString();
            
            File file = new File(pathFile);
            try (OutputStream os = new FileOutputStream(file)) {
                xssfWorkbook.write(os);
            }
            
            String pathOut = (File.separator + Paths.get(CommonConstant.PATH_FILE_EXPORT, CommonConstant.PATH_FILE_DLVN
            		, templateName.replace(CommonConstant.TYPE_EXCEL, "") + appendExportFileName  + CommonConstant.TYPE_EXCEL).toString()).replace("\\", "/");
            
            ByteArrayInputStream in = new ByteArrayInputStream(out.toByteArray());               
            HttpHeaders headers = new HttpHeaders();
            headers.add(CommonConstant.CONTENT_DISPOSITION, CommonConstant.ATTCHMENT_FILENAME + templateName.replace(CommonConstant.TYPE_EXCEL, "") + "_"
            		+ appendExportFileName  + CommonConstant.TYPE_EXCEL + "\""+";path="+pathOut);
            
            headers.add("Access-Control-Expose-Headers", CommonConstant.CONTENT_DISPOSITION);
            
             return ResponseEntity
                        .ok()
                        .eTag(pathOut)
                        .headers(headers)
                        .contentType(MediaType.parseMediaType(CommonConstant.CONTENT_TYPE_EXCEL))
                        .body(new InputStreamResource(in));
        }
        return null;
    }
}
