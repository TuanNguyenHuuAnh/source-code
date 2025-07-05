/*******************************************************************************
 * Class        ：IntroductionDto
 * Created date ：2017/02/22
 * Lasted date  ：2017/02/22
 * Author       ：thuydtn
 * Change log   ：2017/02/22：01-00 thuydtn create a new
 ******************************************************************************/
package vn.com.unit.cms.admin.all.dto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.validation.constraints.Size;

import org.apache.commons.lang.StringUtils;

import vn.com.unit.cms.admin.all.constant.ConstantHistoryApprove;
import vn.com.unit.cms.admin.all.entity.IntroductionCategory;
import vn.com.unit.cms.admin.all.entity.IntroductionCategoryLanguage;
import vn.com.unit.ep2p.admin.dto.SortOrderDto;
import vn.com.unit.ep2p.core.res.dto.DocumentActionReq;
import vn.com.unit.ep2p.dto.JProcessStepDto;

/**
 * IntroductionDto
 * 
 * @version 01-00
 * @since 01-00
 * @author thuydtn
 */
public class IntroductionCategoryDto extends DocumentActionReq {

    private Long id;
    private String code;
    private String name;
    private String linkAlias;
    private String note;
    private String imageName;
    private String imagePhysicalName;
    private String imageNameMobile;
    private String imagePhysicalNameMobile;
    private String description;
    private Long sort;
    private String viewType;
    private boolean enabled;
    private Long parentId;
    private String parentName;
    private List<IntroductionCategoryLanguageDto> infoByLanguages;
    private String url;
    private String introType;
    private List<SortOrderDto> sortOderList;
    private Date createDate;
    private String createBy;
    private String approvedBy;
    private Date approvedDate;
    private String publishedBy;
    private Date publishedDate;
    private String updateBy;
    private Date updateDate;
    /** comment */
    private String comment;

    /** bannerVideo */
    private String bannerVideo;

    /** bannerPhysicalVideo */
    private String bannerPhysicalVideo;

    /** bannerTitleVideo */
    @Size(max = 500)
    private String bannerTitleVideo;

    /** requestToken */
    private String requestToken;

    private Long beforeId;

    private String title;

    private Integer indexLangActive;
    
    /** Status name */
    private String statusName;
    
    private List<JProcessStepDto> stepBtnList;
    
    /** button action*/
    private String buttonAction;
    
    /** Button id */
//    private String buttonId;
    
    private String currItem;
    
    /** processId */
    private Long processId;

    /** status */
    private Integer status;
    
    private Integer oldStatus;
    
    private Boolean typeOfMain;
    
    private Boolean pictureIntroduction;
    
    private String referenceType;
    
    private Long referenceId;
    
    private Long customerTypeId;
    
    private String codeSearch;
    
    private String nameSearch;
    
    private String statusSearch;
    
    private Integer enabledSearch;
    
    private Integer pictureIntroductionSearch;
    
    private Integer typeOfMainSearch;
    
    public IntroductionCategoryDto() {

    }

