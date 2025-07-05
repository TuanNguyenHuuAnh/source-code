package vn.com.unit.ep2p.adp.dto;

import lombok.Getter;
import lombok.Setter;
import vn.com.unit.cms.core.dto.CommonSearchWithPagingDto;

@Getter
@Setter
public class ProposalDetailSearchDto extends CommonSearchWithPagingDto {
	private String type;
	private String proposalNo;
	private String policyNo;
}
