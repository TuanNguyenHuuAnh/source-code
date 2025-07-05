package vn.com.unit.cms.core.module.emulate.dto;

import java.io.Serializable;
import java.util.List;

import jp.sf.amateras.mirage.annotation.In;
import jp.sf.amateras.mirage.annotation.Out;
import jp.sf.amateras.mirage.annotation.ResultSet;

@SuppressWarnings("serial")
public class EmulateGroupParam<T> implements Serializable{
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
    public String search;
    
    @ResultSet
    public List<EmulateResultSearchResultDto> data;
    @Out
    public Integer TotalRows;
	
//	@In
//    public String agentCode;
//    @In
//    public String agentGroup;
//    @In
//    public String orgCode;
//    @In
//    public String contestType;
}
