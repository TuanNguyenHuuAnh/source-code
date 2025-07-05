/*******************************************************************************
 * Class        ：BannerEditDto
 * Created date ：2017/02/20
 * Lasted date  ：2017/02/20
 * Author       ：hand
 * Change log   ：2017/02/20：01-00 hand create a new
 ******************************************************************************/
package vn.com.unit.cms.admin.all.dto;

import java.util.Date;
import java.util.List;

import javax.validation.Valid;

//import vn.com.unit.cms.admin.all.entity.Banner;
import vn.com.unit.cms.admin.all.jcanary.dto.HistoryApproveDto;
import vn.com.unit.cms.core.module.banner.entity.Banner;
import vn.com.unit.ep2p.core.res.dto.DocumentActionReq;
import vn.com.unit.ep2p.dto.JProcessStepDto;

/**
 * HomepageEditDto
 * 
 * @version 01-00
 * @since 01-00
 * @author sonnt
 */
public class HomepageSettingEditDto extends DocumentActionReq {

	private Long id;

	private int speedRoll;

	private List<String> bannerTop;

	private List<Banner> bannerTopList;

	private List<String> bannerTopMobile;

	private List<Banner> bannerTopMobileList;

	private Date effectiveDateFrom;

	private Date effectiveDateTo;

	private String description;

	private String note;

	private boolean action;

	private String url;

	private Long ownerId;

	private Long ownerBranchId;

	private Long ownerSectionId;

	private Long assignerId;

	private Long assignerBranchId;

	private Long assignerSectionId;

	private String approveBy;

	private String createBy;

	private String publishBy;

	private String statusCode;

	private Long processId;

	private String comment;

	private Long referenceId;

	private String referenceType;

	private String bannerPage;

	private Integer status;

	private List<JProcessStepDto> stepBtnList;

	/** requestToken */
	private String requestToken;

	/** button action */
	private String buttonAction;

	/** Button id */
//	private String buttonId;

	/** Customer Alias */
	private String customerAlias;

	/** Status name */
	private String statusName;

	/** Business code */
	private String businessCode;

	private String currItem;

	/** status */
	private Integer oldStatus;

	@Valid
	private List<HistoryApproveDto> historyApproveDtos;

	private String startDateSearch;

	private String endDateSearch;

	private Integer statusSearch;

	private String bannerPageSearch;

	private Date updateDate;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public int getSpeedRoll() {
		return speedRoll;
	}

	public void setSpeedRoll(int speedRoll) {
		this.speedRoll = speedRoll;
	}

	public List<String> getBannerTop() {
		return bannerTop;
	}

	public void setBannerTop(List<String> bannerTop) {
		this.bannerTop = bannerTop;
	}

	public List<Banner> getBannerTopList() {
		return bannerTopList;
	}

	public void setBannerTopList(List<Banner> bannerTopList) {
		this.bannerTopList = bannerTopList;
	}

	public List<String> getBannerTopMobile() {
		return bannerTopMobile;
	}

	public void setBannerTopMobile(List<String> bannerTopMobile) {
		this.bannerTopMobile = bannerTopMobile;
	}

	public List<Banner> getBannerTopMobileList() {
		return bannerTopMobileList;
	}

	public void setBannerTopMobileList(List<Banner> bannerTopMobileList) {
		this.bannerTopMobileList = bannerTopMobileList;
	}

	public Date getEffectiveDateFrom() {
		return effectiveDateFrom;
	}

	public void setEffectiveDateFrom(Date effectiveDateFrom) {
		this.effectiveDateFrom = effectiveDateFrom;
	}

	public Date getEffectiveDateTo() {
		return effectiveDateTo;
	}

