package vn.com.unit.cms.admin.all.dto;

import org.hibernate.validator.constraints.NotEmpty;

@SuppressWarnings("deprecation")
public class InvestorCategoryLanguageDto {

	private Long id;

	private Long categoryId;

	private String languageCode;

	@NotEmpty
	private String label;

	private String shortContent;

	private String linkAlias;

	private String keyWord;

	private String descriptionKeyword;

	private String languageDispName;

	private String description;

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
	 * @return the label
	 */
	public String getLabel() {
		return label;
	}

	/**
	 * @return the shortContent
	 */
	public String getShortContent() {
		return shortContent;
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
	 * @param label the label to set
	 */
	public void setLabel(String label) {
		this.label = label;
	}

	/**
	 * @param shortContent the shortContent to set
	 */
	public void setShortContent(String shortContent) {
		this.shortContent = shortContent;
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
	 * @return the languageDispName
	 */
	public String getLanguageDispName() {
		return languageDispName;
	}

	/**
	 * @param languageDispName the languageDispName to set
	 */
	public void setLanguageDispName(String languageDispName) {
		this.languageDispName = languageDispName;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}
	
	  public InvestorCategoryLanguageDto(){
	        
	    }
	
	 public InvestorCategoryLanguageDto(InvestorCategoryLanguageDto entity, String langDispName){
	        this.id = entity.getId();
	        this.categoryId = entity.getCategoryId();
	        this.languageCode = entity.getLanguageCode();
	        this.label = entity.getLabel();
	        this.description = entity.getDescription();
	        this.languageDispName = langDispName;
	        this.linkAlias = entity.getLinkAlias();
	        this.keyWord = entity.getKeyWord();
	        this.descriptionKeyword = entity.getDescriptionKeyword();
	        this.shortContent = entity.getShortContent();
	    }
}
