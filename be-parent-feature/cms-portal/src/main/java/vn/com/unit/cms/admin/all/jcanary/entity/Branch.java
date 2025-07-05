/*******************************************************************************
 * Class        Branch
 * Created date 2017/03/10
 * Lasted date  2017/03/10
 * Author       TranLTH
 * Change log   2017/03/1001-00 TranLTH create a new
 ******************************************************************************/
package vn.com.unit.cms.admin.all.jcanary.entity;

import org.springframework.data.annotation.Id;

import jp.sf.amateras.mirage.annotation.Column;
import jp.sf.amateras.mirage.annotation.PrimaryKey;
import jp.sf.amateras.mirage.annotation.Table;
import vn.com.unit.cms.core.entity.AbstractTracking;
import vn.com.unit.cms.core.utils.CmsUtils;
import jp.sf.amateras.mirage.annotation.PrimaryKey.GenerationType;
//import vn.com.unit.jcanary.constant.DatabaseConstant;
//import vn.com.unit.util.Util;

/**
 * Branch
 * 
 * @version 01-00
 * @since 01-00
 * @author TranLTH
 */
@Table(name = "JCA_M_BRANCH") // DatabaseConstant.TABLE_BRANCH
public class Branch extends AbstractTracking {
    @Id
    @Column(name = "id")
    @PrimaryKey(generationType = GenerationType.SEQUENCE, generator = "SEQ_JCA_BRANCH")
    private Long id;
    
    /** Column: code type VARCHAR(30) */
    @Column(name = "code")   
    private String code;
    
    /** Column: name type VARCHAR(255) */
    @Column(name = "name")
    private String name;
    
    /** Column: note type VARCHAR(3000) */
    @Column(name = "note")
    private String note;
    
    /** Column: address type VARCHAR(255) */
    @Column(name = "address")
    private String address;
    
    /** Column: latitude type VARCHAR(200) */
    @Column(name = "latitude")
    private String latitude;
    
    /** Column: longtitude type VARCHAR(200) */
    @Column(name = "longtitude")
    private String longtitude;
    
    /** Column: is_primary type bit(1) */
    @Column(name = "is_primary")
    private boolean is_primary;
    
    /** Column: type type VARCHAR(100) */
    @Column(name = "type")
    private String type;
    
    /** Column: icon type VARCHAR(50) */
    @Column(name = "icon")
    private String icon;
    
    /** Column: phone type VARCHAR(50) */
    @Column(name = "phone")
    private String phone;
    
    /** Column: fax type VARCHAR(50) */
    @Column(name = "fax")
    private String fax;
    
    /** Column: district type VARCHAR(100) */
    @Column(name = "district")
    private String district;
    
    /** Column: city type VARCHAR(100) */
    @Column(name = "city")
    private String city;
    
    @Column(name = "working_hours")
    private String workingHours;
    
    /** Column: email type VARCHAR(255) */
    @Column(name = "email")
    private String email;
    
    @Column(name = "active_flag")
    private String activeFlag;

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
     * Get name
     * @return String
     * @author TranLTH
     */
    public String getName() {
        return name;
    }

    /**
     * Set name
     * @param   name
     *          type String
     * @return
     * @author  TranLTH
     */
    public void setName(String name) {
        this.name = name;
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
     * Get address
     * @return String
     * @author TranLTH
     */
    public String getAddress() {
        return address;
    }

    /**
     * Set address
     * @param   address
     *          type String
     * @return
     * @author  TranLTH
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * Get latitude
     * @return String
     * @author TranLTH
     */
    public String getLatitude() {
        return latitude;
    }

    /**
     * Set latitude
     * @param   latitude
     *          type String
     * @return
     * @author  TranLTH
     */
    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    /**
     * Get longtitude
     * @return String
     * @author TranLTH
     */
    public String getLongtitude() {
        return longtitude;
    }

    /**
     * Set longtitude
     * @param   longtitude
     *          type String
     * @return
     * @author  TranLTH
     */
    public void setLongtitude(String longtitude) {
        this.longtitude = longtitude;
    }

    /**
     * Get is_primary
     * @return boolean
     * @author TranLTH
     */
    public boolean isIs_primary() {
        return is_primary;
    }

    /**
     * Set is_primary
     * @param   is_primary
     *          type boolean
     * @return
     * @author  TranLTH
     */
    public void setIs_primary(boolean is_primary) {
        this.is_primary = is_primary;
    }

    /**
     * Get type
     * @return String
     * @author TranLTH
     */
    public String getType() {
        return type;
    }

    /**
     * Set type
     * @param   type
     *          type String
     * @return
     * @author  TranLTH
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * Get icon
     * @return String
     * @author TranLTH
     */
    public String getIcon() {
        return icon;
    }

    /**
     * Set icon
     * @param   icon
     *          type String
     * @return
     * @author  TranLTH
     */
    public void setIcon(String icon) {
        this.icon = icon;
    }

    /**
     * Get phone
     * @return String
     * @author TranLTH
     */
    public String getPhone() {
        return phone;
    }

    /**
     * Set phone
     * @param   phone
     *          type String
     * @return
     * @author  TranLTH
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }

    /**
     * Get fax
     * @return String
     * @author TranLTH
     */
    public String getFax() {
        return fax;
    }

    /**
     * Set fax
     * @param   fax
     *          type String
     * @return
     * @author  TranLTH
     */
    public void setFax(String fax) {
        this.fax = fax;
    }

    /**
     * Get district
     * @return String
     * @author TranLTH
     */
    public String getDistrict() {
        return district;
    }

    /**
     * Set district
     * @param   district
     *          type String
     * @return
     * @author  TranLTH
     */
    public void setDistrict(String district) {
        this.district = district;
    }

    /**
     * Get city
     * @return String
     * @author TranLTH
     */
    public String getCity() {
        return city;
    }

    /**
     * Set city
     * @param   city
     *          type String
     * @return
     * @author  TranLTH
     */
    public void setCity(String city) {
        this.city = city;
    }

    /**
     * Get workingHours
     * @return String
     * @author hand
     */
    public String getWorkingHours() {
        return workingHours;
    }

    /**
     * Set workingHours
     * @param   workingHours
     *          type String
     * @return
     * @author  hand
     */
    public void setWorkingHours(String workingHours) {
        this.workingHours = workingHours;
    }

    /**
     * Get email
     * @return String
     * @author TranLTH
     */
    public String getEmail() {
        return email;
    }

    /**
     * Set email
     * @param   email
     *          type String
     * @return
     * @author  TranLTH
     */
    public void setEmail(String email) {
        this.email = email;
    }

    
    public String getActiveFlag() {
        return activeFlag;
    }

    
    public void setActiveFlag(String activeFlag) {
        this.activeFlag = activeFlag;
    }
    
    
}