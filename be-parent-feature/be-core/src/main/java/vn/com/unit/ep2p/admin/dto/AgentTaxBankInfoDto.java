package vn.com.unit.ep2p.admin.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AgentTaxBankInfoDto {
	private String taxCodeInb;
	private String businessHouseholds;
	private String bankAccountNumber;
	private String bankAccountName; 
	private String trainedId;
	private String agentType;
	private String agentName;
}
