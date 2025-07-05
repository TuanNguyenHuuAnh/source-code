/*******************************************************************************
 * Class        ：NewsTypeLanguageSearchDto
 * Created date ：2017/02/28
 * Lasted date  ：2017/02/28
 * Author       ：hand
 * Change log   ：2017/02/28：01-00 hand create a new
 ******************************************************************************/
package vn.com.unit.cms.admin.all.dto;

import java.util.Date;

/**
 * NewsTypeLanguageSearchDto
 * 
 * @version 01-00
 * @since 01-00
 * @author hand
 */
public class DocumentTypeLanguageSearchDto {

	/** id */
	private Long id;

	/** code */
	private String code;

	/** name */
	private String name;

	/** label */
	private String label;

	/** sort */
	private Long sort;

	/** enabled */
	private int enabled;

	/** description */
	private String description;

	/** createDate */
	private Date createDate;

	private String statusName;

	private String createBy;

	private Integer status;

	private String typeComment;

	private Integer stt;

	private String note;

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
	 * Set id
	 * 
	 * @param id type Long
	 * @return
	 * @author hand
	 */
	public void setId(Long id) {
		this.id = id;
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
	 * Set code
	 * 
	 * @param code type String
	 * @return
	 * @author hand
	 */
	public void setCode(String code) {
		this.code = code;
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
	 * Set name
	 * 
	 * @param name type String
	 * @return
	 * @author hand
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Get label
	 * 
	 * @return String
	 * @author hand
	 */
	public String getLabel() {
		return label;
	}

	/**
	 * Set label
	 * 
	 * @param label type String
	 * @return
	 * @author hand
	 */
	public void setLabel(String label) {
		this.label = label;
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
	 * Set sort
	 * 
	 * @param sort type Long
	 * @return
	 * @author hand
	 */
	public void setSort(Long sort) {
		this.sort = sort;
	}

	/**
	 * Get enabled
	 * 
	 * @return int
	 * @author hand
	 */
	public int getEnabled() {
		return enabled;
	}

	/**
	 * Set enabled
	 * 
	 * @param enabled type int
	 * @return
	 * @author hand
	 */
	public void setEnabled(int enabled) {
		this.enabled = enabled;
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
	 * Set description
	 * 
	 * @param description type String
	 * @return
	 * @author hand
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * Get createDate
	 * 
	 * @return Date
	 * @author hand
	 */
	public Date getCreateDate() {
		return createDate;
	}

	/**
	 * Set createDate
	 * 
	 * @param createDate type Date
	 * @return
	 * @author hand
	 */
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
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
	 * @return the typeComment
	 */
	public String getTypeComment() {
		return typeComment;
	}

	/**
	 * @param typeComment the typeComment to set
	 */
	public void setTypeComment(String typeComment) {
		this.typeComment = typeComment;
	}

	/**
	 * @return the stt
	 */
	public Integer getStt() {
		return stt;
	}

	/**
	 * @param stt the stt to set
	 */
	public void setStt(Integer stt) {
		this.stt = stt;
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
}
