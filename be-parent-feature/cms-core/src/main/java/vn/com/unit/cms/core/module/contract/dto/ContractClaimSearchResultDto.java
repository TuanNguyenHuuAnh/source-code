package vn.com.unit.cms.core.module.contract.dto;

import java.math.BigDecimal;
import java.util.Date;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ContractClaimSearchResultDto {
	private String managerAgentCode;
	private String managerAgentType;
	private String managerAgentName;
	private String agentCode;
	private String agentName; //tvtc
	private String agentType;
	private String docNo;
	private String policyNo; //so hdbh
	private String totalContract;//tong so HĐ
	private String no;
	private String insuranceBuyer;// nguoi mua bao hiem
	private String insuredPerson;// nguoi duoc bao hiem
	private String docCreatedDate;
	private String docType;
	private String infoWaitingToAdd;
	private String statusvn;
	private String actionDate;
	private String approvedDate;
	private String reasonReject;
	private BigDecimal compensationAmount;
	private String additionalDoc;
	private Date resultDate;//ngay co ket qua boi thuong
	private String agentAll;
	private String parentAll;
	private String claimNo;//so hs
	private Date scanDate;//ngay yeu cau
	private Date scanDateType;
	private String claimtype;  // loại
	private String rejectremarks;
	private String claimrejectedremarks;
	private boolean check;
	
	private String agent; // Tư vấn tài chính
	private String requestDetail; //Chi tiết yêu cầu
	private String note;  // Lý do từ chối
	private String infor; //Bổ sung chứng từ nhận quyền lợi thanh toán
	
	private String customerNo;

	private String documentname;		//Thông tin chờ bổ sung
	private Date expireddate;			//Ngày hết hạn bổ sung

	private Date approvedate; 			//ngày có kết quả bồi thường
	private BigDecimal totalapproveamount; //Số tiền bồi thường
	private String approvedremark;      // Bổ sung quyền lợi nhận thanh toán
	private String rejectedremark;		//Lý do từ chối
	private Integer isShare;
}

