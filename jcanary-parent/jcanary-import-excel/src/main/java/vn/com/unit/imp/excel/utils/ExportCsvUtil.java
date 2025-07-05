package vn.com.unit.imp.excel.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.util.CellAddress;
import org.apache.poi.ss.util.CellReference;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import vn.com.unit.imp.excel.constant.CommonConstant;
import vn.com.unit.imp.excel.dto.CellStyleDto;
import vn.com.unit.imp.excel.dto.ItemColsExcelDto;

/**
 * ntr.bang
 * @param <T>
 */
public class ExportCsvUtil<T> {


	private enum DataType {
		LONG, DOUBLE, INTEGER, STRING, DATE, TIMESTAMP, INT, BIGDECIMAL, BOOLEAN, BYTE;
	}
	
	private static String EMP_STRING = "";

	/** List<T> */
	private List<T> data;

	/**
	 * Get data
	 * 
	 * @return List<T>
	 * @author ntr.bang
	 */
	public List<T> getData() {
		return data != null ? new ArrayList<>(data) : null;
	}

	/**
	 * Set data
	 * 
	 * @param data
	 *            type List<T>
	 * @return
	 * @author ntr.bang
	 */
	public void setData(List<T> data) {
		this.data = data != null ? new ArrayList<>(data) : null;
	}

	private SXSSFWorkbook workbook;

	private XSSFWorkbook workbookXS;

	public XSSFWorkbook getWorkbookXS() {
		return workbookXS;
	}

	public void setWorkbookXS(XSSFWorkbook workbookXS) {
		this.workbookXS = workbookXS;
	}

	/**
	 * Get workbook
	 * 
	 * @return SXSSFWorkbook
	 * @author ntr.bang
	 */
	public SXSSFWorkbook getWorkbook() {
		return workbook;
	}

	/**
	 * Set workbook
	 * 
	 * @param workbook
	 *            type SXSSFWorkbook
	 * @return
	 * @author phunghn
	 */
	public void setWorkbook(SXSSFWorkbook workbook) {
		this.workbook = workbook;
	}

	private static final Logger logger = LoggerFactory.getLogger(ExportCsvUtil.class);


    public XSSFWorkbook getXSSFWorkbook(String template) throws Exception {
        File file = new File(template);
        FileInputStream fileInputStream = new FileInputStream(file);
        XSSFWorkbook xssfWorkbook = new XSSFWorkbook(fileInputStream);
        return xssfWorkbook;

    }
	
