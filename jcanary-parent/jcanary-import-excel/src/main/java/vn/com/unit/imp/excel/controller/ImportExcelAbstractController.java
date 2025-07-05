/*******************************************************************************
 * Class        ：CommonImportControllerInterface
 * Created date ：2020/05/31
 * Lasted date  ：2020/05/31
 * Author       ：TaiTM
 * Change log   ：2020/05/31：01-00 TaiTM create a new
 ******************************************************************************/
package vn.com.unit.imp.excel.controller;

import java.io.File;
import java.io.FileInputStream;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.beans.propertyeditors.CustomNumberEditor;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import vn.com.unit.common.dto.PageWrapper;
import vn.com.unit.core.config.SystemConfig;
import vn.com.unit.core.security.UserProfileUtils;
import vn.com.unit.imp.excel.constant.CommonConstant;
import vn.com.unit.imp.excel.constant.ConstantCore;
import vn.com.unit.imp.excel.constant.ConstantMessageDms;
import vn.com.unit.imp.excel.constant.ImportExcelConstant;
import vn.com.unit.imp.excel.constant.ImportExcelViewConstant;
import vn.com.unit.imp.excel.constant.Message;
import vn.com.unit.imp.excel.constant.MessageList;
import vn.com.unit.imp.excel.constant.UrlConst;
import vn.com.unit.imp.excel.dto.ImportCommonDto;
import vn.com.unit.imp.excel.dto.ImportExcelSearchDto;
import vn.com.unit.imp.excel.service.ImportExcelInterfaceService;
import vn.com.unit.imp.excel.utils.CommonImportUtils;
import vn.com.unit.imp.excel.utils.ConstantImportUtils;
import vn.com.unit.imp.excel.utils.PluploadUtil;
import vn.com.unit.imp.excel.utils.Utils;

/**
 * ImportExcelInterfaceController
 * 
 * @version 01-00
 * @since 01-00
 * @author TaiTM
 */
@SuppressWarnings("rawtypes")
@Controller
public abstract class ImportExcelAbstractController<T extends ImportCommonDto> {

    /** MessageSource */
    @Autowired
    private MessageSource msg;

    /** SystemConfigure */
    /*
     * @Autowired SystemConfig systemConfig;
     */

    @Autowired
    ServletContext servletContext;

    static final Logger logger = LoggerFactory.getLogger(ImportExcelAbstractController.class);

    public abstract String getRoleForPage();
    
    public abstract SystemConfig getSystemConfig();

    /**
     * searchImport
     *
     * @param page
     * @param searchDto, pageSize, page
     * @return
     * @author TaiTM
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    public PageWrapper<T> searchImport(ImportExcelSearchDto searchDto, int pageSize, int page, Locale locale,
            HttpServletRequest request) throws Exception {

        List<Integer> listPageSize = getListPageSize();
        int sizeOfPage = getSizeOfPage(listPageSize, pageSize);
        int curPage = page != 0 ? page : 1;
        int count = getCommonImportService().countData(searchDto.getSessionKey());
        // pageWrapper
        PageWrapper<T> pageWrapper = new PageWrapper<T>(curPage, sizeOfPage, listPageSize);
        List<T> results = new ArrayList<T>();
        if (count > 0) {
            int currentPage = pageWrapper.getCurrentPage();
            int startIndex = (currentPage - 1) * sizeOfPage;
            results = getCommonImportService().getListData(startIndex, searchDto.getSessionKey(), sizeOfPage);
            getCommonImportService().parseData(results, false, locale);
        }

        pageWrapper.setDataAndCount(results, count);

        return pageWrapper;
    }
    
    /**
     * getTemplateImportName
     * 
     * @author TaiTM
     * @date 01-01-1990
     * @param locale
     * 
     * @description: Định nghĩa tên của Template Import, dựa vào tên để lấy ra file
     *               được lưu trên hệ thống
     */
    public abstract String getTemplateImportName(Locale locale);
    
    /**
     * getTemplateExportName
     * 
     * @author TaiTM
     * @date 01-01-1990
     * @param locale
     * 
     * @description: Định nghĩa tên của Template Export, dựa vào tên để lấy ra file
     *               được lưu trên hệ thống
     */
    public abstract String getTemplateExportName(Locale locale);

    /**
     * getEnumImport
     * 
     * @return Enum
     * @author TaiTM
     * 
     * @description Định nghĩa Enum phục vụ cho việc Import
     */
    public abstract Class getEnumImport();

    /**
     * getEnumExport
     * 
     * @return Enum
     * @author TaiTM
     * 
     * @description Định nghĩa Enum phục vụ cho việc Export
     */
    public abstract Class getEnumExport();
    
