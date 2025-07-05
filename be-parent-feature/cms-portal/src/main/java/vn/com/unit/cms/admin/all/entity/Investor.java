package vn.com.unit.cms.admin.all.entity;

import java.util.Date;

import org.springframework.data.annotation.Id;

import jp.sf.amateras.mirage.annotation.Column;
import jp.sf.amateras.mirage.annotation.PrimaryKey;
import jp.sf.amateras.mirage.annotation.Table;
import jp.sf.amateras.mirage.annotation.PrimaryKey.GenerationType;

@Table(name = "m_investor")
public class Investor {
	@Id
	@Column(name = "id")
	@PrimaryKey(generationType = GenerationType.SEQUENCE, generator = "SEQ_M_INVESTOR")

	private Long id;
	@Column(name = "m_investor_category_id")
	private Long categoryId;

	@Column(name = "code")
	private String code;

	@Column(name = "image_url")
	private String physicalImg;

	@Column(name = "image_name")
	private String imageName;

	@Column(name = "enabled")
	private boolean enabled;

	@Column(name = "sort")
	private Long sort;

	@Column(name = "before_id")
	private Long beforeId;;

	@Column(name = "process_id")
	private Long processId;

	@Column(name = "status")
	private Integer status;

	@Column(name = "create_by")
	private String createBy;

	@Column(name = "update_by")
	private String updateBy;

	@Column(name = "approved_by")
	private String approvedBy;

	@Column(name = "published_by")
	private String publishedBy;

	@Column(name = "delete_by")
	private String deleteBy;

	@Column(name = "create_date")
	private Date createDate;

	@Column(name = "update_date")
	private Date updateDate;

	@Column(name = "delete_date")
	private Date deleteDate;

	@Column(name = "customer_type_id")
	private Long customerTypeId;

	@Column(name = "kind")
	private Long kind;

	@Column(name = "hot_news")
	private Boolean hotNews;

	@Column(name = "homepage")
	private Boolean homepage;

	@Column(name = "posted_date")
	private Date postedDate;

	/**
	 * @return the id
	 * @author taitm
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 * @author taitm
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @return the categoryId
	 * @author taitm
	 */
	public Long getCategoryId() {
		return categoryId;
	}

	/**
	 * @param categoryId
	 *            the categoryId to set
	 * @author taitm
	 */
	public void setCategoryId(Long categoryId) {
		this.categoryId = categoryId;
	}

	/**
	 * @return the code
	 * @author taitm
	 */
	public String getCode() {
		return code;
	}

	/**
	 * @param code
	 *            the code to set
	 * @author taitm
	 */
	public void setCode(String code) {
		this.code = code;
	}

	/**
	 * @return the imageUrl
	 * @author taitm
	 */
	public String getPhysicalImg() {
		return physicalImg;
	}

	/**
	 * @param imageUrl
	 *            the imageUrl to set
	 * @author taitm
	 */
	public void setPhysicalImg(String physicalImg) {
		this.physicalImg = physicalImg;
	}

	/**
	 * @return the imageName
	 * @author taitm
	 */
	public String getImageName() {
		return imageName;
	}

	/**
	 * @param imageName
	 *            the imageName to set
	 * @author taitm
	 */
	public void setImageName(String imageName) {
		this.imageName = imageName;
	}

	/**
	 * @return the enabled
	 * @author taitm
	 */
	public boolean isEnabled() {
		return enabled;
	}

	/**
	 * @param enabled
	 *            the enabled to set
	 * @author taitm
	 */
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	/**
	 * @return the sort
	 * @author taitm
	 */
	public Long getSort() {
		return sort;
	}

	/**
	 * @param sort
	 *            the sort to set
	 * @author taitm
	 */
	public void setSort(Long sort) {
		this.sort = sort;
	}

	/**
	 * @return the beforeId
	 * @author taitm
	 */
	public Long getBeforeId() {
		return beforeId;
	}

	/**
	 * @param beforeId
	 *            the beforeId to set
	 * @author taitm
	 */
	public void setBeforeId(Long beforeId) {
		this.beforeId = beforeId;
	}

	/**
	 * @return the processId
	 * @author taitm
	 */
	public Long getProcessId() {
		return processId;
	}

	/**
	 * @param processId
	 *            the processId to set
	 * @author taitm
	 */
	public void setProcessId(Long processId) {
		this.processId = processId;
	}

