/*******************************************************************************
 * Class        ：PopupLanguage
 * Created date ：2017/11/01
 * Lasted date  ：2017/11/01
 * Author       ：TranLTH
 * Change log   ：2017/11/01：01-00 TranLTH create a new
 ******************************************************************************/
package vn.com.unit.cms.admin.all.entity;

import org.springframework.data.annotation.Id;

import jp.sf.amateras.mirage.annotation.Column;
import jp.sf.amateras.mirage.annotation.PrimaryKey;
import jp.sf.amateras.mirage.annotation.Table;
import jp.sf.amateras.mirage.annotation.PrimaryKey.GenerationType;
import vn.com.unit.cms.core.entity.AbstractTracking;

/**
 * PopupLanguage
 * 
 * @version 01-00
 * @since 01-00
 * @author TranLTH
 */
@Table(name = "m_popup_language")
public class PopupLanguage extends AbstractTracking {
    @Id
    @Column(name = "id")
    @PrimaryKey(generationType = GenerationType.SEQUENCE, generator = "SEQ_M_POPUP_LANGUAGE")
    private Long id;
    
    @Column(name = "m_popup_code")
    private String popupCode;
    
    @Column(name = "name")
    private String name;

    @Column(name = "content")
    private String content;
    
    @Column(name = "m_language_code")
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