/*******************************************************************************
 * Class        SystemLogsService
 * Created date 2018/01/08
 * Lasted date  2018/01/08
 * Author       HUNGHT
 * Change log   2018/01/0801-00 HUNGHT create a new
 ******************************************************************************/
package vn.com.unit.ep2p.admin.service;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import vn.com.unit.common.dto.PageWrapper;
import vn.com.unit.ep2p.admin.dto.SystemLogsDto;
import vn.com.unit.ep2p.admin.entity.SystemLogs;

/**
 * SystemLogsService
 * 
 * @version 01-00
 * @since 01-00
 * @author HUNGHT
 */
public interface SystemLogsService {

    /**
     * writeSystemLogs
     *
     * @param functionCode
     * @param logSummary
     * @param logDetail
     * @author HUNGHT
     */
    void writeSystemLogs(String functionCode, String logSummary, String logDetail, HttpServletRequest request);
    
    /**
     * writeSystemLogs
     * 
     * @param functionCode
     * @param logSummary
     * @param logType
     * @param logDetail
     * @param request
     * @author HUNGHT
     */
    void writeSystemLogs(String functionCode, String logSummary, int logType, String logDetail, HttpServletRequest request);

    /**
     * saveEntity
     *
     * @param systemLogs
     * @author VinhLT
     */
    void saveEntity(SystemLogs systemLogs);

    /**
     * writeSystemLogs
     *
     * @param functionCode
     * @param logSummary
     * @param logType
     * @param logDetail
     * @param username
     * @param request
     * @author VinhLT
     */
    void writeSystemLogs(String functionCode, String logSummary, int logType, String logDetail, String username, HttpServletRequest request);
    
    /**
     * 
     * writeSystemLogs
     * @param functionCode
     * @param logSummary
     * @param logType
     * @param logDetail
     * @param username
     * @param request
     * @author taitt
     */
    void writeSystemLogs(String functionCode, String logSummary, int logType, String logDetail, String username, HttpServletRequest request,String device);
    
    /**
     * writeSystemLogs
     * 
     * @param functionCode
     * @param logSummary
     * @param logType
     * @param logDetail
     * @param username
     * @param companyId
     * @param request
     * @author HungHT
     */
    void writeSystemLogs(String functionCode, String logSummary, int logType, String logDetail, String username, Long companyId,
            HttpServletRequest request);
    
    /**
     * 
     * writeSystemLogs
     * @param functionCode
     * @param logSummary
     * @param logType
     * @param logDetail
     * @param username
     * @param companyId
     * @param request
     * @param device
     * @author taitt
     */
    void writeSystemLogs(String functionCode, String logSummary, int logType, String logDetail, String username, Long companyId,
            HttpServletRequest request,String device);
    
    public PageWrapper<SystemLogsDto> search(SystemLogsDto systemLogsDto, int page, int pageSize);
    
    void exportExcel(SystemLogsDto searchDto, HttpServletResponse response, Locale locale);

    /**
     * exportExcelNonPassword
     * @param searchDto
     * @param res
     * @param locale
     * @author HungHT
     */
    void exportExcelNonPassword(SystemLogsDto searchDto, HttpServletResponse res, Locale locale);
}
