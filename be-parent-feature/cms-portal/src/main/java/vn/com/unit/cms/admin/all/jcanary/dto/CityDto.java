/*******************************************************************************
 * Class        CityDto
 * Created date 2017/02/17
 * Lasted date  2017/02/17
 * Author       TranLTH
 * Change log   2017/02/1701-00 TranLTH create a new
 ******************************************************************************/
package vn.com.unit.cms.admin.all.jcanary.dto;

import java.util.Date;
import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.Size;

import vn.com.unit.cms.core.utils.CmsUtils;

//import vn.com.unit.util.Util;

/**
 * CityDto
 * 
 * @version 01-00
 * @since 01-00
 * @author TranLTH
 */
public class CityDto {
    
    private Long cityId;
    
    @Size(min=1,max=30)
    public String cityCode;
    
    private String cityName;
        
    private Long mCountryId;
        
    private String countryName;
        
    private Long mRegionId;
        
    private String regionName;
    
    private Double latitude;
    
    private Double longtitude;
    
    private String note;
    
    private String description;
    
    private String regionCountry;
    
    private String url;
    
    private Date createDate;
    
    private Boolean activeFlag;
    
    private String parentCity;
    
    private String cType;
    
    private String phoneCode;
    
    private String zipCode;
    
    private String shipCode;
    
    private String carCode;
    
    private String fieldSearch;

	private List<String> fieldValues;   
    
    @Valid
    private List<CityLanguageDto> cityLanguageDtos;

    /**
     * Get cityId
     * @return Long
     * @author TranLTH
     */
    public Long getCityId() {
        return cityId;
    }

    /**
     * Set cityId
     * @param   cityId
     *          type Long
     * @return
     * @author  TranLTH
     */
    public void setCityId(Long cityId) {
        this.cityId = cityId;
    }

    /**
     * Get cityCode
     * @return String
     * @author TranLTH
     */
    public String getCityCode() {
        return cityCode;
    }

    /**
     * Set cityCode
     * @param   cityCode
     *          type String
     * @return
     * @author  TranLTH
     */
    public void setCityCode(String cityCode) {
        this.cityCode = CmsUtils.toUppercase(cityCode);
    }
    
    /**
     * Get cityName
     * @return String
     * @author TranLTH
     */
    public String getCityName() {
        return cityName;
    }

    /**
     * Set cityName
     * @param   cityName
     *          type String
     * @return
     * @author  TranLTH
     */
    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    /**
     * Get mCountryId
     * @return Long
     * @author TranLTH
     */
    public Long getmCountryId() {
        return getMCountryId();
    }
    
    public Long getMCountryId() {
        return mCountryId;
    }

    /**
     * Set mCountryId
     * @param   mCountryId
     *          type Long
     * @return
     * @author  TranLTH
     */
    public void setmCountryId(Long mCountryId) {
        this.mCountryId = mCountryId;
    }

    /**
     * Get countryName
     * @return String
     * @author TranLTH
     */
    public String getCountryName() {
        return countryName;
    }

    /**
     * Set countryName
     * @param   countryName
     *          type String
     * @return
     * @author  TranLTH
     */
    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    /**
     * Get mRegionId
     * @return Long
     * @author TranLTH
     */
    public Long getmRegionId() {
        return getMRegionId();
    }
    
    public Long getMRegionId() {
        return mRegionId;
    }

    /**
     * Set mRegionId
     * @param   mRegionId
     *          type Long
     * @return
     * @author  TranLTH
     */
    public void setmRegionId(Long mRegionId) {
        this.mRegionId = mRegionId;
    }

    /**
     * Get regionName
     * @return String
     * @author TranLTH
     */
    public String getRegionName() {
        return regionName;
    }

    /**
     * Set regionName
     * @param   regionName
     *          type String
     * @return
     * @author  TranLTH
     */
    public void setRegionName(String regionName) {
        this.regionName = regionName;
    }