    public IntroductionCategoryDto(IntroductionCategory entity) {
        this.id = entity.getId();
        this.code = entity.getCode();
        this.name = entity.getName();
        this.description = entity.getDescription();
        this.sort = entity.getSort();
        this.enabled = entity.isEnabled();
        this.parentId = entity.getParentId();
        this.imageName = entity.getImageName();
        this.imagePhysicalName = entity.getImageUrl();
        this.viewType = entity.getViewType();
        this.introType = entity.getIntroType();
        this.linkAlias = entity.getLinkAlias();
        this.status = entity.getStatus();
        this.createDate = entity.getCreateDate();
        this.createBy = entity.getCreateBy();
        this.approvedBy = entity.getApproveBy();
        this.approvedDate = entity.getApproveDate();
        this.publishedBy = entity.getPublishBy();
        this.publishedDate = entity.getPublishDate();
        this.comment = entity.getComment();
        this.bannerVideo = entity.getBannerVideo();
        this.bannerPhysicalVideo = entity.getBannerPhysicalVideo();
        this.bannerTitleVideo = entity.getBannerTitleVideo();
        this.processId = entity.getProcessId();
        this.updateDate = entity.getUpdateDate();
        this.referenceType = ConstantHistoryApprove.APPROVE_INTRODUCTION_CATEGORY;
        this.referenceId = entity.getId();
        this.imageNameMobile = entity.getImageNameMobile();
        this.imagePhysicalNameMobile = entity.getImageUrlMobile();
        this.customerTypeId = entity.getCustomerTypeId();
        this.status = entity.getStatus();
        this.beforeId = entity.getBeforeId();
        if (entity.getTypeOfMain() != null && entity.getTypeOfMain().equals(1)){
        	this.typeOfMain = true;
        }
        if (entity.getPictureIntroduction() != null && entity.getPictureIntroduction().equals(1)){
        	this.pictureIntroduction = true;
        }
    }

    public IntroductionCategory createIntroCateEntity() {
        IntroductionCategory entity = new IntroductionCategory();
        entity.setId(this.id);
        entity.setCode(this.code);
        entity.setName(this.name);
        entity.setNote(this.note);
        entity.setDescription(this.description);
        entity.setSort(this.sort);
        entity.setEnabled(this.enabled);
        entity.setImageName(this.imageName);
        entity.setImageNameMobile(this.imageNameMobile);
        entity.setParentId(this.parentId);
        entity.setViewType(this.viewType);
        entity.setIntroType(this.introType);
        entity.setLinkAlias(this.linkAlias);
        entity.setStatus(this.status);
        entity.setCreateDate(this.createDate);
        entity.setCreateBy(this.createBy);
        entity.setPublishBy(this.publishedBy);
        entity.setPublishDate(this.publishedDate);
        entity.setComment(this.comment);
        entity.setBannerPhysicalVideo(this.bannerPhysicalVideo);
        entity.setBannerVideo(this.bannerVideo);
        entity.setBannerTitleVideo(this.bannerTitleVideo);
        entity.setImageUrl(this.imagePhysicalName);
        entity.setImageUrlMobile(this.imagePhysicalNameMobile);
        entity.setCustomerTypeId(this.customerTypeId);
        return entity;
    }

    public List<IntroductionCategoryLanguage> createCateLanguageEntities() {
        List<IntroductionCategoryLanguage> cateLanguageEntities = new ArrayList<IntroductionCategoryLanguage>();
        if (this.infoByLanguages != null) {
            for (IntroductionCategoryLanguageDto cateLangDto : this.infoByLanguages) {
                IntroductionCategoryLanguage cateLangEntity = cateLangDto.createEntity();
                cateLangEntity.setCategoryId(this.id);
                cateLanguageEntities.add(cateLangEntity);
            }
        }
        return cateLanguageEntities;
    }

    /**
     * Get id
     * 
     * @return String
     * @author thuydtn
     */
    public Long getId() {
        return id;
    }

    /**
     * Set id
     * 
     * @param id
     *            type String
     * @return
     * @author thuydtn
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Get code
     * 
     * @return String
     * @author thuydtn
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
     * @author thuydtn
     */
    public void setCode(String code) {
        if (code != null) {
            this.code = StringUtils.upperCase(code);
        } else {
            this.code = code;
        }
    }

    /**
     * Get name
     * 
     * @return String
     * @author thuydtn
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
     * @author thuydtn
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Get note
     * 
     * @return String
     * @author thuydtn
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
     * @author thuydtn
     */
    public void setNote(String note) {
        this.note = note;
    }
    
    /**
     * Get description
     * 
     * @return String
     * @author thuydtn
     */
    public String getDescription() {
        return description;
    }

