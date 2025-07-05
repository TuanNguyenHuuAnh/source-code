package vn.com.unit.ep2p.core.ers.entity;

import org.springframework.data.annotation.Id;

import jp.sf.amateras.mirage.annotation.Column;
import jp.sf.amateras.mirage.annotation.PrimaryKey;
import jp.sf.amateras.mirage.annotation.PrimaryKey.GenerationType;
import jp.sf.amateras.mirage.annotation.Table;
import jp.sf.amateras.mirage.annotation.Transient;
import lombok.Getter;
import lombok.Setter;

@Table(name = "ERS_INTERVIEW_RESULT")
@Getter
@Setter
public class ErsInterviewResult extends AbstractTracking {
	@Id
	@PrimaryKey(generationType =  GenerationType.SEQUENCE, generator = "SEQ_ERS_INTERVIEW_RESULT")
	@Column(name="ID")
 	private Long id;	
	
	@Column(name="CANDIDATE_ID")
	private Long candidateId;
	
	@Column(name="INTERVIEW_ID")
 	private Long interviewId;
	
	@Column(name="CANDIDATE_ANSWER")
 	private String candidateAnswer;
	
	@Transient
	private String candidateAnswer2;
	
	@Column(name="INTERVIEW_POINT")
 	private Integer interviewPoint;
	
	@Column(name="INTERVIEW_ORDER")
 	private Integer interviewOrder;
	
	@Column(name="INTERVIEW_TIMES")
 	private Integer interviewTimes;
	
	@Transient
	private String contentQuestion;
	
	@Transient
	private Integer stt;
}
