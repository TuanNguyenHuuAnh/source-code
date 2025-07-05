package vn.com.unit.cms.admin.all.dto;

import java.util.Date;
import java.util.List;

import vn.com.unit.ep2p.dto.JProcessStepDto;
import vn.com.unit.ep2p.admin.dto.SortOrderDto;
import vn.com.unit.ep2p.core.res.dto.DocumentActionReq;

public class InvestorCategoryDto extends DocumentActionReq {

	private Long id;


	private String name;
	
	//eport Excel

	private Integer stt;
	
	private String code;
	
	private String title;
	
	private String investorCategoryNameLevel1;

	private String investorCategoryNameLevel2;

	private String investorCategoryNameLevel3;

	private String investorCategoryNameLevel4;
	
	private String categoryTypeName;
	
	private String locationLeft;

	private String locationRightTop;

	private String locationRightBottom;
	
	
	private Integer categoryType;

	private Boolean categoryLocation;

	private Boolean categoryLocationLeft;

	private Boolean categoryLocationRightTop;

	private Boolean categoryLocationRightBottom;

	private Long parentId;

	private String parentName;

	private Integer levelId;

	private String imageNameLeft;

	private String imageUrlLeft;

	private String imageNameRight;

	private String imageUrlRight;

	private Long customerTypeId;

	private String note;


	private String createBy;
	private Date createDate;

	private Date updateDate;
	private String updateBy;

	private Date approvedDate;
	private String approveBy;

	private String publishBy;

	private Long beforeId;

	private Long sort;

	private Boolean enabled;

	private Integer status;

	private String statusName;
	private Long processId;

	private String comment;

	private List<SortOrderDto> sortOderList;

	private String requestToken;

	private List<JProcessStepDto> stepBtnList;

	/** button action */
	private String buttonAction;

	/** Button id */
//	private String buttonId;

	private String currItem;

	private Integer oldStatus;

	private String referenceType;

	private Integer enabledSearch;

	private Long referenceId;

	private String categoryLocationSearch;


	private String url;

	private List<InvestorCategoryLanguageDto> infoByLanguages;

	private String businessCode;

	private Integer indexLangActive;

	private String codeSearch;

	private String nameSearch;

	private Integer statusSearch;

	private String categoryTypeSearch;

	private String messageNotify;

	private Long categoryId;

	private Integer countLocationLeft;

	private Integer countLocationRightTop;

	private Integer countLocationRightBottom;

	private String categoryKind;

	private Integer numberInvestor;

	private List<InvestorCategoryDto> listInvestorCategoryDto;

	private String token;

	private Long investorParentId;
	
	private String imageName;

