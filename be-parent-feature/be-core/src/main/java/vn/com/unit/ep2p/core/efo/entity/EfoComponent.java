/*******************************************************************************
 * Class        ：EfoComponent
 * Created date ：2020/12/24
 * Lasted date  ：2020/12/24
 * Author       ：taitt
 * Change log   ：2020/12/24：01-00 taitt create a new
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
import vn.com.unit.ep2p.core.constant.AppCoreConstant;

/**
 * EfoComponent
 * 
 * @version 01-00
 * @since 01-00
 * @author taitt
 */
@Getter
@Setter
@Table(name = AppCoreConstant.TABLE_EFO_COMPONENT)
public class EfoComponent extends AbstractTracking {

    /** Column: ID type decimal(20,0) NOT NULL */
    @Id
    @Column(name = "ID")
    @PrimaryKey(generationType = GenerationType.SEQUENCE, generator = CoreConstant.SEQ
            + CoreConstant.TABLE_EFO_COMPONENT)
    private Long id;

    /** Column: COMPANY_ID type decimal(20,0) NULL */
    @Column(name = "COMPANY_ID")
    private Long companyId;

    /** Column: NAME type nvarchar(255) NOT NULL */
    @Column(name = "NAME")
    private String name;

    /** Column: FORM_ID type decimal(20,0) NULL */
    @Column(name = "FORM_ID")
    private String description;

    /** Column: DISPLAY_ORDER type decimal(20,0) NULL */
    @Column(name = "DISPLAY_ORDER")
    private Long displayOrder;

    /** Column: ACTIVE_FLAG type char(1) NOT NULL */
    @Column(name = "ACTIVED")
    private String actived;
}
