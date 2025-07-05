/*******************************************************************************
 * Class        BranchSearchDto
 * Created date 2017/03/22
 * Lasted date  2017/03/22
 * Author       TranLTH
 * Change log   2017/03/2201-00 TranLTH create a new
 ******************************************************************************/
package vn.com.unit.cms.admin.all.jcanary.dto;

import java.util.List;

/**
 * BranchSearchDto
 * 
 * @version 01-00
 * @since 01-00
 * @author TranLTH
 */
public class BranchSearchDto {
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

    /** address */
    private String address;
    
    /** phone */
    private String phone;
    
    /** url */
    private String url;
    
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
     * Get address
     * @return String
     * @author TranLTH
     */
    public String getAddress() {
        return address;
    }

    /**
     * Set address
     * @param   address
     *          type String
     * @return
     * @author  TranLTH
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * Get phone
     * @return String
     * @author TranLTH
     */
    public String getPhone() {
        return phone;
    }

    /**
     * Set phone
     * @param   phone
     *          type String
     * @return
     * @author  TranLTH
     */
    public void setPhone(String phone) {
        this.phone = phone;
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

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

}
