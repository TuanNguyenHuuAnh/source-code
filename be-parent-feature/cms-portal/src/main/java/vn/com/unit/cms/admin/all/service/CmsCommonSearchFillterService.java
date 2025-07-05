package vn.com.unit.cms.admin.all.service;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;

import vn.com.unit.cms.admin.all.constant.CmsCommonConstant;
import vn.com.unit.cms.admin.all.jcanary.service.CmsCommonService;
import vn.com.unit.cms.admin.all.util.CommonSearchFilterUtils;
import vn.com.unit.cms.core.dto.CmsCommonEditDto;
import vn.com.unit.cms.core.dto.CmsCommonSearchFilterDto;
import vn.com.unit.cms.core.dto.CmsCommonSearchResultFilterDto;
import vn.com.unit.common.dto.PageWrapper;
import vn.com.unit.core.config.SystemConfig;
import vn.com.unit.core.dto.JcaDatatableDefaultConfigDto;
import vn.com.unit.core.service.JcaDatatableConfigService;
import vn.com.unit.dts.exception.DetailException;
import vn.com.unit.ep2p.core.dto.CommonSearchFilterDto;
import vn.com.unit.imp.excel.dto.ItemColsExcelDto;
import vn.com.unit.imp.excel.utils.ExportExcelUtil;
import vn.com.unit.imp.excel.utils.ImportExcelUtil;

@Transactional(readOnly = true, rollbackFor = Exception.class)
public interface CmsCommonSearchFillterService<SEARCH_DTO extends CmsCommonSearchFilterDto, SEARCH_RESULT_DTO extends CmsCommonSearchResultFilterDto, EDIT_DTO extends CmsCommonEditDto> {

    public List<SEARCH_RESULT_DTO> getListByCondition(SEARCH_DTO searchDto, Pageable pageable);

    public int countListByCondition(SEARCH_DTO searchDto);

    public ServletContext getServletContext();

    public SystemConfig getSystemConfig();

    public CmsCommonService getCmsCommonService();

    public JcaDatatableConfigService getJcaDatatableConfigService();

    public CommonSearchFilterUtils getCommonSearchFilterUtils();

    public default List<CommonSearchFilterDto> initListSearchFilter(SEARCH_DTO searchDto, Locale locale) {
        List<CommonSearchFilterDto> rs = new ArrayList<>();
        List<JcaDatatableDefaultConfigDto> listColumns = getListColumnInTable(searchDto.getFunctionCode(),
                locale.toString());

        Field[] chidrenFields = searchDto.getClass().getDeclaredFields();
        Field[] parentFields = searchDto.getClass().getSuperclass().getDeclaredFields();
        Field[] fields = new Field[parentFields.length + chidrenFields.length];
        int index = 0;
        for (Field f : chidrenFields) {
            fields[index] = f;
            index++;
        }
        for (Field f : parentFields) {
            boolean inList = false;

            for (Field fp : fields) {
                if (fp == null) {
                    break;
                } else if (f.getName().equals(fp.getName())) {
                    inList = true;
                    break;
                }
            }
            if (!inList) {
                fields[index] = f;
                index++;
            }
        }

        if (CollectionUtils.isNotEmpty(listColumns)) {
            if (getCommonSearchFilterUtils() != null) {
                for (JcaDatatableDefaultConfigDto colum : listColumns) {
                    CommonSearchFilterDto data = new CommonSearchFilterDto();
                    try {
                        for (Field f : fields) {
                            if (f != null) {
                                f.setAccessible(true);
                                if (colum.getField().equals(f.getName())) {
                                    if ("TEXT".equals(colum.getFieldType())) {
                                        data = getCommonSearchFilterUtils().createInputCommonSearchFilterDto(
                                                colum.getField(), colum.getFieldName(), null);
                                    }

                                    if ("CHECKBOX".equals(colum.getFieldType())) {
                                        data = getCommonSearchFilterUtils().createCheckboxCommonSearchFilterDto(
                                                colum.getField(), colum.getFieldName(), false);
                                    }

                                    if ("DATE".equals(colum.getFieldType())) {
                                        data = getCommonSearchFilterUtils().createDateCommonSearchFilterDto(
                                                colum.getField(), colum.getFieldName(), new Date());
                                    }

                                    rs.add(data);
                                }
                            }
                        }
                    } catch (Exception e) {
                    }
                }
            }
        }
        return rs;
    }

    public default List<JcaDatatableDefaultConfigDto> getListColumnInTable(String functionCode, String languageCode) {
        return getJcaDatatableConfigService().getListJcaDatatableDefaultConfigDto(functionCode, languageCode);
    }

