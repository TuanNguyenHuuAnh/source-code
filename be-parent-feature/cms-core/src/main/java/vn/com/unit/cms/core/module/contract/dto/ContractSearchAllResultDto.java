package vn.com.unit.cms.core.module.contract.dto;

import java.math.BigDecimal;
import java.util.Date;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ContractSearchAllResultDto {
	private Integer no;							// STT
	private String insuranceContract;			// So HDBH
	private String customerName;				// Ben mua BH
	private String insuranceOfCustomerMain;			// Nguoi duoc BH chinh
	private String divisionRatio;				// Ty le chia
	private Date effectiveDate;					// Ngay hieu luc
	private String insuranceFees;				// Phi BH theo ky
	private String annualPremium;				// Phi BH nam
	private String periodicFeePayment;			// Dinh ki dong phi
	private Date payfeesDate;					// Ky dong phi
	private String assignmentForm;				// Hinh thuc phan cong
	private Date assignmentDate;				// Ngay phan cong
	private String financialAdvisorRole;		// Vai tro TVTC
	private String AdjustmentStatus;			// Trang thai dieu chinh
	private String claimStatus;                    // Trang thai claim 
	private String status;					// Trang thai claim
	private Date dueDate;						// Ngay dao han
	private String totalFeePaid;				// Tong phi da dong
	private String chargeStatus;				// Tinh trang thu phi
	private BigDecimal recurringBasicFee;			// Phí định kỳ/cơ bản định kỳ
	private BigDecimal estimatedRecurringFee;		// Phi du tinh dinh ki

	private String agentCode;					//tu van tai chinh
	private String agentName;
	private String agentType;
	private Date expirationDate;					//ngay mat hieu luc
	private BigDecimal hangingFee; 					//so phi con treo
	private String policyStatus; //trang thai hd
	private String debtApl; //no APL
	//ODS column
	private String policyKey;				// So HDBH
	private String poName;					// Ben mua BH
	private String liName;						// Nguoi duoc BH chinh
	private BigDecimal polAgtShrPct;				// Ty le chia
	private String polAgtShrPctStr;				// Ty le chia

	private Date polIssueEff;					// Ngay hieu luc
	private String polBillModeCd;				// Dinh ki dong phi
	private String catOfficialName;				// Hinh thuc phan cong
	private Date servAgtAsignDt;				// Ngay phan cong
	private String homeAddress;
	private String cellPhone;
	private boolean isCheck; 
	private Date dateDue;						//còn hiệu lực - ngày đáo hạn ; mạt hieu luc - ngat mat hieu luc
	
	private String customerNo;
	private Integer type;
	private Integer typeStatus;

	private BigDecimal polBaseFaceAmt;   //Số tiền bảo hiểm
	private BigDecimal polBasicMprem;	// Phí bảo hiểm cơ bản (HĐ IL/UL/Pension)
	private BigDecimal polExcessAmt;	// Phí đóng thêm (HĐ IL/UL/Pension)
	private BigDecimal totalPremiumPaid; // Tổng phí bảo hiểm đã đóng
	private BigDecimal polPremSuspAmt;
	private BigDecimal polMiscSuspAmt;
	private BigDecimal polCount;     //Phí đóng trước cho kỳ tới

	private String loaihopdong;
	private BigDecimal zprxUnpaidPremAmt;	// phi co ban cac ky truoc chua dong
	private Date polLapseStartDt;    //Ngay se mat hieu luc

	private BigDecimal feeMustReceive;
	private BigDecimal debts;

	private BigDecimal basicPremium;  //phi dong bao hiem
	private Integer isShare;

	private BigDecimal aplAmt;		// no apl
	
	private Date issuedDate;		// Ngày phát hành
	private String proposalNo;		// Số HSYCBH
	private String polStatus;		// Tình trạng HĐ
}
