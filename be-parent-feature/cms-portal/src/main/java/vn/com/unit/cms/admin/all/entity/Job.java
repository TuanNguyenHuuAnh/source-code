/*******************************************************************************
 * Class        ：Job
 * Created date ：2017/03/06
 * Lasted date  ：2017/03/06
 * Author       ：TranLTH
 * Change log   ：2017/03/06：01-00 TranLTH create a new
 ******************************************************************************/
package vn.com.unit.cms.admin.all.entity;

import java.util.Date;

import org.springframework.data.annotation.Id;

import jp.sf.amateras.mirage.annotation.Column;
import jp.sf.amateras.mirage.annotation.PrimaryKey;
import jp.sf.amateras.mirage.annotation.PrimaryKey.GenerationType;
import jp.sf.amateras.mirage.annotation.Table;
import vn.com.unit.cms.core.entity.AbstractTracking;
import vn.com.unit.cms.core.utils.CmsUtils;
//import vn.com.unit.util.Util;

/**
 * Job
 * 
 * @version 01-00
 * @since 01-00
 * @author TranLTH
 */
@Table(name = "m_job")
public class Job extends AbstractTracking {
    @Id
    @Column(name = "id")
    @PrimaryKey(generationType = GenerationType.SEQUENCE, generator = "SEQ_M_JOB")
    private Long id;
    
    /** Column: link_alias type VARCHAR(255) */
    @Column(name = "link_alias")
    private String linkAlias;
    
    /** Column: career type BIGINT(20) */
    @Column(name = "career")
    private String mCareerId;
    
    /** Column: code type VARCHAR(30) */
    @Column(name = "code")
    private String code;
    
    /** Column: name type VARCHAR(255) */
    @Column(name = "name")
    private String name;
    
    /** Column: note type TEXT(0) */
    @Column(name = "note")
    private String note;
    
    /** Column: position type VARCHAR(255) */
    @Column(name = "position")
    private String position;
    
    /** Column: location type VARCHAR(255) */
    @Column(name = "location")
    private String location;
    
    /** Column: urgent type TINYINT(1) */
    @Column(name = "urgent")
    private Boolean urgent;
    
    /** Column: enabled type TINYINT(1) */
    @Column(name = "enabled")
    private Boolean enabled;
    
    /** Column: sort type BIGINT(20) */
    @Column(name = "sort")
    private String sort;
    
    /** Column: expiry date type DATETIME(20) */
    @Column(name = "expiry_date")
    private Date expiryDate;
    
    /** Column: owner_id type BIGINT(20) */
    @Column(name = "owner_id")
    private Long ownerId;
    
    /** Column: owner_branch_id type BIGINT(20) */
    @Column(name = "owner_branch_id")
    private Long ownerBranchId;
    
    /** Column: owner_section_id type BIGINT(20) */
    @Column(name = "owner_section_id")
    private Long ownerSectionId;
    
    /** Column: assigner_id type BIGINT(20) */
    @Column(name = "assigner_id")
    private Long assignerId;
    
    /** Column: assigner_branch_id type BIGINT(20) */
    @Column(name = "assigner_branch_id")
    private Long assignerBranchId;
    
    /** Column: assigner_section_id type BIGINT(20) */
    @Column(name = "assigner_section_id")
    private Long assignerSectionId;
    
    /** Column: status_code type VARCHAR(30) */
    @Column(name = "status_code")
    private String statusCode;
    
    /** Column: process_id type BIGINT(20) */
    @Column(name = "process_id")
    private Long processId;  
    
    @Column(name = "process_instance_id")
    private Long processInstanceId;
    
    /** Column: experience type VARCHAR(255) */
    @Column(name = "experience")
    private String experience;
    
    /** Column: division type VARCHAR(255) */
    @Column(name = "division")
    private String division;
    
    /** Column: effective date type DATETIME(20) */
    @Column(name = "effective_date")
    private Date effectiveDate;
    
    /** Column: salary type VARCHAR(255) */
    @Column(name = "salary")
    private String salary;    
    
    @Column(name = "recruitment_number")
    private int recruitmentNumber;
    
    public Job() {
    }

    /**
     * Get id
     * @return Long
     * @author TranLTH
     */
    public Long getId() {
        return id;
    }

