package vn.com.unit.ep2p.service;

import org.springframework.http.ResponseEntity;
import vn.com.unit.cms.core.dto.CmsCommonPagination;
import vn.com.unit.cms.core.module.report.dto.*;
import vn.com.unit.core.config.SystemConfig;

import java.util.Locale;

import javax.servlet.http.HttpServletResponse;

public interface ApiReportActiveService {

	SystemConfig getSystemConfig();

	CmsCommonPagination<ReportActiveDto> getListReportActiveByAgentCode(ReportActiveSearchDto dto);

	CmsCommonPagination<ReportActiveDto> getListReportActive3MonthsAgoByAgent(ReportActiveSearchDto dto);

	CmsCommonPagination<ReportActiveContractMonthDto> getListContractMonth(ReportActiveContractSearchDto dto);
	
	@SuppressWarnings("rawtypes")
	ResponseEntity exportResultListContractMonth(ReportActiveContractSearchDto searchDto, HttpServletResponse response,
			Locale locale);

    CmsCommonPagination<ReportActiveContractMonthDto> getListContractYear(ReportActiveContractSearchDto searchDto);

	ResponseEntity exportResultListContractYear(ReportActiveContractSearchDto searchDto, HttpServletResponse response, Locale locale);
}
