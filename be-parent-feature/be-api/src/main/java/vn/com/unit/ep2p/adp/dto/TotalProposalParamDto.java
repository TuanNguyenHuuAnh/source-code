package vn.com.unit.ep2p.adp.dto;

import java.util.List;

import jp.sf.amateras.mirage.annotation.In;
import jp.sf.amateras.mirage.annotation.ResultSet;

public class TotalProposalParamDto {
    @In
    public String agentCode;
    @ResultSet
    public List<TotalProposalDto> data;
}
