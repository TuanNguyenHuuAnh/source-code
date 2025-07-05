package vn.com.unit.imp.excel.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.GeneralSecurityException;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.poifs.crypt.CipherAlgorithm;
import org.apache.poi.poifs.crypt.EncryptionInfo;
import org.apache.poi.poifs.crypt.EncryptionMode;
import org.apache.poi.poifs.crypt.Encryptor;
import org.apache.poi.poifs.crypt.HashAlgorithm;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.ClientAnchor;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.DataFormat;
import org.apache.poi.ss.usermodel.Drawing;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.ClientAnchor.AnchorType;
import org.apache.poi.ss.util.CellAddress;
import org.apache.poi.ss.util.CellReference;
import org.apache.poi.util.Units;
import org.apache.poi.xssf.streaming.SXSSFCell;
import org.apache.poi.xssf.streaming.SXSSFRow;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import com.itextpdf.text.pdf.parser.clipper.Path;

import javassist.expr.NewArray;
import vn.com.unit.imp.excel.dto.ItemColsExcelDto;
import vn.com.unit.imp.excel.constant.CommonConstant;
import vn.com.unit.imp.excel.dto.CellStyleDto;

public class ExportExcelUtil<T> {


	private enum DataType {
		LONG, DOUBLE, INTEGER, STRING, DATE, TIMESTAMP, INT, BIGDECIMAL, BOOLEAN, BYTE;
	}
	
	private static String EMP_STRING = "";

	/** List<T> */
	private List<T> data;

	/**
	 * Get data
	 * 
	 * @return List<T>
	 * @author phunghn
	 */
	public List<T> getData() {
		return data != null ? new ArrayList<>(data) : null;
	}

	/**
	 * Set data
	 * 
	 * @param data
	 *            type List<T>
	 * @return
	 * @author phunghn
	 */
	public void setData(List<T> data) {
		this.data = data != null ? new ArrayList<>(data) : null;
	}

	private SXSSFWorkbook workbook;

	private XSSFWorkbook workbookXS;

	public XSSFWorkbook getWorkbookXS() {
		return workbookXS;
	}

	public void setWorkbookXS(XSSFWorkbook workbookXS) {
		this.workbookXS = workbookXS;
	}

	/**
	 * Get workbook
	 * 
	 * @return SXSSFWorkbook
	 * @author phunghn
	 */
	public SXSSFWorkbook getWorkbook() {
		return workbook;
	}

	/**
	 * Set workbook
	 * 
	 * @param workbook
	 *            type SXSSFWorkbook
	 * @return
	 * @author phunghn
	 */
	public void setWorkbook(SXSSFWorkbook workbook) {
		this.workbook = workbook;
	}

	private static final Logger logger = LoggerFactory.getLogger(ExportExcelUtil.class);

	public void ExportExcel(String template, Locale locale, List<T> listData, Class<T> objDto,
			List<ItemColsExcelDto> cols, String datePattern)
			throws IOException, IllegalArgumentException, IllegalAccessException, InstantiationException {

		File file = new File(template);
		
            
        
		try (FileInputStream fileInputStream = new FileInputStream(file);
				XSSFWorkbook xssfWorkbook = new XSSFWorkbook(fileInputStream);
				SXSSFWorkbook workbookExport = new SXSSFWorkbook(xssfWorkbook, 1000);) {

			// create sheet of file excel
			SXSSFSheet sxssfSheet = workbookExport.getSheetAt(0);
			sxssfSheet.setRandomAccessWindowSize(1000);

			CellReference landMark = new CellReference("A5");
			int startRow = landMark.getRow();
			// style
			CellStyle cellStyleCenter = xssfWorkbook.createCellStyle();
			cellStyleCenter.setAlignment(HorizontalAlignment.CENTER);
			CellStyle cellStyleRight = xssfWorkbook.createCellStyle();
			cellStyleRight.setAlignment(HorizontalAlignment.RIGHT);
			// format
			NumberFormat formatCurrency = NumberFormat.getInstance(locale);

			// createHelper
			CreationHelper createHelper = xssfWorkbook.getCreationHelper();
			// dataFormat
			DataFormat dataFormat = createHelper.createDataFormat();
			// cellStyleDateCenter
			CellStyle cellStyleDateCenter = xssfWorkbook.createCellStyle();
			cellStyleDateCenter.setAlignment(HorizontalAlignment.CENTER);
			// See class org.apache.poi.ss.usermodel.BuiltinFormats for more details about
			// format
			cellStyleDateCenter.setDataFormat(dataFormat.getFormat("dd/mm/yyyy"));

			// field of objDto
			Map<String, Field> mapFields = new HashMap<String, Field>();
			T objDefault = objDto.newInstance();
			Class<?> cls = objDefault.getClass();
			Field[] fields = cls.getDeclaredFields();
			for (Field f : fields) {
				mapFields.put(f.getName().toUpperCase(), f);
			}
			if (listData != null) {
				if (listData.size() > 0) {
					for (int i = 0; i < listData.size(); i++) {

						SXSSFRow row = sxssfSheet.createRow(startRow);
						T excelDto = listData.get(i);
						if (excelDto != null) {
							// set value to map
							Field[] headerFields = objDto.getDeclaredFields();
							Map<String, Object> mapValueFields = new HashMap<String, Object>();
							for (Field field : headerFields) {
								if (!field.isAccessible()) {
									field.setAccessible(true);
								}
								mapValueFields.put(field.getName().toUpperCase(), field.get(excelDto));
							}
							// begin fill to cell
							try {
							for (ItemColsExcelDto col : cols) {
								// data type of field
								SXSSFCell cell = row.createCell(col.getColIndex());
								// System.out.println("AAAAA: " +
								// col.getColName());
								Field field = mapFields.get(col.getColName().toUpperCase());
								String typeFields = field.getType().getSimpleName().toUpperCase();
								DataType dataType = DataType.valueOf(typeFields);
								switch (dataType) {
								case LONG:
									if (mapValueFields.get(col.getColName().toUpperCase()) != null) {
										Long valueOfLong = Long.parseLong(
												mapValueFields.get(col.getColName().toUpperCase()).toString());
										cell.setCellValue(valueOfLong);
										cell.setCellStyle(cellStyleRight);
									}
									break;
								case INTEGER:
									if (mapValueFields.get(col.getColName().toUpperCase()) != null) {
										Integer valueOfInteger = Integer.parseInt(
												mapValueFields.get(col.getColName().toUpperCase()).toString());
										cell.setCellValue(valueOfInteger);
										cell.setCellStyle(cellStyleRight);
									}
									break;
								case INT:
									if (mapValueFields.get(col.getColName().toUpperCase()) != null) {
										int valueOfInt = Integer.parseInt(
												mapValueFields.get(col.getColName().toUpperCase()).toString());
										cell.setCellValue(valueOfInt);
										if (col.getColName().equals("ROWNUM")) {
											cell.setCellStyle(cellStyleCenter);
										}
										cell.setCellStyle(cellStyleRight);
									}
									break;
								case DOUBLE:
									if (mapValueFields.get(col.getColName().toUpperCase()) != null) {
										Double valueOfDouble = Double.parseDouble(
												mapValueFields.get(col.getColName().toUpperCase()).toString());
										cell.setCellValue(valueOfDouble);
										cell.setCellStyle(cellStyleRight);
									}
									break;
								case BIGDECIMAL:
									if (mapValueFields.get(col.getColName().toUpperCase()) != null) {
										cell.setCellValue(formatCurrency
												.format(mapValueFields.get(col.getColName().toUpperCase())).toString());
										cell.setCellStyle(cellStyleRight);
									}
									break;
								case DATE:
									if (mapValueFields.get(col.getColName().toUpperCase()) != null) {
										// cell.setCellValue(formatDate
										// .format(mapValueFields.get(col.getColName().toUpperCase())).toString());
										// cell.setCellStyle(cellStyleCenter);
										// cell.setCellType(CellType.NUMERIC);
										// cell.setCellType(Cell.CELL_TYPE_NUMERIC);

										cell.setCellValue((Date) mapValueFields.get(col.getColName().toUpperCase()));
										cell.setCellStyle(cellStyleDateCenter);
									}
									break;
								case STRING:
									if (mapValueFields.get(col.getColName().toUpperCase()) != null) {
										cell.setCellValue(
												mapValueFields.get(col.getColName().toUpperCase()).toString());
									}
									break;
								case BOOLEAN:
									if (mapValueFields.get(col.getColName().toUpperCase()) != null) {
										cell.setCellValue(
												mapValueFields.get(col.getColName().toUpperCase()).toString());
										cell.setCellStyle(cellStyleCenter);
									}
									break;
								// TRITV add for QR Code
								case BYTE:
									if (mapValueFields.get(col.getColName().toUpperCase()) != null) {
										try {
											row.setHeight((short) (100 * 20));
											// cell.setCellValue(mapValueFields.get(col.getColName().toUpperCase()).toString());
											byte[] bytes = (byte[]) mapValueFields.get(col.getColName().toUpperCase());
											int pictureIdx = xssfWorkbook.addPicture(bytes, Workbook.PICTURE_TYPE_PNG);

											drawImageOnExcelSheet(sxssfSheet, cell.getRowIndex(), col.getColIndex(), 1,
													1, 300, 425, pictureIdx);
										} catch (Exception e) {
											e.printStackTrace();
										}
										// cell.setCellValue("TODO: DANG CHEN QR");
										cell.setCellStyle(cellStyleCenter);

									}
									break;
								default:
									break;
								}

							} // END FOR 2
							startRow += 1;
						} catch (Exception e) {
				            e.printStackTrace();
				        }
						}
					}
				} // end for
			}

			this.workbook = workbookExport;

		}
		

	}
	
    /**
     * drawImageOnExcelSheet
     * @param sheet
     * @param row
     * @param col
     * @param left in px
     * @param top in pt
     * @param width in px
     * @param height in pt
     * @param pictureIdx
     * @throws Exception
     * @author tritv
     */
    private void drawImageOnExcelSheet(SXSSFSheet sheet, int row, int col,
            int left, int top, int width,
            int height, int pictureIdx) throws Exception {

        CreationHelper helper = sheet.getWorkbook().getCreationHelper();
        Drawing<?> drawing = sheet.createDrawingPatriarch();

        ClientAnchor anchor = helper.createClientAnchor();
        anchor.setAnchorType(AnchorType.MOVE_AND_RESIZE);

        anchor.setCol1(col); // first anchor determines upper left position
        anchor.setRow1(row);
        anchor.setDx1(Units.pixelToEMU(left)); // dx = left in px
        anchor.setDy1(Units.toEMU(top)); // dy = top in pt

        anchor.setCol2(col); // second anchor determines bottom right position
        anchor.setRow2(row);
        anchor.setDx2(Units.pixelToEMU(left + width)); // dx = left + wanted width in px
        anchor.setDy2(Units.toEMU(top + height)); // dy= top + wanted height in pt

        drawing.createPicture(anchor, pictureIdx);
    }

	public void exportExcelWithXSSF(String template, Locale locale, List<T> listData, Class<T> objDto,
			List<ItemColsExcelDto> cols, String datePattern, HttpServletResponse res, String templateName,
			String passExport) throws IOException, GeneralSecurityException, IllegalArgumentException,
			IllegalAccessException, InstantiationException {

		// FileInputStream fileInputStream = null;
		File file = new File(template);
		try (FileInputStream fileInputStream = new FileInputStream(file);
				XSSFWorkbook xssfWorkbook = new XSSFWorkbook(fileInputStream);) {
			// SXSSFWorkbook workbookExport = new SXSSFWorkbook(xssfWorkbook,
			// 1000);

			// create sheet of file excel
			XSSFSheet sxssfSheet = xssfWorkbook.getSheetAt(0);
			// sxssfSheet.setRandomAccessWindowSize(1000);

			CellReference landMark = new CellReference("A5");
			int startRow = landMark.getRow();
			// style
			CellStyle cellStyleCenter = xssfWorkbook.createCellStyle();
			cellStyleCenter.setAlignment(HorizontalAlignment.CENTER);
			CellStyle cellStyleRight = xssfWorkbook.createCellStyle();
			cellStyleRight.setAlignment(HorizontalAlignment.RIGHT);

			// createHelper
			CreationHelper createHelper = xssfWorkbook.getCreationHelper();
			// dataFormat
			DataFormat dataFormat = createHelper.createDataFormat();
			// cellStyleDateCenter
			CellStyle cellStyleDateCenter = xssfWorkbook.createCellStyle();
			cellStyleDateCenter.setAlignment(HorizontalAlignment.CENTER);
			// See class org.apache.poi.ss.usermodel.BuiltinFormats for more details about
			// format
			cellStyleDateCenter.setDataFormat(dataFormat.getFormat("dd/mm/yyyy"));

			// format
			NumberFormat formatCurrency = NumberFormat.getInstance(locale);
			// field of objDto
			Map<String, Field> mapFields = new HashMap<>();
			T objDefault = objDto.newInstance();
			Class<?> cls = objDefault.getClass();
			Field[] fields = cls.getDeclaredFields();
			for (Field f : fields) {
				mapFields.put(f.getName().toUpperCase(), f);
			}
			if (listData != null) {
				if (listData.size() > 0) {
					for (int i = 0; i < listData.size(); i++) {

						XSSFRow row = sxssfSheet.createRow(startRow);
						T excelDto = listData.get(i);
						if (excelDto != null) {
							// set value to map
							Field[] headerFields = objDto.getDeclaredFields();
							Map<String, Object> mapValueFields = new HashMap<String, Object>();
							for (Field field : headerFields) {
								if (!field.isAccessible()) {
									field.setAccessible(true);
								}
								mapValueFields.put(field.getName().toUpperCase(), field.get(excelDto));
							}
							// begin fill to cell
							for (ItemColsExcelDto col : cols) {
								// data type of field
								XSSFCell cell = row.createCell(col.getColIndex());
								// System.out.println("AAAAA: " +
								// col.getColName());
								Field field = mapFields.get(col.getColName().toUpperCase());
								String typeFields = field.getType().getSimpleName().toUpperCase();
								DataType dataType = DataType.valueOf(typeFields);
								switch (dataType) {
								case LONG:
									if (mapValueFields.get(col.getColName().toUpperCase()) != null) {
										Long valueOfLong = Long.parseLong(
												mapValueFields.get(col.getColName().toUpperCase()).toString());
										cell.setCellValue(valueOfLong);
										cell.setCellStyle(cellStyleRight);
									}
									break;
								case INTEGER:
									if (mapValueFields.get(col.getColName().toUpperCase()) != null) {
										Integer valueOfInteger = Integer.parseInt(
												mapValueFields.get(col.getColName().toUpperCase()).toString());
										cell.setCellValue(valueOfInteger);
										cell.setCellStyle(cellStyleRight);
									}
									break;
								case INT:
									if (mapValueFields.get(col.getColName().toUpperCase()) != null) {
										int valueOfInt = Integer.parseInt(
												mapValueFields.get(col.getColName().toUpperCase()).toString());
										cell.setCellValue(valueOfInt);
										if (col.getColName().equals("ROWNUM")) {
											cell.setCellStyle(cellStyleCenter);
										}
										cell.setCellStyle(cellStyleRight);
									}
									break;
								case DOUBLE:
									if (mapValueFields.get(col.getColName().toUpperCase()) != null) {
										Double valueOfDouble = Double.parseDouble(
												mapValueFields.get(col.getColName().toUpperCase()).toString());
										cell.setCellValue(valueOfDouble);
										cell.setCellStyle(cellStyleRight);
									}
									break;
								case BIGDECIMAL:
									if (mapValueFields.get(col.getColName().toUpperCase()) != null) {
										cell.setCellValue(formatCurrency
												.format(mapValueFields.get(col.getColName().toUpperCase())).toString());
										cell.setCellStyle(cellStyleRight);
									}
									break;
								case DATE:
									if (mapValueFields.get(col.getColName().toUpperCase()) != null) {
										// cell.setCellValue(formatDate
										// .format(mapValueFields.get(col.getColName().toUpperCase())).toString());
										// cell.setCellStyle(cellStyleCenter);
										cell.setCellStyle(cellStyleDateCenter);
										cell.setCellValue((Date) mapValueFields.get(col.getColName().toUpperCase()));
									}
									break;
								case TIMESTAMP:
									if (mapValueFields.get(col.getColName().toUpperCase()) != null) {
										cell.setCellStyle(cellStyleDateCenter);
										cell.setCellValue((Date) mapValueFields.get(col.getColName().toUpperCase()));
									}
									break;
								case STRING:
									if (mapValueFields.get(col.getColName().toUpperCase()) != null) {
										cell.setCellValue(
												mapValueFields.get(col.getColName().toUpperCase()).toString());
									}
									break;
								case BOOLEAN:
									if (mapValueFields.get(col.getColName().toUpperCase()) != null) {
										cell.setCellValue(
												mapValueFields.get(col.getColName().toUpperCase()).toString());
										cell.setCellStyle(cellStyleCenter);
									}
									break;
								default:
									break;
								}

							} // END FOR 2
							startRow += 1;
						}
					}
				} // end for
			}

			for (int i = 0; i <= cols.size(); i++) {
				sxssfSheet.autoSizeColumn(i);
			}

			// set point view and active cell default
			sxssfSheet.setActiveCell(new CellAddress(landMark));
			sxssfSheet.getCTWorksheet().getSheetViews().getSheetViewArray(0).setTopLeftCell("A1");

			// this.workbookXS = xssfWorkbook;
			SimpleDateFormat formatDateExport = new SimpleDateFormat("yyyyMMdd_HHmmss");
			String currentDate = formatDateExport.format(new Date());

			ServletOutputStream outputStream = res.getOutputStream();
			res.setContentType(CommonConstant.CONTENT_TYPE_EXCEL);
			res.addHeader(CommonConstant.CONTENT_DISPOSITION, CommonConstant.ATTCHMENT_FILENAME + templateName + "_"
					+ currentDate + CommonConstant.TYPE_EXCEL + "\"");

			if (passExport != null && !passExport.equals("")) {
				// ConstantExcelWithPassword.createAndWriteEncryptedWorkbook(response.getOutputStream(),
				// xssfWorkbook,
				// passExport);
				try (POIFSFileSystem fileSystem = new POIFSFileSystem()) {
					// encrypt data
					EncryptionInfo encryptionInfo = new EncryptionInfo(EncryptionMode.agile, CipherAlgorithm.aes256,
							HashAlgorithm.sha512, -1, -1, null);
					Encryptor encryptor = encryptionInfo.getEncryptor();
					encryptor.confirmPassword(passExport);

					OutputStream outputStremEncryped = encryptor.getDataStream(fileSystem);
					// OutputStream outputStremEncryped =
					// getEncryptingOutputStream(fileSystem, passExport);
					xssfWorkbook.write(outputStremEncryped);
					fileSystem.writeFilesystem(res.getOutputStream());
					outputStremEncryped.flush();
				}
			} else {
				xssfWorkbook.write(outputStream);
				outputStream.flush();
			}
			if (xssfWorkbook != null) {
				xssfWorkbook.close();
			}
		}

	}

