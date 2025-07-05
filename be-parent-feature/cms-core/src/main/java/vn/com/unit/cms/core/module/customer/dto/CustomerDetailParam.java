package vn.com.unit.cms.core.module.customer.dto;

import java.util.List;

import jp.sf.amateras.mirage.annotation.In;
import jp.sf.amateras.mirage.annotation.ResultSet;

public class CustomerDetailParam {

	@In
	public String customerNo;
	
	@ResultSet
	public List<CustomerInformationDetailDto> data;

}
