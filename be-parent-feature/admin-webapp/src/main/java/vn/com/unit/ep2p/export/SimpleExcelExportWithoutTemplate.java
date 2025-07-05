package vn.com.unit.ep2p.export;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class SimpleExcelExportWithoutTemplate extends ExcelExport {
	
	public SimpleExcelExportWithoutTemplate(Class<? extends Workbook> clazz) {
		this.initWorkbook(clazz);
	}

	@Override
	public void initWorkbook(Class<? extends Workbook> clazz) {
		Workbook workbook = null;
		if( clazz.isAssignableFrom(XSSFWorkbook.class) ) {
			workbook = new XSSFWorkbook();
		} else {
			workbook = new HSSFWorkbook();
		}
		this.setHasHeaderData(true);
		this.setAutoSizeColumn(true);
		workbook.createSheet("sheet1");
		this.setWorkbook(workbook);
	
	}
}