	public void ExportExcelWithXSSF(String template, Locale locale, List<T> listData, Class<T> objDto,
			List<ItemColsExcelDto> cols, String datePatten, HttpServletResponse response, String templateName,
			String passExport, int sheet, String cellReference) throws IOException {

		// FileInputStream fileInputStream = null;
		XSSFWorkbook xssfWorkbook = null;
		try {
			File file = new File(template);
			try (FileInputStream fileInputStream = new FileInputStream(file)) {
				xssfWorkbook = new XSSFWorkbook(fileInputStream);
			}
			// SXSSFWorkbook workbookExport = new SXSSFWorkbook(xssfWorkbook,
			// 1000);

			// create sheet of file excel
			XSSFSheet sxssfSheet = xssfWorkbook.getSheetAt(sheet);
			// sxssfSheet.setRandomAccessWindowSize(1000);

			CellReference landMark = new CellReference(cellReference);
			int startRow = landMark.getRow();
			// style
			CellStyle cellStyleCenter = xssfWorkbook.createCellStyle();
			cellStyleCenter.setAlignment(HorizontalAlignment.CENTER);
			CellStyle cellStyleRight = xssfWorkbook.createCellStyle();
			cellStyleRight.setAlignment(HorizontalAlignment.RIGHT);
			// format
			NumberFormat formatCurrency = NumberFormat.getInstance(locale);

			// createHelper
			CreationHelper createHelper = xssfWorkbook.getCreationHelper();
			// dataFormat
			DataFormat dataFormat = createHelper.createDataFormat();

			// cellStyleDateCenter
			CellStyle cellStyleDateCenter = xssfWorkbook.createCellStyle();
			cellStyleDateCenter.setAlignment(HorizontalAlignment.CENTER);
			// See class org.apache.poi.ss.usermodel.BuiltinFormats for more details about
			// format
			cellStyleDateCenter.setDataFormat(dataFormat.getFormat("dd/mm/yyyy"));

			// field of objDto
			Map<String, Field> mapFields = new HashMap<String, Field>();
			T objDefault = objDto.newInstance();
			Class<?> cls = objDefault.getClass();
			Field[] fields = cls.getDeclaredFields();
			for (Field f : fields) {
				mapFields.put(f.getName().toUpperCase(), f);
			}
			if (listData != null) {
				if (listData.size() > 0) {
					for (int i = 0; i < listData.size(); i++) {

						XSSFRow row = sxssfSheet.createRow(startRow);
						T excelDto = listData.get(i);
						if (excelDto != null) {
							// set value to map
							Field[] headerFields = objDto.getDeclaredFields();
							Map<String, Object> mapValueFields = new HashMap<String, Object>();
							for (Field field : headerFields) {
								if (!field.isAccessible()) {
									field.setAccessible(true);
								}
								mapValueFields.put(field.getName().toUpperCase(), field.get(excelDto));
							}
							// begin fill to cell
							for (ItemColsExcelDto col : cols) {
								// data type of field
								XSSFCell cell = row.createCell(col.getColIndex());
								// System.out.println("AAAAA: " +
								// col.getColName());
								Field field = mapFields.get(col.getColName().toUpperCase());
								String typeFields = field.getType().getSimpleName().toUpperCase();
								DataType dataType = DataType.valueOf(typeFields);
								switch (dataType) {
								case LONG:
									if (mapValueFields.get(col.getColName().toUpperCase()) != null) {
										Long valueOfLong = Long.parseLong(
												mapValueFields.get(col.getColName().toUpperCase()).toString());
										cell.setCellValue(valueOfLong);
										cell.setCellStyle(cellStyleRight);
									}
									break;
								case INTEGER:
									if (mapValueFields.get(col.getColName().toUpperCase()) != null) {
										Integer valueOfInteger = Integer.parseInt(
												mapValueFields.get(col.getColName().toUpperCase()).toString());
										cell.setCellValue(valueOfInteger);
										cell.setCellStyle(cellStyleRight);
									}
									break;
								case INT:
									if (mapValueFields.get(col.getColName().toUpperCase()) != null) {
										int valueOfInt = Integer.parseInt(
												mapValueFields.get(col.getColName().toUpperCase()).toString());
										cell.setCellValue(valueOfInt);
										if (col.getColName().equals("ROWNUM")) {
											cell.setCellStyle(cellStyleCenter);
										}
										cell.setCellStyle(cellStyleRight);
									}
									break;
								case DOUBLE:
									if (mapValueFields.get(col.getColName().toUpperCase()) != null) {
										Double valueOfDouble = Double.parseDouble(
												mapValueFields.get(col.getColName().toUpperCase()).toString());
										cell.setCellValue(valueOfDouble);
										cell.setCellStyle(cellStyleRight);
									}
									break;
								case BIGDECIMAL:
									if (mapValueFields.get(col.getColName().toUpperCase()) != null) {
										cell.setCellValue(formatCurrency
												.format(mapValueFields.get(col.getColName().toUpperCase())).toString());
										cell.setCellStyle(cellStyleRight);
									}
									break;
								case DATE:
									if (mapValueFields.get(col.getColName().toUpperCase()) != null) {
										// cell.setCellValue(formatDate
										// .format(mapValueFields.get(col.getColName().toUpperCase())).toString());
										// cell.setCellStyle(cellStyleCenter);
										cell.setCellValue((Date) mapValueFields.get(col.getColName().toUpperCase()));
										cell.setCellStyle(cellStyleDateCenter);
									}
									break;
								case STRING:
									if (mapValueFields.get(col.getColName().toUpperCase()) != null) {
										cell.setCellValue(
												mapValueFields.get(col.getColName().toUpperCase()).toString());
									}
									break;
								case BOOLEAN:
									if (mapValueFields.get(col.getColName().toUpperCase()) != null) {
										cell.setCellValue(
												mapValueFields.get(col.getColName().toUpperCase()).toString());
										cell.setCellStyle(cellStyleCenter);
									}
									break;
								default:
									break;
								}

							} // END FOR 2
							startRow += 1;
						}
					}
				} // end for
			}

			for (int i = 0; i <= cols.size(); i++) {
				sxssfSheet.autoSizeColumn(i);
			}

			// set point view and active cell default
			sxssfSheet.setActiveCell(new CellAddress(landMark));
			sxssfSheet.getCTWorksheet().getSheetViews().getSheetViewArray(0).setTopLeftCell("A1");

			// this.workbookXS = xssfWorkbook;
			SimpleDateFormat formatDateExport = new SimpleDateFormat("yyyyMMdd_HHmmss");
			String currentDate = formatDateExport.format(new Date());

			ServletOutputStream outputStream = response.getOutputStream();
			response.setContentType(CommonConstant.CONTENT_TYPE_EXCEL);
			response.addHeader(CommonConstant.CONTENT_DISPOSITION, CommonConstant.ATTCHMENT_FILENAME + templateName
					+ "_" + currentDate + CommonConstant.TYPE_EXCEL + "\"");

			if (passExport != null && !passExport.equals("")) {
				// ConstantExcelWithPassword.createAndWriteEncryptedWorkbook(response.getOutputStream(),
				// xssfWorkbook,
				// passExport);
				try (POIFSFileSystem fileSystem = new POIFSFileSystem()) {
					// encrypt data
					EncryptionInfo encryptionInfo = new EncryptionInfo(EncryptionMode.agile, CipherAlgorithm.aes256,
							HashAlgorithm.sha512, -1, -1, null);
					Encryptor encryptor = encryptionInfo.getEncryptor();
					encryptor.confirmPassword(passExport);

					OutputStream outputStremEncryped = encryptor.getDataStream(fileSystem);
					// OutputStream outputStremEncryped =
					// getEncryptingOutputStream(fileSystem, passExport);
					xssfWorkbook.write(outputStremEncryped);
					fileSystem.writeFilesystem(response.getOutputStream());
					outputStremEncryped.flush();
				}
			} else {
				xssfWorkbook.write(outputStream);
				outputStream.flush();
			}
			if (xssfWorkbook != null) {
				xssfWorkbook.close();
			}
		} catch (Exception ex) {
			logger.error("#ExportExcelWithXSSF#", ex);
			if (xssfWorkbook != null) {
				xssfWorkbook.close();
			}
		} finally {
			if (xssfWorkbook != null) {
				xssfWorkbook.close();
			}
		}

	}
	
    public void exportExcelWithXSSFNonPass(String template, Locale locale, List<T> listData, Class<T> objDto,
            List<ItemColsExcelDto> cols, String datePattern, HttpServletResponse res, String templateName,
            String startExportRows) throws IOException, GeneralSecurityException, IllegalArgumentException,
            IllegalAccessException, InstantiationException, InvocationTargetException {

        File file = new File(template);
        try (FileInputStream fileInputStream = new FileInputStream(file);
                XSSFWorkbook xssfWorkbook = new XSSFWorkbook(fileInputStream);
                SXSSFWorkbook sxssfWorkbook = new SXSSFWorkbook(xssfWorkbook, 1000);) {

            this.setDataToXSSFWorkbook(sxssfWorkbook, listData, objDto, cols, datePattern, startExportRows, false, null,
                    0, null);

            // this.workbookXS = xssfWorkbook;
            SimpleDateFormat formatDateExport = new SimpleDateFormat("yyyyMMdd_HHmmss");
            String currentDate = formatDateExport.format(new Date());

            ServletOutputStream outputStream = res.getOutputStream();
            res.setContentType(CommonConstant.CONTENT_TYPE_EXCEL);
            res.addHeader(CommonConstant.CONTENT_DISPOSITION, CommonConstant.ATTCHMENT_FILENAME + templateName + "_"
                    + currentDate + CommonConstant.TYPE_EXCEL + "\"");

            sxssfWorkbook.write(outputStream);
            outputStream.flush();
        }
    }
	
	public void exportExcelWithXSSFNonPass(String template, Locale locale, List<T> listData, Class<T> objDto, List<ItemColsExcelDto> cols,
            String datePattern, HttpServletResponse res, String templateName) throws IOException, GeneralSecurityException,
            IllegalArgumentException, IllegalAccessException, InstantiationException, InvocationTargetException {

        File file = new File(template);
        try (FileInputStream fileInputStream = new FileInputStream(file);
                XSSFWorkbook xssfWorkbook = new XSSFWorkbook(fileInputStream);
                SXSSFWorkbook sxssfWorkbook = new SXSSFWorkbook(xssfWorkbook, 1000);) {

            this.setDataToXSSFWorkbook(sxssfWorkbook, listData, objDto, cols, datePattern, "A5", false, null, 0, null);

            // this.workbookXS = xssfWorkbook;
            SimpleDateFormat formatDateExport = new SimpleDateFormat("yyyyMMdd_HHmmss");
            String currentDate = formatDateExport.format(new Date());

            ServletOutputStream outputStream = res.getOutputStream();
            res.setContentType(CommonConstant.CONTENT_TYPE_EXCEL);
            res.addHeader(CommonConstant.CONTENT_DISPOSITION,
                    CommonConstant.ATTCHMENT_FILENAME + templateName + "_" + currentDate + CommonConstant.TYPE_EXCEL + "\"");

            sxssfWorkbook.write(outputStream);
            outputStream.flush();
        }
    }
	
