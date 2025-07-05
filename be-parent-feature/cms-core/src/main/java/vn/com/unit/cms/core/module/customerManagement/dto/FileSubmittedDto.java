package vn.com.unit.cms.core.module.customerManagement.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class FileSubmittedDto {
	private String no;
	private String fileName;
	private Date submittedDate;
	private String customerConfirm;
}
