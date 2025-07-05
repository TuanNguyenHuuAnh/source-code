package vn.com.unit.ep2p.adp.dto;

import java.math.BigDecimal;
import java.util.List;

import jp.sf.amateras.mirage.annotation.In;
import jp.sf.amateras.mirage.annotation.Out;
import jp.sf.amateras.mirage.annotation.ResultSet;

public class ReportK2K2PlusParamDto {
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
    public String dateKey;
    @In
    public String dataType;
    @In
    public Integer option;
    @In
    public Integer page;
    @In
    public Integer pageSize;
	@In
	public String sort;
	@In
	public String search;

    @ResultSet
    public List<ReportK2K2PlusDto> lstData;
    @Out
    public Integer totalRows;
    @Out
    public BigDecimal totalEpp;
    @Out
    public BigDecimal totalApp;
    @Out
    public BigDecimal totalTp;
    @Out
    public BigDecimal totalEp;
}
