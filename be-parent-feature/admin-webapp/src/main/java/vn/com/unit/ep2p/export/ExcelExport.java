package vn.com.unit.ep2p.export;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CellType;
//import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.DataFormat;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.RegionUtil;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFColor;

import vn.com.unit.ep2p.export.util.Alignment;
import vn.com.unit.ep2p.export.util.CellHeader;
import vn.com.unit.ep2p.export.util.CellStyleAttribute;
import vn.com.unit.ep2p.export.util.CellStyleOption;
import vn.com.unit.ep2p.export.util.ColumnFormat;
import vn.com.unit.ep2p.export.util.DataTable;
import vn.com.unit.ep2p.export.util.DataType;
import vn.com.unit.ep2p.export.util.FontStyleOption;
import vn.com.unit.ep2p.export.util.HeaderTable;
import vn.com.unit.ep2p.export.util.StyleOption;

public abstract class ExcelExport implements IExport {

	private int sheetIndex = 0;

	private int rowHeaderDataStart = 0;
	
	private int rowDataStart = 1;

	protected CellStyleAttribute cellStyleCenterAttr = new CellStyleAttribute(CellStyleOption.HORIZONTAL_ALIGNMENT_CENTER, null);

	private CellStyleAttribute cellStyleRightAttr = new CellStyleAttribute(CellStyleOption.HORIZONTAL_ALIGNMENT_RIGHT, null);

	private CellStyleAttribute cellStyleLeftAttr = new CellStyleAttribute(CellStyleOption.HORIZONTAL_ALIGNMENT_LEFT, null);

	private CellStyleAttribute cellStyleEmptyAttr = new CellStyleAttribute(CellStyleOption.UNIT_EMPTY_STYPE, null);
	
	private CellStyleAttribute cellStyleNumberIntAttr = new CellStyleAttribute(CellStyleOption.DATA_FORMAT, NUMBER_INT_FORMAT_DEFAULT);
	
	private CellStyleAttribute cellStyleNumberDoubleAttr = new CellStyleAttribute(CellStyleOption.DATA_FORMAT, NUMBER_DOUBLE_FORMAT_DEFAULT);
	
	protected CellStyleAttribute cellStyleDateAttr = new CellStyleAttribute(CellStyleOption.DATA_FORMAT, (short) 14);
	
	protected CellStyleAttribute cellStyleVeticalCenterAttr = new CellStyleAttribute(CellStyleOption.VERTICAL_ALIGNMENT_CENTER, null);

	private DataFormat dataFormat;

	private Workbook workbook;

	private boolean hasHeaderData = false;
	
	private boolean autoSizeColumn = false;
	
	public List<CellRangeAddress> cellRangeAddressList = new ArrayList<>();

	private Map<Set<CellStyleAttribute>, CellStyle> styleBank = new HashMap<Set<CellStyleAttribute>, CellStyle>();
	
	/** Constant */
	private static final String FONT_NAME_DEFAULT = "Arial";
	private static final String NUMBER_INT_FORMAT_DEFAULT = "_(* #,##0_);_(* (#,##0);_(* \"-\"??_);_(@_)";
	private static final String NUMBER_DOUBLE_FORMAT_DEFAULT = "_(* #,##0.00_);_(* (#,##0.00);_(* \"-\"??_);_(@_)";

	public abstract void initWorkbook(Class<? extends Workbook> clazz);
	
