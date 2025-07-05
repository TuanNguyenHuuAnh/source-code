/*******************************************************************************
 * Class        OrgInfo
 * Created date 2017/02/14
 * Lasted date  2017/02/14
 * Author       KhoaNA
 * Change log   2017/02/1401-00 KhoaNA create a new
 ******************************************************************************/
package vn.com.unit.cms.admin.all.jcanary.entity;

import java.util.Date;
import org.springframework.data.annotation.Id;
import jp.sf.amateras.mirage.annotation.Column;
import jp.sf.amateras.mirage.annotation.PrimaryKey;
import jp.sf.amateras.mirage.annotation.PrimaryKey.GenerationType;
import jp.sf.amateras.mirage.annotation.Table;
//import vn.com.unit.jcanary.constant.DatabaseConstant;
import vn.com.unit.cms.core.entity.AbstractTracking;

/**
 * OrgInfo
 * 
 * @version 01-00
 * @since 01-00
 * @author KhoaNA
 */
@Table(name = "JCA_M_ORG") // DatabaseConstant.TABLE_ORG_INFO
public class OrgInfo extends AbstractTracking {
    
    /** Column: id bigint(20) NOT NULL */
    @Id
    @Column(name = "id")
    @PrimaryKey(generationType = GenerationType.SEQUENCE, generator = "SEQ_JCA_ORG")
    private Long id;
    
    /** Column: org_code varchar(50) NOT NULL */
    @Column(name = "org_code")
    private String orgCode;
    
    /** Column: org_name varchar(255) NOT NULL */
    @Column(name = "org_name")
    private String orgName;
    
    /** Column: org_name_abv varchar(125) NULL */
    @Column(name = "org_name_abv")
    private String orgNameAbv;
    
    /** Column: effected_date datetime NOT NULL */
    @Column(name = "effected_date")
    private Date effectedDate;
    
    /** Column: expired_date datetime NOT NULL */
    @Column(name = "expired_date")
    private Date expiredDate;
    
    /** Column: parent_org_id bigint(20) NULL */
    @Column(name = "parent_org_id")
    private Long parentOrgId;
    
    /** Column: org_level int(11) NOT NULL */
    @Column(name = "org_level")
    private Integer orgLevel;
    
    /** Column: order_by int(11) NOT NULL */
    @Column(name = "order_by")
    private Integer orderBy;
    
    /** Column: org_tree_id varchar(255) NOT NULL */
    @Column(name = "org_tree_id")
    private String orgTreeId;
    
    /** Column: org_type char(1) NOT NULL */
    @Column(name = "org_type")
    private String orgType;
    
    /** Column: del_flg TINYNT */
    @Column(name = "del_flg")
    private Boolean delFlg;
    
    /** Column: email varchar(255) NULL */
    @Column(name = "email")
    private String email;
    
    /** Column: phone varchar(20) NULL */
    @Column(name = "phone")
    private String phone;
    
    /** Column: address varchar(1000) NULL */
    @Column(name = "address")
    private String address;
    
    /** Column: surrogate varchar(255) NULL */
    @Column(name = "surrogate")
    private String surrogate;
    
    /** Column: org_sub_type_1 varchar(50) NULL */
    @Column(name = "org_sub_type_1")
    private String orgSubType1;
    
    /** Column: org_sub_type_2 varchar(50) NULL */
    @Column(name = "org_sub_type_2")
    private String orgSubType2;
    
    /** Column: org_sub_type_3 varchar(50) NULL */
    @Column(name = "org_sub_type_3")
    private String orgSubType3;
    
    /** Column: city_id bigint(20) NOT NULL */
    @Column(name = "city_id")
    private Long cityId;

    /** Column: latitude varchar(200) NULL */
    @Column(name = "latitude")
    private String latitude;
    
    /** Column: longtitude varchar(200) NULL */
    @Column(name = "longtitude")
    private String longtitude;
    
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
     * Get id
     * @return Long
     * @author KhoaNA
     */
    public Long getId() {
        return id;
    }

    /**
     * Set id
     * @param   id
     *          type Long
     * @return
     * @author  KhoaNA
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Get orgCode
     * @return String
     * @author KhoaNA
     */
    public String getOrgCode() {
        return orgCode;
    }

    /**
     * Set orgCode
     * @param   orgCode
     *          type String
     * @return
     * @author  KhoaNA
     */
    public void setOrgCode(String orgCode) {
        this.orgCode = orgCode;
    }

    /**
     * Get orgName
     * @return String
     * @author KhoaNA
     */
    public String getOrgName() {
        return orgName;
    }

    /**
     * Set orgName
     * @param   orgName
     *          type String
     * @return
     * @author  KhoaNA
     */
    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    /**
     * Get orgNameAbv
     * @return String
     * @author KhoaNA
     */
    public String getOrgNameAbv() {
        return orgNameAbv;
    }

    /**
     * Set orgNameAbv
     * @param   orgNameAbv
     *          type String
     * @return
     * @author  KhoaNA
     */
    public void setOrgNameAbv(String orgNameAbv) {
        this.orgNameAbv = orgNameAbv;
    }

    /**
     * Get effectedDate
     * @return Date
     * @author KhoaNA
     */
    public Date getEffectedDate() {
        return effectedDate;
    }

    /**
     * Set effectedDate
     * @param   effectedDate
     *          type Date
     * @return
     * @author  KhoaNA
     */
    public void setEffectedDate(Date effectedDate) {
        this.effectedDate = effectedDate;
    }

