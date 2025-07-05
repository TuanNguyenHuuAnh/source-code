package vn.com.unit.cms.core.module.ga.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class GrowthGaDto extends  GrowthGaQuarterlyGrowthBonusDto{

    private String orgTypeName; // tên vùng RH - vung kinh doanh
    private String orgId;
    private String orgName;  // gaOffice - van phong

    private String agentCode;
    private String agentName; //gad

    private String gaName;
    private String countBmAgentCode;        // quantityBm
    private String countUmTypeAgentCode;    // quantityUmOrBm
    private String countFcAgentCode;        // quantityAgent
    private String countActive;             // quantityActive
    private String countPolicy;             // ho so nop
    private String countNewRecruiment;      // tuyen dung
    private String policyCountReceived;     // fyp nop
    private String fypReceived ;            // hd phat hanh
    private String fypissued;               // fyp phat hanh
    private String ryp;
    private String k2App;                   // k2 =  k2A/k2E
    private String k2Epp;
    private String k2PlusApp;               // k2Plus =  k2AP/k2EP
    private String k2PlusEpp;

    private String quantityRecruitNew;
    private String quantitySubmitDoc;
    private String quantitySubmitFyp;
    private String quantityContractRelease;
    private String quantityFypRelease;
    private String quantityRyp;
    private String ratioK2;
    private String ratioK2Plus;
    private Date cutoffDate;

    private String lastYearAgentCode;
    private String lastYearAgentName;
    private String lastYearOrgId;
    private String lastYearGaName;
    private String lastYearQuantityBm;
    private String lastYearQuantityUmOrBm;
    private String lastYearQuantityAgent;
    private String lastYearQuantityRecruitNew;
    private String lastYearQuantityActive;
    private String lastYearQuantitySubmitDoc;
    private String lastYearQuantitySubmitFyp;
    private String lastYearQuantityContractRelease;
    private String lastYearQuantityFypRelease;
    private String lastYearQuantityRyp;
    private String lastYearRatioK2;
    private String lastYearRatioK2Plus;

    private String quarter;
    private String data;                    //Số liệu
    private String officeGa;



}
