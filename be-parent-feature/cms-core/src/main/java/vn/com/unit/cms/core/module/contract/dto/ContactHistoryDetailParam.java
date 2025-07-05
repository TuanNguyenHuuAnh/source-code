package vn.com.unit.cms.core.module.contract.dto;

import java.util.List;

import jp.sf.amateras.mirage.annotation.In;
import jp.sf.amateras.mirage.annotation.ResultSet;

public class ContactHistoryDetailParam {
	@In
	public String claimNo;
	@ResultSet
	public List<ContactHistoryDetailDto> data;
}
