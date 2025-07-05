package vn.com.unit.cms.core.module.ga.dto.param;

import jp.sf.amateras.mirage.annotation.In;
import jp.sf.amateras.mirage.annotation.Out;
import jp.sf.amateras.mirage.annotation.ResultSet;
import vn.com.unit.cms.core.module.ga.dto.GrowthGaDto;
import vn.com.unit.cms.core.module.ga.dto.IncomeGaDto;

import java.util.List;

public class IncomeParamGa {

    @In
    public String agentCode;
    @In
    public String orgCode;
    @In
    public String yyyyMMDD;
    @ResultSet
    public List<IncomeGaDto> lstData;
}
