// 2021-04-06 LocLT Task #40894

package vn.com.unit.ep2p.core.utils;

import java.lang.reflect.Field;
import java.math.BigDecimal;
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

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;

import lombok.Getter;
import lombok.Setter;
import vn.com.unit.common.utils.CommonCollectionUtil;
import vn.com.unit.common.utils.CommonDateUtil;
import vn.com.unit.common.utils.CommonStringUtil;
import vn.com.unit.ep2p.core.constant.ImportExcelConstant;
import vn.com.unit.ep2p.core.dto.ImportCommonDto;
import vn.com.unit.ep2p.core.dto.ItemColsExcelDto;

@SuppressWarnings("rawtypes")
@Getter
@Setter
public class ConstantImportUtils<T extends ImportCommonDto> {
    
    private enum DataType {
        LONG, DOUBLE, INTEGER, STRING, DATE, INT, BIGDECIMAL, FLOAT;
    }
    
	private static final Logger logger = LoggerFactory.getLogger(ConstantImportUtils.class);
	
	private static final String FORMAT_DATE_DEFAULT = "dd/MM/yyyy";
	
    private Map<String, List<Integer>> rowErrorMap = new HashMap<>();

    private String fileName;

    private String pathFileName;

    private List<T> data;

    private List<T> dataError;

    private List<T> dataSuccess;
    
	public List<T> getData() {
		return data;
	}
    
    public void setData(List<T> data) {
        this.data = data;
    }

