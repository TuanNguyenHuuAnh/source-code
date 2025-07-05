package vn.com.unit.imp.excel.res;

import java.io.*;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.MessageSource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import vn.com.unit.core.config.SystemConfig;
import vn.com.unit.core.res.ObjectDataImportRes;
import vn.com.unit.dts.web.rest.common.DtsApiResponse;
import vn.com.unit.imp.excel.constant.CommonConstant;
import vn.com.unit.imp.excel.constant.ConstantMessageDms;
import vn.com.unit.imp.excel.constant.ImportExcelConstant;
import vn.com.unit.imp.excel.constant.Message;
import vn.com.unit.imp.excel.constant.MessageList;
import vn.com.unit.imp.excel.dto.ImportCommonDto;
import vn.com.unit.imp.excel.dto.ImportExcelSearchDto;
import vn.com.unit.imp.excel.dto.UploadResultDto;
import vn.com.unit.imp.excel.service.ImportExcelInterfaceService;
import vn.com.unit.imp.excel.utils.CommonImportUtils;
import vn.com.unit.imp.excel.utils.ConstantImportUtils;
import vn.com.unit.imp.excel.utils.ExportExcelUtil;
import vn.com.unit.imp.excel.utils.PluploadUtil;
import vn.com.unit.imp.excel.utils.Utils;

/**
 * ImportExcelAbstractRest
 * 
 * @version 01-00
 * @since 01-00
 * @author TuyenNX
 */
@SuppressWarnings("rawtypes")
@RestController
public abstract class ImportExcelAbstractRest<T extends ImportCommonDto> extends AbstractRest{

    /** MessageSource */
    @Autowired
    @Qualifier("messageSource")
    private MessageSource msg;

    @Autowired
    ServletContext servletContext;

    @Autowired
    private SystemConfig systemConfig;

    static final Logger logger = LoggerFactory.getLogger(ImportExcelAbstractRest.class);
    
    /**
     * listGet
     * @param sessionKey
     * @return
     * @author TuyenNX
     */
    @GetMapping("/list")
    public DtsApiResponse listGet(@RequestParam(name="sessionKey", required = false) String sessionKey
    		,@RequestParam(value = "language", required = false) String language
    		,@RequestParam(defaultValue = "1" ,value = "page", required = false) Integer page
    		,@RequestParam(defaultValue = "5" ,value = "size", required = false) Integer size
    		, Locale locale) {
        long start = System.currentTimeMillis();
        if(StringUtils.isNotEmpty(language)) {
    		locale = new Locale(language);
    	} else {
            locale = new Locale("vi");
        }
        try {
            ObjectDataImportRes<T> resObj = doSearch(sessionKey, page, size, locale);
            return this.successHandler.handlerSuccess(resObj, start, sessionKey, "");
        } catch (Exception ex) {
            return this.errorHandler.handlerException(ex, start, sessionKey, "");
        }
    }
    
    @SuppressWarnings("unchecked")
    public ObjectDataImportRes<T> doSearch(String sessionKey, Integer page, Integer sizeOfPage, Locale locale) throws Exception {
        ObjectDataImportRes<T> resObj = null;
        try {
            /** init pageable */
            int totalData = getCommonImportService().countData(sessionKey);
            List<T> datas = new ArrayList<>();
            if (totalData > 0) {
                datas = getCommonImportService().getListData(getOffset(page, sizeOfPage), sessionKey, sizeOfPage);
                getCommonImportService().parseData(datas, false, locale);
            }
//            String messageError = msg.getMessage("CLASS.INFO.E001", null, locale);
            resObj = new ObjectDataImportRes<>(totalData, datas);
            int error = getCommonImportService().countError(sessionKey);
            if (error > 0) {
                resObj.setIsError(true);
            }
        } catch (Exception e) {
            logger.info("Import list", e);
            throw e;
        }
        return resObj;
    }
    
