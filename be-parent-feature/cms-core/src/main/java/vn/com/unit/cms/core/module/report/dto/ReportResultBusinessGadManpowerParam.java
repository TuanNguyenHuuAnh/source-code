package vn.com.unit.cms.core.module.report.dto;

import java.util.List;

import jp.sf.amateras.mirage.annotation.In;
import jp.sf.amateras.mirage.annotation.Out;
import jp.sf.amateras.mirage.annotation.ResultSet;

public class ReportResultBusinessGadManpowerParam {
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
    public String dataLevel;    //'ALL','SUM','DETAIL'
    @In
    public Integer page;
    @In
    public Integer pageSize;
    @In
    public String sort;
    @In
    public String search; //10
    
    @Out
    public Integer totalRow;
    @ResultSet
    public List<ReportResultViewGADTabManPowerDto> data;
}
