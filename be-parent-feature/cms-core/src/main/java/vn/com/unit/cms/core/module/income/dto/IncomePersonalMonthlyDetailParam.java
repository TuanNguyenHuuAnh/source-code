package vn.com.unit.cms.core.module.income.dto;

import java.util.List;

import jp.sf.amateras.mirage.annotation.In;
import jp.sf.amateras.mirage.annotation.ResultSet;

public class IncomePersonalMonthlyDetailParam {
	@In
	public String agentCode;
	@In
	public String paymentPeriod; //MM_YYYY
	@In
	public String mainDirectoryName;
	@ResultSet
	public List<IncomePersonalMonthlyDto> data;
}
