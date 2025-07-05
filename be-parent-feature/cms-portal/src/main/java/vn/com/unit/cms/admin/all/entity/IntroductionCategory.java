/*******************************************************************************
 * Class        ：IntroductionCategory
 * Created date ：2017/03/06
 * Lasted date  ：2017/03/06
 * Author       ：thuydtn
 * Change log   ：2017/03/06：01-00 thuydtn create a new
 ******************************************************************************/
package vn.com.unit.cms.admin.all.entity;

import java.util.Date;

import org.springframework.data.annotation.Id;

import jp.sf.amateras.mirage.annotation.Column;
import jp.sf.amateras.mirage.annotation.PrimaryKey;
import jp.sf.amateras.mirage.annotation.Table;
import jp.sf.amateras.mirage.annotation.PrimaryKey.GenerationType;
import vn.com.unit.cms.admin.all.dto.IntroductionCategoryDto;

/**
 * IntroductionCategory
 * 
 * @version 01-00
 * @since 01-00
 * @author thuydtn
 */
@Table(name = "m_introduction_category")
public class IntroductionCategory {

    @Id
    @Column(name = "id")
    @PrimaryKey(generationType = GenerationType.SEQUENCE, generator = "SEQ_M_INTRODUCTION_CATEGORY")
    private Long id;

    @Column(name = "code")
    private String code;

    @Column(name = "name")
    private String name;

    @Column(name = "link_alias")
    private String linkAlias;

    @Column(name = "note")
    private String note;

    @Column(name = "image_url")
    private String imageUrl;
    
    @Column(name = "image_url_mobile")
    private String imageUrlMobile;

    @Column(name = "parent_id")
    private Long parentId;

    @Column(name = "image_name")
    private String imageName;
    
    @Column(name = "image_name_mobile")
    private String imageNameMobile;

    @Column(name = "description")
    private String description;

    @Column(name = "sort")
    private Long sort;

    @Column(name = "enabled")
    private boolean enabled;

    @Column(name = "introduction_type")
    private String introType;

    @Column(name = "view_type")
    private String viewType;

    @Column(name = "status")
    private Integer status;

    @Column(name = "intro_comment")
    private String comment;

    @Column(name = "banner_title_video")
    private String bannerTitleVideo;

    @Column(name = "banner_video")
    private String bannerVideo;

    @Column(name = "banner_physical_video")
    private String bannerPhysicalVideo;

    @Column(name = "before_id")
    private Long beforeId;

    @Column(name = "create_date")
    private Date createDate;

    @Column(name = "create_by")
    private String createBy;

    @Column(name = "update_date")
    private Date updateDate;

    @Column(name = "update_by")
    private String updateBy;

    @Column(name = "delete_date")
    private Date deleteDate;

    @Column(name = "delete_by")
    private String deleteBy;

    @Column(name = "publish_by")
    private String publishBy;

    @Column(name = "publish_date")
    private Date publishDate;

    @Column(name = "approve_by")
    private String approveBy;

    @Column(name = "approve_date")
    private Date approveDate;
    
    @Column(name = "process_id")
    private Long processId;
    
    @Column(name = "type_of_main")
    private Integer typeOfMain;
    
    @Column(name = "picture_introduction")
    private Integer pictureIntroduction;
    
    @Column(name = "customer_type_id")
    private Long customerTypeId;

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
     * Get imageUrl
     * 
     * @return String
     * @author thuydtn
     */
    public String getImageUrl() {
        return imageUrl;
    }

    /**
     * Set imageUrl
     * 
     * @param imageUrl
     *            type String
     * @return
     * @author thuydtn
     */
    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
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
     * Get sort
     * 
     * @return Long
     * @author thuydtn
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
     * @author thuydtn
     */
    public void setSort(Long sort) {
        this.sort = sort;
    }

