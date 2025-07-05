/*******************************************************************************
 * Class        :EfoFormDto
 * Created date :2019/04/17
 * Lasted date  :2019/04/17
 * Author       :HungHT
 * Change log   :2019/04/17:01-00 HungHT create a new
 ******************************************************************************/
package vn.com.unit.ep2p.dto;

import java.util.Date;

/**
 * EfoFormDto
 * 
 * @version 01-00
 * @since 01-00
 * @author HungHT
 */
public class FormDto {

    private Long id;
    private Long companyId;
    private Long categoryId;
    private String functionCode;
    private String name;
    private String description;
    private String fileName;
    private String image;
    private Long displayOrder;
    private String deviceType;
    private String activeFlag;
    private String createdBy;
    private Date createdDate;
    private String updatedBy;
    private Date updatedDate;
    private String deletedBy;
    private Date deletedDate;

    private Long jpmBusinessId;
    private String formType;
    private String multiRecruiting;

    /**
     * Set id
     * 
     * @param id
     *            type Long
     * @return
     * @author HungHT
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Get id
     * 
     * @return Long
     * @author HungHT
     */
    public Long getId() {
        return id;
    }

    /**
     * Set companyId
     * 
     * @param companyId
     *            type Long
     * @return
     * @author HungHT
     */
    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }

    /**
     * Get companyId
     * 
     * @return Long
     * @author HungHT
     */
    public Long getCompanyId() {
        return companyId;
    }

    /**
     * Set categoryId
     * 
     * @param categoryId
     *            type Long
     * @return
     * @author HungHT
     */
    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    /**
     * Get categoryId
     * 
     * @return Long
     * @author HungHT
     */
    public Long getCategoryId() {
        return categoryId;
    }

    /**
     * Set functionCode
     * 
     * @param functionCode
     *            type String
     * @return
     * @author HungHT
     */
    public void setFunctionCode(String functionCode) {
        this.functionCode = functionCode;
    }

    /**
     * Get functionCode
     * 
     * @return String
     * @author HungHT
     */
    public String getFunctionCode() {
        return functionCode;
    }

    /**
     * Set name
     * 
     * @param name
     *            type String
     * @return
     * @author HungHT
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Get name
     * 
     * @return String
     * @author HungHT
     */
    public String getName() {
        return name;
    }

    /**
     * Set description
     * 
     * @param description
     *            type String
     * @return
     * @author HungHT
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Get description
     * 
     * @return String
     * @author HungHT
     */
    public String getDescription() {
        return description;
    }

    /**
     * Set fileName
     * 
     * @param fileName
     *            type String
     * @return
     * @author HungHT
     */
    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    /**
     * Get fileName
     * 
     * @return String
     * @author HungHT
     */
    public String getFileName() {
        return fileName;
    }

    /**
     * Set image
     * 
     * @param image
     *            type String
     * @return
     * @author HungHT
     */
    public void setImage(String image) {
        this.image = image;
    }

    /**
     * Get image
     * 
     * @return String
     * @author HungHT
     */
    public String getImage() {
        return image;
    }

    /**
     * Set displayOrder
     * 
     * @param displayOrder
     *            type Long
     * @return
     * @author HungHT
     */
    public void setDisplayOrder(Long displayOrder) {
        this.displayOrder = displayOrder;
    }

    /**
     * Get displayOrder
     * 
     * @return Long
     * @author HungHT
     */
    public Long getDisplayOrder() {
        return displayOrder;
    }

    /**
     * Set deviceType
     * 
     * @param deviceType
     *            type String
     * @return
     * @author HungHT
     */
    public void setDeviceType(String deviceType) {
        this.deviceType = deviceType;
    }

    /**
     * Get deviceType
     * 
     * @return String
     * @author HungHT
     */
    public String getDeviceType() {
        return deviceType;
    }

    /**
     * Set activeFlag
     * 
     * @param activeFlag
     *            type String
     * @return
     * @author HungHT
     */
    public void setActiveFlag(String activeFlag) {
        this.activeFlag = activeFlag;
    }

    /**
     * Get activeFlag
     * 
     * @return String
     * @author HungHT
     */
    public String getActiveFlag() {
        return activeFlag;
    }

    /**
     * Set createdBy
     * 
     * @param createdBy
     *            type String
     * @return
     * @author HungHT
     */
    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    /**
     * Get createdBy
     * 
     * @return String
     * @author HungHT
     */
    public String getCreatedBy() {
        return createdBy;
    }

    /**
     * Set createdDate
     * 
     * @param createdDate
     *            type Date
     * @return
     * @author HungHT
     */
    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    /**
     * Get createdDate
     * 
     * @return Date
     * @author HungHT
     */
    public Date getCreatedDate() {
        return createdDate;
    }

    /**
     * Set updatedBy
     * 
     * @param updatedBy
     *            type String
     * @return
     * @author HungHT
     */
    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    /**
     * Get updatedBy
     * 
     * @return String
     * @author HungHT
     */
    public String getUpdatedBy() {
        return updatedBy;
    }

    /**
     * Set updatedDate
     * 
     * @param updatedDate
     *            type Date
     * @return
     * @author HungHT
     */
    public void setUpdatedDate(Date updatedDate) {
        this.updatedDate = updatedDate;
    }

    /**
     * Get updatedDate
     * 
     * @return Date
     * @author HungHT
     */
    public Date getUpdatedDate() {
        return updatedDate;
    }

    /**
     * Set deletedBy
     * 
     * @param deletedBy
     *            type String
     * @return
     * @author HungHT
     */
    public void setDeletedBy(String deletedBy) {
        this.deletedBy = deletedBy;
    }

    /**
     * Get deletedBy
     * 
     * @return String
     * @author HungHT
     */
    public String getDeletedBy() {
        return deletedBy;
    }

    /**
     * Set deletedDate
     * 
     * @param deletedDate
     *            type Date
     * @return
     * @author HungHT
     */
    public void setDeletedDate(Date deletedDate) {
        this.deletedDate = deletedDate;
    }

    /**
     * Get deletedDate
     * 
     * @return Date
     * @author HungHT
     */
    public Date getDeletedDate() {
        return deletedDate;
    }

    public Long getJpmBusinessId() {
        return jpmBusinessId;
    }

    public void setJpmBusinessId(Long jpmBusinessId) {
        this.jpmBusinessId = jpmBusinessId;
    }

    public String getFormType() {
        return formType;
    }

    public void setFormType(String formType) {
        this.formType = formType;
    }

	public String getMultiRecruiting() {
		return multiRecruiting;
	}

	public void setMultiRecruiting(String multiRecruiting) {
		this.multiRecruiting = multiRecruiting;
	}

}