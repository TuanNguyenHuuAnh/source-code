package vn.com.unit.cms.core.module.agent.dto;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GroupInfoAgent<T>{
	
	public Integer total;
	public Integer totalHeadOfDepartment;
	public Integer totalManager;
	public Integer totalTVTC;
	public Integer totalTVTCSA;
	public List<T> data;

}
