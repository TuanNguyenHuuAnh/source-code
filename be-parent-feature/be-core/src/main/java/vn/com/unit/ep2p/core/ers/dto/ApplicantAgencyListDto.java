package vn.com.unit.ep2p.core.ers.dto;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Getter;
import lombok.Setter;
import vn.com.unit.ep2p.core.ers.annotation.IesTableHeader;

@Getter
@Setter
public class ApplicantAgencyListDto {

	// HH:mm:ss
	protected static final String DATE_PATTERN = "dd/MM/yyyy";
	
	protected static final String TIME_ZONE = "Asia/Saigon";

	@IesTableHeader(value = "no", width = 50, format = "center")
	private String no;

//	@IesTableHeader(value = "id", width = 50)
	private Long id;

	@IesTableHeader("apply.for.position")
	private String applyForPosition;

	@IesTableHeader("id.no.card")
	private String idNo;

	@IesTableHeader(value = "full.name", width = 200)
	private String candidateName;

	@JsonFormat(pattern = DATE_PATTERN, timezone = TIME_ZONE)
	@IesTableHeader(value = "date.of.birth", format = "date")
	private Date dob;

//	@IesTableHeader("recruiter.code")
	private String recruiterCode;
	
	@IesTableHeader("agency.list.recruiter.code")
	private String recruiterCodeOrIdCard;

	@IesTableHeader(value = "recruiter.name", width = 150)
	private String recruiterName;

	@IesTableHeader("recruiter.position")
	private String recruiterPosition;


//	@IesTableHeader("ad.code")
//	private String recruiterAdCode;
//
//	@IesTableHeader(value = "ad.name", width = 150)
//	private String recruiterAdName;
//
//	@IesTableHeader("ad.position")
//	private String recruiterAdPosition;


	@IesTableHeader("ad.code")
	private String adCode;

	@IesTableHeader(value = "ad.name", width = 200)
	private String adName;

	@IesTableHeader(value = "ad.position", width = 200)
	private String adPosition;


	@IesTableHeader(value = "class.code", width = 200)
	private String classCode;

	@IesTableHeader(value = "status.process", width = 200)
	private String statusProcess; // ? trạng thái quy trình

	@IesTableHeader(value = "status.form", width = 250)
	private String formStatus; // ? trạng thái hồ sơ

	@IesTableHeader("avicad.status")
	private String avicadStatus;

	// @IesTableHeader("check.avicad.status")
	private int checkAvicadStatus;

	// Status check AVICAD
	/*
	 * Check rule: kiểm tra dữ liệu được import tại chức năng Import Check AVICAD
	 * Result. 1. Danh sách đen – Khi check AVICAD là Black List 2. Tạm hoãn – Khi
	 * check AVICAD là Active/Doubt 3. Đạt chuẩn - Khi check AVICAD là Ter/Null
	 * Note: Đối với các ứng viên có nhiều dòng kết quả check AVICAD sẽ lấy dòng dữ
	 * liệu gần nhất theo rule: Đọc dòng có ngày kết thúc (ngày nghỉ việc) gần nhất
	 * với ngày hiện tại
	 */

	@IesTableHeader(value = "pending.at", width = 300, format = "date")
	private String assigneeAccountName;

	@IesTableHeader(value = "created.by", width = 150)
	private String fullnameCreatedBy;
	
	private String createdBy;

	@JsonFormat(pattern = DATE_PATTERN, timezone = TIME_ZONE)
	@IesTableHeader(value = "created.date", width = 100, format = "date")
	private Date createdDate;

//	@IesTableHeader("updated.by")
	private String updatedBy;

//	@IesTableHeader(value = "updated.date", width = 150)
	private Date updatedDate;

//	@IesTableHeader("deleted.by")
	private String deletedBy;

//	@IesTableHeader(value = "deleted.date", width = 150)
	private Date deletedDate;
	
//	private String assigneeAccountId;
//
//	private String ownerSaveDraft;
	
	private int hasEdit;
	
	private int hasDelete;

	public void init() {
		no = null;
		createdBy = null;
		createdDate = null;
		updatedBy = null;
		updatedDate = null;
		deletedBy = null;
		deletedDate = null;
	}
	private Long formId;
	
	private String formStatusCode;
	
	private int slaFlag;
}
