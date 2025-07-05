/*******************************************************************************
 * Class        ：RecruitmentDto
 * Created date ：2017/04/13
 * Lasted date  ：2017/04/26
 * Author       ：phunghn
 * Change log   ：2017/04/26：01-00 phunghn create a new
 ******************************************************************************/
package vn.com.unit.cms.admin.all.dto;

import java.util.Date;

public class RecruitmentDto {
    /** id */
	private long id;
	/** branchId */
	private String branchId;
	/** branchName */
	private String branchName;
	/** positionEn */
	private String positionEn;
	/** position */
	private String position;
	/** address */
	private String address;
	/** addressEn */
	private String addressEn;
	/** description */
	private String description;
	/** descriptionEn */
	private String descriptionEn;
	/** deadlinetime */
	private Date deadlinetime;
	/** id */
    /**
     * Get id
     * @return long
     * @author phunghn
     */
    public long getId() {
        return id;
    }
    /**
     * Set id
     * @param   id
     *          type long
     * @return
     * @author  phunghn
     */
    public void setId(long id) {
        this.id = id;
    }
    /**
     * Get branchId
     * @return String
     * @author phunghn
     */
    public String getBranchId() {
        return branchId;
    }
    /**
     * Set branchId
     * @param   branchId
     *          type String
     * @return
     * @author  phunghn
     */
    public void setBranchId(String branchId) {
        this.branchId = branchId;
    }
    /**
     * Get branchName
     * @return String
     * @author phunghn
     */
    public String getBranchName() {
        return branchName;
    }
    /**
     * Set branchName
     * @param   branchName
     *          type String
     * @return
     * @author  phunghn
     */
    public void setBranchName(String branchName) {
        this.branchName = branchName;
    }
    /**
     * Get positionEn
     * @return String
     * @author phunghn
     */
    public String getPositionEn() {
        return positionEn;
    }
    /**
     * Set positionEn
     * @param   positionEn
     *          type String
     * @return
     * @author  phunghn
     */
    public void setPositionEn(String positionEn) {
        this.positionEn = positionEn;
    }
    /**
     * Get position
     * @return String
     * @author phunghn
     */
    public String getPosition() {
        return position;
    }
    /**
     * Set position
     * @param   position
     *          type String
     * @return
     * @author  phunghn
     */
    public void setPosition(String position) {
        this.position = position;
    }
    /**
     * Get address
     * @return String
     * @author phunghn
     */
    public String getAddress() {
        return address;
    }
    /**
     * Set address
     * @param   address
     *          type String
     * @return
     * @author  phunghn
     */
    public void setAddress(String address) {
        this.address = address;
    }
    /**
     * Get addressEn
     * @return String
     * @author phunghn
     */
    public String getAddressEn() {
        return addressEn;
    }
    /**
     * Set addressEn
     * @param   addressEn
     *          type String
     * @return
     * @author  phunghn
     */
    public void setAddressEn(String addressEn) {
        this.addressEn = addressEn;
    }
    /**
     * Get description
     * @return String
     * @author phunghn
     */
    public String getDescription() {
        return description;
    }
    /**
     * Set description
     * @param   description
     *          type String
     * @return
     * @author  phunghn
     */
    public void setDescription(String description) {
        this.description = description;
    }
    /**
     * Get descriptionEn
     * @return String
     * @author phunghn
     */
    public String getDescriptionEn() {
        return descriptionEn;
    }
    /**
     * Set descriptionEn
     * @param   descriptionEn
     *          type String
     * @return
     * @author  phunghn
     */
    public void setDescriptionEn(String descriptionEn) {
        this.descriptionEn = descriptionEn;
    }
    /**
     * Get deadlinetime
     * @return Date
     * @author phunghn
     */
    public Date getDeadlinetime() {
        return deadlinetime;
    }
    /**
     * Set deadlinetime
     * @param   deadlinetime
     *          type Date
     * @return
     * @author  phunghn
     */
    public void setDeadlinetime(Date deadlinetime) {
        this.deadlinetime = deadlinetime;
    }

	
}
