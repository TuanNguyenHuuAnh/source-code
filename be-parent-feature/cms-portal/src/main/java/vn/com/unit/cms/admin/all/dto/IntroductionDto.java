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

import javax.validation.Valid;

import org.apache.commons.lang.StringUtils;
import javax.validation.constraints.NotNull;

import vn.com.unit.cms.admin.all.entity.Introduction;
import vn.com.unit.cms.admin.all.entity.IntroductionLanguage;
import vn.com.unit.cms.admin.all.jcanary.dto.HistoryApproveDto;
import vn.com.unit.cms.core.module.banner.enumdef.StepStatusEnum;
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
public class IntroductionDto extends DocumentActionReq {

    private Long id;
    @NotNull
    private Long categoryId;
    private String code;
    private String name;
    private String linkAlias;
    private String note;
    private String description;
    private String subTitle;
    private String imageUrl;
    private String keyWord;
    private Long sort;
    private Integer gender;
    private String genderNameKey;
    private Long views;
    private boolean enabled;
    private List<IntroductionLanguageDto> infoByLanguages;

    private String imageName;
    private String imagePhysicalName;
    private String categoryName;
    private Date createDate;

    /** processId */
    private Long processId;

    /** status */
    private Integer status;

    private String comment;
    private Long referenceId;
    private String referenceType;

    private String requestToken;

    /** status */
    private String statusName;

    @Valid
    private List<HistoryApproveDto> historyApproveDtos;

    private List<SortOrderDto> sortOderList;

    private String introductionPhysicalVideo;

    private String introductionTitleVideo;

    private String introductionVideo;

    private String url;

    private String approvedBy;

    private String publishedBy;

    private String introductionComment;

    private String createBy;

    private IntroductionCategoryDto introductionCategoryDto;

    private String title;

    private Integer indexLangActive;
    
    private String currItem;
    
    /** status */
    private Integer oldStatus;
    
    private String statusCode;
    
    private List<JProcessStepDto> stepBtnList;
    
//    /** Button id */
//    private String buttonId;
    
    /** button action*/
    private String buttonAction;
    
    private Long beforeId;
    
    private Date updateDate;
    
    private String businessCode;
    
    private Long customerTypeId;
    
    private String codeSearch;
    
    private String nameSearch;
    
    private String statusSearch;
    
    private Integer enabledSearch;
    
    private Long categoryIdSearch;

    public IntroductionDto() {

    }

    public IntroductionDto(Introduction entity) {
        this.id = entity.getId();
        this.categoryId = entity.getCategoryId();
        this.code = entity.getCode();
        this.name = entity.getName();
        this.linkAlias = entity.getLinkAlias();
        this.note = entity.getNote();
        this.description = entity.getDescription();
        this.subTitle = entity.getSubTitle();
        this.imageUrl = entity.getImageUrl();
        this.imagePhysicalName = entity.getImageUrl();
        this.keyWord = entity.getKeyWord();
        this.views = entity.getViews();
        this.imageName = entity.getImageName();
        this.status = entity.getStatus();
        this.sort = entity.getSort() != null ? entity.getSort() : 1;
        this.gender = entity.getGender();
        this.createDate = entity.getCreateDate();
        if (this.status == null) {
            this.status = StepStatusEnum.DRAFT.getStepNo();
        }
        this.processId = entity.getProcessId();
        this.referenceId = entity.getId();
        this.introductionPhysicalVideo = entity.getIntroductionPhysicalVideo();
        this.introductionTitleVideo = entity.getIntroductionTitleVideo();
        this.introductionVideo = entity.getIntroductionVideo();
        this.approvedBy = entity.getApprovedBy();
        this.publishedBy = entity.getPublishedBy();
        this.createBy = entity.getCreateBy();
        this.customerTypeId = entity.getCustomerTypeId();
        this.updateDate = entity.getUpdateDate();
        this.beforeId = entity.getBeforeId();
    }

