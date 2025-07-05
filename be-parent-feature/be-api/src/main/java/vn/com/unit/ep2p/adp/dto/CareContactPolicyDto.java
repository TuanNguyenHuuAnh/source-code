package vn.com.unit.ep2p.adp.dto;

import java.math.BigDecimal;
import java.util.Date;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CareContactPolicyDto {
	private int no;
	private String polStatus;
	private String policyOwner;
	private String policyNo;
	private Date issuedDate;
	private BigDecimal amount;	
	private String iconType;
	private String premiumStatus;		// Tình trạng phí
	private Date polPaidToDate;			// Ngày đến hạn đóng phí
	private String partner; 			// Đối Tác
	private Date polIssueEff; 			// Ngày hiệu lực
	private String polBillModeCd; 		// Định kỳ đóng phí
	private BigDecimal polMpremAmt; 	// Phí bảo hiểm định kỳ
	private BigDecimal tp; 				// Phí dự tính đóng định kỳ
	private Date dueToDate; 			// Ngày đến hạn đóng phí
	private BigDecimal expectedPremium; // Phí tái tục cần phải thu
	private String referralCode; 		// Mã NVGTCT
	private String referralBankCode; 	// Mã NH NVGTCT
	private String referralName; 		// Tên NVGTCT
	private String packageName; 		// Tên sản phẩm
	private String region; 				// Khu Vực/ Vùng
	private String branchCode; 			// Mã Cụm
	private String branchName; 			// Tên Cụm
	private String uoCode; 				// Mã ĐVKD
	private String uoName; 				// Tên ĐVKD
	private String agentCode; 			// Mã số ĐLBH
	private String agentName; 			// Tên ĐLBH
	private String agentType; 			// Loại ĐLBH
	private String ilAgentCode; 		// Mã IL
	private String ilAgentName; 		// Tên IL
	private String ilAgentType; 		// Loại ĐLBH (IL)
	private String smAgentCode; 		// Mã SM
	private String smAgentName; 		// Tên SM
	private String smAgentType; 		// Loại ĐLBH (SM)
	private String regionDlvn; 			// Tên Vùng DLVN
	private String rdName; 				// Tên RD
	private String areaDlvn; 			// Tên Khu Vực DLVN
	private String adName; 				// Tên AD
	private Date createdDate; 			// Ngày cập nhật
	private Date updatedDate; 			// Ngày chỉnh sửa
	private Date contactDate; 			// Ngày liên hệ
	private Integer contactDays;		// Ngày liên hệ - Ngày Đến hạn
	private String contactStage; 		// Chương trình/Giai đoạn liên hệ
	private Integer contactStageDays;	// Số ngày giai đoạn liên hệ
	private String contactMethod; 		// Hình thức liên hệ
	private String contactResult; 		// Kết quả liên hệ
	private String notes; 				// Ghi chú
	private String contactGroup; 		// Nhóm liên hệ
	private String contactCode; 		// Mã Liên hệ
}