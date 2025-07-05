package vn.com.unit.cms.core.module.customerManagement.dto;

import java.math.BigDecimal;
import java.util.Date;

import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
public class ContractOverdueFeeRypDto {
	private Integer no;
	private String managerAgentCode;
	private String managerAgentType;
	private String managerAgentName;
	private String agentCode;
	private String agentName;
	private String agentType;
	private String insuranceBuyer;		// ben mua bao hiem
	private Date effectiveDate;
	private Date birthday;
	private String address;
	private String phoneNumber;
	private String recurringFeePayment;	//dinh ki dong phi
	private String premiumPaymentPeriod;//phi dinh ki
	private BigDecimal feeMustReceive;	//phi phai thu	- Phí các kỳ trước chưa đóng
	private Date paymentPeriodDate;		//ki dong phi
	private Date paymentPeriodRecent;	//ki dong phi gan nhat		- Kỳ đóng đủ phí gần nhất
	private String policyNo;			//so hd
	private Integer totalContract;		//tong so hd
	private BigDecimal feeExpected; 	//phi mong doi
	private Date expiredDate;			//ngay se mat hieu luc
	private String managerAll;
	private String agentAll;
	private String parentAll;
	private Integer treeLevel;
	private String lv1AgentCode;
	private String lv1AgentName;
	private String lv1AgentType;
	private String lv1AgentGroup;
	private String lv2AgentCode;
	private String lv2AgentName;
	private String lv2AgentType;
	private String lv2AgentGroup;
	private String lv3AgentCode;
	private String lv3AgentName;
	private String lv3AgentType;
	private String lv3AgentGroup;

	private Date terminatedDate;		//Ngay cham dut ma so
	private String fullAddress;			//Dia chi thu phi
	private String workPhone; 			//DT co quan
	private String polBillModeCd;		//Dinh ky thu phi
	private BigDecimal	phiDuTinhDinhKy;	//phi du tinh dinh ky
	private BigDecimal phiCoBanDinhKy; 	//phi co ban dinh ky
	private String phoneNumberNr;
	private String phoneNumberOffice;

}