    /**
     * getStartRowExportError
     * 
     * @return Enum
     * @author TaiTM
     * 
     * @description Quy định dòng bắt đầu của file export error
     */
    public String getStartRowExportError() {
        return "A5";
    }

    /**
     * getStartRowExportResult
     * 
     * @return Enum
     * @author TaiTM
     * 
     * @description Quy định dòng bắt đầu của file export ở màn hình list
     */
    public String getStartRowExportResult() {
        return "A5";
    }

    /**
     * getStartRowExportResult
     * 
     * @return Enum
     * @author TaiTM
     * 
     * @description Quy định Class của DTO để phục vụ việc xử lý cho import common
     */
    public abstract Class getClassForExport();

    /**
     * setValidateFormat
     * 
     * @param entity
     * @author TaiTM
     * @return Map<key, List<String>> Key is field name to upper case. Value is list
     *         format to check
     *         
     * @description Quy định các format cho các field ([NOT NULL]/ [LENGTH]/ DATE_FORMAT)
     */
    public abstract Map<String, List<String>> setValidateFormat();

    @InitBinder
    public void dateBinder(WebDataBinder binder, HttpServletRequest request, Locale locale) {
        request.getSession().setAttribute("formatDate", getSystemConfig().getConfig(CommonConstant.DATE_PATTERN));
        // The date format to parse or output your dates
        String patternDate = (String) request.getSession().getAttribute(ConstantCore.FORMAT_DATE);
        SimpleDateFormat dateFormat = new SimpleDateFormat(patternDate);
        // Create a new CustomDateEditor
        CustomDateEditor editor = new CustomDateEditor(dateFormat, true);
        // Register it as custom editor for the Date type
        binder.registerCustomEditor(Date.class, editor);
        binder.registerCustomEditor(String.class, new StringTrimmerEditor(false));
        //binder.registerCustomEditor(Double.class, new DoubleEditor(locale, ConstantCore.PATTERN_CURRENCY));
        NumberFormat numberFormat = NumberFormat.getInstance(locale);
        CustomNumberEditor customNumberEditor = new CustomNumberEditor(BigDecimal.class, numberFormat, true);
        binder.registerCustomEditor(BigDecimal.class, customNumberEditor);
    }

    /**
     * initParam
     * 
     * @author TaiTM
     * @param plupload
     * @param request
     * @param locale
     * 
     * @description Khởi tạo các giá trị cần thiết trước khi thực hiện import
     */
    @SuppressWarnings("unchecked")
    public void initParam(T plupload, HttpServletRequest request, Locale locale) throws Exception {
        // set request
        plupload.setRequest(request);

        // Enums for read and write data to excel
        plupload.setEnumImport(getEnumImport());
        if (plupload.getSessionKey() == null || "".equals(plupload.getSessionKey())) {
            plupload.setSessionKey(CommonImportUtils.getImportSessionKey());
        }

        plupload.setColsValidate(setValidateFormat());
    }

    /**
     * ImportExcelInterfaceService
     * 
     * @author TaiTM
     * 
     * @description Quy định Service để xử lý
     */
    public abstract ImportExcelInterfaceService getCommonImportService();

    /**
     * getViewImportList
     *
     * @return
     * @author TaiTM
     */
    public abstract String getViewImportList();

    /**
     * getViewImportTable
     *
     * @return
     * @author TaiTM
     */
    public abstract String getViewImportTable();

    /**
     * getUrlController
     * 
     * @author TaiTM
     * 
     * @description Quy định Controller URL
     */
    public abstract String getUrlController();