	// xuất excel với footer
	public void exportExcelWithXSSFWithFooter(String template, Locale locale, List<T> lstData, Class<T> objDto, List<ItemColsExcelDto> cols
			, String datePattern, HttpServletResponse res, String templateName, Map<Integer, String> mapFooterDate)  throws IOException, GeneralSecurityException,
			IllegalArgumentException, IllegalAccessException, InstantiationException, InvocationTargetException {

		File file = new File(template);
		try (FileInputStream fileInputStream = new FileInputStream(file);
			 XSSFWorkbook xssfWorkbook = new XSSFWorkbook(fileInputStream);
			 SXSSFWorkbook sxssfWorkbook = new SXSSFWorkbook(xssfWorkbook, 1000);) {

			this.setDataToXSSFWorkbookWithFooter(sxssfWorkbook, lstData, objDto, cols, datePattern, "A5", true, null, 0, null, mapFooterDate);

			// this.workbookXS = xssfWorkbook;
			SimpleDateFormat formatDateExport = new SimpleDateFormat("yyyyMMdd_HHmmss");
			String currentDate = formatDateExport.format(new Date());

			ServletOutputStream outputStream = res.getOutputStream();
			res.setContentType(CommonConstant.CONTENT_TYPE_EXCEL);
			res.addHeader(CommonConstant.CONTENT_DISPOSITION,
					CommonConstant.ATTCHMENT_FILENAME + templateName + "_" + currentDate + CommonConstant.TYPE_EXCEL + "\"");

			sxssfWorkbook.write(outputStream);
			outputStream.flush();
		}

	}
	public void setDataToXSSFWorkbookWithFooter(SXSSFWorkbook sxssfWorkbook, List<T> listData, Class<T> objDto, List<ItemColsExcelDto> cols, String datePattern,
									  String cellReference, boolean isAllBorder, Map<String, String> mapColFormat, int isFormatFinance, XSSFColor colorToTal
								, Map<Integer, String> mapFooterData)
			throws InstantiationException, IllegalAccessException, InvocationTargetException {
		// FileInputStream fileInputStream = null;
		// create sheet of file excel
		SXSSFSheet sxssfSheet = sxssfWorkbook.getSheetAt(0);
		CellReference landMark = new CellReference(cellReference);

		int startRow = landMark.getRow();
		// cellStyleDto
		CellStyleDto cellStyleDto = new CellStyleDto(sxssfWorkbook, "Arial", isAllBorder, isFormatFinance, datePattern) ;
		//cellstyle footer
		CellStyle cellStyleFooter = sxssfWorkbook.createCellStyle();
		Font font = sxssfWorkbook.createFont();
		font.setBold(true);
		cellStyleFooter.setAlignment(HorizontalAlignment.CENTER);
		cellStyleFooter.setWrapText(true);
		cellStyleFooter.setFont(font);
		// cellStyleDtoForTotal
		CellStyleDto cellStyleDtoForTotal = new CellStyleDto(cellStyleDto, colorToTal);

		// field of objDto
		Map<String, Field> mapFields = new HashMap<>();
		T objDefault = objDto.newInstance();
		Class<?> cls = objDefault.getClass();
		Field[] fields = populateFields(cls);
		for (Field f : fields) {
			mapFields.put(f.getName().toUpperCase(), f);
		}
		if (listData != null) {
			int dataSize = listData.size();
			if (dataSize > 0) {
				for (int i = 0; i < dataSize; i++) {

					// DÃ²ng last
					if (colorToTal != null && i == dataSize - 1) {
						// Do fill data
						this.doFillData(sxssfSheet, listData, objDto, cols, mapColFormat, mapFields, startRow, i, cellStyleDtoForTotal, dataSize, true);
					} else {
						// Do fill data
						this.doFillData(sxssfSheet, listData, objDto, cols, mapColFormat, mapFields, startRow, i, cellStyleDto, dataSize, false);
					}

					startRow += 1;
				}
			} // end for
			doFillDataWithFooter(sxssfSheet, startRow, cellStyleFooter, mapFooterData);
		}

		// set point view and active cell default
		sxssfSheet.setActiveCell(new CellAddress(landMark));
	}
	private void doFillDataWithFooter(SXSSFSheet sxssfSheet, int rowIndex, CellStyle cellStyle,  Map<Integer, String> mapFooterData)
			throws IllegalArgumentException, IllegalAccessException {

//        SXSSFRow row = sxssfSheet.createRow(rowIndex);
		SXSSFRow row = sxssfSheet.getRow(rowIndex);
		if(row == null) {
			row = sxssfSheet.createRow(rowIndex);
		}
		for (Map.Entry<Integer, String> entry : mapFooterData.entrySet()) {
			Integer k = entry.getKey();
			String v = entry.getValue();
			SXSSFCell cell = row.createCell(k);
			cell.setCellValue(v);
			cell.setCellStyle(cellStyle);
		}

	}
	public void exportExcelWithXSSFNonDec(String template, Locale locale, List<T> listData, Class<T> objDto, List<ItemColsExcelDto> cols,
            String datePattern, HttpServletResponse res, String templateName, Map<String, String> mapColumnFormat) throws IOException, GeneralSecurityException,
            IllegalArgumentException, IllegalAccessException, InstantiationException, InvocationTargetException {

        File file = new File(template);
        try (FileInputStream fileInputStream = new FileInputStream(file);
                XSSFWorkbook xssfWorkbook = new XSSFWorkbook(fileInputStream);
                SXSSFWorkbook sxssfWorkbook = new SXSSFWorkbook(xssfWorkbook, 1000);) {

            this.setDataToXSSFWorkbook(sxssfWorkbook, listData, objDto, cols, datePattern, "A5", false, mapColumnFormat, 0, null);

            // this.workbookXS = xssfWorkbook;
            SimpleDateFormat formatDateExport = new SimpleDateFormat("yyyyMMdd_HHmmss");
            String currentDate = formatDateExport.format(new Date());

            ServletOutputStream outputStream = res.getOutputStream();
            res.setContentType(CommonConstant.CONTENT_TYPE_EXCEL);
            res.addHeader(CommonConstant.CONTENT_DISPOSITION,
                    CommonConstant.ATTCHMENT_FILENAME + templateName + "_" + currentDate + CommonConstant.TYPE_EXCEL + "\"");

            sxssfWorkbook.write(outputStream);
            outputStream.flush();
        }
    }
    /**
     * Export với những column được chỉ định trong mapColFormat 
     * Map<String, Integer> mapColFormat: ứng  với String: column - Integer:scale
     * 
     * exportExcelWithXSSFNonPassWithScale
     * @param template
     * @param locale
     * @param listData
     * @param objDto
     * @param cols
     * @param datePattern
     * @param res
     * @param templateName
     * @param mapColFormat
     * @throws IOException
     * @throws GeneralSecurityException
     * @throws IllegalArgumentException
     * @throws IllegalAccessException
     * @throws InstantiationException
     */
    public void exportExcelWithXSSFNonPassWithScale(String template, Locale locale, List<T> listData, Class<T> objDto, List<ItemColsExcelDto> cols,
            String datePattern, HttpServletResponse res, String templateName, Map<String, Integer> mapColFormat)
            throws IOException, GeneralSecurityException, IllegalArgumentException, IllegalAccessException, InstantiationException {

        // FileInputStream fileInputStream = null;
        File file = new File(template);
        try (FileInputStream fileInputStream = new FileInputStream(file); XSSFWorkbook xssfWorkbook = new XSSFWorkbook(fileInputStream);) {
            // create sheet of file excel
            XSSFSheet sxssfSheet = xssfWorkbook.getSheetAt(0);
            CellReference landMark = new CellReference("A5");
            int startRow = landMark.getRow();
            // style date-center number-right text-left
            CellStyle cellStyleCenter = xssfWorkbook.createCellStyle();
            cellStyleCenter.setAlignment(HorizontalAlignment.CENTER);
            
            CellStyle cellStyleRightDecScale = xssfWorkbook.createCellStyle();
            cellStyleRightDecScale.setAlignment(HorizontalAlignment.RIGHT);
            
            CellStyle cellStyleRightDec = xssfWorkbook.createCellStyle();
            cellStyleRightDec.setAlignment(HorizontalAlignment.RIGHT);
           
            CellStyle cellStyleRightNonDec = xssfWorkbook.createCellStyle();
            cellStyleRightNonDec.setAlignment(HorizontalAlignment.RIGHT);
            
            CellStyle cellStyleRight = xssfWorkbook.createCellStyle();
            cellStyleRight.setAlignment(HorizontalAlignment.RIGHT);
            
            CellStyle cellStyleLeft = xssfWorkbook.createCellStyle();
            cellStyleLeft.setAlignment(HorizontalAlignment.LEFT);

            // createHelper
            CreationHelper createHelper = xssfWorkbook.getCreationHelper();
            // dataFormat
            DataFormat dataFormat = createHelper.createDataFormat();
            cellStyleRightDec.setDataFormat(dataFormat.getFormat("#,##0.00"));
            cellStyleRightNonDec.setDataFormat(dataFormat.getFormat("#,##0"));
            // cellStyleDateCenter
            CellStyle cellStyleDateCenter = xssfWorkbook.createCellStyle();
            cellStyleDateCenter.setAlignment(HorizontalAlignment.CENTER);
            // See class org.apache.poi.ss.usermodel.BuiltinFormats for more details about
            // format
            cellStyleDateCenter.setDataFormat(dataFormat.getFormat(datePattern));

            // format
//            NumberFormat formatCurrency = NumberFormat.getInstance(locale);
            // field of objDto
            Map<String, Field> mapFields = new HashMap<>();
            T objDefault = objDto.newInstance();
            Class<?> cls = objDefault.getClass();
			Field[] fields = populateFields(cls);
			for (Field f : fields) {
                mapFields.put(f.getName().toUpperCase(), f);
            }
            if (listData != null) {
                if (listData.size() > 0) {
                    for (int i = 0; i < listData.size(); i++) {

                        XSSFRow row = sxssfSheet.createRow(startRow);
                        T excelDto = listData.get(i);
                        if (excelDto != null) {
                            // set value to map
                            Field[] headerFields = populateFields(objDto);
                            Map<String, Object> mapValueFields = new HashMap<String, Object>();
                            for (Field field : headerFields) {
                                if (!field.isAccessible()) {
                                    field.setAccessible(true);
                                }
                                mapValueFields.put(field.getName().toUpperCase(), field.get(excelDto));
                            }
                            // begin fill to cell
                            for (ItemColsExcelDto col : cols) {
                                // data type of field
                                XSSFCell cell = row.createCell(col.getColIndex());
                                // col.getColName());
                                Field field = mapFields.get(col.getColName().toUpperCase());
                                String typeFields = field.getType().getSimpleName().toUpperCase();
                                DataType dataType = DataType.valueOf(typeFields);
                                
                                switch (dataType) {
                                case LONG:
                                    if (mapValueFields.get(col.getColName().toUpperCase()) != null) {
                                        Long valueOfLong = Long.parseLong(mapValueFields.get(col.getColName().toUpperCase()).toString());
//                                        cell.setCellValue(valueOfLong);
                                        cell.setCellValue(valueOfLong.toString());
                                        cell.setCellStyle(cellStyleRight);
                                    }
                                    break;
                                case INTEGER:
                                    if (mapValueFields.get(col.getColName().toUpperCase()) != null) {
                                        Integer valueOfInteger = Integer
                                                .parseInt(mapValueFields.get(col.getColName().toUpperCase()).toString());
                                        cell.setCellValue(valueOfInteger);
                                        cell.setCellStyle(cellStyleRight);
                                    }
                                    break;
                                case INT:
                                    if (mapValueFields.get(col.getColName().toUpperCase()) != null) {
                                        int valueOfInt = Integer.parseInt(mapValueFields.get(col.getColName().toUpperCase()).toString());
                                        cell.setCellValue(valueOfInt);
                                        if (col.getColName().equals("ROWNUM") || col.getColName().equals("NO")) {
                                            cell.setCellStyle(cellStyleCenter);
                                        }
                                        else {
                                        	cell.setCellStyle(cellStyleRight);
                                        }
                                        
                                    }
                                    break;
                                case DOUBLE:
                                    if (mapValueFields.get(col.getColName().toUpperCase()) != null) {
                                        Double valueOfDouble = Double
                                                .parseDouble(mapValueFields.get(col.getColName().toUpperCase()).toString());
                                        cell.setCellValue(valueOfDouble);
                                        cell.setCellStyle(cellStyleRight);
                                    }
                                    break;
                                case BIGDECIMAL:
                                    /*if (mapValueFields.get(col.getColName().toUpperCase()) != null) {
                                        cell.setCellValue(
                                                formatCurrency.format(mapValueFields.get(col.getColName().toUpperCase())).toString());
                                        cell.setCellStyle(cellStyleRight);
                                    }
                                    break;*/
                                	if (mapValueFields.get(col.getColName().toUpperCase()) != null) {
                                    	BigDecimal valueBigdecimal = (BigDecimal) mapValueFields.get(col.getColName().toUpperCase());
                                        cell.setCellValue(valueBigdecimal.doubleValue());
                                        
                                        //cell.setCellValue(formatCurrency.format(mapValueFields.get(col.getColName().toUpperCase())));
										if (valueBigdecimal.doubleValue() % 1 > 0) {
											// lay ra column can format theo scale
											Integer scale = mapColFormat.get(col.getColName());
											if(scale != null){
												setCellStyleWithScale(scale, cellStyleRightDecScale, xssfWorkbook);
											}else {
												cell.setCellStyle(cellStyleRightDec);	
											}
										} else {
											cell.setCellStyle(cellStyleRightNonDec);
										}
                                    }
                                    break;
                                case DATE:
                                    if (mapValueFields.get(col.getColName().toUpperCase()) != null) {
                                        cell.setCellStyle(cellStyleDateCenter);
                                        cell.setCellValue((Date) mapValueFields.get(col.getColName().toUpperCase()));
                                    }
                                    break;
                                case TIMESTAMP:
                                    if (mapValueFields.get(col.getColName().toUpperCase()) != null) {
                                        cell.setCellStyle(cellStyleDateCenter);
                                        cell.setCellValue((Date) mapValueFields.get(col.getColName().toUpperCase()));
                                        cell.setCellStyle(cellStyleCenter);
                                    }
                                    break;
                                case STRING:
                                    if (mapValueFields.get(col.getColName().toUpperCase()) != null) {
                                        cell.setCellValue(mapValueFields.get(col.getColName().toUpperCase()).toString());
                                        cell.setCellStyle(cellStyleLeft);
                                    }
                                    break;
                                case BOOLEAN:
                                    if (mapValueFields.get(col.getColName().toUpperCase()) != null) {
                                        cell.setCellValue(mapValueFields.get(col.getColName().toUpperCase()).toString());
                                        cell.setCellStyle(cellStyleCenter);
                                    }
                                    break;
                                default:
                                    break;
                                }

                            } // END FOR 2
                            startRow += 1;
                        }
                    }
                } // end for
            }

//            for (int i = 0; i <= cols.size(); i++) {
//                sxssfSheet.autoSizeColumn(i);
//            }

            // set point view and active cell default
            sxssfSheet.setActiveCell(new CellAddress(landMark));
            sxssfSheet.getCTWorksheet().getSheetViews().getSheetViewArray(0).setTopLeftCell("A1");

            // this.workbookXS = xssfWorkbook;
            SimpleDateFormat formatDateExport = new SimpleDateFormat("yyyyMMdd_HHmmss");
            String currentDate = formatDateExport.format(new Date());

            ServletOutputStream outputStream = res.getOutputStream();
            res.setContentType(CommonConstant.CONTENT_TYPE_EXCEL);
            res.addHeader(CommonConstant.CONTENT_DISPOSITION,
                    CommonConstant.ATTCHMENT_FILENAME + templateName + "_" + currentDate + CommonConstant.TYPE_EXCEL + "\"");

            xssfWorkbook.write(outputStream);
            outputStream.flush();

            if (xssfWorkbook != null) {
                xssfWorkbook.close();
            }
        }
    }
    
