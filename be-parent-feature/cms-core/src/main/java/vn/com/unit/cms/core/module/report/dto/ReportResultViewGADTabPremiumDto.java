package vn.com.unit.cms.core.module.report.dto;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReportResultViewGADTabPremiumDto {
    //PremiumDto
	private Integer policyCountReceived;           //--tổng hồ sơ nộp
	private BigDecimal fypReceived;                   //--tổng fyp nộp
	private Integer policyCountIssued;             //--tổng hồ sơ phát hành
	private BigDecimal fypIssued;                     //--tổng hồ sơ phát hành
	private Integer policyCount;                   // --tổng hồ sơ phát hành thuần
	private BigDecimal fyp;                           // --tổng fyp phát hành thuần
	private Integer policyCountCancel;             // --tổng hồ sơ hủy
	private BigDecimal fypCancel;                     // --tổng fyp hủy
	private BigDecimal ryp;                           //--tổng ryp
	private BigDecimal rfyp;                          //--đc/tái tục ryp
    private BigDecimal k2App;                          //--thực thu
    private BigDecimal k2Epp;                          //--dự thu
    private BigDecimal k2plusApp;                      //--thực thu
    private BigDecimal k2plusEpp;                      //--dự thu
    private BigDecimal k2;                             //(k2App/k2Epp)
    private String k2Str;
    private BigDecimal k2Plus;                         //(k2plusApp+/k2plusEpp+)//12
    private String k2PlusStr;

    //detail
    private Integer lastPolicyCountIssued;          //hs phát hành 
    private BigDecimal lastFypIssued;                  //fyp phát hành F
    private Integer lastPolicyCountReceived;        //hs nộp
    private BigDecimal lastFypReceived;                //fyp nộp 
    private Integer lastPolicyCount;                //hs phát hành thuần
    private BigDecimal lastFyp;                        //Fyp phát hành thuần
    //private Integer lastPolicyCountCancel;        //hs huy    -> ko có hủy
    //private Integer lastFypCancel;                //fyp huy   -> ko có hủy
    private BigDecimal lastRyp;                        //Tổng RYP
    private BigDecimal lastRfyp;                       //ĐC/Tái tục RYP
    
    //Manpower
    private Integer countBmTypeAgentcode;       //sl
    private Integer countUmTypeAgentcode;
    private Integer countPumTypeAgentcode;
    private Integer countFcTypeAgentcode;
    private Integer countSaTypeAgentcode;
    private Integer countBmAgentcode;       //sl
    private Integer countUmAgentcode;
    private Integer countPumAgentcode;
    private Integer countFcAgentcode;
    private Integer countSaAgentcode;
    private Integer countNewRecruitment;       //Số lượng Tuyển dụng
    private Integer countReinstate;            //Số lượng khôi phục mã số
    private Integer countActive;               //Số lượng FC hoạt động
    private Integer countNewRecruitmentActive; //Số lượng FC mới hoạt động
    private Integer countSchemeFc;             //Số lượng FC hoạt động theo scheme
    private Integer countPfcFc;                //Số lượng FC năng động (PFC)
    private BigDecimal sumFyp;
    private BigDecimal sumRyp;
    private Integer lastDate;
    //detail
    private Integer lastCountNewRecruitment;   //Số lượng Tuyển dụng
    private Integer lastCountReinstate;        //Số lượng khôi phục mã số
    private Integer lastCountActive;           //Số lượng hoạt động
    private Integer lastCountNewRecruitmentActive;     //Số lượng TV mới hoạt động
    
    private String orgId;
    private String orgParentId;
    private String parentId;
    private String orgParentName;
    
    private Integer grandTotal;
    private String agentCode;
    private String agentName;
    private String agentType;
    
    private String bdthName;
    private String bdthCode;
    private String bdthType;

    private String bdahName;
    private String bdahCode;
    private String bdahType;

    private String bdrhName;
    private String bdrhCode;
    private String bdrhType;

    private String bdohName;
    private String bdohType;
    private String bdohCode;

    private String branchName;
    private String branchCode;
    private String branchType;

    private String unitName;
    private String unitCode;
    private String unitType;

    private String caoCode;
    private String caoName;
    private String caoType;

    private String managerAgentName;
    private String managerAgentCode;
    private String managerAgentType;

    private String gaCode;//office
    private String gaName;
    private String gaType;

    private String parentCode;
    private String parentAgentCode;
    private String parentAgentName;
    private String parentAgentType;
    private Integer agentLevel;
    private Integer treeLevel;
    
    private String parentGroup;
    private String childGroup;
    private String orgName;
}
