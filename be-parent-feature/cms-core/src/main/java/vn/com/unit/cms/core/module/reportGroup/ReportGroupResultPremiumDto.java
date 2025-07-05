package vn.com.unit.cms.core.module.reportGroup;

import lombok.Getter;
import lombok.Setter;
import vn.com.unit.ep2p.core.annotation.Date;

import java.math.BigDecimal;

@Getter
@Setter
public class ReportGroupResultPremiumDto{

    // luoi tong doanh so premium

    private String orgName; 								// miền
    private String agentName;								// BDAH,BDTH....
    private String gadName;
    private String area; 									// khu
    private String district;							    // vùng
    private String officeAll;								// Văn phòng
    private BigDecimal fypTarget;								// FYP mục tiêu
    private BigDecimal fyp;									// FYP thực đạt
    private BigDecimal rateOfReachingFyp;						// Tỉ lệ đạt FYP
    private BigDecimal rypTarget;								// RYP mục tiêu
    private BigDecimal ryp;									// RYP thực đạt
    private BigDecimal rateOfReachingRyp;						// Tỉ lệ đạt RYP
    private BigDecimal policyCountTarget;						// Số HĐBH mục tiêu
    private BigDecimal policyCount;							// Số HĐBH thực đạt
    private BigDecimal insuranceContractRatio;					// Tỉ lệ đạt số HĐBH
    private int treeLevel;
}
