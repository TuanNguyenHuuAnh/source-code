package vn.com.unit.cms.core.module.customerManagement.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;

@Getter
@Setter
public class OfficeInsuranceContractDetailDto {
	private String no;
	private String manager;						// MANAGER
	private String managerAgentType;
	private String managerAgentCode;
	private String managerAgentName;
	private String agent;						// TVTC
	private String agentType;
	private String agentCode;
	private String agentName;
	private String office;						// office
	private String officeName;
	private String officeCode;
	private String policyKey;					//so HD
	private String proposalCode;				//so HSYCBH
	private String insuranceBuyer;				// ben mua bao hiem
	private String insuredPerson;				// nguoi dc bao hiem
	private String divisionRatio;				// ti le chia
	private String recurringFeePayment;			// dinh ky dong phi
	private Date effectiveDate;				    // ngay hieu luc
	private String planName;					// ten sp
	private String ape;							// phi phat hang
	private Date cancelDate;					// ngay huy
	private String cancelReason;				// ly do huy
	private String apeCancel;					// phi huy
	private Integer totalContract;
	private Integer totalProposal;				//SO HS
	private Date polInfcDt;						//Ngày phát hành
	private BigDecimal polSndryAmt;				//phi du tinh dinh ky
	private BigDecimal polMpremAmt;				//phi dinh ky/co ban dinh ky
	private BigDecimal ip;						// Phi dau tien
	private BigDecimal polBaseFaceAmt; 			// tien bao hiem
	private String policyNo;
	private Date polIssueEff;         			//Ngay hieu luc :HSYCBH phat hang
	private String polBillModeCd;			//Dinh kì dong phi :HSYCBH phat hang
	private String orgId;
	private String orgCode;
	private BigDecimal hangingFee;
	private Date expirationDate;					//ngay mat hieu luc
	private BigDecimal polNtuCnclFeeAmt;

}
