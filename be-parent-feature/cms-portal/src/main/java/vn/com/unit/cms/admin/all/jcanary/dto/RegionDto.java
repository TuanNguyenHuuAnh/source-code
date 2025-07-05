/*******************************************************************************
 * Class        jCaMRegionDto
 * Created date 2017/02/14
 * Lasted date  2017/02/14
 * Author       TranLTH
 * Change log   2017/02/1401-00 TranLTH create a new
 ******************************************************************************/
package vn.com.unit.cms.admin.all.jcanary.dto;

import java.util.Date;
import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.Size;

import vn.com.unit.cms.core.utils.CmsUtils;

//import vn.com.unit.util.Util;

/**
 * jCaM
 * @version 01-00
 * @since 01-00
 * @author TranLTH
 */
public class RegionDto {  
    
    private Long regionId;
    
    @Size(min=1,max=30)
    private String regionCode;
    
    private String regionName;
    
    private Long mCountryId;
    
    private String countryName;
    
    private String note;
    
    private String description;
    
    private String url;
    
    private Date createDate;
    
    private Boolean activeFlag;
    
    private String fieldSearch;

	private List<String> fieldValues;  
    
    @Valid
    private List<RegionLanguageDto> regionLanguageDtos;
    
    
    /**
     * Get regionLanguageDtos
     * @return List<RegionLanguageDto>
     * @author TranLTH
     */
    public List<RegionLanguageDto> getRegionLanguageDtos() {
        return regionLanguageDtos;
    }

    /**
     * Set regionLanguageDtos
     * @param   regionLanguageDtos
     *          type List<RegionLanguageDto>
     * @return
     * @author  TranLTH
     */
    public void setRegionLanguageDtos(List<RegionLanguageDto> regionLanguageDtos) {
        this.regionLanguageDtos = regionLanguageDtos;
    }

    /**
     * Get regionId
     * @return Long
     * @author TranLTH
     */
    public Long getRegionId() {
        return regionId;
    }

    /**
     * Set regionId
     * @param   regionId
     *          type Long
     * @return
     * @author  TranLTH
     */
    public void setRegionId(Long regionId) {
        this.regionId = regionId;
    }

    /**
     * Get regionCode
     * @return String
     * @author TranLTH
     */
    public String getRegionCode() {
        return regionCode;
    }

    /**
     * Set regionCode
     * @param   regionCode
     *          type String
     * @return
     * @author  TranLTH
     */
    public void setRegionCode(String regionCode) {
        this.regionCode = CmsUtils.toUppercase(regionCode);
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