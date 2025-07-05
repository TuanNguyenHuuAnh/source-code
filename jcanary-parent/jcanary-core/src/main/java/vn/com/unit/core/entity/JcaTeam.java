/*******************************************************************************
 * Class        :JcaItem
 * Created date :2020/12/08
 * Lasted date  :2020/12/08
 * Author       :MinhNV
 * Change log   :2020/12/08:01-00 MinhNV create a new
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
 * JcaTeam
 * 
 * @version 01-00
 * @since 01-00
 * @author MinhNV
 */
@Getter
@Setter
@Table(name = CoreConstant.TABLE_JCA_TEAM)
public class JcaTeam  extends AbstractTracking{

    /** Column: ID type NUMBER(20,0) NOT NULL */
    @Id
    @Column(name = "ID")
    @PrimaryKey(generationType = GenerationType.SEQUENCE, generator = CoreConstant.SEQ + CoreConstant.TABLE_JCA_TEAM)
    private Long id;

    /** Column: CODE type VARCHAR2(255) NOT NULL */
    @Column(name = "CODE")
    private String code;

    /** Column: NAME type NVARCHAR2(500) NULL */
    @Column(name = "NAME")
    private String name;

    /** Column: NAME_ABV type NVARCHAR2(255) NULL */
    @Column(name = "NAME_ABV")
    private String nameAbv;

    /** Column: DESCRIPTION type NVARCHAR2(255) NULL */
    @Column(name = "DESCRIPTION")
    private String description;

    /** Column: ACTIVED type NUMBER(1,0) NULL */
    @Column(name = "ACTIVED")
    private Boolean actived;

    /** Column: COMPANY_ID type NUMBER(20,0) NULL */
    @Column(name = "COMPANY_ID")
    private Long companyId;
    
}
