/*******************************************************************************
* Class        EfoFormAuthority
* Created date 2021/03/03
* Lasted date  2021/03/03
* Author       TaiTT
* Change log   2021/03/03 01-00 TaiTT create a new
******************************************************************************/

package vn.com.unit.ep2p.core.efo.entity;

import org.springframework.data.annotation.Id;

import jp.sf.amateras.mirage.annotation.Column;
import jp.sf.amateras.mirage.annotation.PrimaryKey;
import jp.sf.amateras.mirage.annotation.PrimaryKey.GenerationType;
import jp.sf.amateras.mirage.annotation.Table;
import lombok.Getter;
import lombok.Setter;
import vn.com.unit.db.entity.AbstractCreatedTracking;
import vn.com.unit.ep2p.core.constant.AppCoreConstant;

/**
 * EfoFormAuthority
 * 
 * @version 01-00
 * @since 01-00
 * @author TaiTT
 */

@Getter
@Setter
@Table(name = AppCoreConstant.TABLE_EFO_FORM_AUTHORITY)
public class EfoFormAuthority extends AbstractCreatedTracking {

    /** Column: ID type NUMBER (20,0) NOT NULL */
    @Id
    @Column(name = "FORM_ID")
    @PrimaryKey(generationType = GenerationType.APPLICATION)
    private Long formId;

    /** Column: ROLE_ID type NUMBER(20,0) null */
    @Id
    @Column(name = "ROLE_ID")
    @PrimaryKey(generationType = GenerationType.APPLICATION)
    private Long roleId;

}