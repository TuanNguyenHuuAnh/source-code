package vn.com.unit.cms.core.module.contact.dto;

import java.util.Date;
//import java.util.List;

import org.springframework.web.multipart.MultipartFile;

//import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CmsContactDto {

	protected static final String DATE_PATTERN = "dd/MM/yyyy";
	protected static final String TIME_ZONE = "Asia/Saigon";

	private Long id;

	private String agentCode;

	private String name;

	private String email;

	private String address;

	private String phone;

	private String title;

	private String content;

	private String attachFile;

	private Integer sendMail;

	private Long createdId;

//	@JsonFormat(pattern = DATE_PATTERN, timezone = TIME_ZONE)
	private Date createdDate;

	private Long updatedId;

//	@JsonFormat(pattern = DATE_PATTERN, timezone = TIME_ZONE)
	private Date updatedDate;

	private Long deletedId;

//	@JsonFormat(pattern = DATE_PATTERN, timezone = TIME_ZONE)
	private Date deletedDate;
	private String titleName;
	private String uuid;
	private String patch;
//	private MultipartFile[] file;

	private MultipartFile[] file;
	private String valueToken;
	
	public void init() {
		id = null;
		createdId = null;
		createdDate = null;
		updatedId = null;
		updatedDate = null;
		deletedId = null;
		deletedDate = null;
	}

}
