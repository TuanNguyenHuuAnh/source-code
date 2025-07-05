package vn.com.unit.cms.core.module.document.dto;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;
import vn.com.unit.imp.excel.annotation.ExcelImportField;
import vn.com.unit.imp.excel.annotation.ExcelImportTable;
import vn.com.unit.imp.excel.dto.ImportCommonDto;
import vn.com.unit.imp.excel.enumdef.DataTypeEnum;

@SuppressWarnings("rawtypes")
@ExcelImportTable(tableName = "M_DOCUMENT_IMPORT")
@Getter
@Setter
public class DocumentImportDto extends ImportCommonDto {
    @ExcelImportField(colName = "CODE", colType = DataTypeEnum.STRING)
    private String code;
    
    @ExcelImportField(colName = "DOCUMENT_CODE", colType = DataTypeEnum.STRING)
    private String documentCode;
    
    @ExcelImportField(colName = "TITLE", colType = DataTypeEnum.STRING)
    private String title;
    
    @ExcelImportField(colName = "KEYWORDS_DESC", colType = DataTypeEnum.STRING)
    private String keywordsDesc;
    
    @ExcelImportField(colName = "ENABLED", colType = DataTypeEnum.STRING)
    private String enabled;
    
    @ExcelImportField(colName = "PHYSICAL_FILE_NAME", colType = DataTypeEnum.STRING)
    private String physicalFileName;
    
    @ExcelImportField(colName = "POSTED_DATE", colType = DataTypeEnum.DATE)
    private Date postedDate;
    
    @ExcelImportField(colName = "EXPIRATION_DATE", colType = DataTypeEnum.DATE)
    private Date expirationDate;
    
    private Long noId;
    
    private String keywordsSeo;
    private String keywords;
    private String docCateTitle;
    private String fileName;
}
