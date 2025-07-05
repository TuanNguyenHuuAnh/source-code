/*******************************************************************************
 * Class        ：DocumentTypeLanguage
 * Created date ：2017/04/17
 * Lasted date  ：2017/04/17
 * Author       ：thuydtn
 * Change log   ：2017/04/17：01-00 thuydtn create a new
 ******************************************************************************/
package vn.com.unit.cms.admin.all.entity;

import org.springframework.data.annotation.Id;

import jp.sf.amateras.mirage.annotation.Column;
import jp.sf.amateras.mirage.annotation.PrimaryKey;
import jp.sf.amateras.mirage.annotation.PrimaryKey.GenerationType;
import jp.sf.amateras.mirage.annotation.Table;
import vn.com.unit.cms.core.entity.AbstractTracking;

/**
 * DocumentCategoryLanguage
 * 
 * @version 01-00
 * @since 01-00
 * @author thuydtn
 */
@Table(name = "m_document_type_language")
public class DocumentTypeLanguage extends AbstractTracking {

    @Id
    @Column(name = "id")
    @PrimaryKey(generationType = GenerationType.SEQUENCE, generator = "SEQ_M_DOCUMENT_TYPE_LANGUAGE")
    private Long id;
    @Column(name = "m_document_type_id")
    private Long documentTypeId;
    @Column(name = "m_language_code")
    private String languageCode;
    @Column(name = "title")
    private String title;
    @Column(name = "introduce")
    private String introduce;

    @Column(name = "key_word")
    private String keyWord;

    @Column(name = "key_word_description")
    private String keyWordDescription;

    @Column(name = "link_alias")
    private String linkAlias;

    /**
     * Get id
     * 
     * @return Long
     * @author thuydtn
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
     * @author thuydtn
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Get documentTypeId
     * 
     * @return Long
     * @author thuydtn
     */
    public Long getDocumentTypeId() {
        return documentTypeId;
    }

    /**
     * Set documentTypeId
     * 
     * @param documentTypeId
     *            type Long
     * @return
     * @author thuydtn
     */
    public void setDocumentTypeId(Long documentTypeId) {
        this.documentTypeId = documentTypeId;
    }

    /**
     * Get languageCode
     * 
     * @return String
     * @author thuydtn
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
     * @author thuydtn
     */
    public void setLanguageCode(String languageCode) {
        this.languageCode = languageCode;
    }

    /**
     * Get title
     * 
     * @return String
     * @author thuydtn
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
     * @author thuydtn
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Get introduce
     * 
     * @return String
     * @author thuydtn
     */
    public String getIntroduce() {
        return introduce;
    }

    /**
     * Set introduce
     * 
     * @param introduce
     *            type String
     * @return
     * @author thuydtn
     */
    public void setIntroduce(String introduce) {
        this.introduce = introduce;
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
