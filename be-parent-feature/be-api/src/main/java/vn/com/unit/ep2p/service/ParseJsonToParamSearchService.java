package vn.com.unit.ep2p.service;

import vn.com.unit.cms.core.dto.CommonSearchWithPagingDto;

public interface ParseJsonToParamSearchService {

	public CommonSearchWithPagingDto getSearchCondition(String jsonParam, String functionCode);

	public CommonSearchWithPagingDto getSearchConditionBd(String jsonParam, String functionCode);
	
	public CommonSearchWithPagingDto getParamSearch(String jsonParam, String functionCode);
}
