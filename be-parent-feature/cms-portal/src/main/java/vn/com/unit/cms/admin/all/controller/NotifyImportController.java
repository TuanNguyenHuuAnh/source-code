package vn.com.unit.cms.admin.all.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import vn.com.unit.cms.admin.all.enumdef.exports.NotifyResultExportEnum;
import vn.com.unit.cms.admin.all.enumdef.imports.NotifyImportEnum;
import vn.com.unit.cms.admin.all.service.NotifyImportService;
import vn.com.unit.cms.core.module.notify.dto.NotifyImportDto;
import vn.com.unit.common.dto.PageWrapper;
import vn.com.unit.core.config.SystemConfig;
import vn.com.unit.ep2p.constant.UrlConst;
import vn.com.unit.imp.excel.constant.ConstantCore;
import vn.com.unit.imp.excel.constant.ImportExcelViewConstant;
import vn.com.unit.imp.excel.controller.ImportExcelAbstractController;
import vn.com.unit.imp.excel.dto.ImportExcelSearchDto;
import vn.com.unit.imp.excel.service.ImportExcelInterfaceService;

@Controller
@RequestMapping(UrlConst.NOTIFY_IMPORT_CONTROLLER)
public class NotifyImportController extends ImportExcelAbstractController<NotifyImportDto> {
    
    private static final String VIEW_LIST_IMPORT = "views/CMS/all/notify-import/notify-import-list.html";

    private static final String VIEW_TABLE_IMPORT = "views/CMS/all/notify-import/notify-import-table.html";

    private static final String CONTROLLER_IMPORT = "notify-import-controller";

    private static final String TEMPLATE_IMPORT = "notify_template_import" + ".xlsx";

    private static final String TEMPLATE_EXPORT = "notify_template_export" + ".xlsx";

    @Autowired
    private NotifyImportService notifyImportService;

    @Autowired
    private SystemConfig systemConfig;

    @Override
    public String getRoleForPage() {
        return "ROLE_AUTHED";
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
    public Class<NotifyImportEnum> getEnumImport() {
        return NotifyImportEnum.class;
    }

    @Override
    public Class<NotifyResultExportEnum> getEnumExport() {
        return NotifyResultExportEnum.class;
    }

    @Override
    public Class<NotifyImportDto> getClassForExport() {
        return NotifyImportDto.class;
    }

    @Override
    public Map<String, List<String>> setValidateFormat() {
        Map<String, List<String>> mapValid = new HashMap<>();
        // mapValid.put("AGENTCODE", Arrays.asList(ImportExcelConstant.NOT_NULL));
        return mapValid;
    }

    @Override
    public ImportExcelInterfaceService<NotifyImportDto> getCommonImportService() {
        return notifyImportService;
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

    @Override
    public String redirectTo(String url, String sessionKey, boolean isError) {
        return UrlConst.REDIRECT.concat("/").concat("notify/edit-import") + "?sessionKey=" + sessionKey+ "&isError="+isError;
    }
    @Override
    public ModelAndView ajaxList(@ModelAttribute(value = "searchDto") ImportExcelSearchDto searchDto,
                                 @RequestParam(value = "sessionKey", required = false) String sessionKey,
                                 @RequestParam(value = "pageSize", required = false, defaultValue = "0") int pageSize,
                                 @RequestParam(value = ConstantCore.PAGE, required = false, defaultValue = "1") int page,
                                 HttpServletRequest request, Locale locale) throws Exception {
        if (securityForPage(getRoleForPage())) {
            return new ModelAndView(ImportExcelViewConstant.ACCESS_DENIED_MODELANDVIEW);
        }

        // init
        ModelAndView mav = new ModelAndView(getViewImportTable());

        PageWrapper<NotifyImportDto> pageWrapper = searchImport(searchDto, pageSize, page, locale, request);

        int error = getCommonImportService().countError(searchDto.getSessionKey());
        if (error > 0) {
            searchDto.setIsError(true);
        }

        // set param
        String url = getUrlController().concat("/list");
        if (StringUtils.isNotBlank(sessionKey)) {
            url = url + "?sessionKey=" + sessionKey;
            searchDto.setUrl(url);
        }

        mav.addObject(ConstantCore.PAGE_WRAPPER, pageWrapper);
        checkError(sessionKey, mav);
        mav.addObject("searchDto", searchDto);
        mav.addObject("controllerUrl", getUrlController());
        mav.addObject("sessionKey", searchDto.getSessionKey());
        return mav;
    }

    @RequestMapping(value = "/reset-grid-data-upload", method = RequestMethod.POST)
    public ModelAndView resetGridDataUpload(@ModelAttribute(value = "searchDto") ImportExcelSearchDto searchDto,
	            @RequestParam(value = "sessionKey", required = false) String sessionKey,
	            @RequestParam(value = "pageSize", required = false, defaultValue = "0") int pageSize,
	            @RequestParam(value = ConstantCore.PAGE, required = false, defaultValue = "1") int page,
	            HttpServletRequest request, Locale locale) throws Exception {
		if (securityForPage(getRoleForPage())) {
			return new ModelAndView(ImportExcelViewConstant.ACCESS_DENIED_MODELANDVIEW);
		}
		
		// init
		ModelAndView mav = new ModelAndView(getViewImportTable());
		
		PageWrapper<NotifyImportDto> pageWrapper = new PageWrapper<>();
		pageWrapper.setDataAndCount(new ArrayList<>(), 0);
		
		int error = getCommonImportService().countError(searchDto.getSessionKey());
		if (error > 0) {
			searchDto.setIsError(true);
		}
		
		// set param
		String url = getUrlController().concat("/list");
		if (StringUtils.isNotBlank(sessionKey)) {
		url = url + "?sessionKey=" + sessionKey;
		searchDto.setUrl(url);
		}
		
		mav.addObject(ConstantCore.PAGE_WRAPPER, pageWrapper);
		checkError(sessionKey, mav);
		mav.addObject("searchDto", searchDto);
		mav.addObject("controllerUrl", getUrlController());
		mav.addObject("sessionKey", searchDto.getSessionKey());
		return mav;
	}
	
    public void checkError(String sessionKey, ModelAndView mav) {
        if(StringUtils.isNotBlank(sessionKey)){
            int error = notifyImportService.countError(sessionKey);
            if (error > 0) {
                mav.addObject("isError",true);
            } else {
                mav.addObject("isError",false);
            }
        } else {
            mav.addObject("isError",false);
        }

    }
    
}
