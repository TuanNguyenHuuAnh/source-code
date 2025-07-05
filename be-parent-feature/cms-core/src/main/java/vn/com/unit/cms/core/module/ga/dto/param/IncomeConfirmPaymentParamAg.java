package vn.com.unit.cms.core.module.ga.dto.param;

import java.util.List;

import jp.sf.amateras.mirage.annotation.In;
import jp.sf.amateras.mirage.annotation.ResultSet;
import vn.com.unit.ep2p.admin.dto.IncomeConfirmPaymentAGDto;

public class IncomeConfirmPaymentParamAg {

    @In
    public String agentCode;

    @In
    public String period;
    @ResultSet
    public List<IncomeConfirmPaymentAGDto> lstData;
}
