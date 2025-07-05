package vn.com.unit.ep2p.service;

import org.springframework.http.ResponseEntity;

import vn.com.unit.cms.core.module.ag.dto.AcceptanceCertificationAGDto;
import vn.com.unit.cms.core.module.ag.dto.AcceptanceCertificationDetailAG;
import vn.com.unit.cms.core.module.payment.entity.PaymentConfirm;
import vn.com.unit.ep2p.admin.dto.AgentTaxBankInfoDto;

public interface AcceptanceCertificationAGService {
	
	public AgentTaxBankInfoDto getTaxBusinessHouseHoldByAgentCode(String agentCode);
	
	public AcceptanceCertificationAGDto getAcceptanceCertificationAG(String agentCode, String cutOffDateYYYYMM);
	
	public boolean sendOTPAgent(String agentCode, String cutOffDateYYYYMM) throws Exception;
	
	public boolean confirmAcceptanceCertificationAG(AcceptanceCertificationDetailAG data) throws Exception;
	
	public boolean denyAcceptanceCertificationAG(AcceptanceCertificationDetailAG data);
	
	public PaymentConfirm getPaymentConfirmInfoAG(String agentCode, String cutOffDateYYYYMM);
	
	public ResponseEntity<byte[]> getAcceptanceCertificationReportAG(String agentCode, String cutOffDateYYYYMM, String note2, String note3);
}