package vn.com.unit.cms.core.module.trainingCourse.dto;

import java.util.Date;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TrainingCourseDto {
	private Long id;
	private String courseCode;
	private String courseName;
	private String courseNameCode;
	private String office;
	private String officeName;
	private String location;
	private Date startDate;
	private Date endDate;
	private String contents;
	private String notes;
	private int status;
	private int newStatus;
	private String statusText;
	private Long quantity;
	private String qrCode;
	private String qrCodeData;
	private String createdBy;
	private String createdName;
	private String approvedBy;
	private String approvedName;
	private String rejectedBy;
	private String rejectedReason;
	private boolean changeDetails;
	private boolean ableDelete;
	private boolean ableUpdate;
	private boolean ableEnd;
	private boolean ableApprove;
	private boolean ableAttendance;
	private String action;

	private List<TrainingTraineeDto> trainees; // Danh sách học viên
}