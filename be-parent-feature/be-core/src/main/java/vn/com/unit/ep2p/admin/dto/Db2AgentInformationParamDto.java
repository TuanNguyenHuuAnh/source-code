package vn.com.unit.ep2p.admin.dto;

import java.util.List;

import jp.sf.amateras.mirage.annotation.In;
import jp.sf.amateras.mirage.annotation.ResultSet;

public class Db2AgentInformationParamDto {
	
	@In
    public String agent;
	
    @ResultSet
    public List<ResetPasswordDto> data;
}
