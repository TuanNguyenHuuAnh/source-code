package vn.com.unit.cms.core.module.contract.dto;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ClaimOdsDetailDto {
	private String policyKey;
	private String poId;
	private String poName;
	private String servicingAgentKey;
	private String agentName;
	private String policyno;
	private String claimno;
	private String insuredclientid;
	private String liName;
	private String scanneddate;
	private String modifiedondate;
	private String claimtype;
	private String statusid;
	private String claimstsatus;
	private String approvedate;
	private String approvedremark;
	private String rejectedremark;
	private BigDecimal totalapproveamount;
	private String WFADDDOCID;
	private String wfadddocid;
	private String subdocid;
	private String doctypeid;
	private String expireddate;
	private String submitteddate;
	private String doctypename;
	private String assessorcomment;
	private Integer isShare;
}

