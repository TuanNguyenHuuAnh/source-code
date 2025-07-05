/*******************************************************************************
 * Class ：IntroductionLanguage Created date ：2017/03/07 Lasted date ：2017/03/07 Author ：thuydtn Change log
 * ：2017/03/07：01-00 thuydtn create a new
 ******************************************************************************/
package vn.com.unit.cms.admin.all.entity;

//import javax.persistence.Lob;

import org.springframework.data.annotation.Id;

import jp.sf.amateras.mirage.annotation.Column;
import jp.sf.amateras.mirage.annotation.PrimaryKey;
import jp.sf.amateras.mirage.annotation.Table;
import jp.sf.amateras.mirage.annotation.PrimaryKey.GenerationType;
import vn.com.unit.cms.core.entity.AbstractTracking;

/**
 * IntroductionLanguage
 * 
 * @version 01-00
 * @since 01-00
 * @author thuydtn
 */
@Table(name = "m_introduction_language")
public class IntroductionLanguage extends AbstractTracking {

	@Id
	@Column(name = "id")
	@PrimaryKey(generationType = GenerationType.SEQUENCE, generator = "SEQ_M_INTRODUCTION_LANGUAGE")
	private Long id;
	@Column(name = "m_introduction_id")
	private Long introductionId;
	@Column(name = "m_language_code")
	private String languageCode;
	@Column(name = "title")
	private String title;
	@Column(name = "short_content")
	private String shortContent;
	@Column(name = "content")
	private String content;
	@Column(name = "link_alias")
	private String linkAlias;
	@Column(name = "banner_img_desktop")
	private String bannerImgDesktop;
	@Column(name = "banner_img_desktop_physical")
	private String bannerImgDesktopPhysical;
	@Column(name = "banner_img_mobile")
	private String bannerImgMobile;
	@Column(name = "banner_img_mobile_physical")
	private String bannerImgMobilePhysical;

	@Column(name = "keyword")
	private String keyword;

	@Column(name = "KEYWORD_DESCRIPTION")
	private String keywordDescription;

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
	 * Get introductionId
	 * 
	 * @return Long
	 * @author thuydtn
	 */
	public Long getIntroductionId() {
		return introductionId;
	}

	/**
	 * Set introductionId
	 * 
	 * @param introductionId
	 *            type Long
	 * @return
	 * @author thuydtn
	 */
	public void setIntroductionId(Long introductionId) {
		this.introductionId = introductionId;
	}

	/**
	 * Get title
	 * 
	 * @return String
	 * @author thuydtn
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * Set title
	 * 
	 * @param title
	 *            type String
	 * @return
	 * @author thuydtn
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * Get shortContent
	 * 
	 * @return String
	 * @author thuydtn
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
	 * @author thuydtn
	 */
	public void setShortContent(String shortContent) {
		this.shortContent = shortContent;
	}

	/**
	 * Get content
	 * 
	 * @return byte[]
	 * @author thuydtn
	 */
	public String getContent() {
		return content;
	}

	/**
	 * Set content
	 * 
	 * @param content
	 *            type byte[]
	 * @return
	 * @author thuydtn
	 */
	public void setContent(String content) {
		this.content = content;
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
	 * Get LinkAlias type String
	 * 
	 * @return
	 * @author diennv
	 */
	public String getLinkAlias() {
		return linkAlias;
	}

	/**
	 * Set LinkAlias
	 * 
	 * @param setLinkAlias
	 *            type String
	 * @return
	 * @author diennv
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

}
