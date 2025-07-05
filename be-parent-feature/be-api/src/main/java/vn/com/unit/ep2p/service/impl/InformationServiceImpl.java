package vn.com.unit.ep2p.service.impl;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.*;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.sl.usermodel.VerticalAlignment;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.DataFormat;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.util.CellAddress;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.CellReference;
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
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import vn.com.unit.cms.core.dto.CommonSearchWithPagingDto;
import vn.com.unit.cms.core.module.emulate.dto.EmulateResultSearchResultDto;
import vn.com.unit.cms.core.module.emulate.dto.EmulateSearchDto;
import vn.com.unit.cms.core.module.information.dto.*;
import vn.com.unit.common.dto.Select2Dto;
import vn.com.unit.common.utils.CommonCollectionUtil;
import vn.com.unit.core.res.ObjectDataRes;
import vn.com.unit.db.service.SqlManagerDb2Service;
import vn.com.unit.core.config.SystemConfig;
import vn.com.unit.ep2p.enumdef.DebtInforExportEnum;
import vn.com.unit.ep2p.enumdef.LetterResultGAEnum;
import vn.com.unit.ep2p.service.ApiAgentDetailService;
import vn.com.unit.ep2p.service.InformationService;
import vn.com.unit.ep2p.service.ParseJsonToParamSearchService;
import vn.com.unit.imp.excel.constant.CommonConstant;
import vn.com.unit.imp.excel.dto.CellStyleDto;
import vn.com.unit.imp.excel.dto.ItemColsExcelDto;
import vn.com.unit.imp.excel.utils.ExportExcelUtil;
import vn.com.unit.imp.excel.utils.ImportExcelUtil;

import org.apache.poi.ss.usermodel.FillPatternType;
import vn.com.unit.cms.core.module.agent.dto.CmsAgentDetail;

@Service
@Transactional(transactionManager = "transactionManagerDb2", readOnly = true, rollbackFor = Exception.class)
public class InformationServiceImpl implements InformationService {
	@Autowired
	private ServletContext servletContext;

	@Autowired
	private SystemConfig systemConfig;

	@Autowired
	@Qualifier("sqlManageDb2Service")
	private SqlManagerDb2Service sqlManagerDb2Service;

	@Autowired
	ParseJsonToParamSearchService parseJsonToParamSearchService;

	@Autowired
	ApiAgentDetailService apiAgentDetailService;

	private Logger logger = LoggerFactory.getLogger(getClass());

	private static final String STORE_DEBT_INFORMATION = "RPT_ODS.DS_SP_GET_DEBT_BY_AGENT";
	private static final String STORE_DEBT_INFORMATION_GA = "RPT_ODS.DS_SP_GET_DEBT_BY_GA";
	private static final String STORE_GA_INFORMATION = "RPT_ODS.DS_SP_GET_INFOMATION_GA";
	private static final String STORE_OFFICE_GAD = "RPT_ODS.";
	private static final String STORE_CHALLENGE_LETTER = "RPT_ODS.";
	private static final String STORE_RESULT_CHALLENGE_LETTER = "RPT_ODS.";

