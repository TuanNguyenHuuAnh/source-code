/*******************************************************************************
 * Class        ：ConstantImportUtils<T>
 * Created date ：2020/05/31
 * Lasted date  ：2020/05/31
 * Author       ：taitm
 * Change log   ：2020/05/31：01-00 taitm create a new
 ******************************************************************************/
package vn.com.unit.imp.excel.utils;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.text.DateFormat;
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
import java.util.regex.Pattern;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;

import vn.com.unit.imp.excel.annotation.ExcelImportField;
import vn.com.unit.imp.excel.constant.ImportExcelConstant;
import vn.com.unit.imp.excel.dto.ImportCommonDto;
import vn.com.unit.imp.excel.dto.ItemColsExcelDto;

/**
 * ConstantImport<T>
 * 
 * @version 01-00
 * @since 01-00
 * @author taitm
 */
@SuppressWarnings("rawtypes")
public class ConstantImportUtils<T extends ImportCommonDto> {

    private static final Logger logger = LoggerFactory.getLogger(ConstantImportUtils.class);
    
    private static final String FORMAT_DATE_DEFAULT = "dd/MM/yyyy";
    
    private static final Integer IMPORT_TYPE_NOT_CHECK_DATA = 0;
    private static final Integer IMPORT_TYPE_CHECK_DATA = 1;

    private enum DataType {
        LONG, DOUBLE, INTEGER, STRING, DATE, INT, BIGDECIMAL, FLOAT;
    }

    /** error list */
    private Map<String, List<Integer>> rowErrorMap = new HashMap<>();

    private String fileName;

    private String pathFileName;

    /** List<T> */
    private List<T> data;

    /** List<T> */
    private List<T> dataError;

    /** List<T> */
    private List<T> dataSuccess;

    /**
     * @return the dataSuccess
     * @author taitm
     * @date Jun 1, 2020
     */
    public List<T> getDataSuccess() {
        return dataSuccess;
    }

    /**
     * @param dataSuccess the dataSuccess to set
     * @author taitm
     * @date Jun 1, 2020
     */
    public void setDataSuccess(List<T> dataSuccess) {
        this.dataSuccess = dataSuccess;
    }

    /**
     * Get data
     * 
     * @return List<T>
     * @author taitm
     */
    public List<T> getData() {
        return data;
    }

    /**
     * Set data
     * 
     * @param data type List<T>
     * @return
     * @author taitm
     */
    public void setData(List<T> data) {
        this.data = data;
    }

    /**
     * @return the dataError
     * @author taitm
     * @date May 30, 2020
     */
    public List<T> getDataError() {
        return dataError;
    }

    /**
     * @param dataError the dataError to set
     * @author taitm
     * @date May 30, 2020
     */
    public void setDataError(List<T> dataError) {
        this.dataError = dataError;
    }
    
    /**
     * setListColumnExcel
     *
     * @param enumType
     * @param cols
     * @author taitm
     */
    public static <E extends Enum<E>> void setListColumnExcel(Class<E> enumType, List<ItemColsExcelDto> cols) {
        // loop enum
        for (E en : enumType.getEnumConstants()) {
            ItemColsExcelDto itemCol = new ItemColsExcelDto();
            itemCol.setColName(en.name());
            itemCol.setColIndex(Integer.parseInt(en.toString()));
            cols.add(itemCol);
        }
    }
    
