package vn.com.unit.ep2p.adp.dto;

import java.math.BigDecimal;
import java.util.Date;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PersonalInsuranceDto {
	private Integer no;					// STT
	private String policyNo;			// Số HĐBH
	private String docNo;				// Số HSYCBH
	private String policyOwner;			// BMBH
	private Date docReceivedDate;		// Ngày nhận HS
	private Date polIssueEff;			// Ngày hiệu lực
	private Date issueDate;				// Ngày phát hành
	private Date inActiveDate;			// Ngày mất hiệu lực
	private Date premiumDueDate;		// Thời hạn hợp đồng
	private Date polCeasDate; 			// Ngày đáo hạn
	private BigDecimal faceAmount;		// Mệnh giá sản phẩm chính	
	private BigDecimal tp;				// Phí cơ bản (TP)	
	private BigDecimal ep;				// Phí đóng thêm (EP)
	private BigDecimal ipCs;			// Tổng phí thực nộp kỳ đầu (IP_CS)
	private BigDecimal ip;				// IP (100% TP + 10% EP)
	private String status;				// Tình trạng HSYCBH
	private String polStatus;			// Tình trạng HĐ
	private Integer polAgtShrPct;		// Tỉ lệ chia
	private String billingMethod;		// Định kỳ đóng phí
	private BigDecimal pca;				// Phí dự tính định kỳ
	private BigDecimal modalPremium; 	// Phí định kỳ/cơ bản định kỳ
	private BigDecimal apl;				// Nợ APL(Vay đóng phí + Lãi)	
	private BigDecimal amount;			// Số tiền	
	private Date paymentPeriodDate;		// Kỳ đóng phí
	private BigDecimal feeNextPeriod;	// Phí đóng trước cho kỳ tới	
	private BigDecimal hangingFee;		// Số phí còn treo
	private String note;				// Ghi chú	
	private String referralCode;		// Mã NVGTCT	
	private String referralBankCode;	// Mã NH NVGTCT
	private String referralName;		// Tên NVGTCT
	private String packageName;			// Sản phẩm	
	private String region;				// Khu Vực/ Vùng
	private String branchCode;			// Mã Cụm
	private String branchName;			// Tên Cụm
	private String uoCode;				// Mã ĐVKD
	private String uoName;				// Tên ĐVKD
	private String agentCode;			// Mã số ĐLBH
	private String agentName;			// Tên ĐLBH
	private String agentType;			// Loại ĐLBH 
	private String ilAgentCode;			// Mã IL
	private String ilAgentName;			// Tên IL
	private String ilAgentType;			// Loại ĐLBH (IL)
	private String smAgentCode;			// Mã SM
	private String smAgentName;			// Tên SM
	private String smAgentType;			// Loại ĐLBH (SM)
	private String branchDlvn;			// Tên Cụm DLVN	
	private String zdName;				// Tên ZD
	private String regionDlvn;			// Tên Vùng DLVN
	private String rdName;				// Tên RD
	private String areaDlvn;			// Tên Khu Vực DLVN
	private String adName;				// Tên AD
	private String partner;				// Đối tác
}
