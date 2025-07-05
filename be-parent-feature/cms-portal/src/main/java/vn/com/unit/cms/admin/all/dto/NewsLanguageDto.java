/*******************************************************************************
 * Class        ：NewsLanguageDto
 * Created date ：2017/02/24
 * Lasted date  ：2017/02/24
 * Author       ：hand
 * Change log   ：2017/02/24：01-00 hand create a new
 ******************************************************************************/
package vn.com.unit.cms.admin.all.dto;

import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;

/**
 * NewsLanguageDto
 * 
 * @version 01-00
 * @since 01-00
 * @author hand
 */
@SuppressWarnings("deprecation")
public class NewsLanguageDto {

    /** id */
    private Long id;

    /** mNewsId */
    private Long mNewsId;

    /** languageCode */
    private String languageCode;

    /** title */
    @NotEmpty
    @Size(min = 1, max = 500)
    private String title;

    /** shortContent */
    private String shortContent;

    /** content */
    private String content;
    
    /** giftMessage */
    @Size(max = 200)
    private String giftMessage;
    
    private String linkAlias;
    
    /** keyWord */
    private String keyWord;
    
    /** descriptionKeyword */
    private String descriptionKeyword;
    
    private String bannerMobilePhysicalImg;
    
    private String bannerDesktopPhysicalImg;
    
    private String physicalImgUrl;
    
    private String imgUrl;
    
    private String bannerDesktopImg;
    
    private String bannerMobileImg;
    
    private String label;

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
     * Get mNewsId
     * 
     * @return Long
     * @author hand
     */
    public Long getmNewsId() {
        return mNewsId;
    }

    /**
     * Set mNewsId
     * 
     * @param mNewsId
     *            type Long
     * @return
     * @author hand
     */
    public void setmNewsId(Long mNewsId) {
        this.mNewsId = mNewsId;
    }

    /**
     * Get languageCode
     * 
     * @return String
     * @author hand
     */
    public String getLanguageCode() {
        return languageCode;
    }

    /**
     * Set languageCode
     * 
     * @param languageCode
     *            type String
     * @return
     * @author hand
     */
    public void setLanguageCode(String languageCode) {
        this.languageCode = languageCode;
    }

    /**
     * Get title
     * 
     * @return String
     * @author hand
     */
    public String getTitle() {
        return title;
    }

    /**
     * Set title
     * 
     * @param title
     *            type String
     * @return
     * @author hand
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Get shortContent
     * 
     * @return String
     * @author hand
     */
    public String getShortContent() {
        return shortContent;
    }

    /**
     * Set shortContent
     * 
     * @param shortContent
     *            type String
     * @return
     * @author hand
     */
    public void setShortContent(String shortContent) {
        this.shortContent = shortContent;
    }

    /**
     * Get content
     * 
     * @return String
     * @author hand
     */
    public String getContent() {
        return content;
    }

    /**
     * Set content
     * 
     * @param content
     *            type String
     * @return
     * @author hand
     */
    public void setContent(String content) {
        this.content = content;
    }

    /**
     * Get giftMessage
     * @return String
     * @author hand
     */
    public String getGiftMessage() {
        return giftMessage;
    }

    /**
     * Set giftMessage
     * 
     * @param giftMessage
     *            type String
     * @return
     * @author hand
     */
    public void setGiftMessage(String giftMessage) {
        this.giftMessage = giftMessage;
    }

    /**
     * Get linkAlias
     * 
     * @return String
     * @author taitm
     */
    public String getLinkAlias() {
        return linkAlias;
    }

    /**
     * Set linkAlias
     * 
     * @param linkAlias
     *            type String
     * @return
     * @author taitm
     */
    public void setLinkAlias(String linkAlias) {
        this.linkAlias = linkAlias;
    }

    /**
     * Get keyWord
     * 
     * @return String
     * @author taitm
     */
    public String getKeyWord() {
        return keyWord;
    }

    /**
     * Set keyWord
     * 
     * @param keyWord
     *            type String
     * @return
     * @author taitm
     */
    public void setKeyWord(String keyWord) {
        this.keyWord = keyWord;
    }

    /**
     * Get descriptionKeyword
     * 
     * @return String
     * @author taitm
     */
    public String getDescriptionKeyword() {
        return descriptionKeyword;
    }

    /**
     * Set descriptionKeyword
     * 
     * @param descriptionKeyword
     *            type String
     * @return
     * @author taitm
     */
    public void setDescriptionKeyword(String descriptionKeyword) {
        this.descriptionKeyword = descriptionKeyword;
    }

    /**
     * Get bannerDesktop
     * 
     * @return Long
     * @author taitm
     */
    public String getBannerDesktopImg() {
        return bannerDesktopImg;
    }

    /**
     * Set bannerDesktop
     * 
     * @param bannerDesktop
     *            type Long
     * @return
     * @author taitm
     */
    public void setBannerDesktopImg(String bannerDesktop) {
        this.bannerDesktopImg = bannerDesktop;
    }

    /**
     * Get bannerMobile
     * 
     * @return Long
     * @author taitm
     */
    public String getBannerMobile() {
        return bannerMobileImg;
    }

    /**
     * Set bannerMobile
     * 
     * @param bannerMobile
     *            type Long
     * @return
     * @author taitm
     */
    public void setBannerMobileImg(String bannerMobile) {
        this.bannerMobileImg = bannerMobile;
    }

    /**
     * Get label
     * 
     * @return String
     * @author taitm
     */
    public String getLabel() {
        return label;
    }

    /**
     * Set label
     * 
     * @param label
     *            type String
     * @return
     * @author taitm
     */
    public void setLabel(String label) {
        this.label = label;
    }

    /**
     * Get bannerMobilePhysicalImg
     * 
     * @return String
     * @author taitm
     */
    public String getBannerMobilePhysicalImg() {
        return bannerMobilePhysicalImg;
    }

    /**
     * Set bannerMobilePhysicalImg
     * 
     * @param bannerMobilePhysicalImg
     *            type String
     * @return
     * @author taitm
     */
    public void setBannerMobilePhysicalImg(String bannerMobilePhysicalImg) {
        this.bannerMobilePhysicalImg = bannerMobilePhysicalImg;
    }

    /**
     * Get bannerDesktopPhysicalImg
     * 
     * @return String
     * @author taitm
     */
    public String getBannerDesktopPhysicalImg() {
        return bannerDesktopPhysicalImg;
    }

    /**
     * Set bannerDesktopPhysicalImg
     * 
     * @param bannerDesktopPhysicalImg
     *            type String
     * @return
     * @author taitm
     */
    public void setBannerDesktopPhysicalImg(String bannerDesktopPhysicalImg) {
        this.bannerDesktopPhysicalImg = bannerDesktopPhysicalImg;
    }

    /**
     * Get bannerMobileImg
     * 
     * @return String
     * @author taitm
     */
    public String getBannerMobileImg() {
        return bannerMobileImg;
    }

    public String getPhysicalImgUrl() {
        return physicalImgUrl;
    }

    public void setPhysicalImgUrl(String physicalImgUrl) {
        this.physicalImgUrl = physicalImgUrl;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

}
