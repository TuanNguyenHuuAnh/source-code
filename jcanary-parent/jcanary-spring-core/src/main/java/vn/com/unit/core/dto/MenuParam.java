package vn.com.unit.core.dto;

import java.util.List;

import jp.sf.amateras.mirage.annotation.In;
import jp.sf.amateras.mirage.annotation.ResultSet;

public class MenuParam{

	@In
	public Long accountId;
	@ResultSet
	public List<MenuDto> data;
}