    /**
     * map cols-type
     *
     * @param rows
     * @param cols
     * @param rowHeader
     * @param obj
     * @param formatColInput -> (key,value) -> upper case, the map used to format
     *                       column Ex: agent code -> number, rate -> percent
     * @author taitm
     * @throws Exception
     */
    @SuppressWarnings({ "unchecked" })
    public void setDataFileExcelWithErrorMapWithFieldType(Sheet sheet, int startRow, int lastRow,
            List<ItemColsExcelDto> cols, Row rowHeader, T objDefault, Map<String, List<String>> formatColInputs,
            String sessionKey, MessageSource msg, Locale locale) throws Exception {
        try {
            List<T> list = new ArrayList<T>();
            List<T> listError = new ArrayList<T>();
            List<T> listSuccess = new ArrayList<T>();

            Map<String, Field> mapFields = new HashMap<String, Field>();
            // get list fields of class
            Class<?> cls = objDefault.getClass();
            Field[] fields = cls.getDeclaredFields();
            
            
            // PhatLT : lấy field từ class cha nếu có 
            Field [] fieldSuperClass = null;
            // mọi class đều extend từ Object, nên nếu không có class "cha" thì getSuperclass sẽ lấy được Object class 
            if(cls.getSuperclass()!= null && !StringUtils.equalsIgnoreCase("Object", cls.getSuperclass().getSimpleName()))
            	fieldSuperClass = cls.getSuperclass().getDeclaredFields();
            List<Field> lstField = new ArrayList<Field>();
            lstField.addAll(Arrays.asList(fields));
            if(fieldSuperClass != null && fieldSuperClass.length > 0)
            	lstField.addAll(Arrays.asList(fieldSuperClass));
            
            	
            for (Field f : lstField) {
                mapFields.put(f.getName().toUpperCase(), f);
            }

            // check error
            Map<String, List<Integer>> errorList = new HashMap<>();

            short lastCellNum = (short) (rowHeader != null ? (rowHeader.getLastCellNum() - 1) : 0);

            Iterator<ItemColsExcelDto> itemColsExcelIterator = cols.iterator();
            while (itemColsExcelIterator.hasNext()) {
                ItemColsExcelDto col = itemColsExcelIterator.next();
                // item cols excel not in rowheader
                if (col.getColIndex() > lastCellNum) {
                    itemColsExcelIterator.remove();
                    continue;
                }

                String fieldName = rowHeader.getCell(col.getColIndex()).toString();
                errorList.put(fieldName, new ArrayList<>());
            }

            // cell name
            String cellName = StringUtils.EMPTY;

            Integer indexData = 0;
            // loop rows
            for (int i = startRow; i < lastRow; i++) {
                Row currentRow = sheet.getRow(i);
                
                indexData++;
                T objAdd = (T) objDefault.getClass().newInstance();

                // rowEmptyFlag
                boolean rowEmptyFlag = true;
                List<String> messageError = new ArrayList<>();

                // currentRow is not null and LastCellNum > 0
                if (currentRow != null && currentRow.getLastCellNum() > 0) {
                    // set value of object
                    for (ItemColsExcelDto col : cols) {
                        try {
                            Cell currentCell = currentRow.getCell(col.getColIndex());
                            // set value of fields
                            Field f = mapFields.get(col.getColName().toUpperCase().trim());

                            if (f == null) {
                                String[] args = new String[1];
                                args[0] = col.getColName().toUpperCase().trim();
                                String mes = msg.getMessage("message.error.cannot.find.field", args, locale);
                                throw new NullPointerException(mes);
                            }

                            f.setAccessible(true);
                            String typeFields = f.getType().getSimpleName();
                            typeFields = typeFields.toUpperCase();
                            DataType dataType = DataType.valueOf(typeFields);
                            
                            // set cell name
                            cellName = rowHeader.getCell(col.getColIndex()).toString();

                            // currentCell is not null, not blank
                            if (currentCell != null && currentCell.getCellTypeEnum() != CellType.BLANK
                                    && StringUtils.isNotBlank(currentCell.toString())) {
                            	rowEmptyFlag = false;
                                String valueOfString = ImportExcelConstant.EMPTY_STRING;
                                String valueOfNumber = ImportExcelConstant.EMPTY_STRING;
                                Date valueOfDate = null;
                                logger.info("CELL TYPE " + currentCell.getCellTypeEnum());

                                // check type cell
                                switch (currentCell.getCellTypeEnum()) {
                                case BOOLEAN:
                                	Boolean valueBoolean = currentCell.getBooleanCellValue();
                                	switch (dataType) {
                                    case STRING: 
                                    	valueOfString = valueBoolean ? ImportExcelConstant.TRUE : ImportExcelConstant.FALSE;
                                    	break;
                                    default:
                                        break;
                                	}
                                    break;
                                case STRING:
                                    valueOfString = currentCell.getStringCellValue();
                                    break;
                                case NUMERIC:
                                    if (DateUtil.isCellDateFormatted(currentCell)) {
                                        valueOfDate = currentCell.getDateCellValue();
                                    } else {
                                        valueOfNumber = String.format("%f", (double) Double
                                                .parseDouble(String.valueOf(currentCell.getNumericCellValue())));
                                        
                                        if (valueOfNumber.contains(",")) {
                                        	valueOfNumber = valueOfNumber.replace(",", ".");
                                        }
                                    }
                                    break;
                                case FORMULA:
                                    switch (currentCell.getCachedFormulaResultTypeEnum()) {
                                    case NUMERIC:
                                        valueOfNumber = currentCell.getNumericCellValue()
                                                + ImportExcelConstant.EMPTY_STRING;
                                        break;
                                    case STRING:
                                        valueOfString = currentCell.getRichStringCellValue()
                                                + ImportExcelConstant.EMPTY_STRING;
                                        break;
                                    default:
                                        break;
                                    }
                                    break;
                                default:
                                    break;
                                }

                                List<String> lstColType = new ArrayList<>();

                                // set value in object
                                if (formatColInputs != null && !formatColInputs.isEmpty()) {
                                    lstColType = formatColInputs.get(col.getColName()) == null ? new ArrayList<>()
                                            : formatColInputs.get(col.getColName());

                                    if (CollectionUtils.isNotEmpty(lstColType)) {
                                        if (lstColType.contains(ImportExcelConstant.STRING)) {
                                            DataFormatter formatter = new DataFormatter();
                                            valueOfString = formatter.formatCellValue(currentCell);
                                        } else if (lstColType.contains(ImportExcelConstant.NUMBER)) {
                                            valueOfNumber = String.valueOf((int) Double
                                                    .parseDouble(String.valueOf(currentCell.getNumericCellValue())));
                                        } else if (lstColType.contains(ImportExcelConstant.PERCENT)) {
                                            valueOfString = String.valueOf(currentCell.getNumericCellValue() * 100);
                                        } else if (lstColType.contains(ImportExcelConstant.DATE)) {
                                            valueOfString = currentCell.toString();
                                        }
                                    }
                                }
                                
                                if (valueOfDate != null) {
                                    valueOfString = valueOfDate.toString();
                                }else {
                                    valueOfString = valueOfString.trim();
                                }
                                
                                if (lstColType.contains(ImportExcelConstant.NOT_NULL)
                                        && StringUtils.isBlank(valueOfString) && StringUtils.isBlank(valueOfNumber)) {
                                    // set error
                                    String message = ImportExcelConstant.ERROR_EMPTY.concat(ImportExcelConstant.SIGN_DASH).concat(cellName);
                                    
                                    setListMessageError(objAdd, messageError, message, cellName, errorList, indexData,
                                            currentRow.getRowNum());
                                } else {
                                    switch (dataType) {
                                    case LONG:
                                        Double numberLong = Double.parseDouble(valueOfNumber);
                                        if (numberLong % 1 != 0) {
                                            // set error
                                            String message = ImportExcelConstant.ERROR_INTEGER_TYPE.concat(ImportExcelConstant.SIGN_DASH).concat(cellName);
                                            
                                            setListMessageError(objAdd, messageError, message, cellName, errorList,
                                                    indexData, currentRow.getRowNum());
                                        } else {
                                            valueOfNumber = String.valueOf(numberLong.longValue());

                                            Long valueOfLong = valueOfNumber.length() == 0 ? null
                                                    : Long.parseLong(valueOfNumber);
                                            if (CollectionUtils.isNotEmpty(lstColType)) {
                                                for (String colType : lstColType) {
                                                    if (!StringUtils.isBlank(colType)
                                                            && !ImportExcelConstant.NOT_NULL.equalsIgnoreCase(colType)) {
                                                        try {
                                                            String[] types = colType.split("=");
                                                            if (!ImportExcelConstant.STRING.equalsIgnoreCase(types[0])) {
                                                                if (ImportExcelConstant.MAX_LENGTH.equalsIgnoreCase(types[0])) {
                                                                    int length = Integer.parseInt(types[1]);
                                                                    if (valueOfNumber.length() > length) {
                                                                        // set error
                                                                        String message = ImportExcelConstant.ERROR_MAX_LENGTH
                                                                                .concat(ImportExcelConstant.SIGN_DASH)
                                                                                .concat(cellName)
                                                                                .concat(ImportExcelConstant.COMMA)
                                                                                .concat(String.valueOf(length));

                                                                        setListMessageError(objAdd, messageError, message,
                                                                                cellName, errorList, indexData,
                                                                                currentRow.getRowNum());
                                                                    } else {
                                                                        if (StringUtils.isNotBlank(valueOfNumber)) {
                                                                        	f.set(objAdd, valueOfLong);
                                                                        } else {
                                                                            f.set(objAdd, ImportExcelConstant.EMPTY_STRING);
                                                                        }
                                                                    }
                                                                } else if (ImportExcelConstant.MIN_LENGTH.equalsIgnoreCase(types[0])) {
                                                                    int length = Integer.parseInt(types[1]);
                                                                    if (valueOfNumber.length() < length) {
                                                                        // set error
                                                                        String message = ImportExcelConstant.ERROR_MIN_LENGTH
                                                                                .concat(ImportExcelConstant.SIGN_DASH)
                                                                                .concat(cellName)
                                                                                .concat(ImportExcelConstant.COMMA)
                                                                                .concat(String.valueOf(length));

                                                                        setListMessageError(objAdd, messageError, message,
                                                                                cellName, errorList, indexData,
                                                                                currentRow.getRowNum());
                                                                    } else {
                                                                        if (StringUtils.isNotBlank(valueOfNumber)) {
                                                                        	f.set(objAdd, valueOfLong);
                                                                        } else {
                                                                            f.set(objAdd, ImportExcelConstant.EMPTY_STRING);
                                                                        }
                                                                    }
                                                                } else if (ImportExcelConstant.FORMAT.equalsIgnoreCase(types[0])) {
                                                                    // use a better name, though
                                                                    Pattern p = Pattern.compile(types[1]);
                                                                    if (!p.matcher(valueOfNumber).matches()) {
                                                                        // set error
                                                                        String message = ImportExcelConstant.ERROR_FORMAT_TYPE
                                                                                .concat(ImportExcelConstant.SIGN_DASH)
                                                                                .concat(cellName)
                                                                                .concat(ImportExcelConstant.COMMA)
                                                                                .concat(types[1]);

                                                                        setListMessageError(objAdd, messageError, message,
                                                                                cellName, errorList, indexData,
                                                                                currentRow.getRowNum());
                                                                    } else {
                                                                        if (StringUtils.isNotBlank(valueOfNumber)) {
                                                                        	f.set(objAdd, valueOfLong);
                                                                        } else {
                                                                            f.set(objAdd, ImportExcelConstant.EMPTY_STRING);
                                                                        }
                                                                    }
                                                                }
                                                            }
                                                        } catch (Exception e) {
                                                            // set error
                                                            objAdd.setIsError(true);

                                                            // set error
                                                            String message = ImportExcelConstant.ERROR_FORMAT
                                                                    .concat(ImportExcelConstant.SIGN_DASH).concat(cellName);

                                                            setListMessageError(objAdd, messageError, message, cellName,
                                                                    errorList, indexData, currentRow.getRowNum());
                                                        }
                                                    } else if (StringUtils.isBlank(colType)) {
                                                    	f.set(objAdd, valueOfLong);
                                                    } else {
                                                    	f.set(objAdd, valueOfLong);
                                                    }
                                                }
                                            } else {
                                            	f.set(objAdd, valueOfLong);
                                            }
                                            
                                        }
                                        break;
                                    case DOUBLE:
                                        Double valueOfDouble = currentCell.getNumericCellValue();
                                        //f.set(objAdd, valueOfDouble);
                                        if (CollectionUtils.isNotEmpty(lstColType)) {
                                            for (String colType : lstColType) {
                                                if (!StringUtils.isBlank(colType)
                                                        && !ImportExcelConstant.NOT_NULL.equalsIgnoreCase(colType)) {
                                                    try {
                                                        String[] types = colType.split("=");
                                                        if (!ImportExcelConstant.STRING.equalsIgnoreCase(types[0])) {
                                                            if (ImportExcelConstant.MAX_VALUE.equalsIgnoreCase(types[0])) {
                                                            	int valueCompare = Integer.parseInt(types[1]);
                                                                if (valueOfDouble > valueCompare) {
                                                                	if(lstColType.contains(ImportExcelConstant.PERCENT))
                                                                		valueCompare *= 100;
                                                                    // set error
                                                                    String message = ImportExcelConstant.ERROR_MAX_VALUE
                                                                            .concat(ImportExcelConstant.SIGN_DASH)
                                                                            .concat(cellName)
                                                                            .concat(ImportExcelConstant.COMMA)
                                                                            .concat(String.valueOf(valueCompare));

                                                                    setListMessageError(objAdd, messageError, message,
                                                                            cellName, errorList, indexData,
                                                                            currentRow.getRowNum());
                                                                } else {
                                                                    if (StringUtils.isNotBlank(valueOfNumber)) {
                                                                    	f.set(objAdd, valueOfDouble);
                                                                    } else {
                                                                        f.set(objAdd, ImportExcelConstant.EMPTY_STRING);
                                                                    }
                                                                }
                                                            } else if (ImportExcelConstant.MIN_VALUE.equalsIgnoreCase(types[0])) {
                                                            	int valueCompare = Integer.parseInt(types[1]);
                                                                if (valueOfDouble < valueCompare) {
                                                                	if(lstColType.contains(ImportExcelConstant.PERCENT))
                                                                		valueCompare *= 100;
                                                                    // set error
                                                                    String message = ImportExcelConstant.ERROR_MIN_VALUE
                                                                            .concat(ImportExcelConstant.SIGN_DASH)
                                                                            .concat(cellName)
                                                                            .concat(ImportExcelConstant.COMMA)
                                                                            .concat(String.valueOf(valueCompare));

                                                                    setListMessageError(objAdd, messageError, message,
                                                                            cellName, errorList, indexData,
                                                                            currentRow.getRowNum());
                                                                } else {
                                                                    if (StringUtils.isNotBlank(valueOfNumber)) {
                                                                    	f.set(objAdd, valueOfDouble);
                                                                    } else {
                                                                        f.set(objAdd, ImportExcelConstant.EMPTY_STRING);
                                                                    }
                                                                }
                                                            } else if (ImportExcelConstant.FORMAT.equalsIgnoreCase(types[0])) {
                                                                // use a better name, though
                                                                Pattern p = Pattern.compile(types[1]);
                                                                if (!p.matcher(valueOfNumber).matches()) {
                                                                    // set error
                                                                    String message = ImportExcelConstant.ERROR_FORMAT_TYPE
                                                                            .concat(ImportExcelConstant.SIGN_DASH)
                                                                            .concat(cellName)
                                                                            .concat(ImportExcelConstant.COMMA)
                                                                            .concat(types[1]);

                                                                    setListMessageError(objAdd, messageError, message,
                                                                            cellName, errorList, indexData,
                                                                            currentRow.getRowNum());
                                                                } else {
                                                                    if (StringUtils.isNotBlank(valueOfNumber)) {
                                                                    	f.set(objAdd, valueOfDouble);
                                                                    } else {
                                                                        f.set(objAdd, ImportExcelConstant.EMPTY_STRING);
                                                                    }
                                                                }
                                                            }
                                                        }
                                                    } catch (Exception e) {
                                                        // set error
                                                        objAdd.setIsError(true);

                                                        // set error
                                                        String message = ImportExcelConstant.ERROR_FORMAT
                                                                .concat(ImportExcelConstant.SIGN_DASH).concat(cellName);

                                                        setListMessageError(objAdd, messageError, message, cellName,
                                                                errorList, indexData, currentRow.getRowNum());
                                                    }
                                                } else if (StringUtils.isBlank(colType)) {
                                                	f.set(objAdd, valueOfDouble);
                                                } else {
                                                	f.set(objAdd, valueOfDouble);
                                                }
                                            }
                                        } else {
                                        	f.set(objAdd, valueOfDouble);
                                        }
                                        break;
                                    case INTEGER:
                                        Double numberInteger = Double.parseDouble(valueOfNumber);
                                        if (numberInteger % 1 != 0) {
                                            // set error
                                            String message = ImportExcelConstant.ERROR_INTEGER_TYPE.concat(ImportExcelConstant.SIGN_DASH).concat(cellName);

                                            setListMessageError(objAdd, messageError, message, cellName, errorList,
                                                    indexData, currentRow.getRowNum());
                                        } else {
                                            valueOfNumber = String.valueOf(numberInteger.intValue());
                                            
                                            Integer valueOfInterger = valueOfNumber.length() == 0 ? null
                                                    : Integer.parseInt(valueOfNumber);
                                            f.set(objAdd, valueOfInterger);
                                        }
                                        
                                        break;
                                    case INT:
                                        // special case : value = 1.0
                                        int valueOfInt = valueOfNumber.length() == 0 ? 0
                                                : (int) Float.parseFloat(valueOfNumber);
                                        f.set(objAdd, valueOfInt);

                                        break;
                                    case STRING:
                                        if (CellType.NUMERIC.equals(currentCell.getCellTypeEnum())) {
                                            if (DateUtil.isCellDateFormatted(currentCell)) {
                                                valueOfNumber = currentCell.toString();
                                            } else {
                                                valueOfNumber = String.format("%.0f", (double) Double.parseDouble(
                                                        String.valueOf(currentCell.getNumericCellValue())));
                                            }
                                            f.set(objAdd, valueOfNumber);
                                            
                                            valueOfString = valueOfNumber;
                                        }
                                        if (CollectionUtils.isNotEmpty(lstColType)) {
                                            for (String colType : lstColType) {
                                                if (!StringUtils.isBlank(colType)
                                                        && !ImportExcelConstant.NOT_NULL.equalsIgnoreCase(colType)) {
                                                    try {
                                                        String[] types = colType.split("=");
                                                        if (!ImportExcelConstant.STRING.equalsIgnoreCase(types[0])) {
                                                            if (ImportExcelConstant.MAX_LENGTH.equalsIgnoreCase(types[0])) {
                                                                int length = Integer.parseInt(types[1]);
                                                                if (valueOfString.length() > length) {
                                                                    // set error
                                                                    String message = ImportExcelConstant.ERROR_MAX_LENGTH
                                                                            .concat(ImportExcelConstant.SIGN_DASH)
                                                                            .concat(cellName)
                                                                            .concat(ImportExcelConstant.COMMA)
                                                                            .concat(String.valueOf(length));

                                                                    setListMessageError(objAdd, messageError, message,
                                                                            cellName, errorList, indexData,
                                                                            currentRow.getRowNum());
                                                                } else {
                                                                    if (StringUtils.isNotBlank(valueOfString)) {
                                                                        f.set(objAdd, valueOfString);
                                                                    } else {
                                                                        f.set(objAdd, ImportExcelConstant.EMPTY_STRING);
                                                                    }
                                                                }
                                                            } else if (ImportExcelConstant.MIN_LENGTH.equalsIgnoreCase(types[0])) {
                                                                int length = Integer.parseInt(types[1]);
                                                                if (valueOfString.length() < length) {
                                                                    // set error
                                                                    String message = ImportExcelConstant.ERROR_MIN_LENGTH
                                                                            .concat(ImportExcelConstant.SIGN_DASH)
                                                                            .concat(cellName)
                                                                            .concat(ImportExcelConstant.COMMA)
                                                                            .concat(String.valueOf(length));

                                                                    setListMessageError(objAdd, messageError, message,
                                                                            cellName, errorList, indexData,
                                                                            currentRow.getRowNum());
                                                                } else {
                                                                    if (StringUtils.isNotBlank(valueOfString)) {
                                                                        f.set(objAdd, valueOfString);
                                                                    } else {
                                                                        f.set(objAdd, ImportExcelConstant.EMPTY_STRING);
                                                                    }
                                                                }
                                                            } else if (ImportExcelConstant.FORMAT.equalsIgnoreCase(types[0])) {
                                                                // use a better name, though
                                                                Pattern p = Pattern.compile(types[1]);
                                                                if (!p.matcher(valueOfString).matches()) {
                                                                    // set error
                                                                    String message = ImportExcelConstant.ERROR_FORMAT_TYPE
                                                                            .concat(ImportExcelConstant.SIGN_DASH)
                                                                            .concat(cellName)
                                                                            .concat(ImportExcelConstant.COMMA)
                                                                            .concat(types[1]);

                                                                    setListMessageError(objAdd, messageError, message,
                                                                            cellName, errorList, indexData,
                                                                            currentRow.getRowNum());
                                                                } else {
                                                                    if (StringUtils.isNotBlank(valueOfString)) {
                                                                        f.set(objAdd, valueOfString);
                                                                    } else {
                                                                        f.set(objAdd, ImportExcelConstant.EMPTY_STRING);
                                                                    }
                                                                }
                                                            }
                                                        }
                                                    } catch (Exception e) {
                                                        // set error
                                                        objAdd.setIsError(true);

                                                        // set error
                                                        String message = ImportExcelConstant.ERROR_FORMAT
                                                                .concat(ImportExcelConstant.SIGN_DASH).concat(cellName);

                                                        setListMessageError(objAdd, messageError, message, cellName,
                                                                errorList, indexData, currentRow.getRowNum());
                                                    }
                                                } else if (StringUtils.isBlank(colType)) {
                                                    f.set(objAdd, valueOfString);
                                                } else {
                                                    f.set(objAdd, valueOfString);
                                                }
                                            }
                                        } else {
                                            f.set(objAdd, valueOfString);
                                            //set cột MESSAGEWARNING = ""
                                            if(col.getColName().equalsIgnoreCase(ImportExcelConstant.MESSAGEWARNING)) {
                                        		objAdd.setMessageWarning("");
                                        		f.set(objAdd, ImportExcelConstant.EMPTY_STRING);
                                        	}
                                        }
                                        break;
                                    case DATE:
                                        if (CollectionUtils.isNotEmpty(lstColType)) {
                                            for (String colType : lstColType) {
                                                if (!StringUtils.isBlank(colType)
                                                        && !ImportExcelConstant.NOT_NULL.equalsIgnoreCase(colType)
                                                        && !ImportExcelConstant.DATE.equalsIgnoreCase(colType)) {
                                                    try {
                                                        if (valueOfDate == null) {
                                                            Date date = null;
                                                            SimpleDateFormat sdf = new SimpleDateFormat(colType);
                                                            sdf.setLenient(false);
                                                            date = sdf.parse(currentCell.toString());
                                                            if (!currentCell.toString().equals(sdf.format(date))) {
                                                                date = null;
                                                            }
                                                            if (date == null) {
                                                                // set error
                                                                String message = ImportExcelConstant.ERROR_FORMAT_DATE_TYPE
                                                                        .concat(ImportExcelConstant.SIGN_DASH).concat(cellName)
                                                                        .concat(ImportExcelConstant.COMMA).concat(colType);

                                                                setListMessageError(objAdd, messageError, message, cellName,
                                                                        errorList, indexData, currentRow.getRowNum());
                                                            } else {
                                                                valueOfDate = DateUtils.formatStringToDate(
                                                                        currentCell.toString(), colType);
                                                                f.set(objAdd, valueOfDate);
                                                            }
                                                        } else {
                                                            f.set(objAdd, valueOfDate);
                                                        }
                                                    } catch (Exception e) {
                                                        // set error
                                                        String message = ImportExcelConstant.ERROR_FORMAT_DATE
                                                                .concat(ImportExcelConstant.SIGN_DASH).concat(cellName);

                                                        setListMessageError(objAdd, messageError, message, cellName, errorList,
                                                                indexData, currentRow.getRowNum());
                                                    }
                                                } else if (ImportExcelConstant.NOT_NULL.equalsIgnoreCase(colType)
                                                        && lstColType.size() == 1) {
                                                    try {
                                                        colType = FORMAT_DATE_DEFAULT;
                                                        if (valueOfDate == null) {
                                                            Date date = null;
                                                            SimpleDateFormat sdf = new SimpleDateFormat(colType);
                                                            sdf.setLenient(false);
                                                            date = sdf.parse(currentCell.toString());
                                                            if (!currentCell.toString().equals(sdf.format(date))) {
                                                                date = null;
                                                            }
                                                            if (date == null) {
                                                                // set error
                                                                String message = ImportExcelConstant.ERROR_FORMAT_DATE_TYPE
                                                                        .concat(ImportExcelConstant.SIGN_DASH)
                                                                        .concat(cellName)
                                                                        .concat(ImportExcelConstant.COMMA)
                                                                        .concat(colType);

                                                                setListMessageError(objAdd, messageError, message,
                                                                        cellName, errorList, indexData,
                                                                        currentRow.getRowNum());
                                                            } else {
                                                                valueOfDate = DateUtils.formatStringToDate(
                                                                        currentCell.toString(), colType);
                                                                f.set(objAdd, valueOfDate);
                                                            }
                                                        } else {
                                                            f.set(objAdd, valueOfDate);
                                                        }
                                                    } catch (Exception e) {
                                                        // set error
                                                        String message = ImportExcelConstant.ERROR_FORMAT_DATE
                                                                .concat(ImportExcelConstant.SIGN_DASH).concat(cellName);

                                                        setListMessageError(objAdd, messageError, message, cellName,
                                                                errorList, indexData, currentRow.getRowNum());
                                                    }
                                                }
                                            }
                                        } else {
                                            try {
                                                String colType = FORMAT_DATE_DEFAULT;
                                                if (valueOfDate == null) {
                                                    Date date = null;
                                                    SimpleDateFormat sdf = new SimpleDateFormat(colType);
                                                    sdf.setLenient(false);
                                                    date = sdf.parse(currentCell.toString());
                                                    if (!currentCell.toString().equals(sdf.format(date))) {
                                                        date = null;
                                                    }
                                                    if (date == null) {
                                                        // set error
                                                        String message = ImportExcelConstant.ERROR_FORMAT_DATE_TYPE
                                                                .concat(ImportExcelConstant.SIGN_DASH).concat(cellName)
                                                                .concat(ImportExcelConstant.COMMA).concat(colType);

                                                        setListMessageError(objAdd, messageError, message, cellName,
                                                                errorList, indexData, currentRow.getRowNum());
                                                    } else {
                                                        valueOfDate = DateUtils
                                                                .formatStringToDate(currentCell.toString(), colType);
                                                        f.set(objAdd, valueOfDate);
                                                    }
                                                } else {
                                                    f.set(objAdd, valueOfDate);
                                                }
                                            } catch (Exception e) {
                                                // set error
                                                String message = ImportExcelConstant.ERROR_FORMAT_DATE
                                                        .concat(ImportExcelConstant.SIGN_DASH).concat(cellName);

                                                setListMessageError(objAdd, messageError, message, cellName, errorList,
                                                        indexData, currentRow.getRowNum());
                                            }
                                        }
                                        break;
                                    case BIGDECIMAL:
                                        // Create a DecimalFormat that fits your
                                        DecimalFormatSymbols symbols = new DecimalFormatSymbols();
                                        symbols.setGroupingSeparator(',');
                                        symbols.setDecimalSeparator('.');
                                        String pattern = "#,##0.0###";
                                        DecimalFormat decimalFormat = new DecimalFormat(pattern,
                                                symbols);
                                        decimalFormat.setParseBigDecimal(true);
                                        BigDecimal valueOfBigDecimal = BigDecimal
                                                .valueOf(currentCell.getNumericCellValue());
                                        
                                        if (CollectionUtils.isNotEmpty(lstColType)) {
                                            for (String colType : lstColType) {
                                                if (!StringUtils.isBlank(colType)
                                                        && !ImportExcelConstant.NOT_NULL.equalsIgnoreCase(colType)) {
                                                    try {
                                                        String[] types = colType.split("=");
                                                        if (!ImportExcelConstant.STRING.equalsIgnoreCase(types[0])) {
                                                            if (ImportExcelConstant.MAX_LENGTH
                                                                    .equalsIgnoreCase(types[0])) {
                                                                int length = Integer.parseInt(types[1]);
                                                                if (valueOfBigDecimal.toString().length() > length) {
                                                                    // set error
                                                                    String message = ImportExcelConstant.ERROR_MAX_LENGTH
                                                                            .concat(ImportExcelConstant.SIGN_DASH)
                                                                            .concat(cellName)
                                                                            .concat(ImportExcelConstant.COMMA)
                                                                            .concat(String.valueOf(length - 2));

                                                                    setListMessageError(objAdd, messageError, message,
                                                                            cellName, errorList, indexData,
                                                                            currentRow.getRowNum());
                                                                } else {
                                                                    f.set(objAdd, valueOfBigDecimal);
                                                                }
                                                            } else if (ImportExcelConstant.MIN_LENGTH
                                                                    .equalsIgnoreCase(types[0])) {
                                                                int length = Integer.parseInt(types[1]);
                                                                if (valueOfBigDecimal.toString().length() < length) {
                                                                    // set error
                                                                    String message = ImportExcelConstant.ERROR_MIN_LENGTH
                                                                            .concat(ImportExcelConstant.SIGN_DASH)
                                                                            .concat(cellName)
                                                                            .concat(ImportExcelConstant.COMMA)
                                                                            .concat(String.valueOf(length - 2));

                                                                    setListMessageError(objAdd, messageError, message,
                                                                            cellName, errorList, indexData,
                                                                            currentRow.getRowNum());
                                                                } else {
                                                                    f.set(objAdd, valueOfBigDecimal);
                                                                }
                                                            }
                                                            
                                                            // -- truong hop khong nam trong 2 case tren lai ko set value.
                                                            else {
                                                            	f.set(objAdd, valueOfBigDecimal);
                                                            }
                                                            
                                                        } else {
                                                            f.set(objAdd, valueOfBigDecimal);
                                                        }
                                                    } catch (Exception e) {
                                                        // set error
                                                        objAdd.setIsError(true);

                                                        // set error
                                                        String message = ImportExcelConstant.ERROR_FORMAT
                                                                .concat(ImportExcelConstant.SIGN_DASH).concat(cellName);

                                                        setListMessageError(objAdd, messageError, message, cellName,
                                                                errorList, indexData, currentRow.getRowNum());
                                                    }
                                                } else {
                                                    f.set(objAdd, valueOfBigDecimal);
                                                }
                                            }
                                        } else {
                                            f.set(objAdd, valueOfBigDecimal);
                                        }
                                        break;
                                    default:
                                        break;
                                    }
                                    
                                    rowEmptyFlag = false;
                                }
                            } else {
                                List<String> lstColType = new ArrayList<>();

                                if (formatColInputs != null && !formatColInputs.isEmpty()) {
                                    // colType = formatColInput.get(col.getColName());
                                    lstColType = formatColInputs.get(col.getColName()) == null ? new ArrayList<>()
                                            : formatColInputs.get(col.getColName());
                                    if (lstColType.contains("NOT_NULL")) {
                                        // set error
                                        String message = ImportExcelConstant.ERROR_EMPTY
                                                .concat(ImportExcelConstant.SIGN_DASH).concat(cellName);

                                        setListMessageError(objAdd, messageError, message, cellName, errorList,
                                                indexData, currentRow.getRowNum());
                                    }
                                }
                            }
                        } catch (NumberFormatException ex) {
                            // set error
                            String message = ImportExcelConstant.ERROR_NUMBERRIC
                                    .concat(ImportExcelConstant.SIGN_DASH)
                                    .concat(cellName);

                            setListMessageError(objAdd, messageError, message, cellName, errorList, indexData,
                                    currentRow.getRowNum());
                        } catch (IllegalStateException ex) {
                            // set error
                            String message = ImportExcelConstant.EMPTY_STRING;

                            if (ex.getMessage() != null
                                    && ImportExcelConstant.ERROR_PARSE_NUMBER.equalsIgnoreCase(ex.getMessage())) {
                                message = ImportExcelConstant.ERROR_NUMBERRIC.concat(ImportExcelConstant.SIGN_DASH)
                                        .concat(cellName);
                            } else {
                                message = ImportExcelConstant.ERROR_FORMAT.concat(ImportExcelConstant.SIGN_DASH)
                                        .concat(cellName);
                            }
                            setListMessageError(objAdd, messageError, message, cellName, errorList, indexData,
                                    currentRow.getRowNum());
                        }
                    }
                } else {
                    currentRow = sheet.createRow(i);
                    for (ItemColsExcelDto col : cols) {
                        Cell currentCell = currentRow.getCell(col.getColIndex());
                        if (currentCell == null) {
                            currentCell = currentRow.createCell(col.getColIndex());
                        }

                        // set value of fields
                        Field f = mapFields.get(col.getColName().toUpperCase().trim());

                        if (f == null) {
                            String[] args = new String[1];
                            args[0] = col.getColName().toUpperCase().trim();
                            String mes = msg.getMessage("message.error.cannot.find.field", args, locale);
                            throw new NullPointerException(mes);
                        }

                        f.setAccessible(true);
                        String typeFields = f.getType().getSimpleName();
                        typeFields = typeFields.toUpperCase();
                        
                        // set cell name
                        cellName = rowHeader.getCell(col.getColIndex()).toString();

                        // currentCell is not null, not blank
                        List<String> lstColType = new ArrayList<>();

                        // set value in object
                        if (formatColInputs != null && !formatColInputs.isEmpty()) {
                            lstColType = formatColInputs.get(col.getColName()) == null ? new ArrayList<>()
                                    : formatColInputs.get(col.getColName());
                        }
                        if (lstColType.contains(ImportExcelConstant.NOT_NULL)) {
                            // set error
                            String message = ImportExcelConstant.ERROR_EMPTY.concat(ImportExcelConstant.SIGN_DASH)
                                    .concat(cellName);

                            setListMessageError(objAdd, messageError, message, cellName, errorList, indexData,
                                    currentRow.getRowNum());
                        }
                    }
                }
                
                if (!rowEmptyFlag) {
                    objAdd.setSessionKey(sessionKey);

                    if (objAdd.isError()) {
                        objAdd.setIsError(true);
                        listError.add(objAdd);
                    } else {
                        listSuccess.add(objAdd);
                    }
                    list.add(objAdd);
                } else {
                    objAdd.setSessionKey(sessionKey);
                    objAdd.setIsError(true);
                    listError.add(objAdd);
                    listSuccess.add(objAdd);
                    list.add(objAdd);
                }

            } // end white

            // set error
            this.rowErrorMap = errorList;

            //setMessageError(list);

            // set data
            this.data = list;
            this.dataError = listError;
            this.dataSuccess = listSuccess;

        } catch (Exception ex) {
            logger.error("#setDataFileExcelWithErrorMap#", ex.getMessage());
            this.data = new ArrayList<T>();
            throw new Exception(ex.getMessage());
        }
    }
    
