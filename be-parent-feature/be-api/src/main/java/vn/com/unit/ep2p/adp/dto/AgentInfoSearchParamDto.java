package vn.com.unit.ep2p.adp.dto;

import java.util.List;

import jp.sf.amateras.mirage.annotation.In;
import jp.sf.amateras.mirage.annotation.ResultSet;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AgentInfoSearchParamDto {
	@In
    public String agentCode;
	@In
	public String search;
    @ResultSet
    public List<AgentInfoSearchResultDto> datas;
}
