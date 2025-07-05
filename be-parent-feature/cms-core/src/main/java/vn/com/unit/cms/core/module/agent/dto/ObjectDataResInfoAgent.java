package vn.com.unit.cms.core.module.agent.dto;

import java.io.Serializable;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ObjectDataResInfoAgent<T> implements Serializable {

	private static final long serialVersionUID = 1L;
	
	protected int total;
	protected int totalHeadOfDepartment;
	protected int totalManager;
	protected int totalTVTC;
	protected int totalTVTCSA;
	protected List<T> datas;
}
