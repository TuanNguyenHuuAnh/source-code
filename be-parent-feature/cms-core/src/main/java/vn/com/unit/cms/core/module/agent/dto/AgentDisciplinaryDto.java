package vn.com.unit.cms.core.module.agent.dto;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AgentDisciplinaryDto {
	private Integer no;
	private String saleOfficeName;
	private String saleOfficeCode;
	private String agentType;
	private String agentCode;
	private String agentName;
	private String disciplineCode;
	private String disciplineType;
	private Date requestDate;
	private String disciplinaryReasons;
	private String disciplinaryCode;
	private String disciplinaryName;
	private Date effectiveDate;
	private Date expirationDate;
	private String userSuggestViolation;
	private String decisionNumber;
	private String linkLetter;
	private boolean share;
	private boolean download;
	private Date startDate;
	private Date endDate;
	//export
	private String bdoh;
	private String bdohCode;
	private String bdohName;
	private String tvtc;
	private String tdCode;
	private String tdName;
	private String bdthCode;
	private String bdthName;
	private String bdthAgenttype;
	private String nCode;
	private String nName;
	private String bdahCode;
	private String bdahName;
	private String bdahAgenttype;
	private String rCode;
	private String rName;
	private String bdrhCode;
	private String bdrhName;
	private String bdrhAgenttype;
	private String bdohAgenttype;
	private String orgCode;
	private String orgName;
	private String gadCode;
	private String gadName;
	private String gadType;

	private String territory;
	private String bdth;
	private String area;
	private String bdah;
	private String region;
	private String bdrh;
	private String office;
	private String gad;
	private String branch;
	private String unit;
	private String headOfDepartmentCode;
	private String headOfDepartmentName;
	private String headOfDepartmentType;
	private String managerCode;
	private String managerName;
	private String managerType;
	private Integer totalAgent;
}
