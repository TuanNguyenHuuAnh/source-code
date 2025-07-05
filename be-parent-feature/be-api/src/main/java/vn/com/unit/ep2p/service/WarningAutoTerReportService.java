package vn.com.unit.ep2p.service;

import org.springframework.http.ResponseEntity;

import vn.com.unit.cms.core.dto.CmsCommonPagination;
import vn.com.unit.cms.core.module.autoter.dto.AutoterReportDetailDto;
import vn.com.unit.cms.core.module.autoter.dto.AutoterSearchDto;
import vn.com.unit.cms.core.module.autoter.dto.AutoterSumaryDto;
import vn.com.unit.core.config.SystemConfig;

public interface WarningAutoTerReportService {

     SystemConfig getSystemConfig();
	 CmsCommonPagination<AutoterReportDetailDto>  getListAutoTerReportDetail(AutoterSearchDto searchDto);
	 CmsCommonPagination<AutoterSumaryDto>  getAutoterSumary(AutoterSearchDto searchDto); 
     ResponseEntity getListAutoTerReportExport(AutoterSearchDto searchDto);
}
