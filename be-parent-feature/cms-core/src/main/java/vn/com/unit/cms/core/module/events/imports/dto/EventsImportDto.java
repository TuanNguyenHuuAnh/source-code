package vn.com.unit.cms.core.module.events.imports.dto;

import lombok.Getter;
import lombok.Setter;
import vn.com.unit.imp.excel.annotation.ExcelImportField;
import vn.com.unit.imp.excel.annotation.ExcelImportTable;
import vn.com.unit.imp.excel.dto.ImportCommonDto;
import vn.com.unit.imp.excel.enumdef.DataTypeEnum;

@SuppressWarnings("rawtypes")
@ExcelImportTable(tableName = "M_EVENTS_IMPORT")
@Getter
@Setter
public class EventsImportDto extends ImportCommonDto{

	private Integer no;

	@ExcelImportField(colName = "TYPE", colType = DataTypeEnum.STRING)
    private String type;
	
    @ExcelImportField(colName = "AGENT_CODE", colType = DataTypeEnum.STRING)
    private String agentCode;
    
    @ExcelImportField(colName = "NAME", colType = DataTypeEnum.STRING)
    private String name;
    
    @ExcelImportField(colName = "GENDER", colType = DataTypeEnum.STRING)
    private String gender;
    
    @ExcelImportField(colName = "EMAIL", colType = DataTypeEnum.STRING)
    private String email;
    
    @ExcelImportField(colName = "TEL", colType = DataTypeEnum.STRING)
    private String tel;
    
    @ExcelImportField(colName = "ADDRESS", colType = DataTypeEnum.STRING)
    private String address;
    
    @ExcelImportField(colName = "REFER_CODE", colType = DataTypeEnum.STRING)
    private String referCode;
    
    //@Transient
    private String idNumber;
    private String territorry;
    private String area;
    private String region;
    private String office;
    private String position;
    private Integer startRowData = new Integer(8);
}
