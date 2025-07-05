/*******************************************************************************
 * Class        ：ClassImportDto
 * Created date ：2021/04/06
 * Lasted date  ：2021/04/06
 * Author       ：TuyenNX
 * Change log   ：2021/04/06：01-00 TuyenNX create a new
 ******************************************************************************/
package vn.com.unit.ep2p.core.ers.dto;

import lombok.Getter;
import lombok.Setter;
import vn.com.unit.imp.excel.annotation.ExcelImportField;
import vn.com.unit.imp.excel.annotation.ExcelImportTable;
import vn.com.unit.imp.excel.dto.ImportCommonDto;
import vn.com.unit.imp.excel.enumdef.DataTypeEnum;

/**
 * ClassImportDto
 * 
 * @version 01-00
 * @since 01-00
 * @author TuyenNX
 */
@SuppressWarnings("rawtypes")
@ExcelImportTable(tableName = "ERS_CLASS_INFO_IMPORT")
@Getter
@Setter
public class ErsClassInfoImportDto extends ImportCommonDto {
	private Integer no;

    @ExcelImportField(colName = "CHANNEL", colType = DataTypeEnum.STRING)
    private String channel;

	@ExcelImportField(colName = "CLASS_TYPE", colType = DataTypeEnum.STRING)
    private String classType;
	
	@ExcelImportField(colName = "CLASS_CODE", colType = DataTypeEnum.STRING)
	private String classCode;
	
	@ExcelImportField(colName = "ONLINE_OFFLINE", colType = DataTypeEnum.STRING)
	private String onlineOffline;
	
	@ExcelImportField(colName = "PROVINCE", colType = DataTypeEnum.STRING)
	private String province;
	
	@ExcelImportField(colName = "START_DATE", colType = DataTypeEnum.DATE)
	private String startDate;
	
	@ExcelImportField(colName = "END_DATE", colType = DataTypeEnum.DATE)
	private String endDate;
	
	@ExcelImportField(colName = "EXAM_DATE", colType = DataTypeEnum.DATE)
	private String examDate;
	
}
