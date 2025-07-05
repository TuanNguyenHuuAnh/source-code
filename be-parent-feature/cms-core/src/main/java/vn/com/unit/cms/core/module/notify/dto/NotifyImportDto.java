package vn.com.unit.cms.core.module.notify.dto;

import lombok.Getter;
import lombok.Setter;
import vn.com.unit.cms.core.dto.CmsCommonEditDto;
import vn.com.unit.common.tree.TreeNode;
import vn.com.unit.imp.excel.annotation.ExcelImportField;
import vn.com.unit.imp.excel.annotation.ExcelImportTable;
import vn.com.unit.imp.excel.dto.ImportCommonDto;
import vn.com.unit.imp.excel.enumdef.DataTypeEnum;

import java.util.Date;

@Getter
@Setter
@ExcelImportTable(tableName = "M_AGENT_NOTIFY_IMPORT")
public class NotifyImportDto extends ImportCommonDto {
	@ExcelImportField(colName = "AGENT_CODE", colType = DataTypeEnum.STRING)
	private String agentCode;
	
	@ExcelImportField(colName = "TITLE", colType = DataTypeEnum.STRING)
	private String title;
	
	@ExcelImportField(colName = "CONTENT", colType = DataTypeEnum.STRING)
	private String content;

	@ExcelImportField(colName = "MESSAGE_ERROR", colType = DataTypeEnum.STRING)
	private String messageError;

	private String no;
}
