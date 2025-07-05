/*******************************************************************************
 * Class        ：ProductCategoryLanguageDto
 * Created date ：2017/05/03
 * Lasted date  ：2017/05/03
 * Author       ：hand
 * Change log   ：2017/05/03：01-00 hand create a new
 ******************************************************************************/
package vn.com.unit.cms.admin.all.dto;

import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;

/**
 * ProductCategoryLanguageDto
 * 
 * @version 01-00
 * @since 01-00
 * @author hand
 */
@SuppressWarnings("deprecation")
public class ProductCategoryLanguageDto {

    /** id */
    private Long id;

    /** languageCode */
    private String languageCode;

    /** title */
    @NotEmpty
    @Size(max = 255)
    private String title;
    
    /** description */
    private String description;

    private String keyword;
    
    private String keywordDescription;

    private String linkAlias;

    private String textOnBanner;
    
    private Long bannerDesktop;
    
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
     * Get languageCode
     * @return String
     * @author hand
     */
    public String getLanguageCode() {
        return languageCode;
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
