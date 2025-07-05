package vn.com.unit.cms.core.module.income.dto;

import java.util.List;

import jp.sf.amateras.mirage.annotation.In;
import jp.sf.amateras.mirage.annotation.ResultSet;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
@AllArgsConstructor
@NoArgsConstructor
public class IncomePersonalWeeklyParam {
	@In
	public String agentCode;
	@In
	public String paymentPeriod; //MMYYYY
	@In
	public String sort;
	@ResultSet
	public List<IncomePersonalWeeklyDto> data;
}