    /**
     * Get expiredDate
     * @return Date
     * @author KhoaNA
     */
    public Date getExpiredDate() {
        return expiredDate;
    }

    /**
     * Set expiredDate
     * @param   expiredDate
     *          type Date
     * @return
     * @author  KhoaNA
     */
    public void setExpiredDate(Date expiredDate) {
        this.expiredDate = expiredDate;
    }

    /**
     * Get parentOrgId
     * @return Long
     * @author KhoaNA
     */
    public Long getParentOrgId() {
        return parentOrgId;
    }

    /**
     * Set parentOrgId
     * @param   parentOrgId
     *          type Long
     * @return
     * @author  KhoaNA
     */
    public void setParentOrgId(Long parentOrgId) {
        this.parentOrgId = parentOrgId;
    }

    /**
     * Get orgLevel
     * @return Integer
     * @author KhoaNA
     */
    public Integer getOrgLevel() {
        return orgLevel;
    }

    /**
     * Set orgLevel
     * @param   orgLevel
     *          type Integer
     * @return
     * @author  KhoaNA
     */
    public void setOrgLevel(Integer orgLevel) {
        this.orgLevel = orgLevel;
    }

    /**
     * Get orderBy
     * @return Integer
     * @author KhoaNA
     */
    public Integer getOrderBy() {
        return orderBy;
    }

    /**
     * Set orderBy
     * @param   orderBy
     *          type Integer
     * @return
     * @author  KhoaNA
     */
    public void setOrderBy(Integer orderBy) {
        this.orderBy = orderBy;
    }

    /**
     * Get orgTreeId
     * @return String
     * @author KhoaNA
     */
    public String getOrgTreeId() {
        return orgTreeId;
    }

    /**
     * Set orgTreeId
     * @param   orgTreeId
     *          type String
     * @return
     * @author  KhoaNA
     */
    public void setOrgTreeId(String orgTreeId) {
        this.orgTreeId = orgTreeId;
    }

    /**
     * Get orgType
     * @return String
     * @author KhoaNA
     */
    public String getOrgType() {
        return orgType;
    }

    /**
     * Set orgType
     * @param   orgType
     *          type String
     * @return
     * @author  KhoaNA
     */
    public void setOrgType(String orgType) {
        this.orgType = orgType;
    }

    /**
     * Get email
     * @return String
     * @author KhoaNA
     */
    public String getEmail() {
        return email;
    }

    /**
     * Set email
     * @param   email
     *          type String
     * @return
     * @author  KhoaNA
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Get phone
     * @return String
     * @author KhoaNA
     */
    public String getPhone() {
        return phone;
    }

    /**
     * Set phone
     * @param   phone
     *          type String
     * @return
     * @author  KhoaNA
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }

    /**
     * Get address
     * @return String
     * @author KhoaNA
     */
    public String getAddress() {
        return address;
    }

    /**
     * Set address
     * @param   address
     *          type String
     * @return
     * @author  KhoaNA
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * Get surrogate
     * @return String
     * @author KhoaNA
     */
    public String getSurrogate() {
        return surrogate;
    }

    /**
     * Set surrogate
     * @param   surrogate
     *          type String
     * @return
     * @author  KhoaNA
     */
    public void setSurrogate(String surrogate) {
        this.surrogate = surrogate;
    }

    /**
     * Get orgSubType1
     * @return String
     * @author KhoaNA
     */
    public String getOrgSubType1() {
        return orgSubType1;
    }

    /**
     * Set orgSubType1
     * @param   orgSubType1
     *          type String
     * @return
     * @author  KhoaNA
     */
    public void setOrgSubType1(String orgSubType1) {
        this.orgSubType1 = orgSubType1;
    }

    /**
     * Get orgSubType2
     * @return String
     * @author KhoaNA
     */
    public String getOrgSubType2() {
        return orgSubType2;
    }

    /**
     * Set orgSubType2
     * @param   orgSubType2
     *          type String
     * @return
     * @author  KhoaNA
     */
    public void setOrgSubType2(String orgSubType2) {
        this.orgSubType2 = orgSubType2;
    }

    /**
     * Get orgSubType3
     * @return String
     * @author KhoaNA
     */
    public String getOrgSubType3() {
        return orgSubType3;
    }

    /**
     * Set orgSubType3
     * @param   orgSubType3
     *          type String
     * @return
     * @author  KhoaNA
     */
    public void setOrgSubType3(String orgSubType3) {
        this.orgSubType3 = orgSubType3;
    }

    /**
     * Get cityId
     * @return Long
     * @author KhoaNA
     */
    public Long getCityId() {
        return cityId;
    }

    /**
     * Set cityId
     * @param   cityId
     *          type Long
     * @return
     * @author  KhoaNA
     */
    public void setCityId(Long cityId) {
        this.cityId = cityId;
    }

    /**
     * Get delFlg
     * @return Boolean
     * @author trieunh <trieunh@unit.com.vn>
     */
    public Boolean getDelFlg() {
        return delFlg;
    }

    /**
     * Set delFlg
     * @param   delFlg
     *          type Boolean
     * @return
     * @author  trieunh <trieunh@unit.com.vn>
     */
    public void setDelFlg(Boolean delFlg) {
        this.delFlg = delFlg;
    }

}
