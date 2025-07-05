/*******************************************************************************
 * Class        ：ProductCategoryLanguageSearchDto
 * Created date ：2017/05/03
 * Lasted date  ：2017/05/03
 * Author       ：hand
 * Change log   ：2017/05/03：01-00 hand create a new
 ******************************************************************************/
package vn.com.unit.cms.admin.all.dto;

import java.util.Date;

/**
 * ProductCategoryLanguageSearchDto
 * 
 * @version 01-00
 * @since 01-00
 * @author hand
 */
public class ProductCategorySubLanguageSearchDto {

    /** id */
    private Long id;

    /** code */
    private String code;

    /** name */
    private String name;

    /** title */
    private String title;

    /** sort */
    private Long sort;

    /** enabled */
    private int enabled;

    /** typeName */
    private String typeName;

    /** description */
    private String description;

    /** createDate */
    private Date createDate;
    
    private String createBy;
    
    private Integer numberProduct;
    
    private String categoryTitle;

    /** approvedBy */
    private String approvedBy;

    /** approvedDate */
    private Date approvedDate;

    /** publishedBy */
    private String publishedBy;

    /** publishedDate */
    private Date publishedDate;
    
    private Integer status;
    
    private Integer numberFaqs;
    
    private boolean priority;
    
    private String statusName;
    
    /**
     * Get id
     * 
     * @return Long
     * @author hand
     */
    public Long getId() {
        return id;
    }

    /**
     * Set id
     * 
     * @param id
     *            type Long
     * @return
     * @author hand
     */
    public void setId(Long id) {
        this.id = id;
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
        this.title = title;
    }

    /**
     * Get sort
     * 
     * @return Long
     * @author hand
     */
    public Long getSort() {
        return sort;
    }

    /**
     * Set sort
     * 
     * @param sort
     *            type Long
     * @return
     * @author hand
     */
    public void setSort(Long sort) {
        this.sort = sort;
    }

    /**
     * Get enabled
     * 
     * @return int
     * @author hand
     */
    public int getEnabled() {
        return enabled;
    }

    /**
     * Set enabled
     * 
     * @param enabled
     *            type int
     * @return
     * @author hand
     */
    public void setEnabled(int enabled) {
        this.enabled = enabled;
    }

    /**
     * Get typeName
     * 
     * @return String
     * @author hand
     */
    public String getTypeName() {
        return typeName;
    }

    /**
     * Set typeName
     * 
     * @param typeName
     *            type String
     * @return
     * @author hand
     */
    public void setTypeName(String typeName) {
        this.typeName = typeName;
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

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public Integer getNumberProduct() {
        return numberProduct;
    }

    public void setNumberProduct(Integer numberProduct) {
        this.numberProduct = numberProduct;
    }

    public String getCategoryTitle() {
        return categoryTitle;
    }

    public void setCategoryTitle(String categoryTitle) {
        this.categoryTitle = categoryTitle;
    }

    public String getApprovedBy() {
        return approvedBy;
    }

    public void setApprovedBy(String approvedBy) {
        this.approvedBy = approvedBy;
    }

    public Date getApprovedDate() {
        return approvedDate;
    }

    public void setApprovedDate(Date approvedDate) {
        this.approvedDate = approvedDate;
    }

    public String getPublishedBy() {
        return publishedBy;
    }

    public void setPublishedBy(String publishedBy) {
        this.publishedBy = publishedBy;
    }

    public Date getPublishedDate() {
        return publishedDate;
    }

    public void setPublishedDate(Date publishedDate) {
        this.publishedDate = publishedDate;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    /**
     * Get numberFaqs
     * 
     * @return Integer
     * @author taitm
     */
    public Integer getNumberFaqs() {
        return numberFaqs;
    }

    /**
     * Set numberFaqs
     * 
     * @param numberFaqs
     *            type Integer
     * @return
     * @author taitm
     */
    public void setNumberFaqs(Integer numberFaqs) {
        this.numberFaqs = numberFaqs;
    }

    /**
     * Get priority
     * 
     * @return boolean
     * @author taitm
     */
    public boolean isPriority() {
        return priority;
    }

    /**
     * Set priority
     * 
     * @param priority
     *            type boolean
     * @return
     * @author taitm
     */
    public void setPriority(boolean priority) {
        this.priority = priority;
    }

	/**
	 * @return the statusName
	 */
	public String getStatusName() {
		return statusName;
	}

	/**
	 * @param statusName the statusName to set
	 */
	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}

    
}
