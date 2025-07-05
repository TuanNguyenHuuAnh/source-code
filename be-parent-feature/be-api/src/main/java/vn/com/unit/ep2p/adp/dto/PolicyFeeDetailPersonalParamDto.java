package vn.com.unit.ep2p.adp.dto;

import java.util.Date;
import java.util.List;

import jp.sf.amateras.mirage.annotation.In;
import jp.sf.amateras.mirage.annotation.Out;
import jp.sf.amateras.mirage.annotation.ResultSet;

public class PolicyFeeDetailPersonalParamDto {
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
	public String areaDlvnCode;

	@In
	public String zoneDlvnCode;

	@In
	public String regionDlvnCode;

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
	public String searchStr;

    @ResultSet
    public List<PolicyFeeDetailPersonalDto> lstData;
    
    @Out
    public Integer totalRows;
}
