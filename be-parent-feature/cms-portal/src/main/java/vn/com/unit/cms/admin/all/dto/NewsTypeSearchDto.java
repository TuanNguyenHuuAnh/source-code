/*******************************************************************************
 * Class        ：NewsTypeSearchDto
 * Created date ：2017/02/27
 * Lasted date  ：2017/02/27
 * Author       ：hand
 * Change log   ：2017/02/27：01-00 hand create a new
 ******************************************************************************/
package vn.com.unit.cms.admin.all.dto;

import java.util.List;

import vn.com.unit.cms.core.utils.CmsUtils;

//import vn.com.unit.util.Util;

/**
 * NewsTypeSearchDto
 * 
 * @version 01-00
 * @since 01-00
 * @author hand
 */
public class NewsTypeSearchDto {

    /** code */
    private String code;

    /** description */
    private String description;

    /** label */
    private String label;

    /** languageCode */
    private String languageCode;

    /** fieldValues */
    private List<String> fieldValues;

    /** fieldSearch */
    private String fieldSearch;

    /** url */
    private String url;

    /** customerTypeName */
    private String customerTypeName;

    private Integer pageSize;

    private Long customerTypeId;

    private String status;

    private Integer statusActive;
    
    private String statusName;
    
    private Integer enabled;
    
    private Integer typeOfLibrary;
    
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
     * Set languageCode
     * 
     * @param languageCode
     *            type String
     * @return
     * @author hand
     */
    public void setLanguageCode(String languageCode) {
        this.languageCode = languageCode;
    }

    /**
     * Get code
     * 
     * @return String
     * @author hand
     */
    public String getCode() {
        return code;
    }

    /**
     * Set code
     * 
     * @param code
     *            type String
     * @return
     * @author hand
     */
    public void setCode(String code) {
        this.code = code;
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
     * Get fieldValues
     * 
     * @return List<String>
     * @author hand
     */
    public List<String> getFieldValues() {
        return fieldValues;
    }

    /**
     * Set fieldValues
     * 
     * @param fieldValues
     *            type List<String>
     * @return
     * @author hand
     */
    public void setFieldValues(List<String> fieldValues) {
        this.fieldValues = fieldValues;
    }

    /**
     * Get fieldSearch
     * 
     * @return String
     * @author hand
     */
    public String getFieldSearch() {
        return fieldSearch;
    }

    /**
     * Set fieldSearch
     * 
     * @param fieldSearch
     *            type String
     * @return
     * @author hand
     */
    public void setFieldSearch(String fieldSearch) {
        this.fieldSearch = CmsUtils.trimForSearch(fieldSearch);
    }

    /**
     * Get url
     * 
     * @return String
     * @author hand
     */
    public String getUrl() {
        return url;
    }

    /**
     * Set url
     * 
     * @param url
     *            type String
     * @return
     * @author hand
     */
    public void setUrl(String url) {
        this.url = url;
    }

    /**
     * Get description
     * 
     * @return String
     * @author hand
     */
    public String getDescription() {
        return description;
    }

    /**
     * Set description
     * 
     * @param description
     *            type String
     * @return
     * @author hand
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Get customerTypeName
     * 
     * @return String
     * @author hand
     */
    public String getCustomerTypeName() {
        return customerTypeName;
    }

    /**
     * Set customerTypeName
     * 
     * @param customerTypeName
     *            type String
     * @return
     * @author hand
     */
    public void setCustomerTypeName(String customerTypeName) {
        this.customerTypeName = customerTypeName;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Long getCustomerTypeId() {
        return customerTypeId;
    }

    public void setCustomerTypeId(Long customerTypeId) {
        this.customerTypeId = customerTypeId;
    }

    /**
     * Get status
     * 
     * @return String
     * @author taitm
     */
    public String getStatus() {
        return status;
    }

    /**
     * Set status
     * 
     * @param status
     *            type String
     * @return
     * @author taitm
     */
    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getStatusActive() {
        return statusActive;
    }

    public void setStatusActive(Integer statusActive) {
        this.statusActive = statusActive;
    }

    /**
     * @return the statusName
     * @author taitm
     */
    public String getStatusName() {
        return statusName;
    }

    /**
     * @param statusName
     *            the statusName to set
     * @author taitm
     */
    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }

    /**
     * @return the enabled
     * @author taitm
     */
    public Integer getEnabled() {
        return enabled;
    }

    /**
     * @param enabled
     *            the enabled to set
     * @author taitm
     */
    public void setEnabled(Integer enabled) {
        this.enabled = enabled;
    }

    /**
     * @return the typeOfLibrary
     * @author taitm
     */
    public Integer getTypeOfLibrary() {
        return typeOfLibrary;
    }

    /**
     * @param typeOfLibrary
     *            the typeOfLibrary to set
     * @author taitm
     */
    public void setTypeOfLibrary(Integer typeOfLibrary) {
        this.typeOfLibrary = typeOfLibrary;
    }
}
