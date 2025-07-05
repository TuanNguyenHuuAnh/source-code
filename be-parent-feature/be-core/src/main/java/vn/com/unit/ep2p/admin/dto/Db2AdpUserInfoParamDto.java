package vn.com.unit.ep2p.admin.dto;

import jp.sf.amateras.mirage.annotation.In;
import jp.sf.amateras.mirage.annotation.ResultSet;

import java.util.List;

public class Db2AdpUserInfoParamDto {
	
	@In
    public String agentCode;
	    
    @ResultSet
    public List<Db2AdpUserInfoDto> lstUser;
}
