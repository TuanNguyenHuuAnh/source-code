/*******************************************************************************
 * Class        ：DocumentType
 * Created date ：2017/04/17
 * Lasted date  ：2017/04/17
 * Author       ：thuydtn
 * Change log   ：2017/04/17:01-00 thuydtn create a new
 ******************************************************************************/
package vn.com.unit.cms.admin.all.entity;

import org.springframework.data.annotation.Id;

import jp.sf.amateras.mirage.annotation.Column;
import jp.sf.amateras.mirage.annotation.PrimaryKey;
import jp.sf.amateras.mirage.annotation.PrimaryKey.GenerationType;
import jp.sf.amateras.mirage.annotation.Table;
import vn.com.unit.cms.admin.all.dto.DocumentTypeDto;
import vn.com.unit.cms.core.entity.AbstractTracking;

/**
 * 
 * DocumentType
 * 
 * @version 01-00
 * @since 01-00
 * @author thuydtn
 */
@Table(name = "m_document_type")
public class DocumentType extends AbstractTracking {

	@Id
	@Column(name = "id")
	@PrimaryKey(generationType = GenerationType.SEQUENCE, generator = "SEQ_M_DOCUMENT_TYPE")
	private Long id;
	@Column(name = "code")
	private String code;
	@Column(name = "name")
	private String name;
	@Column(name = "note")
	private String note;
	@Column(name = "description")
	private String description;
	@Column(name = "sort_order")
	private Long sortOrder;

	@Column(name = "enabled")
	private boolean enabled;

	@Column(name = "is_interest_rate")
	private boolean interestRate;

	@Column(name = "m_customer_type_id")
	private Long customerTypeId;

	@Column(name = "before_id")
	private Long beforeId;

	@Column(name = "status")
	private Integer status;

	@Column(name = "approve_by")
	private String approveBy;

	@Column(name = "publish_by")
	private String publishBy;

	@Column(name = "type_comment")
	private String typeComment;

	@Column(name = "process_id")
	private Long processId;

	/**
	 * @param updateDto
	 */
	public void copyDtoProperties(DocumentTypeDto updateDto) {
		this.setCode(updateDto.getCode());
		this.setName(updateDto.getName());
		this.setDescription(updateDto.getDescription());
		this.setNote(updateDto.getNote());

		this.setEnabled(updateDto.isEnabled());
		this.setCustomerTypeId(Long.valueOf(updateDto.getCustomerTypeId()));
		this.setBeforeId(updateDto.getBeforeId());
		this.setStatus(updateDto.getStatus());

		this.setApproveBy(updateDto.getApprovedBy());
		this.setPublishBy(updateDto.getPublishedBy());

		this.setSortOrder(updateDto.getSortOrder());
		this.setTypeComment(updateDto.getTypeComment());
		this.setInterestRate(updateDto.isInterestRate());
		
		this.setProcessId(updateDto.getProcessId());
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
		this.code = code;
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
	 * Get sortOrder
	 * 
	 * @return int
	 * @author thuydtn
	 */
	public Long getSortOrder() {
		return sortOrder;
	}

	/**
	 * Set sortOrder
	 * 
	 * @param sortOrder
	 *            type int
	 * @return
	 * @author thuydtn
	 */
	public void setSortOrder(Long sortOrder) {
		this.sortOrder = sortOrder;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public Long getCustomerTypeId() {
		return customerTypeId;
	}

	public void setCustomerTypeId(Long customerTypeId) {
		this.customerTypeId = customerTypeId;
	}

	public Long getBeforeId() {
		return beforeId;
	}

	public void setBeforeId(Long beforeId) {
		this.beforeId = beforeId;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getApproveBy() {
		return approveBy;
	}

	public void setApproveBy(String approveBy) {
		this.approveBy = approveBy;
	}

	public String getPublishBy() {
		return publishBy;
	}

	public void setPublishBy(String publishBy) {
		this.publishBy = publishBy;
	}

	public String getTypeComment() {
		return typeComment;
	}

	public void setTypeComment(String typeComment) {
		this.typeComment = typeComment;
	}

	public boolean isInterestRate() {
		return interestRate;
	}

	public void setInterestRate(boolean interestRate) {
		this.interestRate = interestRate;
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

}
