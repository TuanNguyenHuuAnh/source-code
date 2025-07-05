package vn.com.unit.ep2p.adp.dto;

import lombok.Getter;
import lombok.Setter;
import vn.com.unit.cms.core.dto.CommonSearchWithPagingDto;

@Getter
@Setter
public class AggregateReportSearchDto extends CommonSearchWithPagingDto {
	private String agentCode;
	private String areaCode;
	private String regionCode;
	private String zoneCode;
    private String monthYear;
    private String type;
}
