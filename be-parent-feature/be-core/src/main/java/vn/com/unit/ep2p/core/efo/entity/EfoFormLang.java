/*******************************************************************************
* Class        EfoFormLang
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
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import vn.com.unit.db.entity.AbstractAuditTracking;
import vn.com.unit.ep2p.core.constant.AppCoreConstant;

/**
 * EfoFormLang
 * 
 * @version 01-00
 * @since 01-00
 * @author TaiTT
 */

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = AppCoreConstant.TABLE_EFO_FORM_LANG)
public class EfoFormLang extends AbstractAuditTracking {

    /** Column: ID type NUMBER (20,0) NOT NULL */
    @Id
    @PrimaryKey(generationType = GenerationType.APPLICATION)
    @Column(name = "FORM_ID")
    private Long formId;

    /** Column: LANG_ID type NUMBER(20,0) null */
    @Id
    @PrimaryKey(generationType = GenerationType.APPLICATION)
    @Column(name = "LANG_ID")
    private Long langId;

    /** Column: LANG_CODE type VARCHAR2(10,0) null */
    @Column(name = "LANG_CODE")
    private String langCode;

    /** Column: NAME type NVARCHAR2(255,0) null */
    @Column(name = "NAME")
    private String name;
    
    
    public EfoFormLang(String langCode) {
        super();
        this.langCode = langCode;
    }

}