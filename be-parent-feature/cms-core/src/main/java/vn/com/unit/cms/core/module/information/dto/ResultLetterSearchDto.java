package vn.com.unit.cms.core.module.information.dto;

import lombok.Getter;
import lombok.Setter;
import vn.com.unit.cms.core.dto.CommonSearchWithPagingDto;

@Getter
@Setter
public class ResultLetterSearchDto extends CommonSearchWithPagingDto{
	private String agentCode;
	private String officeCode;
	private String contestType;
	
	private String memoCode;
	private String title;
	private String description;
	private String result;
	private String advance;
	private String bonus;
	private String clawback;
}
