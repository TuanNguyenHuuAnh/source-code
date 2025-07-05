/*******************************************************************************
 * Class        ：ProductCategorySub
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
 * ProductCategorySub
 * 
 * @version 01-00
 * @since 01-00
 * @author hand
 */
@Table(name = "m_product_category_sub")
public class ProductCategorySub extends AbstractTracking {

    @Id
    @Column(name = "id")
    @PrimaryKey(generationType = GenerationType.SEQUENCE, generator = "SEQ_M_PRODUCT_CATEGORY_SUB")
    private Long id;

    @Column(name = "m_customer_type_id")
    private Long customerTypeId;

    @Column(name = "m_product_category_id")
    private Long categId;

    @Column(name = "code")
    private String code;

    @Column(name = "name")
    private String name;

    @Lob
    @Column(name = "description")
    private String description;

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
    
    @Column(name = "approve_by")
    private String approveBy;
    
    @Column(name = "approve_date")
    private Date approveDate;
    
    @Column(name = "publish_by")
    private String publishBy;
    
    @Column(name = "publish_date")
    private Date publishDate;
    
    @Column(name = "before_id")
    private Long beforeId;
    
    @Column(name = "product_category_comment")
    private String comment;

    @Column(name = "is_priority")
    private Boolean priority;
    
    /**
     * Get id
     * @return Long
     * @author hand
     */
    public Long getId() {
        return id;
    }

    /**
     * Set id
     * @param   id
     *          type Long
     * @return
     * @author  hand
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Get customerTypeId
     * @return Long
     * @author hand
     */
    public Long getCustomerTypeId() {
        return customerTypeId;
    }

    /**
     * Set customerTypeId
     * @param   customerTypeId
     *          type Long
     * @return
     * @author  hand
     */
    public void setCustomerTypeId(Long customerTypeId) {
        this.customerTypeId = customerTypeId;
    }

    /**
     * Get categId
     * @return Long
     * @author hand
     */
    public Long getCategId() {
        return categId;
    }

    /**
     * Set categId
     * @param   categId
     *          type Long
     * @return
     * @author  hand
     */
    public void setCategId(Long categId) {
        this.categId = categId;
    }

    /**
     * Get code
     * @return String
     * @author hand
     */
    public String getCode() {
        return code;
    }

    /**
     * Set code
     * @param   code
     *          type String
     * @return
     * @author  hand
     */
    public void setCode(String code) {
        this.code = code;
    }

    /**
     * Get name
     * @return String
     * @author hand
     */
    public String getName() {
        return name;
    }

    /**
     * Set name
     * @param   name
     *          type String
     * @return
     * @author  hand
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Get description
     * @return String
     * @author hand
     */
    public String getDescription() {
        return description;
    }

    /**
     * Set description
     * @param   description
     *          type String
     * @return
     * @author  hand
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Get note
     * @return String
     * @author hand
     */
    public String getNote() {
        return note;
    }

    /**
     * Set note
     * @param   note
     *          type String
     * @return
     * @author  hand
     */
    public void setNote(String note) {
        this.note = note;
    }

    /**
     * Get sort
     * @return Long
     * @author hand
     */
    public Long getSort() {
        return sort;
    }

    /**
     * Set sort
     * @param   sort
     *          type Long
     * @return
     * @author  hand
     */
    public void setSort(Long sort) {
        this.sort = sort;
    }

    /**
     * Get enabled
     * @return boolean
     * @author hand
     */
    public boolean isEnabled() {
        return enabled;
    }

    /**
     * Set enabled
     * @param   enabled
     *          type boolean
     * @return
     * @author  hand
     */
    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    
    public Integer getStatus() {
        return status;
    }

    
    public void setStatus(Integer status) {
        this.status = status;
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

    
    public String getImageName() {
        return imageName;
    }

    
    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    
    public String getPhysicalImg() {
        return physicalImg;
    }

    
    public void setPhysicalImg(String physicalImg) {
        this.physicalImg = physicalImg;
    }

    
    public String getImageHoverName() {
        return imageHoverName;
    }

    
    public void setImageHoverName(String imageHoverName) {
        this.imageHoverName = imageHoverName;
    }

    
    public String getPhysicalImgHover() {
        return physicalImgHover;
    }

    
    public void setPhysicalImgHover(String physicalImgHover) {
        this.physicalImgHover = physicalImgHover;
    }

    
    public String getIcon() {
        return icon;
    }

    
    public void setIcon(String icon) {
        this.icon = icon;
    }

    
    public String getIconImg() {
        return iconImg;
    }

    
    public void setIconImg(String iconImg) {
        this.iconImg = iconImg;
    }

    
    public String getPhysicalIcon() {
        return physicalIcon;
    }

    
    public void setPhysicalIcon(String physicalIcon) {
        this.physicalIcon = physicalIcon;
    }

    
    public Long getBannerDesktop() {
        return bannerDesktop;
    }

    
    public void setBannerDesktop(Long bannerDesktop) {
        this.bannerDesktop = bannerDesktop;
    }

    
    public Long getBannerMobile() {
        return bannerMobile;
    }

    
    public void setBannerMobile(Long bannerMobile) {
        this.bannerMobile = bannerMobile;
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

    
    public Long getBeforeId() {
        return beforeId;
    }

    
    public void setBeforeId(Long beforeId) {
        this.beforeId = beforeId;
    }

    
    public String getComment() {
        return comment;
    }

    
    public void setComment(String comment) {
        this.comment = comment;
    }

    
    public Boolean getPriority() {
        return priority;
    }

    
    public void setPriority(Boolean priority) {
        this.priority = priority;
    }

}
