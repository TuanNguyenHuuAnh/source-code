package vn.com.unit.ep2p.adp.dto;

import jp.sf.amateras.mirage.annotation.In;
import jp.sf.amateras.mirage.annotation.Out;

public class CheckPermissionParamDto {
    @In
    public String agentCode;
    @In
    public String policyKey;
    @Out
    public Integer permision;
}
