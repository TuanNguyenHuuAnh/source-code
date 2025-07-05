package vn.com.unit.cms.admin.all.entity;

import org.springframework.data.annotation.Id;

import jp.sf.amateras.mirage.annotation.Column;
import jp.sf.amateras.mirage.annotation.PrimaryKey;
import jp.sf.amateras.mirage.annotation.Table;
import jp.sf.amateras.mirage.annotation.PrimaryKey.GenerationType;
import vn.com.unit.cms.core.entity.AbstractTracking;

@Table(name = "m_currency_language")
public class CurrencyLanguage extends AbstractTracking{
	
	@Id
    @Column(name = "id")
    @PrimaryKey(generationType = GenerationType.SEQUENCE, generator = "SEQ_M_CURRENCY_LANGUAGE")
	private Long id;
	
	@Column(name = "m_currency_id")
	private Long currencyId;
	
	@Column(name = "m_language_code")
	private String languageCode;
	
	@Column(name = "title")
	private String title;

	/**
	 * get id
	 * @return
	 */
	public Long getId() {
		return id;
	}

	/**
	 * set id
	 * @param id
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * get currency id
	 * @return
	 */
	public Long getCurrencyId() {
		return currencyId;
	}

	/**
	 * set currency id
	 * @param currencyId
	 */
	public void setCurrencyId(Long currencyId) {
		this.currencyId = currencyId;
	}

	/**
	 * get language code
	 * @return
	 */
	public String getLanguageCode() {
		return languageCode;
	}

	/**
	 * set language code
	 * @param languageCode
	 */
	public void setLanguageCode(String languageCode) {
		this.languageCode = languageCode;
	}

	/**
	 * get title
	 * @return
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * set title
	 * @param title
	 */
	public void setTitle(String title) {
		this.title = title;
	}
}
