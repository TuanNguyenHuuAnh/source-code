package vn.com.unit.cms.core.module.report.dto;
import java.math.BigDecimal;
import java.util.Date;

import com.google.errorprone.annotations.NoAllocation;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ReportDetailDto {
					
	private Integer no; 								//STT
	private String policyKey; 							//so HĐBH
	private Date polIssueEff; 							//Ngày phát hành
	private String polCstatCdCode; 							//Tình trạng hợp đồng
	private String polCstatCd; 							//Tình trạng hợp đồng
	private BigDecimal polMprenAmt; 						//Phí cho 1 lần đóng
	private String polBillModeCd; 						//Định kỳ đóng phí
	private String opm; 								//YES/NO
	
	private BigDecimal k2Epp;      							//Phí cần đóng EPP K2
	private BigDecimal k2App;      							//Phí cần đóng APP K2
	private BigDecimal k2ChargesIssue;         				//Phí thiếu k2

	private BigDecimal k2plusEpp;     						//Phí cần đóng EPP K2+
	private BigDecimal k2plusApp;     						//Phí cần đóng APP K2+
	private BigDecimal kk2ChargesIssue;       				 //Phí thiếu k2

	private String poName;

//	private Integer RYPMustBeCollectedPeriodically;		// RYP phải thu theo kỳ
//	private Integer REALRYCollectedPeriodically;		// RYP thực thu theo kỳ
//	private Integer realityK2;							// % K2 thực tế
//	private String estimateK2ToGetTheRatio;				// Dự tính K2 đạt tỉ lệ
//	private Integer RYPNeedsToCollectl;					// RYP cần phải thu


}