	public void setEffectiveDateTo(Date effectiveDateTo) {
		this.effectiveDateTo = effectiveDateTo;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public String getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(String statusCode) {
		this.statusCode = statusCode;
	}

	public boolean isAction() {
		return action;
	}

	public void setAction(boolean action) {
		this.action = action;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Long getOwnerId() {
		return ownerId;
	}

	public void setOwnerId(Long ownerId) {
		this.ownerId = ownerId;
	}

	public Long getOwnerBranchId() {
		return ownerBranchId;
	}

	public void setOwnerBranchId(Long ownerBranchId) {
		this.ownerBranchId = ownerBranchId;
	}

	public Long getOwnerSectionId() {
		return ownerSectionId;
	}

	public void setOwnerSectionId(Long ownerSectionId) {
		this.ownerSectionId = ownerSectionId;
	}

	public Long getAssignerId() {
		return assignerId;
	}

	public void setAssignerId(Long assignerId) {
		this.assignerId = assignerId;
	}

	public Long getAssignerBranchId() {
		return assignerBranchId;
	}

	public void setAssignerBranchId(Long assignerBranchId) {
		this.assignerBranchId = assignerBranchId;
	}

	public Long getAssignerSectionId() {
		return assignerSectionId;
	}

	public void setAssignerSectionId(Long assignerSectionId) {
		this.assignerSectionId = assignerSectionId;
	}

	public Long getProcessId() {
		return processId;
	}

	public void setProcessId(Long processId) {
		this.processId = processId;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public Long getReferenceId() {
		return referenceId;
	}

	public void setReferenceId(Long referenceId) {
		this.referenceId = referenceId;
	}

	public String getReferenceType() {
		return referenceType;
	}

	public void setReferenceType(String referenceType) {
		this.referenceType = referenceType;
	}

	/**
	 * Get bannerPage
	 * 
	 * @return String
	 * @author TranLTH
	 */
	public String getBannerPage() {
		return bannerPage;
	}

	/**
	 * Set bannerPage
	 * 
	 * @param bannerPage type String
	 * @return
	 * @author TranLTH
	 */
	public void setBannerPage(String bannerPage) {
		this.bannerPage = bannerPage;
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
	 * @param historyApproveDtos type List<HistoryApproveDto>
	 * @return
	 * @author TranLTH
	 */
	public void setHistoryApproveDtos(List<HistoryApproveDto> historyApproveDtos) {
		this.historyApproveDtos = historyApproveDtos;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
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
	 * @param requestToken type String
	 * @return
	 * @author taitm
	 */
	public void setRequestToken(String requestToken) {
		this.requestToken = requestToken;
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

	/**
	 * @return the approveBy
	 * @author taitm
	 */
	public String getApproveBy() {
		return approveBy;
	}

	/**
	 * @param approveBy the approveBy to set
	 * @author taitm
	 */
	public void setApproveBy(String approveBy) {
		this.approveBy = approveBy;
	}

	/**
	 * @return the createBy
	 * @author taitm
	 */
	public String getCreateBy() {
		return createBy;
	}

	/**
	 * @param createBy the createBy to set
	 * @author taitm
	 */
	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}

	/**
	 * @return the publishBy
	 * @author taitm
	 */
	public String getPublishBy() {
		return publishBy;
	}

	/**
	 * @param publishBy the publishBy to set
	 * @author taitm
	 */
	public void setPublishBy(String publishBy) {
		this.publishBy = publishBy;
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
	 * @return the startDateSearch
	 * @author taitm
	 */
	public String getStartDateSearch() {
		return startDateSearch;
	}

	/**
	 * @param startDateSearch the startDateSearch to set
	 * @author taitm
	 */
	public void setStartDateSearch(String startDateSearch) {
		this.startDateSearch = startDateSearch;
	}

	/**
	 * @return the endDateSearch
	 * @author taitm
	 */
	public String getEndDateSearch() {
		return endDateSearch;
	}

	/**
	 * @param endDateSearch the endDateSearch to set
	 * @author taitm
	 */
	public void setEndDateSearch(String endDateSearch) {
		this.endDateSearch = endDateSearch;
	}

	/**
	 * @return the statusSearch
	 * @author taitm
	 */
	public Integer getStatusSearch() {
		return statusSearch;
	}

	/**
	 * @param statusSearch the statusSearch to set
	 * @author taitm
	 */
	public void setStatusSearch(Integer statusSearch) {
		this.statusSearch = statusSearch;
	}

	/**
	 * @return the bannerPageSearch
	 * @author taitm
	 */
	public String getBannerPageSearch() {
		return bannerPageSearch;
	}

	/**
	 * @param bannerPageSearch the bannerPageSearch to set
	 * @author taitm
	 */
	public void setBannerPageSearch(String bannerPageSearch) {
		this.bannerPageSearch = bannerPageSearch;
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
}