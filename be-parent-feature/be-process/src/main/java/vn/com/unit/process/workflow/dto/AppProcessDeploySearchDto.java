/*******************************************************************************
 * Class        AppProcessDeploySearchDto
 * Created date 2019/07/11
 * Lasted date  2019/07/11
 * Author       KhuongTH
 * Change log   2019/07/11 01-00 KhuongTH create a new
 ******************************************************************************/
package vn.com.unit.process.workflow.dto;

import java.util.Date;
import java.util.List;

import vn.com.unit.common.dto.AbstractCompanyDto;

/**
 * AppProcessDeploySearchDto
 * 
 * @version 01-00
 * @since 01-00
 * @author KhuongTH
 */
public class AppProcessDeploySearchDto extends AbstractCompanyDto{
	private Long companySearchId;
	private Long businessId;
	private Long processId;
	/** fieldSearch */
    private String fieldSearch;

    /** fieldValues */
    private List<String> fieldValues;

    private Date fromDate;
    private Date toDate;
    
    private String code;
    private String name;
	
	public Long getCompanySearchId() {
		return companySearchId;
	}
	public void setCompanySearchId(Long companySearchId) {
		this.companySearchId = companySearchId;
	}
	public Long getBusinessId() {
		return businessId;
	}
	public void setBusinessId(Long businessId) {
		this.businessId = businessId;
	}
	public Long getProcessId() {
		return processId;
	}
	public void setProcessId(Long processId) {
		this.processId = processId;
	}
	public String getFieldSearch() {
		return fieldSearch;
	}
	public void setFieldSearch(String fieldSearch) {
		this.fieldSearch = fieldSearch;
	}
	public List<String> getFieldValues() {
		return fieldValues;
	}
	public void setFieldValues(List<String> fieldValue) {
		this.fieldValues = fieldValue;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Date getFromDate() {
		return fromDate;
	}
	public void setFromDate(Date fromDate) {
		this.fromDate = fromDate;
	}
	public Date getToDate() {
		return toDate;
	}
	public void setToDate(Date toDate) {
		this.toDate = toDate;
	}
	

}