    public void setDataFileExcelWithErrorMapWithFieldType(Sheet sheet, int startRow, int lastRow,
            List<ItemColsExcelDto> cols, Row rowHeader, Class<T> objDefault, Map<String, List<String>> formatColInputs,
            String sessionKey, MessageSource msg, Locale locale) throws Exception {
        try {
        	if (null == locale) {
        		locale = new Locale ("en", "US");
        	}
        	
            List<T> list = new ArrayList<T>();
            List<T> listError = new ArrayList<T>();
            List<T> listSuccess = new ArrayList<T>();

            Map<String, Field> mapFields = new HashMap<String, Field>();
            // get list fields of class
            Field[] fields = objDefault.getDeclaredFields();
            
            
            // PhatLT : lấy field từ class cha nếu có 
            Field [] fieldSuperClass = null;
            // mọi class đều extend từ Object, nên nếu không có class "cha" thì getSuperclass sẽ lấy được Object class 
            if(objDefault.getSuperclass()!= null && !CommonStringUtil.equalsIgnoreCase("Object", objDefault.getSuperclass().getSimpleName()))
            	fieldSuperClass = objDefault.getSuperclass().getDeclaredFields();
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
            String cellName = CommonStringUtil.EMPTY;

            Integer indexData = 0;
            // loop rows
            for (int i = startRow; i < lastRow; i++) {
                Row currentRow = sheet.getRow(i);
                
                indexData++;
                // T objAdd = (T) objDefault.getClass().newInstance();
                T objAdd = objDefault.newInstance();

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
                            	String mes = "message.error.cannot.find.field = Cannot find the field of Enums.";
                            	try {
                                    String[] args = new String[1];
                                    args[0] = col.getColName().toUpperCase().trim();
                                    mes = "message.error.cannot.find.field = Cannot find the field by column " + args[0] + " of Enums.";
                                    mes = msg.getMessage("message.error.cannot.find.field", args, locale);
								} catch (Exception e) { }

                                throw new NullPointerException(mes);
                            }

                            f.setAccessible(true);
                            String typeFields = f.getType().getSimpleName();
                            typeFields = typeFields.toUpperCase();
                            String dataType = null;
                            
                            try {
                            	dataType = DataType.valueOf(typeFields).toString();
							} catch (Exception e) {
								throw e;
							}
                            
                            // set cell name
                            cellName = rowHeader.getCell(col.getColIndex()).toString();

                            // currentCell is not null, not blank
                            if (currentCell != null && currentCell.getCellTypeEnum() != CellType.BLANK
                                    && CommonStringUtil.isNotBlank(currentCell.toString())) {
                            	rowEmptyFlag = false;
                                String valueOfString = ImportExcelConstant.EMPTY_STRING;
                                String valueOfNumber = ImportExcelConstant.EMPTY_STRING;
                                Date valueOfDate = null;
                                logger.info("CELL TYPE " + currentCell.getCellTypeEnum());

                                // 2021-04-06 LocLT Task #40894
                                // Don't change switch case enum here => fix NoClassDefFoundError
                                String typeEnum = null;
                                try {
                                	typeEnum = currentCell.getCellTypeEnum().toString();
								} catch (Exception e) {
									throw e;
								}
                                
                                // check type cell
                                switch (typeEnum) {
                                case "BOOLEAN":
                                	Boolean valueBoolean = currentCell.getBooleanCellValue();
                                	switch (dataType) {
                                    case "STRING": 
                                    	valueOfString = valueBoolean ? ImportExcelConstant.TRUE : ImportExcelConstant.FALSE;
                                    	break;
                                    default:
                                        break;
                                	}
                                    break;
                                case "STRING":
                                    valueOfString = currentCell.getStringCellValue();
                                    break;
                                case "NUMERIC":
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
                                case "FORMULA":
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

                                    if (CommonCollectionUtil.isNotEmpty(lstColType)) {
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
                                        && CommonStringUtil.isBlank(valueOfString) && CommonStringUtil.isBlank(valueOfNumber)) {
                                    // set error
                                    String message = ImportExcelConstant.ERROR_EMPTY.concat(ImportExcelConstant.SIGN_DASH).concat(cellName);
                                    
                                    setListMessageError(objAdd, messageError, message, cellName, errorList, indexData,
                                            currentRow.getRowNum());
                                } else {
                                    switch (dataType) {
                                    case "LONG":
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
                                            if (CommonCollectionUtil.isNotEmpty(lstColType)) {
                                                for (String colType : lstColType) {
                                                    if (!CommonStringUtil.isBlank(colType)
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
                                                                        if (CommonStringUtil.isNotBlank(valueOfNumber)) {
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
                                                                        if (CommonStringUtil.isNotBlank(valueOfNumber)) {
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
                                                                        if (CommonStringUtil.isNotBlank(valueOfNumber)) {
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
                                                    } else if (CommonStringUtil.isBlank(colType)) {
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
                                    case "DOUBLE":
                                        Double valueOfDouble = currentCell.getNumericCellValue();
                                        //f.set(objAdd, valueOfDouble);
                                        if (CommonCollectionUtil.isNotEmpty(lstColType)) {
                                            for (String colType : lstColType) {
                                                if (!CommonStringUtil.isBlank(colType)
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
                                                                    if (CommonStringUtil.isNotBlank(valueOfNumber)) {
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
                                                                    if (CommonStringUtil.isNotBlank(valueOfNumber)) {
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
                                                                    if (CommonStringUtil.isNotBlank(valueOfNumber)) {
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
                                                } else if (CommonStringUtil.isBlank(colType)) {
                                                	f.set(objAdd, valueOfDouble);
                                                } else {
                                                	f.set(objAdd, valueOfDouble);
                                                }
                                            }
                                        } else {
                                        	f.set(objAdd, valueOfDouble);
                                        }
                                        break;
                                    case "INTEGER":
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
                                    case "INT":
                                        // special case : value = 1.0
                                        int valueOfInt = valueOfNumber.length() == 0 ? 0
                                                : (int) Float.parseFloat(valueOfNumber);
                                        f.set(objAdd, valueOfInt);

                                        break;
                                    case "STRING":
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
                                        if (CommonCollectionUtil.isNotEmpty(lstColType)) {
                                            for (String colType : lstColType) {
                                                if (!CommonStringUtil.isBlank(colType)
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
                                                                    if (CommonStringUtil.isNotBlank(valueOfString)) {
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
                                                                    if (CommonStringUtil.isNotBlank(valueOfString)) {
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
                                                                    if (CommonStringUtil.isNotBlank(valueOfString)) {
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
                                                } else if (CommonStringUtil.isBlank(colType)) {
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
                                    case "DATE":
                                        if (CommonCollectionUtil.isNotEmpty(lstColType)) {
                                            for (String colType : lstColType) {
                                                if (!CommonStringUtil.isBlank(colType)
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
                                                                valueOfDate = CommonDateUtil.formatStringToDate(
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
                                                                valueOfDate = CommonDateUtil.formatStringToDate(
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
                                                        valueOfDate = CommonDateUtil
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
                                    case "BIGDECIMAL":
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
                                        String valueData = null;
                                        //convert data to check length value: exception 4.23423E61
                                       if(CommonStringUtil.isNotEmpty(String.valueOf(currentCell.getNumericCellValue())) &&
                                    		   String.valueOf(currentCell.getNumericCellValue()).indexOf("E") != -1) {
                                    	   valueData = String.format("%.1f", (double) Double
                                                    .parseDouble(String.valueOf(currentCell.getNumericCellValue())));
                                        }
                                        if (CommonCollectionUtil.isNotEmpty(lstColType)) {
                                            for (String colType : lstColType) {
                                                if (!CommonStringUtil.isBlank(colType)
                                                        && !ImportExcelConstant.NOT_NULL.equalsIgnoreCase(colType)) {
                                                    try {
                                                        String[] types = colType.split("=");
                                                        if (!ImportExcelConstant.STRING.equalsIgnoreCase(types[0])) {
                                                            if (ImportExcelConstant.MAX_LENGTH
                                                                    .equalsIgnoreCase(types[0])) {
                                                                int length = Integer.parseInt(types[1]);
                                                                int lengthData = CommonStringUtil.isNotEmpty(valueData)?valueData.length():valueOfBigDecimal.toString().length();
                                                                if (lengthData > length) {
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

}
