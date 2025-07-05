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
@ExcelImportTable(tableName = "RESULT_EXAM_AGENCY_IMPORT")
public class ErsResultExamAgencyDto extends ImportCommonDto {
	
	private Integer no;
	private Integer key;
    @ExcelImportField(colName = "APPLY_FOR_POSITION", colType = DataTypeEnum.STRING)
    private String applyForPosition;
    
    @ExcelImportField(colName = "FULL_NAME", colType = DataTypeEnum.STRING)
    private String fullName;
    
    @ExcelImportField(colName = "ID_NO", colType = DataTypeEnum.STRING)
    private String idNo;
    
    @ExcelImportField(colName = "MBSU_CLASS_REGISTRATION", colType = DataTypeEnum.STRING)
    private String mbsuClassRegistration;

    @ExcelImportField(colName = "ORGANIZATION_CLASS_PROVINCE", colType = DataTypeEnum.STRING)
    private String organizationClassProvince;
    
    @ExcelImportField(colName = "REGISTRATION_KIND", colType = DataTypeEnum.STRING)
    private String registrationKind;
    
    @ExcelImportField(colName = "ATTEND_DAY_ONE", colType = DataTypeEnum.STRING)
    private String attendDayOne;
    
    @ExcelImportField(colName = "ELIGIBLE_EXAM", colType = DataTypeEnum.STRING)
    private String eligibleExam;
    
    @ExcelImportField(colName = "CONTEST", colType = DataTypeEnum.STRING)
    private String contest;
      
    @ExcelImportField(colName = "COMPLETE_MBSU", colType = DataTypeEnum.STRING)
    private String completeMbsu;
    
    @ExcelImportField(colName = "COMPLETE_MBFS", colType = DataTypeEnum.STRING)
    private String completeMbfs;
    
    @ExcelImportField(colName = "MBFS_CLASS_REGISTRATION", colType = DataTypeEnum.STRING)
    private String mbfsClassRegistration;
    
    @ExcelImportField(colName = "COMPLETE_TRAINING", colType = DataTypeEnum.STRING)
    private String completeTraining;
    
    @ExcelImportField(colName = "COLUMN1", colType = DataTypeEnum.STRING)
    private String column1;
    
    @ExcelImportField(colName = "COLUMN2", colType = DataTypeEnum.STRING)
    private String column2;
    
    @ExcelImportField(colName = "COLUMN3", colType = DataTypeEnum.STRING)
    private String column3;
    
    @ExcelImportField(colName = "COLUMN4", colType = DataTypeEnum.STRING)
    private String column4;
    
    @ExcelImportField(colName = "COLUMN5", colType = DataTypeEnum.STRING)
    private String column5;
    
    @ExcelImportField(colName = "COLUMN6", colType = DataTypeEnum.STRING)
    private String column6;
	
    @ExcelImportField(colName = "COLUMN7", colType = DataTypeEnum.STRING)
    private String column7;
    
    @ExcelImportField(colName = "COLUMN8", colType = DataTypeEnum.STRING)
    private String column8;
    
    @ExcelImportField(colName = "COLUMN9", colType = DataTypeEnum.STRING)
    private String column9;
    
    @ExcelImportField(colName = "COLUMN10", colType = DataTypeEnum.STRING)
    private String column10;
    
    @ExcelImportField(colName = "COLUMN11", colType = DataTypeEnum.STRING)
    private String column11;
    
    @ExcelImportField(colName = "COLUMN12", colType = DataTypeEnum.STRING)
    private String column12;
    
    @ExcelImportField(colName = "COLUMN13", colType = DataTypeEnum.STRING)
    private String column13;
    
    @ExcelImportField(colName = "COLUMN14", colType = DataTypeEnum.STRING)
    private String column14;
    
    @ExcelImportField(colName = "COLUMN15", colType = DataTypeEnum.STRING)
    private String column15;
    
    @ExcelImportField(colName = "COLUMN16", colType = DataTypeEnum.STRING)
    private String column16;
    
