/*******************************************************************************
 * Class        ：FaqsTypeLanguage
 * Created date ：2017/04/17
 * Lasted date  ：2017/04/17
 * Author       ：hand
 * Change log   ：2017/04/17：01-00 hand create a new
 ******************************************************************************/
package vn.com.unit.cms.admin.all.entity;

import org.springframework.data.annotation.Id;

import jp.sf.amateras.mirage.annotation.Column;
import jp.sf.amateras.mirage.annotation.PrimaryKey;
import jp.sf.amateras.mirage.annotation.PrimaryKey.GenerationType;
import jp.sf.amateras.mirage.annotation.Table;
import vn.com.unit.cms.core.entity.AbstractTracking;

/**
 * FaqsTypeLanguage
 * 
 * @version 01-00
 * @since 01-00
 * @author hand
 */
@Table(name = "m_faqs_type_language")
public class FaqsTypeLanguage extends AbstractTracking {

    @Id
    @Column(name = "id")
    @PrimaryKey(generationType = GenerationType.SEQUENCE, generator = "SEQ_M_FAQS_TYPE_LANGUAGE")
    private Long id;

    @Column(name = "m_language_code")
    private String languageCode;

    @Column(name = "m_faqs_type_id")
    private Long typeId;

    @Column(name = "title")
    private String title;
    
    @Column(name = "key_word")
    private String keyWord;
    
    @Column(name = "key_word_description")
    private String keyWordDescription;
    
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
     * Get typeId
     * @return Long
     * @author hand
     */
    public Long getTypeId() {
        return typeId;
    }

    /**
     * Set typeId
     * @param   typeId
     *          type Long
     * @return
     * @author  hand
     */
    public void setTypeId(Long typeId) {
        this.typeId = typeId;
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

	public String getKeyWord() {
		return keyWord;
	}

	public void setKeyWord(String keyWord) {
		this.keyWord = keyWord;
	}

	public String getKeyWordDescription() {
		return keyWordDescription;
	}

	public void setKeyWordDescription(String keyWordDescription) {
		this.keyWordDescription = keyWordDescription;
	}

	public String getLinkAlias() {
		return linkAlias;
	}

	public void setLinkAlias(String linkAlias) {
		this.linkAlias = linkAlias;
	}

    
}