    @GetMapping(value = "/download-template-excel")
    public ResponseEntity downloadTemplate() {
        String templateName = getTemplateImportName(null);
        ResponseEntity res = null;
        try {
            String repo = systemConfig.getConfig(systemConfig.REPO_UPLOADED_MAIN);
            String path = systemConfig.getPhysicalPathById(repo, null); //path up service
            String templatePath = servletContext.getRealPath(CommonConstant.REAL_PATH_TEMPLATE_EXCEL + "/" + templateName);
            ExportExcelUtil exportExcel = new ExportExcelUtil<>();
            Workbook workbook = exportExcel.getXSSFWorkbook(templatePath);
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            SimpleDateFormat formatDateExport = new SimpleDateFormat("yyyyMMdd_HHmmss");
            String currentDate = formatDateExport.format(new Date());
            workbook.write(out);
            //save file to server
            String pathFile = Paths.get(path,CommonConstant.PATH_FILE_EXPORT, CommonConstant.PATH_FILE_DLVN,
                    templateName.replace(CommonConstant.TYPE_EXCEL, "") + "_" + currentDate + CommonConstant.TYPE_EXCEL).toString();

            File file = new File(pathFile);
            try (OutputStream os = new FileOutputStream(file)) {
                workbook.write(os);
            }
            String pathOut = Paths.get(CommonConstant.PATH_FILE_EXPORT, CommonConstant.PATH_FILE_DLVN,
                    templateName.replace(CommonConstant.TYPE_EXCEL, "") + "_" + currentDate + CommonConstant.TYPE_EXCEL).toString().replace("\\", "/");
            ByteArrayInputStream in = new ByteArrayInputStream(out.toByteArray());
            HttpHeaders headers = new HttpHeaders();
            headers.add(CommonConstant.CONTENT_DISPOSITION, CommonConstant.ATTCHMENT_FILENAME
                    + templateName.replace(CommonConstant.TYPE_EXCEL, "") + "_" + currentDate + CommonConstant.TYPE_EXCEL + "\"" +";path="+pathOut);
            headers.add("Access-Control-Expose-Headers", "Content-Disposition");
            res = ResponseEntity.ok().headers(headers).contentType(MediaType.parseMediaType(CommonConstant.CONTENT_TYPE_EXCEL))
                    .body(new InputStreamResource(in));
        } catch (Exception e) {
            logger.error("##downloadTemplate##", e);
        }
        return res;
    }
    
