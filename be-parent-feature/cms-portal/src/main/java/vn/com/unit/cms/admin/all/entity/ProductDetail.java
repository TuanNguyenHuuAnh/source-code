/*******************************************************************************
 * Class        ：ProductDetail
 * Created date ：2017/06/14
 * Lasted date  ：2017/06/14
 * Author       ：hand
 * Change log   ：2017/06/14：01-00 hand create a new
 ******************************************************************************/
package vn.com.unit.cms.admin.all.entity;

import org.springframework.data.annotation.Id;

import jp.sf.amateras.mirage.annotation.Column;
import jp.sf.amateras.mirage.annotation.PrimaryKey;
import jp.sf.amateras.mirage.annotation.PrimaryKey.GenerationType;
import jp.sf.amateras.mirage.annotation.Table;
import vn.com.unit.cms.core.entity.AbstractTracking;

/**
 * ProductDetail
 * 
 * @version 01-00
 * @since 01-00
 * @author hand
 */
@Table(name = "m_product_detail")
public class ProductDetail extends AbstractTracking {
	@Id
	@Column(name = "id")
	@PrimaryKey(generationType = GenerationType.SEQUENCE, generator = "SEQ_M_PRODUCT_DETAIL")
	private Long id;

	@Column(name = "m_product_id")
	private Long productId;

	@Column(name = "m_language_code")
	private String languageCode;

	@Column(name = "content")
	private String content;

	@Column(name = "key_content")
	private String keyContent;

	@Column(name = "background_url")
	private String backgroundUrl;

	@Column(name = "background_physical")
	private String backgroundPhysical;

	@Column(name = "group_content")
	private String groupContent;

	@Column(name = "microsite_content")
	private String micrositeContent;

	@Column(name = "microsite_banner_desktop")
	private String micrositeBannerDesktop;

	@Column(name = "microsite_banner_physical_desktop")
	private String micrositeBannerPhysicalDesktop;

	@Column(name = "microsite_banner_mobile")
	private String micrositeBannerMobile;

	@Column(name = "microsite_banner_physical_mobile")
	private String micrositeBannerPhysicalMobile;

	@Column(name = "microsite_title")
	private String title;

	@Column(name = "MICROSITE_IMAGE_URL")
	private String imageUrl;

	@Column(name = "microsite_physical_img")
	private String physicalImg;

	@Column(name = "microsite_icon_img")
	private String iconImg;

	@Column(name = "microsite_physical_icon")
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

	/**
	 * Get micrositeBannerDesktop
	 * 
	 * @return Long
	 * @author taitm
	 */
	public String getMicrositeBannerDesktop() {
		return micrositeBannerDesktop;
	}

	/**
	 * Set micrositeBannerDesktop
	 * 
	 * @param micrositeBannerDesktop type Long
	 * @return
	 * @author taitm
	 */
	public void setMicrositeBannerDesktop(String micrositeBannerDesktop) {
		this.micrositeBannerDesktop = micrositeBannerDesktop;
	}

	/**
	 * Get micrositeBannerMobile
	 * 
	 * @return Long
	 * @author taitm
	 */
	public String getMicrositeBannerMobile() {
		return micrositeBannerMobile;
	}

	/**
	 * Set micrositeBannerMobile
	 * 
	 * @param micrositeBannerMobile type Long
	 * @return
	 * @author taitm
	 */
	public void setMicrositeBannerMobile(String micrositeBannerMobile) {
		this.micrositeBannerMobile = micrositeBannerMobile;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
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

	public String getMicrositeBannerPhysicalDesktop() {
		return micrositeBannerPhysicalDesktop;
	}

	public void setMicrositeBannerPhysicalDesktop(String micrositeBannerPhysicalDesktop) {
		this.micrositeBannerPhysicalDesktop = micrositeBannerPhysicalDesktop;
	}

	public String getMicrositeBannerPhysicalMobile() {
		return micrositeBannerPhysicalMobile;
	}

	public void setMicrositeBannerPhysicalMobile(String micrositeBannerPhysicalMobile) {
		this.micrositeBannerPhysicalMobile = micrositeBannerPhysicalMobile;
	}

}
