package vn.com.unit.ep2p.core.ers.dto;

//import java.util.Date;

import lombok.Getter;
import lombok.Setter;
import vn.com.unit.imp.excel.annotation.ExcelImportField;
import vn.com.unit.imp.excel.annotation.ExcelImportTable;
import vn.com.unit.imp.excel.dto.ImportCommonDto;
import vn.com.unit.imp.excel.enumdef.DataTypeEnum;

@SuppressWarnings("rawtypes")
@ExcelImportTable(tableName = "ERS_COP_IMPORT")
@Getter
@Setter
public class ErsCopImportDto extends ImportCommonDto {

	private Integer no;

	private String messageError;

	@ExcelImportField(colName = "STUDY_DATE", colType = DataTypeEnum.DATE)
	private String studyDate;

	@ExcelImportField(colName = "CANDIDATE_NAME", colType = DataTypeEnum.STRING)
	private String candidateName;

	@ExcelImportField(colName = "ID_NO", colType = DataTypeEnum.STRING)
	private String idNo;

	@ExcelImportField(colName = "MOBILE", colType = DataTypeEnum.STRING)
	private String mobile;

	@ExcelImportField(colName = "RECRUITER_CODE_ID", colType = DataTypeEnum.STRING)
	private String recruiterCodeId;

	@ExcelImportField(colName = "RECRUITER_NAME", colType = DataTypeEnum.STRING)
	private String recruiterName;

	@ExcelImportField(colName = "MANAGER_CODE_ID", colType = DataTypeEnum.STRING)
	private String managerCodeId;

	@ExcelImportField(colName = "MANAGER_NAME", colType = DataTypeEnum.STRING)
	private String managerName;

	@ExcelImportField(colName = "AD_NAME", colType = DataTypeEnum.STRING)
	private String adName;

	@ExcelImportField(colName = "RD_NAME", colType = DataTypeEnum.STRING)
	private String rdName;

	@ExcelImportField(colName = "REGION_NAME", colType = DataTypeEnum.STRING)
	private String regionName;
}
