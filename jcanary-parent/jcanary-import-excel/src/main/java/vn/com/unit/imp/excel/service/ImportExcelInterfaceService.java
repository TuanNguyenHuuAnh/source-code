package vn.com.unit.imp.excel.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;

import jp.sf.amateras.mirage.SqlManager;
import jp.sf.amateras.mirage.annotation.Column;
import jp.sf.amateras.mirage.annotation.Table;
import jp.sf.amateras.mirage.provider.ConnectionProvider;
import vn.com.unit.core.config.SystemConfig;
import vn.com.unit.imp.excel.annotation.ExcelImportField;
import vn.com.unit.imp.excel.annotation.ExcelImportTable;
import vn.com.unit.imp.excel.constant.CommonConstant;
import vn.com.unit.imp.excel.constant.ImportExcelConstant;
import vn.com.unit.imp.excel.dto.ImportCommonDto;
import vn.com.unit.imp.excel.dto.ImportExcelSearchDto;
import vn.com.unit.imp.excel.dto.ItemColsExcelDto;
import vn.com.unit.imp.excel.enumdef.DataTypeEnum;
import vn.com.unit.imp.excel.utils.CommonImportUtils;
import vn.com.unit.imp.excel.utils.ConstantImportUtils;
import vn.com.unit.imp.excel.utils.ExportExcelUtil;
import vn.com.unit.imp.excel.utils.ImportExcelUtil;
import vn.com.unit.imp.excel.utils.PluploadUtil;

/**
 * ImportExcelInterfaceService
 * 
 * @author TaiTM
 * @date 01-01-1990
 * @description: Service common xử lý việc upload/import file excel
 */
@SuppressWarnings("rawtypes")
public interface ImportExcelInterfaceService<T extends ImportCommonDto> {
    static final Logger logger = LoggerFactory.getLogger("CommonImportService");

    /**
     * countData
     * 
     * @author TaiTM
     * @date 01-01-1990
     * @param sessionKey
     * @description: Lấy ra số lượng dữ liệu ở màn hình List
     */
    public int countData(String sessionKey);

    /**
     * getListData
     * 
     * @author TaiTM
     * @date 01-01-1990
     * @param sessionKey
     * @description: Lấy ra dữ liệu ở màn hình List
     */
    public List<T> getListData(int page, String sessionKey, int sizeOfPage);
    
    /**
     * getListDataExport
     * 
     * @author TaiTM
     * @date 01-01-1990
     * @param sessionKey
     * @description: Lấy ra liệu cho việc Export
     */
    public List<T> getListDataExport(String sessionKey);

    /**
     * countError
     * 
     * @author TaiTM
     * @date 01-01-1990
     * @param sessionKey
     * @description: Lấy ra số lượng dữ liệu lỗi [IS_ERROR = 1]
     */
    public int countError(String sessionKey);

    /**
     * getAllDatas
     * 
     * @author TaiTM
     * @date 01-01-1990
     * @param sessionKey
     * @description: Lấy tất cả dữ liệu 
     *  + sử dụng ở method [downloadTemplateError]: Lấy ra tất cả dữ liệu để ghi vào file excel
     *  + sử dụng ở method [uploadExcelCheckData]: Lấy ra tất cả dữ liệu phục vụ cho method [writeDataFileError]
     */
    public List<T> getAllDatas(String sessionKey);

    /**
     * getImportDto
     * 
     * @author TaiTM
     * @date 01-01-1990
     * 
     * @description: Định nghĩa DTO cho phần Import
     */
    public Class getImportDto();

    /**
     * getImportDto
     * 
     * @author TaiTM
     * @date 01-01-1990
     * 
     * @description: Định nghĩa Entity cho phần Import.
     *  + Entity này phục vụ cho việc lưu dữ liệu từ IMPORT_TABLE sang MAIN_TABLE
     */
    public Class getEntity();

    /**
     * getConnectionProvider
     * 
     * @author TaiTM
     * @date 01-01-1990
     * 
     * @description: Định nghĩa Connection Provider để sử dụng truy vấn database
     */
    public ConnectionProvider getConnectionProvider();

    /**
     * getMessageSource
     * 
     * @author TaiTM
     * @date 01-01-1990
     * 
     * @description: Định nghĩa Message Source
     */
    public MessageSource getMessageSource();

    /**
     * getServletContext
     * 
     * @author TaiTM
     * @date 01-01-1990
     * 
     * @description: Định nghĩa Servlet Context
     */
    public ServletContext getServletContext();

    /**
     * getSystemConfig
     * 
     * @author TaiTM
     * @date 01-01-1990
     * 
     * @description: Định nghĩa System Config Service
     */
    public SystemConfig getSystemConfig();
    
    public SqlManager getSqlManager();

    /**
     * @author TaiTM
     * @throws Exception
     * 
     * @description: upload file excel
     *  1. Đọc file excel được upload lên
     *  2. Thực hiện các validate liên quan đến file
     *      2.1. Kiểm tra file uploiad lên có phải là template được lưu trong hệ thống [isTemplate]
     *      2.2. Sẽ có 2 cách để xử lý: có truyền header và không truyền header
     *      - Header (Title của các cột trên file excel) truyền vào sẽ quy định việc xuất thông báo lỗi khi file có lỗi 
     *      về format data(sai kiểu dữ liệu/ sai định dạng ngày tháng năm/NOT NULL)
     *      - Nếu không truyền header thì các công thông báo lỗi về format data của file sẽ mặc định lấy Header trong file excel.
     *      VD: File excel có các cột: [Agent code] | [Agent name] | [Agent type] và đang có lỗi về NOT NULL ở cột Agent code
     *      - Không truyền header:
     *          ---> Agent code should not be blank.
     *      - Truyền header = [Agent code A] | [Agent name] | [Agent type]:
     *          ---> Agent code A should not be blank.
     *          
     *  importCommon.setFileName(filename): phục vụ cho việc download template error
     */
    @SuppressWarnings({ "resource", "unchecked" })
    public default ConstantImportUtils<T> uploadExcel(T plupload, HttpServletRequest request,
            HttpServletResponse response, String password, Locale locale) throws Exception {

        String filename = "" + System.currentTimeMillis() + plupload.getName();

        ConstantImportUtils<T> importCommon = new ConstantImportUtils<>();

        String path = plupload.getRequest().getSession().getServletContext().getRealPath("/EXCEL-IMPORT-TEMP/");
        File dir = new File(path);
        try {
            PluploadUtil.upload(plupload, dir, filename);

            if (PluploadUtil.isUploadFinish(plupload)) {

                String tempFile = path + "//" + filename;
                File file = new File(tempFile);
                FileInputStream fileInputStream = new FileInputStream(file);

                Workbook workbook = null;
                String ext = FilenameUtils.getExtension(tempFile);
                if (ImportExcelConstant.EXT_EXCEL_97_2003_WORKBOOK.equalsIgnoreCase(ext)) {
                    workbook = new HSSFWorkbook(fileInputStream);
                } else {
                    workbook = new XSSFWorkbook(fileInputStream);
                }

                Sheet sheet = workbook.getSheetAt(0);
                int startRow = plupload.getStartRowData() - 1;
                int lastRow = sheet.getLastRowNum();

                // get row header
                Row rowHeader = sheet.getRow(startRow - 1);

                // check Template
                String templateName = (String) request.getAttribute(ImportExcelConstant.TEMPLATE_NAME);
                try {
                    if (!isTemplate(startRow, sheet, templateName)) {
                        throw new Exception(getMessageSource().getMessage("message.error.not.template", null, locale));
                    }
                } catch (Exception e) {
                    throw new Exception(getMessageSource().getMessage("message.error.not.template", null, locale));
                }

                // create column
                List<ItemColsExcelDto> cols = new ArrayList<>();
                ConstantImportUtils.setListColumnExcel(plupload.getEnumImport(), cols);

                // check Row Empty
                if (startRow > lastRow) {
                    throw new Exception(getMessageSource().getMessage("message.error.row.is.empty", null, locale));
                }

                /*
                 * for (int i = startRow; i < lastRow; i++) { Row currentRow = sheet.getRow(i);
                 * if (currentRow == null) { if (i == startRow) { throw new
                 * Exception(getMessageSource().getMessage("message.error.row.start", null,
                 * locale)); } }
                 * 
                 * 
                 * if (isRowEmpty(currentRow) && lastRow != i) { throw new
                 * Exception(getMessageSource().getMessage("message.error.row.is.empty", null,
                 * locale)); }
                 * 
                 * }
                 */

                List<String> headers = initHeaderTemplate();
                if (CollectionUtils.isNotEmpty(headers)) {
                    importCommon.setDataFileExcelWithErrorMapWithFieldType(sheet, startRow, lastRow + 1, cols,
                            rowHeader, (T) plupload, plupload.getColsValidate(), plupload.getSessionKey(),
                            getMessageSource(), headers, locale);
                } else {
                    importCommon.setDataFileExcelWithErrorMapWithFieldType(sheet, startRow, lastRow + 1, cols,
                            rowHeader, (T) plupload, plupload.getColsValidate(), plupload.getSessionKey(),
                            getMessageSource(), locale);
                }

                importCommon.setPathFileName(tempFile);
                importCommon.setFileName(filename);

                fileInputStream.close();
                if (workbook != null) {
                    workbook.close();
                }
            }
        } catch (Exception e) {
            request.setAttribute("fileName", filename);
            importCommon.setFileName(filename);
            logger.error("####uploadExcel####", e);
            throw new Exception(e.getMessage());
        }

        return importCommon;
    }

