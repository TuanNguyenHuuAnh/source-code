/***************************************************************
 * @author vunt					
 * @date Apr 5, 2021	
 * @project mbal-webapp
 * @version 1.0 
 * @description 
 ***************************************************************/
package vn.com.unit.ep2p.core.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AccountApiDto {

	private String responseCode;

	private String responseStrEn;

	private String responseStrVi;

	private String title;

	private String fullName;

	private String department;

	private String result;

	private String groupType;

	private String linkGmail;

	private String linkFacebook;

	private String linkApple;

	@JsonProperty("AuthStatus")
	private String authStatus;

	@JsonProperty("UserID")
	private String userId;

	@JsonProperty("Level")
	private String level;

	@JsonProperty("AgentType")
	private String agentType;

	@JsonProperty("UserName")
	private String username;

	@JsonProperty("UserStatus")
	private String userStatus;

	@JsonProperty("ResponseMsg")
	private String responseMsg;

	@JsonProperty("TeamNo")
	private String teamNo;

	@JsonProperty("Region")
	private String region;

	@JsonProperty("Channel")
	private String channel;

	@JsonProperty("APIToken")
	private String apiToken;

	@JsonProperty("InsightURL")
	private String insightUrl;

	@JsonProperty("Password")
	private String password;

	@JsonProperty("ContactEmail")
	private String contactEmail;

	@JsonProperty("CellPhone")
	private String cellPhone;

	@JsonProperty("AccountType")
	private String accountType;

	@JsonProperty("Active")
	private String active;

	@JsonProperty("Gender")
	private String gender;

	@JsonProperty("Birthday")
	private String birthDay;
	
	@JsonProperty("PasswordGad")
	private String passwordGad;
	
	@JsonProperty("PasswordModifiedDate")
	private String passwordModifiedDate;
	
	@JsonProperty("FirstTimeLogin")
	private String firstTimeLogin;

}
