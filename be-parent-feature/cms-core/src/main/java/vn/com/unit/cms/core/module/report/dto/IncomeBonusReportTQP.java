package vn.com.unit.cms.core.module.report.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class IncomeBonusReportTQP {
	private Integer fypQuarterlyBonus;
	private Integer k2Plus;
	private Integer fycBonus;			//Mức thưởng (%) trên FYC
	private Integer fypNext;
	private Integer fypHighest;
}
