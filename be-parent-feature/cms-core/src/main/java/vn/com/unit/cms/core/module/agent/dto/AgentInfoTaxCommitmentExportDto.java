package vn.com.unit.cms.core.module.agent.dto;

import lombok.Getter;
import lombok.Setter;
import java.sql.Timestamp;

@Getter
@Setter
public class AgentInfoTaxCommitmentExportDto {
	private String agentName;
	private String fullAddress;
	private String taxCode;
	private Timestamp date;
	private String identificationNumber;
	private String agentCode;
}