    /**
     * Set id
     * @param   id
     *          type Long
     * @return
     * @author  TranLTH
     */
    public void setId(Long id) {
        this.id = id;
    }
    /**
     * Get linkAlias
     * @return String
     * @author TranLTH
     */
    public String getLinkAlias() {
        return linkAlias;
    }

    /**
     * Set linkAlias
     * @param   linkAlias
     *          type String
     * @return
     * @author  TranLTH
     */
    public void setLinkAlias(String linkAlias) {
        this.linkAlias = linkAlias;
    }

    /**
     * Get mCareerId
     * @return String
     * @author TranLTH
     */
    public String getmCareerId() {
        return mCareerId;
    }

    /**
     * Set mCareerId
     * @param   mCareerId
     *          type String
     * @return
     * @author  TranLTH
     */
    public void setmCareerId(String mCareerId) {
        this.mCareerId = mCareerId;
    }

    /**
     * Get code
     * @return String
     * @author TranLTH
     */
    public String getCode() {
        return code;
    }

    /**
     * Set code
     * @param   code
     *          type String
     * @return
     * @author  TranLTH
     */
    public void setCode(String code) {
        this.code = CmsUtils.toUppercase(code);
    }

    /**
     * Get name
     * @return String
     * @author TranLTH
     */
    public String getName() {
        return name;
    }

    /**
     * Set name
     * @param   name
     *          type String
     * @return
     * @author  TranLTH
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Get note
     * @return String
     * @author TranLTH
     */
    public String getNote() {
        return note;
    }

    /**
     * Set note
     * @param   note
     *          type String
     * @return
     * @author  TranLTH
     */
    public void setNote(String note) {
        this.note = note;
    }
    /**
     * Get position
     * @return String
     * @author TranLTH
     */
    public String getPosition() {
        return position;
    }

    /**
     * Set position
     * @param   position
     *          type String
     * @return
     * @author  TranLTH
     */
    public void setPosition(String position) {
        this.position = position;
    }

    /**
     * Get location
     * @return String
     * @author TranLTH
     */
    public String getLocation() {
        return location;
    }

    /**
     * Set location
     * @param   location
     *          type String
     * @return
     * @author  TranLTH
     */
    public void setLocation(String location) {
        this.location = location;
    }

    /**
     * Get urgent
     * @return Boolean
     * @author TranLTH
     */
    public Boolean getUrgent() {
        return urgent;
    }

    /**
     * Set urgent
     * @param   urgent
     *          type Boolean
     * @return
     * @author  TranLTH
     */
    public void setUrgent(Boolean urgent) {
        this.urgent = urgent;
    }

    /**
     * Get enabled
     * @return Boolean
     * @author TranLTH
     */
    public Boolean getEnabled() {
        return enabled;
    }

    /**
     * Set enabled
     * @param   enabled
     *          type Boolean
     * @return
     * @author  TranLTH
     */
    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    /**
     * Get sort
     * @return String
     * @author TranLTH
     */
    public String getSort() {
        return sort;
    }

    /**
     * Set sort
     * @param   sort
     *          type String
     * @return
     * @author  TranLTH
     */
    public void setSort(String sort) {
        this.sort = sort;
    }

    /**
     * Get expiryDate
     * @return Date
     * @author TranLTH
     */
    public Date getExpiryDate() {
        return expiryDate;
    }

    /**
     * Set expiryDate
     * @param   expiryDate
     *          type Date
     * @return
     * @author  TranLTH
     */
    public void setExpiryDate(Date expiryDate) {
        this.expiryDate = expiryDate;
    }

    /**
     * Get ownerId
     * @return Long
     * @author TranLTH
     */
    public Long getOwnerId() {
        return ownerId;
    }

    /**
     * Set ownerId
     * @param   ownerId
     *          type Long
     * @return
     * @author  TranLTH
     */
    public void setOwnerId(Long ownerId) {
        this.ownerId = ownerId;
    }

    /**
     * Get ownerBranchId
     * @return Long
     * @author TranLTH
     */
    public Long getOwnerBranchId() {
        return ownerBranchId;
    }

    /**
     * Set ownerBranchId
     * @param   ownerBranchId
     *          type Long
     * @return
     * @author  TranLTH
     */
    public void setOwnerBranchId(Long ownerBranchId) {
        this.ownerBranchId = ownerBranchId;
    }

