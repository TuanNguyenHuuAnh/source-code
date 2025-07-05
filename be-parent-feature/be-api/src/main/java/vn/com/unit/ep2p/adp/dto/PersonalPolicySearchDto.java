package vn.com.unit.ep2p.adp.dto;

import lombok.Getter;
import lombok.Setter;
import vn.com.unit.cms.core.dto.CommonSearchWithPagingDto;

@Getter
@Setter
public class PersonalPolicySearchDto extends CommonSearchWithPagingDto {
	private Object insuranceContract;			// So HDBH
	private Object customerName;				// Ben mua BH
	private Object effectiveDate;
	private Object polAgtShrPct;
	private String agentCode;
	private String typeEffect;
	private String policyNo;
	private String agentGroup;
	private String agentName;
	private String agentType;

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
	
	private String fromDate;
	private String toDate;
}
