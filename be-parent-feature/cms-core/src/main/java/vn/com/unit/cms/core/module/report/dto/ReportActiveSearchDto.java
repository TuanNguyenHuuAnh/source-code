package vn.com.unit.cms.core.module.report.dto;

import lombok.Getter;
import lombok.Setter;
import vn.com.unit.cms.core.dto.CommonSearchWithPagingDto;

@Getter
@Setter
public class ReportActiveSearchDto extends CommonSearchWithPagingDto{
	private String agentCode;
	private String yyyyMM;
	private String month;
	private String year;
	private Integer page;
	private Integer pageSize;
}
