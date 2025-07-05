/*******************************************************************************
 * Class        :ComponentDto
 * Created date :2019/04/22
 * Lasted date  :2019/04/22
 * Author       :HungHT
 * Change log   :2019/04/22:01-00 HungHT create a new
 ******************************************************************************/
package vn.com.unit.ep2p.admin.dto;

import java.util.Date;

/**
 * ComponentDto
 * @version 01-00
 * @since 01-00
 * @author HungHT
 */
public class ComponentDto {

	private Long id;
	private Long formId;
	private String compId;
	private String compName;
	private Long displayOrder;
	private String activeFlag;
	private String createdBy;
	private Date createdDate;
	private String updatedBy;
	private Date updatedDate;
	private String deletedBy;
	private Date deletedDate;

	/**
	* Set id
	* @param id
	*        type Long
	* @return
	* @author HungHT
	*/
	public void setId(Long id) {
		this.id = id;
	}

	/**
	* Get id
	* @return Long
	* @author HungHT
	*/
	public Long getId() {
		return id;
	}

	/**
	* Set formId
	* @param formId
	*        type Long
	* @return
	* @author HungHT
	*/
	public void setFormId(Long formId) {
		this.formId = formId;
	}

	/**
	* Get formId
	* @return Long
	* @author HungHT
	*/
	public Long getFormId() {
		return formId;
	}

	/**
	* Set compId
	* @param compId
	*        type String
	* @return
	* @author HungHT
	*/
	public void setCompId(String compId) {
		this.compId = compId;
	}

	/**
	* Get compId
	* @return String
	* @author HungHT
	*/
	public String getCompId() {
		return compId;
	}

	/**
	* Set compName
	* @param compName
	*        type String
	* @return
	* @author HungHT
	*/
	public void setCompName(String compName) {
		this.compName = compName;
	}

	/**
	* Get compName
	* @return String
	* @author HungHT
	*/
	public String getCompName() {
		return compName;
	}

	/**
	* Set displayOrder
	* @param displayOrder
	*        type Long
	* @return
	* @author HungHT
	*/
	public void setDisplayOrder(Long displayOrder) {
		this.displayOrder = displayOrder;
	}

	/**
	* Get displayOrder
	* @return Long
	* @author HungHT
	*/
	public Long getDisplayOrder() {
		return displayOrder;
	}

	/**
	* Set activeFlag
	* @param activeFlag
	*        type String
	* @return
	* @author HungHT
	*/
	public void setActiveFlag(String activeFlag) {
		this.activeFlag = activeFlag;
	}

	/**
	* Get activeFlag
	* @return String
	* @author HungHT
	*/
	public String getActiveFlag() {
		return activeFlag;
	}

	/**
	* Set createdBy
	* @param createdBy
	*        type String
	* @return
	* @author HungHT
	*/
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	/**
	* Get createdBy
	* @return String
	* @author HungHT
	*/
	public String getCreatedBy() {
		return createdBy;
	}

	/**
	* Set createdDate
	* @param createdDate
	*        type Date
	* @return
	* @author HungHT
	*/
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	/**
	* Get createdDate
	* @return Date
	* @author HungHT
	*/
	public Date getCreatedDate() {
		return createdDate;
	}

	/**
	* Set updatedBy
	* @param updatedBy
	*        type String
	* @return
	* @author HungHT
	*/
	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}

	/**
	* Get updatedBy
	* @return String
	* @author HungHT
	*/
	public String getUpdatedBy() {
		return updatedBy;
	}

	/**
	* Set updatedDate
	* @param updatedDate
	*        type Date
	* @return
	* @author HungHT
	*/
	public void setUpdatedDate(Date updatedDate) {
		this.updatedDate = updatedDate;
	}

	/**
	* Get updatedDate
	* @return Date
	* @author HungHT
	*/
	public Date getUpdatedDate() {
		return updatedDate;
	}

	/**
	* Set deletedBy
	* @param deletedBy
	*        type String
	* @return
	* @author HungHT
	*/
	public void setDeletedBy(String deletedBy) {
		this.deletedBy = deletedBy;
	}

	/**
	* Get deletedBy
	* @return String
	* @author HungHT
	*/
	public String getDeletedBy() {
		return deletedBy;
	}

	/**
	* Set deletedDate
	* @param deletedDate
	*        type Date
	* @return
	* @author HungHT
	*/
	public void setDeletedDate(Date deletedDate) {
		this.deletedDate = deletedDate;
	}

	/**
	* Get deletedDate
	* @return Date
	* @author HungHT
	*/
	public Date getDeletedDate() {
		return deletedDate;
	}

}