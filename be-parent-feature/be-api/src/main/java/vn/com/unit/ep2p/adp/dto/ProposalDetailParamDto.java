package vn.com.unit.ep2p.adp.dto;

import java.util.List;

import jp.sf.amateras.mirage.annotation.In;
import jp.sf.amateras.mirage.annotation.ResultSet;

public class ProposalDetailParamDto {
    @In
    public String agentCode;
    @In
    public String type;
    @In
    public String docNo;
    @ResultSet
    public List<ProposalDetailDto> lstData;
}
