package vn.com.unit.ep2p.admin.dto;

import jp.sf.amateras.mirage.annotation.In;
import jp.sf.amateras.mirage.annotation.Out;
import jp.sf.amateras.mirage.annotation.ResultSet;

import java.util.List;

public class CustomerBirthdayInMonthParam {
    @In
    public String agentCode;
    @In
    public String fromDate;
    @In
    public String toDate;

    @ResultSet
    public List<CustomerBirthdayInMonthDto> lstData;
}
