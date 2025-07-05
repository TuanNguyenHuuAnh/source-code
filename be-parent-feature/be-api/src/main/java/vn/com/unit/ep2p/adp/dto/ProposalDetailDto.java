package vn.com.unit.ep2p.adp.dto;

import java.math.BigDecimal;
import java.util.Date;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProposalDetailDto {
	private String polNo;
	private String proposalNo;
	private String polStatus;
	private String polBillModeCd;
	
	private BigDecimal amount;
	private BigDecimal polSndryAmt;
	private BigDecimal polMpremAmt;
	private BigDecimal zprxUnpaidPremAmt;
	private BigDecimal totalPremiumPaidAmt; 
	private BigDecimal basicPremium;
	private BigDecimal zprxXcesPremAmt;
	private BigDecimal feeNextPeriodAmt;
	private BigDecimal excessPremiumAmt;
	private BigDecimal aplAmt;
	
	private Date polIssueEffDate;
	private Date polInfcDate;
	private Date polPaidToDate;
	private Date zprxLastPdToDate;
	private Date polCeasDate;
	private Date rejectedDate;
	private String rejectedReason;
	private String referralId;
	private String referralName;
	
	private String agentCode;
	private String agentName;
	private String agentType;
	
	private String poNo;
	private String poName;
	private Date poDateOfBirth;
	private String poGender;
	private String poIdNum;
	private String poCellPhone;
	private String poWorkPhone;
	private String poHomePhone;
	private String poOfficeAddress;
	private String poHomeAddress;
	private String poEmail;

	private String liName;
	private Date liDateOfBirth;
	private String liGender;
	private String liIdNum;
	private String liCellPhone;
	private String liWorkPhone;
	private String liHomePhone;
	private String liOfficeAddress;
	private String liHomeAddress;
	
	private String uoName;		// Tên đơn vị KD
	private BigDecimal shrtAmt;
}
