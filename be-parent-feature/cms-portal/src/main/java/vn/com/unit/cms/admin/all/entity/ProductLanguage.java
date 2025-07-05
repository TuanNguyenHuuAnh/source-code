/*******************************************************************************
 * Class        ：ProductLanguage
 * Created date ：2017/05/03
 * Lasted date  ：2017/05/03
 * Author       ：hand
 * Change log   ：2017/05/03：01-00 hand create a new
 ******************************************************************************/
package vn.com.unit.cms.admin.all.entity;

import org.springframework.data.annotation.Id;

import jp.sf.amateras.mirage.annotation.Column;
import jp.sf.amateras.mirage.annotation.PrimaryKey;
import jp.sf.amateras.mirage.annotation.PrimaryKey.GenerationType;
import jp.sf.amateras.mirage.annotation.Table;
import vn.com.unit.cms.core.entity.AbstractTracking;

/**
 * ProductLanguage
 * 
 * @version 01-00
 * @since 01-00
 * @author hand
 */
@Table(name = "m_product_language")
public class ProductLanguage extends AbstractTracking {

    @Id
    @Column(name = "id")
    @PrimaryKey(generationType = GenerationType.SEQUENCE, generator = "SEQ_M_PRODUCT_LANGUAGE")
    private Long id;

    @Column(name = "m_product_id")
    private Long productId;

    @Column(name = "m_language_code")
    private String languageCode;

    @Column(name = "title")
    private String title;

    @Column(name = "short_content")
    private String shortContent;

    @Column(name = "link_alias")
    private String linkAlias;
    
    @Column(name = "key_word")
    private String keyWord;
    
    @Column(name = "description_keyword")
    private String descriptionKeyword;
    
    @Column(name = "words_banner")
    private String wordsBanner;
    
    @Column(name = "banner_desktop")
    private Long bannerDesktop;
    
    @Column(name = "banner_mobile")
    private Long bannerMobile;
    
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
     * Get productId
     * 
     * @return Long
     * @author hand
     */
    public Long getProductId() {
        return productId;
    }

    /**
     * Set productId
     * 
     * @param productId
     *            type Long
     * @return
     * @author hand
     */
    public void setProductId(Long productId) {
        this.productId = productId;
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

    public String getKeyWord() {
        return keyWord;
    }

    public void setKeyWord(String keyWord) {
        this.keyWord = keyWord;
    }

    public String getDescriptionKeyword() {
        return descriptionKeyword;
    }

    public void setDescriptionKeyword(String descriptionKeyword) {
        this.descriptionKeyword = descriptionKeyword;
    }

    public String getWordsBanner() {
        return wordsBanner;
    }

    public void setWordsBanner(String wordsBanner) {
        this.wordsBanner = wordsBanner;
    }

    public String getLinkAlias() {
        return linkAlias;
    }

    public void setLinkAlias(String linkAlias) {
        this.linkAlias = linkAlias;
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

}
