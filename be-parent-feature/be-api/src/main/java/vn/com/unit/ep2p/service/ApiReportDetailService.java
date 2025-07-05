package vn.com.unit.ep2p.service;

import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletResponse;

import org.springframework.http.ResponseEntity;

import vn.com.unit.cms.core.dto.CmsCommonPagination;
import vn.com.unit.cms.core.module.report.dto.ReportDetailDto;
import vn.com.unit.cms.core.module.report.dto.ReportDetailSearchDto;
import vn.com.unit.cms.core.module.report.dto.ReportGetDetailDto;
import vn.com.unit.cms.core.module.report.dto.ReportGroupDetailDto;
import vn.com.unit.cms.core.module.report.dto.ReportGroupDetailSearchDto;
import vn.com.unit.cms.core.module.report.dto.ReportListDetailSearchDto;
import vn.com.unit.dts.exception.DetailException;
import vn.com.unit.core.config.SystemConfig;

public interface ApiReportDetailService {
	
	CmsCommonPagination<ReportGetDetailDto> getReportDetailByAgentCode(ReportDetailSearchDto searchDto) throws DetailException;

	CmsCommonPagination<ReportDetailDto> getListReportDetailByAgentCode(ReportListDetailSearchDto searchDto);
	
	CmsCommonPagination<ReportGroupDetailDto> getListReportGroupDetailByAgentGroup(ReportGroupDetailSearchDto dto);
	
	@SuppressWarnings("rawtypes")
	ResponseEntity exportResultDetail(ReportListDetailSearchDto searchDto, HttpServletResponse response,
			Locale locale);
	
	@SuppressWarnings("rawtypes")
	ResponseEntity exportResulGrouptDetail(ReportGroupDetailSearchDto searchDto,Locale locale);

	SystemConfig getSystemConfig();


}
