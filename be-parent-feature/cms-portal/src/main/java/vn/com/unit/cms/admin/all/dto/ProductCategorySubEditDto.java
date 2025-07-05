/*******************************************************************************
 * Class        ：ProductCategorySubEditDto
 * Created date ：2017/05/03
 * Lasted date  ：2017/05/03
 * Author       ：hand
 * Change log   ：2017/05/03：01-00 hand create a new
 ******************************************************************************/
package vn.com.unit.cms.admin.all.dto;

import java.util.Date;
import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import vn.com.unit.cms.core.utils.CmsUtils;
import vn.com.unit.ep2p.core.res.dto.DocumentActionReq;
import vn.com.unit.ep2p.dto.JProcessStepDto;
//import vn.com.unit.util.Util;

/**
 * ProductCategorySubEditDto
 * 
 * @version 01-00
 * @since 01-00
 * @author hand
 */
public class ProductCategorySubEditDto extends DocumentActionReq {

	/** requestToken */
	private String requestToken;

	/** id */
	private Long id;

	/** ProductTypeId */
	@NotNull
	private Long customerTypeId;

	/** code */
	// @Size(min = 1, max = 30)
	// @NotEmpty
	private String code;

	/** name */
	private String name;

	/** note */
	private String note;

	/** description */
	private String description;

	/** sort */
	private Long sort;

	/** processId */
	private Long processId;

	/** status */
	private Integer status;

	/** enabled */
	private boolean enabled;

	/** typeName */
	private String typeName;

	/** categoryName */
	private String categoryName;

	/** url */
	private String url;

	/** categoryId */
	private Long categoryId;

	/** categoryJsonHidden */
	private String categoryJsonHidden;

	/** categoryLanguageList */
	@Valid
	private List<ProductCategorySubLanguageDto> categoryLanguageList;

	private String approveBy;

	private String createBy;

	private String publishBy;

	private Long beforeId;

	/** comment */
	private String comment;

	/** imageName */
	private String imageName;

	/** physicalImg */
	private String physicalImg;

	/** imageHoverName */
	private String imageHoverName;

	/** physicalImgHover */
	private String physicalImgHover;

	/** icon name */
	private String iconImg;

	/** physical icon */
	private String physicalIcon;

	private String icon;

	/** bannerDesktop */
	private Long bannerDesktop;

	/** bannerMobile */
	private Long bannerMobile;

	private Boolean isPriority;

	private Integer indexLangActive;

	private String searchDto;

	private String languageCode;

	private List<JProcessStepDto> stepBtnList;

	/** button action */
	private String buttonAction;

//	/** Button id */
//	private String buttonId;

	/** Customer Alias */
	private String customerAlias;

	/** Status name */
	private String statusName;

	private String currItem;

	private String statusCode;

	/** Business code */
	private String businessCode;

	/** status */
	private Integer oldStatus;
	
    /** referenceType */
    private String referenceType;
    
    private Date updateDate;
    
    /** referenceId */
    private Long referenceId;

	/**
	 * Get id
	 * 
	 * @return Long
	 * @author hand
	 */
	public Long getId() {
		return id;
	}

	/**
	 * Set id
	 * 
	 * @param id type Long
	 * @return
	 * @author hand
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * Get customerTypeId
	 * 
	 * @return Long
	 * @author hand
	 */
	public Long getCustomerTypeId() {
		return customerTypeId;
	}

	/**
	 * Set customerTypeId
	 * 
	 * @param customerTypeId type Long
	 * @return
	 * @author hand
	 */
	public void setCustomerTypeId(Long customerTypeId) {
		this.customerTypeId = customerTypeId;
	}

	/**
	 * Get code
	 * 
	 * @return String
	 * @author hand
	 */
	public String getCode() {
		return code;
	}

	/**
	 * Set code
	 * 
	 * @param code type String
	 * @return
	 * @author hand
	 */
	public void setCode(String code) {
		this.code = CmsUtils.toUppercase(code);
	}

	/**
	 * Get name
	 * 
	 * @return String
	 * @author hand
	 */
	public String getName() {
		return name;
	}

	/**
	 * Set name
	 * 
	 * @param name type String
	 * @return
	 * @author hand
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Get note
	 * 
	 * @return String
	 * @author hand
	 */
	public String getNote() {
		return note;
	}

	/**
	 * Set note
	 * 
	 * @param note type String
	 * @return
	 * @author hand
	 */
	public void setNote(String note) {
		this.note = note;
	}