    /**
     * Set description
     * 
     * @param description
     *            type String
     * @return
     * @author thuydtn
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Get sort
     * 
     * @return Long
     * @author thuydtn
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
     * @author thuydtn
     */
    public void setSort(Long sort) {
        this.sort = sort;
    }

    /**
     * Get enabled
     * 
     * @return boolean
     * @author thuydtn
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
     * @author thuydtn
     */
    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    /**
     * Get infoByLanguages
     * 
     * @return List<IntroductionCategoryLanguageDto>
     * @author thuydtn
     */
    public List<IntroductionCategoryLanguageDto> getInfoByLanguages() {
        return infoByLanguages;
    }

    /**
     * Set infoByLanguages
     * 
     * @param infoByLanguages
     *            type List<IntroductionCategoryLanguageDto>
     * @return
     * @author thuydtn
     */
    public void setInfoByLanguages(List<IntroductionCategoryLanguageDto> infoByLanguages) {
        this.infoByLanguages = infoByLanguages;
    }

    // /**
    // * @param introductionCateLangEntities
    // */
    // public void loadInforByLanguage(List<IntroductionCategoryLanguage> introductionCateLangEntities) {
    // this.infoByLanguages = new ArrayList<IntroductionCategoryLanguageDto>();
    // for(IntroductionCategoryLanguage cateLangEntity : introductionCateLangEntities){
    // IntroductionCategoryLanguageDto cateLangDto = new IntroductionCategoryLanguageDto();
    // }
    // }

    /**
     * Get parentId
     * 
     * @return Long
     * @author thuydtn
     */
    public Long getParentId() {
        return parentId;
    }

    /**
     * Set parentId
     * 
     * @param parentId
     *            type Long
     * @return
     * @author thuydtn
     */
    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    /**
     * Get imageName
     * 
     * @return String
     * @author thuydtn
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
     * @author thuydtn
     */
    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    /**
     * Get imagePhysicalName
     * 
     * @return String
     * @author thuydtn
     */
    public String getImagePhysicalName() {
        return imagePhysicalName;
    }

    /**
     * Set imagePhysicalName
     * 
     * @param imagePhysicalName
     *            type String
     * @return
     * @author thuydtn
     */
    public void setImagePhysicalName(String imagePhysicalName) {
        this.imagePhysicalName = imagePhysicalName;
    }

    /**
     * Get parentName
     * 
     * @return String
     * @author thuydtn
     */
    public String getParentName() {
        return parentName;
    }

    /**
     * Set parentName
     * 
     * @param parentName
     *            type String
     * @return
     * @author thuydtn
     */
    public void setParentName(String parentName) {
        this.parentName = parentName;
    }

    /**
     * Get url
     * 
     * @return String
     * @author thuydtn
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
     * @author thuydtn
     */
    public void setUrl(String url) {
        this.url = url;
    }

    /**
     * Get createDate
     * 
     * @return Date
     * @author thuydtn
     */
    public Date getCreateDate() {
        return createDate;
    }

    /**
     * Set createDate
     * 
     * @param createDate
     *            type Date
     * @return
     * @author thuydtn
     */
    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    /**
     * Get viewType
     * 
     * @return String
     * @author thuydtn
     */
    public String getViewType() {
        return viewType;
    }

    /**
     * Set viewType
     * 
     * @param viewType
     *            type String
     * @return
     * @author thuydtn
     */
    public void setViewType(String viewType) {
        this.viewType = viewType;
    }

    public String getIntroType() {
        return introType;
    }

    public void setIntroType(String introType) {
        this.introType = introType;
    }

    public String getLinkAlias() {
        return linkAlias;
    }

    public void setLinkAlias(String linkAlias) {
        this.linkAlias = linkAlias;
    }

    /**
     * Get bannerVideo
     * 
     * @return String
     * @author taitm
     */
    public String getBannerVideo() {
        return bannerVideo;
    }

