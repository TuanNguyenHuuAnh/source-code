package vn.com.unit.cms.admin.all.controller;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.ServletContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import vn.com.unit.cms.admin.all.enumdef.exports.DocumentResultExportEnum;
import vn.com.unit.cms.admin.all.enumdef.imports.DocumentImportEnum;
import vn.com.unit.cms.admin.all.service.DocumentImportService;
import vn.com.unit.cms.core.constant.CmsRoleConstant;
import vn.com.unit.cms.core.module.document.dto.DocumentImportDto;
import vn.com.unit.core.config.SystemConfig;
import vn.com.unit.ep2p.constant.UrlConst;
import vn.com.unit.imp.excel.constant.ImportExcelConstant;
import vn.com.unit.imp.excel.controller.ImportExcelAbstractController;
import vn.com.unit.imp.excel.service.ImportExcelInterfaceService;

@Controller
@RequestMapping(UrlConst.DOCUMENT_IMPORT)
public class DocumentImportController extends ImportExcelAbstractController<DocumentImportDto> {
    
    private static final String VIEW_LIST_IMPORT = "views/CMS/all/document-import/document-import-list.html";

    private static final String VIEW_TABLE_IMPORT = "views/CMS/all/document-import/document-import-table.html";

    private static final String CONTROLLER_IMPORT = "document-import";

    private static final String TEMPLATE_IMPORT = "Document_template_import" + ".xlsx";

    private static final String TEMPLATE_EXPORT = "Document_template_import_error" + ".xlsx";
    
    static final Logger logger = LoggerFactory.getLogger(DocumentImportController.class);
    
    @Autowired
    private DocumentImportService documentImportService;
    
    @Autowired
    private SystemConfig systemConfig;
    
    @Autowired
    ServletContext servletContext;


    @Override
    public String getRoleForPage() {
        return CmsRoleConstant.BUTTON_DOCUMENT_IMPORT;
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
    public Class<DocumentImportEnum> getEnumImport() {
        return DocumentImportEnum.class;
    }

    @Override
    public Class<DocumentResultExportEnum> getEnumExport() {
        return DocumentResultExportEnum.class;
    }

    @Override
    public Class<DocumentImportDto> getClassForExport() {
        return DocumentImportDto.class;
    }

    @Override
    public Map<String, List<String>> setValidateFormat() {
        Map<String, List<String>> mapValid = new HashMap<>();
        
        mapValid.put("CODE", Arrays.asList(ImportExcelConstant.NOT_NULL));
        mapValid.put("TITLE", Arrays.asList(ImportExcelConstant.NOT_NULL));
        mapValid.put("POSTEDDATE", Arrays.asList(ImportExcelConstant.NOT_NULL, "dd/MM/yyyy"));
        
        return mapValid;
    }

    @Override
    public ImportExcelInterfaceService<DocumentImportDto> getCommonImportService() {
        return documentImportService;
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