    /**
     * Get enabled
     * 
     * @return boolean
     * @author thuydtn
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
     * @author thuydtn
     */
    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    /**
     * @param updateDto
     */
    public void copyDtoProperties(IntroductionCategoryDto updateDto) {
        this.code = updateDto.getCode();
        this.name = updateDto.getName();
        this.description = updateDto.getDescription();
        this.note = updateDto.getNote();
        this.sort = updateDto.getSort();
        this.enabled = updateDto.isEnabled();
        this.parentId = updateDto.getParentId();
        this.viewType = updateDto.getViewType();
        this.introType = updateDto.getIntroType();
        this.linkAlias = updateDto.getLinkAlias();
        this.createBy = updateDto.getCreateBy();
        this.publishBy = updateDto.getPublishedBy();
        this.publishDate = updateDto.getPublishedDate();
        this.approveBy = updateDto.getApprovedBy();
        this.approveDate = updateDto.getApprovedDate();
        this.comment = updateDto.getComment();
        this.bannerVideo = updateDto.getBannerVideo();
        this.bannerPhysicalVideo = updateDto.getBannerPhysicalVideo();
        this.bannerTitleVideo = updateDto.getBannerTitleVideo();
        this.imageName = updateDto.getImageName();
        this.imageUrl = updateDto.getImagePhysicalName();
        this.imageNameMobile = updateDto.getImageNameMobile();
        this.imageUrlMobile = updateDto.getImagePhysicalNameMobile();
        this.status = updateDto.getStatus();
        this.customerTypeId = updateDto.getCustomerTypeId();
        this.beforeId = updateDto.getBeforeId();
        if (updateDto.getTypeOfMain()){
        	this.typeOfMain = 1;
        }
        if (updateDto.getPictureIntroduction()){
        	this.pictureIntroduction = 1;
        }
    }

    /**
     * Get parentId
     * 
     * @return Long
     * @author thuydtn
     */
    public Long getParentId() {
        return parentId;
    }

    /**
     * Set parentId
     * 
     * @param parentId
     *            type Long
     * @return
     * @author thuydtn
     */
    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    /**
     * Get imageName
     * 
     * @return String
     * @author thuydtn
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
     * @author thuydtn
     */
    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    /**
     * Get viewType
     * 
     * @return String
     * @author thuydtn
     */
    public String getViewType() {
        return viewType;
    }

    /**
     * Set viewType
     * 
     * @param viewType
     *            type String
     * @return
     * @author thuydtn
     */
    public void setViewType(String viewType) {
        this.viewType = viewType;
    }

    public String getIntroType() {
        return introType;
    }

    public void setIntroType(String introType) {
        this.introType = introType;
    }

    public String getLinkAlias() {
        return linkAlias;
    }

    public void setLinkAlias(String linkAlias) {
        this.linkAlias = linkAlias;
    }

    /**
     * Get status
     * 
     * @return String
     * @author taitm
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * Set status
     * 
     * @param status
     *            type String
     * @return
     * @author taitm
     */
    public void setStatus(Integer status) {
        this.status = status;
    }

    /**
     * Get comment
     * 
     * @return String
     * @author taitm
     */
    public String getComment() {
        return comment;
    }

    /**
     * Set comment
     * 
     * @param comment
     *            type String
     * @return
     * @author taitm
     */
    public void setComment(String comment) {
        this.comment = comment;
    }

    /**
     * Get bannerVideo
     * 
     * @return String
     * @author taitm
     */
    public String getBannerVideo() {
        return bannerVideo;
    }

    /**
     * Set bannerVideo
     * 
     * @param bannerVideo
     *            type String
     * @return
     * @author taitm
     */
    public void setBannerVideo(String bannerVideo) {
        this.bannerVideo = bannerVideo;
    }

    /**
     * Get bannerPhysicalVideo
     * 
     * @return String
     * @author taitm
     */
    public String getBannerPhysicalVideo() {
        return bannerPhysicalVideo;
    }

    /**
     * Set bannerPhysicalVideo
     * 
     * @param bannerPhysicalVideo
     *            type String
     * @return
     * @author taitm
     */
    public void setBannerPhysicalVideo(String bannerPhysicalVideo) {
        this.bannerPhysicalVideo = bannerPhysicalVideo;
    }

    /**
     * Get bannerTitleVideo
     * 
     * @return String
     * @author taitm
     */
    public String getBannerTitleVideo() {
        return bannerTitleVideo;
    }

    /**
     * Set bannerTitleVideo
     * 
     * @param bannerTitleVideo
     *            type String
     * @return
     * @author taitm
     */
    public void setBannerTitleVideo(String bannerTitleVideo) {
        this.bannerTitleVideo = bannerTitleVideo;
    }

    /**
     * Get approveBy
     * 
     * @return String
     * @author taitm
     */
    public String getApproveBy() {
        return approveBy;
    }

    /**
     * Set approveBy
     * 
     * @param approveBy
     *            type String
     * @return
     * @author taitm
     */
    public void setApproveBy(String approveBy) {
        this.approveBy = approveBy;
    }

    /**
     * Get approveDate
     * 
     * @return Date
     * @author taitm
     */
    public Date getApproveDate() {
        return approveDate;
    }

    /**
     * Set approveDate
     * 
     * @param approveDate
     *            type Date
     * @return
     * @author taitm
     */
    public void setApproveDate(Date approveDate) {
        this.approveDate = approveDate;
    }

    /**
     * Get beforeId
     * 
     * @return Long
     * @author taitm
     */
    public Long getBeforeId() {
        return beforeId;
    }