    /**
     * Nếu Field Search rỗng sẽ tìm kiếm theo điều kiện filter. ==> Tìm theo cột chỉ
     * định và tìm theo điều kiện AND
     * 
     * Ngược lại nếu Field Search có giá trị sẽ tìm kiếm theo Field Search ==>
     * search tất cả các cột theo điều kiện OR
     */
    public default void setSearchParm(SEARCH_DTO searchDto) {
        if (StringUtils.isBlank(searchDto.getFieldSearch())) {
            searchDto.setSearchType(CmsCommonConstant.SEARCH_TYPE_AT_FIELD);

            List<JcaDatatableDefaultConfigDto> listColumns = getJcaDatatableConfigService()
                    .getListJcaDatatableDefaultConfigDto(searchDto.getFunctionCode(), searchDto.getLanguageCode());

            StringBuffer querySearch = new StringBuffer();
            for (JcaDatatableDefaultConfigDto column : listColumns) {
                try {
                    Field f = searchDto.getClass().getDeclaredField(column.getField());
                    f.setAccessible(true);
                    if (f.get(searchDto) != null && !"".equals(f.get(searchDto))) {
                        if (StringUtils.isBlank(querySearch.toString())) {
                            if(column.getFieldType().equalsIgnoreCase("DATE")) {
                                String columQuery = "";
                                if ("DATE".equals(column.getFieldType())) {
                                    columQuery = "CONVERT(DATE, " + column.getColumn() + ", 103)";
                                }
                                querySearch.append(columQuery + " " + f.get(searchDto) + " ");
                            } else {
                                querySearch.append(column.getColumn() + " " + f.get(searchDto) + " ");
                            }
                        } else {
                            if(column.getFieldType().equalsIgnoreCase("DATE")) {
                                String columQuery = "";
                                if ("DATE".equals(column.getFieldType())) {
                                    columQuery ="AND " + "CONVERT(DATE, " + column.getColumn() + ", 103)";
                                }
                                querySearch.append(columQuery + " " + f.get(searchDto) + " ");
                            } else {
                                querySearch.append("AND " + column.getColumn() + " " + f.get(searchDto) + " ");
                            }
                        }
                    }
                } catch (Exception e) {
                    try {
                        Field f = searchDto.getClass().getSuperclass().getDeclaredField(column.getField());
                        f.setAccessible(true);
                        if (f.get(searchDto) != null && !"".equals(f.get(searchDto))) {
                            String columQuery = column.getColumn();
                            // DOI VOI DATE
                            if ("DATE".equals(column.getFieldType())) {
                                columQuery = "CONVERT(DATE, " + columQuery + ", 103)";
                            }

                            if (StringUtils.isBlank(querySearch.toString())) {
                                querySearch.append(columQuery + " " + f.get(searchDto) + " ");
                            } else {
                                querySearch.append("AND " + columQuery + " " + f.get(searchDto) + " ");
                            }
                        }
                    } catch (Exception e1) {
                        e1.printStackTrace();
                    }
                }
            }
            searchDto.setQuerySearch(querySearch.toString());
        } else {
            searchDto.setSearchType(CmsCommonConstant.SEARCH_TYPE_ALL);

            List<String> listColumns = getJcaDatatableConfigService().getListCoLumn(searchDto.getFunctionCode(),
                    searchDto.getLanguageCode());

            StringBuffer querySearch = new StringBuffer();
            if (StringUtils.isNotBlank(searchDto.getFieldSearch())) {
                for (String column : listColumns) {
                    if (StringUtils.isBlank(querySearch.toString())) {
                        querySearch.append(column + " LIKE N'%" + searchDto.getFieldSearch() + "%' ");
                    } else {
                        querySearch.append("OR " + column + " LIKE N'%" + searchDto.getFieldSearch() + "%' ");
                    }
                }
                searchDto.setQuerySearch(querySearch.toString());
            }
        }
    }

    public default PageWrapper<SEARCH_RESULT_DTO> doSearch(int page, int pageSize, SEARCH_DTO searchDto,
            Pageable pageable) throws DetailException {

        // set SearchParm
        setSearchParm(searchDto);

        Sort sortForEnums = getCmsCommonService().buildSortEnums(pageable.getSort());

        PageWrapper<SEARCH_RESULT_DTO> pageWrapper = new PageWrapper<SEARCH_RESULT_DTO>();
        getSystemConfig().settingPageSizeList(pageSize, pageWrapper, page);
        pageable = PageRequest.of(page - 1, pageSize, sortForEnums);

        int count = countListByCondition(searchDto);
        List<SEARCH_RESULT_DTO> result = new ArrayList<SEARCH_RESULT_DTO>();

        if (count > 0) {
            result = getListByCondition(searchDto, pageable);
        }

        pageWrapper.setDataAndCount(result, count);
        return pageWrapper;
    }

    public EDIT_DTO getEditDtoById(Long id, Locale locale);

    public void saveOrUpdate(EDIT_DTO editDto, Locale locale) throws Exception;

    public void deleteDataById(Long id) throws Exception;

    public List<SEARCH_RESULT_DTO> getListForSort(SEARCH_DTO searchDto);

    public void updateSortAll(SEARCH_DTO searchDto);

    @SuppressWarnings("rawtypes")
    public Class getEnumColumnForExport();

    /**
     * @author TaiTM
     * @description tên file excel để export dữ liệu
     */
    public String getTemplateNameForExport(Locale locale);

    @SuppressWarnings("unchecked")
    public default void exportExcel(SEARCH_DTO searchDto, Pageable pageable, Class<SEARCH_RESULT_DTO> classSearchResult,
            HttpServletResponse response, Locale locale) {
        try {
            String templateName = getTemplateNameForExport(locale);
            String template = getServletContext().getRealPath(CmsCommonConstant.REAL_PATH_TEMPLATE_EXCEL) + "/"
                    + templateName + CmsCommonConstant.TYPE_EXCEL;

            String datePattern = getSystemConfig().getConfig(SystemConfig.DATE_PATTERN);
            
            setSearchParm(searchDto);

            // listData
            List<SEARCH_RESULT_DTO> lstdata = getListByCondition(searchDto, pageable);

            List<ItemColsExcelDto> cols = new ArrayList<>();
            // start fill data to workbook
            ImportExcelUtil.setListColumnExcel(getEnumColumnForExport(), cols);
            ExportExcelUtil<SEARCH_RESULT_DTO> exportExcel = new ExportExcelUtil<>();

            // do export
            exportExcel.exportExcelWithXSSFNonPass(template, locale, lstdata, classSearchResult, cols, datePattern,
                    response, templateName);
        } catch (Exception e) {

        }
    }
}