	public void setExportInput(IDataTable iDataTable) throws Exception {
		try {
			HeaderTable headerTable = iDataTable.getHeaderTable();
			List<ColumnFormat> columnFormats = iDataTable.getColumnFormats();
			DataTable dataTable = iDataTable.getDataTable();

			Sheet sheet = workbook.getSheetAt(sheetIndex);

			// Format cell default
			dataFormat = workbook.getCreationHelper().createDataFormat();
			this.getCellStyle(workbook, cellStyleCenterAttr);
			this.getCellStyle(workbook, cellStyleRightAttr);
			this.getCellStyle(workbook, cellStyleLeftAttr);
			this.getCellStyle(workbook, cellStyleEmptyAttr);
			this.getCellStyle(workbook, cellStyleNumberIntAttr);
			this.getCellStyle(workbook, cellStyleNumberDoubleAttr);

			// write header data
			if (hasHeaderData && headerTable != null) {
				this.writeHeaderData(workbook, headerTable);
			}

			// write body - data
			int rowDataStartIndex = rowDataStart;
			for (int rowDataTableIndex = 0; rowDataTableIndex < dataTable.size(); rowDataTableIndex++) {
				Row row = sheet.createRow(rowDataStartIndex);

				// write row
				this.writeRowData(workbook, row, rowDataTableIndex, columnFormats, dataTable);

				rowDataStartIndex++;
			}
			
			if( autoSizeColumn && dataTable.size() > 0) {
				Row row = sheet.getRow(rowDataStart);
				for (int i = 0; i <= row.getLastCellNum(); i++) {
					sheet.autoSizeColumn(i);
				}
			}
			
			// write header
			this.writeHeader(workbook, sheet);

			// write footer
			this.writeFooter(workbook, sheet, iDataTable);
			
			// merge cell
			for (CellRangeAddress cellRangeAddress : cellRangeAddressList) {
				sheet.addMergedRegion(cellRangeAddress);
				
				int firstColumn = cellRangeAddress.getFirstColumn();
				int firstRow = cellRangeAddress.getFirstRow();
				Row rowMerged = sheet.getRow(firstRow);
				Cell cellMerged = rowMerged.getCell(firstColumn);
				CellStyle cellStyle = cellMerged.getCellStyle();
				
				BorderStyle borderBottom = cellStyle.getBorderBottomEnum();
				RegionUtil.setBorderBottom(borderBottom, cellRangeAddress, sheet);
				
				BorderStyle borderTop = cellStyle.getBorderTopEnum();
			    RegionUtil.setBorderTop(borderTop, cellRangeAddress, sheet);
			    
			    BorderStyle borderLeft = cellStyle.getBorderLeftEnum();
			    RegionUtil.setBorderLeft(borderLeft, cellRangeAddress, sheet);
			    
			    BorderStyle borderRight = cellStyle.getBorderRightEnum();
			    RegionUtil.setBorderRight(borderRight, cellRangeAddress, sheet);
			}
		} catch (FileNotFoundException e) {
			throw new Exception(e.toString());
		}
	}

	@Override
	public void export(OutputStream outputStream) throws Exception {
		try {
			workbook.write(outputStream);
			outputStream.flush();
			outputStream.close();
			this.destroy();
		} catch (IOException e) {
			throw new Exception(e.toString());
		}
	}

	@Override
	public void destroy() throws Exception {
		try {
			workbook.close();
		} catch (IOException e) {
			throw new Exception(e.toString());
		}
	}
	
	public void writeHeader(Workbook workbook, Sheet sheet) throws Exception {
		// Optional for report
	}
	
	public void writeFooter(Workbook workbook, Sheet sheet, IDataTable iDataTable) {
		// Optional for report
	}

	public void writeHeaderData(Workbook workbook, HeaderTable headerTable) throws Exception {

		Sheet sheet = workbook.getSheetAt(sheetIndex);
		List<List<CellHeader>> lstRowHeader = headerTable.getRowHeaders();
		if (!lstRowHeader.isEmpty()) {
			int rowHeaderDataStartIndex = rowHeaderDataStart;
			for (int rowIndex = 0; rowIndex < headerTable.size();rowIndex++) {
				List<CellHeader> rowHeader = lstRowHeader.get(rowIndex);
				if ( rowHeader!= null && !rowHeader.isEmpty()) {
					// fill 1 row
					Row row = sheet.createRow(rowHeaderDataStartIndex);
					CellHeader cellHeaderFirst = rowHeader.get(0);
					int colIndexFirst = cellHeaderFirst.getColIndex();
					
					this.writeRowHeaderData(workbook, row, rowHeaderDataStartIndex, colIndexFirst, rowHeader);
					rowHeaderDataStartIndex++;
				}

			}
		}
	}
	
	public void writeRowHeaderData(Workbook workbook, Row row, int rowIndex, int colIndexFirst, List<CellHeader> rowHeader)  throws Exception {
		// create empty cells from col A to col [colIndexFirst - 1]
		int colIndexA = 0;
		createEmptyCells(row, colIndexA, colIndexFirst);

		// fill data
		for (CellHeader cellHeader : rowHeader) {
			this.writeCellHeaderData(workbook, row, rowIndex, cellHeader);
		}
	}
	
