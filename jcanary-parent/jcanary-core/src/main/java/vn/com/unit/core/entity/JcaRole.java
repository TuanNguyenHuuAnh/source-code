/*******************************************************************************
 * Class        ：JcaRole
 * Created date ：2020/12/02
 * Lasted date  ：2020/12/02
 * Author       ：NganNH
 * Change log   ：2020/12/02：01-00 NganNH create a new
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
 * JcaRole
 * 
 * @version 01-00
 * @since 01-00
 * @author NganNH
 */
@Getter
@Setter
@Table(name = CoreConstant.TABLE_JCA_ROLE)
public class JcaRole extends AbstractTracking{
    /** Column: ID type NUMBER(20,0)  NOT NULL */
    @Id
    @Column(name = "ID")
    @PrimaryKey(generationType = GenerationType.SEQUENCE, generator = CoreConstant.SEQ + CoreConstant.TABLE_JCA_ROLE)
    private Long id;

    /** Column: NAME type NVARCHAR2(255)  NOT NULL */
    @Column(name = "NAME")
    private String name;
    
    /** Column: CODE type NVARCHAR2(30)  NOT NULL */
    @Column(name = "CODE")
    private String code;

    /** Column: DESCRIPTION type NVARCHAR2(255) NULL */   
    @Column(name = "DESCRIPTION")
    private String description;

    /** Column: ACTIVE type NUMBER(1,0) NULL */
    @Column(name = "ACTIVED")
    private boolean actived;
    
    /** Column: COMPANY_ID type NUMBER(20,0) NULL */ 
    @Column(name = "COMPANY_ID")
    private Long companyId;

}