    /**
     * @param sheet
     * @param startRow
     * @param lastRow
     * @param cols
     * @param rowHeader
     * @param objDefault
     * @param formatColInput -> (key,value) -> upper case, the map used to format
     *                       column Ex: agent code -> number, rate -> percent
     * @param sessionKey
     * @param msg
     * @param headers
     * @author TaiTM
     * @throws Exception
     */
    @SuppressWarnings({ "unchecked" })
    public void setDataFileExcelWithErrorMapWithFieldType(Sheet sheet, int startRow, int lastRow,
            List<ItemColsExcelDto> cols, Row rowHeader, T objDefault, Map<String, List<String>> formatColInputs,
            String sessionKey, MessageSource msg, List<String> headers, Locale locale) throws Exception {
        try {
            List<T> list = new ArrayList<T>();
            List<T> listError = new ArrayList<T>();
            List<T> listSuccess = new ArrayList<T>();

            Map<String, Field> mapFields = new HashMap<String, Field>();
            // get list fields of class
            Class<?> cls = objDefault.getClass();
            Field[] fields = cls.getDeclaredFields();
            
            // PhatLT : lấy field từ class cha nếu có 
            Field [] fieldSuperClass = null;
            // mọi class đều extend từ Object, nên nếu không có class "cha" thì getSuperclass sẽ lấy được Object class 
            if(cls.getSuperclass()!= null && !StringUtils.equalsIgnoreCase("Object", cls.getSuperclass().getSimpleName()))
            	fieldSuperClass = cls.getSuperclass().getDeclaredFields();
            List<Field> lstField = new ArrayList<Field>();
            lstField.addAll(Arrays.asList(fields));
            if(fieldSuperClass != null && fieldSuperClass.length > 0)
            	lstField.addAll(Arrays.asList(fieldSuperClass));
            
            for (Field f : lstField) {
                if(!mapFields.containsKey(f.getName().toUpperCase())) 
                    mapFields.put(f.getName().toUpperCase(), f);
            }

            // check error
            Map<String, List<Integer>> errorList = new HashMap<>();

            short lastCellNum = (short) (rowHeader != null ? (rowHeader.getLastCellNum() - 1) : 0);

            Iterator<ItemColsExcelDto> itemColsExcelIterator = cols.iterator();
            while (itemColsExcelIterator.hasNext()) {
                ItemColsExcelDto col = itemColsExcelIterator.next();
                // item cols excel not in rowheader
                if (col.getColIndex() > lastCellNum) {
                    itemColsExcelIterator.remove();
                    continue;
                }

                if (CollectionUtils.isNotEmpty(headers)) {
                    errorList.put(headers.get(col.getColIndex()), new ArrayList<>());
                } else {
                    String fieldName = rowHeader.getCell(col.getColIndex()).toString();
                    errorList.put(fieldName, new ArrayList<>());
                }
            }

            // cell name
            String cellName = StringUtils.EMPTY;

            Integer indexData = 0;
            // loop rows
            for (int i = startRow; i < lastRow; i++) {
                Row currentRow = sheet.getRow(i);
                indexData++;
                T objAdd = (T) objDefault.getClass().newInstance();

                // rowEmptyFlag
                boolean rowEmptyFlag = true;
                List<String> messageError = new ArrayList<>();

                // currentRow is not null and LastCellNum > 0
                if (currentRow != null && currentRow.getLastCellNum() > 0) {
                    // set value of object
                    for (int j = 0; j < cols.size(); j++) {
                        ItemColsExcelDto col = cols.get(j);
                        try {
                            Cell currentCell = currentRow.getCell(col.getColIndex());
                            // set value of fields
                            Field f = mapFields.get(col.getColName().toUpperCase().trim());

                            if (f == null) {
                                String[] args = new String[1];
                                args[0] = col.getColName().toUpperCase().trim();
                                String mes = msg.getMessage("message.error.cannot.find.field", args, locale);
                                throw new NullPointerException(mes);
                            }

                            f.setAccessible(true);
                            String typeFields = f.getType().getSimpleName();
                            typeFields = typeFields.toUpperCase();
                            DataType dataType = DataType.valueOf(typeFields);
                            
                            // set cell name
                            if (CollectionUtils.isNotEmpty(headers)) {
                                cellName = headers.get(j);
                            } else {
                                cellName = rowHeader.getCell(col.getColIndex()).toString();
                            }

                            // currentCell is not null, not blank
                            if (currentCell != null && currentCell.getCellTypeEnum() != CellType.BLANK
                                    && StringUtils.isNotBlank(currentCell.toString())) {
                                rowEmptyFlag = false;
                                String valueOfString = ImportExcelConstant.EMPTY_STRING;
                                String valueOfNumber = ImportExcelConstant.EMPTY_STRING;
                                Date valueOfDate = null;
                                logger.info("CELL TYPE " + currentCell.getCellTypeEnum());

                                // check type cell
                                switch (currentCell.getCellTypeEnum()) {
                                case BOOLEAN:
                                	Boolean valueBoolean = currentCell.getBooleanCellValue();
                                	switch (dataType) {
                                    case STRING: 
                                    	valueOfString = valueBoolean ? ImportExcelConstant.TRUE : ImportExcelConstant.FALSE;
                                    	break;
                                    default:
                                        break;
                                	}
                                    break;
                                case STRING:
                                    valueOfString = currentCell.getStringCellValue();
                                    break;
                                case NUMERIC:
                                    if (DateUtil.isCellDateFormatted(currentCell)) {
                                        valueOfDate = currentCell.getDateCellValue();
                                    } else {
                                        valueOfNumber = String.format("%f", (double) Double
                                                .parseDouble(String.valueOf(currentCell.getNumericCellValue())));

                                        if (valueOfNumber.contains(",")) {
                                            valueOfNumber = valueOfNumber.replace(",", ".");
                                        }
                                    }
                                    break;
                                case FORMULA:
                                    switch (currentCell.getCachedFormulaResultTypeEnum()) {
                                    case NUMERIC:
                                        valueOfNumber = currentCell.getNumericCellValue()
                                                + ImportExcelConstant.EMPTY_STRING;
                                        break;
                                    case STRING:
                                        valueOfString = currentCell.getRichStringCellValue()
                                                + ImportExcelConstant.EMPTY_STRING;
                                        break;
                                    default:
                                        break;
                                    }
                                    break;
                                default:
                                    break;
                                }

                                List<String> lstColType = new ArrayList<>();

                                // set value in object
                                if (formatColInputs != null && !formatColInputs.isEmpty()) {
                                    lstColType = formatColInputs.get(col.getColName()) == null ? new ArrayList<>()
                                            : formatColInputs.get(col.getColName());

                                    if (CollectionUtils.isNotEmpty(lstColType)) {
                                        if (lstColType.contains(ImportExcelConstant.STRING)) {
                                            DataFormatter formatter = new DataFormatter();
                                            valueOfString = formatter.formatCellValue(currentCell);
                                        } else if (lstColType.contains(ImportExcelConstant.NUMBER)) {
                                            valueOfNumber = String.valueOf((int) Double
                                                    .parseDouble(String.valueOf(currentCell.getNumericCellValue())));
                                        } else if (lstColType.contains(ImportExcelConstant.PERCENT)) {
                                            valueOfString = String.valueOf(currentCell.getNumericCellValue() * 100);
                                        } else if (lstColType.contains(ImportExcelConstant.DATE)) {
                                            valueOfString = currentCell.toString();
                                        }
                                    }
                                }
                                
                                if (valueOfDate != null) {
                                    valueOfString = valueOfDate.toString();
                                } else {
                                    valueOfString = valueOfString.trim();
                                }
                                
                                if (lstColType.contains(ImportExcelConstant.NOT_NULL)
                                        && StringUtils.isBlank(valueOfString) && StringUtils.isBlank(valueOfNumber)) {
                                    // set error
                                    String message = ImportExcelConstant.ERROR_EMPTY.concat(ImportExcelConstant.SIGN_DASH).concat(cellName);
                                    
                                    setListMessageError(objAdd, messageError, message, cellName, errorList, indexData,
                                            currentRow.getRowNum());
                                } else {
                                    switch (dataType) {
                                    case LONG:
                                        Double numberLong = Double.parseDouble(valueOfNumber);
                                        if (numberLong % 1 != 0) {
                                            // set error
                                            String message = ImportExcelConstant.ERROR_INTEGER_TYPE.concat(ImportExcelConstant.SIGN_DASH).concat(cellName);
                                            
                                            setListMessageError(objAdd, messageError, message, cellName, errorList,
                                                    indexData, currentRow.getRowNum());
                                        } else {
                                            valueOfNumber = String.valueOf(numberLong.longValue());

                                            Long valueOfLong = valueOfNumber.length() == 0 ? null
                                                    : Long.parseLong(valueOfNumber);
                                            if (CollectionUtils.isNotEmpty(lstColType)) {
                                                for (String colType : lstColType) {
                                                    if (!StringUtils.isBlank(colType)
                                                            && !ImportExcelConstant.NOT_NULL.equalsIgnoreCase(colType)) {
                                                        try {
                                                            String[] types = colType.split("=");
                                                            if (!ImportExcelConstant.STRING.equalsIgnoreCase(types[0])) {
                                                                if (ImportExcelConstant.MAX_LENGTH.equalsIgnoreCase(types[0])) {
                                                                    int length = Integer.parseInt(types[1]);
                                                                    if (valueOfNumber.length() > length) {
                                                                        // set error
                                                                        String message = ImportExcelConstant.ERROR_MAX_LENGTH
                                                                                .concat(ImportExcelConstant.SIGN_DASH)
                                                                                .concat(cellName)
                                                                                .concat(ImportExcelConstant.COMMA)
                                                                                .concat(String.valueOf(length));

                                                                        setListMessageError(objAdd, messageError, message,
                                                                                cellName, errorList, indexData,
                                                                                currentRow.getRowNum());
                                                                    } else {
                                                                        if (StringUtils.isNotBlank(valueOfNumber)) {
                                                                        	f.set(objAdd, valueOfLong);
                                                                        } else {
                                                                            f.set(objAdd, ImportExcelConstant.EMPTY_STRING);
                                                                        }
                                                                    }
                                                                } else if (ImportExcelConstant.MIN_LENGTH.equalsIgnoreCase(types[0])) {
                                                                    int length = Integer.parseInt(types[1]);
                                                                    if (valueOfNumber.length() < length) {
                                                                        // set error
                                                                        String message = ImportExcelConstant.ERROR_MIN_LENGTH
                                                                                .concat(ImportExcelConstant.SIGN_DASH)
                                                                                .concat(cellName)
                                                                                .concat(ImportExcelConstant.COMMA)
                                                                                .concat(String.valueOf(length));

                                                                        setListMessageError(objAdd, messageError, message,
                                                                                cellName, errorList, indexData,
                                                                                currentRow.getRowNum());
                                                                    } else {
                                                                        if (StringUtils.isNotBlank(valueOfNumber)) {
                                                                        	f.set(objAdd, valueOfLong);
                                                                        } else {
                                                                            f.set(objAdd, ImportExcelConstant.EMPTY_STRING);
                                                                        }
                                                                    }
                                                                } else if (ImportExcelConstant.FORMAT.equalsIgnoreCase(types[0])) {
                                                                    // use a better name, though
                                                                    Pattern p = Pattern.compile(types[1]);
                                                                    if (!p.matcher(valueOfNumber).matches()) {
                                                                        // set error
                                                                        String message = ImportExcelConstant.ERROR_FORMAT_TYPE
                                                                                .concat(ImportExcelConstant.SIGN_DASH)
                                                                                .concat(cellName)
                                                                                .concat(ImportExcelConstant.COMMA)
                                                                                .concat(types[1]);

                                                                        setListMessageError(objAdd, messageError, message,
                                                                                cellName, errorList, indexData,
                                                                                currentRow.getRowNum());
                                                                    } else {
                                                                        if (StringUtils.isNotBlank(valueOfNumber)) {
                                                                        	f.set(objAdd, valueOfLong);
                                                                        } else {
                                                                            f.set(objAdd, ImportExcelConstant.EMPTY_STRING);
                                                                        }
                                                                    }
                                                                }
                                                            }
                                                        } catch (Exception e) {
                                                            // set error
                                                            objAdd.setIsError(true);

                                                            // set error
                                                            String message = ImportExcelConstant.ERROR_FORMAT
                                                                    .concat(ImportExcelConstant.SIGN_DASH).concat(cellName);

                                                            setListMessageError(objAdd, messageError, message, cellName,
                                                                    errorList, indexData, currentRow.getRowNum());
                                                        }
                                                    } else if (StringUtils.isBlank(colType)) {
                                                    	f.set(objAdd, valueOfLong);
                                                    } else {
                                                    	f.set(objAdd, valueOfLong);
                                                    }
                                                }
                                            } else {
                                            	f.set(objAdd, valueOfLong);
                                            }
                                        }
                                        break;
                                    case DOUBLE:
                                        Double valueOfDouble = currentCell.getNumericCellValue();
                                        f.set(objAdd, valueOfDouble);

                                        break;
                                    case INTEGER:
                                        Double numberInteger = Double.parseDouble(valueOfNumber);
                                        if (numberInteger % 1 != 0) {
                                            // set error
                                            String message = ImportExcelConstant.ERROR_INTEGER_TYPE.concat(ImportExcelConstant.SIGN_DASH).concat(cellName);

                                            setListMessageError(objAdd, messageError, message, cellName, errorList,
                                                    indexData, currentRow.getRowNum());
                                        } else {
                                            valueOfNumber = String.valueOf(numberInteger.intValue());
                                            
                                            Integer valueOfInterger = valueOfNumber.length() == 0 ? null
                                                    : Integer.parseInt(valueOfNumber);
                                            f.set(objAdd, valueOfInterger);
                                        }
                                        
                                        break;
                                    case INT:
                                        // special case : value = 1.0
                                        int valueOfInt = valueOfNumber.length() == 0 ? 0
                                                : (int) Float.parseFloat(valueOfNumber);
                                        f.set(objAdd, valueOfInt);

                                        break;
                                    case STRING:
                                        if (CellType.NUMERIC.equals(currentCell.getCellTypeEnum())) {
                                            if (DateUtil.isCellDateFormatted(currentCell)) {
                                                valueOfNumber = currentCell.toString();
                                            } else {
                                                valueOfNumber = String.format("%.0f", (double) Double.parseDouble(
                                                        String.valueOf(currentCell.getNumericCellValue())));
                                            }
                                            f.set(objAdd, valueOfNumber);
                                            
                                            valueOfString = valueOfNumber;
                                        }
                                        if (CollectionUtils.isNotEmpty(lstColType)) {
                                            for (String colType : lstColType) {
                                                if (!StringUtils.isBlank(colType)
                                                        && !ImportExcelConstant.NOT_NULL.equalsIgnoreCase(colType)) {
                                                    try {
                                                        String[] types = colType.split("=");
                                                        if (!ImportExcelConstant.STRING.equalsIgnoreCase(types[0])) {
                                                            if (ImportExcelConstant.MAX_LENGTH.equalsIgnoreCase(types[0])) {
                                                                int length = Integer.parseInt(types[1]);
                                                                if (valueOfString.length() > length) {
                                                                    // set error
                                                                    String message = ImportExcelConstant.ERROR_MAX_LENGTH
                                                                            .concat(ImportExcelConstant.SIGN_DASH)
                                                                            .concat(cellName)
                                                                            .concat(ImportExcelConstant.COMMA)
                                                                            .concat(String.valueOf(length));

                                                                    setListMessageError(objAdd, messageError, message,
                                                                            cellName, errorList, indexData,
                                                                            currentRow.getRowNum());
                                                                } else {
                                                                    if (StringUtils.isNotBlank(valueOfString)) {
                                                                        f.set(objAdd, valueOfString);
                                                                    } else {
                                                                        f.set(objAdd, ImportExcelConstant.EMPTY_STRING);
                                                                    }
                                                                }
                                                            } else if (ImportExcelConstant.MIN_LENGTH.equalsIgnoreCase(types[0])) {
                                                                int length = Integer.parseInt(types[1]);
                                                                if (valueOfString.length() < length) {
                                                                    // set error
                                                                    String message = ImportExcelConstant.ERROR_MIN_LENGTH
                                                                            .concat(ImportExcelConstant.SIGN_DASH)
                                                                            .concat(cellName)
                                                                            .concat(ImportExcelConstant.COMMA)
                                                                            .concat(String.valueOf(length));

                                                                    setListMessageError(objAdd, messageError, message,
                                                                            cellName, errorList, indexData,
                                                                            currentRow.getRowNum());
                                                                } else {
                                                                    if (StringUtils.isNotBlank(valueOfString)) {
                                                                        f.set(objAdd, valueOfString);
                                                                    } else {
                                                                        f.set(objAdd, ImportExcelConstant.EMPTY_STRING);
                                                                    }
                                                                }
                                                            } else if (ImportExcelConstant.FORMAT.equalsIgnoreCase(types[0])) {
                                                                // use a better name, though
                                                                Pattern p = Pattern.compile(types[1]);
                                                                if (!p.matcher(valueOfString).matches()) {
                                                                    // set error
                                                                    String message = ImportExcelConstant.ERROR_FORMAT_TYPE
                                                                            .concat(ImportExcelConstant.SIGN_DASH)
                                                                            .concat(cellName)
                                                                            .concat(ImportExcelConstant.COMMA)
                                                                            .concat(types[1]);

                                                                    setListMessageError(objAdd, messageError, message,
                                                                            cellName, errorList, indexData,
                                                                            currentRow.getRowNum());
                                                                } else {
                                                                    f.set(objAdd, ImportExcelConstant.EMPTY_STRING);
                                                                }
                                                            }
                                                        }
                                                    } catch (Exception e) {
                                                        // set error
                                                        objAdd.setIsError(true);

                                                        // set error
                                                        String message = ImportExcelConstant.ERROR_FORMAT
                                                                .concat(ImportExcelConstant.SIGN_DASH).concat(cellName);

                                                        setListMessageError(objAdd, messageError, message, cellName,
                                                                errorList, indexData, currentRow.getRowNum());
                                                    }
                                                } else if (StringUtils.isBlank(colType)) {
                                                    f.set(objAdd, valueOfString);
                                                } else {
                                                    f.set(objAdd, valueOfString);
                                                }
                                            }
                                        } else {
                                            f.set(objAdd, valueOfString);
                                        }
                                        break;
                                    case DATE:
                                        if (CollectionUtils.isNotEmpty(lstColType)) {
                                            for (String colType : lstColType) {
                                                if (!StringUtils.isBlank(colType)
                                                        && !ImportExcelConstant.NOT_NULL.equalsIgnoreCase(colType)
                                                        && !ImportExcelConstant.DATE.equalsIgnoreCase(colType)) {
                                                    try {
                                                        if (valueOfDate == null) {
                                                            Date date = null;
                                                            SimpleDateFormat sdf = new SimpleDateFormat(colType);
                                                            sdf.setLenient(false);
                                                            date = sdf.parse(currentCell.toString());
                                                            if (!currentCell.toString().equals(sdf.format(date))) {
                                                                date = null;
                                                            }
                                                            if (date == null) {
                                                                // set error
                                                                String message = ImportExcelConstant.ERROR_FORMAT_DATE_TYPE
                                                                        .concat(ImportExcelConstant.SIGN_DASH).concat(cellName)
                                                                        .concat(ImportExcelConstant.COMMA).concat(colType);

                                                                setListMessageError(objAdd, messageError, message, cellName,
                                                                        errorList, indexData, currentRow.getRowNum());
                                                            } else {
                                                                valueOfDate = DateUtils.formatStringToDate(
                                                                        currentCell.toString(), colType);
                                                                f.set(objAdd, valueOfDate);
                                                            }
                                                        } else {
                                                            f.set(objAdd, valueOfDate);
                                                        }
                                                    } catch (Exception e) {
                                                        // set error
                                                        String message = ImportExcelConstant.ERROR_FORMAT_DATE
                                                                .concat(ImportExcelConstant.SIGN_DASH).concat(cellName);

                                                        setListMessageError(objAdd, messageError, message, cellName, errorList,
                                                                indexData, currentRow.getRowNum());
                                                    }
                                                } else if (ImportExcelConstant.NOT_NULL.equalsIgnoreCase(colType)
                                                        && lstColType.size() == 1) {
                                                    try {
                                                        colType = FORMAT_DATE_DEFAULT;
                                                        if (valueOfDate == null) {
                                                            Date date = null;
                                                            SimpleDateFormat sdf = new SimpleDateFormat(colType);
                                                            sdf.setLenient(false);
                                                            date = sdf.parse(currentCell.toString());
                                                            if (!currentCell.toString().equals(sdf.format(date))) {
                                                                date = null;
                                                            }
                                                            if (date == null) {
                                                                // set error
                                                                String message = ImportExcelConstant.ERROR_FORMAT_DATE_TYPE
                                                                        .concat(ImportExcelConstant.SIGN_DASH)
                                                                        .concat(cellName)
                                                                        .concat(ImportExcelConstant.COMMA)
                                                                        .concat(colType);

                                                                setListMessageError(objAdd, messageError, message,
                                                                        cellName, errorList, indexData,
                                                                        currentRow.getRowNum());
                                                            } else {
                                                                valueOfDate = DateUtils.formatStringToDate(
                                                                        currentCell.toString(), colType);
                                                                f.set(objAdd, valueOfDate);
                                                            }
                                                        } else {
                                                            f.set(objAdd, valueOfDate);
                                                        }
                                                    } catch (Exception e) {
                                                        // set error
                                                        String message = ImportExcelConstant.ERROR_FORMAT_DATE
                                                                .concat(ImportExcelConstant.SIGN_DASH).concat(cellName);

                                                        setListMessageError(objAdd, messageError, message, cellName,
                                                                errorList, indexData, currentRow.getRowNum());
                                                    }
                                                }
                                            }
                                        } else {
                                            try {
                                                String colType = FORMAT_DATE_DEFAULT;
                                                if (valueOfDate == null) {
                                                    Date date = null;
                                                    SimpleDateFormat sdf = new SimpleDateFormat(colType);
                                                    sdf.setLenient(false);
                                                    date = sdf.parse(currentCell.toString());
                                                    if (!currentCell.toString().equals(sdf.format(date))) {
                                                        date = null;
                                                    }
                                                    if (date == null) {
                                                        // set error
                                                        String message = ImportExcelConstant.ERROR_FORMAT_DATE_TYPE
                                                                .concat(ImportExcelConstant.SIGN_DASH).concat(cellName)
                                                                .concat(ImportExcelConstant.COMMA).concat(colType);

                                                        setListMessageError(objAdd, messageError, message, cellName,
                                                                errorList, indexData, currentRow.getRowNum());
                                                    } else {
                                                        valueOfDate = DateUtils
                                                                .formatStringToDate(currentCell.toString(), colType);
                                                        f.set(objAdd, valueOfDate);
                                                    }
                                                } else {
                                                    f.set(objAdd, valueOfDate);
                                                }
                                            } catch (Exception e) {
                                                // set error
                                                String message = ImportExcelConstant.ERROR_FORMAT_DATE
                                                        .concat(ImportExcelConstant.SIGN_DASH).concat(cellName);

                                                setListMessageError(objAdd, messageError, message, cellName, errorList,
                                                        indexData, currentRow.getRowNum());
                                            }
                                        }
                                        break;
                                    case BIGDECIMAL:
                                        // Create a DecimalFormat that fits your
                                        DecimalFormatSymbols symbols = new DecimalFormatSymbols();
                                        symbols.setGroupingSeparator(',');
                                        symbols.setDecimalSeparator('.');
                                        String pattern = "#,##0.0###";
                                        DecimalFormat decimalFormat = new DecimalFormat(pattern, symbols);
                                        decimalFormat.setParseBigDecimal(true);
                                        BigDecimal valueOfBigDecimal = BigDecimal
                                                .valueOf(currentCell.getNumericCellValue());
                                        f.set(objAdd, valueOfBigDecimal);
                                        break;
                                    default:
                                        break;
                                    }
                                    
                                    rowEmptyFlag = false;
                                }
                            } else {
                                List<String> lstColType = new ArrayList<>();

                                if (formatColInputs != null && !formatColInputs.isEmpty()) {
                                    // colType = formatColInput.get(col.getColName());
                                    lstColType = formatColInputs.get(col.getColName()) == null ? new ArrayList<>()
                                            : formatColInputs.get(col.getColName());
                                    if (lstColType.contains("NOT_NULL")) {
                                        // set error
                                        String message = ImportExcelConstant.ERROR_EMPTY
                                                .concat(ImportExcelConstant.SIGN_DASH).concat(cellName);

                                        setListMessageError(objAdd, messageError, message, cellName, errorList,
                                                indexData, currentRow.getRowNum());
                                    }
                                }
                            }
                        } catch (NumberFormatException ex) {
                            // set error
                            String message = ImportExcelConstant.ERROR_NUMBERRIC
                                    .concat(ImportExcelConstant.SIGN_DASH)
                                    .concat(cellName);

                            setListMessageError(objAdd, messageError, message, cellName, errorList, indexData,
                                    currentRow.getRowNum());
                        } catch (IllegalStateException ex) {
                            // set error
                            String message = ImportExcelConstant.EMPTY_STRING;

                            if (ex.getMessage() != null
                                    && ImportExcelConstant.ERROR_PARSE_NUMBER.equalsIgnoreCase(ex.getMessage())) {
                                message = ImportExcelConstant.ERROR_NUMBERRIC.concat(ImportExcelConstant.SIGN_DASH)
                                        .concat(cellName);
                            } else {
                                message = ImportExcelConstant.ERROR_FORMAT.concat(ImportExcelConstant.SIGN_DASH)
                                        .concat(cellName);
                            }
                            setListMessageError(objAdd, messageError, message, cellName, errorList, indexData,
                                    currentRow.getRowNum());
                        }
                    }
                } else {
                    currentRow = sheet.createRow(i);
                    for (ItemColsExcelDto col : cols) {
                        Cell currentCell = currentRow.getCell(col.getColIndex());
                        if (currentCell == null) {
                            currentCell = currentRow.createCell(col.getColIndex());
                        }

                        // set value of fields
                        Field f = mapFields.get(col.getColName().toUpperCase().trim());

                        if (f == null) {
                            String[] args = new String[1];
                            args[0] = col.getColName().toUpperCase().trim();
                            String mes = msg.getMessage("message.error.cannot.find.field", args, locale);
                            throw new NullPointerException(mes);
                        }

                        f.setAccessible(true);
                        String typeFields = f.getType().getSimpleName();
                        typeFields = typeFields.toUpperCase();
                        
                        // set cell name
                        // cellName = rowHeader.getCell(col.getColIndex()).toString();
                        
                        cellName = headers.get(col.getColIndex());

                        // currentCell is not null, not blank
                        List<String> lstColType = new ArrayList<>();

                        // set value in object
                        if (formatColInputs != null && !formatColInputs.isEmpty()) {
                            lstColType = formatColInputs.get(col.getColName()) == null ? new ArrayList<>()
                                    : formatColInputs.get(col.getColName());
                        }
                        if (lstColType.contains(ImportExcelConstant.NOT_NULL)) {
                            // set error
                            String message = ImportExcelConstant.ERROR_EMPTY.concat(ImportExcelConstant.SIGN_DASH)
                                    .concat(cellName);

                            setListMessageError(objAdd, messageError, message, cellName, errorList, indexData,
                                    currentRow.getRowNum());
                        }
                    }
                }
                
                if (!rowEmptyFlag) {
                    objAdd.setSessionKey(sessionKey);

                    if (objAdd.isError()) {
                        objAdd.setIsError(true);
                        listError.add(objAdd);
                    } else {
                        listSuccess.add(objAdd);
                    }
                    list.add(objAdd);
                } else {
                    objAdd.setSessionKey(sessionKey);
                    objAdd.setIsError(true);
                    listError.add(objAdd);
                    listSuccess.add(objAdd);
                    list.add(objAdd);
                }

            } // end white

            // set error
            this.rowErrorMap = errorList;

            //setMessageError(list);

            // set data
            this.data = list;
            this.dataError = listError;
            this.dataSuccess = listSuccess;

        } catch (Exception ex) {
            logger.error("#setDataFileExcelWithErrorMap#", ex.getMessage());
            this.data = new ArrayList<T>();
            throw new Exception(ex.getMessage());
        }
    }
    
