package vn.com.unit.ep2p.dto;

import java.util.Date;
//import java.util.List;

public class FormVO {
	private String formCategoryCode;
	private String formCategoryName;
	private int formId;
	private String companyId;
	private String formName;
	private String formDesc;
	private String formFileName;
	private String formImg;
	private String formOzCategory;
	private String publicFlag;
	private int displayOrder;
	private Date   createdDate;
	private String createdUserId;
	private String createdUserName;
	private Date   updatedDate;
	private String updatedUserId;
	private String updatedUserName;
	private String usedFlag;
	private String deviceType;

	public String getDeviceType() {
		return deviceType;
	}
	public void setDeviceType(String deviceType) {
		this.deviceType = deviceType;
	}
	public String getFormCategoryCode() {
		return formCategoryCode;
	}
	public void setFormCategoryCode(String formCategoryCode) {
		this.formCategoryCode = formCategoryCode;
	}
	public String getFormCategoryName() {
		return formCategoryName;
	}
	public void setFormCategoryName(String formCategoryName) {
		this.formCategoryName = formCategoryName;
	}
	public int getFormId() {
		return formId;
	}
	public void setFormId(int formId) {
		this.formId = formId;
	}
	public String getCompanyId() {
		return companyId;
	}
	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}
	public String getFormName() {
		return formName;
	}
	public void setFormName(String formName) {
		this.formName = formName;
	}
	public String getFormDesc() {
		return formDesc;
	}
	public void setFormDesc(String formDesc) {
		this.formDesc = formDesc;
	}
	public String getFormFileName() {
		return formFileName;
	}
	public void setFormFileName(String formFileName) {
		this.formFileName = formFileName;
	}
	public String getFormImg() {
		return formImg;
	}
	public void setFormImg(String formImg) {
		this.formImg = formImg;
	}
	public String getFormOzCategory() {
		return formOzCategory;
	}
	public void setFormOzCategory(String formOzCategory) {
		this.formOzCategory = formOzCategory;
	}

	public int getDisplayOrder() {
		return displayOrder;
	}
	public void setDisplayOrder(int displayOrder) {
		this.displayOrder = displayOrder;
	}
	public Date getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
	public String getCreatedUserId() {
		return createdUserId;
	}
	public void setCreatedUserId(String createdUserId) {
		this.createdUserId = createdUserId;
	}
	public String getCreatedUserName() {
		return createdUserName;
	}
	public void setCreatedUserName(String createdUserName) {
		this.createdUserName = createdUserName;
	}
	public Date getUpdatedDate() {
		return updatedDate;
	}
	public void setUpdatedDate(Date updatedDate) {
		this.updatedDate = updatedDate;
	}
	public String getUpdatedUserId() {
		return updatedUserId;
	}
	public void setUpdatedUserId(String updatedUserId) {
		this.updatedUserId = updatedUserId;
	}
	public String getUpdatedUserName() {
		return updatedUserName;
	}
	public void setUpdatedUserName(String updatedUserName) {
		this.updatedUserName = updatedUserName;
	}
	public String getUsedFlag() {
		return usedFlag;
	}
	public void setUsedFlag(String usedFlag) {
		this.usedFlag = usedFlag;
	}
	public String getPublicFlag() {
		return publicFlag;
	}
	public void setPublicFlag(String publicFlag) {
		this.publicFlag = publicFlag;
	}
}
