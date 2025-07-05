package vn.com.unit.cms.core.module.reportGroup;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReportGroupResultManPowerDto{
    private String orgName; 								// miền
    private String agentName;								// BDAH,BDTH....

    private BigDecimal newRecruitFc;							// Số TVTC tuyển dụng trong tháng mục tiêu
    private BigDecimal countNewRecruitment;					// Số TVTC tuyển dụng trong tháng thực đạt
    private String rcRatio;								// Tỉ lệ đạt so với mục tiêu = Số TVTC tuyển dụng thực đạt/ Số TVTC tuyển dụng mục tiêu, tuyển dụng

    private BigDecimal activeFc;								// Số TVTC hoạt động trong tháng mục tiêu
    private BigDecimal countActive;							// Số TVTC hoạt động trong tháng thực đạt
    private String actionRatio;							// Tỉ lệ đạt so với mục tiêu = Số TVTC hoạt động thực đạt/ Số TVTC hoạt động mục tiêu, hoạt động
    private String actionRatioAction;						// Tỉ lệ hoạt động=số lượng TVTC hoạt động/số lượng đại lý

    private BigDecimal countNewRecruitmentActive;				// Số TVTC hoạt động mới trong tháng thực đạt
    private String actionNewRaio;							// Số TVTC hoạt động mới /Số lượng TVTC mới tuyển trong tháng

    private BigDecimal countFcTypeAgentcode ; 					//số lượng đại lý sum
    private BigDecimal countSaTypeAgentcode; 					//số lượng đại lý sum
    private Integer treeLevel;
}
