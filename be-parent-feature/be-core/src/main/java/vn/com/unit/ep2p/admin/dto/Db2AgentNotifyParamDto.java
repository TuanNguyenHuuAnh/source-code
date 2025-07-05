package vn.com.unit.ep2p.admin.dto;

import jp.sf.amateras.mirage.annotation.In;
import jp.sf.amateras.mirage.annotation.ResultSet;

import java.util.List;

public class Db2AgentNotifyParamDto {
    @In
    public String territory;
    @In
    public String region;
    @In
    public String office;
    @In
    public String area;
    @In
    public String position;
    @ResultSet
    public List<Db2AgentDto> lstAgent;
}
