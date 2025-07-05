package vn.com.unit.cms.admin.all.controller;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.ServletContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import vn.com.unit.cms.admin.all.enumdef.exports.EmulateResultExportEnum;
import vn.com.unit.cms.admin.all.enumdef.imports.EmulateResultImportEnum;
import vn.com.unit.cms.admin.all.service.EmunateResultImportService;
import vn.com.unit.cms.core.constant.CmsRoleConstant;
import vn.com.unit.cms.core.module.emulate.dto.EmulateResultImportDto;
import vn.com.unit.core.config.SystemConfig;
import vn.com.unit.ep2p.constant.UrlConst;
import vn.com.unit.imp.excel.constant.ImportExcelConstant;
import vn.com.unit.imp.excel.controller.ImportExcelAbstractController;
import vn.com.unit.imp.excel.service.ImportExcelInterfaceService;

@Controller
@RequestMapping(UrlConst.EMULATE_RESULT_IMPORT)
public class EmulateResultImportController extends ImportExcelAbstractController<EmulateResultImportDto> {
    
    private static final String VIEW_LIST_IMPORT = "views/CMS/all/emulate-result-import/emulate-result-import-list.html";

    private static final String VIEW_TABLE_IMPORT = "views/CMS/all/emulate-result-import/emulate-result-import-table.html";

    private static final String CONTROLLER_IMPORT = "emulate-result-import";

    private static final String TEMPLATE_IMPORT = "emulate_result_import" + ".xlsx";

    private static final String TEMPLATE_EXPORT = "emulate_result_export" + ".xlsx";

    @Autowired
    private EmunateResultImportService emunateResultImportService;

    @Autowired
    private SystemConfig systemConfig;

    @Autowired
    ServletContext servletContext;
    
    @Override
    public String getRoleForPage() {
        return CmsRoleConstant.BUTTON_EMULATE_RESULT_IMPORT;

    }

    @Override
    public SystemConfig getSystemConfig() {
        return systemConfig;
    }

    @Override
    public String getTemplateImportName(Locale locale) {
        return TEMPLATE_IMPORT;
    }

    @Override
    public String getTemplateExportName(Locale locale) {
        return TEMPLATE_EXPORT;
    }

    @Override
    public Class<EmulateResultImportEnum> getEnumImport() {
        return EmulateResultImportEnum.class;
    }

    @Override
    public Class<EmulateResultExportEnum> getEnumExport() {
        return EmulateResultExportEnum.class;
    }

    @Override
    public Class<EmulateResultImportDto> getClassForExport() {
        return EmulateResultImportDto.class;
    }

    @Override
    public Map<String, List<String>> setValidateFormat() {
        Map<String, List<String>> mapValid = new HashMap<>();
        
        mapValid.put("MEMONO", Arrays.asList(ImportExcelConstant.NOT_NULL, "MAX_LENGTH=255"));
        mapValid.put("AGENTCODE", Arrays.asList(ImportExcelConstant.NOT_NULL, "MAX_LENGTH=255"));
        mapValid.put("REPORTINGTOCODE", Arrays.asList(ImportExcelConstant.NOT_NULL, "MAX_LENGTH=255"));
        mapValid.put("BMCODE", Arrays.asList("MAX_LENGTH=255"));
        mapValid.put("GADCODE", Arrays.asList("MAX_LENGTH=255"));
        mapValid.put("GACODE", Arrays.asList(ImportExcelConstant.NOT_NULL, "MAX_LENGTH=255"));
        mapValid.put("BDOHCODE", Arrays.asList(ImportExcelConstant.NOT_NULL, "MAX_LENGTH=255"));
        mapValid.put("BDRHCODE", Arrays.asList(ImportExcelConstant.NOT_NULL, "MAX_LENGTH=255"));
        mapValid.put("REGION", Arrays.asList(ImportExcelConstant.NOT_NULL, "MAX_LENGTH=255"));
        mapValid.put("BDAHCODE", Arrays.asList(ImportExcelConstant.NOT_NULL, "MAX_LENGTH=255"));
        mapValid.put("AREA", Arrays.asList(ImportExcelConstant.NOT_NULL, "MAX_LENGTH=255"));
        mapValid.put("BDTHCDE", Arrays.asList(ImportExcelConstant.NOT_NULL, "MAX_LENGTH=255"));
        mapValid.put("TERRITORY", Arrays.asList(ImportExcelConstant.NOT_NULL, "MAX_LENGTH=255"));
        mapValid.put("POLICYNO", Arrays.asList("MAX_LENGTH=255"));
                        
        return mapValid;
    }

    @Override
    public ImportExcelInterfaceService<EmulateResultImportDto> getCommonImportService() {
        return emunateResultImportService;
    }

    @Override
    public String getViewImportList() {
        return VIEW_LIST_IMPORT;
    }

    @Override
    public String getViewImportTable() {
        return VIEW_TABLE_IMPORT;
    }

    @Override
    public String getUrlController() {
        return CONTROLLER_IMPORT;
    }
}
