package vn.com.unit.cms.core.module.agent.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ActivityAgentDto {
	private String agentCode;
    private String agentName;
    private String agentType;
    private String status;
    private String gadCode;
    private String reportingToCode;// REPORTING_TO_CODE 
    private Integer totalProposal; //SO_HSYCBH_NOP_VAO, //tong HSYCBH da gop
    private Double totalfypReceived; //Tổng FYP nộp vào
    private Double totalRfyp; //SUM(sba.RFYP)	TONG_FYP_TAI_TUC,
    private Double totalFypCancel; //  SUM(sba.FYP_Cancel)	TONG_FYP_HUY,
    private Integer totalPolicyCount;// COUNT(sba.POLICY_COUNT)	SO_HOP_DONG_PHAT_HANH,
    private Double totalFyp;// SUM(sba.FYP)	TONG_FYP_PHAT_HANH_THUAN,
    private Double totalFypIssued; // SUM(sba.FYP_ISSUSED) TONG_FYP_PHAT_HANH,
    private Double totalRyp;// SUM(sba.RYP)	TONG_RYP_DA_THU,
    private Double k2;//ti le K2 thuc thu
    private Double k2Plus;//ti le K2+ thuc thu
	private Double totalProposalMoreInfo;
}
