package vn.com.unit.cms.core.module.document.dto;

import java.util.List;

import jp.sf.amateras.mirage.annotation.In;
import jp.sf.amateras.mirage.annotation.Out;
import jp.sf.amateras.mirage.annotation.ResultSet;

public class DocumentSearchParam {
	
	@In
	public String agentCode;
	
	@In
	public String docNo;
	
	@In
	public String policyNo;

	@In
	public Integer page;
	@In
	public Integer pageSize;

	@ResultSet
	public List<DocumentInformationSearchDto> data;
	@Out
	public Integer totalData;
}
