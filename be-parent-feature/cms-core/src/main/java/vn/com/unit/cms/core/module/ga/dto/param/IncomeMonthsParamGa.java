package vn.com.unit.cms.core.module.ga.dto.param;

import jp.sf.amateras.mirage.annotation.In;
import jp.sf.amateras.mirage.annotation.ResultSet;
import vn.com.unit.cms.core.module.ga.dto.IncomeGaDto;
import vn.com.unit.cms.core.module.ga.dto.IncomeMonthsGaDto;

import java.util.List;

public class IncomeMonthsParamGa {

    @In
    public String agentCode;
    @In
    public String orgCode;
    @In
    public String yyyyMMDD;
    @ResultSet
    public List<IncomeMonthsGaDto> lstData;
}
