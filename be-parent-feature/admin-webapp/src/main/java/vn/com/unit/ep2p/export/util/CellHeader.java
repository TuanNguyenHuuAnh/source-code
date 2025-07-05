package vn.com.unit.ep2p.export.util;

public class CellHeader {

	private String columnName;

	private String nameDisplay;
	
	private DataType dataType;
	
	private String codeNameDisplay;

	private int rowspan = 1;

	private int colspan = 1;

	private int colIndex;

	public CellHeader(String columnName, int colIndex) {
		this.columnName = columnName;
		this.colIndex = colIndex;
		this.nameDisplay = columnName;
	}
	
	public CellHeader(String columnName, int colIndex, String nameDisplay) {
		this.columnName = columnName;
		this.colIndex = colIndex;
		this.nameDisplay = nameDisplay;
	}

	public CellHeader(String columnName, int colIndex, String nameDisplay, DataType dataType) {
		this.columnName = columnName;
		this.colIndex = colIndex;
		this.nameDisplay = nameDisplay;
		this.dataType = dataType;
	}

	public CellHeader(String columnName, int colIndex, String nameDisplay, String codeNameDisplay) {
		this.columnName = columnName;
		this.colIndex = colIndex;
		this.nameDisplay = nameDisplay;
		this.codeNameDisplay = codeNameDisplay;
	}

	public CellHeader(String columnName, int colIndex, String nameDisplay, String codeNameDisplay, int rowspan, int colspan) {
		this.columnName = columnName;
		this.colIndex = colIndex;
		this.nameDisplay = nameDisplay;
		this.codeNameDisplay = codeNameDisplay;
		this.rowspan = rowspan;
		this.colspan = colspan;
	}

	public String getColumnName() {
		return columnName;
	}

	public void setColumnName(String columnName) {
		this.columnName = columnName;

	}

	public String getNameDisplay() {
		return nameDisplay;
	}

	public void setNameDisplay(String nameDisplay) {
		this.nameDisplay = nameDisplay;
	}

	public String getCodeNameDisplay() {
		return codeNameDisplay;
	}

	public void setCodeNameDisplay(String codeNameDisplay) {
		this.codeNameDisplay = codeNameDisplay;
	}

	public int getRowspan() {
		return rowspan;
	}

	public void setRowspan(int rowspan) {
		this.rowspan = rowspan;
	}

	public int getColspan() {
		return colspan;
	}

	public void setColspan(int colspan) {
		this.colspan = colspan;
	}
	public int getColIndex() {
		return colIndex;
	}

	public void setColIndex(int colIndex) {
		this.colIndex = colIndex;
	}

	public DataType getDataType() {
		return dataType;
	}

	public void setDataType(DataType dataType) {
		this.dataType = dataType;
	}
}
