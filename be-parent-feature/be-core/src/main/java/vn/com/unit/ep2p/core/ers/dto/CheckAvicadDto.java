package vn.com.unit.ep2p.core.ers.dto;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CheckAvicadDto {
	private String idNo;

	private String taxCode;

	private String candidateName;

	private Date dob;
	
	private String gender;

	private String genderName;
	
	private String idCard;
}