	private String imageUrl;

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @return the code
	 */
	public String getCode() {
		return code;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @return the categoryType
	 */
	public Integer getCategoryType() {
		return categoryType;
	}

	/**
	 * @return the categoryLocation
	 */
	public Boolean getCategoryLocation() {
		return categoryLocation;
	}

	/**
	 * @return the parentId
	 */
	public Long getParentId() {
		return parentId;
	}

	/**
	 * @return the parentName
	 */
	public String getParentName() {
		return parentName;
	}

	/**
	 * @return the levelId
	 */
	public Integer getLevelId() {
		return levelId;
	}

	/**
	 * @return the imageNameLeft
	 */
	public String getImageNameLeft() {
		return imageNameLeft;
	}

	/**
	 * @return the imageUrlLeft
	 */
	public String getImageUrlLeft() {
		return imageUrlLeft;
	}

	/**
	 * @return the imageNameRight
	 */
	public String getImageNameRight() {
		return imageNameRight;
	}

	/**
	 * @return the imageUrlRight
	 */
	public String getImageUrlRight() {
		return imageUrlRight;
	}

	/**
	 * @return the customerTypeId
	 */
	public Long getCustomerTypeId() {
		return customerTypeId;
	}

	/**
	 * @return the note
	 */
	public String getNote() {
		return note;
	}

	/**
	 * @return the createBy
	 */
	public String getCreateBy() {
		return createBy;
	}

	/**
	 * @return the createDate
	 */
	public Date getCreateDate() {
		return createDate;
	}

	/**
	 * @return the updateDate
	 */
	public Date getUpdateDate() {
		return updateDate;
	}

	/**
	 * @return the updateBy
	 */
	public String getUpdateBy() {
		return updateBy;
	}

	/**
	 * @return the approvedDate
	 */
	public Date getApprovedDate() {
		return approvedDate;
	}

	/**
	 * @return the approveBy
	 */
	public String getApproveBy() {
		return approveBy;
	}

	/**
	 * @return the publishBy
	 */
	public String getPublishBy() {
		return publishBy;
	}

	/**
	 * @return the beforeId
	 */
	public Long getBeforeId() {
		return beforeId;
	}

	/**
	 * @return the sort
	 */
	public Long getSort() {
		return sort;
	}

	/**
	 * @return the enabled
	 */
	public Boolean getEnabled() {
		return enabled;
	}

	/**
	 * @return the status
	 */
	public Integer getStatus() {
		return status;
	}

	/**
	 * @return the statusName
	 */
	public String getStatusName() {
		return statusName;
	}

	/**
	 * @return the processId
	 */
	public Long getProcessId() {
		return processId;
	}

	/**
	 * @return the comment
	 */
	public String getComment() {
		return comment;
	}

	/**
	 * @return the sortOderList
	 */
	public List<SortOrderDto> getSortOderList() {
		return sortOderList;
	}

	/**
	 * @return the requestToken
	 */
	public String getRequestToken() {
		return requestToken;
	}

	/**
	 * @return the stepBtnList
	 */
	public List<JProcessStepDto> getStepBtnList() {
		return stepBtnList;
	}

	/**
	 * @return the buttonAction
	 */
	public String getButtonAction() {
		return buttonAction;
	}

	/**
	 * @return the buttonId
	 */
//	public String getButtonId() {
//		return buttonId;
//	}

	/**
	 * @return the currItem
	 */
	public String getCurrItem() {
		return currItem;
	}

	/**
	 * @return the oldStatus
	 */
	public Integer getOldStatus() {
		return oldStatus;
	}

	/**
	 * @return the referenceType
	 */
	public String getReferenceType() {
		return referenceType;
	}

	/**
	 * @return the enabledSearch
	 */
	public Integer getEnabledSearch() {
		return enabledSearch;
	}

	/**
	 * @return the referenceId
	 */
	public Long getReferenceId() {
		return referenceId;
	}

	/**
	 * @return the nameSearch
	 */
	public String getNameSearch() {
		return nameSearch;
	}

	/**
	 * @return the statusSearch
	 */
	public Integer getStatusSearch() {
		return statusSearch;
	}

	/**
	 * @return the categoryLocationSearch
	 */
	public String getCategoryLocationSearch() {
		return categoryLocationSearch;
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
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @param categoryType the categoryType to set
	 */
	public void setCategoryType(Integer categoryType) {
		this.categoryType = categoryType;
	}

	/**
	 * @param categoryLocation the categoryLocation to set
	 */
	public void setCategoryLocation(Boolean categoryLocation) {
		this.categoryLocation = categoryLocation;
	}

	/**
	 * @param parentId the parentId to set
	 */
	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}

	/**
	 * @param parentName the parentName to set
	 */
	public void setParentName(String parentName) {
		this.parentName = parentName;
	}

	/**
	 * @param levelId the levelId to set
	 */
	public void setLevelId(Integer levelId) {
		this.levelId = levelId;
	}

	/**
	 * @param imageNameLeft the imageNameLeft to set
	 */
	public void setImageNameLeft(String imageNameLeft) {
		this.imageNameLeft = imageNameLeft;
	}

	/**
	 * @param imageUrlLeft the imageUrlLeft to set
	 */
	public void setImageUrlLeft(String imageUrlLeft) {
		this.imageUrlLeft = imageUrlLeft;
	}

	/**
	 * @param imageNameRight the imageNameRight to set
	 */
	public void setImageNameRight(String imageNameRight) {
		this.imageNameRight = imageNameRight;
	}

	/**
	 * @param imageUrlRight the imageUrlRight to set
	 */
	public void setImageUrlRight(String imageUrlRight) {
		this.imageUrlRight = imageUrlRight;
	}

	/**
	 * @param customerTypeId the customerTypeId to set
	 */
	public void setCustomerTypeId(Long customerTypeId) {
		this.customerTypeId = customerTypeId;
	}

	/**
	 * @param note the note to set
	 */
	public void setNote(String note) {
		this.note = note;
	}

	/**
	 * @param createBy the createBy to set
	 */
	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}

