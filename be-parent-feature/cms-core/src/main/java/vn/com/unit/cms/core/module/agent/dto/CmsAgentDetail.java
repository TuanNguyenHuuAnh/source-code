package vn.com.unit.cms.core.module.agent.dto;

import java.util.Date;
import java.util.List;

import lombok.Getter;
import lombok.Setter;
import vn.com.unit.common.dto.AaGaOfficeDto;

@Getter
@Setter
public class CmsAgentDetail {
	private Long id;
	private String fullName;
	private String agentType;
	private Integer agentLevel;
	private Date dateOfBirth;
	private String maritalStatus;
	private String educational;
	private String educationalName;
	private String maritalStatusName;
	private String idNumber;
	private String idPlaceOfIssue;
	private String homeTown;
	private String phoneNumber;
	private String mobilePhone;
	private String fax;
	private String email;
	private String emailDlvn;
	private String permanentAddress;
	private String temporaryAddress;
	private String agentCode;
	private String agentTypeName;
	private String officeName;
	private String officeCode;
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
	private Date idIssueDate;
	private List<String> takingWinnerList;	
	private String idPersonalIncomeTax;
	private Date appointDate;
	private String facebook;
	private String zalo;
	private String gender;
	private String agentGroup;
	private String titleName;
	private String genderName;
	private String avatar;
	private Date terminatedDate;
	private String groupType;
	private String occupation;
	private String agentStatus;
	private Integer agentStatusCode;
	private String orgCode;
	private String orgId;
	private String agentGroupType;
	private String careerName;
	private String bankAccountNumber;
	private String bankAccountName;
	private Integer isGad;
	private List<AaGaOfficeDto> listGa;
	private String listGaString;
	private String salesOfficeCode;
	private String salesOfficeName;
	private Integer isDfa;//check agent type is DFA channel
	private String globalMdrtCode;
	//SR14387 add start
	private String provinceCode;
	private String provinceName;
	private String districtCode;
	private String districtName;
	private String wardCode;
	private String wardName;
	private String address;
	private String taxCode;
	private String taxGroup;
	private String channel;
	//SR14387 add end
}