    public Introduction createIntroEntity() {
        Introduction entity = new Introduction();
        entity.setId(this.id);
        entity.setCategoryId(this.categoryId);
        entity.setName(this.name);
        entity.setLinkAlias(this.linkAlias);
        entity.setNote(this.note);
        entity.setDescription(this.description);
        entity.setSubTitle(this.subTitle);
        entity.setImageUrl(this.imageUrl);
        entity.setKeyWord(this.keyWord);
        entity.setSort(this.sort);
        entity.setGender(this.getGender());
        entity.setViews(this.views);
        entity.setProcessId(this.processId);
        entity.setStatus(this.status);
        entity.setIntroductionPhysicalVideo(this.getIntroductionPhysicalVideo());
        entity.setIntroductionTitleVideo(this.getIntroductionTitleVideo());
        entity.setIntroductionVideo(this.getIntroductionVideo());
        entity.setApprovedBy(this.getApprovedBy());
        entity.setPublishedBy(this.getPublishedBy());
        entity.setIntroductionComment(this.getIntroductionComment());
        entity.setEnabled(this.enabled);
        return entity;
    }

    /**
     * @return
     */
    public List<IntroductionLanguage> createIntroLanguageEntities() {

        List<IntroductionLanguage> introLanguageEntities = new ArrayList<IntroductionLanguage>();
        if (this.getInfoByLanguages() != null) {
            for (IntroductionLanguageDto introLangDto : this.getInfoByLanguages()) {
                IntroductionLanguage introLangEntity = introLangDto.createEntity();
                introLangEntity.setIntroductionId(this.id);
                introLanguageEntities.add(introLangEntity);
            }
        }
        return introLanguageEntities;

    }

    /**
     * Get id
     * 
     * @return Long
     * @author thuydtn
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
     * @author thuydtn
     */
    public void setId(Long id) {
        this.id = id;
        this.referenceId = id;
    }

    /**
     * Get categoryId
     * 
     * @return Long
     * @author thuydtn
     */
    public Long getCategoryId() {
        return categoryId;
    }

    /**
     * Set categoryId
     * 
     * @param categoryId
     *            type Long
     * @return
     * @author thuydtn
     */
    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
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
     * Get subTitle
     * 
     * @return String
     * @author thuydtn
     */
    public String getSubTitle() {
        return subTitle;
    }

    /**
     * Set subTitle
     * 
     * @param subTitle
     *            type String
     * @return
     * @author thuydtn
     */
    public void setSubTitle(String subTitle) {
        this.subTitle = subTitle;
    }

    /**
     * Get imageUrl
     * 
     * @return String
     * @author thuydtn
     */
    public String getImageUrl() {
        return imageUrl;
    }

    /**
     * Set imageUrl
     * 
     * @param imageUrl
     *            type String
     * @return
     * @author thuydtn
     */
    public void setImageUrl(String imageUrl) {
        if (StringUtils.isEmpty(imageUrl)) {
            this.imageUrl = null;
        } else {
            this.imageUrl = imageUrl;
        }
    }

    /**
     * Get keyWord
     * 
     * @return String
     * @author thuydtn
     */
    public String getKeyWord() {
        return keyWord;
    }

    /**
     * Set keyWord
     * 
     * @param keyWord
     *            type String
     * @return
     * @author thuydtn
     */
    public void setKeyWord(String keyWord) {
        this.keyWord = keyWord;
    }

    /**
     * Get views
     * 
     * @return Long
     * @author thuydtn
     */
    public Long getViews() {
        return views;
    }

