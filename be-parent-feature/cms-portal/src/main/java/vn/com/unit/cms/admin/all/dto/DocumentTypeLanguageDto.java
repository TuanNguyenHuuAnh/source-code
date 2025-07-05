/*******************************************************************************
 * Class        ：DocumentTypeLanguage
 * Created date ：2017/04/17
 * Lasted date  ：2017/04/17
 * Author       ：thuydtn
 * Change log   ：2017/04/17：01-00 thuydtn create a new
 ******************************************************************************/
package vn.com.unit.cms.admin.all.dto;

import vn.com.unit.cms.admin.all.entity.DocumentTypeLanguage;

/**
 * DocumentCategoryLanguage
 * 
 * @version 01-00
 * @since 01-00
 * @author thuydtn
 */
public class DocumentTypeLanguageDto {

    private Long id;
    private Long documentTypeId;
    private String languageCode;
    private String title;
    private String languageDispName;
    private String introduce;

    private String keyWord;

    private String keyWordDescription;

    private String linkAlias;

    public DocumentTypeLanguageDto() {

    }

    public DocumentTypeLanguageDto(DocumentTypeLanguage entity, String languageName) {
        this.setId(entity.getId());
        this.setDocumentTypeId(entity.getDocumentTypeId());
        this.setLanguageCode(entity.getLanguageCode());
        this.setTitle(entity.getTitle());
        this.setLanguageDispName(languageName);
        this.setIntroduce(entity.getIntroduce());

        this.setKeyWord(entity.getKeyWord());
        this.setKeyWordDescription(entity.getKeyWordDescription());
        this.setLinkAlias(entity.getLinkAlias());        
    }

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
     * Get languageDispName
     * 
     * @return String
     * @author thuydtn
     */
    public String getLanguageDispName() {
        return languageDispName;
    }

    /**
     * Set languageDispName
     * 
     * @param languageDispName
     *            type String
     * @return
     * @author thuydtn
     */
    public void setLanguageDispName(String languageDispName) {
        this.languageDispName = languageDispName;
    }

    /**
     * Get description
     * 
     * @return String
     * @author thuydtn
     */
    public String getIntroduce() {
        return introduce;
    }

    /**
     * Set description
     * 
     * @param description
     *            type String
     * @return
     * @author thuydtn
     */
    public void setIntroduce(String description) {
        this.introduce = description;
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
