package vn.com.unit.ep2p.adp.dto;

import jp.sf.amateras.mirage.annotation.In;
import jp.sf.amateras.mirage.annotation.ResultSet;

import java.util.List;

public class ProductOfSopParamDto {
    @In
    public String agentCode;
    @ResultSet
    public List<String> datas;
}
