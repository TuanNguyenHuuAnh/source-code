package vn.com.unit.cms.core.module.ga.dto;

import java.util.Date;
import java.util.List;

import lombok.Getter;
import lombok.Setter;
import vn.com.unit.ep2p.admin.dto.AcceptanceCertificationGaInformationDto;

@Getter
@Setter
public class AcceptanceCertificationDto {
	private AcceptanceCertificationGaInformationDto gaInformation;
	private Long confirmId;
	private Date confirmTime;
	private String rejectReason;
	private Date rejectTime;
	private List<IncomeConfirmPaymentDto> incomes;
}
