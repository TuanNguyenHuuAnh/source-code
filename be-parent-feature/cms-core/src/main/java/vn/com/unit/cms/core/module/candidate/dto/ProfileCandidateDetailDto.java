package vn.com.unit.cms.core.module.candidate.dto;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProfileCandidateDetailDto {
	private String linkAvatar;
	private String birthdate;
	private Date issueDate;
	private String idPlaceOfIssue;
	private String phoneNum;
	private String email;
	private String address;
	private String position;
	private String introducerName;
	private String introducerCode;
	private String idNoIntroducer;
	private String office;
	private String officeCode;
	private String bdManagerName;
	private String bdManagerCode;
	private String statusdescvn;
	private String idNoImage;
	private String familyRegisterImage;
	private String positionApply;
	private boolean sex;//boolean
	private String maritalStatus;
	private String proQualifications;
	private String job;
	private String idNumber;
	
}
