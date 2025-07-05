package vn.com.unit.ep2p.adp.dto;

import lombok.Getter;
import lombok.Setter;
import vn.com.unit.cms.core.dto.CommonSearchWithPagingDto;

@Getter
@Setter
public class AgentInfoSearchDto extends CommonSearchWithPagingDto {
	private Object agentCode;
	private Object agentName;
	private Object agentType;
	private Object agentStatus;
	private Object partnerCode;
	private Object operationalModel;	// mo hinh hoat dong
	private Object unitCode;			// mã đơn vị
	private Object unitName;			// tên đơn vị
	private String type;
}
