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
public class ProductCategoryLanguageSearchDto {

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
	private Integer enabled;

	/** typeName */
	private String typeName;

	/** description */
	private String description;

	/** createDate */
	private Date createDate;

	/** processId */
	private Long processId;

	/** status */
	private Integer status;

	/** status */
	private String statusName;

	/** customerId */
	private Long customerId;

	/** linkAlias */
	private String linkAlias;

	private String createBy;

	private String approveBy;

	private Date approveDate;

	private String publishBy;

	private Date publishDate;

	private Integer numberProduct;

	private Integer numberFaqs;

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
	 * @param id type Long
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
	 * @param code type String
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
	 * @param name type String
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
	 * @param title type String
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
	 * @param sort type Long
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
	public Integer getEnabled() {
		return enabled;
	}

	/**
	 * Set enabled
	 * 
	 * @param enabled type int
	 * @return
	 * @author hand
	 */
	public void setEnabled(Integer enabled) {
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
	 * @param typeName type String
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
	 * @param description type String
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
	 * @param createDate type Date
	 * @return
	 * @author hand
	 */
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	/**
	 * Get processId
	 * 
	 * @return Long
	 * @author hand
	 */
	public Long getProcessId() {
		return processId;
	}

	/**
	 * Set processId
	 * 
	 * @param processId type Long
	 * @return
	 * @author hand
	 */
	public void setProcessId(Long processId) {
		this.processId = processId;
	}

	/**
	 * Get status
	 * 
	 * @return String
	 * @author hand
	 */
	public Integer getStatus() {
		return status;
	}

	/**
	 * Set status
	 * 
	 * @param status type String
	 * @return
	 * @author hand
	 */
	public void setStatus(Integer status) {
		this.status = status;
	}

	/**
	 * Get statusName
	 * 
	 * @return String
	 * @author hand
	 */
	public String getStatusName() {
		return statusName;
	}

	/**
	 * Set statusName
	 * 
	 * @param statusName type String
	 * @return
	 * @author hand
	 */
	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}

	/**
	 * Get customerId
	 * 
	 * @return Long
	 * @author hand
	 */
	public Long getCustomerId() {
		return customerId;
	}

	/**
	 * Set customerId
	 * 
	 * @param customerId type Long
	 * @return
	 * @author hand
	 */
	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}

	/**
	 * @return the linkAlias
	 */
	public String getLinkAlias() {
		return linkAlias;
	}

	/**
	 * @param linkAlias the linkAlias to set
	 */
	public void setLinkAlias(String linkAlias) {
		this.linkAlias = linkAlias;
	}

	public String getCreateBy() {
		return createBy;
	}

	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}

	public String getApproveBy() {
		return approveBy;
	}

	public void setApproveBy(String approveBy) {
		this.approveBy = approveBy;
	}

	public Date getApproveDate() {
		return approveDate;
	}

	public void setApproveDate(Date approveDate) {
		this.approveDate = approveDate;
	}

	public String getPublishBy() {
		return publishBy;
	}

	public void setPublishBy(String publishBy) {
		this.publishBy = publishBy;
	}

	public Date getPublishDate() {
		return publishDate;
	}

	public void setPublishDate(Date publishDate) {
		this.publishDate = publishDate;
	}

	public Integer getNumberProduct() {
		return numberProduct;
	}

	public void setNumberProduct(Integer numberProduct) {
		this.numberProduct = numberProduct;
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
	 * @param numberFaqs type Integer
	 * @return
	 * @author taitm
	 */
	public void setNumberFaqs(Integer numberFaqs) {
		this.numberFaqs = numberFaqs;
	}

}
