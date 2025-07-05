/*******************************************************************************
 * Class        :JcaPosition
 * Created date :2020/12/01
 * Lasted date  :2020/12/01
 * Author       :MinhNV
 * Change log   :2020/12/01:01-00 MinhNV create a new
 ******************************************************************************/
package vn.com.unit.core.entity;

import org.springframework.data.annotation.Id;

import jp.sf.amateras.mirage.annotation.Column;
import jp.sf.amateras.mirage.annotation.PrimaryKey;
import jp.sf.amateras.mirage.annotation.PrimaryKey.GenerationType;
import jp.sf.amateras.mirage.annotation.Table;
import lombok.Getter;
import lombok.Setter;
import vn.com.unit.core.constant.CoreConstant;
import vn.com.unit.db.entity.AbstractTracking;

/**
 * JcaPosition
 * 
 * @version 01-00
 * @since 01-00
 * @author MinhNV
 */

@Getter
@Setter
@Table(name = CoreConstant.TABLE_JCA_POSITION)
public class JcaPosition extends AbstractTracking {

    /** Column: id type NUMBER(20,0) NOT NULL */
    @Id
    @Column(name = "ID")
    @PrimaryKey(generationType = GenerationType.SEQUENCE, generator = CoreConstant.SEQ + CoreConstant.TABLE_JCA_POSITION)
    private Long id;

    /** Column: name type NVARCHAR2(255) NOT NULL */
    @Column(name = "NAME")
    public String name;

    /** Column: name_abv type NVARCHAR2(255) NULL */
    @Column(name = "NAME_ABV")
    public String nameAbv;

    /** Column: description type NVARCHAR2(2000) NULL */
    @Column(name = "DESCRIPTION")
    private String description;

    /** Column: note type NVARCHAR2(2000) NULL */
//    @Column(name = "NOTE")
//    private String note;
    

    /** Column: active type NUMBER(1,0) NOT NULL */
    @Column(name = "ACTIVED")
    private Boolean actived;

    /** Column: code type VARCHAR2(30) NULL */
    @Column(name = "CODE")
    public String code;

    /** Column: company_id type NUMBER(20,0) NULL */
    @Column(name = "COMPANY_ID")
    private Long companyId;
//    
//    /** Column: POSITION_LEVEL type decimal(11,0) NULL */
//    @Column(name = "POSITION_LEVEL")
//    private Integer positionLevel;
    
    /** Column: POSITION_ORDER type decimal(11,0) NULL */
    @Column(name = "DISPLAY_ORDER")
    private Integer displayOrder;
}
