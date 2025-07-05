package vn.com.unit.ep2p.adp.dto;

import java.math.BigDecimal;
import java.util.Date;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReportK2K2PlusDto {
	private String no;					// STT
	private String partner;				// Đối tác
	private String policyNo;			// Số HĐ
	private String poName;				// BMBH
	private Date issueDate;				// Ngày phát hành
	private Date effDate;				// Ngày hiệu lực
	
    private BigDecimal epp;				// Phí phải thu (epp)
    private BigDecimal app;				// Phí đã thu (app)
    private Double k2Rate;				// %K2
    private BigDecimal eppModif;		// Phí phải thu (epp)_modif
    private BigDecimal appModif;		// Phí đã thu (app)_modif
    private BigDecimal appRemaining;	// Phí đã thu (app)
    private BigDecimal opm;				// OPM
    private BigDecimal tp;				// TP2
    private BigDecimal ep;				// EP2
    private BigDecimal tpep;			// TP2 + EP2
    private BigDecimal tpep2;			// TP2 + 10%*EP2
    private Double adjustRate;			// He so dieu chinh
    private Double pfRate;				// He so dieu chinh PF
    
    private String polStatus;			// Tình trạng HĐ
    private String polBillMode;			// Định kỳ đóng phí
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
}
