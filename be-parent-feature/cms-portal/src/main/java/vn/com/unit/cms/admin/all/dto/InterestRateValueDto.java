
package vn.com.unit.cms.admin.all.dto;

import java.util.Date;
import java.util.List;

import vn.com.unit.ep2p.core.res.dto.DocumentActionReq;
import vn.com.unit.ep2p.dto.JProcessStepDto;

public class InterestRateValueDto extends DocumentActionReq {

	private Long id;

	private String interestRateType;

	private String languageCode;

	private String title;

	private String value01;

	private String value02;

	private String value03;

	private String value04;

	private String value05;

	private String value06;

	private String value07;

	private String value08;

	private String value09;

	private String value10;

	private Long customerTypeId;

	private List<String> lstValues;

	private List<InterestRateValueDto> datas;

	/** status */
	private Integer status;

	/** processId */
	private Long processId;

	/** button action */
	private String buttonAction;

	/** Button id */
//	private String buttonId;

	private List<JProcessStepDto> stepBtnList;

	/** Status name */
	private String statusName;

	/** Customer Alias */
	private String customerAlias;

	private String currItem;

	private String note;

	private Integer oldStatus;

	private String referenceType;

	private Date updateDate;

	private Long referenceId;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getInterestRateType() {
		return interestRateType;
	}

	public void setInterestRateType(String interestRateType) {
		this.interestRateType = interestRateType;
	}

	public String getLanguageCode() {
		return languageCode;
	}

	public void setLanguageCode(String languageCode) {
		this.languageCode = languageCode;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public List<InterestRateValueDto> getDatas() {
		return datas;
	}

	public void setDatas(List<InterestRateValueDto> datas) {
		this.datas = datas;
	}

	public Long getCustomerTypeId() {
		return customerTypeId;
	}

	public void setCustomerTypeId(Long customerTypeId) {
		this.customerTypeId = customerTypeId;
	}

	public String getValue01() {
		return value01;
	}

	public void setValue01(String value01) {
		this.value01 = value01;
	}

	public String getValue02() {
		return value02;
	}

	public void setValue02(String value02) {
		this.value02 = value02;
	}

	public String getValue03() {
		return value03;
	}

	public void setValue03(String value03) {
		this.value03 = value03;
	}

	public String getValue04() {
		return value04;
	}

	public void setValue04(String value04) {
		this.value04 = value04;
	}

	public String getValue05() {
		return value05;
	}

	public void setValue05(String value05) {
		this.value05 = value05;
	}

	public String getValue06() {
		return value06;
	}

	public void setValue06(String value06) {
		this.value06 = value06;
	}

	public String getValue07() {
		return value07;
	}

	public void setValue07(String value07) {
		this.value07 = value07;
	}

	public String getValue08() {
		return value08;
	}

	public void setValue08(String value08) {
		this.value08 = value08;
	}

	public String getValue09() {
		return value09;
	}

	public void setValue09(String value09) {
		this.value09 = value09;
	}

	public String getValue10() {
		return value10;
	}

	public void setValue10(String value10) {
		this.value10 = value10;
	}

	public List<String> getLstValues() {
		return lstValues;
	}

	public void setLstValues(List<String> lstValues) {
		this.lstValues = lstValues;
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
	 * @return the note
	 */
	public String getNote() {
		return note;
	}

	/**
	 * @param note the note to set
	 */
	public void setNote(String note) {
		this.note = note;
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

}