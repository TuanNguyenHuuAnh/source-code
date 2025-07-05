package vn.com.unit.cms.core.module.report.dto;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;
import vn.com.unit.cms.core.dto.CommonSearchWithPagingDto;

@Getter
@Setter
public class ReportListDetailSearchDto extends CommonSearchWithPagingDto {
	private String agentCode; 
	private String agentGroup;
	private String orgCode;
	private String yyyyMM;
	private String month;
	private String year;
	
	private Object policyKey; 							//so HĐBH
	private Object polIssueEff; 						//Ngày phát hành
	private Object polCstatCd; 							//Tình trạng hợp đồng
	private Object polMprenAmt; 						//Phí cho 1 lần đóng
	private Object polBillModeCd; 						//Định kỳ đóng phí
	private Object opm; 								//YES/NO
	
}