    /**
     * Get latitude
     * @return Double
     * @author TranLTH
     */
    public Double getLatitude() {
        return latitude;
    }

    /**
     * Set latitude
     * @param   latitude
     *          type Double
     * @return
     * @author  TranLTH
     */
    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    /**
     * Get longtitude
     * @return Double
     * @author TranLTH
     */
    public Double getLongtitude() {
        return longtitude;
    }

    /**
     * Set longtitude
     * @param   longtitude
     *          type Double
     * @return
     * @author  TranLTH
     */
    public void setLongtitude(Double longtitude) {
        this.longtitude = longtitude;
    }

    /**
     * Get note
     * @return String
     * @author TranLTH
     */
    public String getNote() {
        return note;
    }

    /**
     * Set note
     * @param   note
     *          type String
     * @return
     * @author  TranLTH
     */
    public void setNote(String note) {
        this.note = note;
    }

    /**
     * Get description
     * @return String
     * @author TranLTH
     */
    public String getDescription() {
        return description;
    }

    /**
     * Set description
     * @param   description
     *          type String
     * @return
     * @author  TranLTH
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Get regionCountry
     * @return String
     * @author TranLTH
     */
    public String getRegionCountry() {
        return regionCountry;
    }

    /**
     * Set regionCountry
     * @param   regionCountry
     *          type String
     * @return
     * @author  TranLTH
     */
    public void setRegionCountry(String regionCountry) {
        this.regionCountry = regionCountry;
    }

    /**
     * Get cityLanguageDtos
     * @return List<CityLanguageDto>
     * @author TranLTH
     */
    public List<CityLanguageDto> getCityLanguageDtos() {
        return cityLanguageDtos;
    }

    /**
     * Set cityLanguageDtos
     * @param   cityLanguageDtos
     *          type List<CityLanguageDto>
     * @return
     * @author  TranLTH
     */
    public void setCityLanguageDtos(List<CityLanguageDto> cityLanguageDtos) {
        this.cityLanguageDtos = cityLanguageDtos;
    }

    /**
     * Get url
     * @return String
     * @author TranLTH
     */
    public String getUrl() {
        return url;
    }

    /**
     * Set url
     * @param   url
     *          type String
     * @return
     * @author  TranLTH
     */
    public void setUrl(String url) {
        this.url = url;
    }

    /**
     * Get createDate
     * @return Date
     * @author TranLTH
     */
    public Date getCreateDate() {
        return createDate;
    }

    /**
     * Set createDate
     * @param   createDate
     *          type Date
     * @return
     * @author  TranLTH
     */
    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    
    public Boolean getActiveFlag() {
        return activeFlag;
    }

    
    public void setActiveFlag(Boolean activeFlag) {
        this.activeFlag = activeFlag;
    }

    
    public String getParentCity() {
        return parentCity;
    }

    
    public void setParentCity(String parentCity) {
        this.parentCity = parentCity;
    }

    
    public String getcType() {
        return cType;
    }

    
    public void setcType(String cType) {
        this.cType = cType;
    }

    
    public String getPhoneCode() {
        return phoneCode;
    }

    
    public void setPhoneCode(String phoneCode) {
        this.phoneCode = phoneCode;
    }

    
    public String getZipCode() {
        return zipCode;
    }

    
    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    
    public String getShipCode() {
        return shipCode;
    }

    
    public void setShipCode(String shipCode) {
        this.shipCode = shipCode;
    }

    
    public String getCarCode() {
        return carCode;
    }

    
    public void setCarCode(String carCode) {
        this.carCode = carCode;
    }        
    
    public String getFieldSearch() {
		return fieldSearch;
	}

	public void setFieldSearch(String fieldSearch) {
		this.fieldSearch = fieldSearch;
	}

	public List<String> getFieldValues() {
		return fieldValues;
	}

	public void setFieldValues(List<String> fieldValues) {
		this.fieldValues = fieldValues;
	}
	
}
