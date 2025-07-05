package vn.com.unit.cms.core.module.candidate.dto;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProfileCandidateDto {
	private Integer no;
	private Integer id;
	private Integer level;
	private String nationwideCode;
	private String codeCao;
	private String territory;
	private String codeBdth;
	private String area;
	private String codeBdah;
	private String region;
	private String codeBdrh;
	private String officCodeBdoh;
	private String codeBdoh;
	private String officCodeBm;
	private String codeBm;
	private String officUm;
	private String codeUm;
	private String codeFc;
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
	private String idNo;
	private String fullnameCandidate;
	private String positionApply;
	private Date birthdayCandidate;
	private String phoneNum;
	private String email;
	private Date dateExam;
	private String addressExam;
	private Integer scoreExam;	
}
