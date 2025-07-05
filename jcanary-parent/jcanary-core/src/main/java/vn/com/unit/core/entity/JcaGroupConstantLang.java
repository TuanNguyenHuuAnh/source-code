/*******************************************************************************
 * Class        :JcaGroupConstantLanguage
 * Created date :2020/12/01
 * Lasted date  :2020/12/01
 * Author       :tantm
 * Change log   :2020/12/01:01-00 tantm create a new
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
 * 
 * JcaGroupConstantLanguage
 * 
 * @version 01-00
 * @since 01-00
 * @author tantm
 */

@Getter
@Setter
@Table(name = CoreConstant.TABLE_JCA_GROUP_CONSTANT_LANG)
public class JcaGroupConstantLang extends AbstractTracking {

    /** Column: GROUP_ID type NUMBER(20,0) NULL */
    @Id
    @PrimaryKey(generationType = GenerationType.APPLICATION)
    @Column(name = "GROUP_ID")
    private Long groupId;

    /** Column: LANGUAGE_ID type VARCHAR2(30) NULL */
    @Id
    @PrimaryKey(generationType = GenerationType.APPLICATION)
    @Column(name = "LANG_ID")
    private Long langId;
    
    /** Column: LANGUAGE_CODE type VARCHAR2(30) NULL */
    @Column(name = "LANG_CODE")
    private String langCode;

    /** Column: NAME type NVARCHAR2(255) NULL */
    @Column(name = "NAME")
    private String name;

}