    /**
     * @param sheet
     * @param startRow
     * @param lastRow
     * @param cols
     * @param rowHeader
     * @param objDefault
     * @param formatColInput -> (key,value) -> upper case, the map used to format
     *                       column Ex: agent code -> number, rate -> percent
     * @param sessionKey
     * @param msg
     * @param headers
     * @author TaiTM
     * @throws Exception
     */
    @SuppressWarnings({ "unchecked" })
    public void setAllDataWithErrorMapWithFieldType(Sheet sheet, int startRow, int lastRow, List<ItemColsExcelDto> cols,
            Row rowHeader, T objDefault, Map<String, List<String>> formatColInputs, String sessionKey,
            MessageSource msg, List<String> headers, Locale locale) throws Exception {
        try {
            List<T> list = new ArrayList<T>();
            List<T> listError = new ArrayList<T>();
            List<T> listSuccess = new ArrayList<T>();

            Map<String, Field> mapFields = new HashMap<String, Field>();
            // get list fields of class
            Class<?> cls = objDefault.getClass();
            Field[] fields = cls.getDeclaredFields();

            // PhatLT : lấy field từ class cha nếu có
            Field[] fieldSuperClass = null;
            // mọi class đều extend từ Object, nên nếu không có class "cha" thì
            // getSuperclass sẽ lấy được Object class
            if (cls.getSuperclass() != null
                    && !StringUtils.equalsIgnoreCase("Object", cls.getSuperclass().getSimpleName())) {
                fieldSuperClass = cls.getSuperclass().getDeclaredFields();
            }

            List<Field> lstField = new ArrayList<Field>();
            lstField.addAll(Arrays.asList(fields));
            if (fieldSuperClass != null && fieldSuperClass.length > 0) {
                lstField.addAll(Arrays.asList(fieldSuperClass));
            }

            for (Field f : lstField) {
                if(!mapFields.containsKey(f.getName().toUpperCase())) 
                    mapFields.put(f.getName().toUpperCase(), f);
            }

            // check error
            Map<String, List<Integer>> errorList = new HashMap<>();

            short lastCellNum = (short) (rowHeader != null ? (rowHeader.getLastCellNum() - 1) : 0);

            Iterator<ItemColsExcelDto> itemColsExcelIterator = cols.iterator();
            while (itemColsExcelIterator.hasNext()) {
                ItemColsExcelDto col = itemColsExcelIterator.next();
                // item cols excel not in rowheader
                if (col.getColIndex() > lastCellNum) {
                    itemColsExcelIterator.remove();
                    continue;
                }

                if (CollectionUtils.isNotEmpty(headers)) {
                    errorList.put(headers.get(col.getColIndex()), new ArrayList<>());
                } else {
                    String fieldName = rowHeader.getCell(col.getColIndex()).toString();
                    errorList.put(fieldName, new ArrayList<>());
                }
            }

            // cell name
            String cellName = StringUtils.EMPTY;

            Integer indexData = 0;
            
            // loop rows
            for (int i = startRow; i < lastRow; i++) {
                Row currentRow = sheet.getRow(i);
                indexData++;
                T objAdd = (T) objDefault.getClass().newInstance();

                // rowEmptyFlag
                boolean rowEmptyFlag = true;
                List<String> messageError = new ArrayList<>();

                // currentRow is not null and LastCellNum > 0
                if (currentRow != null && currentRow.getLastCellNum() > 0) {
                    // set value of object
                    for (int j = 0; j < cols.size(); j++) {
                        ItemColsExcelDto col = cols.get(j);
                        try {
                            Cell currentCell = currentRow.getCell(col.getColIndex());
                            // set value of fields
                            Field f = mapFields.get(col.getColName().toUpperCase().trim());

                            if (f == null) {
                                String[] args = new String[1];
                                args[0] = col.getColName().toUpperCase().trim();
                                String mes = msg.getMessage("message.error.cannot.find.field", args, locale);
                                throw new NullPointerException(mes);
                            }

                            ExcelImportField[] ano = f.getDeclaredAnnotationsByType(ExcelImportField.class);

                            if (ano.length > 0) {
                                f.setAccessible(true);
                                DataType dataType = DataType.valueOf(ano[0].colType().toString());

                                // set cell name
                                if (CollectionUtils.isNotEmpty(headers)) {
                                    cellName = headers.get(col.getColIndex());
                                } else {
                                    cellName = rowHeader.getCell(col.getColIndex()).toString();
                                }

                                // currentCell is not null, not blank
                                if (currentCell != null && currentCell.getCellTypeEnum() != CellType.BLANK
                                        && StringUtils.isNotBlank(currentCell.toString())) {
                                    
                                    rowEmptyFlag = false;
                                    String valueOfString = ImportExcelConstant.EMPTY_STRING;
                                    String valueOfNumber = ImportExcelConstant.EMPTY_STRING;
                                    Date valueOfDate = null;
                                    logger.info("CELL TYPE " + currentCell.getCellTypeEnum());

                                    // check type cell
                                    List<String> lstColType = new ArrayList<>();
                                    Map<String, Object> mapData = checkTypeCell(currentCell, col, dataType, formatColInputs,
                                            lstColType, valueOfString, valueOfNumber, valueOfDate, IMPORT_TYPE_NOT_CHECK_DATA);

                                    valueOfString = (String) mapData.get("valueOfString");
                                    valueOfNumber = (String) mapData.get("valueOfNumber");
                                    valueOfDate = (Date) mapData.get("valueOfDate");
                                    lstColType = (List<String>) mapData.get("lstColType");

                                    if (lstColType.contains(ImportExcelConstant.NOT_NULL)
                                            && StringUtils.isBlank(valueOfString) && StringUtils.isBlank(valueOfNumber)
                                            && valueOfDate == null) {
                                        // set error
                                        String message = ImportExcelConstant.ERROR_EMPTY
                                                .concat(ImportExcelConstant.SIGN_DASH).concat(cellName);

                                        setListMessageError(objAdd, messageError, message, cellName, errorList, indexData,
                                                currentRow.getRowNum());
                                    } else {
                                        switch (dataType) {
                                        case LONG:
                                            setLongData(lstColType, currentCell, cellName, objAdd, f, messageError,
                                                    errorList, indexData, currentRow, valueOfString, valueOfNumber,
                                                    valueOfDate, IMPORT_TYPE_NOT_CHECK_DATA);
                                            break;
                                        case DOUBLE:
                                            Double valueOfDouble = currentCell.getNumericCellValue();
                                            f.set(objAdd, valueOfDouble);
                                            break;
                                        case INTEGER:
                                            setIntegerData(lstColType, currentCell, cellName, objAdd, f, messageError,
                                                    errorList, indexData, currentRow, valueOfString, valueOfNumber,
                                                    valueOfDate, IMPORT_TYPE_NOT_CHECK_DATA);
                                            break;
                                        case INT:
                                            // special case : value = 1.0
                                            int valueOfInt = valueOfNumber.length() == 0 ? 0
                                                    : (int) Float.parseFloat(valueOfNumber);
                                            f.set(objAdd, String.valueOf(valueOfInt));
                                            break;
                                        case STRING:
                                            setStringData(lstColType, currentCell, cellName, objAdd, f, messageError,
                                                    errorList, indexData, currentRow, valueOfString, valueOfNumber,
                                                    valueOfDate, IMPORT_TYPE_NOT_CHECK_DATA);
                                            break;
                                        case DATE:
                                            setDateData(lstColType, currentCell, cellName, objAdd, f, messageError,
                                                    errorList, indexData, currentRow, valueOfString, valueOfNumber,
                                                    valueOfDate, IMPORT_TYPE_NOT_CHECK_DATA);
                                            break;
                                        case BIGDECIMAL:
                                            setDecimalData(lstColType, currentCell, cellName, objAdd, f, messageError,
                                                    errorList, indexData, currentRow, valueOfString, valueOfNumber,
                                                    valueOfDate, IMPORT_TYPE_NOT_CHECK_DATA);
                                            break;
                                        default:
                                            break;
                                        }

                                        rowEmptyFlag = false;
                                    }
                                } else {
                                    List<String> lstColType = new ArrayList<>();

                                    if (formatColInputs != null && !formatColInputs.isEmpty()) {
                                        // colType = formatColInput.get(col.getColName());
                                        lstColType = formatColInputs.get(col.getColName()) == null ? new ArrayList<>()
                                                : formatColInputs.get(col.getColName());
                                        if (lstColType.contains("NOT_NULL")) {
                                            // set error
                                            String message = ImportExcelConstant.ERROR_EMPTY
                                                    .concat(ImportExcelConstant.SIGN_DASH).concat(cellName);

                                            setListMessageError(objAdd, messageError, message, cellName, errorList,
                                                    indexData, currentRow.getRowNum());
                                            rowEmptyFlag = false;
                                        }
                                    }
                                }
                            }
                        } catch (NumberFormatException ex) {
                            // set error
                            String message = ImportExcelConstant.ERROR_NUMBERRIC.concat(ImportExcelConstant.SIGN_DASH)
                                    .concat(cellName);

                            setListMessageError(objAdd, messageError, message, cellName, errorList, indexData,
                                    currentRow.getRowNum());
                        } catch (IllegalStateException ex) {
                            // set error
                            String message = ImportExcelConstant.EMPTY_STRING;

                            if (ex.getMessage() != null
                                    && ImportExcelConstant.ERROR_PARSE_NUMBER.equalsIgnoreCase(ex.getMessage())) {
                                message = ImportExcelConstant.ERROR_NUMBERRIC.concat(ImportExcelConstant.SIGN_DASH)
                                        .concat(cellName);
                            } else {
                                message = ImportExcelConstant.ERROR_FORMAT.concat(ImportExcelConstant.SIGN_DASH)
                                        .concat(cellName);
                            }
                            setListMessageError(objAdd, messageError, message, cellName, errorList, indexData,
                                    currentRow.getRowNum());
                        }
                    }
                } else {
                    currentRow = sheet.createRow(i);
                    for (ItemColsExcelDto col : cols) {
                        Cell currentCell = currentRow.getCell(col.getColIndex());
                        if (currentCell == null) {
                            currentCell = currentRow.createCell(col.getColIndex());
                        }

                        // set value of fields
                        Field f = mapFields.get(col.getColName().toUpperCase().trim());

                        if (f == null) {
                            String[] args = new String[1];
                            args[0] = col.getColName().toUpperCase().trim();
                            String mes = msg.getMessage("message.error.cannot.find.field", args, locale);
                            throw new NullPointerException(mes);
                        }

                        f.setAccessible(true);
                        
                        if (CollectionUtils.isNotEmpty(headers)) {
                            errorList.put(headers.get(col.getColIndex()), new ArrayList<>());
                        } else {
                            String fieldName = rowHeader.getCell(col.getColIndex()).toString();
                            errorList.put(fieldName, new ArrayList<>());
                        }

                        // currentCell is not null, not blank
                        List<String> lstColType = new ArrayList<>();

                        // set value in object
                        if (formatColInputs != null && !formatColInputs.isEmpty()) {
                            lstColType = formatColInputs.get(col.getColName()) == null ? new ArrayList<>()
                                    : formatColInputs.get(col.getColName());
                        }
                        if (lstColType.contains(ImportExcelConstant.NOT_NULL)) {
                            // set error
                            String message = ImportExcelConstant.ERROR_EMPTY.concat(ImportExcelConstant.SIGN_DASH)
                                    .concat(cellName);

                            setListMessageError(objAdd, messageError, message, cellName, errorList, indexData,
                                    currentRow.getRowNum());
                        }
                    }
                }

                if (!rowEmptyFlag) {
                    objAdd.setSessionKey(sessionKey);

                    if (objAdd.isError()) {
                        objAdd.setIsError(true);
                        listError.add(objAdd);
                        String error = String.join(ImportExcelConstant.EMPTY_STRING, objAdd.getListMessageErrors());
                        objAdd.setMessageError(error);
                    } else {
                        listSuccess.add(objAdd);
                    }
                    list.add(objAdd);
                } else {
                    objAdd.setSessionKey(sessionKey);
                    objAdd.setIsError(true);
                    listError.add(objAdd);
                    listSuccess.add(objAdd);
                    list.add(objAdd);
                }

            } // end white

            // set error
            this.rowErrorMap = errorList;

            // set data
            this.data = list;
            this.dataError = listError;
            this.dataSuccess = listSuccess;

        } catch (Exception ex) {
            logger.error("#setDataFileExcelWithErrorMap#", ex.getMessage());
            this.data = new ArrayList<T>();
            throw new Exception(ex.getMessage());
        }
    }
    