	public Cell writeCellHeaderData(Workbook workbook, Row row, int rowIndex, CellHeader cellHeader)  throws Exception {
		int colIndex = cellHeader.getColIndex();
		Cell cell = row.createCell(colIndex);
		String colNameDis = cellHeader.getNameDisplay();
		cell.setCellValue(!StringUtils.isEmpty(colNameDis)? colNameDis : "");
		
		// create empty cells from col colIndex+1 to col [colIndex+cellHeader.getColspan() - 1]
		createEmptyCells(row, colIndex + 1, colIndex + cellHeader.getColspan());

		int rowSpan = cellHeader.getRowspan();
		int colSpan = cellHeader.getColspan();
		if (rowSpan > 1 || colSpan > 1) {
			int firstRow = rowIndex;
			int lastRow = rowIndex + rowSpan - 1;
			int firstCol = colIndex;
			int lastCol = colIndex + colSpan - 1;
			CellRangeAddress cellRangeAddress = new CellRangeAddress(firstRow, lastRow, firstCol, lastCol);
			cellRangeAddressList.add(cellRangeAddress);
		}
		
		return cell;
	}
	
	private void createEmptyCells(Row row, int startColIndex, int endColIndex) {
		while (startColIndex < endColIndex) {
			row.createCell(startColIndex);
			startColIndex++;
		}
	}

	public void writeRowData(Workbook workbook, Row row, int rowDataTableIndex, List<ColumnFormat> columnFormats, DataTable dataTable)
			throws Exception {
		// write cell
		for (ColumnFormat columnFormat : columnFormats) {
			this.writeCellData(workbook, row, rowDataTableIndex, columnFormat, dataTable);
		}
	}

	public Cell writeCellData(Workbook workbook, Row row, int rowDataTableIndex, ColumnFormat columnFormat, DataTable dataTable) throws Exception {
		String columnName = columnFormat.getColumnName();
		int columnIndex = columnFormat.getColumnIndex();

		String formatPattern = columnFormat.getFormatPattern();

		// get dataType
		DataType dataType = columnFormat.getDataType();
		if (dataType == null) {
			DataType dataTypeObject = dataTable.getDataTypeOfCell(rowDataTableIndex, columnName);
			if (dataTypeObject != null) {
				dataType = dataTypeObject;
			} else {
				dataType = DataType.STRING;
			}
		}

		// set value for cell
		Cell cell = this.createCellWithValue(row, columnIndex, dataType, dataTable, rowDataTableIndex, columnName);

		// set cellStyle for cell
		Alignment alignment = columnFormat.getAlignment();
		this.setCellStyleDefault(cell, alignment, dataType, formatPattern);

		return cell;
	}
	
	private Cell createCellWithValue(Row row, int columnIndex, DataType dataType, DataTable dataTable, int rowDataTableIndex, String columnName) {
		Cell cell = null;
		
		switch (dataType) {
			case STRING:
				cell = row.createCell(columnIndex, CellType.STRING);
				String valueString = dataTable.getStringValueOfCell(rowDataTableIndex, columnName);
				cell.setCellValue(valueString);
				break;
			case DATE:
				cell = row.createCell(columnIndex);
				Date valueDate = dataTable.getDateValueOfCell(rowDataTableIndex, columnName);
				cell.setCellValue(valueDate);
				break;
			case BOOLEAN:
				cell = row.createCell(columnIndex, CellType.BOOLEAN);
				boolean valueBoolean = dataTable.getBooleanValueOfCell(rowDataTableIndex, columnName);
				cell.setCellValue(valueBoolean);
				break;
			default:
				// for number int,long....
				cell = row.createCell(columnIndex, CellType.NUMERIC);
				Double valueNumber = dataTable.getDoubleValueOfCell(rowDataTableIndex, columnName);
				cell.setCellValue(valueNumber);
				break;
		}
		
		return cell;
	}
	
