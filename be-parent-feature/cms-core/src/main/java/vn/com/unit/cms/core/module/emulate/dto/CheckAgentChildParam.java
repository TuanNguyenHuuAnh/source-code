package vn.com.unit.cms.core.module.emulate.dto;

import java.util.List;

import jp.sf.amateras.mirage.annotation.In;
import jp.sf.amateras.mirage.annotation.ResultSet;
import vn.com.unit.ep2p.admin.dto.Db2SummaryDto;

public class CheckAgentChildParam {
    @In
    public String territory;
    @In
    public String region;
    @In
    public String office;
    @In
    public String area;
    @In
    public String position;
    @In
    public String agentParent;
    @In
    public String agentChild;
	@ResultSet
	public List<Db2SummaryDto> data;
}