    /**
     * Set bannerVideo
     * 
     * @param bannerVideo
     *            type String
     * @return
     * @author taitm
     */
    public void setBannerVideo(String bannerVideo) {
        this.bannerVideo = bannerVideo;
    }

    /**
     * Get bannerPhysicalVideo
     * 
     * @return String
     * @author taitm
     */
    public String getBannerPhysicalVideo() {
        return bannerPhysicalVideo;
    }

    /**
     * Set bannerPhysicalVideo
     * 
     * @param bannerPhysicalVideo
     *            type String
     * @return
     * @author taitm
     */
    public void setBannerPhysicalVideo(String bannerPhysicalVideo) {
        this.bannerPhysicalVideo = bannerPhysicalVideo;
    }

    /**
     * Get bannerTitleVideo
     * 
     * @return String
     * @author taitm
     */
    public String getBannerTitleVideo() {
        return bannerTitleVideo;
    }

    /**
     * Set bannerTitleVideo
     * 
     * @param bannerTitleVideo
     *            type String
     * @return
     * @author taitm
     */
    public void setBannerTitleVideo(String bannerTitleVideo) {
        this.bannerTitleVideo = bannerTitleVideo;
    }

    /**
     * Get createBy
     * 
     * @return String
     * @author taitm
     */
    public String getCreateBy() {
        return createBy;
    }