    private Map<String, Object> checkTypeCell(Cell currentCell, ItemColsExcelDto col, DataType dataType,
            Map<String, List<String>> formatColInputs, List<String> lstColType, String valueOfString,
            String valueOfNumber, Date valueOfDate, Integer importType) {
        
        Map<String, Object> mapData = new HashMap<String, Object>();
        
        switch (currentCell.getCellTypeEnum()) {
        case BOOLEAN:
            Boolean valueBoolean = currentCell.getBooleanCellValue();
            switch (dataType) {
            case STRING:
                valueOfString = valueBoolean ? ImportExcelConstant.TRUE : ImportExcelConstant.FALSE;
                break;
            default:
                break;
            }
            break;
        case STRING:
            valueOfString = currentCell.getStringCellValue();
            break;
        case NUMERIC:
            if (DateUtil.isCellDateFormatted(currentCell)) {
                valueOfDate = currentCell.getDateCellValue();
            } else {
                valueOfNumber = String.format("%f",
                        (double) Double.parseDouble(String.valueOf(currentCell.getNumericCellValue())));

                if (valueOfNumber.contains(",")) {
                    valueOfNumber = valueOfNumber.replace(",", ".");
                }
            }
            break;
        case FORMULA:
            switch (currentCell.getCachedFormulaResultTypeEnum()) {
            case NUMERIC:
                valueOfNumber = currentCell.getNumericCellValue() + ImportExcelConstant.EMPTY_STRING;
                break;
            case STRING:
                valueOfString = currentCell.getRichStringCellValue() + ImportExcelConstant.EMPTY_STRING;
                break;
            default:
                break;
            }
            break;
        default:
            break;
        }

        // set value in object
        if (formatColInputs != null && !formatColInputs.isEmpty()) {
            lstColType = formatColInputs.get(col.getColName()) == null ? new ArrayList<>()
                    : formatColInputs.get(col.getColName());

            if (CollectionUtils.isNotEmpty(lstColType)) {
                if (lstColType.contains(ImportExcelConstant.STRING)) {
                    DataFormatter formatter = new DataFormatter();
                    valueOfString = formatter.formatCellValue(currentCell);
                } else if (lstColType.contains(ImportExcelConstant.NUMBER)) {
                    valueOfNumber = String
                            .valueOf((int) Double.parseDouble(String.valueOf(currentCell.getNumericCellValue())));
                } else if (lstColType.contains(ImportExcelConstant.PERCENT)) {
                    valueOfString = String.valueOf(currentCell.getNumericCellValue() * 100);
                } else if (lstColType.contains(ImportExcelConstant.DATE)) {
                    valueOfString = currentCell.toString();
                }
            }
        }

        if (importType.equals(IMPORT_TYPE_CHECK_DATA)) {
            if (valueOfDate != null) {
                valueOfString = valueOfDate.toString();
            } else {
                valueOfString = valueOfString.trim();
            }
        }
        
        mapData.put("valueOfString", valueOfString);
        mapData.put("valueOfNumber", valueOfNumber);
        mapData.put("valueOfDate", valueOfDate);
        mapData.put("lstColType", lstColType);
        
        return mapData;
    }
    
