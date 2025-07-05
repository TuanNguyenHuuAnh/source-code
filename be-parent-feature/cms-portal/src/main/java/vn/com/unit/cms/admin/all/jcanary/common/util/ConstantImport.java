/*******************************************************************************
 * Class        ：ConstantImport<T>
 * Created date ：2017/04/20
 * Lasted date  ：2017/04/26
 * Author       ：phunghn
 * Change log   ：2017/04/26：01-00 phunghn create a new
 ******************************************************************************/
package vn.com.unit.cms.admin.all.jcanary.common.util;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

//import vn.com.unit.imp.excel.dto.ItemColsExcelDto;
import vn.com.unit.imp.excel.dto.ItemColsExcelDto;

//import vn.com.unit.dto.ItemColsExcelDto;

/**
 * ConstantImport<T>
 * 
 * @version 01-00
 * @since 01-00
 * @author phunghn
 */

public class ConstantImport<T> {
	
	private static final Logger logger = LoggerFactory.getLogger(ConstantImport.class);
	
    private enum DataType {
        LONG, DOUBLE, INTEGER, STRING, DATE, INT, BIGDECIMAL;
    }

    
    /** List<T> */
    private List<T> data;

    /**
     * Get data
     * 
     * @return List<T>
     * @author phunghn
     */
    public List<T> getData() {
        return data;
    }

    /**
     * Set data
     * 
     * @param data
     *            type List<T>
     * @return
     * @author phunghn
     */
    public void setData(List<T> data) {
        this.data = data;
    }
    
    /**
     * setDataFileExcel
     *
     * @param rows
     * @param cols
     * @param rowHeader
     * @param obj
     * @throws IllegalArgumentException
     * @throws IllegalAccessException
     * @throws InstantiationException
     * @author phunghn
     */
	public void setDataFileExcel(Iterator<Row> rows, List<ItemColsExcelDto> cols, Row rowHeader, Class<T> obj)
            throws IllegalArgumentException, IllegalAccessException, InstantiationException {
        try {
            List<T> list = new ArrayList<T>();
            Map<String, Field> mapFields = new HashMap<String, Field>();
            // get list fields of class
            T objDefault = (T) obj.newInstance();
            Class<?> cls = objDefault.getClass();
            Field[] fields = cls.getDeclaredFields();
            for(Field f : fields){
            	mapFields.put(f.getName().toUpperCase(), f);
            }
            // loop rows
            while (rows.hasNext()) {
                Row currentRow = rows.next();
                T objAdd = (T) obj.newInstance();             
                // set value of object
                for (ItemColsExcelDto col : cols) {
                	Cell currentCell = currentRow.getCell(col.getColIndex());
                    // set value of fields
                    Field f = mapFields.get(col.getColName().toUpperCase().trim());
                    f.setAccessible(true);
                    String typeFields = f.getType().getSimpleName();
                    typeFields = typeFields.toUpperCase();
                    DataType dataType = DataType.valueOf(typeFields);
                    if(currentCell != null && currentCell.getCellTypeEnum() != CellType.BLANK)
                    {
						String valueOfString = "";
						Date valueOfDate = null;
						// check type cell 
						switch (currentCell.getCellTypeEnum()) {
							case BOOLEAN:											
								break;
							case STRING:
								valueOfString = currentCell.getStringCellValue();
								break;
							case NUMERIC:
								if(DateUtil.isCellDateFormatted(currentCell)){
									valueOfDate = currentCell.getDateCellValue();									
	                            } else {
									valueOfString = String.valueOf((int) Double.parseDouble(String.valueOf(currentCell.getNumericCellValue())));
								}												
								break;
							case FORMULA:
								break;
							default:
								break;
						}										
						//set value in object
						valueOfString = valueOfString.trim();
						switch (dataType) {
							case LONG:		
								Long valueOfLong = valueOfString.length() == 0 ? null : Long.parseLong(valueOfString); 
								f.set(objAdd, valueOfLong);	
									break;
								case DOUBLE:
								Double valueOfDouble = valueOfString.length() == 0 ? null : Double.parseDouble(valueOfString);
								f.set(objAdd, valueOfDouble);
								break;
							case INTEGER:
							    Integer valueOfInterger = valueOfString.length() == 0 ? null :  Integer.parseInt(valueOfString); 
                                f.set(objAdd, valueOfInterger);                                  
                                break;
							case INT:
								int valueOfInt = valueOfString.length() == 0 ? 0 : (int)Float.parseFloat(valueOfString); // special case : value = 1.0 
								f.set(objAdd, valueOfInt);									
								break;
							case STRING:
								f.set(objAdd, valueOfString);
								break;
							case DATE:								
                                f.set(objAdd, valueOfDate);
	                            break;
							case BIGDECIMAL:
						        // Create a DecimalFormat that fits your
						        // requirements
						        DecimalFormatSymbols symbols = new DecimalFormatSymbols();
						        symbols.setGroupingSeparator(',');
						        symbols.setDecimalSeparator('.');
						        String pattern = "#,##0.0#################";
						        DecimalFormat decimalFormat = new DecimalFormat(pattern, symbols);
						        decimalFormat.setParseBigDecimal(true);
						        BigDecimal valueOfBigDecimal = valueOfString.length() == 0 ? null
						          : (BigDecimal) decimalFormat.parse(valueOfString);
						        f.set(objAdd, valueOfBigDecimal);
						        break;    
							default:
								break;
						}
                    }              
                }
                list.add(objAdd);
            } // end white
            this.data = list;
        } catch (Exception ex) {
            logger.error("_setDataFileExcel_", ex);
        	 this.data = new ArrayList<T>();
        }
    }  
	
	
	
