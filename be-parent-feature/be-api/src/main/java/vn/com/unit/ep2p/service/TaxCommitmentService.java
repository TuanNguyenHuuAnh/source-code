package vn.com.unit.ep2p.service;

import vn.com.unit.ep2p.admin.dto.AgentAllowExportTaxCommitmentDto;
import vn.com.unit.ep2p.dto.req.CommitmentInfoFileReq;
import vn.com.unit.ep2p.dto.res.UploadTaxCommitmentDocRes;
import vn.com.unit.cms.core.module.agent.dto.AgentInfoTaxCommitmentExportDto;
import vn.com.unit.cms.core.module.income.entity.TaxRegistration;

import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

public interface TaxCommitmentService {
	AgentAllowExportTaxCommitmentDto getAgentInfoTaxCommitment(String agentCode);
	
	ResponseEntity<byte[]> getExportTemplate(AgentInfoTaxCommitmentExportDto data) throws Exception;
	
	Boolean checkUploadStatus(String agentCode, String year);
	
	boolean checkDocumentStatus(String agentCode);
	
	void saveUploadTime(TaxRegistration data);
	
	String getTaxCommitmentFileName();
	
	UploadTaxCommitmentDocRes uploadTaxCommitment(MultipartFile file, CommitmentInfoFileReq fileInfo);
}