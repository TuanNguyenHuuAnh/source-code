package vn.com.unit.ep2p.adp.dto;

import java.util.List;

import jp.sf.amateras.mirage.annotation.In;
import jp.sf.amateras.mirage.annotation.ResultSet;

public class ProductDetailParam {

	@In
	public int polId;
	@ResultSet
	public List<ProductDetailDto> data;
}
