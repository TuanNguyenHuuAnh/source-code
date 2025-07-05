package vn.com.unit.ep2p.core.ers.entity;

import org.springframework.data.annotation.Id;

import jp.sf.amateras.mirage.annotation.Column;
import jp.sf.amateras.mirage.annotation.PrimaryKey;
import jp.sf.amateras.mirage.annotation.PrimaryKey.GenerationType;
import jp.sf.amateras.mirage.annotation.Table;
import jp.sf.amateras.mirage.annotation.Transient;
import lombok.Getter;
import lombok.Setter;

@Table(name="ERS_WORKING_EXPERIENCE")
@Getter
@Setter
public class ErsWorkingExperience extends AbstractTracking {
	@Id
	@PrimaryKey(generationType = GenerationType.SEQUENCE, generator = "SEQ_ERS_WORKING_EXPERIENCE")
	@Column(name="ID")
	private Long id;
	
	@Column(name="CANDIDATE_ID")
	private Long candidateId;
	
	@Column(name="COMPANY_NAME")
	private String companyName;
	
	@Column(name="POSITION")
	private String position;
	
	@Column(name="AGENT_CODE")
	private Long agentCode;
	
	@Column(name="INCOME")
	private String income;
	
	@Column(name="WORKING_PERIOD")
	private String workingPeriod;
	
	@Transient
	private Boolean operation;
	
}
