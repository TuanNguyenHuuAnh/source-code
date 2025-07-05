/*******************************************************************************
 * Class        CommonImportUtils
 * Created date 2020/05/31
 * Lasted date  2020/05/31
 * Author       TaiTM
 * Change log   2020/05/3101-00 TaiTM create a new
 ******************************************************************************/
package vn.com.unit.imp.excel.utils;

import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import vn.com.unit.imp.excel.constant.Message;
import vn.com.unit.imp.excel.constant.MessageList;
import vn.com.unit.imp.excel.constant.ImportExcelConstant;
import vn.com.unit.imp.excel.dto.ItemColsExcelDto;
import vn.com.unit.imp.excel.annotation.ExcelField;

/**
 * Utils
 * 
 * @version 01-00
 * @since 01-00
 * @author TaiTM
 */
@Component
public class CommonImportUtils {

    public static MessageSource msg;

    /** logger */
    final static Logger logger = LoggerFactory.getLogger(CommonImportUtils.class);

    @Autowired
    public CommonImportUtils(MessageSource msg) {
        CommonImportUtils.msg = msg;
    }

    public static boolean checkIsErrorImport(Map<String, List<Integer>> rowErrorMap, MessageList messageResult,
            Locale locale) {
        boolean result = false;
        // check error
        for (Map.Entry<String, List<Integer>> entry : rowErrorMap.entrySet()) {
            List<Integer> rowErrorList = entry.getValue();
            if (!rowErrorList.isEmpty()) {
                String fieldErrors = entry.getKey().toString();
                for (String required : ImportExcelConstant.LIST_REQUIRED) {
                    if (fieldErrors.contains(required)) {
                        fieldErrors = fieldErrors.replace(required, ImportExcelConstant.EMPTY_STRING);
                    }
                }
                fieldErrors = fieldErrors.trim();

                messageResult.add(msg.getMessage("message.error.convert.data",
                        new String[] { fieldErrors, StringUtils.join(rowErrorList, ", ") }, locale));
                result = true;
            }
        }

        if (result) {
            messageResult.setStatus(Message.ERROR);
        }

        return result;
    }

    public static String getMessageError(List<String> listMessageError) {
        String results = ImportExcelConstant.EMPTY_STRING;
        if (CollectionUtils.isNotEmpty(listMessageError)) {
            String messageError = ImportExcelConstant.EMPTY_STRING;
            for (int i = 0; i < listMessageError.size(); i++) {
                String message = listMessageError.get(i);
                if (i == (listMessageError.size() - 1) || listMessageError.size() == 1) {
                    messageError = messageError.concat(message);
                } else {
                    messageError = messageError.concat(message).concat(ImportExcelConstant.SEMICOLON_SPALCE);
                }
            }
            results = messageError;
        }
        return results;
    }

    @SuppressWarnings({ "incomplete-switch" })
    public static void copyRow(Sheet worksheet, Row sourceRow, int destinationRowNum) {
        // Get the source / new row
        Row newRow = worksheet.getRow(destinationRowNum);

        // If the row exist in destination, push down all rows by 1 else create a new
        // row
        if (newRow != null) {
            worksheet.shiftRows(destinationRowNum, worksheet.getLastRowNum(), 1);
        } else {
            newRow = worksheet.createRow(destinationRowNum);
        }

        // Loop through source columns to add to new row
        for (int i = 0; i < sourceRow.getLastCellNum(); i++) {
            // Grab a copy of the old/new cell
            Cell oldCell = sourceRow.getCell(i);
            Cell newCell = newRow.createCell(i);

            // If the old cell is null jump to next cell
            if (oldCell == null) {
                newCell = null;
                continue;
            }

            // Use old cell style
            newCell.setCellStyle(oldCell.getCellStyle());

            // If there is a cell comment, copy
            if (newCell.getCellComment() != null) {
                newCell.setCellComment(oldCell.getCellComment());
            }

            // If there is a cell hyperlink, copy
            if (oldCell.getHyperlink() != null) {
                newCell.setHyperlink(oldCell.getHyperlink());
            }

            // Set the cell data type
            newCell.setCellType(oldCell.getCellTypeEnum());

            // Set the cell data value
            switch (oldCell.getCellTypeEnum()) {
            case BLANK:
                break;
            case BOOLEAN:
                newCell.setCellValue(oldCell.getBooleanCellValue());
                break;
            case ERROR:
                newCell.setCellErrorValue(oldCell.getErrorCellValue());
                break;
            case FORMULA:
                newCell.setCellFormula(oldCell.getCellFormula());
                break;
            case NUMERIC:
                newCell.setCellValue(oldCell.getNumericCellValue());
                break;
            case STRING:
                newCell.setCellValue(oldCell.getRichStringCellValue());
                break;
            }
        }
    }
    
    /**
     * setListColumnExcelByEntity
     *
     * @param entityClazzz
     * @param cols
     * @author TaiTM
     */
    public static void setListColumnExcelByEntity(Class<?> entityClazzz, List<ItemColsExcelDto> cols) {
        if (entityClazzz.getDeclaredFields() != null && entityClazzz.getDeclaredFields().length > 0) {
            for (Field field : entityClazzz.getDeclaredFields()) {
                ExcelField excelField = field.getAnnotation(ExcelField.class);
                if (excelField != null) {
                    ItemColsExcelDto itemCol = new ItemColsExcelDto();
                    itemCol.setColName(field.getName());
                    itemCol.setColIndex(excelField.colNum());
                    cols.add(itemCol);
                }
            }
        }
    }

    public static boolean isRowEmpty(Row row) {
        for (int c = row.getFirstCellNum(); c < row.getLastCellNum(); c++) {
            Cell cell = row.getCell(c);
            if (cell != null && cell.getCellTypeEnum() != CellType.BLANK)
                return false;
        }
        return true;
    }

    public static String getImportSessionKey() {
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyymmddhhmmssS");
        return sdf.format(date);
    }
}
