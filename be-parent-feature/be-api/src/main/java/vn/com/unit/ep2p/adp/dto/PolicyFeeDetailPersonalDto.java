package vn.com.unit.ep2p.adp.dto;

import java.math.BigDecimal;
import java.util.Date;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PolicyFeeDetailPersonalDto {

	private Integer no; // STT

	private String policyNo; // số HD

	private String docNo; // số HSYCBH

	private String policyOwner; // Họ tên BMBH

	private String polStatus; // trạng thái HĐ

	private Date docReceivedDate; // Ngày nhận HS

	private Date issueDate; // Ngày phát hành

	private Date premiumDueDate; // Thời hạn hợp đồng

	private String billingMethod; // Định kỳ đóng phí

	private BigDecimal tp; // Phí cơ bản (TP)

	private BigDecimal ep; // Phí đóng thêm (EP)

	private BigDecimal ipCs; // Tổng phí

	private BigDecimal ip; // IP (100% TP + 10% EP)

	private BigDecimal rfyp; // RFYP (Năm 1 và điều kiện POL_CS_STAT_CD)

	private BigDecimal ryp; // RYP (Năm lớn hơn 1)

	private int premiumYear; // Năm phí

	private int policyYear; // Năm HĐ

	private String planId; // Mã sản phẩm

	private String packageName; // Tên sản phẩm

	private Date premiumApplyDate; // Ngày phát sinh phí

	private String agentCode; // Mã số ĐLBH

	private String agentName; // Tên ĐLBH

	private String agentType; // Loại ĐLBH

	private String saleMode; // Kênh

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
	
	private String productCode; // Mã Sản phẩm
	
	private String productName; // Tên Sản phẩm
	
	private String referralCode; // Mã NVGTCT

	private String referralBankCode; // Mã NH NVGTCT

	private String referralName; // Tên NVGTCT
	
}