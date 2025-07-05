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

import vn.com.unit.cms.admin.all.enumdef.exports.FaqsCategoryExportEnum;
import vn.com.unit.cms.admin.all.enumdef.imports.FaqsCategoryImportEnum;
import vn.com.unit.cms.admin.all.service.FaqsCategoryImportService;
import vn.com.unit.cms.core.constant.CmsRoleConstant;
import vn.com.unit.cms.core.module.faqs.dto.FaqsCategoryImportDto;
import vn.com.unit.core.config.SystemConfig;
import vn.com.unit.ep2p.constant.UrlConst;
import vn.com.unit.imp.excel.constant.ImportExcelConstant;
import vn.com.unit.imp.excel.controller.ImportExcelAbstractController;
import vn.com.unit.imp.excel.service.ImportExcelInterfaceService;

@Controller
@RequestMapping(UrlConst.FAQS_CATEGORY_IMPORT)
public class FaqsCategoryImportController extends ImportExcelAbstractController<FaqsCategoryImportDto> {

    private static final String VIEW_LIST_IMPORT = "views/CMS/all/faqs-category-import/faqs-category-import-list.html";

    private static final String VIEW_TABLE_IMPORT = "views/CMS/all/faqs-category-import/faqs-category-import-table.html";

    private static final String CONTROLLER_IMPORT = "faqs-category-import";

    private static final String TEMPLATE_IMPORT = "Faqs_Category_template_import" + ".xlsx";

    private static final String TEMPLATE_EXPORT = "Faqs_Category_template_import_error" + ".xlsx";
    
    static final Logger logger = LoggerFactory.getLogger(FaqsCategoryImportController.class);
    
    @Autowired
    private FaqsCategoryImportService faqsCategoryImportService;
    
    @Autowired
    private SystemConfig systemConfig;
    
    @Autowired
    ServletContext servletContext;

    @Override
    public String getRoleForPage() {
        return CmsRoleConstant.BUTTON_FAQS_IMPORT;
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
    public Class<FaqsCategoryImportEnum> getEnumImport() {
        return FaqsCategoryImportEnum.class;
    }

    @Override
    public Class<FaqsCategoryExportEnum> getEnumExport() {
        return FaqsCategoryExportEnum.class;
    }

    @Override
    public Class<FaqsCategoryImportDto> getClassForExport() {
        return FaqsCategoryImportDto.class;
    }

    @Override
    public Map<String, List<String>> setValidateFormat() {
        Map<String, List<String>> mapValid = new HashMap<>();
       
        mapValid.put("CODE", Arrays.asList(ImportExcelConstant.NOT_NULL));
        mapValid.put("TITLE", Arrays.asList(ImportExcelConstant.NOT_NULL));
        mapValid.put("CONTENT", Arrays.asList(ImportExcelConstant.NOT_NULL));
        mapValid.put("POSTEDDATE", Arrays.asList(ImportExcelConstant.NOT_NULL, "dd/MM/yyyy"));
        
        return mapValid;
    }

    @Override
    public ImportExcelInterfaceService<FaqsCategoryImportDto> getCommonImportService() {
        return faqsCategoryImportService;
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
