package vn.com.unit.ep2p.admin.dto;

import jp.sf.amateras.mirage.annotation.In;
import jp.sf.amateras.mirage.annotation.ResultSet;

import java.util.List;

public class CustomerChargeInMonthParam {
    @In
    public String agentCode;
    @In
    public String fromDate;
    @In
    public String toDate;

    @ResultSet
    public List<CustomerChargeDto> lstData;
}
