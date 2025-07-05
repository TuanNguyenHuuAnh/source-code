/*******************************************************************************
 * Class        ：ProductCategory
 * Created date ：2017/05/03
 * Lasted date  ：2017/05/03
 * Author       ：hand
 * Change log   ：2017/05/03：01-00 hand create a new
 ******************************************************************************/
package vn.com.unit.cms.admin.all.entity;

import java.util.Date;

import javax.persistence.Lob;

import org.springframework.data.annotation.Id;

import jp.sf.amateras.mirage.annotation.Column;
import jp.sf.amateras.mirage.annotation.PrimaryKey;
import jp.sf.amateras.mirage.annotation.PrimaryKey.GenerationType;
import jp.sf.amateras.mirage.annotation.Table;
import vn.com.unit.cms.core.entity.AbstractTracking;

/**
 * ProductCategory
 * 
 * @version 01-00
 * @since 01-00
 * @author hand
 */
@Table(name = "m_product_category")
public class ProductCategory extends AbstractTracking {

    @Id
    @Column(name = "id")
    @PrimaryKey(generationType = GenerationType.SEQUENCE, generator = "SEQ_M_PRODUCT_CATEGORY")
    private Long id;

    @Column(name = "m_customer_type_id")
    private Long customerTypeId;

    @Column(name = "code")
    private String code;

    @Column(name = "name")
    private String name;

    @Lob
    @Column(name = "note")
    private String note;

    @Column(name = "sort")
    private Long sort;

    @Column(name = "enabled")
    private boolean enabled;

    @Column(name = "status")
    private Integer status;

    @Column(name = "process_id")
    private Long processId;

    @Column(name = "process_instance_id")
    private Long processInstanceId;

    @Column(name = "owner_id")
    private Long ownerId;

    @Column(name = "owner_branch_id")
    private Long ownerBranchId;

    @Column(name = "owner_section_id")
    private Long ownerSectionId;

    @Column(name = "assigner_id")
    private Long assignerId;

    @Column(name = "assigner_branch_id")
    private Long assignerBranchId;

    @Column(name = "assigner_section_id")
    private Long assignerSectionId;

    @Column(name = "image_name")
    private String imageName;

    @Column(name = "physical_img")
    private String physicalImg;

    @Column(name = "image_hover_name")
    private String imageHoverName;

    @Column(name = "physical_img_hover")
    private String physicalImgHover;

    @Column(name = "icon")
    private String icon;
    
    @Column(name = "icon_img")
    private String iconImg;

    @Column(name = "physical_icon")
    private String physicalIcon;
    
    @Column(name = "banner_desktop")
    private Long bannerDesktop;
    
    @Column(name = "banner_mobile")
    private Long bannerMobile;
    
    @Column(name = "is_promotion")
    private boolean promotion;
    
    @Column(name = "link_alias")
    private String linkAlias;

    @Column(name = "product_type_comment")
    private String comment;
    
    @Column(name = "before_id")
    private Long beforeId;
    
    @Column(name = "approve_by")
    private String approveBy;
    
    @Column(name = "approve_date")
    private Date approveDate;
    
    @Column(name = "publish_by")
    private String publishBy;
    
    @Column(name = "publish_date")
    private Date publishDate;

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
     * @param id
     *            type Long
     * @return
     * @author hand
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Get customerTypeId
     * 
     * @return Long
     * @author hand
     */
    public Long getCustomerTypeId() {
        return customerTypeId;
    }