	@Override
	public ObjectDataRes<DebtInformationDto> getListDebtInformation(DebtInformationSearchDto searchDto) {
		searchDto.setFunctionCode("DEBT_INFORMATION");
		ObjectMapper mapper = new ObjectMapper();
		String stringJsonParam = "";
		try {
			stringJsonParam = mapper.writeValueAsString(searchDto);
		} catch (JsonProcessingException e) {
			logger.error("Exception ", e);
			logger.error("getDebtInformation: ", e);
		}
		CommonSearchWithPagingDto common = parseJsonToParamSearchService.getSearchCondition(stringJsonParam, "DEBT_INFORMATION");
		ObjectDataRes<DebtInformationDto> resultData = callStoreDebtInfor(searchDto, common);		
		return resultData;
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

	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public ResponseEntity exportListDebtInformation( HttpServletResponse response, Locale locale, DebtInformationSearchDto searchDto) {
		ResponseEntity res = null;
		try {
			searchDto.setPage(null);
			searchDto.setPageSize(null);
			ObjectDataRes<DebtInformationDto> resultData= getListDebtInformation(searchDto);
            String datePattern = systemConfig.getConfig(SystemConfig.DATE_PATTERN);
            datePattern = "dd/MM/yyyy";
            String templateName = "Thong_tin_no.xlsx";
    		String templatePath = servletContext.getRealPath(CommonConstant.REAL_PATH_TEMPLATE_EXCEL + "/" + templateName);
    		String startRow = "A8";
            List<DebtInformationDto> lstdata = resultData.getDatas();
         
            
            List<ItemColsExcelDto> cols = new ArrayList<>();
			ImportExcelUtil.setListColumnExcel(DebtInforExportEnum.class, cols);
			ExportExcelUtil exportExcel = new ExportExcelUtil<>();
			Map<String, String> mapColFormat = null;// setMapColFormat();
			Map<String, Object> setMapColDefaultValue = null;// setMapColDefaultValue();
            // do export
            try (XSSFWorkbook xssfWorkbook = exportExcel.getXSSFWorkbook(templatePath);) {
            	  if(lstdata != null) {
                      lstdata.stream().forEach(x -> x.setDebtMoneyDto(BigDecimal.valueOf(x.getDebtMoney())));
		            	int maxRow = lstdata.size();
		            	DebtInformationDto total = lstdata.get(maxRow-1);
		            	lstdata.remove(maxRow-1);
		
		            	writeTotalData(total,xssfWorkbook,0,maxRow);
            	  }
            	
				Map<String, CellStyle> mapColStyle = null;// setMapColStyle(xssfWorkbook);
				
				CmsAgentDetail infoAgent = apiAgentDetailService.getCmsAgentDetailByUsername(searchDto.getAgentCode());
				List<String> lstInfo = Arrays.asList(
			            "Đại lý: " + infoAgent.getAgentCode() + " - " + infoAgent.getAgentType() + " " + infoAgent.getFullName(),
			            "Mã số thuế: "+infoAgent.getIdPersonalIncomeTax(),
			            "Số TK: " + infoAgent.getBankAccountNumber() + " - " + infoAgent.getBankAccountName()
			            );
				setDataHeaderToXSSFWorkbookSheet(xssfWorkbook, 0, null, null, "A3", lstInfo);

				String repo = systemConfig.getConfig(systemConfig.REPO_UPLOADED_MAIN);
				String path = systemConfig.getPhysicalPathById(repo, null); //path up service
            	res =  exportExcel.doExportExcelHeaderWithColFormatRestUpService(xssfWorkbook, 0, null, lstdata, DebtInformationDto.class
            			, cols, datePattern, startRow, mapColFormat, mapColStyle,
						setMapColDefaultValue, null, true, templateName, true,path);
            	
            } catch (Exception e) {
				throw new Exception(e);
			}
          } catch (Exception e) {
        	  logger.error("exportListDebtInfor: ", e);
        }
		return res;
	}
	
    public void writeTotalData(DebtInformationDto total,XSSFWorkbook xssfWorkbook, int sheetNumber,int maxRow )  {

    	 
               
    	XSSFSheet xssfSheet = xssfWorkbook.getSheetAt(sheetNumber);
		XSSFRow row = xssfSheet.createRow(maxRow+6);
		XSSFRow rowl = xssfSheet.getRow(maxRow+6);

		xssfSheet.addMergedRegion(new CellRangeAddress(maxRow+6,maxRow+6,0,1)); 

		CellStyle style = xssfWorkbook.createCellStyle();  
	    style.setBorderBottom(BorderStyle.THIN);  
        style.setBorderRight(BorderStyle.THIN);  
        style.setBorderLeft(BorderStyle.THIN);  
        style.setBorderTop(BorderStyle.THIN);
		  for(int i =0; i < 5 ; i++) {
	    		XSSFCell cell = row.createCell(i);
	    		cell.setCellStyle(style);            
	        } 
		  
		Font font = xssfWorkbook.createFont();
		font.setBold(true);
		style.setFont(font);
		
		XSSFCell cellTotal = row.getCell(0);
		cellTotal.setCellStyle(style);
		cellTotal.setCellValue(total.getNo());
		
		CellStyle styleNumber = xssfWorkbook.createCellStyle();  
		styleNumber.setBorderBottom(BorderStyle.THIN);  
		styleNumber.setBorderRight(BorderStyle.THIN);  
		styleNumber.setBorderLeft(BorderStyle.THIN);  
		styleNumber.setBorderTop(BorderStyle.THIN);
		styleNumber.setAlignment(HorizontalAlignment.RIGHT);
		styleNumber.setFont(font);
		
		   // createHelper
        CreationHelper createHelper = xssfWorkbook.getCreationHelper();
        // dataFormat
        DataFormat dataFormat = createHelper.createDataFormat();
		
        styleNumber.setAlignment(HorizontalAlignment.RIGHT);
        styleNumber.setFont(font);
        styleNumber.setDataFormat(dataFormat.getFormat("#,##0"));
		

		XSSFCell cell= rowl.getCell(3);
		cell.setCellStyle(styleNumber);
        BigDecimal valueBigdecimal = (BigDecimal) total.getDebtMoneyDto();
        cell.setCellValue(valueBigdecimal.doubleValue());

		

    	
    }
 

	public ObjectDataRes<DebtInformationDto> callStoreDebtInfor(DebtInformationSearchDto searchDto, CommonSearchWithPagingDto common) {
		ObjectDataRes<DebtInformationDto> resultData = new ObjectDataRes<DebtInformationDto>();
		List<DebtInformationDto> listData = new ArrayList<>();
		if (StringUtils.endsWithIgnoreCase(searchDto.getKeyType(), "1")) {
			DebtInformationPagingParam param = new DebtInformationPagingParam();
			param.agentCode = searchDto.getAgentCode();
			if (StringUtils.isNotEmpty(searchDto.getYear()) || StringUtils.isNotEmpty(searchDto.getMonths())) {
				param.yyyyMm = searchDto.getYear() + searchDto.getMonths();
			}
			param.page = common.getPage();
			param.pageSize = common.getPageSize();
			param.sort = common.getSort();
			param.search = common.getSearch();
			sqlManagerDb2Service.call(STORE_DEBT_INFORMATION, param);
			if (CommonCollectionUtil.isNotEmpty(param.data)) {
				listData = param.data;
				DebtInformationDto dto = new DebtInformationDto();
				dto.setNo("Tổng nợ");
				dto.setDebtMoney(param.data.stream().mapToInt(x -> x.getDebtMoney()) .sum());
				listData.add(dto);
				resultData.setDatas(listData);
				resultData.setTotalData(param.totalData);
			}
			return resultData;
		} else if (StringUtils.endsWithIgnoreCase(searchDto.getKeyType(), "2")) {
			DebtInformationGaPagingParam param = new DebtInformationGaPagingParam();
			param.agentCode = searchDto.getAgentCode();
			param.orgCode = searchDto.getOrgCode();
			if (StringUtils.isNotEmpty(searchDto.getYear()) || StringUtils.isNotEmpty(searchDto.getMonths())) {
				param.yyyyMm = searchDto.getYear() + searchDto.getMonths();
			}
			param.page = common.getPage();
			param.pageSize = common.getPageSize();
			param.sort = common.getSort();
			param.search = common.getSearch();
			sqlManagerDb2Service.call(STORE_DEBT_INFORMATION_GA, param);
			if (CommonCollectionUtil.isNotEmpty(param.data)) {
				listData = param.data;
				DebtInformationDto dto = new DebtInformationDto();
				dto.setType("Tổng nợ");
				dto.setDebtMoney(param.data.stream().mapToInt(x -> x.getDebtMoney()) .sum());
				listData.add(dto);
				resultData.setDatas(listData);
				resultData.setTotalData(param.totalData);
			}
			return resultData;
		}
		else{
			return null;
		}
	}

	@Override
	public List<GAInformationDto> getListGA(String agentCode, String orgCode) {
		List<GAInformationDto> datas = new ArrayList<>();
        try {
            GAInformationParam param = new GAInformationParam(orgCode, agentCode, null);
        	sqlManagerDb2Service.call(STORE_GA_INFORMATION, param);
            if (CommonCollectionUtil.isNotEmpty(param.data)) {
            datas = param.data;
            }
        } catch(Exception e) {
        	logger.error("getListGA: ", e);
         }
		return datas;
	}

	@Override
	public List<Select2Dto> getOfficeOfGad(String agentCode) {
		OfficeGadParam param = new OfficeGadParam();
		List<Select2Dto> datas = new ArrayList<>();
		try {
        	sqlManagerDb2Service.call(STORE_OFFICE_GAD, param);
            if (CommonCollectionUtil.isNotEmpty(param.data)) {
            	datas = param.data;
            }
        } catch(Exception e) {
        	logger.error("getOfficeOfGad: ", e);
         }
		return datas;
	}

	@Override
	public List<EmulateSearchDto> getListchallengeLetterOffice(LetterSearchDto searchDto) {
		LetterParam param = new LetterParam();
		List<EmulateSearchDto> datas = new ArrayList<>();
		try {
			param.agentCode=searchDto.getAgentCode();
			param.contestType=searchDto.getContestType();
			param.officeCode=searchDto.getOfficeCode();
			param.memoCode=searchDto.getMemoCode();
			param.title=searchDto.getTitle();
        	sqlManagerDb2Service.call(STORE_CHALLENGE_LETTER, param);
            if (CommonCollectionUtil.isNotEmpty(param.data)) {
            	datas = param.data;
            }
        } catch(Exception e) {
        	logger.error("getOfficeOfGad: ", e);
         }
		return datas;
	}

	public List<EmulateResultSearchResultDto> listResultchallengeLetterOffice(ResultLetterSearchDto searchDto){
		ResultLetterParam param = new ResultLetterParam();
		searchDto.setFunctionCode("RESULT_LETTER_CHALLENGE");
    	ObjectMapper mapper = new ObjectMapper();
    	String stringJsonParam ="";
    	try {
			stringJsonParam = mapper.writeValueAsString(searchDto);
		} catch (JsonProcessingException e) {
			logger.error("Exception ", e);
			logger.error("JsonProcessingException: ", e);
		}
		CommonSearchWithPagingDto common = parseJsonToParamSearchService.getSearchCondition(stringJsonParam, "RESULT_LETTER_CHALLENGE");
		param.agentCode = searchDto.getAgentCode();
		param.officeCode=searchDto.getOfficeCode();
		param.contestType=searchDto.getContestType();
		param.sort = common.getSort();
		param.search = common.getSearch();
		List<EmulateResultSearchResultDto> datas = new ArrayList<>();
		try {
        	sqlManagerDb2Service.call(STORE_RESULT_CHALLENGE_LETTER, param);
            if (CommonCollectionUtil.isNotEmpty(param.data)) {
            	datas = param.data;
            }
        } catch(Exception e) {
        	logger.error("getListResultchallengeLetterOffice: ", e);
         }
		return datas;
	}
	
	@Override
	public List<EmulateResultSearchResultDto> getListResultchallengeLetterOffice(ResultLetterSearchDto searchDto) {
		List<EmulateResultSearchResultDto> datas = listResultchallengeLetterOffice(searchDto);
		return datas;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public ResponseEntity exportListResultchallengeLetterOffice(HttpServletResponse response, Locale locale,
			ResultLetterSearchDto searchDto) {
		ResponseEntity res = null;
		try {
			searchDto.setPage(null);
			searchDto.setPageSize(null);
			List<EmulateResultSearchResultDto> lstdata = listResultchallengeLetterOffice(searchDto);
            String datePattern = systemConfig.getConfig(SystemConfig.DATE_PATTERN);
            datePattern = "dd/MM/yyyy";
            String templateName = "Challenge_letter_result_GA.xlsx";
    		String templatePath = servletContext.getRealPath(CommonConstant.REAL_PATH_TEMPLATE_EXCEL + "/" + templateName);
    		String startRow = "A4";
            List<ItemColsExcelDto> cols = new ArrayList<>();
			ImportExcelUtil.setListColumnExcel(LetterResultGAEnum.class, cols);
			ExportExcelUtil exportExcel = new ExportExcelUtil<>();
			Map<String, String> mapColFormat = null;// setMapColFormat();
			Map<String, Object> setMapColDefaultValue = null;// setMapColDefaultValue();
            // do export
            try (XSSFWorkbook xssfWorkbook = exportExcel.getXSSFWorkbook(templatePath);) {
				Map<String, CellStyle> mapColStyle = null;// setMapColStyle(xssfWorkbook);
				String repo = systemConfig.getConfig(systemConfig.REPO_UPLOADED_MAIN);
				String path = systemConfig.getPhysicalPathById(repo, null); //path up service
            	res =  exportExcel.doExportExcelHeaderWithColFormatRestUpService(xssfWorkbook, 0, null, lstdata, EmulateResultSearchResultDto.class
            			, cols, datePattern, startRow, mapColFormat, mapColStyle,
						setMapColDefaultValue, null, true, templateName, true,path);
            } catch (Exception e) {
				throw new Exception(e);
			}
          } catch (Exception e) {
        	  logger.error("exportListResult: ", e);
        }
		return res;
	}
	
	
	
}
