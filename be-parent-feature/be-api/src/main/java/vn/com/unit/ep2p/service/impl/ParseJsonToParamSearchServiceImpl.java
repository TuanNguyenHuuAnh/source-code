package vn.com.unit.ep2p.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import vn.com.unit.cms.core.dto.CommonSearchWithPagingDto;
import vn.com.unit.cms.core.module.agent.dto.CmsCommonPagingParamDto;
import vn.com.unit.db.service.impl.SqlManagerServiceImpl;
import vn.com.unit.ep2p.service.ParseJsonToParamSearchService;

@Service
@Transactional(readOnly = true, rollbackFor = Exception.class)
public class ParseJsonToParamSearchServiceImpl implements ParseJsonToParamSearchService {
	
	private static final String SP_PARSE_JSON_TO_PARAM_SEARCH_DB2 = "SP_PARSE_JSON_TO_PARAM_SEARCH_DB2";
	private static final String SP_PARSE_JSON_TO_PARAM_SEARCH_DB2_BD = "SP_PARSE_JSON_TO_PARAM_SEARCH_DB2_BD";
	private static final String SP_PARSE_JSON_TO_PARAM_SEARCH = "SP_PARSE_JSON_TO_PARAM_SEARCH";
	@Autowired
	@Qualifier("sqlManagerServicePr")
	private SqlManagerServiceImpl sqlManagerService;
	/** logger */
	private static final Logger logger = LoggerFactory.getLogger(ParseJsonToParamSearchServiceImpl.class);

	@Override
	public CommonSearchWithPagingDto getSearchCondition(String jsonParam, String functionCode) {
		CommonSearchWithPagingDto dto = new CommonSearchWithPagingDto();
		try {

 			CmsCommonPagingParamDto common = new CmsCommonPagingParamDto();
 			common.jsonParam = jsonParam;
 			common.functionCode = functionCode;
 			sqlManagerService.call(SP_PARSE_JSON_TO_PARAM_SEARCH_DB2, common);
 			dto.setFunctionCode(functionCode);
 			dto.setPage(common.page);
 			dto.setPageSize(common.size);
 			dto.setSort(common.sort);
 			dto.setOffset(common.offset);
 			dto.setSearch(common.search);
		} catch (Exception e) {
			logger.error("getSearchCondition", e);
		}
		return dto;
	}

	@Override
	public CommonSearchWithPagingDto getSearchConditionBd(String jsonParam, String functionCode) {
		CommonSearchWithPagingDto dto = new CommonSearchWithPagingDto();
		try {

 			CmsCommonPagingParamDto common = new CmsCommonPagingParamDto();
 			common.jsonParam = jsonParam;
 			common.functionCode = functionCode;
 			sqlManagerService.call(SP_PARSE_JSON_TO_PARAM_SEARCH_DB2_BD, common);
 			dto.setFunctionCode(functionCode);
 			dto.setPage(common.page);
 			dto.setPageSize(common.size);
 			dto.setSort(common.sort);
 			dto.setOffset(common.offset);
 			dto.setSearch(common.search);
		} catch (Exception e) {
			logger.error("getSearchCondition", e);
		}
		return dto;
	}

	@Override
	public CommonSearchWithPagingDto getParamSearch(String jsonParam, String functionCode) {
		CommonSearchWithPagingDto dto = new CommonSearchWithPagingDto();
		try {

 			CmsCommonPagingParamDto common = new CmsCommonPagingParamDto();
 			common.jsonParam = jsonParam;
 			common.functionCode = functionCode;
 			sqlManagerService.call(SP_PARSE_JSON_TO_PARAM_SEARCH, common);
 			dto.setFunctionCode(functionCode);
 			dto.setPage(common.page);
 			dto.setPageSize(common.size);
 			dto.setSort(common.sort);
 			dto.setOffset(common.offset);
 			dto.setSearch(common.search);
		} catch (Exception e) {
			logger.error("getSearchCondition", e);
		}
		return dto;
	}
}
