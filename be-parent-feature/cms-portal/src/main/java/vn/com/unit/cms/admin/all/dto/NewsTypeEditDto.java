/*******************************************************************************
 * Class        ：NewsTypeEditDto
 * Created date ：2017/03/01
 * Lasted date  ：2017/03/01
 * Author       ：hand
 * Change log   ：2017/03/01：01-00 hand create a new
 ******************************************************************************/
package vn.com.unit.cms.admin.all.dto;

import java.util.Date;
import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.Size;

import vn.com.unit.cms.admin.all.jcanary.dto.HistoryApproveDto;
import vn.com.unit.cms.core.utils.CmsUtils;
import vn.com.unit.ep2p.dto.JProcessStepDto;
import vn.com.unit.ep2p.admin.dto.SortOrderDto;
//import vn.com.unit.util.Util;
import vn.com.unit.ep2p.core.res.dto.DocumentActionReq;

/**
 * NewsTypeEditDto
 * 
 * @version 01-00
 * @since 01-00
 * @author hand
 */
public class NewsTypeEditDto extends DocumentActionReq {

    /** id */
    private Long id;

    /** ProductTypeId */
    private Long customerTypeId;

    /** code */
    // @Size(min = 1, max = 30)
    // @NotEmpty
    private String code;
    
    private String requestToken;
    
    /** imageName */
    private String imageName;

    /** physicalImg */
    private String physicalImg;
    
    private boolean typeOfLibrary;
    
    private boolean typeOfNew;

    /** name */
    @Size(min = 1, max = 255)
    private String name;

    /** description */
    private String description;

    /** note */
    private String note;

    /** sort */
    private Long sort;

    /** enabled */
    private boolean enabled;
    private boolean hasDisableCheckEnabled;

    /** typeLanguageList */
    @Valid
    private List<NewsTypeLanguageDto> typeLanguageList;

    /** url */
    private String url;

    /** customerTypeName */
    private String customerTypeName;

    /** isPromotion */
    private boolean promotion;

    /** linkAlias */
    private String linkAlias;

    /** promotion */
    private List<SortOrderDto> sortOderList;
    
    private String createdBy;
    private String approvedBy;
    private String publishedBy;
    private Long beforeId;
    private String comment;

    private int numberNewsCategory;
    private int numbereNews;

    private String lang;
    private Integer indexLangActive;
    
    private String searchDto;
    
    /** processId */
    private Long processId;

    /** status */
    private Integer status;
    
    /** referenceId */
    private Long referenceId;

    /** referenceType */
    private String referenceType;
    
    @Valid
    private List<HistoryApproveDto> historyApproveDtos;
    
    private List<JProcessStepDto> stepBtnList;
    
    /** button action*/
    private String buttonAction;
    
    /** Button id */
    private Long buttonId;
    
    /** Customer Alias */
    private String customerAlias;
    
    /** Status name */
    private String statusName;
    
    /** Business code */
    private String businessCode;
    
    private String currItem;
    
    /** status */
    private Integer oldStatus;
    
    private Date updateDate;
    
