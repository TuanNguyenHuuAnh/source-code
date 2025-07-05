// 2021-04-06 LocLT Task #40894
// vn.com.unit.dms.utils.ConstantImportUtils

package vn.com.unit.ep2p.core.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FilenameUtils;
//import org.apache.cxf.BusException;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;

import vn.com.unit.ep2p.core.constant.ImportExcelConstant;
import vn.com.unit.ep2p.core.dto.ImportCommonDto;
import vn.com.unit.ep2p.core.dto.ItemColsExcelDto;
import vn.com.unit.ep2p.core.exception.BusinessException;
import vn.com.unit.ep2p.core.service.ImportExcelService;
import vn.com.unit.ep2p.core.utils.ConstantImportUtils;

@Service
//@Transactional
//@Transactional(readOnly = true, rollbackFor = Exception.class)
public class ImportExcelServiceImpl implements ImportExcelService {

	@Override
	public <E extends Enum<E>, T extends ImportCommonDto<E>> List<T> getDataFromExcel(File file, int startRow,
			Class<T> dto, Class<E> enumType) throws Exception {
		FileInputStream fileInputStream = null;
		Workbook workbook = null;

		try {
			fileInputStream = new FileInputStream(file);

			String ext = FilenameUtils.getExtension(file.getName());
			if (ImportExcelConstant.EXT_EXCEL_97_2003_WORKBOOK.equalsIgnoreCase(ext)) {
				workbook = new HSSFWorkbook(fileInputStream);
			} else {
				workbook = new XSSFWorkbook(fileInputStream);
			}

			Sheet sheet = workbook.getSheetAt(0);
			startRow = startRow - 1;
			int lastRow = sheet.getLastRowNum();

			// check row is empty
			if (startRow > lastRow) {
				// getMessageSource().getMessage("message.error.row.is.empty", null, locale)
				throw new BusinessException("File has no data");
			}

			Row rowHeader = sheet.getRow(startRow - 1);

			List<ItemColsExcelDto> cols = new ArrayList<>();

			setListColumnExcel(enumType, cols);

//			List<String> headers = initHeaderTemplate();
//			if (CollectionUtils.isNotEmpty(headers)) {
//				importCommon.setDataFileExcelWithErrorMapWithFieldType(sheet, startRow, lastRow + 1, cols, rowHeader,
//						(T) plupload, plupload.getColsValidate(), plupload.getSessionKey(), getMessageSource(), headers,
//						locale);
//			} else {
//				importCommon.setDataFileExcelWithErrorMapWithFieldType(sheet, startRow, lastRow + 1, cols, rowHeader,
//						(T) plupload, plupload.getColsValidate(), plupload.getSessionKey(), getMessageSource(), locale);
//			}

			ConstantImportUtils<T> importCommon = new ConstantImportUtils<>();
			importCommon.setDataFileExcelWithErrorMapWithFieldType(sheet, startRow, lastRow + 1, cols, rowHeader, dto,
					null, null, null, null);

			return importCommon.getData();
		} catch (Exception e) {
			throw e;
		} finally {
			try {
				fileInputStream.close();
				if (workbook != null) {
					workbook.close();
				}
			} catch (Exception e) {
			}

		}
	}

	private static <E extends Enum<E>> void setListColumnExcel(Class<E> enumType, List<ItemColsExcelDto> cols) {
		// loop enum
		for (E en : enumType.getEnumConstants()) {
			ItemColsExcelDto itemCol = new ItemColsExcelDto();
			itemCol.setColName(en.name());
			itemCol.setColIndex(Integer.parseInt(en.toString()));
			cols.add(itemCol);
		}
	}

}
