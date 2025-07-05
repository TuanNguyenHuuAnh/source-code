package vn.com.unit.cms.core.module.report.dto;

import lombok.Getter;
import lombok.Setter;
import vn.com.unit.cms.core.dto.CommonSearchWithPagingDto;

@Getter
@Setter
public class ReportResultSearchDto extends CommonSearchWithPagingDto{
	private Object gaName;
	private Object branchName;
    private String agentCode;
    private String orgCode;
    private String agentGroup;
    private String year;         //MMyyyy
    private String month;
    private String time;
    private String dataType;     //MTD/QTD/YTD
    private String dataLevel;    //'ALL','SUM','DETAIL'
}
