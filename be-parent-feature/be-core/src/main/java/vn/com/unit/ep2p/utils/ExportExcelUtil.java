package vn.com.unit.ep2p.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Field;
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

import org.apache.poi.poifs.crypt.CipherAlgorithm;
import org.apache.poi.poifs.crypt.EncryptionInfo;
import org.apache.poi.poifs.crypt.EncryptionMode;
import org.apache.poi.poifs.crypt.Encryptor;
import org.apache.poi.poifs.crypt.HashAlgorithm;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.ClientAnchor;
import org.apache.poi.ss.usermodel.ClientAnchor.AnchorType;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.DataFormat;
import org.apache.poi.ss.usermodel.Drawing;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellAddress;
import org.apache.poi.ss.util.CellReference;
import org.apache.poi.util.Units;
import org.apache.poi.xssf.streaming.SXSSFCell;
import org.apache.poi.xssf.streaming.SXSSFRow;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import vn.com.unit.ep2p.constant.CommonConstant;
import vn.com.unit.ep2p.dto.ItemColsExcelDto;

public class ExportExcelUtil<T> {

	private enum DataType {
		LONG, DOUBLE, INTEGER, STRING, DATE, TIMESTAMP, INT, BIGDECIMAL, BOOLEAN, BYTE;
	}

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
	 * @param data type List<T>
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
	 * @param workbook type SXSSFWorkbook
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
													.format(mapValueFields.get(col.getColName().toUpperCase()))
													.toString());
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

											cell.setCellValue(
													(Date) mapValueFields.get(col.getColName().toUpperCase()));
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
							} catch (Exception e) {
								logger.error("Exception ", e);
							}
						}
					}
				} // end for
			}

			this.workbook = workbookExport;

		}

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
			SimpleDateFormat formatDateExport = new SimpleDateFormat("yyyyMMddHHmmss");
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
			SimpleDateFormat formatDateExport = new SimpleDateFormat("yyyyMMddHHmmss");
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
			List<ItemColsExcelDto> cols, String datePattern, HttpServletResponse res, String templateName)
			throws IOException, GeneralSecurityException, IllegalArgumentException, IllegalAccessException,
			InstantiationException {

		// FileInputStream fileInputStream = null;
		File file = new File(template);
		try (FileInputStream fileInputStream = new FileInputStream(file);
				XSSFWorkbook xssfWorkbook = new XSSFWorkbook(fileInputStream);) {
			// create sheet of file excel
			XSSFSheet sxssfSheet = xssfWorkbook.getSheetAt(0);

			CellReference landMark = new CellReference("A5");
			int startRow = landMark.getRow();
			// style date-center number-right text-left
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
			cellStyleDateCenter.setDataFormat(dataFormat.getFormat(datePattern));

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
											logger.error("Exception ", e);
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
			SimpleDateFormat formatDateExport = new SimpleDateFormat("yyyyMMddHHmmss");
			String currentDate = formatDateExport.format(new Date());

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
    private void drawImageOnExcelSheet(XSSFSheet sheet, int row, int col,
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

	public String templateNameSuff() {
		SimpleDateFormat sf = new SimpleDateFormat("yyyyMMdd_HHmmss");
		return sf.format(new Date());
	}

	public String extendTemplateNameSuff(String templateName, String extend) {
		return templateName.split("\\.")[0] + "_" + templateNameSuff() + "." + extend;
	}

}
