package vn.com.unit.cms.core.module.customerManagement.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class TotalClaimGroupDto {
    private Integer no;
	private String id;
	private String orgId;
	private String orgName;
	private String orgParentId;
	private String orgParentName;
	
	private BigDecimal totalHandling;
	private BigDecimal totalWaiting;
	private BigDecimal totalAgreeing;
	private BigDecimal totalRejecting;
	private BigDecimal totalError;
	private BigDecimal total;
	
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
	
	private String gaCode;
	private String gaName;
	private String gaType;
	
	private String agentCode;
	private String agentName;
	private String agentType;
	private String agentGroup;
	private String parentGroup;
	private String childGroup;
	private BigDecimal numOfChild;
	private Integer agentLevel;
	private Integer treeLevel;
	private String agentAll;
	private String orgNameNew;
	private String orgCode;
	private String parentCode;
	private String parentAgentCode;
	private String parentAgentName;
}
