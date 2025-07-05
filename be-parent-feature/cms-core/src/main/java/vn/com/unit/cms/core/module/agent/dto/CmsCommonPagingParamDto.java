package vn.com.unit.cms.core.module.agent.dto;

import jp.sf.amateras.mirage.annotation.In;
import jp.sf.amateras.mirage.annotation.Out;

public class CmsCommonPagingParamDto {
	@In
	public String jsonParam;
	@Out
	public Integer page;
	@Out
	public Integer size;
	@Out
	public String sort;
	@Out
	public String tableName;
	@Out
	public String search;
	@Out
	public Integer offset;
	@Out
	public String language;
	@In
	public String functionCode;
}
