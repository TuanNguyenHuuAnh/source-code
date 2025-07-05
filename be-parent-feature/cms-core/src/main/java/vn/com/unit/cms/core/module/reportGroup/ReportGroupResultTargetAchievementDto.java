package vn.com.unit.cms.core.module.reportGroup;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReportGroupResultTargetAchievementDto extends ReportGroupTargetDayDto{
    // DOANH SO
    private String agentCode;				//	Code của BDOH
    private String agentName;				//	Name của BDOH
    private String agentType;
    private String agentCodeParent;
    private String agentNameParent;
    private String agentTypeParent;
    private String target;					//	FYP mục tiêu
    private String tpCs;					//	IP
    private Integer RfypA2;					//	RFYP: Re Newall FYP: Phí tái tục
    private Integer actual;					//	FYP thực đạt=A1 + A2
    private Integer targetAchievement;		//	FYP thực đạt/FYP mục tiêu
    private Integer cmVsLm;					//	FYP thực đạt/FYP tháng trước
    private Integer cmVsLy;					//	FYP thực đạt/FYP cùng kỳ năm trước
    private Integer targetYyp;				//	Target của RYP
    private Integer actualRyp;				//	Actual của RYP
    private Integer ratio;					//	Ratio của RYP= Actual RYP/Target RYP
    private Integer targetTotalPremium;		//	Target của tổng Premium
    private Integer actualTotalPremium;		//	Actual của tổng Premium
    private Integer ratioTotalPremium;		//	Ratio của tổng Premium = Actual Premium/Target Premium
    private String office;					//	Văn phòng /TĐL
    private String manager;					//	Trưởng phòng
    
    private Integer premiumGrandTotal;		//	Cộng các cột tương ứng

    // SO SANH DOANH SO
    private Integer mtdBLastMonth;					//	FYP MTD tháng trước
    private Integer mtdBFullMonth;					//	FYP (nguyên tháng) tháng trước
    private Integer mtdBCb;							//	= C- B'
    private Integer mtdIpThisMothLastYear;			//	IP MTD tháng này năm trước
    private Integer fullmonthIpThisMothLastYear;	//	IP ( nguyên tháng) tháng này năm trước
    private Integer fullmonthRypThisMothLastYear;	//	FYP (nguyên tháng) tháng này năm trước
    private Integer saleGrandTotal;						//	Cộng các cột tương ứng Mục So sánh Doanh số

    //Lưới Ước tính doanh số còn thiếu so với mục tiêu		
    private Integer fypEstimateFypBy;				//	Estimate FYP
    private Integer targetEstimateFypBy;			//	Estimate FYP/Target FYP
    private Integer fypMissingProgress;				//	= if (FYP Target – estimate FYP<0,0, YP Target – estimate FYP)
    private Integer rypMissingProgress;				//	= if (RYP Target – actual RYP<0,0, RYP Target – actual RYP)
    private Integer tpMissingProgress;				//	"= if (Premium Target – Actual Premium <0,0, Premium Targe  – Actual Premium)
    private Integer estimateAndMissingGrandTotal;	//	Cộng các cột tương ứng Mục Ước tính doanh số còn thiếu so với mục tiêu:
    
    //Lưới Tình hình nộp phí theo ngày
    private Integer dayOneToEnd	;					//  Day 1 đến Day 31 ( hoặc đến ngày cuối tháng)



}
