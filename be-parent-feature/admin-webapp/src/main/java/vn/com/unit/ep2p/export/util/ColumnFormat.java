package vn.com.unit.ep2p.export.util;

public class ColumnFormat {
	
	private String columnName;
	
	private int columnIndex;
	
	private DataType dataType;
	
	private String formatPattern;
	
	private Alignment alignment;
	
	public ColumnFormat(String columnName, int columnIndex, DataType dataType) {
		this.columnName = columnName;
		this.columnIndex = columnIndex;
		this.dataType = dataType;
	}

	public String getColumnName() {
		return columnName;
	}

	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}

	public String getFormatPattern() {
		return formatPattern;
	}

	public void setFormatPattern(String formatPattern) {
		this.formatPattern = formatPattern;
	}

	public DataType getDataType() {
		return dataType;
	}

	public void setDataType(DataType dataType) {
		this.dataType = dataType;
	}
	
	public Alignment getAlignment() {
		return alignment;
	}

	public void setAlignment(Alignment alignment) {
		this.alignment = alignment;
	}

	public int getColumnIndex() {
		return columnIndex;
	}

	public void setColumnIndex(int columnIndex) {
		this.columnIndex = columnIndex;
	}
}
