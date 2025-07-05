package vn.com.unit.cms.admin.all.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import vn.com.unit.cms.admin.all.enumdef.exports.EmulateExportEnum;
import vn.com.unit.cms.admin.all.enumdef.imports.EmulateImportEnum;
import vn.com.unit.cms.admin.all.service.EmulateImportService;
import vn.com.unit.cms.core.constant.CmsRoleConstant;
import vn.com.unit.cms.core.module.emulate.dto.EmulateImportDto;
import vn.com.unit.cms.core.module.emulate.dto.EmulateResultImportDto;
import vn.com.unit.core.config.SystemConfig;
import vn.com.unit.core.security.UserProfileUtils;
import vn.com.unit.ep2p.constant.UrlConst;
import vn.com.unit.imp.excel.constant.ImportExcelConstant;
import vn.com.unit.imp.excel.controller.ImportExcelAbstractController;
import vn.com.unit.imp.excel.service.ImportExcelInterfaceService;

import javax.servlet.ServletContext;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

@Controller
@RequestMapping(UrlConst.EMULATE_IMPORT)
public class EmulateImportController extends ImportExcelAbstractController<EmulateImportDto> {

    private static final String VIEW_LIST_IMPORT = "views/CMS/all/emulate-import/emulate-import-list.html";

    private static final String VIEW_TABLE_IMPORT = "views/CMS/all/emulate-import/emulate-import-table.html";

    private static final String CONTROLLER_IMPORT = "emulate-import";

    private static final String TEMPLATE_IMPORT = "emulate_import" + ".xlsx";

    private static final String TEMPLATE_EXPORT = "emulate_export" + ".xlsx";

    @Autowired
    private EmulateImportService emulateImportService;
  
    @Autowired
    private SystemConfig systemConfig;

    @Autowired
    ServletContext servletContext;


    @Override
    public String getRoleForPage() {
        return CmsRoleConstant.BUTTON_EMULATE_IMPORT;

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
    public Class<EmulateImportEnum> getEnumImport() {
        return EmulateImportEnum.class;
    }

    @Override
    public Class<EmulateExportEnum> getEnumExport() {
        return EmulateExportEnum.class;
    }

    @Override
    public Class<EmulateImportDto> getClassForExport() {
        return EmulateImportDto.class;
    }

    @Override
    public Map<String, List<String>> setValidateFormat() {
        Map<String, List<String>> mapValid = new HashMap<>();
        mapValid.put("MEMONO", Arrays.asList(ImportExcelConstant.NOT_NULL));
        mapValid.put("CONTESTNAME", Arrays.asList(ImportExcelConstant.NOT_NULL));
        mapValid.put("DESCRIPTION", Arrays.asList(ImportExcelConstant.NOT_NULL));
        mapValid.put("CONTESTTYPE", Arrays.asList(ImportExcelConstant.NOT_NULL));
        mapValid.put("STARTDATE", Arrays.asList(ImportExcelConstant.NOT_NULL, "dd/MM/yyyy"));
        mapValid.put("EFFECTIVEDATE", Arrays.asList(ImportExcelConstant.NOT_NULL,"dd/MM/yyyy hh:mm"));
        mapValid.put("APPLICABLEOBJECT", Arrays.asList(ImportExcelConstant.NOT_NULL));

        return mapValid;
    }

    @Override
    public ImportExcelInterfaceService<EmulateImportDto> getCommonImportService() {
        return emulateImportService;
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
