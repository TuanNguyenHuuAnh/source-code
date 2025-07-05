package vn.com.unit.ep2p.admin.dto;

import jp.sf.amateras.mirage.annotation.In;
import jp.sf.amateras.mirage.annotation.Out;
import jp.sf.amateras.mirage.annotation.ResultSet;

import java.sql.Date;
import java.util.List;

public class CustomerBirthdayParam {
    @In
    public String eventDate;
    @In
    public String agentCode;
    @In
    public String customerRole;
    @In
    public Integer page;
    @In
    public Integer pageSize;
    @In
    public String sort;
    @In
    public Integer clientId;

    @ResultSet
    public List<CustomerBirthdayDto> lstData;

    @Out
    public Integer totalRows;
}
