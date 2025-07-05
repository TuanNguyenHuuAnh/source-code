package vn.com.unit.ep2p.adp.dto;

import java.util.List;

import jp.sf.amateras.mirage.annotation.In;
import jp.sf.amateras.mirage.annotation.ResultSet;

public class ItemParamDto {
    @In
    public String agentCode;

    @In
    public String parentCode;
    
    @In
    public String itemName;
    
    @ResultSet
    public List<ItemDto> lstData;
}
