package vn.com.unit.cms.admin.all.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import vn.com.unit.cms.admin.all.constant.CmsCommonConstant;
import vn.com.unit.cms.admin.all.enumdef.exports.EmulateExportDetailEnum;
import vn.com.unit.cms.admin.all.enumdef.exports.NotifyExportEnum;
import vn.com.unit.cms.admin.all.jcanary.service.CmsCommonService;
import vn.com.unit.cms.admin.all.service.EmulateResultService;
import vn.com.unit.cms.admin.all.service.EmulateService;
import vn.com.unit.cms.admin.all.util.CommonSearchFilterUtils;
import vn.com.unit.cms.core.module.emulate.dto.EmulateResultEditDto;
import vn.com.unit.cms.core.module.emulate.dto.EmulateResultSearchDto;
import vn.com.unit.cms.core.module.emulate.dto.EmulateResultSearchResultDto;
import vn.com.unit.cms.core.module.emulate.dto.EmulateSearchDto;
import vn.com.unit.cms.core.module.emulate.repository.EmulateResultRepository;
import vn.com.unit.cms.core.module.notify.dto.NotifySearchDto;
import vn.com.unit.cms.core.module.notify.dto.NotifySearchResultDto;
import vn.com.unit.core.service.JcaDatatableConfigService;
import vn.com.unit.core.config.SystemConfig;
import vn.com.unit.ep2p.admin.dto.SortOrderDto;
import vn.com.unit.ep2p.core.dto.CommonSearchFilterDto;
import vn.com.unit.imp.excel.dto.ItemColsExcelDto;
import vn.com.unit.imp.excel.utils.ExportExcelUtil;
import vn.com.unit.imp.excel.utils.ImportExcelUtil;

@Service
@Transactional(readOnly = true, rollbackFor = Exception.class)
public class EmulateResultServiceImpl implements EmulateResultService {

	@Autowired
	private EmulateResultRepository emulateResultRepository;

	@Autowired
	private CmsCommonService cmsCommonService;

	@Autowired
	private SystemConfig appSystemConfig;

	@Autowired
	private JcaDatatableConfigService jcaDatatableConfigService;

	@Autowired
	private CommonSearchFilterUtils commonSearchFilterUtils;

	@Autowired
	private MessageSource msg;

	@Autowired
	private ServletContext servletContext;

	@Override
	public List<EmulateResultSearchResultDto> getListByCondition(EmulateResultSearchDto searchDto, Pageable pageable) {
		return emulateResultRepository.findListSearch(searchDto, pageable).getContent();
	}

	@Override
	public int countListByCondition(EmulateResultSearchDto searchDto) {
		return emulateResultRepository.countList(searchDto);
	}

	@Override
	public ServletContext getServletContext() {
		return servletContext;
	}

	@Override
	public SystemConfig getSystemConfig() {
		return appSystemConfig;
	}

	@Override
	public CmsCommonService getCmsCommonService() {
		return cmsCommonService;
	}

	@Override
	public JcaDatatableConfigService getJcaDatatableConfigService() {
		return jcaDatatableConfigService;
	}

	@Override
	public List<CommonSearchFilterDto> initListSearchFilter(EmulateResultSearchDto searchDto, Locale locale) {

		List<CommonSearchFilterDto> list = EmulateResultService.super.initListSearchFilter(searchDto, locale);
		List<CommonSearchFilterDto>  rs = new ArrayList<>();
		if (CollectionUtils.isNotEmpty(list)) {
			for (CommonSearchFilterDto filter : list) {
				rs.add(filter);
			}
		}
		return rs;
	}

	@Override
	public EmulateResultEditDto getEditDtoById(Long id, Locale locale) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void saveOrUpdate(EmulateResultEditDto editDto, Locale locale) throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public void deleteDataById(Long id) throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public List<EmulateResultSearchResultDto> getListForSort(EmulateResultSearchDto searchDto) {
		return emulateResultRepository.findListSorting(searchDto);
	}

	@Override
	public void updateSortAll(EmulateResultSearchDto searchDto) {
		for (SortOrderDto dto : searchDto.getSortOderList()) {
			emulateResultRepository.updateSortAll(dto);
		}
	}

	@Override
	public Class<EmulateExportDetailEnum> getEnumColumnForExport() {
		return EmulateExportDetailEnum.class;

	}

	@Override
	public String getTemplateNameForExport(Locale locale) {
		return "ContestDto";
	}

	@Override
	public void exportExcel(EmulateResultSearchDto searchDto, Pageable pageable,
			Class<EmulateResultSearchResultDto> classSearchResult, HttpServletResponse response, Locale locale) {

		try {
			String templateName = getTemplateNameForExport(locale);
			String template = getServletContext().getRealPath(CmsCommonConstant.REAL_PATH_TEMPLATE_EXCEL) + "/"
					+ templateName + CmsCommonConstant.TYPE_EXCEL;

			String datePattern = getSystemConfig().getConfig(SystemConfig.DATE_PATTERN);

			setSearchParm(searchDto);

			List<EmulateResultSearchResultDto> lstdata = emulateResultRepository.getDeatilByMemo(searchDto);

			List<ItemColsExcelDto> cols = new ArrayList<>();
			// start fill data to workbook
			ImportExcelUtil.setListColumnExcel(getEnumColumnForExport(), cols);
			ExportExcelUtil<EmulateResultSearchResultDto> exportExcel = new ExportExcelUtil<>();
			// do export
			exportExcel.exportExcelWithXSSFNonPass(template, locale, lstdata, classSearchResult, cols, datePattern,
					response, templateName);
		} catch (Exception e) {

		}
	}

	@Override
	public CommonSearchFilterUtils getCommonSearchFilterUtils() {
		return commonSearchFilterUtils;
	}

}
