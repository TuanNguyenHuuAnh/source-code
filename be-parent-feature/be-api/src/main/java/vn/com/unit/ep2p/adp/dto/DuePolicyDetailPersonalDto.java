package vn.com.unit.ep2p.adp.dto;

import java.math.BigDecimal;
import java.util.Date;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DuePolicyDetailPersonalDto {

	private int no; // ROW_NUMBER

	private String policyNo; // số HD

	private String docNo; // số HSYCBH

	private String policyOwner; // Họ tên BMBH

	private String polStatus; // trạng thái HĐ

	private String poHomeAddress; // Địa chỉ BMBH

	private String poHomePhone; // Điện thoại nhà

	private String poCellPhone; // Điện thoại di động

	private String lifeInsured; // Tên NĐBH

	private BigDecimal polBaseFaceAmt; // Mệnh giá SP chính

	private Date polIssueEff; // Ngày hiệu lực

	private String polBillModeCd; // Định kỳ đóng phí

	private BigDecimal ape; // Phí năm

	private BigDecimal polMpremAmt; // Phí bảo hiểm định kỳ

	private BigDecimal tp; // Phí dự tính đóng định kỳ

	private BigDecimal polSuspenseAmt; // Phí treo
	
	private BigDecimal shrtAmt; // Shortage Amount

	private Date dueToDate; // Ngày đến hạn đóng phí

	private String premiumStatus; // Tình trạng phí

	private BigDecimal expectedPremium; // Phí tái tục cần phải thu

	private Date polLstPaidToDate; // Kỳ phí đóng đủ gần nhất

	private String planId; // Mã sản phẩm

	private String packageName; // Tên sản phẩm

	private String agentCode; // Mã số ĐLBH

	private String agentName; // Tên ĐLBH

	private String agentType; // Loại ĐLBH

	private String saleMode; // Kênh

	private String referralCode; // Mã NVGTCT

	private String referralBankCode; // Mã NH NVGTCT

	private String referralName; // Tên NVGTCT

	private String region; // Khu Vực/ Vùng

	private String branchCode; // Mã Cụm

	private String branchName; // Tên Cụm

	private String uoCode; // Mã ĐVKD

	private String uoName; // Tên ĐVKD

	private String ilAgentCode; // Mã IL

	private String ilAgentName; // Tên IL

	private String ilAgentType; // Loại ĐLBH (IL)

	private String smAgentCode; // Mã SM

	private String smAgentName; // Tên SM

	private String smAgentType; // Loại ĐLBH (SM)

	private String branchDlvn; // Tên Cụm DLVN

	private String zdName; // Tên ZD

	private String regionDlvn; // Tên Vùng DLVN

	private String rdName; // Tên RD

	private String areaDlvn; // Tên Khu Vực DLVN

	private String adName; // Tên AD

	private String partner; // Đối tác
	
}