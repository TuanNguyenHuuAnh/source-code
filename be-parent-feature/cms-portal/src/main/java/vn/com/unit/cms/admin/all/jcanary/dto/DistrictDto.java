/*******************************************************************************
 * Class        DistrictDto
 * Created date 2017/02/20
 * Lasted date  2017/02/20
 * Author       TranLTH
 * Change log   2017/02/2001-00 TranLTH create a new
 ******************************************************************************/
package vn.com.unit.cms.admin.all.jcanary.dto;

import java.util.Date;
import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.Size;

import vn.com.unit.cms.core.utils.CmsUtils;

//import vn.com.unit.util.Util;

/**
 * DistrictDto
 * 
 * @version 01-00
 * @since 01-00
 * @author TranLTH
 */
public class DistrictDto {
   
    private Long districtId;
    
    @Size(min=1,max=30)
    private String districtCode;
   
    private String districtName;
    
    private Long mCityId;
    
    private String cityName;
    
    private Long mRegionId;
     
    private String regionName;
    
    private Long mCountryId;    
    
    private String countryName;
    
    private Double latitude;
    
    private Double longtitude;
    
    private String note;
    
    private String description;
    
    private String cityRegionCountry;
    
    private String url;
    
    private Date createDate;      
    
    private Boolean activeFlag;
    
    private String parentDistrict;
    
    private String dType;
    
    private String fieldSearch;

	private List<String> fieldValues;   
    
    @Valid
    private List<DistrictLanguageDto> districtLanguageDtos;

    /**
     * Get districtId
     * @return Long
     * @author TranLTH
     */
    public Long getDistrictId() {
        return districtId;
    }

    /**
     * Set districtId
     * @param   districtId
     *          type Long
     * @return
     * @author  TranLTH
     */
    public void setDistrictId(Long districtId) {
        this.districtId = districtId;
    }

    /**
     * Get districtCode
     * @return String
     * @author TranLTH
     */
    public String getDistrictCode() {
        return districtCode;
    }

    /**
     * Set districtCode
     * @param   districtCode
     *          type String
     * @return
     * @author  TranLTH
     */
    public void setDistrictCode(String districtCode) {
        this.districtCode = CmsUtils.toUppercase(districtCode);
    }

    /**
     * Get districtName
     * @return String
     * @author TranLTH
     */
    public String getDistrictName() {
        return districtName;
    }

    /**
     * Set districtName
     * @param   districtName
     *          type String
     * @return
     * @author  TranLTH
     */
    public void setDistrictName(String districtName) {
        this.districtName = districtName;
    }

    /**
     * Get mCityId
     * @return Long
     * @author TranLTH
     */
    public Long getmCityId() {
        return mCityId;
    }

    /**
     * Set mCityId
     * @param   mCityId
     *          type Long
     * @return
     * @author  TranLTH
     */
    public void setmCityId(Long mCityId) {
        this.mCityId = mCityId;
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
     * Get mRegionId
     * @return Long
     * @author TranLTH
     */
    public Long getmRegionId() {
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
     * Get mCountryId
     * @return Long
     * @author TranLTH
     */
    public Long getmCountryId() {
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
     * Get cityRegionCountry
     * @return String
     * @author TranLTH
     */
    public String getCityRegionCountry() {
        return cityRegionCountry;
    }

    /**
     * Set cityRegionCountry
     * @param   cityRegionCountry
     *          type String
     * @return
     * @author  TranLTH
     */
    public void setCityRegionCountry(String cityRegionCountry) {
        this.cityRegionCountry = cityRegionCountry;
    }

    /**
     * Get districtLanguageDtos
     * @return List<DistrictLanguageDto>
     * @author TranLTH
     */
    public List<DistrictLanguageDto> getDistrictLanguageDtos() {
        return districtLanguageDtos;
    }

    /**
     * Set districtLanguageDtos
     * @param   districtLanguageDtos
     *          type List<DistrictLanguageDto>
     * @return
     * @author  TranLTH
     */
    public void setDistrictLanguageDtos(
            List<DistrictLanguageDto> districtLanguageDtos) {
        this.districtLanguageDtos = districtLanguageDtos;
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

    
    public String getParentDistrict() {
        return parentDistrict;
    }

    
    public void setParentDistrict(String parentDistrict) {
        this.parentDistrict = parentDistrict;
    }

    
    public String getdType() {
        return dType;
    }

    
    public void setdType(String dType) {
        this.dType = dType;
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
