package vn.com.unit.cms.admin.all.dto;

import java.util.Date;
import java.util.List;

import javax.validation.Valid;

import vn.com.unit.cms.admin.all.jcanary.dto.HistoryApproveDto;
import vn.com.unit.ep2p.core.res.dto.DocumentActionReq;
import vn.com.unit.ep2p.dto.JProcessStepDto;

public class InvestorEditDto extends DocumentActionReq {
	private Long id;
	private String code;
	private Integer status;
	private String statusName;
	private Integer kind;
	private Long categoryId;
	private String categoryJsonHidden;
	private String url;
	private Boolean enabled;
	private Integer pageSize;
	private String language;
	private String imageUrl;
	private String physicalImg;
	private String beforeIdsJsonHidden;
	private Long processId;
	private Long referenceId;
	private String referenceType;
	private String comment;
	private String requestToken;
	private Integer indexLangActive;
	private Boolean hotNews;
	private Boolean homepage;
	private Long sort;
	private String title;
	@Valid
	private List<InvestorLanguageEditDto> investorLanguageList;
	@Valid
	private List<HistoryApproveDto> historyApproveDtos;

	private String createBy;

	private List<JProcessStepDto> stepBtnList;

	private Long beforeId;

	/** button action */
	private String buttonAction;

//	/** Button id */
//	private String buttonId;

	private String currItem;

	private String statusCode;

	private Integer oldStatus;

	private Date updateDate;

	private Integer statusSearch;

	private Integer kindSearch;

	private Long categoryIdSearch;

	private String nameSearch;

	private Date postedDate;

	/**
	 * @return the status
	 * @author taitm
	 */
	public Integer getStatus() {
		return status;
	}

	/**
	 * @param status
	 *            the status to set
	 * @author taitm
	 */
	public void setStatus(Integer status) {
		this.status = status;
	}

	/**
	 * @return the kind
	 * @author taitm
	 */
	public Integer getKind() {
		return kind;
	}

	/**
	 * @param kind
	 *            the kind to set
	 * @author taitm
	 */
	public void setKind(Integer kind) {
		this.kind = kind;
	}

	/**
	 * @return the categoryId
	 * @author taitm
	 */
	public Long getCategoryId() {
		return categoryId;
	}

	/**
	 * @param categoryId
	 *            the categoryId to set
	 * @author taitm
	 */
	public void setCategoryId(Long categoryId) {
		this.categoryId = categoryId;
	}

	/**
	 * @return the url
	 * @author taitm
	 */
	public String getUrl() {
		return url;
	}

	/**
	 * @param url
	 *            the url to set
	 * @author taitm
	 */
	public void setUrl(String url) {
		this.url = url;
	}

	/**
	 * @return the enabled
	 * @author taitm
	 */
	public Boolean getEnabled() {
		return enabled;
	}

