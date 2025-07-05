package vn.com.unit.cms.core.module.agent.dto;

import java.util.Date;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AgentDisciplinaryDetailDto {
	private boolean statusTVTC;//1 dl ter, 2 dl inforce
	private String fullName;
	private Integer debt;//no tien
	private Integer receipts;//phieu thu
	private boolean liquidation;
	private String phoneNumber;
	private String fax;
	private String email;
	private String emailDlvn;
	private String temporaryAddress;
	private String permanentAddress;
	private String agentCode;
	private String agentTypeName;
	private String officeName;
	private String gadType;
	private String gadCode;
	private String gadName;
	private String introducerType;
	private String introducerCode;
	private String introducerName;
	private String branchType;
	private String branchCode;
	private String branchName;
	private Date appointDate;
	private String takingWinner;
	private List<String> takingWinnerList;
	private String idPersonalIncomeTax;
	
}
