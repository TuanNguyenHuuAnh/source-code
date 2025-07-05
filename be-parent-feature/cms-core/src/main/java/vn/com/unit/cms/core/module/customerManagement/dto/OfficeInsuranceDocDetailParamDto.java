package vn.com.unit.cms.core.module.customerManagement.dto;

import jp.sf.amateras.mirage.annotation.In;
import jp.sf.amateras.mirage.annotation.Out;
import jp.sf.amateras.mirage.annotation.ResultSet;

import java.util.List;

public class OfficeInsuranceDocDetailParamDto  {

    @In
    public String agentCode;
    @In
    public String docStatus;
    @In
    public Integer page;
    @In
    public Integer size;
    @In
    public String conditionSearch;
    @In
    public String docNo;
    @In
    public String sort;
    @ResultSet
    public List<OfficeInsuranceDetailDto> lstData;
    @Out
    public Integer totalRows;
}
