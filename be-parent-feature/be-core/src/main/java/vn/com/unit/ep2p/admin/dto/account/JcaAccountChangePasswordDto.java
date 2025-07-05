package vn.com.unit.ep2p.admin.dto.account;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JcaAccountChangePasswordDto {

	private Long accountId;
	
	private String username;
	
	private String oldPassword;
	
	private String newPassword;
	
	private String confirmNewPassword;

	
}
