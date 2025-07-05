/*******************************************************************************
 * Class        :SvcBoardDto
 * Created date :2019/04/22
 * Lasted date  :2019/04/22
 * Author       :HungHT
 * Change log   :2019/04/22:01-00 HungHT create a new
 ******************************************************************************/
package vn.com.unit.ep2p.dto;

import java.util.Date;

/**
 * SvcBoardDto
 * @version 01-00
 * @since 01-00
 * @author HungHT
 */
public class PPLSvcBoardDto {
	 
	private Long id;
	private Long companyId;
	private Long categoryId;
	private Long categoryOrder;
	private String categoryName;
	private String functionCode;
	private String name;
	private String description;
	private String fileName;
	private String image;
	private Long repositoryId;
	private Long displayOrder;
	private String deviceType;
	private String activeFlag;
	private String createdBy;
	private Date createdDate;
	private String updatedBy;
	private Date updatedDate;
	private String deletedBy;
	private Date deletedDate;
	private String businessCode;
	private String businessName;
	private Long  businessId;
	private String formType;
	private String formTypeName;
	private String processType;
	private String integUrl;

	/**
	* Set id
	* @param id
	*        type Long
	* @return
	* @author HungHT
	*/
	public void setId(Long id) {
		this.id = id;
	}

	/**
	* Get id
	* @return Long
	* @author HungHT
	*/
	public Long getId() {
		return id;
	}

