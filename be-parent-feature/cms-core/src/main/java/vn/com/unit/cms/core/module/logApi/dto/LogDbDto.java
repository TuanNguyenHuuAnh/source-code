package vn.com.unit.cms.core.module.logApi.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LogDbDto {	
	private Long id;
	private Long apID;
	private String storeName;
	private String param;
	private int tatms;
}