    /**
     * Set createBy
     * 
     * @param createBy
     *            type String
     * @return
     * @author taitm
     */
    public void setCreateBy(String createBy) {
        this.createBy = createBy;
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
     * Get approvedDate
     * 
     * @return Date
     * @author taitm
     */
    public Date getApprovedDate() {
        return approvedDate;
    }

    /**
     * Set approvedDate
     * 
     * @param approvedDate
     *            type Date
     * @return
     * @author taitm
     */
    public void setApprovedDate(Date approvedDate) {
        this.approvedDate = approvedDate;
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
     * Get publishedDate
     * 
     * @return Date
     * @author taitm
     */
    public Date getPublishedDate() {
        return publishedDate;
    }

    /**
     * Set publishedDate
     * 
     * @param publishedDate
     *            type Date
     * @return
     * @author taitm
     */
    public void setPublishedDate(Date publishedDate) {
        this.publishedDate = publishedDate;
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
     * Get updateBy
     * 
     * @return String
     * @author taitm
     */
    public String getUpdateBy() {
        return updateBy;
    }

    /**
     * Set updateBy
     * 
     * @param updateBy
     *            type String
     * @return
     * @author taitm
     */
    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy;
    }

    /**
     * Get updateDate
     * 
     * @return Date
     * @author taitm
     */
    public Date getUpdateDate() {
        return updateDate;
    }

    /**
     * Set updateDate
     * 
     * @param updateDate
     *            type Date
     * @return
     * @author taitm
     */
    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    /**
     * Get requestToken
     * 
     * @return String
     * @author taitm
     */
    public String getRequestToken() {
        return requestToken;
    }

    /**
     * Set requestToken
     * 
     * @param requestToken
     *            type String
     * @return
     * @author taitm
     */
    public void setRequestToken(String requestToken) {
        this.requestToken = requestToken;
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
     * Get title
     * 
     * @return String
     * @author taitm
     */
    public String getTitle() {
        return title;
    }

    /**
     * Set title
     * 
     * @param title
     *            type String
     * @return
     * @author taitm
     */
    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getIndexLangActive() {
        return indexLangActive;
    }

    public void setIndexLangActive(Integer indexLangActive) {
        this.indexLangActive = indexLangActive;
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
//    public String getButtonId() {
//        return buttonId;
//    }

    /**
     * @param buttonId the buttonId to set
     * @author taitm
     */
//    public void setButtonId(String buttonId) {
//        this.buttonId = buttonId;
//    }

    /**
     * @return the currItem
     * @author taitm
     */
    public String getCurrItem() {
        return currItem;
    }

    /**
     * @param currItem
     *            the currItem to set
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
     * @param oldStatus
     *            the oldStatus to set
     * @author taitm
     */
    public void setOldStatus(Integer oldStatus) {
        this.oldStatus = oldStatus;
    }

    /**
     * @return the typeOfMain
     * @author taitm
     */
    public Boolean getTypeOfMain() {
        return typeOfMain;
    }

    /**
     * @param typeOfMain the typeOfMain to set
     * @author taitm
     */
    public void setTypeOfMain(Boolean typeOfMain) {
        this.typeOfMain = typeOfMain;
    }

    /**
     * @return the pictureIntroduction
     * @author taitm
     */
    public Boolean getPictureIntroduction() {
        return pictureIntroduction;
    }

    /**
     * @param pictureIntroduction
     *            the pictureIntroduction to set
     * @author taitm
     */
    public void setPictureIntroduction(Boolean pictureIntroduction) {
        this.pictureIntroduction = pictureIntroduction;
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
     * @return the referenceId
     * @author taitm
     */
    public Long getReferenceId() {
        return referenceId;
    }

    /**
     * @param referenceId
     *            the referenceId to set
     * @author taitm
     */
    public void setReferenceId(Long referenceId) {
        this.referenceId = referenceId;
    }

    /**
     * @return the imageNameMobile
     * @author taitm
     */
    public String getImageNameMobile() {
        return imageNameMobile;
    }

    /**
     * @param imageNameMobile the imageNameMobile to set
     * @author taitm
     */
    public void setImageNameMobile(String imageNameMobile) {
        this.imageNameMobile = imageNameMobile;
    }

    /**
     * @return the imagePhysicalNameMobile
     * @author taitm
     */
    public String getImagePhysicalNameMobile() {
        return imagePhysicalNameMobile;
    }

    /**
     * @param imagePhysicalNameMobile
     *            the imagePhysicalNameMobile to set
     * @author taitm
     */
    public void setImagePhysicalNameMobile(String imagePhysicalNameMobile) {
        this.imagePhysicalNameMobile = imagePhysicalNameMobile;
    }

    /**
     * @return the customerTypeId
     * @author taitm
     */
    public Long getCustomerTypeId() {
        return customerTypeId;
    }

    /**
     * @param customerTypeId
     *            the customerTypeId to set
     * @author taitm
     */
    public void setCustomerTypeId(Long customerTypeId) {
        this.customerTypeId = customerTypeId;
	}

	public List<SortOrderDto> getSortOderList() {
		return sortOderList;
	}

	public void setSortOderList(List<SortOrderDto> sortOderList) {
		this.sortOderList = sortOderList;
	}

	public String getCodeSearch() {
		return codeSearch;
	}

	public void setCodeSearch(String codeSearch) {
		this.codeSearch = codeSearch;
	}

	public String getNameSearch() {
		return nameSearch;
	}

	public void setNameSearch(String nameSearch) {
		this.nameSearch = nameSearch;
	}

	public String getStatusSearch() {
		return statusSearch;
	}

	public void setStatusSearch(String statusSearch) {
		this.statusSearch = statusSearch;
	}

	public Integer getEnabledSearch() {
		return enabledSearch;
	}

	public void setEnabledSearch(Integer enabledSearch) {
		this.enabledSearch = enabledSearch;
	}

	public Integer getPictureIntroductionSearch() {
		return pictureIntroductionSearch;
	}

	public void setPictureIntroductionSearch(Integer pictureIntroductionSearch) {
		this.pictureIntroductionSearch = pictureIntroductionSearch;
	}

	public Integer getTypeOfMainSearch() {
		return typeOfMainSearch;
	}

	public void setTypeOfMainSearch(Integer typeOfMainSearch) {
		this.typeOfMainSearch = typeOfMainSearch;
	}

}