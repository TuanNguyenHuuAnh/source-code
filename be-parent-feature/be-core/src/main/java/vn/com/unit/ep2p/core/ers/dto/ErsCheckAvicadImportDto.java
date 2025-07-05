/***************************************************************
 * @author vunt					
 * @date Apr 14, 2021	
 * @project mbal-core
 * @version 1.0 
 * @description 
 ***************************************************************/
package vn.com.unit.ep2p.core.ers.dto;

//import java.util.Date;

import lombok.Getter;
import lombok.Setter;
import vn.com.unit.imp.excel.annotation.ExcelImportField;
import vn.com.unit.imp.excel.annotation.ExcelImportTable;
import vn.com.unit.imp.excel.dto.ImportCommonDto;
import vn.com.unit.imp.excel.enumdef.DataTypeEnum;

@Getter
@Setter
@SuppressWarnings("rawtypes")
@ExcelImportTable(tableName = "ERS_CHECK_AVICAD_IMPORT")
public class ErsCheckAvicadImportDto extends ImportCommonDto {

	private Integer no;
	private Integer key;
//	ID_NO VARCHAR(50),
	@ExcelImportField(colName = "ID_NO", colType = DataTypeEnum.STRING)
	private String idNo;
//	ID_CARD VARCHAR(50),
	@ExcelImportField(colName = "ID_CARD", colType = DataTypeEnum.STRING)
	private String idCard;
//	TAX_CODE VARCHAR(50),
	@ExcelImportField(colName = "TAX_CODE", colType = DataTypeEnum.STRING)
	private String taxCode;
//	ORGANIZATION_NAME NVARCHAR(255),
	@ExcelImportField(colName = "ORGANIZATION_NAME", colType = DataTypeEnum.STRING)
	private String organizationName;
//	AGENT_NAME NVARCHAR(255),
	@ExcelImportField(colName = "AGENT_NAME", colType = DataTypeEnum.STRING)
	private String agentName;
//	DOB DATE,
	@ExcelImportField(colName = "DOB", colType = DataTypeEnum.DATE)
	private String dob;
//	BEGOING_DATE DATE,
	@ExcelImportField(colName = "BEGOING_DATE", colType = DataTypeEnum.DATE)
	private String begoingDate;
//	GENDER VARCHAR(2),
	@ExcelImportField(colName = "GENDER", colType = DataTypeEnum.STRING)
	private String gender;
//	AGENT_CODE VARCHAR(50),
	@ExcelImportField(colName = "AGENT_CODE", colType = DataTypeEnum.STRING)
	private String agentCode;
//	LIFE_CER NVARCHAR(255),
	@ExcelImportField(colName = "LIFE_CER", colType = DataTypeEnum.STRING)
	private String lifeCer;
//	INSURER NVARCHAR(255),
	@ExcelImportField(colName = "INSURER", colType = DataTypeEnum.STRING)
	private String insurer;
//	AGTSTS VARCHAR(1),
	@ExcelImportField(colName = "AGTSTS", colType = DataTypeEnum.STRING)
	private String agtsts;
//	RECENT_CONT DATE,
	@ExcelImportField(colName = "RECENT_CONT", colType = DataTypeEnum.DATE)
	private String recentCont;
//	RECENT_TER DATE,
	@ExcelImportField(colName = "RECENT_TER", colType = DataTypeEnum.DATE)
	private String recentTer;
//	MISDAT DATE,
	@ExcelImportField(colName = "MISDAT", colType = DataTypeEnum.STRING)
	private String misdat;
//	BLK_CODE VARCHAR(50),
	@ExcelImportField(colName = "BLK_CODE", colType = DataTypeEnum.STRING)
	private String blkCode;
//	BLACK_LIST VARCHAR(1),
	@ExcelImportField(colName = "BLACK_LIST", colType = DataTypeEnum.STRING)
	private String blackList;
//	IS_COMPANY VARCHAR(1),
	@ExcelImportField(colName = "IS_COMPANY", colType = DataTypeEnum.STRING)
	private String isCompany;
//	IS_OTHER_COMPANY VARCHAR(1),
	@ExcelImportField(colName = "IS_OTHER_COMPANY", colType = DataTypeEnum.STRING)
	private String isOtherCompany;
//	RESULT NVARCHAR(255),
	@ExcelImportField(colName = "RESULT", colType = DataTypeEnum.STRING)
	private String result;
	
	private String channel;
    private Integer startRowData = new Integer(2);
}
