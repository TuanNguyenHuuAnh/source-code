package vn.com.unit.cms.core.module.usersLogin.entity;

import java.util.Date;

import org.springframework.data.annotation.Id;

import jp.sf.amateras.mirage.annotation.Column;
import jp.sf.amateras.mirage.annotation.PrimaryKey;
import jp.sf.amateras.mirage.annotation.PrimaryKey.GenerationType;
import jp.sf.amateras.mirage.annotation.Table;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Table(name = "M_USER_LOGIN")
public class UserLogin {

    @Id
    @Column(name = "ID")
	@PrimaryKey(generationType = GenerationType.SEQUENCE, generator = "SEQ_M_USER_LOGIN")
    private Long id;

    @Column(name = "USER_ID")
    private String userID;
    
    @Column(name = "USERNAME")
    private String username;

    @Column(name = "LOGIN_TYPE")
    private String loginType;
    
    @Column(name = "LOGIN_STATUS")
    private String loginStatus;
    
    @Column(name = "LOGIN_DATE")
    private Date loginDate;
    
    @Column(name = "LOGOUT_DATE")
    private Date logoutDate;
    
    @Column(name = "REMOTE_HOST")
    private String remoteHost;
    
    @Column(name = "BROWSER")
    private String browser;
    
    @Column(name = "OPERATING_SYSTEM")
    private String os;
    
    @Column(name = "DEVICE")
    private String device;
    
    @Column(name = "ACCESS_TOKEN")
    private String accessToken;
    
    @Column(name = "CHANNEL")
    private String channel;
    
    @Column(name = "USER_AGENT")
    private String userAgent;
    
    @Column(name = "CLIENT_IP")
    private String clientIp;
    
    @Column(name = "EXPIRED_DATE")
    private Date expiredDate;

}

