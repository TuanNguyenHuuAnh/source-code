package vn.com.unit.cms.core.module.agent.dto;

import lombok.Getter;
import lombok.Setter;
import vn.com.unit.cms.core.dto.CommonSearchWithPagingDto;

@Getter
@Setter
public class IntroduceSearchDto extends CommonSearchWithPagingDto {
	private String ngayHieuLuc;
	private String agentCode;
	private Object totalPolicy;
	private Object mainPolicy;
	private Object assignment;
	private Object effectiveDate;

	private Object soHdbh;
	private Object insuranceByName;
	private Object formOfDistribution;
}
