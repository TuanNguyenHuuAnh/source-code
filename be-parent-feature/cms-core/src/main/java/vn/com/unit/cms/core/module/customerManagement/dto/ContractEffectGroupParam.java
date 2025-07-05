package vn.com.unit.cms.core.module.customerManagement.dto;

import java.util.List;

import jp.sf.amateras.mirage.annotation.In;
import jp.sf.amateras.mirage.annotation.Out;
import jp.sf.amateras.mirage.annotation.ResultSet;

public class ContractEffectGroupParam {
	@In
	public String agentCode;
	@In
    public String agentGroup;
	@In
    public String orgCode;
	@In
    public String policyType;
	@In
    public String dateKey;
	@In
    public Integer page;
	@In
    public Integer pageSize;
	@In
    public String sort;
	@In
	public String search;
	@ResultSet
	public List<ContractEffectGroupDto> data;
	@Out
	public Integer totalRows;
}
