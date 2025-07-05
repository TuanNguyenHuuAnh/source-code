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
@ExcelImportTable(tableName = "ERS_CC_AGENCY_IMPORT")
@Getter
@Setter
public class ErsCcAgencyImportDto extends ImportCommonDto {
//    private static final String DATE_PATTERN = "dd/MM/yyyy";
	private Integer no;

//	@ExcelImportField(colName = "MESSAGE_ERROR", colType = DataTypeEnum.STRING)
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

	@ExcelImportField(colName = "ID_NUMBER", colType = DataTypeEnum.STRING)
	private String idNumber;

}
