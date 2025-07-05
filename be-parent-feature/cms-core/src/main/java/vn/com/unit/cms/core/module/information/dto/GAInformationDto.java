package vn.com.unit.cms.core.module.information.dto;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GAInformationDto {
	private String gadCode;
	private String gadName;
	
	private String gaCode;
	private String gaName;
	
	private Date effectiveDate;
	private Date effectiveDateSubGa;
	private String gaAddress;
	private String phone;
	private String segment;
	
	private String gaCodeMain;
}