	/**
	 * @param enabled
	 *            the enabled to set
	 * @author taitm
	 */
	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}

	/**
	 * @return the pageSize
	 * @author taitm
	 */
	public Integer getPageSize() {
		return pageSize;
	}

	/**
	 * @param pageSize
	 *            the pageSize to set
	 * @author taitm
	 */
	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

	/**
	 * @return the statusName
	 * @author taitm
	 */
	public String getStatusName() {
		return statusName;
	}

	/**
	 * @param statusName
	 *            the statusName to set
	 * @author taitm
	 */
	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}

	/**
	 * @return the language
	 * @author taitm
	 */
	public String getLanguage() {
		return language;
	}

	/**
	 * @param language
	 *            the language to set
	 * @author taitm
	 */
	public void setLanguage(String language) {
		this.language = language;
	}

	/**
	 * @return the id
	 * @author taitm
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 * @author taitm
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @return the categoryJsonHidden
	 * @author taitm
	 */
	public String getCategoryJsonHidden() {
		return categoryJsonHidden;
	}

	/**
	 * @param categoryJsonHidden
	 *            the categoryJsonHidden to set
	 * @author taitm
	 */
	public void setCategoryJsonHidden(String categoryJsonHidden) {
		this.categoryJsonHidden = categoryJsonHidden;
	}

	/**
	 * @return the imageUrl
	 * @author taitm
	 */
	public String getImageUrl() {
		return imageUrl;
	}

	/**
	 * @param imageUrl
	 *            the imageUrl to set
	 * @author taitm
	 */
	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	/**
	 * @return the physicalImg
	 * @author taitm
	 */
	public String getPhysicalImg() {
		return physicalImg;
	}

	/**
	 * @param physicalImg
	 *            the physicalImg to set
	 * @author taitm
	 */
	public void setPhysicalImg(String physicalImg) {
		this.physicalImg = physicalImg;
	}

	/**
	 * @return the beforeIdsJsonHidden
	 * @author taitm
	 */
	public String getBeforeIdsJsonHidden() {
		return beforeIdsJsonHidden;
	}

	/**
	 * @param beforeIdsJsonHidden
	 *            the beforeIdsJsonHidden to set
	 * @author taitm
	 */
	public void setBeforeIdsJsonHidden(String beforeIdsJsonHidden) {
		this.beforeIdsJsonHidden = beforeIdsJsonHidden;
	}

	/**
	 * @return the processId
	 * @author taitm
	 */
	public Long getProcessId() {
		return processId;
	}

	/**
	 * @param processId
	 *            the processId to set
	 * @author taitm
	 */
	public void setProcessId(Long processId) {
		this.processId = processId;
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
	 * @return the comment
	 * @author taitm
	 */
	public String getComment() {
		return comment;
	}

	/**
	 * @param comment
	 *            the comment to set
	 * @author taitm
	 */
	public void setComment(String comment) {
		this.comment = comment;
	}

	/**
	 * @return the requestToken
	 * @author taitm
	 */
	public String getRequestToken() {
		return requestToken;
	}

	/**
	 * @param requestToken
	 *            the requestToken to set
	 * @author taitm
	 */
	public void setRequestToken(String requestToken) {
		this.requestToken = requestToken;
	}

	/**
	 * @return the investorLanguageList
	 * @author taitm
	 */
	public List<InvestorLanguageEditDto> getInvestorLanguageList() {
		return investorLanguageList;
	}

	/**
	 * @param investorLanguageList
	 *            the investorLanguageList to set
	 * @author taitm
	 */
	public void setInvestorLanguageList(List<InvestorLanguageEditDto> investorLanguageList) {
		this.investorLanguageList = investorLanguageList;
	}

	/**
	 * @return the historyApproveDtos
	 * @author taitm
	 */
	public List<HistoryApproveDto> getHistoryApproveDtos() {
		return historyApproveDtos;
	}

	/**
	 * @param historyApproveDtos
	 *            the historyApproveDtos to set
	 * @author taitm
	 */
	public void setHistoryApproveDtos(List<HistoryApproveDto> historyApproveDtos) {
		this.historyApproveDtos = historyApproveDtos;
	}

	/**
	 * @return the createBy
	 * @author taitm
	 */
	public String getCreateBy() {
		return createBy;
	}

	/**
	 * @param createBy
	 *            the createBy to set
	 * @author taitm
	 */
	public void setCreateBy(String createBy) {
		this.createBy = createBy;
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

//	/**
//	 * @return the buttonId
//	 * @author taitm
//	 */
//	public String getButtonId() {
//		return buttonId;
//	}
//
//	/**
//	 * @param buttonId
//	 *            the buttonId to set
//	 * @author taitm
//	 */
//	public void setButtonId(String buttonId) {
//		this.buttonId = buttonId;
//	}

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
	 * @return the code
	 * @author taitm
	 */
	public String getCode() {
		return code;
	}

	/**
	 * @param code
	 *            the code to set
	 * @author taitm
	 */
	public void setCode(String code) {
		this.code = code;
	}

	/**
	 * @return the indexLangActive
	 * @author taitm
	 */
	public Integer getIndexLangActive() {
		return indexLangActive;
	}

	/**
	 * @param indexLangActive
	 *            the indexLangActive to set
	 * @author taitm
	 */
	public void setIndexLangActive(Integer indexLangActive) {
		this.indexLangActive = indexLangActive;
	}

	/**
	 * @return the sort
	 * @author taitm
	 */
	public Long getSort() {
		return sort;
	}

	/**
	 * @param sort
	 *            the sort to set
	 * @author taitm
	 */
	public void setSort(Long sort) {
		this.sort = sort;
	}

	/**
	 * @return the hotNews
	 * @author taitm
	 */
	public Boolean getHotNews() {
		return hotNews;
	}

	/**
	 * @param hotNews
	 *            the hotNews to set
	 * @author taitm
	 */
	public void setHotNews(Boolean hotNews) {
		this.hotNews = hotNews;
	}

	/**
	 * @return the homepage
	 * @author taitm
	 */
	public Boolean getHomepage() {
		return homepage;
	}

	/**
	 * @param homepage
	 *            the homepage to set
	 * @author taitm
	 */
	public void setHomepage(Boolean homepage) {
		this.homepage = homepage;
	}

	/**
	 * @return the statusSearch
	 * @author taitm
	 */
	public Integer getStatusSearch() {
		return statusSearch;
	}

	/**
	 * @param statusSearch
	 *            the statusSearch to set
	 * @author taitm
	 */
	public void setStatusSearch(Integer statusSearch) {
		this.statusSearch = statusSearch;
	}

	/**
	 * @return the kindSearch
	 * @author taitm
	 */
	public Integer getKindSearch() {
		return kindSearch;
	}

	/**
	 * @param kindSearch
	 *            the kindSearch to set
	 * @author taitm
	 */
	public void setKindSearch(Integer kindSearch) {
		this.kindSearch = kindSearch;
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

	/**
	 * @return the nameSearch
	 * @author taitm
	 */
	public String getNameSearch() {
		return nameSearch;
	}

	/**
	 * @param nameSearch
	 *            the nameSearch to set
	 * @author taitm
	 */
	public void setNameSearch(String nameSearch) {
		this.nameSearch = nameSearch;
	}

	/**
	 * @return the title
	 * @author taitm
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * @param title
	 *            the title to set
	 * @author taitm
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * @return the postedDate
	 * @author taitm
	 */
	public Date getPostedDate() {
		return postedDate;
	}

	/**
	 * @param postedDate
	 *            the postedDate to set
	 * @author taitm
	 */
	public void setPostedDate(Date postedDate) {
		this.postedDate = postedDate;
	}

}
