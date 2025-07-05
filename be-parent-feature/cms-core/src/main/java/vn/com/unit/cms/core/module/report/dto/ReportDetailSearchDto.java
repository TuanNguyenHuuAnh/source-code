package vn.com.unit.cms.core.module.report.dto;

import lombok.Getter;
import lombok.Setter;
import vn.com.unit.cms.core.dto.CommonSearchWithPagingDto;

@Getter
@Setter
public class ReportDetailSearchDto extends CommonSearchWithPagingDto {
	private String agentCode; 
	private String agentGroup;
	private String orgCode;
	private String yyyyMM;
	private String month;
	private String year;
	private String estimateK2ToGetTheRatioK2;
	private String estimateK2ToGetTheRatioK2Plus;
	
	private boolean isPersonal; 
}