    @SuppressWarnings({ "resource", "unchecked" })
    public default ConstantImportUtils<T> uploadAllDataExcel(T plupload, HttpServletRequest request,
            HttpServletResponse response, String password, Locale locale) throws Exception {

        String filename = "" + System.currentTimeMillis() + plupload.getName();

        ConstantImportUtils<T> importCommon = new ConstantImportUtils<>();

        String path = plupload.getRequest().getSession().getServletContext().getRealPath("/EXCEL-IMPORT-TEMP/");
        File dir = new File(path);
        try {
            PluploadUtil.upload(plupload, dir, filename);

            if (PluploadUtil.isUploadFinish(plupload)) {

                String tempFile = path + "//" + filename;
                File file = new File(tempFile);
                FileInputStream fileInputStream = new FileInputStream(file);

                Workbook workbook = null;
                String ext = FilenameUtils.getExtension(tempFile);
                if (ImportExcelConstant.EXT_EXCEL_97_2003_WORKBOOK.equalsIgnoreCase(ext)) {
                    workbook = new HSSFWorkbook(fileInputStream);
                } else {
                    workbook = new XSSFWorkbook(fileInputStream);
                }

                Sheet sheet = workbook.getSheetAt(0);
                int startRow = plupload.getStartRowData() - 1;
                int lastRow = sheet.getLastRowNum();

                // get row header
                Row rowHeader = sheet.getRow(startRow - 1);

                // check Template
                String templateName = (String) request.getAttribute(ImportExcelConstant.TEMPLATE_NAME);
                try {
                    if (!isTemplate(startRow, sheet, templateName)) {
                        throw new Exception(getMessageSource().getMessage("message.error.not.template", null, locale));
                    }
                } catch (Exception e) {
                    throw new Exception(getMessageSource().getMessage("message.error.not.template", null, locale));
                }

                // create column
                List<ItemColsExcelDto> cols = new ArrayList<>();
                ConstantImportUtils.setListColumnExcel(plupload.getEnumImport(), cols);

                // check Row Empty
                if (startRow > lastRow) {
                    throw new Exception(getMessageSource().getMessage("message.error.row.is.empty", null, locale));
                }

                List<String> headers = initHeaderTemplate();
                if (CollectionUtils.isNotEmpty(headers)) {
                    importCommon.setAllDataWithErrorMapWithFieldType(sheet, startRow, lastRow + 1, cols, rowHeader,
                            (T) plupload, plupload.getColsValidate(), plupload.getSessionKey(), getMessageSource(),
                            headers, locale);
                } else {
                    importCommon.setAllDataWithErrorMapWithFieldType(sheet, startRow, lastRow + 1, cols, rowHeader,
                            (T) plupload, plupload.getColsValidate(), plupload.getSessionKey(), getMessageSource(),
                            null, locale);
                }

                importCommon.setPathFileName(tempFile);
                importCommon.setFileName(filename);

                fileInputStream.close();
                if (workbook != null) {
                    workbook.close();
                }
            }
        } catch (Exception e) {
            request.setAttribute("fileName", filename);
            logger.error("####uploadExcel####", e.getMessage());
            throw new Exception(e.getMessage());
        }

        return importCommon;
    }
    
    /**
     * @author TaiTM
     * @throws Exception
     * 
     * @description: Export file excel
     *  1. Thực hiện việc chuyển đổi các properties Error/Warning thành các câu thông báo [parseData]
     *  2. Thực hiện Export excel dự vào common
     *  
     */
    @SuppressWarnings("unchecked")
    public default void exportExcel(List<T> lstData, String templateName, Class enums, Class dtoExport,
            String columsStart, HttpServletRequest req, HttpServletResponse res, Locale locale) throws Exception {
        String template = getServletContext().getRealPath(CommonConstant.REAL_PATH_TEMPLATE_EXCEL) + "/" + templateName;

        parseData(lstData, true, locale);

        String datePattern = getSystemConfig().getConfig(CommonConstant.DATE_PATTERN, null);

        List<ItemColsExcelDto> cols = new ArrayList<>();

        // start fill data to workbook
        ImportExcelUtil.setListColumnExcel(enums, cols);
        ExportExcelUtil exportExcel = new ExportExcelUtil<>();

        Map<String, String> mapColFormat = setMapColFormat();

        Map<String, Object> setMapColDefaultValue = setMapColDefaultValue();
        
        // XSSFWorkbook xssfWorkbook = exportExcel.getXSSFWorkbook(template);
        try (XSSFWorkbook xssfWorkbook = exportExcel.getXSSFWorkbook(template);) {

            Map<String, CellStyle> mapColStyle = setMapColStyle(xssfWorkbook);

            exportExcel.doExportExcelHeaderWithColFormat(xssfWorkbook, 0, locale, lstData, dtoExport, cols, datePattern,
                    columsStart, mapColFormat, mapColStyle, setMapColDefaultValue, null, true, res, templateName, true);
        } catch (Exception e) {
            logger.error("#####exportExcel#####: ", e);
            throw new Exception(e.getMessage());
        }
    }

    /**
     * Specifies the column-related format. key: FIELD mapping of enums value:
     * format of field EX: MAP<"PROCESSDATE", CommonConstant.PERCENT>
     */
    public default Map<String, String> setMapColFormat() {
        Map<String, String> mapColFormat = new HashMap<>();

        if (mapColFormat.isEmpty()) {
            return null;
        }
        return mapColFormat;
    }

    /**
     * Specifies the cell-related format in excel row. key: FIELD mapping of enums
     * value: CellStyle EX: MAP<"CHANNEL", cellStyleLeft>
     */
    public default Map<String, CellStyle> setMapColStyle(XSSFWorkbook xssfWorkbook) {
        Map<String, CellStyle> mapColStyle = new HashMap<>();

        if (mapColStyle.isEmpty()) {
            return null;
        }

        return mapColStyle;
    }

    /**
     * set default value for column. key: FIELD mapping of enums value: value of
     * field EX: MAP<"CHANNEL", "AG">
     */
    public default Map<String, Object> setMapColDefaultValue() {
        Map<String, Object> mapColDefaultValue = new HashMap<>();

        if (mapColDefaultValue.isEmpty()) {
            return null;
        }
        return mapColDefaultValue;
    }

    /**
     * saveListImport
     * 
     * @author TaiTM
     * @param username 
     * @throws Exception
     * 
     * @description: Lưu dữ liệu từ IMPORT_TABLE sang MAIN_TABLE
     */
    @Transactional
    public default void saveListImport(List<T> listDataSave, String sessionKey, Locale locale, String username) throws Exception {
        logger.debug("Handle function save data by entity with SQL");
        if (CollectionUtils.isEmpty(listDataSave)) {
            saveDataToTableMainBySessionKey(sessionKey, null, username);
        } else {
            List<Long> ids = listDataSave.stream().filter(item -> item.getId() != null).map(T::getId)
                    .collect(Collectors.toList());
            saveDataToTableMainBySessionKey(sessionKey, ids, username);
        }
    }

