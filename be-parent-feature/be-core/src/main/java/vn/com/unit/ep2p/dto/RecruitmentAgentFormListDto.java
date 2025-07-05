package vn.com.unit.ep2p.dto;

import java.util.Date;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class RecruitmentAgentFormListDto {

	private int no;
	
	private Boolean checked;
	
	private String status;
	
	private String positionCandidacy;
	
	private String fullName;
	
	private String provinceName;
	
	private String adCode;
	
	private String adName;
	
	private Date assignAdDate;
	
	private String recruiterCode;
	
	private String recruiterName;
	
	private Date assignTdDate;
	
	private Date dob;
	
	private String gender;
	
	private String idNumber;
	
	private String phoneNumber;
	
	private String email;
	
	private String createdDate;
	
	
}
