package vn.com.unit.cms.admin.all.dto;

import java.util.Date;

public class InterestRateSearchDto {
	
	/** city id */
	private Long cityId;
	
	private boolean isEdit;
	
	private Date displayDate;
			
	/** language code */
	private String languageCode;
	
	/** searchValue */
	private String searchKeyWord;
	
	/** url */
	private String url;

	public Long getCityId() {
		return cityId;
	}

	public void setCityId(Long cityId) {
		this.cityId = cityId;
	}

	public boolean isEdit() {
		return isEdit;
	}

	public void setEdit(boolean isEdit) {
		this.isEdit = isEdit;
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
	 * get display date
	 * @return
	 */
	public Date getDisplayDate() {
		return displayDate;
	}

	/**
	 * set display date
	 * @param displayDate
	 */
	public void setDisplayDate(Date displayDate) {
		this.displayDate = displayDate;
	}

	/**
	 * get search keyword
	 * @return
	 */
	public String getSearchKeyWord() {
		return searchKeyWord;
	}

	/**
	 * set search keyword
	 * @param searchKeyWord
	 */
	public void setSearchKeyWord(String searchKeyWord) {
		this.searchKeyWord = searchKeyWord;
	}

	/**
	 * get url
	 * @return
	 */
	public String getUrl() {
		return url;
	}

	/**
	 * set url
	 * @param url
	 */
	public void setUrl(String url) {
		this.url = url;
	}
}
