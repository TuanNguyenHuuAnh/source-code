/*******************************************************************************
 * Class        :JcaItem
 * Created date :2020/12/07
 * Lasted date  :2020/12/07
 * Author       :MinhNV
 * Change log   :2020/12/07:01-00 MinhNV create a new
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
 * JcaItem
 * @version 01-00
 * @since 01-00
 * @author MinhNV
 */
@Getter
@Setter
@Table(name = CoreConstant.TABLE_JCA_ITEM)
public class JcaItem extends AbstractTracking{
    
    /** Column: ID type NUMBER(20,0)  NOT NULL */
    @Id
    @Column(name = "ID")
    @PrimaryKey(generationType = GenerationType.SEQUENCE, generator =  CoreConstant.SEQ + CoreConstant.TABLE_JCA_ITEM)
    private Long id;
    
    /** Column: FUNCTION_CODE type VARCHAR2(100)  NOT NULL */
    @Column(name = "FUNCTION_CODE")
    private String functionCode;
    
    /** Column: FUNCTION_NAME type NVARCHAR2(255) NULL */
    @Column(name = "FUNCTION_NAME")
    private String functionName;
    
    /** Column: DESCRIPTION type NVARCHAR2(255) NULL */
    @Column(name = "DESCRIPTION")
    private String description;
    
    /** Column: FUNCTION_TYPE type CHAR(1)  NOT NULL */
    @Column(name = "FUNCTION_TYPE")
    private String functionType;
    
    /** Column: DISPLAY_ORDER type NUMBER(20,0) NULL */
    @Column(name = "DISPLAY_ORDER")
    private int displayOrder;
    
    /** Column: ACTIVED type NUMBER(0,1) NULL */
    @Column(name = "ACTIVED")
    private int actived;

    
    /** Column: COMPANY_ID type NUMBER(20,0) NULL */
    @Column(name = "COMPANY_ID")
    private Long companyId;
}
