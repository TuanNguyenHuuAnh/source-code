/*******************************************************************************
 * Class        ：FaqsCategoryLanguageDto
 * Created date ：2017/02/27
 * Lasted date  ：2017/02/27
 * Author       ：hand
 * Change log   ：2017/02/27：01-00 hand create a new
 ******************************************************************************/
package vn.com.unit.cms.admin.all.dto;

import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;

/**
 * FaqsCategoryLanguageDto
 * 
 * @version 01-00
 * @since 01-00
 * @author hand
 */
@SuppressWarnings("deprecation")
public class FaqsCategoryLanguageDto {

    /** id */
    private Long id;

    /** languageCode */
    private String languageCode;

    /** title */
    @NotEmpty
    @Size(max = 255)
    private String title;

    /**
     * Get id
     * 
     * @return Long
     * @author hand
     */
    public Long getId() {
        return id;
    }
    
  //Add start
    /**KEYWORDS_SEO*/
    private String keywordsSeo;
    /**KEYWORDS*/
    private String keywords;
    /**KEYWORDS_DESC*/
    private String keywordsDesc;
    /**
     * Get Keywords seo
     * @param String keywordsSeo
     * @return String
     * @author thaonv
     */
    public String getKeywordsSeo() {
        return keywordsSeo;
    }
    /**
     * Set Keywords seo
     * @param String keywordsSeo
     * @author thaonv
     */
    public void setKeywordsSeo(String keywordsSeo) {
        this.keywordsSeo = keywordsSeo;
    }
    /**
     * Get Keywords seo
     * @return String
     * @author thaonv
     */
    public String getKeywords() {
        return keywords;
    }
    /**
     * Set Keywords seo
     * @param String keywords
     * @author thaonv
     */
    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }
    /**
     * Get Keywords description
     * @return String keywordsDesc
     * @author thaonv
     */
    public String getKeywordsDesc() {
        return keywordsDesc;
    }
    /**
     * Set Keywords description
     * @param String keywordsDesc
     * @author thaonv
     */
    public void setKeywordsDesc(String keywordsDesc) {
        this.keywordsDesc = keywordsDesc;
    }
    //Add end

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
}