	/**
	 * Get description
	 * 
	 * @return String
	 * @author hand
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * Set description
	 * 
	 * @param description type String
	 * @return
	 * @author hand
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * Get sort
	 * 
	 * @return Long
	 * @author hand
	 */
	public Long getSort() {
		return sort;
	}

	/**
	 * Set sort
	 * 
	 * @param sort type Long
	 * @return
	 * @author hand
	 */
	public void setSort(Long sort) {
		this.sort = sort;
	}

	/**
	 * Get enabled
	 * 
	 * @return boolean
	 * @author hand
	 */
	public boolean isEnabled() {
		return enabled;
	}

	/**
	 * Set enabled
	 * 
	 * @param enabled type boolean
	 * @return
	 * @author hand
	 */
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	/**
	 * Get categoryLanguageList
	 * 
	 * @return List<ProductCategorySubLanguageDto>
	 * @author hand
	 */
	public List<ProductCategorySubLanguageDto> getCategoryLanguageList() {
		return categoryLanguageList;
	}

	/**
	 * Set categoryLanguageList
	 * 
	 * @param categoryLanguageList type List<ProductCategorySubLanguageDto>
	 * @return
	 * @author hand
	 */
	public void setCategoryLanguageList(List<ProductCategorySubLanguageDto> categoryLanguageList) {
		this.categoryLanguageList = categoryLanguageList;
	}

	/**
	 * Get typeName
	 * 
	 * @return String
	 * @author hand
	 */
	public String getTypeName() {
		return typeName;
	}

	/**
	 * Set typeName
	 * 
	 * @param typeName type String
	 * @return
	 * @author hand
	 */
	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	/**
	 * Get categoryName
	 * 
	 * @return String
	 * @author hand
	 */
	public String getCategoryName() {
		return categoryName;
	}

	/**
	 * Set categoryName
	 * 
	 * @param categoryName type String
	 * @return
	 * @author hand
	 */
	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	/**
	 * Get url
	 * 
	 * @return String
	 * @author hand
	 */
	public String getUrl() {
		return url;
	}

	/**
	 * Set url
	 * 
	 * @param url type String
	 * @return
	 * @author hand
	 */
	public void setUrl(String url) {
		this.url = url;
	}

	/**
	 * Get categoryId
	 * 
	 * @return Long
	 * @author hand
	 */
	public Long getCategoryId() {
		return categoryId;
	}

	/**
	 * Set categoryId
	 * 
	 * @param categoryId type Long
	 * @return
	 * @author hand
	 */
	public void setCategoryId(Long categoryId) {
		this.categoryId = categoryId;
	}

	/**
	 * Get categoryJsonHidden
	 * 
	 * @return String
	 * @author hand
	 */
	public String getCategoryJsonHidden() {
		return categoryJsonHidden;
	}

	/**
	 * Set categoryJsonHidden
	 * 
	 * @param categoryJsonHidden type String
	 * @return
	 * @author hand
	 */
	public void setCategoryJsonHidden(String categoryJsonHidden) {
		this.categoryJsonHidden = categoryJsonHidden;
	}

	public Long getProcessId() {
		return processId;
	}

	public void setProcessId(Long processId) {
		this.processId = processId;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getApproveBy() {
		return approveBy;
	}

	public void setApproveBy(String approveBy) {
		this.approveBy = approveBy;
	}

	public String getCreateBy() {
		return createBy;
	}

	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}

	public String getPublishBy() {
		return publishBy;
	}

	public void setPublishBy(String publishBy) {
		this.publishBy = publishBy;
	}

	public Long getBeforeId() {
		return beforeId;
	}

