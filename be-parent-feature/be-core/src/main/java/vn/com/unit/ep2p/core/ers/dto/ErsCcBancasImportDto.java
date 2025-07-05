package vn.com.unit.ep2p.core.ers.dto;

import lombok.Getter;
import lombok.Setter;
import vn.com.unit.imp.excel.annotation.ExcelImportField;
import vn.com.unit.imp.excel.annotation.ExcelImportTable;
import vn.com.unit.imp.excel.dto.ImportCommonDto;
import vn.com.unit.imp.excel.enumdef.DataTypeEnum;

@SuppressWarnings("rawtypes")
@ExcelImportTable(tableName = "ERS_CC_BANCAS_IMPORT")
@Getter
@Setter
public class ErsCcBancasImportDto extends ImportCommonDto {

	private Integer no;

//	private String messageError;

	@ExcelImportField(colName = "CC_CODE", colType = DataTypeEnum.STRING)
	private String ccCode;

	@ExcelImportField(colName = "EFFECTIVE_DATE", colType = DataTypeEnum.DATE)
	private String effectiveDate;

	@ExcelImportField(colName = "BP_CODE", colType = DataTypeEnum.STRING)
	private String bpCode;

	@ExcelImportField(colName = "POSITION", colType = DataTypeEnum.STRING)
	private String position;

	@ExcelImportField(colName = "FULL_NAME", colType = DataTypeEnum.STRING)
	private String fullName;

//	@ExcelImportField(colName = "ID_TYPE", colType = DataTypeEnum.STRING)
//	private String idType;

	@ExcelImportField(colName = "ID_NUMBER", colType = DataTypeEnum.STRING)
	private String idNumber;

//	@ExcelImportField(colName = "COLUMN1", colType = DataTypeEnum.STRING)
//	private String column1;
//
//	@ExcelImportField(colName = "COLUMN2", colType = DataTypeEnum.STRING)
//	private String column2;
//
//	@ExcelImportField(colName = "COLUMN3", colType = DataTypeEnum.STRING)
//	private String column3;
//
//	@ExcelImportField(colName = "COLUMN4", colType = DataTypeEnum.STRING)
//	private String column4;
//
//	@ExcelImportField(colName = "COLUMN5", colType = DataTypeEnum.STRING)
//	private String column5;
//
//	@ExcelImportField(colName = "COLUMN6", colType = DataTypeEnum.STRING)
//	private String column6;
//
//	@ExcelImportField(colName = "COLUMN7", colType = DataTypeEnum.STRING)
//	private String column7;
//
//	@ExcelImportField(colName = "COLUMN8", colType = DataTypeEnum.STRING)
//	private String column8;
//
//	@ExcelImportField(colName = "COLUMN9", colType = DataTypeEnum.STRING)
//	private String column9;
//
//	@ExcelImportField(colName = "COLUMN10", colType = DataTypeEnum.STRING)
//	private String column10;
//
//	@ExcelImportField(colName = "COLUMN11", colType = DataTypeEnum.STRING)
//	private String column11;
//
//	@ExcelImportField(colName = "COLUMN12", colType = DataTypeEnum.STRING)
//	private String column12;
//
//	@ExcelImportField(colName = "COLUMN13", colType = DataTypeEnum.STRING)
//	private String column13;
//
//	@ExcelImportField(colName = "COLUMN14", colType = DataTypeEnum.STRING)
//	private String column14;
//
//	@ExcelImportField(colName = "COLUMN15", colType = DataTypeEnum.STRING)
//	private String column15;
//
//	@ExcelImportField(colName = "COLUMN16", colType = DataTypeEnum.STRING)
//	private String column16;
//
//	@ExcelImportField(colName = "COLUMN17", colType = DataTypeEnum.STRING)
//	private String column17;
//
//	@ExcelImportField(colName = "COLUMN18", colType = DataTypeEnum.STRING)
//	private String column18;
//
//	@ExcelImportField(colName = "COLUMN19", colType = DataTypeEnum.STRING)
//	private String column19;
//
//	@ExcelImportField(colName = "COLUMN20", colType = DataTypeEnum.STRING)
//	private String column20;

}
