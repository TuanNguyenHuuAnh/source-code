/*******************************************************************************
 * Class        ：NewsCategoryLanguage
 * Created date ：2017/02/24
 * Lasted date  ：2017/02/24
 * Author       ：hand
 * Change log   ：2017/02/24：01-00 hand create a new
 ******************************************************************************/
package vn.com.unit.cms.admin.all.entity;

import org.springframework.data.annotation.Id;

import jp.sf.amateras.mirage.annotation.Column;
import jp.sf.amateras.mirage.annotation.PrimaryKey;
import jp.sf.amateras.mirage.annotation.PrimaryKey.GenerationType;
import jp.sf.amateras.mirage.annotation.Table;
import vn.com.unit.cms.core.entity.AbstractTracking;

/**
 * NewsCategoryLanguage
 * 
 * @version 01-00
 * @since 01-00
 * @author hand
 */
@Table(name = "m_news_category_language")
public class NewsCategoryLanguage extends AbstractTracking {

    @Id
    @Column(name = "id")
    @PrimaryKey(generationType = GenerationType.SEQUENCE, generator = "SEQ_M_NEWS_CATEGORY_LANGUAGE")
    private Long id;

    @Column(name = "m_news_category_id")
    private Long mNewsCategoryId;

    @Column(name = "m_language_code")
    private String languageCode;

    @Column(name = "label")
    private String label;
    
    @Column(name = "key_word")
    private String keyWord;
    
    @Column(name = "description_keyword")
    private String descriptionKeyword;
    
    @Column(name = "link_alias")
    private String linkAlias;


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
     * Get mNewsCategoryId
     * @return Long
     * @author hand
     */
    public Long getmNewsCategoryId() {
        return mNewsCategoryId;
    }

    /**
     * Set mNewsCategoryId
     * @param   mNewsCategoryId
     *          type Long
     * @return
     * @author  hand
     */
    public void setmNewsCategoryId(Long mNewsCategoryId) {
        this.mNewsCategoryId = mNewsCategoryId;
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
     * Get label
     * @return String
     * @author hand
     */
    public String getLabel() {
        return label;
    }

    /**
     * Set label
     * @param   label
     *          type String
     * @return
     * @author  hand
     */
    public void setLabel(String label) {
        this.label = label;
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

}
