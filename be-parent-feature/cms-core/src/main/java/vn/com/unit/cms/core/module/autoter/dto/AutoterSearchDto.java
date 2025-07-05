package vn.com.unit.cms.core.module.autoter.dto;

import lombok.Getter;
import lombok.Setter;
import vn.com.unit.cms.core.dto.CommonSearchWithPagingDto;

@Getter
@Setter
public class AutoterSearchDto extends CommonSearchWithPagingDto{

    private String agentCode;
    private String orgCode;
    private String agentGroup;
    private String yyyyMM;
	private String month;
	private String year;
	
	private Object lv1Agentname;
	private Object lv2Agentname;
	private Object lv3Agentname;
	private Object officeCode;

	private Object policyCountIssue;
	private Object policyCountService;

}