    /**
     * setMapParamTableMain
     * 
     * @author TaiTM
     * @throws Exception
     * 
     * @description: Tạo ra map cấu trúc chứa các cột và các value của cột để lưu vào TABLE_MAIN
     */
    public default Map<String, String> setMapParamTableMain(Map<String, String> mapParam, SimpleDateFormat formatDate,
            T data, Field field, int i, Map<String, String> mapColNames) throws Exception {

        if (mapParam.isEmpty()) {
            mapParam = new HashMap<String, String>();
            mapParam.put(ImportExcelConstant.PARAM_HEADER, ImportExcelConstant.EMPTY_STRING);
            mapParam.put(ImportExcelConstant.PARAM_VALUE, ImportExcelConstant.EMPTY_STRING);
            mapParam.put(ImportExcelConstant.PARAM_SELECT, ImportExcelConstant.EMPTY_STRING);
        }

        String paramHeader = mapParam.get(ImportExcelConstant.PARAM_HEADER);
        String paramValue = mapParam.get(ImportExcelConstant.PARAM_VALUE);
        String paramSelect = mapParam.get(ImportExcelConstant.PARAM_SELECT);

        field.setAccessible(true);

        for (String fieldName : mapColNames.keySet()) {
            if (fieldName.equals(field.getName()) && !ImportExcelConstant.ID.equalsIgnoreCase(fieldName)) {
                String colName = mapColNames.get(fieldName);

                Field entityField = getEntity().getDeclaredField(fieldName);
                entityField.setAccessible(true);
                String typeFields = entityField.getType().getSimpleName();
                typeFields = typeFields.toUpperCase();
                DataTypeEnum dataType = DataTypeEnum.valueOf(typeFields);

                if (StringUtils.isBlank(paramHeader)) {
                    paramHeader = paramHeader.concat(colName);
                    if (!dataType.equals(DataTypeEnum.DATE)) {
                        paramSelect = paramSelect.concat(colName);
                    } else {
                        String str = "(SELECT CASE WHEN ISDATE(PROCESS_DATE) = 0 ISNULL(TRY_PARSE(CAST(" + colName
                                + " AS VARCHAR(255)) AS DATETIME USING 'en-us'), TRY_PARSE(CAST(" + colName
                                + " AS VARCHAR(255)) AS DATETIME USING 'en-gb')))";
                        paramSelect = paramSelect.concat(str);
                    }
                } else {
                    paramHeader = paramHeader.concat(ImportExcelConstant.COMMA).concat(colName);
                    if (!dataType.equals(DataTypeEnum.DATE)) {
                        paramSelect = paramSelect.concat(ImportExcelConstant.COMMA).concat(colName);
                    } else {
                        String str = "(SELECT ISNULL(TRY_PARSE(CAST(" + colName
                                + " AS VARCHAR(255)) AS DATETIME USING 'en-us'), TRY_PARSE(CAST(" + colName
                                + " AS VARCHAR(255)) AS DATETIME USING 'en-gb')))";
                        paramSelect = paramSelect.concat(ImportExcelConstant.COMMA).concat(str);
                    }
                }
            }
        }

        mapParam.put(ImportExcelConstant.PARAM_HEADER, paramHeader);
        mapParam.put(ImportExcelConstant.PARAM_VALUE, paramValue);
        mapParam.put(ImportExcelConstant.PARAM_SELECT, paramSelect);

        return mapParam;
    }

    /**
     * setSqlInsertWithExcelImportField
     * 
     * @author TaiTM
     * @param mapParam
     * @param formatDate
     * @param data
     * @param field
     * @param i
     * @param mapColNames
     * @throws Exception
     * 
     * @description: Tạo ra map cấu trúc chứa các cột và các value của cột để lưu vào database
     */
    @SuppressWarnings("unchecked")
    public default Map<String, String> setSqlInsertWithExcelImportField(Map<String, String> mapParam,
            SimpleDateFormat formatDate, T data, Field field, int i, Map<String, String> mapColNames) throws Exception {

        if (mapParam.isEmpty()) {
            mapParam = new HashMap<String, String>();
            mapParam.put(ImportExcelConstant.PARAM_HEADER, ImportExcelConstant.EMPTY_STRING);
            mapParam.put(ImportExcelConstant.PARAM_VALUE, ImportExcelConstant.EMPTY_STRING);
        }

        String paramHeader = mapParam.get(ImportExcelConstant.PARAM_HEADER);
        String paramValue = mapParam.get(ImportExcelConstant.PARAM_VALUE);

        field.setAccessible(true);

        for (String fieldName : mapColNames.keySet()) {
            if (fieldName.equals(field.getName()) && !ImportExcelConstant.ID.equalsIgnoreCase(fieldName)) {
                String colName = mapColNames.get(fieldName);

                Object val = null;
                if (data != null) {
                    val = field.get(data);
                }

                if (ImportExcelConstant.MESSAGE_ERROR.equalsIgnoreCase(colName)) {
                    if (CollectionUtils.isNotEmpty(data.getListMessageErrors())) {
                        val = CommonImportUtils.getMessageError(data.getListMessageErrors());
                        for (String required : ImportExcelConstant.LIST_REQUIRED) {
                            if (val.toString().contains(required)) {
                                val = val.toString().replace(required, ImportExcelConstant.EMPTY_STRING);
                            }
                        }
                    }
                }

                if (ImportExcelConstant.MESSAGE_WARNING.equalsIgnoreCase(colName)) {
                    if (CollectionUtils.isNotEmpty(data.getListMessageWarnings())) {
                        val = CommonImportUtils.getMessageError(data.getListMessageWarnings());
                        val = val.toString().replace(ImportExcelConstant.REQUIRED, ImportExcelConstant.EMPTY_STRING);
                    }
                }

                if (StringUtils.isBlank(paramHeader)) {
                    paramHeader = paramHeader.concat(colName);
                    if (val == null) {
                        paramValue = paramValue.concat(ImportExcelConstant.NULL_STRING);
                    } else {
                        paramValue = paramValue.concat(ImportExcelConstant.N).concat(ImportExcelConstant.APOSTROPHE)
                                .concat(val.toString()).concat(ImportExcelConstant.APOSTROPHE);
                    }
                } else {
                    paramHeader = paramHeader.concat(ImportExcelConstant.COMMA).concat(colName);
                    if (val == null) {
                        paramValue = paramValue.concat(ImportExcelConstant.COMMA)
                                .concat(ImportExcelConstant.NULL_STRING);
                    } else {
                        paramValue = paramValue.concat(ImportExcelConstant.COMMA).concat(ImportExcelConstant.N)
                                .concat(ImportExcelConstant.APOSTROPHE).concat(val.toString())
                                .concat(ImportExcelConstant.APOSTROPHE);
                    }
                }
            }
        }

        mapParam.put(ImportExcelConstant.PARAM_HEADER, paramHeader);
        mapParam.put(ImportExcelConstant.PARAM_VALUE, paramValue);

        return mapParam;
    }

