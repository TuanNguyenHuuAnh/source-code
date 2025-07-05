package vn.com.unit.cms.admin.all.entity;

import org.springframework.data.annotation.Id;

import jp.sf.amateras.mirage.annotation.Column;
import jp.sf.amateras.mirage.annotation.PrimaryKey;
import jp.sf.amateras.mirage.annotation.Table;
import jp.sf.amateras.mirage.annotation.PrimaryKey.GenerationType;
import vn.com.unit.cms.core.entity.AbstractTracking;

@Table(name = "m_customer_vip_language")
public class CustomerVipLanguage extends AbstractTracking {
	@Id
	@Column(name = "id")
	@PrimaryKey(generationType = GenerationType.SEQUENCE, generator = "SEQ_M_CUSTOMER_VIP_LANGUAGE")
	private Long id;

	@Column(name = "M_CUSTOMER_VIP_ID")
	private Long customerVipId;;

	@Column(name = "m_language_code")
	private String languageCode;

	@Column(name = "title")
	private String title;

	@Column(name = "SHORT_CONTENT")
	private String shortContent;

	@Column(name = "CONTENT")
	private String content;

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @return the customerVipId
	 */
	public Long getCustomerVipId() {
		return customerVipId;
	}

	/**
	 * @param customerVipId the customerVipId to set
	 */
	public void setCustomerVipId(Long customerVipId) {
		this.customerVipId = customerVipId;
	}

	/**
	 * @return the languageCode
	 */
	public String getLanguageCode() {
		return languageCode;
	}

	/**
	 * @param languageCode the languageCode to set
	 */
	public void setLanguageCode(String languageCode) {
		this.languageCode = languageCode;
	}

	/**
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * @param title the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * @return the shortContent
	 */
	public String getShortContent() {
		return shortContent;
	}

	/**
	 * @param shortContent the shortContent to set
	 */
	public void setShortContent(String shortContent) {
		this.shortContent = shortContent;
	}

	/**
	 * @return the content
	 */
	public String getContent() {
		return content;
	}

	/**
	 * @param content the content to set
	 */
	public void setContent(String content) {
		this.content = content;
	}

}
