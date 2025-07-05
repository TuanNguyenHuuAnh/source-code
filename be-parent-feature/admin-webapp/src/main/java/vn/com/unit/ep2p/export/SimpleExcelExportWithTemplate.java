package vn.com.unit.ep2p.export;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class SimpleExcelExportWithTemplate extends ExcelExport {
	
	private String templatePath;

	public SimpleExcelExportWithTemplate(Class<? extends Workbook> clazz, String templatePath) {
		this.templatePath = templatePath;
		this.initWorkbook(clazz);
	}
	
	public String getTemplatePath() {
		return templatePath;
	}

	public void setTemplatePath(String templatePath) {
		this.templatePath = templatePath;
	}

	@Override
	public void initWorkbook(Class<? extends Workbook> clazz) {
		FileInputStream fis;
		try {
			fis = new FileInputStream(new File(templatePath));
			
			Workbook workbook = null;
			if( clazz.isAssignableFrom(XSSFWorkbook.class) ) {
				workbook = new XSSFWorkbook(fis);
			} else {
				workbook = new HSSFWorkbook(fis);
			}
			this.setWorkbook(workbook);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
