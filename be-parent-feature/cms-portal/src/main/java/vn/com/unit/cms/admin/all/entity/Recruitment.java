/*******************************************************************************
 * Class        ：Recruitment
 * Created date ：2017/04/13
 * Lasted date  ：2017/04/26
 * Author       ：phunghn
 * Change log   ：2017/04/26：01-00 phunghn create a new
 ******************************************************************************/
package vn.com.unit.cms.admin.all.entity;

import java.util.Date;
import java.util.List;

import org.springframework.data.annotation.Id;

import jp.sf.amateras.mirage.annotation.Column;
import jp.sf.amateras.mirage.annotation.PrimaryKey;
import jp.sf.amateras.mirage.annotation.PrimaryKey.GenerationType;
import jp.sf.amateras.mirage.annotation.Table;
import jp.sf.amateras.mirage.annotation.Transient;

/**
* Recruitment
* 
* @version 01-00
* @since 01-00
* @author phunghn
*/
@Table( name = "m_recruitment_test")
public class Recruitment {
	
	
    @Id
    @Column(name = "recruitment_id")
	@PrimaryKey(generationType = GenerationType.APPLICATION)
	private Long recruitmentId;
		
	@Column(name = "m_branch_id")
    private String mBranchId;
	
	@Column(name = "position_en")
    private String positionEN;
	
	@Column(name = "position")
    private String position;
	
	@Column(name = "address")
    private String address;
	
	@Column(name = "address_en")
    private String addressEN;
	
	@Column(name = "description_en")
    private String descriptionEN;
	
	@Column(name = "description")
    private String description;
	
	@Column(name = "deadline_time")
    private Date deadlineTime;
	
	@Column(name = "user_name")
    private String userName;
	
	@Column(name = "user_id")
    private Long userId;
	
	@Column(name = "created_date")
    private Date createdDate;	

	@Column(name = "updated_date")
    private Date updatedDate;	
	
	@Column(name = "deleted_date")
    private Date deletedDate;

	@Transient
	private List<Long> branchFields;

    /**
     * Get recruitmentId
     * @return Long
     * @author phunghn
     */
    public Long getRecruitmentId() {
        return recruitmentId;
    }

    /**
     * Set recruitmentId
     * @param   recruitmentId
     *          type Long
     * @return
     * @author  phunghn
     */
    public void setRecruitmentId(Long recruitmentId) {
        this.recruitmentId = recruitmentId;
    }

    /**
     * Get mBranchId
     * @return String
     * @author phunghn
     */
    public String getmBranchId() {
        return mBranchId;
    }

    /**
     * Set mBranchId
     * @param   mBranchId
     *          type String
     * @return
     * @author  phunghn
     */
    public void setmBranchId(String mBranchId) {
        this.mBranchId = mBranchId;
    }

    /**
     * Get positionEN
     * @return String
     * @author phunghn
     */
    public String getPositionEN() {
        return positionEN;
    }

    /**
     * Set positionEN
     * @param   positionEN
     *          type String
     * @return
     * @author  phunghn
     */
    public void setPositionEN(String positionEN) {
        this.positionEN = positionEN;
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
     * Get addressEN
     * @return String
     * @author phunghn
     */
    public String getAddressEN() {
        return addressEN;
    }

    /**
     * Set addressEN
     * @param   addressEN
     *          type String
     * @return
     * @author  phunghn
     */
    public void setAddressEN(String addressEN) {
        this.addressEN = addressEN;
    }

    /**
     * Get descriptionEN
     * @return String
     * @author phunghn
     */
    public String getDescriptionEN() {
        return descriptionEN;
    }

    /**
     * Set descriptionEN
     * @param   descriptionEN
     *          type String
     * @return
     * @author  phunghn
     */
    public void setDescriptionEN(String descriptionEN) {
        this.descriptionEN = descriptionEN;
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
     * Get deadlineTime
     * @return Date
     * @author phunghn
     */
    public Date getDeadlineTime() {
        return deadlineTime;
    }

    /**
     * Set deadlineTime
     * @param   deadlineTime
     *          type Date
     * @return
     * @author  phunghn
     */
    public void setDeadlineTime(Date deadlineTime) {
        this.deadlineTime = deadlineTime;
    }

    /**
     * Get userName
     * @return String
     * @author phunghn
     */
    public String getUserName() {
        return userName;
    }

    /**
     * Set userName
     * @param   userName
     *          type String
     * @return
     * @author  phunghn
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**
     * Get userId
     * @return Long
     * @author phunghn
     */
    public Long getUserId() {
        return userId;
    }

    /**
     * Set userId
     * @param   userId
     *          type Long
     * @return
     * @author  phunghn
     */
    public void setUserId(Long userId) {
        this.userId = userId;
    }

    /**
     * Get createdDate
     * @return Date
     * @author phunghn
     */
    public Date getCreatedDate() {
        return createdDate;
    }

    /**
     * Set createdDate
     * @param   createdDate
     *          type Date
     * @return
     * @author  phunghn
     */
    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    /**
     * Get updatedDate
     * @return Date
     * @author phunghn
     */
    public Date getUpdatedDate() {
        return updatedDate;
    }

    /**
     * Set updatedDate
     * @param   updatedDate
     *          type Date
     * @return
     * @author  phunghn
     */
    public void setUpdatedDate(Date updatedDate) {
        this.updatedDate = updatedDate;
    }

    /**
     * Get deletedDate
     * @return Date
     * @author phunghn
     */
    public Date getDeletedDate() {
        return deletedDate;
    }

    /**
     * Set deletedDate
     * @param   deletedDate
     *          type Date
     * @return
     * @author  phunghn
     */
    public void setDeletedDate(Date deletedDate) {
        this.deletedDate = deletedDate;
    }

    /**
     * Get branchFields
     * @return List<Long>
     * @author phunghn
     */
    public List<Long> getBranchFields() {
        return branchFields;
    }

    /**
     * Set branchFields
     * @param   branchFields
     *          type List<Long>
     * @return
     * @author  phunghn
     */
    public void setBranchFields(List<Long> branchFields) {
        this.branchFields = branchFields;
    }

	

}
