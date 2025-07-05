package vn.com.unit.ep2p.export;

import java.io.OutputStream;
import java.util.Date;
import java.util.List;

import org.springframework.util.StringUtils;

import vn.com.unit.common.utils.CommonDateUtil
;
import vn.com.unit.ep2p.admin.constant.ConstantCore;
import vn.com.unit.ep2p.constant.AppConstantCore;
import vn.com.unit.ep2p.export.util.CellHeader;
import vn.com.unit.ep2p.export.util.ColumnFormat;
import vn.com.unit.ep2p.export.util.DataTable;
import vn.com.unit.ep2p.export.util.DataType;
import vn.com.unit.ep2p.export.util.HeaderTable;

public abstract class CsvExport implements IExport {
    
    private IDataTable iDataTable;
    
    private String charSeparator = ",";
    
    public void writeHeader(HeaderTable headerTable, OutputStream outputStream) throws Exception {
        if( headerTable != null && !headerTable.isEmpty() ) {
            List<CellHeader> rowHeader = headerTable.getRowHeader(0);
            
            StringBuilder lineBuilder = new StringBuilder();
            for (CellHeader cellHeader : rowHeader) {
                lineBuilder.append(cellHeader.getNameDisplay());
                lineBuilder.append(charSeparator);
            }
            
            String line = lineBuilder.substring(0, lineBuilder.length() - 1).concat(AppConstantCore.BREAK_LINE);
            outputStream.write(line.getBytes());
        }
    }
    
    @SuppressWarnings("deprecation")
	public void writeData(DataTable dataTable, List<ColumnFormat> columnFormats, OutputStream outputStream) throws Exception {
        for (int rowIndex = 0; rowIndex < dataTable.size(); rowIndex++) {
            
            StringBuilder lineBuilder = new StringBuilder();
            for (ColumnFormat columnFormat : columnFormats) {
                String columnName = columnFormat.getColumnName();

                String formatPattern = columnFormat.getFormatPattern();
                
                // get dataType
                DataType dataType = columnFormat.getDataType();
                if (dataType == null) {
                    DataType dataTypeObject = dataTable.getDataTypeOfCell(rowIndex, columnName);
                    if (dataTypeObject != null) {
                        dataType = dataTypeObject;
                    } else {
                        dataType = DataType.STRING;
                    }
                }
                
                switch (dataType) {
                    case STRING:
                        String valueString = dataTable.getStringValueOfCell(rowIndex, columnName);
                        if( StringUtils.isEmpty(valueString) ) {
                            valueString = ConstantCore.EMPTY;
                        }
                        lineBuilder.append(valueString);
                        break;
                    case DATE:
                        Date valueDate = dataTable.getDateValueOfCell(rowIndex, columnName);
                        String dateStr = CommonDateUtil.formatDateToString(valueDate, formatPattern);
                        if( StringUtils.isEmpty(dateStr) ) {
                            dateStr = ConstantCore.EMPTY;
                        }
                        lineBuilder.append(dateStr);
                        break;
                    case BOOLEAN:
                        boolean valueBoolean = dataTable.getBooleanValueOfCell(rowIndex, columnName);
                        lineBuilder.append(valueBoolean);
                        break;
                    default:
                        Double valueNumber = dataTable.getDoubleValueOfCell(rowIndex, columnName);
                        if( valueNumber == null ) {
                            valueNumber = 0D;
                        }
                        lineBuilder.append(valueNumber);
                        break;
                }
                
                lineBuilder.append(charSeparator);
            }
            String line = lineBuilder.substring(0, lineBuilder.length() - 1).concat(AppConstantCore.BREAK_LINE);
            outputStream.write(line.getBytes());
        }
    }
    
    @Override
    public void setExportInput(IDataTable iDataTable) throws Exception {
        this.iDataTable = iDataTable;
    }
    
    @Override
    public void export(OutputStream outputStream) throws Exception {
        try {
            // Write the header line
            HeaderTable headerTable = iDataTable.getHeaderTable();
            writeHeader(headerTable, outputStream);
            
            List<ColumnFormat> columnFormats = iDataTable.getColumnFormats();
            DataTable dataTable = iDataTable.getDataTable();
            writeData(dataTable, columnFormats, outputStream);
            
            outputStream.flush();
        } catch (Exception e) {
            throw new Exception(e.toString());
        }
    }
    
    @Override
    public void destroy() throws Exception {
        // TODO Auto-generated method stub
        
    }

    /**
     * Get charSeparator
     * @return String
     * @author KhoaNA
     */
    public String getCharSeparator() {
        return charSeparator;
    }

    /**
     * @param charSeparator the charSeparator to set
     */
    public void setCharSeparator(String charSeparator) {
        this.charSeparator = charSeparator;
    }
}
