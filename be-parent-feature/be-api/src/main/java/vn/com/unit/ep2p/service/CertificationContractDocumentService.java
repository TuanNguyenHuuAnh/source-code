package vn.com.unit.ep2p.service;

import java.io.IOException;
import java.util.List;

import vn.com.unit.cms.core.module.agent.dto.ContractDocumentDto;
import vn.com.unit.cms.core.module.agent.dto.FileDto;

public interface CertificationContractDocumentService {
	
	public List<ContractDocumentDto> getListTermsAndConditions(List<String> docIds);

	public String getFileByBase64(FileDto file) throws IOException;
}
