package vn.com.unit.cms.core.module.information.dto;

import java.util.List;

import jp.sf.amateras.mirage.annotation.In;
import jp.sf.amateras.mirage.annotation.ResultSet;
import vn.com.unit.common.dto.Select2Dto;

public class OfficeGadParam {
	@In
	public String agentCode;
	@ResultSet
	public List<Select2Dto> data;
}
