package vn.com.unit.ep2p.adp.dto;

import java.util.Date;
import java.util.List;

import jp.sf.amateras.mirage.annotation.In;
import jp.sf.amateras.mirage.annotation.Out;
import jp.sf.amateras.mirage.annotation.ResultSet;

public class PolicyRequestSearchParam {
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
    public String areaCodeDLVN;
    @In
    public String regionCodeDLVN;
    @In
    public String zoneCodeDLVN;
    @In
    public String ilCode;
    @In
    public String isCode;
    @In
    public String smCode;
    @In
    public Date fromDate;
    @In
    public Date toDate;
	@In
	public String policyType;
	@In
	public Integer page;
	@In
	public Integer pageSize;
	@In
	public String sort;
	@In
	public String search;
	@ResultSet
	public List<PolicyRequestSearchResultDto> datas;
	@Out
	public Integer total;
}
