package vn.com.unit.cms.core.module.information.dto;

import java.util.List;
import vn.com.unit.cms.core.module.emulate.dto.EmulateResultSearchResultDto;
import jp.sf.amateras.mirage.annotation.In;
import jp.sf.amateras.mirage.annotation.Out;
import jp.sf.amateras.mirage.annotation.ResultSet;

public class ResultLetterParam {
	@In
	public String agentCode;
	@In
	public String contestType;
	@In
	public String officeCode;
	@In
	public String sort;
	@In
	public String search;
	@ResultSet
	public List<EmulateResultSearchResultDto> data;
	@Out
	public Integer totalData;
}
