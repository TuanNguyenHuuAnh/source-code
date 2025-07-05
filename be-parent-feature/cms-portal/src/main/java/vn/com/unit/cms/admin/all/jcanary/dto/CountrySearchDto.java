/*******************************************************************************
 * Class        CountrySearchDto
 * Created date 2017/03/22
 * Lasted date  2017/03/22
 * Author       TranLTH
 * Change log   2017/03/2201-00 TranLTH create a new
 ******************************************************************************/
package vn.com.unit.cms.admin.all.jcanary.dto;

import java.util.List;

/**
 * CountrySearchDto
 * 
 * @version 01-00
 * @since 01-00
 * @author TranLTH
 */
public class CountrySearchDto {
    /** fieldSearch */
    private String fieldSearch;

    /** languageCode */
    private String languageCode;

    /** fieldValues */
    private List<String> fieldValues;
    
    /** code */
    private String code;

    /** name */
    private String name;

    /** note */
    private String note;
    
    /** local name */
    private String localName;
    
    /** web code */ 
    private String webCode;
    
    /** phone code */
    private String phoneCode;
    
    /** url */
    private String url;
    
    private String description;
    
    private Integer pageSize;

    /**
     * Get fieldSearch
     * @return String
     * @author TranLTH
     */
    public String getFieldSearch() {
        return fieldSearch;
    }

    /**
     * Set fieldSearch
     * @param   fieldSearch
     *          type String
     * @return
     * @author  TranLTH
     */
    public void setFieldSearch(String fieldSearch) {
        this.fieldSearch = fieldSearch;
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
     * Get fieldValues
     * @return List<String>
     * @author TranLTH
     */
    public List<String> getFieldValues() {
        return fieldValues;
    }

    /**
     * Set fieldValues
     * @param   fieldValues
     *          type List<String>
     * @return
     * @author  TranLTH
     */
    public void setFieldValues(List<String> fieldValues) {
        this.fieldValues = fieldValues;
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
     * Get note
     * @return String
     * @author TranLTH
     */
    public String getNote() {
        return note;
    }

    /**
     * Set note
     * @param   note
     *          type String
     * @return
     * @author  TranLTH
     */
    public void setNote(String note) {
        this.note = note;
    }

    /**
     * Get localName
     * @return String
     * @author TranLTH
     */
    public String getLocalName() {
        return localName;
    }

    /**
     * Set localName
     * @param   localName
     *          type String
     * @return
     * @author  TranLTH
     */
    public void setLocalName(String localName) {
        this.localName = localName;
    }

    /**
     * Get webCode
     * @return String
     * @author TranLTH
     */
    public String getWebCode() {
        return webCode;
    }

    /**
     * Set webCode
     * @param   webCode
     *          type String
     * @return
     * @author  TranLTH
     */
    public void setWebCode(String webCode) {
        this.webCode = webCode;
    }

    /**
     * Get phoneCode
     * @return String
     * @author TranLTH
     */
    public String getPhoneCode() {
        return phoneCode;
    }

    /**
     * Set phoneCode
     * @param   phoneCode
     *          type String
     * @return
     * @author  TranLTH
     */
    public void setPhoneCode(String phoneCode) {
        this.phoneCode = phoneCode;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }
       
}