    /**
     * saveLstImport
     *
     * @param listImport
     * @author TaiTM
     * @throws Exception
     */
    @PostMapping("/save-data-import")
    @ResponseBody
    public ModelAndView saveListDataImport(@ModelAttribute(value = "searchDto") T searchDto,
            @RequestParam(value = "listDataSave", required = false) List<T> listDataSave,
            @RequestParam(value = "pageSize", required = false, defaultValue = "0") int pageSize,
            @RequestParam(value = ConstantCore.PAGE, required = false, defaultValue = "1") int page,
            RedirectAttributes redirectAttributes, HttpServletRequest request, Locale locale) throws Exception {
        // init
        ModelAndView mav = new ModelAndView(getViewImportList());
        MessageList messageList = new MessageList(Message.SUCCESS);
        
        if (securityForSave(getRoleForPage())) {
            return new ModelAndView(ImportExcelViewConstant.ACCESS_DENIED_MODELANDVIEW);
        }
        
        try {
            doSaveListImport(listDataSave, searchDto.getSessionKey(), locale);

            String message = msg.getMessage(ConstantMessageDms.MSG_SUCCESS_SUBMIT_LANG, null, locale);
            messageList.add(message);
            
            redirectAttributes.addAttribute("isSubmit", true);
        } catch (Exception e) {
            messageList.setStatus(Message.ERROR);
            
            String messageError = e.getMessage();
            if (StringUtils.isNotBlank(messageError)) {
                if (messageError.contains(ConstantMessageDms.ERROR_DEADLOCK_STRING)) {
                    messageError = msg.getMessage(ConstantMessageDms.ERROR_DEADLOCK, null, locale);
                }
            }
            
            String messsage = "ERROR: " + messageError;
            messageList.add(messsage);
            redirectAttributes.addAttribute("isSubmit", false);
            
            searchDto.setIsError(true);
        }

        redirectAttributes.addFlashAttribute(ConstantCore.MSG_LIST, messageList);
        redirectAttributes.addAttribute("sessionKey", searchDto.getSessionKey());
        
        String viewName = UrlConst.REDIRECT.concat("/").concat(getUrlController()).concat("/list");
        mav.setViewName(viewName);

        return mav;
    }
    
    @SuppressWarnings("unchecked")
    public void doSaveListImport(List<T> listDataSave, String sessionKey, Locale locale) throws Exception {
        getCommonImportService().saveListImport(listDataSave, sessionKey, locale, UserProfileUtils.getUserNameLogin());
    }
    
    /**
     * doGetListI
     * 
     * @author TaiTM
     * 
     * @param searchDto
     * @param sessionKey
     * @param pageSize
     * @param page
     * @param request
     * @param locale
     * @return ModelAndView
     * @throws Exception
     * 
     * @description Màn hình list
     */
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public ModelAndView doGetListI(@ModelAttribute(value = "searchDto") ImportExcelSearchDto searchDto,
            @RequestParam(value = "sessionKey", required = false) String sessionKey,
            @RequestParam(value = "pageSize", required = false, defaultValue = "0") int pageSize,
            @RequestParam(value = ConstantCore.PAGE, required = false, defaultValue = "1") int page,
            HttpServletRequest request, Locale locale) throws Exception {

        if (securityForPage(getRoleForPage())) {
            return new ModelAndView("/views/permission-pages/access-denied-page.html");
        }
        
        // init
        ModelAndView mav = new ModelAndView(getViewImportList());

        PageWrapper<T> pageWrapper = searchImport(searchDto, pageSize, page, locale, request);

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

        mav.addObject("searchDto", searchDto);
        mav.addObject("controllerUrl", getUrlController());
        mav.addObject("sessionKey", sessionKey);
        
        return mav;
    }

    /**
     * ajaxList
     * 
     * @author TaiTM
     * 
     * @param searchDto
     * @param sessionKey
     * @param pageSize
     * @param page
     * @param request
     * @param locale
     * @return ModelAndView
     * @throws Exception
     * 
     * @description ajax Search
     */
    @RequestMapping(value = UrlConst.AJAXLIST, method = RequestMethod.POST)
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

        PageWrapper<T> pageWrapper = searchImport(searchDto, pageSize, page, locale, request);

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