	private void setCellStyleDefault(Cell cell, Alignment alignment, DataType dataType, String formatPattern) throws Exception {

		// if alignment is null - set alignment default by dataType
		if (alignment == null) {
			switch (dataType) {
				case STRING:
					alignment = Alignment.LEFT;
					break;
				case DATE:
					alignment = Alignment.CENTER;
					break;
				case BOOLEAN:
					alignment = Alignment.CENTER;
					break;
				default:
					// for number int,long....
					alignment = Alignment.DEFAULT;
					break;
			}
		}
		
		// set cellStyle default
		CellStyle cellStyle = null;
		switch (alignment) {
			case CENTER:
				cellStyle = this.getCellStyle(workbook, cellStyleCenterAttr);
				break;
			case RIGHT:
				cellStyle = this.getCellStyle(workbook, cellStyleRightAttr);
				break;
			case LEFT:
				cellStyle = this.getCellStyle(workbook, cellStyleLeftAttr);
				break;
			default:
				// for number int,long....
				cellStyle = this.getCellStyle(workbook, cellStyleEmptyAttr);
				break;
		}
		
		// format cell
		switch (dataType) {
			case DATE:
				short dateFormatValue = 14;
				if (!StringUtils.isEmpty(formatPattern)) {
					dateFormatValue = dataFormat.getFormat(formatPattern);
				}
				cellStyle.setDataFormat(dateFormatValue);
				
				CellStyleAttribute cellStyleDate = null;
				if(!StringUtils.isEmpty(formatPattern)) {
					cellStyleDate = new CellStyleAttribute(CellStyleOption.DATA_FORMAT, formatPattern);
				} else {
					cellStyleDate = cellStyleDateAttr;
				}
				cellStyle = this.getCellStyle(workbook, cellStyleCenterAttr, cellStyleDate);
				break;
			case NUMERIC:
				// for number int,long....
				CellStyleAttribute cellStyleNumber = null;
				if(!StringUtils.isEmpty(formatPattern)) {
					cellStyleNumber = new CellStyleAttribute(CellStyleOption.DATA_FORMAT, formatPattern);
				} else {
					cellStyleNumber = cellStyleNumberIntAttr;
				}
				cellStyle = this.getCellStyle(workbook, cellStyleEmptyAttr, cellStyleNumber);
				break;
			default:
				break;
		}
		
		cell.setCellStyle(cellStyle);
	}

	public CellStyle getCellStyle(Workbook workbook, List<CellStyleAttribute> cellStyleAttributeList, CellStyleAttribute... cellStyleAttributes)
			throws Exception {
		CellStyle cellStyle = null;

		Set<CellStyleAttribute> cellStyleAttributeSet = new HashSet<CellStyleAttribute>();
		cellStyleAttributeSet.addAll(Arrays.asList(cellStyleAttributes));
		cellStyleAttributeSet.addAll(cellStyleAttributeList);

		CellStyleAttribute[] cellStyleAttributeAll = cellStyleAttributeSet.toArray(new CellStyleAttribute[cellStyleAttributeSet.size()]);
		cellStyle = this.getCellStyle(workbook, cellStyleAttributeAll);

		return cellStyle;
	}

