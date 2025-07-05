/*******************************************************************************
 * Class        SystemLogsServiceImpl
 * Created date 2018/01/08
 * Lasted date  2018/01/08
 * Author       HUNGHT
 * Change log   2018/01/0801-00 HUNGHT create a new
 ******************************************************************************/
package vn.com.unit.ep2p.admin.service.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import vn.com.unit.common.dto.PageWrapper;
import vn.com.unit.common.utils.CommonDateUtil;
import vn.com.unit.core.config.SystemConfig;
import vn.com.unit.ep2p.admin.dto.SystemLogsDto;
import vn.com.unit.ep2p.admin.entity.SystemLogs;
import vn.com.unit.ep2p.admin.enumdef.DatabaseTypeEnum;
import vn.com.unit.ep2p.admin.enumdef.SystemLogsEnum;
import vn.com.unit.ep2p.admin.repository.SystemLogsRepository;
import vn.com.unit.ep2p.admin.service.SystemLogsService;
import vn.com.unit.ep2p.constant.CommonConstant;
import vn.com.unit.ep2p.dto.ItemColsExcelDto;
import vn.com.unit.ep2p.enumdef.LogTypeEnum;
import vn.com.unit.ep2p.enumdef.SystemLogsExportEnum;
import vn.com.unit.ep2p.utils.ExportExcelUtil;
import vn.com.unit.ep2p.utils.ImportExcelUtil;

