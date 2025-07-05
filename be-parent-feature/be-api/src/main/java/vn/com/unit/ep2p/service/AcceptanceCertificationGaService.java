package vn.com.unit.ep2p.service;

import vn.com.unit.cms.core.module.ga.dto.AcceptanceCertificationDetailGa;
import vn.com.unit.cms.core.module.ga.dto.AcceptanceCertificationDto;
import vn.com.unit.cms.core.module.ga.dto.AcceptanceCertificationReportDto;
import vn.com.unit.cms.core.module.ga.dto.param.IncomeConfirmPaymentParamGa;
import vn.com.unit.cms.core.module.payment.entity.PaymentConfirm;
import org.springframework.http.ResponseEntity;

public interface AcceptanceCertificationGaService {
	public AcceptanceCertificationDto getAcceptanceCertificationDetail(IncomeConfirmPaymentParamGa searchDto);
	
	public boolean sendOtpGad(String agentCode) throws Exception;
	
	public boolean confirmAcceptanceCertification(AcceptanceCertificationDetailGa data) throws Exception;
	
	public boolean denyAcceptanceCertification(AcceptanceCertificationDetailGa data);
	
	public PaymentConfirm getPaymentConfirmInfo(String agentCode, String gaCode, String period);
	
	public ResponseEntity<byte[]> getAcceptanceCertificationReport(AcceptanceCertificationReportDto dataSource, PaymentConfirm paymentConfirm);
}