    public void exportExcelWithXSSFNonPassNonFormatNumber(String template, Locale locale, List<T> listData, Class<T> objDto, List<ItemColsExcelDto> cols,
            String datePattern, HttpServletResponse res, String templateName)
            throws IOException, GeneralSecurityException, IllegalArgumentException, IllegalAccessException, InstantiationException {

        // FileInputStream fileInputStream = null;
        File file = new File(template);
        try (FileInputStream fileInputStream = new FileInputStream(file); XSSFWorkbook xssfWorkbook = new XSSFWorkbook(fileInputStream);) {
            // create sheet of file excel
            XSSFSheet sxssfSheet = xssfWorkbook.getSheetAt(0);
            CellReference landMark = new CellReference("A5");
            int startRow = landMark.getRow();
            // style date-center number-right text-left
            CellStyle cellStyleCenter = xssfWorkbook.createCellStyle();
            cellStyleCenter.setAlignment(HorizontalAlignment.CENTER);
            cellStyleCenter.setWrapText(true);
            CellStyle cellStyleRight = xssfWorkbook.createCellStyle();
            cellStyleRight.setAlignment(HorizontalAlignment.RIGHT);
            cellStyleRight.setWrapText(true);
            CellStyle cellStyleLeft = xssfWorkbook.createCellStyle();
            cellStyleLeft.setAlignment(HorizontalAlignment.LEFT);
            cellStyleLeft.setWrapText(true);

            // createHelper
            CreationHelper createHelper = xssfWorkbook.getCreationHelper();
            // dataFormat
            DataFormat dataFormat = createHelper.createDataFormat();
            // cellStyleDateCenter
            CellStyle cellStyleDateCenter = xssfWorkbook.createCellStyle();
            cellStyleDateCenter.setAlignment(HorizontalAlignment.CENTER);
            // See class org.apache.poi.ss.usermodel.BuiltinFormats for more details about
            // format
            cellStyleDateCenter.setDataFormat(dataFormat.getFormat(datePattern));
            
            // field of objDto
            Map<String, Field> mapFields = new HashMap<>();
            T objDefault = objDto.newInstance();
            Class<?> cls = objDefault.getClass();
            Field[] fields = populateFields(cls);
            for (Field f : fields) {
                mapFields.put(f.getName().toUpperCase(), f);
            }
            if (listData != null) {
                if (listData.size() > 0) {
                    for (int i = 0; i < listData.size(); i++) {

                        XSSFRow row = sxssfSheet.createRow(startRow);
                        T excelDto = listData.get(i);
                        if (excelDto != null) {
                            // set value to map
                            Field[] headerFields = populateFields(objDto);
                            Map<String, Object> mapValueFields = new HashMap<String, Object>();
                            for (Field field : headerFields) {
                                if (!field.isAccessible()) {
                                    field.setAccessible(true);
                                }
                                mapValueFields.put(field.getName().toUpperCase(), field.get(excelDto));
                            }
                            // begin fill to cell
                            for (ItemColsExcelDto col : cols) {
                                // data type of field
                                XSSFCell cell = row.createCell(col.getColIndex());
                                // col.getColName());
                                Field field = mapFields.get(col.getColName().toUpperCase());
                                String typeFields = field.getType().getSimpleName().toUpperCase();
                                DataType dataType = DataType.valueOf(typeFields);
                                switch (dataType) {
                                case LONG:
                                    if (mapValueFields.get(col.getColName().toUpperCase()) != null) {
                                        Long valueOfLong = Long.parseLong(mapValueFields.get(col.getColName().toUpperCase()).toString());
                                        cell.setCellValue(valueOfLong.toString());
                                        cell.setCellStyle(cellStyleRight);
                                    }
                                    break;
                                case INTEGER:
                                    if (mapValueFields.get(col.getColName().toUpperCase()) != null) {
                                        Integer valueOfInteger = Integer
                                                .parseInt(mapValueFields.get(col.getColName().toUpperCase()).toString());
                                        cell.setCellValue(valueOfInteger);
                                        cell.setCellStyle(cellStyleRight);
                                    }
                                    break;
                                case INT:
                                    if (mapValueFields.get(col.getColName().toUpperCase()) != null) {
                                        int valueOfInt = Integer.parseInt(mapValueFields.get(col.getColName().toUpperCase()).toString());
                                        cell.setCellValue(valueOfInt);
                                        if (col.getColName().equals("ROWNUM") || col.getColName().equals("NO")) {
                                            cell.setCellStyle(cellStyleCenter);
                                        }
                                        else {
                                            cell.setCellStyle(cellStyleRight);
                                        }
                                        
                                    }
                                    break;
                                case DOUBLE:
                                    if (mapValueFields.get(col.getColName().toUpperCase()) != null) {
                                        Double valueOfDouble = Double
                                                .parseDouble(mapValueFields.get(col.getColName().toUpperCase()).toString());
                                        cell.setCellValue(valueOfDouble);
                                        cell.setCellStyle(cellStyleRight);
                                    }
                                    break;
                                case BIGDECIMAL:
                                    if (mapValueFields.get(col.getColName().toUpperCase()) != null) {
                                        BigDecimal valueBigdecimal = (BigDecimal) mapValueFields.get(col.getColName().toUpperCase());
                                        cell.setCellValue(valueBigdecimal.doubleValue());
                                        
                                        //cell.setCellValue(formatCurrency.format(mapValueFields.get(col.getColName().toUpperCase())));
                                        short currencyFormat = valueBigdecimal.doubleValue() % 1 > 0 ? dataFormat.getFormat("##0.00") : dataFormat.getFormat("##0");
                                        cellStyleRight.setDataFormat(currencyFormat);
                                        cell.setCellStyle(cellStyleRight);
                                    }
                                    break;
                                case DATE:
                                    if (mapValueFields.get(col.getColName().toUpperCase()) != null) {
                                        cell.setCellStyle(cellStyleDateCenter);
                                        cell.setCellValue((Date) mapValueFields.get(col.getColName().toUpperCase()));
                                    }
                                    break;
                                case TIMESTAMP:
                                    if (mapValueFields.get(col.getColName().toUpperCase()) != null) {
                                        cell.setCellStyle(cellStyleDateCenter);
                                        cell.setCellValue((Date) mapValueFields.get(col.getColName().toUpperCase()));
                                        cell.setCellStyle(cellStyleCenter);
                                    }
                                    break;
                                case STRING:
                                    if (mapValueFields.get(col.getColName().toUpperCase()) != null) {
                                        cell.setCellValue(mapValueFields.get(col.getColName().toUpperCase()).toString());
                                        cell.setCellStyle(cellStyleLeft);
                                    }
                                    break;
                                case BOOLEAN:
                                    if (mapValueFields.get(col.getColName().toUpperCase()) != null) {
                                        cell.setCellValue(mapValueFields.get(col.getColName().toUpperCase()).toString());
                                        cell.setCellStyle(cellStyleCenter);
                                    }
                                    break;
                                default:
                                    break;
                                }

                            } // END FOR 2
                            startRow += 1;
                        }
                    }
                } // end for
            }
            
            sxssfSheet.setActiveCell(new CellAddress(landMark));
            sxssfSheet.getCTWorksheet().getSheetViews().getSheetViewArray(0).setTopLeftCell("A1");

            // this.workbookXS = xssfWorkbook;
            SimpleDateFormat formatDateExport = new SimpleDateFormat("yyyyMMdd_HHmmss");
            String currentDate = formatDateExport.format(new Date());

            ServletOutputStream outputStream = res.getOutputStream();
            res.setContentType(CommonConstant.CONTENT_TYPE_EXCEL);
            res.addHeader(CommonConstant.CONTENT_DISPOSITION,
                    CommonConstant.ATTCHMENT_FILENAME + templateName + "_" + currentDate + CommonConstant.TYPE_EXCEL + "\"");

            xssfWorkbook.write(outputStream);
            outputStream.flush();

            if (xssfWorkbook != null) {
                xssfWorkbook.close();
            }
        }
    }

	private Field[] populateFields(Class<?> cls) {
		Field[] fieldsSuper = cls.getSuperclass().getDeclaredFields();
		Field[] fieldsChild = cls.getDeclaredFields();
		int superLength = fieldsSuper.length;
		int childLength = fieldsChild.length;
		Field[] fields = new Field[superLength + childLength];
		System.arraycopy(fieldsSuper, 0, fields, 0, superLength);
		System.arraycopy(fieldsChild, 0, fields, superLength, childLength);
		return fields;
	}

	public String templateNameSuff() {
        SimpleDateFormat sf = new SimpleDateFormat("yyyyMMdd_HHmmss");
        return sf.format(new Date());
    }
    
    public String extendTemplateNameSuff(String templateName, String extend) {
        return templateName.split("\\.")[0] + "_" + templateNameSuff() + "." + extend;
    }
    
    
    public void doExportSheetWithMap(XSSFWorkbook workbook, int sheetPosition, List<Map<String,Object>> data, Map<String, String> mapFields, //Class<T> clazz
			List<ItemColsExcelDto> cols, String datePattern, String cellReference) throws Exception {
        double timeStart = System.currentTimeMillis();
        if (workbook != null) {
            XSSFSheet sheet = workbook.getSheetAt(sheetPosition);
            CellReference landMark = new CellReference(cellReference);
            int startRow = landMark.getRow();
            // style format
            CellStyle styleCenter = workbook.createCellStyle();
            CellStyle styleRight = workbook.createCellStyle();
            CellStyle styleCurrency = workbook.createCellStyle();
            CreationHelper createHelper = workbook.getCreationHelper();
            DataFormat dataFormat = createHelper.createDataFormat();
            short currencyFormat = 0;
            styleCenter.setAlignment(HorizontalAlignment.CENTER);
            styleRight.setAlignment(HorizontalAlignment.RIGHT);
            styleCurrency.setAlignment(HorizontalAlignment.RIGHT);
            // field of clazz
            //Map<String, Field> mapFields = new HashMap<>();
//            Field[] fields = clazz.getDeclaredFields();
//            for (Field f : fields) {
//                mapFields.put(f.getName().toUpperCase(), f);
//            }
            // set value to map
            //Map<String, Object> mapValueFields = new HashMap<>();
            int size = data.size();
            XSSFRow row = null;
            Map<String,Object> excelDto = null;
            String field = null;
            DataType dataType = null;
            String colName = "", strValue = "";

			// 01-Jan-2018
			// 10:10:10
//			SimpleDateFormat sf = new SimpleDateFormat("yyyy-mm-dd HH:mm:ss");

			SimpleDateFormat formatDate = new SimpleDateFormat(datePattern);
			for (int i = 0; i < size; i++) {
                excelDto = data.get(i);
                if (excelDto == null) {
                    throw new Exception("Excel Dto null");
                }
                // set value to map
//                for (Field fieldTmp : fields) {
//                    fieldTmp.setAccessible(true);
//                    mapValueFields.put(fieldTmp.getName().toUpperCase(), fieldTmp.get(excelDto));
//                }
                row =  sheet.getRow(startRow);
                if(row == null){
                	row = sheet.createRow(startRow);
                }
                
                // begin fill to cell
                for (ItemColsExcelDto col : cols) {
                    colName = col.getColName();
                    // data type of field
                    XSSFCell cell = null;
                    cell =  row.getCell(col.getColIndex());
                    if(cell== null){
                    	cell = row.createCell(col.getColIndex());
                    }
                    
                    field = mapFields.get(colName);
                    dataType = DataType.valueOf(field);
                    // get value as string
                    strValue = String.valueOf(excelDto.get(colName));
                    // break if value is null or empty
                    if ("null".equals(strValue) || "".equals(strValue)) {
                        continue;
                    }
                    // detect type and write cell
                    switch (dataType) {
                    case STRING:
                        // check for boolean value
                        if (strValue.equalsIgnoreCase("y") || strValue.equalsIgnoreCase("n") || strValue.equalsIgnoreCase("true")
                                || strValue.equalsIgnoreCase("false") || cell.getCellStyle() == null) {
                        		cell.setCellStyle(styleCenter);
                            
                        } 
//                        CellStyle cellStyle_ = cell.getCellStyle();
//                        HorizontalAlignment hor = cellStyle_.getAlignmentEnum();
//                        String dataType_ = cellStyle_.getDataFormatString();
                        cell.setCellValue(strValue);
                        break;
                    case DATE:
                        try {
                            cell.setCellValue(formatDate.format(excelDto.get(colName)));
                        } catch (Exception e) {
                        	logger.info(e.getMessage().toString());
                            cell.setCellValue(strValue);
                        }
                        if(cell.getCellStyle() == null){
                        	cell.setCellStyle(styleCenter);
                        }
                        
                        
                        break;
                    case BIGDECIMAL:
                        try {
                            BigDecimal valueBigdecimal = (BigDecimal)excelDto.get(colName);
                            cell.setCellValue(valueBigdecimal.doubleValue());
                            currencyFormat = valueBigdecimal.doubleValue() % 1 > 0 ? dataFormat.getFormat("#,##0.00") : dataFormat.getFormat("#,##0");
                        } catch (Exception e) {
                        	logger.info(e.getMessage().toString());
                            cell.setCellValue(strValue);
                        }
                        styleCurrency.setDataFormat(currencyFormat);
                        if(cell.getCellStyle() == null){
                        	cell.setCellStyle(styleCurrency);
                        }
                        else{
                        	CellStyle cellStyle = cell.getCellStyle();
                        	cellStyle.setAlignment(HorizontalAlignment.RIGHT);
                        	cellStyle.setDataFormat(currencyFormat);
                        	cell.setCellStyle(cellStyle);
                        }
                        
                        break;
                    case BOOLEAN:
                        cell.setCellValue(strValue);
                        if(cell.getCellStyle() == null){
                        	 cell.setCellStyle(styleCenter);
                        }
                        break;
                    default:
                        // for numeric
                        double valueDouble = Double.parseDouble(strValue);
                        cell.setCellValue(valueDouble);
                        if (("ROWNUM").equals(colName)) {
                        	if(cell.getCellStyle() == null){
                           	 cell.setCellStyle(styleCenter);
                           }
                        } else {
                            currencyFormat = valueDouble % 1 > 0 ? dataFormat.getFormat("#,##0.00") : dataFormat.getFormat("#,##0");
                            styleCurrency.setDataFormat(currencyFormat);
                            if(cell.getCellStyle() == null){
                            	cell.setCellStyle(styleCurrency);
                            }
                            else{
                            	CellStyle cellStyle = cell.getCellStyle();
                            	cellStyle.setAlignment(HorizontalAlignment.RIGHT);
                            	cellStyle.setDataFormat(currencyFormat);
                            	cell.setCellStyle(cellStyle);
                            }
                        }
                        break;
                    }
                }
                startRow++;
            }
            // auto resize col width
            for (int i = 0; i <= cols.size(); i++) {
                sheet.autoSizeColumn(i);
            }
            // set point view and active cell default
            sheet.setActiveCell(new CellAddress(landMark));
            sheet.getCTWorksheet().getSheetViews().getSheetViewArray(0).setTopLeftCell("A1");
            logger.info(String.format("\n---##Export Excel done##------------Finish in %.2fs-------------#",
                    (System.currentTimeMillis() - timeStart) / 1000));
        }
	}
    
    
    
    
    public void doExportSheet(XSSFWorkbook workbook, int sheetPosition, List<T> data, Class<T> clazz,
			List<ItemColsExcelDto> cols, String datePattern, String cellReference) throws Exception {
        double timeStart = System.currentTimeMillis();
        if (workbook != null) {
            XSSFSheet sheet = workbook.getSheetAt(sheetPosition);
            CellReference landMark = new CellReference(cellReference);
            int startRow = landMark.getRow();
            // style format
            CellStyle styleCenter = workbook.createCellStyle();
            CellStyle styleRight = workbook.createCellStyle();
            CellStyle styleCurrency = workbook.createCellStyle();
            CreationHelper createHelper = workbook.getCreationHelper();
            DataFormat dataFormat = createHelper.createDataFormat();
            short currencyFormat = 0;
            styleCenter.setAlignment(HorizontalAlignment.CENTER);
            styleRight.setAlignment(HorizontalAlignment.RIGHT);
            styleCurrency.setAlignment(HorizontalAlignment.RIGHT);
            // field of clazz
            Map<String, Field> mapFields = new HashMap<>();
            Field[] fields = clazz.getDeclaredFields();
            for (Field f : fields) {
                mapFields.put(f.getName().toUpperCase(), f);
            }
            // set value to map
            Map<String, Object> mapValueFields = new HashMap<>();
            int size = data.size();
            XSSFRow row = null;
            T excelDto = null;
            Field field = null;
            DataType dataType = null;
            String colName = "", strValue = "";
            SimpleDateFormat sf = new SimpleDateFormat("yyyy-mm-dd HH:mm:ss"); // 01-Jan-2018
                                                                               // 10:10:10
            SimpleDateFormat formatDate = new SimpleDateFormat(datePattern);
            for (int i = 0; i < size; i++) {
                excelDto = data.get(i);
                if (excelDto == null) {
                    throw new Exception("Excel Dto null");
                }
                // set value to map
                for (Field fieldTmp : fields) {
                    fieldTmp.setAccessible(true);
                    mapValueFields.put(fieldTmp.getName().toUpperCase(), fieldTmp.get(excelDto));
                }
                row =  sheet.getRow(startRow);
                if(row == null){
                	row = sheet.createRow(startRow);
                }
                
                // begin fill to cell
                for (ItemColsExcelDto col : cols) {
                    colName = col.getColName();
                    // data type of field
                    XSSFCell cell = null;
                    cell =  row.getCell(col.getColIndex());
                    if(cell== null){
                    	cell = row.createCell(col.getColIndex());
                    }
                    
                    field = mapFields.get(colName);
                    dataType = DataType.valueOf(field.getType().getSimpleName().toUpperCase());
                    // get value as string
                    strValue = String.valueOf(mapValueFields.get(colName));
                    // break if value is null or empty
                    if ("null".equals(strValue) || "".equals(strValue)) {
                        continue;
                    }
                    // detect type and write cell
                    switch (dataType) {
                    case STRING:
                        // check for boolean value
                        if (strValue.equalsIgnoreCase("y") || strValue.equalsIgnoreCase("n") || strValue.equalsIgnoreCase("true")
                                || strValue.equalsIgnoreCase("false")) {
                        	if(cell.getCellStyle() == null){
                        		cell.setCellStyle(styleCenter);
                        	}
                            
                        } else {
                            try {
                                // date value
                                strValue = formatDate.format(sf.parse(strValue));
                                
                                
                                if(cell.getCellStyle() == null){
                                	 cell.setCellStyle(styleCenter);
                                }
                                
                               
                            } catch (Exception e) {
                                logger.info(e.getMessage().toString());
                                try {
                                    SimpleDateFormat sfTry = new SimpleDateFormat(datePattern);
                                    strValue = formatDate.format(sfTry.parse(strValue));
                                    
                                    
                                    if(cell.getCellStyle() == null){
                                    	cell.setCellStyle(styleCenter);
                                    }
                                    
                                } catch (Exception e1) {
                                	logger.info(e.getMessage().toString());
                                }
                            }
                        }
                        cell.setCellValue(strValue);
                        break;
                    case DATE:
                        try {
                            cell.setCellValue(formatDate.format(mapValueFields.get(colName)));
                        } catch (Exception e) {
                        	logger.info(e.getMessage().toString());
                            cell.setCellValue(strValue);
                        }
                        if(cell.getCellStyle() == null){
                        	cell.setCellStyle(styleCenter);
                        }
                        
                        
                        break;
                    case BIGDECIMAL:
                        try {
                            BigDecimal valueBigdecimal = (BigDecimal) mapValueFields.get(colName);
                            cell.setCellValue(valueBigdecimal.doubleValue());
                            currencyFormat = valueBigdecimal.doubleValue() % 1 > 0 ? dataFormat.getFormat("#,##0.00") : dataFormat.getFormat("#,##0");
                        } catch (Exception e) {
                        	logger.info(e.getMessage().toString());
                            cell.setCellValue(strValue);
                        }
                        styleCurrency.setDataFormat(currencyFormat);
                        if(cell.getCellStyle() == null){
                        	cell.setCellStyle(styleCurrency);
                        }
                        else{
                        	CellStyle cellStyle = cell.getCellStyle();
                        	cellStyle.setAlignment(HorizontalAlignment.RIGHT);
                        	cellStyle.setDataFormat(currencyFormat);
                        	cell.setCellStyle(cellStyle);
                        }
                        
                        break;
                    case BOOLEAN:
                        cell.setCellValue(strValue);
                        if(cell.getCellStyle() == null){
                        	 cell.setCellStyle(styleCenter);
                        }
                        break;
                    default:
                        // for numeric
                        double valueDouble = Double.parseDouble(strValue);
                        cell.setCellValue(valueDouble);
                        if (("ROWNUM").equals(colName)) {
                        	if(cell.getCellStyle() == null){
                           	 cell.setCellStyle(styleCenter);
                           }
                        } else {
                            currencyFormat = valueDouble % 1 > 0 ? dataFormat.getFormat("#,##0.00") : dataFormat.getFormat("#,##0");
                            styleCurrency.setDataFormat(currencyFormat);
                            if(cell.getCellStyle() == null){
                            	cell.setCellStyle(styleCurrency);
                            }
                            else{
                            	CellStyle cellStyle = cell.getCellStyle();
                            	cellStyle.setAlignment(HorizontalAlignment.RIGHT);
                            	cellStyle.setDataFormat(currencyFormat);
                            	cell.setCellStyle(cellStyle);
                            }
                        }
                        break;
                    }
                }
                startRow++;
            }
            // auto resize col width
            for (int i = 0; i <= cols.size(); i++) {
                sheet.autoSizeColumn(i);
            }
            // set point view and active cell default
            sheet.setActiveCell(new CellAddress(landMark));
            sheet.getCTWorksheet().getSheetViews().getSheetViewArray(0).setTopLeftCell("A1");
            logger.info(String.format("\n---##Export Excel done##------------Finish in %.2fs-------------#",
                    (System.currentTimeMillis() - timeStart) / 1000));
        }
	}
    
    
    public void writeOutputWorkbook(XSSFWorkbook workbook, HttpServletResponse res, String templateName,
			String passExport, boolean isUseCurrentDate) throws IOException, GeneralSecurityException {
		ServletOutputStream outputStream = res.getOutputStream();
		res.setContentType(CommonConstant.CONTENT_TYPE_EXCEL);
		if (isUseCurrentDate) {
			String currentDate = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
			res.addHeader(CommonConstant.CONTENT_DISPOSITION, String.format("%s%s_%s%s", "attachment; filename=",
					templateName, currentDate, CommonConstant.TYPE_EXCEL));
		} else {
			res.addHeader(CommonConstant.CONTENT_DISPOSITION,
					String.format("%s%s%s", "attachment; filename=", templateName, CommonConstant.TYPE_EXCEL));
		}
		if (StringUtils.isNotEmpty(passExport)) {
			try (POIFSFileSystem fileSystem = new POIFSFileSystem()) {
				// encrypt data
				EncryptionInfo encryptionInfo = new EncryptionInfo(EncryptionMode.agile, CipherAlgorithm.aes256,
						HashAlgorithm.sha512, -1, -1, null);
				Encryptor encryptor = encryptionInfo.getEncryptor();
				encryptor.confirmPassword(passExport);
				OutputStream outputStreamEncryped = encryptor.getDataStream(fileSystem);
				workbook.write(outputStreamEncryped);
				fileSystem.writeFilesystem(res.getOutputStream());
				outputStreamEncryped.flush();
			}
		} else {
			workbook.write(outputStream);
			outputStream.flush();
		}
	}
    
