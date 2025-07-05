package vn.com.unit.cms.core.module.payment.entity;

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
@Table(name = "M_CONFIRM_PAYMENT")
public class PaymentConfirm {
	@Id
	@Column(name = "ID")
	@PrimaryKey(generationType = GenerationType.SEQUENCE, generator = "SEQ_M_CONFIRM_PAYMENT" )
	private Long id;
	
	@Column(name = "GA_CODE")
	private String gaCode;
	
	@Column(name = "PERIOD")
	private String period;
	
	@Column(name = "AGENT_CODE")
	private String agentCode;
	
	@Column(name = "OTP")
	private String otp;
	
	@Column(name = "CONFIRM_TIME")
	private Date confirmTime;
	
	@Column(name = "REJECT_REASON")
	private String rejectReason;
	
	@Column(name = "REJECT_TIME")
	private Date rejectTime;
}

