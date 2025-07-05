package vn.com.unit.ep2p.core.res.dto;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import vn.com.unit.ep2p.core.ers.annotation.IesTableHeader;

@Getter
@Setter
@NoArgsConstructor
public class ErsAgSeftInputListRes {
	
	protected static final String DATE_PATTERN = "dd/MM/yyyy";
	
	protected static final String TIME_ZONE = "Asia/Saigon";
	
	private Long id;
	
	private String channel;
	
	@IesTableHeader(value = "no", width = 50, format = "center")
	private String no;
	
	@IesTableHeader(value="candidate.public.status", width = 150)
	private String statusForm; 
	
	@IesTableHeader("candidate.public.apply-for-position")
	private String applyForPosition; 
	
	@IesTableHeader(value ="candidate.public.fullname", width = 150)
	private String candidateName;
	
	@IesTableHeader(value="candidate.public.province", width = 150)
	private String currentProvinceName;
	
	@IesTableHeader("candidate.public.ad-code")
	private Long adCode;
	
	@IesTableHeader(value="candidate.public.ad-name", width = 150)
	private String adName;
	
	@JsonFormat(pattern = DATE_PATTERN, timezone = TIME_ZONE)
	@IesTableHeader(value= "candidate.public.ad-location-date", width = 100)
	private Date allocationAdDate; 
	
	@IesTableHeader("candidate.public.recruiter-code")
	private Long recruiterCode; 
	
	@IesTableHeader(value="candidate.public.recruiter-name", width = 150)
	private String recruiterName;
	
	@JsonFormat(pattern = DATE_PATTERN, timezone = TIME_ZONE)
	@IesTableHeader(value="candidate.public.recruiter-location-date", width = 100)
	private Date allocationRecruiterDate;
	
	@JsonFormat(pattern = DATE_PATTERN, timezone = TIME_ZONE)
	@IesTableHeader("candidate.public.dob")
	private Date dob;
	
	private String gender;

	@IesTableHeader("candidate.public.gender")
	private String genderName; 

	@IesTableHeader("candidate.public.id-title")
	private String idNo;
	
	@IesTableHeader("candidate.public.mobile")
	private String mobile;
	
	@IesTableHeader(value="candidate.public.email", width = 150)
	private String email;

	@JsonFormat(pattern = DATE_PATTERN, timezone = TIME_ZONE)
	@IesTableHeader("candidate.public.created-date")
	private Date createdDate;

	private String currentAddress;

	private String currentNest;
	
	private String currentProvince;
	
	private String currentDistrict;
	
	private String currentDistrictName;
	
	private String currentWard; 

	private String currentWardName;
	
	private String arAllocationName;
	
	private String positionName;
	
	
}
