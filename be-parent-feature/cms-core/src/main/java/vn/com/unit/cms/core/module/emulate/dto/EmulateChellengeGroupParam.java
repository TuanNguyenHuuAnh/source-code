package vn.com.unit.cms.core.module.emulate.dto;

import java.util.List;

import jp.sf.amateras.mirage.annotation.In;
import jp.sf.amateras.mirage.annotation.Out;
import jp.sf.amateras.mirage.annotation.ResultSet;

public class EmulateChellengeGroupParam {
    @In
    public String agentCode;
    @In
    public String ga;
    @In
    public Integer isChallenge;
    @In
    public String contestType;
    @In
    public String memoNo;
    //search by location
    @In
    public String territoryCode;
    @In
    public String areaCode;
    @In
    public String regionCode;
    @In
    public String officeCode;
    
    @In
    public Integer page;
    @In
    public Integer size;
    
    @ResultSet
    public List<EmulateResultSearchResultDto> data;
    @Out
    public Integer TotalRows;
}
