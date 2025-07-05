package vn.com.unit.ep2p.admin.dto;

import jp.sf.amateras.mirage.annotation.In;
import jp.sf.amateras.mirage.annotation.ResultSet;

import java.util.List;

public class AgentBankInfoParamDto {
    @In
    public String agentCode;
    @ResultSet
    public List<AgentBankDto> datas;
}