    @ExcelImportField(colName = "COLUMN17", colType = DataTypeEnum.STRING)
    private String column17;
    
    @ExcelImportField(colName = "COLUMN18", colType = DataTypeEnum.STRING)
    private String column18;
    
    @ExcelImportField(colName = "COLUMN19", colType = DataTypeEnum.STRING)
    private String column19;
    
    @ExcelImportField(colName = "COLUMN20", colType = DataTypeEnum.STRING)
    private String column20;
    
    @ExcelImportField(colName = "COLUMN21", colType = DataTypeEnum.STRING)
    private String column21;
    
    @ExcelImportField(colName = "COLUMN22", colType = DataTypeEnum.STRING)
    private String column22;
    
    @ExcelImportField(colName = "COLUMN23", colType = DataTypeEnum.STRING)
    private String column23;
    
    @ExcelImportField(colName = "COLUMN24", colType = DataTypeEnum.STRING)
    private String column24;
    
    @ExcelImportField(colName = "COLUMN25", colType = DataTypeEnum.STRING)
    private String column25;
    
    @ExcelImportField(colName = "COLUMN26", colType = DataTypeEnum.STRING)
    private String column26;
    
    @ExcelImportField(colName = "COLUMN27", colType = DataTypeEnum.STRING)
    private String column27;
    
    @ExcelImportField(colName = "COLUMN28", colType = DataTypeEnum.STRING)
    private String column28;
    
    @ExcelImportField(colName = "COLUMN29", colType = DataTypeEnum.STRING)
    private String column29;
    
    @ExcelImportField(colName = "COLUMN30", colType = DataTypeEnum.STRING)
    private String column30;
    
    @ExcelImportField(colName = "COLUMN31", colType = DataTypeEnum.STRING)
    private String column31;
    
    @ExcelImportField(colName = "COLUMN32", colType = DataTypeEnum.STRING)
    private String column32;
    
    @ExcelImportField(colName = "COLUMN33", colType = DataTypeEnum.STRING)
    private String column33;
    
    @ExcelImportField(colName = "COLUMN34", colType = DataTypeEnum.STRING)
    private String column34;
    
    @ExcelImportField(colName = "COLUMN35", colType = DataTypeEnum.STRING)
    private String column35;
    
    @ExcelImportField(colName = "COLUMN36", colType = DataTypeEnum.STRING)
    private String column36;
    
    @ExcelImportField(colName = "COLUMN37", colType = DataTypeEnum.STRING)
    private String column37;
    
    @ExcelImportField(colName = "COLUMN38", colType = DataTypeEnum.STRING)
    private String column38;
    
    @ExcelImportField(colName = "COLUMN39", colType = DataTypeEnum.STRING)
    private String column39;
    
    @ExcelImportField(colName = "COLUMN40", colType = DataTypeEnum.STRING)
    private String column40;
    
    @ExcelImportField(colName = "COLUMN41", colType = DataTypeEnum.STRING)
    private String column41;
    
    @ExcelImportField(colName = "COLUMN42", colType = DataTypeEnum.STRING)
    private String column42;
    
    @ExcelImportField(colName = "COLUMN43", colType = DataTypeEnum.STRING)
    private String column43;
    
    @ExcelImportField(colName = "COLUMN44", colType = DataTypeEnum.STRING)
    private String column44;
    
    @ExcelImportField(colName = "COLUMN45", colType = DataTypeEnum.STRING)
    private String column45;
    
    @ExcelImportField(colName = "COLUMN46", colType = DataTypeEnum.STRING)
    private String column46;
    
    @ExcelImportField(colName = "COLUMN47", colType = DataTypeEnum.STRING)
    private String column47;
    
    @ExcelImportField(colName = "COLUMN48", colType = DataTypeEnum.STRING)
    private String column48;
    
    @ExcelImportField(colName = "COLUMN49", colType = DataTypeEnum.STRING)
    private String column49;
    
    
}