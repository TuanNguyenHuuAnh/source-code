/*******************************************************************************
 * Class        :ComponentAuthorityDto
 * Created date :2019/04/23
 * Lasted date  :2019/04/23
 * Author       :HungHT
 * Change log   :2019/04/23:01-00 HungHT create a new
 ******************************************************************************/
package vn.com.unit.ep2p.dto;

import java.util.Date;

/**
 * ComponentAuthorityDto
 * @version 01-00
 * @since 01-00
 * @author HungHT
 */
public class ComponentAuthorityDto {

	private Long id;
	private String accessFlg;
	private Long itemId;
	private Long compId;
	private String compName;
	private String createdBy;
	private Date createdDate;
	private String updatedBy;
	private Date updatedDate;
	private String deletedBy;
	private Date deletedDate;
	private boolean canAccessFlg;
    private boolean canDispFlg;
    private boolean canEditFlg;

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
	* Set accessFlg
	* @param accessFlg
	*        type String
	* @return
	* @author HungHT
	*/
	public void setAccessFlg(String accessFlg) {
		this.accessFlg = accessFlg;
	}

	/**
	* Get accessFlg
	* @return String
	* @author HungHT
	*/
	public String getAccessFlg() {
		return accessFlg;
	}

	/**
	* Set itemId
	* @param itemId
	*        type Long
	* @return
	* @author HungHT
	*/
	public void setItemId(Long itemId) {
		this.itemId = itemId;
	}

	/**
	* Get itemId
	* @return Long
	* @author HungHT
	*/
	public Long getItemId() {
		return itemId;
	}

	/**
	* Set compId
	* @param compId
	*        type Long
	* @return
	* @author HungHT
	*/
	public void setCompId(Long compId) {
		this.compId = compId;
	}

	/**
	* Get compId
	* @return Long
	* @author HungHT
	*/
	public Long getCompId() {
		return compId;
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
     * Set compName
     * @param   compName
     *          type String
     * @return
     * @author  HungHT
     */
    public void setCompName(String compName) {
        this.compName = compName;
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
    
    /**
     * Get canAccessFlg
     * @return boolean
     * @author HungHT
     */
    public boolean isCanAccessFlg() {
        return canAccessFlg;
    }
    
    /**
     * Set canAccessFlg
     * @param   canAccessFlg
     *          type boolean
     * @return
     * @author  HungHT
     */
    public void setCanAccessFlg(boolean canAccessFlg) {
        this.canAccessFlg = canAccessFlg;
    }

    /**
     * Get canDispFlg
     * @return boolean
     * @author HungHT
     */
    public boolean isCanDispFlg() {
        return canDispFlg;
    }

    /**
     * Set canDispFlg
     * @param   canDispFlg
     *          type boolean
     * @return
     * @author  HungHT
     */
    public void setCanDispFlg(boolean canDispFlg) {
        this.canDispFlg = canDispFlg;
    }
    
    /**
     * Get canEditFlg
     * @return boolean
     * @author HungHT
     */
    public boolean isCanEditFlg() {
        return canEditFlg;
    }
    
    /**
     * Set canEditFlg
     * @param   canEditFlg
     *          type boolean
     * @return
     * @author  HungHT
     */
    public void setCanEditFlg(boolean canEditFlg) {
        this.canEditFlg = canEditFlg;
    }
}