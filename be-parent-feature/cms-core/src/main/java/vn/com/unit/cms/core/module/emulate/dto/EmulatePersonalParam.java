package vn.com.unit.cms.core.module.emulate.dto;

import java.util.List;

import jp.sf.amateras.mirage.annotation.In;
import jp.sf.amateras.mirage.annotation.Out;
import jp.sf.amateras.mirage.annotation.ResultSet;

public class EmulatePersonalParam {
    @In
    public String agentCode;
    @In
    public String orgCode;
    @In
    public String agentGroup;
    @In
    public Integer page;
    @In
    public Integer size;
    @In
    public String sort;
    @In
    public String searchContent;
    @In
    public String territory;
    @In
    public String area;
    @In
    public String region;
    @In
    public String office;
    @In
    public String agentCodeChild;
    
	@ResultSet
	public List<EmulateResultSearchResultDto> data;
	@Out
	public Integer TotalRows;
}
