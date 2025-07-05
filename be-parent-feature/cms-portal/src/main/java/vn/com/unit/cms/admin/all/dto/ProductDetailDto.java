/*******************************************************************************
 * Class        ：ProductDetailDto
 * Created date ：2017/06/14
 * Lasted date  ：2017/06/14
 * Author       ：hand
 * Change log   ：2017/06/14：01-00 hand create a new
 ******************************************************************************/
package vn.com.unit.cms.admin.all.dto;

import org.hibernate.validator.constraints.NotEmpty;

/**
 * ProductDetailDto
 * 
 * @version 01-00
 * @since 01-00
 * @author hand
 */
@SuppressWarnings("deprecation")
public class ProductDetailDto {

	/** id */
	private Long id;

	/** productId */
	private Long productId;

	/** languageCode */
	private String languageCode;

	/** groupContent */
	private String groupContent;

	/** content */
	@NotEmpty
	private String content;

	/** keyContent */
	private String keyContent;

	/** backgroundUrl */
	private String backgroundUrl;

	/** backgroundPhysical */
	private String backgroundPhysical;

	private String bannerDesktopMicrosite;

	private String bannerDesktopPhysicalMicrosite;

	private String bannerMobileMicrosite;

	private String bannerMobilePhysicalMicrosite;

	private String micrositeContent;

	private String titleMicrosite;

	/** imageUrl */
	private String imageUrl;

	/** physicalImg */
	private String physicalImg;

	/** icon name */
	private String iconImg;

	/** physical icon */
	private String physicalIcon;

	/**
	 * Get id
	 * 
	 * @return Long
	 * @author hand
	 */
	public Long getId() {
		return id;
	}

	/**
	 * Set id
	 * 
	 * @param id type Long
	 * @return
	 * @author hand
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * Get productId
	 * 
	 * @return Long
	 * @author hand
	 */
	public Long getProductId() {
		return productId;
	}

	/**
	 * Set productId
	 * 
	 * @param productId type Long
	 * @return
	 * @author hand
	 */
	public void setProductId(Long productId) {
		this.productId = productId;
	}

	/**
	 * Get languageCode
	 * 
	 * @return String
	 * @author hand
	 */
	public String getLanguageCode() {
		return languageCode;
	}

	/**
	 * Set languageCode
	 * 
	 * @param languageCode type String
	 * @return
	 * @author hand
	 */
	public void setLanguageCode(String languageCode) {
		this.languageCode = languageCode;
	}

	/**
	 * Get groupContent
	 * 
	 * @return String
	 * @author hand
	 */
	public String getGroupContent() {
		return groupContent;
	}

	/**
	 * Set groupContent
	 * 
	 * @param groupContent type String
	 * @return
	 * @author hand
	 */
	public void setGroupContent(String groupContent) {
		this.groupContent = groupContent;
	}

	/**
	 * Get content
	 * 
	 * @return String
	 * @author hand
	 */
	public String getContent() {
		return content;
	}

	/**
	 * Set content
	 * 
	 * @param content type String
	 * @return
	 * @author hand
	 */
	public void setContent(String content) {
		this.content = content;
	}

	/**
	 * Get keyContent
	 * 
	 * @return String
	 * @author hand
	 */
	public String getKeyContent() {
		return keyContent;
	}

	/**
	 * Set keyContent
	 * 
	 * @param keyContent type String
	 * @return
	 * @author hand
	 */
	public void setKeyContent(String keyContent) {
		this.keyContent = keyContent;
	}

	/**
	 * Get backgroundUrl
	 * 
	 * @return String
	 * @author hand
	 */
	public String getBackgroundUrl() {
		return backgroundUrl;
	}

	/**
	 * Set backgroundUrl
	 * 
	 * @param backgroundUrl type String
	 * @return
	 * @author hand
	 */
	public void setBackgroundUrl(String backgroundUrl) {
		this.backgroundUrl = backgroundUrl;
	}

	/**
	 * Get backgroundPhysical
	 * 
	 * @return String
	 * @author hand
	 */
	public String getBackgroundPhysical() {
		return backgroundPhysical;
	}

	/**
	 * Set backgroundPhysical
	 * 
	 * @param backgroundPhysical type String
	 * @return
	 * @author hand
	 */
	public void setBackgroundPhysical(String backgroundPhysical) {
		this.backgroundPhysical = backgroundPhysical;
	}

	/**
	 * Get bannerDesktop
	 * 
	 * @return Long
	 * @author taitm
	 */
	public String getBannerDesktopMicrosite() {
		return bannerDesktopMicrosite;
	}

	/**
	 * Set bannerDesktop
	 * 
	 * @param bannerDesktop type Long
	 * @return
	 * @author taitm
	 */
	public void setBannerDesktopMicrosite(String bannerDesktop) {
		this.bannerDesktopMicrosite = bannerDesktop;
	}

	/**
	 * Get bannerMobile
	 * 
	 * @return Long
	 * @author taitm
	 */
	public String getBannerMobileMicrosite() {
		return bannerMobileMicrosite;
	}

	/**
	 * Set bannerMobile
	 * 
	 * @param bannerMobile type Long
	 * @return
	 * @author taitm
	 */
	public void setBannerMobileMicrosite(String bannerMobile) {
		this.bannerMobileMicrosite = bannerMobile;
	}

	/**
	 * Get micrositeContent
	 * 
	 * @return String
	 * @author taitm
	 */
	public String getMicrositeContent() {
		return micrositeContent;
	}

	/**
	 * Set micrositeContent
	 * 
	 * @param micrositeContent type String
	 * @return
	 * @author taitm
	 */
	public void setMicrositeContent(String micrositeContent) {
		this.micrositeContent = micrositeContent;
	}

	public String getTitleMicrosite() {
		return titleMicrosite;
	}

	public void setTitleMicrosite(String titleMicrosite) {
		this.titleMicrosite = titleMicrosite;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public String getPhysicalImg() {
		return physicalImg;
	}

	public void setPhysicalImg(String physicalImg) {
		this.physicalImg = physicalImg;
	}

	public String getIconImg() {
		return iconImg;
	}

	public void setIconImg(String iconImg) {
		this.iconImg = iconImg;
	}

	public String getPhysicalIcon() {
		return physicalIcon;
	}

	public void setPhysicalIcon(String physicalIcon) {
		this.physicalIcon = physicalIcon;
	}

	public String getBannerDesktopPhysicalMicrosite() {
		return bannerDesktopPhysicalMicrosite;
	}

	public void setBannerDesktopPhysicalMicrosite(String bannerDesktopPhysicalMicrosite) {
		this.bannerDesktopPhysicalMicrosite = bannerDesktopPhysicalMicrosite;
	}

	public String getBannerMobilePhysicalMicrosite() {
		return bannerMobilePhysicalMicrosite;
	}

	public void setBannerMobilePhysicalMicrosite(String bannerMobilePhysicalMicrosite) {
		this.bannerMobilePhysicalMicrosite = bannerMobilePhysicalMicrosite;
	}

}
