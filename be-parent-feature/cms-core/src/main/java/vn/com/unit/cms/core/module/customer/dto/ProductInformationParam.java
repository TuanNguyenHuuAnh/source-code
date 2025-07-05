package vn.com.unit.cms.core.module.customer.dto;

import java.util.List;

import jp.sf.amateras.mirage.annotation.In;
import jp.sf.amateras.mirage.annotation.Out;
import jp.sf.amateras.mirage.annotation.ResultSet;

public class ProductInformationParam {

	@In
	public int polId;
//	@In
//	public Integer page;
//	@In
//	public Integer pageSize;
	@ResultSet
	public List<ProductInformationDto> data;
//	@Out
//	public Integer totalData;
}
