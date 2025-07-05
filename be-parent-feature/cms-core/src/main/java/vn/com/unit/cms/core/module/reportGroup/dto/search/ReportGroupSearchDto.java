package vn.com.unit.cms.core.module.reportGroup.dto.search;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReportGroupSearchDto {
    private String agentCode;
    private String orgCode;
    private String agentGroup;
    private String yyyyMM;
    private String dataType;
	private String month;
	private String year;
	private Integer qtd;
	private Integer page;
	private Integer pageSize;

}
