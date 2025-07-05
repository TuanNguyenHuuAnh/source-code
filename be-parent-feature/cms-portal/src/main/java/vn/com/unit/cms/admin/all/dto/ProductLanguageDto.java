/*******************************************************************************
 * Class        ：ProductLanguageDto
 * Created date ：2017/05/04
 * Lasted date  ：2017/05/04
 * Author       ：hand
 * Change log   ：2017/05/04：01-00 hand create a new
 ******************************************************************************/
package vn.com.unit.cms.admin.all.dto;

import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;

/**
 * ProductLanguageDto
 * 
 * @version 01-00
 * @since 01-00
 * @author hand
 */
@SuppressWarnings("deprecation")
public class ProductLanguageDto {

    /** id */
    private Long id;

    /** ProductId */
    private Long productId;

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

    /** keyWord */
    private String keyWord;
    
    /** descriptionKeyword */
    private String descriptionKeyword;
    
    /** wordsBanner */
    private String wordsBanner;
    
    private String linkAlias;
    
    private Long bannerDesktop;
    
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
     * Get ProductId
     * 
     * @return Long
     * @author hand
     */
    public Long getProductId() {
        return productId;
    }

    /**
     * Set ProductId
     * 
     * @param ProductId
     *            type Long
     * @return
     * @author hand
     */
    public void setProductId(Long ProductId) {
        this.productId = ProductId;
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
