package vn.com.unit.ep2p.admin.dto;

import java.util.List;

import vn.com.unit.quartz.job.dto.ConditionSearchCommonDto;

/**
 * QrtzMScheduleSearchDto
 * 
 * @version 01-00
 * @since 01-00
 * @author LongPNT
 */
public class QrtzMScheduleSearchDto extends ConditionSearchCommonDto {

	private String schedCode;

	private String schedName;

	private String cronExpression;

	private String description;

	/** fieldValues */
	private List<String> fieldValues;

	/** fieldSearch */
	private String fieldSearch;
	
	private Long companyId;
	
	private String companyName;
	
	private boolean companyAdmin;
	
	private List<Long> companyIdList;

	public String getSchedCode() {
		return schedCode;
	}

	public void setSchedCode(String schedCode) {
		this.schedCode = schedCode;
	}

	public String getSchedName() {
		return schedName;
	}

	public void setSchedName(String schedName) {
		this.schedName = schedName;
	}

	public String getCronExpression() {
		return cronExpression;
	}

	public void setCronExpression(String cronExpression) {
		this.cronExpression = cronExpression;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public List<String> getFieldValues() {
		return fieldValues;
	}

	public void setFieldValues(List<String> fieldValues) {
		this.fieldValues = fieldValues;
	}

	public String getFieldSearch() {
		return fieldSearch;
	}

	public void setFieldSearch(String fieldSearch) {
		this.fieldSearch = fieldSearch;
	}

	public Long getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public boolean isCompanyAdmin() {
		return companyAdmin;
	}

	public void setCompanyAdmin(boolean companyAdmin) {
		this.companyAdmin = companyAdmin;
	}

	public List<Long> getCompanyIdList() {
		return companyIdList;
	}

	public void setCompanyIdList(List<Long> companyIdList) {
		this.companyIdList = companyIdList;
	}
}
