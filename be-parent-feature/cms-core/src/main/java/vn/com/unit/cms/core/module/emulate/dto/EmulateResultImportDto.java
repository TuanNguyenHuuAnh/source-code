package vn.com.unit.cms.core.module.emulate.dto;

import lombok.Getter;
import lombok.Setter;
import vn.com.unit.imp.excel.annotation.ExcelImportField;
import vn.com.unit.imp.excel.annotation.ExcelImportTable;
import vn.com.unit.imp.excel.dto.ImportCommonDto;
import vn.com.unit.imp.excel.enumdef.DataTypeEnum;

import java.util.Date;

import jp.sf.amateras.mirage.annotation.Column;

/**
 * @author TaiTM
 */
@SuppressWarnings("rawtypes")
@ExcelImportTable(tableName = "M_CONTEST_DETAIL_IMPORT")
@Getter
@Setter
public class EmulateResultImportDto extends ImportCommonDto {

	private Long no;
	
	private String code;
	
    @ExcelImportField(colName = "MEMO_NO",colType = DataTypeEnum.STRING)
    private String memoNo;


    @ExcelImportField(colName = "AGENT_CODE", colType = DataTypeEnum.STRING)
    private String agentCode;

    private String agentName;
    
    private String agentType;


    @ExcelImportField(colName = "REPORTINGTO_CODE", colType = DataTypeEnum.STRING)
    private String reportingtoCode;

    private String reportingtoName;
   
    private String reportingtoType;


    @ExcelImportField(colName = "BM_CODE", colType = DataTypeEnum.STRING)
    private String bmCode;

    private String bmName;
    
    private String bmType;


    @ExcelImportField(colName = "GAD_CODE", colType = DataTypeEnum.STRING)
    private String gadCode;

    private String gadName;


    @ExcelImportField(colName = "GA_CODE", colType = DataTypeEnum.STRING)
    private String gaCode;

    private String gaName;


    @ExcelImportField(colName = "BDOH_CODE", colType = DataTypeEnum.STRING)
    private String bdohCode;

    private String bdohName;


    @ExcelImportField(colName = "BDRH_CODE", colType = DataTypeEnum.STRING)
    private String bdrhCode;

    private String bdrhName;


    @ExcelImportField(colName = "REGION", colType = DataTypeEnum.STRING)
    private String region;


    @ExcelImportField(colName = "BDAH_CODE", colType = DataTypeEnum.STRING)
    private String bdahCode;

    private String bdahName;


    @ExcelImportField(colName = "AREA", colType = DataTypeEnum.STRING)
    private String area;


    @ExcelImportField(colName = "BDTH_CDE", colType = DataTypeEnum.STRING)
    private String bdthCde;

    private String bdthName;


    @ExcelImportField(colName = "TERRITORY", colType = DataTypeEnum.STRING)
    private String territory;

    @ExcelImportField(colName = "POLICY_NO", colType = DataTypeEnum.STRING)
    private String policyNo;


    @ExcelImportField(colName = "APPLIED_DATE", colType = DataTypeEnum.DATE)
    private Date appliedDate;

    @ExcelImportField(colName = "ISSUED_DATE", colType = DataTypeEnum.DATE)
    private Date issuedDate;


    @ExcelImportField(colName = "RESULT", colType = DataTypeEnum.STRING)
    private String result;

    @ExcelImportField(colName = "ADVANCE", colType = DataTypeEnum.STRING)
    private String advance;

    @ExcelImportField(colName = "BONUS", colType = DataTypeEnum.STRING)
    private String bonus;

    @ExcelImportField(colName = "CLAWBACK", colType = DataTypeEnum.STRING)
    private String clawback;

    @ExcelImportField(colName = "NOTE", colType = DataTypeEnum.STRING)
    private String note;
    
    @Column(name = "MESSAGE_ERROR")
    private String messageError;

}
