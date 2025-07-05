package vn.com.unit.cms.core.module.candidate.dto;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;
import vn.com.unit.cms.core.dto.CommonSearchWithPagingDto;

@Getter
@Setter
public class TemporaryCandidateSearchDto extends CommonSearchWithPagingDto{
	private Object officeNameReg;
	private Object fullname;
	private Object email;
	private Object gender;
	private Object phone;
	private Object avatar;
	private Object registerDate;
	private Object contact;
	
	private String agentCode;
	private String orgId;
}


