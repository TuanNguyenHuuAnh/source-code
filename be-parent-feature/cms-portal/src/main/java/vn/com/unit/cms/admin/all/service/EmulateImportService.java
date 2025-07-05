package vn.com.unit.cms.admin.all.service;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import vn.com.unit.cms.core.module.emulate.dto.EmulateImportDto;
import vn.com.unit.cms.core.module.emulate.dto.EmulateImportSearchDto;
import vn.com.unit.cms.core.module.emulate.entity.ContestSummary;
import vn.com.unit.common.dto.PageWrapper;
import vn.com.unit.imp.excel.service.ImportExcelInterfaceService;

public interface EmulateImportService extends ImportExcelInterfaceService<EmulateImportDto> {

	public PageWrapper<EmulateImportDto> doSearchDetail(int page, EmulateImportSearchDto searchDto, int pageSize,
			Locale locale) throws Exception;

	public void exportExcel(EmulateImportSearchDto searchDto, HttpServletRequest req, HttpServletResponse res, Locale locale)
			throws Exception;

	public void updateDataBySessionKey(String sessionKey);

	public void updateData(String memoNo, String username,ContestSummary getSummary);
}
