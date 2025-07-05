package vn.com.unit.cms.admin.all.dto;

import java.util.Date;
import java.util.List;

import javax.validation.Valid;

import vn.com.unit.cms.admin.all.jcanary.dto.HistoryApproveDto;
//import vn.com.unit.cms.core.entity.AbstractTracking;
import vn.com.unit.ep2p.core.res.dto.DocumentActionReq;
import vn.com.unit.ep2p.dto.JProcessStepDto;

public class CustomerVipEditDto extends DocumentActionReq {
	private Long id;

	private String code;

	private String imgUrl;

	private String imgName;

	private String imgMobileUrl;

	private String imgMobileName;

	private String iconUrl;

	private String iconName;

	private String iconMobileUrl;

	private String iconMobileName;
	
	private String bannerUrl;

	private String bannerName;

	private String bannerMobileUrl;

	private String bannerMobileName;

	private Long sort;

	private boolean enabled;

	private Integer status;

	private String comment;

	private Date createDate;

	private String createBy;

	private Date updateDate;

	private String updateBy;

	private Date deleteDate;

	private String deleteBy;

	private Long processId;

	/** productLanguageList */
	@Valid
	private List<CustomerVipLanguageEditDto> customerVipLanguageList;

	@Valid
	private List<HistoryApproveDto> historyApproveDtos;

	private List<JProcessStepDto> stepBtnList;

	/** button action */
	private String buttonAction;

	/** Button id */
	private Long buttonId;

	private String currItem;

	private String statusCode;

	private Integer oldStatus;

	private String url;

	private String searchDto;

	private Integer indexLangActive;

	/** requestToken */
	private String requestToken;

	private String statusName;

	/** referenceId */
	private Long referenceId;

	/** referenceType */
	private String referenceType;

	private String approvedBy;

	private Date approvedDate;

	private String publishedBy;

	private Date publishedDate;

	private String languageCode;

	private Integer vip;

	private Integer fdi;
	
	private String customerAlias;
	
	private String buttonCode;

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @return the code
	 */
	public String getCode() {
		return code;
	}

	/**
	 * @param code the code to set
	 */
	public void setCode(String code) {
		this.code = code;
	}

	/**
	 * @return the imgUrl
	 */
	public String getImgUrl() {
		return imgUrl;
	}

