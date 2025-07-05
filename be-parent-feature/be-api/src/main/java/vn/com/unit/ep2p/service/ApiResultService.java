package vn.com.unit.ep2p.service;

import java.text.ParseException;
import java.util.Locale;

import org.springframework.http.ResponseEntity;

import vn.com.unit.cms.core.dto.CmsCommonPagination;
import vn.com.unit.cms.core.module.result.dto.ResultDto;
import vn.com.unit.cms.core.module.result.dto.ResultSearchDto;
import vn.com.unit.core.config.SystemConfig;

public interface ApiResultService {
	
	SystemConfig getSystemConfig();

	CmsCommonPagination<ResultDto> getLisResultPromotion(ResultSearchDto searchDto);
	
	CmsCommonPagination<ResultDto> getLisResultMaintain(ResultSearchDto searchDto);

	ResultDto getLisResultPromotionPersonal(ResultSearchDto searchDto) throws ParseException;

	ResultDto getLisResultMaintainPersonal(ResultSearchDto searchDto) throws ParseException;

	@SuppressWarnings("rawtypes")
	ResponseEntity exportPromotion(ResultSearchDto searchDto,Locale locale);
	
	@SuppressWarnings("rawtypes")
	ResponseEntity exportMaintain(ResultSearchDto searchDto,Locale locale);

    boolean checkAgentType(String agentType);
}
