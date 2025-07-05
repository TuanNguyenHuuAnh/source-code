package vn.com.unit.ep2p.sam.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.ServletContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import vn.com.unit.cms.core.module.sam.dto.ActivitityExportDto;
import vn.com.unit.cms.core.module.sam.repository.SamActivityRepository;
import vn.com.unit.core.config.SystemConfig;
import vn.com.unit.ep2p.enumdef.SamExportActDetailEnum;
import vn.com.unit.ep2p.enumdef.SamExportEnum;
import vn.com.unit.ep2p.sam.service.SamExportCsvService;
import vn.com.unit.imp.excel.constant.CommonConstant;
import vn.com.unit.imp.excel.dto.ItemColsExcelDto;
import vn.com.unit.imp.excel.utils.ImportExcelUtil;

/**
 * @author ntr.bang
 * Export to CSV all activities with search condition are (from date - to date)
 */
@Service
@Transactional(readOnly = true, rollbackFor = Exception.class)
public class SamExportCsvServiceImpl extends SamBaseServiceImpl implements SamExportCsvService {

	@Autowired
	private SamActivityRepository actRepository;
	
	@Autowired
    private SystemConfig systemConfig;
	
	@Autowired
	private ServletContext servletContext;
	
	@Override
	public SystemConfig getSystemConfig() {
		return systemConfig;
	}
	
	private static final Logger log = LoggerFactory.getLogger(SamExportCsvServiceImpl.class);

	@SuppressWarnings({ "unchecked", "rawtypes", "static-access" })
	@Override
	public ResponseEntity exportCsv(String pfromDate, String ptoDate, Boolean isDetail, Locale locale) {
		ResponseEntity res = null;
		try {
			log.info("Begin exportCsv(String pfromDate, String ptoDate)");

			// Convert date
			String format = "dd/MM/yyyy HH:mm:ss";
			// early in the day
			Date fromDate = convertToDate(pfromDate + " 00:00:00", format);
			// end day
			Date toDate = convertToDate(ptoDate + " 23:59:59", format);

			String datePattern = getSystemConfig().getConfig(SystemConfig.DATE_PATTERN);
            datePattern = "dd/MM/yyyy";
            String startRow = "A2";
            String templateName = "";
            String templatePath = servletContext.getRealPath(CommonConstant.REAL_PATH_TEMPLATE_EXCEL + "/" + templateName);
            
			if(fromDate == null || toDate == null) {
				return res;
			} else {
				List<ActivitityExportDto> lstdata = actRepository.exportCsv(fromDate, toDate);
				
				List<ItemColsExcelDto> cols = new ArrayList<>();
	            // start fill data to workbook
				if (isDetail) {
					templateName = "SAMDetailReport.xlsx";
					ImportExcelUtil.setListColumnExcel(SamExportActDetailEnum.class, cols);
				} else {
					templateName = "SAMReport.xlsx";
					ImportExcelUtil.setListColumnExcel(SamExportEnum.class, cols);
				}
				
				//ExportCsvUtil exportCsv = new ExportCsvUtil<>();
				Map<String, String> mapColFormat = null;// setMapColFormat();
				Map<String, Object> setMapColDefaultValue = null;
				
				// do export
	            /*try (XSSFWorkbook xssfWorkbook = exportCsv.getXSSFWorkbook(templatePath);) {
					Map<String, CellStyle> mapColStyle = null;// setMapColStyle(xssfWorkbook);
					String repo = systemConfig.getConfig(systemConfig.REPO_UPLOADED_MAIN);
					// D:/data/repository/main/export-excel/DLVN
					String path = systemConfig.getPhysicalPathById(repo, null); //path up service
					
	            	res =  exportCsv.doExportExcelAndCsv(xssfWorkbook, 0, null, lstdata, ActivitityExportDto.class,
	            			cols, datePattern, startRow, mapColFormat, mapColStyle,
							setMapColDefaultValue, null, true, templateName, true, path);
	            } catch (Exception e) {
	            	log.error("##doExport##", e);
	            }*/
			}
		} catch (Exception e) {
			log.error("Exception: ", e);
		}

		return res;
	}

}
