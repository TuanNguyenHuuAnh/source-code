/*******************************************************************************
 * Class        ：JcaMenuLanguage
 * Created date ：2021/02/24
 * Lasted date  ：2021/02/24
 * Author       ：taitt
 * Change log   ：2021/02/24：01-00 taitt create a new
 ******************************************************************************/
package vn.com.unit.core.entity;

import jp.sf.amateras.mirage.annotation.Column;
import jp.sf.amateras.mirage.annotation.PrimaryKey;
import jp.sf.amateras.mirage.annotation.PrimaryKey.GenerationType;
import jp.sf.amateras.mirage.annotation.Table;
import lombok.Getter;
import lombok.Setter;
import vn.com.unit.core.constant.CoreConstant;
import vn.com.unit.db.entity.AbstractAuditTracking;

/**
 * JcaMenuLanguage
 * 
 * @version 01-00
 * @since 01-00
 * @author taitt
 */
@Getter
@Setter
@Table(name = CoreConstant.TABLE_JCA_MENU_LANG)
public class JcaMenuLang extends AbstractAuditTracking {

    // "MENU_ID" NUMBER(20,0) NOT NULL,
    @PrimaryKey(generationType = GenerationType.APPLICATION)
    @Column(name = "MENU_ID")
    private Long menuId;

    // "LANG_ID" NUMBER(20,0) NOT NULL,
    @PrimaryKey(generationType = GenerationType.APPLICATION)
    @Column(name = "LANG_ID")
    private Long langId;

    // "LANG_CODE" VARCHAR2(10 CHAR) NOT NULL,
    @Column(name = "LANG_CODE")
    private String langCode;

    // "NAME" NVARCHAR2(255) NOT NULL,
    @Column(name = "NAME")
    private String name;

    // "NAME_ABV" NVARCHAR2(150) NOT NULL,
    @Column(name = "NAME_ABV")
    private String nameAbv;
}
