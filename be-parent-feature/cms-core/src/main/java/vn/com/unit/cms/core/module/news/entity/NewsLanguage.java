/*******************************************************************************
 * Class        ：NewsLanguage
 * Created date ：2017/02/24
 * Lasted date  ：2017/02/24
 * Author       ：hand
 * Change log   ：2017/02/24：01-00 hand create a new
 ******************************************************************************/
package vn.com.unit.cms.core.module.news.entity;

import javax.persistence.Lob;

import org.springframework.data.annotation.Id;

import jp.sf.amateras.mirage.annotation.Column;
import jp.sf.amateras.mirage.annotation.PrimaryKey;
import jp.sf.amateras.mirage.annotation.PrimaryKey.GenerationType;
import jp.sf.amateras.mirage.annotation.Table;
import vn.com.unit.cms.core.entity.AbstractTracking;

/**
 * NewsLanguage
 * 
 * @version 01-00
 * @since 01-00
 * @author hand
 */
@Table(name = "m_news_language")
public class NewsLanguage extends AbstractTracking {

    @Id
    @Column(name = "id")
    @PrimaryKey(generationType = GenerationType.SEQUENCE, generator = "SEQ_M_NEWS_LANGUAGE")
    private Long id;

    @Column(name = "m_news_id")
    private Long mNewsId;

    @Column(name = "m_language_code")
    private String languageCode;

    @Column(name = "title")
    private String title;

    @Column(name = "short_content")
    private String shortContent;

    @Column(name = "content")
    @Lob
    private byte[] content;
    
    @Column(name = "gift_message")
    private String giftMessage;
    
    @Column(name = "link_alias")
    private String linkAlias;
    
    @Column(name = "key_word")
    private String keyWord;
    
    @Column(name = "description_keyword")
    private String descriptionKeyword;
    
    @Column(name = "banner_desktop_img")
    private String bannerDesktopImg;
    
    @Column(name = "banner_desktop_physical_img")
    private String bannerDesktopPhysicalImg;
    
    @Column(name = "banner_mobile_img")
    private String bannerMobileImg;
    
    @Column(name = "banner_mobile_physical_img")
    private String bannerMobilePhysicalImg;
    
    @Column(name = "label")
    private String label;
    
    @Column(name = "img_url")
    private String imgUrl;
    
    @Column(name = "physical_img_url")
    private String physicalImgUrl;    

    /**
     * Get id
     * @return Long
     * @author hand
     */
    public Long getId() {
        return id;
    }

    /**
     * Get mNewsId
     * @return String
     * @author hand
     */
    public Long getmNewsId() {
        return mNewsId;
    }

    /**
     * Get languageCode
     * @return String
     * @author hand
     */
    public String getLanguageCode() {
        return languageCode;
    }

    /**
     * Get title
     * @return String
     * @author hand
     */
    public String getTitle() {
        return title;
    }

    /**
     * Get shortContent
     * @return String
     * @author hand
     */
    public String getShortContent() {
        return shortContent;
    }

