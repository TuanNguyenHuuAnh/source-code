package vn.com.unit.cms.core.module.customerManagement.dto;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ContractEffectGroupDto {
	private Integer no;
	private String managerAgentCode;// quan li
	private String managerAgentType;
	private String managerAgentName;
	private String managerOrgId;
	private String managerAgentGroup;
	private String agentCode;//tu van tai chinh
	private String agentName;
	private String agentType;
	private Integer policyNo; //so hd
	private Integer totalContract; //so hd
	private String insuranceBuyer;// ben mua bao hiem
	private String insuredPerson;//nguoi duoc bao hiem
	private Date effectiveDate;//ngay hieu luc
	private Integer periodAmount;
	private Integer yearAmount;
	private String recurringFeePayment;//dinh ki dong phi
	private String premiumPaymentPeriod;
	private String assignmentForm; //hinh thuc phan cong
	private String assignedContract;
	private Date assignmentDate;//ngay phan cong
	private boolean contractAdjustmentStatus;
	private boolean claimStatus;
	private Date dueDate;//ngay dao han
	private Integer totalFeesPaid;
	private boolean tollStatus;
	private Integer divisionRatio; //ti le chia
	private String parentAll;
	private String agentAll;
	private String parentGroup;
	private String childGroup;
	private String requestAdjustment;// yeu cau dieu chinh
	private String status;// trang thai
	private String waitingAddInformation;//thong tin cho bo sung
	private Date requestDate;// ngay yeu cau
	private Date startDate;// ngay thuc hien
	private Date addExpirationDate;// ngay het han bo sung
	
	// Survey
	private String surveyStatus;				// Trạng thái khảo sát: 0: Không đồng ý mua mới, 1: Đồng ý mua mới, 2: Phân vân
	private String totalNewContract;				// Tổng số HĐBH mới phát sinh
}
