/*******************************************************************************
 * Class        ：JcaStyle
 * Created date ：2021/03/17
 * Lasted date  ：2021/03/17
 * Author       ：Tan Tai
 * Change log   ：2021/03/17：01-00 Tan Tai create a new
 ******************************************************************************/
package vn.com.unit.core.entity;

import java.util.Date;

import org.springframework.data.annotation.Id;

import jp.sf.amateras.mirage.annotation.Column;
import jp.sf.amateras.mirage.annotation.PrimaryKey;
import jp.sf.amateras.mirage.annotation.Table;
import jp.sf.amateras.mirage.annotation.PrimaryKey.GenerationType;
import lombok.Getter;
import lombok.Setter;
import vn.com.unit.core.constant.CoreConstant;
import vn.com.unit.db.entity.AbstractTracking;

/**
 * JcaStyle
 * 
 * @version 01-00
 * @since 01-00
 * @author Tan Tai
 */
@Getter
@Setter
@Table(name = CoreConstant.TABLE_JCA_STYLE)
public class JcaStyle extends AbstractTracking {

	   /** Column: ID type NUMBER(20,0)  NOT NULL */
    @Id
    @Column(name = "ID")
    @PrimaryKey(generationType = GenerationType.SEQUENCE, generator = CoreConstant.SEQ + CoreConstant.TABLE_JCA_STYLE)
    private Long id;
   
    /** Column: CODE type NVARCHAR2(100)  NOT NULL */
    @Column(name = "CODE")
    private String code;
    
    /** Column: NAME type NVARCHAR2(255)  NOT NULL */
    @Column(name = "NAME")
    private String name;
    
    /** Column: STYLE_URL type NVARCHAR2(255)  NOT NULL */
    @Column(name = "STYLE_URL")
    private String styleUrl;
    
    /** Column: PACKAGE_STYLE_URL type NVARCHAR2(255)  NOT NULL */
    @Column(name = "PACKAGE_STYLE_URL")
    private String packageStyleUrl;
    
    /** Column: IMAGE_URL type NVARCHAR2(255)  NOT NULL */
    @Column(name = "IMAGE_URL")
    private String imageUrl;
    
    /** Column: IMAGE_URL_REPO_ID type NUMBER(20,0)   NOT NULL */
    @Column(name = "IMAGE_URL_REPO_ID")
    private Long imageUrlRepoId;
    
    /** Column: PACKAGE_LOGIN_LOGO type NVARCHAR2(255)  NOT NULL */
    @Column(name = "PACKAGE_LOGIN_LOGO")
    private String packageLoginLogo;
    
    /** Column: PACKAGE_SHORTCUT_ICON type NVARCHAR2(255)  NOT NULL */
    @Column(name = "PACKAGE_SHORTCUT_ICON")
    private String packageShortcutIncon;
    
    /** Column: PACKAGE_LOGO_LARGE type NVARCHAR2(255)  NOT NULL */
    @Column(name = "PACKAGE_LOGO_LARGE")
    private String packageLogoLarge;
    
    /** Column: PACKAGE_LOGO_MINI type NVARCHAR2(255)  NOT NULL */
    @Column(name = "PACKAGE_LOGO_MINI")
    private String packageLogoMini;
    
    /** Column: DISPLAY_ORDER type NVARCHAR2(255)  NOT NULL */
    @Column(name = "DISPLAY_ORDER")
    private Long displayOrder;

}