	/**
	 * @param imgUrl the imgUrl to set
	 */
	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}

	/**
	 * @return the imgName
	 */
	public String getImgName() {
		return imgName;
	}

	/**
	 * @param imgName the imgName to set
	 */
	public void setImgName(String imgName) {
		this.imgName = imgName;
	}

	/**
	 * @return the imgMobileUrl
	 */
	public String getImgMobileUrl() {
		return imgMobileUrl;
	}

	/**
	 * @param imgMobileUrl the imgMobileUrl to set
	 */
	public void setImgMobileUrl(String imgMobileUrl) {
		this.imgMobileUrl = imgMobileUrl;
	}

	/**
	 * @return the imgMobileName
	 */
	public String getImgMobileName() {
		return imgMobileName;
	}

	/**
	 * @param imgMobileName the imgMobileName to set
	 */
	public void setImgMobileName(String imgMobileName) {
		this.imgMobileName = imgMobileName;
	}

	/**
	 * @return the iconUrl
	 */
	public String getIconUrl() {
		return iconUrl;
	}

	/**
	 * @param iconUrl the iconUrl to set
	 */
	public void setIconUrl(String iconUrl) {
		this.iconUrl = iconUrl;
	}

	/**
	 * @return the iconName
	 */
	public String getIconName() {
		return iconName;
	}

	/**
	 * @param iconName the iconName to set
	 */
	public void setIconName(String iconName) {
		this.iconName = iconName;
	}

	/**
	 * @return the iconMobileUrl
	 */
	public String getIconMobileUrl() {
		return iconMobileUrl;
	}

	/**
	 * @param iconMobileUrl the iconMobileUrl to set
	 */
	public void setIconMobileUrl(String iconMobileUrl) {
		this.iconMobileUrl = iconMobileUrl;
	}

	/**
	 * @return the iconMobileName
	 */
	public String getIconMobileName() {
		return iconMobileName;
	}

	/**
	 * @param iconMobileName the iconMobileName to set
	 */
	public void setIconMobileName(String iconMobileName) {
		this.iconMobileName = iconMobileName;
	}

	/**
	 * @return the sort
	 */
	public Long getSort() {
		return sort;
	}

	/**
	 * @param sort the sort to set
	 */
	public void setSort(Long sort) {
		this.sort = sort;
	}

	/**
	 * @return the enabled
	 */
	public boolean isEnabled() {
		return enabled;
	}

	/**
	 * @param enabled the enabled to set
	 */
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	/**
	 * @return the status
	 */
	public Integer getStatus() {
		return status;
	}

	/**
	 * @param status the status to set
	 */
	public void setStatus(Integer status) {
		this.status = status;
	}

	/**
	 * @return the comment
	 */
	public String getComment() {
		return comment;
	}

	/**
	 * @param comment the comment to set
	 */
	public void setComment(String comment) {
		this.comment = comment;
	}

	/**
	 * @return the createDate
	 */
	public Date getCreateDate() {
		return createDate;
	}

	/**
	 * @param createDate the createDate to set
	 */
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	/**
	 * @return the createBy
	 */
	public String getCreateBy() {
		return createBy;
	}

	/**
	 * @param createBy the createBy to set
	 */
	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}

	/**
	 * @return the updateDate
	 */
	public Date getUpdateDate() {
		return updateDate;
	}

	/**
	 * @param updateDate the updateDate to set
	 */
	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	/**
	 * @return the updateBy
	 */
	public String getUpdateBy() {
		return updateBy;
	}

	/**
	 * @param updateBy the updateBy to set
	 */
	public void setUpdateBy(String updateBy) {
		this.updateBy = updateBy;
	}

	/**
	 * @return the deleteDate
	 */
	public Date getDeleteDate() {
		return deleteDate;
	}

	/**
	 * @param deleteDate the deleteDate to set
	 */
	public void setDeleteDate(Date deleteDate) {
		this.deleteDate = deleteDate;
	}

	/**
	 * @return the deleteBy
	 */
	public String getDeleteBy() {
		return deleteBy;
	}

	/**
	 * @param deleteBy the deleteBy to set
	 */
	public void setDeleteBy(String deleteBy) {
		this.deleteBy = deleteBy;
	}

	/**
	 * @return the customerVipLanguageList
	 */
	public List<CustomerVipLanguageEditDto> getCustomerVipLanguageList() {
		return customerVipLanguageList;
	}

	/**
	 * @param customerVipLanguageList the customerVipLanguageList to set
	 */
	public void setCustomerVipLanguageList(List<CustomerVipLanguageEditDto> customerVipLanguageList) {
		this.customerVipLanguageList = customerVipLanguageList;
	}

	/**
	 * @return the historyApproveDtos
	 */
	public List<HistoryApproveDto> getHistoryApproveDtos() {
		return historyApproveDtos;
	}

	/**
	 * @param historyApproveDtos the historyApproveDtos to set
	 */
	public void setHistoryApproveDtos(List<HistoryApproveDto> historyApproveDtos) {
		this.historyApproveDtos = historyApproveDtos;
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

	/**
	 * @return the buttonId
	 */
	public Long getButtonId() {
		return buttonId;
	}

	/**
	 * @param buttonId the buttonId to set
	 */
	public void setButtonId(Long buttonId) {
		this.buttonId = buttonId;
	}

	/**
	 * @return the currItem
	 */
	public String getCurrItem() {
		return currItem;
	}

	/**
	 * @param currItem the currItem to set
	 */
	public void setCurrItem(String currItem) {
		this.currItem = currItem;
	}

	/**
	 * @return the statusCode
	 */
	public String getStatusCode() {
		return statusCode;
	}

	/**
	 * @param statusCode the statusCode to set
	 */
	public void setStatusCode(String statusCode) {
		this.statusCode = statusCode;
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
	 * @return the searchDto
	 */
	public String getSearchDto() {
		return searchDto;
	}

	/**
	 * @param searchDto the searchDto to set
	 */
	public void setSearchDto(String searchDto) {
		this.searchDto = searchDto;
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
	 * @return the requestToken
	 */
	public String getRequestToken() {
		return requestToken;
	}

	/**
	 * @param requestToken the requestToken to set
	 */
	public void setRequestToken(String requestToken) {
		this.requestToken = requestToken;
	}

	/**
	 * @return the processId
	 */
	public Long getProcessId() {
		return processId;
	}

	/**
	 * @param processId the processId to set
	 */
	public void setProcessId(Long processId) {
		this.processId = processId;
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
	 * @return the referenceId
	 */
	public Long getReferenceId() {
		return referenceId;
	}

	/**
	 * @param referenceId the referenceId to set
	 */
	public void setReferenceId(Long referenceId) {
		this.referenceId = referenceId;
	}

	/**
	 * @return the referenceType
	 */
	public String getReferenceType() {
		return referenceType;
	}

	/**
	 * @param referenceType the referenceType to set
	 */
	public void setReferenceType(String referenceType) {
		this.referenceType = referenceType;
	}

	/**
	 * @return the approvedBy
	 */
	public String getApprovedBy() {
		return approvedBy;
	}

	/**
	 * @param approvedBy the approvedBy to set
	 */
	public void setApprovedBy(String approvedBy) {
		this.approvedBy = approvedBy;
	}

	/**
	 * @return the approvedDate
	 */
	public Date getApprovedDate() {
		return approvedDate;
	}

	/**
	 * @param approvedDate the approvedDate to set
	 */
	public void setApprovedDate(Date approvedDate) {
		this.approvedDate = approvedDate;
	}

	/**
	 * @return the publishedBy
	 */
	public String getPublishedBy() {
		return publishedBy;
	}

	/**
	 * @param publishedBy the publishedBy to set
	 */
	public void setPublishedBy(String publishedBy) {
		this.publishedBy = publishedBy;
	}

	/**
	 * @return the publishedDate
	 */
	public Date getPublishedDate() {
		return publishedDate;
	}

	/**
	 * @param publishedDate the publishedDate to set
	 */
	public void setPublishedDate(Date publishedDate) {
		this.publishedDate = publishedDate;
	}

	/**
	 * @return the languageCode
	 */
	public String getLanguageCode() {
		return languageCode;
	}

	/**
	 * @param languageCode the languageCode to set
	 */
	public void setLanguageCode(String languageCode) {
		this.languageCode = languageCode;
	}

	/**
	 * @return the vip
	 */
	public Integer getVip() {
		return vip;
	}

	/**
	 * @param vip the vip to set
	 */
	public void setVip(Integer vip) {
		this.vip = vip;
	}

	/**
	 * @return the fdi
	 */
	public Integer getFdi() {
		return fdi;
	}

	/**
	 * @param fdi the fdi to set
	 */
	public void setFdi(Integer fdi) {
		this.fdi = fdi;
	}

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
	 * @return the bannerUrl
	 */
	public String getBannerUrl() {
		return bannerUrl;
	}

	/**
	 * @param bannerUrl the bannerUrl to set
	 */
	public void setBannerUrl(String bannerUrl) {
		this.bannerUrl = bannerUrl;
	}

	/**
	 * @return the bannerName
	 */
	public String getBannerName() {
		return bannerName;
	}

	/**
	 * @param bannerName the bannerName to set
	 */
	public void setBannerName(String bannerName) {
		this.bannerName = bannerName;
	}

	/**
	 * @return the bannerMobileUrl
	 */
	public String getBannerMobileUrl() {
		return bannerMobileUrl;
	}

	/**
	 * @param bannerMobileUrl the bannerMobileUrl to set
	 */
	public void setBannerMobileUrl(String bannerMobileUrl) {
		this.bannerMobileUrl = bannerMobileUrl;
	}

	/**
	 * @return the bannerMobileName
	 */
	public String getBannerMobileName() {
		return bannerMobileName;
	}

	/**
	 * @param bannerMobileName the bannerMobileName to set
	 */
	public void setBannerMobileName(String bannerMobileName) {
		this.bannerMobileName = bannerMobileName;
	}

	public String getButtonCode() {
		return buttonCode;
	}

	public void setButtonCode(String buttonCode) {
		this.buttonCode = buttonCode;
	}
	
}
