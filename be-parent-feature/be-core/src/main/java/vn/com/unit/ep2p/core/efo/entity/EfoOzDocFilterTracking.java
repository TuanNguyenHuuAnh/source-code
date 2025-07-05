/*******************************************************************************
 * Class        ：EfoOzDocFilterIn
 * Created date ：2021/01/13
 * Lasted date  ：2021/01/13
 * Author       ：taitt
 * Change log   ：2021/01/13：01-00 taitt create a new
 ******************************************************************************/
package vn.com.unit.ep2p.core.efo.entity;

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
 * EfoOzDocFilterIn
 * 
 * @version 01-00
 * @since 01-00
 * @author taitt
 */
@Getter
@Setter
@Table(name = CoreConstant.TABLE_EFO_OZ_DOC_FILTER_TRACKING)
public class EfoOzDocFilterTracking extends AbstractTracking{
    /** Column: ID type DECIMAL(20) NOT NULL */
    @Id
    @Column(name = "ID")
    @PrimaryKey(generationType = GenerationType.SEQUENCE, generator = CoreConstant.SEQ + CoreConstant.TABLE_EFO_OZ_DOC_FILTER_TRACKING)
    private Long id;
    
    /** Column: STEP_CODE type NVARCHAR2(255) NOT NULL */
    @Column(name = "STEP_CODE")
    private String stepCode;
    
    /** Column: READ_FLAG type CHAR(1) NOT NULL */
    @Column(name = "READ_FLAG")
    private boolean readFlag;
    
    /** Column: ACCOUNT_ID type NUMBER(20,0) NOT NULL */
    @Column(name = "ACCOUNT_ID")
    private Long accountId;
    
    /** Column: EFO_OZ_DOC_ID type NUMBER(20,0) NOT NULL */
    @Column(name = "EFO_OZ_DOC_ID")
    private Long efoOzDocId;
    
    /** Column: TYPE_TRACKING type CHAR(1) NOT NULL */
    @Column(name = "TYPE_TRACKING")
    private String typeTracking;
    
    /** Column: DEL_FLAG type CHAR(1) NOT NULL */
    @Column(name = "DEL_FLAG")
    private boolean delFlag;
}
