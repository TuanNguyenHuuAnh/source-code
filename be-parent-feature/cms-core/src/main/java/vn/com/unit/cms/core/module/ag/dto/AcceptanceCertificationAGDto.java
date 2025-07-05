package vn.com.unit.cms.core.module.ag.dto;

import java.util.Date;
import java.util.List;

import lombok.Getter;
import lombok.Setter;
//import vn.com.unit.cms.core.module.ga.dto.IncomeMonthsGaDto;
//import vn.com.unit.ep2p.admin.dto.AcceptanceCertificationAGInformationDto;
import vn.com.unit.ep2p.admin.dto.AgentTaxBankInfoDto;
import vn.com.unit.ep2p.admin.dto.IncomeConfirmPaymentAGDto;

@Getter
@Setter
public class AcceptanceCertificationAGDto {
	private AgentTaxBankInfoDto taxBankInfo;
	private Long confirmId;
	private Date confirmTime;
	private String rejectReason;
	private List<IncomeConfirmPaymentAGDto> incomes;
	
}
