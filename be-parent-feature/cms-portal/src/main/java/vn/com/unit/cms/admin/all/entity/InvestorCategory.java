package vn.com.unit.cms.admin.all.entity;

import java.util.Date;

import org.springframework.data.annotation.Id;

import jp.sf.amateras.mirage.annotation.Column;
import jp.sf.amateras.mirage.annotation.PrimaryKey;
import jp.sf.amateras.mirage.annotation.PrimaryKey.GenerationType;
import jp.sf.amateras.mirage.annotation.Table;

@Table(name = "m_investor_category")
public class InvestorCategory {

	@Id
	@Column(name = "id")
	@PrimaryKey(generationType = GenerationType.SEQUENCE, generator = "SEQ_M_INVESTOR_CATEGORY")
	private Long id;

	@Column(name = "code")
	private String code;

	@Column(name = "name")
	private String name;

	@Column(name = "status")
	private Integer status;

	@Column(name = "process_id")
	private Long processId;

	@Column(name = "investor_category_type")
	private Integer investorCategoryType;

	@Column(name = "investor_category_location")
	private Integer investorCategoryLocation;

	@Column(name = "investor_category_parent_id")
	private Long investorCategoryParentId;

	@Column(name = "investor_category_level_id")
	private Integer investorCategoryLevelId;

	@Column(name = "image_name_left")
	private String imageNameLeft;

	@Column(name = "image_url_left")
	private String imageUrlLeft;

	@Column(name = "image_name_right")
	private String imageNameRight;

	@Column(name = "image_url_right")
	private String imageUrlRight;

	@Column(name = "customer_type_id")
	private Long customerTypeId;

	@Column(name = "note")
	private String note;

	@Column(name = "create_date")
	private Date createDate;

	@Column(name = "create_by")
	private String createBy;

	@Column(name = "update_date")
	private Date updateDate;

	@Column(name = "update_by")
	private String updateBy;

	@Column(name = "delete_date")
	private Date deleteDate;

	@Column(name = "delete_by")
	private String deleteBy;

	@Column(name = "approve_date")
	private Date approveDate;

	@Column(name = "approve_by")
	private String approveBy;

	@Column(name = "publish_by")
	private String publishBy;

	@Column(name = "publish_date")
	private Date publishDate;

	@Column(name = "before_id")
	private Long beforeId;

	@Column(name = "sort")
	private Long sort;

	@Column(name = "enable")
	private boolean enable;
	
	@Column(name = "investor_category_location_left")
	private boolean investorCategoryLocationLeft;
	
	@Column(name = "investor_category_location_right_top")
	private boolean investorCategoryLocationRightTop;
	
	@Column(name = "investor_category_location_right_bottom")
	private boolean investorCategoryLocationRightBottom;
	
	@Column(name = "image_name")
	private String imageName;

