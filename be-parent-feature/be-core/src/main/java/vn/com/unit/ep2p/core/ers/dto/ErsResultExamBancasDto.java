/***************************************************************
 * @author vunt					
 * @date Apr 28, 2021	
 * @project mbal-core
 * @version 1.0 
 * @description 
 ***************************************************************/
package vn.com.unit.ep2p.core.ers.dto;

import lombok.Getter;
import lombok.Setter;
import vn.com.unit.imp.excel.annotation.ExcelImportField;
import vn.com.unit.imp.excel.annotation.ExcelImportTable;
import vn.com.unit.imp.excel.dto.ImportCommonDto;
import vn.com.unit.imp.excel.enumdef.DataTypeEnum;

@Getter
@Setter
@SuppressWarnings("rawtypes")
@ExcelImportTable(tableName = "RESULT_EXAM_BANCAS_IMPORT")
public class ErsResultExamBancasDto extends ImportCommonDto {
	private Integer no;
	private Integer key;
//	NAME
    @ExcelImportField(colName = "FULL_NAME", colType = DataTypeEnum.STRING)
    private String fullName;
//	DOB
    @ExcelImportField(colName = "DOB", colType = DataTypeEnum.STRING)
    private String dob;
//	MOB
    @ExcelImportField(colName = "MOB", colType = DataTypeEnum.STRING)
    private String mob;
//	YOB
    @ExcelImportField(colName = "YOB", colType = DataTypeEnum.STRING)
    private String yob;
//	GENDER
    @ExcelImportField(colName = "GENDER", colType = DataTypeEnum.STRING)
    private String gender;
//	ID_NUMBER
    @ExcelImportField(colName = "ID_NUMBER", colType = DataTypeEnum.STRING)
    private String idNumber;
//	ID_DATE
    @ExcelImportField(colName = "ID_DATE", colType = DataTypeEnum.STRING)
    private String idDate;
//	ID_PLACE
    @ExcelImportField(colName = "ID_PLACE", colType = DataTypeEnum.STRING)
    private String idPlace;
//	PHONE_NUMBER
    @ExcelImportField(colName = "PHONE_NUMBER", colType = DataTypeEnum.STRING)
    private String phoneNumber;
//	PERSONAL_EMAIL
    @ExcelImportField(colName = "PERSONAL_EMAIL", colType = DataTypeEnum.STRING)
    private String personalEmail;
//	POSITION
    @ExcelImportField(colName = "POSITION", colType = DataTypeEnum.STRING)
    private String position;
//	AREA
    @ExcelImportField(colName = "AREA", colType = DataTypeEnum.STRING)
    private String area;
//	AM_TEAM
    @ExcelImportField(colName = "AM_TEAM", colType = DataTypeEnum.STRING)
    private String amTeam;
//	REFERER
    @ExcelImportField(colName = "REFERER", colType = DataTypeEnum.STRING)
    private String referer;
//	RESULT
    @ExcelImportField(colName = "RESULT", colType = DataTypeEnum.STRING)
    private String result;
    
    @ExcelImportField(colName = "CERTIFICATE_NO", colType = DataTypeEnum.STRING)
    private String certificateNo;

    @ExcelImportField(colName = "DATE_OF_CERTIFICATE", colType = DataTypeEnum.DATE)
    private String dateOfCertificate;
    
//	NOTE
    @ExcelImportField(colName = "NOTE", colType = DataTypeEnum.STRING)
    private String note;
}
