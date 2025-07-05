package vn.com.unit.cms.core.module.customerManagement.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;

@Getter
@Setter
public class ContractFeeGroupDto {
	private String no;
	private String policyNo;//so hop dong
	private BigDecimal totalContract;//tong so hop dong
	private String managerAgentCode;
	private String managerAgentType;
	private String managerAgentName;
	private String agentCode;
	private String agentName;
	private String agentType;
	private String insuranceContract;
	private String customerName;
	private BigDecimal feeMustReceive; //fee phi can thu
	private BigDecimal feeExpected;//phi mong doi
	private String parentGroup;
	private String childGroup;
	private String orgParentId;
	private String orgParentName;
	private String insuranceBuyer;
	private String agentAll;
	private String managerAll;
	private String parentAll;
	private String premiumStatus;   //tinh trang thu phi
	private Integer treeLevel;
	private String lv1AgentCode;
	private String lv1AgentName;
	private String lv1AgentType;
	private String lv2AgentCode;
	private String lv2AgentName;
	private String lv2AgentType;
	private String lv3AgentCode;
	private String lv3AgentName;
	private String lv3AgentType;

	private Date terminatedDate;		//Ngay cham dut ma so
	private String fullAddress;			//Dia chi thu phi
	private String workPhone; 			//DT co quan
	private String polBillModeCd;		//Dinh ky thu phi
	private BigDecimal	phiDuTinhDinhKy;	//phi du tinh dinh ky
	private BigDecimal phiCoBanDinhKy; 	//phi co ban dinh ky
	private Date expiredDate;			//ngay se mat hieu luc
	private Date paymentPeriodRecent;	//ki dong phi gan nhat		- Kỳ đóng đủ phí gần nhất
	private String address;
	private String recurringFeePayment;	//dinh ki dong phi
	private String premiumPaymentPeriod;//phi dinh ki
	private Date paymentPeriodDate;		//ki dong phi
	private String phoneNumber;
	private String phoneNumberNr;
	private String phoneNumberOffice;



}