	@Column(name = "image_url")
	private String imageUrl;

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @return the code
	 */
	public String getCode() {
		return code;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @return the status
	 */
	public Integer getStatus() {
		return status;
	}

	/**
	 * @return the processId
	 */
	public Long getProcessId() {
		return processId;
	}

	/**
	 * @return the investorCategoryType
	 */
	public Integer getInvestorCategoryType() {
		return investorCategoryType;
	}

	/**
	 * @return the investorCategoryLocation
	 */
	public Integer getInvestorCategoryLocation() {
		return investorCategoryLocation;
	}

	/**
	 * @return the investorCategoryParentId
	 */
	public Long getInvestorCategoryParentId() {
		return investorCategoryParentId;
	}

	/**
	 * @return the investorCategoryLevelId
	 */
	public Integer getInvestorCategoryLevelId() {
		return investorCategoryLevelId;
	}

	/**
	 * @return the imageNameLeft
	 */
	public String getImageNameLeft() {
		return imageNameLeft;
	}

	/**
	 * @return the imageUrlLeft
	 */
	public String getImageUrlLeft() {
		return imageUrlLeft;
	}

	/**
	 * @return the imageNameRight
	 */
	public String getImageNameRight() {
		return imageNameRight;
	}

	/**
	 * @return the imageUrlRight
	 */
	public String getImageUrlRight() {
		return imageUrlRight;
	}

	/**
	 * @return the customerTypeId
	 */
	public Long getCustomerTypeId() {
		return customerTypeId;
	}

	/**
	 * @return the note
	 */
	public String getNote() {
		return note;
	}

	/**
	 * @return the createDate
	 */
	public Date getCreateDate() {
		return createDate;
	}

	/**
	 * @return the createBy
	 */
	public String getCreateBy() {
		return createBy;
	}

	/**
	 * @return the updateDate
	 */
	public Date getUpdateDate() {
		return updateDate;
	}

	/**
	 * @return the updateBy
	 */
	public String getUpdateBy() {
		return updateBy;
	}

	/**
	 * @return the deleteDate
	 */
	public Date getDeleteDate() {
		return deleteDate;
	}

	/**
	 * @return the deleteBy
	 */
	public String getDeleteBy() {
		return deleteBy;
	}

	/**
	 * @return the approveDate
	 */
	public Date getApproveDate() {
		return approveDate;
	}

	/**
	 * @return the approveBy
	 */
	public String getApproveBy() {
		return approveBy;
	}

	/**
	 * @return the publishBy
	 */
	public String getPublishBy() {
		return publishBy;
	}

	/**
	 * @return the publishDate
	 */
	public Date getPublishDate() {
		return publishDate;
	}

	/**
	 * @return the beforeId
	 */
	public Long getBeforeId() {
		return beforeId;
	}

	/**
	 * @return the sort
	 */
	public Long getSort() {
		return sort;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @param code the code to set
	 */
	public void setCode(String code) {
		this.code = code;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @param status the status to set
	 */
	public void setStatus(Integer status) {
		this.status = status;
	}

	/**
	 * @param processId the processId to set
	 */
	public void setProcessId(Long processId) {
		this.processId = processId;
	}

	/**
	 * @param investorCategoryType the investorCategoryType to set
	 */
	public void setInvestorCategoryType(Integer investorCategoryType) {
		this.investorCategoryType = investorCategoryType;
	}

	/**
	 * @param investorCategoryLocation the investorCategoryLocation to set
	 */
	public void setInvestorCategoryLocation(Integer investorCategoryLocation) {
		this.investorCategoryLocation = investorCategoryLocation;
	}

	/**
	 * @param investorCategoryParentId the investorCategoryParentId to set
	 */
	public void setInvestorCategoryParentId(Long investorCategoryParentId) {
		this.investorCategoryParentId = investorCategoryParentId;
	}

	/**
	 * @param investorCategoryLevelId the investorCategoryLevelId to set
	 */
	public void setInvestorCategoryLevelId(Integer investorCategoryLevelId) {
		this.investorCategoryLevelId = investorCategoryLevelId;
	}

	/**
	 * @param imageNameLeft the imageNameLeft to set
	 */
	public void setImageNameLeft(String imageNameLeft) {
		this.imageNameLeft = imageNameLeft;
	}

	/**
	 * @param imageUrlLeft the imageUrlLeft to set
	 */
	public void setImageUrlLeft(String imageUrlLeft) {
		this.imageUrlLeft = imageUrlLeft;
	}

	/**
	 * @param imageNameRight the imageNameRight to set
	 */
	public void setImageNameRight(String imageNameRight) {
		this.imageNameRight = imageNameRight;
	}

	/**
	 * @param imageUrlRight the imageUrlRight to set
	 */
	public void setImageUrlRight(String imageUrlRight) {
		this.imageUrlRight = imageUrlRight;
	}

	/**
	 * @param customerTypeId the customerTypeId to set
	 */
	public void setCustomerTypeId(Long customerTypeId) {
		this.customerTypeId = customerTypeId;
	}

	/**
	 * @param note the note to set
	 */
	public void setNote(String note) {
		this.note = note;
	}

	/**
	 * @param createDate the createDate to set
	 */
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	/**
	 * @param createBy the createBy to set
	 */
	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}

	/**
	 * @param updateDate the updateDate to set
	 */
	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	/**
	 * @param updateBy the updateBy to set
	 */
	public void setUpdateBy(String updateBy) {
		this.updateBy = updateBy;
	}

	/**
	 * @param deleteDate the deleteDate to set
	 */
	public void setDeleteDate(Date deleteDate) {
		this.deleteDate = deleteDate;
	}

	/**
	 * @param deleteBy the deleteBy to set
	 */
	public void setDeleteBy(String deleteBy) {
		this.deleteBy = deleteBy;
	}

	/**
	 * @param approveDate the approveDate to set
	 */
	public void setApproveDate(Date approveDate) {
		this.approveDate = approveDate;
	}

	/**
	 * @param approveBy the approveBy to set
	 */
	public void setApproveBy(String approveBy) {
		this.approveBy = approveBy;
	}

	/**
	 * @param publishBy the publishBy to set
	 */
	public void setPublishBy(String publishBy) {
		this.publishBy = publishBy;
	}

	/**
	 * @param publishDate the publishDate to set
	 */
	public void setPublishDate(Date publishDate) {
		this.publishDate = publishDate;
	}

	/**
	 * @param beforeId the beforeId to set
	 */
	public void setBeforeId(Long beforeId) {
		this.beforeId = beforeId;
	}

	/**
	 * @param sort the sort to set
	 */
	public void setSort(Long sort) {
		this.sort = sort;
	}

	/**
	 * @return the enable
	 */
	public boolean getEnable() {
		return enable;
	}

	/**
	 * @param enable the enable to set
	 */
	public void setEnable(boolean enable) {
		this.enable = enable;
	}

	/**
	 * @return the investorCategoryLocationLeft
	 */
	public boolean getInvestorCategoryLocationLeft() {
		return investorCategoryLocationLeft;
	}

	/**
	 * @return the investorCategoryLocationRightTop
	 */
	public boolean getInvestorCategoryLocationRightTop() {
		return investorCategoryLocationRightTop;
	}

	/**
	 * @return the investorCategoryLocationRightBottom
	 */
	public boolean getInvestorCategoryLocationRightBottom() {
		return investorCategoryLocationRightBottom;
	}

	/**
	 * @param investorCategoryLocationLeft the investorCategoryLocationLeft to set
	 */
	public void setInvestorCategoryLocationLeft(boolean investorCategoryLocationLeft) {
		this.investorCategoryLocationLeft = investorCategoryLocationLeft;
	}

	/**
	 * @param investorCategoryLocationRightTop the investorCategoryLocationRightTop to set
	 */
	public void setInvestorCategoryLocationRightTop(boolean investorCategoryLocationRightTop) {
		this.investorCategoryLocationRightTop = investorCategoryLocationRightTop;
	}

	/**
	 * @param investorCategoryLocationRightBottom the investorCategoryLocationRightBottom to set
	 */
	public void setInvestorCategoryLocationRightBottom(boolean investorCategoryLocationRightBottom) {
		this.investorCategoryLocationRightBottom = investorCategoryLocationRightBottom;
	}

	public String getImageName() {
		return imageName;
	}

	public void setImageName(String imageName) {
		this.imageName = imageName;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

}