    /**
     * setSqlInsertWithFieldType
     * 
     * @author TaiTM
     * @param mapParam
     * @param formatDate
     * @param data
     * @param field
     * @param i
     * @param mapColNames
     * @throws Exception
     * 
     * @description: Tạo ra map cấu trúc chứa các cột và các value của cột để lưu vào database
     */
    @SuppressWarnings("unchecked")
    public default Map<String, String> setSqlInsertWithFieldType(Map<String, String> mapParam,
            SimpleDateFormat formatDate, T data, Field field, int i, Map<String, String> mapColNames) throws Exception {

        if (mapParam.isEmpty()) {
            mapParam = new HashMap<String, String>();
            mapParam.put(ImportExcelConstant.PARAM_HEADER, ImportExcelConstant.EMPTY_STRING);
            mapParam.put(ImportExcelConstant.PARAM_VALUE, ImportExcelConstant.EMPTY_STRING);
        }

        String paramHeader = mapParam.get(ImportExcelConstant.PARAM_HEADER);
        String paramValue = mapParam.get(ImportExcelConstant.PARAM_VALUE);

        field.setAccessible(true);
        String typeFields = field.getType().getSimpleName();
        typeFields = typeFields.toUpperCase();
        DataTypeEnum dataType = DataTypeEnum.valueOf(typeFields);

        for (String fieldName : mapColNames.keySet()) {
            if (fieldName.equals(field.getName()) && !ImportExcelConstant.ID.equalsIgnoreCase(fieldName)) {
                String colName = mapColNames.get(fieldName);

                Object val = null;
                if (data != null) {
                    val = field.get(data);
                }

                if (ImportExcelConstant.MESSAGE_ERROR.equalsIgnoreCase(colName)) {
                    if (CollectionUtils.isNotEmpty(data.getListMessageErrors())) {
                        val = CommonImportUtils.getMessageError(data.getListMessageErrors());
                        for (String required : ImportExcelConstant.LIST_REQUIRED) {
                            if (val.toString().contains(required)) {
                                val = val.toString().replace(required, ImportExcelConstant.EMPTY_STRING);
                            }
                        }
                    } else {
                        data.setMessageError(null);
                        val = null;
                    }
                }

                if (ImportExcelConstant.MESSAGE_WARNING.equalsIgnoreCase(colName)) {
                    if (CollectionUtils.isNotEmpty(data.getListMessageWarnings())) {
                        val = CommonImportUtils.getMessageError(data.getListMessageWarnings());
                        val = val.toString().replace(ImportExcelConstant.REQUIRED, ImportExcelConstant.EMPTY_STRING);
                    } else {
                        data.setMessageError(null);
                        val = null;
                    }
                }

                if (StringUtils.isBlank(paramHeader)) {
                    paramHeader = paramHeader.concat(colName);
                    if (val == null) {
                        paramValue = paramValue.concat(ImportExcelConstant.NULL_STRING);
                    } else {
                        if (dataType.equals(DataTypeEnum.DATE)) {
                            paramValue = paramValue.concat(ImportExcelConstant.N).concat(ImportExcelConstant.APOSTROPHE)
                                    .concat(formatDate.format(val)).concat(ImportExcelConstant.APOSTROPHE);
                        } else {
                            paramValue = paramValue.concat(ImportExcelConstant.N).concat(ImportExcelConstant.APOSTROPHE)
                                    .concat(val.toString()).concat(ImportExcelConstant.APOSTROPHE);
                        }
                    }
                } else {
                    paramHeader = paramHeader.concat(ImportExcelConstant.COMMA).concat(colName);
                    if (val == null) {
                        paramValue = paramValue.concat(ImportExcelConstant.COMMA)
                                .concat(ImportExcelConstant.NULL_STRING);
                    } else {
                    	switch (dataType) {
						case DATE:
							 paramValue = paramValue.concat(ImportExcelConstant.COMMA).concat(ImportExcelConstant.N)
                             .concat(ImportExcelConstant.APOSTROPHE).concat(formatDate.format(val))
                             .concat(ImportExcelConstant.APOSTROPHE);
							break;
						case BIGDECIMAL:
							DecimalFormatSymbols symbols = new DecimalFormatSymbols();
                            symbols.setDecimalSeparator('.');
                            String pattern = "#0.0#";
                        	DecimalFormat decimalFormat = new DecimalFormat(pattern,
                                    symbols);
                            decimalFormat.setParseBigDecimal(true);
                            decimalFormat.setMaximumFractionDigits(2);
                            decimalFormat.setMaximumIntegerDigits(100);
                            String StringValue = decimalFormat.format(val);
                            paramValue = paramValue.concat(ImportExcelConstant.COMMA).concat(ImportExcelConstant.N)
                                    .concat(ImportExcelConstant.APOSTROPHE).concat(StringValue)
                                    .concat(ImportExcelConstant.APOSTROPHE);
                            break;
						default:
							 paramValue = paramValue.concat(ImportExcelConstant.COMMA).concat(ImportExcelConstant.N)
                             .concat(ImportExcelConstant.APOSTROPHE).concat(formatString(val.toString()))
                             .concat(ImportExcelConstant.APOSTROPHE);
							break;
						}
                        
                    }
                }
            }
        }

        mapParam.put(ImportExcelConstant.PARAM_HEADER, paramHeader);
        mapParam.put(ImportExcelConstant.PARAM_VALUE, paramValue);

        return mapParam;
    }
    
    public default String formatString(String strIn) {
		String rs;
		if (strIn.contains("'")) {
			rs = strIn.replace("'", "''");
		} else {
			rs = strIn;
		}
		return rs;
    }
    
    /**
     * isRowEmpty
     * 
     * @author TaiTM
     * @param Row
     * @throws Exception
     * 
     * @description: Kiểm tra dòng dữ liệu trong file excel có phải là dòng rỗng
     */
    default boolean isRowEmpty(Row row) {
        for (int c = row.getFirstCellNum(); c < row.getLastCellNum(); c++) {
            Cell cell = row.getCell(c);
            if (cell != null && cell.getCellTypeEnum() != CellType.BLANK) {
                return false;
            }
        }
        return true;
    }

    /**
     * deleteDatasBySessionKey
     * 
     * @author TaiTM
     * @param sessionKey
     * @throws Exception
     * 
     * @description: Xóa dữ liệu dựa vào session key
     */
    @SuppressWarnings("unchecked")
    @Transactional
    public default void deleteDatasBySessionKey(String sessionKey) throws SQLException {
        Connection connection = getConnectionProvider().getConnection();

        ExcelImportTable table = (ExcelImportTable) getImportDto().getDeclaredAnnotation(ExcelImportTable.class);
        String tableName = table.tableName();
        String sql = "DELETE FROM @tableName WHERE SESSION_KEY = ?";
        sql = sql.replace("@tableName", tableName);

        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setString(1, sessionKey);
        ps.addBatch();
        ps.executeBatch();
        ps.close();
    }