	public CellStyle getCellStyle(Workbook workbook, CellStyleAttribute... cellStyleAttributes) throws Exception {
		CellStyle style = null;

		Set<CellStyleAttribute> cellStyleAttributeSet = new HashSet<CellStyleAttribute>();
		cellStyleAttributeSet.addAll(Arrays.asList(cellStyleAttributes));

		if (styleBank.containsKey(cellStyleAttributeSet)) {
			return styleBank.get(cellStyleAttributeSet);
		}

		style = workbook.createCellStyle();

		Font font = workbook.createFont();
		style.setFont(font);

		for (CellStyleAttribute cellStyleAttribute : cellStyleAttributeSet) {
			StyleOption styleOption = (StyleOption) cellStyleAttribute.getStyleOption();
			if (CellStyleOption.class.isInstance(styleOption)) {
				CellStyleOption cellStyleOption = (CellStyleOption) styleOption;
				Object valueObject = cellStyleAttribute.getValue();

				switch (cellStyleOption) {
					case UNIT_EMPTY_STYPE:
						break;
					case BORDER_TOP_THIN:
						style.setBorderTop(BorderStyle.THIN);
						break;
					case BORDER_BOTTOM_THIN:
						style.setBorderBottom(BorderStyle.THIN);
						break;
					case BORDER_TOP_THICK:
						style.setBorderTop(BorderStyle.THICK);
						break;
					case BORDER_BOTTOM_THICK:
						style.setBorderBottom(BorderStyle.THICK);
						break;
					case BORDER_TOP_DOTTED:
						style.setBorderTop(BorderStyle.DOTTED);
						break;
					case BORDER_BOTTOM_DOTTED:
						style.setBorderBottom(BorderStyle.DOTTED);
						break;
					case BORDER_LEFT_DOTTED:
						style.setBorderLeft(BorderStyle.DOTTED);
						break;
					case BORDER_RIGHT_DOTTED:
						style.setBorderRight(BorderStyle.DOTTED);
						break;
					case BORDER_TOP_HAIR:
                        style.setBorderTop(BorderStyle.HAIR);
                        break;
                    case BORDER_BOTTOM_HAIR:
                        style.setBorderBottom(BorderStyle.HAIR);
                        break;
                    case BORDER_LEFT_HAIR:
                        style.setBorderLeft(BorderStyle.HAIR);
                        break;
                    case BORDER_RIGHT_HAIR:
                        style.setBorderRight(BorderStyle.HAIR);
                        break;
					case HORIZONTAL_ALIGNMENT_LEFT:
						style.setAlignment(HorizontalAlignment.LEFT);
						break;
					case HORIZONTAL_ALIGNMENT_RIGHT:
						style.setAlignment(HorizontalAlignment.RIGHT);
						break;
					case HORIZONTAL_ALIGNMENT_CENTER:
						style.setAlignment(HorizontalAlignment.CENTER);
						break;
					case VERTICAL_ALIGNMENT_TOP:
						style.setVerticalAlignment(VerticalAlignment.TOP);
						break;
					case VERTICAL_ALIGNMENT_BOTTOM:
						style.setVerticalAlignment(VerticalAlignment.BOTTOM);
						break;
					case VERTICAL_ALIGNMENT_CENTER:
						style.setVerticalAlignment(VerticalAlignment.CENTER);
						break;
					case DATA_FORMAT:
						if( null != valueObject ) {
							if( valueObject instanceof String ) {
								String patternFormat = String.valueOf(valueObject);
								
								if( !StringUtils.isEmpty(patternFormat) ) {
									short numberFormatValue = dataFormat.getFormat(patternFormat);
									style.setDataFormat(numberFormatValue);
								}
							} else if( valueObject instanceof Short ) {
								Short valueObjectShort = (Short) valueObject;
								style.setDataFormat(valueObjectShort);
							}
						}
						break;
					case FILL_FOREGROUND_COLOR:
						
						if( null != valueObject ) {
							if( valueObject instanceof Short ) {
								Short fillForegroundColor = (Short) valueObject;
								style.setFillForegroundColor(fillForegroundColor);
							} else if( valueObject instanceof XSSFColor ) {
								XSSFColor xssfColor = (XSSFColor) valueObject;
								((XSSFCellStyle) style).setFillForegroundColor(xssfColor);
							}
						}
						break;
					case FILL_PATTERN:
						FillPatternType fillPattern = FillPatternType.SOLID_FOREGROUND;
						
						if( null != valueObject && valueObject instanceof FillPatternType ) {
							fillPattern = (FillPatternType) valueObject;
						}
						style.setFillPattern(fillPattern);
						break;
					case WRAP_TEXT:
						style.setWrapText(true);
						break;
					default:
						System.out.println("unknown cell style attribute: " + cellStyleOption);
						throw new Exception("unknown cell style attribute: " + cellStyleOption);
				}
			} else {
				FontStyleOption cellStyleOption = (FontStyleOption) styleOption;
				Object valueObject = cellStyleAttribute.getValue();

				switch (cellStyleOption) {
					case SIZE:
						Short textSize = 10;
						if( null != valueObject && valueObject instanceof Short ) {
							textSize = (Short) valueObject;
						}
						
						font.setFontHeightInPoints(textSize);
						break;
					case BOLD:
						font.setBold(true);
						break;
					case COLOR:
						Short textColor = Font.COLOR_NORMAL;
						
						if( null != valueObject && valueObject instanceof Short ) {
							textColor = (Short) valueObject;
						}
						
						font.setColor(textColor);
						break;
					case NAME:
						String fontName = FONT_NAME_DEFAULT;
						
						if( null != valueObject && valueObject instanceof String ) {
							fontName = String.valueOf(valueObject);
						}
						
						font.setFontName(fontName);
						break;
					default:
						System.out.println("unknown cell style attribute: " + cellStyleOption);
						throw new Exception("unknown cell style attribute: " + cellStyleOption);
				}
			}
		}

		styleBank.put(cellStyleAttributeSet, style);
		return style;
	}