        mav.addObject("searchDto", searchDto);
        mav.addObject("controllerUrl", getUrlController());
        mav.addObject("sessionKey", searchDto.getSessionKey());
        return mav;
    }

    /**
     * exportDataList
     * 
     * @author TaiTM
     * 
     * @param token
     * @param sessionKey
     * @param searchDto
     * @param req
     * @param res
     * @param locale
     * @throws Exception
     * 
     * @description export excel
     */
    @SuppressWarnings("unchecked")
    @PostMapping("/export-excel")
    public void exportDataList(@RequestParam("token") String token,
            @RequestParam(value = "sessionKey", required = false) String sessionKey,
            @ModelAttribute(value = "searchDto") ImportExcelSearchDto searchDto, HttpServletRequest req,
            HttpServletResponse res, Locale locale) throws Exception {
        try {
            Utils.addCookieForExport(token, req, res);
            List<T> lstData = getCommonImportService().getListDataExport(searchDto.getSessionKey());

            getCommonImportService().exportExcel(lstData, getTemplateImportName(locale), getEnumExport(),
                    getClassForExport(), getStartRowExportResult(), req, res, locale);
        } catch (Exception e) {
            logger.error("##Export-Result##", e);
        }
    }
    
    public Map<String, Object> getParameterMap(Map<String, String[]> mapParams, ImportExcelSearchDto searchDto) {
        Map<String, Object> mapResults = new HashMap<String, Object>();
        if (mapParams != null) {
            String[] searchDtos = mapParams.get(ImportExcelConstant.SEARCH_DTO);
            if (searchDtos != null && searchDtos.length > 0) {
                String[] args = searchDtos[0].split(ImportExcelConstant.SIGN_AND);
                if (args != null && args.length > 0) {
                    for (String arg : args) {
                        String[] values = arg.split(ImportExcelConstant.EQUAL);
                        if (values.length > 1) {
                            String val = values[1].replace(ImportExcelConstant.SIGN_COMMA, ImportExcelConstant.COMMA)
                                    .replace(ImportExcelConstant.SIGN_PERCENT, " ");
                            mapResults.put(values[0].trim(), val);
                        } else {
                            mapResults.put(values[0].trim(), ImportExcelConstant.EMPTY_STRING);
                        }
                    }
                }
            }
        }

        return mapResults;
    }
    
    /**
     * uploadExcelSaveAllDatas
     * 
     * @author TaiTM
     * 
     * @param searchDto
     * @param plupload
     * @param redirectAttributes
     * @param password
     * @param locale
     * @throws Exception
     * 
     * @description upload file excel: với method này thì tất cả dữ liệu sẽ được lưu
     *              vào databse bỏ qua trường hợp check format data và ghi vào file
     *              nếu có lỗi về format(tất cả các cột trong table nên là VARCHAR/NVARCHAR)
     */
    @SuppressWarnings({ "unchecked" })
    @RequestMapping(value = "/upload-template-excel", method = RequestMethod.POST)
    @ResponseBody
    public ModelAndView uploadExcelSaveAllDatas(@ModelAttribute("searchDto") ImportExcelSearchDto searchDto, T plupload,
            HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes,
            @RequestParam(value = "password", required = false, defaultValue = "") String password, Locale locale)
            throws Exception {
        
        if (securityForUpload(getRoleForPage())) {
            return new ModelAndView(ImportExcelViewConstant.ACCESS_DENIED_MODELANDVIEW);
        }

        ModelAndView mav = new ModelAndView(getViewImportList());
        MessageList messageList = new MessageList(Message.SUCCESS);

        // set date default and set type of field for validate
        plupload.setSessionKey(ImportExcelConstant.EMPTY_STRING);
        initParam(plupload, request, locale);

        // Check oleObject
        String error = null;

        String fileName = ImportExcelConstant.EMPTY_STRING;
        try {
            MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) plupload.getRequest();
            MultiValueMap<String, MultipartFile> map = multipartRequest.getMultiFileMap();
            if (map != null) {
                Iterator<String> iter = map.keySet().iterator();
                while (iter.hasNext()) {
                    String str = (String) iter.next();
                    List<MultipartFile> fileList = map.get(str);
                    for (MultipartFile multipartFile : fileList) {
                        boolean oleObject = PluploadUtil.checkOLEObject(multipartFile.getInputStream());
                        if (oleObject) {
                            error = "Error: File exists OLE object";
                            break;
                        }
                    }
                }
            }
            
            Map<String, Object> mapParams = getParameterMap(multipartRequest.getParameterMap(), searchDto);
            
            String template = getTemplateImportName(locale);
            request.setAttribute(ImportExcelConstant.TEMPLATE_NAME, template);
            
            // handle for update file
            ConstantImportUtils importCommon = getCommonImportService().uploadAllDataExcel(plupload, request, response,
                    password, locale);
            
            request.removeAttribute(ImportExcelConstant.TEMPLATE_NAME);

            // save data success import for database
            getCommonImportService().saveDataToTableImport(importCommon.getData(), plupload.getSessionKey(), locale, UserProfileUtils.getUserNameLogin());

            // validate data by business rule
            if (!getCommonImportService().validateBusiness(plupload.getSessionKey(), searchDto, mapParams,
                    importCommon.getDataSuccess())) {
                String messageError = msg.getMessage("message.error.invalid.data", null, locale);
                messageList.add(messageError);
                messageList.setStatus(Message.ERROR);
            }
            
            CommonImportUtils.checkIsErrorImport(importCommon.getRowErrorMap(), messageList, locale);

            if (!importCommon.getDataError().isEmpty()) {
                plupload.setIsError(true);
            }
        } catch (Exception e1) {
            error = "Error: " + e1.getMessage();
            if (msg.getMessage("message.error.not.template", null, locale).equals(e1.getMessage())) {
                redirectAttributes.addFlashAttribute("error", e1.getMessage());
            } else if (msg.getMessage("message.error.row.is.empty", null, locale).equals(e1.getMessage())) {
                redirectAttributes.addFlashAttribute("error", e1.getMessage());
            } else if (msg.getMessage("message.error.row.start", null, locale).equals(e1.getMessage())) {
                String[] errors = new String[1];
                errors[0] = String.valueOf(plupload.getStartRowData());
                redirectAttributes.addFlashAttribute("error",
                        msg.getMessage("message.error.row.start", errors, locale));
            } else {
                redirectAttributes.addFlashAttribute("error",
                        msg.getMessage("message.error.invalid.data", null, locale));
            }
        }
        if (StringUtils.isNotBlank(error)) {
            // handle error
            messageList = new MessageList(Message.ERROR);
            messageList.add(error);

            logger.error(error);
        }

        if (ImportExcelConstant.SUCCESS.equals(messageList.getStatus())) {
            String message = msg.getMessage("message.uploaded.success", null, locale);
            messageList.add(message);
        }
        
        redirectAttributes.addFlashAttribute(ConstantCore.MSG_ERROR_LIST, messageList);
        redirectAttributes.addFlashAttribute("sessionKey", plupload.getSessionKey());
        redirectAttributes.addFlashAttribute("fileName", fileName);
        redirectAttributes.addFlashAttribute("searchDto", searchDto);
        String viewName = UrlConst.REDIRECT.concat("/").concat(getUrlController())
                .concat("/list?sessionKey=" + plupload.getSessionKey());
        mav.setViewName(viewName);

        return mav;
    }
    
    /**
     * uploadExcelSaveAllDatas
     * 
     * @author TaiTM
     * 
     * @param searchDto
     * @param plupload
     * @param redirectAttributes
     * @param password
     * @param locale
     * @throws Exception
     * 
     * @description upload file excel
     */
    @SuppressWarnings({ "unchecked" })
    @RequestMapping(value = "/upload-template-excel-check-data", method = RequestMethod.POST)
    @ResponseBody
    public ModelAndView uploadExcelCheckData(@ModelAttribute("searchDto") ImportExcelSearchDto searchDto, T plupload,
            HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes,
            @RequestParam(value = "password", required = false, defaultValue = "") String password, Locale locale)
            throws Exception {
        
        if (securityForUpload(getRoleForPage())) {
            return new ModelAndView(ImportExcelViewConstant.ACCESS_DENIED_MODELANDVIEW);
        }

        ModelAndView mav = new ModelAndView(getViewImportList());
        MessageList messageList = new MessageList(Message.SUCCESS);

        // set date default and set type of field for validate
        plupload.setSessionKey(ImportExcelConstant.EMPTY_STRING);
        initParam(plupload, request, locale);

        // Check oleObject
        String error = null;

        String fileName = ImportExcelConstant.EMPTY_STRING;
        try {
            MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) plupload.getRequest();
            MultiValueMap<String, MultipartFile> map = multipartRequest.getMultiFileMap();
            if (map != null) {
                Iterator<String> iter = map.keySet().iterator();
                while (iter.hasNext()) {
                    String str = (String) iter.next();
                    List<MultipartFile> fileList = map.get(str);
                    for (MultipartFile multipartFile : fileList) {
                    	try {
                            // it slurp the input stream
                            Workbook workbook = org.apache.poi.ss.usermodel.WorkbookFactory.create(multipartFile.getInputStream());
                            workbook.close();

                        } catch (java.lang.IllegalArgumentException | org.apache.poi.openxml4j.exceptions.InvalidFormatException e) {
                        	error = "Error: File exists OLE object";
                            break;
                        }
//                        boolean oleObject = PluploadUtil.checkOLEObject(multipartFile.getInputStream());
//                        if (oleObject) {
//                            error = "Error: File exists OLE object";
//                            break;
//                        }
                    }
                }
            }

            Map<String, Object> mapParams = getParameterMap(multipartRequest.getParameterMap(), searchDto);
            
            String template = getTemplateImportName(locale);
            if ( "true".equals(plupload.getIsMultiple())) {
            	request.setAttribute(ImportExcelConstant.TEMPLATE_NAME, "notify_template_import_multi.xlsx");
            }
            else {
            	request.setAttribute(ImportExcelConstant.TEMPLATE_NAME, template);
            }
            
            // handle for update file
            /**
             * - thực hiện việc validate các rule liên quan đến business.
             * - trong quá trình validate nên thực hiện update các message lỗi vào database.
             * - Update cột message_error theo message_code được mapping trong file messages.properties 
             *  và được cách nhau bởi dấu "@;-"
             *      VD: E_BACKDATE@;-E_AGENT_TYPE_EXIST;
             * - kết quả trả về của function validate là TRUE
             * */
            ConstantImportUtils importCommon = getCommonImportService().uploadExcel(plupload, request, response,
                    password, locale);
            
            request.removeAttribute(ImportExcelConstant.TEMPLATE_NAME);
            
            /**
             * File name dùng cho việc download file lỗi
             * */
            fileName = importCommon.getFileName();
            
            /**
             * - Nếu có lỗi, thì ghi lỗi vào file excel đã import và không thực hiện việc lưu các dữ liệu vào database.
             * - Nếu không có lỗi thì lưu dữ liệu thành công vào database và thực hiện tiếp việc kiểm tra các lỗi liên quan đến business.
             * */
            if (CollectionUtils.isNotEmpty(importCommon.getDataError())) {
                getCommonImportService().writeDataFileError(importCommon.getPathFileName(),
                        plupload.getStartRowData() - 1, importCommon.getData(), getEnumImport(), locale);
            } else {
                // save data success import for database
                getCommonImportService().saveDataToTableImport(importCommon.getData(), plupload.getSessionKey(),
                        locale, UserProfileUtils.getUserNameLogin());

                // validate data by business rule
                /**
                 * - thực hiện việc validate các rule liên quan đến business.
                 * - trong quá trình validate nên thực hiện update các message lỗi vào database.
                 * - Update cột message_error theo message_code được mapping trong file messages.properties 
                 *  và được cách nhau bởi dấu "@;-"
                 *      VD: E_BACKDATE@;-E_AGENT_TYPE_EXIST@;-
                 * - kết quả trả về của function validate là TRUE
                 * */
                searchDto.setIsMultiple(plupload.getIsMultiple());
                if (!getCommonImportService().validateBusiness(plupload.getSessionKey(), searchDto, mapParams,
                        importCommon.getDataSuccess())) {
                    String messageError = msg.getMessage("message.error.invalid.data", null, locale);
                    messageList.add(messageError);
                    messageList.setStatus(Message.ERROR);
                    error = messageError;
                }
                
                List allDatas = getCommonImportService().getAllDatas(plupload.getSessionKey());
                getCommonImportService().writeDataFileError(importCommon.getPathFileName(),
                        plupload.getStartRowData() - 1, allDatas, getEnumImport(), locale);
            }
            
            /**
             * Nếu tồn tại lỗi thì dựa vào các lỗi đã lưu trong rowErrorMap sẽ chuyển đổi
             * thành các câu lỗi để đưa ra màn hình.
             */
            CommonImportUtils.checkIsErrorImport(importCommon.getRowErrorMap(), messageList, locale);
            if (!importCommon.getDataError().isEmpty()) {
                searchDto.setIsError(true);
                /*
                 * String messageError = ImportExcelConstant.EMPTY_STRING; for (Message mess :
                 * messageList.getMessages()) { messageError =
                 * messageError.concat(mess.getContent()).concat(ImportExcelConstant.
                 * HTML_BREAK_LINE); }
                 */
                String messageError = msg.getMessage("message.error.invalid.data", null, locale);
                redirectAttributes.addFlashAttribute("error", messageError);
            }
        } catch (Exception e1) {
            error = "Error: " + e1.getMessage();
            searchDto.setIsError(true);
            fileName = (String) request.getAttribute("fileName");
            
            // TRƯỜNG HỢP BẮT LỖI DEAD LOCK VÀ TIMEOUT
            if (StringUtils.isNotBlank(e1.getMessage()) && e1.getMessage().contains(ConstantMessageDms.ERROR_DEADLOCK_STRING)) {
                searchDto.setDeadLock(true);
                redirectAttributes.addFlashAttribute("deadLock", true);
                redirectAttributes.addFlashAttribute("error",
                        msg.getMessage(ConstantMessageDms.ERROR_DEADLOCK, null, locale));
            } else if (StringUtils.isNotBlank(e1.getMessage()) && e1.getMessage().contains(ConstantMessageDms.ERROR_LOCK_TIMEOUT_STRING)) {
                searchDto.setTimeout(true);
                redirectAttributes.addFlashAttribute("timeout", true);
                redirectAttributes.addFlashAttribute("error",
                        msg.getMessage(ConstantMessageDms.ERROR_LOCK_TIMEOUT, null, locale));
            }else if(StringUtils.isNotBlank(e1.getMessage()) && e1.getMessage().contains(ConstantMessageDms.ERROR_TYPE_EXCEL_VALID)) {
            	 searchDto.setExcelFileInvalid(true);
                 redirectAttributes.addFlashAttribute("invalidExcelFile", true);
                 redirectAttributes.addFlashAttribute("error",
                         msg.getMessage(ConstantMessageDms.ERROR_TYPE_EXCEL, null, locale));
            }
            else {
                // TRƯỜNG HỢP BẮT CÁC LỖI LIÊN QUAN ĐẾN FILE EXCEL
                if (msg.getMessage(ImportExcelConstant.ERROR_NOT_TEMPLATE, null, locale).equals(e1.getMessage())) {
                    redirectAttributes.addFlashAttribute("error", e1.getMessage());
                } else if (msg.getMessage(ImportExcelConstant.ERROR_ROW_EMPTY, null, locale).equals(e1.getMessage())) {
                    fileName = (String) request.getAttribute("fileName");
                    redirectAttributes.addFlashAttribute("error", e1.getMessage());
                    request.removeAttribute("fileName");
                } else if (msg.getMessage(ImportExcelConstant.ERROR_ROW_START, null, locale).equals(e1.getMessage())) {
                    String[] errors = new String[1];
                    errors[0] = String.valueOf(plupload.getStartRowData());
                    redirectAttributes.addFlashAttribute("error", e1.getMessage());
                } else {
                    redirectAttributes.addFlashAttribute("error",
                            msg.getMessage(ImportExcelConstant.ERROR_INVALID_DATA, null, locale));
                }
            }
            
        }

        if (StringUtils.isNotBlank(error)) {
            // handle error
            messageList = new MessageList(Message.ERROR);
            messageList.add(error);

            logger.error(error);
        }

        if (ImportExcelConstant.SUCCESS.equals(messageList.getStatus())) {
            String message = msg.getMessage("message.uploaded.success", null, locale);
            messageList.add(message);
        }
        
        redirectAttributes.addFlashAttribute(ConstantCore.MSG_ERROR_LIST, messageList);
        redirectAttributes.addFlashAttribute("sessionKey", plupload.getSessionKey());
        redirectAttributes.addFlashAttribute("fileName", fileName);
        redirectAttributes.addFlashAttribute("searchDto", searchDto);
