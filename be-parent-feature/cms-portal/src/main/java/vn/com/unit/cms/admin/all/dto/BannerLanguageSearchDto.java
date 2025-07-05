/*******************************************************************************
 * Class        ：BannerSearchListDto
 * Created date ：2017/02/15
 * Lasted date  ：2017/02/15
 * Author       ：hand
 * Change log   ：2017/02/15：01-00 hand create a new
 ******************************************************************************/
package vn.com.unit.cms.admin.all.dto;

import java.util.Date;

/**
 * BannerSearchListDto
 * 
 * @version 01-00
 * @since 01-00
 * @author hand
 */
public class BannerLanguageSearchDto {

    /** id */
    private Long id;

    /** code */
    private String code;

    /** name */
    private String name;

    /** note */
    private String note;

    /** bannerImg */
    private String bannerImg;

    /** bannerPhysicalImg */
    private String bannerPhysicalImg;

    /** bannerVideo */
    private String bannerVideo;

    /** bannerPhysicalVideo */
    private String bannerPhysicalVideo;

    /** description */
    private String description;

    /** createDate */
    private Date createDate;

    private String title;

    private String isMobile;

    private boolean isUsed;

    private String createBy;

    private Integer status;

    private String statusName;

    private Long numberBanner;

    private Long numberProduct;

    private Long numberProductType;

    private Long numberProductCategory;

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
     * Get bannerImg
     * 
     * @return String
     * @author hand
     */
    public String getBannerImg() {
        return bannerImg;
    }

    /**
     * Set bannerImg
     * 
     * @param bannerImg
     *            type String
     * @return
     * @author hand
     */
    public void setBannerImg(String bannerImg) {
        this.bannerImg = bannerImg;
    }

    /**
     * Get bannerVideo
     * 
     * @return String
     * @author hand
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
     * @author hand
     */
    public void setBannerVideo(String bannerVideo) {
        this.bannerVideo = bannerVideo;
    }

    /**
     * Get bannerPhysicalImg
     * 
     * @return String
     * @author hand
     */
    public String getBannerPhysicalImg() {
        return bannerPhysicalImg;
    }

    /**
     * Set bannerPhysicalImg
     * 
     * @param bannerPhysicalImg
     *            type String
     * @return
     * @author hand
     */
    public void setBannerPhysicalImg(String bannerPhysicalImg) {
        this.bannerPhysicalImg = bannerPhysicalImg;
    }

    /**
     * Get bannerPhysicalVideo
     * 
     * @return String
     * @author hand
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
     * @author hand
     */
    public void setBannerPhysicalVideo(String bannerPhysicalVideo) {
        this.bannerPhysicalVideo = bannerPhysicalVideo;
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
     * @param description
     *            type String
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
     * @param createDate
     *            type Date
     * @return
     * @author hand
     */
    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getIsMobile() {
        return isMobile;
    }

    public void setIsMobile(String isMobile) {
        this.isMobile = isMobile;
    }

    public boolean isUsed() {
        return isUsed;
    }

    public void setUsed(boolean isUsed) {
        this.isUsed = isUsed;
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public Long getNumberBanner() {
        return numberBanner;
    }

    public void setNumberBanner(Long numberBanner) {
        this.numberBanner = numberBanner;
    }

    public Long getNumberProduct() {
        return numberProduct;
    }

    public Long getNumberProductType() {
        return numberProductType;
    }

    public void setNumberProductType(Long numberProductType) {
        this.numberProductType = numberProductType;
    }

    public Long getNumberProductCategory() {
        return numberProductCategory;
    }

    public void setNumberProductCategory(Long numberProductCategory) {
        this.numberProductCategory = numberProductCategory;
    }

    public void setNumberProduct(Long numberProduct) {
        this.numberProduct = numberProduct;
    }

    public String getStatusName() {
        return statusName;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

}
