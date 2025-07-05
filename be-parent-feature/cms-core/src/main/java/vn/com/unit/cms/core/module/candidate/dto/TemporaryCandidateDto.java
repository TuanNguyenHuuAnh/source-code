package vn.com.unit.cms.core.module.candidate.dto;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TemporaryCandidateDto {
	private Integer no;
	private String officeNameReg;
	private String fullname;
	private String email;
	private String gender;
	private String phone;
	private String avatar;
	private Date registerDate;
	private boolean contact;
	private String contactString;
	private Long id;

}