    /**
     * saveDataToTableImport
     * 
     * @author TaiTM
     * @param listDataImport
     * @param sessionKey
     * @param locale
     * @throws Exception
     * 
     * @description: Lưu dữ liệu import vào IMPORT_TABLE
     *  + Dựa vào danh sách data import đã qua xử lý thực hiện việc lưu dữ liệu vào IMPORT_TABLE
     *  + Lấy ra TABLE_NAME
     *  + Lấy ra danh sách các COLUMN
     *  + Lấy ra danh sách các value tương ứng các COLUMN
     *  + Tạo ra câu query dựa vào các kiều kiện trên --> lưu vào database
     */
    @SuppressWarnings("unchecked")
    @Transactional
    public default void saveDataToTableImport(List<T> listDataImport, String sessionKey, Locale locale, String username)
            throws Exception {

        Connection connection = getConnectionProvider().getConnection();
        Statement statement = connection.createStatement();

        if (CollectionUtils.isNotEmpty(listDataImport)) {

            // Lấy ra tên table
            ExcelImportTable table = (ExcelImportTable) getImportDto().getDeclaredAnnotation(ExcelImportTable.class);
            String tableName = table.tableName();

            SimpleDateFormat sf = new SimpleDateFormat(ImportExcelConstant.FORMAT_DATE_FULL);

            // lấy ra danh sách các cột sẽ lưu dữ liệu vào
            Map<String, String> mapColNames = getMapColNamesByExcelImportField(getImportDto());

            Iterator<T> iterator = listDataImport.iterator();
            while (iterator.hasNext()) {
                T entity = iterator.next();
                Field[] fields = entity.getClass().getDeclaredFields();

                // lấy ra danh sách các value
                Map<String, String> mapParam = new HashMap<String, String>();

                for (int i = 0; i < fields.length; i++) {
                    // mapParam = setSqlInsertWithExcelImportField(mapParam, sf, entity, fields[i],
                    // i, mapColNames);
                    mapParam = setSqlInsertWithFieldType(mapParam, sf, entity, fields[i], i, mapColNames);
                }

                String paramHeader = mapParam.get(ImportExcelConstant.PARAM_HEADER);
                String paramValue = mapParam.get(ImportExcelConstant.PARAM_VALUE);

                if (entity.isError() || CollectionUtils.isNotEmpty(entity.getListMessageErrors())) {
                    paramHeader = paramHeader.concat(ImportExcelConstant.COMMA).concat(ImportExcelConstant.IS_ERROR)
                            .concat(ImportExcelConstant.COMMA).concat(ImportExcelConstant.MESSAGE_ERROR);
                    paramValue = paramValue.concat(ImportExcelConstant.COMMA).concat(ImportExcelConstant.ACTIVE_STRING)
                            .concat(ImportExcelConstant.COMMA).concat(ImportExcelConstant.APOSTROPHE)
                            .concat(Optional.ofNullable(entity.getMessageError()).orElse("")).concat(ImportExcelConstant.APOSTROPHE);
                } else {
                    paramHeader = paramHeader.concat(ImportExcelConstant.COMMA).concat(ImportExcelConstant.IS_ERROR);
                    paramValue = paramValue.concat(ImportExcelConstant.COMMA)
                            .concat(ImportExcelConstant.INACTIVE_STRING);
                }

                if (!paramHeader.contains(ImportExcelConstant.SESSION_KEY)) {
                    paramHeader = paramHeader.concat(ImportExcelConstant.COMMA).concat(ImportExcelConstant.SESSION_KEY);
                    paramValue = paramValue.concat(ImportExcelConstant.COMMA).concat(ImportExcelConstant.APOSTROPHE)
                            .concat(sessionKey).concat(ImportExcelConstant.APOSTROPHE);
                }

                if (!paramHeader.contains(ImportExcelConstant.COMMA.concat(ImportExcelConstant.STATUS)
                        .concat(ImportExcelConstant.COMMA))) {
                    paramHeader = paramHeader.concat(ImportExcelConstant.COMMA).concat(ImportExcelConstant.STATUS);
                    paramValue = paramValue.concat(ImportExcelConstant.COMMA).concat(ImportExcelConstant.ACTIVE_STRING);
                }

                if (!paramHeader.contains(ImportExcelConstant.CREATED_BY)) {
                    paramHeader = paramHeader.concat(ImportExcelConstant.COMMA).concat(ImportExcelConstant.CREATED_BY);
                    paramValue = createParam(paramValue, username);
                }

                if (!paramHeader.contains(ImportExcelConstant.CREATED_DATE)) {
                    paramHeader = paramHeader.concat(ImportExcelConstant.COMMA)
                            .concat(ImportExcelConstant.CREATED_DATE);
                    paramValue = createParam(paramValue, sf.format(new Date()));
                }

                // tạo ra câu query insert
                String sqlInsert = "INSERT INTO @tableName (@paramHeader) VALUES(@paramValue);";
                sqlInsert = sqlInsert.replace("@tableName", tableName);
                sqlInsert = sqlInsert.replace("@paramHeader", paramHeader);
                sqlInsert = sqlInsert.replace("@paramValue", paramValue);

                statement.addBatch(sqlInsert);
            }

            try {
                // thực hiện execute query
                statement.executeBatch();
                statement.close();
            } catch (Exception e) {
                connection.rollback();
                throw new Exception(e.getMessage());
            }
        }
    }

    /**
     * createParam
     * 
     * @author TaiTM
     * @param paramValue
     * @param value
     * 
     * @description: Tạo ra value cho câu query
     */
    public default String createParam(String paramValue, String value) {
        String result = paramValue.concat(ImportExcelConstant.COMMA).concat(ImportExcelConstant.N)
                .concat(ImportExcelConstant.APOSTROPHE).concat(value).concat(ImportExcelConstant.APOSTROPHE);
        return result;
    }
    
    /**
     * getMapColNames
     * 
     * @author TaiTM
     * @param entity
     * @throws Exception
     * 
     * @description: Tạo ra map chứa các COLUMN dựa vào entity
     *  + sử dụng ở method [saveDataToTableMainBySessionKey]
     */
    public default Map<String, String> getMapColNames(Class entity) {
        Map<String, String> mapColNames = new HashMap<>();

        Field[] entityFields = entity.getDeclaredFields();
        for (Field entityField : entityFields) {
            Column[] cols = entityField.getDeclaredAnnotationsByType(Column.class);
            if (cols != null && cols.length != 0) {
                mapColNames.put(entityField.getName(), cols[0].name().toUpperCase());
            }
        }
        return mapColNames;
    }

    /**
     * getMapColNamesByExcelImportField
     * 
     * @author TaiTM
     * @param entity
     * @throws Exception
     * 
     * @description: Tạo ra map chứa các COLUMN dựa vào entity
     *  + sử dụng ở method [saveDataToTableImport]
     */
    public default Map<String, String> getMapColNamesByExcelImportField(Class entity) {
        Map<String, String> mapColNames = new HashMap<>();

        Field[] entityFields = entity.getDeclaredFields();
        for (Field entityField : entityFields) {
            ExcelImportField[] excelImportField = entityField.getDeclaredAnnotationsByType(ExcelImportField.class);
            if (excelImportField != null && excelImportField.length != 0) {
                mapColNames.put(entityField.getName(), excelImportField[0].colName().toUpperCase());
            }
        }
        return mapColNames;
    }

    /**
     * saveDataToTableMainBySessionKey
     * 
     * @author TaiTM
     * @param sessionKey
     * @param ids
     * @throws Exception
     * 
     * @description: Lưu dữ liệu import vào MAIN_TABLE
     *  + Dựa vào danh sách data import đã qua xử lý thực hiện việc lưu dữ liệu vào MAIN_TABLE
     *  + Nếu ids != sẽ xử lý dựa theo ids
     *  + Lấy ra TABLE_NAME
     *  + Lấy ra danh sách các COLUMN
     *  + Lấy ra danh sách các value tương ứng các COLUMN
     *  + Tạo ra câu query dựa vào các kiều kiện trên --> lưu vào database
     *  + Sau khi lưu dữ liệu thành công vào MAIN_TABLE thì cập nhật lại status = 0 ở IMPORT_TABLE
     */
    @SuppressWarnings("unchecked")
    @Transactional
    public default void saveDataToTableMainBySessionKey(String sessionKey, List<Long> ids, String username)
            throws InstantiationException, IllegalAccessException, Exception {
        logger.debug("Handle function save data by entity with session key");

        Connection connection = getConnectionProvider().getConnection();
        Statement statement = connection.createStatement();

        // Lấy ra TABLE_NAME
        ExcelImportTable table = (ExcelImportTable) getImportDto().getDeclaredAnnotation(ExcelImportTable.class);
        String tableImportName = table.tableName();

        Table tableEntity = (Table) getEntity().getDeclaredAnnotation(Table.class);
        String tableName = "";
        if (tableEntity != null) {
            tableName = tableEntity.name();
        } else {
            tableImportName.replace(ImportExcelConstant._IMPORT, ImportExcelConstant.EMPTY_STRING);
        }

        SimpleDateFormat formatDate = new SimpleDateFormat(ImportExcelConstant.FORMAT_DATE_FULL);

        Field[] importFields = getImportDto().getDeclaredFields();
        Field[] fields = getImportDto().getDeclaredFields();
        for (int i = 0; i < importFields.length; i++) {
            Field f = importFields[i];
            ExcelImportField[] excelImportField = f.getDeclaredAnnotationsByType(ExcelImportField.class);
            if (excelImportField != null && excelImportField.length != 0) {
                fields[i] = f;
            }
        }

        // Lấy ra danh sách các COLUMN
        Map<String, String> mapParam = new HashMap<String, String>();

        Map<String, String> mapColNames = getMapColNames(getEntity());

        for (int i = 0; i < fields.length; i++) {
            mapParam = setMapParamTableMain(mapParam, formatDate, null, fields[i], i, mapColNames);
        }

        String paramHeader = mapParam.get(ImportExcelConstant.PARAM_HEADER);
        String paramSelect = mapParam.get(ImportExcelConstant.PARAM_SELECT);

        String sqlInsert = "INSERT INTO @tableName (@paramHeader) @selectDataTmp";
        String selectDataTmp = "SELECT @paramHeader FROM @tableImportName WHERE SESSION_KEY = " + sessionKey;
        if (CollectionUtils.isNotEmpty(ids)) {
            String idStr = "";
            for (Long id : ids) {
                idStr = idStr.concat(ImportExcelConstant.COMMA).concat(String.valueOf(id));
            }
            selectDataTmp = selectDataTmp.concat(" AND id IN (" + idStr + ")");
        }

        if (!paramHeader.contains(ImportExcelConstant.CREATED_BY)) {
            paramHeader = paramHeader.concat(ImportExcelConstant.COMMA).concat(ImportExcelConstant.CREATED_BY);
            paramSelect = paramSelect.concat(ImportExcelConstant.COMMA).concat(ImportExcelConstant.CREATED_BY);
        }

        if (!paramHeader.contains(ImportExcelConstant.CREATED_DATE)) {
            paramHeader = paramHeader.concat(ImportExcelConstant.COMMA).concat(ImportExcelConstant.CREATED_DATE);
            paramSelect = paramSelect.concat(ImportExcelConstant.COMMA).concat(ImportExcelConstant.CREATED_DATE);
        }

        selectDataTmp = selectDataTmp.replace("@paramHeader", paramSelect).replace("@tableImportName", tableImportName);

        sqlInsert = sqlInsert.replace("@tableName", tableName).replace("@paramHeader", paramHeader)
                .replace("@selectDataTmp", selectDataTmp);

        logger.info("SQL INSERT: " + sqlInsert);

        statement.addBatch(sqlInsert);

        // update status 1 -> 0 of table import
        statement.addBatch("UPDATE " + tableImportName + " SET STATUS = 0 WHERE SESSION_KEY =" + sessionKey);

        try {
            statement.executeBatch();
            statement.close();
        } catch (Exception e) {
            connection.rollback();
            logger.error("####saveDataToTableMainBySessionKey####", e);
            throw new Exception(e.getMessage());
        }
    }

