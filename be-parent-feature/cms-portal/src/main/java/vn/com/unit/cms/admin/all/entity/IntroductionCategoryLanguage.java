/*******************************************************************************
 * Class        ：IntroductionCategoryLanguageDto
 * Created date ：2017/03/08
 * Lasted date  ：2017/03/08
 * Author       ：thuydtn
 * Change log   ：2017/03/08：01-00 thuydtn create a new
 ******************************************************************************/
package vn.com.unit.cms.admin.all.entity;

import org.springframework.data.annotation.Id;

import jp.sf.amateras.mirage.annotation.Column;
import jp.sf.amateras.mirage.annotation.PrimaryKey;
import jp.sf.amateras.mirage.annotation.Table;
import jp.sf.amateras.mirage.annotation.PrimaryKey.GenerationType;
import vn.com.unit.cms.core.entity.AbstractTracking;

/**
 * IntroductionCategoryLanguageDto
 * 
 * @version 01-00
 * @since 01-00
 * @author thuydtn
 */
@Table(name = "m_introduction_category_language")
public class IntroductionCategoryLanguage extends AbstractTracking {
	@Id
	@Column(name = "id")
	@PrimaryKey(generationType = GenerationType.SEQUENCE, generator = "SEQ_M_INTRODUCTION_CATEGORY_LANGUAGE")
	private Long id;
	@Column(name = "m_introduce_category_id")
	private Long categoryId;
	@Column(name = "m_language_code")
	private String languageCode;
	@Column(name = "label")
	private String label;
	@Column(name = "description")
	private String description;
	
    @Column(name = "link_alias")
    private String linkAlias;
    
    @Column(name = "key_word")
    private String keyWord;
    
    @Column(name = "description_keyword")
    private String descriptionKeyword;
    
    @Column(name = "banner_desktop")
    private Long bannerDesktop;
    
    @Column(name = "banner_mobile")
    private Long bannerMobile;
    
    @Column(name = "short_content")
    private String shortContent;

	/**
	 * Get id
	 * 
	 * @return Long
	 * @author thuydtn
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
	 * @author thuydtn
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * Get categoryId
	 * 
	 * @return Long
	 * @author thuydtn
	 */
	public Long getCategoryId() {
		return categoryId;
	}

	/**
	 * Set categoryId
	 * 
	 * @param categoryId
	 *            type Long
	 * @return
	 * @author thuydtn
	 */
	public void setCategoryId(Long categoryId) {
		this.categoryId = categoryId;
	}

	/**
	 * Get label
	 * 
	 * @return String
	 * @author thuydtn
	 */
	public String getLabel() {
		return label;
	}

	/**
	 * Set label
	 * 
	 * @param label
	 *            type String
	 * @return
	 * @author thuydtn
	 */
	public void setLabel(String label) {
		this.label = label;
	}

	/**
	 * Get languageCode
	 * 
	 * @return String
	 * @author thuydtn
	 */
	public String getLanguageCode() {
		return languageCode;
	}

	/**
	 * Set languageCode
	 * 
	 * @param languageCode
	 *            type String
	 * @return
	 * @author thuydtn
	 */
	public void setLanguageCode(String languageCode) {
		this.languageCode = languageCode;
	}

	/**
	 * 
	 * @return
	 */
	public String getDescription() {
		return description;
	}

    /**
     * 
     * @param description
     */
    public void setDescription(String description) {
        this.description = description;
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
     * Get keyWord
     * 
     * @return String
     * @author taitm
     */
    public String getKeyWord() {
        return keyWord;
    }

    /**
     * Set keyWord
     * 
     * @param keyWord
     *            type String
     * @return
     * @author taitm
     */
    public void setKeyWord(String keyWord) {
        this.keyWord = keyWord;
    }

    /**
     * Get descriptionKeyword
     * 
     * @return String
     * @author taitm
     */
    public String getDescriptionKeyword() {
        return descriptionKeyword;
    }

    /**
     * Set descriptionKeyword
     * 
     * @param descriptionKeyword
     *            type String
     * @return
     * @author taitm
     */
    public void setDescriptionKeyword(String descriptionKeyword) {
        this.descriptionKeyword = descriptionKeyword;
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

	public String getShortContent() {
		return shortContent;
	}

	public void setShortContent(String shortContent) {
		this.shortContent = shortContent;
	}

    
}