	public void setBeforeId(Long beforeId) {
		this.beforeId = beforeId;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public String getImageName() {
		return imageName;
	}

	public void setImageName(String imageName) {
		this.imageName = imageName;
	}

	public String getPhysicalImg() {
		return physicalImg;
	}

	public void setPhysicalImg(String physicalImg) {
		this.physicalImg = physicalImg;
	}

	public String getImageHoverName() {
		return imageHoverName;
	}

	public void setImageHoverName(String imageHoverName) {
		this.imageHoverName = imageHoverName;
	}

	public String getPhysicalImgHover() {
		return physicalImgHover;
	}

	public void setPhysicalImgHover(String physicalImgHover) {
		this.physicalImgHover = physicalImgHover;
	}

	public String getIconImg() {
		return iconImg;
	}

	public void setIconImg(String iconImg) {
		this.iconImg = iconImg;
	}

	public String getPhysicalIcon() {
		return physicalIcon;
	}

	public void setPhysicalIcon(String physicalIcon) {
		this.physicalIcon = physicalIcon;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public Long getBannerDesktop() {
		return bannerDesktop;
	}

	public void setBannerDesktop(Long bannerDesktop) {
		this.bannerDesktop = bannerDesktop;
	}

	public Long getBannerMobile() {
		return bannerMobile;
	}

	public void setBannerMobile(Long bannerMobile) {
		this.bannerMobile = bannerMobile;
	}

	public Boolean getIsPriority() {
		return isPriority;
	}

	public void setIsPriority(Boolean isPriority) {
		this.isPriority = isPriority;
	}

	public String getRequestToken() {
		return requestToken;
	}

	public void setRequestToken(String requestToken) {
		this.requestToken = requestToken;
	}

	public Integer getIndexLangActive() {
		return indexLangActive;
	}

	public void setIndexLangActive(Integer indexLangActive) {
		this.indexLangActive = indexLangActive;
	}

	/**
	 * Get searchDto
	 * 
	 * @return String
	 * @author taitm
	 */
	public String getSearchDto() {
		return searchDto;
	}

	/**
	 * Set searchDto
	 * 
	 * @param searchDto type String
	 * @return
	 * @author taitm
	 */
	public void setSearchDto(String searchDto) {
		this.searchDto = searchDto;
	}

	public String getLanguageCode() {
		return languageCode;
	}

	public void setLanguageCode(String languageCode) {
		this.languageCode = languageCode;
	}

	/**
	 * @return the stepBtnList
	 */
	public List<JProcessStepDto> getStepBtnList() {
		return stepBtnList;
	}

	/**
	 * @param stepBtnList the stepBtnList to set
	 */
	public void setStepBtnList(List<JProcessStepDto> stepBtnList) {
		this.stepBtnList = stepBtnList;
	}

	/**
	 * @return the buttonAction
	 */
	public String getButtonAction() {
		return buttonAction;
	}

	/**
	 * @param buttonAction the buttonAction to set
	 */
	public void setButtonAction(String buttonAction) {
		this.buttonAction = buttonAction;
	}

//	/**
//	 * @return the buttonId
//	 */
//	public String getButtonId() {
//		return buttonId;
//	}
//
//	/**
//	 * @param buttonId the buttonId to set
//	 */
//	public void setButtonId(String buttonId) {
//		this.buttonId = buttonId;
//	}

	/**
	 * @return the customerAlias
	 */
	public String getCustomerAlias() {
		return customerAlias;
	}

	/**
	 * @param customerAlias the customerAlias to set
	 */
	public void setCustomerAlias(String customerAlias) {
		this.customerAlias = customerAlias;
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
	 * @return the currItem
	 * @author taitm
	 */
	public String getCurrItem() {
		return currItem;
	}

	/**
	 * @param currItem the currItem to set
	 * @author taitm
	 */
	public void setCurrItem(String currItem) {
		this.currItem = currItem;
	}

	public String getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(String statusCode) {
		this.statusCode = statusCode;
	}

	/**
	 * @return the businessCode
	 */
	public String getBusinessCode() {
		return businessCode;
	}

	/**
	 * @param businessCode the businessCode to set
	 */
	public void setBusinessCode(String businessCode) {
		this.businessCode = businessCode;
	}

	/**
	 * @return the oldStatus
	 */
	public Integer getOldStatus() {
		return oldStatus;
	}

	/**
	 * @param oldStatus the oldStatus to set
	 */
	public void setOldStatus(Integer oldStatus) {
        this.oldStatus = oldStatus;
    }

    /**
     * @return the referenceType
     * @author taitm
     */
    public String getReferenceType() {
        return referenceType;
    }

    /**
     * @param referenceType
     *            the referenceType to set
     * @author taitm
     */
    public void setReferenceType(String referenceType) {
        this.referenceType = referenceType;
    }

    /**
     * @return the updateDate
     * @author taitm
     */
    public Date getUpdateDate() {
        return updateDate;
    }

    /**
     * @param updateDate the updateDate to set
     * @author taitm
     */
    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

	public Long getReferenceId() {
		return referenceId;
	}

	public void setReferenceId(Long referenceId) {
		this.referenceId = referenceId;
	}
    
    

}
