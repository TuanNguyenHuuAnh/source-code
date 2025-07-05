package vn.com.unit.ep2p.adp.dto;

import java.math.BigDecimal;
import java.util.Date;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PolicyRequestSearchResultDto {
	private Integer no;					// STT
	private String policyNo;			// Số HĐ
	private String proposalNo;			// Số HSYCBH
	private String polStatus;			// trang thai HĐ
	private String insuranceBuyer;		// Ben mua bao hiem
	private Date issuedDate;			// Ngày phát hành
	private String processtypecode;
	private String workitem;
	private String requestAdjustment; 	// Loai yeu cau
	private Date requestDate;			// Ngay tiep nhan
	private String status;				// Tinh trang xu ly
	private String addInformation;		// Thong tin cho bo sung
	private Date addExpirationDate;		// Ngay het han bo sung
	private Date startDate;				// Ngay thuc hien
	private BigDecimal initalAmount;	// STBH ban đầu
	private BigDecimal adjustmentAmount;// STBH sau điều chỉnh
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