/**
 * SystemLogsServiceImpl
 * 
 * @version 01-00
 * @since 01-00
 * @author HUNGHT
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class SystemLogsServiceImpl implements SystemLogsService {
	private Logger logger = LoggerFactory.getLogger(getClass());

	/** systemLogsRepository */
	@Autowired
	private SystemLogsRepository systemLogsRepository;

	@Autowired
	SystemConfig systemConfig;

	@Autowired
	private ServletContext servletContext;
	
	@Override
	@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRES_NEW)
	public void writeSystemLogs(String functionCode, String logSummary, int logType, String logDetail, String username,
			HttpServletRequest request) {
		// Set data for log
		SystemLogs systemLogs = new SystemLogs();
		systemLogs.setFunctionCode(functionCode);
		systemLogs.setLogSummary(logSummary);
		systemLogs.setLogType(logType);
		systemLogs.setLogDetail(logDetail);
		systemLogs.setUsername(username);

		// Save system logs
//		saveSystemLogs(systemLogs, request,null);
	}
	

    @Override
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRES_NEW)
    public void writeSystemLogs(String functionCode, String logSummary, int logType, String logDetail, String username, Long companyId,
            HttpServletRequest request) {
        // Set data for log
        SystemLogs systemLogs = new SystemLogs();
        systemLogs.setFunctionCode(functionCode);
        systemLogs.setLogSummary(logSummary);
        systemLogs.setLogType(logType);
        systemLogs.setLogDetail(logDetail);
        systemLogs.setUsername(username);
        systemLogs.setCompanyId(companyId);

        // Save system logs
//        saveSystemLogs(systemLogs, request,null);
    }


	@Override
	@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRES_NEW)
	public void writeSystemLogs(String functionCode, String logSummary, int logType, String logDetail,
			HttpServletRequest request) {
		// Set data for log
		SystemLogs systemLogs = new SystemLogs();
		systemLogs.setFunctionCode(functionCode);
		systemLogs.setLogSummary(logSummary);
		systemLogs.setLogType(logType);
		systemLogs.setLogDetail(logDetail);

		// Save system logs
//		saveSystemLogs(systemLogs, request,null);

	}


	@Override
	@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRES_NEW)
	public void writeSystemLogs(String functionCode, String logSummary, String logDetail, HttpServletRequest request) {
		// Set data for log
		SystemLogs systemLogs = new SystemLogs();
		systemLogs.setFunctionCode(functionCode);
		systemLogs.setLogSummary(logSummary);
		systemLogs.setLogType(LogTypeEnum.INFO.toInt());
		systemLogs.setLogDetail(logDetail);

		// Save system logs
        saveEntity(systemLogs);
	}
	
	@Override
	public void saveEntity(SystemLogs systemLogs) {
		systemLogsRepository.create(systemLogs);
	}

    @Override
    public PageWrapper<SystemLogsDto> search(SystemLogsDto systemLogsDto, int page, int pageSize) {
        setSearchParm(systemLogsDto);

        // Get listPageSize, sizeOfPage
        List<Integer> listPageSize = systemConfig.getListPage(pageSize);
        int sizeOfPage = systemConfig.getSizeOfPage(listPageSize, pageSize);
        PageWrapper<SystemLogsDto> pageWrapper = new PageWrapper<>(page, sizeOfPage);

        // Set listPageSize, sizeOfPage
        pageWrapper.setListPageSize(listPageSize);
        pageWrapper.setSizeOfPage(sizeOfPage);
        List<SystemLogsDto> lstSystemLogsDto = null;
        int count = 0;
        
        if(StringUtils.equals(DatabaseTypeEnum.ORACLE.toString(), systemConfig.getConfig(SystemConfig.DBTYPE))) {
        	count = systemLogsRepository.countRecordsOracle(systemLogsDto);
        	if (count > 0) {
                int startIndex = systemConfig.getStartIndex(pageWrapper.getCurrentPage(), sizeOfPage);
                lstSystemLogsDto = systemLogsRepository.searchSystemLogsOracle(systemLogsDto, startIndex, sizeOfPage);
            }
        }
        else {
        	count = systemLogsRepository.countRecordsSQL(systemLogsDto);
        	if (count > 0) {
                int startIndex = systemConfig.getStartIndex(pageWrapper.getCurrentPage(), sizeOfPage);
                lstSystemLogsDto = systemLogsRepository.searchSystemLogsSQL(systemLogsDto, startIndex, sizeOfPage);
        	}
        }
        
        
//        int count = 0;
//        if (StringUtils.equals(DatabaseTypeEnum.ORACLE.toString(), systemConfig.getConfig(AppSystemConfig.DBTYPE))) {
//            count = systemLogsRepository.countRecordsOracle(systemLogsDto);
//            if (count > 0) {
//                int startIndex = systemConfig.getStartIndex(pageWrapper.getCurrentPage(), sizeOfPage);
//                lstSystemLogsDto = systemLogsRepository.searchSystemLogsOracle(systemLogsDto, startIndex, sizeOfPage);
//            }
//        } else {
//            count = systemLogsRepository.countRecordsSQL(systemLogsDto);
//            if (count > 0) {
//                int startIndex = systemConfig.getStartIndex(pageWrapper.getCurrentPage(), sizeOfPage);
//                lstSystemLogsDto = systemLogsRepository.searchSystemLogsSQL(systemLogsDto, startIndex, sizeOfPage);
//            }
//        }
        pageWrapper.setDataAndCount(lstSystemLogsDto, count);
        return pageWrapper;
    }

    private void setSearchParm(SystemLogsDto systemLogsDto) {
        if (null == systemLogsDto.getFieldValues()) {
            systemLogsDto.setFieldValues(new ArrayList<String>());
        }
//        systemLogsDto.setCompanyAdmin(UserProfileUtils.isCompanyAdmin());
        Date fromDate = systemLogsDto.getFromDate();
        if(null != fromDate) {
            fromDate = CommonDateUtil.truncate(systemLogsDto.getFromDate(), Calendar.DATE);
            systemLogsDto.setFromDate(fromDate);
        }
        Date toDate = systemLogsDto.getToDate();
        if(null != toDate) {
            toDate = CommonDateUtil.truncate(systemLogsDto.getToDate(), Calendar.DATE);
            toDate = CommonDateUtil.addDays(toDate, 1);
            systemLogsDto.setToDate(toDate);
        }
        
        if (systemLogsDto.getFieldValues().isEmpty()) {
            systemLogsDto
                    .setIp(systemLogsDto.getFieldSearch() != null ? systemLogsDto.getFieldSearch().trim() : systemLogsDto.getFieldSearch());
            systemLogsDto.setUsername(
                    systemLogsDto.getFieldSearch() != null ? systemLogsDto.getFieldSearch().trim() : systemLogsDto.getFieldSearch());
            systemLogsDto.setLogTypeText(
                    systemLogsDto.getFieldSearch() != null ? systemLogsDto.getFieldSearch().trim() : systemLogsDto.getFieldSearch());
            systemLogsDto.setLogSummary(
                    systemLogsDto.getFieldSearch() != null ? systemLogsDto.getFieldSearch().trim() : systemLogsDto.getFieldSearch());
            systemLogsDto.setLogDetail(
                    systemLogsDto.getFieldSearch() != null ? systemLogsDto.getFieldSearch().trim() : systemLogsDto.getFieldSearch());
            systemLogsDto.setFunctionCode(
                    systemLogsDto.getFieldSearch() != null ? systemLogsDto.getFieldSearch().trim() : systemLogsDto.getFieldSearch());
        } else {
            for (String field : systemLogsDto.getFieldValues()) {
                if (StringUtils.equals(field, SystemLogsEnum.IP.name())) {
                    systemLogsDto.setIp(systemLogsDto.getFieldSearch().trim());
                    continue;
                }
                if (StringUtils.equals(field, SystemLogsEnum.USERNAME.name())) {
                    systemLogsDto.setUsername(systemLogsDto.getFieldSearch().trim());
                    continue;
                }
                if (StringUtils.equals(field, SystemLogsEnum.LOG_TYPE.name())) {
                    systemLogsDto.setLogTypeText(systemLogsDto.getFieldSearch().trim());
                    continue;
                }
                if (StringUtils.equals(field, SystemLogsEnum.LOG_SUMMARY.name())) {
                    systemLogsDto.setLogSummary(systemLogsDto.getFieldSearch().trim());
                    continue;
                }
                if (StringUtils.equals(field, SystemLogsEnum.LOG_DETAIL.name())) {
                    systemLogsDto.setLogDetail(systemLogsDto.getFieldSearch().trim());
                    continue;
                }
                if (StringUtils.equals(field, SystemLogsEnum.FUNCTION_CODE.name())) {
                    systemLogsDto.setFunctionCode(systemLogsDto.getFieldSearch().trim());
                    continue;
                }
            }
        }
    }

	@Override
    public void exportExcel(SystemLogsDto searchDto, HttpServletResponse res, Locale locale) {
        try {
            String templateName = CommonConstant.TEMPLATE_EXCEL_SYSTEM_LOGS;
            String template = servletContext.getRealPath(CommonConstant.REAL_PATH_TEMPLATE_EXCEL) + "/" + templateName
                    + CommonConstant.TYPE_EXCEL;
            String datePattern = systemConfig.getConfig(SystemConfig.DATE_PATTERN);
            // takedata
            setSearchParm(searchDto);
            List<SystemLogsDto> lstData = null;
            int count = 0;
            if (StringUtils.equals(DatabaseTypeEnum.ORACLE.toString(), systemConfig.getConfig(SystemConfig.DBTYPE))) {
                count = systemLogsRepository.countRecordsOracle(searchDto);
                if (count > 0) {
                    lstData = systemLogsRepository.searchSystemLogsOracle(searchDto, 1, count);
                }
            } else {
                count = systemLogsRepository.countRecordsSQL(searchDto);
                if (count > 0) {
                    lstData = systemLogsRepository.searchSystemLogsSQL(searchDto, 1, count);
                }
            }
            List<ItemColsExcelDto> cols = new ArrayList<>();
            // start fill data to workbook
            ImportExcelUtil.setListColumnExcel(SystemLogsExportEnum.class, cols);
            ExportExcelUtil<SystemLogsDto> exportExcel = new ExportExcelUtil<>();
            // do export
            exportExcel.exportExcelWithXSSF(template, locale, lstData, SystemLogsDto.class, cols, datePattern, res, templateName,
                    searchDto.getPassExport());

        } catch (Exception e) {
            logger.error("Exception ", e);
        }
    }
	
	@Override
    public void exportExcelNonPassword(SystemLogsDto searchDto, HttpServletResponse res, Locale locale) {
        try {
            String templateName = CommonConstant.TEMPLATE_EXCEL_SYSTEM_LOGS;
            String template = servletContext.getRealPath(CommonConstant.REAL_PATH_TEMPLATE_EXCEL) + "/" + templateName
                    + CommonConstant.TYPE_EXCEL;
            String datePattern = systemConfig.getConfig(SystemConfig.DATE_PATTERN);
            // takedata
            setSearchParm(searchDto);
            List<SystemLogsDto> lstData = null;
            int count = 0;
            if (StringUtils.equals(DatabaseTypeEnum.ORACLE.toString(), systemConfig.getConfig(SystemConfig.DBTYPE))) {
                count = systemLogsRepository.countRecordsOracle(searchDto);
                if (count > 0) {
                    lstData = systemLogsRepository.searchSystemLogsOracle(searchDto, 0, count);
                }
            } else {
                count = systemLogsRepository.countRecordsSQL(searchDto);
                if (count > 0) {
                    lstData = systemLogsRepository.searchSystemLogsSQL(searchDto, 0, count);
                }
            }
            List<ItemColsExcelDto> cols = new ArrayList<>();
            // start fill data to workbook
            ImportExcelUtil.setListColumnExcel(SystemLogsExportEnum.class, cols);
            ExportExcelUtil<SystemLogsDto> exportExcel = new ExportExcelUtil<>();
            // do export
            exportExcel.exportExcelWithXSSFNonPass(template, locale, lstData, SystemLogsDto.class, cols, datePattern, res, templateName);

        } catch (Exception e) {
            logger.error("Exception ", e);
        }
    }


	@Override
	@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRES_NEW)
	public void writeSystemLogs(String functionCode, String logSummary, int logType, String logDetail, String username,
			HttpServletRequest request,String device) {
		// Set data for log
		SystemLogs systemLogs = new SystemLogs();
		systemLogs.setFunctionCode(functionCode);
		systemLogs.setLogSummary(logSummary);
		systemLogs.setLogType(logType);
		systemLogs.setLogDetail(logDetail);
		systemLogs.setUsername(username);

		// Save system logs
//		saveSystemLogs(systemLogs, request,device);
	}
	
    @Override
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRES_NEW)
    public void writeSystemLogs(String functionCode, String logSummary, int logType, String logDetail, String username, Long companyId,
            HttpServletRequest request,String device) {
        // Set data for log
        SystemLogs systemLogs = new SystemLogs();
        systemLogs.setFunctionCode(functionCode);
        systemLogs.setLogSummary(logSummary);
        systemLogs.setLogType(logType);
        systemLogs.setLogDetail(logDetail);
        systemLogs.setUsername(username);
        systemLogs.setCompanyId(companyId);

        // Save system logs
//        saveSystemLogs(systemLogs, request,device);
    }
}