    public XSSFWorkbook getXSSFWorkbook(String template) throws Exception {
        File file = new File(template);
        FileInputStream fileInputStream = new FileInputStream(file);
        XSSFWorkbook xssfWorkbook = new XSSFWorkbook(fileInputStream);
        return xssfWorkbook;

    }
    
    /**
     * 
     * @param xssfWorkbook
     * @param locale
     * @param listData
     * @param objDto
     * @param cols
     * @param datePattern
     * @param cellReference
     * @param res
     * @param templateName
     * @throws Exception
     * @author TriTV
     */
    public void doExportExcelWithHeader(XSSFWorkbook xssfWorkbook, Locale locale, List<T> listData, Class<T> objDto, List<ItemColsExcelDto> cols,
            String datePattern, String cellReference, HttpServletResponse res, String templateName) 
            		throws Exception {
    	// create sheet of file excel
    	XSSFSheet sxssfSheet = xssfWorkbook.getSheetAt(0);

        CellReference landMark = new CellReference(cellReference);
        int startRow = landMark.getRow();
        
        // style date-center number-right text-left
        CellStyle cellStyleCenter = xssfWorkbook.createCellStyle();
        cellStyleCenter.setAlignment(HorizontalAlignment.CENTER);
        cellStyleCenter.setWrapText(true);
        cellStyleCenter.setVerticalAlignment(VerticalAlignment.CENTER);
        CellStyle cellStyleRight = xssfWorkbook.createCellStyle();
        cellStyleRight.setAlignment(HorizontalAlignment.RIGHT);
        cellStyleRight.setWrapText(true);
        cellStyleRight.setVerticalAlignment(VerticalAlignment.CENTER);
        CellStyle cellStyleLeft = xssfWorkbook.createCellStyle();
        cellStyleLeft.setAlignment(HorizontalAlignment.LEFT);
        cellStyleLeft.setWrapText(true);
        cellStyleLeft.setVerticalAlignment(VerticalAlignment.CENTER);

        // createHelper
        CreationHelper createHelper = xssfWorkbook.getCreationHelper();
        // dataFormat
        DataFormat dataFormat = createHelper.createDataFormat();
        // cellStyleDateCenter
        CellStyle cellStyleDateCenter = xssfWorkbook.createCellStyle();
        cellStyleDateCenter.setAlignment(HorizontalAlignment.CENTER);
        cellStyleDateCenter.setVerticalAlignment(VerticalAlignment.CENTER);
        // See class org.apache.poi.ss.usermodel.BuiltinFormats for more details about
        // format
        cellStyleDateCenter.setDataFormat(dataFormat.getFormat(datePattern));

        // format
//        NumberFormat formatCurrency = NumberFormat.getInstance(locale);

        // field of objDto
        Map<String, Field> mapFields = new HashMap<>();
        T objDefault = objDto.newInstance();
        Class<?> cls = objDefault.getClass();
        Field[] fields = cls.getDeclaredFields();
        for (Field f : fields) {
            mapFields.put(f.getName().toUpperCase(), f);
        }
        if (listData != null) {
            if (listData.size() > 0) {
                for (int i = 0; i < listData.size(); i++) {

                    XSSFRow row = sxssfSheet.createRow(startRow);
                    T excelDto = listData.get(i);
                    if (excelDto != null) {
                        // set value to map
                        Field[] headerFields = objDto.getDeclaredFields();
                        Map<String, Object> mapValueFields = new HashMap<String, Object>();
                        for (Field field : headerFields) {
                            if (!field.isAccessible()) {
                                field.setAccessible(true);
                            }
                            mapValueFields.put(field.getName().toUpperCase(), field.get(excelDto));
                        }
                        // begin fill to cell
                        for (ItemColsExcelDto col : cols) {
                            // data type of field
                            XSSFCell cell = row.createCell(col.getColIndex());
                            // col.getColName());
                            Field field = mapFields.get(col.getColName().toUpperCase());
                            String typeFields = field.getType().getSimpleName().toUpperCase();
                            DataType dataType = DataType.valueOf(typeFields);
                            switch (dataType) {
                            case LONG:
                                if (mapValueFields.get(col.getColName().toUpperCase()) != null) {
                                    Long valueOfLong = Long.parseLong(mapValueFields.get(col.getColName().toUpperCase()).toString());
                                    cell.setCellValue(valueOfLong);
                                    cell.setCellStyle(cellStyleRight);
                                }
                                break;
                            case INTEGER:
                                if (mapValueFields.get(col.getColName().toUpperCase()) != null) {
                                    Integer valueOfInteger = Integer
                                            .parseInt(mapValueFields.get(col.getColName().toUpperCase()).toString());
                                    cell.setCellValue(valueOfInteger);
                                    cell.setCellStyle(cellStyleRight);
                                }
                                break;
                            case INT:
                                if (mapValueFields.get(col.getColName().toUpperCase()) != null) {
                                    int valueOfInt = Integer.parseInt(mapValueFields.get(col.getColName().toUpperCase()).toString());
                                    cell.setCellValue(valueOfInt);
                                    if (col.getColName().equals("ROWNUM") || col.getColName().equals("NO")) {
                                        cell.setCellStyle(cellStyleCenter);
                                    } else {
                                    	cell.setCellStyle(cellStyleRight);
                                    }
                                }
                                break;
                            case DOUBLE:
                                if (mapValueFields.get(col.getColName().toUpperCase()) != null) {
                                    Double valueOfDouble = Double
                                            .parseDouble(mapValueFields.get(col.getColName().toUpperCase()).toString());
                                    cell.setCellValue(valueOfDouble);
                                    cell.setCellStyle(cellStyleRight);
                                }
                                break;
                            case BIGDECIMAL:
                                if (mapValueFields.get(col.getColName().toUpperCase()) != null) {
                                	BigDecimal valueBigdecimal = (BigDecimal) mapValueFields.get(col.getColName().toUpperCase());
                                    cell.setCellValue(valueBigdecimal.doubleValue());
                                	
                                    //cell.setCellValue(formatCurrency.format(mapValueFields.get(col.getColName().toUpperCase())));
                                    cellStyleRight.setDataFormat(dataFormat.getFormat("#,##0"));
                                    cell.setCellStyle(cellStyleRight);
                                }
                                break;
                            case DATE:
                                if (mapValueFields.get(col.getColName().toUpperCase()) != null) {
                                    cell.setCellStyle(cellStyleDateCenter);
                                    cell.setCellValue((Date) mapValueFields.get(col.getColName().toUpperCase()));
                                }
                                break;
                            case TIMESTAMP:
                                if (mapValueFields.get(col.getColName().toUpperCase()) != null) {
                                    cell.setCellStyle(cellStyleDateCenter);
                                    cell.setCellValue((Date) mapValueFields.get(col.getColName().toUpperCase()));
                                    cell.setCellStyle(cellStyleCenter);
                                }
                                break;
                            case STRING:
                                if (mapValueFields.get(col.getColName().toUpperCase()) != null) {
                                    cell.setCellValue(mapValueFields.get(col.getColName().toUpperCase()).toString());
                                    cell.setCellStyle(cellStyleLeft);
                                }
                                break;
                            case BOOLEAN:
                                if (mapValueFields.get(col.getColName().toUpperCase()) != null) {
                                    cell.setCellValue(mapValueFields.get(col.getColName().toUpperCase()).toString());
                                    cell.setCellStyle(cellStyleCenter);
                                }
                                break;
                            default:
                                break;
                            }

                        } // END FOR 2
                        startRow += 1;
                    }
                }
            } // end for
        }

        // set point view and active cell default
        sxssfSheet.setActiveCell(new CellAddress(landMark));
        sxssfSheet.getCTWorksheet().getSheetViews().getSheetViewArray(0).setTopLeftCell("A1");

        // this.workbookXS = xssfWorkbook;
        SimpleDateFormat formatDateExport = new SimpleDateFormat("yyyyMMdd_HHmmss");
        String currentDate = formatDateExport.format(new Date());

        ServletOutputStream outputStream = res.getOutputStream();
        res.setContentType(CommonConstant.CONTENT_TYPE_EXCEL);
        res.addHeader(CommonConstant.CONTENT_DISPOSITION,
                CommonConstant.ATTCHMENT_FILENAME + templateName + "_" + currentDate + CommonConstant.TYPE_EXCEL + "\"");

        xssfWorkbook.write(outputStream);
        outputStream.flush();

        if (xssfWorkbook != null) {
            xssfWorkbook.close();
        }
    }
    
