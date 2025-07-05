package vn.com.unit.cms.core.module.sam.entity;

import java.util.Date;

import org.springframework.data.annotation.Id;

import jp.sf.amateras.mirage.annotation.Column;
import jp.sf.amateras.mirage.annotation.PrimaryKey;
import jp.sf.amateras.mirage.annotation.Table;
import jp.sf.amateras.mirage.annotation.PrimaryKey.GenerationType;
import lombok.Getter;
import lombok.Setter;
import vn.com.unit.cms.core.constant.CmsTableConstant;

@Table(name = CmsTableConstant.TABLE_SAM_PLAN)
@Getter
@Setter
public class SamPlan {

	@Id
    @Column(name = "ID")
    @PrimaryKey(generationType = GenerationType.SEQUENCE, generator = CmsTableConstant.SEQ_SAM_PLAN)
    private Long id;
	
	@Column(name = "PLAN_DATE")
	private Date planDate;
	
	@Column(name = "ACTUAL_DATE")
	private Date actualDate;
	
	@Column(name = "PERSON_NUMBER")
	private Integer personNumber;
	
	@Column(name = "COST_AMT")
	private Double costAmt;
	
	@Column(name = "SALES_AMT")
	private Double salesAmt;
	
	@Column(name = "DESCRIPTION")
	private String description;
	
	@Column(name = "ACTIVITY_ID")
	private Long activityId;
	
	@Column(name = "CREATED_BY")
	private String createdBy;
	
	@Column(name = "CREATED_DATE")
	private Date createdDate;

	@Column(name = "UPDATED_BY")
	private String updatedBy;
	
	@Column(name = "UPDATED_DATE")
	private Date updatedDate;

	@Column(name = "ACTUAL_RESULT")
	private String actualResult;
}
