/*******************************************************************************
 * Class        CountryDto
 * Created date 2017/02/23
 * Lasted date  2017/02/23
 * Author       TranLTH
 * Change log   2017/02/2301-00 TranLTH create a new
 ******************************************************************************/
package vn.com.unit.cms.admin.all.jcanary.dto;

import java.util.Date;
import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.Size;

import vn.com.unit.cms.core.utils.CmsUtils;

//import vn.com.unit.util.Util;

/**
 * CountryDto
 * 
 * @version 01-00
 * @since 01-00
 * @author TranLTH
 */
public class CountryDto {
    private Long countryId;
    
    @Size(min=1,max=30)
    private String countryCode;
   
    private String countryName;
    
    private Double latitude;
    
    private Double longtitude;
    
    private String webCode;
    
    private String phoneCode;
    
    private String note;
    
    private String description;
    
    private String url;
    
    private Date createDate;
    
    private Boolean activeFlag;
    
    private String fieldSearch;

	private List<String> fieldValues;
    
    @Valid
    private List<CountryLanguageDto> countryLanguageDtos;
    
    /**
     * Get countryLanguageDtos
     * @return List<CountryLanguageDto>
     * @author TranLTH
     */
    public List<CountryLanguageDto> getCountryLanguageDtos() {
        return countryLanguageDtos;
    }

    /**
     * Set countryLanguageDtos
     * @param   countryLanguageDtos
     *          type List<CountryLanguageDto>
     * @return
     * @author  TranLTH
     */
    public void setCountryLanguageDtos(
            List<CountryLanguageDto> countryLanguageDtos) {
        this.countryLanguageDtos = countryLanguageDtos;
    }

    /**
     * Get countryId
     * @return Long
     * @author TranLTH
     */
    public Long getCountryId() {
        return countryId;
    }

    /**
     * Set countryId
     * @param   countryId
     *          type Long
     * @return
     * @author  TranLTH
     */
    public void setCountryId(Long countryId) {
        this.countryId = countryId;
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
     * Get countryCode
     * @return String
     * @author TranLTH
     */
    public String getCountryCode() {
        return countryCode;
    }

    /**
     * Set countryCode
     * @param   countryCode
     *          type String
     * @return
     * @author  TranLTH
     */
    public void setCountryCode(String countryCode) {
        this.countryCode = CmsUtils.toUppercase(countryCode);
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
     * Get webCode
     * @return String
     * @author TranLTH
     */
    public String getWebCode() {
        return webCode;
    }

    /**
     * Set webCode
     * @param   webCode
     *          type String
     * @return
     * @author  TranLTH
     */
    public void setWebCode(String webCode) {
        this.webCode = CmsUtils.toUppercase(webCode);
    }

    /**
     * Get phoneCode
     * @return String
     * @author TranLTH
     */
    public String getPhoneCode() {
        return phoneCode;
    }

    /**
     * Set phoneCode
     * @param   phoneCode
     *          type String
     * @return
     * @author  TranLTH
     */
    public void setPhoneCode(String phoneCode) {
        this.phoneCode = CmsUtils.toUppercase(phoneCode);
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