    /**
     * doExportExcelAndCsv
     * @param xssfWorkbook
     * @param sheetIndex
     * @param locale
     * @param listData
     * @param objDto
     * @param cols
     * @param datePattern
     * @param cellReference
     * @param mapColFormat
     * @param mapColStyle
     * @param mapColDefaultValue
     * @param colorToTal
     * @param isAllBorder
     * @param templateName
     * @param exportFile
     * @param path
     * @return
     * @throws Exception
     */
    @SuppressWarnings("rawtypes")
    public ResponseEntity doExportExcelAndCsv(XSSFWorkbook xssfWorkbook, Integer sheetIndex, Locale locale,
            List<T> listData, Class<T> objDto, List<ItemColsExcelDto> cols, String datePattern, String cellReference,
            Map<String, String> mapColFormat, Map<String, CellStyle> mapColStyle,
            Map<String, Object> mapColDefaultValue, XSSFColor colorToTal, boolean isAllBorder,
            String templateName, boolean exportFile, String path) throws Exception {
    	logger.info("");
        // create sheet of file excel
        XSSFSheet sxssfSheet = null;
        if (sheetIndex == null) {
            sxssfSheet = xssfWorkbook.getSheetAt(0);
        } else {
            sxssfSheet = xssfWorkbook.getSheetAt(sheetIndex);
        }

        if (sxssfSheet == null) {
            throw new Exception("Cannot find Sheet!!!");
        }
        
        CellReference landMark = new CellReference(cellReference);
        int startRow = landMark.getRow();

        // cellStyleDto
        CellStyleDto cellStyleDto = new CellStyleDto(xssfWorkbook, "Times New Roman", isAllBorder, datePattern);

        // cellStyleDtoForTotal
        CellStyleDto cellStyleDtoForTotal = new CellStyleDto(cellStyleDto, colorToTal);

        // field of objDto
        Map<String, Field> mapFields = new HashMap<>();
        Field[] fields = populateFields(objDto);
        for (Field f : fields) {
            mapFields.put(f.getName().toUpperCase(), f);
        }

        if (listData != null) {
            if (listData.size() > 0) {
                int dataSize = listData.size();
                for (int i = 0; i < listData.size(); i++) {
                    if (colorToTal != null && i == dataSize - 1) {
                        // Do fill data
                        fillDataForCell(sxssfSheet, listData, objDto, cols, mapColFormat, mapColStyle, mapColDefaultValue, mapFields, startRow, i,
                                cellStyleDtoForTotal, dataSize, true);
                    } else {
                        // Do fill data
                        fillDataForCell(sxssfSheet, listData, objDto, cols, mapColFormat, mapColStyle, mapColDefaultValue, mapFields, startRow, i,
                                cellStyleDto, dataSize, false);
                    }
                    startRow += 1;
                }
            }
        }

        // set point view and active cell default
        sxssfSheet.setActiveCell(new CellAddress(landMark));
        sxssfSheet.getCTWorksheet().getSheetViews().getSheetViewArray(0).setTopLeftCell("A1");
        
        if (exportFile) {
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            SimpleDateFormat formatDateExport = new SimpleDateFormat("yyyyMMdd_HHmmss");
            String currentDate = formatDateExport.format(new Date());
            xssfWorkbook.write(out);
            //update to service
    		
            String pathFile = Paths.get(path,CommonConstant.PATH_FILE_EXPORT, CommonConstant.PATH_FILE_DLVN,
            		templateName.replace(CommonConstant.TYPE_EXCEL, "") + "_" + currentDate + CommonConstant.TYPE_EXCEL).toString();
            
            File file = new File(pathFile);
            try (OutputStream os = new FileOutputStream(file)) {
                xssfWorkbook.write(os);
            }
            
            String pathCsvFile = Paths.get(path,CommonConstant.PATH_FILE_EXPORT, CommonConstant.PATH_FILE_DLVN,
            		templateName.replace(CommonConstant.TYPE_EXCEL, "") + "_" + currentDate + CommonConstant.TYPE_CSV).toString();
            // Convert from Excel to CSV
            convertExcelToCSV(sxssfSheet, pathCsvFile);
            
            
            String pathOut = (File.separator + Paths.get(CommonConstant.PATH_FILE_EXPORT, CommonConstant.PATH_FILE_DLVN
            		, templateName.replace(CommonConstant.TYPE_EXCEL, "") + "_" + currentDate + CommonConstant.TYPE_CSV).toString()).replace("\\", "/");
            
            ByteArrayInputStream in = new ByteArrayInputStream(out.toByteArray());               
            HttpHeaders headers = new HttpHeaders();
            headers.add(CommonConstant.CONTENT_DISPOSITION, CommonConstant.ATTCHMENT_FILENAME + templateName.replace(CommonConstant.TYPE_EXCEL, "") + "_"
                    + currentDate + CommonConstant.TYPE_CSV + "\""+";path="+pathOut);
            
            headers.add("Access-Control-Expose-Headers", CommonConstant.CONTENT_DISPOSITION);
            
             return ResponseEntity
                        .ok()
                        .eTag(pathOut)
                        .headers(headers)
                        .contentType(MediaType.parseMediaType(CommonConstant.CONTENT_TYPE_CSV))
                        .body(new InputStreamResource(in));
        }
        return null;
    }
    
    private Field[] populateFields(Class<?> cls) {
		Field[] fieldsSuper = cls.getSuperclass().getDeclaredFields();
		Field[] fieldsChild = cls.getDeclaredFields();
		int superLength = fieldsSuper.length;
		int childLength = fieldsChild.length;
		Field[] fields = new Field[superLength + childLength];
		System.arraycopy(fieldsSuper, 0, fields, 0, superLength);
		System.arraycopy(fieldsChild, 0, fields, superLength, childLength);
		return fields;
	}
    
