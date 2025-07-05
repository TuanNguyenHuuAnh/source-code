/*******************************************************************************
 * Class        ：ClassImportDto
 * Created date ：2021/04/06
 * Lasted date  ：2021/04/06
 * Author       ：TuyenNX
 * Change log   ：2021/04/06：01-00 TuyenNX create a new
 ******************************************************************************/
package vn.com.unit.ep2p.core.ers.dto;

//import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import vn.com.unit.imp.excel.annotation.ExcelImportField;
import vn.com.unit.imp.excel.annotation.ExcelImportTable;
import vn.com.unit.imp.excel.dto.ImportCommonDto;
import vn.com.unit.imp.excel.enumdef.DataTypeEnum;

//import java.util.Date;

/**
 * ClassImportDto
 * 
 * @version 01-00
 * @since 01-00
 * @author TuyenNX
 */
@SuppressWarnings("rawtypes")
@ExcelImportTable(tableName = "ERS_MIC_IMPORT")
@Getter
@Setter
public class ErsMicImportDto extends ImportCommonDto {
//    private static final String DATE_PATTERN = "dd/MM/yyyy";
	private Integer no;

//	@ExcelImportField(colName = "MESSAGE_ERROR", colType = DataTypeEnum.STRING)
//	private String messageError;
	
    @ExcelImportField(colName = "UNIT_CODE", colType = DataTypeEnum.STRING)
    private String unitCode;

	@ExcelImportField(colName = "UNIT_NAME", colType = DataTypeEnum.STRING)
    private String unitName;
	
	@ExcelImportField(colName = "AREA_NAME", colType = DataTypeEnum.STRING)
	private String areaName;
	
	@ExcelImportField(colName = "REGION_NAME", colType = DataTypeEnum.STRING)
	private String regionName;

}
