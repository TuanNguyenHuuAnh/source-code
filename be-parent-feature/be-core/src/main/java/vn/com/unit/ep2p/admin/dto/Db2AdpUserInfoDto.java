package vn.com.unit.ep2p.admin.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Db2AdpUserInfoDto {

	private String agentCode; 
	private String agentName;
	private String agentType;
	private String branchCode;
	private String branchName;
	private String phoneNumber;
	private String officeCode;
	private String officeName;
}
