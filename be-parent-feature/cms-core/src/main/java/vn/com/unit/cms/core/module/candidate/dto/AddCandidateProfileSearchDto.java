package vn.com.unit.cms.core.module.candidate.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddCandidateProfileSearchDto extends PagingDto{
	private Integer agentType;
	public String userName;
	public String agentName;
	public String regionSelect;
	private String office;
	private String headOfDepartment;
	public Integer requestNum;
	public String manager;
	public String TVTC;
	public String idNo;
	public String candidateName;
	public String positionApply;
	public String birthday;// date
	public String phoneNum;
	public String email;
	public String request;
	public String addInfo;
}
