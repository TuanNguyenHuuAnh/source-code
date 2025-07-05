package vn.com.unit.cms.core.module.candidate.dto;

import java.util.List;

import jp.sf.amateras.mirage.annotation.In;
import jp.sf.amateras.mirage.annotation.Out;
import jp.sf.amateras.mirage.annotation.ResultSet;

public class AddCandidateProfileDtoParam extends PagingParamDto{
	@In
	public Integer agentType;
	@In
	public String userName;
	@In
	public String regionSelect;
	@In
	public String office;
	@In
	public String headOfDepartment;
	@In
	public Integer requestNum;
	@In
	public String manager;
	@In
	public String TVTC;
	@In
	public String idNo;
	@In
	public String candidateName;
	@In
	public String positionApply;
	@In
	public String birthday;//date
	@In
	public String phoneNum;
	@In
	public String email;
	@In
	public String request;
	@In
	public String addInfo;
	@ResultSet
	public List<AddCandidateProfileDto> data;
	@Out
	public Integer TotalRows;
}
