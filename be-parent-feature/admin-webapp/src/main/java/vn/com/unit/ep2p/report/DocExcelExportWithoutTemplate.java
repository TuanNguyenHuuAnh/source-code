package vn.com.unit.ep2p.report;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import vn.com.unit.ep2p.export.SimpleExcelExportWithoutTemplate;
import vn.com.unit.ep2p.export.util.CellHeader;
import vn.com.unit.ep2p.export.util.CellStyleAttribute;
import vn.com.unit.ep2p.export.util.CellStyleOption;
import vn.com.unit.ep2p.export.util.ColumnFormat;
import vn.com.unit.ep2p.export.util.DataTable;
import vn.com.unit.ep2p.export.util.DataType;
import vn.com.unit.ep2p.export.util.FontStyleOption;

public class DocExcelExportWithoutTemplate extends SimpleExcelExportWithoutTemplate {
	
	private CellStyleAttribute fontNameArialCellStyle;
	
	private CellStyleAttribute fontSize11CellStyle;
	private CellStyleAttribute fontSize23CellStyle;
	
	private CellStyleAttribute textBoldCellStyle;
	
	private CellStyleAttribute textRedCellStyle;
	
	private CellStyleAttribute cellColorYellowCellStyle;
	private CellStyleAttribute cellColorPatternCellStyle;
	
	private CellStyleAttribute borderTopHeaderCellStyle;
	private CellStyleAttribute borderTopCellStyle;
    private CellStyleAttribute borderBottomCellStyle;
    private CellStyleAttribute borderRightCellStyle;
    
    private List<CellStyleAttribute> defaultCellStyleList;
	
	private static final String FONT_NAME_ARIAL = "Arial";
	
	private Date updateAs;

	/** 
	 * @author KhoaNA
	 */
	public DocExcelExportWithoutTemplate() {
		super(XSSFWorkbook.class);
		this.setRowDataStart(4);
		this.setRowHeaderDataStart(3);
		this.setAutoSizeColumn(true);
		
		fontNameArialCellStyle = new CellStyleAttribute(FontStyleOption.NAME, FONT_NAME_ARIAL);
		
		fontSize11CellStyle = new CellStyleAttribute(FontStyleOption.SIZE, (short) 11);
		fontSize23CellStyle = new CellStyleAttribute(FontStyleOption.SIZE, (short) 23);
		
		textBoldCellStyle = new CellStyleAttribute(FontStyleOption.BOLD, null);
		
		textRedCellStyle = 	new CellStyleAttribute(FontStyleOption.COLOR, IndexedColors.RED.getIndex());
		
		cellColorYellowCellStyle = new CellStyleAttribute(CellStyleOption.FILL_FOREGROUND_COLOR, IndexedColors.YELLOW.getIndex());
		cellColorPatternCellStyle = new CellStyleAttribute(CellStyleOption.FILL_PATTERN, FillPatternType.SOLID_FOREGROUND);
		
		borderTopHeaderCellStyle = new CellStyleAttribute(CellStyleOption.BORDER_TOP_THIN, null);
		borderTopCellStyle = new CellStyleAttribute(CellStyleOption.BORDER_TOP_HAIR, null);
        borderBottomCellStyle = new CellStyleAttribute(CellStyleOption.BORDER_BOTTOM_HAIR, null);
        borderRightCellStyle = new CellStyleAttribute(CellStyleOption.BORDER_RIGHT_HAIR, null);
        
        defaultCellStyleList = new ArrayList<>();
        defaultCellStyleList.add(borderTopCellStyle);
        defaultCellStyleList.add(borderBottomCellStyle);
        defaultCellStyleList.add(borderRightCellStyle);
        defaultCellStyleList.add(fontNameArialCellStyle);
	}
	
	/**
	 * Write title - header page
	 */
	@Override
	public void writeHeader(Workbook workbook, Sheet sheet) throws Exception {
		// Write title
		Row titleRow = sheet.createRow(0);
		titleRow.setHeightInPoints(33);
		
		Cell titleCell = titleRow.createCell(0);
		titleCell.setCellValue("Documents");
		CellStyle titleCellStyle = this.getCellStyle(workbook, fontSize23CellStyle, textBoldCellStyle, fontNameArialCellStyle);
		titleCell.setCellStyle(titleCellStyle);
		
		// Write update as
		Row updateAsRow = sheet.createRow(1);
		updateAsRow.setHeightInPoints(15);
		
		Cell updateAsLabelCell = updateAsRow.createCell(0);
		updateAsLabelCell.setCellValue("Report date:");
		CellStyle updateAsLabelCellStyle = this.getCellStyle(workbook, fontSize11CellStyle, fontNameArialCellStyle);
		updateAsLabelCell.setCellStyle(updateAsLabelCellStyle);
		
		
		Cell updateAsValueCell = updateAsRow.createCell(1);
		updateAsValueCell.setCellValue(updateAs);
		CellStyleAttribute dateFormat = this.getCellStyleDateAttr();
		CellStyleAttribute cellStyleLeftAttr = this.getCellStyleLeftAttr();
		CellStyle updateAsValueCellStyle = this.getCellStyle(workbook, fontSize11CellStyle, fontNameArialCellStyle, textBoldCellStyle, textRedCellStyle, cellStyleLeftAttr, dateFormat);
		updateAsValueCell.setCellStyle(updateAsValueCellStyle);
		
		sheet.createRow(2);
	}

	@Override
	public Cell writeCellHeaderData(Workbook workbook, Row row, int rowIndex, CellHeader cellHeader) throws Exception {
	    Cell cell = super.writeCellHeaderData(workbook, row, rowIndex, cellHeader);
	    
	    CellStyle cellStyle = this.getCellStyle(workbook, cellStyleCenterAttr, cellStyleVeticalCenterAttr, textBoldCellStyle, fontSize11CellStyle,
                cellColorYellowCellStyle, cellColorPatternCellStyle, borderTopHeaderCellStyle, borderBottomCellStyle, borderRightCellStyle,
                fontNameArialCellStyle);
	    
        cell.setCellStyle(cellStyle);
	    return cell;
	}
	
	@Override
	public Cell writeCellData(Workbook workbook, Row row, int rowDataTableIndex, ColumnFormat columnFormat, DataTable dataTable)
	        throws Exception {
	    Cell cell = super.writeCellData(workbook, row, rowDataTableIndex, columnFormat, dataTable);
	    
	    List<CellStyleAttribute> typeCellStyleList = new ArrayList<CellStyleAttribute>(defaultCellStyleList);
	    
	    DataType dataType = columnFormat.getDataType();
	    if( DataType.DATE.equals(dataType) ) {
	        typeCellStyleList.add(cellStyleDateAttr);
	        typeCellStyleList.add(cellStyleCenterAttr);
	    }
	    
	    CellStyle cellStyle = this.getCellStyle(workbook, typeCellStyleList);
	    cell.setCellStyle(cellStyle);
	    return cell;
	}
	
	/**
	 * Get updateAs
	 * @return Date
	 * @author KhoaNA
	 */
	public Date getUpdateAs() {
		return updateAs;
	}

	/**
	 * Set updateAs
	 * @param   updateAs
	 *          type Date
	 * @return
	 * @author  KhoaNA
	 */
	public void setUpdateAs(Date updateAs) {
		this.updateAs = updateAs;
	}
}