//        String viewName = UrlConst.REDIRECT.concat("/").concat(getUrlController())
//                .concat("/list?sessionKey=" + plupload.getSessionKey());
        String viewName = redirectTo(UrlConst.REDIRECT.concat("/").concat(getUrlController())
                .concat("/list"),plupload.getSessionKey(), searchDto.getIsError());
        mav.setViewName(viewName);

        return mav;
    }

    public String redirectTo(String url, String sessionKey, boolean isError) {
        String viewName = url + "?sessionKey=" +sessionKey;
        return viewName;
    }

    /**
     * downloadTemplate
     * 
     * @author TaiTM
     * 
     * @param request
     * @param response
     * @param locale
     * @param redirectAttributes
     * @throws Exception
     * 
     * @description thực hiện download template dựa vào [getTemplateImportName]
     */
    @PostMapping(value = UrlConst.URL_DOWNLOAD_TEMPLATE_EXCEL)
    public void downloadTemplate(HttpServletRequest request, HttpServletResponse response, Locale locale,
            RedirectAttributes redirectAttributes) {
        try {
        	String isMultiple = request.getParameter("isMultiple");
            logger.info("isMultiple: " + isMultiple);
            SimpleDateFormat sf = new SimpleDateFormat("yyyyMMdd_HHmmss");
            String extendName = "_" + sf.format(new Date());
            String template = "";
            if ( "true".equals(isMultiple)) {
            	template = servletContext.getRealPath(CommonConstant.REAL_PATH_TEMPLATE_EXCEL) + "/"
            			+ "notify_template_import_multi.xlsx";
            } else {
            	template = servletContext.getRealPath(CommonConstant.REAL_PATH_TEMPLATE_EXCEL) + "/"
            			+ getTemplateImportName(locale);
            }
            File file = new File(template);
            String[] templateNames = getTemplateImportName(locale).split("\\.");
            String name = "";
            if ( "true".equals(isMultiple)) {
            	name =  "notify_template_import_content";
            } else {
            	name = templateNames[0];
            }
            String typeName = "." + templateNames[templateNames.length - 1];
            name = name + extendName + typeName;
            // Create Blank workbook
            try (FileInputStream fileInputStream = new FileInputStream(file);
                    Workbook workbook = new XSSFWorkbook(fileInputStream);) {
                ServletOutputStream outputStream = response.getOutputStream();
                response.setContentType(CommonConstant.CONTENT_TYPE_EXCEL);
                response.addHeader(CommonConstant.CONTENT_DISPOSITION, CommonConstant.ATTCHMENT_FILENAME + name + "\"");
                workbook.write(outputStream);
                outputStream.flush();
            } catch (Exception e) {
                logger.error("##downloadTemplate##", e);
            }
        } catch (Exception e) {
            logger.error("##downloadTemplate##", e);
        }
    }
    
    /**
     * downloadTemplate
     * 
     * @author TaiTM
     * 
     * @param token
     * @param sessionKey
     * @param searchDto
     * @param req
     * @param res
     * @param locale
     * @throws Exception
     * 
     * @description thực hiện download template error dựa vào [getTemplateImportName]
     */
    @SuppressWarnings("unchecked")
    @PostMapping(value = "/download-template-error-excel")
    public void downloadTemplateError(@RequestParam("token") String token,
            @RequestParam(value = "sessionKey", required = false) String sessionKey,
            @ModelAttribute(value = "searchDto") ImportExcelSearchDto searchDto, HttpServletRequest req,
            HttpServletResponse res, Locale locale) throws Exception {
        try {
            Utils.addCookieForExport(token, req, res);
            List<T> lstData = getCommonImportService().getAllDatas(sessionKey);
            getCommonImportService().exportExcel(lstData, getTemplateImportName(locale), getEnumImport(),
                    getClassForExport(), getStartRowExportError(), req, res, locale);
        } catch (Exception e) {
            logger.error("##download-Template-Error##", e);
        }
    }
    
    /**
     * downloadTemplate
     * 
     * @author TaiTM
     * 
     * @param token
     * @param fileName
     * @param req
     * @param res
     * @param locale
     * @throws Exception
     * 
     * @description thực hiện download template error dựa vào fileName
     */
    @PostMapping(value = "/download-template-error-excel-with-file-name")
    public void downloadTemplateErrorWithFileName(@RequestParam("token") String token,
            @RequestParam("fileName") String fileName, HttpServletRequest req, HttpServletResponse res, Locale locale,
            RedirectAttributes redirectAttributes) throws Exception {
        try {
            Utils.addCookieForExport(token, req, res);

            SimpleDateFormat sf = new SimpleDateFormat("yyyyMMdd_HHmmss");
            String extendName = "_" + sf.format(new Date());
            String template = servletContext.getRealPath("/EXCEL-IMPORT-TEMP/") + "/" + fileName;
            File file = new File(template);
            String[] templateNames = getTemplateImportName(locale).split("\\.");
            String name = templateNames[0];
            String typeName = "." + templateNames[templateNames.length - 1];
            name = name + extendName + typeName;
            // Create Blank workbook
            try (FileInputStream fileInputStream = new FileInputStream(file);
                    Workbook workbook = new XSSFWorkbook(fileInputStream);) {
                ServletOutputStream outputStream = res.getOutputStream();
                res.setContentType(CommonConstant.CONTENT_TYPE_EXCEL);
                res.addHeader(CommonConstant.CONTENT_DISPOSITION, CommonConstant.ATTCHMENT_FILENAME + "Error_" + name + "\"");
                workbook.write(outputStream);
                outputStream.flush();
            } catch (Exception e) {
                logger.error("##downloadTemplate##", e);
            }
        } catch (Exception e) {
            logger.error("##downloadTemplate##", e);
        }
    }
    
    /**
     * Role for Save data to Main Table
     */
    public boolean securityForSave(String role) {
        return securityForPage(role);
    }

    /**
     * Role for upload file
     */
    public boolean securityForUpload(String role) {
        return securityForPage(role);
    }

    /**
     * Check role for page
     */
    public boolean securityForPage(String SCREEN_FUNCTION_CODE) {
        return (!UserProfileUtils.hasRole(SCREEN_FUNCTION_CODE)
                && !UserProfileUtils.hasRole(SCREEN_FUNCTION_CODE.concat(ConstantCore.COLON_DISP))
                && !UserProfileUtils.hasRole(SCREEN_FUNCTION_CODE.concat(ConstantCore.COLON_EDIT)));
    }
    
    public List<Integer> getListPageSize() {
        List<Integer> list = new ArrayList<Integer>();
        String listPageSize = getSystemConfig().getConfig("LIST_PAGE_SIZE");
        if (listPageSize != null) {
            String[] pages = listPageSize.split(",");
            if (pages.length > 0) {
                for (String i : pages) {
                    list.add(Integer.parseInt(i));
                }
            }
        }
        return list;
    }
    
    public int getSizeOfPage(List<Integer> listPageSize, int pageSize) {

        int sizeOfPage = 0;
        if (pageSize == 0) {
            sizeOfPage = !listPageSize.isEmpty() ? listPageSize.get(0) : getSystemConfig().getIntConfig(CommonConstant.PAGING_SIZE);
        } else {
            sizeOfPage = pageSize;
        }
        return sizeOfPage;
    }
}