    @SuppressWarnings("unchecked")
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
            List<T> lstData = getCommonImportService().getListDataExport(sessionKey);
            resEntity = getCommonImportService().exportExcelRestApi(lstData, getTemplateExportName(locale), getEnumExport(),
                    getClassForExport(), getStartRowExportResult(), req, res, locale);
        } catch (Exception e) {
            logger.error("##Export-Result##", e);
        }
        return resEntity;
    }
    
    @SuppressWarnings({ "unchecked" })
    @PostMapping("/upload-template-excel-check-data")
    public DtsApiResponse uploadExcelCheckData(@ModelAttribute("searchDto") ImportExcelSearchDto searchDto, T plupload,
            HttpServletRequest request, HttpServletResponse response, 
            @RequestParam(value = "password", required = false, defaultValue = "") String password, Locale locale)
            throws Exception {
        long start = System.currentTimeMillis();
        try {
            UploadResultDto uploadResultDto = new UploadResultDto();
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
    
                        }
                    }
                }
    
                Map<String, Object> mapParams = getParameterMap(multipartRequest.getParameterMap(), searchDto);
                
                String template = getTemplateImportName(locale);
                request.setAttribute(ImportExcelConstant.TEMPLATE_NAME, template);
                
                // handle for update file
                /**
                 * - thực hiện việc validate các rule liên quan đến business.
                 * - trong quá trình validate nên thực hiện update các message lỗi vào database.
                 * - Update cột message_error theo message_code được mapping trong file messages.properties 
                 *  và được cách nhau bởi dấu "@;-"
                 *      VD: E_BACKDATE@;-E_AGENT_TYPE_EXIST;
                 * - kết quả trả về của function validate là TRUE
                 * */
                ConstantImportUtils importCommon = getCommonImportService().uploadAllDataExcel(plupload, request, response,
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
                            locale, plupload.getUsername());
    
                    // validate data by business rule
                    /**
                     * - thực hiện việc validate các rule liên quan đến business.
                     * - trong quá trình validate nên thực hiện update các message lỗi vào database.
                     * - Update cột message_error theo message_code được mapping trong file messages.properties 
                     *  và được cách nhau bởi dấu "@;-"
                     *      VD: E_BACKDATE@;-E_AGENT_TYPE_EXIST@;-
                     * - kết quả trả về của function validate là TRUE
                     * */
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
                    String messageError = msg.getMessage("message.error.invalid.data", null, locale);
                    uploadResultDto.setTypeError(UploadResultDto.INVALID_DATA);
                    uploadResultDto.setMessageError(messageError);
                }
            } catch (Exception e1) {
                error = "Error: " + e1.getMessage();
                searchDto.setIsError(true);
                
                // TRƯỜNG HỢP BẮT LỖI DEAD LOCK VÀ TIMEOUT
                if (StringUtils.isNotBlank(e1.getMessage()) && e1.getMessage().contains(ConstantMessageDms.ERROR_DEADLOCK_STRING)) {
                    searchDto.setDeadLock(true);
                    uploadResultDto.setTypeError(UploadResultDto.DEADLOCK);
                    uploadResultDto.setMessageError(msg.getMessage(ConstantMessageDms.ERROR_DEADLOCK, null, locale));
                } else if (StringUtils.isNotBlank(e1.getMessage()) && e1.getMessage().contains(ConstantMessageDms.ERROR_LOCK_TIMEOUT_STRING)) {
                    searchDto.setTimeout(true);
                    uploadResultDto.setTypeError(UploadResultDto.TIMEOUT);
                    uploadResultDto.setMessageError(msg.getMessage(ConstantMessageDms.ERROR_LOCK_TIMEOUT, null, locale));
                }else if(StringUtils.isNotBlank(e1.getMessage()) && e1.getMessage().contains(ConstantMessageDms.ERROR_TYPE_EXCEL_VALID)) {
                     searchDto.setExcelFileInvalid(true);
                     uploadResultDto.setTypeError(UploadResultDto.INVALID_EXCEL_FILE);
                     uploadResultDto.setMessageError(msg.getMessage(ConstantMessageDms.ERROR_TYPE_EXCEL, null, locale));
                }
                else {
                    // TRƯỜNG HỢP BẮT CÁC LỖI LIÊN QUAN ĐẾN FILE EXCEL
                    if (msg.getMessage(ImportExcelConstant.ERROR_NOT_TEMPLATE, null, locale).equals(e1.getMessage())) {
                        uploadResultDto.setTypeError(UploadResultDto.ERROR_NOT_TEMPLATE);
                        uploadResultDto.setMessageError(e1.getMessage());
                    } else if (msg.getMessage(ImportExcelConstant.ERROR_ROW_EMPTY, null, locale).equals(e1.getMessage())) {
                        fileName = (String) request.getAttribute("fileName");
                        uploadResultDto.setTypeError(UploadResultDto.ERROR_ROW_EMPTY);
                        uploadResultDto.setMessageError(e1.getMessage());
                        request.removeAttribute("fileName");
                    } else if (msg.getMessage(ImportExcelConstant.ERROR_ROW_START, null, locale).equals(e1.getMessage())) {
                        String[] errors = new String[1];
                        errors[0] = String.valueOf(plupload.getStartRowData());
                        uploadResultDto.setTypeError(UploadResultDto.ERROR_ROW_START);
                        uploadResultDto.setMessageError(e1.getMessage());
                    } else {
                        uploadResultDto.setTypeError(UploadResultDto.INVALID_DATA);
                        uploadResultDto.setMessageError(msg.getMessage(ImportExcelConstant.ERROR_INVALID_DATA, null, locale));
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
            
            uploadResultDto.setMessageList(messageList);
            uploadResultDto.setSessionKey(plupload.getSessionKey());
            uploadResultDto.setFileName(fileName);
            uploadResultDto.setSearchDto(searchDto);
            return this.successHandler.handlerSuccess(uploadResultDto, start);
        } catch (Exception ex) {
            return this.errorHandler.handlerException(ex, start);
        }
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
    public DtsApiResponse saveListDataImport(@RequestParam(value = "sessionKey") String sessionKey,
    		@ModelAttribute(value = "searchDto") ImportExcelSearchDto searchDto,
            @RequestParam(value = "listDataSave", required = false) List<T> listDataSave,
            HttpServletRequest request, Locale locale, String username
            ) throws Exception {
        long start = System.currentTimeMillis();
        boolean isSubmit = false;
        try {
            UploadResultDto uploadResultDto = new UploadResultDto();
            MessageList messageList = new MessageList(Message.SUCCESS);
    
            try {
                doSaveListImport(listDataSave, sessionKey, locale, username);
                String message = msg.getMessage(ConstantMessageDms.MSG_SUCCESS_SUBMIT, null, locale);
                messageList.add(message);
                isSubmit = true;
            } catch (Exception e) {
                messageList.setStatus(Message.ERROR);
                
                String messageError = e.getMessage();
                if (StringUtils.isNotBlank(messageError)) {
                    if (messageError.contains(ConstantMessageDms.ERROR_DEADLOCK_STRING)) {
                        messageError = msg.getMessage(ConstantMessageDms.ERROR_DEADLOCK, null, locale);
                        uploadResultDto.setTypeError(UploadResultDto.DEADLOCK);
                    }
                }
                
                String messsage = "ERROR: " + messageError;
                messageList.add(messsage);
                uploadResultDto.setMessageError(messsage);
                isSubmit = false;
                searchDto.setIsError(true);
            }
            searchDto.setIsSubmit(isSubmit);
            uploadResultDto.setMessageList(messageList);
            uploadResultDto.setSessionKey(sessionKey);
            uploadResultDto.setSearchDto(searchDto);
            return this.successHandler.handlerSuccess(uploadResultDto, start, sessionKey, null);
        } catch (Exception ex) {
            return this.errorHandler.handlerException(ex, start, sessionKey, null);
        }
    }
    
    @SuppressWarnings("unchecked")
    public void doSaveListImport(List<T> listDataSave, String sessionKey, Locale locale, String username) throws Exception {
        getCommonImportService().saveListImport(listDataSave, sessionKey, locale, username);
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
    public DtsApiResponse uploadExcelSaveAllDatas(@ModelAttribute("searchDto") ImportExcelSearchDto searchDto, T plupload,
            HttpServletRequest request, HttpServletResponse response,
            @RequestParam(value = "password", required = false, defaultValue = "") String password
    		, @RequestParam(value = "language", required = false, defaultValue = "") String language, Locale locale)
            throws Exception {
        long start = System.currentTimeMillis();
        try {
        	if(StringUtils.isNotEmpty(language)) {
        		locale = new Locale(language);
        	}else {
        		locale = new Locale("vi");
        	}
            UploadResultDto uploadResultDto = new UploadResultDto();
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
                getCommonImportService().saveDataToTableImport(importCommon.getData()
                		, plupload.getSessionKey(), locale, plupload.getUsername());

                //tinhnt add Id
                mapParams.put("id", plupload.getId());
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
                searchDto.setIsError(true);
                if (msg.getMessage(ImportExcelConstant.ERROR_NOT_TEMPLATE, null, locale).equals(e1.getMessage())) {
                    uploadResultDto.setTypeError(UploadResultDto.ERROR_NOT_TEMPLATE);
                    uploadResultDto.setMessageError(e1.getMessage());
                } else if (msg.getMessage(ImportExcelConstant.ERROR_ROW_EMPTY, null, locale).equals(e1.getMessage())) {
                    fileName = (String) request.getAttribute("fileName");
                    uploadResultDto.setTypeError(UploadResultDto.ERROR_ROW_EMPTY);
                    uploadResultDto.setMessageError(e1.getMessage());
                    request.removeAttribute("fileName");
                } else if (msg.getMessage(ImportExcelConstant.ERROR_ROW_START, null, locale).equals(e1.getMessage())) {
                    String[] errors = new String[1];
                    errors[0] = String.valueOf(plupload.getStartRowData());
                    uploadResultDto.setTypeError(UploadResultDto.ERROR_ROW_START);
                    uploadResultDto.setMessageError(e1.getMessage());
                } else {
                    uploadResultDto.setTypeError(UploadResultDto.INVALID_DATA);
                    uploadResultDto.setMessageError(msg.getMessage(ImportExcelConstant.ERROR_INVALID_DATA, null, locale));
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
            
            uploadResultDto.setMessageList(messageList);
            uploadResultDto.setSessionKey(plupload.getSessionKey());
            uploadResultDto.setFileName(fileName);
            uploadResultDto.setSearchDto(searchDto);
            return this.successHandler.handlerSuccess(uploadResultDto, start);
        } catch (Exception ex) {
            return this.errorHandler.handlerException(ex, start);
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
    public ResponseEntity downloadTemplateError(@RequestParam("token") String token,
            @RequestParam(value = "sessionKey", required = false) String sessionKey,
            @ModelAttribute(value = "searchDto") ImportExcelSearchDto searchDto, HttpServletRequest req,
            HttpServletResponse res, Locale locale) throws Exception {
        ResponseEntity resEntity = null;
        try {
            Utils.addCookieForExport(token, req, res);
            List<T> lstData = getCommonImportService().getAllDatas(sessionKey);
            resEntity = getCommonImportService().exportExcelRestApi(lstData, getTemplateImportName(locale), getEnumImport(),
                    getClassForExport(), getStartRowExportError(), req, res, locale);
        } catch (Exception e) {
            logger.error("##download-Template-Error##", e);
        }
        return resEntity;
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
    public ResponseEntity downloadTemplateErrorWithFileName(@RequestParam("token") String token,
            @RequestParam("fileName") String fileName, HttpServletRequest req, HttpServletResponse res, Locale locale,
            RedirectAttributes redirectAttributes) throws Exception {
        ResponseEntity resEntity = null;
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
                ByteArrayOutputStream out = new ByteArrayOutputStream();
                workbook.write(out);
                ByteArrayInputStream in = new ByteArrayInputStream(out.toByteArray());
                HttpHeaders headers = new HttpHeaders();
                headers.add(CommonConstant.CONTENT_DISPOSITION, CommonConstant.ATTCHMENT_FILENAME + "Error_" + name + "\"");
                resEntity = ResponseEntity.ok().headers(headers).contentType(MediaType.parseMediaType(CommonConstant.CONTENT_TYPE_EXCEL))
                        .body(new InputStreamResource(in));
            } catch (Exception e) {
                logger.error("##downloadTemplate##", e);
            }
        } catch (Exception e) {
            logger.error("##downloadTemplate##", e);
        }
        return resEntity;
    }
    
    public Integer getOffset(Integer page, Integer size) {
        return null != page && null != size ? (page - 1) * size : null;
    }
}