    public void fillDataForCell(XSSFSheet sxssfSheet, List<T> listData, Class<T> objDto, List<ItemColsExcelDto> cols,
            Map<String, String> mapColFormat, Map<String, CellStyle> mapColStyle,
            Map<String, Object> mapColDefaultValue, Map<String, Field> mapFields, int rowIndex, int dataIndex,
            CellStyleDto cellStyleDto, int dataSize, boolean fillColor)
            throws IllegalArgumentException, IllegalAccessException {

        XSSFRow row = sxssfSheet.createRow(rowIndex);

        T excelDto = listData.get(dataIndex);
        if (excelDto != null) {
            // set value to map
            Field[] headerFields = populateFields(objDto);
            Map<String, Object> mapValueFields = new HashMap<String, Object>();
            for (Field field : headerFields) {
                if (!field.isAccessible()) {
                    field.setAccessible(true);
                }
                mapValueFields.put(field.getName().toUpperCase(), field.get(excelDto));
            }
            // begin fill to cell
            for (ItemColsExcelDto col : cols) {

                // data type of field
                XSSFCell cell = row.createCell(col.getColIndex());
                // col.getColName());
                Field field = mapFields.get(col.getColName().toUpperCase());
                String typeFields = field.getType().getSimpleName().toUpperCase();
                DataType dataType = DataType.valueOf(typeFields);

                String formatType = null;
                if (null != mapColFormat && mapColFormat.size() != 0) {
                    formatType = mapColFormat.get(col.getColName());
                }
                
                // col value
                Object colValue = mapValueFields.get(col.getColName().toUpperCase());

                if (mapColDefaultValue != null && mapColDefaultValue.containsKey(col.getColName())) {
                    if (null == mapColDefaultValue.get(col.getColName())
                            || EMP_STRING.equals(mapColDefaultValue.get(col.getColName()))) {
                        cell.setCellValue(EMP_STRING);
                    } else {
                        String val = String.valueOf(mapColDefaultValue.get(col.getColName()));
                        cell.setCellValue(val);
                    }
                }
                
                switch (dataType) {
                case LONG:
                    if (colValue != null) {
                        Long valueOfLong = Long.parseLong(colValue.toString());
                        cell.setCellValue(valueOfLong);
                        cell.setCellType(CellType.NUMERIC);
                    }
                    
                    cell.setCellStyle(cellStyleDto.getCellStyleLeft());
                    if (mapColStyle != null && mapColStyle.containsKey(col.getColName())) {
                        cell.setCellStyle(mapColStyle.get(col.getColName()));
                    }
                    break;
                case INTEGER:
                    if (colValue != null) {
                        Integer valueOfInteger = Integer.parseInt(colValue.toString());
                        cell.setCellValue(valueOfInteger);
                        cell.setCellType(CellType.NUMERIC);
                    }
                    
                    cell.setCellStyle(cellStyleDto.getCellStyleRight());
                    if (col.getColName().equals("ROWNUM") || col.getColName().equals("NO")) {
                        cell.setCellStyle(cellStyleDto.getCellStyleCenter());
                    }
                    
                    if (mapColStyle != null && mapColStyle.containsKey(col.getColName())) {
                        cell.setCellStyle(mapColStyle.get(col.getColName()));
                    }
                    break;
                case INT:
                    if (col.getColName().equals("ROWNUM") || col.getColName().equals("NO")) {
                        if (colValue != null && !fillColor) {
                            int valueOfInt = Integer.parseInt(colValue.toString());
                            cell.setCellValue(valueOfInt);
                        }
                        
                        cell.setCellStyle(cellStyleDto.getCellStyleCenter());
                        if (mapColStyle != null && mapColStyle.containsKey(col.getColName())) {
                            cell.setCellStyle(mapColStyle.get(col.getColName()));
                        }
                    } else {
                        if (colValue != null) {
                            int valueOfInt = Integer.parseInt(colValue.toString());
                            cell.setCellValue(valueOfInt);
                        }
                        
                        cell.setCellStyle(cellStyleDto.getCellStyleRight());
                        if (mapColStyle != null && mapColStyle.containsKey(col.getColName())) {
                            cell.setCellStyle(mapColStyle.get(col.getColName()));
                        }
                    }

                    break;
                case DOUBLE:
                    if (colValue != null) {
                        Double valueOfDouble = Double.parseDouble(colValue.toString());

                        if (valueOfDouble % 1 > 0) {
                            // format "0.00"
                            if (null != formatType && formatType.equalsIgnoreCase(CommonConstant.PERCENT)) {
                                cell.setCellValue(valueOfDouble / 100);
                                cell.setCellStyle(cellStyleDto.getCellStyleRightDouble1WithPercent());
                            } else if (null != formatType
                                    && formatType.equalsIgnoreCase(CommonConstant.DOUBLE_SHOW_ALL)) {
                                cell.setCellValue(valueOfDouble);
                                cell.setCellStyle(cellStyleDto.getCellStyleRightDouble3());
                            } else {
                                cell.setCellValue(valueOfDouble);
                                cell.setCellStyle(cellStyleDto.getCellStyleRightDouble1());
                            }
                            
                            if (mapColStyle != null && mapColStyle.containsKey(col.getColName())) {
                                cell.setCellStyle(mapColStyle.get(col.getColName()));
                            }
                        } else {
                            // format "0"
                            if (null != formatType && formatType.equalsIgnoreCase(CommonConstant.PERCENT)) {
                                cell.setCellValue(valueOfDouble / 100);
                                cell.setCellStyle(cellStyleDto.getCellStyleRightDouble2WithPercent());
                            } else {
                                cell.setCellValue(valueOfDouble);
                                cell.setCellStyle(cellStyleDto.getCellStyleRightDouble2());
                            }
                            
                            if (mapColStyle != null && mapColStyle.containsKey(col.getColName())) {
                                cell.setCellStyle(mapColStyle.get(col.getColName()));
                            }
                        }
                    } else {
                        cell.setCellStyle(cellStyleDto.getCellStyleBoder());
                        
                        if (mapColStyle != null && mapColStyle.containsKey(col.getColName())) {
                            cell.setCellStyle(mapColStyle.get(col.getColName()));
                        }
                    }

                    break;
                case BIGDECIMAL:
                    if (colValue != null) {                   	
                        BigDecimal valueBigdecimal = (BigDecimal) colValue;
                    	//update 0 -> -
                       
                    	if(((valueBigdecimal.intValue() == 0) || StringUtils.isBlank(colValue.toString())) && (valueBigdecimal.doubleValue() < new Double("-0.99") || valueBigdecimal.doubleValue() == new Double("0.0"))) {
                    		colValue = new BigDecimal(0).intValue();
                    		cell.setCellValue(Integer.parseInt(colValue.toString()));
                            cell.setCellStyle(cellStyleDto.getCellStyleRightBigDecimal3());
                            break;
                    	}
                    	//
                        if (valueBigdecimal != null) {                        	
                            cell.setCellValue(valueBigdecimal.doubleValue());
                            if (cellStyleDto.getIsFormatFinance() == 0) { // Normal
                                // format
                                // number
                                if (valueBigdecimal.doubleValue() % 1 != 0) {
                                    // format "#,##0.00"
                                    cell.setCellStyle(cellStyleDto.getCellStyleRightBigDecimal1());
                                } else {
                                    // format "#,##0"
                                    cell.setCellStyle(cellStyleDto.getCellStyleRightBigDecimal2());
                                }
                            } else { // Format number with Finance
                                     // money
                                cell.setCellStyle(cellStyleDto.getCellStyleFinanceFormat());
                            }
                            
                            if (mapColStyle != null && mapColStyle.containsKey(col.getColName())) {
                                cell.setCellStyle(mapColStyle.get(col.getColName()));
                            }
                        }
                    } else {
                        cell.setCellStyle(cellStyleDto.getCellStyleBoder());
                        
                        if (mapColStyle != null && mapColStyle.containsKey(col.getColName())) {
                            cell.setCellStyle(mapColStyle.get(col.getColName()));
                        }
                    }

                    break;
                case DATE:
                    if (colValue != null) {
                        cell.setCellValue((Date) colValue);
                    }
                    cell.setCellStyle(cellStyleDto.getCellStyleDateCenter());
                    try {
                    	if (formatType != null) {
                        	SXSSFWorkbook wb = cellStyleDto.getSxssfWorkbook();
                        	CellStyle cellStyle = wb.createCellStyle();
                        	cellStyle.cloneStyleFrom(cellStyleDto.getCellStyleDateCenter());
                        	CreationHelper createHelper = wb.getCreationHelper();
                        	
                        	short fmt = createHelper.createDataFormat().getFormat(formatType);
                        	cellStyle.setDataFormat(fmt);
                        	
                        	cell.setCellStyle(cellStyle);
                        }
					} catch (Exception e) {
						logger.error("Ex: ", e);
					}
                    if (mapColStyle != null && mapColStyle.containsKey(col.getColName())) {
                        cell.setCellStyle(mapColStyle.get(col.getColName()));
                    }
                    
                    break;
                case TIMESTAMP:
                    if (colValue != null) {
                        cell.setCellValue((Date) colValue);
                    }
                    cell.setCellStyle(cellStyleDto.getCellStyleDateCenter());
                    
                    if (mapColStyle != null && mapColStyle.containsKey(col.getColName())) {
                        cell.setCellStyle(mapColStyle.get(col.getColName()));
                    }
                    break;
                case STRING:
                    if (colValue != null) {
                        cell.setCellValue(colValue.toString());
                        cell.setCellStyle(cellStyleDto.getCellStyleLeft());
                        
                        if (col.getColName().equals("MESSAGEERROR")) {
                            cell.setCellStyle(cellStyleDto.getCellStyleMessageError());
                        }
                        
                        if (col.getColName().equals("MESSAGEWARNING")) {
                            cell.setCellStyle(cellStyleDto.getCellStyleMessageWarning());
                        }
                        
                        if (col.getColName().contains("NOTE") || col.getColName().contains("DESCRIPTION")) {
                            cell.setCellStyle(cellStyleDto.getCellStyleDescription());
                        }
                        if (StringUtils.contains(colValue.toString(),"%")) {
                            cell.setCellStyle(cellStyleDto.getCellStyleRight());
                        }
                    } else {
                        cell.setCellStyle(cellStyleDto.getCellStyleBoder());
                    }
                    
                    if (mapColStyle != null && mapColStyle.containsKey(col.getColName())) {
                        cell.setCellStyle(mapColStyle.get(col.getColName()));
                    }
                    
                    break;
                case BOOLEAN:
                    if (colValue != null) {
                        cell.setCellValue(colValue.toString());
                    }
                    cell.setCellStyle(cellStyleDto.getCellStyleCenter());
                    
                    if (mapColStyle != null && mapColStyle.containsKey(col.getColName())) {
                        cell.setCellStyle(mapColStyle.get(col.getColName()));
                    }
                    break;
                default:
                    break;
                }
            } // END FOR 2
        }
    }
    
