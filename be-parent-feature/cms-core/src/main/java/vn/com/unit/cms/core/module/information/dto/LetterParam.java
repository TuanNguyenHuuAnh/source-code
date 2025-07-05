package vn.com.unit.cms.core.module.information.dto;

import java.util.List;

import jp.sf.amateras.mirage.annotation.In;
import jp.sf.amateras.mirage.annotation.ResultSet;
import vn.com.unit.cms.core.module.emulate.dto.EmulateSearchDto;

public class LetterParam {
	@In
	public String agentCode;
	@In
	public String officeCode;
	@In
	public String contestType;
	@In
	public String memoCode;
	@In
	public String title;
	@ResultSet
	public List<EmulateSearchDto> data;
}