    private String buttonCode;

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
     * Get id
     * 
     * @return Long
     * @author hand
     */
    public Long getId() {
        return id;
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
     * Get name
     * 
     * @return String
     * @author hand
     */
    public String getName() {
        return name;
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
     * Get note
     * 
     * @return String
     * @author hand
     */
    public String getNote() {
        return note;
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
     * Get enabled
     * 
     * @return boolean
     * @author hand
     */
    public boolean isEnabled() {
        return enabled;
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
     * Set description
     * 
     * @param description
     *            type String
     * @return
     * @author hand
     */
    public void setDescription(String description) {
        this.description = description;
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
     * Get typeLanguageList
     * 
     * @return List<NewsTypeLanguageDto>
     * @author hand
     */
    public List<NewsTypeLanguageDto> getTypeLanguageList() {
        return typeLanguageList;
    }

    /**
     * Set typeLanguageList
     * 
     * @param typeLanguageList
     *            type List<NewsTypeLanguageDto>
     * @return
     * @author hand
     */
    public void setTypeLanguageList(List<NewsTypeLanguageDto> typeLanguageList) {
        this.typeLanguageList = typeLanguageList;
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
     * Get customerTypeName
     * 
     * @return String
     * @author hand
     */
    public String getCustomerTypeName() {
        return customerTypeName;
    }

    /**
     * Set customerTypeName
     * 
     * @param customerTypeName
     *            type String
     * @return
     * @author hand
     */
    public void setCustomerTypeName(String customerTypeName) {
        this.customerTypeName = customerTypeName;
    }

    public String getLinkAlias() {
        return linkAlias;
    }

    public void setLinkAlias(String linkAlias) {
        this.linkAlias = linkAlias;
    }

    /**
     * @return the sortOderList
     */
    public List<SortOrderDto> getSortOderList() {
        return sortOderList;
    }

    /**
     * @param sortOderList
     *            the sortOderList to set
     */
    public void setSortOderList(List<SortOrderDto> sortOderList) {
        this.sortOderList = sortOderList;
    }

    /**
     * Get createdBy
     * 
     * @return String
     * @author taitm
     */
    public String getCreatedBy() {
        return createdBy;
    }

    /**
     * Set createdBy
     * 
     * @param createdBy
     *            type String
     * @return
     * @author taitm
     */
    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    /**
     * Get approvedBy
     * 
     * @return String
     * @author taitm
     */
    public String getApprovedBy() {
        return approvedBy;
    }

    /**
     * Set approvedBy
     * 
     * @param approvedBy
     *            type String
     * @return
     * @author taitm
     */
    public void setApprovedBy(String approvedBy) {
        this.approvedBy = approvedBy;
    }

    /**
     * Get publishedBy
     * 
     * @return String
     * @author taitm
     */
    public String getPublishedBy() {
        return publishedBy;
    }

    /**
     * Set publishedBy
     * 
     * @param publishedBy
     *            type String
     * @return
     * @author taitm
     */
    public void setPublishedBy(String publishedBy) {
        this.publishedBy = publishedBy;
    }

    /**
     * Get beforeId
     * 
     * @return Long
     * @author taitm
     */
    public Long getBeforeId() {
        return beforeId;
    }

    /**
     * Set beforeId
     * 
     * @param beforeId
     *            type Long
     * @return
     * @author taitm
     */
    public void setBeforeId(Long beforeId) {
        this.beforeId = beforeId;
    }

    /**
     * Get comment
     * 
     * @return String
     * @author taitm
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
     * @author taitm
     */
    public void setComment(String comment) {
        this.comment = comment;
    }

    /**
     * Get numberNewsCategory
     * 
     * @return int
     * @author taitm
     */
    public int getNumberNewsCategory() {
        return numberNewsCategory;
    }

    /**
     * Set numberNewsCategory
     * 
     * @param numberNewsCategory
     *            type int
     * @return
     * @author taitm
     */
    public void setNumberNewsCategory(int numberNewsCategory) {
        this.numberNewsCategory = numberNewsCategory;
    }

    /**
     * Get numbereNews
     * 
     * @return int
     * @author taitm
     */
    public int getNumbereNews() {
        return numbereNews;
    }

    /**
     * Set numbereNews
     * 
     * @param numbereNews
     *            type int
     * @return
     * @author taitm
     */
    public void setNumbereNews(int numbereNews) {
        this.numbereNews = numbereNews;
    }

    /**
     * Get lang
     * 
     * @return String
     * @author taitm
     */
    public String getLang() {
        return lang;
    }

    /**
     * Set lang
     * 
     * @param lang
     *            type String
     * @return
     * @author taitm
     */
    public void setLang(String lang) {
        this.lang = lang;
    }

    public boolean isHasDisableCheckEnabled() {
        return hasDisableCheckEnabled;
    }

    public void setHasDisableCheckEnabled(boolean hasDisableCheckEnabled) {
        this.hasDisableCheckEnabled = hasDisableCheckEnabled;
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
     * @param searchDto
     *            type String
     * @return
     * @author taitm
     */
    public void setSearchDto(String searchDto) {
        this.searchDto = searchDto;
    }

	public String getRequestToken() {
		return requestToken;
	}

	public void setRequestToken(String requestToken) {
		this.requestToken = requestToken;
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

	public boolean isTypeOfLibrary() {
		return typeOfLibrary;
	}

	public void setTypeOfLibrary(boolean typeOfLibrary) {
		this.typeOfLibrary = typeOfLibrary;
	}

    /**
     * @return the typeOfNew
     * @author taitm
     */
    public boolean isTypeOfNew() {
        return typeOfNew;
    }

    /**
     * @param typeOfNew the typeOfNew to set
     * @author taitm
     */
    public void setTypeOfNew(boolean typeOfNew) {
        this.typeOfNew = typeOfNew;
    }

    /**
     * @return the processId
     * @author taitm
     */
    public Long getProcessId() {
        return processId;
    }

    /**
     * @param processId the processId to set
     * @author taitm
     */
    public void setProcessId(Long processId) {
        this.processId = processId;
    }

    /**
     * @return the status
     * @author taitm
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * @param status the status to set
     * @author taitm
     */
    public void setStatus(Integer status) {
        this.status = status;
    }

    /**
     * @return the referenceId
     * @author taitm
     */
    public Long getReferenceId() {
        return referenceId;
    }

    /**
     * @param referenceId the referenceId to set
     * @author taitm
     */
    public void setReferenceId(Long referenceId) {
        this.referenceId = referenceId;
    }

    /**
     * @return the referenceType
     * @author taitm
     */
    public String getReferenceType() {
        return referenceType;
    }

    /**
     * @param referenceType the referenceType to set
     * @author taitm
     */
    public void setReferenceType(String referenceType) {
        this.referenceType = referenceType;
    }

    /**
     * @return the historyApproveDtos
     * @author taitm
     */
    public List<HistoryApproveDto> getHistoryApproveDtos() {
        return historyApproveDtos;
    }

    /**
     * @param historyApproveDtos the historyApproveDtos to set
     * @author taitm
     */
    public void setHistoryApproveDtos(List<HistoryApproveDto> historyApproveDtos) {
        this.historyApproveDtos = historyApproveDtos;
    }

    /**
     * @return the stepBtnList
     * @author taitm
     */
    public List<JProcessStepDto> getStepBtnList() {
        return stepBtnList;
    }

    /**
     * @param stepBtnList the stepBtnList to set
     * @author taitm
     */
    public void setStepBtnList(List<JProcessStepDto> stepBtnList) {
        this.stepBtnList = stepBtnList;
    }

    /**
     * @return the buttonAction
     * @author taitm
     */
    public String getButtonAction() {
        return buttonAction;
    }

    /**
     * @param buttonAction the buttonAction to set
     * @author taitm
     */
    public void setButtonAction(String buttonAction) {
        this.buttonAction = buttonAction;
    }

    /**
     * @return the buttonId
     * @author taitm
     */
    public Long getButtonId() {
        return buttonId;
    }

    /**
     * @param buttonId the buttonId to set
     * @author taitm
     */
    public void setButtonId(Long buttonId) {
        this.buttonId = buttonId;
    }

    /**
     * @return the customerAlias
     * @author taitm
     */
    public String getCustomerAlias() {
        return customerAlias;
    }

    /**
     * @param customerAlias the customerAlias to set
     * @author taitm
     */
    public void setCustomerAlias(String customerAlias) {
        this.customerAlias = customerAlias;
    }

    /**
     * @return the statusName
     * @author taitm
     */
    public String getStatusName() {
        return statusName;
    }

    /**
     * @param statusName the statusName to set
     * @author taitm
     */
    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }

    /**
     * @return the businessCode
     * @author taitm
     */
    public String getBusinessCode() {
        return businessCode;
    }

    /**
     * @param businessCode the businessCode to set
     * @author taitm
     */
    public void setBusinessCode(String businessCode) {
        this.businessCode = businessCode;
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

    /**
     * @return the oldStatus
     * @author taitm
     */
    public Integer getOldStatus() {
        return oldStatus;
    }

    /**
     * @param oldStatus the oldStatus to set
     * @author taitm
     */
    public void setOldStatus(Integer oldStatus) {
        this.oldStatus = oldStatus;
    }

	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	public String getButtonCode() {
		return buttonCode;
	}

	public void setButtonCode(String buttonCode) {
		this.buttonCode = buttonCode;
	}
    
}
