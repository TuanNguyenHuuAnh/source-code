package vn.com.unit.ep2p.core.ers.entity;

import org.springframework.data.annotation.Id;

import jp.sf.amateras.mirage.annotation.Column;
import jp.sf.amateras.mirage.annotation.PrimaryKey;
import jp.sf.amateras.mirage.annotation.PrimaryKey.GenerationType;
import jp.sf.amateras.mirage.annotation.Table;
import jp.sf.amateras.mirage.annotation.Transient;
import lombok.Getter;
import lombok.Setter;

@Table(name="ERS_CANDIDATE_Q_A")
@Getter
@Setter
public class ErsCandidateQA extends AbstractTracking {
	
	@Id
	@PrimaryKey(generationType = GenerationType.SEQUENCE, generator = "SEQ_ERS_CANDIDATE_Q_A")
	@Column(name="ID")
	 private Long id;	
	
	@Column(name="CANDIDATE_ID")
	private Long candidateId;
	
	@Column(name="QUESTION_ID")
	private Long questionId;
	
	@Column(name="CANDIDATE_ANSWER")
	private Integer candidateAnswer;
	
	@Column(name="QUESTION_ORDER")
	private Integer questionOrder;
	
	@Transient
	private String contentQuestion;
	
	@Transient
	private Integer stt;
	
}
