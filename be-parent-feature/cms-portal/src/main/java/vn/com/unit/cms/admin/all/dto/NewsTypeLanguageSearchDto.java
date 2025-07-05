/*******************************************************************************
 * Class        ：NewsTypeLanguageSearchDto
 * Created date ：2017/02/28
 * Lasted date  ：2017/02/28
 * Author       ：hand
 * Change log   ：2017/02/28：01-00 hand create a new
 ******************************************************************************/
package vn.com.unit.cms.admin.all.dto;

import java.util.Date;

/**
 * NewsTypeLanguageSearchDto
 * 
 * @version 01-00
 * @since 01-00
 * @author hand
 */
public class NewsTypeLanguageSearchDto {

    /** id */
    private Long id;

    /** code */
    private String code;
    
    /** name */
    private String name;

    /** label */
    private String label;
    
    /** sort */
    private Long sort;

    /** enabled */
    private int enabled;
    
    /** description*/
    private String description;

    /** createDate*/
    private Date createDate;
    
    /** customerTypeName*/
    private String customerTypeName;
    
    private Long customerId;
    
    private int numberNews;
    
    private int numberNewsCategory;
    
    private Integer status;
    
    private String createBy;
    
    private Integer typeOfLibrary;
    
    private String statusName;
    
    /**
     * Get id
     * @return Long
     * @author hand
     */
    public Long getId() {
        return id;
    }

    /**
     * Set id
     * @param   id
     *          type Long
     * @return
     * @author  hand
     */
    public void setId(Long id) {
        this.id = id;
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
     * Get name
     * @return String
     * @author hand
     */
    public String getName() {
        return name;
    }

    /**
     * Set name
     * @param   name
     *          type String
     * @return
     * @author  hand
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Get label
     * @return String
     * @author hand
     */
    public String getLabel() {
        return label;
    }

    /**
     * Set label
     * @param   label
     *          type String
     * @return
     * @author  hand
     */
    public void setLabel(String label) {
        this.label = label;
    }

    /**
     * Get sort
     * @return Long
     * @author hand
     */
    public Long getSort() {
        return sort;
    }

    /**
     * Set sort
     * @param   sort
     *          type Long
     * @return
     * @author  hand
     */
    public void setSort(Long sort) {
        this.sort = sort;
    }

    /**
     * Get enabled
     * @return int
     * @author hand
     */
    public int getEnabled() {
        return enabled;
    }

    /**
     * Set enabled
     * @param   enabled
     *          type int
     * @return
     * @author  hand
     */
    public void setEnabled(int enabled) {
        this.enabled = enabled;
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

    /**
     * Get createDate
     * @return Date
     * @author hand
     */
    public Date getCreateDate() {
        return createDate;
    }

    /**
     * Set createDate
     * @param   createDate
     *          type Date
     * @return
     * @author  hand
     */
    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    /**
     * Get customerTypeName
     * @return String
     * @author hand
     */
    public String getCustomerTypeName() {
        return customerTypeName;
    }

    /**
     * Set customerTypeName
     * @param   customerTypeName
     *          type String
     * @return
     * @author  hand
     */
    public void setCustomerTypeName(String customerTypeName) {
        this.customerTypeName = customerTypeName;
    }

    /**
     * Get customerId
     * 
     * @return Long
     * @author taitm
     */
    public Long getCustomerId() {
        return customerId;
    }

    /**
     * Set customerId
     * 
     * @param customerId
     *            type Long
     * @return
     * @author taitm
     */
    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    /**
     * Get numberNews
     * 
     * @return int
     * @author taitm
     */
    public int getNumberNews() {
        return numberNews;
    }

    /**
     * Set numberNews
     * 
     * @param numberNews
     *            type int
     * @return
     * @author taitm
     */
    public void setNumberNews(int numberNews) {
        this.numberNews = numberNews;
    }

    /**
     * Get numberNewsCategory
     * 
     * @return int
     * @author taitm
     */
    public int getNumberNewsCategory() {
        return numberNewsCategory;
    }

    /**
     * Set numberNewsCategory
     * 
     * @param numberNewsCategory
     *            type int
     * @return
     * @author taitm
     */
    public void setNumberNewsCategory(int numberNewsCategory) {
        this.numberNewsCategory = numberNewsCategory;
    }

    /**
     * Get status
     * 
     * @return String
     * @author taitm
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
     * @author taitm
     */
    public void setStatus(Integer status) {
        this.status = status;
    }

    /**
     * Get createBy
     * 
     * @return String
     * @author taitm
     */
    public String getCreateBy() {
        return createBy;
    }

    /**
     * Set createBy
     * 
     * @param createBy
     *            type String
     * @return
     * @author taitm
     */
    public void setCreateBy(String createBy) {
        this.createBy = createBy;
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

    /**
     * @return the statusName
     * @author taitm
     */
    public String getStatusName() {
        return statusName;
    }

    /**
     * @param statusName the statusName to set
     * @author taitm
     */
    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }
    

}
