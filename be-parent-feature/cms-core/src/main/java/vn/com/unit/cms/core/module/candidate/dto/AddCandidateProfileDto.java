package vn.com.unit.cms.core.module.candidate.dto;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddCandidateProfileDto{
	private Integer no;
	private Integer id;
	private String officeBdoh;
	private String codeBdoh;
	private String officeBm;
	private String codeBm;
	private String officeUm;
	private String codeUm;
	private String office;
	private String headOfDepartment;
	private Integer requestNum;
	private String manager;
	private String tvtc;
	private String idNo;//cmnd
	private String candidateName;
	private String positionApply;
	private Date birthday;
	private String phoneNum;
	private String email;
	private String request;
	private String addInfo;
}
