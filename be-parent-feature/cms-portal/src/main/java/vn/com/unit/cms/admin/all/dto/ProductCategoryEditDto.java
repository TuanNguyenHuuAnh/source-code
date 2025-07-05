/*******************************************************************************
 * Class        ：ProductCategoryEditDto
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

import vn.com.unit.cms.admin.all.jcanary.dto.HistoryApproveDto;
import vn.com.unit.cms.core.utils.CmsUtils;
import vn.com.unit.ep2p.admin.dto.SortOrderDto;
import vn.com.unit.ep2p.core.res.dto.DocumentActionReq;
import vn.com.unit.ep2p.dto.JProcessStepDto;
//import vn.com.unit.util.Util;

/**
 * ProductCategoryEditDto
 * 
 * @version 01-00
 * @since 01-00
 * @author hand
 */
public class ProductCategoryEditDto extends DocumentActionReq {

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

    /** sort */
    private Long sort;

    /** enabled */
    private boolean enabled;

    /** categoryLanguageList */
    @Valid
    private List<ProductCategoryLanguageDto> categoryLanguageList;

    /** typeName */
    private String typeName;

    /** url */
    private String url;

    /** processId */
    private Long processId;

    /** status */
    private Integer status;

    /** referenceId */
    private Long referenceId;

    /** referenceType */
    private String referenceType;

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

    @Valid
    private List<HistoryApproveDto> historyApproveDtos;

    /** bannerDesktop */
    private Long bannerDesktop;

    /** bannerMobile */
    private Long bannerMobile;

    /** promotion */
    private boolean promotion;

    /** promotion */
    private List<SortOrderDto> sortOderList;

    /** linkAlias */
    private String linkAlias;

    private String approveBy;

    private String createBy;

    private String publishBy;

    private Long beforeId;

    private List<ProductCategoryEditDto> lstProductTypeToSort;

    private String searchDto;
    
    private String languageCode;

    private Integer indexLangActive;
    
    private List<JProcessStepDto> stepBtnList;
    
    /** button action*/
    private String buttonAction;
    
//    /** Button id */
//    private String buttonId;
    
    /** Customer Alias */
    private String customerAlias;
    
    /** Status name */
    private String statusName;
    
    /** Business code */
    private String businessCode;
    
    private String currItem;
    
    /** status */
    private Integer oldStatus;
    
    private String statusCode;
    
