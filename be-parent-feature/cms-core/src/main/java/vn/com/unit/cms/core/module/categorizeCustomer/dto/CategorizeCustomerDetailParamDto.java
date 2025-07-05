package vn.com.unit.cms.core.module.categorizeCustomer.dto;

import java.util.List;

import jp.sf.amateras.mirage.annotation.In;
import jp.sf.amateras.mirage.annotation.Out;
import jp.sf.amateras.mirage.annotation.ResultSet;


public class CategorizeCustomerDetailParamDto {
	@In
	public String agentCode;
	@In
	public String proposalNo;
	@ResultSet
	public List<CategorizeCustomerDto> lstData;

}