	/**
	 * map cols-type
	 *
	 * @param rows
	 * @param cols
	 * @param rowHeader
	 * @param obj
	 * @param formatColInput -> (key,value) -> upper case, the map used to format column Ex: agent code -> number, rate -> percent 
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 * @throws InstantiationException
	 * @author phatlt
	 */
	public void setDataFileExcel(Iterator<Row> rows, List<ItemColsExcelDto> cols, Row rowHeader, Class<T> obj, Map<String,String>formatColInput)
            throws IllegalArgumentException, IllegalAccessException, InstantiationException {
        try {
            List<T> list = new ArrayList<T>();
            Map<String, Field> mapFields = new HashMap<String, Field>();
            // get list fields of class
            T objDefault = (T) obj.newInstance();
            Class<?> cls = objDefault.getClass();
            Field[] fields = cls.getDeclaredFields();
            for(Field f : fields){
                mapFields.put(f.getName().toUpperCase(), f);
            }
            // loop rows
            while (rows.hasNext()) {
                Row currentRow = rows.next();
                T objAdd = (T) obj.newInstance();             
                // set value of object
                for (ItemColsExcelDto col : cols) {
                    
                    
                  
                    
                    Cell currentCell = currentRow.getCell(col.getColIndex());
                    // set value of fields
                    Field f = mapFields.get(col.getColName().toUpperCase().trim());
                    f.setAccessible(true);
                    String typeFields = f.getType().getSimpleName();
                    typeFields = typeFields.toUpperCase();
                    DataType dataType = DataType.valueOf(typeFields);
                    if(currentCell != null && currentCell.getCellTypeEnum() != CellType.BLANK)
                    {
                        String valueOfString = "";
                        Date valueOfDate = null;
                        // check type cell 
                        switch (currentCell.getCellTypeEnum()) {
                            case BOOLEAN:                                           
                                break;
                            case STRING:
                                valueOfString = currentCell.getStringCellValue();
                                break;
                            case NUMERIC:
                                if(DateUtil.isCellDateFormatted(currentCell)){
                                    valueOfDate = currentCell.getDateCellValue();                                   
                                }
                                else{
                                    valueOfString = String.valueOf((int) Double.parseDouble(String.valueOf(currentCell.getNumericCellValue())));
                                }                                               
                                break;
                            case FORMULA:
                                break;
                            default:
                                break;
                        }                                       
                        //set value in object
                        String colType = formatColInput.get(col.getColName().toUpperCase());
                        if(colType!=null && !valueOfString.trim().equals("")){
                            switch (colType) {
                            case "STRING":
                                DataFormatter formatter = new DataFormatter();
                                valueOfString= formatter.formatCellValue(currentCell);
                                break;
                            case "NUMBER":
                                valueOfString = String.valueOf((int) Double.parseDouble(String.valueOf(currentCell.getNumericCellValue())));
                                break;
                            case "PERCENT":
                                valueOfString = String.valueOf(currentCell.getNumericCellValue() * 100); 
                                break;
                            case "DATE": 
                                valueOfDate = currentCell.getDateCellValue();
                                break;
                            default:
                                break;
                            }
                        }
                        
                        valueOfString = valueOfString.trim();
                        switch (dataType) {
                        case LONG:
                            Long valueOfLong = valueOfString.length() == 0 ? null : Long.parseLong(valueOfString);
                            f.set(objAdd, valueOfLong);
                            break;
                        case DOUBLE:
                            Double valueOfDouble = valueOfString.length() == 0 ? null : Double.parseDouble(valueOfString);
                            f.set(objAdd, valueOfDouble);
                            break;
                        case INTEGER:
                            Integer valueOfInterger = valueOfString.length() == 0 ? null : Integer.parseInt(valueOfString);
                            f.set(objAdd, valueOfInterger);
                        case INT:
                            int valueOfInt = valueOfString.length() == 0 ? 0 : (int) Float.parseFloat(valueOfString); // special
                                                                                                                      // case
                                                                                                                      // :
                                                                                                                      // value
                                                                                                                      // =
                                                                                                                      // 1.0
                            f.set(objAdd, valueOfInt);
                            break;
                        case STRING:
                            f.set(objAdd, valueOfString);
                            break;
                        case DATE:
                            f.set(objAdd, valueOfDate);
                            break;
                        case BIGDECIMAL:
                            // Create a DecimalFormat that fits your
                            // requirements
                            DecimalFormatSymbols symbols = new DecimalFormatSymbols();
                            symbols.setGroupingSeparator(',');
                            symbols.setDecimalSeparator('.');
                            String pattern = "#,##0.0#################";
                            DecimalFormat decimalFormat = new DecimalFormat(pattern, symbols);
                            decimalFormat.setParseBigDecimal(true);
                            BigDecimal valueOfBigDecimal = valueOfString.length() == 0 ? null : (BigDecimal) decimalFormat.parse(valueOfString);
                            f.set(objAdd, valueOfBigDecimal);
                            break;
                        default:
                            break;
                        }
                    }              
                }
                list.add(objAdd);
            } // end white
            this.data = list;
        } catch (Exception ex) {
            logger.error("_setDataFileExcel_", ex);
             this.data = new ArrayList<T>();
        }
    } 
    
