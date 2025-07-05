package vn.com.unit.cms.core.module.customerManagement.dto;

import jp.sf.amateras.mirage.annotation.In;
import jp.sf.amateras.mirage.annotation.Out;
import jp.sf.amateras.mirage.annotation.ResultSet;

import java.util.List;

public class OfficePolicyRenewParamDto {

    @In
    public String agentCode;
    @In
    public String orgId;
    @In
    public String agentGroup;
    @In
    public String search;
    @ResultSet
    public List<OfficePolicyRenewDetailDto> lstData;
    @Out
    public Integer totalRows;
}
