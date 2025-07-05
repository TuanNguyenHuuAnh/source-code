/*******************************************************************************
 * Class        ：JcaMBanner
 * Created date ：2017/02/14
 * Lasted date  ：2017/02/14
 * Author       ：hand
 * Change log   ：2017/02/14：01-00 hand create a new
 ******************************************************************************/
package vn.com.unit.cms.admin.all.entity;


import java.util.Date;

import org.springframework.data.annotation.Id;

import jp.sf.amateras.mirage.annotation.Column;
import jp.sf.amateras.mirage.annotation.PrimaryKey;
import jp.sf.amateras.mirage.annotation.PrimaryKey.GenerationType;
import jp.sf.amateras.mirage.annotation.Table;
import vn.com.unit.cms.core.entity.AbstractTracking;

/**
 * Homepage entity
 * 
 * @version 01-00
 * @since 01-00
 * @author sonnt
 */
@Table(name = "m_homepage_setting")
public class HomepageSetting extends AbstractTracking {

    @Id
    @Column(name = "id")
    @PrimaryKey(generationType = GenerationType.SEQUENCE, generator = "SEQ_M_HOMEPAGE_SETTING")
    private Long id;

    @Column(name = "speed_roll")
    private int speedRoll;

    @Column(name = "m_banner_top_id")
    private String bannerTopId;

    @Column(name = "m_banner_fix_id")
    private String bannerFixId;
    
    @Column(name = "m_banner_top_mobile_id")
    private String bannerTopMobileId;
    
    @Column(name = "m_banner_fix_mobile_id")
    private String bannerFixMobileId;
    
    @Column(name = "effective_date_from")
    private Date effectiveDateFrom;
    
    @Column(name = "effective_date_to")
    private Date effectiveDateTo;
    
    @Column(name = "description")
    private String description;
    
    @Column(name = "note")
    private String note;
    
    @Column(name = "status")
    private Integer status;
    
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
    
    @Column(name = "banner_page")
    private String bannerPage;

    /**
     * get id
     * @return
     */
	public Long getId() {
		return id;
	}

	/**
	 * set id
	 * @param id
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * get rolling speed
	 * @return
	 */
	public int getSpeedRoll() {
		return speedRoll;
	}

	/**
	 * set rolling speed
	 * @param speedRoll
	 */
	public void setSpeedRoll(int speedRoll) {
		this.speedRoll = speedRoll;
	}

	/**
	 * get banner top id
	 * @return
	 */
	public String getBannerTopId() {
		return bannerTopId;
	}

	/**
	 * set banner top id
	 * @param bannerTopId
	 */
	public void setBannerTopId(String bannerTopId) {
		this.bannerTopId = bannerTopId;
	}

	/**
	 * get banner fix id
	 * @return
	 */
	public String getBannerFixId() {
		return bannerFixId;
	}

	/**
	 * set banner fix id
	 * @param bannerFixId
	 */
	public void setBannerFixId(String bannerFixId) {
		this.bannerFixId = bannerFixId;
	}

	/**
	 * get banner top mobile id
	 * @return
	 */
	public String getBannerTopMobileId() {
		return bannerTopMobileId;
	}

	/**
	 * set banner top mobile id
	 * @param bannerTopMobileId
	 */
	public void setBannerTopMobileId(String bannerTopMobileId) {
		this.bannerTopMobileId = bannerTopMobileId;
	}

	/**
	 * get banner fix mobile id
	 * @return
	 */
	public String getBannerFixMobileId() {
		return bannerFixMobileId;
	}

	/**
	 * set banner fix mobile id
	 * @param bannerFixMobileId
	 */
	public void setBannerFixMobileId(String bannerFixMobileId) {
		this.bannerFixMobileId = bannerFixMobileId;
	}

	/**
	 * get effective date from
	 * @return
	 */
	public Date getEffectiveDateFrom() {
		return effectiveDateFrom;
	}

	/**
	 * set effective date from
	 * @param effectiveDateFrom
	 */
	public void setEffectiveDateFrom(Date effectiveDateFrom) {
		this.effectiveDateFrom = effectiveDateFrom;
	}

	/**
	 * get effective date to
	 * @return
	 */
	public Date getEffectiveDateTo() {
		return effectiveDateTo;
	}

	/**
	 * set effective date to
	 * @param effectiveDateTo
	 */
	public void setEffectiveDateTo(Date effectiveDateTo) {
		this.effectiveDateTo = effectiveDateTo;
	}

	/**
	 * get description
	 * @return
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * set description
	 * @param description
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * get note
	 * @return
	 */
	public String getNote() {
		return note;
	}

	/**
	 * set note
	 * @param note
	 */
	public void setNote(String note) {
		this.note = note;
	}

	/**
	 * get status
	 * @return
	 */
	public Integer getStatus() {
		return status;
	}

	/**
	 * set status
	 * @param status
	 */
	public void setStatus(Integer status) {
		this.status = status;
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

	public String getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(String statusCode) {
		this.statusCode = statusCode;
	}

	public Long getProcessId() {
		return processId;
	}

	public void setProcessId(Long processId) {
		this.processId = processId;
	}

	public Long getProcessInstanceId() {
		return processInstanceId;
	}

	public void setProcessInstanceId(Long processInstanceId) {
		this.processInstanceId = processInstanceId;
	}

    /**
     * Get bannerPage
     * @return String
     * @author TranLTH
     */
    public String getBannerPage() {
        return bannerPage;
    }

    /**
     * Set bannerPage
     * @param   bannerPage
     *          type String
     * @return
     * @author  TranLTH
     */
    public void setBannerPage(String bannerPage) {
        this.bannerPage = bannerPage;
    }
}