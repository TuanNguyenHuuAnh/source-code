package vn.com.unit.cms.core.module.income.entity;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

import jp.sf.amateras.mirage.annotation.Table;
import jp.sf.amateras.mirage.annotation.Column;
import jp.sf.amateras.mirage.annotation.PrimaryKey;
import jp.sf.amateras.mirage.annotation.PrimaryKey.GenerationType;

import org.springframework.data.annotation.Id;

@Table(name = "M_TAX_REGISTRATION")
@Getter
@Setter
public class TaxRegistration {
	@Id
	@Column(name = "ID")
	@PrimaryKey(generationType = GenerationType.SEQUENCE, generator = "SEQ_M_TAX_REGISTRATION")
	private Long id;
	
	@Column(name = "AGENT_CODE")
	private String agentCode;
	
	@Column(name = "DOCUMENT_NAME")
	private String documentName;
	
	@Column(name = "REGISTRATION_TIME")
	private Date registrationTime;
}