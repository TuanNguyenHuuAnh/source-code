package vn.com.unit.cms.core.module.trainingCourse.entity;

import jp.sf.amateras.mirage.annotation.Column;
import jp.sf.amateras.mirage.annotation.PrimaryKey;
import jp.sf.amateras.mirage.annotation.Table;
import jp.sf.amateras.mirage.annotation.PrimaryKey.GenerationType;
import lombok.Getter;
import lombok.Setter;
import vn.com.unit.cms.core.constant.CmsTableConstant;

import javax.persistence.*;
import java.util.Date;

@Table(name = CmsTableConstant.TABLE_TRAINING_COURSES_DETAIL)
@Getter
@Setter
public class TrainingCoursesDetailEntity {

	@Id
	@Column(name = "id")
	@PrimaryKey(generationType = GenerationType.SEQUENCE, generator = CmsTableConstant.SEQ_TRAINING_COURSES_DETAIL)
	private Long id;	

	@Column(name = "COURSE_ID")
	private Long courseId;
	
	@Column(name = "AGENT_CODE")
	private String agentCode;
	
	@Column(name = "AGENT_TYPE")
	private String agentType;
	
	@Column(name = "EFFECTIVED_DATE")
	private Date effectivedDate;
	
	@Column(name = "ID_NUMBER")
	private String idNumber;
	
	@Column(name = "NAME")
	private String name;
	
	@Column(name = "UM_CODE")
	private String umCode;
	
	@Column(name = "UM_NAME")
	private String umName;
	
	@Column(name = "BM_CODE")
	private String bmCode;
	
	@Column(name = "BM_NAME")
	private String bmName;
	
	@Column(name = "OFFICE_CODE")
	private String officeCode;
	
	@Column(name = "OFFICE_NAME")
	private String officeName;
	
	@Column(name = "REGISTER_TIME")
	private Date registerTime;
	
	@Column(name = "ATTENDANCE_TIME")
	private Date attendanceTime;
}
