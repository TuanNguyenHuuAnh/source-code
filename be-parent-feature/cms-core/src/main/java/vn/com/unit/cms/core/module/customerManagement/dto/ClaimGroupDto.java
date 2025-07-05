package vn.com.unit.cms.core.module.customerManagement.dto;

import java.math.BigDecimal;
import java.util.Date;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ClaimGroupDto {
	private Integer no;
	private String managerAgentCode;// quan li
	private String managerAgentType;
	private String managerAgentName;
	private String agentCode;//tu van tai chinh
	private String agentName;
	private String agentType;
	private String policyNo; //so hd
	private String poName;	//ben mua bh
	private String liName;	//nguoi duoc bao hiem
	private String claimNo;
	private String claimType;
	private Date scanDate;	//ngay mo HS
	private String statusVn;
	private String parentAll;
	private String agentAll;
	private Date approvedate;
	private String approvedremark;
	private String rejectedremark;
	private BigDecimal totalapproveamount;
	private Date expireddate;
	private String documentname;
}
