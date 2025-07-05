package vn.com.unit.cms.admin.all.dto;

import java.util.Date;

/**
 * 
 * @author sonnt
 *
 */
public class InterestRateHistoryDto {
	
	/**
	 * id
	 */
	private Long id;
	
	/**
	 * interest rate id
	 */
	private Long interestId;
	
	/**
	 * city id
	 */
	private Long cityId;
	
	/**
	 * city name
	 */
	private String cityName;
	
	/**
	 * update time
	 */
	private Date updateDateTime;

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
	 * get city
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
	 * get city name
	 * @return
	 */
	public String getCityName() {
		return cityName;
	}

	/**
	 * set city name
	 * @param cityName
	 */
	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	/**
	 * get update date
	 * @return
	 */
	public Date getUpdateDateTime() {
		return updateDateTime;
	}

	/**
	 * set update time
	 * @param updateDateTime
	 */
	public void setUpdateDateTime(Date updateDateTime) {
		this.updateDateTime = updateDateTime;
	}
}
