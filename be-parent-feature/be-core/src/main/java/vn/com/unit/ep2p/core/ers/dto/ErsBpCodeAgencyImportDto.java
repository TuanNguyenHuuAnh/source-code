package vn.com.unit.ep2p.core.ers.dto;

//import java.text.ParseException;
//import java.util.Date;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import vn.com.unit.ep2p.core.ers.annotation.IesTableHeader;
import vn.com.unit.imp.excel.annotation.ExcelImportField;
import vn.com.unit.imp.excel.annotation.ExcelImportTable;
import vn.com.unit.imp.excel.dto.ImportCommonDto;
import vn.com.unit.imp.excel.enumdef.DataTypeEnum;
//import vn.com.unit.imp.excel.utils.DateUtils;

@Getter
@Setter
@SuppressWarnings("rawtypes")
@ExcelImportTable(tableName = "ERS_BP_CODE_AGENCY_IMPORT")
public class ErsBpCodeAgencyImportDto extends ImportCommonDto {
	@IesTableHeader(value = "no", width = 50, format = "center")
	private Integer no;
	
//	@Setter(value = AccessLevel.NONE)
	@Getter(value = AccessLevel.NONE)
//	@ExcelImportField(colName = "MESSAGE_ERROR", colType = DataTypeEnum.STRING)
	@IesTableHeader(value = "common.import.message.error", width = 150)
	private String messageErrorFrontend;

	@ExcelImportField(colName = "BP_CODE", colType = DataTypeEnum.STRING)
	@IesTableHeader(value = "import.bp.code.agency.bp.code", width = 100)
	private String bpCode; // bp

	@ExcelImportField(colName = "APPLY_FOR_POSITION", colType = DataTypeEnum.STRING)
	@IesTableHeader(value = "import.bp.code.agency.apply.for.position", width = 100)
	private String applyForPosition;// chức vụ dự kiến

	@ExcelImportField(colName = "CLASS_CODE", colType = DataTypeEnum.STRING)
	@IesTableHeader(value = "import.bp.code.agency.class.code", width = 100)
	private String classCode; // lớp học

	@ExcelImportField(colName = "CANDIDATE_NAME", colType = DataTypeEnum.STRING)
	@IesTableHeader(value = "import.bp.code.agency.candidate.name", width = 200)
	private String candidateName; // Họ và tên

	@ExcelImportField(colName = "GENDER_NAME", colType = DataTypeEnum.STRING)
	@IesTableHeader(value = "import.bp.code.agency.gender.name", width = 50)
	private String genderName; // Giới tính

	@ExcelImportField(colName = "MARITAL_STATUS", colType = DataTypeEnum.STRING)
	@IesTableHeader(value = "import.bp.code.agency.marital.status", width = 100)
	private String maritalStatus; // Tình trạng hôn nhân

//	@Setter(value = AccessLevel.NONE)
	@ExcelImportField(colName = "DOB", colType = DataTypeEnum.STRING)
	@IesTableHeader(value = "import.bp.code.agency.date.of.birth", format = "date")
	private String dob;

	@ExcelImportField(colName = "NATIONALITY", colType = DataTypeEnum.STRING)
	@IesTableHeader(value = "import.bp.code.agency.nationality", width = 100)
	private String nationality;

	@ExcelImportField(colName = "OTHER_ID_NO", colType = DataTypeEnum.STRING)
	@IesTableHeader(value = "import.bp.code.agency.other.id.no", width = 100)
	private String otherIdNo;

	@ExcelImportField(colName = "ID_TYPE", colType = DataTypeEnum.STRING)
	@IesTableHeader(value = "import.bp.code.agency.other.id.type", width = 50)
	private String idType;

	@ExcelImportField(colName = "ID_NO", colType = DataTypeEnum.STRING)
	@IesTableHeader(value = "import.bp.code.agency.id.no", width = 50)
	private String idNo;

	@Override
	public void setMessageError(String messageError) {
		super.setMessageError(messageError);
		this.setMessageErrorFrontend(messageError);
	}
	
	public String getMessageErrorFrontend() {
		return super.getMessageError();
	}

	

//	public void setDob(Date dob) {
//		this.dob = dob;
//	}
//	
//	public void setDob(String dob) {
//		Date valueOfDate = null;
//		try {
//			valueOfDate = DateUtils.formatStringToDate(dob, "valueOfDate = DateUtils.formatStringToDate(currentCell.toString(), colType);");
//		} catch (ParseException e) {
//			e.printStackTrace();
//		}
//		this.dob = valueOfDate;
//	}
}
