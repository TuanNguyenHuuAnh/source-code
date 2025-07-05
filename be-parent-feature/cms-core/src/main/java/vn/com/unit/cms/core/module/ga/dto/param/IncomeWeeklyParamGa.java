package vn.com.unit.cms.core.module.ga.dto.param;

import java.util.List;

import jp.sf.amateras.mirage.annotation.In;
import jp.sf.amateras.mirage.annotation.ResultSet;
import vn.com.unit.cms.core.module.ga.dto.IncomeGaDto;

public class IncomeWeeklyParamGa {
    @In
    public String agentCode;
    @In
    public String paymentPeriod;
    @ResultSet
    public List<IncomeGaDto> lstData;
}
