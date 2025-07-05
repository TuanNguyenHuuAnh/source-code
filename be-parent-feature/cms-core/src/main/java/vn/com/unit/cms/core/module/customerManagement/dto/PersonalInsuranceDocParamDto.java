package vn.com.unit.cms.core.module.customerManagement.dto;

import jp.sf.amateras.mirage.annotation.In;
import jp.sf.amateras.mirage.annotation.Out;
import jp.sf.amateras.mirage.annotation.ResultSet;

import java.util.List;

public class PersonalInsuranceDocParamDto {
    @In
    public String agentCode;
    @In
    public String type;
    @In
    public Integer page;
    @In
    public Integer pageSize;
	@In
	public String sort;
	@In
	public String search;
    @In
    public String docNo;    
    @In
    public Integer realTime;
    @In
    public Integer 	intervalOffline;
    @In
    public Integer 	countMode;

    @ResultSet
    public List<PersonalInsuranceDocumentDto> lstData;
    @Out
    public Integer totalRows;
}
