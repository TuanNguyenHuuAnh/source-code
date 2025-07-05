package vn.com.unit.ep2p.service;

import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.ResponseEntity;

import vn.com.unit.cms.core.module.candidate.dto.AddCandidateProfileDto;
import vn.com.unit.cms.core.module.candidate.dto.AddCandidateProfileSearchDto;
import vn.com.unit.cms.core.module.candidate.dto.CandidateInfoDto;
import vn.com.unit.cms.core.module.candidate.dto.ExamScheduleCandidateDto;
import vn.com.unit.cms.core.module.candidate.dto.ExamScheduleCandidateSearchDto;
import vn.com.unit.cms.core.module.candidate.dto.ExamScheduleHomepage;
import vn.com.unit.cms.core.module.candidate.dto.MonthYear;
import vn.com.unit.cms.core.module.candidate.dto.ProfileCandidateDetailDto;
import vn.com.unit.cms.core.module.candidate.dto.ProfileCandidateDto;
import vn.com.unit.cms.core.module.candidate.dto.ProfileCandidateSearchDto;
import vn.com.unit.cms.core.module.candidate.dto.RegulationExamScheduleDto;
import vn.com.unit.cms.core.module.candidate.dto.ScheduleCandidateDto;
import vn.com.unit.cms.core.module.candidate.dto.ScheduleCandidateSearchDto;
import vn.com.unit.cms.core.module.candidate.dto.TemporaryCandidateDto;
import vn.com.unit.cms.core.module.candidate.dto.TemporaryCandidateSearchDto;
import vn.com.unit.cms.core.module.document.dto.DocumentVersionDto;
import vn.com.unit.cms.core.module.document.entity.Document;
import vn.com.unit.core.res.ObjectDataRes;
import vn.com.unit.core.config.SystemConfig;

public interface CandidateService {
	
	public SystemConfig getSystemConfig();
	
	public ExamScheduleHomepage getScheduleHomepage(String agentCode);
	
	public List<DocumentVersionDto> getListRegulation(String languageCode);
	
	public CandidateInfoDto getCandidateInfo(String userName);
	
	public ObjectDataRes<ExamScheduleCandidateDto> getListExamScheduleByCondition(ExamScheduleCandidateSearchDto searchDto);

	public ObjectDataRes<ScheduleCandidateDto> getListScheduleByCondition(ScheduleCandidateSearchDto searchDto);
	
	List<MonthYear> getListMonth();
	
	List<MonthYear> getListYear();
	
	@SuppressWarnings("rawtypes")
	ResponseEntity exportExamSchedule(Locale locale,ExamScheduleCandidateSearchDto searchDto );
	
	@SuppressWarnings("rawtypes")
	ResponseEntity exportSchedule(Locale locale,ScheduleCandidateSearchDto searchDto);
	
	public ObjectDataRes<ProfileCandidateDto> getListProfileCandidate(ProfileCandidateSearchDto searchDto);
	
	public ProfileCandidateDetailDto getProfileCandidateDetail(String idCardCandidate);
	
	public ObjectDataRes<AddCandidateProfileDto> getListProfileCandidateDetail(AddCandidateProfileSearchDto searchDto);
	
	public ObjectDataRes<TemporaryCandidateDto> getListTemporaryCandidateParam(TemporaryCandidateSearchDto searchDto);
	
	@SuppressWarnings("rawtypes")
	ResponseEntity exportListTemporaryCandidate(TemporaryCandidateSearchDto searchDto);

}