    /**
     * Set customerTypeId
     * 
     * @param customerTypeId
     *            type Long
     * @return
     * @author hand
     */
    public void setCustomerTypeId(Long customerTypeId) {
        this.customerTypeId = customerTypeId;
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
     * @param code
     *            type String
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
     * @param name
     *            type String
     * @return
     * @author hand
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Get note
     * 
     * @return String
     * @author hand
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
     * @author hand
     */
    public void setNote(String note) {
        this.note = note;
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
     * @param sort
     *            type Long
     * @return
     * @author hand
     */
    public void setSort(Long sort) {
        this.sort = sort;
    }

    /**
     * Get enabled
     * 
     * @return boolean
     * @author hand
     */
    public boolean isEnabled() {
        return enabled;
    }

    /**
     * Set enabled
     * 
     * @param enabled
     *            type boolean
     * @return
     * @author hand
     */
    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    /**
     * Get status
     * 
     * @return Integer
     * @author hand
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * Set status
     * 
     * @param status
     *            type Integer
     * @return
     * @author hand
     */
    public void setStatus(Integer status) {
        this.status = status;
    }

    /**
     * Get processId
     * 
     * @return Long
     * @author hand
     */
    public Long getProcessId() {
        return processId;
    }

    /**
     * Set processId
     * 
     * @param processId
     *            type Long
     * @return
     * @author hand
     */
    public void setProcessId(Long processId) {
        this.processId = processId;
    }

    /**
     * Get processInstanceId
     * 
     * @return Long
     * @author hand
     */
    public Long getProcessInstanceId() {
        return processInstanceId;
    }

    /**
     * Set processInstanceId
     * 
     * @param processInstanceId
     *            type Long
     * @return
     * @author hand
     */
    public void setProcessInstanceId(Long processInstanceId) {
        this.processInstanceId = processInstanceId;
    }

    /**
     * Get ownerId
     * 
     * @return Long
     * @author hand
     */
    public Long getOwnerId() {
        return ownerId;
    }

    /**
     * Set ownerId
     * 
     * @param ownerId
     *            type Long
     * @return
     * @author hand
     */
    public void setOwnerId(Long ownerId) {
        this.ownerId = ownerId;
    }

    /**
     * Get ownerBranchId
     * 
     * @return Long
     * @author hand
     */
    public Long getOwnerBranchId() {
        return ownerBranchId;
    }

    /**
     * Set ownerBranchId
     * 
     * @param ownerBranchId
     *            type Long
     * @return
     * @author hand
     */
    public void setOwnerBranchId(Long ownerBranchId) {
        this.ownerBranchId = ownerBranchId;
    }

    /**
     * Get ownerSectionId
     * 
     * @return Long
     * @author hand
     */
    public Long getOwnerSectionId() {
        return ownerSectionId;
    }

    /**
     * Set ownerSectionId
     * 
     * @param ownerSectionId
     *            type Long
     * @return
     * @author hand
     */
    public void setOwnerSectionId(Long ownerSectionId) {
        this.ownerSectionId = ownerSectionId;
    }

    /**
     * Get assignerId
     * 
     * @return Long
     * @author hand
     */
    public Long getAssignerId() {
        return assignerId;
    }

    /**
     * Set assignerId
     * 
     * @param assignerId
     *            type Long
     * @return
     * @author hand
     */
    public void setAssignerId(Long assignerId) {
        this.assignerId = assignerId;
    }

    /**
     * Get assignerBranchId
     * 
     * @return Long
     * @author hand
     */
    public Long getAssignerBranchId() {
        return assignerBranchId;
    }

    /**
     * Set assignerBranchId
     * 
     * @param assignerBranchId
     *            type Long
     * @return
     * @author hand
     */
    public void setAssignerBranchId(Long assignerBranchId) {
        this.assignerBranchId = assignerBranchId;
    }

    /**
     * Get assignerSectionId
     * 
     * @return Long
     * @author hand
     */
    public Long getAssignerSectionId() {
        return assignerSectionId;
    }

    /**
     * Set assignerSectionId
     * 
     * @param assignerSectionId
     *            type Long
     * @return
     * @author hand
     */
    public void setAssignerSectionId(Long assignerSectionId) {
        this.assignerSectionId = assignerSectionId;
    }

    /**
     * Get imageName
     * 
     * @return String
     * @author hand
     */
    public String getImageName() {
        return imageName;
    }

    /**
     * Set imageName
     * 
     * @param imageName
     *            type String
     * @return
     * @author hand
     */
    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    /**
     * Get physicalImg
     * 
     * @return String
     * @author hand
     */
    public String getPhysicalImg() {
        return physicalImg;
    }

    /**
     * Set physicalImg
     * 
     * @param physicalImg
     *            type String
     * @return
     * @author hand
     */
    public void setPhysicalImg(String physicalImg) {
        this.physicalImg = physicalImg;
    }

    /**
     * Get imageHoverName
     * 
     * @return String
     * @author hand
     */
    public String getImageHoverName() {
        return imageHoverName;
    }

    /**
     * Set imageHoverName
     * 
     * @param imageHoverName
     *            type String
     * @return
     * @author hand
     */
    public void setImageHoverName(String imageHoverName) {
        this.imageHoverName = imageHoverName;
    }

    /**
     * Get physicalImgHover
     * 
     * @return String
     * @author hand
     */
    public String getPhysicalImgHover() {
        return physicalImgHover;
    }

    /**
     * Set physicalImgHover
     * 
     * @param physicalImgHover
     *            type String
     * @return
     * @author hand
     */
    public void setPhysicalImgHover(String physicalImgHover) {
        this.physicalImgHover = physicalImgHover;
    }

    /**
     * Get iconImg
     * @return String
     * @author hand
     */
    public String getIconImg() {
        return iconImg;
    }

    /**
     * Get physicalIcon
     * @return String
     * @author hand
     */
    public String getPhysicalIcon() {
        return physicalIcon;
    }

    /**
     * Set iconImg
     * @param   iconImg
     *          type String
     * @return
     * @author  hand
     */
    public void setIconImg(String iconImg) {
        this.iconImg = iconImg;
    }

    /**
     * Set physicalIcon
     * @param   physicalIcon
     *          type String
     * @return
     * @author  hand
     */
    public void setPhysicalIcon(String physicalIcon) {
        this.physicalIcon = physicalIcon;
    }

    /**
     * Get bannerDesktop
     * @return Long
     * @author hand
     */
    public Long getBannerDesktop() {
        return bannerDesktop;
    }

    /**
     * Set bannerDesktop
     * @param   bannerDesktop
     *          type Long
     * @return
     * @author  hand
     */
    public void setBannerDesktop(Long bannerDesktop) {
        this.bannerDesktop = bannerDesktop;
    }

    /**
     * Get bannerMobile
     * @return Long
     * @author hand
     */
    public Long getBannerMobile() {
        return bannerMobile;
    }

    /**
     * Set bannerMobile
     * @param   bannerMobile
     *          type Long
     * @return
     * @author  hand
     */
    public void setBannerMobile(Long bannerMobile) {
        this.bannerMobile = bannerMobile;
    }

    /**
     * Get promotion
     * @return boolean
     * @author hand
     */
    public boolean isPromotion() {
        return promotion;
    }

    /**
     * Set promotion
     * @param   promotion
     *          type boolean
     * @return
     * @author  hand
     */
    public void setPromotion(boolean promotion) {
        this.promotion = promotion;
    }

    /**
     * @return the linkAlias
     */
    public String getLinkAlias() {
        return linkAlias;
    }

    /**
     * @param linkAlias the linkAlias to set
     */
    public void setLinkAlias(String linkAlias) {
        this.linkAlias = linkAlias;
    }

    public String getComment() {
        return comment;
    }

    
    public void setComment(String comment) {
        this.comment = comment;
    }

    
    public String getIcon() {
        return icon;
    }

    
    public void setIcon(String icon) {
        this.icon = icon;
    }

    
    public Long getBeforeId() {
        return beforeId;
    }

    
    public void setBeforeId(Long beforeId) {
        this.beforeId = beforeId;
    }

    
    public String getApproveBy() {
        return approveBy;
    }

    
    public void setApproveBy(String approveBy) {
        this.approveBy = approveBy;
    }

    
    public Date getApproveDate() {
        return approveDate;
    }

    
    public void setApproveDate(Date approveDate) {
        this.approveDate = approveDate;
    }

    
    public String getPublishBy() {
        return publishBy;
    }

    
    public void setPublishBy(String publishBy) {
        this.publishBy = publishBy;
    }

    
    public Date getPublishDate() {
        return publishDate;
    }

    
    public void setPublishDate(Date publishDate) {
        this.publishDate = publishDate;
    }
    
    
    
}
