package vn.com.unit.cms.admin.all.dto;

public class InvestorLanguageEditDto {

	private Long id;

	private Long investorId;
	private String title;
	private String linkAlias;
	private String keyword;
	private String descriptionKeyword;
	private String shortContent;
	private String content;

	private String languageCode;

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

}
