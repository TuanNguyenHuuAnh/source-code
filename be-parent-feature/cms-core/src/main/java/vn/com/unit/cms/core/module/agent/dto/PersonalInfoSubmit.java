package vn.com.unit.cms.core.module.agent.dto;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PersonalInfoSubmit {
	private String sourceSystem;
	private String agentCode;
	private String idNumber;
	private String idIssueDate;
	private String idIssuePlace;
	private String agentBirthDate;
	private String agentFullname;
	private String temporaryAddress;
	private String residenceAddress;
	private String mobileNumber;
	private String email;
	private List<FileInfor> documentList;
	//SR14387 add start
	private String residenceCity;
	private String residenceDistrict;
	private String residenceWard;
	private String residenceZipcode;
	private String taxCodePersonal;
	//SR14387 add end
}
