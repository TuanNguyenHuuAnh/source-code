package vn.com.unit.cms.core.module.agent.dto;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ContractDocumentDto {
	private String sourceSystem;
	private String agentCode;
	private String isArchiveOnly;
	private String action;
	private String taxCodePersonal;
	private String taxCodeCompany;
	private List<FileInfor> documentList;
	private List<String> listDocumentName;
	
	private String no;
	private String docId;
	private String docName;
	private String fileName;
	private String applicableDate;
	private String notes;
}
