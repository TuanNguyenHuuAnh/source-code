package vn.com.unit.cms.core.module.report.dto;

import java.util.Scanner;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class IncomeBonusReportExport {
	
	private String office;
	private String manager;
	
	private String tvtc;
	
	private String headOfDepartment;
	
	//MO
	private String fypPassedMo;
	private Integer fycBonusMo;
	private Integer tvtcNoMo;
	private Integer contractsNumberMo;
	private Integer fypNextMo;
	private Integer fypHighestMo;
	
	//TQP
	private Integer fypQuarterlyBonusTqp;
	private Integer k2PlusTqp;
	private Integer fycBonusTqp;
	private Integer fypNextTqp;
	private Integer fypHighestTqp;
	
	//TSM
	private Integer fypQuarterlyPassedTsm;
	private Integer fcOperateQuarterlyTsm;
	private Integer ratingTsm;
	private Integer k2PlusTsm;
	private Integer fypHighestTsm;
}
