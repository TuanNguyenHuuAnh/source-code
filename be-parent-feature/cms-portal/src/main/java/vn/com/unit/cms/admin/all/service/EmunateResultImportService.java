/**
 * @author TaiTM
 * @date Aug 18, 2020
 */

package vn.com.unit.cms.admin.all.service;

import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import vn.com.unit.cms.core.module.emulate.dto.EmulateResultImportDto;
import vn.com.unit.cms.core.module.emulate.dto.EmulateResultImportSearchDto;
import vn.com.unit.common.dto.PageWrapper;
import vn.com.unit.imp.excel.service.ImportExcelInterfaceService;

public interface EmunateResultImportService extends ImportExcelInterfaceService<EmulateResultImportDto> {

    public PageWrapper<EmulateResultImportDto> doSearchDetail(int page, EmulateResultImportSearchDto searchDto,
            int pageSize, Locale locale) throws Exception;

    public List<EmulateResultImportDto> getListDataExportBySearchDto(EmulateResultImportSearchDto searchDto);

    public void exportExcel(EmulateResultImportSearchDto searchDto, HttpServletRequest req, HttpServletResponse res,
            Locale locale) throws Exception;

    public void updateDataBySessionKey(String sessionKey);

    public void deleteDataBySessionKey(EmulateResultImportSearchDto searchDto) throws Exception;
}