    private Date updateDate;
    
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
     * @param id
     *            type Long
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
     * @param customerTypeId
     *            type Long
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
     * @param code
     *            type String
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
     * @param name
     *            type String
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
     * @param note
     *            type String
     * @return
     * @author hand
     */
    public void setNote(String note) {
        this.note = note;
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
     * @param sort
     *            type Long
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
     * @param enabled
     *            type boolean
     * @return
     * @author hand
     */
    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    /**
     * Get categoryLanguageList
     * 
     * @return List<ProductCategoryLanguageDto>
     * @author hand
     */
    public List<ProductCategoryLanguageDto> getCategoryLanguageList() {
        return categoryLanguageList;
    }

    /**
     * Set categoryLanguageList
     * 
     * @param categoryLanguageList
     *            type List<ProductCategoryLanguageDto>
     * @return
     * @author hand
     */
    public void setCategoryLanguageList(List<ProductCategoryLanguageDto> categoryLanguageList) {
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
     * @param typeName
     *            type String
     * @return
     * @author hand
     */
    public void setTypeName(String typeName) {
        this.typeName = typeName;
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
     * @param url
     *            type String
     * @return
     * @author hand
     */
    public void setUrl(String url) {
        this.url = url;
    }

    /**
     * Get processId
     * 
     * @return Long
     * @author hand
     */
    public Long getProcessId() {
        return processId;
    }

    /**
     * Set processId
     * 
     * @param processId
     *            type Long
     * @return
     * @author hand
     */
    public void setProcessId(Long processId) {
        this.processId = processId;
    }

    /**
     * Get status
     * 
     * @return Integer
     * @author hand
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * Set status
     * 
     * @param status
     *            type Integer
     * @return
     * @author hand
     */
    public void setStatus(Integer status) {
        this.status = status;
    }

    /**
     * Get referenceId
     * 
     * @return Long
     * @author hand
     */
    public Long getReferenceId() {
        return referenceId;
    }

    /**
     * Set referenceId
     * 
     * @param referenceId
     *            type Long
     * @return
     * @author hand
     */
    public void setReferenceId(Long referenceId) {
        this.referenceId = referenceId;
    }

    /**
     * Get referenceType
     * 
     * @return String
     * @author hand
     */
    public String getReferenceType() {
        return referenceType;
    }

    /**
     * Set referenceType
     * 
     * @param referenceType
     *            type String
     * @return
     * @author hand
     */
    public void setReferenceType(String referenceType) {
        this.referenceType = referenceType;
    }

    /**
     * Get comment
     * 
     * @return String
     * @author hand
     */
    public String getComment() {
        return comment;
    }

    /**
     * Set comment
     * 
     * @param comment
     *            type String
     * @return
     * @author hand
     */
    public void setComment(String comment) {
        this.comment = comment;
    }

    /**
     * Get historyApproveDtos
     * 
     * @return List<HistoryApproveDto>
     * @author hand
     */
    public List<HistoryApproveDto> getHistoryApproveDtos() {
        return historyApproveDtos;
    }

    /**
     * Set historyApproveDtos
     * 
     * @param historyApproveDtos
     *            type List<HistoryApproveDto>
     * @return
     * @author hand
     */
    public void setHistoryApproveDtos(List<HistoryApproveDto> historyApproveDtos) {
        this.historyApproveDtos = historyApproveDtos;
    }

    /**
     * Get imageName
     * 
     * @return String
     * @author hand
     */
    public String getImageName() {
        return imageName;
    }

    /**
     * Set imageName
     * 
     * @param imageName
     *            type String
     * @return
     * @author hand
     */
    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    /**
     * Get physicalImg
     * 
     * @return String
     * @author hand
     */
    public String getPhysicalImg() {
        return physicalImg;
    }

    /**
     * Set physicalImg
     * 
     * @param physicalImg
     *            type String
     * @return
     * @author hand
     */
    public void setPhysicalImg(String physicalImg) {
        this.physicalImg = physicalImg;
    }

    /**
     * Get imageHoverName
     * 
     * @return String
     * @author hand
     */
    public String getImageHoverName() {
        return imageHoverName;
    }

    /**
     * Set imageHoverName
     * 
     * @param imageHoverName
     *            type String
     * @return
     * @author hand
     */
    public void setImageHoverName(String imageHoverName) {
        this.imageHoverName = imageHoverName;
    }

    /**
     * Get physicalImgHover
     * 
     * @return String
     * @author hand
     */
    public String getPhysicalImgHover() {
        return physicalImgHover;
    }

    /**
     * Set physicalImgHover
     * 
     * @param physicalImgHover
     *            type String
     * @return
     * @author hand
     */
    public void setPhysicalImgHover(String physicalImgHover) {
        this.physicalImgHover = physicalImgHover;
    }

    /**
     * Get iconImg
     * 
     * @return String
     * @author hand
     */
    public String getIconImg() {
        return iconImg;
    }

    /**
     * Set iconImg
     * 
     * @param iconImg
     *            type String
     * @return
     * @author hand
     */
    public void setIconImg(String iconImg) {
        this.iconImg = iconImg;
    }

    /**
     * Get physicalIcon
     * 
     * @return String
     * @author hand
     */
    public String getPhysicalIcon() {
        return physicalIcon;
    }

    /**
     * Set physicalIcon
     * 
     * @param physicalIcon
     *            type String
     * @return
     * @author hand
     */
    public void setPhysicalIcon(String physicalIcon) {
        this.physicalIcon = physicalIcon;
    }

    /**
     * Get bannerDesktop
     * 
     * @return Long
     * @author hand
     */
    public Long getBannerDesktop() {
        return bannerDesktop;
    }

    /**
     * Get bannerMobile
     * 
     * @return Long
     * @author hand
     */
    public Long getBannerMobile() {
        return bannerMobile;
    }

    /**
     * Set bannerDesktop
     * 
     * @param bannerDesktop
     *            type Long
     * @return
     * @author hand
     */
    public void setBannerDesktop(Long bannerDesktop) {
        this.bannerDesktop = bannerDesktop;
    }

    /**
     * Set bannerMobile
     * 
     * @param bannerMobile
     *            type Long
     * @return
     * @author hand
     */
    public void setBannerMobile(Long bannerMobile) {
        this.bannerMobile = bannerMobile;
    }

    /**
     * Get promotion
     * 
     * @return boolean
     * @author hand
     */
    public boolean isPromotion() {
        return promotion;
    }

    /**
     * Set promotion
     * 
     * @param promotion
     *            type boolean
     * @return
     * @author hand
     */
    public void setPromotion(boolean promotion) {
        this.promotion = promotion;
    }

    /**
     * Get sortOderList
     * 
     * @return List<SortOrderDto>
     * @author hand
     */
    public List<SortOrderDto> getSortOderList() {
        return sortOderList;
    }

    /**
     * Set sortOderList
     * 
     * @param sortOderList
     *            type List<SortOrderDto>
     * @return
     * @author hand
     */
    public void setSortOderList(List<SortOrderDto> sortOderList) {
        this.sortOderList = sortOderList;
    }

    /**
     * @return the linkAlias
     */
    public String getLinkAlias() {
        return linkAlias;
    }

    /**
     * @param linkAlias
     *            the linkAlias to set
     */
    public void setLinkAlias(String linkAlias) {
        this.linkAlias = linkAlias;
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

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public List<ProductCategoryEditDto> getLstProductTypeToSort() {
        return lstProductTypeToSort;
    }

    public void setLstProductTypeToSort(List<ProductCategoryEditDto> lstProductTypeToSort) {
        this.lstProductTypeToSort = lstProductTypeToSort;
    }

    public String getRequestToken() {
        return requestToken;
    }

    public void setRequestToken(String requestToken) {
        this.requestToken = requestToken;
    }

    /**
     * Get searchDto
     * 
     * @return ProductCategorySearchDto
     * @author taitm
     */
    public String getSearchDto() {
        return searchDto;
    }

    /**
     * Set searchDto
     * 
     * @param searchDto
     *            type ProductCategorySearchDto
     * @return
     * @author taitm
     */
    public void setSearchDto(String searchDto) {
        this.searchDto = searchDto;
    }

    public Integer getIndexLangActive() {
        return indexLangActive;
    }

    public void setIndexLangActive(Integer indexLangActive) {
        this.indexLangActive = indexLangActive;
    }

	public List<JProcessStepDto> getStepBtnList() {
		return stepBtnList;
	}

	public void setStepBtnList(List<JProcessStepDto> stepBtnList) {
		this.stepBtnList = stepBtnList;
	}

	public String getButtonAction() {
		return buttonAction;
	}

	public void setButtonAction(String buttonAction) {
		this.buttonAction = buttonAction;
	}

//	public String getButtonId() {
//		return buttonId;
//	}
//
//	public void setButtonId(String buttonId) {
//		this.buttonId = buttonId;
//	}

	public String getCustomerAlias() {
		return customerAlias;
	}

	public void setCustomerAlias(String customerAlias) {
		this.customerAlias = customerAlias;
	}

	public String getStatusName() {
		return statusName;
	}

	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}

    public String getLanguageCode() {
        return languageCode;
    }

    public void setLanguageCode(String languageCode) {
        this.languageCode = languageCode;
    }

	public String getBusinessCode() {
		return businessCode;
	}

	public void setBusinessCode(String businessCode) {
		this.businessCode = businessCode;
	}

	public String getCurrItem() {
		return currItem;
	}

	public void setCurrItem(String currItem) {
		this.currItem = currItem;
	}

	public Integer getOldStatus() {
		return oldStatus;
	}

	public void setOldStatus(Integer oldStatus) {
		this.oldStatus = oldStatus;
	}

	public String getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(String statusCode) {
		this.statusCode = statusCode;
	}

    /**
     * @return the updateDate
     * @author taitm
     */
    public Date getUpdateDate() {
        return updateDate;
    }

    /**
     * @param updateDate
     *            the updateDate to set
     * @author taitm
     */
    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

}
