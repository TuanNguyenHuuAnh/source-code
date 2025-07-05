package vn.com.unit.ep2p.service;


import org.springframework.http.ResponseEntity;
import vn.com.unit.cms.core.dto.CmsCommonPagination;
import vn.com.unit.cms.core.module.reportGroup.*;
import vn.com.unit.cms.core.module.reportGroup.dto.search.ReportGroupSearchDetailDto;
import vn.com.unit.cms.core.module.reportGroup.dto.search.ReportGroupSearchDto;
import vn.com.unit.core.config.SystemConfig;

import java.util.Locale;

public interface ApiReportGroupService {

     SystemConfig getSystemConfig();

     ReportGroupTotalDto getListReportGroupTotal(ReportGroupSearchDto searchDto);

     CmsCommonPagination<ReportGroupResultPremiumDto> getListReportGroupPremium(ReportGroupSearchDetailDto searchDto);

    CmsCommonPagination<ReportGroupResultManPowerDetailDto>  getListReportGroupManpowerDetail(ReportGroupSearchDetailDto searchDto);
//
     CmsCommonPagination<ReportGroupResultManPower2Dto> getListReportGroupManPower(ReportGroupSearchDetailDto searchDto);
     CmsCommonPagination<ReportGroupResultManPowerDetailDto> getListReportGroupManPowerUmBmDetail(ReportGroupSearchDetailDto searchDto);

     CmsCommonPagination<ReportGroupResultPremiumDetailDto> getListReportGroupPremiumDetail(ReportGroupSearchDetailDto searchDto);
     CmsCommonPagination<ReportGroupResultPremiumDetailDto> getListReportGroupPremiumUmBmDetail(ReportGroupSearchDetailDto searchDto);

     ResponseEntity getListReportGroupExportPremium(ReportGroupSearchDetailDto searchDto, Locale locale);
     ResponseEntity getListReportGroupExportPremiumUmBm(ReportGroupSearchDetailDto searchDto, Locale locale);

     ResponseEntity  getListReportGroupExportManpower(ReportGroupSearchDetailDto searchDto,Locale locale);
     ResponseEntity  getListReportGroupExportManpowerUmBm(ReportGroupSearchDetailDto searchDto,Locale locale);
     
     CmsCommonPagination<ReportGroupResultTargetAchievementDto> getListTargetAchievementSale(ReportGroupSearchDetailDto searchDto);
     CmsCommonPagination<ReportGroupResultTargetAchievementDto> getListTargetAchievementCompare(ReportGroupSearchDetailDto searchDto);
     CmsCommonPagination<ReportGroupResultTargetAchievementDto> getListTargetAchievementMissing(ReportGroupSearchDetailDto searchDto);
     CmsCommonPagination<ReportGroupResultTargetAchievementDto> getListTargetAchievementPayFees(ReportGroupSearchDetailDto searchDto);
}
