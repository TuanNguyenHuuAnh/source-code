package vn.com.unit.ep2p.service;

import java.io.IOException;
import java.util.Locale;

import javax.servlet.http.HttpServletResponse;

import org.springframework.http.ResponseEntity;

import vn.com.unit.cms.core.dto.CmsCommonPagination;
import vn.com.unit.cms.core.module.agent.dto.CertificateAnotherSearchDto;
import vn.com.unit.cms.core.module.agent.dto.CertificateDto;
import vn.com.unit.cms.core.module.agent.dto.CertificateSearchDto;
import vn.com.unit.cms.core.module.agent.dto.FileDto;
import vn.com.unit.cms.core.module.agent.dto.IntroduceLetterDto;
import vn.com.unit.cms.core.module.agent.dto.IntroduceSearchDto;
import vn.com.unit.cms.core.module.agent.dto.LetterAgentDto;
import vn.com.unit.cms.core.module.agent.dto.LetterAgentSearchDto;
import vn.com.unit.core.res.ObjectDataRes;
import vn.com.unit.core.config.SystemConfig;

public interface CertificateAndLetterService {
	public SystemConfig getSystemConfig();

	CmsCommonPagination<LetterAgentDto> getListLetterAgentByCondition(LetterAgentSearchDto dto);

	CmsCommonPagination<IntroduceLetterDto> getListIntroduceLetterByCondition(IntroduceSearchDto dto);

	ObjectDataRes<CertificateDto> getListCertificateByCondition(CertificateSearchDto dto);
	
	@SuppressWarnings("rawtypes")
	ResponseEntity exportExcelLetterAgent(LetterAgentSearchDto dto, HttpServletResponse response, Locale locale);

	@SuppressWarnings("rawtypes")
	ResponseEntity exportExcelIntroduceLetter(IntroduceSearchDto dto, HttpServletResponse response, Locale locale);

	public ObjectDataRes<CertificateDto> getListCertificateAnotherByCondition(CertificateAnotherSearchDto searchDto);

	@SuppressWarnings("rawtypes")
	public ResponseEntity exportListCertificateByCondition(CertificateSearchDto searchDto, HttpServletResponse response,
			Locale locale);

	String getFileByBase64(FileDto file) throws IOException;

	public CmsCommonPagination<LetterAgentDto> getListLetterAgentTer(LetterAgentSearchDto searchDto);
	

}