    /**
     * Set beforeId
     * 
     * @param beforeId
     *            type Long
     * @return
     * @author taitm
     */
    public void setBeforeId(Long beforeId) {
        this.beforeId = beforeId;
    }

    /**
     * Get createDate
     * 
     * @return Date
     * @author taitm
     */
    public Date getCreateDate() {
        return createDate;
    }

    /**
     * Set createDate
     * 
     * @param createDate
     *            type Date
     * @return
     * @author taitm
     */
    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    /**
     * Get createBy
     * 
     * @return String
     * @author taitm
     */
    public String getCreateBy() {
        return createBy;
    }

    /**
     * Set createBy
     * 
     * @param createBy
     *            type String
     * @return
     * @author taitm
     */
    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    /**
     * Get updateDate
     * 
     * @return Date
     * @author taitm
     */
    public Date getUpdateDate() {
        return updateDate;
    }

    /**
     * Set updateDate
     * 
     * @param updateDate
     *            type Date
     * @return
     * @author taitm
     */
    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    /**
     * Get updateBy
     * 
     * @return String
     * @author taitm
     */
    public String getUpdateBy() {
        return updateBy;
    }

    /**
     * Set updateBy
     * 
     * @param updateBy
     *            type String
     * @return
     * @author taitm
     */
    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy;
    }

    /**
     * Get deleteDate
     * 
     * @return Date
     * @author taitm
     */
    public Date getDeleteDate() {
        return deleteDate;
    }

    /**
     * Set deleteDate
     * 
     * @param deleteDate
     *            type Date
     * @return
     * @author taitm
     */
    public void setDeleteDate(Date deleteDate) {
        this.deleteDate = deleteDate;
    }

    /**
     * Get deleteBy
     * 
     * @return String
     * @author taitm
     */
    public String getDeleteBy() {
        return deleteBy;
    }

    /**
     * Set deleteBy
     * 
     * @param deleteBy
     *            type String
     * @return
     * @author taitm
     */
    public void setDeleteBy(String deleteBy) {
        this.deleteBy = deleteBy;
    }

    /**
     * Get publishBy
     * 
     * @return String
     * @author taitm
     */
    public String getPublishBy() {
        return publishBy;
    }

    /**
     * Set publishBy
     * 
     * @param publishBy
     *            type String
     * @return
     * @author taitm
     */
    public void setPublishBy(String publishBy) {
        this.publishBy = publishBy;
    }

    /**
     * Get publishDate
     * 
     * @return Date
     * @author taitm
     */
    public Date getPublishDate() {
        return publishDate;
    }

    /**
     * Set publishDate
     * 
     * @param publishDate
     *            type Date
     * @return
     * @author taitm
     */
    public void setPublishDate(Date publishDate) {
        this.publishDate = publishDate;
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

    /**
     * @return the typeOfMain
     * @author taitm
     */
    public Integer getTypeOfMain() {
        return typeOfMain;
    }

    /**
     * @param typeOfMain
     *            the typeOfMain to set
     * @author taitm
     */
    public void setTypeOfMain(Integer typeOfMain) {
        this.typeOfMain = typeOfMain;
    }

    /**
     * @return the pictureIntroduction
     * @author taitm
     */
    public Integer getPictureIntroduction() {
        return pictureIntroduction;
    }

    /**
     * @param pictureIntroduction
     *            the pictureIntroduction to set
     * @author taitm
     */
    public void setPictureIntroduction(Integer pictureIntroduction) {
        this.pictureIntroduction = pictureIntroduction;
    }

    /**
     * @return the imageUrlMobile
     * @author taitm
     */
    public String getImageUrlMobile() {
        return imageUrlMobile;
    }

    /**
     * @param imageUrlMobile the imageUrlMobile to set
     * @author taitm
     */
    public void setImageUrlMobile(String imageUrlMobile) {
        this.imageUrlMobile = imageUrlMobile;
    }

    /**
     * @return the imageNameMobile
     * @author taitm
     */
    public String getImageNameMobile() {
        return imageNameMobile;
    }

    /**
     * @param imageNameMobile
     *            the imageNameMobile to set
     * @author taitm
     */
    public void setImageNameMobile(String imageNameMobile) {
        this.imageNameMobile = imageNameMobile;
    }

    /**
     * @return the customerTypeId
     * @author taitm
     */
    public Long getCustomerTypeId() {
        return customerTypeId;
    }

    /**
     * @param customerTypeId
     *            the customerTypeId to set
     * @author taitm
     */
    public void setCustomerTypeId(Long customerTypeId) {
        this.customerTypeId = customerTypeId;
    }

}
