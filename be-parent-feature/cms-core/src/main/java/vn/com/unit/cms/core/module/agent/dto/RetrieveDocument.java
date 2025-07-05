package vn.com.unit.cms.core.module.agent.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RetrieveDocument {
	private String sourceSystem;
	private String agentCode;
	private String templateType;
	private String catagory;
	private String letterType;
	private String agentMovementId;
	private String assignDate;
	private String policyNo;
	private String effectiveDate;

}