    /**
     * writeDataFileError
     * 
     * @author TaiTM
     * @param fileName
     * @param startRow
     * @param allDatas
     * @param enums
     * @param locale
     * @throws Exception
     * 
     * @description: ghi các dòng lỗi vào file đã import vào hệ thống
     *  1. Thực hiện việc chuyển đổi các properties Error/Warning thành các câu thông báo [parseData]
     *  2. Thực hiện việc ghi các lỗi vào file excel
     *      + Nếu dữ liệu có lỗi:
     *          + Ưu tiên kiểm tra [MessageError]: Nếu khác rỗng thì sẽ thực hiện việc replace các giá trị đặc biệt
     *          + [MessageError] = NULL thì xét đến [ListMessageErrors]: thực hiện việc tách danh sách ra theo từng lỗi
     *          + Ghi lỗi vừa mới xử lý vào cột lỗi tương ứng
     *  3. Thực hiện việc ghi các warning vào file: tương tự ghi lỗi nhưng là cột Warning 
     */
    @SuppressWarnings("unchecked")
    public default void writeDataFileError(String fileName, int startRow, List<T> allDatas, Class enums, Locale locale)
            throws IOException, IllegalArgumentException, IllegalAccessException {
        FileInputStream fileInputStream;
        fileInputStream = new FileInputStream(fileName);

        Workbook workbook = null;
        String ext = FilenameUtils.getExtension(fileName);
        if ((ImportExcelConstant.EXT_EXCEL_97_2003_WORKBOOK).equalsIgnoreCase(ext)) {
            workbook = new HSSFWorkbook(fileInputStream);
        } else {
            workbook = new XSSFWorkbook(fileInputStream);
        }

        Sheet sheet = workbook.getSheetAt(0);

        // int indexRowError = 0;
        CellStyle cellStyleError = workbook.createCellStyle();
        cellStyleError.setAlignment(HorizontalAlignment.LEFT);
        cellStyleError.setVerticalAlignment(VerticalAlignment.CENTER);
        // cellStyleError.setWrapText(true);

        Font fontError = workbook.createFont();
        fontError.setColor(Font.COLOR_RED);
        cellStyleError.setFont(fontError);
        ExportExcelUtil.setAllBorder(cellStyleError);

        CellStyle cellStyleWarning = workbook.createCellStyle();
        cellStyleWarning.setAlignment(HorizontalAlignment.LEFT);
        cellStyleWarning.setVerticalAlignment(VerticalAlignment.CENTER);
        // cellStyleError.setWrapText(true);

        Font fontWarning = workbook.createFont();
        fontWarning.setColor(IndexedColors.ORANGE.getIndex());
        cellStyleWarning.setFont(fontWarning);
        ExportExcelUtil.setAllBorder(cellStyleWarning);

        parseData(allDatas, true, locale);

        int indexMessageError = -1;
        Field[] fields = enums.getDeclaredFields();
        for (int i = 0; i < fields.length; i++) {
            Field f = fields[i];
            f.setAccessible(true);
            if (f.getName().equalsIgnoreCase("MESSAGEERROR")) {
                indexMessageError = i;
                break;
            }
        }

        int indexMessageWarning = -1;
        for (int i = 0; i < fields.length; i++) {
            Field f = fields[i];
            f.setAccessible(true);
            if (f.getName().equalsIgnoreCase("MESSAGEWARNING")) {
                indexMessageWarning = i;
                break;
            }
        }
        if (CollectionUtils.isNotEmpty(allDatas)) {
            for (int i = 0; i < allDatas.size(); i++) {
                T dto = allDatas.get(i);
                if (dto != null) {
                    Row row = sheet.getRow(startRow + i);

                    if (row == null) {
                        row = sheet.createRow(startRow + i);
                    }

                    String valueError = "";
                    String valueWarning = "";
                    if (!dto.isError()) {
                        // do something
                        // sheet.removeRow(row);

                    } else {
                        valueError = dto.getMessageError();

                        if (StringUtils.isNotBlank(valueError)
                                && !ImportExcelConstant.EMPTY_STRING.equals(dto.getMessageError())) {
                            valueError = dto.getMessageError()
                                    .replace(ImportExcelConstant.JAVA_BREAK_LINE, ImportExcelConstant.EMPTY_STRING)
                                    .replace(ImportExcelConstant.JAVA_N_BREAK_LINE,
                                            ImportExcelConstant.EMPTY_STRING);
                        } else {
                            valueError = CommonImportUtils.getMessageError(dto.getListMessageErrors());

                            if (StringUtils.isNotBlank(valueError)) {
                                // tách các lỗi ra theo dấu ;
                                String[] listErrors = valueError.split(ImportExcelConstant.SIGN_SEMICOLON);
                                List<String> errors = Arrays.asList(listErrors);
                                String error = ImportExcelConstant.EMPTY_STRING;

                                for (String mes : errors) {
                                    error = error.concat(doParseMessageErrorForExport(mes, locale));
                                }

                                valueError = error
                                        .replace(ImportExcelConstant.JAVA_BREAK_LINE,
                                                ImportExcelConstant.EMPTY_STRING)
                                        .replace(ImportExcelConstant.JAVA_N_BREAK_LINE,
                                                ImportExcelConstant.EMPTY_STRING);

                                for (String required : ImportExcelConstant.LIST_REQUIRED) {
                                    if (valueError.contains(required)) {
                                        valueError = valueError.replace(required, ImportExcelConstant.EMPTY_STRING);
                                    }
                                }
                            }
                        }
                    }

                    // set value for Cell Message Error
                    if (indexMessageError != -1) {
                        Cell cellMessageError = row.getCell(indexMessageError);
                        if (cellMessageError == null) {
                            cellMessageError = row.createCell(indexMessageError);
                        }
                        cellMessageError.setCellValue(valueError);

                        cellMessageError.getCellStyle();
                        cellMessageError.setCellStyle(cellStyleError);
                    }

                    // set value for Cell Message Warning
                    valueWarning = dto.getMessageWarning();

                    if (StringUtils.isNotBlank(valueWarning)
                            && !ImportExcelConstant.EMPTY_STRING.equals(dto.getMessageWarning())) {
                        if (StringUtils.isNotBlank(dto.getMessageWarning())) {
                            valueWarning = dto.getMessageWarning()
                                    .replace(ImportExcelConstant.JAVA_BREAK_LINE, ImportExcelConstant.EMPTY_STRING)
                                    .replace(ImportExcelConstant.JAVA_N_BREAK_LINE,
                                            ImportExcelConstant.EMPTY_STRING);
                        }
                    } else {
                        valueWarning = CommonImportUtils.getMessageError(dto.getListMessageWarnings());
                        // tách các lỗi ra theo dấu ;
                        if (StringUtils.isNotBlank(valueWarning)) {
                            String[] listWarning = valueWarning.split(ImportExcelConstant.SIGN_SEMICOLON);
                            List<String> warnings = Arrays.asList(listWarning);
                            String warning = ImportExcelConstant.EMPTY_STRING;

                            for (String mes : warnings) {
                                warning = warning.concat(doParseMessageErrorForExport(mes, locale));
                            }

                            valueWarning = warning
                                    .replace(ImportExcelConstant.JAVA_BREAK_LINE, ImportExcelConstant.EMPTY_STRING)
                                    .replace(ImportExcelConstant.JAVA_N_BREAK_LINE,
                                            ImportExcelConstant.EMPTY_STRING);

                            for (String required : ImportExcelConstant.LIST_REQUIRED) {
                                if (valueWarning.contains(required)) {
                                    valueWarning = valueError.replace(required, ImportExcelConstant.EMPTY_STRING);
                                }
                            }
                        }
                    }

                    if (indexMessageWarning != -1) {
                        Cell cellMessageWarning = row.getCell(indexMessageWarning);
                        if (cellMessageWarning == null) {
                            cellMessageWarning = row.createCell(indexMessageWarning);
                        }
                        cellMessageWarning.setCellValue(valueWarning);

                        cellMessageWarning.getCellStyle();
                        cellMessageWarning.setCellStyle(cellStyleWarning);
                    }
                }
            }
        }
        FileOutputStream fileOut = new FileOutputStream(fileName, false);
        workbook.write(fileOut);

        fileInputStream.close();
        fileOut.close();
        if (workbook != null) {
            workbook.close();
        }
    }