    /**
     * 
     * @param xssfWorkbook
     * @param sheetIndex
     * @param locale
     * @param listData
     * @param objDto
     * @param cols
     * @param datePattern
     * @param cellReference
     * @param mapColFormat
     * @param mapColStyle
     * @param mapColDefaultValue
     * @param colorToTal
     * @param res
     * @param templateName
     * @throws Exception
     * @author TaiTM
     */
    public void doExportExcelHeaderWithColFormat(XSSFWorkbook xssfWorkbook, Integer sheetIndex, Locale locale,
            List<T> listData, Class<T> objDto, List<ItemColsExcelDto> cols, String datePattern, String cellReference,
            Map<String, String> mapColFormat, Map<String, CellStyle> mapColStyle,
            Map<String, Object> mapColDefaultValue, XSSFColor colorToTal, boolean isAllBorder,
            HttpServletResponse res, String templateName, boolean exportFile) throws Exception {
        // create sheet of file excel
        XSSFSheet sxssfSheet = null;
        if (sheetIndex == null) {
            sxssfSheet = xssfWorkbook.getSheetAt(0);
        } else {
            sxssfSheet = xssfWorkbook.getSheetAt(sheetIndex);
        }

        if (sxssfSheet == null) {
            throw new Exception("Cannot find Sheet!!!");
        }
        
        CellReference landMark = new CellReference(cellReference);
        int startRow = landMark.getRow();

        // cellStyleDto
        CellStyleDto cellStyleDto = new CellStyleDto(xssfWorkbook, "Arial", isAllBorder, datePattern);

        // cellStyleDtoForTotal
        CellStyleDto cellStyleDtoForTotal = new CellStyleDto(cellStyleDto, colorToTal);

        // field of objDto
        Map<String, Field> mapFields = new HashMap<>();
        Field[] fields = populateFields(objDto);
        for (Field f : fields) {
            mapFields.put(f.getName().toUpperCase(), f);
        }

        if (listData != null) {
            if (listData.size() > 0) {
                int dataSize = listData.size();
                for (int i = 0; i < listData.size(); i++) {
                    if (colorToTal != null && i == dataSize - 1) {
                        // Do fill data
                        fillDataForCell(sxssfSheet, listData, objDto, cols, mapColFormat, mapColStyle, mapColDefaultValue, mapFields, startRow, i,
                                cellStyleDtoForTotal, dataSize, true);
                    } else {
                        // Do fill data
                        fillDataForCell(sxssfSheet, listData, objDto, cols, mapColFormat, mapColStyle, mapColDefaultValue, mapFields, startRow, i,
                                cellStyleDto, dataSize, false);
                    }
                    startRow += 1;
                }
            }
        }

        // set point view and active cell default
        sxssfSheet.setActiveCell(new CellAddress(landMark));
        sxssfSheet.getCTWorksheet().getSheetViews().getSheetViewArray(0).setTopLeftCell("A1");
        
        if (exportFile) {
            // this.workbookXS = xssfWorkbook;
            SimpleDateFormat formatDateExport = new SimpleDateFormat("yyyyMMdd_HHmmss");
            String currentDate = formatDateExport.format(new Date());

            templateName = templateName.replace(CommonConstant.TYPE_EXCEL, "");
            
            ServletOutputStream outputStream = res.getOutputStream();
            res.setContentType(CommonConstant.CONTENT_TYPE_EXCEL);
            res.addHeader(CommonConstant.CONTENT_DISPOSITION, CommonConstant.ATTCHMENT_FILENAME + templateName + "_"
                    + currentDate + CommonConstant.TYPE_EXCEL + "\"");

            xssfWorkbook.write(outputStream);
            outputStream.flush();

            if (xssfWorkbook != null) {
                xssfWorkbook.close();
            }
        }
    }
    
    public void fillDataForCell(XSSFSheet sxssfSheet, List<T> listData, Class<T> objDto, List<ItemColsExcelDto> cols,
            Map<String, String> mapColFormat, Map<String, CellStyle> mapColStyle,
            Map<String, Object> mapColDefaultValue, Map<String, Field> mapFields, int rowIndex, int dataIndex,
            CellStyleDto cellStyleDto, int dataSize, boolean fillColor)
            throws IllegalArgumentException, IllegalAccessException {

        XSSFRow row = sxssfSheet.createRow(rowIndex);

        T excelDto = listData.get(dataIndex);
        if (excelDto != null) {
            // set value to map
            Field[] headerFields = populateFields(objDto);
            Map<String, Object> mapValueFields = new HashMap<String, Object>();
            for (Field field : headerFields) {
                if (!field.isAccessible()) {
                    field.setAccessible(true);
                }
                mapValueFields.put(field.getName().toUpperCase(), field.get(excelDto));
            }
            // begin fill to cell
            for (ItemColsExcelDto col : cols) {

                // data type of field
                XSSFCell cell = row.createCell(col.getColIndex());
                // col.getColName());
                Field field = mapFields.get(col.getColName().toUpperCase());
                String typeFields = field.getType().getSimpleName().toUpperCase();
                DataType dataType = DataType.valueOf(typeFields);

                String formatType = null;
                if (null != mapColFormat && mapColFormat.size() != 0) {
                    formatType = mapColFormat.get(col.getColName());
                }
                
                // col value
                Object colValue = mapValueFields.get(col.getColName().toUpperCase());

                if (mapColDefaultValue != null && mapColDefaultValue.containsKey(col.getColName())) {
                    if (null == mapColDefaultValue.get(col.getColName())
                            || EMP_STRING.equals(mapColDefaultValue.get(col.getColName()))) {
                        cell.setCellValue(EMP_STRING);
                    } else {
                        String val = String.valueOf(mapColDefaultValue.get(col.getColName()));
                        cell.setCellValue(val);
                    }
                }
                
                switch (dataType) {
                case LONG:
                    if (colValue != null) {
                        Long valueOfLong = Long.parseLong(colValue.toString());
                        cell.setCellValue(valueOfLong);
                        cell.setCellType(CellType.NUMERIC);
                    }
                    
                    cell.setCellStyle(cellStyleDto.getCellStyleLeft());
                    if (mapColStyle != null && mapColStyle.containsKey(col.getColName())) {
                        cell.setCellStyle(mapColStyle.get(col.getColName()));
                    }
                    break;
                case INTEGER:
                    if (colValue != null) {
                        Integer valueOfInteger = Integer.parseInt(colValue.toString());
                        cell.setCellValue(valueOfInteger);
                        cell.setCellType(CellType.NUMERIC);
                    }
                    
                    cell.setCellStyle(cellStyleDto.getCellStyleRight());
                    if (col.getColName().equals("ROWNUM") || col.getColName().equals("NO")) {
                        cell.setCellStyle(cellStyleDto.getCellStyleCenter());
                    }
                    
                    if (mapColStyle != null && mapColStyle.containsKey(col.getColName())) {
                        cell.setCellStyle(mapColStyle.get(col.getColName()));
                    }
                    break;
                case INT:
                    if (col.getColName().equals("ROWNUM") || col.getColName().equals("NO")) {
                        if (colValue != null && !fillColor) {
                            int valueOfInt = Integer.parseInt(colValue.toString());
                            cell.setCellValue(valueOfInt);
                        }
                        
                        cell.setCellStyle(cellStyleDto.getCellStyleCenter());
                        if (mapColStyle != null && mapColStyle.containsKey(col.getColName())) {
                            cell.setCellStyle(mapColStyle.get(col.getColName()));
                        }
                    } else {
                        if (colValue != null) {
                            int valueOfInt = Integer.parseInt(colValue.toString());
                            cell.setCellValue(valueOfInt);
                        }
                        
                        cell.setCellStyle(cellStyleDto.getCellStyleRight());
                        if (mapColStyle != null && mapColStyle.containsKey(col.getColName())) {
                            cell.setCellStyle(mapColStyle.get(col.getColName()));
                        }
                    }

                    break;
                case DOUBLE:
                    if (colValue != null) {
                        Double valueOfDouble = Double.parseDouble(colValue.toString());

                        if (valueOfDouble % 1 > 0) {
                            // format "0.00"
                            if (null != formatType && formatType.equalsIgnoreCase(CommonConstant.PERCENT)) {
                                cell.setCellValue(valueOfDouble / 100);
                                cell.setCellStyle(cellStyleDto.getCellStyleRightDouble1WithPercent());
                            } else if (null != formatType
                                    && formatType.equalsIgnoreCase(CommonConstant.DOUBLE_SHOW_ALL)) {
                                cell.setCellValue(valueOfDouble);
                                cell.setCellStyle(cellStyleDto.getCellStyleRightDouble3());
                            } else {
                                cell.setCellValue(valueOfDouble);
                                cell.setCellStyle(cellStyleDto.getCellStyleRightDouble1());
                            }
                            
                            if (mapColStyle != null && mapColStyle.containsKey(col.getColName())) {
                                cell.setCellStyle(mapColStyle.get(col.getColName()));
                            }
                        } else {
                            // format "0"
                            if (null != formatType && formatType.equalsIgnoreCase(CommonConstant.PERCENT)) {
                                cell.setCellValue(valueOfDouble / 100);
                                cell.setCellStyle(cellStyleDto.getCellStyleRightDouble2WithPercent());
                            } else {
                                cell.setCellValue(valueOfDouble);
                                cell.setCellStyle(cellStyleDto.getCellStyleRightDouble2());
                            }
                            
                            if (mapColStyle != null && mapColStyle.containsKey(col.getColName())) {
                                cell.setCellStyle(mapColStyle.get(col.getColName()));
                            }
                        }
                    } else {
                        cell.setCellStyle(cellStyleDto.getCellStyleBoder());
                        
                        if (mapColStyle != null && mapColStyle.containsKey(col.getColName())) {
                            cell.setCellStyle(mapColStyle.get(col.getColName()));
                        }
                    }

                    break;
                case BIGDECIMAL:
                    if (colValue != null) {                   	
                        BigDecimal valueBigdecimal = (BigDecimal) colValue;
                    	//update 0 -> -
                       
                    	if(((valueBigdecimal.intValue() == 0) || StringUtils.isBlank(colValue.toString())) && (valueBigdecimal.doubleValue() < new Double("-0.99") || valueBigdecimal.doubleValue() == new Double("0.0"))) {
                    		colValue = new BigDecimal(0).intValue();
                    		cell.setCellValue(Integer.parseInt(colValue.toString()));
                            cell.setCellStyle(cellStyleDto.getCellStyleRightBigDecimal3());
                            break;
                    	}
                    	//
                        if (valueBigdecimal != null) {                        	
                            cell.setCellValue(valueBigdecimal.doubleValue());
                            if (cellStyleDto.getIsFormatFinance() == 0) { // Normal
                                // format
                                // number
                                if (valueBigdecimal.doubleValue() % 1 != 0) {
                                    // format "#,##0.00"
                                    cell.setCellStyle(cellStyleDto.getCellStyleRightBigDecimal1());
                                } else {
                                    // format "#,##0"
                                    cell.setCellStyle(cellStyleDto.getCellStyleRightBigDecimal2());
                                }
                            } else { // Format number with Finance
                                     // money
                                cell.setCellStyle(cellStyleDto.getCellStyleFinanceFormat());
                            }
                            
                            if (mapColStyle != null && mapColStyle.containsKey(col.getColName())) {
                                cell.setCellStyle(mapColStyle.get(col.getColName()));
                            }
                        }
                    } else {
                        cell.setCellStyle(cellStyleDto.getCellStyleBoder());
                        
                        if (mapColStyle != null && mapColStyle.containsKey(col.getColName())) {
                            cell.setCellStyle(mapColStyle.get(col.getColName()));
                        }
                    }

                    break;
                case DATE:
                    if (colValue != null) {
                        cell.setCellValue((Date) colValue);
                    }
                    cell.setCellStyle(cellStyleDto.getCellStyleDateCenter());

                    // short oldFormat = cellStyleDto.getCellStyleDateCenter().getDataFormat();
                    
                    // 2020 11 25 LocLT format cell date
                    try {
                    	if (formatType != null) {
                    		
//                        	short format = cellStyleDto.getSxssfWorkbook().getCreationHelper().createDataFormat().getFormat(formatType);
//                        	cellStyleDto.getCellStyleDateCenter().setDataFormat(format);
                        	
                        	SXSSFWorkbook wb = cellStyleDto.getSxssfWorkbook();
                        	CellStyle cellStyle = wb.createCellStyle();
                        	cellStyle.cloneStyleFrom(cellStyleDto.getCellStyleDateCenter());
                        	CreationHelper createHelper = wb.getCreationHelper();
                        	
                        	short fmt = createHelper.createDataFormat().getFormat(formatType);
                        	cellStyle.setDataFormat(fmt);
                        	
                        	cell.setCellStyle(cellStyle);
                        }
					} catch (Exception e) {
						// TODO: handle exception
					}
                    // 2020 11 25 LocLT format cell date

                    if (mapColStyle != null && mapColStyle.containsKey(col.getColName())) {
                        cell.setCellStyle(mapColStyle.get(col.getColName()));
                    }
                    
                    // cellStyleDto.getCellStyleDateCenter().setDataFormat(oldFormat);
                    
                    break;
                case TIMESTAMP:
                    if (colValue != null) {
                        cell.setCellValue((Date) colValue);
                    }
                    cell.setCellStyle(cellStyleDto.getCellStyleDateCenter());
                    
                    if (mapColStyle != null && mapColStyle.containsKey(col.getColName())) {
                        cell.setCellStyle(mapColStyle.get(col.getColName()));
                    }
                    break;
                case STRING:
                    if (colValue != null) {
                        cell.setCellValue(colValue.toString());
                        cell.setCellStyle(cellStyleDto.getCellStyleLeft());
                        
                        if (col.getColName().equals("MESSAGEERROR")) {
                            cell.setCellStyle(cellStyleDto.getCellStyleMessageError());
                        }
                        
                        if (col.getColName().equals("MESSAGEWARNING")) {
                            cell.setCellStyle(cellStyleDto.getCellStyleMessageWarning());
                        }
                        
                        if (col.getColName().contains("NOTE") || col.getColName().contains("DESCRIPTION")) {
                            cell.setCellStyle(cellStyleDto.getCellStyleDescription());
                        }
                        if (StringUtils.contains(colValue.toString(),"%")) {
                            cell.setCellStyle(cellStyleDto.getCellStyleRight());
                        }
                    } else {
                        cell.setCellStyle(cellStyleDto.getCellStyleBoder());
                    }
                    
                    if (mapColStyle != null && mapColStyle.containsKey(col.getColName())) {
                        cell.setCellStyle(mapColStyle.get(col.getColName()));
                    }
                    
                    break;
                case BOOLEAN:
                    if (colValue != null) {
                        cell.setCellValue(colValue.toString());
                    }
                    cell.setCellStyle(cellStyleDto.getCellStyleCenter());
                    
                    if (mapColStyle != null && mapColStyle.containsKey(col.getColName())) {
                        cell.setCellStyle(mapColStyle.get(col.getColName()));
                    }
                    break;
                default:
                    break;
                }
            } // END FOR 2
        }
    }
    
