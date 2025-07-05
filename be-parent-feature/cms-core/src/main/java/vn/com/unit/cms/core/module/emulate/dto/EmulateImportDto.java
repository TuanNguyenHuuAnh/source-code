package vn.com.unit.cms.core.module.emulate.dto;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;
import vn.com.unit.imp.excel.annotation.ExcelImportField;
import vn.com.unit.imp.excel.annotation.ExcelImportTable;
import vn.com.unit.imp.excel.dto.ImportCommonDto;
import vn.com.unit.imp.excel.enumdef.DataTypeEnum;

@SuppressWarnings("rawtypes")
@ExcelImportTable(tableName = "M_CONTEST_SUMMARY_IMPORT")
@Getter
@Setter
public class EmulateImportDto extends ImportCommonDto {

    private Long no;

    @ExcelImportField(colName = "CODE",colType = DataTypeEnum.STRING)
    private String code;

    @ExcelImportField(colName = "MEMO_NO",colType = DataTypeEnum.STRING)
    private String memoNo;

    @ExcelImportField(colName = "CONTEST_NAME",colType = DataTypeEnum.STRING)
    private String contestName;

    @ExcelImportField(colName = "KEYWORDS_SEO",colType = DataTypeEnum.STRING)
    private String keywordsSeo;

    @ExcelImportField(colName = "KEYWORDS",colType = DataTypeEnum.STRING)
    private String keywords;

    @ExcelImportField(colName = "KEYWORDS_DESC",colType = DataTypeEnum.STRING)
    private String keywordsDesc;

    @ExcelImportField(colName = "DESCRIPTION",colType = DataTypeEnum.STRING)
    private String description;

    @ExcelImportField(colName = "CONTEST_TYPE",colType = DataTypeEnum.STRING)
    private String contestType;

    @ExcelImportField(colName = "CONTEST_PHYSICAL_IMT",colType = DataTypeEnum.STRING)
    private String contestPhysicalImt;

    @ExcelImportField(colName = "CONTEST_PHYSICAL_FILE",colType = DataTypeEnum.STRING)
    private String contestPhysicalFile;

    @ExcelImportField(colName = "IS_HOT",colType = DataTypeEnum.STRING)
    private String isHot;

    @ExcelImportField(colName = "IS_CHALLENGE",colType = DataTypeEnum.STRING)
    private String isChallenge;

    @ExcelImportField(colName = "ENABLED",colType = DataTypeEnum.STRING)
    private String enabled;

    @ExcelImportField(colName = "IS_ODS",colType = DataTypeEnum.STRING)
    private String isOds;

    @ExcelImportField(colName = "START_DATE",colType = DataTypeEnum.DATE)
    private Date startDate;
    
    @ExcelImportField(colName = "END_DATE",colType = DataTypeEnum.DATE)
    private Date endDate;

    @ExcelImportField(colName = "EFFECTIVE_DATE",colType = DataTypeEnum.DATE)
    private Date effectiveDate;

    @ExcelImportField(colName = "EXPIRED_DATE",colType = DataTypeEnum.DATE)
    private Date expiredDate;

    @ExcelImportField(colName = "APPLICABLE_OBJECT",colType = DataTypeEnum.STRING)
    private String applicableObject;

    @ExcelImportField(colName = "AGENT_NAME",colType = DataTypeEnum.STRING)
    private String agentName;

    @ExcelImportField(colName = "AGENT_CODE",colType = DataTypeEnum.STRING)
    private String agentCode;

    @ExcelImportField(colName = "AREA",colType = DataTypeEnum.STRING)
    private String area;

    @ExcelImportField(colName = "TERRITORY",colType = DataTypeEnum.STRING)
    private String territory;

    @ExcelImportField(colName = "REGION",colType = DataTypeEnum.STRING)
    private String region;

    @ExcelImportField(colName = "OFFICE",colType = DataTypeEnum.STRING)
    private String office;

    @ExcelImportField(colName = "POSITION",colType = DataTypeEnum.STRING)
    private String position;

    @ExcelImportField(colName = "IS_PERSON",colType = DataTypeEnum.STRING)
    private String isPerson;

    @ExcelImportField(colName = "REPORTINGTO_NAME",colType = DataTypeEnum.STRING)
    private String reportingtoName;

    @ExcelImportField(colName = "NOTE",colType = DataTypeEnum.STRING)
    private String note;

    @ExcelImportField(colName = "MESSAGE_ERROR",colType = DataTypeEnum.STRING)
    private String messageError;
    
    
}
