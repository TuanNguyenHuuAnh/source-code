/*******************************************************************************
 * Class        ：FaqsCategoryLanguage
 * Created date ：2017/02/28
 * Lasted date  ：2017/02/28
 * Author       ：vinhnht
 * Change log   ：2017/02/28：01-00 vinhnht create a new
 ******************************************************************************/
package vn.com.unit.cms.admin.all.entity;

import org.springframework.data.annotation.Id;

import jp.sf.amateras.mirage.annotation.Column;
import jp.sf.amateras.mirage.annotation.PrimaryKey;
import jp.sf.amateras.mirage.annotation.PrimaryKey.GenerationType;
import jp.sf.amateras.mirage.annotation.Table;
import vn.com.unit.cms.core.entity.AbstractTracking;

/**
 * FaqsCategoryLanguage
 * 
 * @version 01-00
 * @since 01-00
 * @author vinhnht
 */
@Table(name = "m_faqs_category_language")
public class FaqsCategoryLanguage extends AbstractTracking {

    @Id
    @Column(name = "id")
    @PrimaryKey(generationType = GenerationType.SEQUENCE, generator = "SEQ_M_FAQS_CATEGORY_LANGUAGE")
    private Long id;

    @Column(name = "m_faqs_category_id")
    private Long categoryId;

    @Column(name = "m_language_code")
    private String languageCode;

    @Column(name = "title")
    private String title;
    
    //ADD START
    @Column( name = "KEYWORDS_SEO" )
    private String keywordsSeo;

    @Column( name = "KEYWORDS" )
    private String keywords;

    @Column( name = "KEYWORDS_DESC" )
    private String keywordsDesc;

    /**
     * Get keywords seo
     * @return String
     * @author thaonv
     */
    public String getKeywordsSeo() {
        return keywordsSeo;
    }

    /**
     * Get keywords seo
     * @param String
     * @author thaonv
     */
    public void setKeywordsSeo(String keywordsSeo) {
        this.keywordsSeo = keywordsSeo;
    }

    /**
     * Get keywords
     * @return String
     * @author thaonv
     */
    public String getKeywords() {
        return keywords;
    }

    /**
     * Set keywords
     * @param String
     * @author thaonv
     */
    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }

    /**
     * Get keywords description
     * @return String
     * @author thaonv
     */
    public String getKeywordsDesc() {
        return keywordsDesc;
    }

    /**
     * set keywords description
     * @return String
     * @author thaonv
     */
    public void setKeywordsDesc(String keywordsDesc) {
        this.keywordsDesc = keywordsDesc;
    }
    //ADD END

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
     * Get categoryId
     * @return Long
     * @author hand
     */
    public Long getCategoryId() {
        return categoryId;
    }

    /**
     * Set categoryId
     * @param   categoryId
     *          type Long
     * @return
     * @author  hand
     */
    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
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
}