    /**
     * Get content
     * @return byte[]
     * @author hand
     */
    public byte[] getContent() {
        return content;
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
     * Set mNewsId
     * @param   mNewsId
     *          type Long
     * @return
     * @author  hand
     */
    public void setmNewsId(Long mNewsId) {
        this.mNewsId = mNewsId;
    }

    /**
     * Set languageCode
     * @param   languageCode
     *          type Long
     * @return
     * @author  hand
     */
    public void setLanguageCode(String languageCode) {
        this.languageCode = languageCode;
    }

    /**
     * Set title
     * @param   title
     *          type String
     * @return
     * @author  hand
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Set shortContent
     * @param   shortContent
     *          type String
     * @return
     * @author  hand
     */
    public void setShortContent(String shortContent) {
        this.shortContent = shortContent;
    }

    /**
     * Set content
     * @param   content
     *          type byte[]
     * @return
     * @author  hand
     */
    public void setContent(byte[] content) {
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
     * @param   giftMessage
     *          type String
     * @return
     * @author  hand
     */
    public void setGiftMessage(String giftMessage) {
        this.giftMessage = giftMessage;
    }

    
    /**
     * Get linkAlias
     * @return String
     * @author taitm
     */
    public String getLinkAlias() {
        return linkAlias;
    }

    
    /**
     * Set linkAlias
     * @param   linkAlias
     *          type String
     * @return
     * @author  taitm
     */
    public void setLinkAlias(String linkAlias) {
        this.linkAlias = linkAlias;
    }

    
    /**
     * Get keyWord
     * @return String
     * @author taitm
     */
    public String getKeyWord() {
        return keyWord;
    }

    
    /**
     * Set keyWord
     * @param   keyWord
     *          type String
     * @return
     * @author  taitm
     */
    public void setKeyWord(String keyWord) {
        this.keyWord = keyWord;
    }

    
    /**
     * Get descriptionKeyword
     * @return String
     * @author taitm
     */
    public String getDescriptionKeyword() {
        return descriptionKeyword;
    }

    
    /**
     * Set descriptionKeyword
     * @param   descriptionKeyword
     *          type String
     * @return
     * @author  taitm
     */
    public void setDescriptionKeyword(String descriptionKeyword) {
        this.descriptionKeyword = descriptionKeyword;
    }

    
    /**
     * Get bannerDesktop
     * @return Long
     * @author taitm
     */
    public String getBannerDesktopImg() {
        return bannerDesktopImg;
    }

    
    /**
     * Set bannerDesktop
     * @param   bannerDesktop
     *          type Long
     * @return
     * @author  taitm
     */
    public void setBannerDesktopImg(String bannerDesktopImg) {
        this.bannerDesktopImg = bannerDesktopImg;
    }

    
    /**
     * Get bannerMobile
     * @return Long
     * @author taitm
     */
    public String getBannerMobileImg() {
        return bannerMobileImg;
    }

    
    /**
     * Set bannerMobile
     * @param   bannerMobile
     *          type Long
     * @return
     * @author  taitm
     */
    public void setBannerMobileImg(String bannerMobileImg) {
        this.bannerMobileImg = bannerMobileImg;
    }

    
    /**
     * Get label
     * @return String
     * @author taitm
     */
    public String getLabel() {
        return label;
    }

    
    /**
     * Set label
     * @param   label
     *          type String
     * @return
     * @author  taitm
     */
    public void setLabel(String label) {
        this.label = label;
    }

    /**
     * Get bannerDesktopPhysicalImg
     * 
     * @return Long
     * @author taitm
     */
    public String getBannerDesktopPhysicalImg() {
        return bannerDesktopPhysicalImg;
    }

    /**
     * Set bannerDesktopPhysicalImg
     * 
     * @param bannerDesktopPhysicalImg
     *            type Long
     * @return
     * @author taitm
     */
    public void setBannerDesktopPhysicalImg(String bannerDesktopPhysicalImg) {
        this.bannerDesktopPhysicalImg = bannerDesktopPhysicalImg;
    }

    /**
     * Get bannerMobilePhysicalImg
     * 
     * @return Long
     * @author taitm
     */
    public String getBannerMobilePhysicalImg() {
        return bannerMobilePhysicalImg;
    }

    /**
     * Set bannerMobilePhysicalImg
     * 
     * @param bannerMobilePhysicalImg
     *            type Long
     * @return
     * @author taitm
     */
    public void setBannerMobilePhysicalImg(String bannerMobilePhysicalImg) {
        this.bannerMobilePhysicalImg = bannerMobilePhysicalImg;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getPhysicalImgUrl() {
        return physicalImgUrl;
    }

    public void setPhysicalImgUrl(String physicalImgUrl) {
        this.physicalImgUrl = physicalImgUrl;
    }

}
