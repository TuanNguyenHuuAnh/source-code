package vn.com.unit.cms.core.module.categorizeCustomer.dto;
import java.util.List;

import jp.sf.amateras.mirage.annotation.In;
import jp.sf.amateras.mirage.annotation.Out;
import jp.sf.amateras.mirage.annotation.ResultSet;

public class CategorizeCustomerParamDto {
	@In
	public String agentCode;
	@In
	public String proposalNo;
	@In
	public String poName;
	@In
	public String partnerCode;
	@In
	public String status;
	/*@In
	public Integer page;
	@In
	public Integer pageSize;*/
	@ResultSet
	public List<CategorizeCustomerDto> lstData;
	/*@Out
    public Integer totalRows;*/
}
