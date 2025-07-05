/*******************************************************************************
 * Class        :Account
 * Created date :2020/12/01
 * Lasted date  :2020/12/01
 * Author       :SonND
 * Change log   :2020/12/01:01-00 SonND create a new
 ******************************************************************************/
package vn.com.unit.core.entity;

import java.math.BigDecimal;
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
 * Account
 * @version 01-00
 * @since 01-00
 * @author SonND
 */

@Getter
@Setter
@Table(name = CoreConstant.TABLE_JCA_ACCOUNT)
public class JcaAccount extends AbstractTracking {

	/** Column: id type decimal(20,0) NOT NULL */
    @Id
    @Column(name = "ID")
    @PrimaryKey(generationType = GenerationType.SEQUENCE, generator = CoreConstant.SEQ +  CoreConstant.TABLE_JCA_ACCOUNT)
    private Long id;
    
    /** Column: code type nvarchar(255)  NULL */
    @Column(name="CODE")
    private String code;
    
    /** Column: username type nvarchar(100) NOT NULL */
    @Column(name = "USERNAME")
    private String username;
    
    /** Column: password type nvarchar(128) NOT NULL */
    @Column(name = "PASSWORD")
    private String password;
    
    @Column(name = "ACCOUNT_TYPE")
    private String accountType;

    /** Column: fullname type nvarchar(255)  NULL */
    @Column(name = "FULLNAME")
    private String fullname;
    
    /** Column: email type nvarchar(255)  NULL */
    @Column(name = "EMAIL")
    private String email;
    
    /** Column: birthday type date NULL */
    @Column(name = "BIRTHDAY")
    private Date birthday;
    
    /** Column: phone type nvarchar(20) NULL */
    @Column(name = "PHONE")
    private String phone;

    /** Column: avatar type nvarchar(255)  NULL */
    @Column(name = "AVATAR")
    private String avatar;
    
    @Column(name = "AVATAR_REPO_ID")
    private Long avatarRepoId;
    
    /** Column: login_date type date NULL */
    @Column(name = "LOGIN_DATE")
    private Date loginDate;
    
    /** Column: failed_login_count type decimal(1,0) NULL */
    @Column(name="FAILED_LOGIN_COUNT")
    private int failedLoginCount;
    
    /** Column: gender type nvarchar(255)  NULL */
    @Column(name="GENDER")
    private String gender;
    
    @Column(name = "RECEIVED_NOTIFICATION")
    private boolean receivedNotification;
    
    @Column(name = "RECEIVED_EMAIL")
    private boolean receivedEmail;
    
    /** Column: actived type decimal(1,0) NULL */
    @Column(name = "ACTIVED")
    private boolean actived;
    
    /** Column: enabled type decimal(1,0) NULL */
    @Column(name = "ENABLED")
    private boolean enabled;
    
    /** Column: company_id type decimal(20,0)  NULL */
    @Column(name = "POSITION_ID")
    private Long positionId;
    
    @Column(name = "COMPANY_ID")
    private Long companyId;
    
    @Column(name="URL_FACEBOOK")
    private String urlFacebook;
    
    @Column(name="URL_ZALO")
    private String urlZalo;
    @Column(name="APPLE_LOGIN")
    private Integer appleLogin;
    @Column(name="FACEBOOK_LOGIN")
    private Integer facebookLogin;
    @Column(name="GOOGLE_LOGIN")
    private Integer googleLogin;

    @Column(name="LOGIN_LOCK")
    private boolean loginLock;

    @Column(name="DEPARTMENT")
    private String department;
    
    @Column(name="UID")
    private String uid;
    @Column(name="GAD_PASSWORD")
    private String gadPassword;
    @Column(name="FACE_MASK")
    private String faceMask;

    @Column(name="RESET_PASSWORD")
    private BigDecimal resetPassword;
    ///////////////////
    @Column(name="GAD_LOGIN_LOCK")
    private boolean gadLoginLock;

    /** Column: login_date type date NULL */
    @Column(name = "GAD_LOGIN_DATE")
    private Date gadLoginDate;

    /** Column: failed_login_count type decimal(1,0) NULL */
    @Column(name="GAD_FAILED_LOGIN_COUNT")
    private int gadFailedLoginCount;

    @Column(name="DEVICE_TOKEN_MOBILE")
    private String deviceTokenMobile;
    
    @Column(name = "CHANNEL")
    private String channel;
    
    @Column(name = "PARTNER")
    private String partner;
}