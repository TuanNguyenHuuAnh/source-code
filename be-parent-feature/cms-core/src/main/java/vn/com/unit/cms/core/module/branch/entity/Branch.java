package vn.com.unit.cms.core.module.branch.entity;

import org.springframework.data.annotation.Id;

import jp.sf.amateras.mirage.annotation.Column;
import jp.sf.amateras.mirage.annotation.PrimaryKey;
import jp.sf.amateras.mirage.annotation.Table;
import vn.com.unit.cms.core.entity.AbstractTracking;
import jp.sf.amateras.mirage.annotation.PrimaryKey.GenerationType;
//import vn.com.unit.jcanary.constant.DatabaseConstant;
//import vn.com.unit.util.Util;
import lombok.Getter;
import lombok.Setter;

/**
 * Branch
 * 
 * @version 01-00
 * @since 01-00
 * @author TranLTH
 */
@Getter
@Setter
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

}