    private void setLongData(List<String> lstColType, Cell currentCell, String cellName, T objAdd, Field f,
            List<String> messageError, Map<String, List<Integer>> errorList, Integer indexData, Row currentRow,
            String valueOfString, String valueOfNumber, Date valueOfDate, Integer importType) throws Exception {
        Double numberLong = Double.parseDouble(valueOfNumber);
        if (numberLong % 1 != 0) {
            // set error
            String message = ImportExcelConstant.ERROR_INTEGER_TYPE.concat(ImportExcelConstant.SIGN_DASH)
                    .concat(cellName);

            setListMessageError(objAdd, messageError, message, cellName, errorList, indexData, currentRow.getRowNum());
        } else {
            valueOfNumber = String.valueOf(numberLong.longValue());
            Long valueOfLong = valueOfNumber.length() == 0 ? null : Long.parseLong(valueOfNumber);

            if (CollectionUtils.isNotEmpty(lstColType)) {
                for (String colType : lstColType) {
                    if (!StringUtils.isBlank(colType) && !ImportExcelConstant.NOT_NULL.equalsIgnoreCase(colType)) {
                        try {
                            String[] types = colType.split("=");
                            if (!ImportExcelConstant.STRING.equalsIgnoreCase(types[0])) {
                                if (ImportExcelConstant.MAX_LENGTH.equalsIgnoreCase(types[0])) {
                                    handleMaxLength(types, cellName, objAdd, f, messageError, errorList, indexData,
                                            currentRow, DataType.LONG, valueOfString, valueOfNumber, valueOfDate,
                                            importType);
                                } else if (ImportExcelConstant.MIN_LENGTH.equalsIgnoreCase(types[0])) {
                                    handleMinLength(types, cellName, objAdd, f, messageError, errorList, indexData,
                                            currentRow, DataType.LONG, valueOfString, valueOfNumber, valueOfDate,
                                            importType);
                                } else if (ImportExcelConstant.FORMAT.equalsIgnoreCase(types[0])) {
                                    handleFormat(types, cellName, objAdd, f, messageError, errorList, indexData,
                                            currentRow, DataType.LONG, valueOfString, valueOfNumber, valueOfDate,
                                            importType);
                                }
                            }
                        } catch (Exception e) {
                            // set error
                            objAdd.setIsError(true);

                            // set error
                            String message = ImportExcelConstant.ERROR_FORMAT.concat(ImportExcelConstant.SIGN_DASH)
                                    .concat(cellName);

                            setListMessageError(objAdd, messageError, message, cellName, errorList, indexData,
                                    currentRow.getRowNum());
                        }
                    } else if (StringUtils.isBlank(colType)) {
                        if (importType.equals(IMPORT_TYPE_CHECK_DATA)) {
                            f.set(objAdd, valueOfLong);
                        } else {
                            f.set(objAdd, valueOfString);
                        }
                    } else {
                        if (importType.equals(IMPORT_TYPE_CHECK_DATA)) {
                            f.set(objAdd, valueOfLong);
                        } else {
                            f.set(objAdd, valueOfString);
                        }
                    }
                }
            } else {
                if (importType.equals(IMPORT_TYPE_CHECK_DATA)) {
                    f.set(objAdd, valueOfLong);
                } else {
                    f.set(objAdd, valueOfString);
                }
            }
        }
    }

