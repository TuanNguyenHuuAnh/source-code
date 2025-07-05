package vn.com.unit.cms.core.module.contact.dto;

import java.util.Date;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ContactEmailSearchDto {

	private List<String> searchKeyIds;
	private String searchValue;
	private String fullName;
	private String email;
	private String emailSubject;
	private String emailContent;
	private Integer motive;
	private String processingStatus;
	private Date fromDate;
	private Date toDate;
	private String service;
	private Long customerId;
	private String language;

	private Integer pageSize;

}
