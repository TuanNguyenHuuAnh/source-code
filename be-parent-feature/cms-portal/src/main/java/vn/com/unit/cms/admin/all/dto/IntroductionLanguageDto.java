/*******************************************************************************
 * Class        ：IntroductionLanguage
 * Created date ：2017/03/07
 * Lasted date  ：2017/03/07
 * Author       ：thuydtn
 * Change log   ：2017/03/07：01-00 thuydtn create a new
 ******************************************************************************/
package vn.com.unit.cms.admin.all.dto;

import java.util.Date;

import org.hibernate.validator.constraints.NotEmpty;

import vn.com.unit.cms.admin.all.entity.IntroductionLanguage;
//import vn.com.unit.util.Util;

/**
 * IntroductionLanguage
 * 
 * @version 01-00
 * @since 01-00
 * @author thuydtn
 */
@SuppressWarnings("deprecation")
public class IntroductionLanguageDto{

    private Long id;
    private Long introductionId;
    private String code;
    private String languageCode;
    private String categoryName;
    @NotEmpty
    private String title;
    private String shortContent;
    private String content; 
    private String languageDispName;
    private String linkAlias;
    private String bannerImgDesktop;
    private String bannerImgDesktopPhysical;
    private String bannerImgMobile;
    private String bannerImgMobilePhysical;
    private Date createDate;
    private String keyword;
    
    private String keywordDescription;
    
    private String createBy;
    
    private String statusName;
    
    private Integer status;
    
    private Boolean enabled;
    
    public IntroductionLanguageDto(){
        
    }
    
    public IntroductionLanguageDto(IntroductionLanguage entity, String langDispName){
          this.id = entity.getId();
          this.introductionId = entity.getIntroductionId();
          this.setLanguageCode(entity.getLanguageCode());
          this.title = entity.getTitle();
          this.shortContent = entity.getShortContent();
          this.content = entity.getContent();
          this.languageDispName = langDispName;
          this.linkAlias = entity.getLinkAlias();
          this.bannerImgDesktop = entity.getBannerImgDesktop();
          this.bannerImgDesktopPhysical = entity.getBannerImgDesktopPhysical();
          this.bannerImgMobile = entity.getBannerImgMobile();
          this.bannerImgMobilePhysical = entity.getBannerImgMobilePhysical();
          this.keyword = entity.getKeyword();
          this.keywordDescription = entity.getKeywordDescription();
    }
    
    public IntroductionLanguage createEntity(){
        IntroductionLanguage entity = new IntroductionLanguage();
        entity.setId(this.id);
        entity.setIntroductionId(this.introductionId);
        entity.setLanguageCode(this.getLanguageCode());
        entity.setTitle(this.title);
        entity.setShortContent(this.shortContent);
        entity.setContent(this.content);
        entity.setLinkAlias(this.linkAlias);
        entity.setBannerImgDesktop(this.bannerImgDesktop);
        entity.setBannerImgDesktopPhysical(this.bannerImgDesktopPhysical);
        entity.setBannerImgMobile(this.bannerImgMobile);
        entity.setBannerImgMobilePhysical(this.bannerImgMobilePhysical);
        entity.setKeyword(this.keyword);
        entity.setKeywordDescription(this.keywordDescription);
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
     * Get introductionId
     * @return Long
     * @author thuydtn
     */
    public Long getIntroductionId() {
        return introductionId;
    }
    /**
     * Set introductionId
     * @param   introductionId
     *          type Long
     * @return
     * @author  thuydtn
     */
    public void setIntroductionId(Long introductionId) {
        this.introductionId = introductionId;
    }
    /**
     * Get title
     * @return String
     * @author thuydtn
     */
    public String getTitle() {
        return title;
    }
    /**
     * Set title
     * @param   title
     *          type String
     * @return
     * @author  thuydtn
     */
    public void setTitle(String title) {
        this.title = title;
    }
    /**
     * Get shortContent
     * @return String
     * @author thuydtn
     */
    public String getShortContent() {
        return shortContent;
    }
    /**
     * Set shortContent
     * @param   shortContent
     *          type String
     * @return
     * @author  thuydtn
     */
    public void setShortContent(String shortContent) {
        this.shortContent = shortContent;
    }
    /**
     * Get content
     * @return String
     * @author thuydtn
     */
    public String getContent() {
        return content;
    }
    /**
     * Set content
     * @param   content
     *          type String
     * @return
     * @author  thuydtn
     */
    public void setContent(String content) {
        this.content = content;
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
     * Get linkAlias
     *          type String
     * @return
     * @author  diennv
     */
	public String getLinkAlias() {
		return linkAlias;
	}

	/**
     * Set linkAlias
     * @param   linkAlias
     *          type String
     * @return
     * @author  diennv
     */
	public void setLinkAlias(String linkAlias) {
		this.linkAlias = linkAlias;
	}

	public String getBannerImgDesktop() {
		return bannerImgDesktop;
	}

	public void setBannerImgDesktop(String bannerImgDesktop) {
		this.bannerImgDesktop = bannerImgDesktop;
	}

	public String getBannerImgDesktopPhysical() {
		return bannerImgDesktopPhysical;
	}

	public void setBannerImgDesktopPhysical(String bannerImgDesktopPhysical) {
		this.bannerImgDesktopPhysical = bannerImgDesktopPhysical;
	}

	public String getBannerImgMobile() {
		return bannerImgMobile;
	}

	public void setBannerImgMobile(String bannerImgMobile) {
		this.bannerImgMobile = bannerImgMobile;
	}

	public String getBannerImgMobilePhysical() {
		return bannerImgMobilePhysical;
	}

	public void setBannerImgMobilePhysical(String bannerImgMobilePhysical) {
		this.bannerImgMobilePhysical = bannerImgMobilePhysical;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	/**
	 * @return the keyword
	 * @author taitm
	 */
	public String getKeyword() {
		return keyword;
	}

	/**
	 * @param keyword the keyword to set
	 * @author taitm
	 */
	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	/**
	 * @return the keywordDescription
	 * @author taitm
	 */
	public String getKeywordDescription() {
		return keywordDescription;
	}

	/**
	 * @param keywordDescription
	 *            the keywordDescription to set
	 * @author taitm
	 */
	public void setKeywordDescription(String keywordDescription) {
		this.keywordDescription = keywordDescription;
	}

	/**
	 * @return the categoryName
	 * @author taitm
	 */
	public String getCategoryName() {
		return categoryName;
	}

	/**
	 * @param categoryName
	 *            the categoryName to set
	 * @author taitm
	 */
	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
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
	 * @return the statusName
	 * @author taitm
	 */
	public String getStatusName() {
		return statusName;
	}

	/**
	 * @param statusName
	 *            the statusName to set
	 * @author taitm
	 */
	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}

	/**
	 * @return the enabled
	 * @author taitm
	 */
	public Boolean getEnabled() {
		return enabled;
	}

	/**
	 * @param enabled
	 *            the enabled to set
	 * @author taitm
	 */
	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
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

}
