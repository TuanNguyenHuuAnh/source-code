package vn.com.unit.ep2p.rest.cms;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import vn.com.unit.cms.core.constant.CmsApiConstant;
import vn.com.unit.cms.core.module.events.imports.dto.EventsImportDto;
import vn.com.unit.cms.core.module.events.imports.enumdef.EventsExportEnum;
import vn.com.unit.cms.core.module.events.imports.enumdef.EventsImportEnum;
import vn.com.unit.imp.excel.constant.ImportExcelConstant;
import vn.com.unit.imp.excel.res.ImportExcelAbstractRest;
import vn.com.unit.imp.excel.service.ImportExcelInterfaceService;

@RestController
@RequestMapping(CmsApiConstant.BASE_API_URL + CmsApiConstant.API_CMS_EVENTS_IMPORT)
@Api(tags = { "Events import" })
public class EventsImportRest extends ImportExcelAbstractRest<EventsImportDto>{
	static final Logger logger = LoggerFactory.getLogger(EventsImportRest.class);
 private static final String IMPORT_TEMPLATE_NAME = "EventsImport.xlsx";
    
    private static final String EXPORT_TEMPLATE_NAME = "Danh sach khach moi.xlsx";

    private static final String CONTROLLER_URL = "events-import";

    @Autowired
    @Qualifier("eventsImportServiceImpl")
    ImportExcelInterfaceService<EventsImportDto> commonImportService;


    @Override
    public String getUrlController() {
        return CONTROLLER_URL;
    }
    
    @Override
    public ImportExcelInterfaceService<EventsImportDto> getCommonImportService() {
        return commonImportService;
    }
    
    @SuppressWarnings("rawtypes")
    @Override
    public Class getEnumImport() {
        return EventsImportEnum.class;
    }
    
    @SuppressWarnings("rawtypes")
    @Override
    public Class getEnumExport() {
        return EventsExportEnum.class;
    }
    
    @Override
    public String getTemplateImportName(Locale locale) {
        return IMPORT_TEMPLATE_NAME;
    }

    @Override
    public String getTemplateExportName(Locale locale) {
        return EXPORT_TEMPLATE_NAME;
    }

    @SuppressWarnings("rawtypes")
    @Override
    public Class getClassForExport() {
        return EventsImportDto.class;
    }

    @Override
    public String getStartRowExportResult() {
        return "A8";
    }
    
    @Override
    public Map<String, List<String>> setValidateFormat() {
        Map<String, List<String>> colsValidate = new HashMap<>();
        colsValidate.put("TYPE", Arrays.asList(ImportExcelConstant.NOT_NULL));
        return colsValidate;
    }
    
    @GetMapping("/export-excel")
    public ResponseEntity exportDataList(//@RequestParam("token") String token,
            @RequestParam(value = "sessionKey", required = false) String sessionKey,
            @RequestParam(value = "language", required = false) String language,
            HttpServletRequest req, HttpServletResponse res, Locale locale) throws Exception {
        ResponseEntity resEntity = null;
        try {
        	if(StringUtils.isNotEmpty(language)) {
        		locale = new Locale(language);
        	}
            //Utils.addCookieForExport(token, req, res);
            List<EventsImportDto> lstData = getCommonImportService().getListDataExport(sessionKey);
            resEntity = getCommonImportService().exportExcelRestApi(lstData, getTemplateExportName(locale), getEnumExport(),
                    getClassForExport(), getStartRowExportResult(), req, res, locale);
        } catch (Exception e) {
            logger.error("##Export-Result##", e);
        }
        return resEntity;
    }
}
