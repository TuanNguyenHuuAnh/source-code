package vn.com.unit.cms.core.module.agent.dto;

import java.util.List;

import jp.sf.amateras.mirage.annotation.In;
import jp.sf.amateras.mirage.annotation.ResultSet;

public class CmsCheckAgentParamDto {
	@In
	public String CODE;
	@ResultSet 
	public List<CmsAgentTerminationInfor> data;
}
