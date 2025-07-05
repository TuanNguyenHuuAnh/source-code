/*******************************************************************************
 * Class        BranchDto
 * Created date 2017/03/10
 * Lasted date  2017/03/10
 * Author       TranLTH
 * Change log   2017/03/1001-00 TranLTH create a new
 ******************************************************************************/
package vn.com.unit.cms.admin.all.jcanary.dto;

import java.util.Date;

import javax.validation.constraints.Size;

import vn.com.unit.cms.core.utils.CmsUtils;

//import vn.com.unit.util.Util;

/**
 * BranchDto
 * 
 * @version 01-00
 * @since 01-00
 * @author TranLTH
 */
public class BranchDto {
   
    private Long id;
     
    @Size(min=1,max=30)
    private String code;
    
    @Size(min=1,max=255)
    private String name;
        
    private String note;
        
    private String address;
        
    private String latitude;
       
    private String longtitude;
        
    private Boolean isPrimary;
        
    private String type;
        
    private String icon;
        
    private String phone;
        
    private String fax;
        
    private String district;
     
    private String city;
    
    private Date createDate;
    
    private String url;
    
    private String districtJsonHidden;
    
    private Long parentId;
    
    private String workingHours;
    
    private String email;
    
    private Boolean activeFlag;
    
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
     * Get isPrimary
     * @return Boolean
     * @author TranLTH
     */
    public Boolean getIsPrimary() {
        return isPrimary;
    }

    /**
     * Set isPrimary
     * @param   isPrimary
     *          type Boolean
     * @return
     * @author  TranLTH
     */
    public void setIsPrimary(Boolean isPrimary) {
        this.isPrimary = isPrimary;
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

    /**
     * Get districtJsonHidden
     * @return String
     * @author TranLTH
     */
    public String getDistrictJsonHidden() {
        return districtJsonHidden;
    }

    /**
     * Set districtJsonHidden
     * @param   districtJsonHidden
     *          type String
     * @return
     * @author  TranLTH
     */
    public void setDistrictJsonHidden(String districtJsonHidden) {
        this.districtJsonHidden = districtJsonHidden;
    }

    /**
     * Get parentId
     * @return Long
     * @author TranLTH
     */
    public Long getParentId() {
        return parentId;
    }

    /**
     * Set parentId
     * @param   parentId
     *          type Long
     * @return
     * @author  TranLTH
     */
    public void setParentId(Long parentId) {
        this.parentId = parentId;
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

    
    public Boolean getActiveFlag() {
        return activeFlag;
    }

    
    public void setActiveFlag(Boolean activeFlag) {
        this.activeFlag = activeFlag;
    }
    
    
}