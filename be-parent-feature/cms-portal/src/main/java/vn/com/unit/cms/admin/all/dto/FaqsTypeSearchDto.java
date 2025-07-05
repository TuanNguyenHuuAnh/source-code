/*******************************************************************************
 * Class        ：FaqsSearchDto
 * Created date ：2017/02/23
 * Lasted date  ：2017/02/23
 * Author       ：hand
 * Change log   ：2017/02/23：01-00 hand create a new
 ******************************************************************************/
package vn.com.unit.cms.admin.all.dto;

import java.util.Date;
import java.util.List;

import vn.com.unit.cms.core.utils.CmsUtils;

/**
 * FaqsSearchDto
 * 
 * @version 01-00
 * @since 01-00
 * @author hand
 */
public class FaqsTypeSearchDto {

    /** id */
    private String id;

    /** code */
    private String code;

    /** title */
    private String title;

    /** description */
    private String description;

    /** languageCode */
    private String languageCode;

    /** fieldValues */
    private List<String> fieldValues;

    /** fieldSearch */
    private String fieldSearch;

    /** createDate */
    private Date createDate;

    /** url */
    private String pageUrl;

    private Long customerId;

    private Long sort;

    private String name;

    private String status;

    private String createBy;

    private String keyWord;

    private String keyWordDescribe;

    private Integer numberCategory;

    private Integer pageSize;

    private Integer enabled;

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
     * Get createDate
     * 
     * @return Date
     * @author hand
     */
    public Date getCreateDate() {
        return createDate;
    }

    /**
     * Set createDate
     * 
     * @param createDate
     *            type Date
     * @return
     * @author hand
     */
    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    /**
     * Get pageUrl
     * 
     * @return String
     * @author hand
     */
    public String getPageUrl() {
        return pageUrl;
    }

    /**
     * Set pageUrl
     * 
     * @param pageUrl
     *            type String
     * @return
     * @author hand
     */
    public void setPageUrl(String pageUrl) {
        this.pageUrl = pageUrl;
    }

    /**
     * Get id
     * 
     * @return String
     * @author hand
     */
    public String getId() {
        return id;
    }

    /**
     * Set id
     * 
     * @param id
     *            type String
     * @return
     * @author hand
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Get title
     * 
     * @return String
     * @author hand
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
     * @author hand
     */
    public void setTitle(String title) {
        this.title = title;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public Long getSort() {
        return sort;
    }

    public void setSort(Long sort) {
        this.sort = sort;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public String getKeyWord() {
        return keyWord;
    }

    public void setKeyWord(String keyWord) {
        this.keyWord = keyWord;
    }

    public String getKeyWordDescribe() {
        return keyWordDescribe;
    }

    public void setKeyWordDescribe(String keyWordDescribe) {
        this.keyWordDescribe = keyWordDescribe;
    }

    public Integer getNumberCategory() {
        return numberCategory;
    }

    public void setNumberCategory(Integer numberCategory) {
        this.numberCategory = numberCategory;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    /**
     * Get enabled
     * 
     * @return Boolean
     * @author taitm
     */
    public Integer getEnabled() {
        return enabled;
    }

    /**
     * Set enabled
     * 
     * @param enabled
     *            type Boolean
     * @return
     * @author taitm
     */
    public void setEnabled(Integer enabled) {
        this.enabled = enabled;
    }

}
