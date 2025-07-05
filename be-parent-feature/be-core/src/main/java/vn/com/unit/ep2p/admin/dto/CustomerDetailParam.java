package vn.com.unit.ep2p.admin.dto;

import jp.sf.amateras.mirage.annotation.In;
import jp.sf.amateras.mirage.annotation.ResultSet;

import java.util.List;

public class CustomerDetailParam {
    @In
    public String agentCode;
    @In
    public String customerNo;
    @ResultSet
    public List<CustomerInformationDetailDto> lstData;
}
