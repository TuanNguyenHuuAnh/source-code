package vn.com.unit.cms.core.module.contract.dto;

import lombok.Getter;
import lombok.Setter;
import vn.com.unit.cms.core.dto.CommonSearchWithPagingDto;

@Getter
@Setter
public class ContractSearchDto extends CommonSearchWithPagingDto {
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

}
