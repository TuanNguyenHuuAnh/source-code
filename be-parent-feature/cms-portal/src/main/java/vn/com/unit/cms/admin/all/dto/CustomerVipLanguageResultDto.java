package vn.com.unit.cms.admin.all.dto;

import java.util.Date;

public class CustomerVipLanguageResultDto {

	private Long id;

	private String code;

	private Integer status;

	private String title;

	private String createBy;

	private Date createDate;

	private Integer enabled;

	private String enabledString;

	private String statusName;

	private Integer stt;

	/**
	 * @return the code
	 */
	public String getCode() {
		return code;
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
	 * @param code the code to set
	 */
	public void setCode(String code) {
		this.code = code;
	}

	/**
	 * @return the status
	 */
	public Integer getStatus() {
		return status;
	}

	/**
	 * @param status the status to set
	 */
	public void setStatus(Integer status) {
		this.status = status;
	}

	/**
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * @param title the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * @return the createBy
	 */
	public String getCreateBy() {
		return createBy;
	}

	/**
	 * @param createBy the createBy to set
	 */
	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}

	/**
	 * @return the createDate
	 */
	public Date getCreateDate() {
		return createDate;
	}

	/**
	 * @param createDate the createDate to set
	 */
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	/**
	 * @return the enabled
	 */
	public Integer getEnabled() {
		return enabled;
	}

	/**
	 * @param enabled the enabled to set
	 */
	public void setEnabled(Integer enabled) {
		this.enabled = enabled;
	}

	/**
	 * @return the statusName
	 */
	public String getStatusName() {
		return statusName;
	}

	/**
	 * @param statusName the statusName to set
	 */
	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}

	/**
	 * @return the stt
	 */
	public Integer getStt() {
		return stt;
	}

	/**
	 * @param stt the stt to set
	 */
	public void setStt(Integer stt) {
		this.stt = stt;
	}

	/**
	 * @return the enabledString
	 */
	public String getEnabledString() {
		return enabledString;
	}

	/**
	 * @param enabledString the enabledString to set
	 */
	public void setEnabledString(String enabledString) {
		this.enabledString = enabledString;
	}
}