    /**
     * Set views
     * 
     * @param views
     *            type Long
     * @return
     * @author thuydtn
     */
    public void setViews(Long views) {
        this.views = views;
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
    public List<IntroductionLanguageDto> getInfoByLanguages() {
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
    public void setInfoByLanguages(List<IntroductionLanguageDto> infoByLanguages) {
        this.infoByLanguages = infoByLanguages;
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
     * Get processId
     * 
     * @return Long
     * @author thuydtn
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
     * @author thuydtn
     */
    public void setProcessId(Long processId) {
        this.processId = processId;
    }

    /**
     * Get status
     * 
     * @return String
     * @author thuydtn
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * Set status
     * 
     * @param status
     *            type String
     * @return
     * @author thuydtn
     */
    public void setStatus(Integer status) {
        this.status = status;
    }

    /**
     * Get categoryName
     * 
     * @return String
     * @author thuydtn
     */
    public String getCategoryName() {
        return categoryName;
    }

    /**
     * Set categoryName
     * 
     * @param categoryName
     *            type String
     * @return
     * @author thuydtn
     */
    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    /**
     * Get comment
     * 
     * @return String
     * @author thuydtn
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
     * @author thuydtn
     */
    public void setComment(String comment) {
        this.comment = comment;
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
     * Get referenceId
     * 
     * @return Long
     * @author TranLTH
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
     * @author TranLTH
     */
    public void setReferenceId(Long referenceId) {
        this.referenceId = referenceId;
    }

    /**
     * Get referenceType
     * 
     * @return String
     * @author TranLTH
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
     * @author TranLTH
     */
    public void setReferenceType(String referenceType) {
        this.referenceType = referenceType;
    }

    /**
     * Get historyApproveDtos
     * 
     * @return List<HistoryApproveDto>
     * @author TranLTH
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
     * @author TranLTH
     */
    public void setHistoryApproveDtos(List<HistoryApproveDto> historyApproveDtos) {
        this.historyApproveDtos = historyApproveDtos;
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
     * Get requestToken
     * 
     * @return String
     * @author thuydtn
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
     * @author thuydtn
     */
    public void setRequestToken(String requestToken) {
        this.requestToken = requestToken;
    }

    /**
     * Get statusName
     * 
     * @return String
     * @author thuydtn
     */
    public String getStatusName() {
        return statusName;
    }

    /**
     * Set statusName
     * 
     * @param statusName
     *            type String
     * @return
     * @author thuydtn
     */
    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }

    /**
     * @return
     */
    public Integer getGender() {
        return gender;
    }

    /**
     * @param gender
     */
    public void setGender(Integer gender) {
        this.gender = gender;
    }

    /**
     * @return
     */
    public String getGenderNameKey() {
        return genderNameKey;
    }

    /**
     * @param genderName
     */
    public void setGenderNameKey(String genderName) {
        this.genderNameKey = genderName;
    }

    public String getLinkAlias() {
        return linkAlias;
    }

    public void setLinkAlias(String linkAlias) {
        this.linkAlias = linkAlias;
    }

    /**
     * Get sortOderList
     * 
     * @return List<SortOrderDto>
     * @author TranLTH
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
     * @author TranLTH
     */
    public void setSortOderList(List<SortOrderDto> sortOderList) {
        this.sortOderList = sortOderList;
    }

    public String getIntroductionPhysicalVideo() {
        return introductionPhysicalVideo;
    }

    public void setIntroductionPhysicalVideo(String introductionPhysicalVideo) {
        this.introductionPhysicalVideo = introductionPhysicalVideo;
    }

    public String getIntroductionTitleVideo() {
        return introductionTitleVideo;
    }

    public void setIntroductionTitleVideo(String introductionTitleVideo) {
        this.introductionTitleVideo = introductionTitleVideo;
    }

    public String getIntroductionVideo() {
        return introductionVideo;
    }

    public void setIntroductionVideo(String introductionVideo) {
        this.introductionVideo = introductionVideo;
    }

    public String getApprovedBy() {
        return approvedBy;
    }

    public void setApprovedBy(String approvedBy) {
        this.approvedBy = approvedBy;
    }

    public String getPublishedBy() {
        return publishedBy;
    }

    public void setPublishedBy(String publishedBy) {
        this.publishedBy = publishedBy;
    }

    public String getIntroductionComment() {
        return introductionComment;
    }

    public void setIntroductionComment(String introductionComment) {
        this.introductionComment = introductionComment;
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public IntroductionCategoryDto getIntroductionCategoryDto() {
        return introductionCategoryDto;
    }

    public void setIntroductionCategoryDto(IntroductionCategoryDto introductionCategoryDto) {
        this.introductionCategoryDto = introductionCategoryDto;
    }

    public String getTitle() {
        return title;
    }

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
	 * @param oldStatus
	 *            the oldStatus to set
	 * @author taitm
	 */
	public void setOldStatus(Integer oldStatus) {
		this.oldStatus = oldStatus;
	}

	/**
	 * @return the stepBtnList
	 * @author taitm
	 */
	public List<JProcessStepDto> getStepBtnList() {
		return stepBtnList;
	}

	/**
	 * @param stepBtnList
	 *            the stepBtnList to set
	 * @author taitm
	 */
	public void setStepBtnList(List<JProcessStepDto> stepBtnList) {
		this.stepBtnList = stepBtnList;
	}

//	/**
//	 * @return the buttonId
//	 * @author taitm
//	 */
//	public String getButtonId() {
//		return buttonId;
//	}
//
//	/**
//	 * @param buttonId the buttonId to set
//	 * @author taitm
//	 */
//	public void setButtonId(String buttonId) {
//		this.buttonId = buttonId;
//	}

	/**
	 * @return the beforeId
	 * @author taitm
	 */
	public Long getBeforeId() {
		return beforeId;
	}

	/**
	 * @param beforeId
	 *            the beforeId to set
	 * @author taitm
	 */
	public void setBeforeId(Long beforeId) {
		this.beforeId = beforeId;
	}

	/**
	 * @return the buttonAction
	 * @author taitm
	 */
	public String getButtonAction() {
		return buttonAction;
	}

	/**
	 * @param buttonAction
	 *            the buttonAction to set
	 * @author taitm
	 */
	public void setButtonAction(String buttonAction) {
		this.buttonAction = buttonAction;
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

	/**
	 * @return the businessCode
	 * @author taitm
	 */
	public String getBusinessCode() {
		return businessCode;
	}

	/**
	 * @param businessCode
	 *            the businessCode to set
	 * @author taitm
	 */
	public void setBusinessCode(String businessCode) {
		this.businessCode = businessCode;
	}

	/**
	 * @return the statusCode
	 * @author taitm
	 */
	public String getStatusCode() {
		return statusCode;
	}

	/**
	 * @param statusCode
	 *            the statusCode to set
	 * @author taitm
	 */
	public void setStatusCode(String statusCode) {
		this.statusCode = statusCode;
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

	/**
	 * @return the codeSearch
	 * @author taitm
	 */
	public String getCodeSearch() {
		return codeSearch;
	}

	/**
	 * @param codeSearch the codeSearch to set
	 * @author taitm
	 */
	public void setCodeSearch(String codeSearch) {
		this.codeSearch = codeSearch;
	}

	/**
	 * @return the nameSearch
	 * @author taitm
	 */
	public String getNameSearch() {
		return nameSearch;
	}

	/**
	 * @param nameSearch the nameSearch to set
	 * @author taitm
	 */
	public void setNameSearch(String nameSearch) {
		this.nameSearch = nameSearch;
	}

	/**
	 * @return the statusSearch
	 * @author taitm
	 */
	public String getStatusSearch() {
		return statusSearch;
	}

	/**
	 * @param statusSearch the statusSearch to set
	 * @author taitm
	 */
	public void setStatusSearch(String statusSearch) {
		this.statusSearch = statusSearch;
	}

	/**
	 * @return the enabledSearch
	 * @author taitm
	 */
	public Integer getEnabledSearch() {
		return enabledSearch;
	}

	/**
	 * @param enabledSearch the enabledSearch to set
	 * @author taitm
	 */
	public void setEnabledSearch(Integer enabledSearch) {
		this.enabledSearch = enabledSearch;
	}

	/**
	 * @return the categoryIdSearch
	 * @author taitm
	 */
	public Long getCategoryIdSearch() {
		return categoryIdSearch;
	}

	/**
	 * @param categoryIdSearch
	 *            the categoryIdSearch to set
	 * @author taitm
	 */
	public void setCategoryIdSearch(Long categoryIdSearch) {
		this.categoryIdSearch = categoryIdSearch;
	}

}