	public void setDataFileExcel(Sheet sheet, int startRow, int lastRow, List<ItemColsExcelDto> cols, Row rowHeader, Class<T> obj)
            throws IllegalArgumentException, IllegalAccessException, InstantiationException {
        try {
            List<T> list = new ArrayList<T>();
            Map<String, Field> mapFields = new HashMap<String, Field>();
            // get list fields of class
            T objDefault = (T) obj.newInstance();
            Class<?> cls = objDefault.getClass();
            Field[] fields = cls.getDeclaredFields();
            for(Field f : fields){
            	mapFields.put(f.getName().toUpperCase(), f);
            }
            // loop rows
            for(int i=startRow; i< lastRow; i++){
                Row currentRow = sheet.getRow(i);
                T objAdd = (T) obj.newInstance();             
                // set value of object
                for (ItemColsExcelDto col : cols) {
                	Cell currentCell = currentRow.getCell(col.getColIndex());
                    // set value of fields
                    Field f = mapFields.get(col.getColName().toUpperCase().trim());
                    f.setAccessible(true);
                    String typeFields = f.getType().getSimpleName();
                    typeFields = typeFields.toUpperCase();
                    DataType dataType = DataType.valueOf(typeFields);
                    if(currentCell != null && currentCell.getCellTypeEnum() != CellType.BLANK)
                    {
						String valueOfString = "";
						Date valueOfDate = null;
						// check type cell 
						switch (currentCell.getCellTypeEnum()) {
							case BOOLEAN:											
								break;
							case STRING:
								valueOfString = currentCell.getStringCellValue();
								break;
							case NUMERIC:
								if(DateUtil.isCellDateFormatted(currentCell)){
									valueOfDate = currentCell.getDateCellValue();									
								}
								else{
									valueOfString = String.valueOf((int) Double.parseDouble(String.valueOf(currentCell.getNumericCellValue())));
								}												
								break;
							case FORMULA:
								break;
							default:
								break;
						}										
						//set value in object
						valueOfString = valueOfString.trim();
						switch (dataType) {
							case LONG:		
								Long valueOfLong = valueOfString.length() == 0 ? null : Long.parseLong(valueOfString); 
								f.set(objAdd, valueOfLong);	
								break;
							case DOUBLE:
								Double valueOfDouble = valueOfString.length() == 0 ? null : Double.parseDouble(valueOfString);
								f.set(objAdd, valueOfDouble);
								break;
							case INTEGER:
							    Integer valueOfInterger = valueOfString.length() == 0 ? null :  Integer.parseInt(valueOfString); 
                                f.set(objAdd, valueOfInterger);      
							case INT:
								int valueOfInt = valueOfString.length() == 0 ? 0 : (int)Float.parseFloat(valueOfString); // special case : value = 1.0 
								f.set(objAdd, valueOfInt);									
								break;
							case STRING:
								f.set(objAdd, valueOfString);
								break;
							case DATE:								
								f.set(objAdd, valueOfDate);												
								break;
							default:
								break;
						}
                    }              
                }
                list.add(objAdd);
            } // end white
            this.data = list;
        } catch (Exception ex) {
        	logger.error("_setDataFileExcel_", ex);
        	 this.data = new ArrayList<T>();
        }
    }    
    
