package vn.com.unit.cms.core.module.ga.dto.param;

import jp.sf.amateras.mirage.annotation.In;
import jp.sf.amateras.mirage.annotation.ResultSet;

import vn.com.unit.cms.core.module.ga.dto.IncomeConfirmPaymentDto;

import java.util.List;

public class IncomeConfirmPaymentParamGa {
	@In
	public String gadCode;
	
	@In
	public String period;
	
	@In
	public String orgCode;
	
	@ResultSet
	public List<IncomeConfirmPaymentDto> lstData;
}