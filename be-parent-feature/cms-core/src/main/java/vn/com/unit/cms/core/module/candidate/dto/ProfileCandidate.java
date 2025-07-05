package vn.com.unit.cms.core.module.candidate.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProfileCandidate {
	private Integer agentType;
	private String userName;
	private String statusCandidateProfile;
	private String statusCandidateExam;
	private String BDTH;
	private String BDAH;
	private String BDRH;
	private String BDOH;
	private String office;
	private String headOfDepartment;
	private String manager;
	private Integer submitted;
	private Integer approved;
	private Integer reject;
	private Integer requestAddRecords;
	private Integer notYetExam;
	private Integer passExam;
	private Integer failedExam;
	private Integer retest;
	private Integer recruitment;
	private Integer contacted;
	private String TVTC;
	private String idCard;
	private String fullnameCandidate;
	private String positionApply;
	private String birthdayCandidate;//date
	private String phoneNum;
	private String email;
	private String dateExam;//date
	private String addressExam;
	private Integer scoreExam;
	
}
