package vn.com.unit.cms.core.module.customerManagement.dto;

import jp.sf.amateras.mirage.annotation.In;
import jp.sf.amateras.mirage.annotation.Out;
import jp.sf.amateras.mirage.annotation.ResultSet;

import java.util.List;

public class OfficeInsuranceDocParamSumDto {

    @In
    public String agentCode;
    @In
    public String orgCode;
    @In
    public String agentGroup;
    @In
    public String docStatus;
    @In
    public Integer page;
    @In
    public Integer size;
	@In
	public String sort;
	@In
	public String search;
    @ResultSet
    public List<OfficeInsuranceDetailDto> lstData;
    @Out
    public Integer totalRows;
}
