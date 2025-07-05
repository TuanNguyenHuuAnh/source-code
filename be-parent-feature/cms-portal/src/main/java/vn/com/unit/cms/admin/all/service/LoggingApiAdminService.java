package vn.com.unit.cms.admin.all.service;

import vn.com.unit.cms.core.module.logApi.dto.LogApiDto;
import vn.com.unit.cms.core.module.logApi.dto.LogApiSearchDto;
import vn.com.unit.common.dto.PageWrapper;
import vn.com.unit.dts.exception.DetailException;

import java.util.Locale;

public interface LoggingApiAdminService {
    PageWrapper<LogApiDto> doSearch(int page, LogApiSearchDto searchDto, int pageSize, Locale locale) throws DetailException;
}
