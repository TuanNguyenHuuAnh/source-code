package vn.com.unit.ep2p.report.data;

import java.util.ArrayList;
import java.util.List;

import vn.com.unit.ep2p.export.DataTableWithHeader;
import vn.com.unit.ep2p.export.util.CellHeader;
import vn.com.unit.ep2p.export.util.ColumnFormat;
import vn.com.unit.ep2p.export.util.DataTable;
import vn.com.unit.ep2p.export.util.DataType;
import vn.com.unit.ep2p.export.util.HeaderTable;

public class DocDataTable extends DataTableWithHeader {
    
    List<String> dyColumns;
	
	public static final String COL_TITLE = "docTitle";
	public static final String COL_SERVICE = "formName";
	public static final String COL_CREATED_DATE = "createdDate";
	public static final String COL_CREATED_BY = "createdBy";
	public static final String COL_STATUS = "statusName";
	public static final String COL_UPDATED_DATE = "updatedDate";
	public static final String COL_UPDATED_BY = "updatedBy";

	/* (non-Javadoc)
	 * @see vn.com.unit.dms.export.SimpleDataTable#initHeaderTable(vn.com.unit.dms.util.DataTable)
	 */
	@Override
	public HeaderTable initHeaderTable(DataTable dataTable) {
		HeaderTable headerTable = new HeaderTable();

		List<CellHeader> rowHeader = new ArrayList<>();
		
		CellHeader titleColumn = new CellHeader(COL_TITLE, 0, "Title", "", 1, 1);
		rowHeader.add(titleColumn);

		CellHeader serviceColumn = new CellHeader(COL_SERVICE, 1, "Service", "", 1, 1);
		rowHeader.add(serviceColumn);
		
		CellHeader createdDateColumn = new CellHeader(COL_CREATED_DATE, 2, "Created date", "", 1, 1);
		createdDateColumn.setDataType(DataType.DATE);
		rowHeader.add(createdDateColumn);

		CellHeader createdByColumn = new CellHeader(COL_CREATED_BY, 3, "Created by", "", 1, 1);
		rowHeader.add(createdByColumn);
		
		CellHeader updatedDateColumn = new CellHeader(COL_UPDATED_DATE, 4, "Updated date", "", 1, 1);
		updatedDateColumn.setDataType(DataType.DATE);
        rowHeader.add(updatedDateColumn);

        CellHeader updatedByColumn = new CellHeader(COL_UPDATED_BY, 5, "Updated by", "", 1, 1);
        rowHeader.add(updatedByColumn);
		
		CellHeader statusColumn = new CellHeader(COL_STATUS, 6, "Status", "", 1, 1);
		rowHeader.add(statusColumn);
		
		if( dyColumns != null && !dyColumns.isEmpty() ) {
		    int indexColumn = 5;
		    for (String dyColumn : dyColumns) {
		        CellHeader cellHeaderDyColumn = new CellHeader(dyColumn, indexColumn, dyColumn, "", 1, 1);
		        rowHeader.add(cellHeaderDyColumn);
		        indexColumn++;
            }
		}
		
		headerTable.addRowHeader(rowHeader);
		
		return headerTable;
	}
	
	@Override
	public List<ColumnFormat> initFormatColumns(HeaderTable headerTable) {
	    List<ColumnFormat> columnFormats = super.initFormatColumns(headerTable);
	    for (ColumnFormat columnFormat : columnFormats) {
            if( COL_CREATED_DATE.equals(columnFormat.getColumnName()) || COL_UPDATED_DATE.equals(columnFormat.getColumnName()) ) {
                columnFormat.setFormatPattern("dd/MM/yyyy");
            }
        }
	    return columnFormats;
	}

	public DocDataTable(DataTable dataTable, List<String> dyColumns) {
	    this.dyColumns = dyColumns;
		this.setDataTable(dataTable);
	}
}