    /**
     * Convert from Excel file to CSV file
     * @param sheet
     * @param pathOut
     */
    private void convertExcelToCSV(XSSFSheet sheet, String pathOut) {
    	logger.info("Begin convertExcelToCSV(XSSFSheet sheet, String pathOut)");
        StringBuilder data = new StringBuilder();
        try {
            Iterator<Row> rowIterator = sheet.iterator();
            while (rowIterator.hasNext()) {
                Row row = rowIterator.next();
                Iterator<Cell> cellIterator = row.cellIterator();
                while (cellIterator.hasNext()) {
                    Cell cell = cellIterator.next();

                    CellType type = cell.getCellTypeEnum();
                    // Check type of cell to convert
                    if (type == CellType.BOOLEAN) {
                        data.append(cell.getBooleanCellValue());
                    } else if (type == CellType.NUMERIC) {
                        data.append(cell.getNumericCellValue());
                    } else if (type == CellType.STRING) {
                        String cellValue = cell.getStringCellValue();
                        if(!cellValue.isEmpty()) {
                            cellValue = cellValue.replaceAll("\"", "\"\"");
                            data.append("\"").append(cellValue).append("\"");
                        }
                    } else if (type == CellType.BLANK) {
                    } else {
                        data.append(cell + "");
                    }
                    if(cell.getColumnIndex() != row.getLastCellNum()-1) {
                        data.append(",");
                    }
                }
                data.append('\n');
            }
            Files.write(Paths.get(pathOut), data.toString().getBytes("UTF-8"));
        } catch (FileNotFoundException e) {
            logger.error("FileNotFoundException : ", e);
        } catch (IOException e) {
        	logger.error("IOException : ", e);
        }
    }
}
