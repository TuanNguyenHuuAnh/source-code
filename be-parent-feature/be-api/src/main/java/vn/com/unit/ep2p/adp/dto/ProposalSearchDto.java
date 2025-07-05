package vn.com.unit.ep2p.adp.dto;

import lombok.Getter;
import lombok.Setter;
import vn.com.unit.cms.core.dto.CommonSearchWithPagingDto;

@Getter
@Setter
public class ProposalSearchDto extends CommonSearchWithPagingDto {
	private Object polNo;
	private Object proposalNo;
	private Object poName;
	private Object liName;
	private Object receivedDate;
	private Object agentName;
	private String agentCode;
	private String uoCode;
	private String agentType;
}
