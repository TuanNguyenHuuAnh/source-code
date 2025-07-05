package vn.com.unit.ep2p.admin.sla.dto;

import java.util.Date;

//import jp.sf.amateras.mirage.annotation.Column;

/**
 * @author TuyenTD
 *
 */
public class SlaAlertHistoryDto {

	private Date alertDate;

	private String createdBy;

	private Date createdDate;

	private String deletedBy;

	private Date deletedDate;

	private Long id;

	private Long referenceId;

	private String reference;

	private Long slaSettingId;

	private Long stepId;

	private String type;

	private String updatedBy;

	private Date updatedDate;

	private Date deadline;

	private Date submitDate;

	private String link;

	private String summary;

	private String status;

	private String submitBy;

	public SlaAlertHistoryDto() {
	}

	public Date getAlertDate() {
		return this.alertDate;
	}

	public void setAlertDate(Date alertDate) {
		this.alertDate = alertDate;
	}

	public String getCreatedBy() {
		return this.createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public Date getCreatedDate() {
		return this.createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public String getDeletedBy() {
		return this.deletedBy;
	}

	public void setDeletedBy(String deletedBy) {
		this.deletedBy = deletedBy;
	}

	public Date getDeletedDate() {
		return this.deletedDate;
	}

	public void setDeletedDate(Date deletedDate) {
		this.deletedDate = deletedDate;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getReferenceId() {
		return referenceId;
	}

	public void setReferenceId(Long referenceId) {
		this.referenceId = referenceId;
	}

	public String getReference() {
		return reference;
	}

	public void setReference(String reference) {
		this.reference = reference;
	}

	public Long getStepId() {
		return stepId;
	}

	public void setStepId(Long stepId) {
		this.stepId = stepId;
	}

	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getUpdatedBy() {
		return this.updatedBy;
	}

	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}

	public Date getUpdatedDate() {
		return this.updatedDate;
	}

	public void setUpdatedDate(Date updatedDate) {
		this.updatedDate = updatedDate;
	}

	public Date getDeadline() {
		return deadline;
	}

	public void setDeadline(Date deadline) {
		this.deadline = deadline;
	}

	public Date getSubmitDate() {
		return submitDate;
	}

	public void setSubmitDate(Date submitDate) {
		this.submitDate = submitDate;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getSubmitBy() {
		return submitBy;
	}

	public void setSubmitBy(String submitBy) {
		this.submitBy = submitBy;
	}

	public Long getSlaSettingId() {
		return slaSettingId;
	}

	public void setSlaSettingId(Long slaSettingId) {
		this.slaSettingId = slaSettingId;
	}

}