    private void setIntegerData(List<String> lstColType, Cell currentCell, String cellName, T objAdd, Field f,
            List<String> messageError, Map<String, List<Integer>> errorList, Integer indexData, Row currentRow,
            String valueOfString, String valueOfNumber, Date valueOfDate, Integer importType) throws Exception {
        Double numberInteger = Double.parseDouble(valueOfNumber);
        if (numberInteger % 1 != 0) {
            // set error
            String message = ImportExcelConstant.ERROR_INTEGER_TYPE.concat(ImportExcelConstant.SIGN_DASH)
                    .concat(cellName);

            setListMessageError(objAdd, messageError, message, cellName, errorList, indexData, currentRow.getRowNum());
        } else {
            valueOfNumber = String.valueOf(numberInteger.intValue());

            Integer valueOfInterger = valueOfNumber.length() == 0 ? null : Integer.parseInt(valueOfNumber);
            if (importType.equals(IMPORT_TYPE_CHECK_DATA)) {
                f.set(objAdd, valueOfInterger);
            } else {
                f.set(objAdd, valueOfString);
            }
        }
    }

    private void setStringData(List<String> lstColType, Cell currentCell, String cellName, T objAdd, Field f,
            List<String> messageError, Map<String, List<Integer>> errorList, Integer indexData, Row currentRow,
            String valueOfString, String valueOfNumber, Date valueOfDate, Integer importType) throws Exception {
        if (CellType.NUMERIC.equals(currentCell.getCellTypeEnum())) {
            if (DateUtil.isCellDateFormatted(currentCell)) {
                valueOfNumber = currentCell.toString();
            } else {
                valueOfNumber = String.format("%.0f",
                        (double) Double.parseDouble(String.valueOf(currentCell.getNumericCellValue())));
            }
            f.set(objAdd, valueOfNumber);

            valueOfString = valueOfNumber;
        }
        if (CollectionUtils.isNotEmpty(lstColType)) {
            for (String colType : lstColType) {
                if (!ImportExcelConstant.NOT_NULL.equalsIgnoreCase(colType) && !StringUtils.isBlank(colType)) {
                    try {
                        String[] types = colType.split("=");
                        if (!ImportExcelConstant.STRING.equalsIgnoreCase(types[0])) {
                            if (ImportExcelConstant.MAX_LENGTH.equalsIgnoreCase(types[0])) {
                                handleMaxLength(types, cellName, objAdd, f, messageError, errorList, indexData,
                                        currentRow, DataType.STRING, valueOfString, valueOfNumber, valueOfDate,
                                        importType);
                            } else if (ImportExcelConstant.MIN_LENGTH.equalsIgnoreCase(types[0])) {
                                handleMinLength(types, cellName, objAdd, f, messageError, errorList, indexData,
                                        currentRow, DataType.STRING, valueOfString, valueOfNumber, valueOfDate,
                                        importType);
                            } else if (ImportExcelConstant.FORMAT.equalsIgnoreCase(types[0])) {
                                handleFormat(types, cellName, objAdd, f, messageError, errorList, indexData, currentRow,
                                        DataType.STRING, valueOfString, valueOfNumber, valueOfDate, importType);
                            }
                        }
                    } catch (Exception e) {
                        // set error
                        objAdd.setIsError(true);

                        // set error
                        String message = ImportExcelConstant.ERROR_FORMAT.concat(ImportExcelConstant.SIGN_DASH)
                                .concat(cellName);

                        setListMessageError(objAdd, messageError, message, cellName, errorList, indexData,
                                currentRow.getRowNum());
                    }
                } else if (StringUtils.isBlank(colType)) {
                    f.set(objAdd, valueOfString);
                } else {
                    f.set(objAdd, valueOfString);
                }
            }
        } else {
            f.set(objAdd, valueOfString);
        }
    }

