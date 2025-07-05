/*******************************************************************************
 * Class        ：JcaDatatableConfig
 * Created date ：2021/01/27
 * Lasted date  ：2021/01/27
 * Author       ：vinhlt
 * Change log   ：2021/01/27：01-00 vinhlt create a new
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
 * JcaDatatableConfig
 * 
 * @version 01-00
 * @since 01-00
 * @author vinhlt
 */
@Getter
@Setter
@Table(name = CoreConstant.TABLE_JCA_DATATABLE_CONFIG)
public class JcaDatatableConfig extends AbstractTracking{
    /** Column: ID type NUMBER(20,0) NOT NULL */
    @Id
    @Column(name = "ID")
    @PrimaryKey(generationType = GenerationType.SEQUENCE, generator = CoreConstant.SEQ + CoreConstant.TABLE_JCA_DATATABLE_CONFIG)
    private Long id;

    /** Column: USER_ID type NUMBER(20,0) NULL */
    @Column(name = "USER_ID")
    private Long userId;

    /** Column: JSON_CONFIG type NCLOB NULL */
    @Column(name = "JSON_CONFIG")
    private String jsonConfig;

    /** Column: FUNCTION_CODE type NVARCHAR2(100) NULL */
    @Column(name = "FUNCTION_CODE")
    private String functionCode;
}
