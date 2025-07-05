/*******************************************************************************
 * Class        ：ProductCategoryLanguage
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
 * ProductCategoryLanguage
 * 
 * @version 01-00
 * @since 01-00
 * @author hand
 */
@Table(name = "m_product_category_language")
public class ProductCategoryLanguage extends AbstractTracking {

    @Id
    @Column(name = "id")
    @PrimaryKey(generationType = GenerationType.SEQUENCE, generator = "SEQ_M_PRODUCT_CATEGORY_LANGUAGE")
    private Long id;

    @Column(name = "m_language_code")
    private String languageCode;

    @Column(name = "m_product_category_id")
    private Long productCategoryId;

    @Column(name = "title")
    private String title;

    @Column(name = "description")
    private String description;
    
    @Column(name = "keyword")
    private String keyword;
    
    @Column(name = "keyword_description")
    private String keywordDescription;

    @Column(name = "link_alias")
    private String linkAlias;
    
    @Column(name = "text_on_banner")
    private String textOnBanner;
    
    @Column(name = "banner_desktop")
    private Long bannerDesktop;
    
    @Column(name = "banner_mobile")
    private Long bannerMobile;

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
     * Get languageCode
     * @return String
     * @author hand
     */
    public String getLanguageCode() {
        return languageCode;
    }

    /**
     * Set languageCode
     * @param   languageCode
     *          type String
     * @return
     * @author  hand
     */
    public void setLanguageCode(String languageCode) {
        this.languageCode = languageCode;
    }

    /**
     * Get productCategoryId
     * @return Long
     * @author hand
     */
    public Long getProductCategoryId() {
        return productCategoryId;
    }

    /**
     * Set productCategoryId
     * @param   productCategoryId
     *          type Long
     * @return
     * @author  hand
     */
    public void setProductCategoryId(Long productCategoryId) {
        this.productCategoryId = productCategoryId;
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

    
    public String getKeyword() {
        return keyword;
    }

    
    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    
    public String getKeywordDescription() {
        return keywordDescription;
    }

    
    public void setKeywordDescription(String keywordDescription) {
        this.keywordDescription = keywordDescription;
    }

    
    public String getLinkAlias() {
        return linkAlias;
    }

    
    public void setLinkAlias(String linkAlias) {
        this.linkAlias = linkAlias;
    }

    
    public String getTextOnBanner() {
        return textOnBanner;
    }

    
    public void setTextOnBanner(String textOnBanner) {
        this.textOnBanner = textOnBanner;
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
