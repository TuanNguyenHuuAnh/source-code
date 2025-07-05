package vn.com.unit.ep2p.core.ers.dto;

import java.util.Date;
//import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import vn.com.unit.ep2p.core.ers.annotation.IesTableHeader;

// extends ErsTableDto 

@Getter
@Setter
public class QuestionaireManagementDto {

	// HH:mm:ss
	protected static final String DATE_PATTERN = "dd/MM/yyyy";
	protected static final String TIME_ZONE = "Asia/Saigon";

	@IesTableHeader(value = "no", width = 50, format = "center")
	private Long no;

//	@IesTableHeader(value = "id", width = 50)
	private Long id;

	// content
	@IesTableHeader(value = "questionaire.management.content", width = 300)
	private String content;

	@IesTableHeader(value = "order", width = 75, format = "number")
	private int orderOnForm;

	@Setter(value = AccessLevel.NONE)
	@IesTableHeader("apply.for.position")
	private String applyForPosition;

	@IesTableHeader("type")
	private String typeQuestion;

	@IesTableHeader("status")
	private String statusItem;

//	@IesTableHeader("created.by")
	private String createdBy;

//	@IesTableHeader(value = "created.date", width = 150)
	private Date createdDate;

	@IesTableHeader("updated.by")
	private String updatedBy;

	@JsonFormat(pattern = DATE_PATTERN, timezone = TIME_ZONE)
	@IesTableHeader(value = "updated.date", width = 150, format = "date")
	private Date updatedDate;

//	@IesTableHeader("deleted.by")
	private String deletedBy;

//	@IesTableHeader(value = "deleted.date", width = 150)
	private Date deletedDate;

	private int deletedFlag;

	public void init() {
		no = null;
		createdBy = null;
		createdDate = null;
		updatedBy = null;
		updatedDate = null;
		deletedBy = null;
		deletedDate = null;
	}

	public void setApplyForPosition(String ...applyForPosition) {
		this.applyForPosition = String.join(",", applyForPosition);
	}

//	public void setApplyForPosition(String applyForPosition) {
//		this.applyForPosition = applyForPosition;
//	}
}