    /**
     * setDataToXSSFWorkbook
     * @param xssfWorkbook
     * @param listData
     * @param objDto
     * @param cols
     * @param datePattern
     * @param cellReference
     * @param isAllBorder
     * @throws InstantiationException
     * @throws IllegalAccessException
     * @throws InvocationTargetException 
     */
    public void setDataToXSSFWorkbook(SXSSFWorkbook sxssfWorkbook, List<T> listData, Class<T> objDto, List<ItemColsExcelDto> cols, String datePattern,
            String cellReference, boolean isAllBorder, Map<String, String> mapColFormat, int isFormatFinance, XSSFColor colorToTal)
            throws InstantiationException, IllegalAccessException, InvocationTargetException {
        // FileInputStream fileInputStream = null;
        // create sheet of file excel
        SXSSFSheet sxssfSheet = sxssfWorkbook.getSheetAt(0);
        CellReference landMark = new CellReference(cellReference);

        int startRow = landMark.getRow();
        // cellStyleDto
        CellStyleDto cellStyleDto = new CellStyleDto(sxssfWorkbook, "Arial", isAllBorder, isFormatFinance, datePattern) ;

        // cellStyleDtoForTotal
        CellStyleDto cellStyleDtoForTotal = new CellStyleDto(cellStyleDto, colorToTal);

        // field of objDto
        Map<String, Field> mapFields = new HashMap<>();
        T objDefault = objDto.newInstance();
        Class<?> cls = objDefault.getClass();
        Field[] fields = populateFields(cls);
        for (Field f : fields) {
            mapFields.put(f.getName().toUpperCase(), f);
        }
        if (listData != null) {
            int dataSize = listData.size();
            if (dataSize > 0) {
                for (int i = 0; i < dataSize; i++) {
                    
                    // DÃ²ng last
                    if (colorToTal != null && i == dataSize - 1) {
                        // Do fill data
                        this.doFillData(sxssfSheet, listData, objDto, cols, mapColFormat, mapFields, startRow, i, cellStyleDtoForTotal, dataSize, true);
                    } else {
                        // Do fill data
                        this.doFillData(sxssfSheet, listData, objDto, cols, mapColFormat, mapFields, startRow, i, cellStyleDto, dataSize, false);
                    }
                    
                    startRow += 1;
                }
            } // end for
        }

        // set point view and active cell default
        sxssfSheet.setActiveCell(new CellAddress(landMark));
//        ((XSSFSheet) sxssfSheet).getCTWorksheet().getSheetViews().getSheetViewArray(0).setTopLeftCell("A1");
    }
    
    /**
     * setDataToXSSFWorkbook
     * @param xssfWorkbook
     * @param listData
     * @param objDto
     * @param cols
     * @param datePattern
     * @param cellReference
     * @param isAllBorder
     * @throws InstantiationException
     * @throws IllegalAccessException
     * @throws InvocationTargetException 
     */
    public void setDataToXSSFWorkbookSheet(SXSSFWorkbook sxssfWorkbook, int sheetNumber, List<T> listData, Class<T> objDto, List<ItemColsExcelDto> cols, String datePattern,
            String cellReference, boolean isAllBorder, Map<String, String> mapColFormat, int isFormatFinance, XSSFColor colorToTal)
            throws InstantiationException, IllegalAccessException, InvocationTargetException {
        // FileInputStream fileInputStream = null;
        // create sheet of file excel
        SXSSFSheet sxssfSheet = sxssfWorkbook.getSheetAt(sheetNumber);
        CellReference landMark = new CellReference(cellReference);

        int startRow = landMark.getRow();
        // cellStyleDto
        CellStyleDto cellStyleDto = new CellStyleDto(sxssfWorkbook, "Arial", isAllBorder, isFormatFinance, datePattern) ;

        // cellStyleDtoForTotal
        CellStyleDto cellStyleDtoForTotal = new CellStyleDto(cellStyleDto, colorToTal);

        // field of objDto
        Map<String, Field> mapFields = new HashMap<>();
        T objDefault = objDto.newInstance();
        Class<?> cls = objDefault.getClass();
        Field[] fields = populateFields(cls);
        for (Field f : fields) {
            mapFields.put(f.getName().toUpperCase(), f);
        }
        if (listData != null) {
            int dataSize = listData.size();
            if (dataSize > 0) {
                for (int i = 0; i < dataSize; i++) {
                    
                    // DÃ²ng last
                    if (colorToTal != null && i == dataSize - 1) {
                        // Do fill data
                        this.doFillData(sxssfSheet, listData, objDto, cols, mapColFormat, mapFields, startRow, i, cellStyleDtoForTotal, dataSize, true);
                    } else {
                        // Do fill data
                        this.doFillData(sxssfSheet, listData, objDto, cols, mapColFormat, mapFields, startRow, i, cellStyleDto, dataSize, false);
                    }
                    
                    startRow += 1;
                }
            } // end for
        }

        // set point view and active cell default
        sxssfSheet.setActiveCell(new CellAddress(landMark));
//        ((XSSFSheet) sxssfSheet).getCTWorksheet().getSheetViews().getSheetViewArray(0).setTopLeftCell("A1");
    }
    
    /**
     * Do fill data
     * @param sxssfSheet
     * @param listData
     * @param objDto
     * @param cols
     * @param mapColFormat
     * @param mapFields
     * @param rowIndex
     * @param dataIndex
     * @param cellStyleDto
     * @param dataSize
     * @throws IllegalAccessException 
     * @throws IllegalArgumentException 
     */
    private void doFillData(SXSSFSheet sxssfSheet, List<T> listData, Class<T> objDto, List<ItemColsExcelDto> cols, Map<String, String> mapColFormat,
            Map<String, Field> mapFields, int rowIndex, int dataIndex, CellStyleDto cellStyleDto, int dataSize, boolean fillColor)
            throws IllegalArgumentException, IllegalAccessException {

//        SXSSFRow row = sxssfSheet.createRow(rowIndex);
        SXSSFRow row = sxssfSheet.getRow(rowIndex);
        if(row == null) {
        	 row = sxssfSheet.createRow(rowIndex);
        }
        T excelDto = listData.get(dataIndex);
        if (excelDto != null) {
            // set value to map
            Field[] headerFields = populateFields(objDto);
            Map<String, Object> mapValueFields = new HashMap<String, Object>();
            for (Field field : headerFields) {
                if (!field.isAccessible()) {
                    field.setAccessible(true);
                }
                mapValueFields.put(field.getName().toUpperCase(), field.get(excelDto));
            }
            // begin fill to cell
            for (ItemColsExcelDto col : cols) {

                // data type of field
                SXSSFCell cell = row.createCell(col.getColIndex());
                // col.getColName());
                Field field = mapFields.get(col.getColName().toUpperCase());
                String typeFields = field.getType().getSimpleName().toUpperCase();
                
                // TriTV add support byte[] for qrcode
                if (typeFields!=null && typeFields.endsWith("[]")) {
                	typeFields = typeFields.replace("[]", "");
                }
                //

                DataType dataType = DataType.valueOf(typeFields);

                String formatType = null;
                if (null != mapColFormat && mapColFormat.size() != 0) {
                    formatType = mapColFormat.get(col.getColName());
                }

                // col value
                Object colValue = mapValueFields.get(col.getColName().toUpperCase());

                switch (dataType) {
                case LONG:
                    if (colValue != null) {
                        Long valueOfLong = Long.parseLong(colValue.toString());
                        cell.setCellValue(valueOfLong);
                        cell.setCellType(CellType.NUMERIC);
                    }
                    cell.setCellStyle(cellStyleDto.getCellStyleLeft());
                    break;
                case INTEGER:
                    if (colValue != null) {
                        Integer valueOfInteger = Integer.parseInt(colValue.toString());
                        cell.setCellValue(valueOfInteger);
                        cell.setCellType(CellType.NUMERIC);
                    }
                    cell.setCellStyle(cellStyleDto.getCellStyleRight());
                    break;
                case INT:
                    if (col.getColName().equals("ROWNUM") || col.getColName().equals("NO")) {
                        if (colValue != null && !fillColor) {
                            int valueOfInt = Integer.parseInt(colValue.toString());
                            cell.setCellValue(valueOfInt);

                        }
                        cell.setCellStyle(cellStyleDto.getCellStyleCenter());
                    } else {
                        if (colValue != null) {
                            int valueOfInt = Integer.parseInt(colValue.toString());
                            cell.setCellValue(valueOfInt);

                        }
                        cell.setCellStyle(cellStyleDto.getCellStyleRight());
                    }

                    break;
                case DOUBLE:
                    if (colValue != null) {
                        Double valueOfDouble = Double.parseDouble(colValue.toString());

                        if (valueOfDouble % 1 > 0) {
                            // format "0.00"
                            if (null != formatType && formatType.equalsIgnoreCase(CommonConstant.PERCENT)) {
                                cell.setCellValue(valueOfDouble / 100);
                                cell.setCellStyle(cellStyleDto.getCellStyleRightDouble1WithPercent());
                            } else if (null != formatType && formatType.equalsIgnoreCase(CommonConstant.DOUBLE_SHOW_ALL)) {
                                cell.setCellValue(valueOfDouble);
                                cell.setCellStyle(cellStyleDto.getCellStyleRightDouble3());
                            } else {
                                cell.setCellValue(valueOfDouble);
                                cell.setCellStyle(cellStyleDto.getCellStyleRightDouble1());
                            }
                        } else {
                            // format "0"
                            if (null != formatType && formatType.equalsIgnoreCase(CommonConstant.PERCENT)) {
                                cell.setCellValue(valueOfDouble / 100);
                                cell.setCellStyle(cellStyleDto.getCellStyleRightDouble2WithPercent());
                            } else {
                                cell.setCellValue(valueOfDouble);
                                cell.setCellStyle(cellStyleDto.getCellStyleRightDouble2());
                            }
                        }
                    } else {
                        cell.setCellStyle(cellStyleDto.getCellStyleBoder());
                    }

                    break;
                case BIGDECIMAL:
                    if (colValue != null) {
                        BigDecimal valueBigdecimal = (BigDecimal) colValue;
                        if (valueBigdecimal != null) {
                            cell.setCellValue(valueBigdecimal.doubleValue());
                            if (cellStyleDto.getIsFormatFinance() == 0) { // Normal
                                // format
                                // number
                            	if (formatType != null
										&& formatType.equalsIgnoreCase(CommonConstant.MONEY_WITHOUT_DEC)) {
									cell.setCellStyle(cellStyleDto.getCellStyleRightBigDecimal2());
								} else {
									cell.setCellStyle(cellStyleDto.getCellStyleRightBigDecimal1());
								}
//                            	 cell.setCellStyle(cellStyleDto.getCellStyleRightBigDecimal1());
                            } else { // Format number with Finance
                                     // money
                                cell.setCellStyle(cellStyleDto.getCellStyleFinanceFormat());
                            }

                        }
                    } else {
                        cell.setCellStyle(cellStyleDto.getCellStyleBoder());
                    }

                    break;
                case DATE:
                    if (colValue != null) {
                        cell.setCellValue((Date) colValue);
                    }

                    cell.setCellStyle(cellStyleDto.getCellStyleDateCenter());
                    break;
                case TIMESTAMP:
                    if (colValue != null) {
                        cell.setCellValue((Date) colValue);
                    }
                    cell.setCellStyle(cellStyleDto.getCellStyleDateCenter());
                    break;
                case STRING:
                    if (colValue != null) {
                        cell.setCellValue(colValue.toString());
                        cell.setCellStyle(cellStyleDto.getCellStyleLeft());
                    } else {
                        cell.setCellStyle(cellStyleDto.getCellStyleBoder());
                    }

                    break;
                case BOOLEAN:
                    if (colValue != null) {
                        cell.setCellValue(colValue.toString());
                    }
                    cell.setCellStyle(cellStyleDto.getCellStyleCenter());
                    break;
                default:
                    break;
                }

            } // END FOR 2

        }
        
    }
    
    public static void setAllBorder(CellStyle cellStyle) {
        cellStyle.setBorderBottom(BorderStyle.THIN);
        cellStyle.setBorderTop(BorderStyle.THIN);
        cellStyle.setBorderRight(BorderStyle.THIN);
        cellStyle.setBorderLeft(BorderStyle.THIN);
    }
    
    public void setCellStyleWithScale(Integer scale, CellStyle cellStyle, XSSFWorkbook xssfWorkbook) {
    	// createHelper
        CreationHelper createHelper = xssfWorkbook.getCreationHelper();
        // dataFormat
        DataFormat dataFormat = createHelper.createDataFormat();
        Integer i = 0;
        String strZeros ="";
        while ( i < scale) {
        	strZeros +="0";
        	i++;
        }
    	cellStyle.setDataFormat(dataFormat.getFormat("#,##0."+strZeros));
    }
    
    /**
     * setDataToXSSFWorkbook
     * @param xssfWorkbook
     * @param listData
     * @param objDto
     * @param cols
     * @param datePattern
     * @param cellReference
     * @param isAllBorder
     * @throws InstantiationException
     * @throws IllegalAccessException
     * @throws InvocationTargetException 
     */
    public void setDataToXSSFWorkbookIOISIL(SXSSFWorkbook sxssfWorkbook, List<T> listData, Class<T> objDto, List<ItemColsExcelDto> cols, String datePattern,
            String cellReference, boolean isAllBorder, Map<String, String> mapColFormat, int isFormatFinance, XSSFColor colorToTal)
            throws InstantiationException, IllegalAccessException, InvocationTargetException {
        // FileInputStream fileInputStream = null;
        // create sheet of file excel
        SXSSFSheet sxssfSheet = sxssfWorkbook.getSheetAt(0);
        CellReference landMark = new CellReference(cellReference);

        int startRow = landMark.getRow();
        // cellStyleDto
        CellStyleDto cellStyleDto = new CellStyleDto(sxssfWorkbook, "Arial", isAllBorder, isFormatFinance, datePattern) ;

        // cellStyleDtoForTotal
        CellStyleDto cellStyleDtoForTotal = new CellStyleDto(cellStyleDto, colorToTal);

        // field of objDto
        Map<String, Field> mapFields = new HashMap<>();
        T objDefault = objDto.newInstance();
        Class<?> cls = objDefault.getClass();
        Field[] fields = populateFields(cls);
        for (Field f : fields) {
            mapFields.put(f.getName().toUpperCase(), f);
        }
        if (listData != null) {
            int dataSize = listData.size();
            if (dataSize > 0) {
                for (int i = 0; i < dataSize; i++) {
                    
                    // DÃ²ng last
                    if (colorToTal != null && i == dataSize - 1) {
                        // Do fill data
                        this.doFillDataIOISIL(sxssfSheet, listData, objDto, cols, mapColFormat, mapFields, startRow, i, cellStyleDtoForTotal, dataSize, true);
                    } else {
                        // Do fill data
                        this.doFillDataIOISIL(sxssfSheet, listData, objDto, cols, mapColFormat, mapFields, startRow, i, cellStyleDto, dataSize, false);
                    }
                    
                    startRow += 1;
                }
            } // end for
        }

        // set point view and active cell default
        sxssfSheet.setActiveCell(new CellAddress(landMark));
//        ((XSSFSheet) sxssfSheet).getCTWorksheet().getSheetViews().getSheetViewArray(0).setTopLeftCell("A1");
    }
    