	/**
	* Set companyId
	* @param companyId
	*        type Long
	* @return
	* @author HungHT
	*/
	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
	}

	/**
	* Get companyId
	* @return Long
	* @author HungHT
	*/
	public Long getCompanyId() {
		return companyId;
	}

	/**
	* Set categoryId
	* @param categoryId
	*        type Long
	* @return
	* @author HungHT
	*/
	public void setCategoryId(Long categoryId) {
		this.categoryId = categoryId;
	}

	/**
	* Get categoryId
	* @return Long
	* @author HungHT
	*/
	public Long getCategoryId() {
		return categoryId;
	}

    /**
     * Get categoryOrder
     * @return Long
     * @author HungHT
     */
    public Long getCategoryOrder() {
        return categoryOrder;
    }

    /**
     * Set categoryOrder
     * @param   categoryOrder
     *          type Long
     * @return
     * @author  HungHT
     */
    public void setCategoryOrder(Long categoryOrder) {
        this.categoryOrder = categoryOrder;
    }

    /**
     * Get categoryName
     * @return String
     * @author HungHT
     */
    public String getCategoryName() {
        return categoryName;
    }

    /**
     * Set categoryName
     * @param   categoryName
     *          type String
     * @return
     * @author  HungHT
     */
    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    /**
	* Set functionCode
	* @param functionCode
	*        type String
	* @return
	* @author HungHT
	*/
	public void setFunctionCode(String functionCode) {
		this.functionCode = functionCode;
	}

	/**
	* Get functionCode
	* @return String
	* @author HungHT
	*/
	public String getFunctionCode() {
		return functionCode;
	}

	/**
	* Set name
	* @param name
	*        type String
	* @return
	* @author HungHT
	*/
	public void setName(String name) {
		this.name = name;
	}

	/**
	* Get name
	* @return String
	* @author HungHT
	*/
	public String getName() {
		return name;
	}

	/**
	* Set description
	* @param description
	*        type String
	* @return
	* @author HungHT
	*/
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	* Get description
	* @return String
	* @author HungHT
	*/
	public String getDescription() {
		return description;
	}

	/**
	* Set fileName
	* @param fileName
	*        type String
	* @return
	* @author HungHT
	*/
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	/**
	* Get fileName
	* @return String
	* @author HungHT
	*/
	public String getFileName() {
		return fileName;
	}

	/**
	* Set image
	* @param image
	*        type String
	* @return
	* @author HungHT
	*/
	public void setImage(String image) {
		this.image = image;
	}

	/**
	* Get image
	* @return String
	* @author HungHT
	*/
	public String getImage() {
		return image;
	}

    /**
     * Get repositoryId
     * @return Long
     * @author HungHT
     */
    public Long getRepositoryId() {
        return repositoryId;
    }

    /**
     * Set repositoryId
     * @param   repositoryId
     *          type Long
     * @return
     * @author  HungHT
     */
    public void setRepositoryId(Long repositoryId) {
        this.repositoryId = repositoryId;
    }

    /**
	* Set displayOrder
	* @param displayOrder
	*        type Long
	* @return
	* @author HungHT
	*/
	public void setDisplayOrder(Long displayOrder) {
		this.displayOrder = displayOrder;
	}

	/**
	* Get displayOrder
	* @return Long
	* @author HungHT
	*/
	public Long getDisplayOrder() {
		return displayOrder;
	}

	/**
	* Set deviceType
	* @param deviceType
	*        type String
	* @return
	* @author HungHT
	*/
	public void setDeviceType(String deviceType) {
		this.deviceType = deviceType;
	}

	/**
	* Get deviceType
	* @return String
	* @author HungHT
	*/
	public String getDeviceType() {
		return deviceType;
	}

	/**
	* Set activeFlag
	* @param activeFlag
	*        type String
	* @return
	* @author HungHT
	*/
	public void setActiveFlag(String activeFlag) {
		this.activeFlag = activeFlag;
	}

	/**
	* Get activeFlag
	* @return String
	* @author HungHT
	*/
	public String getActiveFlag() {
		return activeFlag;
	}

	/**
	* Set createdBy
	* @param createdBy
	*        type String
	* @return
	* @author HungHT
	*/
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	/**
	* Get createdBy
	* @return String
	* @author HungHT
	*/
	public String getCreatedBy() {
		return createdBy;
	}

	/**
	* Set createdDate
	* @param createdDate
	*        type Date
	* @return
	* @author HungHT
	*/
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	/**
	* Get createdDate
	* @return Date
	* @author HungHT
	*/
	public Date getCreatedDate() {
		return createdDate;
	}

	/**
	* Set updatedBy
	* @param updatedBy
	*        type String
	* @return
	* @author HungHT
	*/
	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}

	/**
	* Get updatedBy
	* @return String
	* @author HungHT
	*/
	public String getUpdatedBy() {
		return updatedBy;
	}

	/**
	* Set updatedDate
	* @param updatedDate
	*        type Date
	* @return
	* @author HungHT
	*/
	public void setUpdatedDate(Date updatedDate) {
		this.updatedDate = updatedDate;
	}

	/**
	* Get updatedDate
	* @return Date
	* @author HungHT
	*/
	public Date getUpdatedDate() {
		return updatedDate;
	}

	/**
	* Set deletedBy
	* @param deletedBy
	*        type String
	* @return
	* @author HungHT
	*/
	public void setDeletedBy(String deletedBy) {
		this.deletedBy = deletedBy;
	}

	/**
	* Get deletedBy
	* @return String
	* @author HungHT
	*/
	public String getDeletedBy() {
		return deletedBy;
	}

	/**
	* Set deletedDate
	* @param deletedDate
	*        type Date
	* @return
	* @author HungHT
	*/
	public void setDeletedDate(Date deletedDate) {
		this.deletedDate = deletedDate;
	}

	/**
	* Get deletedDate
	* @return Date
	* @author HungHT
	*/
	public Date getDeletedDate() {
		return deletedDate;
	}

	/**
	* Set businessCode
	* @param businessCode
	*        type String
	* @return
	* @author HungHT
	*/
	public void setBusinessCode(String businessCode) {
		this.businessCode = businessCode;
	}

	/**
	* Get businessCode
	* @return String
	* @author HungHT
	*/
	public String getBusinessCode() {
		return businessCode;
	}

	/**
	 * Get businessName
	 * @return String
	 * @author taitt
	 */
	public String getBusinessName() {
		return businessName;
	}

	/**
	 * Set businessName
	 * @param   businessName
	 *          type String
	 * @return
	 * @author  taitt
	 */
	public void setBusinessName(String businessName) {
		this.businessName = businessName;
	}

	/**
	 * Get businessId
	 * @return Long
	 * @author taitt
	 */
	public Long getBusinessId() {
		return businessId;
	}

	/**
	 * Set businessId
	 * @param   businessId
	 *          type Long
	 * @return
	 * @author  taitt
	 */
	public void setBusinessId(Long businessId) {
		this.businessId = businessId;
	}

    /**
     * getFormType
     * @return
     * @author trieuvd
     */
    public String getFormType() {
        return formType;
    }
    
    /**
     * setFormType
     * @param formType
     * @author trieuvd
     */
    public void setFormType(String formType) {
        this.formType = formType;
    }
    
    /**
     * getFormTypeName
     * @return
     * @author trieuvd
     */
    public String getFormTypeName() {
        return formTypeName;
    }

    /**
     * setFormTypeName
     * @param formTypeName
     * @author trieuvd
     */
    public void setFormTypeName(String formTypeName) {
        this.formTypeName = formTypeName;
    }

    /**
     * Get processType
     * @return String
     * @author trieuvd
     */
    public String getProcessType() {
        return processType;
    }

    /**
     * Set processType
     * @param   processType
     *          type String
     * @return
     * @author  trieuvd
     */
    public void setProcessType(String processType) {
        this.processType = processType;
    }

    public String getIntegUrl() {
        return integUrl;
    }

    public void setIntegUrl(String integUrl) {
        this.integUrl = integUrl;
    }

}