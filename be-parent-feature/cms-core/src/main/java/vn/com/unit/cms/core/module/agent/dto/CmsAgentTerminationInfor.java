package vn.com.unit.cms.core.module.agent.dto;

import java.math.BigDecimal;
import java.util.Date;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CmsAgentTerminationInfor {
	private String agentStatus;
	private Integer checkAgentExist;
	private String fullNameAgent;
	private String agentCode;
	private String office;
	private String officeName;
	private Date expriteDate;
	private BigDecimal debt;
	private String receipts;
	private String liquidation;
	private String terminationLetter;
	private String liquidationLetter;
	private String recruitment;
	private String emailAddressPersonal;
	private Integer checkEmailExist;
	private Integer agentStatusCode;
	private String soNoQuyenPhieuThu;
}