    /**
     * isValidFileExcelImport
     *
     * @param rowHeader
     * @param cols
     * @return Boolean
     * @author phunghn
     */
    @SuppressWarnings("deprecation")
	public static <E extends Enum<E>> Boolean isValidFileExcelImport(Row rowHeader, List<ItemColsExcelDto> cols) {
        Boolean isCheck = true;
        for (int i = 0; i < rowHeader.getLastCellNum(); i++) {
            Cell currentCell = rowHeader.getCell(i);
            String valueCell = "";
            if (currentCell.getCellType() == 0) { // number
                valueCell = String.valueOf(currentCell.getNumericCellValue());
            } else if (currentCell.getCellType() == 1) { // string
                valueCell = currentCell.getStringCellValue();
            } else if (currentCell.getCellType() == 4) // bool
            {
                valueCell = String.valueOf(currentCell.getBooleanCellValue());
            }
            Boolean isExist = false;
            for (ItemColsExcelDto col : cols) {
                if (col.getColName().equals(valueCell) && i == col.getColIndex()) {
                    isExist = true;
                    break;
                }
            }
            if (isExist == false) {
                isCheck = false;
                break;
            }
        }
        // check columns have loop in file ?
        if (isCheck == true) {
            for (int i = 0; i < rowHeader.getLastCellNum(); i++) {
                Cell celli = rowHeader.getCell(i);
                Boolean isExist = false;
                for (int j = i + 1; j < rowHeader.getLastCellNum(); j++) {
                    Cell cellj = rowHeader.getCell(j);
                    if (celli.getStringCellValue().trim().equals(cellj.getStringCellValue().trim())) {
                        isExist = true;
                        break;
                    }
                }
                if (isExist == true) {
                    isCheck = false;
                    break;
                }
            }
        }
        return isCheck;
    }
    /**
     * setListColumnExcel
     *
     * @param enumType
     * @param cols
     * @author phunghn
     */
    // vn.com.unit.imp.excel.dto.ItemColsExcelDto
    public static <E extends Enum<E>> void setListColumnExcel(Class<E> enumType, List<ItemColsExcelDto> cols) {
        // loop enum
        for (E en : enumType.getEnumConstants()) {
            ItemColsExcelDto itemCol = new ItemColsExcelDto();
            itemCol.setColName(en.name());
            itemCol.setColIndex(Integer.parseInt(en.toString()));
            cols.add(itemCol);
        }
    }
}
