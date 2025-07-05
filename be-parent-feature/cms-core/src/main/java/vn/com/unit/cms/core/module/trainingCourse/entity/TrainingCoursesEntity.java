package vn.com.unit.cms.core.module.trainingCourse.entity;

import org.springframework.data.annotation.Id;

import jp.sf.amateras.mirage.annotation.Column;
import jp.sf.amateras.mirage.annotation.PrimaryKey;
import jp.sf.amateras.mirage.annotation.Table;
import jp.sf.amateras.mirage.annotation.PrimaryKey.GenerationType;
import lombok.Getter;
import lombok.Setter;
import vn.com.unit.cms.core.constant.CmsTableConstant;

import java.util.Date;

@Table(name = CmsTableConstant.TABLE_TRAINING_COURSES)
@Getter
@Setter
public class TrainingCoursesEntity {

	@Id
	@Column(name = "id")
	@PrimaryKey(generationType = GenerationType.SEQUENCE, generator = CmsTableConstant.SEQ_TRAINING_COURSES)
	private Long id;	

	@Column(name = "COURSE_CODE")
	private String courseCode;

	@Column(name = "COURSE_NAME_CODE")
	private String courseNameCode;

	@Column(name = "OFFICE")
	private String office;

	@Column(name = "LOCATION")
	private String location;

	@Column(name = "START_DATE")
	private Date startDate;

	@Column(name = "END_DATE")
	private Date endDate;

	@Column(name = "CONTENTS")
	private String contents;
	
	@Column(name = "NOTES")
	private String notes;

	@Column(name = "STATUS")
	private int status;

	@Column(name = "QR_CODE")
	private String qrCode;
	
	@Column(name = "CREATED_DATE")
	private Date createdDate;
	
	@Column(name = "CREATED_BY")
	private String createdBy;
	
	@Column(name = "CREATED_NAME")
	private String createdName;
	
	@Column(name = "UPDATED_DATE")
	private Date updatedDate;
	
	@Column(name = "UPDATED_BY")
	private String updatedBy;
	
	@Column(name = "COMPLETED_DATE")
	private Date completedDate;
	
	@Column(name = "APPROVED_DATE")
	private Date approvedDate;
	
	@Column(name = "APPROVED_BY")
	private String approvedBy;
	
	@Column(name = "APPROVED_NAME")
	private String approvedName;
	
	@Column(name = "REJECTED_DATE")
	private Date rejectedDate;
	
	@Column(name = "REJECTED_BY")
	private String rejectedBy;
	
	@Column(name = "REJECTED_NAME")
	private String rejectedName;
	
	@Column(name = "REJECTED_REASON")
	private String rejectedReason;
	
	@Column(name = "DELETE_FLG")
	private boolean deleteFlg;
}
