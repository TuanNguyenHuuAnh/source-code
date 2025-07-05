package vn.com.unit.ep2p.core.ers.dto;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ApplicantAgencyListExportListClassDto extends ApplicantAgencyListDto {
//	private String taxCode;

	private String genderName;
	
	@JsonFormat(pattern = DATE_PATTERN, timezone = TIME_ZONE)
	private Date idDateOfIssue;
	
	private String idPlaceOfIssueName;
	
	private String mobile;
	
	private String email;
	
	private String recruiterIdNo;
	
	private Long managerIndirectCode;
	
	private String managerIndirectName;
	
	private String managerIdNo;
	
	private String managerName;
	
	private String managerAdName;

	private String directRdName;

	private String areaManagerName;
	
	private String classCode;
	
	private String classProvinceName;
	
	private String classType;
	
	private String onlineOffline;
}
