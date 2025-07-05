package vn.com.unit.ep2p.adp.dto;

import java.math.BigDecimal;
import java.util.Date;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PolicyClaimSearchResultDto {
	private Integer no;					// STT
	private String policyNo;			// Số
	private String proposalNo;			// Số HSYCBH
	private String polStatus;			// trang thai HĐ
	private String policyOwner;			// Ben mua bao hiem
	private String insuredPerson;		// Người được BH
	private String claimNo;				// Số hồ sơ bồi thường
	private Date scanDate;				// Ngày mở hồ sơ
	private String claimtype;			// Loại Yêu cầu bồi thường
	private String statusvn;			// Tình trạng
	private Date expiredDate;			// Ngày hết hạn bổ sung
	private Date approveDate;			// Ngày có kết quả bồi thường
	private BigDecimal approveAmt;		// Số tiền bồi thường
	private String remark;				// Ghi chú
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
	private String addInformation;		// Thông tin chờ bổ sung
	private String assessorComment;		// Diễn giải
}
