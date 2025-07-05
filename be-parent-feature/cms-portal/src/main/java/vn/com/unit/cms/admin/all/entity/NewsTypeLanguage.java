/*******************************************************************************
 * Class        ：NewsTypeLanguage
 * Created date ：2017/03/01
 * Lasted date  ：2017/03/01
 * Author       ：hand
 * Change log   ：2017/03/01：01-00 hand create a new
 ******************************************************************************/
package vn.com.unit.cms.admin.all.entity;


import org.springframework.data.annotation.Id;

import jp.sf.amateras.mirage.annotation.Column;
import jp.sf.amateras.mirage.annotation.PrimaryKey;
import jp.sf.amateras.mirage.annotation.PrimaryKey.GenerationType;
import jp.sf.amateras.mirage.annotation.Table;
import vn.com.unit.cms.core.entity.AbstractTracking;

/**
 * NewsTypeLanguage
 * 
 * @version 01-00
 * @since 01-00
 * @author hand
 */
@Table(name = "m_news_type_language")
public class NewsTypeLanguage extends AbstractTracking{

    @Id
    @Column(name = "id")
    @PrimaryKey(generationType = GenerationType.SEQUENCE, generator = "SEQ_M_NEWS_TYPE_LANGUAGE")
    private Long id;

    @Column(name = "m_news_type_id")
    private Long mNewsTypeId;

    @Column(name = "m_language_code")
    private String languageCode;

    @Column(name = "label")
    private String label;
    
    @Column(name = "description")
    private String description;
    
    @Column(name = "key_word")
    private String keyWord;
    
    @Column(name = "description_keyword")
    private String descriptionKeyword;
    
    @Column(name = "link_alias")
    private String linkAlias;
    
    @Column(name = "IMAGE_NAME")
    private String imageName;
    
    @Column(name = "PHYCICAL_IMG")
    private String phycicalImg;

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
     * Get mNewsTypeId
     * 
     * @return String
     * @author hand
     */
    public Long getmNewsTypeId() {
        return mNewsTypeId;
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
     * Get label
     * 
     * @return String
     * @author hand
     */
    public String getLabel() {
        return label;
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
     * Set mNewsTypeId
     * 
     * @param mNewsTypeId
     *            type Long
     * @return
     * @author hand
     */
    public void setmNewsTypeId(Long mNewsTypeId) {
        this.mNewsTypeId = mNewsTypeId;
    }

    /**
     * Set languageCode
     * 
     * @param languageCode
     *            type Long
     * @return
     * @author hand
     */
    public void setLanguageCode(String languageCode) {
        this.languageCode = languageCode;
    }

    /**
     * Set label
     * 
     * @param label
     *            type String
     * @return
     * @author hand
     */
    public void setLabel(String label) {
        this.label = label;
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

    /**
     * @return the imageName
     * @author taitm
     */
    public String getImageName() {
        return imageName;
    }

    /**
     * @param imageName
     *            the imageName to set
     * @author taitm
     */
    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    /**
     * @return the phycicalImg
     * @author taitm
     */
    public String getPhycicalImg() {
        return phycicalImg;
    }

    /**
     * @param phycicalImg
     *            the phycicalImg to set
     * @author taitm
     */
    public void setPhycicalImg(String phycicalImg) {
        this.phycicalImg = phycicalImg;
    }

}
