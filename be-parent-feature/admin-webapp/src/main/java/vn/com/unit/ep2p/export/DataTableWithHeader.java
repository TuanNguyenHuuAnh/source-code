package vn.com.unit.ep2p.export;

import java.util.ArrayList;
import java.util.List;

import vn.com.unit.ep2p.export.util.CellHeader;
import vn.com.unit.ep2p.export.util.ColumnFormat;
import vn.com.unit.ep2p.export.util.DataTable;
import vn.com.unit.ep2p.export.util.DataType;
import vn.com.unit.ep2p.export.util.HeaderTable;

public abstract class DataTableWithHeader implements IDataTable {
	
	private DataTable dataTable;
	
	private HeaderTable headerTable;
	
	private List<ColumnFormat> columnFormats;
	
	public abstract HeaderTable initHeaderTable(DataTable dataTable);
	
	public List<ColumnFormat> initFormatColumns(HeaderTable headerTable) {
        List<ColumnFormat> columnFormats = new ArrayList<>();
		
        int rowLastIndex = headerTable.size();
        if( rowLastIndex > 0 ) {
        	rowLastIndex = rowLastIndex - 1;
        	
        	List<CellHeader> rowHeader = headerTable.getRowHeader(rowLastIndex);
    		for (CellHeader cellHeader : rowHeader) {
    			int columnIndex = cellHeader.getColIndex();
    			String columnName = cellHeader.getColumnName();
    			DataType dataType = cellHeader.getDataType();
    			ColumnFormat columnFormat = new ColumnFormat(columnName, columnIndex, dataType);
    			columnFormats.add(columnFormat);
    		}
        }
		return columnFormats;
	}
	
	@Override
	public void initDataTable() {
		dataTable = getDataTable();
		
		headerTable = initHeaderTable(dataTable);
		columnFormats = initFormatColumns(headerTable);
	}

	@Override
	public List<ColumnFormat> getColumnFormats() {
		return columnFormats;
	}

	public void setColumnFormats(List<ColumnFormat> columnFormats) {
		this.columnFormats = columnFormats;
	}

	@Override
	public HeaderTable getHeaderTable() {
		return headerTable;
	}

	public void setHeaderTable(HeaderTable headerTable) {
		this.headerTable = headerTable;
	}

	@Override
	public DataTable getDataTable() {
		return dataTable;
	}

	public void setDataTable(DataTable dataTable) {
		this.dataTable = dataTable;
	}
}
