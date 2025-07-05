package vn.com.unit.cms.core.module.customerManagement.dto;

import java.math.BigDecimal;
import java.util.Date;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PersonalInsuranceDocumentDto {
	private Integer no;					//	STT
	private String agentName;			//	ten TVTC
	private String agentCode;			//	agentCode
	private String agentType;			//	chuc vu
	private BigDecimal fyp;				//	phi BH nam nhat
	private String policyOwner;			//	chu HDBH
	private String status;				//	trang thai
	private Date docReceivedDate;		//	ngay nhan HSYCBH
	private Date signDate;				//	ngay ki HSYCBH
	private String insuredPerson;		//	nguoi duoc BH chinh
	private String insuranceBuyer;		//	nguoi mua BH
	private String policyNo;			//	so HD
	private String docNo;				//	so HSYCBH
	private String ackStatus;			//	trang thai ACK
	private Date ackSendDate;			//	ngay nop ACK
	private String reason;				//	li do
	private Date releaseDate;           //	ngay phat hanh
	private Date rejectDate;            //	ngay tu choi
	private BigDecimal ip;				//	phi BH ki dau tien / Phí đầu tiên
	private String billingMethod; 		// 	dinh ky dong phi
	private BigDecimal pca; 			//  Phí dự tính định kỳ
	private BigDecimal modalPremium; 	//  Phí định kỳ/cơ bản định kỳ: 
	private boolean checkData;
	private Integer type;
	private Integer typeStatus;
	private String customerNo; 			//	mã khách hàng
	private Date ackReceivedDate; 		//	Ngày nộp thư xác nhận
	private Date ackCusSignDate; 		//	Ngày KH ký nhận
	private String statusText;
	private String ackStatusCd;
	private String ackComment;
	private Date oaSendPolicyDate;
}