    /**
     * validateBusiness
     * 
     * @author TaiTM
     * @param sessionKey
     * @param searchDto
     * @param mapParams
     * @param listDataValidate
     * @param locale
     * @throws Exception
     * 
     * @description: thực việc validate và cập nhật các lỗi liên quan đến các rule bussiness cho dữ liệu
     */
    public boolean validateBusiness(String sessionKey, ImportExcelSearchDto searchDto, Map<String, Object> mapParams,
            List<T> listDataValidate);

    /**
     * parseData
     * 
     * @author TaiTM
     * @param datas
     * @param isExport
     * @param locale
     * @throws Exception
     * 
     * @description: thực việc chuyển các properties code thành các câu thông báo
     *  + isExport = true: sẽ áp dụng cho file import
     *  + isExport = false: sẽ áp dụng cho màn hình list
     */
    @SuppressWarnings("unchecked")
    default void parseData(List<T> datas, boolean isExport, Locale locale)
            throws IllegalArgumentException, IllegalAccessException {
        if (CollectionUtils.isNotEmpty(datas)) {
            for (T data : datas) {
                if (data.getIsError()) {
                    String messageError = ImportExcelConstant.EMPTY_STRING;
                    if (CollectionUtils.isNotEmpty(data.getListMessageErrors())) {
                        List<String> lstMes = data.getListMessageErrors();
                        for (String mes : lstMes) {
                            String[] listErrors = mes.split(ImportExcelConstant.SIGN_SEMICOLON);
                            for (String error : listErrors) {
                                if (isExport) {
                                    messageError = messageError.concat(doParseMessageErrorForExport(error, locale));
                                } else {
                                    messageError = messageError.concat(doParseMessageError(error, locale));
                                }
                            }
                        }
                    } else if (StringUtils.isNotBlank(data.getMessageError())) {
                        // tách các lỗi ra theo dấu ;
                        String[] listErrors = data.getMessageError().split(ImportExcelConstant.SIGN_SEMICOLON);
                        for (String error : listErrors) {
                            if (isExport) {
                                messageError = messageError.concat(doParseMessageErrorForExport(error, locale));
                            } else {
                                messageError = messageError.concat(doParseMessageError(error, locale));
                            }
                        }
                    }
                    data.setMessageError(messageError);
                }

                if (CollectionUtils.isNotEmpty(data.getListMessageWarnings())) {
                    List<String> lstMes = data.getListMessageWarnings();
                    String messageWarning = ImportExcelConstant.EMPTY_STRING;
                    for (String mes : lstMes) {
                        String[] listErrors = mes.split(ImportExcelConstant.SIGN_SEMICOLON);
                        for (String error : listErrors) {
                            if (isExport) {
                                messageWarning = messageWarning.concat(doParseMessageErrorForExport(error, locale));
                            } else {
                                messageWarning = messageWarning.concat(doParseMessageError(error, locale));
                            }
                        }
                    }
                    data.setMessageWarning(messageWarning);
                } else if (StringUtils.isNotBlank(data.getMessageWarning())) {
                    // tách các lỗi ra theo dấu ;
                    String[] listWarning = data.getMessageWarning().split(ImportExcelConstant.SIGN_SEMICOLON);
                    String messageWarning = ImportExcelConstant.EMPTY_STRING;
                    for (String warning : listWarning) {
                        if (isExport) {
                            messageWarning = messageWarning.concat(doParseMessageErrorForExport(warning, locale));
                        } else {
                            messageWarning = messageWarning.concat(doParseMessageError(warning, locale));
                        }
                    }
                    data.setMessageWarning(messageWarning);
                }
            }
        }
    }

    /**
     * doParseMessageError
     * 
     * @author TaiTM
     * @param error
     * @param locale
     * @throws Exception
     * 
     * @description: method thực hiện của [parseData]
     */
    default String doParseMessageError(String error, Locale locale) {
        StringBuilder results = new StringBuilder(ImportExcelConstant.EMPTY_STRING);

        String[] val = error.split(ImportExcelConstant.SIGN_DASH);
        String messageCode = val[0];

        String[] messageParams = null;
        if (val.length > 1) {
            messageParams = val[1].split(ImportExcelConstant.COMMA);
            for (int i = 0; i < messageParams.length; i++) {
                String param = messageParams[i]
                        .replace(ImportExcelConstant.JAVA_BREAK_LINE, ImportExcelConstant.EMPTY_STRING)
                        .replace(ImportExcelConstant.JAVA_N_BREAK_LINE, ImportExcelConstant.EMPTY_STRING);
                try {
                    messageParams[i] = getMessageSource().getMessage(param, null, locale);
                } catch (Exception e) {
                    messageParams[i] = param;
                }
            }
        }

        String messageError = getMessageSource().getMessage(messageCode.trim(), messageParams, locale);
        if (StringUtils.isNotBlank(messageError)) {
            results.append(messageError);
            results.append(" "+ImportExcelConstant.HTML_BREAK_LINE);
            results.append(" "+ImportExcelConstant.JAVA_BREAK_LINE);
        }

        return results.toString();
    }

    /**
     * doParseMessageErrorForExport
     * 
     * @author TaiTM
     * @param error
     * @param locale
     * @throws Exception
     * 
     * @description: method thực hiện của [parseData]
     */
    default String doParseMessageErrorForExport(String error, Locale locale) {
        StringBuilder results = new StringBuilder(ImportExcelConstant.EMPTY_STRING);

        String[] val = error.split(ImportExcelConstant.SIGN_DASH);
        String messageCode = val[0];

        List<String> headers = initHeaderTemplate();

        String[] messageParams = null;
        if (val.length > 1) {
            messageParams = val[1].split(ImportExcelConstant.COMMA);
            for (int i = 0; i < messageParams.length; i++) {
                String paramMes = messageParams[i];

                if (CollectionUtils.isNotEmpty(headers) && headers.contains(paramMes)) {
                    paramMes = getMessageSource().getMessage(paramMes, null, locale);
                }

                String param = paramMes.replace(ImportExcelConstant.JAVA_BREAK_LINE, ImportExcelConstant.EMPTY_STRING)
                        .replace(ImportExcelConstant.JAVA_N_BREAK_LINE, ImportExcelConstant.EMPTY_STRING);
                messageParams[i] = param;
            }
        }

        String messageError = getMessageSource().getMessage(messageCode.trim(), messageParams, locale);
        if (StringUtils.isNotBlank(messageError)) {
            results.append(messageError);
            results.append(ImportExcelConstant.SEMICOLON_SPALCE);
            results.append(ImportExcelConstant.JAVA_BREAK_LINE);
        }

        return results.toString();
    }

