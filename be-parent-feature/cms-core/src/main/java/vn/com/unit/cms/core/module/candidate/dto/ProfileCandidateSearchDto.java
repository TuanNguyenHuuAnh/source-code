package vn.com.unit.cms.core.module.candidate.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProfileCandidateSearchDto extends PagingDto{
	private Integer agentType;
	private String userName;
	private String agentName;
	private String statusCandidateProfile;
	private String statusCandidateExam;
	private String bdth;
	private String bdah;
	private String bdrh;
	private String bdoh;
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
	private String tvtc;
	private String idCard;
	private String fullnameCandidate;
	private String positionApply;
	private String birthdayCandidate;
	private String phoneNum;
	private String email;
	private String dateExam;
	private String addressExam;
	private Integer scoreExam;
}
