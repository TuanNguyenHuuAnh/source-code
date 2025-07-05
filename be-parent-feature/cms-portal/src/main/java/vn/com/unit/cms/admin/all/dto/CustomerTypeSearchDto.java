/*******************************************************************************
 * Class        ：CustomerTypeSearchDto
 * Created date ：2017/05/03
 * Lasted date  ：2017/05/03
 * Author       ：hand
 * Change log   ：2017/05/03：01-00 hand create a new
 ******************************************************************************/
package vn.com.unit.cms.admin.all.dto;

import java.util.List;

import vn.com.unit.cms.core.utils.CmsUtils;

//import vn.com.unit.util.Util;

/**
 * CustomerTypeSearchDto
 * 
 * @version 01-00
 * @since 01-00
 * @author hand
 */
public class CustomerTypeSearchDto {

    /** code */
    private String code;

    /** description */
    private String description;

    /** title */
    private String title;

    /** languageCode*/
    private String languageCode;
    
    /** fieldValues */
    private List<String> fieldValues;
    
    /** fieldSearch */
    private String fieldSearch;
    
    /** url*/
    private String url;

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
     * Get code
     * @return String
     * @author hand
     */
    public String getCode() {
        return code;
    }

    /**
     * Set code
     * @param   code
     *          type String
     * @return
     * @author  hand
     */
    public void setCode(String code) {
        this.code = code;
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

    /**
     * Get fieldValues
     * @return List<String>
     * @author hand
     */
    public List<String> getFieldValues() {
        return fieldValues;
    }

    /**
     * Set fieldValues
     * @param   fieldValues
     *          type List<String>
     * @return
     * @author  hand
     */
    public void setFieldValues(List<String> fieldValues) {
        this.fieldValues = fieldValues;
    }

    /**
     * Get fieldSearch
     * @return String
     * @author hand
     */
    public String getFieldSearch() {
        return fieldSearch;
    }

    /**
     * Set fieldSearch
     * @param   fieldSearch
     *          type String
     * @return
     * @author  hand
     */
    public void setFieldSearch(String fieldSearch) {
        this.fieldSearch = CmsUtils.trimForSearch(fieldSearch);
    }

    /**
     * Get url
     * @return String
     * @author hand
     */
    public String getUrl() {
        return url;
    }

    /**
     * Set url
     * @param   url
     *          type String
     * @return
     * @author  hand
     */
    public void setUrl(String url) {
        this.url = url;
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

}
