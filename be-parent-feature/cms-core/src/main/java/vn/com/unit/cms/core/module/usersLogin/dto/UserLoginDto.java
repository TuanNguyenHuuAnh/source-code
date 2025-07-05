package vn.com.unit.cms.core.module.usersLogin.dto;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserLoginDto {	
	private Long id;
	private String userId;
	private String username;
	private String loginType;
	private String loginStatus;
	private Date loginDate;
	private Date logoutDate;
	private String clientIp;
	private String browser;
	private String os;
	private String device;
	private String channel;
}