    @SuppressWarnings("unused")
	private void setDateData(List<String> lstColType, Cell currentCell, String cellName, T objAdd, Field f,
            List<String> messageError, Map<String, List<Integer>> errorList, Integer indexData, Row currentRow,
            String valueOfString, String valueOfNumber, Date valueOfDate, Integer importType) throws Exception {
        if (CollectionUtils.isNotEmpty(lstColType)) {
            for (String colType : lstColType) {
                if (!StringUtils.isBlank(colType) && !ImportExcelConstant.NOT_NULL.equalsIgnoreCase(colType)
                        && !ImportExcelConstant.DATE.equalsIgnoreCase(colType)) {
                    try {
                        if (valueOfDate == null) {
                            Date date = null;
                            SimpleDateFormat sdf = new SimpleDateFormat(colType);
                            sdf.setLenient(false);
                            date = sdf.parse(currentCell.toString());
                            if (!currentCell.toString().equals(sdf.format(date))) {
                                date = null;
                            }
                            
                            try {
                            	// 2021 06 02 - LocLT - fix Bug #43957 - format dd/MM/yyyy limit year 4 digits
								if (date.getYear() > 9999) {
	                                date = null;
								}
							} catch (Exception e) {
								// TODO: handle exception
							}
                            if (date == null) {
                                // set error
                                String message = ImportExcelConstant.ERROR_FORMAT_DATE_TYPE
                                        .concat(ImportExcelConstant.SIGN_DASH).concat(cellName)
                                        .concat(ImportExcelConstant.COMMA).concat(colType);

                                setListMessageError(objAdd, messageError, message, cellName, errorList, indexData,
                                        currentRow.getRowNum());
                                
                                if (importType.equals(IMPORT_TYPE_NOT_CHECK_DATA)) {
                                    f.set(objAdd, valueOfString);
                                }
                            } else {
                                if (importType.equals(IMPORT_TYPE_CHECK_DATA)) {
                                    valueOfDate = DateUtils.formatStringToDate(currentCell.toString(), colType);
                                    f.set(objAdd, valueOfDate);
                                } else {
                                    sdf = new SimpleDateFormat(colType);
                                    sdf.setLenient(false);
                                    DataFormatter df = new DataFormatter();
                                    if(colType == "M/d/yyyy") {
                                        df.addFormat("M/d/yyyy", new java.text.SimpleDateFormat("M/d/yyyy"));//change default format US
                                    }
                                    String cellValue=df.formatCellValue(currentCell);
                                    date = sdf.parse(cellValue);
                                    if (!currentCell.toString().equals(sdf.format(date))) {
                                        date = null;
                                    }
                                    if (date == null) {
                                        // set error
                                        String message = ImportExcelConstant.ERROR_FORMAT_DATE_TYPE
                                                .concat(ImportExcelConstant.SIGN_DASH).concat(cellName)
                                                .concat(ImportExcelConstant.COMMA).concat(colType);

                                        setListMessageError(objAdd, messageError, message, cellName, errorList, indexData,
                                                currentRow.getRowNum());
                                        
                                        if (importType.equals(IMPORT_TYPE_NOT_CHECK_DATA)) {
                                            f.set(objAdd, valueOfString);
                                        }
                                    } else {
                                        if (importType.equals(IMPORT_TYPE_CHECK_DATA)) {
                                            valueOfDate = DateUtils.formatStringToDate(currentCell.toString(), colType);
                                            f.set(objAdd, valueOfDate);
                                        } else {
                                            f.set(objAdd, valueOfString);
                                        }
                                    }
                                }
                            }
                        } else {
                            if (importType.equals(IMPORT_TYPE_CHECK_DATA)) {
                                f.set(objAdd, valueOfDate);
                            } else {
                                if (valueOfDate != null) {   
                                    Date date = null;
                                    SimpleDateFormat sdf = new SimpleDateFormat(colType);
                                    sdf.setLenient(false);
                                    DataFormatter df = new DataFormatter();
                                    if(colType == "M/d/yyyy") {
                                        df.addFormat("M/d/yyyy", new java.text.SimpleDateFormat("M/d/yyyy"));//change default format US
                                    }
                                    String cellValue=df.formatCellValue(currentCell);
                                    date = sdf.parse(cellValue);
                               	 	valueOfString = sdf.format(date); 
                                    if (!cellValue.equals(sdf.format(date))) {
                                        date = null;
                                    }
                                    if (date == null) {
                                        // set error
                                        String message = ImportExcelConstant.ERROR_FORMAT_DATE_TYPE
                                                .concat(ImportExcelConstant.SIGN_DASH).concat(cellName)
                                                .concat(ImportExcelConstant.COMMA).concat(colType);

                                        setListMessageError(objAdd, messageError, message, cellName, errorList, indexData,
                                                currentRow.getRowNum());
                                        
                                        if (importType.equals(IMPORT_TYPE_NOT_CHECK_DATA)) {
                                            f.set(objAdd, valueOfString);
                                        }
                                    } else {
                                        if (importType.equals(IMPORT_TYPE_CHECK_DATA)) {
                                            valueOfDate = DateUtils.formatStringToDate(currentCell.toString(), colType);
                                            f.set(objAdd, valueOfDate);
                                        } else {
                                            f.set(objAdd, valueOfString);
                                        }
                                    }
                                }else {
                                    f.set(objAdd, valueOfString);
                                }
                            }
                        }
                    } catch (Exception e) {
                        // set error
						String message = ImportExcelConstant.ERROR_FORMAT_DATE.concat(ImportExcelConstant.SIGN_DASH)
								.concat(cellName);
						// .concat(ImportExcelConstant.COMMA).concat(colType);

                        setListMessageError(objAdd, messageError, message, cellName, errorList, indexData,
                                currentRow.getRowNum());

                        if (importType.equals(IMPORT_TYPE_NOT_CHECK_DATA)) {
                            f.set(objAdd, valueOfString);
                        }
                    }
                } else if (ImportExcelConstant.NOT_NULL.equalsIgnoreCase(colType) && lstColType.size() == 1) {
                    try {
                        colType = FORMAT_DATE_DEFAULT;
                        if (valueOfDate == null) {
                            Date date = null;
                            SimpleDateFormat sdf = new SimpleDateFormat(colType);
                            sdf.setLenient(false);
                            date = sdf.parse(currentCell.toString());
                            if (!currentCell.toString().equals(sdf.format(date))) {
                                date = null;
                            }
                            if (date == null) {
                                // set error
                                String message = ImportExcelConstant.ERROR_FORMAT_DATE_TYPE
                                        .concat(ImportExcelConstant.SIGN_DASH).concat(cellName)
                                        .concat(ImportExcelConstant.COMMA).concat(colType);

                                setListMessageError(objAdd, messageError, message, cellName, errorList, indexData,
                                        currentRow.getRowNum());
                                
                                if (importType.equals(IMPORT_TYPE_NOT_CHECK_DATA)) {
                                    f.set(objAdd, valueOfString);
                                }
                            } else {
                                if (importType.equals(IMPORT_TYPE_CHECK_DATA)) {
                                    valueOfDate = DateUtils.formatStringToDate(currentCell.toString(), colType);
                                    f.set(objAdd, valueOfDate);
                                } else {
                                    f.set(objAdd, valueOfString);
                                }
                            }
                        } else {
                            if (importType.equals(IMPORT_TYPE_CHECK_DATA)) {
                                f.set(objAdd, valueOfDate);
                            } else {
                                f.set(objAdd, valueOfString);
                            }
                        }
                    } catch (Exception e) {
                        // set error
                        String message = ImportExcelConstant.ERROR_FORMAT_DATE.concat(ImportExcelConstant.SIGN_DASH)
                                .concat(cellName);

                        setListMessageError(objAdd, messageError, message, cellName, errorList, indexData,
                                currentRow.getRowNum());

                        if (importType.equals(IMPORT_TYPE_NOT_CHECK_DATA)) {
                            f.set(objAdd, valueOfString);
                        }
                    }
                }
            }
        } else {
            try {
                String colType = FORMAT_DATE_DEFAULT;
                if (valueOfDate == null) {
                    Date date = null;
                    SimpleDateFormat sdf = new SimpleDateFormat(colType);
                    sdf.setLenient(false);
                    date = sdf.parse(currentCell.toString());
                    if (!currentCell.toString().equals(sdf.format(date))) {
                        date = null;
                    }
                    if (date == null) {
                        // set error
                        String message = ImportExcelConstant.ERROR_FORMAT_DATE_TYPE
                                .concat(ImportExcelConstant.SIGN_DASH).concat(cellName)
                                .concat(ImportExcelConstant.COMMA).concat(colType);

                        setListMessageError(objAdd, messageError, message, cellName, errorList, indexData,
                                currentRow.getRowNum());
                        
                        if (importType.equals(IMPORT_TYPE_NOT_CHECK_DATA)) {
                            f.set(objAdd, valueOfString);
                        }
                    } else {
                        if (importType.equals(IMPORT_TYPE_CHECK_DATA)) {
                            valueOfDate = DateUtils.formatStringToDate(currentCell.toString(), colType);
                            f.set(objAdd, valueOfDate);
                        } else {
                            f.set(objAdd, valueOfString);
                        }
                    }
                } else {
                    if (importType.equals(IMPORT_TYPE_CHECK_DATA)) {
                        f.set(objAdd, valueOfDate);
                    } else {
                        f.set(objAdd, valueOfString);
                    }
                }
            } catch (Exception e) {
                // set error
                String message = ImportExcelConstant.ERROR_FORMAT_DATE.concat(ImportExcelConstant.SIGN_DASH)
                        .concat(cellName);

                setListMessageError(objAdd, messageError, message, cellName, errorList, indexData,
                        currentRow.getRowNum());
                
                if (importType.equals(IMPORT_TYPE_NOT_CHECK_DATA)) {
                    f.set(objAdd, valueOfString);
                }
            }
        }
    }

    private void setDecimalData(List<String> lstColType, Cell currentCell, String cellName, T objAdd, Field f,
            List<String> messageError, Map<String, List<Integer>> errorList, Integer indexData, Row currentRow,
            String valueOfString, String valueOfNumber, Date valueOfDate, Integer importType) throws Exception {
        // Create a DecimalFormat that fits your
        DecimalFormatSymbols symbols = new DecimalFormatSymbols();
        symbols.setGroupingSeparator(',');
        symbols.setDecimalSeparator('.');
        String pattern = "#,##0.0###";
        DecimalFormat decimalFormat = new DecimalFormat(pattern, symbols);
        decimalFormat.setParseBigDecimal(true);
        BigDecimal valueOfBigDecimal = BigDecimal.valueOf(currentCell.getNumericCellValue());
        f.set(objAdd, valueOfBigDecimal);
    }

    private void handleMaxLength(String[] types, String cellName, T objAdd, Field f, List<String> messageError,
            Map<String, List<Integer>> errorList, Integer indexData, Row currentRow, DataType dataType,
            String valueOfString, String valueOfNumber, Date valueOfDate, Integer importType) throws Exception {
        int length = Integer.parseInt(types[1]);
        if (StringUtils.isNotBlank(valueOfString)) {
            if (valueOfString.length() > length) {
                // set error
                String message = ImportExcelConstant.ERROR_MAX_LENGTH.concat(ImportExcelConstant.SIGN_DASH)
                        .concat(cellName).concat(ImportExcelConstant.COMMA).concat(String.valueOf(length));

                setListMessageError(objAdd, messageError, message, cellName, errorList, indexData,
                        currentRow.getRowNum());
            } else {
                if (importType.equals(IMPORT_TYPE_CHECK_DATA)) {
                    switch (dataType) {
                    case LONG:
                        f.set(objAdd, valueOfNumber);
                        break;
                    case STRING:
                        f.set(objAdd, valueOfString);
                        break;
                    default:
                        f.set(objAdd, valueOfString);
                        break;
                    }
                } else {
                    f.set(objAdd, valueOfString);
                }
            }
        } else {
            f.set(objAdd, ImportExcelConstant.EMPTY_STRING);
        }
    }

    private void handleMinLength(String[] types, String cellName, T objAdd, Field f, List<String> messageError,
            Map<String, List<Integer>> errorList, Integer indexData, Row currentRow, DataType dataType,
            String valueOfString, String valueOfNumber, Date valueOfDate, Integer importType) throws Exception {
        int length = Integer.parseInt(types[1]);
        if (StringUtils.isNotBlank(valueOfString)) {
            if (valueOfString.length() < length) {
                // set error
                String message = ImportExcelConstant.ERROR_MIN_LENGTH.concat(ImportExcelConstant.SIGN_DASH)
                        .concat(cellName).concat(ImportExcelConstant.COMMA).concat(String.valueOf(length));

                setListMessageError(objAdd, messageError, message, cellName, errorList, indexData,
                        currentRow.getRowNum());
            } else {

                if (importType.equals(IMPORT_TYPE_CHECK_DATA)) {
                    switch (dataType) {
                    case LONG:
                        f.set(objAdd, valueOfNumber);
                        break;
                    case STRING:
                        f.set(objAdd, valueOfString);
                        break;
                    default:
                        f.set(objAdd, valueOfString);
                        break;
                    }
                } else {
                    f.set(objAdd, valueOfString);
                }
            }
        } else {
            f.set(objAdd, ImportExcelConstant.EMPTY_STRING);
        }
    }

    private void handleFormat(String[] types, String cellName, T objAdd, Field f, List<String> messageError,
            Map<String, List<Integer>> errorList, Integer indexData, Row currentRow, DataType dataType,
            String valueOfString, String valueOfNumber, Date valueOfDate, Integer importType) throws Exception {
        // use a better name, though
        Pattern p = Pattern.compile(types[1]);
        if (!p.matcher(valueOfString).matches()) {
            // set error
            String message = ImportExcelConstant.ERROR_FORMAT_TYPE.concat(ImportExcelConstant.SIGN_DASH)
                    .concat(cellName).concat(ImportExcelConstant.COMMA).concat(types[1]);

            setListMessageError(objAdd, messageError, message, cellName, errorList, indexData, currentRow.getRowNum());
        } else {
            f.set(objAdd, ImportExcelConstant.EMPTY_STRING);
        }
    }

    /**
     * @description set message error for row data
     * @param listMessageError: List of previously existing error messages of row
     *                          message: The new message will be added to the list
     *                          cellName: Column names in excel file errorList:
     *                          mapping error with cell name indexData: This is the
     *                          index of the data list starting at 1
     *                          indexCurrentRow: This is the index of the excel data
     *                          list: (indexData - 1) + (startRowData - 1)
     */
    @SuppressWarnings("unchecked")
    public void setListMessageError(T objAdd, List<String> listMessageError, String message, String cellName,
            Map<String, List<Integer>> errorList, int indexData, int indexCurrentRow) {
        // set error
        if (!listMessageError.contains(message)) {
//            String messageError = message.concat(ImportExcelConstant.JAVA_BREAK_LINE)
//                    .concat(ImportExcelConstant.HTML_BREAK_LINE);
            String messageError = message.concat(ImportExcelConstant.SIGN_SEMICOLON);
            listMessageError.add(messageError);
        }

        objAdd.setIsError(true);
        objAdd.setListMessageErrors(listMessageError);

        errorList.get(cellName).add(indexCurrentRow + 1);
    }
    
    /**
     * Get rowErrorMap
     * 
     * @return Map<String,List<Integer>>
     * @author hand
     */
    public Map<String, List<Integer>> getRowErrorMap() {
        return rowErrorMap;
    }

    /**
     * Set rowErrorMap
     * 
     * @param rowErrorMap type Map<String,List<Integer>>
     * @return
     * @author hand
     */
    public void setRowErrorMap(Map<String, List<Integer>> rowErrorMap) {
        this.rowErrorMap = rowErrorMap;
    }

    /**
     * @return the fileName
     * @author taitm
     * @date Jun 1, 2020
     */
    public String getFileName() {
        return fileName;
    }

    /**
     * @param fileName the fileName to set
     * @author taitm
     * @date Jun 1, 2020
     */
    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    /**
     * @return the pathFileName
     * @author taitm
     * @date Jun 1, 2020
     */
    public String getPathFileName() {
        return pathFileName;
    }

    /**
     * @param pathFileName the pathFileName to set
     * @author taitm
     * @date Jun 1, 2020
     */
    public void setPathFileName(String pathFileName) {
        this.pathFileName = pathFileName;
    }
}