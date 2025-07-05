package vn.com.unit.ep2p.service.impl;

import java.util.List;

import jp.sf.amateras.mirage.annotation.In;
import jp.sf.amateras.mirage.annotation.ResultSet;
import vn.com.unit.cms.core.module.agent.dto.DataRange;

public class DataRangeParam {
	@In
	public String userName;
	@ResultSet
	public List<DataRange> data;
}
