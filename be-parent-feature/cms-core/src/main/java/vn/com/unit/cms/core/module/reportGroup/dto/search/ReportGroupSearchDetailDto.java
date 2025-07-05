package vn.com.unit.cms.core.module.reportGroup.dto.search;

import lombok.Getter;
import lombok.Setter;
import vn.com.unit.cms.core.dto.CommonSearchWithPagingDto;

@Getter
@Setter
public class ReportGroupSearchDetailDto extends CommonSearchWithPagingDto {

    private String agentCode;
    private String orgCode;
    private String agentGroup;
    private String yyyyMM;
    private String dataType;
    private String dataLevel;
	private String month;
	private String year;
	private Object manager;
	private boolean detail;	
	private Integer qtd;
}
