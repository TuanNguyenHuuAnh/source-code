package vn.com.unit.ep2p.admin.dto;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import vn.com.unit.common.dto.AbstractCompanyDto;

public class SystemLogsDto extends AbstractCompanyDto {
	private Long id;
	private String functionCode;
	private String logSummary;
	private int logType;
	private String logTypeText;
	private Timestamp logDate;
	private String logDetail;
	private String ip;
	private String username;
	
	private Date fromDate;
	private Date toDate;
	private int currentPage;
	private int sizePage;
	private String fieldSearch;
	private List<String> fieldValues;
	
	private String token;
	
	private String passExport;
	
	/**
	 * @return the fieldValues
	 */
	public List<String> getFieldValues() {
		return fieldValues;
	}
	/**
	 * @param fieldValues the fieldValues to set
	 */
	public void setFieldValues(List<String> fieldValues) {
		this.fieldValues = fieldValues;
	}
	/** 
	 * @return the fieldSearch
	 */
	public String getFieldSearch() {
		return fieldSearch;
	}
	/**
	 * @param fieldSearch the fieldSearch to set
	 */
	public void setFieldSearch(String fieldSearch) {
		this.fieldSearch = fieldSearch;
	}
	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}
	/**
	 * @return the functionCode
	 */
	public String getFunctionCode() {
		return functionCode;
	}
	/**
	 * @param functionCode the functionCode to set
	 */
	public void setFunctionCode(String functionCode) {
		this.functionCode = functionCode;
	}
	/**
	 * @return the logSummary
	 */
	public String getLogSummary() {
		return logSummary;
	}
	/**
	 * @param logSummary the logSummary to set
	 */
	public void setLogSummary(String logSummary) {
		this.logSummary = logSummary;
	}
	/**
	 * @return the logType
	 */
	public int getLogType() {
		return logType;
	}
	/**
	 * @param logType the logType to set
	 */
	public void setLogType(int logType) {
		this.logType = logType;
	}
	public String getLogTypeText() {
		return logTypeText;
	}
	public void setLogTypeText(String logTypeText) {
		this.logTypeText = logTypeText;
	}

	
    /**
     * Get logDate
     * @return Timestamp
     * @author HUNGHT
     */
    public Timestamp getLogDate() {
        return logDate;
    }
    
    /**
     * Set logDate
     * @param   logDate
     *          type Timestamp
     * @return
     * @author  HUNGHT
     */
    public void setLogDate(Timestamp logDate) {
        this.logDate = logDate;
    }
    /**
	 * @return the logDetail
	 */
	public String getLogDetail() {
		return logDetail;
	}
	/**
	 * @param logDetail the logDetail to set
	 */
	public void setLogDetail(String logDetail) {
		this.logDetail = logDetail;
	}
	/**
	 * @return the ip
	 */
	public String getIp() {
		return ip;
	}
	/**
	 * @param ip the ip to set
	 */
	public void setIp(String ip) {
		this.ip = ip;
	}
	/**
	 * @return the username
	 */
	public String getUsername() {
		return username;
	}
	/**
	 * @param username the username to set
	 */
	public void setUsername(String username) {
		this.username = username;
	}
	/**
	 * @return the fromDate
	 */
	public Date getFromDate() {
		return fromDate;
	}
	/**
	 * @param fromDate the fromDate to set
	 */
	public void setFromDate(Date fromDate) {
		this.fromDate = fromDate;
	}
	/**
	 * @return the toDate
	 */
	public Date getToDate() {
		return toDate;
	}
	/**
	 * @param toDate the toDate to set
	 */
	public void setToDate(Date toDate) {
		this.toDate = toDate;
	}
	/**
	 * @return the currentPage
	 */
	public int getCurrentPage() {
		return currentPage;
	}
	/**
	 * @param currentPage the currentPage to set
	 */
	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}
	/**
	 * @return the sizePage
	 */
	public int getSizePage() {
		return sizePage;
	}
	/**
	 * @param sizePage the sizePage to set
	 */
	public void setSizePage(int sizePage) {
		this.sizePage = sizePage;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public String getPassExport() {
		return passExport;
	}
	public void setPassExport(String passExport) {
		this.passExport = passExport;
	}
}
