package vn.com.unit.cms.core.module.candidate.dto;

import java.util.List;

import jp.sf.amateras.mirage.annotation.In;
import jp.sf.amateras.mirage.annotation.Out;
import jp.sf.amateras.mirage.annotation.ResultSet;

public class ProfileCandidateParam extends PagingParamDto{
	@In
	public Integer agentType;
	@In
	public String userName;
	@In
	public String statusCandidateProfile;
	@In
	public String statusCandidateExam;
	@In
	public String BDTH;
	@In
	public String BDAH;
	@In
	public String BDRH;
	@In
	public String BDOH;
	@In
	public String office;
	@In
	public String headOfDepartment;
	@In
	public String manager;
	@In
	public Integer submitted;
	@In
	public Integer approved;
	@In
	public Integer reject;
	@In
	public Integer requestAddRecords;
	@In
	public Integer notYetExam;
	@In
	public Integer passExam;
	@In
	public Integer failedExam;
	@In
	public Integer retest;
	@In
	public Integer recruitment;
	@In
	public Integer contacted;
	@In
	public String TVTC;
	@In
	public String idCard;
	@In
	public String fullnameCandidate;
	@In
	public String positionApply;
	@In
	public String birthdayCandidate;//date
	@In
	public String phoneNum;
	@In
	public String email;
	@In
	public String dateExam;//date
	@In
	public String addressExam;
	@In
	public Integer scoreExam;
	@ResultSet
	public List<ProfileCandidateDto> data;
	@Out
	public Integer TotalRows;
	
}
