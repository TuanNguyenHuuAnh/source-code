package vn.com.unit.ep2p.core.ers.dto;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CopManagementDto extends ErsAbstract {
	
	private static final String DATE_PATTERN = "dd/MM/yyyy";
	
	//@JsonFormat(pattern=DATE_PATTERN)
	@JsonFormat(pattern = DATE_PATTERN, timezone = TIME_ZONE)
	private Date studyDate;

	private String candidateName;

	private String idNo;

	private String mobile;

	private String recruiterCodeId;

	private String recruiterName;

	private String managerCodeId;

	private String managerName;
	
	private String adName;

	private String rdName;

	private String regionName; 

}
