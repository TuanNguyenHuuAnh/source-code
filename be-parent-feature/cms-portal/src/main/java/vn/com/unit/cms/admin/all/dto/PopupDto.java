/*******************************************************************************
 * Class        ：PopupDto
 * Created date ：2017/10/12
 * Lasted date  ：2017/10/12
 * Author       ：TranLTH
 * Change log   ：2017/10/12：01-00 TranLTH create a new
 ******************************************************************************/
package vn.com.unit.cms.admin.all.dto;

import java.util.Date;
import java.util.List;

import javax.validation.Valid;

/**
 * PopupDto
 * 
 * @version 01-00
 * @since 01-00
 * @author TranLTH
 */
public class PopupDto {
    private Long id;    
    private String code;
    private String name;   
    private String type;
    private String typeName;
    private String content;        
    private Date effectiveDate;  
    private Date expiryDate;
    private Boolean active;        
    private String url;
    private String requestToken;
    private Date createDate;
    private String positionDisplay;
    private String pageDisplay;
    private String languageCode;
    @Valid
    private List<PopupLanguageDto> popupLanguageDtos;
   
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
     * Get code
     * @return String
     * @author TranLTH
     */
    public String getCode() {
        return code;
    }
    /**
     * Set code
     * @param   code
     *          type String
     * @return
     * @author  TranLTH
     */
    public void setCode(String code) {
        this.code = code;
    }
    /**
     * Get type
     * @return String
     * @author TranLTH
     */
    public String getType() {
        return type;
    }
    /**
     * Set type
     * @param   type
     *          type String
     * @return
     * @author  TranLTH
     */
    public void setType(String type) {
        this.type = type;
    }
    /**
     * Get typeName
     * @return String
     * @author TranLTH
     */
    public String getTypeName() {
        return typeName;
    }
    /**
     * Set typeName
     * @param   typeName
     *          type String
     * @return
     * @author  TranLTH
     */
    public void setTypeName(String typeName) {
        this.typeName = typeName;
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
     * Get effectiveDate
     * @return Date
     * @author TranLTH
     */
    public Date getEffectiveDate() {
        return effectiveDate;
    }
    /**
     * Set effectiveDate
     * @param   effectiveDate
     *          type Date
     * @return
     * @author  TranLTH
     */
    public void setEffectiveDate(Date effectiveDate) {
        this.effectiveDate = effectiveDate;
    }
    /**
     * Get active
     * @return Boolean
     * @author TranLTH
     */
    public Boolean getActive() {
        return active;
    }
    /**
     * Set active
     * @param   active
     *          type Boolean
     * @return
     * @author  TranLTH
     */
    public void setActive(Boolean active) {
        this.active = active;
    }
    /**
     * Get url
     * @return String
     * @author TranLTH
     */
    public String getUrl() {
        return url;
    }
    /**
     * Set url
     * @param   url
     *          type String
     * @return
     * @author  TranLTH
     */
    public void setUrl(String url) {
        this.url = url;
    }
    /**
     * Get requestToken
     * @return String
     * @author TranLTH
     */
    public String getRequestToken() {
        return requestToken;
    }
    /**
     * Set requestToken
     * @param   requestToken
     *          type String
     * @return
     * @author  TranLTH
     */
    public void setRequestToken(String requestToken) {
        this.requestToken = requestToken;
    }
    /**
     * Get createDate
     * @return Date
     * @author TranLTH
     */
    public Date getCreateDate() {
        return createDate;
    }
    /**
     * Set createDate
     * @param   createDate
     *          type Date
     * @return
     * @author  TranLTH
     */
    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }
    /**
     * Get expiryDate
     * @return Date
     * @author TranLTH
     */
    public Date getExpiryDate() {
        return expiryDate;
    }
    /**
     * Set expiryDate
     * @param   expiryDate
     *          type Date
     * @return
     * @author  TranLTH
     */
    public void setExpiryDate(Date expiryDate) {
        this.expiryDate = expiryDate;
    }
    /**
     * Get positionDisplay
     * @return String
     * @author TranLTH
     */
    public String getPositionDisplay() {
        return positionDisplay;
    }
    /**
     * Set positionDisplay
     * @param   positionDisplay
     *          type String
     * @return
     * @author  TranLTH
     */
    public void setPositionDisplay(String positionDisplay) {
        this.positionDisplay = positionDisplay;
    }
    /**
     * Get pageDisplay
     * @return String
     * @author TranLTH
     */
    public String getPageDisplay() {
        return pageDisplay;
    }
    /**
     * Set pageDisplay
     * @param   pageDisplay
     *          type String
     * @return
     * @author  TranLTH
     */
    public void setPageDisplay(String pageDisplay) {
        this.pageDisplay = pageDisplay;
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
    /**
     * Get popupLanguageDtos
     * @return List<PopupLanguageDto>
     * @author TranLTH
     */
    public List<PopupLanguageDto> getPopupLanguageDtos() {
        return popupLanguageDtos;
    }
    /**
     * Set popupLanguageDtos
     * @param   popupLanguageDtos
     *          type List<PopupLanguageDto>
     * @return
     * @author  TranLTH
     */
    public void setPopupLanguageDtos(List<PopupLanguageDto> popupLanguageDtos) {
        this.popupLanguageDtos = popupLanguageDtos;
    }
}