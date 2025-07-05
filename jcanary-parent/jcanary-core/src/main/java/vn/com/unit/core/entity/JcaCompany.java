/*******************************************************************************
 * Class        ：JcaCompany
 * Created date ：2020/12/07
 * Lasted date  ：2020/12/07
 * Author       ：ngannh
 * Change log   ：2020/12/07：01-00 ngannh create a new
 ******************************************************************************/
package vn.com.unit.core.entity;

import java.util.Date;

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
 * JcaCompany
 * 
 * @version 01-00
 * @since 01-00
 * @author ngannh
 */
@Getter
@Setter
@Table(name = CoreConstant.TABLE_JCA_COMPANY)
public class JcaCompany extends AbstractTracking {
    /** Column: ID type NUMBER(20,0)  NOT NULL */
    @Id
    @Column(name = "ID")
    @PrimaryKey(generationType = GenerationType.SEQUENCE, generator = CoreConstant.SEQ + CoreConstant.TABLE_JCA_COMPANY)
    private Long id;
    
    /** Column: NAME type NVARCHAR2(255)  NOT NULL */
    @Column(name = "NAME")
    private String name;
    
    /** Column: SYSTEM_NAME type NVARCHAR2(255)  NOT NULL */
    @Column(name = "SYSTEM_NAME")
    private String systemName;
    
    /** Column: SYSTEM_CODE type NVARCHAR2(255)  NOT NULL */
    @Column(name = "SYSTEM_CODE")
    private String systemCode;
    
    /** Column: DESCRIPTION type NVARCHAR2(255)  NOT NULL */
    @Column(name = "DESCRIPTION")
    private String description;
    
    /** Column: PACKAGE_LOGIN_BACKGROUND type NVARCHAR2(255)  NOT NULL */
    @Column(name = "PACKAGE_LOGIN_BACKGROUND")
    private String packageLoginBackground;
    
    /** Column: PACKAGE_SHORTCUT_ICON type NVARCHAR2(255)  NOT NULL */
    @Column(name = "PACKAGE_SHORTCUT_ICON")
    private String packageShortcutIcon;
    
    /** Column: PACKAGE_LOGO_LARGE type NVARCHAR2(255)  NOT NULL */
    @Column(name = "PACKAGE_LOGO_LARGE")
    private String packageLogoLarge;
    
    /** Column: PACKAGE_LOGO_MINI type NVARCHAR2(255)  NOT NULL */
    @Column(name = "PACKAGE_LOGO_MINI")
    private String packageLogoMini;
    
    /** Column: LOGIN_BACKGROUND type NVARCHAR2(255)  NOT NULL */
    @Column(name = "LOGIN_BACKGROUND")
    private String loginBackground;
    
    /** Column: LOGIN_BACKGROUND_REPO_ID type decimal(20,0) NULL */
    @Column(name = "LOGIN_BACKGROUND_REPO_ID")
    private Long loginBackgroundRepoId;

    /** Column: SHORTCUT_ICON type varchar(255) NULL */
    @Column(name = "SHORTCUT_ICON")
    private String shortcutIcon;
    
    /** Column: SHORTCUT_ICON_REPO_ID type decimal(20,0) NULL */
    @Column(name = "SHORTCUT_ICON_REPO_ID")
    private Long shortcutIconRepoId;

    /** Column: LOGO_LARGE type varchar(255) NULL */
    @Column(name = "LOGO_LARGE")
    private String logoLarge;
    
    /** Column: LOGO_LARGE_REPO_ID type decimal(20,0) NULL */
    @Column(name = "LOGO_LARGE_REPO_ID")
    private Long logoLargeRepoId;

    /** Column: LOGO_MINI type varchar(255) NULL */
    @Column(name = "LOGO_MINI")
    private String logoMini;
    
    /** Column: LOGO_MINI_REPO_ID type decimal(20,0) NULL */
    @Column(name = "LOGO_MINI_REPO_ID")
    private Long logoMiniRepoId;
    
    /** Column: STYLE type varchar(255) NULL */
    @Column(name = "STYLE")
    private String style;
    
    /** Column: LANGUAGE type varchar(255) NULL */
    @Column(name = "LANGUAGE")
    private String language;

    /** Column: EFFECTED_DATE type datetime2() NULL */
    @Column(name = "EFFECTED_DATE")
    private Date effectedDate;

    /** Column: EXPIRED_DATE type datetime2() NULL */
    @Column(name = "EXPIRED_DATE")
    private Date expiredDate;

    /** Column: LIMIT_NUMBER_USERS type varchar(255) NULL */
    @Column(name = "LIMIT_NUMBER_USERS")
    private Long limitNumberUsers;

    /** Column: LIMIT_NUMBER_TRANSACTION type varchar(255) NULL */
    @Column(name = "LIMIT_NUMBER_TRANSACTION")
    private Long limitNumberTransaction;

    /** Column: ACTIVED type char(1) NOT NULL */
    @Column(name = "ACTIVED")
    private boolean actived;

}
