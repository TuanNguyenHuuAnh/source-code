package vn.com.unit.ep2p.adp.dto;

import lombok.Getter;
import lombok.Setter;
import vn.com.unit.cms.core.dto.CommonSearchWithPagingDto;

@Getter
@Setter
public class ReportK2K2PlusSearchDto extends CommonSearchWithPagingDto {
	private String agentCode;
	private String partner;
	private String regionCode;
	private String zoneCode;
	private String uoCode;
	private String areaCodeDLVN;
	private String regionCodeDLVN;
	private String zoneCodeDLVN;
	private String ilCode;
	private String isCode;
	private String smCode;
    private String monthYear;
    public Integer option;
    public String dataType;
}
