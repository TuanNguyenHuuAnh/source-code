package vn.com.unit.cms.core.module.income.dto;

import java.util.List;

import jp.sf.amateras.mirage.annotation.In;
import jp.sf.amateras.mirage.annotation.ResultSet;

public class YearlyPaymentAGParam {
	@In
	public String agentCode;
	@In
	public String year; 
	@ResultSet
	public List<MonthPaymentDto> data;
}
