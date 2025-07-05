package vn.com.unit.cms.admin.all.dto;

import java.util.Date;
import java.util.List;

public class InterestRateListDto {
	
	/**
	 * interest rate id
	 */
	private Long interestId;
	
	/**
	 * display date
	 */
	private Date displayDate;
	
	/**
	 * city id
	 */
	private Long cityId;
	
	/**
	 * url
	 */
	private String url;
	
	/**
	 * interest rate list
	 */
	private List<InterestRateDto> interestRateDtoList;
	
	
	/** 
	 *
	 * interest rate language dto list
	 *  
	 */
	private List<InterestRateLanguageDto> interestRateLanguageDtoList;
	
	/**
	 * 
	 * request token
	 * 
	 */
	private String requestToken;
	

	/**
	 * get interest id
	 * @return
	 */
	public Long getInterestId() {
		return interestId;
	}

	/**
	 * set interest id
	 * @param interestId
	 */
	public void setInterestId(Long interestId) {
		this.interestId = interestId;
	}
	
	/**
	 * increase interest id by 1
	 */
	public void increaseInterestId(){
		this.interestId += 1;
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
	 * get city id
	 * @return
	 */
	public Long getCityId() {
		return cityId;
	}

	/**
	 * set city id
	 * @param cityId
	 */
	public void setCityId(Long cityId) {
		this.cityId = cityId;
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

	/**
	 * get Interest rate list
	 * @return
	 */
	public List<InterestRateDto> getInterestRateDtoList() {
		return interestRateDtoList;
	}

	/**
	 * set interest rate list
	 * @param interestRateDtoList
	 */
	public void setInterestRateDtoList(List<InterestRateDto> interestRateDtoList) {
		this.interestRateDtoList = interestRateDtoList;
	}

	/**
	 * get interest rate language dto list
	 * @return
	 */
	public List<InterestRateLanguageDto> getInterestRateLanguageDtoList() {
		return interestRateLanguageDtoList;
	}

	/**
	 * set interest rate language dto list
	 * @param interestRateLanguageDtoList
	 */
	public void setInterestRateLanguageDtoList(List<InterestRateLanguageDto> interestRateLanguageDtoList) {
		this.interestRateLanguageDtoList = interestRateLanguageDtoList;
	}

	/**
	 * get request token
	 * @return
	 */
	public String getRequestToken() {
		return requestToken;
	}

	/**
	 * set request token
	 * @param requestToken
	 */
	public void setRequestToken(String requestToken) {
		this.requestToken = requestToken;
	}
}
