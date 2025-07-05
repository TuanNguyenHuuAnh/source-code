package vn.com.unit.ep2p.adp.dto;

import lombok.Getter;
import lombok.Setter;
import vn.com.unit.cms.core.dto.CommonSearchWithPagingDto;

@Getter
@Setter
public class PolicySearchDto extends CommonSearchWithPagingDto {
	private Object policyNo;
	private Object proposalNo;
	private Object poName;
}
