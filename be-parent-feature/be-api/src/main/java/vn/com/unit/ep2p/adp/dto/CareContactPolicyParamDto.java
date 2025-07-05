package vn.com.unit.ep2p.adp.dto;

import java.util.Date;
import java.util.List;

import jp.sf.amateras.mirage.annotation.In;
import jp.sf.amateras.mirage.annotation.Out;
import jp.sf.amateras.mirage.annotation.ResultSet;

public class CareContactPolicyParamDto {
	@In
	public String agentCode; // V_AGENT_CODE

	@In
	public String partner; // V_PARTNER

	@In
	public String regionCode; // V_REGION_CODE

	@In
	public String zoneCode; // V_ZONE_CODE

	@In
	public String uoCode; // V_UO_CODE

	@In
	public String areaDlvnCode; // V_AREA_DLVN_CODE

	@In
	public String regionDlvnCode; // V_REGION_DLVN_CODE
	
	@In
	public String zoneDlvnCode; // V_ZONE_DLVN_CODE

	@In
	public String ilCode; // V_IL_CODE

	@In
	public String isCode; // V_IS_CODE

	@In
	public String smCode; // V_SM_CODE

	@In
	public Date fromDate;
	
	@In
	public Date toDate;
	
	@In
	public String contactResult;
	
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
    public List<CareContactPolicyDto> lstData;
    
    @Out
    public Integer totalRows;
}
