package vn.com.unit.ep2p.admin.dto;

import jp.sf.amateras.mirage.annotation.In;
import jp.sf.amateras.mirage.annotation.ResultSet;

import java.util.List;

public class Db2OrgLocationInfoParamDto {
	@In
    public String agentCode;
	
	@In
    public String agentType;

	@In
    public String partnerCode;
    
    @ResultSet
    public List<OrgLocationDto> resultLst;
}