	/**
	 * @param createDate the createDate to set
	 */
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	/**
	 * @param updateDate the updateDate to set
	 */
	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	/**
	 * @param updateBy the updateBy to set
	 */
	public void setUpdateBy(String updateBy) {
		this.updateBy = updateBy;
	}

	/**
	 * @param approvedDate the approvedDate to set
	 */
	public void setApprovedDate(Date approvedDate) {
		this.approvedDate = approvedDate;
	}

	/**
	 * @param approveBy the approveBy to set
	 */
	public void setApproveBy(String approveBy) {
		this.approveBy = approveBy;
	}

	/**
	 * @param publishBy the publishBy to set
	 */
	public void setPublishBy(String publishBy) {
		this.publishBy = publishBy;
	}

	/**
	 * @param beforeId the beforeId to set
	 */
	public void setBeforeId(Long beforeId) {
		this.beforeId = beforeId;
	}

	/**
	 * @param sort the sort to set
	 */
	public void setSort(Long sort) {
		this.sort = sort;
	}

	/**
	 * @param enabled the enabled to set
	 */
	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}

	/**
	 * @param status the status to set
	 */
	public void setStatus(Integer status) {
		this.status = status;
	}

	/**
	 * @param statusName the statusName to set
	 */
	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}

	/**
	 * @param processId the processId to set
	 */
	public void setProcessId(Long processId) {
		this.processId = processId;
	}

	/**
	 * @param comment the comment to set
	 */
	public void setComment(String comment) {
		this.comment = comment;
	}

	/**
	 * @param sortOderList the sortOderList to set
	 */
	public void setSortOderList(List<SortOrderDto> sortOderList) {
		this.sortOderList = sortOderList;
	}

	/**
	 * @param requestToken the requestToken to set
	 */
	public void setRequestToken(String requestToken) {
		this.requestToken = requestToken;
	}

	/**
	 * @param stepBtnList the stepBtnList to set
	 */
	public void setStepBtnList(List<JProcessStepDto> stepBtnList) {
		this.stepBtnList = stepBtnList;
	}

	/**
	 * @param buttonAction the buttonAction to set
	 */
	public void setButtonAction(String buttonAction) {
		this.buttonAction = buttonAction;
	}

	/**
	 * @param buttonId the buttonId to set
	 */
