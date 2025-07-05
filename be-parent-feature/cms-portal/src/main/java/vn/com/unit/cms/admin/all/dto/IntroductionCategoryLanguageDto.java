/*******************************************************************************
 * Class        ：IntroductionCategoryLanguageDto
 * Created date ：2017/03/08
 * Lasted date  ：2017/03/08
 * Author       ：thuydtn
 * Change log   ：2017/03/08：01-00 thuydtn create a new
 ******************************************************************************/
package vn.com.unit.cms.admin.all.dto;

import org.hibernate.validator.constraints.NotEmpty;

import vn.com.unit.cms.admin.all.entity.IntroductionCategoryLanguage;

/**
 * IntroductionCategoryLanguageDto
 * 
 * @version 01-00
 * @since 01-00
 * @author thuydtn
 */
@SuppressWarnings("deprecation")
public class IntroductionCategoryLanguageDto {
    private Long id;
    private Long categoryId;
    private String languageCode;
    @NotEmpty
    private String label;
    private String languageDispName;
    private String description;
    
    /** shortContent */
    private String shortContent;

    private String linkAlias;
    
    private String keyWord;
    
    private String descriptionKeyword;
    
    private Long bannerDesktop;
    
    private Long bannerMobile;
    
    public IntroductionCategoryLanguageDto(){
        
    }
    
    /**
     * 
     * @param entity type IntroductionCategoryLanguage
     * @param langDispName type String
     */
    public IntroductionCategoryLanguageDto(IntroductionCategoryLanguage entity, String langDispName){
        this.id = entity.getId();
        this.categoryId = entity.getCategoryId();
        this.languageCode = entity.getLanguageCode();
        this.label = entity.getLabel();
        this.description = entity.getDescription();
        this.languageDispName = langDispName;
        this.linkAlias = entity.getLinkAlias();
        this.keyWord = entity.getKeyWord();
        this.descriptionKeyword = entity.getDescriptionKeyword();
        this.bannerDesktop = entity.getBannerDesktop();
        this.bannerMobile = entity.getBannerMobile();
        this.shortContent = entity.getShortContent();
    }
    
    /**
     * 
     * @return entity type IntroductionCategoryLanguage
     */
    public IntroductionCategoryLanguage createEntity(){
        IntroductionCategoryLanguage entity = new IntroductionCategoryLanguage();
        entity.setId(this.id);
        entity.setCategoryId(this.categoryId);
        entity.setLanguageCode(this.languageCode);
        entity.setLabel(this.label);
        entity.setDescription(this.description);
        entity.setLinkAlias(this.linkAlias);
        entity.setKeyWord(this.keyWord);
        entity.setDescriptionKeyword(this.descriptionKeyword);
        entity.setBannerDesktop(this.bannerDesktop);
        entity.setBannerMobile(this.bannerMobile);
        entity.setShortContent(this.getShortContent());
        return entity;
    }
    
    /**
     * Get id
     * @return Long
     * @author thuydtn
     */
    public Long getId() {
        return id;
    }
    
    /**
     * Set id
     * @param   id
     *          type Long
     * @return
     * @author  thuydtn
     */
    public void setId(Long id) {
        this.id = id;
    }
    
    /**
     * Get categoryId
     * @return Long
     * @author thuydtn
     */
    public Long getCategoryId() {
        return categoryId;
    }
    
    /**
     * Set categoryId
     * @param   categoryId
     *          type Long
     * @return
     * @author  thuydtn
     */
    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }
    
    /**
     * Get label
     * @return String
     * @author thuydtn
     */
    public String getLabel() {
        return label;
    }
    
    /**
     * Set label
     * @param   label
     *          type String
     * @return
     * @author  thuydtn
     */
    public void setLabel(String label) {
        this.label = label;
    }
    
    /**
     * Get languageDispName
     * @return String
     * @author thuydtn
     */
    public String getLanguageDispName() {
        return languageDispName;
    }
    /**
     * Set languageDispName
     * @param   languageDispName
     *          type String
     * @return
     * @author  thuydtn
     */
    public void setLanguageDispName(String languageDispName) {
        this.languageDispName = languageDispName;
    }

    /**
     * Get languageCode
     * @return String
     * @author thuydtn
     */
    public String getLanguageCode() {
        return languageCode;
    }

    /**
     * Set languageCode
     * @param   languageCode
     *          type String
     * @return
     * @author  thuydtn
     */
    public void setLanguageCode(String languageCode) {
        this.languageCode = languageCode;
    }

    /**
     * @return
     */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description
	 */
	public void setDescription(String description) {
		this.description = description;
    }

    /**
     * Get shortContent
     * 
     * @return String
     * @author taitm
     */
    public String getShortContent() {
        return shortContent;
    }

    /**
     * Set shortContent
     * 
     * @param shortContent
     *            type String
     * @return
     * @author taitm
     */
    public void setShortContent(String shortContent) {
        this.shortContent = shortContent;
    }
    
    /**
     * Get linkAlias
     * 
     * @return String
     * @author taitm
     */
    public String getLinkAlias() {
        return linkAlias;
    }

    /**
     * Set linkAlias
     * 
     * @param linkAlias
     *            type String
     * @return
     * @author taitm
     */
    public void setLinkAlias(String linkAlias) {
        this.linkAlias = linkAlias;
    }

    /**
     * Get keyword
     * 
     * @return String
     * @author taitm
     */
    public String getKeyWord() {
        return keyWord;
    }

    /**
     * Set keyword
     * 
     * @param keyword
     *            type String
     * @return
     * @author taitm
     */
    public void setKeyWord(String keyWord) {
        this.keyWord = keyWord;
    }

    /**
     * Get bannerDesktop
     * 
     * @return Long
     * @author taitm
     */
    public Long getBannerDesktop() {
        return bannerDesktop;
    }

    /**
     * Set bannerDesktop
     * 
     * @param bannerDesktop
     *            type Long
     * @return
     * @author taitm
     */
    public void setBannerDesktop(Long bannerDesktop) {
        this.bannerDesktop = bannerDesktop;
    }

    /**
     * Get bannerMobile
     * 
     * @return Long
     * @author taitm
     */
    public Long getBannerMobile() {
        return bannerMobile;
    }

    /**
     * Set bannerMobile
     * 
     * @param bannerMobile
     *            type Long
     * @return
     * @author taitm
     */
    public void setBannerMobile(Long bannerMobile) {
        this.bannerMobile = bannerMobile;
    }

    
    /**
     * Get descriptionKeyword
     * @return String
     * @author taitm
     */
    public String getDescriptionKeyword() {
        return descriptionKeyword;
    }

    
    /**
     * Set descriptionKeyword
     * @param   descriptionKeyword
     *          type String
     * @return
     * @author  taitm
     */
    public void setDescriptionKeyword(String descriptionKeyword) {
        this.descriptionKeyword = descriptionKeyword;
    }
}
