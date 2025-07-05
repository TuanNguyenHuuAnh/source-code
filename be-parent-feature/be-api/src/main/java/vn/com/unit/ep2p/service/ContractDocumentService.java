package vn.com.unit.ep2p.service;

import java.io.IOException;
import java.util.Locale;

import javax.servlet.http.HttpServletResponse;

import org.springframework.http.ResponseEntity;

import vn.com.unit.cms.core.module.agent.dto.CertificateSearchDto;
import vn.com.unit.cms.core.module.agent.dto.FileDto;
import vn.com.unit.core.config.SystemConfig;

public interface ContractDocumentService {
	public SystemConfig getSystemConfig();

	@SuppressWarnings("rawtypes")
	ResponseEntity exportExcelTermsConditions(CertificateSearchDto dto, HttpServletResponse response, Locale locale);

	@SuppressWarnings("rawtypes")
	ResponseEntity exportExcelContractDocument(CertificateSearchDto dto, HttpServletResponse response, Locale locale);
	
	String getFileByBase64(FileDto file) throws IOException;
}
