package vn.com.unit.cms.core.module.ga.dto.param;

import jp.sf.amateras.mirage.annotation.In;
import jp.sf.amateras.mirage.annotation.Out;
import jp.sf.amateras.mirage.annotation.ResultSet;

import vn.com.unit.cms.core.module.customerManagement.dto.PersonalInsuranceDocumentDto;
import vn.com.unit.cms.core.module.ga.dto.GrowthGaDto;

import java.util.List;

public class GrowthParamGa {

    @In
    public String agentCode;
    @In
    public String orgCode;
    @In
    public String agentGroup;  //Ga
    @In
    public String dateKey;   //MTD: YYYYMM, QTD: YYYYQQ, YTD: YYYY
    @In
    public String dataType;  //MTD,QTD,YTD,
    @In
    public Integer page;
    @In
    public Integer pageSize;
    @In
    public Integer sort;
    @In
    public String search;
    @ResultSet
    public List<GrowthGaDto> lstData;
    @Out
    public Integer totalRows;



}
