package vn.com.unit.ep2p.adp.dto;

import jp.sf.amateras.mirage.annotation.In;
import jp.sf.amateras.mirage.annotation.Out;
import jp.sf.amateras.mirage.annotation.ResultSet;

import java.util.Date;
import java.util.List;

public class PolicyDeliveryParamCustomDto {
    @In
    public String agentCode;
    @In
    public String partner;
    @In
    public String regionCode;
    @In
    public String zoneCode;
    @In
    public String uoCode;
    @In
    public Date fromDate;
    @In
    public Date toDate;
    @In
    public Integer page;
    @In
    public Integer size;
    @In
	public String sort;
	@In
	public String search;

    @ResultSet
    public List<PolicyDeliveryDto> lstData;
    @Out
    public Integer totalRows;
}
