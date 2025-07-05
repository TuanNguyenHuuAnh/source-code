package vn.com.unit.cms.core.module.contact.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReportActiveSearchDto {
	private Integer page;
	private Integer pageSize;
	private String agentCode;
	private String agentTitle;//chuc danh
	private String yyyyMM; // 202110 date
}
