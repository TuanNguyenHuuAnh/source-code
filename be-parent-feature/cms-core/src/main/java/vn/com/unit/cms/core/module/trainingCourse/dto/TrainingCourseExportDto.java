package vn.com.unit.cms.core.module.trainingCourse.dto;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TrainingCourseExportDto {
	private Integer no;				// STT
	private String courseCode;		// Mã khóa HL
	private String courseName;		// Tên khóa huấn luyện
	private String contents;		// Nội dung huấn luyện
	private String createdName;		// Người huấn luyện
	private Date startDate;			// Thời gian bắt đầu
	private Date endDate;			// Thời gian kết thúc
	private String statusText;		// Trạng thái
	private String location;		// Địa điểm
	private String notes;			// Ghi chú
	private Long quantity;			// Số lượng học viên ghi danh
	private Long quantityAttendance;// Số lượng học viên ghi danh
	private Date approvedDate;		// Ngày duyệt
	private String approvedName;	// Người duyệt
	private Date rejectedDate;		// Ngày từ chối
	private String rejectedName;	// Người từ chối
	private String rejectedReason;	// Lý do từ chối
}