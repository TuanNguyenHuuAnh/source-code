package vn.com.unit.cms.core.module.report.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class IncomeBonusReportMO {
	private String fypPassed;
	private Integer fycBonus;			//Mức thưởng (%) trên FYC
	private Integer tvtcNo;
	private Integer contractsNumber;
	private Integer fypNext;
	private Integer fypHighest;
}
