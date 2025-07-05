/*******************************************************************************
 * Class        City
 * Created date 2017/02/17
 * Lasted date  2017/02/17
 * Author       TranLTH
 * Change log   2017/02/1701-00 TranLTH create a new
 ******************************************************************************/
package vn.com.unit.cms.admin.all.jcanary.entity;

import org.springframework.data.annotation.Id;

import jp.sf.amateras.mirage.annotation.Column;
import jp.sf.amateras.mirage.annotation.PrimaryKey;
import jp.sf.amateras.mirage.annotation.PrimaryKey.GenerationType;
//import vn.com.unit.jcanary.constant.DatabaseConstant;
//import vn.com.unit.util.Util;
import jp.sf.amateras.mirage.annotation.Table;
import vn.com.unit.cms.core.entity.AbstractTracking;
import vn.com.unit.cms.core.utils.CmsUtils;

/**
 * City
 * 
 * @version 01-00
 * @since 01-00
 * @author TranLTH
 */
@Table(name = "JCA_M_CITY") // DatabaseConstant.TABLE_CITY
public class City extends AbstractTracking {
    @Id
    @Column(name = "id")
    @PrimaryKey(generationType = GenerationType.SEQUENCE, generator = "SEQ_JCA_CITY")
    public Long id;
    
    /** Column: code type VARCHAR(50) */
    @Column(name = "code")   
    public String code;
    
    /** Column: m_country_id type BIGINT(11) */
    @Column(name = "m_country_id")
    public Long mCountryId;
    
    /** Column: m_region_id type BIGINT (11) */
    @Column(name = "m_region_id")
    public Long mRegionId;   
    
    /** Column: latitude type DOUBLE  */
    @Column(name = "latitude")
    public Double latitude;
    
    /** Column: longtitude type DOUBLE */
    @Column(name = "longtitude")
    public Double longtitude;
    
    /** Column: note type TEXT(0) */
    @Column(name = "note")
    private String note;
    
    /** Column: description type TEXT(0) */
    @Column(name = "description")
    private String description;
    
    @Column(name = "active_flag")
    private String activeFlag;
    
    @Column(name = "phone_code")
    private String phoneCode;
    
    @Column(name = "zip_code")
    private String zipCode;
    
    @Column(name = "ship_code")
    private String shipCode;
    
    @Column(name = "car_code")
    private String carCode;
    
    @Column(name = "ctype")
    private String cType;
    
    @Column(name = "parent_city_id")
    public Long parentCityId;
    
    public City() {
    }
    
    /**
     * Get id
     * @return Long
     * @author TranLTH
     */
    public Long getId() {
        return id;
    }

    /**
     * Set id
     * @param   id
     *          type Long
     * @return
     * @author  TranLTH
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Get code
     * @return String
     * @author TranLTH
     */
    public String getCode() {
        return code;
    }

    /**
     * Set code
     * @param   code
     *          type String
     * @return
     * @author  TranLTH
     */
    public void setCode(String code) {
        this.code = CmsUtils.toUppercase(code);
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

    
    public String getActiveFlag() {
        return activeFlag;
    }

    
    public void setActiveFlag(String activeFlag) {
        this.activeFlag = activeFlag;
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

    
    public String getcType() {
        return cType;
    }

    
    public void setcType(String cType) {
        this.cType = cType;
    }

    
    public Long getParentCityId() {
        return parentCityId;
    }

    
    public void setParentCityId(Long parentCityId) {
        this.parentCityId = parentCityId;
    } 
    
    
}
