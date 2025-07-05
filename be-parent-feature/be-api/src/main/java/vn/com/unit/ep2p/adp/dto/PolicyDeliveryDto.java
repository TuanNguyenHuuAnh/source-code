package vn.com.unit.ep2p.adp.dto;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PolicyDeliveryDto {
	private String id;
	private String no; 					// STT
    private String policyNo; 			// Số hợp đồng
    private String po; 					// HỌ TÊN BMBH
    private String unitId; 				// Mã đơn vị
    private String partnerCode; 		// Mã đối tác
    private String unitNm; 				// Tên đơn vị
    private Date issuedDate; 			// Ngày phát hành
    private Date sentDate; 				// Ngày gửi
    private String packageNo; 			// Mã gói
    private Date emsDeliveryDate; 		// Ngày giao hàng EMS
    private Date unitConfirmedDate; 	// Ngày CN/PGD xác nhận
    private Date fcReceivedDate; 		// Ngày TVTC xác nhận
    private Date ackReceivedDate; 		// Ngày nộp thư xác nhận
    private Date policyReceiveDate; 	// Ngày khách hàng ký thư xác nhận
    private String status; 				// Tình trạng
    private String ackComment; 			// Lý do thư xác nhận không hợp lệ
    private String ackNote; 			// Ghi chú ACK
    private String note; 				// Ghi chú
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
