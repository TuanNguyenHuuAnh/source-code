package vn.com.unit.cms.core.module.income.dto;

import java.util.List;

import jp.sf.amateras.mirage.annotation.In;
import jp.sf.amateras.mirage.annotation.ResultSet;

public class IncomePersonalMonthlyParam {
	@In
	public String agentCode;
	@In
	public String paymentPeriod; //MM_YYYY
	@ResultSet
	public List<IncomePersonalMonthlyDto> data;
}
