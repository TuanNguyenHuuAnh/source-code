package vn.com.unit.cms.core.module.contract.dto;

import java.util.List;

import jp.sf.amateras.mirage.annotation.In;
import jp.sf.amateras.mirage.annotation.Out;
import jp.sf.amateras.mirage.annotation.ResultSet;
import vn.com.unit.cms.core.module.agent.dto.CertificateDto;

public class ContractPagingParam {
	@In
	public String agentCode;
	@In
	public String customerNo;
	@In
	public String policyType;
	@In
	public Integer page;
	@In
	public Integer pageSize;
	@In
	public String sort;
	@In
	public String search;
	@ResultSet
	public List<ContractSearchAllResultDto> data;
	@Out
	public Integer total;
}
