/*******************************************************************************
 * Class        ：ProductSearchDto
 * Created date ：2017/05/04
 * Lasted date  ：2017/05/04
 * Author       ：hand
 * Change log   ：2017/05/04：01-00 hand create a new
 ******************************************************************************/
package vn.com.unit.cms.admin.all.dto;

import java.util.List;

import vn.com.unit.cms.core.utils.CmsUtils;

//import vn.com.unit.util.Util;

/**
 * ProductSearchDto
 * 
 * @version 01-00
 * @since 01-00
 * @author hand
 */
public class ProductSearchDto {

    /** code */
    private String code;

    /** name */
    private String name;

    /** title */
    private String title;

    /** languageCode */
    private String languageCode;

    /** customerTypeName */
    private String customerTypeName;

    /** categoryName */
    private String categoryName;

    /** fieldValues */
    private List<String> fieldValues;

    /** fieldSearch */
    private String fieldSearch;

    /** url */
    private String url;

    /** description */
    private String description;

    /** id */
    private Long id;

    /** typeId */
    private Long typeId;

    /** categoryId */
    private Long categoryId;

    /** category sub id */
    private Long categorySubId;

    /** status */
    private Integer status;

    private Integer pageSize;

    private Integer microsite;

    private Integer enabled;

    private Integer highlights;

    private Integer lending;

    private Integer priority;

    private Integer showForm;
    
    private String statusName;

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
        this.code = CmsUtils.trimForSearch(code);
    }

    /**
     * Get name
     * 
     * @return String
     * @author hand
     */
    public String getName() {
        return name;
    }

    /**
     * Set name
     * 
     * @param name
     *            type String
     * @return
     * @author hand
     */
    public void setName(String name) {
        this.name = name;
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
        this.title = CmsUtils.trimForSearch(title);
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

    /**
     * Get categoryName
     * 
     * @return String
     * @author hand
     */
    public String getCategoryName() {
        return categoryName;
    }

    /**
     * Set categoryName
     * 
     * @param categoryName
     *            type String
     * @return
     * @author hand
     */
    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
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
     * Get typeId
     * 
     * @return Long
     * @author hand
     */
    public Long getTypeId() {
        return typeId;
    }

    /**
     * Get categoryId
     * 
     * @return Long
     * @author hand
     */
    public Long getCategoryId() {
        return categoryId;
    }

    /**
     * Set typeId
     * 
     * @param typeId
     *            type Long
     * @return
     * @author hand
     */
    public void setTypeId(Long typeId) {
        this.typeId = typeId;
    }

    /**
     * Set categoryId
     * 
     * @param categoryId
     *            type Long
     * @return
     * @author hand
     */
    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    /**
     * Get categorySubId
     * 
     * @return Long
     * @author hand
     */
    public Long getCategorySubId() {
        return categorySubId;
    }

    /**
     * Set categorySubId
     * 
     * @param categorySubId
     *            type Long
     * @return
     * @author hand
     */
    public void setCategorySubId(Long categorySubId) {
        this.categorySubId = categorySubId;
    }

    /**
     * Get status
     * 
     * @return Integer
     * @author hand
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * Set status
     * 
     * @param status
     *            type String
     * @return
     * @author hand
     */
    public void setStatus(Integer status) {
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Integer isMicrosite() {
        return microsite;
    }

    public void setMicrosite(Integer microsite) {
        this.microsite = microsite;
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

    /**
     * Get highlights
     * 
     * @return Boolean
     * @author taitm
     */
    public Integer getHighlights() {
        return highlights;
    }

    /**
     * Set highlights
     * 
     * @param highlights
     *            type Boolean
     * @return
     * @author taitm
     */
    public void setHighlights(Integer highlights) {
        this.highlights = highlights;
    }

    /**
     * Get lending
     * 
     * @return Boolean
     * @author taitm
     */
    public Integer getLending() {
        return lending;
    }

    /**
     * Set lending
     * 
     * @param lending
     *            type Boolean
     * @return
     * @author taitm
     */
    public void setLending(Integer lending) {
        this.lending = lending;
    }

    /**
     * Get priority
     * 
     * @return Boolean
     * @author taitm
     */
    public Integer getPriority() {
        return priority;
    }

    /**
     * Set priority
     * 
     * @param priority
     *            type Boolean
     * @return
     * @author taitm
     */
    public void setPriority(Integer priority) {
        this.priority = priority;
    }

    /**
     * Get showForm
     * 
     * @return Boolean
     * @author taitm
     */
    public Integer getShowForm() {
        return showForm;
    }

    /**
     * Set showForm
     * 
     * @param showForm
     *            type Boolean
     * @return
     * @author taitm
     */
    public void setShowForm(Integer showForm) {
        this.showForm = showForm;
    }

    /**
     * Get microsite
     * 
     * @return Boolean
     * @author taitm
     */
    public Integer getMicrosite() {
        return microsite;
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

}
