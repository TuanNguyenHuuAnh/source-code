package vn.com.unit.cms.admin.all.entity;

import java.util.Date;

import org.springframework.data.annotation.Id;

import jp.sf.amateras.mirage.annotation.Column;
import jp.sf.amateras.mirage.annotation.PrimaryKey;
import jp.sf.amateras.mirage.annotation.Table;
import jp.sf.amateras.mirage.annotation.PrimaryKey.GenerationType;

@Table(name = "m_investor_language")
public class InvestorLanguage {
	@Id
	@Column(name = "id")
	@PrimaryKey(generationType = GenerationType.SEQUENCE, generator = "SEQ_M_INVESTOR_LANGUAGE")
	private Long id;

	@Column(name = "m_investor_id")
	private Long investorId;

	@Column(name = "m_language_code")
	private String languageCode;

	@Column(name = "title")
	private String title;

	@Column(name = "KEYWORD")
	private String keyword;

	@Column(name = "KEYWORD_DESCRIPTION")
	private String descriptionKeyword;

	@Column(name = "SHORT_CONTENT")
	private String shortContent;

	@Column(name = "CONTENT")
	private String content;

	@Column(name = "link_alias")
	private String linkAlias;

	@Column(name = "create_by")
	private String createBy;

	@Column(name = "update_by")
	private String updateBy;

	@Column(name = "delete_by")
	private String deleteBy;

	@Column(name = "create_date")
	private Date createDate;

	@Column(name = "update_date")
	private Date updateDate;

	@Column(name = "delete_date")
	private Date deleteDate;

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
	 * @return the investorId
	 * @author taitm
	 */
	public Long getInvestorId() {
		return investorId;
	}

	/**
	 * @param investorId
	 *            the investorId to set
	 * @author taitm
	 */
	public void setInvestorId(Long investorId) {
		this.investorId = investorId;
	}

	/**
	 * @return the languageCode
	 * @author taitm
	 */
	public String getLanguageCode() {
		return languageCode;
	}

	/**
	 * @param languageCode
	 *            the languageCode to set
	 * @author taitm
	 */
	public void setLanguageCode(String languageCode) {
		this.languageCode = languageCode;
	}

	/**
	 * @return the title
	 * @author taitm
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * @param title
	 *            the title to set
	 * @author taitm
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * @return the keyword
	 * @author taitm
	 */
	public String getKeyword() {
		return keyword;
	}

	/**
	 * @param keyword
	 *            the keyword to set
	 * @author taitm
	 */
	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	/**
	 * @return the descriptionKeyword
	 * @author taitm
	 */
	public String getDescriptionKeyword() {
		return descriptionKeyword;
	}

	/**
	 * @param descriptionKeyword
	 *            the descriptionKeyword to set
	 * @author taitm
	 */
	public void setDescriptionKeyword(String descriptionKeyword) {
		this.descriptionKeyword = descriptionKeyword;
	}

	/**
	 * @return the shortContent
	 * @author taitm
	 */
	public String getShortContent() {
		return shortContent;
	}

	/**
	 * @param shortContent
	 *            the shortContent to set
	 * @author taitm
	 */
	public void setShortContent(String shortContent) {
		this.shortContent = shortContent;
	}

	/**
	 * @return the content
	 * @author taitm
	 */
	public String getContent() {
		return content;
	}

	/**
	 * @param content
	 *            the content to set
	 * @author taitm
	 */
	public void setContent(String content) {
		this.content = content;
	}

	/**
	 * @return the linkAlias
	 * @author taitm
	 */
	public String getLinkAlias() {
		return linkAlias;
	}

	/**
	 * @param linkAlias
	 *            the linkAlias to set
	 * @author taitm
	 */
	public void setLinkAlias(String linkAlias) {
		this.linkAlias = linkAlias;
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

}
