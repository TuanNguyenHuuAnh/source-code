package vn.com.unit.ep2p.adp.dto;

import lombok.Getter;
import lombok.Setter;
import vn.com.unit.cms.core.dto.CommonSearchWithPagingDto;

@Getter
@Setter
public class PolicyRequestSearchDto extends CommonSearchWithPagingDto {
	private String type;
	private String policyNo;
	private String processtypecode;
	private String workitem;
}
