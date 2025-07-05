package vn.com.unit.cms.core.module.report.dto;

import java.util.List;

import jp.sf.amateras.mirage.annotation.In;
import jp.sf.amateras.mirage.annotation.Out;
import jp.sf.amateras.mirage.annotation.ResultSet;

public class ReportResultParam<T>{
	@In
	public String agentCode;
	@In
    public String orgCode;
	@In
	public String agentGroup;
	@In
    public String time;         //MMyyyy
    @In
    public String dataType;     //MTD/QTD/YTD
	@In
	public String page;
	@In
    public String pageSize;
	@In
	public String sort;
	@In
    public String search; 
	
	@Out
	public Integer totalRow;
	@ResultSet
	public List<T> data;
}
