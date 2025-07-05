package vn.com.unit.cms.admin.all.dto;

import java.util.Date;

public class CustomerVipLanguageEditDto {
	private Long id;

	private Long customerVipId;;

	private String languageCode;

	private String title;

	private String shortContent;

	private String content;

	private Date createDate;

	private String createBy;

	private Date updateDate;

	private String updateBy;

	private Date deleteDate;

	private String deleteBy;

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
	 * @return the customerVipId
	 */
	public Long getCustomerVipId() {
		return customerVipId;
	}

	/**
	 * @param customerVipId the customerVipId to set
	 */
	public void setCustomerVipId(Long customerVipId) {
		this.customerVipId = customerVipId;
	}

	/**
	 * @return the languageCode
	 */
	public String getLanguageCode() {
		return languageCode;
	}

	/**
	 * @param languageCode the languageCode to set
	 */
	public void setLanguageCode(String languageCode) {
		this.languageCode = languageCode;
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
	 * @return the shortContent
	 */
	public String getShortContent() {
		return shortContent;
	}

	/**
	 * @param shortContent the shortContent to set
	 */
	public void setShortContent(String shortContent) {
		this.shortContent = shortContent;
	}

	/**
	 * @return the content
	 */
	public String getContent() {
		return content;
	}

	/**
	 * @param content the content to set
	 */
	public void setContent(String content) {
		this.content = content;
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
	 * @return the updateDate
	 */
	public Date getUpdateDate() {
		return updateDate;
	}

	/**
	 * @param updateDate the updateDate to set
	 */
	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	/**
	 * @return the updateBy
	 */
	public String getUpdateBy() {
		return updateBy;
	}

	/**
	 * @param updateBy the updateBy to set
	 */
	public void setUpdateBy(String updateBy) {
		this.updateBy = updateBy;
	}

	/**
	 * @return the deleteDate
	 */
	public Date getDeleteDate() {
		return deleteDate;
	}

	/**
	 * @param deleteDate the deleteDate to set
	 */
	public void setDeleteDate(Date deleteDate) {
		this.deleteDate = deleteDate;
	}

	/**
	 * @return the deleteBy
	 */
	public String getDeleteBy() {
		return deleteBy;
	}

	/**
	 * @param deleteBy the deleteBy to set
	 */
	public void setDeleteBy(String deleteBy) {
		this.deleteBy = deleteBy;
	}

}
