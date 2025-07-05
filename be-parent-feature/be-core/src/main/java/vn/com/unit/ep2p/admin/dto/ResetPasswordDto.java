package vn.com.unit.ep2p.admin.dto;

import lombok.Getter;
import lombok.Setter;
import vn.com.unit.ep2p.admin.constant.MessageList;

@Getter
@Setter
public class ResetPasswordDto{
	
	private Long id;
	private String fullName;
	private String agentType;
	private Integer agentLevel;
	private String educational;
	private String educationalName;
	private String maritalStatusName;
	private String idNumber;
	private String idPlaceOfIssue;
	private String homeTown;
	private String mobilePhone;
	private String fax;
	private String email;
	private String emailDlvn;
	private String permanentAddress;
	private String temporaryAddress;
	private String agentCode;
	private String agentTypeName;
	private String officeName;
	private String gadType;
	private Long gadCode;
	private String gadName;
	private String introducerType;
	private Long introducerCode;
	private String introducerName;
	private String reportingToType;
	private Long reportingToCode;
	private String reportingToName;
	private String branchType;
	private Long branchCode;
	private String branchName;
	private String takingWinner;
	private String facebook;
	private String zalo;
	private String gender;
	private String agentGroup;
}
