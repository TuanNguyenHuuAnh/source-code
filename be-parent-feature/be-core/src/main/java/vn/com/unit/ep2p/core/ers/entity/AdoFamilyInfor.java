package vn.com.unit.ep2p.core.ers.entity;

import org.springframework.data.annotation.Id;

import jp.sf.amateras.mirage.annotation.Column;
import jp.sf.amateras.mirage.annotation.PrimaryKey;
import jp.sf.amateras.mirage.annotation.PrimaryKey.GenerationType;
import jp.sf.amateras.mirage.annotation.Table;
import jp.sf.amateras.mirage.annotation.Transient;
import lombok.Getter;
import lombok.Setter;

@Table(name="ERS_FAMILY_INFOR")
@Getter
@Setter
public class AdoFamilyInfor extends AbstractTracking{
	@Id
	@PrimaryKey(generationType = GenerationType.SEQUENCE, generator = "SEQ_ERS_FAMILY_INFOR")
	@Column(name="ID")
	private Long id;
	
	@Column(name="CANDIDATE_ID")
	private Long candidateId;
	
	@Column(name="RELATIONSHIP")
	private String relationship;
	
	@Column(name="FULL_NAME")
	private String fullName;
	
	@Column(name="AGENT_CODE")
	private Long agentCode;
	
	@Column(name="ID_NO")
	private String idNo;
	
	@Column(name="COMPANY_NAME")
	private String companyName;
	
	@Column(name="POSITION")
	private String position;
	
	@Transient
	private Boolean operation;
}
