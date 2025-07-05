package vn.com.unit.cms.admin.all.entity;

import java.util.Date;

import org.springframework.data.annotation.Id;

import jp.sf.amateras.mirage.annotation.Column;
import jp.sf.amateras.mirage.annotation.PrimaryKey;
import jp.sf.amateras.mirage.annotation.Table;
import jp.sf.amateras.mirage.annotation.PrimaryKey.GenerationType;

@Table(name = "m_investor_category_language")
public class InvestorCategoryLanguage {

	@Id
	@Column(name = "id")
	@PrimaryKey(generationType = GenerationType.SEQUENCE, generator = "SEQ_M_INVESTOR_CATEGORY_LANGUAGE")
	private Long id;

	@Column(name = "m_investor_category_id")
	private Long categoryId;

	@Column(name = "m_language_code")
	private String languageCode;

	@Column(name = "title")
	private String title;

	@Column(name = "link_alias")
	private String linkAlias;

	@Column(name = "key_word")
	private String keyWord;

	@Column(name = "description_keyword")
	private String descriptionKeyword;

	@Column(name = "description")
	private String description;

	@Column(name = "short_content")
	private String shortContent;

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

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @return the categoryId
	 */
	public Long getCategoryId() {
		return categoryId;
	}

	/**
	 * @return the languageCode
	 */
	public String getLanguageCode() {
		return languageCode;
	}

	/**
	 * @return the tittle
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * @return the linkAlias
	 */
	public String getLinkAlias() {
		return linkAlias;
	}

	/**
	 * @return the keyWord
	 */
	public String getKeyWord() {
		return keyWord;
	}

	/**
	 * @return the descriptionKeyword
	 */
	public String getDescriptionKeyword() {
		return descriptionKeyword;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @return the shortContent
	 */
	public String getShortContent() {
		return shortContent;
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
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @param categoryId the categoryId to set
	 */
	public void setCategoryId(Long categoryId) {
		this.categoryId = categoryId;
	}

	/**
	 * @param languageCode the languageCode to set
	 */
	public void setLanguageCode(String languageCode) {
		this.languageCode = languageCode;
	}

	/**
	 * @param tittle the tittle to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * @param linkAlias the linkAlias to set
	 */
	public void setLinkAlias(String linkAlias) {
		this.linkAlias = linkAlias;
	}

	/**
	 * @param keyWord the keyWord to set
	 */
	public void setKeyWord(String keyWord) {
		this.keyWord = keyWord;
	}

	/**
	 * @param descriptionKeyword the descriptionKeyword to set
	 */
	public void setDescriptionKeyword(String descriptionKeyword) {
		this.descriptionKeyword = descriptionKeyword;
	}

	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @param shortContent the shortContent to set
	 */
	public void setShortContent(String shortContent) {
		this.shortContent = shortContent;
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

}