    /**
     * Do fill data
     * @param sxssfSheet
     * @param listData
     * @param objDto
     * @param cols
     * @param mapColFormat
     * @param mapFields
     * @param rowIndex
     * @param dataIndex
     * @param cellStyleDto
     * @param dataSize
     * @throws IllegalAccessException 
     * @throws IllegalArgumentException 
     */
    private void doFillDataIOISIL(SXSSFSheet sxssfSheet, List<T> listData, Class<T> objDto, List<ItemColsExcelDto> cols, Map<String, String> mapColFormat,
            Map<String, Field> mapFields, int rowIndex, int dataIndex, CellStyleDto cellStyleDto, int dataSize, boolean fillColor)
            throws IllegalArgumentException, IllegalAccessException {

//        SXSSFRow row = sxssfSheet.createRow(rowIndex);
        SXSSFRow row = sxssfSheet.getRow(rowIndex);
        if(row == null) {
             row = sxssfSheet.createRow(rowIndex);
        }
        T excelDto = listData.get(dataIndex);
        if (excelDto != null) {
            // set value to map
            Field[] headerFields = populateFields(objDto);
            Map<String, Object> mapValueFields = new HashMap<String, Object>();
            for (Field field : headerFields) {
                if (!field.isAccessible()) {
                    field.setAccessible(true);
                }
                mapValueFields.put(field.getName().toUpperCase(), field.get(excelDto));
            }
            // begin fill to cell
            for (ItemColsExcelDto col : cols) {

                // data type of field
                SXSSFCell cell = row.createCell(col.getColIndex());
                // col.getColName());
                Field field = mapFields.get(col.getColName().toUpperCase());
                String typeFields = field.getType().getSimpleName().toUpperCase();
                DataType dataType = DataType.valueOf(typeFields);

                String formatType = null;
                if (null != mapColFormat && mapColFormat.size() != 0) {
                    formatType = mapColFormat.get(col.getColName());
                }

                // col value
                Object colValue = mapValueFields.get(col.getColName().toUpperCase());

                switch (dataType) {
                case LONG:
                    if (colValue != null) {
                        Long valueOfLong = Long.parseLong(colValue.toString());
                        cell.setCellValue(valueOfLong);
                        cell.setCellType(CellType.NUMERIC);
                    }
                    cell.setCellStyle(cellStyleDto.getCellStyleLeft());
                    break;
                case INTEGER:
                    if (colValue != null) {
                        Integer valueOfInteger = Integer.parseInt(colValue.toString());
                        cell.setCellValue(valueOfInteger);
                        cell.setCellType(CellType.NUMERIC);
                    }
                    cell.setCellStyle(cellStyleDto.getCellStyleRight());
                    break;
                case INT:
                    if (col.getColName().equals("ROWNUM") || col.getColName().equals("NO")) {
                        if (colValue != null && !fillColor) {
                            int valueOfInt = Integer.parseInt(colValue.toString());
                            cell.setCellValue(valueOfInt);

                        }
                        cell.setCellStyle(cellStyleDto.getCellStyleCenter());
                    } else {
                        if (colValue != null) {
                            int valueOfInt = Integer.parseInt(colValue.toString());
                            cell.setCellValue(valueOfInt);

                        }
                        cell.setCellStyle(cellStyleDto.getCellStyleRight());
                    }

                    break;
                case DOUBLE:
                    if (colValue != null) {
                        Double valueOfDouble = Double.parseDouble(colValue.toString());

                        if (valueOfDouble % 1 > 0) {
                            // format "0.00"
                            if (null != formatType && formatType.equalsIgnoreCase(CommonConstant.PERCENT)) {
                                cell.setCellValue(valueOfDouble / 100);
                                cell.setCellStyle(cellStyleDto.getCellStyleRightDouble1WithPercent());
                            } else if (null != formatType && formatType.equalsIgnoreCase(CommonConstant.DOUBLE_SHOW_ALL)) {
                                cell.setCellValue(valueOfDouble);
                                cell.setCellStyle(cellStyleDto.getCellStyleRightDouble3());
                            } else {
                                cell.setCellValue(valueOfDouble);
                                cell.setCellStyle(cellStyleDto.getCellStyleRightDouble1());
                            }
                        } else {
                            // format "0"
                            if (null != formatType && formatType.equalsIgnoreCase(CommonConstant.PERCENT)) {
                                cell.setCellValue(valueOfDouble / 100);
                                cell.setCellStyle(cellStyleDto.getCellStyleRightDouble2WithPercent());
                            } else {
                                cell.setCellValue(valueOfDouble);
                                cell.setCellStyle(cellStyleDto.getCellStyleRightDouble2());
                            }
                        }
                    } else {
                        cell.setCellStyle(cellStyleDto.getCellStyleBoder());
                    }

                    break;
                case BIGDECIMAL:
                    if (colValue != null) {
                        BigDecimal valueBigdecimal = (BigDecimal) colValue;
                        if (valueBigdecimal != null) {
                            cell.setCellValue(valueBigdecimal.doubleValue());
                            cell.setCellStyle(cellStyleDto.getCellStyleRightBigDecimal2());

                        }
                    } else {
                        cell.setCellStyle(cellStyleDto.getCellStyleBoder());
                    }

                    break;
                case DATE:
                    if (colValue != null) {
                        cell.setCellValue((Date) colValue);
                    }
                    cell.setCellStyle(cellStyleDto.getCellStyleDateCenter());
                    break;
                case TIMESTAMP:
                    if (colValue != null) {
                        cell.setCellValue((Date) colValue);
                    }
                    cell.setCellStyle(cellStyleDto.getCellStyleDateCenter());
                    break;
                case STRING:
                    if (colValue != null) {
                        cell.setCellValue(colValue.toString());
                        cell.setCellStyle(cellStyleDto.getCellStyleLeft());
                    } else {
                        cell.setCellStyle(cellStyleDto.getCellStyleBoder());
                    }

                    break;
                case BOOLEAN:
                    if (colValue != null) {
                        cell.setCellValue(colValue.toString());
                    }
                    cell.setCellStyle(cellStyleDto.getCellStyleCenter());
                    break;
                default:
                    break;
                }

            } // END FOR 2

        }
        
    }
    
    /**
     * @author phatlt
     * @param clazz : Type of data export
     * @param format 
     * @param typeClass : all field with typeClass will be set format
     * @return
     * @desc : add mapcolformat quickly
     */
    public Map<String,String> setAllFieldFormatWithType(Class<?> clazz, String format, Class<?> typeClass){
    	Map<String,String> mapColformat = new HashMap<String, String>();
    	if(clazz != null) {
    		Field [] fields = populateFields(clazz);
    		for(Field f : fields) {
    			if(f.getType().equals(typeClass))
    				mapColformat.put(f.getName().toUpperCase(), format);
    		}
    	}
    	return mapColformat;
    }
    
    @SuppressWarnings("rawtypes")
    public ResponseEntity doExportExcelHeaderWithColFormatRest(XSSFWorkbook xssfWorkbook, Integer sheetIndex, Locale locale,
            List<T> listData, Class<T> objDto, List<ItemColsExcelDto> cols, String datePattern, String cellReference,
            Map<String, String> mapColFormat, Map<String, CellStyle> mapColStyle,
            Map<String, Object> mapColDefaultValue, XSSFColor colorToTal, boolean isAllBorder,
            String templateName, boolean exportFile) throws Exception {
        // create sheet of file excel
        XSSFSheet sxssfSheet = null;
        if (sheetIndex == null) {
            sxssfSheet = xssfWorkbook.getSheetAt(0);
        } else {
            sxssfSheet = xssfWorkbook.getSheetAt(sheetIndex);
        }

        if (sxssfSheet == null) {
            throw new Exception("Cannot find Sheet!!!");
        }
        
        CellReference landMark = new CellReference(cellReference);
        int startRow = landMark.getRow();

        // cellStyleDto
        CellStyleDto cellStyleDto = new CellStyleDto(xssfWorkbook, "Times New Roman", isAllBorder, datePattern);

        // cellStyleDtoForTotal
        CellStyleDto cellStyleDtoForTotal = new CellStyleDto(cellStyleDto, colorToTal);

        // field of objDto
        Map<String, Field> mapFields = new HashMap<>();
        Field[] fields = populateFields(objDto);
        for (Field f : fields) {
            mapFields.put(f.getName().toUpperCase(), f);
        }

        if (listData != null) {
            if (listData.size() > 0) {
                int dataSize = listData.size();
                for (int i = 0; i < listData.size(); i++) {
                    if (colorToTal != null && i == dataSize - 1) {
                        // Do fill data
                        fillDataForCell(sxssfSheet, listData, objDto, cols, mapColFormat, mapColStyle, mapColDefaultValue, mapFields, startRow, i,
                                cellStyleDtoForTotal, dataSize, true);
                    } else {
                        // Do fill data
                        fillDataForCell(sxssfSheet, listData, objDto, cols, mapColFormat, mapColStyle, mapColDefaultValue, mapFields, startRow, i,
                                cellStyleDto, dataSize, false);
                    }
                    startRow += 1;
                }
            }
        }

        // set point view and active cell default
        sxssfSheet.setActiveCell(new CellAddress(landMark));
        sxssfSheet.getCTWorksheet().getSheetViews().getSheetViewArray(0).setTopLeftCell("A1");
        
        if (exportFile) {
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            SimpleDateFormat formatDateExport = new SimpleDateFormat("yyyyMMdd_HHmmss");
            String currentDate = formatDateExport.format(new Date());
            xssfWorkbook.write(out);
            ByteArrayInputStream in = new ByteArrayInputStream(out.toByteArray());               
            HttpHeaders headers = new HttpHeaders();
            headers.add(CommonConstant.CONTENT_DISPOSITION, CommonConstant.ATTCHMENT_FILENAME + templateName.replace(CommonConstant.TYPE_EXCEL, "") + "_"
                    + currentDate + CommonConstant.TYPE_EXCEL + "\"");
            
            headers.add("Access-Control-Expose-Headers", CommonConstant.CONTENT_DISPOSITION);
            
             return ResponseEntity
                        .ok()
                        .headers(headers)
                        .contentType(MediaType.parseMediaType(CommonConstant.CONTENT_TYPE_EXCEL))
                        .body(new InputStreamResource(in));
        }
        return null;
    }
    
    
    
    public byte[] doExportToByte(XSSFWorkbook xssfWorkbook, Integer sheetIndex, Locale locale,
            List<T> listData, Class<T> objDto, List<ItemColsExcelDto> cols, String datePattern, String cellReference,
            Map<String, String> mapColFormat, Map<String, CellStyle> mapColStyle,
            Map<String, Object> mapColDefaultValue, XSSFColor colorToTal, boolean isAllBorder,
            String templateName, boolean exportFile) throws Exception {
        // create sheet of file excel
        XSSFSheet sxssfSheet = null;
        if (sheetIndex == null) {
            sxssfSheet = xssfWorkbook.getSheetAt(0);
        } else {
            sxssfSheet = xssfWorkbook.getSheetAt(sheetIndex);
        }

        if (sxssfSheet == null) {
            throw new Exception("Cannot find Sheet!!!");
        }
        
        CellReference landMark = new CellReference(cellReference);
        int startRow = landMark.getRow();

        // cellStyleDto
        CellStyleDto cellStyleDto = new CellStyleDto(xssfWorkbook, "Arial", isAllBorder, datePattern);

        // cellStyleDtoForTotal
        CellStyleDto cellStyleDtoForTotal = new CellStyleDto(cellStyleDto, colorToTal);

        // field of objDto
        Map<String, Field> mapFields = new HashMap<>();
        Field[] fields = populateFields(objDto);
        for (Field f : fields) {
            mapFields.put(f.getName().toUpperCase(), f);
        }

        if (listData != null) {
            if (listData.size() > 0) {
                int dataSize = listData.size();
                for (int i = 0; i < listData.size(); i++) {
                    if (colorToTal != null && i == dataSize - 1) {
                        // Do fill data
                        fillDataForCell(sxssfSheet, listData, objDto, cols, mapColFormat, mapColStyle, mapColDefaultValue, mapFields, startRow, i,
                                cellStyleDtoForTotal, dataSize, true);
                    } else {
                        // Do fill data
                        fillDataForCell(sxssfSheet, listData, objDto, cols, mapColFormat, mapColStyle, mapColDefaultValue, mapFields, startRow, i,
                                cellStyleDto, dataSize, false);
                    }
                    startRow += 1;
                }
            }
        }

        // set point view and active cell default
        sxssfSheet.setActiveCell(new CellAddress(landMark));
        sxssfSheet.getCTWorksheet().getSheetViews().getSheetViewArray(0).setTopLeftCell("A1");
        
        if (exportFile) {
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            xssfWorkbook.write(out);
            return out.toByteArray();
        }
        return null;
    }
    
    @SuppressWarnings("rawtypes")
    public ResponseEntity doExportExcelHeaderWithColFormatRestUpService(XSSFWorkbook xssfWorkbook, Integer sheetIndex, Locale locale,
            List<T> listData, Class<T> objDto, List<ItemColsExcelDto> cols, String datePattern, String cellReference,
            Map<String, String> mapColFormat, Map<String, CellStyle> mapColStyle,
            Map<String, Object> mapColDefaultValue, XSSFColor colorToTal, boolean isAllBorder,
            String templateName, boolean exportFile, String path) throws Exception {
        // create sheet of file excel
        XSSFSheet sxssfSheet = null;
        if (sheetIndex == null) {
            sxssfSheet = xssfWorkbook.getSheetAt(0);
        } else {
            sxssfSheet = xssfWorkbook.getSheetAt(sheetIndex);
        }

        if (sxssfSheet == null) {
            throw new Exception("Cannot find Sheet!!!");
        }
        
        CellReference landMark = new CellReference(cellReference);
        int startRow = landMark.getRow();

        // cellStyleDto
        CellStyleDto cellStyleDto = new CellStyleDto(xssfWorkbook, "Times New Roman", isAllBorder, datePattern);

        // cellStyleDtoForTotal
        CellStyleDto cellStyleDtoForTotal = new CellStyleDto(cellStyleDto, colorToTal);

        // field of objDto
        Map<String, Field> mapFields = new HashMap<>();
        Field[] fields = populateFields(objDto);
        for (Field f : fields) {
            mapFields.put(f.getName().toUpperCase(), f);
        }

        if (listData != null) {
            if (listData.size() > 0) {
                int dataSize = listData.size();
                for (int i = 0; i < listData.size(); i++) {
                    if (colorToTal != null && i == dataSize - 1) {
                        // Do fill data
                        fillDataForCell(sxssfSheet, listData, objDto, cols, mapColFormat, mapColStyle, mapColDefaultValue, mapFields, startRow, i,
                                cellStyleDtoForTotal, dataSize, true);
                    } else {
                        // Do fill data
                        fillDataForCell(sxssfSheet, listData, objDto, cols, mapColFormat, mapColStyle, mapColDefaultValue, mapFields, startRow, i,
                                cellStyleDto, dataSize, false);
                    }
                    startRow += 1;
                }
            }
        }

        // set point view and active cell default
        sxssfSheet.setActiveCell(new CellAddress(landMark));
        sxssfSheet.getCTWorksheet().getSheetViews().getSheetViewArray(0).setTopLeftCell("A1");
        
        if (exportFile) {
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            SimpleDateFormat formatDateExport = new SimpleDateFormat("yyyyMMdd_HHmmss");
            String currentDate = formatDateExport.format(new Date());
            xssfWorkbook.write(out);
            //update to service
    		
            String pathFile = Paths.get(path,CommonConstant.PATH_FILE_EXPORT, CommonConstant.PATH_FILE_DLVN,
            		templateName.replace(CommonConstant.TYPE_EXCEL, "") + "_" + currentDate + CommonConstant.TYPE_EXCEL).toString();
            
            File file = new File(pathFile);
            try (OutputStream os = new FileOutputStream(file)) {
                xssfWorkbook.write(os);
            }
            
            String pathOut = (File.separator + Paths.get(CommonConstant.PATH_FILE_EXPORT, CommonConstant.PATH_FILE_DLVN
            		, templateName.replace(CommonConstant.TYPE_EXCEL, "") + "_" + currentDate + CommonConstant.TYPE_EXCEL).toString()).replace("\\", "/");
            
            ByteArrayInputStream in = new ByteArrayInputStream(out.toByteArray());               
            HttpHeaders headers = new HttpHeaders();
            headers.add(CommonConstant.CONTENT_DISPOSITION, CommonConstant.ATTCHMENT_FILENAME + templateName.replace(CommonConstant.TYPE_EXCEL, "") + "_"
                    + currentDate + CommonConstant.TYPE_EXCEL + "\""+";path="+pathOut);
            
            headers.add("Access-Control-Expose-Headers", CommonConstant.CONTENT_DISPOSITION);
            
             return ResponseEntity
                        .ok()
                        .eTag(pathOut)
                        .headers(headers)
                        .contentType(MediaType.parseMediaType(CommonConstant.CONTENT_TYPE_EXCEL))
                        .body(new InputStreamResource(in));
        }
        return null;
    }
}
