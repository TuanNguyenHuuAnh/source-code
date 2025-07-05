package vn.com.unit.cms.core.module.customerManagement.dto;

import java.util.List;

import jp.sf.amateras.mirage.annotation.In;
import jp.sf.amateras.mirage.annotation.Out;
import jp.sf.amateras.mirage.annotation.ResultSet;

public class ContractExpiresPersonalParamDto {
	@In
	public Integer contractNumber;
	@In
	public String contractOwnerName;
	@In
	public Integer page;
	@In
	public Integer size;
	@Out
	public Integer TotalRows;
	@ResultSet
	public List<ContractExpiresPersonalDto> data;
}