    /**
     * Get ownerSectionId
     * @return Long
     * @author TranLTH
     */
    public Long getOwnerSectionId() {
        return ownerSectionId;
    }

    /**
     * Set ownerSectionId
     * @param   ownerSectionId
     *          type Long
     * @return
     * @author  TranLTH
     */
    public void setOwnerSectionId(Long ownerSectionId) {
        this.ownerSectionId = ownerSectionId;
    }

    /**
     * Get assignerId
     * @return Long
     * @author TranLTH
     */
    public Long getAssignerId() {
        return assignerId;
    }

    /**
     * Set assignerId
     * @param   assignerId
     *          type Long
     * @return
     * @author  TranLTH
     */
    public void setAssignerId(Long assignerId) {
        this.assignerId = assignerId;
    }

    /**
     * Get assignerBranchId
     * @return Long
     * @author TranLTH
     */
    public Long getAssignerBranchId() {
        return assignerBranchId;
    }

    /**
     * Set assignerBranchId
     * @param   assignerBranchId
     *          type Long
     * @return
     * @author  TranLTH
     */
    public void setAssignerBranchId(Long assignerBranchId) {
        this.assignerBranchId = assignerBranchId;
    }

    /**
     * Get assignerSectionId
     * @return Long
     * @author TranLTH
     */
    public Long getAssignerSectionId() {
        return assignerSectionId;
    }

    /**
     * Set assignerSectionId
     * @param   assignerSectionId
     *          type Long
     * @return
     * @author  TranLTH
     */
    public void setAssignerSectionId(Long assignerSectionId) {
        this.assignerSectionId = assignerSectionId;
    }

    /**
     * Get statusCode
     * @return String
     * @author TranLTH
     */
    public String getStatusCode() {
        return statusCode;
    }

    /**
     * Set statusCode
     * @param   statusCode
     *          type String
     * @return
     * @author  TranLTH
     */
    public void setStatusCode(String statusCode) {
        this.statusCode = statusCode;
    }

    /**
     * Get processId
     * @return Long
     * @author TranLTH
     */
    public Long getProcessId() {
        return processId;
    }

    /**
     * Set processId
     * @param   processId
     *          type Long
     * @return
     * @author  TranLTH
     */
    public void setProcessId(Long processId) {
        this.processId = processId;
    }

    /**
     * Get processInstanceId
     * @return Long
     * @author TranLTH
     */
    public Long getProcessInstanceId() {
        return processInstanceId;
    }

    /**
     * Set processInstanceId
     * @param   processInstanceId
     *          type Long
     * @return
     * @author  TranLTH
     */
    public void setProcessInstanceId(Long processInstanceId) {
        this.processInstanceId = processInstanceId;
    }

    /**
     * Get experience
     * @return String
     * @author TranLTH
     */
    public String getExperience() {
        return experience;
    }

    /**
     * Set experience
     * @param   experience
     *          type String
     * @return
     * @author  TranLTH
     */
    public void setExperience(String experience) {
        this.experience = experience;
    }

    /**
     * Get division
     * @return String
     * @author TranLTH
     */
    public String getDivision() {
        return division;
    }

    /**
     * Set division
     * @param   division
     *          type String
     * @return
     * @author  TranLTH
     */
    public void setDivision(String division) {
        this.division = division;
    }

    /**
     * Get effectiveDate
     * @return Date
     * @author TranLTH
     */
    public Date getEffectiveDate() {
        return effectiveDate;
    }

    /**
     * Set effectiveDate
     * @param   effectiveDate
     *          type Date
     * @return
     * @author  TranLTH
     */
    public void setEffectiveDate(Date effectiveDate) {
        this.effectiveDate = effectiveDate;
    }

    /**
     * Get salary
     * @return String
     * @author TranLTH
     */
    public String getSalary() {
        return salary;
    }

    /**
     * Set salary
     * @param   salary
     *          type String
     * @return
     * @author  TranLTH
     */
    public void setSalary(String salary) {
        this.salary = salary;
    }

	/**
	 * @return the recruitmentNumber
	 */
	public int getRecruitmentNumber() {
		return recruitmentNumber;
	}

	/**
	 * @param recruitmentNumber the recruitmentNumber to set
	 */
	public void setRecruitmentNumber(int recruitmentNumber) {
		this.recruitmentNumber = recruitmentNumber;
	}
}