package vn.com.unit.ep2p.core.ers.entity;

import org.springframework.data.annotation.Id;

import jp.sf.amateras.mirage.annotation.Column;
import jp.sf.amateras.mirage.annotation.PrimaryKey;
import jp.sf.amateras.mirage.annotation.PrimaryKey.GenerationType;
import jp.sf.amateras.mirage.annotation.Table;
import lombok.Getter;
import lombok.Setter;

@Table(name="ERS_CANDIDATE_PLAN")
@Getter
@Setter
public class ErsCandidatePlan extends AbstractTracking {
	@PrimaryKey(generationType = GenerationType.SEQUENCE, generator = "SEQ_ERS_CANDIDATE_PLAN")
	@Id
	@Column(name="ID")
	private Long id;
	
	@Column(name="CANDIDATE_ID")
	private Long candidateId;
	
	@Column(name="PLAN_GROUP")
	private String planGroup;
	
	@Column(name="PLAN_ITEM")
	private String planItem;
	
	@Column(name="POSITION_ITEM")
	private String positionItem;
	
	@Column(name="MONTH_1")
	private Integer month1;
	
	@Column(name="MONTH_2")
	private Integer month2;
	
	@Column(name="MONTH_3")
	private Integer month3;
	
	@Column(name="MONTH_4")
	private Integer month4;
	
	@Column(name="MONTH_5")
	private Integer month5;
	
	@Column(name="MONTH_6")
	private Integer month6;
	
	@Column(name="MONTH_7")
	private Integer month7;
	
	@Column(name="MONTH_8")
	private Integer month8;
	
	@Column(name="MONTH_9")
	private Integer month9;
	
	@Column(name="MONTH_10")
	private Integer month10;
	
	@Column(name="MONTH_11")
	private Integer month11;
	
	@Column(name="MONTH_12")
	private Integer month12;
	
	@Column(name="TOTAL_ITEM")
	private Integer totalItem;
	
}
