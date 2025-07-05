package vn.com.unit.ep2p.service;

import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletResponse;

import org.springframework.http.ResponseEntity;

import vn.com.unit.cms.core.module.emulate.dto.EmulateResultSearchResultDto;
import vn.com.unit.cms.core.module.emulate.dto.EmulateSearchDto;
import vn.com.unit.cms.core.module.information.dto.DebtInformationDto;
import vn.com.unit.cms.core.module.information.dto.DebtInformationSearchDto;
import vn.com.unit.cms.core.module.information.dto.GAInformationDto;
import vn.com.unit.cms.core.module.information.dto.LetterSearchDto;
import vn.com.unit.cms.core.module.information.dto.ResultLetterSearchDto;
import vn.com.unit.common.dto.Select2Dto;
import vn.com.unit.core.res.ObjectDataRes;

public interface InformationService {
	public ObjectDataRes<DebtInformationDto> getListDebtInformation(DebtInformationSearchDto searchDto);

	@SuppressWarnings("rawtypes")
	ResponseEntity exportListDebtInformation(HttpServletResponse response, Locale locale, DebtInformationSearchDto searchDto);
	
	public List<GAInformationDto> getListGA(String agentCode, String orgCode);
	
	public List<Select2Dto> getOfficeOfGad(String agentCode);
	
	public List<EmulateSearchDto> getListchallengeLetterOffice(LetterSearchDto searchDto);
	
	public List<EmulateResultSearchResultDto> getListResultchallengeLetterOffice(ResultLetterSearchDto searchDto);
	
	@SuppressWarnings("rawtypes")
	ResponseEntity exportListResultchallengeLetterOffice(HttpServletResponse response, Locale locale, ResultLetterSearchDto searchDto);
	
}