	/**
	 * @return the status
	 * @author taitm
	 */
	public Integer getStatus() {
		return status;
	}

	/**
	 * @param status
	 *            the status to set
	 * @author taitm
	 */
	public void setStatus(Integer status) {
		this.status = status;
	}

	/**
	 * @return the createBy
	 * @author taitm
	 */
	public String getCreateBy() {
		return createBy;
	}

	/**
	 * @param createBy
	 *            the createBy to set
	 * @author taitm
	 */
	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}

	/**
	 * @return the updateBy
	 * @author taitm
	 */
	public String getUpdateBy() {
		return updateBy;
	}

	/**
	 * @param updateBy
	 *            the updateBy to set
	 * @author taitm
	 */
	public void setUpdateBy(String updateBy) {
		this.updateBy = updateBy;
	}

	/**
	 * @return the approvedBy
	 * @author taitm
	 */
	public String getApprovedBy() {
		return approvedBy;
	}

	/**
	 * @param approvedBy
	 *            the approvedBy to set
	 * @author taitm
	 */
	public void setApprovedBy(String approvedBy) {
		this.approvedBy = approvedBy;
	}

	/**
	 * @return the publishedBy
	 * @author taitm
	 */
	public String getPublishedBy() {
		return publishedBy;
	}

	/**
	 * @param publishedBy
	 *            the publishedBy to set
	 * @author taitm
	 */
	public void setPublishedBy(String publishedBy) {
		this.publishedBy = publishedBy;
	}

	/**
	 * @return the deleteBy
	 * @author taitm
	 */
	public String getDeleteBy() {
		return deleteBy;
	}

	/**
	 * @param deleteBy
	 *            the deleteBy to set
	 * @author taitm
	 */
	public void setDeleteBy(String deleteBy) {
		this.deleteBy = deleteBy;
	}

	/**
	 * @return the createDate
	 * @author taitm
	 */
	public Date getCreateDate() {
		return createDate;
	}

	/**
	 * @param createDate
	 *            the createDate to set
	 * @author taitm
	 */
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	/**
	 * @return the updateDate
	 * @author taitm
	 */
	public Date getUpdateDate() {
		return updateDate;
	}

	/**
	 * @param updateDate
	 *            the updateDate to set
	 * @author taitm
	 */
	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	/**
	 * @return the deleteDate
	 * @author taitm
	 */
	public Date getDeleteDate() {
		return deleteDate;
	}

	/**
	 * @param deleteDate
	 *            the deleteDate to set
	 * @author taitm
	 */
	public void setDeleteDate(Date deleteDate) {
		this.deleteDate = deleteDate;
	}

	/**
	 * @return the customerTypeId
	 * @author taitm
	 */
	public Long getCustomerTypeId() {
		return customerTypeId;
	}

	/**
	 * @param customerTypeId
	 *            the customerTypeId to set
	 * @author taitm
	 */
	public void setCustomerTypeId(Long customerTypeId) {
		this.customerTypeId = customerTypeId;
	}

	/**
	 * @return the kind
	 * @author taitm
	 */
	public Long getKind() {
		return kind;
	}

	/**
	 * @param kind
	 *            the kind to set
	 * @author taitm
	 */
	public void setKind(Long kind) {
		this.kind = kind;
	}

	/**
	 * @return the hotNews
	 * @author taitm
	 */
	public Boolean getHotNews() {
		return hotNews;
	}

	/**
	 * @param hotNews
	 *            the hotNews to set
	 * @author taitm
	 */
	public void setHotNews(Boolean hotNews) {
		this.hotNews = hotNews;
	}

	/**
	 * @return the homepage
	 * @author taitm
	 */
	public Boolean getHomepage() {
		return homepage;
	}

	/**
	 * @param homepage
	 *            the homepage to set
	 * @author taitm
	 */
	public void setHomepage(Boolean homepage) {
		this.homepage = homepage;
	}

	/**
	 * @return the postedDate
	 * @author taitm
	 */
	public Date getPostedDate() {
		return postedDate;
	}

	/**
	 * @param postedDate
	 *            the postedDate to set
	 * @author taitm
	 */
	public void setPostedDate(Date postedDate) {
		this.postedDate = postedDate;
	}

}
