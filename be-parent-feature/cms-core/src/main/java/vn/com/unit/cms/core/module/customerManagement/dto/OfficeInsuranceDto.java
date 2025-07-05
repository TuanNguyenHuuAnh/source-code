package vn.com.unit.cms.core.module.customerManagement.dto;

import lombok.Getter;
import lombok.Setter;
import vn.com.unit.cms.core.module.agent.dto.InfoAgentDto;

import java.math.BigDecimal;

@Getter
@Setter
public class OfficeInsuranceDto extends InfoAgentDto {
	private String orgParentId;
	private String orgParentName;
	private String parentAgentCode;
	private String parentAgentType;
	private String parentAgentName;


	private String orgId;
	private String orgName;
	private String agentCode;
	private String agentName;
	private String agentType;

	private BigDecimal sumOfDocumentSubmitted;				//đã nộp
	private BigDecimal sumOfDocumentAddition;				//bổ sung
	private BigDecimal sumOfDocumentReleased;				//đã phát hành
	private BigDecimal sumOfDocumentRejected;				//từ chối
	private BigDecimal sumOfPolicyReleased;				//phát hành
	private BigDecimal sumOfPolicyCanceled;				//đồng hủy

	private String agentGroup;
	private String childGroup;
	
	private String paren;
	private String child;
	private String orgNameNew;

}
