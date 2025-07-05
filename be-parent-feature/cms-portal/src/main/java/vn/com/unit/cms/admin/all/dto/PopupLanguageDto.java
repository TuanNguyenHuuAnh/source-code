/*******************************************************************************
 * Class        ：PopupLanguageDto
 * Created date ：2017/11/01
 * Lasted date  ：2017/11/01
 * Author       ：TranLTH
 * Change log   ：2017/11/01：01-00 TranLTH create a new
 ******************************************************************************/
package vn.com.unit.cms.admin.all.dto;

/**
 * PopupLanguageDto
 * 
 * @version 01-00
 * @since 01-00
 * @author TranLTH
 */
public class PopupLanguageDto {
    private Long id;
    private String name;
    private String content;
    private String popupCode;
    private String languageCode;    
    /**
     * Get id
     * @return Long
     * @author TranLTH
     */
    public Long getId() {
        return id;
    }
    /**
     * Set id
     * @param   id
     *          type Long
     * @return
     * @author  TranLTH
     */
    public void setId(Long id) {
        this.id = id;
    }
    /**
     * Get name
     * @return String
     * @author TranLTH
     */
    public String getName() {
        return name;
    }
    /**
     * Set name
     * @param   name
     *          type String
     * @return
     * @author  TranLTH
     */
    public void setName(String name) {
        this.name = name;
    }
    /**
     * Get content
     * @return String
     * @author TranLTH
     */
    public String getContent() {
        return content;
    }
    /**
     * Set content
     * @param   content
     *          type String
     * @return
     * @author  TranLTH
     */
    public void setContent(String content) {
        this.content = content;
    }
    /**
     * Get popupCode
     * @return String
     * @author TranLTH
     */
    public String getPopupCode() {
        return popupCode;
    }
    /**
     * Set popupCode
     * @param   popupCode
     *          type String
     * @return
     * @author  TranLTH
     */
    public void setPopupCode(String popupCode) {
        this.popupCode = popupCode;
    }
    /**
     * Get languageCode
     * @return String
     * @author TranLTH
     */
    public String getLanguageCode() {
        return languageCode;
    }
    /**
     * Set languageCode
     * @param   languageCode
     *          type String
     * @return
     * @author  TranLTH
     */
    public void setLanguageCode(String languageCode) {
        this.languageCode = languageCode;
    }
}