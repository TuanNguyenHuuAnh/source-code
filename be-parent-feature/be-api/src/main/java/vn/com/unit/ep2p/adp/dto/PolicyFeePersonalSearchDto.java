package vn.com.unit.ep2p.adp.dto;

import lombok.Getter;
import lombok.Setter;
import vn.com.unit.cms.core.dto.CommonSearchWithPagingDto;

@Getter
@Setter
public class PolicyFeePersonalSearchDto extends CommonSearchWithPagingDto {
	private Object insuranceContract;			// So HDBH
	private Object customerName;				// Ben mua BH
	private Object effectiveDate;
	private Object polAgtShrPct;
	private String agentCode;
	private String type;
	private String policyNo;
	private String agentGroup;
	private String agentName;
	private String agentType;

	private String uoCode;
	private String uoName;
}
