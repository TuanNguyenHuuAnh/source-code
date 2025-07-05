package vn.com.unit.cms.core.module.emulate.dto;

import lombok.Getter;
import lombok.Setter;
import vn.com.unit.imp.excel.annotation.ExcelImportField;
import vn.com.unit.imp.excel.annotation.ExcelImportTable;
import vn.com.unit.imp.excel.dto.ImportCommonDto;
import vn.com.unit.imp.excel.enumdef.DataTypeEnum;

import java.util.Date;

import org.springframework.data.annotation.Id;

import jp.sf.amateras.mirage.annotation.Column;
import jp.sf.amateras.mirage.annotation.PrimaryKey;
import jp.sf.amateras.mirage.annotation.Table;
import jp.sf.amateras.mirage.annotation.PrimaryKey.GenerationType;

@Getter
@Setter
@Table(name = "M_CONTEST_DETAIL")
public class ContestDto {
	@Id
	@Column(name = "id")
	@PrimaryKey(generationType = GenerationType.SEQUENCE, generator = "SEQ_M_CONTEST_DETAIL")
	private Long id;

	private String memoNo;

	private String agentCode;

	private String agentName;

	private String agentType;

	private String reportingtoCode;

	private String reportingtoName;

	private String reportingtoType;

	private String bmCode;

	private String bmName;

	private String bmType;

	private String gadCode;

	private String gadName;

	private String gaCode;

	private String gaName;

	private String bdohCode;

	private String bdohName;

	private String bdrhCode;

	private String bdrhName;

	private String region;

	private String bdahCode;

	private String bdahName;

	private String area;

	private String bdthCde;

	private String bdthName;

	private String territory;

	private String policyNo;

	private Date appliedDate;

	private Date issuedDate;

	private String result;

	private String advance;

	private String bonus;

	private String clawback;

	private String note;

	private Date createdDate;

	private String createdBy;

	private Date updatedDate;

	private String updatedBy;
	
	private Date deletedDate;

	private String deletedBy;

}
