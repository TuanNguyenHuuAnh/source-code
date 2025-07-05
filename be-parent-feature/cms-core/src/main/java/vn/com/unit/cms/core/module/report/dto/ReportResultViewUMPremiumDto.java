package vn.com.unit.cms.core.module.report.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReportResultViewUMPremiumDto {
	private String officeCode;
	private String officeName;
	private String umCode;
	private String umName;
	private String reportingDate;
	
	private String agentCode;//tvtc
	private String agentName;
	private String agentType;
	
	//sumary UM
    private Integer fcQuantityByUm;                     //so luong fc
    private Integer newRecruitmentQuantityByUm;         //Tổng số tuyển dụng mới
    private Integer recoveryCodeQuantityByUm;           //Số lượng khôi phục mã số
    private Integer activeQuantityByUm;                 //Số lượng hoạt động
    private Integer fypTotalByUm;                       //Tổng số FYP 
    private Integer rypTotalByUm;                       //Tổng số RYP
	
	// Chi tiết Month to date
    private Integer fypDay;                             //fyp ngày
	// nop vao
	private Integer profileTotalSubmit;
	private Integer fypTotalSubmit;
	// phat hanh
	private Integer contractTotalRelease;
	private Integer fypTotalRelease;
	// phat hanh thuan
	private Integer contractTotalReleasePure;
	private Integer fypTotalReleasePure;
	// huy
	private Integer contractTotalCancel;
	private Integer fypTotalCancel;

	private Integer fypResume;
	private Integer rypTotal;
	private Integer k2;
	private Integer k2Plus;
	
	//phat thuan option
	private Integer contractReleaseTotalMonth2m3;
	private Integer fypTotalMonth2m3;
	private Integer contractReleaseTotalMonth3m2;
	private Integer fypTotalMonth3m2;
	private Integer contractReleaseTotalMonth4m1;
	private Integer fypTotalMonth4m1;

}
