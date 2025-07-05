package vn.com.unit.cms.core.module.agentbank.entity;

import java.util.Date;

import org.springframework.data.annotation.Id;

import jp.sf.amateras.mirage.annotation.Column;
import jp.sf.amateras.mirage.annotation.PrimaryKey;
import jp.sf.amateras.mirage.annotation.PrimaryKey.GenerationType;
import jp.sf.amateras.mirage.annotation.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Table(name = "M_AGENT_BANK_UPDATE_HISTORY")
public class AgentBankUpdateHistory {
	@Id
	@Column(name = "ID")
	@PrimaryKey(generationType = GenerationType.SEQUENCE, generator = "SEQ_M_AGENT_BANK_UPDATE_HISTORY" )
	private Long id;
	
	@Column(name = "AGENT_CODE")
	private String agentCode;
	
	@Column(name = "ID_NUMBER")
	private String idNumber;
	
	@Column(name = "BANK_ACCOUNT_NUMBER")
	private String bankAccountNumber;
	
	@Column(name = "BANK_ACCOUNT_NAME")
	private String bankAccountName;
	
	@Column(name = "BANK_CODE")
	private String bankCode;
	
	@Column(name = "BANK_NAME")
	private String bankName;
	
	@Column(name = "BANK_BRANCH_NAME")
	private String bankBranchName;
	
	@Column(name = "OTP")
	private String otp;
	
	@Column(name = "EMAIL")
	private String email;
	
	@Column(name = "TRANSACTION_TYPE")
	private String transactionType;
	
	@Column(name = "IMG1_PHYSICAL")
	private String img1Physical;
	
	@Column(name = "IMG2_PHYSICAL")
	private String img2Physical;
	
	@Column(name = "SUBMIT_TIME")
	private Date submitTime;
	
	@Column(name = "CONFIRM_TIME")
	private Date confirmTime;
}