    /**
     * isTemplate
     * 
     * @author TaiTM
     * @param startRow
     * @param sheet
     * @param templateName
     * @return
     * @throws Exception
     * 
     * @description: Kiểm tra file import vào có phải là file được lưu trên hệ thống
     */
    default boolean isTemplate(int startRow, Sheet sheet, String templateName) throws Exception {
        try {
            boolean results = true;

            String tempFile = getServletContext().getRealPath(CommonConstant.REAL_PATH_TEMPLATE_EXCEL) + "/"
                    + templateName;
            File file = new File(tempFile);
            FileInputStream fileInputStream = new FileInputStream(file);
            XSSFWorkbook xssfWorkbook = new XSSFWorkbook(fileInputStream);
            XSSFSheet sheetTemp = xssfWorkbook.getSheetAt(0);

            // check title
            results = checkTitle(startRow, sheet, sheetTemp);
            if (!results) {
                xssfWorkbook.close();
                return results;
            }

            // check header
            Map<Integer, String> mapHeader1 = getRowHeader(startRow, sheetTemp);
            Map<Integer, String> mapHeader2 = getRowHeader(startRow, sheet);

            for (Map.Entry<Integer, String> header : mapHeader1.entrySet()) {
                String header1 = Optional.ofNullable(header.getValue()).orElse("").trim();
                String header2 = Optional.ofNullable(mapHeader2.get(header.getKey())).orElse("").trim();
                if (!StringUtils.equalsIgnoreCase(header2, header1)) {
                    xssfWorkbook.close();
                    results = false;
                }
            }

            xssfWorkbook.close();
            return results;
        } catch (Exception e) {
            logger.error("####isTemplate####", e);
            throw new Exception(e.getMessage());
        }
    }

    // return true if want to custom value compare
    public default boolean compareHeader(String headerExport, String headerImport, int row, int col) {
    	return false;
    }

    /**
     * checkTitle
     * 
     * @author TaiTM
     * @param startRow
     * @param sheet
     * @param sheetTemp
     * @return
     * @throws Exception
     * 
     * @description: Kiểm tra title của file import vào so với title của file trên hệ thống
     */
    public default boolean checkTitle(int startRow, Sheet sheet, Sheet sheetTemp) {
        boolean results = true;

        for (int i = 0; i < startRow - 1; i++) {
            Row header1 = sheet.getRow(i);
            Row header2 = sheetTemp.getRow(i);

            if (header1 == null && header2 == null) {
                continue;
            }

            if (header1 == null && header2 != null || header1 != null && header2 == null) {
                // results = false;
                return false; // return về luôn chứ step tiếp theo chết
            }

            int maxlengthHeader = header1.getLastCellNum();
            if (header2 != null && header2.getLastCellNum() > maxlengthHeader) {
                maxlengthHeader = header2.getLastCellNum();
            }

            String cellValue1 = "";
            String cellValue2 = "";

            for (int j = 0; j < maxlengthHeader; j++) {
                Cell cell1 = header1.getCell(j);
                Cell cell2 = header2.getCell(j);

                if (cell1 == null && cell2 == null) {
                    continue;
                }

                if (cell1 == null && cell2 != null) {
                    cellValue1 = cellValue1 + "";
                    if (StringUtils.isEmpty(cell2.getStringCellValue())) {
                        cellValue2 = cellValue2 + "";
                    } else {
                        cellValue2 = cellValue2 + cell2.getStringCellValue();
                    }

                    if (StringUtils.isNotEmpty(cell2.getStringCellValue().toString())) {
                        results = false;
                        break;
                    }
                }

                if (cell1 != null && cell2 == null) {
                    if (StringUtils.isEmpty(cell1.getStringCellValue())) {
                        cellValue1 = cellValue1 + "";
                    } else {
                        cellValue1 = cellValue1 + cell1.getStringCellValue();
                    }

                    cellValue2 = cellValue2.concat("");

                    if (StringUtils.isNotEmpty(cell1.getStringCellValue())) {
                        results = false;
                        break;
                    }
                }

                if (cell1 != null && cell2 != null) {
                    cellValue1 = cellValue1
                            .concat(StringUtils.isEmpty(cell1.getStringCellValue()) ? "" : cell1.getStringCellValue());
                    cellValue2 = cellValue2
                            .concat(StringUtils.isEmpty(cell2.getStringCellValue()) ? "" : cell2.getStringCellValue());
                }

                if (!compareHeader(cellValue1.trim(), cellValue2.trim(), i, j) && !cellValue1.trim().equals(cellValue2.trim())) {
                    results = false;
                    break;
                }
            }
        }

        return results;
    }

    /**
     * @author TaiTM
     * @param startRow
     * @param sheet
     * @return
     */
    public default Map<Integer, String> getRowHeader(int startRow, Sheet sheet) {
        Map<Integer, String> result = new HashMap<Integer, String>();
        int indexHeader2 = startRow - 2;
        int indexHeader1 = startRow - 1;

        Row header1 = sheet.getRow(indexHeader1);
        Row header2 = sheet.getRow(indexHeader2);
        int maxlengthHeader = header1.getLastCellNum();
        if (header2 != null && header2.getLastCellNum() > maxlengthHeader)
            maxlengthHeader = header2.getLastCellNum();
        for (int i = 0; i < maxlengthHeader; i++) {
            Cell cell1 = header1.getCell(i);
            Cell cell2 = null;
            if (header2 != null) {
                cell2 = header2.getCell(i);
            }
            String cellValue = "";

            if (cell1 != null)
                cellValue = cell1.getStringCellValue();

            if ((cell1 == null || StringUtils.isEmpty(cell1.getStringCellValue())) && cell2 != null) {
                cellValue = cell2.getStringCellValue();
            }

            if (StringUtils.isNotEmpty(cellValue)) // chỉ tính những cột header nào có giá trị, tránh trường hợp lấy cột
                                                   // empty của template mẫu.
                result.put(i, cellValue);
        }

        return result;
    }

    /**
     * @description: Khởi tạo danh sách header để phục vụ cho việc xuất thông báo
     *               lỗi khi validate format data. Giá trị của danh sách là các
     *               mes_code được định nghĩa trong messages.properties
     * @author TaiTM
     * @param startRow
     * @param sheet
     * @return List<String> = [mesCode1, mesCode2]
     */
    public default List<String> initHeaderTemplate() {
        List<String> results = new ArrayList<String>();
        return results;
    }
    
    @SuppressWarnings("unchecked")
    public default ResponseEntity exportExcelRestApi(List<T> lstData, String templateName, Class enums, Class dtoExport,
            String columsStart, HttpServletRequest req, HttpServletResponse res, Locale locale) throws Exception {
        ResponseEntity resEntity = null;

        parseData(lstData, true, locale);

        String datePattern = getSystemConfig().getConfig(CommonConstant.DATE_PATTERN, null);

        List<ItemColsExcelDto> cols = new ArrayList<>();

        // start fill data to workbook
        ImportExcelUtil.setListColumnExcel(enums, cols);
        ExportExcelUtil exportExcel = new ExportExcelUtil<>();

        Map<String, String> mapColFormat = setMapColFormat();

        Map<String, Object> setMapColDefaultValue = setMapColDefaultValue();
        String templatePath = getServletContext().getRealPath(CommonConstant.REAL_PATH_TEMPLATE_EXCEL + "/" + templateName);
        try (XSSFWorkbook xssfWorkbook = exportExcel.getXSSFWorkbook(templatePath);) {

            Map<String, CellStyle> mapColStyle = setMapColStyle(xssfWorkbook);

            resEntity = exportExcel.doExportExcelHeaderWithColFormatRest(xssfWorkbook, 0, locale, lstData, dtoExport, cols, datePattern,
                    columsStart, mapColFormat, mapColStyle, setMapColDefaultValue, null, true, templateName, true);
        } catch (Exception e) {
            logger.error("#####exportExcel#####: ", e);
            throw new Exception(e.getMessage());
        }
        return resEntity;
    }
}
