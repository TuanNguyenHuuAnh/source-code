package vn.com.unit.cms.core.module.customer.dto;

import java.util.List;

import jp.sf.amateras.mirage.annotation.In;
import jp.sf.amateras.mirage.annotation.Out;
import jp.sf.amateras.mirage.annotation.ResultSet;

public class InsuranceFeesInformationParam {


	@In
	public String stringJsonParam;
	@ResultSet
	public List<InsuranceFeesInformationDto> data;
	@Out
	public Integer totalData;
}
