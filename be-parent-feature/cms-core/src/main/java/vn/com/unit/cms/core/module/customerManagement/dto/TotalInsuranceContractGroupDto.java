package vn.com.unit.cms.core.module.customerManagement.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class TotalInsuranceContractGroupDto {
    private Integer no;
	private String id;
	private String orgId;
	private String orgParentId;
	private String parentId;
	private String orgParentName;
	private String orgName;
	private BigDecimal totalContractEffect;//hd con hieu luc

	private BigDecimal totalContractDueFee;//hd den han thu phi
	private BigDecimal totalContractOverdueFeeRyp;//hd qua han thu phi ryp 30 ngay
	
	private BigDecimal totalContractExpired;//hd da dao han
	private BigDecimal totalContractExpired30Days;// hd se dao han 30 ngay
	
	private BigDecimal totalContractInvalid;//hd mat hieu luc
	private BigDecimal totalContractOrphan;// hd mo coi
	private BigDecimal totalContractClaim;//HD yeu cau boi thuong
	private BigDecimal totalPolicyMatured;	// hd da dao han(trong 3 tháng)
	private BigDecimal sumOfPolicyClaim;
	private String agentCode;
	private String agentName;
	private String agentType;
	private String reportingToCode;
	private String reportingToName;
	private String reportingToType;
	private String agentGroup;
	private String parentGroup;
	private String childGroup;
	private BigDecimal numOfChild;
	private String parentAll;//type-code-name
	private String agentAll;
	private BigDecimal total;
	private BigDecimal totalGrand;

	private String bdthName;
	private String bdthCode;
	private String bdthType;

	private String bdahName;
	private String bdahCode;
	private String bdahType;

	private String bdrhName;
	private String bdrhCode;
	private String bdrhType;

	private String bdohName;
	private String bdohType;
	private String bdohCode;

	private String branchName;
	private String branchCode;
	private String branchType;

	private String unitName;
	private String unitCode;
	private String unitType;

	private String caoCode;
	private String caoName;
	private String caoType;

	private String managerAgentName;
	private String managerAgentCode;
	private String managerAgentType;

	private String gaCode;
	private String gaName;
	private String gaType;

	private String parentCode;
	private String parentAgentCode;
	private String parentAgentName;
	private Integer agentLevel;
	private Integer treeLevel;
	private String orgNameNew;
	private String orgCode;

	// Survey
	private String surveyStatus;				// Thông tin khảo sát: 0: null, 1: Có nhu cầu mua mới, 2: Đã nộp hồ sơ mua mới
}