//	public void setButtonId(String buttonId) {
//		this.buttonId = buttonId;
//	}

	/**
	 * @param currItem the currItem to set
	 */
	public void setCurrItem(String currItem) {
		this.currItem = currItem;
	}

	/**
	 * @param oldStatus the oldStatus to set
	 */
	public void setOldStatus(Integer oldStatus) {
		this.oldStatus = oldStatus;
	}

	/**
	 * @param referenceType the referenceType to set
	 */
	public void setReferenceType(String referenceType) {
		this.referenceType = referenceType;
	}

	/**
	 * @param enabledSearch the enabledSearch to set
	 */
	public void setEnabledSearch(Integer enabledSearch) {
		this.enabledSearch = enabledSearch;
	}

	/**
	 * @param referenceId the referenceId to set
	 */
	public void setReferenceId(Long referenceId) {
		this.referenceId = referenceId;
	}

	/**
	 * @param nameSearch the nameSearch to set
	 */
	public void setNameSearch(String nameSearch) {
		this.nameSearch = nameSearch;
	}

	/**
	 * @param statusSearch the statusSearch to set
	 */
	public void setStatusSearch(Integer statusSearch) {
		this.statusSearch = statusSearch;
	}

	/**
	 * @param categoryLocationSearch the categoryLocationSearch to set
	 */
	public void setCategoryLocationSearch(String categoryLocationSearch) {
		this.categoryLocationSearch = categoryLocationSearch;
	}

	/**
	 * @return the categoryLocationLeft
	 */
	public Boolean getCategoryLocationLeft() {
		return categoryLocationLeft;
	}

	/**
	 * @return the categoryLocationRightTop
	 */
	public Boolean getCategoryLocationRightTop() {
		return categoryLocationRightTop;
	}

	/**
	 * @return the categoryLocationRightBottom
	 */
	public Boolean getCategoryLocationRightBottom() {
		return categoryLocationRightBottom;
	}

	/**
	 * @param categoryLocationLeft the categoryLocationLeft to set
	 */
	public void setCategoryLocationLeft(Boolean categoryLocationLeft) {
		this.categoryLocationLeft = categoryLocationLeft;
	}

	/**
	 * @param categoryLocationRightTop the categoryLocationRightTop to set
	 */
	public void setCategoryLocationRightTop(Boolean categoryLocationRightTop) {
		this.categoryLocationRightTop = categoryLocationRightTop;
	}

	/**
	 * @param categoryLocationRightBottom the categoryLocationRightBottom to set
	 */
	public void setCategoryLocationRightBottom(Boolean categoryLocationRightBottom) {
		this.categoryLocationRightBottom = categoryLocationRightBottom;
	}

	/**
	 * @return the categoryTypeName
	 */
	public String getCategoryTypeName() {
		return categoryTypeName;
	}

	/**
	 * @param categoryTypeName the categoryTypeName to set
	 */
	public void setCategoryTypeName(String categoryTypeName) {
		this.categoryTypeName = categoryTypeName;
	}

	/**
	 * @return the url
	 */
	public String getUrl() {
		return url;
	}

	/**
	 * @param url the url to set
	 */
	public void setUrl(String url) {
		this.url = url;
	}

	/**
	 * @return the infoByLanguages
	 */
	public List<InvestorCategoryLanguageDto> getInfoByLanguages() {
		return infoByLanguages;
	}

	/**
	 * @param infoByLanguages the infoByLanguages to set
	 */
	public void setInfoByLanguages(List<InvestorCategoryLanguageDto> infoByLanguages) {
		this.infoByLanguages = infoByLanguages;
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
	 * @return the indexLangActive
	 */
	public Integer getIndexLangActive() {
		return indexLangActive;
	}

	/**
	 * @param indexLangActive the indexLangActive to set
	 */
	public void setIndexLangActive(Integer indexLangActive) {
		this.indexLangActive = indexLangActive;
	}

	/**
	 * @return the codeSearch
	 */
	public String getCodeSearch() {
		return codeSearch;
	}

	/**
	 * @return the categoryTypeSearch
	 */
	public String getCategoryTypeSearch() {
		return categoryTypeSearch;
	}

	/**
	 * @param codeSearch the codeSearch to set
	 */
	public void setCodeSearch(String codeSearch) {
		this.codeSearch = codeSearch;
	}

	/**
	 * @param categoryTypeSearch the categoryTypeSearch to set
	 */
	public void setCategoryTypeSearch(String categoryTypeSearch) {
		this.categoryTypeSearch = categoryTypeSearch;
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
	 * @return the messageNotify
	 */
	public String getMessageNotify() {
		return messageNotify;
	}

	/**
	 * @param messageNotify the messageNotify to set
	 */
	public void setMessageNotify(String messageNotify) {
		this.messageNotify = messageNotify;
	}

	/**
	 * @return the categoryId
	 */
	public Long getCategoryId() {
		return categoryId;
	}

	/**
	 * @param categoryId the categoryId to set
	 */
	public void setCategoryId(Long categoryId) {
		this.categoryId = categoryId;
	}

	/**
	 * @return the countLocationLeft
	 */
	public Integer getCountLocationLeft() {
		return countLocationLeft;
	}

	/**
	 * @return the countLocationRightTop
	 */
	public Integer getCountLocationRightTop() {
		return countLocationRightTop;
	}

	/**
	 * @return the countLocationRightBottom
	 */
	public Integer getCountLocationRightBottom() {
		return countLocationRightBottom;
	}

	/**
	 * @param countLocationLeft the countLocationLeft to set
	 */
	public void setCountLocationLeft(Integer countLocationLeft) {
		this.countLocationLeft = countLocationLeft;
	}

	/**
	 * @param countLocationRightTop the countLocationRightTop to set
	 */
	public void setCountLocationRightTop(Integer countLocationRightTop) {
		this.countLocationRightTop = countLocationRightTop;
	}

	/**
	 * @param countLocationRightBottom the countLocationRightBottom to set
	 */
	public void setCountLocationRightBottom(Integer countLocationRightBottom) {
		this.countLocationRightBottom = countLocationRightBottom;
	}

	/**
	 * @return the categoryKind
	 */
	public String getCategoryKind() {
		return categoryKind;
	}

	/**
	 * @param categoryKind the categoryKind to set
	 */
	public void setCategoryKind(String categoryKind) {
		this.categoryKind = categoryKind;
	}

	/**
	 * @return the numberInvestor
	 */
	public Integer getNumberInvestor() {
		return numberInvestor;
	}

	/**
	 * @param numberInvestor the numberInvestor to set
	 */
	public void setNumberInvestor(Integer numberInvestor) {
		this.numberInvestor = numberInvestor;
	}

	/**
	 * @return the listInvestorCategoryDto
	 */
	public List<InvestorCategoryDto> getListInvestorCategoryDto() {
		return listInvestorCategoryDto;
	}

	/**
	 * @param listInvestorCategoryDto the listInvestorCategoryDto to set
	 */
	public void setListInvestorCategoryDto(List<InvestorCategoryDto> listInvestorCategoryDto) {
		this.listInvestorCategoryDto = listInvestorCategoryDto;
	}

	/**
	 * @return the token
	 */
	public String getToken() {
		return token;
	}

	/**
	 * @param token the token to set
	 */
	public void setToken(String token) {
		this.token = token;
	}

	/**
	 * @return the investorParentId
	 */
	public Long getInvestorParentId() {
		return investorParentId;
	}

	/**
	 * @param investorParentId the investorParentId to set
	 */
	public void setInvestorParentId(Long investorParentId) {
		this.investorParentId = investorParentId;
	}

	/**
	 * @return the stt
	 */
	public Integer getStt() {
		return stt;
	}

	/**
	 * @return the investorCategoryNameLevel1
	 */
	public String getInvestorCategoryNameLevel1() {
		return investorCategoryNameLevel1;
	}

	/**
	 * @return the investorCategoryNameLevel2
	 */
	public String getInvestorCategoryNameLevel2() {
		return investorCategoryNameLevel2;
	}

	/**
	 * @return the investorCategoryNameLevel3
	 */
	public String getInvestorCategoryNameLevel3() {
		return investorCategoryNameLevel3;
	}

	/**
	 * @return the investorCategoryNameLevel4
	 */
	public String getInvestorCategoryNameLevel4() {
		return investorCategoryNameLevel4;
	}

	/**
	 * @return the locationLeft
	 */
	public String getLocationLeft() {
		return locationLeft;
	}

	/**
	 * @return the locationRightTop
	 */
	public String getLocationRightTop() {
		return locationRightTop;
	}

	/**
	 * @return the locationRightBottom
	 */
	public String getLocationRightBottom() {
		return locationRightBottom;
	}

	/**
	 * @param stt the stt to set
	 */
	public void setStt(Integer stt) {
		this.stt = stt;
	}

	/**
	 * @param investorCategoryNameLevel1 the investorCategoryNameLevel1 to set
	 */
	public void setInvestorCategoryNameLevel1(String investorCategoryNameLevel1) {
		this.investorCategoryNameLevel1 = investorCategoryNameLevel1;
	}

	/**
	 * @param investorCategoryNameLevel2 the investorCategoryNameLevel2 to set
	 */
	public void setInvestorCategoryNameLevel2(String investorCategoryNameLevel2) {
		this.investorCategoryNameLevel2 = investorCategoryNameLevel2;
	}

	/**
	 * @param investorCategoryNameLevel3 the investorCategoryNameLevel3 to set
	 */
	public void setInvestorCategoryNameLevel3(String investorCategoryNameLevel3) {
		this.investorCategoryNameLevel3 = investorCategoryNameLevel3;
	}

	/**
	 * @param investorCategoryNameLevel4 the investorCategoryNameLevel4 to set
	 */
	public void setInvestorCategoryNameLevel4(String investorCategoryNameLevel4) {
		this.investorCategoryNameLevel4 = investorCategoryNameLevel4;
	}

	/**
	 * @param locationLeft the locationLeft to set
	 */
	public void setLocationLeft(String locationLeft) {
		this.locationLeft = locationLeft;
	}

	/**
	 * @param locationRightTop the locationRightTop to set
	 */
	public void setLocationRightTop(String locationRightTop) {
		this.locationRightTop = locationRightTop;
	}

	/**
	 * @param locationRightBottom the locationRightBottom to set
	 */
	public void setLocationRightBottom(String locationRightBottom) {
		this.locationRightBottom = locationRightBottom;
	}

	public String getImageName() {
		return imageName;
	}

	public void setImageName(String imageName) {
		this.imageName = imageName;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

}