	public int getSheetIndex() {
		return sheetIndex;
	}

	public void setSheetIndex(int sheetIndex) {
		this.sheetIndex = sheetIndex;
	}

	public int getRowDataStart() {
		return rowDataStart;
	}

	public void setRowDataStart(int rowDataStart) {
		this.rowDataStart = rowDataStart;
	}

	public CellStyleAttribute getCellStyleCenterAttr() {
		return cellStyleCenterAttr;
	}

	public void setCellStyleCenterAttr(CellStyleAttribute cellStyleCenterAttr) {
		this.cellStyleCenterAttr = cellStyleCenterAttr;
	}

	public CellStyleAttribute getCellStyleRightAttr() {
		return cellStyleRightAttr;
	}

	public void setCellStyleRightAttr(CellStyleAttribute cellStyleRightAttr) {
		this.cellStyleRightAttr = cellStyleRightAttr;
	}

	public CellStyleAttribute getCellStyleLeftAttr() {
		return cellStyleLeftAttr;
	}

	public void setCellStyleLeftAttr(CellStyleAttribute cellStyleLeftAttr) {
		this.cellStyleLeftAttr = cellStyleLeftAttr;
	}

	public CellStyleAttribute getCellStyleEmptyAttr() {
		return cellStyleEmptyAttr;
	}

	public void setCellStyleEmptyAttr(CellStyleAttribute cellStyleEmptyAttr) {
		this.cellStyleEmptyAttr = cellStyleEmptyAttr;
	}

	public DataFormat getDataFormat() {
		return dataFormat;
	}

	public void setDataFormat(DataFormat dataFormat) {
		this.dataFormat = dataFormat;
	}

	public Workbook getWorkbook() {
		return workbook;
	}

	public void setWorkbook(Workbook workbook) {
		this.workbook = workbook;
	}

	public boolean isHasHeaderData() {
		return hasHeaderData;
	}

	public void setHasHeaderData(boolean hasHeaderData) {
		this.hasHeaderData = hasHeaderData;
	}

	public CellStyleAttribute getCellStyleNumberIntAttr() {
		return cellStyleNumberIntAttr;
	}

	public void setCellStyleNumberIntAttr(CellStyleAttribute cellStyleNumberIntAttr) {
		this.cellStyleNumberIntAttr = cellStyleNumberIntAttr;
	}

	public CellStyleAttribute getCellStyleNumberDoubleAttr() {
		return cellStyleNumberDoubleAttr;
	}

	public void setCellStyleNumberDoubleAttr(CellStyleAttribute cellStyleNumberDoubleAttr) {
		this.cellStyleNumberDoubleAttr = cellStyleNumberDoubleAttr;
	}

	public CellStyleAttribute getCellStyleDateAttr() {
		return cellStyleDateAttr;
	}

	public void setCellStyleDateAttr(CellStyleAttribute cellStyleDateAttr) {
		this.cellStyleDateAttr = cellStyleDateAttr;
	}

	public int getRowHeaderDataStart() {
		return rowHeaderDataStart;
	}

	public void setRowHeaderDataStart(int rowHeaderDataStart) {
		this.rowHeaderDataStart = rowHeaderDataStart;
	}

	public CellStyleAttribute getCellStyleVeticalCenterAttr() {
		return cellStyleVeticalCenterAttr;
	}

	public void setCellStyleVeticalCenterAttr(CellStyleAttribute cellStyleVeticalCenterAttr) {
		this.cellStyleVeticalCenterAttr = cellStyleVeticalCenterAttr;
	}

	public boolean isAutoSizeColumn() {
		return autoSizeColumn;
	}

	public void setAutoSizeColumn(boolean autoSizeColumn) {
		this.autoSizeColumn = autoSizeColumn;
	}
}
